package com.MCWorld.framework.base.image;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView.ScaleType;
import com.MCWorld.framework.AppConfig;
import com.MCWorld.framework.base.utils.DoNotStrip;
import com.MCWorld.video.recorder.b;

public class Config implements Parcelable {
    public static final Creator<Config> CREATOR = new 1();
    public static final int DEFAULT_FADE_DURATION = 250;
    public boolean autoAnimated;
    public int brightness = AppConfig.getInstance().getBrightness();
    public int errorHolder = AppConfig.getInstance().getErrHolderImg();
    public int fadeDuration = DEFAULT_FADE_DURATION;
    public boolean highQualityAnimated;
    public int placeHolder = AppConfig.getInstance().getPlaceHolderImg();
    public ScaleType scaleType;
    public int targetHeight = b.bpd;
    public int targetWidth = b.bpd;

    @DoNotStrip
    public enum NetFormat {
        FORMAT_160,
        FORMAT_80
    }

    public static Config defaultConfig() {
        return new Config();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        byte b;
        byte b2 = (byte) 1;
        dest.writeInt(this.targetWidth);
        dest.writeInt(this.targetHeight);
        dest.writeInt(this.scaleType == null ? -1 : this.scaleType.ordinal());
        dest.writeInt(this.placeHolder);
        dest.writeInt(this.errorHolder);
        dest.writeInt(this.fadeDuration);
        dest.writeInt(this.brightness);
        if (this.autoAnimated) {
            b = (byte) 1;
        } else {
            b = (byte) 0;
        }
        dest.writeByte(b);
        if (!this.highQualityAnimated) {
            b2 = (byte) 0;
        }
        dest.writeByte(b2);
    }

    protected Config(Parcel in) {
        boolean z;
        boolean z2 = true;
        this.targetWidth = in.readInt();
        this.targetHeight = in.readInt();
        int tmpScaleType = in.readInt();
        this.scaleType = tmpScaleType == -1 ? null : ScaleType.values()[tmpScaleType];
        this.placeHolder = in.readInt();
        this.errorHolder = in.readInt();
        this.fadeDuration = in.readInt();
        this.brightness = in.readInt();
        if (in.readByte() != (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.autoAnimated = z;
        if (in.readByte() == (byte) 0) {
            z2 = false;
        }
        this.highQualityAnimated = z2;
    }
}
