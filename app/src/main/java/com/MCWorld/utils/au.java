package com.MCWorld.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.preference.PreferenceManager;
import com.MCWorld.HTApplication;
import com.MCWorld.bbs.b.c;
import com.MCWorld.bbs.b.l;
import com.MCWorld.bbs.b.m;
import com.MCWorld.data.topic.TopicItem;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.base.utils.UtilsImage;
import com.MCWorld.framework.base.widget.dialog.CommonShareDialog;
import com.MCWorld.framework.base.widget.dialog.CommonShareDialog.CommonShareDialogAdapter.CommonShareDialogListener;
import com.MCWorld.framework.base.widget.dialog.CommonShareDialog.CommonShareDialogAdapter.commonShareItem;
import com.MCWorld.http.base.e;
import com.MCWorld.module.GameInfo;
import com.MCWorld.module.s;
import com.MCWorld.widget.dialog.n;
import com.simple.colorful.d;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;

/* compiled from: UtilsShare */
public class au {
    public static final int bmR = 0;
    public static final int bmS = 1;
    public static final int bmT = 2;
    public static final int bmU = 3;
    public static final int bmV = 3;
    private static au bmY;
    public Activity aTg;
    public CommonShareDialog bmW;
    public ArrayList<Object> bmX = new ArrayList();

    public static synchronized au MD() {
        au auVar;
        synchronized (au.class) {
            if (bmY == null) {
                bmY = new au();
            }
            auVar = bmY;
        }
        return auVar;
    }

    public void r(Activity activity) {
        this.aTg = activity;
        CommonShareDialogListener mCommonShareDialogListener = new 1(this);
        this.bmX.clear();
        this.bmX.add(new commonShareItem(this.aTg.getString(m.qq_friend), 3, d.o(this.aTg, c.share_to_qq_friend), 0));
        this.bmX.add(new commonShareItem(this.aTg.getString(m.qq_space), 2, d.o(this.aTg, c.share_to_qq_space), 0));
        this.bmX.add(new commonShareItem(this.aTg.getString(m.wx_friend), 1, d.o(this.aTg, c.share_to_wx_friend), 0));
        this.bmW = new CommonShareDialog(this.aTg, this.bmX, mCommonShareDialogListener, d.RB(), 3);
    }

    public void a(Activity context, TopicItem topicItem, long factor, String para_in) {
        String para = null;
        if (para_in != null) {
            try {
                para = URLEncoder.encode(para_in, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                HLog.error("UtilsShare", "UnsupportedEncodingException  " + e.getMessage(), new Object[0]);
            } catch (Exception e2) {
                HLog.error("UtilsShare", "Exception  " + e2.getMessage(), new Object[0]);
            }
        }
        String shareurl = String.format(Locale.getDefault(), HTApplication.bV(), new Object[]{Long.valueOf(factor)}) + "?para=" + para;
        r(context);
        this.bmW.showMenu(topicItem, shareurl);
    }

    public static void a(Activity context, GameInfo data, boolean setApp, boolean tipDownload, boolean onlyPublic) {
        if (data != null) {
            n dialogMenu = UtilsMenu.c((Context) context, onlyPublic);
            dialogMenu.show();
            dialogMenu.a(new 2(setApp, context, data, dialogMenu));
        }
    }

    private static Bitmap N(Context context, String urlString) {
        if (urlString != null) {
            urlString = String.format("%s_160x160.jpeg", new Object[]{urlString});
            Bitmap bitmap = f.getBitmap(urlString);
            if (bitmap != null) {
                return bitmap;
            }
            byte[] data = e.a(urlString, null);
            if (data != null) {
                bitmap = UtilsImage.decodeByteArray(data);
                if (bitmap != null) {
                    f.b(urlString, bitmap);
                    return bitmap;
                }
            }
        }
        return new BitmapDrawable(context.getResources(), context.getResources().openRawResource(l.floor_app_icon)).getBitmap();
    }

    public static boolean gl(String appId) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(HTApplication.getAppContext());
        if (sp.getInt(s.SHARE + appId, 0) <= 2 && sp.getInt("sharesuccess" + appId, 0) == 0) {
            return false;
        }
        return true;
    }

    public static void e(long appId, boolean success) {
        if (appId != 0) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(HTApplication.getAppContext());
            String shareFlag;
            Editor editor;
            if (success) {
                shareFlag = "sharesuccess" + appId;
                editor = sp.edit();
                editor.putInt(shareFlag, 1);
                editor.commit();
                return;
            }
            shareFlag = s.SHARE + appId;
            int val = sp.getInt(shareFlag, 0);
            editor = sp.edit();
            editor.putInt(shareFlag, val + 1);
            editor.commit();
        }
    }

    public static void a(Activity context, long appId, String desc, String logo, String title, String shareUrl, boolean setApp, boolean tipDownload, boolean onlyPublic) {
        if (!UtilsFunction.empty((CharSequence) desc) && !UtilsFunction.empty((CharSequence) logo) && !UtilsFunction.empty((CharSequence) title) && !UtilsFunction.empty((CharSequence) shareUrl)) {
            n dialogMenu = UtilsMenu.c((Context) context, onlyPublic);
            dialogMenu.show();
            dialogMenu.a(new 3(setApp, context, appId, logo, title, desc, shareUrl, dialogMenu));
        }
    }
}
