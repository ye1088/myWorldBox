package com.MCWorld.framework.base.widget.status.state;

import android.os.Parcel;

import com.MCWorld.framework.R;
import com.MCWorld.framework.base.widget.status.Statement;
import com.MCWorld.framework.base.widget.status.Statement.Size;

public class LoadingStatement extends Statement {
    public static final Creator<Statement> CREATOR = new 1();

    private LoadingStatement(Parcel source) {
        super(source);
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    public static LoadingStatement generateDefault() {
        LoadingStatement statement = new LoadingStatement();
        statement.generalImg = R.drawable.common_loading3;
        statement.generalSubtitle = R.string.loading;
        statement.gerneralImgSize = new Size(20, 20);
        return statement;
    }
}
