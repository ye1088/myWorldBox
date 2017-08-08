package hlx.module.resources;

import android.os.Parcel;
import android.os.Parcelable;

import com.MCWorld.module.b;
import java.util.ArrayList;

/* compiled from: ResourceSubjectInfo */
public class d extends b implements Parcelable {
    public static final Creator<d> CREATOR = new Creator<d>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return bJ(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return nq(i);
        }

        public d bJ(Parcel source) {
            return new d(source);
        }

        public d[] nq(int size) {
            return new d[size];
        }
    };
    public ArrayList<a> topList = new ArrayList();

    /* compiled from: ResourceSubjectInfo */
    public static class a implements Parcelable {
        public static final Creator<a> CREATOR = new Creator<a>() {
            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return bK(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return nr(i);
            }

            public a[] nr(int size) {
                return new a[size];
            }

            public a bK(Parcel source) {
                return new a(source);
            }
        };
        public String icon;
        public int id;
        public String name;

        public a(Parcel source) {
            this.id = source.readInt();
            this.icon = source.readString();
            this.name = source.readString();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.icon);
            dest.writeString(this.name);
        }
    }

    public d(Parcel source) {
        source.readTypedList(this.topList, a.CREATOR);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.topList);
    }
}
