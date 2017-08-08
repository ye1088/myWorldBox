package com.MCWorld.framework.base.widget.status.state;

import android.os.Parcel;

import com.MCWorld.framework.R;
import com.MCWorld.framework.R$string;
import com.MCWorld.framework.base.widget.status.Statement;

public class NetworkErrorStatement extends ClickableStatement {
    public static final Creator<Statement> CREATOR = new Creator<Statement>() {
        public NetworkErrorStatement createFromParcel(Parcel source) {
            return new NetworkErrorStatement(source);
        }

        public NetworkErrorStatement[] newArray(int size) {
            return new NetworkErrorStatement[size];
        }
    };
    public int buttonText;

    private NetworkErrorStatement() {
    }

    private NetworkErrorStatement(Parcel source) {
        super(source);
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    public static NetworkErrorStatement generateDefault() {
        NetworkErrorStatement statement = new NetworkErrorStatement();
        statement.generalImg = R.drawable.icon_no_network;
        statement.generalSubtitle = R$string.no_network;
        statement.generalSubtitleSize = 18;
        statement.generalSubtitleColor = R.color.black_bb;
        statement.buttonText = R$string.click_button_reload;
        statement.buttonBackground = R.drawable.bg_corner_rect_red_selector;
        statement.buttonTextColor = R.color.white;
        statement.buttonTextSize = 14;
        return statement;
    }
}
