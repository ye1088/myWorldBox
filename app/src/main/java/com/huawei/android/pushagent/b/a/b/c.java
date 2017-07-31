package com.huawei.android.pushagent.b.a.b;

import android.content.Context;
import com.huawei.android.pushagent.c.a.e;
import java.io.InputStream;
import java.net.Socket;

public class c implements b {
    Socket a;

    public c(Context context) {
    }

    public void a() throws Exception {
        if (this.a != null) {
            this.a.close();
        }
    }

    public boolean a(Socket socket) {
        e.a("PushLogAC2705", "enter NormalChannel:init(" + socket.getRemoteSocketAddress() + ")");
        if (socket.isConnected()) {
            this.a = socket;
            return true;
        }
        e.d("PushLogAC2705", "when init SSL Channel, socket is not ready:" + socket);
        return false;
    }

    public boolean a(byte[] bArr) throws Exception {
        if (this.a == null || this.a.getOutputStream() == null) {
            e.d("PushLogAC2705", "when call send, socket is not ready!!");
            return false;
        }
        this.a.getOutputStream().write(bArr);
        this.a.getOutputStream().flush();
        return true;
    }

    public boolean b() {
        return this.a != null && this.a.isConnected();
    }

    public Socket c() {
        return this.a;
    }

    public InputStream d() {
        if (this.a != null) {
            try {
                return this.a.getInputStream();
            } catch (Throwable e) {
                e.c("PushLogAC2705", "call socket.getInputStream cause:" + e.toString(), e);
            }
        }
        return null;
    }
}
