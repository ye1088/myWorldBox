package com.huluxia.ui.status.page;

import android.os.Bundle;
import android.support.annotation.z;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.huluxia.bbs.b.c;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.n;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.widget.status.state.NetworkErrorFragment;
import com.huluxia.framework.base.widget.status.state.NetworkErrorStatement;
import com.simple.colorful.a;
import com.simple.colorful.d;

public class McNetworkErrorFragment extends NetworkErrorFragment {
    protected a aIM;
    private int aIN = 0;
    private CallbackHandler aIO = new 1(this);

    public static McNetworkErrorFragment Kj() {
        return a(null);
    }

    public static McNetworkErrorFragment a(NetworkErrorStatement statement) {
        Bundle bundle = new Bundle();
        if (statement == null) {
            statement = NetworkErrorStatement.generateDefault();
        }
        bundle.putParcelable("STATEMENT", statement);
        McNetworkErrorFragment fragment = new McNetworkErrorFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @z
    public View onCreateView(LayoutInflater inflater, @z ViewGroup container, @z Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        EventNotifyCenter.add(com.huluxia.pref.a.class, this.aIO);
        return view;
    }

    public void onActivityCreated(@z Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.aIN = d.Lo();
        if (this.aIM == null) {
            a.a builder = new a.a(this);
            a(builder);
            this.aIM = builder.Rz();
            EventNotifyCenter.add(com.huluxia.pref.a.class, this.aIO);
        }
    }

    public void onDestroyView() {
        super.onDestroy();
        EventNotifyCenter.remove(this.aIO);
    }

    protected void a(a.a builder) {
        builder.aY(g.clickable_container, c.backgroundDefault).ba(g.error_text, c.textColorTopicCredit).bc(g.net_err_icon, c.drawableLoadingFailed);
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
