package com.MCWorld.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import com.MCWorld.bbs.b.i;

/* compiled from: UtilsEndlessListScrollListener */
public class aa implements OnScrollListener {
    private View bjW;
    private a blG;
    private OnClickListener blH = new 1(this);
    private boolean blI = false;
    private View blJ;
    private View mFooter;
    private boolean mLastItemVisible;
    private ListView mListView;
    private boolean mLoading = false;
    private ViewGroup mLoadingViewContainer;
    private OnScrollListener mOnScrollListener;
    private int visibleThreshold = 1;

    /* compiled from: UtilsEndlessListScrollListener */
    public interface a {
        void onLoadData();

        boolean shouldLoadData();
    }

    public aa(ListView listView) {
        this.mListView = listView;
        LayoutInflater inflater = (LayoutInflater) listView.getContext().getSystemService("layout_inflater");
        this.mFooter = inflater.inflate(i.layout_load_more, null);
        this.blJ = inflater.inflate(i.layout_load_error, null);
        this.blJ.setOnClickListener(this.blH);
    }

    public aa(ViewGroup loadingViewContainer, int resourceId) {
        this.mLoadingViewContainer = loadingViewContainer;
        setLoadingView(resourceId);
    }

    public aa(View footerView) {
        this.bjW = footerView;
    }

    public void a(a listener) {
        this.blG = listener;
    }

    public void setParentOnScrollListener(OnScrollListener listener) {
        this.mOnScrollListener = listener;
    }

    protected void setLoadingView(int resourceId) {
        if (this.mLoadingViewContainer != null) {
            LayoutInflater inflater = (LayoutInflater) this.mLoadingViewContainer.getContext().getSystemService("layout_inflater");
            this.mFooter = inflater.inflate(resourceId, null);
            this.blJ = inflater.inflate(i.layout_load_error, null);
            this.blJ.setOnClickListener(this.blH);
        }
    }

    public void setVisibleThreshold(int visibleThreshold) {
        this.visibleThreshold = visibleThreshold;
    }

    public void onLoadComplete() {
        this.mLoading = false;
        this.blI = false;
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

    public void KN() {
        if (this.mListView == null || this.mListView.getAdapter() == null || this.mListView.getAdapter().getCount() != 0) {
            this.blI = true;
            this.mLoading = false;
            if (this.mLoadingViewContainer != null) {
                this.mLoadingViewContainer.removeView(this.mFooter);
                this.mLoadingViewContainer.removeView(this.blJ);
                this.mLoadingViewContainer.addView(this.blJ);
            }
            if (this.mListView != null) {
                this.mListView.removeFooterView(this.mFooter);
                this.mListView.removeFooterView(this.blJ);
                this.mListView.addFooterView(this.blJ);
            }
            if (this.bjW != null) {
                this.bjW.setVisibility(0);
            }
        }
    }

    protected void onLoading() {
        this.mLoading = true;
        this.blI = false;
        if (this.mLoadingViewContainer != null) {
            this.mLoadingViewContainer.addView(this.mFooter);
            this.mLoadingViewContainer.removeView(this.blJ);
        }
        if (this.mListView != null) {
            this.mListView.addFooterView(this.mFooter);
            this.mListView.removeFooterView(this.blJ);
        }
        if (this.bjW != null) {
            this.bjW.setVisibility(0);
        }
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        boolean z = totalItemCount > 0 && firstVisibleItem + visibleItemCount >= totalItemCount - this.visibleThreshold;
        this.mLastItemVisible = z;
        if (!(this.blG == null || !this.mLastItemVisible || this.mLoading || this.blI || !this.blG.shouldLoadData())) {
            onLoading();
            this.blG.onLoadData();
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
