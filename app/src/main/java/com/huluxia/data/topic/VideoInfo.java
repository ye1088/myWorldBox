package com.huluxia.data.topic;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.huluxia.framework.base.json.Json;
import com.huluxia.framework.base.log.HLog;
import java.io.Serializable;

public class VideoInfo implements Parcelable, Serializable {
    public static final Creator<VideoInfo> CREATOR = new Creator<VideoInfo>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return ao(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return aY(i);
        }

        public VideoInfo ao(Parcel source) {
            return new VideoInfo(source);
        }

        public VideoInfo[] aY(int size) {
            return new VideoInfo[size];
        }
    };
    public String imgfid;
    public String imghost;
    public String imgurl;
    private String length;
    public String videofid;
    public String videohost;
    public String videourl;
    public String vtype;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.imgfid);
        dest.writeString(this.imgurl);
        dest.writeString(this.imghost);
        dest.writeString(this.videofid);
        dest.writeString(this.videourl);
        dest.writeString(this.videohost);
        dest.writeString(this.vtype);
        dest.writeString(this.length);
    }

    public VideoInfo(String _imgfid, String _videofid, String _length) {
        this.imgfid = _imgfid;
        this.videofid = _videofid;
        this.length = _length;
    }

    protected VideoInfo(Parcel in) {
        this.imgfid = in.readString();
        this.imgurl = in.readString();
        this.imghost = in.readString();
        this.videofid = in.readString();
        this.videourl = in.readString();
        this.videohost = in.readString();
        this.vtype = in.readString();
        this.length = in.readString();
    }

    public static VideoInfo convertFromString(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        VideoInfo info = null;
        try {
            info = (VideoInfo) Json.parseJsonObject(str, VideoInfo.class);
            info.imgurl = info.imghost + info.imgfid;
            info.videourl = info.videohost + info.videofid;
        } catch (Exception e) {
            HLog.error("VideoInfo", "convertFromString  " + e.getMessage(), new Object[0]);
        }
        HLog.verbose("VideoInfo", "convertFromString  " + info, new Object[0]);
        return info;
    }

    public String toString() {
        return "VideoInfo{imgfid='" + this.imgfid + '\'' + ", imgurl='" + this.imgurl + '\'' + ", imghost='" + this.imghost + '\'' + ", videofid='" + this.videofid + '\'' + ", videourl='" + this.videourl + '\'' + ", videohost='" + this.videohost + '\'' + ", vtype=" + this.vtype + '}';
    }

    public long getLength() {
        long len = 0;
        if (this.length == null) {
            return 0;
        }
        try {
            len = Long.valueOf(this.length).longValue();
        } catch (Exception e) {
        }
        return len;
    }
}
