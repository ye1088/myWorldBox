package com.MCWorld.data.banner;

import android.os.Parcel;
import android.os.Parcelable;

import com.MCWorld.module.b;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: HomeBannerInfo */
public class a extends b {
    public static final Creator<a> CREATOR = new Creator<a>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return l(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return V(i);
        }

        public a l(Parcel source) {
            return new a(source);
        }

        public a[] V(int size) {
            return new a[size];
        }
    };
    public ArrayList<a> mcCarousellist;

    /* compiled from: HomeBannerInfo */
    public static class a extends com.MCWorld.widget.banner.a implements Parcelable {
        public static final Creator<a> CREATOR = new Creator<a>() {
            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return m(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return W(i);
            }

            public a m(Parcel source) {
                return new a(source);
            }

            public a[] W(int size) {
                return new a[size];
            }
        };
        public String apptitle;
        public String icon;
        public int mc_type;
        public String md5;
        public int openID;
        public int opentype;
        public String packname;
        public long recommentid;
        public String recommenturl;
        public int versionCode;

        public a(Parcel source) {
            this();
            this.opentype = source.readInt();
            this.recommentid = source.readLong();
            this.recommenturl = source.readString();
            this.icon = source.readString();
            this.versionCode = source.readInt();
            this.apptitle = source.readString();
            this.md5 = source.readString();
            this.packname = source.readString();
            this.openID = source.readInt();
            this.mc_type = source.readInt();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.opentype);
            dest.writeLong(this.recommentid);
            dest.writeString(this.recommenturl);
            dest.writeString(this.icon);
            dest.writeInt(this.versionCode);
            dest.writeString(this.apptitle);
            dest.writeString(this.md5);
            dest.writeString(this.packname);
            dest.writeInt(this.openID);
            dest.writeInt(this.mc_type);
        }
    }

    public a() {
        this.mcCarousellist = new ArrayList();
    }

    public a(Parcel source) {
        super(source);
        source.readTypedList(this.mcCarousellist, a.CREATOR);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.mcCarousellist);
    }

    public void setUrl() {
        Iterator it = this.mcCarousellist.iterator();
        while (it.hasNext()) {
            a item = (a) it.next();
            item.url = item.icon;
        }
    }
}
