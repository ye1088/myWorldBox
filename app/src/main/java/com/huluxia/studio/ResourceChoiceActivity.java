package com.huluxia.studio;

import android.content.Context;
import android.os.Bundle;
import com.huluxia.framework.R;
import com.huluxia.ui.base.HTBaseLoadingActivity;
import com.simple.colorful.a.a;

public class ResourceChoiceActivity extends HTBaseLoadingActivity {
    private static final String TAG = "ResourceChoiceActivity";
    protected Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.fragment_studio_common);
        this.mContext = this;
        initView();
        EQ();
        EW();
        Fy();
    }

    private void initView() {
    }

    private void EQ() {
    }

    private void EW() {
    }

    protected void EX() {
        super.EX();
        EW();
    }

    private void reload() {
    }

    private void EY() {
    }

    protected void kj(int themeId) {
        super.kj(themeId);
    }

    protected void a(a builder) {
        super.a(builder);
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }
}
