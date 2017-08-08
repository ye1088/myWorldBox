package com.baidu.mapapi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord;
import com.tencent.open.SocialConstants;

class b extends BroadcastReceiver {
    b() {
    }

    public void onReceive(Context context, Intent intent) {
        WifiManager wifiManager = (WifiManager) context.getSystemService("wifi");
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(0);
        String str = "WIFI";
        int i = activeNetworkInfo != null ? (wifiManager.getWifiState() == 3 && str.matches(activeNetworkInfo.getTypeName())) ? 1 : 0 : networkInfo == null ? -1 : (wifiManager.getWifiState() == 3 && str.matches(networkInfo.getTypeName())) ? 1 : 0;
        Mj.s = i;
        Bundle bundle = new Bundle();
        bundle.putInt("opt", 15010902);
        bundle.putInt(SocialConstants.PARAM_ACT, 15010900);
        bundle.putInt(DownloadRecord.COLUMN_STATE, Mj.s);
        Mj.sendBundle(bundle);
        if (-1 != Mj.s) {
            Mj.changeGprsConnect();
        }
    }
}
