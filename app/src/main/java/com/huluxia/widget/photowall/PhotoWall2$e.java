package com.huluxia.widget.photowall;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.view.View.BaseSavedState;
import com.huluxia.module.picture.b;
import java.util.ArrayList;

protected class PhotoWall2$e extends BaseSavedState {
    public static final Creator<PhotoWall2$e> CREATOR = new Creator<PhotoWall2$e>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return bB(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return ml(i);
        }

        public PhotoWall2$e bB(Parcel in) {
            return new PhotoWall2$e(in);
        }

        public PhotoWall2$e[] ml(int size) {
            return new PhotoWall2$e[size];
        }
    };
    int albumsColumn;
    boolean enableAdd;
    boolean enableDel;
    float gridRatio;
    boolean inGridMode;
    ArrayList<b> photo;
    boolean showText;

    private PhotoWall2$e(Parcel in) {
        boolean z;
        boolean z2 = true;
        super(in);
        this.photo = new ArrayList();
        in.readTypedList(this.photo, b.CREATOR);
        this.showText = in.readInt() != 0;
        if (in.readInt() != 0) {
            z = true;
        } else {
            z = false;
        }
        this.enableAdd = z;
        if (in.readInt() != 0) {
            z = true;
        } else {
            z = false;
        }
        this.enableDel = z;
        this.albumsColumn = in.readInt();
        this.gridRatio = in.readFloat();
        if (in.readInt() == 0) {
            z2 = false;
        }
        this.inGridMode = z2;
    }

    public PhotoWall2$e(Parcelable superState) {
        super(superState);
        this.photo = new ArrayList();
    }

    public void writeToParcel(Parcel out, int flags) {
        int i;
        int i2 = 1;
        super.writeToParcel(out, flags);
        out.writeTypedList(this.photo);
        out.writeInt(this.showText ? 1 : 0);
        if (this.enableAdd) {
            i = 1;
        } else {
            i = 0;
        }
        out.writeInt(i);
        if (this.enableDel) {
            i = 1;
        } else {
            i = 0;
        }
        out.writeInt(i);
        out.writeInt(this.albumsColumn);
        out.writeFloat(this.gridRatio);
        if (!this.inGridMode) {
            i2 = 0;
        }
        out.writeInt(i2);
    }
}
