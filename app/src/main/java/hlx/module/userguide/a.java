package hlx.module.userguide;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.huluxia.module.b;
import java.util.ArrayList;
import java.util.List;

/* compiled from: UserGuideInfo */
public class a extends b {
    public static final Creator<a> CREATOR = new 1();
    public List<a> list = new ArrayList();

    /* compiled from: UserGuideInfo */
    public static class a implements Parcelable {
        public static final Creator<a> CREATOR = new 1();
        public long id;
        public String imgUrl;
        public int seq;

        public a(Parcel source) {
            this.id = source.readLong();
            this.seq = source.readInt();
            this.imgUrl = source.readString();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.id);
            dest.writeInt(this.seq);
            dest.writeString(this.imgUrl);
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
