package com.huluxia.mcsdk;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.media.session.PlaybackStateCompat;
import com.huluxia.framework.base.utils.UtilsMD5;
import io.netty.handler.codec.http2.Http2CodecUtil;
import io.netty.handler.codec.memcache.binary.BinaryMemcacheResponseStatus;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.DiscardPolicy;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpProtocolParams;

/* compiled from: Downloader */
public class f {
    public static final int DEFAULT_MAX_CONN = 10;
    private static int DEFAULT_POOL_SIZE = 4096;
    public static final int DEFAULT_SOCKET_BUFFER_SIZE = 8192;
    public static final int DEFAULT_SOCKET_TIMEOUT = 10000;
    private static f aof;
    private static DefaultHttpClient downloadHttpClient;
    protected final a aoe = new a(DEFAULT_POOL_SIZE);
    ExecutorService service = new ThreadPoolExecutor(3, 5, 5000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque(), new DiscardPolicy());
    private Handler uiHandler = new Handler(Looper.getMainLooper());

    /* compiled from: Downloader */
    public static class a {
        protected static final Comparator<byte[]> BUF_COMPARATOR = new Comparator<byte[]>() {
            public int compare(byte[] lhs, byte[] rhs) {
                return lhs.length - rhs.length;
            }
        };
        private List<byte[]> mBuffersByLastUse = new LinkedList();
        private List<byte[]> mBuffersBySize = new ArrayList(64);
        private int mCurrentSize = 0;
        private final int mSizeLimit;

        public a(int sizeLimit) {
            this.mSizeLimit = sizeLimit;
        }

        public synchronized byte[] getBuf(int len) {
            byte[] buf;
            for (int i = 0; i < this.mBuffersBySize.size(); i++) {
                buf = (byte[]) this.mBuffersBySize.get(i);
                if (buf.length >= len) {
                    this.mCurrentSize -= buf.length;
                    this.mBuffersBySize.remove(i);
                    this.mBuffersByLastUse.remove(buf);
                    break;
                }
            }
            buf = new byte[len];
            return buf;
        }

        public synchronized void returnBuf(byte[] buf) {
            if (buf != null) {
                if (buf.length <= this.mSizeLimit) {
                    this.mBuffersByLastUse.add(buf);
                    int pos = Collections.binarySearch(this.mBuffersBySize, buf, BUF_COMPARATOR);
                    if (pos < 0) {
                        pos = (-pos) - 1;
                    }
                    this.mBuffersBySize.add(pos, buf);
                    this.mCurrentSize += buf.length;
                    trim();
                }
            }
        }

        private synchronized void trim() {
            while (this.mCurrentSize > this.mSizeLimit) {
                byte[] buf = (byte[]) this.mBuffersByLastUse.remove(0);
                this.mBuffersBySize.remove(buf);
                this.mCurrentSize -= buf.length;
            }
        }
    }

    /* compiled from: Downloader */
    public interface b {
        void onCallBack(boolean z, String str, String str2);
    }

    /* compiled from: Downloader */
    public static class c extends ByteArrayOutputStream {
        private static final int DEFAULT_SIZE = 256;
        private final a aoe;

        public c(a pool) {
            this(pool, 256);
        }

        public c(a pool, int size) {
            this.aoe = pool;
            this.buf = this.aoe.getBuf(Math.max(size, 256));
        }

        public void close() throws IOException {
            this.aoe.returnBuf(this.buf);
            this.buf = null;
            super.close();
        }

        public void finalize() {
            this.aoe.returnBuf(this.buf);
        }

        private void expand(int i) {
            if (this.count + i > this.buf.length) {
                byte[] newbuf = this.aoe.getBuf((this.count + i) * 2);
                System.arraycopy(this.buf, 0, newbuf, 0, this.count);
                this.aoe.returnBuf(this.buf);
                this.buf = newbuf;
            }
        }

        public synchronized void write(byte[] buffer, int offset, int len) {
            expand(len);
            super.write(buffer, offset, len);
        }

