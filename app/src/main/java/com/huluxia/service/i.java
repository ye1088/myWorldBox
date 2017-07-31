package com.huluxia.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import com.huluxia.HTApplication;
import com.huluxia.mojang.converter.ItemStack;
import com.huluxia.mojang.entity.Entity;
import com.huluxia.mojang.entity.EntityItem;
import com.huluxia.r;
import org.apache.tools.ant.taskdefs.XSLTLiaison;

/* compiled from: PushMessageClient */
public class i {
    public static final String aDy = "add";
    public static final String aDz = "sub";

    private static Context getContext() {
        return HTApplication.getAppContext();
    }

    public static void b(BroadcastReceiver receiver) {
        Context context = getContext();
        if (context != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(context.getPackageName() + ".action.broadcast.countmsg");
            context.registerReceiver(receiver, intentFilter);
        }
    }

    public static void EE() {
        Context context = getContext();
        if (context != null) {
            Intent intent = new Intent();
            intent.setAction(context.getPackageName() + ".action.broadcast.countmsg");
            context.sendBroadcast(intent);
        }
    }

    public static void c(BroadcastReceiver receiver) {
        Context context = getContext();
        if (context != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(context.getPackageName() + ".action.broadcast.login");
            context.registerReceiver(receiver, intentFilter);
        }
    }

    public static void EF() {
        Context context = getContext();
        if (context != null) {
            Intent intent = new Intent();
            intent.setAction(context.getPackageName() + ".action.broadcast.login");
            context.sendBroadcast(intent);
        }
    }

    public static void d(BroadcastReceiver receiver) {
        Context context = getContext();
        if (context != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(context.getPackageName() + ".action.broadcast.logout");
            context.registerReceiver(receiver, intentFilter);
        }
    }

    public static void EG() {
        Context context = getContext();
        if (context != null) {
            Intent intent = new Intent();
            intent.setAction(context.getPackageName() + ".action.broadcast.logout");
            context.sendBroadcast(intent);
        }
    }

    public static void e(BroadcastReceiver receiver) {
        Context context = getContext();
        if (context != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(context.getPackageName() + ".action.broadcast.msgtip");
            context.registerReceiver(receiver, intentFilter);
        }
    }

    public static void EH() {
        Context context = getContext();
        if (context != null) {
            Intent intent = new Intent();
            intent.setAction(context.getPackageName() + ".action.broadcast.msgtip");
            context.sendBroadcast(intent);
        }
    }

    public static void f(BroadcastReceiver receiver) {
        Context context = getContext();
        if (context != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(context.getPackageName() + ".action.broadcast.cleartip");
            context.registerReceiver(receiver, intentFilter);
        }
    }

    public static void EI() {
        Context context = getContext();
        if (context != null) {
            Intent intent = new Intent();
            intent.setAction(context.getPackageName() + ".action.broadcast.cleartip");
            context.sendBroadcast(intent);
        }
    }

    public static void g(BroadcastReceiver receiver) {
        Context context = getContext();
        if (context != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(context.getPackageName() + ".action.broadcast.kickuser");
            context.registerReceiver(receiver, intentFilter);
        }
    }

    public static void i(long id, String content) {
        Context context = getContext();
        if (context != null) {
            Intent intent = new Intent();
            intent.setAction(context.getPackageName() + ".action.broadcast.kickuser");
            intent.putExtra("id", id);
            intent.putExtra("content", content);
            context.sendBroadcast(intent);
        }
    }

    public static void h(BroadcastReceiver receiver) {
        Context context = getContext();
        if (context != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(context.getPackageName() + ".action.broadcast.download");
            context.registerReceiver(receiver, intentFilter);
        }
    }

    public static void i(BroadcastReceiver receiver) {
        Context context = getContext();
        if (context != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(context.getPackageName() + ".action.broadcast.zipret");
            context.registerReceiver(receiver, intentFilter);
        }
    }

    public static void e(String taskId, int success, int flag) {
        Context context = getContext();
        if (context != null) {
            Intent intent = new Intent();
            intent.setAction(context.getPackageName() + ".action.broadcast.zipret");
            intent.putExtra("taskid", taskId);
            intent.putExtra("success", success);
            intent.putExtra("flag", flag);
            context.sendBroadcast(intent);
        }
    }

    public static void j(BroadcastReceiver receiver) {
        Context context = getContext();
        if (context != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(context.getPackageName() + ".action.broadcast.profile");
            context.registerReceiver(receiver, intentFilter);
        }
    }

    public static void EJ() {
        Context context = getContext();
        if (context != null) {
            Intent intent = new Intent();
            intent.setAction(context.getPackageName() + ".action.broadcast.profile");
            context.sendBroadcast(intent);
        }
    }

    public static void g(Context context, String packName, String ops) {
        if (context != null && context.getApplicationContext() != null) {
            Intent intent = new Intent();
            intent.setAction(context.getPackageName() + ".action.broadcast.packaddsub");
            intent.putExtra("packName", packName);
            intent.putExtra("ops", ops);
            context.getApplicationContext().sendBroadcast(intent);
        }
    }

