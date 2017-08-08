package com.MCWorld.data.profile;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/* compiled from: Studio */
public class c extends com.MCWorld.module.a {
    public static final Creator<c> CREATOR = new 1();
    public List<hlx.module.resources.a> cates;
    public a studioInfo;

    /* compiled from: Studio */
    public static class a implements Parcelable {
        public static final Creator<a> CREATOR = new 1();
        public int count;
        public String coverImg;
        public long createTime;
        public String description;
        public String icon;
        public int id;
        public String integral;
        public int isidentity;
        public int ismedal;
        public String name;
        public String qq;
        public String qqgroup;
        public String tags;
        public int typeid;
        public int userid;

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.icon);
            dest.writeInt(this.count);
            dest.writeString(this.description);
            dest.writeString(this.integral);
            dest.writeString(this.qq);
            dest.writeString(this.qqgroup);
            dest.writeInt(this.ismedal);
            dest.writeInt(this.isidentity);
            dest.writeInt(this.typeid);
            dest.writeString(this.name);
            dest.writeInt(this.id);
            dest.writeLong(this.createTime);
            dest.writeInt(this.userid);
            dest.writeString(this.tags);
            dest.writeString(this.coverImg);
        }

        protected a(Parcel in) {
            this.icon = in.readString();
            this.count = in.readInt();
            this.description = in.readString();
            this.integral = in.readString();
            this.qq = in.readString();
            this.qqgroup = in.readString();
            this.ismedal = in.readInt();
            this.isidentity = in.readInt();
            this.typeid = in.readInt();
            this.name = in.readString();
            this.id = in.readInt();
            this.createTime = in.readLong();
            this.userid = in.readInt();
            this.tags = in.readString();
            this.coverImg = in.readString();
        }

        public String toString() {
            return "StudioInfo{icon='" + this.icon + '\'' + ", count=" + this.count + ", description='" + this.description + '\'' + ", integral='" + this.integral + '\'' + ", qq='" + this.qq + '\'' + ", qqgroup='" + this.qqgroup + '\'' + ", ismedal=" + this.ismedal + ", isidentity=" + this.isidentity + ", typeid=" + this.typeid + ", name='" + this.name + '\'' + ", id=" + this.id + ", createTime=" + this.createTime + ", userid=" + this.userid + ", tags='" + this.tags + '\'' + ", coverImg='" + this.coverImg + '\'' + '}';
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.studioInfo, flags);
        dest.writeTypedList(this.cates);
    }

    protected c(Parcel in) {
        super(in);
        this.studioInfo = (a) in.readParcelable(a.class.getClassLoader());
        this.cates = in.createTypedArrayList(hlx.module.resources.a.CREATOR);
    }

    public String toString() {
        return "Studio{studioInfo=" + this.studioInfo + ", cates=" + this.cates + '}';
    }
}
