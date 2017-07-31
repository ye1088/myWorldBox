package com.xiaomi.xmpush.thrift;

import com.tencent.smtt.sdk.TbsListener.ErrorCode;

public enum b {
    UploadSwitch(1),
    UploadFrequency(2),
    ScreenSizeCollectionSwitch(3),
    MacCollectionSwitch(4),
    IMSICollectionSwitch(5),
    AndroidVnCollectionSwitch(6),
    AndroidVcCollectionSwitch(7),
    AndroidIdCollectionSwitch(8),
    DeviceInfoCollectionFrequency(9),
    AppInstallListCollectionSwitch(10),
    AppInstallListCollectionFrequency(11),
    AppActiveListCollectionSwitch(12),
    AppActiveListCollectionFrequency(13),
    BluetoothCollectionSwitch(14),
    BluetoothCollectionFrequency(15),
    LocationCollectionSwitch(16),
    LocationCollectionFrequency(17),
    AccountCollectionSwitch(18),
    AccountCollectionFrequency(19),
    WifiCollectionSwitch(20),
    WifiCollectionFrequency(21),
    CellularCollectionSwitch(22),
    CellularCollectionFrequency(23),
    TopAppCollectionSwitch(24),
    TopAppCollectionFrequency(25),
    DataCollectionSwitch(26),
    OcVersionCheckFrequency(27),
    CollectionDataPluginVersion(ErrorCode.ERROR_HOSTAPP_UNAVAILABLE),
    CollectionPluginDownloadUrl(ErrorCode.ERROR_FORCE_SYSWEBVIEW),
    CollectionPluginMd5(ErrorCode.ERROR_NOMATCH_COREVERSION),
    CollectionPluginForceStop(1004);
    
    private final int F;

    private b(int i) {
        this.F = i;
    }

    public int a() {
        return this.F;
    }
}
