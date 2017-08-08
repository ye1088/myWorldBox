package com.MCWorld.framework.base;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
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
import org.apache.http.client.params.ClientPNames;
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

public class Downloader {
    public static final int DEFAULT_MAX_CONN = 10;
    private static int DEFAULT_POOL_SIZE = 4096;
    public static final int DEFAULT_SOCKET_BUFFER_SIZE = 8192;
    public static final int DEFAULT_SOCKET_TIMEOUT = 10000;
    private static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_RANG = "Range";
    private static final String HEADER_RANG_VALUE = "bytes=%d-";
    private static final String PROTOCOL_CHARSET = "UTF-8";
    private static final String PROTOCOL_CONTENT_TYPE = String.format("application/x-www-form-urlencoded; charset=%s", new Object[]{"UTF-8"});
    public static final String RESPONSE_HEADER_RANG = "Content-Range";
    private static final String TAG = "Downloader";
    private static DefaultHttpClient downloadHttpClient;
    private static Downloader downloader;
    protected final ByteArrayPool mPool = new ByteArrayPool(DEFAULT_POOL_SIZE);
    ExecutorService service = new ThreadPoolExecutor(3, 5, 5000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque(), new DiscardPolicy());
    private Handler uiHandler = new Handler(Looper.getMainLooper());

    public static class ByteArrayPool {
        protected static final Comparator<byte[]> BUF_COMPARATOR = new Comparator<byte[]>() {
            public int compare(byte[] lhs, byte[] rhs) {
                return lhs.length - rhs.length;
            }
        };
        private List<byte[]> mBuffersByLastUse = new LinkedList();
        private List<byte[]> mBuffersBySize = new ArrayList(64);
        private int mCurrentSize = 0;
        private final int mSizeLimit;

