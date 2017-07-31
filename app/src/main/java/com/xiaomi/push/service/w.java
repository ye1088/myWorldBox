package com.xiaomi.push.service;

import android.util.Pair;
import com.xiaomi.channel.commonutils.misc.b;
import com.xiaomi.xmpush.thrift.c;
import com.xiaomi.xmpush.thrift.d;
import com.xiaomi.xmpush.thrift.e;
import com.xiaomi.xmpush.thrift.g;
import com.xiaomi.xmpush.thrift.p;
import com.xiaomi.xmpush.thrift.q;
import java.util.ArrayList;
import java.util.List;

public class w {
    public static int a(v vVar, c cVar) {
        int i = 0;
        String a = a(cVar);
        switch (cVar) {
            case MISC_CONFIG:
                i = 1;
                break;
        }
        return vVar.a.getInt(a, i);
    }

    private static String a(c cVar) {
        return "oc_version_" + cVar.a();
    }

    private static List<Pair<Integer, Object>> a(List<g> list, boolean z) {
        if (b.a(list)) {
            return null;
        }
        List<Pair<Integer, Object>> arrayList = new ArrayList();
        for (g gVar : list) {
            int a = gVar.a();
            d a2 = d.a(gVar.c());
            if (a2 != null) {
                if (z && gVar.c) {
                    arrayList.add(new Pair(Integer.valueOf(a), null));
                } else {
                    Object obj;
                    Pair pair;
                    switch (a2) {
                        case INT:
                            pair = new Pair(Integer.valueOf(a), Integer.valueOf(gVar.f()));
                            break;
                        case LONG:
                            pair = new Pair(Integer.valueOf(a), Long.valueOf(gVar.h()));
                            break;
                        case STRING:
                            pair = new Pair(Integer.valueOf(a), gVar.j());
                            break;
                        case BOOLEAN:
                            pair = new Pair(Integer.valueOf(a), Boolean.valueOf(gVar.l()));
                            break;
                        default:
                            obj = null;
                            break;
                    }
                    arrayList.add(obj);
                }
            }
        }
        return arrayList;
    }

    public static void a(v vVar, c cVar, int i) {
        vVar.a.edit().putInt(a(cVar), i).commit();
    }

    public static void a(v vVar, p pVar) {
        vVar.b(a(pVar.a(), true));
    }

    public static void a(v vVar, q qVar) {
        for (e eVar : qVar.a()) {
            if (eVar.a() > a(vVar, eVar.d())) {
                a(vVar, eVar.d(), eVar.a());
                vVar.a(a(eVar.b, false));
            }
        }
    }
}
