package com.huluxia.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import com.huluxia.data.map.f;
import com.huluxia.framework.R;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.widget.dialog.CommonShareDialog;
import com.huluxia.framework.base.widget.dialog.CommonShareDialog.CommonShareDialogAdapter.CommonShareDialogListener;
import com.huluxia.framework.base.widget.dialog.CommonShareDialog.CommonShareDialogAdapter.commonShareItem;
import com.huluxia.r;
import com.huluxia.service.j;
import com.huluxia.widget.Constants;
import com.huluxia.widget.dialog.n;
import com.huluxia.widget.dialog.n.a;
import com.huluxia.widget.dialog.o;
import com.simple.colorful.d;
import com.tencent.mm.sdk.platformtools.SpecilApiUtil;

/* compiled from: McUtilsShare */
public class k extends au {
    private static k bkh;

    public static void q(final Activity context) {
        final n dialogMenu = UtilsMenu.bn(context);
        dialogMenu.show();
        dialogMenu.a(new a() {
            public void a(o m) {
                switch ((UtilsMenu$MENU_SHARE_LIST) m.getTag()) {
                    case SHARE_QQZONE:
                        j.m(context).a(0, context.getResources().getString(R.string.mc_ad_title), context.getResources().getString(R.string.mc_ad_desc), Constants.bsA, Constants.bsz);
                        r.ck().dh();
                        break;
                    case SHARE_QQ:
                        j.m(context).e(context.getResources().getString(R.string.mc_ad_title), context.getResources().getString(R.string.mc_ad_desc), Constants.bsA, Constants.bsz);
                        r.ck().dh();
                        break;
                    case SHARE_WEIXIN:
                        k.s(context);
                        r.ck().dh();
                        break;
                }
                dialogMenu.dismiss();
            }
        });
    }

    public static synchronized k KJ() {
        k kVar;
        synchronized (k.class) {
            if (bkh == null) {
                bkh = new k();
            }
            kVar = bkh;
        }
        return kVar;
    }

    public void r(Activity activity) {
        this.aTg = activity;
        CommonShareDialogListener mCommonShareDialogListener = new CommonShareDialogListener(this) {
            final /* synthetic */ k bkj;

            {
                this.bkj = this$0;
            }

            public void pressMenuById(int inIndex, Object object, Object param) {
                f.a info = (f.a) object;
                String src = (String) param;
                String flag;
                switch (inIndex) {
                    case 1:
                        flag = src + "_wxfriend";
                        k.a(this.bkj.aTg, info);
                        r.ck().P(flag);
                        this.bkj.bmW.dismissDialog();
                        return;
                    case 2:
                        flag = src + "_qqzone";
                        j.m(this.bkj.aTg).a(0, info.name, aw.W(info.mapDesc, 60), info.icon, info.shareUrl);
                        r.ck().P(flag);
                        this.bkj.bmW.dismissDialog();
                        return;
                    case 3:
                        flag = src + "_qqfriend";
                        j.m(this.bkj.aTg).e(info.name, aw.W(info.mapDesc, 60), info.icon, info.shareUrl);
                        r.ck().P(flag);
                        this.bkj.bmW.dismissDialog();
                        return;
                    default:
                        return;
                }
            }
        };
        this.bmX.clear();
        this.bmX.add(new commonShareItem(this.aTg.getString(R.string.qq_friend), 3, d.o(this.aTg, R.attr.share_to_qq_friend), 0));
        this.bmX.add(new commonShareItem(this.aTg.getString(R.string.qq_space), 2, d.o(this.aTg, R.attr.share_to_qq_space), 0));
        this.bmX.add(new commonShareItem(this.aTg.getString(R.string.wx_friend), 1, d.o(this.aTg, R.attr.share_to_wx_friend), 0));
        this.bmW = new CommonShareDialog(this.aTg, this.bmX, mCommonShareDialogListener, d.RB(), 3);
    }

    public void b(Activity context, f.a info, String src) {
        r(context);
        this.bmW.showMenu(info, src);
    }

    public static void a(final Activity context, final com.huluxia.data.server.a.a info) {
        final n dialogMenu = UtilsMenu.bn(context);
        dialogMenu.show();
        dialogMenu.a(new a() {
            public void a(o m) {
                switch ((UtilsMenu$MENU_SHARE_LIST) m.getTag()) {
                    case SHARE_QQZONE:
                        j.m(context).a(0, info.name, aw.W(info.mapDesc, 60), info.icon, info.shareUrl);
                        r.ck().dh();
                        break;
                    case SHARE_QQ:
                        j.m(context).e(info.name, aw.W(info.mapDesc, 60), info.icon, info.shareUrl);
                        r.ck().dh();
                        break;
                    case SHARE_WEIXIN:
                        k.b(context, info);
                        r.ck().dh();
                        break;
                }
                dialogMenu.dismiss();
            }
        });
    }

