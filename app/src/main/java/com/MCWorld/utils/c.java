package com.MCWorld.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import com.MCWorld.bbs.b.i;

/* compiled from: EndlessListScrollListener */
public class c implements OnScrollListener {
    private a bjV;
    private View bjW;
    private View mFooter;
    private boolean mLastItemVisible;
    private ListView mListView;
    private boolean mLoading = false;
    private ViewGroup mLoadingViewContainer;
    private OnScrollListener mOnScrollListener;
    private int visibleThreshold = 1;

    /* compiled from: EndlessListScrollListener */
    public interface a {
        void onLoadData();

        boolean shouldLoadData();
    }

    public c(ListView listView) {
        this.mListView = listView;
        this.mFooter = ((LayoutInflater) listView.getContext().getSystemService("layout_inflater")).inflate(i.layout_load_more, null);
    }

    public c(ViewGroup loadingViewContainer, int resourceId) {
        this.mLoadingViewContainer = loadingViewContainer;
        setLoadingView(resourceId);
    }

    public c(View footerView) {
        this.bjW = footerView;
    }

    public void a(a listener) {
        this.bjV = listener;
    }

    public void setParentOnScrollListener(OnScrollListener listener) {
        this.mOnScrollListener = listener;
    }

    protected void setLoadingView(int resourceId) {
        if (this.mLoadingViewContainer != null) {
            this.mFooter = ((LayoutInflater) this.mLoadingViewContainer.getContext().getSystemService("layout_inflater")).inflate(resourceId, null);
        }
    }

    public void setVisibleThreshold(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    public void onLoadComplete() {
        this.mLoading = false;
        if (this.mLoadingViewContainer != null) {
            this.mLoadingViewContainer.removeView(this.mFooter);
        }
        if (this.mListView != null) {
            this.mListView.removeFooterView(this.mFooter);
        }
        if (this.bjW != null) {
            this.bjW.setVisibility(8);
        }
    }

    protected void onLoading() {
        this.mLoading = true;
        if (this.mLoadingViewContainer != null) {
            this.mLoadingViewContainer.addView(this.mFooter);
        }
        if (this.mListView != null) {
            this.mListView.addFooterView(this.mFooter);
        }
        if (this.bjW != null) {
            this.bjW.setVisibility(0);
        }
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        boolean z = totalItemCount > 0 && firstVisibleItem + visibleItemCount >= totalItemCount - this.visibleThreshold;
        this.mLastItemVisible = z;
        if (this.bjV != null && this.mLastItemVisible && !this.mLoading && this.bjV.shouldLoadData()) {
            onLoading();
            this.bjV.onLoadData();
        }
        if (this.mOnScrollListener != null) {
            this.mOnScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (this.mOnScrollListener != null) {
            this.mOnScrollListener.onScrollStateChanged(view, scrollState);
        }
    }
}
