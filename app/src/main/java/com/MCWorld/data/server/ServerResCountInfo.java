package com.MCWorld.data.server;

import android.os.Parcel;
import android.os.Parcelable;

import com.MCWorld.module.a;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class ServerResCountInfo extends a implements Parcelable, Serializable {
    public static final Creator<ServerResCountInfo> CREATOR = new Creator<ServerResCountInfo>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return O(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return ay(i);
        }

        public ServerResCountInfo O(Parcel source) {
            return new ServerResCountInfo(source);
        }

        public ServerResCountInfo[] ay(int size) {
            return new ServerResCountInfo[size];
        }
    };
    private static final long serialVersionUID = 2244236870580456341L;
    private int newCount;
    private int recommendCount;
    private int serverCount;

    public ServerResCountInfo(JSONObject obj) throws JSONException {
        if (obj != null) {
            this.serverCount = obj.optInt("serverCount");
            this.recommendCount = obj.optInt("recommendCount");
            this.newCount = obj.optInt("newCount");
        }
    }

    public ServerResCountInfo(Parcel in) {
        this.serverCount = in.readInt();
        this.recommendCount = in.readInt();
        this.newCount = in.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.serverCount);
        dest.writeInt(this.recommendCount);
        dest.writeInt(this.newCount);
    }

    public int getServerCount() {
        return this.serverCount;
    }

    public void setServerCount(int serverCount) {
        this.serverCount = serverCount;
    }

    public int getNewCount() {
        return this.newCount;
    }

    public void setNewCount(int newCount) {
        this.newCount = newCount;
    }

    public int getRecommendCount() {
        return this.recommendCount;
    }

    public void setRecommendCount(int recommendCount) {
        this.recommendCount = recommendCount;
    }
}
