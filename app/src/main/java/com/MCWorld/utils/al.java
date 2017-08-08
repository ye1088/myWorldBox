package com.MCWorld.utils;

import android.content.Context;
import com.MCWorld.framework.base.utils.UtilsNetwork;
import com.MCWorld.module.news.c;
import com.MCWorld.ui.itemadapter.news.NewsListAdapter;
import com.MCWorld.ui.itemadapter.news.NewsSimpleItemAdapter;
import com.MCWorld.ui.itemadapter.news.NewsWifiItemAdapter;
import com.MCWorld.utils.ah.a;
import java.util.ArrayList;

/* compiled from: UtilsNews */
public class al {
    public static NewsListAdapter b(Context context, ArrayList<c> data) {
        if (br(context)) {
            return new NewsWifiItemAdapter(context, data);
        }
        return new NewsSimpleItemAdapter(context, data);
    }

    private static boolean br(Context ctx) {
        int mod = ah.KZ().Lm();
        if (mod == a.bmd) {
            return false;
        }
        if (mod == a.ALL) {
            return true;
        }
        if (UtilsNetwork.isWifiConnected(ctx)) {
            return true;
        }
        return false;
    }
}
