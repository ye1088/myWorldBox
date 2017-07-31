package com.huawei.android.pushagent.b.a.a;

import android.content.Context;
import android.net.Proxy;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import com.huawei.android.pushagent.PushService;
import com.huawei.android.pushagent.a.d;
import com.huawei.android.pushagent.c.a.b;
import com.huawei.android.pushagent.c.c.e;
import io.netty.handler.codec.rtsp.RtspHeaders.Values;
import io.netty.util.internal.StringUtil;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.apache.tools.ant.util.DateUtils;
import org.apache.tools.ant.util.ProxySetup;

public abstract class c extends Thread {
    protected d a = null;
    protected Context b = null;
    protected a c = null;

    public enum a {
        SocketEvent_CONNECTING,
        SocketEvent_CONNECTED,
        SocketEvent_CLOSE,
        SocketEvent_MSG_RECEIVED
    }

    public c(a aVar) {
        super("SocketRead_" + new SimpleDateFormat(DateUtils.ISO8601_TIME_PATTERN, Locale.getDefault()).format(new Date()));
        this.c = aVar;
        this.b = aVar.d;
        this.a = aVar.a;
    }

    protected Socket a(String str, int i, boolean z) throws Exception {
        Socket socket;
        Throwable e;
        try {
            socket = new Socket();
            try {
                String property;
                String str2;
                int parseInt;
                socket.getTcpNoDelay();
                if (this instanceof com.huawei.android.pushagent.b.a.a.b.c) {
                    if (e.c()) {
                        e.a(1, com.huawei.android.pushagent.c.a.a(socket));
                    } else {
                        com.huawei.android.pushagent.c.a.a(1, com.huawei.android.pushagent.c.a.a(socket));
                    }
                }
                if (VERSION.SDK_INT >= 11) {
                    String property2 = System.getProperty(ProxySetup.HTTP_PROXY_HOST);
                    property = System.getProperty(ProxySetup.HTTP_PROXY_PORT);
                    if (property == null) {
                        property = "-1";
                    }
                    str2 = property2;
                    parseInt = Integer.parseInt(property);
                } else {
                    str2 = Proxy.getHost(this.b);
                    parseInt = Proxy.getPort(this.b);
                }
                int a = b.a(this.b);
                a(a.SocketEvent_CONNECTING, new Bundle());
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "enter createSocket(" + str + ":" + i + ", proxy:" + z + "(" + str2 + ":" + parseInt + ")");
                boolean z2 = (TextUtils.isEmpty(str2) || -1 == parseInt || 1 == a) ? false : true;
                boolean ac = com.huawei.android.pushagent.b.b.a.a(this.b).ac();
                com.huawei.android.pushagent.c.a.e.b("PushLogAC2705", "useProxy is valid:" + z2 + ", allow proxy:" + ac);
                if (z && z2 && ac) {
                    com.huawei.android.pushagent.c.a.e.b("PushLogAC2705", "use Proxy " + str2 + ":" + parseInt + " to connect to push server.");
                    socket.connect(new InetSocketAddress(str2, parseInt), ((int) com.huawei.android.pushagent.b.b.a.a(this.b).u()) * 1000);
                    property = "CONNECT " + str + ":" + i;
                    socket.getOutputStream().write((property + " HTTP/1.1\r\nHost: " + property + "\r\n\r\n").getBytes("UTF-8"));
                    InputStream inputStream = socket.getInputStream();
                    StringBuilder stringBuilder = new StringBuilder(100);
                    a = 0;
                    do {
                        char read = (char) inputStream.read();
                        stringBuilder.append(read);
                        a = ((a == 0 || a == 2) && read == StringUtil.CARRIAGE_RETURN) ? a + 1 : ((a == 1 || a == 3) && read == '\n') ? a + 1 : 0;
                    } while (a != 4);
                    property = new BufferedReader(new StringReader(stringBuilder.toString())).readLine();
                    if (property != null) {
                        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "read data:" + com.huawei.android.pushagent.c.a.a.e.a(property));
                    }
                } else {
                    com.huawei.android.pushagent.c.a.e.b("PushLogAC2705", "create socket without proxy");
                    socket.connect(new InetSocketAddress(str, i), ((int) com.huawei.android.pushagent.b.b.a.a(this.b).u()) * 1000);
                }
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "write the lastcontectsucc_time to the pushConfig.xml file");
                socket.setSoTimeout(((int) com.huawei.android.pushagent.b.b.a.a(this.b).u()) * 1000);
                return socket;
            } catch (UnsupportedEncodingException e2) {
                e = e2;
                com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "call getBytes cause:" + e.toString(), e);
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (Throwable e3) {
                        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "close socket cause:" + e3.toString(), e3);
                    }
                }
                throw new com.huawei.android.pushagent.a.c("create socket failed", com.huawei.android.pushagent.a.c.a.Err_Connect);
            } catch (SocketException e4) {
                e3 = e4;
                com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "call setSoTimeout cause:" + e3.toString(), e3);
                if (socket != null) {
                    socket.close();
                }
                throw new com.huawei.android.pushagent.a.c("create socket failed", com.huawei.android.pushagent.a.c.a.Err_Connect);
            } catch (IOException e5) {
                e3 = e5;
                com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "call connect cause:" + e3.toString(), e3);
                if (socket != null) {
                    socket.close();
                }
                throw new com.huawei.android.pushagent.a.c("create socket failed", com.huawei.android.pushagent.a.c.a.Err_Connect);
            } catch (NumberFormatException e6) {
                e3 = e6;
                com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "call connect cause:" + e3.toString(), e3);
                if (socket != null) {
                    socket.close();
                }
                throw new com.huawei.android.pushagent.a.c("create socket failed", com.huawei.android.pushagent.a.c.a.Err_Connect);
            } catch (IllegalArgumentException e7) {
                e3 = e7;
                com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "call connect cause:" + e3.toString(), e3);
                if (socket != null) {
                    socket.close();
                }
                throw new com.huawei.android.pushagent.a.c("create socket failed", com.huawei.android.pushagent.a.c.a.Err_Connect);
            } catch (Exception e8) {
                e3 = e8;
                com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "call createSocket cause:" + e3.toString(), e3);
                if (socket != null) {
                    socket.close();
                }
                throw new com.huawei.android.pushagent.a.c("create socket failed", com.huawei.android.pushagent.a.c.a.Err_Connect);
            }
        } catch (UnsupportedEncodingException e9) {
            e3 = e9;
            socket = null;
            com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "call getBytes cause:" + e3.toString(), e3);
            if (socket != null) {
                socket.close();
            }
            throw new com.huawei.android.pushagent.a.c("create socket failed", com.huawei.android.pushagent.a.c.a.Err_Connect);
        } catch (SocketException e10) {
            e3 = e10;
            socket = null;
            com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "call setSoTimeout cause:" + e3.toString(), e3);
            if (socket != null) {
                socket.close();
            }
            throw new com.huawei.android.pushagent.a.c("create socket failed", com.huawei.android.pushagent.a.c.a.Err_Connect);
        } catch (IOException e11) {
            e3 = e11;
            socket = null;
            com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "call connect cause:" + e3.toString(), e3);
            if (socket != null) {
                socket.close();
            }
            throw new com.huawei.android.pushagent.a.c("create socket failed", com.huawei.android.pushagent.a.c.a.Err_Connect);
        } catch (NumberFormatException e12) {
            e3 = e12;
            socket = null;
            com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "call connect cause:" + e3.toString(), e3);
            if (socket != null) {
                socket.close();
            }
            throw new com.huawei.android.pushagent.a.c("create socket failed", com.huawei.android.pushagent.a.c.a.Err_Connect);
        } catch (IllegalArgumentException e13) {
            e3 = e13;
            socket = null;
            com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "call connect cause:" + e3.toString(), e3);
            if (socket != null) {
                socket.close();
            }
            throw new com.huawei.android.pushagent.a.c("create socket failed", com.huawei.android.pushagent.a.c.a.Err_Connect);
        } catch (Exception e14) {
            e3 = e14;
            socket = null;
            com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "call createSocket cause:" + e3.toString(), e3);
            if (socket != null) {
                socket.close();
            }
            throw new com.huawei.android.pushagent.a.c("create socket failed", com.huawei.android.pushagent.a.c.a.Err_Connect);
        }
    }

    protected void a(a aVar, Bundle bundle) {
        this.c.a(aVar, bundle);
    }

    protected boolean a() throws com.huawei.android.pushagent.a.c {
        Socket socket = null;
        try {
            long currentTimeMillis = System.currentTimeMillis();
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "start to create socket");
            if (this.a == null || this.a.a == null || this.a.a.length() == 0) {
                com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "the addr is " + this.a + " is invalid");
                return false;
            } else if (this.a.c == null) {
                com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "config sslconetEntity.channelType cfgErr:" + this.a.c + " cannot connect!!");
                return false;
            } else {
                socket = a(this.a.a, this.a.b, this.a.d);
                com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "conetEntity.channelType:" + this.a.c);
                switch (this.a.c) {
                    case ChannelType_Normal:
                        this.c.c = new com.huawei.android.pushagent.b.a.b.c(this.b);
                        break;
                    case ChannelType_SSL:
                    case ChannelType_SSL_Resume:
                        this.c.c = new com.huawei.android.pushagent.b.a.b.d(this.b);
                        break;
                    case ChannelType_Secure:
                        this.c.c = new com.huawei.android.pushagent.b.a.b.a(this.b);
                        break;
                    default:
                        com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "conetEntity.channelType is invalid:" + this.a.c);
                        PushService.a().stopService();
                        socket.close();
                        return false;
                }
                if (this.c.c.a(socket)) {
                    socket.setSoTimeout(0);
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "connect cost " + (System.currentTimeMillis() - currentTimeMillis) + " ms, result:" + this.c.c.b());
                    if (this.c.c.b()) {
                        InetSocketAddress inetSocketAddress = new InetSocketAddress(this.a.a, this.a.b);
                        Bundle bundle = new Bundle();
                        bundle.putString("server_ip", inetSocketAddress.getAddress().getHostAddress());
                        bundle.putInt(Values.SERVER_PORT, inetSocketAddress.getPort());
                        bundle.putString("client_ip", socket.getLocalAddress().getHostAddress());
                        bundle.putInt(Values.CLIENT_PORT, socket.getLocalPort());
                        bundle.putInt("channelEntity", this.c.e().ordinal());
                        this.c.a(a.SocketEvent_CONNECTED, bundle);
                        return true;
                    }
                    com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "Socket connect failed");
                    throw new com.huawei.android.pushagent.a.c("create SSLSocket failed", com.huawei.android.pushagent.a.c.a.Err_Connect);
                }
                com.huawei.android.pushagent.c.a.e.d("PushLogAC2705", "call conetEntity.channel.init failed!!");
                socket.close();
                throw new com.huawei.android.pushagent.a.c("init socket error", com.huawei.android.pushagent.a.c.a.Err_Connect);
            }
        } catch (Throwable e) {
            com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "call connectSync cause " + e.toString(), e);
            if (socket != null) {
                try {
                    socket.close();
                } catch (Throwable e2) {
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "close socket cause:" + e2.toString(), e2);
                }
            }
            throw new com.huawei.android.pushagent.a.c(e, com.huawei.android.pushagent.a.c.a.Err_Connect);
        } catch (Throwable e3) {
            com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "call connectSync cause " + e3.toString(), e3);
            if (socket != null) {
                try {
                    socket.close();
                } catch (Throwable e22) {
                    com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "close socket cause:" + e22.toString(), e22);
                }
            }
            throw new com.huawei.android.pushagent.a.c(e3, com.huawei.android.pushagent.a.c.a.Err_Connect);
        }
    }

    protected abstract void b() throws Exception;

    public void run() {
        long currentTimeMillis = System.currentTimeMillis();
        try {
            if (a()) {
                currentTimeMillis = System.currentTimeMillis();
                b();
            }
            com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "normal to quit.");
            Bundle bundle = new Bundle();
            bundle.putLong("connect_time", System.currentTimeMillis() - currentTimeMillis);
            a(a.SocketEvent_CLOSE, bundle);
            com.huawei.android.pushagent.c.a.e.b("PushLogAC2705", "total connect Time:" + (System.currentTimeMillis() - currentTimeMillis) + " process quit, so close socket");
            if (this.c.c != null) {
                try {
                    this.c.c.a();
                } catch (Throwable e) {
                    com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", e.toString(), e);
                }
            }
        } catch (Throwable e2) {
            com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "connect occurs :" + e2.toString(), e2);
            Serializable serializable = e2.a;
            Bundle bundle2 = new Bundle();
            if (serializable != null) {
                bundle2.putSerializable("errorType", serializable);
            }
            bundle2.putString("push_exception", e2.toString());
            a(a.SocketEvent_CLOSE, bundle2);
            com.huawei.android.pushagent.c.a.e.b("PushLogAC2705", "total connect Time:" + (System.currentTimeMillis() - currentTimeMillis) + " process quit, so close socket");
            if (this.c.c != null) {
                try {
                    this.c.c.a();
                } catch (Throwable e22) {
                    com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", e22.toString(), e22);
                }
            }
        } catch (Throwable e222) {
            com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", "connect cause :" + e222.toString(), e222);
            Bundle bundle3 = new Bundle();
            bundle3.putString("push_exception", e222.toString());
            a(a.SocketEvent_CLOSE, bundle3);
            com.huawei.android.pushagent.c.a.e.b("PushLogAC2705", "total connect Time:" + (System.currentTimeMillis() - currentTimeMillis) + " process quit, so close socket");
            if (this.c.c != null) {
                try {
                    this.c.c.a();
                } catch (Throwable e2222) {
                    com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", e2222.toString(), e2222);
                }
            }
        } catch (Throwable th) {
            com.huawei.android.pushagent.c.a.e.b("PushLogAC2705", "total connect Time:" + (System.currentTimeMillis() - currentTimeMillis) + " process quit, so close socket");
            if (this.c.c != null) {
                try {
                    this.c.c.a();
                } catch (Throwable e3) {
                    com.huawei.android.pushagent.c.a.e.c("PushLogAC2705", e3.toString(), e3);
                }
            }
        }
        com.huawei.android.pushagent.c.a.e.a("PushLogAC2705", "connect thread exit!");
    }
}
