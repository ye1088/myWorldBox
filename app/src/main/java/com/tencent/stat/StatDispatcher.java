package com.tencent.stat;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import com.MCWorld.module.p;
import com.tencent.stat.common.RC4;
import com.tencent.stat.common.StatCommonHelper;
import com.tencent.stat.common.StatConstants;
import com.tencent.stat.common.StatLogger;
import com.tencent.stat.event.Event;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPOutputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

class StatDispatcher {
    private static Context applicationContext = null;
    private static long dispatcherThreadId = -1;
    private static StatDispatcher instance = null;
    private static StatLogger logger = StatCommonHelper.getLogger();
    DefaultHttpClient client = null;
    Handler handler = null;

    private StatDispatcher() {
        try {
            HandlerThread handlerThread = new HandlerThread("StatDispatcher");
            handlerThread.start();
            dispatcherThreadId = handlerThread.getId();
            this.handler = new Handler(handlerThread.getLooper());
            HttpParams basicHttpParams = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(basicHttpParams, 10000);
            HttpConnectionParams.setSoTimeout(basicHttpParams, 10000);
            this.client = new DefaultHttpClient(basicHttpParams);
            this.client.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy() {
                public long getKeepAliveDuration(HttpResponse httpResponse, HttpContext httpContext) {
                    long keepAliveDuration = super.getKeepAliveDuration(httpResponse, httpContext);
                    return keepAliveDuration == -1 ? 20000 : keepAliveDuration;
                }
            });
            if (StatConfig.getStatHttpProxy() != null) {
                this.client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, StatConfig.getStatHttpProxy());
            }
        } catch (Object th) {
            logger.e(th);
        }
    }

    static Context getApplicationContext() {
        return applicationContext;
    }

    static StatDispatcher getInstance() {
        if (instance == null) {
            instance = new StatDispatcher();
        }
        return instance;
    }

    static void setApplicationContext(Context context) {
        applicationContext = context.getApplicationContext();
    }

    void send(Event event, StatDispatchCallback statDispatchCallback) {
        send(Arrays.asList(new String[]{event.toJsonString()}), statDispatchCallback);
    }

    void send(final List<String> list, final StatDispatchCallback statDispatchCallback) {
        if (!list.isEmpty() && this.handler != null) {
            this.handler.post(new Runnable() {
                public void run() {
                    StatDispatcher.this.sendHttpPost(list, statDispatchCallback);
                }
            });
        }
    }

    void sendHttpPost(List<String> list, StatDispatchCallback statDispatchCallback) {
        Object obj = null;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int i = 0; i < list.size(); i++) {
            stringBuilder.append((String) list.get(i));
            if (i != list.size() - 1) {
                stringBuilder.append(MiPushClient.ACCEPT_TIME_SEPARATOR);
            }
        }
        stringBuilder.append("]");
        String statReportUrl = StatConfig.getStatReportUrl();
        logger.i("[" + statReportUrl + "]Send request(" + stringBuilder.toString().length() + "bytes):" + stringBuilder.toString());
        HttpPost httpPost = new HttpPost(statReportUrl);
        try {
            httpPost.addHeader("Accept-Encoding", "gzip");
            httpPost.setHeader("Connection", HTTP.CONN_KEEP_ALIVE);
            httpPost.removeHeaders("Cache-Control");
            HttpHost httpProxy = StatCommonHelper.getHttpProxy(applicationContext);
            if (httpProxy != null) {
                this.client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, StatCommonHelper.getHttpProxy(applicationContext));
                httpPost.addHeader("X-Online-Host", StatConstants.MTA_SERVER);
                httpPost.addHeader("Accept", "*/*");
                httpPost.addHeader("Content-Type", p.JSON);
                obj = 1;
            }
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bytes = stringBuilder.toString().getBytes("UTF-8");
            int length = bytes.length;
            if (stringBuilder.length() >= 256) {
                if (httpProxy == null) {
                    httpPost.addHeader("Content-Encoding", "rc4,gzip");
                } else {
                    httpPost.addHeader("X-Content-Encoding", "rc4,gzip");
                }
                byteArrayOutputStream.write(new byte[4]);
                GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
                gZIPOutputStream.write(bytes);
                gZIPOutputStream.close();
                bytes = byteArrayOutputStream.toByteArray();
                ByteBuffer.wrap(bytes, 0, 4).putInt(length);
                logger.d("before Gzip:" + length + " bytes, after Gzip:" + bytes.length + " bytes");
            } else if (httpProxy == null) {
                httpPost.addHeader("Content-Encoding", "rc4");
            } else {
                httpPost.addHeader("X-Content-Encoding", "rc4");
            }
            httpPost.setEntity(new ByteArrayEntity(RC4.encrypt(bytes)));
            HttpResponse execute = this.client.execute(httpPost);
            if (obj != null) {
                this.client.getParams().removeParameter(ConnRoutePNames.DEFAULT_PROXY);
            }
            HttpEntity entity = execute.getEntity();
            int statusCode = execute.getStatusLine().getStatusCode();
            long contentLength = entity.getContentLength();
            logger.i("recv response status code:" + statusCode + ", content length:" + contentLength);
            if (contentLength == 0) {
                EntityUtils.toString(entity);
                if (statusCode != 200) {
                    logger.error("Server response error code:" + statusCode);
                } else if (statDispatchCallback != null) {
                    statDispatchCallback.onDispatchSuccess();
                }
            } else if (contentLength > 0) {
                InputStream content = entity.getContent();
                bytes = new byte[((int) entity.getContentLength())];
                new DataInputStream(content).readFully(bytes);
                Header firstHeader = execute.getFirstHeader("Content-Encoding");
                if (firstHeader != null) {
                    if (firstHeader.getValue().equalsIgnoreCase("gzip,rc4")) {
                        bytes = RC4.decrypt(StatCommonHelper.deocdeGZipContent(bytes));
                    } else if (firstHeader.getValue().equalsIgnoreCase("rc4,gzip")) {
                        bytes = StatCommonHelper.deocdeGZipContent(RC4.decrypt(bytes));
                    } else if (firstHeader.getValue().equalsIgnoreCase("gzip")) {
                        bytes = StatCommonHelper.deocdeGZipContent(bytes);
                    } else if (firstHeader.getValue().equalsIgnoreCase("rc4")) {
                        bytes = RC4.decrypt(bytes);
                    }
                }
                if (statusCode == 200) {
                    try {
                        logger.d(new String(bytes, "UTF-8"));
                        JSONObject jSONObject = new JSONObject(new String(bytes, "UTF-8")).getJSONObject("cfg");
                        if (jSONObject != null) {
                            StatConfig.updateOnlineConfig(jSONObject);
                        }
                    } catch (JSONException e) {
                        logger.i(e.toString());
                    }
                    if (statDispatchCallback != null) {
                        statDispatchCallback.onDispatchSuccess();
                    }
                } else {
                    logger.error("Server response error code:" + statusCode + ", error:" + new String(bytes, "UTF-8"));
                }
                content.close();
            } else {
                EntityUtils.toString(entity);
            }
        } catch (Object th) {
            logger.e(th);
            if (statDispatchCallback != null) {
                statDispatchCallback.onDispatchFailure();
            }
        }
    }
}
