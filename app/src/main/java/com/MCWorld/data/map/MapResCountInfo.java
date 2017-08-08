package com.MCWorld.data.map;

import android.os.Parcel;
import android.os.Parcelable;

import com.MCWorld.module.a;
import java.io.Serializable;
import org.json.JSONException;
import org.json.JSONObject;

public class MapResCountInfo extends a implements Parcelable, Serializable {
    public static final Creator<MapResCountInfo> CREATOR = new Creator<MapResCountInfo>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return z(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return aj(i);
        }

        public MapResCountInfo z(Parcel source) {
            return new MapResCountInfo(source);
        }

        public MapResCountInfo[] aj(int size) {
            return new MapResCountInfo[size];
        }
    };
    private static final long serialVersionUID = 2244236870580456341L;
    private int adCount;
    private int jsCount;
    private int mapCount;
    private int seedCount;
    private int skinCount;
    private int woodCount;

    public MapResCountInfo(JSONObject obj) throws JSONException {
        if (obj != null) {
            this.mapCount = obj.optInt("mapCount");
            this.jsCount = obj.optInt("jsCount");
            this.skinCount = obj.optInt("skinCount");
            this.woodCount = obj.optInt("woodCount");
            this.seedCount = obj.optInt("seedCount");
            this.adCount = obj.optInt("adCount");
        }
    }

    public MapResCountInfo(Parcel in) {
        this.mapCount = in.readInt();
        this.jsCount = in.readInt();
        this.skinCount = in.readInt();
        this.woodCount = in.readInt();
        this.seedCount = in.readInt();
        this.adCount = in.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.mapCount);
        dest.writeInt(this.jsCount);
        dest.writeInt(this.skinCount);
        dest.writeInt(this.woodCount);
        dest.writeInt(this.seedCount);
        dest.writeInt(this.adCount);
    }

    public int getMapCount() {
        return this.mapCount;
    }

    public void setMapCount(int mapCount) {
        this.mapCount = mapCount;
    }

    public int getJsCount() {
        return this.jsCount;
    }

    public void setJsCount(int jsCount) {
        this.jsCount = jsCount;
    }

    public int getSkinCount() {
        return this.skinCount;
    }

    public void setSkinCount(int skinCount) {
        this.skinCount = skinCount;
    }

    public int getWoodCount() {
        return this.woodCount;
    }

    public void setWoodCount(int woodCount) {
        this.woodCount = woodCount;
    }

    public int getSeedCount() {
        return this.seedCount;
    }

    public void setSeedCount(int seedCount) {
        this.seedCount = seedCount;
    }

    public int getAdCount() {
        return this.adCount;
    }

    public void setAdCount(int adCount) {
        this.adCount = adCount;
    }
}
