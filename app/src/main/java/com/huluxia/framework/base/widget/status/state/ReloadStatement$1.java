package com.huluxia.framework.base.widget.status.state;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.huluxia.framework.base.widget.status.Statement;

class ReloadStatement$1 implements Creator<Statement> {
    ReloadStatement$1() {
    }

    public ReloadStatement createFromParcel(Parcel source) {
        return new ReloadStatement(source, null);
    }

    public ReloadStatement[] newArray(int size) {
        return new ReloadStatement[size];
    }
}
