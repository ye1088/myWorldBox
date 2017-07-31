package com.huluxia.mcjsmanager;

import com.huluxia.mcgame.aa;
import com.huluxia.mcgame.n;
import com.huluxia.mcjavascript.d;
import com.huluxia.mcjavascript.e;

/* compiled from: DTJSHelper */
public class c {
    static final String[] ajW = new String[]{"100001", "100002", "100003", "100004"};

    public static void init() {
        aa.bG(false);
        n.init();
        d.init();
        for (int i = 0; i < 4; i++) {
            d.a(hF(i), 1, 0, i);
        }
    }

    public static void AC() {
        for (int i = 0; i < d.ajz.size(); i++) {
            e pTmpDTJSItem = (e) d.ajz.get(i);
            if (true == pTmpDTJSItem.isValid() && pTmpDTJSItem.Ai() == 0 && pTmpDTJSItem.Ah() == 0) {
                d.b(pTmpDTJSItem.getPath(), 0, true);
            }
        }
    }

    public static String hF(int in_index) {
        if (in_index >= 4) {
            return null;
        }
        return ajW[in_index];
    }
}
