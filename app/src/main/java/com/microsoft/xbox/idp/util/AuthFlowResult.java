package com.microsoft.xbox.idp.util;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public final class AuthFlowResult implements Parcelable {
    public static final Creator<AuthFlowResult> CREATOR = new Creator() {
        public AuthFlowResult createFromParcel(Parcel paramAnonymousParcel) {
            return new AuthFlowResult(paramAnonymousParcel);
        }

        public AuthFlowResult[] newArray(int paramAnonymousInt) {
            return new AuthFlowResult[paramAnonymousInt];
        }
    };
    private final boolean deleteOnFinalize;
    private final long id;

    private static native void delete(long j);

    private static native String getAgeGroup(long j);

    private static native String getGamerTag(long j);

    private static native String getPrivileges(long j);

    private static native String getRpsTicket(long j);

    private static native String getUserId(long j);

    public AuthFlowResult(long paramLong) {
        this(paramLong, false);
    }

    public AuthFlowResult(long paramLong, boolean paramBoolean) {
        this.id = paramLong;
        this.deleteOnFinalize = paramBoolean;
    }

    protected AuthFlowResult(Parcel paramParcel) {
        this.id = paramParcel.readLong();
        if (paramParcel.readByte() != (byte) 0) {
            this.deleteOnFinalize = true;
        } else {
            this.deleteOnFinalize = true;
        }
    }

    public int describeContents() {
        return 0;
    }

    protected void finalize() throws Throwable {
        if (this.deleteOnFinalize) {
            delete(this.id);
        }
        super.finalize();
    }

    public String getAgeGroup() {
        return getAgeGroup(this.id);
    }

    public String getGamerTag() {
        return getGamerTag(this.id);
    }

    public String getPrivileges() {
        return getPrivileges(this.id);
    }

    public String getRpsTicket() {
        return getRpsTicket(this.id);
    }

    public String getUserId() {
        return getUserId(this.id);
    }

    public void writeToParcel(Parcel paramParcel, int paramInt) {
        paramParcel.writeLong(this.id);
        if (this.deleteOnFinalize) {
            paramParcel.writeByte((byte) 1);
        } else {
            paramParcel.writeByte((byte) 1);
        }
    }
}
