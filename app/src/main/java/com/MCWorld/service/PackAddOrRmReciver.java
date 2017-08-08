package com.MCWorld.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PackAddOrRmReciver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (context != null && intent != null && intent.getAction() != null) {
            String data;
            if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
                data = intent.getDataString();
                if (data != null && data.length() >= 9) {
                    i.g(context, data.substring(8), i.aDy);
                } else {
                    return;
                }
            }
            if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
                data = intent.getDataString();
                if (data != null && data.length() >= 9) {
                    i.g(context, data.substring(8), i.aDz);
                }
            }
        }
    }
}
