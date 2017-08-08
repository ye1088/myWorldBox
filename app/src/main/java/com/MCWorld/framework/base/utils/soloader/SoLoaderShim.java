package com.MCWorld.framework.base.utils.soloader;

public class SoLoaderShim {
    private static volatile Handler sHandler = new DefaultHandler();

    public interface Handler {
        void loadLibrary(String str);
    }

    public static class DefaultHandler implements Handler {
        public void loadLibrary(String libraryName) {
            System.loadLibrary(libraryName);
        }
    }

    public static void setHandler(Handler handler) {
        if (handler == null) {
            throw new NullPointerException("Handler cannot be null");
        }
        sHandler = handler;
    }

    public static void loadLibrary(String libraryName) {
        sHandler.loadLibrary(libraryName);
    }

    public static void setInTestMode() {
        setHandler(new Handler() {
            public void loadLibrary(String libraryName) {
            }
        });
    }
}
