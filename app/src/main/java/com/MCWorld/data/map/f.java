package com.MCWorld.data.map;

import android.os.Parcel;
import android.os.Parcelable;

import com.MCWorld.data.UserBaseInfo;
import com.MCWorld.module.b;
import java.util.ArrayList;
import java.util.List;

/* compiled from: MapRankingInfo */
public class f extends b {
    public static final Creator<f> CREATOR = new 1();
    public ArrayList<a> mapList;

    /* compiled from: MapRankingInfo */
    public static class a implements Parcelable {
        public static final Creator<a> CREATOR = new 1();
        public String author;
        public String cateName;
        public String createTime;
        public long downCount;
        public String downUrl;
        public String homeImage;
        public String icon;
        public int id;
        public String instruct;
        public int isRecommend;
        public int istmp;
        public String language;
        public String mapDesc;
        public String mapSize;
        public String md5;
        public String name;
        public long postID;
        public List<String> resourceList;
        public String shareUrl;
        public String source;
        public com.MCWorld.data.topic.a studio;
        public String subVersion;
        public UserBaseInfo user;
        public String version;

        public a() {
            this.resourceList = new ArrayList();
        }

        public static MapItem convertMapItem(a info) {
            return new MapItem((long) info.id, info.name, info.downUrl, info.icon, info.createTime, info.md5, info.version, info.subVersion, info.postID);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.name);
            dest.writeString(this.icon);
            dest.writeString(this.mapSize);
            dest.writeString(this.instruct);
            dest.writeString(this.mapDesc);
            dest.writeString(this.downUrl);
            dest.writeString(this.createTime);
            dest.writeString(this.cateName);
            dest.writeString(this.language);
            dest.writeString(this.author);
            dest.writeString(this.source);
            dest.writeInt(this.isRecommend);
            dest.writeStringList(this.resourceList);
            dest.writeString(this.md5);
            dest.writeLong(this.downCount);
            dest.writeString(this.shareUrl);
            dest.writeString(this.version);
            dest.writeString(this.subVersion);
            dest.writeString(this.homeImage);
            dest.writeInt(this.istmp);
            dest.writeLong(this.postID);
            dest.writeParcelable(this.studio, flags);
            dest.writeParcelable(this.user, flags);
        }

        protected a(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
            this.icon = in.readString();
            this.mapSize = in.readString();
            this.instruct = in.readString();
            this.mapDesc = in.readString();
            this.downUrl = in.readString();
            this.createTime = in.readString();
            this.cateName = in.readString();
            this.language = in.readString();
            this.author = in.readString();
            this.source = in.readString();
            this.isRecommend = in.readInt();
            this.resourceList = in.createStringArrayList();
            this.md5 = in.readString();
            this.downCount = in.readLong();
            this.shareUrl = in.readString();
            this.version = in.readString();
            this.subVersion = in.readString();
            this.homeImage = in.readString();
            this.istmp = in.readInt();
            this.postID = in.readLong();
            this.studio = (com.MCWorld.data.topic.a) in.readParcelable(com.MCWorld.data.topic.a.class.getClassLoader());
            this.user = (UserBaseInfo) in.readParcelable(UserBaseInfo.class.getClassLoader());
        }
    }

    public f() {
        this.mapList = new ArrayList();
        this.mapList = new ArrayList();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.mapList);
    }

    protected f(Parcel in) {
        super(in);
        this.mapList = new ArrayList();
        this.mapList = in.createTypedArrayList(a.CREATOR);
    }
}
