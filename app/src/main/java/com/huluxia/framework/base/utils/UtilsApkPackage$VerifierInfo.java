package com.huluxia.framework.base.utils;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.security.PublicKey;

public class UtilsApkPackage$VerifierInfo implements Parcelable {
    public final Creator<UtilsApkPackage$VerifierInfo> CREATOR;
    public final String packageName;
    public final PublicKey publicKey;

    public UtilsApkPackage$VerifierInfo(String packageName, PublicKey publicKey) {
        this.CREATOR = new Creator<UtilsApkPackage$VerifierInfo>() {
            public UtilsApkPackage$VerifierInfo createFromParcel(Parcel source) {
                return new UtilsApkPackage$VerifierInfo(source);
            }

            public UtilsApkPackage$VerifierInfo[] newArray(int size) {
                return new UtilsApkPackage$VerifierInfo[size];
            }
        };
        if (packageName == null || packageName.length() == 0) {
            throw new IllegalArgumentException("packageName must not be null or empty");
        } else if (publicKey == null) {
            throw new IllegalArgumentException("publicKey must not be null");
        } else {
            this.packageName = packageName;
            this.publicKey = publicKey;
        }
    }

    private UtilsApkPackage$VerifierInfo(Parcel source) {
        this.CREATOR = /* anonymous class already generated */;
        this.packageName = source.readString();
        this.publicKey = (PublicKey) source.readSerializable();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.packageName);
        dest.writeSerializable(this.publicKey);
    }
}
