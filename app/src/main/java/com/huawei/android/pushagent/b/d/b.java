package com.huawei.android.pushagent.b.d;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.huawei.android.pushagent.b.b.c;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushagent.c.a.h;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class b {
    private static b e = null;
    private int a = 3;
    private long b = 600000;
    private long c = 300000;
    private long d = 300000;
    private int f = 0;
    private ArrayList g = new ArrayList();

    static class a implements Comparable {
        private long a;
        private boolean b;

        a() {
        }

        public int a(a aVar) {
            return (int) ((a() - aVar.a()) / 1000);
        }

        public long a() {
            return this.a;
        }

        public void a(long j) {
            this.a = j;
        }

        public void a(boolean z) {
            this.b = z;
        }

        public boolean a(String str) {
            if (TextUtils.isEmpty(str)) {
                return false;
            }
            try {
                String[] split = str.split(";");
                if (split.length < 2) {
                    e.d("PushLogAC2705", "load connectinfo " + str + " error");
                    return false;
                }
                this.a = Long.parseLong(split[0]);
                this.b = Boolean.parseBoolean(split[1]);
                return true;
            } catch (Throwable e) {
                e.c("PushLogAC2705", "load connectinfo " + str + " error:" + e.toString(), e);
                return false;
            }
        }

        public boolean b() {
            return this.b;
        }

        public /* synthetic */ int compareTo(Object obj) {
            return a((a) obj);
        }

        public boolean equals(Object obj) {
            return this == obj ? true : obj == null ? false : getClass() != obj.getClass() ? false : !(obj instanceof a) ? false : this.b == ((a) obj).b && this.a == ((a) obj).a;
        }

        public int hashCode() {
            return (this.b ? 1 : 0) + ((((int) (this.a ^ (this.a >>> 32))) + 527) * 31);
        }

        public String toString() {
            if (this.a <= 0) {
                return "";
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(this.a).append(";").append(this.b);
            return stringBuffer.toString();
        }
    }

    public enum b {
        SOCKET_CLOSE,
        SOCKET_CONNECTED,
        TRS_QUERIED,
        NETWORK_CHANGE
    }

    private b() {
    }

    public static synchronized b a(Context context) {
        b bVar;
        synchronized (b.class) {
            if (e == null) {
                e = new b();
            }
            if (e.g.isEmpty()) {
                e.c(context);
            }
            bVar = e;
        }
        return bVar;
    }

    private void a(Context context, boolean z) {
        a aVar;
        e.a("PushLogAC2705", "save connection info " + z);
        long currentTimeMillis = System.currentTimeMillis();
        Collection arrayList = new ArrayList();
        Iterator it = this.g.iterator();
        while (it.hasNext()) {
            aVar = (a) it.next();
            if (currentTimeMillis < aVar.a() || currentTimeMillis - aVar.a() > this.b) {
                arrayList.add(aVar);
            }
        }
        if (!arrayList.isEmpty()) {
            e.a("PushLogAC2705", "some connection info is expired:" + arrayList.size());
            this.g.removeAll(arrayList);
        }
        aVar = new a();
        aVar.a(z);
        aVar.a(System.currentTimeMillis());
        if (this.g.size() < this.a) {
            this.g.add(aVar);
        } else {
            this.g.remove(0);
            this.g.add(aVar);
        }
        String str = "|";
        StringBuffer stringBuffer = new StringBuffer();
        Iterator it2 = this.g.iterator();
        while (it2.hasNext()) {
            stringBuffer.append(((a) it2.next()).toString());
            stringBuffer.append(str);
        }
        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
        new h(context, "PushConnectControl").a("connectPushSvrInfos", stringBuffer.toString());
    }

    private boolean a() {
        if (this.g.size() < this.a) {
            e.a("PushLogAC2705", "total connect times is less than " + this.a);
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis();
        Iterator it = this.g.iterator();
        int i = 0;
        while (it.hasNext()) {
            a aVar = (a) it.next();
            int i2 = (currentTimeMillis <= aVar.a() || currentTimeMillis - aVar.a() >= this.b) ? i : i + 1;
            i = i2;
        }
        e.a("PushLogAC2705", "connect times in last " + this.b + " is " + i + ", limits is " + this.a);
        return i >= this.a;
    }

    private void b() {
        this.f = 0;
    }

    private void b(Context context, boolean z) {
        e.a("PushLogAC2705", "set bad network mode " + z);
        c.a(context, new com.huawei.android.pushagent.a.a("isBadNetworkMode", Boolean.class, Boolean.valueOf(z)));
    }

    private void c() {
        this.f++;
    }

    private void c(Context context) {
        int i = 0;
        this.a = com.huawei.android.pushagent.b.b.a.a(context).Z();
        this.b = com.huawei.android.pushagent.b.b.a.a(context).Y();
        this.c = com.huawei.android.pushagent.b.b.a.a(context).ab();
        this.d = com.huawei.android.pushagent.b.b.a.a(context).aa();
        String b = new h(context, "PushConnectControl").b("connectPushSvrInfos");
        if (!TextUtils.isEmpty(b)) {
            e.a("PushLogAC2705", "connectPushSvrInfos is " + b);
            for (String str : b.split("\\|")) {
                a aVar = new a();
                if (aVar.a(str)) {
                    this.g.add(aVar);
                }
            }
        }
        Collections.sort(this.g);
        if (this.g.size() > this.a) {
            Collection arrayList = new ArrayList();
            int size = this.g.size() - this.a;
            while (i < size) {
                arrayList.add(this.g.get(i));
                i++;
            }
            this.g.removeAll(arrayList);
        }
    }

    private void d(Context context) {
        if (!g(context)) {
            e.a("PushLogAC2705", "It is not bad network mode, do nothing");
        } else if (this.g.isEmpty()) {
            b(context, false);
        } else {
            a aVar = (a) this.g.get(this.g.size() - 1);
            if (aVar.b()) {
                e.a("PushLogAC2705", "last connection is success");
                long currentTimeMillis = System.currentTimeMillis();
                long a = aVar.a();
                if (currentTimeMillis - a > this.c || currentTimeMillis < a) {
                    e.a("PushLogAC2705", this.c + " has passed since last connect");
                    b(context, false);
                    return;
                }
                e.a("PushLogAC2705", "connection keep too short , still in bad network mode");
                return;
            }
            e.a("PushLogAC2705", "last connection result is false , still in bad network mode");
        }
    }

    private long e(Context context) {
        if (this.g.isEmpty()) {
            e.a("PushLogAC2705", "first connection, return 0");
            return 0;
        }
        long k;
        long o;
        if (!c.a(context, "cloudpush_isNoDelayConnect", false)) {
            if (((long) this.f) != com.huawei.android.pushagent.b.b.a.a(context).s()) {
                switch (this.f) {
                    case 0:
                        k = 1000 * com.huawei.android.pushagent.b.b.a.a(context).k();
                        break;
                    case 1:
                        k = 1000 * com.huawei.android.pushagent.b.b.a.a(context).l();
                        break;
                    case 2:
                        k = 1000 * com.huawei.android.pushagent.b.b.a.a(context).m();
                        break;
                    case 3:
                        k = 1000 * com.huawei.android.pushagent.b.b.a.a(context).n();
                        break;
                    default:
                        o = 1000 * com.huawei.android.pushagent.b.b.a.a(context).o();
                        com.huawei.android.pushagent.b.b.a.a(context).a = true;
                        k = o;
                        break;
                }
            }
            com.huawei.android.pushagent.b.b.a.a(context).a = true;
            k = 1000 * com.huawei.android.pushagent.b.b.a.a(context).o();
        } else {
            k = 1000;
        }
        long currentTimeMillis = System.currentTimeMillis();
        o = ((a) this.g.get(this.g.size() - 1)).a;
        if (currentTimeMillis < o) {
            e.a("PushLogAC2705", "now is less than last connect time");
            o = 0;
        } else {
            o = Math.max((o + k) - currentTimeMillis, 0);
        }
        e.b("PushLogAC2705", "after getConnectPushSrvInterval:" + o + " ms, connectTimes:" + this.f);
        return o;
    }

    private long f(Context context) {
        if (a()) {
            b(context, true);
        }
        boolean g = g(context);
        e.a("PushLogAC2705", "bad network mode is " + g);
        if (!g || this.g.isEmpty()) {
            return 0;
        }
        long currentTimeMillis = System.currentTimeMillis();
        long b = ((a) this.g.get(this.g.size() - 1)).a;
        if (currentTimeMillis < b) {
            e.a("PushLogAC2705", "now is less than last connect time");
            b = 0;
        } else {
            b = Math.max((b + this.d) - currentTimeMillis, 0);
        }
        e.a("PushLogAC2705", "It is in bad network mode, connect limit interval is " + b);
        return b;
    }

    private boolean g(Context context) {
        return c.a(context, "isBadNetworkMode", false);
    }

    public void a(Context context, b bVar, Bundle bundle) {
        e.a("PushLogAC2705", "receive reconnectevent:" + bVar);
        switch (bVar) {
            case NETWORK_CHANGE:
                b();
                return;
            case TRS_QUERIED:
                b();
                return;
            case SOCKET_CLOSE:
                d(context);
                if (bundle.containsKey("errorType")) {
                    if (com.huawei.android.pushagent.a.c.a.Err_Connect == ((com.huawei.android.pushagent.a.c.a) bundle.getSerializable("errorType"))) {
                        a(context, false);
                    } else {
                        e.a("PushLogAC2705", "socket close not caused by connect error, do not need save connection info");
                    }
                } else {
                    e.a("PushLogAC2705", "socket close not caused by pushException");
                }
                c();
                com.huawei.android.pushagent.b.a.a.a(context).a(b(context));
                return;
            case SOCKET_CONNECTED:
                b();
                a(context, true);
                return;
            default:
                return;
        }
    }

    public long b(Context context) {
        return Math.max(e(context), f(context));
    }
}
