package com.MCWorld.framework.base.widget.status;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class Statement implements Parcelable {
    public int background;
    public int clickableId;
    public int generalImg;
    public int generalSubtitle;
    public int generalSubtitleBackground;
    public int generalSubtitleColor;
    public int generalSubtitleSize;
    public Size gerneralImgSize;
    public int layoutId;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.layoutId);
        dest.writeInt(this.clickableId);
        dest.writeInt(this.background);
        dest.writeInt(this.generalImg);
        dest.writeParcelable(this.gerneralImgSize, flags);
        dest.writeInt(this.generalSubtitle);
        dest.writeInt(this.generalSubtitleSize);
        dest.writeInt(this.generalSubtitleColor);
        dest.writeInt(this.generalSubtitleBackground);
    }

    protected Statement(Parcel in) {
        this.layoutId = in.readInt();
        this.clickableId = in.readInt();
        this.background = in.readInt();
        this.generalImg = in.readInt();
        this.gerneralImgSize = (Size) in.readParcelable(Size.class.getClassLoader());
        this.generalSubtitle = in.readInt();
        this.generalSubtitleSize = in.readInt();
        this.generalSubtitleColor = in.readInt();
        this.generalSubtitleBackground = in.readInt();
    }
}
