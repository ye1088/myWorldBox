package com.MCWorld.mconline.gameloc.http;

import android.os.Parcel;

import java.util.ArrayList;
import java.util.List;

/* compiled from: ResourceInfo */
public class e extends b {
    public static final Creator<e> CREATOR = new Creator<e>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return av(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return hL(i);
        }

        public e av(Parcel source) {
            return new e(source);
        }

        public e[] hL(int size) {
            return new e[size];
        }
    };
    public List<GameInfo> gameapps;

    public e() {
        this.gameapps = new ArrayList();
        this.gameapps = new ArrayList();
    }

    public e(Parcel source) {
        super(source);
        this.gameapps = new ArrayList();
        source.readTypedList(this.gameapps, GameInfo.CREATOR);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.gameapps);
    }

    public String toString() {
        return "ResourceInfo{gameapps=" + this.gameapps + '}';
    }
}
