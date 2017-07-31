package hlx.module.news;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* compiled from: HeaderImage */
public class a implements Parcelable {
    public static final Creator<a> CREATOR = new Creator<a>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return bC(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return nj(i);
        }

        public a bC(Parcel in) {
            return new a(in);
        }

        public a[] nj(int size) {
            return new a[size];
        }
    };
    public static final int TYPE_LOCAL_JUMP = 3;
    public static final int TYPE_POST = 1;
    public static final int TYPE_WEB = 2;
    public String icon;
    public int opentype;
    public long recommentid;
    public String recommenturl;

    public a(Parcel source) {
        this.icon = source.readString();
        this.opentype = source.readInt();
        this.recommentid = source.readLong();
        this.recommenturl = source.readString();
    }

    public a(String inputIcon, int inputOpentype, long inputRecommentied, String inputRecommenturl) {
        this.icon = inputIcon;
        this.opentype = inputOpentype;
        this.recommentid = inputRecommentied;
        this.recommenturl = inputRecommenturl;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.icon);
        dest.writeInt(this.opentype);
        dest.writeLong(this.recommentid);
        dest.writeString(this.recommenturl);
    }
}
