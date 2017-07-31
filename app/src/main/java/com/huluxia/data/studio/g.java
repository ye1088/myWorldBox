package com.huluxia.data.studio;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.List;

/* compiled from: StudioRecommenInfo */
public class g extends com.huluxia.module.a {
    public static final Creator<g> CREATOR = new Creator<g>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return aa(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return aK(i);
        }

        public g aa(Parcel source) {
            return new g(source);
        }

        public g[] aK(int size) {
            return new g[size];
        }
    };
    public List<a> list;

    /* compiled from: StudioRecommenInfo */
    public static class a implements Parcelable {
        public static final Creator<a> CREATOR = new Creator<a>() {
            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return ab(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return aL(i);
            }

            public a ab(Parcel source) {
                return new a(source);
            }

            public a[] aL(int size) {
                return new a[size];
            }
        };
        public List<hlx.module.resources.a> cates;
        public int count;
        public String icon;
        public String integral;
        public int isRank;
        public String name;
        public int sid;
        public int type_id;

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.sid);
            dest.writeString(this.name);
            dest.writeString(this.icon);
            dest.writeString(this.integral);
            dest.writeInt(this.type_id);
            dest.writeInt(this.count);
            dest.writeInt(this.isRank);
            dest.writeTypedList(this.cates);
        }

        protected a(Parcel in) {
            this.sid = in.readInt();
            this.name = in.readString();
            this.icon = in.readString();
            this.integral = in.readString();
            this.type_id = in.readInt();
            this.count = in.readInt();
            this.isRank = in.readInt();
            this.cates = in.createTypedArrayList(hlx.module.resources.a.CREATOR);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.list);
    }

    protected g(Parcel in) {
        super(in);
        this.list = in.createTypedArrayList(a.CREATOR);
    }
}