    private static void s(Activity context) {
        String title = "《葫芦侠我的世界》";
        String desc = context.getResources().getString(R.string.mc_ad_desc);
        String url = Constants.bsz;
        Intent intent = new Intent("android.intent.action.SEND", null);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("text/plain");
        for (ResolveInfo info : context.getPackageManager().queryIntentActivities(intent, 0)) {
            if (info.activityInfo.packageName.equalsIgnoreCase("com.tencent.mm")) {
                HLog.verbose("UtilsShare", "packname %s", new Object[]{info.activityInfo.packageName});
                Intent shareIntent = new Intent("android.intent.action.SEND");
                shareIntent.setComponent(new ComponentName(info.activityInfo.packageName, info.activityInfo.name));
                shareIntent.setType("text/plain");
                shareIntent.putExtra("android.intent.extra.TEXT", title + SpecilApiUtil.LINE_SEP + desc + SpecilApiUtil.LINE_SEP + url);
                shareIntent.setFlags(268435456);
                context.startActivity(shareIntent);
                return;
            }
        }
    }

    private static void a(Activity context, f.a info) {
        String title = "《葫芦侠我的世界》";
        String desc = context.getResources().getString(R.string.mc_ad_desc);
        String url = Constants.bsz;
        Intent intent = new Intent("android.intent.action.SEND", null);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("text/plain");
        for (ResolveInfo app : context.getPackageManager().queryIntentActivities(intent, 0)) {
            if (app.activityInfo.packageName.equalsIgnoreCase("com.tencent.mm")) {
                HLog.verbose("UtilsShare", "packname %s", new Object[]{app.activityInfo.packageName});
                Intent shareIntent = new Intent("android.intent.action.SEND");
                shareIntent.setComponent(new ComponentName(app.activityInfo.packageName, app.activityInfo.name));
                shareIntent.setType("text/plain");
                shareIntent.putExtra("android.intent.extra.TEXT", info.name + SpecilApiUtil.LINE_SEP + aw.W(info.mapDesc, 60) + SpecilApiUtil.LINE_SEP + info.shareUrl);
                shareIntent.setFlags(268435456);
                context.startActivity(shareIntent);
                return;
            }
        }
    }

    private static void b(Activity context, com.huluxia.data.server.a.a info) {
        String title = "《葫芦侠我的世界》";
        String desc = context.getResources().getString(R.string.mc_ad_desc);
        String url = Constants.bsz;
        Intent intent = new Intent("android.intent.action.SEND", null);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("text/plain");
        for (ResolveInfo app : context.getPackageManager().queryIntentActivities(intent, 0)) {
            if (app.activityInfo.packageName.equalsIgnoreCase("com.tencent.mm")) {
                HLog.verbose("UtilsShare", "packname %s", new Object[]{app.activityInfo.packageName});
                Intent shareIntent = new Intent("android.intent.action.SEND");
                shareIntent.setComponent(new ComponentName(app.activityInfo.packageName, app.activityInfo.name));
                shareIntent.setType("text/plain");
                shareIntent.putExtra("android.intent.extra.TEXT", info.name + SpecilApiUtil.LINE_SEP + aw.W(info.mapDesc, 60) + SpecilApiUtil.LINE_SEP + info.shareUrl);
                shareIntent.setFlags(268435456);
                context.startActivity(shareIntent);
                return;
            }
        }
    }

    public static void a(Activity context, String title, String desc, String icon, String tipUrl, String TongjiSrc) {
        final n dialogMenu = UtilsMenu.bn(context);
        dialogMenu.show();
        final String str = TongjiSrc;
        final Activity activity = context;
        final String str2 = title;
        final String str3 = desc;
        final String str4 = icon;
        final String str5 = tipUrl;
        dialogMenu.a(new a() {
            public void a(o m) {
                String _tongjiFlag;
                switch ((UtilsMenu$MENU_SHARE_LIST) m.getTag()) {
                    case SHARE_QQZONE:
                        _tongjiFlag = str + "_qqzone";
                        j.m(activity).a(0, str2, str3, str4, str5);
                        r.ck().K(_tongjiFlag);
                        break;
                    case SHARE_QQ:
                        _tongjiFlag = str + "_qqfriend";
                        j.m(activity).e(str2, str3, str4, str5);
                        r.ck().K(_tongjiFlag);
                        break;
                    case SHARE_WEIXIN:
                        _tongjiFlag = str + "_wxfriend";
                        k.a(activity, str2, str3, str5);
                        r.ck().K(_tongjiFlag);
                        break;
                }
                dialogMenu.dismiss();
            }
        });
    }

    private static void a(Activity context, String name, String desc, String shareUrl) {
        Intent intent = new Intent("android.intent.action.SEND", null);
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setType("text/plain");
        for (ResolveInfo app : context.getPackageManager().queryIntentActivities(intent, 0)) {
            if (app.activityInfo.packageName.equalsIgnoreCase("com.tencent.mm")) {
                Intent shareIntent = new Intent("android.intent.action.SEND");
                shareIntent.setComponent(new ComponentName(app.activityInfo.packageName, app.activityInfo.name));
                shareIntent.setType("text/plain");
                shareIntent.putExtra("android.intent.extra.TEXT", name + SpecilApiUtil.LINE_SEP + aw.W(desc, 60) + SpecilApiUtil.LINE_SEP + shareUrl);
                shareIntent.setFlags(268435456);
                context.startActivity(shareIntent);
                return;
            }
        }
    }
}
