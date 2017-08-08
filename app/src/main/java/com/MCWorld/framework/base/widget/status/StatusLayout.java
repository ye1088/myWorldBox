package com.MCWorld.framework.base.widget.status;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.MCWorld.framework.R;
import com.MCWorld.framework.R$string;

public class StatusLayout extends RelativeLayout {
    private View mLoadingContainer;
    private TextView mLoadingMore;
    private View mProgress;

    public StatusLayout(Context context) {
        super(context);
    }

    public StatusLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StatusLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    private void init() {
        addLoadingFooter();
        addStatus();
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void addLoadingFooter() {
        View status = LayoutInflater.from(getContext()).inflate(R.layout.layout_loading_more, null);
        LayoutParams params = new LayoutParams(-1, -2);
        params.addRule(12);
        status.setVisibility(8);
        addView(status, getChildCount(), params);
        this.mLoadingMore = (TextView) findViewById(R.id.loading_text);
        this.mLoadingContainer = findViewById(R.id.loading_more);
        this.mProgress = findViewById(R.id.loading_progress);
    }

    private void addStatus() {
        addView((RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.layout_status_container, null), getChildCount(), new LayoutParams(-1, -1));
    }

    public void showErrorPage(int tips, OnClickListener listener) {
        if (this.mLoadingMore != null) {
            if (tips <= 0) {
                tips = R$string.click_or_pull_refresh;
            }
            this.mLoadingMore.setText(getContext().getString(tips));
            this.mLoadingContainer.setOnClickListener(listener);
            this.mProgress.setVisibility(8);
            this.mLoadingContainer.setVisibility(0);
        }
    }

    public void showLoadMore() {
        this.mLoadingMore.setText(getContext().getString(R$string.loading));
        this.mProgress.setVisibility(0);
        this.mLoadingContainer.setOnClickListener(null);
        this.mLoadingContainer.setVisibility(0);
    }

    public void hideLoadMore() {
        this.mLoadingContainer.setVisibility(8);
    }

    static ViewGroup wrap(View child) {
        return wrap(child, new LayoutParams(-1, -1));
    }

    static ViewGroup wrap(View child, ViewGroup.LayoutParams lp) {
        StatusLayout wrapper = new StatusLayout(child.getContext());
        ViewGroup.LayoutParams childParams = child.getLayoutParams();
        if (childParams != null) {
            wrapper.setLayoutParams(childParams);
        }
        child.setLayoutParams(lp);
        wrapper.addView(child);
        wrapper.init();
        return wrapper;
    }
}
