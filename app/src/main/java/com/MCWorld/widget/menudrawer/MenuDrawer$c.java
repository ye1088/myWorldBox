package com.MCWorld.widget.menudrawer;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View.BaseSavedState;

class MenuDrawer$c extends BaseSavedState {
    public static final Creator<MenuDrawer$c> CREATOR = new Creator<MenuDrawer$c>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return bz(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return mg(i);
        }

        public MenuDrawer$c bz(Parcel in) {
            return new MenuDrawer$c(in);
        }

        public MenuDrawer$c[] mg(int size) {
            return new MenuDrawer$c[size];
        }
    };
    Bundle mState;

    public MenuDrawer$c(Parcelable superState) {
        super(superState);
    }

    public MenuDrawer$c(Parcel in) {
        super(in);
        this.mState = in.readBundle();
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeBundle(this.mState);
    }
}
