package com.huluxia.studio;

import android.content.Context;
import android.os.Bundle;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huluxia.framework.R;
import com.huluxia.studio.adapter.PastRankAdapter;
import com.huluxia.studio.utils.b;
import com.huluxia.ui.base.HTBaseLoadingActivity;
import com.simple.colorful.a.a;
import com.simple.colorful.setter.j;
import com.simple.colorful.setter.k;

public class PastRankActivity extends HTBaseLoadingActivity {
    private static final String TAG = "PastRankActivity";
    protected PullToRefreshListView aEq;
    protected PastRankAdapter aEr;
    private Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.fragment_studio_common);
        this.mContext = this;
        initView();
        EQ();
    }

    private void initView() {
        this.aEq = (PullToRefreshListView) findViewById(R.id.lv_list_view);
    }

    private void EQ() {
        ej("往期");
        this.aIs.setVisibility(8);
        this.aEq.setPullToRefreshEnabled(false);
        this.aEr = new PastRankAdapter(this.mContext);
        this.aEq.setAdapter(this.aEr);
        this.aEr.c(new b().aGU, true);
    }

    protected void kj(int themeId) {
        super.kj(themeId);
    }

    protected void a(a builder) {
        super.a(builder);
        k setter = new j(this.aEq);
        setter.a(this.aEr);
        builder.a(setter).aY(R.id.root_view, R.attr.backgroundDefault);
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }
}
