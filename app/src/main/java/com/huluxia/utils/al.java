package com.huluxia.utils;

import android.content.Context;
import com.huluxia.framework.base.utils.UtilsNetwork;
import com.huluxia.module.news.c;
import com.huluxia.ui.itemadapter.news.NewsListAdapter;
import com.huluxia.ui.itemadapter.news.NewsSimpleItemAdapter;
import com.huluxia.ui.itemadapter.news.NewsWifiItemAdapter;
import com.huluxia.utils.ah.a;
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
