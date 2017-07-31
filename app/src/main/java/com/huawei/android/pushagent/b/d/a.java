package com.huawei.android.pushagent.b.d;

import android.content.Context;
import android.text.TextUtils;
import com.huawei.android.pushagent.b.b.c;
import com.huawei.android.pushagent.c.a.b;
import com.huawei.android.pushagent.c.a.e;
import com.huawei.android.pushagent.c.a.h;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

public class a {
    private static a h = null;
    Context a = null;
    List b = new LinkedList();
    List c = new LinkedList();
    List d = new LinkedList();
    List e = new LinkedList();
    List f = new LinkedList();
    List g = new LinkedList();

    private a(Context context) {
        this.a = context;
        f();
        if (this.b.size() == 0 && this.c.size() == 0 && this.d.size() == 0 && this.e.size() == 0 && this.f.size() == 0 && this.g.size() == 0) {
            e.a("PushLogAC2705", "Connect Control is not set, begin to config it");
            c();
        }
    }

    private boolean a(int i) {
        return 1 == b.a(this.a) ? a(this.g) : a(this.d);
    }

    public static synchronized boolean a(Context context) {
        boolean z;
        synchronized (a.class) {
            b(context);
            if (h == null) {
                e.d("PushLogAC2705", "cannot get ConnectControlMgr instance, may be system err!!");
                z = false;
            } else {
                z = h.d();
            }
        }
        return z;
    }

    public static synchronized boolean a(Context context, int i) {
        boolean z;
        synchronized (a.class) {
            b(context);
            if (h == null) {
                e.d("PushLogAC2705", "cannot get ConnectControlMgr instance, may be system err!!");
                z = false;
            } else {
                z = h.a(i);
            }
        }
        return z;
    }

    private boolean a(h hVar, List list, String str) throws Exception {
        String str2 = "\\|";
        list.clear();
        String b = hVar.b(str);
        if (TextUtils.isEmpty(b)) {
            e.a("PushLogAC2705", str + " is not set");
        } else {
            e.a("PushLogAC2705", str + SimpleComparison.EQUAL_TO_OPERATION + b);
            for (String str3 : b.split(str2)) {
                com.huawei.android.pushagent.b.d.a.a aVar = new com.huawei.android.pushagent.b.d.a.a();
                if (aVar.a(str3)) {
                    list.add(aVar);
                }
            }
        }
        return true;
    }

    private boolean a(List list) {
        if (a(list, 1)) {
            b(list, 1);
            e();
            return true;
        }
        e.b("PushLogAC2705", "volumeControl not allow to pass!!");
        return false;
    }

    private boolean a(List list, long j) {
        if (list == null || list.size() == 0) {
            e.a("PushLogAC2705", "there is no volome control");
            return true;
        }
        for (com.huawei.android.pushagent.b.d.a.b bVar : list) {
            if (bVar.a(j)) {
                e.a("PushLogAC2705", " pass:" + bVar);
            } else {
                e.b("PushLogAC2705", " not pass:" + bVar);
                return false;
            }
        }
        return true;
    }

    private boolean a(List list, List list2) {
        if (list == null && list2 == null) {
            return true;
        }
        if (list == null || list2 == null || list.size() != list2.size()) {
            return false;
        }
        for (com.huawei.android.pushagent.b.d.a.b bVar : list) {
            Object obj;
            for (com.huawei.android.pushagent.b.d.a.b a : list2) {
                if (bVar.a(a)) {
                    obj = 1;
                    continue;
                    break;
                }
            }
            obj = null;
            continue;
            if (obj == null) {
                return false;
            }
        }
        return true;
    }

    public static synchronized a b(Context context) {
        a aVar;
        synchronized (a.class) {
            if (h == null) {
                h = new a(context);
            }
            aVar = h;
        }
        return aVar;
    }

    private boolean b() {
        List linkedList = new LinkedList();
        linkedList.add(new com.huawei.android.pushagent.b.d.a.a(86400000, com.huawei.android.pushagent.b.b.a.a(this.a).G()));
        linkedList.add(new com.huawei.android.pushagent.b.d.a.a(3600000, com.huawei.android.pushagent.b.b.a.a(this.a).H()));
        if (a(linkedList, this.b)) {
            linkedList = new LinkedList();
            linkedList.add(new com.huawei.android.pushagent.b.d.a.a(86400000, com.huawei.android.pushagent.b.b.a.a(this.a).I()));
            if (a(linkedList, this.c)) {
                List linkedList2 = new LinkedList();
                for (Entry entry : com.huawei.android.pushagent.b.b.a.a(this.a).J().entrySet()) {
                    linkedList2.add(new com.huawei.android.pushagent.b.d.a.a(((Long) entry.getKey()).longValue() * 1000, ((Long) entry.getValue()).longValue()));
                }
                if (a(linkedList2, this.d)) {
                    linkedList = new LinkedList();
                    linkedList.add(new com.huawei.android.pushagent.b.d.a.a(86400000, com.huawei.android.pushagent.b.b.a.a(this.a).K()));
                    linkedList.add(new com.huawei.android.pushagent.b.d.a.a(3600000, com.huawei.android.pushagent.b.b.a.a(this.a).L()));
                    if (a(linkedList, this.e)) {
                        linkedList = new LinkedList();
                        linkedList.add(new com.huawei.android.pushagent.b.d.a.a(86400000, com.huawei.android.pushagent.b.b.a.a(this.a).M()));
                        if (a(linkedList, this.f)) {
                            linkedList2 = new LinkedList();
                            for (Entry entry2 : com.huawei.android.pushagent.b.b.a.a(this.a).R().entrySet()) {
                                linkedList2.add(new com.huawei.android.pushagent.b.d.a.a(((Long) entry2.getKey()).longValue() * 1000, ((Long) entry2.getValue()).longValue()));
                            }
                            if (a(linkedList2, this.g)) {
                                e.a("PushLogAC2705", "cur control is equal trs cfg");
                                return true;
                            }
                            e.a("PushLogAC2705", "wifiVolumeControl cfg is change!!");
                            return false;
                        }
                        e.a("PushLogAC2705", "wifiTrsFlowControl cfg is change!!");
                        return false;
                    }
                    e.a("PushLogAC2705", "wifiTrsFirstFlowControl cfg is change!");
                    return false;
                }
                e.a("PushLogAC2705", "flowcControl cfg is change!!");
                return false;
            }
            e.a("PushLogAC2705", "trsFlowControl cfg is change!!");
            return false;
        }
        e.a("PushLogAC2705", "trsFirstFlowControl cfg is change!");
        return false;
    }