        public synchronized void write(int oneByte) {
            expand(1);
            super.write(oneByte);
        }
    }

    /* compiled from: Downloader */
    public static class d extends SSLSocketFactory {
        SSLContext sslContext = SSLContext.getInstance(SSLSocketFactory.TLS);

        public d(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
            super(truststore);
            TrustManager tm = new X509TrustManager(this) {
                final /* synthetic */ d aom;

                {
                    this.aom = this$0;
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }
            };
            this.sslContext.init(null, new TrustManager[]{tm}, null);
        }

        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
            return this.sslContext.getSocketFactory().createSocket(socket, host, port, autoClose);
        }

        public Socket createSocket() throws IOException {
            return this.sslContext.getSocketFactory().createSocket();
        }
    }

    public static synchronized f CK() {
        f fVar;
        synchronized (f.class) {
            if (aof == null) {
                aof = new f();
            }
            fVar = aof;
        }
        return fVar;
    }

    f() {
    }

    public void a(String url, String fileDir, String finalName, b callback) {
        final String path = fileDir + File.separator + UtilsMD5.getMD5String(url);
        final String str = url;
        final String str2 = fileDir;
        final String str3 = finalName;
        final b bVar = callback;
        this.service.submit(new Runnable(this) {
            final /* synthetic */ f aoj;

            public void run() {
                try {
                    byte[] responseContents;
                    HttpClient client = f.getDownloadHttpClient();
                    long progress = this.aoj.getProgress(str, path);
                    HttpUriRequest request = new HttpGet(str);
                    short[] sArr = new short[13];
                    String HEADER_CONTENT_TYPE = b.a(new short[]{(short) 0, (short) 195, (short) 238, (short) 236, (short) 247, (short) 225, (short) 235, (short) 242, (short) 170, (short) 220, (short) 240, (short) 250, (short) 238});
                    sArr = new short[10];
                    String HEADER_RANG_VALUE = b.a(new short[]{(short) 0, (short) 226, (short) 248, (short) 246, (short) 230, (short) 247, (short) 184, (short) 163, (short) 227, (short) 165});
                    short[] sArr2 = new short[6];
                    String HEADER_RANG = b.a(new short[]{(short) 0, (short) 210, (short) 224, (short) 236, (short) 228, (short) 225});
                    sArr = new short[49];
                    request.addHeader(new BasicHeader(HEADER_CONTENT_TYPE, b.a(new short[]{(short) 0, (short) 225, (short) 241, (short) 242, (short) 239, (short) 237, (short) 230, (short) 231, (short) 243, (short) 225, (short) 230, (short) 228, (short) 164, (short) 244, (short) 160, (short) 249, (short) 248, (short) 231, (short) 188, (short) 244, (short) 252, (short) 230, (short) 248, (short) 187, (short) 226, (short) 234, (short) 245, Http2CodecUtil.MAX_UNSIGNED_BYTE, (short) 245, Http2CodecUtil.MAX_UNSIGNED_BYTE, (short) 242, (short) 250, (short) 250, (short) 196, (short) 154, BinaryMemcacheResponseStatus.ENOMEM, (short) 192, (short) 204, (short) 196, (short) 212, (short) 212, (short) 205, (short) 221, (short) 151, (short) 254, (short) 248, (short) 235, (short) 131, (short) 151})));
                    if (progress > 0) {
                        request.addHeader(new BasicHeader(HEADER_RANG, String.format(HEADER_RANG_VALUE, new Object[]{Long.valueOf(progress)})));
                    }
                    HttpResponse httpResponse = client.execute(request);
                    Map<String, String> responseHeaders = f.convertHeaders(httpResponse.getAllHeaders());
                    int statusCode = httpResponse.getStatusLine().getStatusCode();
                    if (httpResponse.getEntity() != null) {
                        responseContents = this.aoj.entityToBytes(httpResponse.getEntity(), request, responseHeaders, path);
                    } else {
                        responseContents = new byte[0];
                    }
                    if (statusCode < 200 || statusCode > 299) {
                        throw new IOException();
                    }
                    final File finalFile = new File(str2, str3);
                    new File(path).renameTo(finalFile);
                    final byte[] bArr = responseContents;
                    this.aoj.uiHandler.post(new Runnable(this) {
                        final /* synthetic */ AnonymousClass1 aol;

                        public void run() {
                            boolean succ = false;
                            if (bVar != null) {
                                if (!(bArr == null || bArr.equals(new byte[0]))) {
                                    succ = true;
                                }
                                bVar.onCallBack(succ, str, finalFile.getAbsolutePath());
                            }
                        }
                    });
                } catch (IOException e) {
                    this.aoj.uiHandler.post(new Runnable(this) {
                        final /* synthetic */ AnonymousClass1 aol;

                        {
                            this.aol = this$1;
                        }

                        public void run() {
                            if (bVar != null) {
                                bVar.onCallBack(false, str, path);
                            }
                        }
                    });
                }
            }
        });
    }

    private long getProgress(String url, String path) {
        File file = new File(path);
        if (file.exists()) {
            return file.length() - PlaybackStateCompat.ACTION_PLAY_FROM_URI;
        }
        return 0;
    }

    protected byte[] entityToBytes(HttpEntity entity, HttpUriRequest request, Map<String, String> responseHeaders, String filePath) throws IOException {
        File file = new File(filePath);
        if (file.exists()) {
            return downloadResume(entity, file, responseHeaders);
        }
        file.createNewFile();
        return downloadNewFile(entity, file);
    }

    private byte[] downloadResume(HttpEntity entity, File file, Map<String, String> responseHeaders) throws IOException {
        int i = 14;
        String RESPONSE_HEADER_RANG = b.a(new short[]{(short) 0, (short) 195, (short) 238, (short) 236, (short) 247, (short) 225, (short) 235, (short) 242, (short) 170, (short) 218, (short) 232, (short) 228, (short) 236, (short) 233});
        if (!responseHeaders.containsKey(RESPONSE_HEADER_RANG) || !file.exists()) {
            return downloadNewFile(entity, file);
        }
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
        byte[] buffer = null;
        InputStream in = entity.getContent();
        if (in == null) {
            throw null;
        }
        buffer = this.aoe.getBuf(8192);
        long seekLocation = 0;
        String rangeValue = (String) responseHeaders.get(RESPONSE_HEADER_RANG);
        int bytePrefix = "bytes ".length();
        if (bytePrefix > rangeValue.length()) {
            throw null;
        }
        rangeValue = rangeValue.substring(bytePrefix, rangeValue.length());
        if (rangeValue.contains("-")) {
            try {
                seekLocation = Long.parseLong(rangeValue.split("-")[0]);
            } catch (NumberFormatException e) {
            }
            try {
                randomAccessFile.seek(seekLocation);
            } finally {
                entity.consumeContent();
                this.aoe.returnBuf(buffer);
                randomAccessFile.close();
            }
        }
        randomAccessFile.seek(seekLocation);
        while (true) {
            int count = in.read(buffer);
            if (count == -1) {
                break;
            }
            randomAccessFile.write(buffer, 0, count);
        }
        byte[] bytes = file.getAbsolutePath().getBytes();
        return bytes;
    }

    private byte[] downloadNewFile(HttpEntity entity, File file) throws IOException {
        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
        byte[] buffer = null;
        try {
            InputStream in = entity.getContent();
            if (in == null) {
                return null;
            }
            buffer = this.aoe.getBuf(8192);
            while (true) {
                int count = in.read(buffer);
                if (count == -1) {
                    byte[] bytes = file.getAbsolutePath().getBytes();
                    entity.consumeContent();
                    this.aoe.returnBuf(buffer);
                    outputStream.close();
                    return bytes;
                }
                outputStream.write(buffer, 0, count);
            }
        } finally {
            entity.consumeContent();
            this.aoe.returnBuf(buffer);
            outputStream.close();
        }
    }

    protected static Map<String, String> convertHeaders(Header[] headers) {
        Map<String, String> result = new HashMap();
        for (int i = 0; i < headers.length; i++) {
            result.put(headers[i].getName(), headers[i].getValue());
        }
        return result;
    }

    public static DefaultHttpClient getDownloadHttpClient() {
        if (downloadHttpClient != null) {
            return downloadHttpClient;
        }
        SSLSocketFactory sf;
        String strHttp;
        String strHttps;
        SchemeRegistry schemeRegistry;
        String PROTOCOL_HEADER = b.a(new short[]{(short) 0, (short) 232, (short) 245, (short) 246, (short) 243, (short) 170, (short) 245, (short) 244, (short) 232, (short) 252, (short) 230, (short) 233, (short) 228, (short) 224, (short) 163, (short) 239, (short) 227, (short) 252, (short) 254, (short) 229, (short) 190, (short) 247, (short) 252, (short) 228, (short) 244, (short) 237, (short) 245, (short) 251, (short) 233, (short) 177, (short) 239, (short) 251, (short) 251, (short) 201, (short) 211, (short) 199, (short) 192, (short) 208, (short) 214});
        BasicHttpParams httpParams = new BasicHttpParams();
        httpParams.setParameter(PROTOCOL_HEADER, Boolean.valueOf(true));
        ConnManagerParams.setTimeout(httpParams, 10000);
        ConnManagerParams.setMaxConnectionsPerRoute(httpParams, new ConnPerRouteBean(10));
        ConnManagerParams.setMaxTotalConnections(httpParams, 10);
        HttpConnectionParams.setSoTimeout(httpParams, 10000);
        HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
        HttpConnectionParams.setTcpNoDelay(httpParams, true);
        HttpConnectionParams.setSocketBufferSize(httpParams, 8192);
        HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
        try {
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(null, null);
            SSLSocketFactory sf2 = new d(trustStore);
            try {
                sf2.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                sf = sf2;
            } catch (Exception e) {
                sf = sf2;
                sf = SSLSocketFactory.getSocketFactory();
                strHttp = b.a(new short[]{(short) 0, (short) 232, (short) 245, (short) 246, (short) 243});
                strHttps = b.a(new short[]{(short) 0, (short) 232, (short) 245, (short) 246, (short) 243, (short) 247});
                schemeRegistry = new SchemeRegistry();
                schemeRegistry.register(new Scheme(strHttp, PlainSocketFactory.getSocketFactory(), 80));
                schemeRegistry.register(new Scheme(strHttps, sf, 443));
                downloadHttpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(httpParams, schemeRegistry), httpParams);
                return downloadHttpClient;
            }
        } catch (Exception e2) {
            sf = SSLSocketFactory.getSocketFactory();
            strHttp = b.a(new short[]{(short) 0, (short) 232, (short) 245, (short) 246, (short) 243});
            strHttps = b.a(new short[]{(short) 0, (short) 232, (short) 245, (short) 246, (short) 243, (short) 247});
            schemeRegistry = new SchemeRegistry();
            schemeRegistry.register(new Scheme(strHttp, PlainSocketFactory.getSocketFactory(), 80));
            schemeRegistry.register(new Scheme(strHttps, sf, 443));
            downloadHttpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(httpParams, schemeRegistry), httpParams);
            return downloadHttpClient;
        }
        strHttp = b.a(new short[]{(short) 0, (short) 232, (short) 245, (short) 246, (short) 243});
        strHttps = b.a(new short[]{(short) 0, (short) 232, (short) 245, (short) 246, (short) 243, (short) 247});
        schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme(strHttp, PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme(strHttps, sf, 443));
        downloadHttpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(httpParams, schemeRegistry), httpParams);
        return downloadHttpClient;
    }
}
