package com.huluxia.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat$Builder;
import com.huluxia.HTApplication;
import com.huluxia.bbs.b.f;
import com.huluxia.bbs.b.l;
import com.huluxia.data.message.MsgCounts;
import com.huluxia.ui.profile.MessageHistoryActivity;
import com.huluxia.widget.Constants;

/* compiled from: MessageNotification */
public class h {
    private static h aDw = null;
    private NotificationManager aDx;
    private boolean aaA;

    private h() {
        this.aDx = null;
        this.aaA = false;
        this.aDx = (NotificationManager) HTApplication.getAppContext().getSystemService("notification");
    }

    public static h EC() {
        if (aDw == null) {
            aDw = new h();
        }
        return aDw;
    }

    public void a(Intent intent, String title, String body, int icon, String tag) {
        Notification nf = new NotificationCompat$Builder(HTApplication.getAppContext()).setContentIntent(PendingIntent.getActivity(HTApplication.getAppContext(), 0, intent, 268435456)).setSmallIcon(icon).setTicker(body).setWhen(5000).setAutoCancel(true).setContentTitle(title).setContentText(body).build();
        if (tag == null) {
            this.aDx.notify(0, nf);
            return;
        }
        this.aDx.notify(Integer.valueOf(tag).intValue(), nf);
    }

    public void cancelNotification(String tag) {
        if (tag == null) {
            this.aDx.cancel(0);
            return;
        }
        this.aDx.cancel(Integer.valueOf(tag).intValue());
    }

    public void a(CharSequence title, CharSequence msg, MsgCounts msgCounts) {
        if (!this.aaA) {
            this.aaA = true;
            Intent intent = new Intent(HTApplication.getAppContext(), MessageHistoryActivity.class);
            intent.putExtra("msgCounts", msgCounts);
            a(title, msg, intent, 0);
        }
    }

    public void a(CharSequence title, CharSequence msg, MsgCounts msgCounts, boolean isSound, boolean isVibration) {
        if (!this.aaA) {
            this.aaA = true;
            Intent in;
            if (HTApplication.bU()) {
                in = new Intent();
                in.putExtra("msgCounts", msgCounts);
                in.setFlags(268435456);
                in.setClass(HTApplication.getAppContext(), MessageHistoryActivity.class);
                a(title, msg, in, 0, isSound, isVibration);
                return;
            }
            in = new Intent();
            in.putExtra("currentIdx", 0);
            in.putExtra("nextintent", "MessageHistoryActivity");
            in.putExtra("msgCounts", msgCounts);
            in.addFlags(268435456);
            in.setComponent(new ComponentName(HTApplication.getAppContext(), "hlx.home.main.HomeActivity"));
            a(title, msg, in, 0, isSound, isVibration);
        }
    }

    public void a(CharSequence title, CharSequence msg, Intent intent, int tag, boolean isSound, boolean isVibration) {
        Notification nf = new NotificationCompat$Builder(HTApplication.getAppContext()).setContentIntent(PendingIntent.getActivity(HTApplication.getAppContext(), 0, intent, 268435456)).setSmallIcon(HTApplication.fp).setTicker(msg).setWhen(System.currentTimeMillis()).setAutoCancel(true).setContentTitle(title).setContentText(msg).build();
        if (isSound) {
            nf.sound = Uri.parse("android.resource://" + HTApplication.getAppContext().getPackageName() + "/" + l.msg);
        }
        long[] vibrate = new long[]{0, 500, 500, 500};
        if (isVibration) {
            nf.vibrate = vibrate;
        }
        this.aDx.notify(tag, nf);
    }

    public void a(CharSequence title, CharSequence msg, long id, int model, int type, int mcrestype) {
        Intent in = new Intent();
        in.putExtra("nextintent", "TopicDetailActivity");
        in.putExtra("id", id);
        in.putExtra("type", type);
        in.putExtra("mcrestype", mcrestype);
        in.putExtra(Constants.brO, model);
        in.addFlags(268435456);
        in.setComponent(new ComponentName(HTApplication.getAppContext(), "hlx.home.main.HomeActivity"));
        a(title, msg, in, Integer.valueOf(String.valueOf(id)).intValue());
    }

    public void a(CharSequence title, CharSequence msg, long id, long resid, int model, int type, int mcrestype) {
        Intent in = new Intent();
        in.putExtra("nextintent", "ResTopicDetailActivity");
        in.putExtra("id", id);
        in.putExtra("resid", resid);
        in.putExtra("type", type);
        in.putExtra("mcrestype", mcrestype);
        in.putExtra(Constants.brO, model);
        in.addFlags(268435456);
        in.setComponent(new ComponentName(HTApplication.getAppContext(), "hlx.home.main.HomeActivity"));
        a(title, msg, in, Integer.valueOf(String.valueOf(id)).intValue());
    }

    public void a(CharSequence title, CharSequence msg, Intent intent, int tag) {
        Notification nf = new NotificationCompat$Builder(HTApplication.getAppContext()).setContentIntent(PendingIntent.getActivity(HTApplication.getAppContext(), 0, intent, 268435456)).setSmallIcon(f.app_icon).setTicker(msg).setWhen(5000).setAutoCancel(true).setContentTitle(title).setContentText(msg).build();
        if (HTApplication.bI().ef()) {
            nf.sound = Uri.parse("android.resource://" + HTApplication.getAppContext().getPackageName() + "/" + l.msg);
        }
        long[] vibrate = new long[]{0, 500, 500, 500};
        if (HTApplication.bI().isVibration()) {
            nf.vibrate = vibrate;
        }
        this.aDx.notify(tag, nf);
    }

    public void bh(long id) {
        this.aDx.cancel(Integer.valueOf(String.valueOf(id)).intValue());
    }

    public void ED() {
        this.aDx.cancel(0);
        this.aaA = false;
    }
}
