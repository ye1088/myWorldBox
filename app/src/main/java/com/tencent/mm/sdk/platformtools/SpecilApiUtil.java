package com.tencent.mm.sdk.platformtools;

import android.os.Build;
import android.os.Build.VERSION;

public class SpecilApiUtil {
    public static final String LINE_SEP = "\n";
    public static final String LINE_SEP_W = "\r\n";
    public static final String LINE_TRIM = "                                                                                                                                                                                                                                                                                                                        ";
    public static final String TAG = "MicroMsg.SpecilApiUtil";

    public static CharSequence fixInAPI16(CharSequence charSequence) {
        return (charSequence != null && VERSION.SDK_INT == 16 && charSequence.toString().contains(LINE_SEP) && Util.nullAs(Build.MANUFACTURER, "").toLowerCase().indexOf("meizu".toLowerCase()) <= 0) ? charSequence.toString().replace(LINE_SEP, LINE_TRIM) : charSequence;
    }

    public static String killsplitAPI16(String str) {
        return (str != null && VERSION.SDK_INT == 16 && str.toString().contains(LINE_SEP) && Util.nullAs(Build.MANUFACTURER, "").toLowerCase().indexOf("meizu".toLowerCase()) <= 0) ? str.toString().replace(LINE_SEP, " ") : str;
    }
}
