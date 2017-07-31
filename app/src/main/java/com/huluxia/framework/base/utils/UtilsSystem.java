package com.huluxia.framework.base.utils;

import android.content.Context;
import android.content.Intent;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class UtilsSystem {
    public static boolean isIntentAvailable(Context context, Intent intent) {
        return context.getPackageManager().queryIntentActivities(intent, 65536).size() > 0;
    }

    public static int getNowHour() {
        return new GregorianCalendar().get(11);
    }

    public static String getHourStr() {
        Calendar calendar = new GregorianCalendar();
        return String.valueOf(calendar.get(1)) + "/" + String.valueOf(calendar.get(2)) + "/" + String.valueOf(calendar.get(5)) + "/" + String.valueOf(calendar.get(11));
    }
}
