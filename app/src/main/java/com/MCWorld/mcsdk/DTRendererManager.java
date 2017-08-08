package com.MCWorld.mcsdk;

import org.apache.tools.ant.DirectoryScanner;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

public class DTRendererManager {

    public static class a {
        private final int rendererId;

        private a(int rendererId) {
            this.rendererId = rendererId;
        }

        public b dw(String name) {
            if (d.d(this.rendererId, name)) {
                return new b(this.rendererId, name);
            }
            throw new RuntimeException("The model part " + name + DirectoryScanner.DOES_NOT_EXIST_POSTFIX);
        }
    }

    public static class b {
        private String modelPartName;
        private int rendererId;
        private float textureHeight;
        private float textureWidth;
        private int textureX;
        private int textureY;
        private boolean transparent;

        private b(int rendererId, String modelPartName) {
            this.textureWidth = 32.0f;
            this.textureHeight = 64.0f;
            this.rendererId = rendererId;
            this.modelPartName = modelPartName;
        }

        public b U(int textureX, int textureY) {
            return d(textureX, textureY, false);
        }

        public b d(int textureX, int textureY, boolean transparent) {
            this.textureX = textureX;
            this.textureY = textureY;
            this.transparent = transparent;
            return this;
        }

        public void addBox(float xOffset, float yOffset, float zOffset, int width, int height, int depth) {
            addBox(xOffset, yOffset, zOffset, width, height, depth, 0.0f);
        }

        public void addBox(float xOffset, float yOffset, float zOffset, int width, int height, int depth, float scale) {
            try {
                d.a(this.rendererId, this.modelPartName, xOffset, yOffset, zOffset, width, height, depth, scale, this.textureX, this.textureY, this.transparent, this.textureWidth, this.textureHeight);
            } catch (Exception e) {
            }
        }

        public b Cj() {
            d.c(this.rendererId, this.modelPartName);
            return this;
        }

        public b b(float width, float height) {
            this.textureWidth = width;
            this.textureHeight = height;
            return this;
        }

        public b c(float x, float y, float z) {
            d.a(this.rendererId, this.modelPartName, x, y, z);
            return this;
        }
    }

    public static class c {
        private final int rendererId;

        public c(int id) {
            this.rendererId = id;
        }

        public int getRenderType() {
            return this.rendererId;
        }

        public a Ck() {
            return new a(this.rendererId);
        }
    }

    public static native int nativeCreateHumanoidRenderer();

    public static native void nativeModelAddBox(int i, String str, float f, float f2, float f3, int i2, int i3, int i4, float f4, int i5, int i6, boolean z, float f5, float f6);

    public static native void nativeModelClear(int i, String str);

    public static native boolean nativeModelPartExists(int i, String str);

    public static native void nativeModelSetRotationPoint(int i, String str, float f, float f2, float f3);

    public static void defineClasses(Scriptable scope) throws Exception {
        ScriptableObject.defineClass(scope, NativeRendererApi.class);
    }
}
