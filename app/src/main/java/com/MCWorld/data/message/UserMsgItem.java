package com.MCWorld.data.message;

import android.os.Parcel;
import android.os.Parcelable;

import com.MCWorld.data.topic.CommentItem;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class UserMsgItem implements Parcelable, Serializable {
    public static final Creator<UserMsgItem> CREATOR = new 1();
    private static final long serialVersionUID = -8772788238904765217L;
    private CommentItem content = null;
    private int contentType;
    private long createTime;
    private int operateType;

    public UserMsgItem(JSONObject obj) throws JSONException {
        this.contentType = obj.optInt("contentType");
        this.createTime = obj.optLong("createTime");
        if (!obj.isNull("content")) {
            this.content = new CommentItem(obj.optJSONObject("content"));
        }
        this.operateType = obj.optInt("operateType", 0);
    }

    public int getContentType() {
        return this.contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public CommentItem getContent() {
        return this.content;
    }

    public void setContent(CommentItem content) {
        this.content = content;
    }

    public int getOperateType() {
        return this.operateType;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.contentType);
        dest.writeLong(this.createTime);
        dest.writeSerializable(this.content);
        dest.writeInt(this.operateType);
    }

    protected UserMsgItem(Parcel in) {
        this.contentType = in.readInt();
        this.createTime = in.readLong();
        this.content = (CommentItem) in.readSerializable();
        this.operateType = in.readInt();
    }
}
