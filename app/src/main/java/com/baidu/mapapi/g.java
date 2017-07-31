package com.baidu.mapapi;

class g implements j {
    private MKGeneralListener a;

    public g(MKGeneralListener mKGeneralListener) {
        this.a = mKGeneralListener;
    }

    public void a(MKEvent mKEvent) {
        switch (mKEvent.a) {
            case 7:
                this.a.onGetNetworkState(mKEvent.b);
                return;
            case 9:
                this.a.onGetPermissionState(mKEvent.b);
                return;
            default:
                return;
        }
    }
}
