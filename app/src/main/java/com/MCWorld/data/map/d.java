package com.MCWorld.data.map;

import android.os.Parcel;
import android.os.Parcelable;

/* compiled from: GameInfo */
public class d implements Parcelable {
    public static final Creator<d> CREATOR = new Creator<d>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return s(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return ac(i);
        }

        public d s(Parcel in) {
            return new d(in);
        }

        public d[] ac(int size) {
            return new d[size];
        }
    };
    private String appId;
    private String downId;
    private int errCode;
    private int orderId;
    private String path;
    private long rcvSize;
    private int status;
    private String title;
    private long totalSize;
    private String url;

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getDownId() {
        return this.downId;
    }

    public void setDownId(String downId) {
        this.downId = downId;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getErrCode() {
        return this.errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }

    public long getRcvSize() {
        return this.rcvSize;
    }

    public void setRcvSize(long rcvSize) {
        this.rcvSize = rcvSize;
    }

    public long getTotalSize() {
        return this.totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public d(String appId, long totalSize, String url, String title) {
        this.appId = appId;
        this.totalSize = totalSize;
        this.url = url;
        this.title = title;
        this.downId = "-1";
        this.status = 0;
        this.errCode = 0;
        this.rcvSize = 0;
    }

    private d(Parcel in) {
        this.appId = in.readString();
        this.downId = in.readString();
        this.status = in.readInt();
        this.errCode = in.readInt();
        this.rcvSize = in.readLong();
        this.totalSize = in.readLong();
        this.orderId = in.readInt();
        this.url = in.readString();
        this.title = in.readString();
        this.path = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.appId);
        dest.writeString(this.downId);
        dest.writeInt(this.status);
        dest.writeInt(this.errCode);
        dest.writeLong(this.rcvSize);
        dest.writeLong(this.totalSize);
        dest.writeInt(this.orderId);
        dest.writeString(this.url);
        dest.writeString(this.title);
        dest.writeString(this.path);
    }

    public int getOrderId() {
        return this.orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
