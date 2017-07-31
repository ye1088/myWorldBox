package com.huawei.android.pushagent.b.a.b;

import android.content.Context;
import com.huawei.android.pushagent.c.a.a.f;
import com.huawei.android.pushagent.c.a.e;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.SecureRandom;

public class a implements b {
    private static byte[] b;
    private static byte[] c;
    protected Socket a;
    private Context d;
    private boolean e = false;

    private class a extends InputStream {
        final /* synthetic */ a a;
        private InputStream b;
        private byte[] c = null;
        private int d = 0;

        public a(a aVar, InputStream inputStream) {
            this.a = aVar;
            this.b = inputStream;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public int read() throws java.io.IOException {
            /*
            r4 = this;
            r0 = -1;
            r1 = r4.a;
            monitor-enter(r1);
            r2 = r4.a;	 Catch:{ all -> 0x0035 }
            r2 = r2.e;	 Catch:{ all -> 0x0035 }
            if (r2 != 0) goto L_0x0017;
        L_0x000c:
            r2 = "PushLogAC2705";
            r3 = "secure socket is not initialized, can not read any data";
            com.huawei.android.pushagent.c.a.e.c(r2, r3);	 Catch:{ all -> 0x0035 }
            monitor-exit(r1);	 Catch:{ all -> 0x0035 }
        L_0x0016:
            return r0;
        L_0x0017:
            monitor-exit(r1);	 Catch:{ all -> 0x0035 }
            r1 = r4.c;
            if (r1 == 0) goto L_0x0041;
        L_0x001c:
            r1 = r4.c;
            r1 = r1.length;
            if (r1 <= 0) goto L_0x0041;
        L_0x0021:
            r1 = r4.d;
            r2 = r4.c;
            r2 = r2.length;
            if (r1 >= r2) goto L_0x0038;
        L_0x0028:
            r0 = r4.c;
            r1 = r4.d;
            r2 = r1 + 1;
            r4.d = r2;
            r0 = r0[r1];
            r0 = r0 & 255;
            goto L_0x0016;
        L_0x0035:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x0035 }
            throw r0;
        L_0x0038:
            r1 = "PushLogAC2705";
            r2 = "bufferByte has read end , need read bytes from socket";
            com.huawei.android.pushagent.c.a.e.a(r1, r2);
        L_0x0041:
            r1 = 0;
            r4.c = r1;
            r1 = 0;
            r4.d = r1;
            r1 = r4.b;
            if (r1 == 0) goto L_0x009c;
        L_0x004b:
            r1 = r4.b;
            r1 = r1.read();
            if (r0 != r1) goto L_0x005d;
        L_0x0053:
            r1 = "PushLogAC2705";
            r2 = "read -1 from inputstream";
            com.huawei.android.pushagent.c.a.e.c(r1, r2);
            goto L_0x0016;
        L_0x005d:
            r2 = 48;
            if (r2 != r1) goto L_0x0091;
        L_0x0061:
            r1 = r4.b;
            r1 = com.huawei.android.pushagent.b.a.b.a.c(r1);
            r2 = com.huawei.android.pushagent.b.a.b.a.c;
            r1 = com.huawei.android.pushagent.c.a.a.f.b(r1, r2);
            r4.c = r1;
            r1 = r4.c;
            if (r1 == 0) goto L_0x007a;
        L_0x0075:
            r1 = r4.c;
            r1 = r1.length;
            if (r1 != 0) goto L_0x0084;
        L_0x007a:
            r1 = "PushLogAC2705";
            r2 = "ase decrypt serverkey error";
            com.huawei.android.pushagent.c.a.e.c(r1, r2);
            goto L_0x0016;
        L_0x0084:
            r0 = r4.c;
            r1 = r4.d;
            r2 = r1 + 1;
            r4.d = r2;
            r0 = r0[r1];
            r0 = r0 & 255;
            goto L_0x0016;
        L_0x0091:
            r1 = "PushLogAC2705";
            r2 = "read secure message error, return -1";
            com.huawei.android.pushagent.c.a.e.c(r1, r2);
            goto L_0x0016;
        L_0x009c:
            r1 = "PushLogAC2705";
            r2 = "secureInputStream is null, return -1";
            com.huawei.android.pushagent.c.a.e.c(r1, r2);
            goto L_0x0016;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushagent.b.a.b.a.a.read():int");
        }
    }

    public a(Context context) {
        this.d = context;
    }

    public static void a(InputStream inputStream, byte[] bArr) throws IOException {
        int i = 0;
        while (i < bArr.length) {
            int read = inputStream.read(bArr, i, bArr.length - i);
            if (-1 == read) {
                throw new IOException("read -1 reached");
            }
            i += read;
        }
    }

    private byte[] a(Context context) throws IOException {
        byte T = (byte) com.huawei.android.pushagent.b.b.a.a(context).T();
        String U = com.huawei.android.pushagent.b.b.a.a(context).U();
        byte[] bArr = new byte[16];
        new SecureRandom().nextBytes(bArr);
        e.a("PushLogAC2705", "ready to send SecureChannelReqMessage, save clientKey for decode serverKey");
        b(bArr);
        byte[] a = f.a(bArr, U);
        if (a == null) {
            e.c("PushLogAC2705", "rsa encrypr clientKey error");
            return new byte[0];
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(20);
        byteArrayOutputStream.write(com.huawei.android.pushagent.c.a.b(((a.length + 1) + 1) + 2));
        byteArrayOutputStream.write(T);
        byteArrayOutputStream.write(a);
        return byteArrayOutputStream.toByteArray();
    }

    public static synchronized void b(byte[] bArr) {
        synchronized (a.class) {
            if (bArr != null) {
                if (bArr.length != 0) {
                    b = new byte[bArr.length];
                    System.arraycopy(bArr, 0, b, 0, bArr.length);
                }
            }
            e.a("PushLogAC2705", "key is null");
        }
    }

    private boolean b(Socket socket) {
        if (socket == null) {
            e.c("PushLogAC2705", "socket is null");
            return false;
        } else if (socket.isConnected()) {
            return true;
        } else {
            e.c("PushLogAC2705", "when init Channel, socket is not ready");
            return false;
        }
    }

    public static synchronized void c(byte[] bArr) {
        synchronized (a.class) {
            if (bArr != null) {
                if (bArr.length != 0) {
                    c = new byte[bArr.length];
                    System.arraycopy(bArr, 0, c, 0, bArr.length);
                }
            }
            e.a("PushLogAC2705", "key is null");
        }
    }

    private static byte[] c(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[2];
        a(inputStream, bArr);
        bArr = new byte[(com.huawei.android.pushagent.c.a.c(bArr) - 3)];
        a(inputStream, bArr);
        return bArr;
    }

    private static byte[] d(byte[] bArr) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(48);
        byte[] a = f.a(bArr, c);
        if (a == null || a.length == 0) {
            e.a("PushLogAC2705", "aes encrypt pushMsgData error");
            return new byte[0];
        }
        byteArrayOutputStream.write(com.huawei.android.pushagent.c.a.b((a.length + 1) + 2));
        byteArrayOutputStream.write(a);
        return byteArrayOutputStream.toByteArray();
    }

    private static synchronized byte[] f() {
        byte[] bArr;
        synchronized (a.class) {
            bArr = b;
        }
        return bArr;
    }

    public synchronized void a() {
        e.a("PushLogAC2705", "enter pushChannel:close()");
        this.e = false;
        try {
            if (this.a == null) {
                e.c("PushLogAC2705", "socket is null, not need close");
                this.a = null;
            } else {
                if (this.a.isClosed()) {
                    e.c("PushLogAC2705", "socket has been closed");
                } else {
                    this.a.close();
                }
                this.a = null;
            }
        } catch (Throwable e) {
            e.c("PushLogAC2705", "close socket error: " + e.toString(), e);
            this.a = null;
        } catch (Throwable th) {
            this.a = null;
        }
    }

    public synchronized boolean a(Socket socket) {
        boolean z = false;
        synchronized (this) {
            if (b(socket)) {
                this.a = socket;
                try {
                    byte[] a = a(this.d);
                    OutputStream outputStream = this.a.getOutputStream();
                    if (outputStream == null) {
                        e.c("PushLogAC2705", "outputStream is null");
                    } else if (a.length == 0) {
                        e.c("PushLogAC2705", "data is null");
                    } else {
                        outputStream.write(a);
                        outputStream.flush();
                        InputStream inputStream = this.a.getInputStream();
                        if (b(socket)) {
                            int read = inputStream.read();
                            if (-1 == read) {
                                e.a("PushLogAC2705", " read -1 when init secure channel, socket maybe closed");
                            } else if (21 == read) {
                                a = a(inputStream);
                                if (a != null) {
                                    c(f.b(a, f()));
                                    this.e = true;
                                    z = true;
                                } else {
                                    e.a("PushLogAC2705", "get server key error");
                                }
                            } else {
                                e.a("PushLogAC2705", "cmdId is not CMD_SECUREKEYEXCHANGE_RSP");
                            }
                        }
                    }
                } catch (Throwable e) {
                    e.c("PushLogAC2705", "call send cause:" + e.toString(), e);
                }
                a();
            } else {
                a();
            }
        }
        return z;
    }

    public synchronized boolean a(byte[] bArr) throws Exception {
        boolean z = false;
        synchronized (this) {
            if (this.a == null) {
                e.c("PushLogAC2705", "socket is null");
            } else if (this.e) {
                try {
                    byte[] d = d(bArr);
                    OutputStream outputStream = this.a.getOutputStream();
                    if (outputStream == null) {
                        e.c("PushLogAC2705", "outputStream is null");
                    } else if (d.length == 0) {
                        e.c("PushLogAC2705", "data is null");
                    } else {
                        outputStream.write(d);
                        outputStream.flush();
                        z = true;
                    }
                } catch (Throwable e) {
                    e.c("PushLogAC2705", "call send cause:" + e.toString(), e);
                    a();
                }
            } else {
                e.c("PushLogAC2705", "secure socket is not initialized, can not write any data");
                a();
            }
        }
        return z;
    }

    public byte[] a(InputStream inputStream) throws Exception {
        a(inputStream, new byte[2]);
        byte[] bArr = new byte[1];
        a(inputStream, bArr);
        byte b = bArr[0];
        e.a("PushLogAC2705", "result is " + b);
        if (b == (byte) 0) {
            bArr = new byte[32];
            a(inputStream, bArr);
            return bArr;
        }
        e.c("PushLogAC2705", "secure key exchange error");
        return null;
    }

    public boolean b() {
        if (this.a != null) {
            return this.a.isConnected();
        }
        e.c("PushLogAC2705", "socket is null");
        return false;
    }

    public Socket c() {
        return this.a;
    }

    public InputStream d() {
        try {
            if (this.a != null) {
                return new a(this, this.a.getInputStream());
            }
            e.c("PushLogAC2705", "socket is null");
            return null;
        } catch (Throwable e) {
            e.c("PushLogAC2705", "call socket.getInputStream cause:" + e.toString(), e);
        }
    }
}
