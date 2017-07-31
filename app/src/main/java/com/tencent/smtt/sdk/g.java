package com.tencent.smtt.sdk;

import com.tencent.smtt.utils.TbsLog;

final class g implements TbsListener {
    g() {
    }

    public void onCallBackErrorCode(int i, String str) {
        TbsLog.e("QbSdk", "onCallBackErrorCode errCode = " + i + " info: " + str);
        if (QbSdk.c() != null) {
            QbSdk.c().onCallBackErrorCode(i, str);
        }
    }

    public void onDownloadFinish(int i) {
        TbsDownloader.a = false;
        if (QbSdk.c() != null) {
            QbSdk.c().onDownloadFinish(i);
        }
        if (QbSdk.mTbsListenerDebug != null) {
            QbSdk.mTbsListenerDebug.onDownloadFinish(i);
        }
    }

    public void onDownloadProgress(int i) {
        if (QbSdk.mTbsListenerDebug != null) {
            QbSdk.mTbsListenerDebug.onDownloadProgress(i);
        }
        if (QbSdk.c() != null) {
            QbSdk.c().onDownloadProgress(i);
        }
    }

    public void onInstallFinish(int i) {
        QbSdk.setTBSInstallingStatus(false);
        TbsDownloader.a = false;
        if (QbSdk.c() != null) {
            QbSdk.c().onInstallFinish(i);
        }
        if (QbSdk.mTbsListenerDebug != null) {
            QbSdk.mTbsListenerDebug.onInstallFinish(i);
        }
    }
}
