package com.MCWorld.ui.base;

import android.os.Bundle;
import android.support.annotation.z;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.MCWorld.bbs.b.n;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.simple.colorful.a;
import com.simple.colorful.d;

public class BaseThemeFragment extends BaseFragment {
    protected a aIM;
    private int aIN = 0;
    private CallbackHandler aIO = new CallbackHandler(this) {
        final /* synthetic */ BaseThemeFragment aIP;

        {
            this.aIP = this$0;
        }

        @MessageHandler(message = 0)
        public void onRecvThemeChanged(int themeMode) {
            this.aIP.kq(themeMode);
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected int AN() {
        return n.HtAppTheme;
    }

    protected int AO() {
        return n.HtAppTheme_Night;
    }

    @z
    public View onCreateView(LayoutInflater inflater, @z ViewGroup container, @z Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        EventNotifyCenter.add(com.MCWorld.pref.a.class, this.aIO);
        return view;
    }

    public void onActivityCreated(@z Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.aIN = d.Lo();
        if (this.aIM == null) {
            a.a builder = new a.a((Fragment) this);
            a(builder);
            this.aIM = builder.Rz();
            EventNotifyCenter.add(com.MCWorld.pref.a.class, this.aIO);
        }
    }

    public void onDestroyView() {
        super.onDestroy();
        EventNotifyCenter.remove(this.aIO);
    }

    protected void a(a.a builder) {
    }

    private void kq(int themeMode) {
        if (themeMode != this.aIN) {
            this.aIN = themeMode;
            int themeId = this.aIN == 1 ? AO() : AN();
            this.aIM.setTheme(themeId);
            kj(themeId);
        }
    }

    protected void kj(int themeId) {
    }
}
