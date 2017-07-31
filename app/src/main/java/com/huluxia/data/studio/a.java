package com.huluxia.data.studio;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.huluxia.module.b;
import java.util.List;

/* compiled from: AllStudioInfo */
public class a extends b {
    public static final Creator<a> CREATOR = new Creator<a>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return Q(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return aA(i);
        }

        public a Q(Parcel source) {
            return new a(source);
        }

        public a[] aA(int size) {
            return new a[size];
        }
    };
    public List<a> list;

    /* compiled from: AllStudioInfo */
    public static class a implements Parcelable {
        public static final Creator<a> CREATOR = new Creator<a>() {
            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return R(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return aB(i);
            }

            public a R(Parcel source) {
                return new a(source);
            }

            public a[] aB(int size) {
                return new a[size];
            }
        };
        public String icon;
        public long identityColor;
        public String identityTitle;
        public String name;
        public int sid;

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.sid);
            dest.writeString(this.icon);
            dest.writeString(this.name);
            dest.writeString(this.identityTitle);
            dest.writeLong(this.identityColor);
        }

        protected a(Parcel in) {
            this.sid = in.readInt();
            this.icon = in.readString();
            this.name = in.readString();
            this.identityTitle = in.readString();
            this.identityColor = in.readLong();
        }

        public int getIdentityColor() {
            return (int) this.identityColor;
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.list);
    }

    protected a(Parcel in) {
        super(in);
        this.list = in.createTypedArrayList(a.CREATOR);
    }
}
