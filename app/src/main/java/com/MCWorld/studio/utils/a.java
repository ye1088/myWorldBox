package com.MCWorld.studio.utils;

import android.content.Context;
import com.MCWorld.framework.R;
import com.simple.colorful.d;
import java.util.HashMap;
import java.util.Map;

/* compiled from: PastBgUtils */
public class a {
    private static final Map<Integer, Integer> aGQ = new HashMap();
    private static final Map<Integer, Integer> aGR = new HashMap();
    static int[] aGS = new int[]{R.attr.studio_past_rank_january, R.attr.studio_past_rank_february, R.attr.studio_past_rank_march, R.attr.studio_past_rank_april, R.attr.studio_past_rank_may, R.attr.studio_past_rank_june, R.attr.studio_past_rank_july, R.attr.studio_past_rank_august, R.attr.studio_past_rank_september, R.attr.studio_past_rank_october, R.attr.studio_past_rank_november, R.attr.studio_past_rank_december};
    static int[] aGT = new int[]{R.attr.ic_past_rank_january, R.attr.ic_past_rank_february, R.attr.ic_past_rank_march, R.attr.ic_past_rank_april, R.attr.ic_past_rank_may, R.attr.ic_past_rank_june, R.attr.ic_past_rank_july, R.attr.ic_past_rank_august, R.attr.ic_past_rank_september, R.attr.ic_past_rank_october, R.attr.ic_past_rank_november, R.attr.ic_past_rank_december};

    static {
        aGQ.put(Integer.valueOf(1), Integer.valueOf(aGS[0]));
        aGQ.put(Integer.valueOf(2), Integer.valueOf(aGS[1]));
        aGQ.put(Integer.valueOf(3), Integer.valueOf(aGS[2]));
        aGQ.put(Integer.valueOf(4), Integer.valueOf(aGS[3]));
        aGQ.put(Integer.valueOf(5), Integer.valueOf(aGS[4]));
        aGQ.put(Integer.valueOf(6), Integer.valueOf(aGS[5]));
        aGQ.put(Integer.valueOf(7), Integer.valueOf(aGS[6]));
        aGQ.put(Integer.valueOf(8), Integer.valueOf(aGS[7]));
        aGQ.put(Integer.valueOf(9), Integer.valueOf(aGS[8]));
        aGQ.put(Integer.valueOf(10), Integer.valueOf(aGS[9]));
        aGQ.put(Integer.valueOf(11), Integer.valueOf(aGS[10]));
        aGQ.put(Integer.valueOf(12), Integer.valueOf(aGS[11]));
        aGR.put(Integer.valueOf(1), Integer.valueOf(aGT[0]));
        aGR.put(Integer.valueOf(2), Integer.valueOf(aGT[1]));
        aGR.put(Integer.valueOf(3), Integer.valueOf(aGT[2]));
        aGR.put(Integer.valueOf(4), Integer.valueOf(aGT[3]));
        aGR.put(Integer.valueOf(5), Integer.valueOf(aGT[4]));
        aGR.put(Integer.valueOf(6), Integer.valueOf(aGT[5]));
        aGR.put(Integer.valueOf(7), Integer.valueOf(aGT[6]));
        aGR.put(Integer.valueOf(8), Integer.valueOf(aGT[7]));
        aGR.put(Integer.valueOf(9), Integer.valueOf(aGT[8]));
        aGR.put(Integer.valueOf(10), Integer.valueOf(aGT[9]));
        aGR.put(Integer.valueOf(11), Integer.valueOf(aGT[10]));
        aGR.put(Integer.valueOf(12), Integer.valueOf(aGT[11]));
    }

    public static int i(Context context, int month) {
        if (month <= 0 || month >= 13) {
            return 0;
        }
        return d.getColor(context, ((Integer) aGQ.get(Integer.valueOf(month))).intValue());
    }

    public static int j(Context context, int month) {
        if (month <= 0 || month >= 13) {
            return 0;
        }
        return d.r(context, ((Integer) aGR.get(Integer.valueOf(month))).intValue());
    }
}
