package com.tencent.smtt.sdk;

import com.tencent.smtt.utils.TbsLog;
import com.tencent.smtt.utils.j.a;

final class y implements a {
    y() {
    }

    public void a(int i) {
        TbsLog.i(TbsDownloader.LOGTAG, "[TbsDownloader.sendRequest] httpResponseCode=" + i);
    }
}