        public ByteArrayPool(int sizeLimit) {
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

    public interface ICallback {
        void onCallBack(boolean z, String str, String str2);
    }

    public static class PoolingByteArrayOutputStream extends ByteArrayOutputStream {
        private static final int DEFAULT_SIZE = 256;
        private final ByteArrayPool mPool;

        public PoolingByteArrayOutputStream(ByteArrayPool pool) {
            this(pool, 256);
        }

        public PoolingByteArrayOutputStream(ByteArrayPool pool, int size) {
            this.mPool = pool;
            this.buf = this.mPool.getBuf(Math.max(size, 256));
        }

        public void close() throws IOException {
            this.mPool.returnBuf(this.buf);
            this.buf = null;
            super.close();
        }

        public void finalize() {
            this.mPool.returnBuf(this.buf);
        }

        private void expand(int i) {
            if (this.count + i > this.buf.length) {
                byte[] newbuf = this.mPool.getBuf((this.count + i) * 2);
                System.arraycopy(this.buf, 0, newbuf, 0, this.count);
                this.mPool.returnBuf(this.buf);
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

    public static class SSLSocketFactoryEx extends SSLSocketFactory {
        SSLContext sslContext = SSLContext.getInstance(SSLSocketFactory.TLS);

        public SSLSocketFactoryEx(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
            super(truststore);
            TrustManager tm = new X509TrustManager() {
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

    public static synchronized Downloader getInstance() {
        Downloader downloader;
        synchronized (Downloader.class) {
            if (downloader == null) {
                downloader = new Downloader();
            }
            downloader = downloader;
        }
        return downloader;
    }

    Downloader() {
    }

    public void download(final String url, final String path, final ICallback callback) {
        this.service.submit(new Runnable() {
            public void run() {
                try {
                    byte[] responseContents;
                    HttpClient client = Downloader.getDownloadHttpClient();
                    long progress = Downloader.this.getProgress(url, path);
                    Log.v(Downloader.TAG, "download progess  = " + progress);
                    HttpUriRequest request = new HttpGet(url);
                    request.addHeader(new BasicHeader("Content-Type", Downloader.PROTOCOL_CONTENT_TYPE));
                    if (progress > 0) {
                        request.addHeader(new BasicHeader("Range", String.format(Downloader.HEADER_RANG_VALUE, new Object[]{Long.valueOf(progress)})));
                    }
                    HttpResponse httpResponse = client.execute(request);
                    Map<String, String> responseHeaders = Downloader.convertHeaders(httpResponse.getAllHeaders());
                    int statusCode = httpResponse.getStatusLine().getStatusCode();
                    if (httpResponse.getEntity() != null) {
                        responseContents = Downloader.this.entityToBytes(httpResponse.getEntity(), request, responseHeaders, path);
                    } else {
                        responseContents = new byte[0];
                    }
                    if (statusCode < 200 || statusCode > 299) {
                        throw new IOException();
                    }
                    Downloader.this.uiHandler.post(new Runnable() {
                        public void run() {
                            boolean succ = false;
                            if (callback != null) {
                                if (!(responseContents == null || responseContents.equals(new byte[0]))) {
                                    succ = true;
                                }
                                callback.onCallBack(succ, url, path);
                            }
                        }
                    });
                } catch (IOException e) {
                    Log.e(Downloader.TAG, "io exception happen");
                    Downloader.this.uiHandler.post(new Runnable() {
                        public void run() {
                            if (callback != null) {
                                callback.onCallBack(false, url, path);
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
        if (!responseHeaders.containsKey("Content-Range") || !file.exists()) {
            return downloadNewFile(entity, file);
        }
        Log.e(TAG, "downloadResume");
        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rwd");
        byte[] buffer = null;
        InputStream in = entity.getContent();
        if (in == null) {
            throw null;
        }
        buffer = this.mPool.getBuf(8192);
        long seekLocation = 0;
        String rangeValue = (String) responseHeaders.get("Content-Range");
        int bytePrefix = "bytes ".length();
        if (bytePrefix > rangeValue.length()) {
            Log.e(TAG, "downloadResume rangeValue INVALID");
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
                this.mPool.returnBuf(buffer);
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
        Log.e(TAG, "downloadNewFile");
        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
        byte[] buffer = null;
        try {
            InputStream in = entity.getContent();
            if (in == null) {
                return null;
            }
            long total = entity.getContentLength();
            buffer = this.mPool.getBuf(8192);
            while (true) {
                int count = in.read(buffer);
                if (count == -1) {
                    byte[] bytes = file.getAbsolutePath().getBytes();
                    entity.consumeContent();
                    this.mPool.returnBuf(buffer);
                    outputStream.close();
                    return bytes;
                }
                outputStream.write(buffer, 0, count);
            }
        } finally {
            entity.consumeContent();
            this.mPool.returnBuf(buffer);
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
        SSLSocketFactory sf;
        SchemeRegistry schemeRegistry;
        if (downloadHttpClient != null) {
            return downloadHttpClient;
        }
        BasicHttpParams httpParams = new BasicHttpParams();
        httpParams.setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, Boolean.valueOf(true));
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
            SSLSocketFactory sf2 = new SSLSocketFactoryEx(trustStore);
            try {
                sf2.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
                sf = sf2;
            } catch (Exception e) {
                sf = sf2;
                sf = SSLSocketFactory.getSocketFactory();
                schemeRegistry = new SchemeRegistry();
                schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
                schemeRegistry.register(new Scheme("https", sf, 443));
                downloadHttpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(httpParams, schemeRegistry), httpParams);
                return downloadHttpClient;
            }
        } catch (Exception e2) {
            sf = SSLSocketFactory.getSocketFactory();
            schemeRegistry = new SchemeRegistry();
            schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
            schemeRegistry.register(new Scheme("https", sf, 443));
            downloadHttpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(httpParams, schemeRegistry), httpParams);
            return downloadHttpClient;
        }
        schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        schemeRegistry.register(new Scheme("https", sf, 443));
        downloadHttpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(httpParams, schemeRegistry), httpParams);
        return downloadHttpClient;
    }
}
