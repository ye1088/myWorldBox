package com.tencent.stat;

interface StatDispatchCallback {
    void onDispatchFailure();

    void onDispatchSuccess();
}
