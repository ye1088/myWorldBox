package com.huluxia.data.category;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.huluxia.data.UserBaseInfo;
import io.netty.handler.codec.rtsp.RtspHeaders.Values;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class Daren implements Parcelable, Serializable {
    public static final Creator<Daren> CREATOR = new 1();
    private static final long serialVersionUID = 1754949163893278682L;
    private boolean flag;
    private long lasttotal;
    private long seq;
    private UserBaseInfo user;
    private long weektotal;

    public Daren(JSONObject obj) throws JSONException {
        this.lasttotal = obj.optLong("lasttotal");
        this.weektotal = obj.optLong("weektotal");
        this.flag = obj.optBoolean("flag");
        this.seq = obj.optLong(Values.SEQ);
        this.user = new UserBaseInfo(obj.optJSONObject("user"));
    }

    protected Daren(Parcel in) {
        this.lasttotal = in.readLong();
        this.weektotal = in.readLong();
        this.flag = in.readByte() != (byte) 0;
        this.seq = in.readLong();
        this.user = (UserBaseInfo) in.readParcelable(UserBaseInfo.class.getClassLoader());
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.lasttotal);
        dest.writeLong(this.weektotal);
        dest.writeByte((byte) (this.flag ? 1 : 0));
        dest.writeLong(this.seq);
        dest.writeParcelable(this.user, flags);
    }

    public int describeContents() {
        return 0;
    }

    public long getLasttotal() {
        return this.lasttotal;
    }

    public void setLasttotal(long lasttotal) {
        this.lasttotal = lasttotal;
    }

    public long getWeektotal() {
        return this.weektotal;
    }

    public void setWeektotal(long weektotal) {
        this.weektotal = weektotal;
    }

    public boolean isFlag() {
        return this.flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public UserBaseInfo getDaren() {
        return this.user;
    }

    public void setDaren(UserBaseInfo daren) {
        this.user = daren;
    }

    public long getSeq() {
        return this.seq;
    }
}
