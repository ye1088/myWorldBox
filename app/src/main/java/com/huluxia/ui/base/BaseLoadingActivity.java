package com.huluxia.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import com.huluxia.ui.base.BaseLoadingLayout.b;

public abstract class BaseLoadingActivity extends BaseActivity implements b {
    private BaseLoadingLayout aIv;

    public abstract void onRetryClick(View view);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.aIv = new BaseLoadingLayout(this);
        this.aIv.setRetryClickListener(this);
    }

    public void setContentView(int layoutResID) {
        this.aIv.addView(LayoutInflater.from(this).inflate(layoutResID, null));
        setContentView(this.aIv);
    }

    public void Fy() {
        this.aIv.setCurrentPage(0);
    }

    public void Fz() {
        this.aIv.setCurrentPage(1);
    }

    public void FA() {
        this.aIv.setCurrentPage(2);
    }

    public int getCurrentPage() {
        return this.aIv.getCurrentPage();
    }
}
