package com.huluxia.framework.base.widget;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.view.View.BaseSavedState;

class PagerSlidingIndicator$SavedState extends BaseSavedState {
    public static final Creator<PagerSlidingIndicator$SavedState> CREATOR = new Creator<PagerSlidingIndicator$SavedState>() {
        public PagerSlidingIndicator$SavedState createFromParcel(Parcel in) {
            return new PagerSlidingIndicator$SavedState(in);
        }

        public PagerSlidingIndicator$SavedState[] newArray(int size) {
            return new PagerSlidingIndicator$SavedState[size];
        }
    };
    int currentPosition;

    public PagerSlidingIndicator$SavedState(Parcelable superState) {
        super(superState);
    }

    private PagerSlidingIndicator$SavedState(Parcel in) {
        super(in);
        this.currentPosition = in.readInt();
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.currentPosition);
    }
}