    private boolean b(h hVar, List list, String str) throws Exception {
        String str2 = "|";
        StringBuffer stringBuffer = new StringBuffer();
        for (com.huawei.android.pushagent.b.d.a.b a : list) {
            stringBuffer.append(a.a()).append(str2);
        }
        if (hVar.a(str, stringBuffer.toString())) {
            return true;
        }
        e.d("PushLogAC2705", "save " + str + " failed!!");
        return false;
    }

    private synchronized boolean b(List list, long j) {
        boolean z;
        if (list != null) {
            if (list.size() != 0) {
                for (com.huawei.android.pushagent.b.d.a.b bVar : list) {
                    if (!bVar.b(j)) {
                        e.b("PushLogAC2705", " control info:" + bVar);
                        z = false;
                        break;
                    }
                }
                z = true;
            }
        }
        z = true;
        return z;
    }

    private boolean b(List list, List list2) {
        if (0 == c.a(this.a, "lastQueryTRSsucc_time", 0)) {
            if (a(list, 1)) {
                b(list, 1);
            } else {
                e.d("PushLogAC2705", "trsFirstFlowControl not allowed to pass!!");
                return false;
            }
        } else if (a(list2, 1)) {
            b(list2, 1);
        } else {
            e.d("PushLogAC2705", "trsFlowControl not allowed to pass!!");
            return false;
        }
        e();
        return true;
    }

    public static synchronized void c(Context context) {
        synchronized (a.class) {
            b(context);
            if (!(h == null || h.b())) {
                e.a("PushLogAC2705", "TRS cfg change, need reload");
                h.c();
            }
        }
    }

    private boolean c() {
        this.b.clear();
        this.b.add(new com.huawei.android.pushagent.b.d.a.a(86400000, com.huawei.android.pushagent.b.b.a.a(this.a).G()));
        this.b.add(new com.huawei.android.pushagent.b.d.a.a(3600000, com.huawei.android.pushagent.b.b.a.a(this.a).H()));
        this.c.clear();
        this.c.add(new com.huawei.android.pushagent.b.d.a.a(86400000, com.huawei.android.pushagent.b.b.a.a(this.a).I()));
        this.d.clear();
        for (Entry entry : com.huawei.android.pushagent.b.b.a.a(this.a).J().entrySet()) {
            this.d.add(new com.huawei.android.pushagent.b.d.a.a(((Long) entry.getKey()).longValue() * 1000, ((Long) entry.getValue()).longValue()));
        }
        this.e.clear();
        this.e.add(new com.huawei.android.pushagent.b.d.a.a(86400000, com.huawei.android.pushagent.b.b.a.a(this.a).K()));
        this.e.add(new com.huawei.android.pushagent.b.d.a.a(3600000, com.huawei.android.pushagent.b.b.a.a(this.a).L()));
        this.f.clear();
        this.f.add(new com.huawei.android.pushagent.b.d.a.a(86400000, com.huawei.android.pushagent.b.b.a.a(this.a).M()));
        this.g.clear();
        for (Entry entry2 : com.huawei.android.pushagent.b.b.a.a(this.a).R().entrySet()) {
            this.g.add(new com.huawei.android.pushagent.b.d.a.a(((Long) entry2.getKey()).longValue() * 1000, ((Long) entry2.getValue()).longValue()));
        }
        e();
        return true;
    }

    private boolean d() {
        return 1 == b.a(this.a) ? b(this.e, this.f) : b(this.b, this.c);
    }

    private boolean e() {
        try {
            h hVar = new h(this.a, "PushConnectControl");
            return b(hVar, this.e, "wifiTrsFirstFlowControlData") && b(hVar, this.f, "wifiTrsFlowControlData") && b(hVar, this.g, "wifiVolumeControlData") && b(hVar, this.b, "trsFirstFlowControlData") && b(hVar, this.c, "trsFlowControlData") && b(hVar, this.d, "volumeControlData");
        } catch (Throwable e) {
            e.c("PushLogAC2705", e.toString(), e);
            return false;
        }
    }

    private boolean f() {
        try {
            h hVar = new h(this.a, "PushConnectControl");
            a(hVar, this.b, "trsFirstFlowControlData");
            a(hVar, this.c, "trsFlowControlData");
            a(hVar, this.d, "volumeControlData");
            a(hVar, this.e, "wifiTrsFirstFlowControlData");
            a(hVar, this.f, "wifiTrsFlowControlData");
            a(hVar, this.g, "wifiVolumeControlData");
            return true;
        } catch (Throwable e) {
            e.c("PushLogAC2705", e.toString(), e);
            return false;
        }
    }

    public void a() {
        this.b.clear();
        this.c.clear();
        this.d.clear();
        this.e.clear();
        this.f.clear();
        this.g.clear();
    }
}
