package com.huluxia.framework.base.http.toolbox.download;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class DownloadRecord$1 implements Creator<DownloadRecord> {
    DownloadRecord$1() {
    }

    public DownloadRecord createFromParcel(Parcel source) {
        return new DownloadRecord(source);
    }

    public DownloadRecord[] newArray(int size) {
        return new DownloadRecord[size];
    }
}
