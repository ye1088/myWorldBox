package com.huluxia.framework.base.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import com.huluxia.framework.R;
import com.huluxia.framework.base.widget.stagger.ExtendableListView;

public class UtilsEndlessListScrollListener implements OnScrollListener {
    private ExtendableListView mExtendableList;
    private View mFooter;
    private boolean mLastItemVisible;
    private ListView mListView;
    private EndlessListener mListener;
    private boolean mLoading = false;
    private ViewGroup mLoadingViewContainer;
    private OnScrollListener mOnScrollListener;
    private int visibleThreshold = 1;

    public interface EndlessListener {
        void onLoadData();

        boolean shouldLoadData();
    }

    public UtilsEndlessListScrollListener(ListView listView) {
        this.mListView = listView;
        this.mFooter = ((LayoutInflater) listView.getContext().getSystemService("layout_inflater")).inflate(R.layout.layout_load_more, null);
    }

    public UtilsEndlessListScrollListener(ExtendableListView listView) {
        this.mExtendableList = listView;
        this.mFooter = ((LayoutInflater) listView.getContext().getSystemService("layout_inflater")).inflate(R.layout.layout_load_more, null);
    }

    public UtilsEndlessListScrollListener(ViewGroup loadingViewContainer, int resourceId) {
        this.mLoadingViewContainer = loadingViewContainer;
        setLoadingView(resourceId);
    }

    public void setListener(EndlessListener listener) {
        this.mListener = listener;
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
        if (this.mExtendableList != null) {
            this.mExtendableList.removeFooterView(this.mFooter);
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
        if (this.mExtendableList != null) {
            this.mExtendableList.addFooterView(this.mFooter);
        }
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        boolean z = totalItemCount > 0 && firstVisibleItem + visibleItemCount >= totalItemCount - this.visibleThreshold;
        this.mLastItemVisible = z;
        if (this.mListener != null && this.mLastItemVisible && !this.mLoading && this.mListener.shouldLoadData()) {
            onLoading();
            this.mListener.onLoadData();
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
