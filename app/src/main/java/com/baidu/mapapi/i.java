package com.baidu.mapapi;

class i implements j {
    private MKMapViewListener a;

    public i(MKMapViewListener mKMapViewListener) {
        this.a = mKMapViewListener;
    }

    public void a(MKEvent mKEvent) {
        if (this.a != null) {
            switch (mKEvent.a) {
                case 14:
                    this.a.onMapMoveFinish();
                    return;
                default:
                    return;
            }
        }
    }
}
