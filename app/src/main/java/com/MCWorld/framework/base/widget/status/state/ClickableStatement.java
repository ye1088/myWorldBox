package com.MCWorld.framework.base.widget.status.state;

import android.os.Parcel;
import com.MCWorld.framework.base.widget.status.Statement;

public abstract class ClickableStatement extends Statement {
    public int buttonBackground;
    public int buttonText;
    public int buttonTextColor;
    public int buttonTextSize;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(this.buttonText);
        dest.writeInt(this.buttonTextSize);
        dest.writeInt(this.buttonTextColor);
        dest.writeInt(this.buttonBackground);
    }

    protected ClickableStatement(Parcel in) {
        super(in);
        this.buttonText = in.readInt();
        this.buttonTextSize = in.readInt();
        this.buttonTextColor = in.readInt();
        this.buttonBackground = in.readInt();
    }
}
