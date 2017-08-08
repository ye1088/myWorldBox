package com.MCWorld.framework.base.widget.status.state;

import android.os.Parcel;

import com.MCWorld.framework.R;
import com.MCWorld.framework.base.widget.status.Statement;

public class ReloadStatement extends ClickableStatement {
    public static final Creator<Statement> CREATOR = new 1();
    public int buttonText;

    private ReloadStatement(Parcel source) {
        super(source);
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    public static ReloadStatement generateDefault() {
        ReloadStatement statement = new ReloadStatement();
        statement.generalImg = R.drawable.icon_load_error;
        statement.generalSubtitle = R.string.load_error;
        statement.generalSubtitleSize = 18;
        statement.generalSubtitleColor = R.color.black_bb;
        statement.buttonText = R.string.click_button_reload;
        statement.buttonBackground = R.drawable.bg_corner_rect_red_selector;
        statement.buttonTextColor = R.color.white;
        statement.buttonTextSize = 14;
        return statement;
    }
}
