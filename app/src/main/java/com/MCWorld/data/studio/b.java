package com.MCWorld.data.studio;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/* compiled from: StudioAnnounceInfo */
public class b extends com.MCWorld.module.b {
    public static final Creator<b> CREATOR = new 1();
    public a info;
    public List<a> list;

    /* compiled from: StudioAnnounceInfo */
    public static class a implements Parcelable {
        public static final Creator<a> CREATOR = new 1();
        public String announcer;
        public String content;
        public long createTime;
        public int id;
        public String title;

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.title);
            dest.writeString(this.announcer);
            dest.writeLong(this.createTime);
            dest.writeString(this.content);
            dest.writeInt(this.id);
        }

        protected a(Parcel in) {
            this.title = in.readString();
            this.announcer = in.readString();
            this.createTime = in.readLong();
            this.content = in.readString();
            this.id = in.readInt();
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.list);
        dest.writeParcelable(this.info, flags);
    }

    protected b(Parcel in) {
        super(in);
        this.list = in.createTypedArrayList(a.CREATOR);
        this.info = (a) in.readParcelable(a.class.getClassLoader());
    }
}
