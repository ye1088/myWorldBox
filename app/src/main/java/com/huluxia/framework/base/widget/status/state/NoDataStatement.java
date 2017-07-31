package com.huluxia.framework.base.widget.status.state;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.huluxia.framework.R;
import com.huluxia.framework.R$string;
import com.huluxia.framework.base.widget.status.Statement;

public class NoDataStatement extends ClickableStatement {
    public static final Creator<Statement> CREATOR = new Creator<Statement>() {
        public NoDataStatement createFromParcel(Parcel source) {
            return new NoDataStatement(source);
        }

        public NoDataStatement[] newArray(int size) {
            return new NoDataStatement[size];
        }
    };
    public int buttonText;

    private NoDataStatement() {
    }

    private NoDataStatement(Parcel source) {
        super(source);
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    public static NoDataStatement generateDefault() {
        NoDataStatement statement = new NoDataStatement();
        statement.generalImg = R.drawable.icon_no_network;
        statement.generalSubtitle = R$string.no_list_data;
        statement.generalSubtitleSize = 18;
        statement.generalSubtitleColor = R.color.black_bb;
        statement.buttonText = R$string.click_button_reload;
        statement.buttonBackground = R.drawable.bg_corner_rect_red_selector;
        statement.buttonTextColor = R.color.white;
        statement.buttonTextSize = 14;
        return statement;
    }
}
