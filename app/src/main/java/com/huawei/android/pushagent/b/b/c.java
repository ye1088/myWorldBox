package com.huawei.android.pushagent.b.b;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.huawei.android.pushagent.a.a;
import com.huawei.android.pushagent.b.a.b.b;
import com.huawei.android.pushagent.c.a.e;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

public class c {
    public static final HashMap a = new HashMap();
    private static c b = null;
    private HashMap c = new HashMap();
    private Context d = null;

    static {
        c();
    }

    private c(Context context) {
        this.d = context;
        a();
    }

    public static int a(Context context, String str, int i) {
        try {
            Object b = b(context, str);
            if (b != null) {
                i = ((Integer) b).intValue();
            }
        } catch (Throwable e) {
            e.c("PushLogAC2705", e.toString(), e);
        }
        return i;
    }

    public static long a(Context context, String str, long j) {
        try {
            Object b = b(context, str);
            if (b != null) {
                j = ((Long) b).longValue();
            }
        } catch (Throwable e) {
            e.c("PushLogAC2705", e.toString(), e);
        }
        return j;
    }

    public static a a(Context context, String str) {
        if (a(context) == null || str == null) {
            return null;
        }
        a aVar = (a) b.c.get(str);
        return aVar == null ? null : aVar;
    }

    public static synchronized c a(Context context) {
        c cVar;
        synchronized (c.class) {
            if (b != null) {
                cVar = b;
            } else if (context == null) {
                cVar = null;
            } else {
                b = new c(context);
                cVar = b;
            }
        }
        return cVar;
    }

    public static String a(Context context, String str, String str2) {
        Object b = b(context, str);
        if (b == null) {
            return str2;
        }
        String str3;
        try {
            str3 = (String) b;
        } catch (Exception e) {
            e.a("PushLogAC2705", "getString from config failed!");
            str3 = str2;
        }
        return str3;
    }

    public static void a(Context context, a aVar) {
        if (aVar == null || aVar.a == null) {
            e.d("PushLogAC2705", "set value err, cfg is null or itemName is null, cfg:" + aVar);
        } else if (a(context) == null) {
            e.d("PushLogAC2705", "System init failed in set Value");
        } else {
            b.c.put(aVar.a, aVar);
            b.b(context, aVar);
        }
    }

    public static boolean a(Context context, String str, boolean z) {
        try {
            Object b = b(context, str);
            if (b != null) {
                z = ((Boolean) b).booleanValue();
            }
        } catch (Throwable e) {
            e.c("PushLogAC2705", e.toString(), e);
        }
        return z;
    }

    public static b.a b(Context context) {
        a a = a(context, "USE_SSL");
        b.a aVar = b.a.b;
        if (a == null) {
            return aVar;
        }
        e.a("PushLogAC2705", " " + a);
        Integer num = (Integer) a.b;
        if (num.intValue() >= 0 && num.intValue() < b.a.values().length) {
            return b.a.values()[num.intValue()];
        }
        e.d("PushLogAC2705", "useSSL:" + a.b + " is invalid cfg");
        return aVar;
    }

    private static Object b(Context context, String str) {
        if (a(context) == null) {
            return null;
        }
        a aVar = (a) b.c.get(str);
        return aVar == null ? null : aVar.b;
    }

    private boolean b(Context context, a aVar) {
        if (context == null) {
            context = this.d;
        }
        Editor edit = context.getSharedPreferences("pushConfig", 4).edit();
        if (Boolean.class == aVar.c) {
            edit.putBoolean(aVar.a, ((Boolean) aVar.b).booleanValue());
        } else if (String.class == aVar.c) {
            edit.putString(aVar.a, (String) aVar.b);
        } else if (Long.class == aVar.c) {
            edit.putLong(aVar.a, ((Long) aVar.b).longValue());
        } else if (Integer.class == aVar.c) {
            edit.putInt(aVar.a, ((Integer) aVar.b).intValue());
        } else if (Float.class == aVar.c) {
            edit.putFloat(aVar.a, ((Float) aVar.b).floatValue());
        }
        return edit.commit();
    }

    private static void c() {
        a.clear();
        a.put("cloudpush_isLogLocal", new a("cloudpush_isLogLocal", Boolean.class, Boolean.valueOf(false)));
        a.put("cloudpush_pushLogLevel", new a("cloudpush_pushLogLevel", Integer.class, Integer.valueOf(4)));
        a.put("cloudpush_isReportLog", new a("cloudpush_isReportLog", Boolean.class, Boolean.valueOf(false)));
        a.put("cloudpush_isNoDelayConnect", new a("cloudpush_isNoDelayConnect", Boolean.class, Boolean.valueOf(false)));
        a.put("cloudpush_isSupportUpdate", new a("cloudpush_isSupportUpdate", Boolean.class, Boolean.valueOf(false)));
        a.put("cloudpush_isSupportCollectSocketInfo", new a("cloudpush_isSupportCollectSocketInfo", Boolean.class, Boolean.valueOf(false)));
        a.put("cloudpush_trsIp", new a("cloudpush_trsIp", String.class, "push.hicloud.com"));
        a.put("cloudpush_fixHeatBeat", new a("cloudpush_fixHeatBeat", String.class, " unit sec"));
        a.put("USE_SSL", new a("USE_SSL", Integer.class, Integer.valueOf(b.a.b.ordinal())));
    }

    private void d() {
        this.c.clear();
        SharedPreferences sharedPreferences = this.d.getSharedPreferences("pushConfig", 4);
        this.c.putAll(a);
        for (Entry entry : sharedPreferences.getAll().entrySet()) {
            this.c.put(entry.getKey(), new a((String) entry.getKey(), entry.getValue().getClass(), entry.getValue()));
        }
    }

    public void a() {
        c();
        d();
    }

    public void b() {
        Editor edit = this.d.getSharedPreferences("pushConfig", 4).edit();
        Set<String> keySet = this.c.keySet();
        LinkedList linkedList = new LinkedList();
        for (String str : keySet) {
            if (!(a.containsKey(str) || "NeedMyServiceRun".equals(str) || "votedPackageName".equals(str) || "version_config".equals(str))) {
                e.a("PushLogAC2705", "item " + str + " remove from " + "pushConfig" + " in deleteNoSysCfg");
                linkedList.add(str);
                edit.remove(str);
            }
        }
        edit.commit();
        Iterator it = linkedList.iterator();
        while (it.hasNext()) {
            this.c.remove((String) it.next());
        }
    }
}
