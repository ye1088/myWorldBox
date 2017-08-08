package com.MCWorld.mcsdk;

import com.MCWorld.mcsdk.DTRendererManager.c;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSStaticFunction;

class DTRendererManager$NativeRendererApi extends ScriptableObject {
    @JSStaticFunction
    public static c get(String name) {
        try {
            return new c(Integer.parseInt(name));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @JSStaticFunction
    public static c createHumanoidRenderer() {
        return new c(d.Cl());
    }

    public String getClassName() {
        return "Renderer";
    }
}
