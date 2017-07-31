package com.huluxia.widget.pulltorefresh;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public class PullToRefreshListView extends ListView implements OnScrollListener {
    private int bEA;
    private b bEB;
    private c bEC;
    private Mode bED;
    private Mode bEE;
    private boolean bEF;
    private final int bEG;
    private float bEH;
    private PullToRefreshState bEI;
    private d bEJ;
    private a bEK;
    protected a bEv;
    protected a bEw;
    protected List<View> bEx;
    private boolean bEy;
    private PullToRefreshState bEz;
    private float mInitialMotionY;
    private float mLastMotionY;

    public interface b {
        void onRefresh();
    }

    public interface c {
        void FK();

        void FL();
    }

    public enum Mode {
        PULL_FROM_START,
        PULL_FROM_END,
        BOTH
    }

    public void dq(boolean hide) {
        ((FooterLayout) this.bEw).setVisibility(hide ? 8 : 0);
    }

    public PullToRefreshListView(Context context) {
        this(context, null);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.bEx = new LinkedList();
        this.bEy = false;
        this.bEz = PullToRefreshState.DONE;
        this.bED = Mode.BOTH;
        this.bEE = Mode.PULL_FROM_START;
        this.bEF = false;
        this.bEG = 5;
        this.bEH = 2.5f;
        this.bEI = PullToRefreshState.PULL_To_REFRESH;
        this.bEK = null;
        init(context);
    }

    private void PQ() {
        if (VERSION.SDK_INT >= 9) {
            try {
                Method method = getClass().getMethod("setOverScrollMode", new Class[]{Integer.TYPE});
                if (method != null) {
                    method.invoke(this, new Object[]{Integer.valueOf(2)});
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void init(Context context) {
        PQ();
        setFadingEdgeLength(0);
        setDividerHeight(0);
        setSelector(17170445);
        this.bEv = new HeaderLayout(context);
        setHeaderView(this.bEv);
        this.bEw = new FooterLayout(context);
        setOnScrollListener(this);
    }

    public void setHeaderView(a headerView) {
        if (this.bEx.size() > 0) {
            this.bEx.remove((View) this.bEv);
        }
        this.bEv = headerView;
        smoothScrollTo(-this.bEv.getContentHeight(), 200);
        this.bEx.add((View) this.bEv);
    }

    public void addHeaderView(View v) {
        if (super.getAdapter() != null) {
            Log.d("PulltoRefreshListView", "Cannot add header view to list -- setAdapter has already been called");
        }
        this.bEx.add(v);
    }

    public void setAdapter(ListAdapter adapter) {
        if (this.bEx.size() > 0) {
            for (View v : this.bEx) {
                super.addHeaderView(v);
            }
            this.bEx.clear();
        }
        super.setAdapter(adapter);
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!(this.bEB == null && this.bEC == null)) {
            switch (event.getAction()) {
                case 1:
                case 3:
                    this.bEy = false;
                    PR();
                    break;
                case 2:
                    if (!(this.bEz == PullToRefreshState.REFRESHING || getFirstVisiblePosition() != 0 || this.bEy)) {
                        this.bEy = true;
                        float y = (float) ((int) event.getY());
                        this.mLastMotionY = y;
                        this.mInitialMotionY = y;
                    }
                    if (getFirstVisiblePosition() == 0 && this.bEy) {
                        this.mLastMotionY = (float) ((int) event.getY());
                        int range = Math.round((this.mLastMotionY - this.mInitialMotionY) / this.bEH);
                        if (Math.abs(range) > 5) {
                            mo(range);
                            break;
                        }
                    }
                    break;
            }
        }
        return super.onTouchEvent(event);
    }

    private void PR() {
        switch (1.bEL[this.bEz.ordinal()]) {
            case 1:
            case 2:
                setState(PullToRefreshState.REFRESHING);
                return;
            case 3:
                setState(PullToRefreshState.DONE);
                return;
            default:
                return;
        }
    }

    private void mo(int range) {
        if (range >= 0) {
            int hearPaddingTop = 0;
            if (this.bEz != PullToRefreshState.REFRESHING) {
                hearPaddingTop = range - this.bEv.getContentHeight();
            }
            this.bEv.setScroll(hearPaddingTop);
            if (hearPaddingTop <= 0) {
                setState(PullToRefreshState.PULL_To_REFRESH);
            } else if (hearPaddingTop > 0) {
                setSelection(0);
                setState(PullToRefreshState.RELEASE_To_REFRESH);
            }
        }
    }

    private void setState(PullToRefreshState state) {
        switch (1.bEL[state.ordinal()]) {
            case 1:
                this.bEE = Mode.PULL_FROM_START;
                PS();
                smoothScrollTo(0, 200);
                break;
            case 2:
                this.bEv.releaseToRefreshImpl();
                break;
            case 3:
                this.bEv.pullToRefreshImpl();
                break;
            case 4:
                smoothScrollTo(-this.bEv.getContentHeight(), 200);
                this.bEv.resetImpl();
                setSelection(0);
                break;
        }
        this.bEz = state;
    }

    private void PS() {
        this.bEv.refreshingImpl();
        callRefreshListener();
        setSelection(0);
    }

    public void PT() {
        setState(PullToRefreshState.REFRESHING);
    }

    private void callRefreshListener() {
        if (this.bEB != null) {
            this.bEB.onRefresh();
        }
        if (this.bEC != null) {
            this.bEC.FL();
        }
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.bEA = firstVisibleItem + visibleItemCount;
    }

    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if ((this.bED == Mode.BOTH || this.bED == Mode.PULL_FROM_END) && scrollState == 0 && this.bEC != null && getFirstVisiblePosition() != 0 && this.bEA == getCount() && this.bEF && this.bEI != PullToRefreshState.REFRESHING) {
            setLoadMoreState(PullToRefreshState.REFRESHING);
        }
        if (this.bEK != null) {
            this.bEK.mp(scrollState);
        }
    }

    private void setLoadMoreState(PullToRefreshState state) {
        switch (1.bEL[state.ordinal()]) {
            case 1:
                this.bEE = Mode.PULL_FROM_END;
                this.bEw.refreshingImpl();
                this.bEC.FK();
                break;
            case 2:
                this.bEw.releaseToRefreshImpl();
                break;
            case 3:
                this.bEw.pullToRefreshImpl();
                break;
        }
        this.bEI = state;
    }

    public void setOnRefreshListener(b refreshListener) {
        this.bEB = refreshListener;
        this.bEC = null;
    }

    public void setOnRefreshListener(c refreshListener) {
        this.bEB = null;
        this.bEC = refreshListener;
    }

    public void onRefreshComplete() {
        setState(PullToRefreshState.DONE);
        if (this.bEF) {
            setLoadMoreState(PullToRefreshState.PULL_To_REFRESH);
        }
    }

    public void setHasMore(boolean hasMore) {
        if (this.bED == Mode.BOTH) {
            PU();
        }
        this.bEF = hasMore;
        if (hasMore) {
            setLoadMoreState(PullToRefreshState.PULL_To_REFRESH);
        } else {
            setLoadMoreState(PullToRefreshState.RELEASE_To_REFRESH);
        }
    }

    public boolean isRefreshing() {
        return PullToRefreshState.REFRESHING == this.bEz;
    }

    private void PU() {
        if (getFooterViewsCount() == 0) {
            addFooterView((View) this.bEw);
        }
    }

    public Mode getMode() {
        return this.bED;
    }

    public void setMode(Mode mode) {
        this.bED = mode;
    }

    public Mode getCurrentMode() {
        return this.bEE;
    }

    private void smoothScrollTo(int newScrollValue, long duration) {
        if (this.bEJ != null) {
            this.bEJ.stop();
        }
        int oldScrollValue = this.bEv.getScroll();
        if (oldScrollValue != newScrollValue) {
            this.bEJ = new d(this, oldScrollValue, newScrollValue, duration);
            post(this.bEJ);
        }
    }

    public void setActionCallback(a mActionCallback) {
        this.bEK = mActionCallback;
    }
}
