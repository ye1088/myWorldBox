package hlx.module.resources;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* compiled from: ServerCateItem */
public class f implements Parcelable {
    public static final Creator<f> CREATOR = new Creator<f>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return bM(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return nt(i);
        }

        public f bM(Parcel in) {
            return new f(in);
        }

        public f[] nt(int size) {
            return new f[size];
        }
    };
    public String categoryName;
    public long id;
    public String imageUrl;

    public f(Parcel source) {
        this.id = source.readLong();
        this.categoryName = source.readString();
        this.imageUrl = source.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.categoryName);
        dest.writeString(this.imageUrl);
    }
}
