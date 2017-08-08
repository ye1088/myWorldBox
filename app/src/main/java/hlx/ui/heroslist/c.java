package hlx.ui.heroslist;

import android.os.Parcel;

import com.MCWorld.module.b;
import java.util.ArrayList;

/* compiled from: HeroRankInfo */
public class c extends b {
    public static final Creator<c> CREATOR = new 1();
    public ArrayList<a> heroRankList;

    public c() {
        this.heroRankList = new ArrayList();
        this.heroRankList = new ArrayList();
    }

    public c(Parcel source) {
        super(source);
        this.heroRankList = new ArrayList();
        source.readTypedList(this.heroRankList, a.CREATOR);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.heroRankList);
    }
}
