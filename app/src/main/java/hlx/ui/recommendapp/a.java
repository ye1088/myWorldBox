package hlx.ui.recommendapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.huluxia.module.b;
import java.util.ArrayList;
import java.util.List;

/* compiled from: RecommendAppMoreInfo */
public class a extends b {
    public static final Creator<a> CREATOR = new 1();
    public ArrayList<a> adList;

    /* compiled from: RecommendAppMoreInfo */
    public static class a implements Parcelable {
        public static final Creator<a> CREATOR = new 1();
        public String appdesc;
        public String appdownurl;
        public String applogo;
        public String appsize;
        public String apptitle;
        public String appversion;
        public String categoryname;
        public long downCount;
        public String filename;
        public int id;
        public String md5;
        public String packname;
        public List<String> resourceList;
        public int versionCode;

        public a(int id, String packname, String md5) {
            this.id = id;
            this.packname = packname;
            this.md5 = md5;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.apptitle);
            dest.writeString(this.appdesc);
            dest.writeString(this.appsize);
            dest.writeString(this.applogo);
            dest.writeString(this.appversion);
            dest.writeString(this.categoryname);
            dest.writeString(this.packname);
            dest.writeInt(this.versionCode);
            dest.writeString(this.appdownurl);
            dest.writeString(this.md5);
            dest.writeString(this.filename);
            dest.writeLong(this.downCount);
            dest.writeStringList(this.resourceList);
        }

        protected a(Parcel in) {
            this.id = in.readInt();
            this.apptitle = in.readString();
            this.appdesc = in.readString();
            this.appsize = in.readString();
            this.applogo = in.readString();
            this.appversion = in.readString();
            this.categoryname = in.readString();
            this.packname = in.readString();
            this.versionCode = in.readInt();
            this.appdownurl = in.readString();
            this.md5 = in.readString();
            this.filename = in.readString();
            this.downCount = in.readLong();
            this.resourceList = in.createStringArrayList();
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.adList);
    }

    protected a(Parcel in) {
        super(in);
        this.adList = in.createTypedArrayList(a.CREATOR);
    }
}
