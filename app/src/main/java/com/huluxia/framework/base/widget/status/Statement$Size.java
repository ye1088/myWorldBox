package com.huluxia.framework.base.widget.status;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Statement$Size implements Parcelable {
    public static final Creator<Statement$Size> CREATOR = new Creator<Statement$Size>() {
        public Statement$Size createFromParcel(Parcel source) {
            return new Statement$Size(source);
        }

        public Statement$Size[] newArray(int size) {
            return new Statement$Size[size];
        }
    };
    public int height;
    public int width;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.width);
        dest.writeInt(this.height);
    }

    public Statement$Size(int width, int height) {
        this.width = width;
        this.height = height;
    }

    protected Statement$Size(Parcel in) {
        this.width = in.readInt();
        this.height = in.readInt();
    }
}
