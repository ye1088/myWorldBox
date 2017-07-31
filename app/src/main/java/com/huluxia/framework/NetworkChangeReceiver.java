package com.huluxia.framework;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.util.ArrayList;
import java.util.List;

public class NetworkChangeReceiver extends BroadcastReceiver {
    private List<INetworkClient> clients = new ArrayList();

    public void onReceive(Context context, Intent intent) {
        for (INetworkClient client : this.clients) {
            isWifiEnabled(intent, client);
        }
    }

    private void isWifiEnabled(Intent intent, INetworkClient client) {
        if ("android.net.wifi.WIFI_STATE_CHANGED".equals(intent.getAction())) {
            int wifiState = intent.getIntExtra("wifi_state", 0);
            if (wifiState == 1) {
                client.onWifiAvailble(false);
            } else if (wifiState == 3) {
                client.onWifiAvailble(true);
            }
        }
    }

    public void registerClient(INetworkClient client) {
        if (!this.clients.contains(client)) {
            this.clients.add(client);
        }
    }

    public void unregisterClient(INetworkClient client) {
        this.clients.remove(client);
    }
}
