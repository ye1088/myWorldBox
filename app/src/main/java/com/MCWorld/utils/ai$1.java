package com.MCWorld.utils;

import android.content.Context;
import com.MCWorld.k;
import java.util.ArrayList;

/* compiled from: UtilsMapHelper */
class ai$1 implements Runnable {
    final /* synthetic */ ArrayList bmk;
    final /* synthetic */ String bml;
    final /* synthetic */ ai$a bmm;
    final /* synthetic */ boolean bmn;
    final /* synthetic */ Context val$context;

    ai$1(ArrayList arrayList, String str, ai$a com_huluxia_utils_ai_a, boolean z, Context context) {
        this.bmk = arrayList;
        this.bml = str;
        this.bmm = com_huluxia_utils_ai_a;
        this.bmn = z;
        this.val$context = context;
    }

    public void run() {
        ArrayList<String> zipNameList = this.bmk;
        String destPath = this.bml;
        int i = 0;
        while (i < zipNameList.size()) {
            if (this.bmm == null || !this.bmm.Me()) {
                String dstName = (String) zipNameList.get(i);
                try {
                    p.b(UtilsFile.Kq() + dstName, destPath, dstName + ".zip", true);
                    if (this.bmn) {
                        k.show_toast(this.val$context, "导出" + dstName + "成功");
                    }
                } catch (Exception e) {
                }
                i++;
            } else {
                return;
            }
        }
    }
}
