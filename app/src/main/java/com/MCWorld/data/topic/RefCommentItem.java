package com.MCWorld.data.topic;

import android.os.Parcel;
import android.os.Parcelable;

import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord;
import io.netty.handler.codec.rtsp.RtspHeaders.Values;
import java.io.Serializable;
import org.apache.tools.ant.types.selectors.ContainsSelector;
import org.json.JSONObject;

public class RefCommentItem implements Parcelable, Serializable {
    public static final Creator<RefCommentItem> CREATOR = new 1();
    public static final long serialVersionUID = 7230398114306443767L;
    public long commentID;
    public String nick;
    public long seq;
    public int state;
    public String text;

    public RefCommentItem(JSONObject obj) {
        this.commentID = obj.optLong("commentID");
        this.nick = obj.optString("nick");
        this.seq = obj.optLong(Values.SEQ);
        this.text = obj.optString(ContainsSelector.CONTAINS_KEY);
        this.state = obj.optInt(DownloadRecord.COLUMN_STATE);
    }

    public RefCommentItem(Parcel source) {
        this.commentID = source.readLong();
        this.nick = source.readString();
        this.seq = source.readLong();
        this.text = source.readString();
        this.state = source.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.commentID);
        dest.writeString(this.nick);
        dest.writeLong(this.seq);
        dest.writeString(this.text);
        dest.writeInt(this.state);
    }

    public long getCommentID() {
        return this.commentID;
    }

    public void setCommentID(long commentID) {
        this.commentID = commentID;
    }

    public String getNick() {
        return this.nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public long getSeq() {
        return this.seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
