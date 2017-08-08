package hlx.module.resources;

import android.os.Parcel;
import android.os.Parcelable;

import com.MCWorld.module.a;
import java.util.ArrayList;

/* compiled from: ServerCateInfo */
public class e extends a implements Parcelable {
    public static final Creator<e> CREATOR = new Creator<e>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return bL(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return ns(i);
        }

        public e bL(Parcel source) {
            return new e(source);
        }

        public e[] ns(int size) {
            return new e[size];
        }
    };
    public ArrayList<f> categorylist = new ArrayList();

    public e(Parcel source) {
        source.readTypedList(this.categorylist, f.CREATOR);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.categorylist);
    }
}
