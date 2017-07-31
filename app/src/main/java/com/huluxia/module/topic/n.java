package com.huluxia.module.topic;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* compiled from: ZoneCategoryItem */
public class n implements Parcelable {
    public static final Creator<n> CREATOR = new 1();
    public int categorycount;
    public String description;
    public String icon;
    public int id;
    public int seq;
    public int status;
    public String title;

    public String toString() {
        return "ZoneCategoryItem{seq=" + this.seq + ", icon='" + this.icon + '\'' + ", status=" + this.status + ", description='" + this.description + '\'' + ", categorycount=" + this.categorycount + ", id=" + this.id + ", title='" + this.title + '\'' + '}';
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (this.id != ((n) o).id) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.id;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.seq);
        dest.writeString(this.icon);
        dest.writeInt(this.status);
        dest.writeString(this.description);
        dest.writeInt(this.categorycount);
        dest.writeInt(this.id);
        dest.writeString(this.title);
    }

    protected n(Parcel in) {
        this.seq = in.readInt();
        this.icon = in.readString();
        this.status = in.readInt();
        this.description = in.readString();
        this.categorycount = in.readInt();
        this.id = in.readInt();
        this.title = in.readString();
    }
}
