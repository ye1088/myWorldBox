package com.tencent.mm.sdk.platformtools;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import junit.framework.Assert;

public class MAlarmHandler {
    public static final long NEXT_FIRE_INTERVAL = Long.MAX_VALUE;
    private static int ac;
    private static Map<Integer, MAlarmHandler> ah = new HashMap();
    private static IBumper aj;
    private static boolean ak = false;
    private final int ad;
    private final boolean ae;
    private long af = 0;
    private long ag = 0;
    private final CallBack ai;

    public interface CallBack {
        boolean onTimerExpired();
    }

    public interface IBumper {
        void cancel();

        void prepare();
    }

    public MAlarmHandler(CallBack callBack, boolean z) {
        Assert.assertTrue("bumper not initialized", ak);
        this.ai = callBack;
        this.ae = z;
        if (ac >= 8192) {
            ac = 0;
        }
        int i = ac + 1;
        ac = i;
        this.ad = i;
    }

    public static long fire() {
        List linkedList = new LinkedList();
        Set<Integer> hashSet = new HashSet();
        hashSet.addAll(ah.keySet());
        long j = Long.MAX_VALUE;
        for (Integer num : hashSet) {
            long j2;
            MAlarmHandler mAlarmHandler = (MAlarmHandler) ah.get(num);
            if (mAlarmHandler != null) {
                long ticksToNow = Util.ticksToNow(mAlarmHandler.af);
                if (ticksToNow < 0) {
                    ticksToNow = 0;
                }
                if (ticksToNow > mAlarmHandler.ag) {
                    if (mAlarmHandler.ai.onTimerExpired() && mAlarmHandler.ae) {
                        j = mAlarmHandler.ag;
                    } else {
                        linkedList.add(num);
                    }
                    mAlarmHandler.af = Util.currentTicks();
                } else if (mAlarmHandler.ag - ticksToNow < j) {
                    j2 = mAlarmHandler.ag - ticksToNow;
                    j = j2;
                }
            }
            j2 = j;
            j = j2;
        }
        for (int i = 0; i < linkedList.size(); i++) {
            ah.remove(linkedList.get(i));
        }
        if (j == Long.MAX_VALUE && aj != null) {
            aj.cancel();
            Log.v("MicroMsg.MAlarmHandler", "cancel bumper for no more handler");
        }
        return j;
    }

    public static void initAlarmBumper(IBumper iBumper) {
        ak = true;
        aj = iBumper;
    }

    protected void finalize() {
        stopTimer();
        super.finalize();
    }

    public void startTimer(long j) {
        this.ag = j;
        this.af = Util.currentTicks();
        long j2 = this.ag;
        Log.d("MicroMsg.MAlarmHandler", "check need prepare: check=" + j2);
        long j3 = Long.MAX_VALUE;
        for (Entry value : ah.entrySet()) {
            long j4;
            MAlarmHandler mAlarmHandler = (MAlarmHandler) value.getValue();
            if (mAlarmHandler != null) {
                long ticksToNow = Util.ticksToNow(mAlarmHandler.af);
                if (ticksToNow < 0) {
                    ticksToNow = 0;
                }
                if (ticksToNow > mAlarmHandler.ag) {
                    j3 = mAlarmHandler.ag;
                } else if (mAlarmHandler.ag - ticksToNow < j3) {
                    j4 = mAlarmHandler.ag - ticksToNow;
                    j3 = j4;
                }
            }
            j4 = j3;
            j3 = j4;
        }
        Object obj = j3 > j2 ? 1 : null;
        stopTimer();
        ah.put(Integer.valueOf(this.ad), this);
        if (aj != null && obj != null) {
            Log.v("MicroMsg.MAlarmHandler", "prepare bumper");
            aj.prepare();
        }
    }

    public void stopTimer() {
        ah.remove(Integer.valueOf(this.ad));
    }

    public boolean stopped() {
        return !ah.containsKey(Integer.valueOf(this.ad));
    }
}
