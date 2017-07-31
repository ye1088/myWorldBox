package com.huawei.android.pushagent.b.a.a.b;

import android.os.Bundle;
import com.huawei.android.pushagent.a.b.l;
import com.huawei.android.pushagent.b.a.a.a;
import com.huawei.android.pushagent.c.a.e;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;

public class c extends com.huawei.android.pushagent.b.a.a.c {
    public c(a aVar) {
        super(aVar);
    }

    protected void b() throws Exception {
        byte i;
        Throwable e;
        InputStream inputStream = null;
        InputStream d;
        try {
            if (this.c.c == null || this.c.c.c() == null) {
                e.d("PushLogAC2705", "no socket when in readSSLSocket");
                if (inputStream != null) {
                    inputStream.close();
                }
                if (this.c.c != null) {
                    this.c.c.a();
                    return;
                }
                return;
            }
            Socket c = this.c.c.c();
            if (c != null) {
                e.a("PushLogAC2705", "socket timeout is " + c.getSoTimeout());
            }
            d = this.c.c.d();
            byte b = (byte) -1;
            int i2 = -1;
            while (!isInterrupted() && this.c.c.b()) {
                try {
                    int i3;
                    if (b != (byte) -1) {
                        i3 = b;
                        b = (byte) -1;
                    } else if (d != null) {
                        i3 = d.read();
                    } else {
                        e.a("PushLogAC2705", "inputstream is null, cannot get cmdId");
                        i3 = i2;
                    }
                    if (-1 == i3) {
                        e.a("PushLogAC2705", "read -1 data, socket may be close");
                        break;
                    }
                    String a = com.huawei.android.pushagent.c.a.a(new byte[]{(byte) i3});
                    e.a("PushLogAC2705", "received a msg cmdId:" + a);
                    try {
                        Serializable a2;
                        if (l.c() == ((byte) i3)) {
                            e.a("PushLogAC2705", "is PushDataReqMessage set read TimeOut 100");
                            if (c != null) {
                                c.setSoTimeout(100);
                            } else {
                                e.a("PushLogAC2705", "socket is null");
                            }
                            a2 = com.huawei.android.pushagent.a.b.a.a.a(Byte.valueOf((byte) i3), d);
                            if (a2 != null) {
                                l lVar = (l) a2;
                                if (lVar.i() != (byte) -1) {
                                    e.a("PushLogAC2705", "is get next cmdId, so set it");
                                    i = lVar.i();
                                }
                            }
                            i = b;
                        } else {
                            a2 = com.huawei.android.pushagent.a.b.a.a.a(Byte.valueOf((byte) i3), d);
                            i = b;
                        }
                        if (a2 != null) {
                            try {
                                com.huawei.android.pushagent.c.a.b();
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("push_msg", a2);
                                this.c.a(com.huawei.android.pushagent.b.a.a.c.a.SocketEvent_MSG_RECEIVED, bundle);
                            } catch (InstantiationException e2) {
                                e.d("PushLogAC2705", "call getEntityByCmdId(cmd:" + i3 + " cause InstantiationException");
                                if (c != null) {
                                    continue;
                                } else if (com.huawei.android.pushagent.b.a.a.c() != this.c.e()) {
                                    this.c.c.c().setSoTimeout((int) (this.c.e.b(false) + com.huawei.android.pushagent.b.b.a.a(this.b).Q()));
                                } else {
                                    this.c.c.c().setSoTimeout(0);
                                }
                                b = i;
                                i2 = i3;
                            } catch (Exception e3) {
                                e.d("PushLogAC2705", "call getEntityByCmdId(cmd:" + i3 + " Exception");
                                if (c != null) {
                                    continue;
                                } else if (com.huawei.android.pushagent.b.a.a.c() != this.c.e()) {
                                    this.c.c.c().setSoTimeout((int) (this.c.e.b(false) + com.huawei.android.pushagent.b.b.a.a(this.b).Q()));
                                } else {
                                    this.c.c.c().setSoTimeout(0);
                                }
                                b = i;
                                i2 = i3;
                            }
                        } else {
                            e.d("PushLogAC2705", "received invalid msg, cmdId" + a);
                        }
                        if (c == null) {
                            continue;
                        } else if (com.huawei.android.pushagent.b.a.a.c() == this.c.e()) {
                            this.c.c.c().setSoTimeout(0);
                        } else {
                            this.c.c.c().setSoTimeout((int) (this.c.e.b(false) + com.huawei.android.pushagent.b.b.a.a(this.b).Q()));
                        }
                    } catch (InstantiationException e4) {
                        i = b;
                        e.d("PushLogAC2705", "call getEntityByCmdId(cmd:" + i3 + " cause InstantiationException");
                        if (c != null) {
                            continue;
                        } else if (com.huawei.android.pushagent.b.a.a.c() != this.c.e()) {
                            this.c.c.c().setSoTimeout(0);
                        } else {
                            this.c.c.c().setSoTimeout((int) (this.c.e.b(false) + com.huawei.android.pushagent.b.b.a.a(this.b).Q()));
                        }
                        b = i;
                        i2 = i3;
                    } catch (Exception e5) {
                        i = b;
                        e.d("PushLogAC2705", "call getEntityByCmdId(cmd:" + i3 + " Exception");
                        if (c != null) {
                            continue;
                        } else if (com.huawei.android.pushagent.b.a.a.c() != this.c.e()) {
                            this.c.c.c().setSoTimeout(0);
                        } else {
                            this.c.c.c().setSoTimeout((int) (this.c.e.b(false) + com.huawei.android.pushagent.b.b.a.a(this.b).Q()));
                        }
                        b = i;
                        i2 = i3;
                    }
                    b = i;
                    i2 = i3;
                } catch (SocketException e6) {
                    e = e6;
                    inputStream = d;
                } catch (IOException e7) {
                    e = e7;
                } catch (Exception e8) {
                    e = e8;
                } catch (Throwable th) {
                    if (c != null) {
                        if (com.huawei.android.pushagent.b.a.a.c() == this.c.e()) {
                            this.c.c.c().setSoTimeout(0);
                        } else {
                            this.c.c.c().setSoTimeout((int) (this.c.e.b(false) + com.huawei.android.pushagent.b.b.a.a(this.b).Q()));
                        }
                    }
                }
            }
            if (d != null) {
                d.close();
            }
            if (this.c.c != null) {
                this.c.c.a();
            }
            throw new com.huawei.android.pushagent.a.c(" read normal Exit", com.huawei.android.pushagent.a.c.a.Err_Read);
        } catch (SocketException e9) {
            e = e9;
            try {
                throw new com.huawei.android.pushagent.a.c(e, com.huawei.android.pushagent.a.c.a.Err_Read);
            } catch (Throwable th2) {
                e = th2;
                d = inputStream;
                if (d != null) {
                    d.close();
                }
                if (this.c.c != null) {
                    this.c.c.a();
                }
                throw e;
            }
        } catch (IOException e10) {
            e = e10;
            d = inputStream;
            try {
                throw new com.huawei.android.pushagent.a.c(e, com.huawei.android.pushagent.a.c.a.Err_Read);
            } catch (Throwable th3) {
                e = th3;
                if (d != null) {
                    d.close();
                }
                if (this.c.c != null) {
                    this.c.c.a();
                }
                throw e;
            }
        } catch (Exception e11) {
            e = e11;
            d = inputStream;
            throw new com.huawei.android.pushagent.a.c(e, com.huawei.android.pushagent.a.c.a.Err_Read);
        } catch (Throwable th4) {
            e = th4;
            d = inputStream;
            if (d != null) {
                d.close();
            }
            if (this.c.c != null) {
                this.c.c.a();
            }
            throw e;
        }
    }
}
