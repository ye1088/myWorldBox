package com.MCWorld.module.picture;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/* compiled from: PictureBucket */
public class a implements Parcelable {
    public static final Creator<a> CREATOR = new Creator<a>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return ba(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return jN(i);
        }

        public a ba(Parcel in) {
            return new a(in);
        }

        public a[] jN(int size) {
            return new a[size];
        }
    };
    public long bucketId;
    public String bucketName;
    public ArrayList<b> pictures = new ArrayList();

    public a(Parcel in) {
        this.bucketId = in.readLong();
        this.bucketName = in.readString();
        in.readTypedList(this.pictures, b.CREATOR);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.bucketId);
        dest.writeString(this.bucketName);
        dest.writeTypedList(this.pictures);
    }

    public int getBucketSize() {
        return this.pictures.size();
    }
}
