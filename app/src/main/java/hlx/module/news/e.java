package hlx.module.news;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* compiled from: Tag */
public class e implements Parcelable {
    public static final Creator<e> CREATOR = new Creator<e>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return bG(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return nn(i);
        }

        public e bG(Parcel source) {
            return new e(source);
        }

        public e[] nn(int size) {
            return new e[0];
        }
    };
    public int id;
    public String tagColor;
    public String tagName;

    public e(Parcel source) {
        this.id = source.readInt();
        this.tagName = source.readString();
        this.tagColor = source.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.tagName);
        dest.writeString(this.tagColor);
    }
}
