package com.MCWorld.http.base;

import android.os.SystemClock;
import android.util.Log;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Iterator;
import javax.net.ssl.SSLException;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;

/* compiled from: RetryHandler */
public class g implements HttpRequestRetryHandler {
    private static final int sf = 1500;
    private static HashSet<Class<?>> sg = new HashSet();
    private static HashSet<Class<?>> sh = new HashSet();
    private final int si;

    static {
        sg.add(NoHttpResponseException.class);
        sg.add(UnknownHostException.class);
        sg.add(SocketException.class);
        sg.add(ConnectTimeoutException.class);
        sh.add(InterruptedIOException.class);
        sh.add(SSLException.class);
    }

    public g(int maxRetries) {
        this.si = maxRetries;
    }

    public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
        boolean retry = true;
        Boolean b = (Boolean) context.getAttribute(ExecutionContext.HTTP_REQ_SENT);
        boolean sent = b != null && b.booleanValue();
        if (executionCount > this.si) {
            retry = false;
        } else if (a(sh, exception)) {
            retry = false;
        } else if (a(sg, exception)) {
            retry = true;
        } else if (!sent) {
            retry = true;
        }
        if (retry) {
            Log.i("executionCount retry", Integer.toString(executionCount));
            SystemClock.sleep(1500);
        } else {
            exception.printStackTrace();
        }
        return retry;
    }

    protected boolean a(HashSet<Class<?>> list, Throwable error) {
        Iterator<Class<?>> itr = list.iterator();
        while (itr.hasNext()) {
            if (((Class) itr.next()).isInstance(error)) {
                return true;
            }
        }
        return false;
    }
}
