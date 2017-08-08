package com.MCWorld.framework.base.widget.hlistview.util;

import android.os.Build.VERSION;
import android.util.Log;
import android.view.View;
import com.MCWorld.framework.base.widget.hlistview.util.v14.ViewHelper14;
import com.MCWorld.framework.base.widget.hlistview.util.v16.ViewHelper16;

public class ViewHelperFactory {
    private static final String LOG_TAG = "ViewHelper";

    public static abstract class ViewHelper {
        protected View view;

        public abstract boolean isHardwareAccelerated();

        public abstract void postOnAnimation(Runnable runnable);

        public abstract void setScrollX(int i);

        protected ViewHelper(View view) {
            this.view = view;
        }
    }

    public static class ViewHelperDefault extends ViewHelper {
        public ViewHelperDefault(View view) {
            super(view);
        }

        public void postOnAnimation(Runnable action) {
            this.view.post(action);
        }

        public void setScrollX(int value) {
            Log.d(ViewHelperFactory.LOG_TAG, "setScrollX: " + value);
            this.view.scrollTo(value, this.view.getScrollY());
        }

        public boolean isHardwareAccelerated() {
            return false;
        }
    }

    public static final ViewHelper create(View view) {
        int version = VERSION.SDK_INT;
        if (version >= 16) {
            return new ViewHelper16(view);
        }
        if (version >= 14) {
            return new ViewHelper14(view);
        }
        return new ViewHelperDefault(view);
    }
}
