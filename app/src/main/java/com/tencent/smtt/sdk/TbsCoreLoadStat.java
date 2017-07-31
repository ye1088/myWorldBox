package com.tencent.smtt.sdk;

import android.content.Context;
import com.tencent.smtt.utils.TbsLog;

public class TbsCoreLoadStat {
    private static TbsCoreLoadStat d = null;
    private TbsSequenceQueue a = null;
    private boolean b = false;
    private final int c = 3;

    public class TbsSequenceQueue {
        final /* synthetic */ TbsCoreLoadStat a;

        public TbsSequenceQueue(TbsCoreLoadStat tbsCoreLoadStat) {
            this.a = tbsCoreLoadStat;
        }
    }

    private TbsCoreLoadStat() {
    }

    public static TbsCoreLoadStat getInstance() {
        if (d == null) {
            d = new TbsCoreLoadStat();
        }
        return d;
    }

    void a() {
    }

    void a(Context context, int i) {
        a(context, i, null);
        TbsLog.e(TbsListener.tag_load_error, "" + i);
    }

    void a(Context context, int i, Throwable th) {
    }
}
