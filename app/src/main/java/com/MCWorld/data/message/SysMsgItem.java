package com.MCWorld.data.message;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class SysMsgItem implements Parcelable, Serializable {
    public static final Creator<SysMsgItem> CREATOR = new Creator<SysMsgItem>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return C(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return am(i);
        }

        public SysMsgItem C(Parcel source) {
            return new SysMsgItem(source);
        }

        public SysMsgItem[] am(int size) {
            return new SysMsgItem[size];
        }
    };
    private static final long serialVersionUID = -8772788238904765217L;
    private SysMsgContent content = null;
    private long contentType;
    private long createTime;
    private int operateType;

    public SysMsgItem(JSONObject obj) throws JSONException {
        this.contentType = obj.optLong("contentType");
        this.createTime = obj.optLong("createTime");
        if (!obj.isNull("content")) {
            this.content = new SysMsgContent(obj.optJSONObject("content"));
        }
        this.operateType = obj.optInt("operateType", 0);
    }

    public long getContentType() {
        return this.contentType;
    }

    public void setContentType(long contentType) {
        this.contentType = contentType;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public SysMsgContent getContent() {
        return this.content;
    }

    public void setContent(SysMsgContent content) {
        this.content = content;
    }

    public int getOperateType() {
        return this.operateType;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.contentType);
        dest.writeLong(this.createTime);
        dest.writeParcelable(this.content, flags);
        dest.writeInt(this.operateType);
    }

    protected SysMsgItem(Parcel in) {
        this.contentType = in.readLong();
        this.createTime = in.readLong();
        this.content = (SysMsgContent) in.readParcelable(SysMsgContent.class.getClassLoader());
        this.operateType = in.readInt();
    }
}
