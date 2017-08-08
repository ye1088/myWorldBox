package com.MCWorld.ui.status.page;

import android.os.Bundle;
import android.support.annotation.z;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.MCWorld.bbs.b.c;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.n;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.widget.status.state.LoadingFragment;
import com.MCWorld.framework.base.widget.status.state.LoadingStatement;
import com.simple.colorful.a;
import com.simple.colorful.d;

public class McLoadingFragment extends LoadingFragment {
    protected a aIM;
    private int aIN = 0;
    private CallbackHandler aIO = new 1(this);

    public static McLoadingFragment Ki() {
        return a(null);
    }

    public static McLoadingFragment a(LoadingStatement statement) {
        Bundle bundle = new Bundle();
        if (statement == null) {
            statement = LoadingStatement.generateDefault();
        }
        bundle.putParcelable("STATEMENT", statement);
        McLoadingFragment fragment = new McLoadingFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            a.a builder = new a.a(this);
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
        ProgressBar progressBar = getView() != null ? (ProgressBar) getView().findViewById(g.progress) : null;
        if (progressBar != null) {
            builder.a(progressBar, c.drawableLoading, progressBar.getWidth(), progressBar.getHeight());
        }
        builder.aY(g.loadingFragment, c.backgroundDefault);
    }

    private void kq(int themeMode) {
        if (themeMode != this.aIN) {
            this.aIN = themeMode;
            int themeId = this.aIN == 1 ? n.HtAppTheme_Night : n.HtAppTheme;
            this.aIM.setTheme(themeId);
            kj(themeId);
        }
    }

    protected void kj(int themeId) {
    }
}
