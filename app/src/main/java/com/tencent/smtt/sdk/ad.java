package com.tencent.smtt.sdk;

import com.tencent.tbs.video.interfaces.IUserStateChangedListener;

class ad implements IUserStateChangedListener {
    final /* synthetic */ ac a;

    ad(ac acVar) {
        this.a = acVar;
    }

    public void onUserStateChanged() {
        this.a.a.c();
    }
}
