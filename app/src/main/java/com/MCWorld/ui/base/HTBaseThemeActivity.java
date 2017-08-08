package com.MCWorld.ui.base;

import android.app.Activity;
import android.os.Bundle;
import com.MCWorld.bbs.b.c;
import com.MCWorld.bbs.b.n;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.simple.colorful.a;
import com.simple.colorful.d;

public class HTBaseThemeActivity extends BaseActivity {
    public static int brightness = 0;
    private a aIM;
    private int aIN = 0;
    private CallbackHandler aIO = new CallbackHandler(this) {
        final /* synthetic */ HTBaseThemeActivity aJl;

        {
            this.aJl = this$0;
        }

        @MessageHandler(message = 0)
        public void onRecvThemeChanged(int themeMode) {
            this.aJl.kq(themeMode);
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        if (d.RB()) {
            this.aIN = 1;
            setTheme(AO());
        } else {
            setTheme(AN());
        }
        brightness = d.d(getTheme(), c.valBrightness);
        super.onCreate(savedInstanceState);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && this.aIM == null) {
            a.a builder = new a.a((Activity) this);
            a(builder);
            this.aIM = builder.Rz();
            EventNotifyCenter.add(com.MCWorld.pref.a.class, this.aIO);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.aIO);
    }

    protected void a(a.a builder) {
    }

    protected void kq(int themeMode) {
        j(themeMode, false);
    }

    protected void j(int themeMode, boolean force) {
        if (themeMode != this.aIN || force) {
            this.aIN = themeMode;
            int themeId = this.aIN == 1 ? AO() : AN();
            this.aIM.setTheme(themeId);
            brightness = d.d(getTheme(), c.valBrightness);
            kj(themeId);
        }
    }

    protected void kj(int themeId) {
    }

    protected int AN() {
        return n.HtAppTheme;
    }

    protected int AO() {
        return n.HtAppTheme_Night;
    }
}
