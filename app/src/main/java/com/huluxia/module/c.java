package com.huluxia.module;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;
import java.util.List;

/* compiled from: GameDetail */
public class c extends a implements Parcelable {
    public static final Creator<c> CREATOR = new Creator<c>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return az(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return iU(i);
        }

        public c az(Parcel source) {
            return new c(source);
        }

        public c[] iU(int size) {
            return new c[size];
        }
    };
    public GameInfo gameinfo;
    public List<a> similarList;

    /* compiled from: GameDetail */
    public static class a implements Parcelable {
        public static final Creator<a> CREATOR = new Creator<a>() {
            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return aA(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return iV(i);
            }

            public a aA(Parcel source) {
                return new a(source);
            }

            public a[] iV(int size) {
                return new a[size];
            }
        };
        public String appid;
        public String applogo;
        private String apptitle;

        public a(Parcel source) {
            this();
            this.applogo = source.readString();
            this.appid = source.readString();
            this.apptitle = source.readString();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.applogo);
            dest.writeString(this.appid);
            dest.writeString(this.apptitle);
        }

        public String getAppTitle() {
            return this.apptitle;
        }

        public void setAppTitle(String apptitle) {
            this.apptitle = apptitle;
        }
    }

    public c() {
        this.similarList = new ArrayList();
        this.similarList = new ArrayList();
    }

    public c(Parcel source) {
        super(source);
        this.similarList = new ArrayList();
        this.gameinfo = (GameInfo) source.readParcelable(GameInfo.class.getClassLoader());
        source.readTypedList(this.similarList, a.CREATOR);
    }

    public int describeContents() {
        return super.describeContents();
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.gameinfo, 0);
        dest.writeTypedList(this.similarList);
    }
}
