package hlx.module.resources;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* compiled from: MapCateItem */
public class a implements Parcelable {
    public static final Creator<a> CREATOR = new 1();
    public long cateid;
    public String catename;
    public String imgurl;

    public a(long cateid, String catename, String imgurl) {
        this.cateid = cateid;
        this.catename = catename;
        this.imgurl = imgurl;
    }

    public a(Parcel source) {
        this.cateid = source.readLong();
        this.catename = source.readString();
        this.imgurl = source.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.cateid);
        dest.writeString(this.catename);
        dest.writeString(this.imgurl);
    }
}
