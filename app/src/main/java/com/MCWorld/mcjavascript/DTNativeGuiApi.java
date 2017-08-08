package com.MCWorld.mcjavascript;

import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSStaticFunction;

public class DTNativeGuiApi extends ScriptableObject {
    @JSStaticFunction
    public static int getScreenHeight() {
        return 0;
    }

    @JSStaticFunction
    public static int getScreenWidth() {
        return 0;
    }

    public String getClassName() {
        return "Gui";
    }
}