    public static void eg(String path) {
        Context context = getContext();
        if (context != null) {
            context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.parse(XSLTLiaison.FILE_PROTOCOL_PREFIX + path)));
        }
    }

    public static void k(BroadcastReceiver receiver) {
        Context context = getContext();
        if (context != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(context.getPackageName() + ".action.broadcast.refresh");
            context.registerReceiver(receiver, intentFilter);
        }
    }

    public static void h(long count, long cate) {
        Context context = getContext();
        if (context != null) {
            Intent intent = new Intent();
            intent.putExtra("count", count);
            intent.putExtra(r.gN, cate);
            intent.setAction(context.getPackageName() + ".action.broadcast.classtip");
            context.sendBroadcast(intent);
        }
    }

    public static void l(BroadcastReceiver receiver) {
        Context context = getContext();
        if (context != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(context.getPackageName() + ".action.broadcast.clearclasstip");
            context.registerReceiver(receiver, intentFilter);
        }
    }

    public static void EK() {
        Context context = getContext();
        if (context != null) {
            Intent intent = new Intent();
            intent.setAction(context.getPackageName() + ".action.broadcast.clearclasstip");
            context.sendBroadcast(intent);
        }
    }

    public static void m(BroadcastReceiver receiver) {
        Context context = getContext();
        if (context != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(context.getPackageName() + ".action.broadcast.binddevice");
            context.registerReceiver(receiver, intentFilter);
        }
    }

    public static void e(String name, boolean reload) {
        Context context = getContext();
        if (context != null) {
            Intent intent = new Intent();
            intent.putExtra("name", name);
            intent.putExtra("reload", reload);
            intent.setAction(context.getPackageName() + ".action.broadcast.replacemap");
            context.sendBroadcast(intent);
        }
    }

    public static void n(BroadcastReceiver receiver) {
        Context context = getContext();
        if (context != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(context.getPackageName() + ".action.broadcast.newres");
            context.registerReceiver(receiver, intentFilter);
        }
    }

    public static void ki(int type) {
        Context context = getContext();
        if (context != null) {
            Intent intent = new Intent();
            intent.putExtra("type", type);
            intent.setAction(context.getPackageName() + ".action.broadcast.newres");
            context.sendBroadcast(intent);
        }
    }

    public static void unregisterReceiver(BroadcastReceiver receiver) {
        Context context = getContext();
        if (context != null) {
            try {
                context.unregisterReceiver(receiver);
            } catch (IllegalArgumentException e) {
            }
        }
    }

    public static void o(BroadcastReceiver receiver) {
        Context context = getContext();
        if (context != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(context.getPackageName() + ".action.broadcast.delstock");
            context.registerReceiver(receiver, intentFilter);
        }
    }

    public static void a(Byte slotId) {
        Context context = getContext();
        if (context != null) {
            Intent intent = new Intent();
            intent.putExtra("id", slotId);
            intent.setAction(context.getPackageName() + ".action.broadcast.delstock");
            context.sendBroadcast(intent);
        }
    }

    public static void a(ItemStack item, int type) {
        Context context = getContext();
        if (context != null) {
            Intent intent = new Intent();
            intent.putExtra("item", item);
            intent.putExtra("type", type);
            intent.setAction(context.getPackageName() + ".action.broadcast.addstock");
            context.sendBroadcast(intent);
        }
    }

    public static void p(BroadcastReceiver receiver) {
        Context context = getContext();
        if (context != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(context.getPackageName() + ".action.broadcast.addstock");
            context.registerReceiver(receiver, intentFilter);
        }
    }

    public static void q(BroadcastReceiver receiver) {
        Context context = getContext();
        if (context != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(context.getPackageName() + ".action.broadcast.addentity");
            context.registerReceiver(receiver, intentFilter);
        }
    }

    public static void a(Entity item, int type) {
        Context context = getContext();
        if (context != null) {
            Intent intent = new Intent();
            intent.putExtra("item", item);
            intent.putExtra("type", type);
            intent.setAction(context.getPackageName() + ".action.broadcast.addentity");
            context.sendBroadcast(intent);
        }
    }

    public static void r(BroadcastReceiver receiver) {
        Context context = getContext();
        if (context != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(context.getPackageName() + ".action.broadcast.delentity");
            context.registerReceiver(receiver, intentFilter);
        }
    }

    public static void a(EntityItem item) {
        Context context = getContext();
        if (context != null) {
            Intent intent = new Intent();
            intent.putExtra("item", item);
            intent.setAction(context.getPackageName() + ".action.broadcast.delentity");
            context.sendBroadcast(intent);
        }
    }

    public static void i(int model, String cloudUserID) {
        Context context = getContext();
        if (context != null) {
            Intent intent = new Intent();
            intent.putExtra("model", model);
            intent.putExtra("cloudUserID", cloudUserID);
            intent.setAction(context.getPackageName() + ".action.broadcast.binddevice");
            HTApplication.getAppContext().sendBroadcast(intent);
        }
    }

    public static void s(BroadcastReceiver receiver) {
        Context context = getContext();
        if (context != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(context.getPackageName() + ".action.broadcast.checkpush");
            HTApplication.getAppContext().registerReceiver(receiver, intentFilter);
        }
    }

    public static void EL() {
        Context context = getContext();
        if (context != null) {
            Intent intent = new Intent();
            intent.setAction(context.getPackageName() + ".action.broadcast.checkpush");
            HTApplication.getAppContext().sendBroadcast(intent);
        }
    }
}
