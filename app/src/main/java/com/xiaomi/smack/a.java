package com.xiaomi.smack;

import android.util.Pair;
import com.xiaomi.channel.commonutils.logger.b;
import com.xiaomi.channel.commonutils.string.c;
import com.xiaomi.push.service.XMPushService;
import com.xiaomi.push.service.x;
import com.xiaomi.push.service.z;
import com.xiaomi.smack.packet.d;
import com.xiaomi.smack.packet.f;
import java.io.Reader;
import java.io.Writer;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class a {
    public static boolean a;
    private static final AtomicInteger q = new AtomicInteger(0);
    protected int b = 0;
    protected long c = -1;
    protected volatile long d = 0;
    protected volatile long e = 0;
    protected int f;
    protected final Map<f, a> g = new ConcurrentHashMap();
    protected final Map<f, a> h = new ConcurrentHashMap();
    protected com.xiaomi.smack.debugger.a i = null;
    protected Reader j;
    protected Writer k;
    protected String l = "";
    protected String m = "";
    protected final int n = q.getAndIncrement();
    protected b o;
    protected XMPushService p;
    private LinkedList<Pair<Integer, Long>> r = new LinkedList();
    private final Collection<d> s = new CopyOnWriteArrayList();
    private int t = 2;
    private long u = 0;

    protected static class a {
        private f a;
        private com.xiaomi.smack.filter.a b;

        public a(f fVar, com.xiaomi.smack.filter.a aVar) {
            this.a = fVar;
            this.b = aVar;
        }

        public void a(d dVar) {
            if (this.b == null || this.b.a(dVar)) {
                this.a.a(dVar);
            }
        }
    }

    static {
        a = false;
        try {
            a = Boolean.getBoolean("smack.debugEnabled");
        } catch (Exception e) {
        }
        j.a();
    }

    protected a(XMPushService xMPushService, b bVar) {
        this.o = bVar;
        this.p = xMPushService;
    }

    private String a(int i) {
        return i == 1 ? "connected" : i == 0 ? "connecting" : i == 2 ? "disconnected" : "unknown";
    }

    private void b(int i) {
        synchronized (this.r) {
            if (i == 1) {
                this.r.clear();
            } else {
                this.r.add(new Pair(Integer.valueOf(i), Long.valueOf(System.currentTimeMillis())));
                if (this.r.size() > 6) {
                    this.r.remove(0);
                }
            }
        }
    }

    public b a() {
        return this.o;
    }

    public void a(int i, int i2, Exception exception) {
        if (i != this.t) {
            b.a(String.format("update the connection status. %1$s -> %2$s : %3$s ", new Object[]{a(this.t), a(i), z.a(i2)}));
        }
        if (com.xiaomi.channel.commonutils.network.d.d(this.p)) {
            b(i);
        }
        if (i == 1) {
            this.p.a(10);
            if (this.t != 0) {
                b.a("try set connected while not connecting.");
            }
            this.t = i;
            for (d a : this.s) {
                a.a(this);
            }
        } else if (i == 0) {
            if (this.t != 2) {
                b.a("try set connecting while not disconnected.");
            }
            this.t = i;
            for (d a2 : this.s) {
                a2.b(this);
            }
        } else if (i == 2) {
            this.p.a(10);
            if (this.t == 0) {
                for (d a22 : this.s) {
                    a22.a(this, exception == null ? new CancellationException("disconnect while connecting") : exception);
                }
            } else if (this.t == 1) {
                for (d a222 : this.s) {
                    a222.a(this, i2, exception);
                }
            }
            this.t = i;
        }
    }

    public abstract void a(x.b bVar);

    public void a(d dVar) {
        if (dVar != null && !this.s.contains(dVar)) {
            this.s.add(dVar);
        }
    }

    public void a(f fVar, com.xiaomi.smack.filter.a aVar) {
        if (fVar == null) {
            throw new NullPointerException("Packet listener is null.");
        }
        this.g.put(fVar, new a(fVar, aVar));
    }

    public abstract void a(d dVar);

    public abstract void a(f fVar, int i, Exception exception);

    public synchronized void a(String str) {
        if (this.t == 0) {
            b.a("setChallenge hash = " + c.a(str).substring(0, 8));
            this.l = str;
            a(1, 0, null);
        } else {
            b.a("ignore setChallenge because connection was disconnected");
        }
    }

    public abstract void a(String str, String str2);

    public abstract void a(d[] dVarArr);

    public boolean a(long j) {
        return this.u >= j;
    }

    public String b() {
        return this.o.c();
    }

    public void b(d dVar) {
        this.s.remove(dVar);
    }

    public void b(f fVar, com.xiaomi.smack.filter.a aVar) {
        if (fVar == null) {
            throw new NullPointerException("Packet listener is null.");
        }
        this.h.put(fVar, new a(fVar, aVar));
    }

    protected void b(d dVar) {
        for (a a : this.h.values()) {
            a.a(dVar);
        }
    }

    public String c() {
        return this.o.f();
    }

    public String d() {
        return this.o.d();
    }

    public int e() {
        return this.f;
    }

    public long f() {
        return this.e;
    }

    protected void g() {
        Class cls = null;
        if (this.j != null && this.k != null && this.o.g()) {
            if (this.i == null) {
                String property;
                try {
                    property = System.getProperty("smack.debuggerClass");
                } catch (Throwable th) {
                    Object obj = cls;
                }
                if (property != null) {
                    try {
                        cls = Class.forName(property);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (cls == null) {
                    this.i = new com.xiaomi.measite.smack.a(this, this.k, this.j);
                    this.j = this.i.a();
                    this.k = this.i.b();
                    return;
                }
                try {
                    this.i = (com.xiaomi.smack.debugger.a) cls.getConstructor(new Class[]{a.class, Writer.class, Reader.class}).newInstance(new Object[]{this, this.k, this.j});
                    this.j = this.i.a();
                    this.k = this.i.b();
                    return;
                } catch (Throwable e2) {
                    throw new IllegalArgumentException("Can't initialize the configured debugger!", e2);
                }
            }
            this.j = this.i.a(this.j);
            this.k = this.i.a(this.k);
        }
    }

    public boolean h() {
        return this.t == 0;
    }

    public boolean i() {
        return this.t == 1;
    }

    public int j() {
        return this.b;
    }

    public void k() {
        this.b = 0;
    }

    public long l() {
        return this.c;
    }

    public void m() {
        this.c = -1;
    }

    public abstract void n();

    public int o() {
        return this.t;
    }

    public void p() {
        this.u = System.currentTimeMillis();
    }

    public boolean q() {
        return System.currentTimeMillis() - this.u < ((long) j.b());
    }

    public void r() {
        synchronized (this.r) {
            this.r.clear();
        }
    }
}
