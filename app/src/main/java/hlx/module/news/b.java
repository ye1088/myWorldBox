package hlx.module.news;

import android.os.Parcel;
import android.os.Parcelable;

import com.MCWorld.module.a;
import java.util.ArrayList;
import java.util.List;

/* compiled from: NewsHeaderInfo */
public class b extends a implements Parcelable {
    public static final Creator<b> CREATOR = new Creator<b>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return bD(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return nk(i);
        }

        public b bD(Parcel source) {
            return new b(source);
        }

        public b[] nk(int size) {
            return new b[0];
        }
    };
    public List<a> bigList = new ArrayList();
    public List<a> bottomList = new ArrayList();
    public List<a> topList = new ArrayList();

    public b(Parcel source) {
        source.readTypedList(this.bigList, a.CREATOR);
        source.readTypedList(this.topList, a.CREATOR);
        source.readTypedList(this.bottomList, a.CREATOR);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.bigList);
        dest.writeTypedList(this.topList);
        dest.writeTypedList(this.bottomList);
    }
}
