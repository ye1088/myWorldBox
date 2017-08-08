package com.MCWorld.module;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/* compiled from: GameGoodInfo */
public class f extends a implements Parcelable {
    public static final Creator<f> CREATOR = new Creator<f>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return aC(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return iX(i);
        }

        public f aC(Parcel in) {
            return new f(in);
        }

        public f[] iX(int size) {
            return new f[size];
        }
    };
    public ArrayList<a> list = new ArrayList();

    /* compiled from: GameGoodInfo */
    public static class a implements Parcelable {
        public static final Creator<a> CREATOR = new Creator<a>() {
            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return aD(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return iY(i);
            }

            public a aD(Parcel in) {
                return new a(in);
            }

            public a[] iY(int size) {
                return new a[size];
            }
        };
        public int id;
        public String imgUrl;
        public String title;
        public String url;

        protected a(Parcel in) {
            this.id = in.readInt();
            this.title = in.readString();
            this.imgUrl = in.readString();
            this.url = in.readString();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.title);
            dest.writeString(this.imgUrl);
            dest.writeString(this.url);
        }
    }

    protected f(Parcel in) {
        super(in);
        this.list = in.createTypedArrayList(a.CREATOR);
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.list);
    }

    public int describeContents() {
        return 0;
    }
}
