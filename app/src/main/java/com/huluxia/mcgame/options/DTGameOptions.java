package com.huluxia.mcgame.options;

import com.huluxia.aa.mcspirit;
import com.huluxia.mcgame.g;
import com.huluxia.mcsdk.dtlib.h;
import java.io.Serializable;

public class DTGameOptions implements Serializable {
    public static final String SKIN_TYPE_Alex = "Standard_Alex";
    public static final String SKIN_TYPE_Custom = "Standard_Custom";
    public static final String SKIN_TYPE_HuLuXia = "Standard_HuLuXia";
    public static final String SKIN_TYPE_Steve = "Standard_Steve";
    private static boolean bOpenFloatWindow = false;
    private static boolean bUpdateGameGUISize = false;
    private static float mGameGUISize = 0.0f;
    private static final long serialVersionUID = 1;

    public static boolean isbOpenFloatWindow() {
        return bOpenFloatWindow;
    }

    public static void setbOpenFloatWindow(boolean bOpenFloatWindow) {
        bOpenFloatWindow = bOpenFloatWindow;
    }

    public static boolean isbUpdateGameGUISize() {
        return bUpdateGameGUISize;
    }

    public static void setbUpdateGameGUISize(boolean bUpdateGameGUISize) {
        bUpdateGameGUISize = bUpdateGameGUISize;
    }

    public static float getmGameGUISize() {
        return mGameGUISize;
    }

    public static void setmGameGUISize(float mGameGUISize) {
        setbUpdateGameGUISize(true);
        mGameGUISize = mGameGUISize;
    }

    public static void dtUpdateGUISize() {
        if (h.CW().CX() == 2 && isbUpdateGameGUISize()) {
            mcspirit.u(g.vP(), g.vQ(), getmGameGUISize());
            setbUpdateGameGUISize(false);
        }
    }

    public static void DTTickFunc_AdjustGameOption() {
        dtUpdateGUISize();
    }
}
