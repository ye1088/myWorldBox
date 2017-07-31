package com.tencent.mm.sdk.platformtools;

import android.os.SystemClock;
import java.util.ArrayList;

public class TimeLogger {
    private String bm;
    private String bn;
    private boolean bo;
    ArrayList<Long> bp;
    ArrayList<String> bq;

    public TimeLogger(String str, String str2) {
        reset(str, str2);
    }

    public void addSplit(String str) {
        if (!this.bo) {
            this.bp.add(Long.valueOf(SystemClock.elapsedRealtime()));
            this.bq.add(str);
        }
    }

    public void dumpToLog() {
        if (!this.bo) {
            Log.d(this.bm, this.bn + ": begin");
            long longValue = ((Long) this.bp.get(0)).longValue();
            int i = 1;
            long j = longValue;
            while (i < this.bp.size()) {
                long longValue2 = ((Long) this.bp.get(i)).longValue();
                Log.d(this.bm, this.bn + ":      " + (longValue2 - ((Long) this.bp.get(i - 1)).longValue()) + " ms, " + ((String) this.bq.get(i)));
                i++;
                j = longValue2;
            }
            Log.d(this.bm, this.bn + ": end, " + (j - longValue) + " ms");
        }
    }

    public void reset() {
        this.bo = false;
        if (!this.bo) {
            if (this.bp == null) {
                this.bp = new ArrayList();
                this.bq = new ArrayList();
            } else {
                this.bp.clear();
                this.bq.clear();
            }
            addSplit(null);
        }
    }

    public void reset(String str, String str2) {
        this.bm = str;
        this.bn = str2;
        reset();
    }
}
