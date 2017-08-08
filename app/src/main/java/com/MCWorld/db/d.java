package com.MCWorld.db;

import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.module.p;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ProfileMemCache */
public class d {
    private static final String TAG = "ProfileMemCache";
    private List<p> memcache = new ArrayList();

    /* compiled from: ProfileMemCache */
    private static class a {
        private static final d rF = new d();

        private a() {
        }
    }

    public static d eK() {
        return a.rF;
    }

    public p q(long uid) {
        p val = null;
        boolean hit = false;
        boolean clear = false;
        synchronized (this.memcache) {
            for (p record : this.memcache) {
                if (uid == record.uid) {
                    val = record;
                    hit = true;
                    break;
                }
            }
            if (this.memcache.size() > 10) {
                for (int i = 0; i < this.memcache.size(); i++) {
                    if (((p) this.memcache.get(i)).uid != uid) {
                        this.memcache.remove(i);
                    }
                }
                clear = true;
            }
        }
        String str = TAG;
        String str2 = "ProfileDbInfo %d in mem hit %s, clear %s";
        Object[] objArr = new Object[3];
        objArr[0] = Long.valueOf(uid);
        objArr[1] = hit ? "true" : "false";
        objArr[2] = clear ? "true" : "false";
        HLog.verbose(str, str2, objArr);
        return val;
    }

    public void a(p info) {
        int index = this.memcache.indexOf(info);
        if (index < 0) {
            this.memcache.add(info);
        } else {
            p memRecord = (p) this.memcache.get(index);
            memRecord.uid = info.uid;
            memRecord.json = info.json;
        }
        c.eJ().a(info, null);
    }
}
