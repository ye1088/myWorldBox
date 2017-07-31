package com.huluxia.controller.resource.handler.segments;

import android.support.annotation.z;
import com.huluxia.framework.base.log.HLog;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* compiled from: SegmentsTableCache */
public class i {
    private static final String TAG = "SegmentsTableCache";
    public static final int oA = 0;
    public static final int oB = 1;
    public static final int oC = 2;
    private static Map<String, f> oz = new ConcurrentHashMap();

    /* compiled from: SegmentsTableCache */
    private static class a {
        public static i oD = new i();

        private a() {
        }
    }

    public static i dW() {
        return a.oD;
    }

    @z
    public f u(String dir, int mode) {
        if (mode == 0) {
            if (((f) oz.get(dir)) == null) {
                return null;
            }
            return ((f) oz.get(dir)).dU();
        } else if (mode == 1) {
            table = h.ap(dir);
            if (table == null) {
                return null;
            }
            oz.put(dir, table);
            return table.dU();
        } else if (mode == 2) {
            table = (f) oz.get(dir);
            if (table != null) {
                return table.dU();
            }
            table = h.ap(dir);
            if (table == null) {
                return null;
            }
            oz.put(dir, table);
            return table.dU();
        } else {
            HLog.error(TAG, "mode is no invalid", new Object[0]);
            return null;
        }
    }

    public boolean a(String dir, f table) {
        if (h.a(dir, table)) {
            oz.put(dir, table);
            return true;
        }
        HLog.error(TAG, "table cache write file failed, dir " + dir + ", table " + table, new Object[0]);
        return false;
    }

    public boolean remove(String dir) {
        if (!h.ao(dir)) {
            return false;
        }
        oz.remove(dir);
        return true;
    }
}
