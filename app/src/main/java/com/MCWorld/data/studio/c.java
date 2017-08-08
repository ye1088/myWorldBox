package com.MCWorld.data.studio;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/* compiled from: StudioCarouselInfo */
public class c extends com.MCWorld.module.a {
    public static final Creator<c> CREATOR = new Creator<c>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return U(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return aE(i);
        }

        public c U(Parcel source) {
            return new c(source);
        }

        public c[] aE(int size) {
            return new c[size];
        }
    };
    public List<a> list;

    /* compiled from: StudioCarouselInfo */
    public static class a extends com.MCWorld.widget.banner.a implements Parcelable {
        public static final Creator<a> CREATOR = new Creator<a>() {
            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return V(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return aF(i);
            }

            public a V(Parcel source) {
                return new a(source);
            }

            public a[] aF(int size) {
                return new a[size];
            }
        };
        public long id;
        public String imgUrl;
        public int mc_type;
        public String openUrl;
        public long postID;
        public int sid;
        public String title;
        public int type_id;

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.sid);
            dest.writeString(this.title);
            dest.writeString(this.openUrl);
            dest.writeInt(this.type_id);
            dest.writeLong(this.id);
            dest.writeInt(this.mc_type);
            dest.writeString(this.imgUrl);
            dest.writeLong(this.postID);
        }

        protected a(Parcel in) {
            this.sid = in.readInt();
            this.title = in.readString();
            this.openUrl = in.readString();
            this.type_id = in.readInt();
            this.id = in.readLong();
            this.mc_type = in.readInt();
            this.imgUrl = in.readString();
            this.postID = in.readLong();
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.list);
    }

    protected c(Parcel in) {
        super(in);
        this.list = in.createTypedArrayList(a.CREATOR);
    }

    public void setUrl() {
        for (a item : this.list) {
            item.url = item.imgUrl;
        }
    }
}
