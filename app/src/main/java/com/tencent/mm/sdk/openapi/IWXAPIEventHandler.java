package com.tencent.mm.sdk.openapi;

public interface IWXAPIEventHandler {
    void onReq(BaseReq baseReq);

    void onResp(BaseResp baseResp);
}
