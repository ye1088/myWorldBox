package com.xiaomi.xmpush.thrift;

import java.util.Map;

public enum f {
    Invalid("INVALID"),
    BarClick("bar:click"),
    BarCancel("bar:cancel"),
    AppOpen("app:open"),
    PackageUninstall("package uninstalled"),
    AppUninstall("app_uninstalled"),
    ClientInfoUpdate("client_info_update"),
    ClientInfoUpdateOk("client_info_update_ok"),
    PullOfflineMessage("pull"),
    IosSleep("ios_sleep"),
    IosWakeUp("ios_wakeup"),
    NormalClientConfigUpdate("normal_client_config_update"),
    CustomClientConfigUpdate("custom_client_config_update"),
    DailyCheckClientConfig("daily_check_client_config"),
    DataCollection("data_collection");
    
    public static Map<String, f> q;
    public String p;

    static {
        q = null;
    }

    private f(String str) {
        this.p = str;
    }

    public String toString() {
        return this.p;
    }
}
