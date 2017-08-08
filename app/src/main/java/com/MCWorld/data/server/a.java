package com.MCWorld.data.server;

import android.os.Parcel;
import android.os.Parcelable;

import com.MCWorld.module.b;
import java.util.ArrayList;
import java.util.List;

/* compiled from: ServerInfo */
public class a extends b {
    public static final Creator<a> CREATOR = new 1();
    public ArrayList<a> serverList;

    /* compiled from: ServerInfo */
    public static class a implements Parcelable {
        public static final Creator<a> CREATOR = new 1();
        public String addr;
        public String author;
        public String cateName;
        public String commend;
        public String createTime;
        public String icon;
        public int id;
        public String language;
        public String mapDesc;
        public long mapId;
        public String mapName;
        public long mapSize;
        public int maxCount;
        public String name;
        public int onlineCount;
        public int port;
        public List<String> resourceList;
        public int seq;
        public String shareUrl;
        public String source;
        public int status;
        public String tagList;
        public String ver;

        public a() {
            this.resourceList = new ArrayList();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.name);
            dest.writeString(this.icon);
            dest.writeString(this.author);
            dest.writeString(this.cateName);
            dest.writeString(this.ver);
            dest.writeString(this.language);
            dest.writeString(this.createTime);
            dest.writeString(this.tagList);
            dest.writeInt(this.status);
            dest.writeInt(this.onlineCount);
            dest.writeInt(this.maxCount);
            dest.writeString(this.addr);
            dest.writeInt(this.port);
            dest.writeInt(this.seq);
            dest.writeString(this.source);
            dest.writeLong(this.mapId);
            dest.writeString(this.mapName);
            dest.writeLong(this.mapSize);
            dest.writeString(this.mapDesc);
            dest.writeString(this.commend);
            dest.writeString(this.shareUrl);
            dest.writeStringList(this.resourceList);
        }

        protected a(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
            this.icon = in.readString();
            this.author = in.readString();
            this.cateName = in.readString();
            this.ver = in.readString();
            this.language = in.readString();
            this.createTime = in.readString();
            this.tagList = in.readString();
            this.status = in.readInt();
            this.onlineCount = in.readInt();
            this.maxCount = in.readInt();
            this.addr = in.readString();
            this.port = in.readInt();
            this.seq = in.readInt();
            this.source = in.readString();
            this.mapId = in.readLong();
            this.mapName = in.readString();
            this.mapSize = in.readLong();
            this.mapDesc = in.readString();
            this.commend = in.readString();
            this.shareUrl = in.readString();
            this.resourceList = in.createStringArrayList();
        }
    }

    public a() {
        this.serverList = new ArrayList();
        this.serverList = new ArrayList();
    }

    public a(Parcel source) {
        super(source);
        this.serverList = new ArrayList();
        source.readTypedList(this.serverList, a.CREATOR);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.serverList);
    }
}
