package hlx.ui.heroslist;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

/* compiled from: HeroRankInfo */
public class c$a implements Parcelable {
    public static final Creator<c$a> CREATOR = new Creator<c$a>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return bQ(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return nx(i);
        }

        public c$a bQ(Parcel source) {
            return new c$a(source);
        }

        public c$a[] nx(int size) {
            return new c$a[size];
        }
    };
    public String cate_id;
    public String createTime;
    public String headImgUrl;
    public int id;
    public int integral;
    public int orderType;
    public int rank;
    public long userID;
    public String userName;

    public c$a(Parcel source) {
        this();
        this.id = source.readInt();
        this.cate_id = source.readString();
        this.integral = source.readInt();
        this.createTime = source.readString();
        this.userName = source.readString();
        this.headImgUrl = source.readString();
        this.userID = source.readLong();
        this.orderType = source.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.cate_id);
        dest.writeInt(this.integral);
        dest.writeString(this.createTime);
        dest.writeString(this.userName);
        dest.writeString(this.headImgUrl);
        dest.writeLong(this.userID);
        dest.writeInt(this.orderType);
    }

    public int getRank() {
        return this.rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
