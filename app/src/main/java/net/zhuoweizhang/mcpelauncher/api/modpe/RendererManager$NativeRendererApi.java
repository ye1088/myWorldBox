package net.zhuoweizhang.mcpelauncher.api.modpe;

import net.zhuoweizhang.mcpelauncher.api.modpe.RendererManager.NativeRenderer;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSStaticFunction;

public class RendererManager$NativeRendererApi extends ScriptableObject {
    @JSStaticFunction
    public static NativeRenderer get(String name) {
        try {
            return new NativeRenderer(Integer.parseInt(name));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @JSStaticFunction
    public static NativeRenderer createHumanoidRenderer() {
        return new NativeRenderer(RendererManager.nativeCreateHumanoidRenderer());
    }

    public String getClassName() {
        return "Renderer";
    }
}
