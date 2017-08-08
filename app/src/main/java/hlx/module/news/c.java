package hlx.module.news;

import android.os.Parcel;
import android.os.Parcelable;

import com.MCWorld.module.b;
import java.util.ArrayList;
import java.util.List;

/* compiled from: NewsInfo */
public class c extends b {
    public static final Creator<c> CREATOR = new Creator<c>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return bE(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return nl(i);
        }

        public c bE(Parcel source) {
            return new c(source);
        }

        public c[] nl(int size) {
            return new c[size];
        }
    };
    public List<a> newsList = new ArrayList();

    /* compiled from: NewsInfo */
    public static class a implements Parcelable {
        public static final Creator<a> CREATOR = new Creator<a>() {
            public /* synthetic */ Object createFromParcel(Parcel parcel) {
                return bF(parcel);
            }

            public /* synthetic */ Object[] newArray(int i) {
                return nm(i);
            }

            public a bF(Parcel source) {
                return new a(source);
            }

            public a[] nm(int size) {
                return new a[0];
            }
        };
        public static final int TYPE_POST = 0;
        public static final int TYPE_WEB = 1;
        public String content;
        public int id;
        public String imgurl;
        public String newsurl;
        public long postid;
        public List<e> tags = new ArrayList();
        public String title;
        public String titlecolor;
        public int type;

        public a(Parcel parcel) {
            this.id = parcel.readInt();
            this.type = parcel.readInt();
            this.postid = parcel.readLong();
            this.newsurl = parcel.readString();
            this.imgurl = parcel.readString();
            this.title = parcel.readString();
            this.titlecolor = parcel.readString();
            this.content = parcel.readString();
            parcel.readTypedList(this.tags, e.CREATOR);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeInt(this.type);
            dest.writeLong(this.postid);
            dest.writeString(this.newsurl);
            dest.writeString(this.title);
            dest.writeString(this.titlecolor);
            dest.writeTypedList(this.tags);
        }
    }

    protected c(Parcel in) {
        super(in);
        this.newsList = in.createTypedArrayList(a.CREATOR);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.newsList);
    }
}
