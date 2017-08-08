package com.MCWorld.framework.base.widget.status.state;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.MCWorld.framework.base.widget.status.Statement;

class LoadingStatement$1 implements Creator<Statement> {
    LoadingStatement$1() {
    }

    public LoadingStatement createFromParcel(Parcel source) {
        return new LoadingStatement(source, null);
    }

    public LoadingStatement[] newArray(int size) {
        return new LoadingStatement[size];
    }
}
