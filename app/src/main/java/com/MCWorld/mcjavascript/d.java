package com.MCWorld.mcjavascript;

import java.util.ArrayList;

/* compiled from: DTJSImport */
public class d {
    private static final boolean DEBUG = false;
    private static final String TAG = "mcjavascript/DTJSImport";
    private static boolean ajx = false;
    private static int ajy = 200;
    public static ArrayList<e> ajz = new ArrayList(ajy);

    public static boolean Af() {
        return ajx;
    }

    private static void bV(boolean bImportExJSFlag) {
        ajx = bImportExJSFlag;
    }

    public static void Ag() {
        int i = 0;
        while (i < ajz.size()) {
            i = true == ((e) ajz.get(i)).isValid() ? i + 1 : i + 1;
        }
    }

    public static void b(String path, int in_encode, boolean in_bEnabled) {
        for (int i = 0; i < ajz.size(); i++) {
            e pTmpDTJSItem = (e) ajz.get(i);
            if (true == pTmpDTJSItem.isValid()) {
                int cur_encode = pTmpDTJSItem.Ah();
                if (pTmpDTJSItem.getPath().equals(path) && cur_encode == in_encode) {
                    String pTmpName = pTmpDTJSItem.getName();
                    if (true == in_bEnabled && pTmpDTJSItem.Ai() == 0) {
                        pTmpDTJSItem.hA(1);
                    }
                    pTmpDTJSItem.bW(in_bEnabled);
                    f.d(pTmpName, in_bEnabled);
                    return;
                }
            }
        }
    }

    public static boolean T(int in_tagIdx, int in_encode) {
        for (int i = 0; i < ajz.size(); i++) {
            e pTmpDTJSItem = (e) ajz.get(i);
            if (true == pTmpDTJSItem.isValid()) {
                int cur_tagIdx = pTmpDTJSItem.Aj();
                int cur_encode = pTmpDTJSItem.Ah();
                if (cur_tagIdx == in_tagIdx && cur_encode == in_encode) {
                    return pTmpDTJSItem.Ak();
                }
            }
        }
        return false;
    }

    public static void c(int in_tagIdx, int in_encode, boolean in_bEnabled) {
        for (int i = 0; i < ajz.size(); i++) {
            e pTmpDTJSItem = (e) ajz.get(i);
            if (true == pTmpDTJSItem.isValid()) {
                int cur_tagIdx = pTmpDTJSItem.Aj();
                int cur_encode = pTmpDTJSItem.Ah();
                String pTmpName = pTmpDTJSItem.getPath();
                if (cur_tagIdx == in_tagIdx && cur_encode == in_encode) {
                    if (true == in_bEnabled && pTmpDTJSItem.Ai() == 0) {
                        pTmpDTJSItem.hA(1);
                    }
                    pTmpDTJSItem.bW(in_bEnabled);
                    f.d(pTmpName, in_bEnabled);
                    return;
                }
            }
        }
    }

    public static boolean a(String in_path, int in_encode, int in_enable, int in_tagIdx) {
        int i = 0;
        while (i < ajz.size()) {
            e pTmpDTJSItem = (e) ajz.get(i);
            if (pTmpDTJSItem.isValid()) {
                i++;
            } else {
                if (in_encode == 0) {
                    bV(true);
                }
                pTmpDTJSItem.a(in_path, in_encode, in_enable, true, in_tagIdx);
                return true;
            }
        }
        return false;
    }

    public static void init() {
        int i;
        if (ajz.size() == ajy) {
            for (i = 0; i < ajz.size(); i++) {
                e pTmpDTJSItem = (e) ajz.get(i);
                pTmpDTJSItem.name = null;
                pTmpDTJSItem.path = null;
                pTmpDTJSItem.ajA = 0;
                pTmpDTJSItem.encode = 0;
                pTmpDTJSItem.agK = false;
            }
            return;
        }
        ajz.clear();
        for (i = 0; i < ajy; i++) {
            ajz.add(new e());
        }
    }
}
