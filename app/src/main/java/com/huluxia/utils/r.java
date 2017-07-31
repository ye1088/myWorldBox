package com.huluxia.utils;

import android.app.Activity;
import com.huluxia.bbs.b.m;
import com.huluxia.framework.base.utils.UtilsCamera;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.ResMenuItem;
import com.simple.colorful.d;
import java.util.ArrayList;

/* compiled from: UtilsCameraHT */
public class r extends UtilsCamera {
    private static CommonMenuDialog bgQ;

    public static void showPhotoMenu(Activity activity) {
        ArrayList<Object> list = new ArrayList();
        list.add(new ResMenuItem(activity.getString(m.take_photo_now), 0, 0));
        list.add(new ResMenuItem(activity.getString(m.select_photo), 1, 0));
        bgQ = new CommonMenuDialog(activity, list, new 1(activity), d.RB(), 1);
        bgQ.showMenu(null, activity.getString(m.modify_avatar));
    }
}
