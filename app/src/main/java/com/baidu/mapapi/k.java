package com.baidu.mapapi;

class k implements j {
    private MKOfflineMapListener a;

    public k(MKOfflineMapListener mKOfflineMapListener) {
        this.a = mKOfflineMapListener;
    }

    public void a(MKEvent mKEvent) {
        if (this.a != null) {
            switch (mKEvent.a) {
                case 0:
                case 6:
                    this.a.onGetOfflineMapState(mKEvent.a, mKEvent.c);
                    return;
                case 4:
                    this.a.onGetOfflineMapState(mKEvent.a, 0);
                    return;
                default:
                    return;
            }
        }
    }
}
