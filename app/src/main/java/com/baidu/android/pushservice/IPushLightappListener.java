package com.baidu.android.pushservice;

public interface IPushLightappListener {
    void initialComplete(PushLightapp pushLightapp);

    void onSubscribeResult(int i, String str);

    void onUnbindLightResult(int i, String str);

    void onUnsubscribeResult(int i, String str);
}
