package com.huluxia.video.views.scalable;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* compiled from: Size */
public class b implements Parcelable {
    public static final Creator<b> CREATOR = new Creator<b>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return by(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return lK(i);
        }

        public b by(Parcel source) {
            return new b(source);
        }

        public b[] lK(int size) {
            return new b[size];
        }
    };
    private int mHeight;
    private int mWidth;

    public b(int width, int height) {
        this.mWidth = width;
        this.mHeight = height;
    }

    public int getWidth() {
        return this.mWidth;
    }

    public int getHeight() {
        return this.mHeight;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mWidth);
        dest.writeInt(this.mHeight);
    }

    protected b(Parcel in) {
        this.mWidth = in.readInt();
        this.mHeight = in.readInt();
    }
}
