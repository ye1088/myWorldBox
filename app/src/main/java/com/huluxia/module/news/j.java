package com.huluxia.module.news;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.huluxia.module.GameInfo;
import com.huluxia.module.a;
import java.util.ArrayList;
import java.util.List;

/* compiled from: NewsRecommendApp */
public class j extends a {
    public static final Creator<j> CREATOR = new Creator<j>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return aY(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return jL(i);
        }

        public j aY(Parcel source) {
            return new j(source);
        }

        public j[] jL(int size) {
            return new j[size];
        }
    };
    public List<GameInfo> gameapps = new ArrayList();

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.gameapps);
    }

    protected j(Parcel in) {
        super(in);
        this.gameapps = in.createTypedArrayList(GameInfo.CREATOR);
    }
}
