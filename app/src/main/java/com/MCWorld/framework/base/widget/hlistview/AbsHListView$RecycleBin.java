package com.MCWorld.framework.base.widget.hlistview;

import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.support.v4.util.SparseArrayCompat;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class AbsHListView$RecycleBin {
    private View[] mActiveViews = new View[0];
    private ArrayList<View> mCurrentScrap;
    private int mFirstActivePosition;
    private AbsHListView$RecyclerListener mRecyclerListener;
    private ArrayList<View>[] mScrapViews;
    private ArrayList<View> mSkippedScrap;
    private SparseArrayCompat<View> mTransientStateViews;
    private int mViewTypeCount;
    final /* synthetic */ AbsHListView this$0;

    public AbsHListView$RecycleBin(AbsHListView this$0) {
        this.this$0 = this$0;
    }

    public void setViewTypeCount(int viewTypeCount) {
        if (viewTypeCount < 1) {
            throw new IllegalArgumentException("Can't have a_isRightVersion viewTypeCount < 1");
        }
        ArrayList<View>[] scrapViews = new ArrayList[viewTypeCount];
        for (int i = 0; i < viewTypeCount; i++) {
            scrapViews[i] = new ArrayList();
        }
        this.mViewTypeCount = viewTypeCount;
        this.mCurrentScrap = scrapViews[0];
        this.mScrapViews = scrapViews;
    }

    public void markChildrenDirty() {
        int i;
        ArrayList<View> scrap;
        int scrapCount;
        if (this.mViewTypeCount == 1) {
            scrap = this.mCurrentScrap;
            scrapCount = scrap.size();
            for (i = 0; i < scrapCount; i++) {
                ((View) scrap.get(i)).forceLayout();
            }
        } else {
            int typeCount = this.mViewTypeCount;
            for (i = 0; i < typeCount; i++) {
                scrap = this.mScrapViews[i];
                scrapCount = scrap.size();
                for (int j = 0; j < scrapCount; j++) {
                    ((View) scrap.get(j)).forceLayout();
                }
            }
        }
        if (this.mTransientStateViews != null) {
            int count = this.mTransientStateViews.size();
            for (i = 0; i < count; i++) {
                ((View) this.mTransientStateViews.valueAt(i)).forceLayout();
            }
        }
    }

    public boolean shouldRecycleViewType(int viewType) {
        return viewType >= 0;
    }

    public void clear() {
        ArrayList<View> scrap;
        int scrapCount;
        int i;
        if (this.mViewTypeCount == 1) {
            scrap = this.mCurrentScrap;
            scrapCount = scrap.size();
            for (i = 0; i < scrapCount; i++) {
                AbsHListView.access$2300(this.this$0, (View) scrap.remove((scrapCount - 1) - i), false);
            }
        } else {
            int typeCount = this.mViewTypeCount;
            for (i = 0; i < typeCount; i++) {
                scrap = this.mScrapViews[i];
                scrapCount = scrap.size();
                for (int j = 0; j < scrapCount; j++) {
                    AbsHListView.access$2400(this.this$0, (View) scrap.remove((scrapCount - 1) - j), false);
                }
            }
        }
        if (this.mTransientStateViews != null) {
            this.mTransientStateViews.clear();
        }
    }

    public void fillActiveViews(int childCount, int firstActivePosition) {
        if (this.mActiveViews.length < childCount) {
            this.mActiveViews = new View[childCount];
        }
        this.mFirstActivePosition = firstActivePosition;
        View[] activeViews = this.mActiveViews;
        for (int i = 0; i < childCount; i++) {
            View child = this.this$0.getChildAt(i);
            AbsHListView$LayoutParams lp = (AbsHListView$LayoutParams) child.getLayoutParams();
            if (!(lp == null || lp.viewType == -2)) {
                activeViews[i] = child;
            }
        }
    }

    public View getActiveView(int position) {
        int index = position - this.mFirstActivePosition;
        View[] activeViews = this.mActiveViews;
        if (index < 0 || index >= activeViews.length) {
            return null;
        }
        View match = activeViews[index];
        activeViews[index] = null;
        return match;
    }

    View getTransientStateView(int position) {
        if (this.mTransientStateViews == null) {
            return null;
        }
        int index = this.mTransientStateViews.indexOfKey(position);
        if (index < 0) {
            return null;
        }
        View result = (View) this.mTransientStateViews.valueAt(index);
        this.mTransientStateViews.removeAt(index);
        return result;
    }

    void clearTransientStateViews() {
        if (this.mTransientStateViews != null) {
            this.mTransientStateViews.clear();
        }
    }

    View getScrapView(int position) {
        if (this.mViewTypeCount == 1) {
            return AbsHListView.retrieveFromScrap(this.mCurrentScrap, position);
        }
        int whichScrap = this.this$0.mAdapter.getItemViewType(position);
        if (whichScrap < 0 || whichScrap >= this.mScrapViews.length) {
            return null;
        }
        return AbsHListView.retrieveFromScrap(this.mScrapViews[whichScrap], position);
    }

    @SuppressLint({"NewApi"})
    public void addScrapView(View scrap, int position) {
        AbsHListView$LayoutParams lp = (AbsHListView$LayoutParams) scrap.getLayoutParams();
        if (lp != null) {
            lp.scrappedFromPosition = position;
            int viewType = lp.viewType;
            boolean scrapHasTransientState = VERSION.SDK_INT >= 16 ? scrap.hasTransientState() : false;
            if (!shouldRecycleViewType(viewType) || scrapHasTransientState) {
                if (viewType != -2 || scrapHasTransientState) {
                    if (this.mSkippedScrap == null) {
                        this.mSkippedScrap = new ArrayList();
                    }
                    this.mSkippedScrap.add(scrap);
                }
                if (scrapHasTransientState) {
                    if (this.mTransientStateViews == null) {
                        this.mTransientStateViews = new SparseArrayCompat();
                    }
                    scrap.onStartTemporaryDetach();
                    this.mTransientStateViews.put(position, scrap);
                    return;
                }
                return;
            }
            scrap.onStartTemporaryDetach();
            if (this.mViewTypeCount == 1) {
                this.mCurrentScrap.add(scrap);
            } else {
                this.mScrapViews[viewType].add(scrap);
            }
            if (VERSION.SDK_INT >= 14) {
                scrap.setAccessibilityDelegate(null);
            }
            if (this.mRecyclerListener != null) {
                this.mRecyclerListener.onMovedToScrapHeap(scrap);
            }
        }
    }

    public void removeSkippedScrap() {
        if (this.mSkippedScrap != null) {
            int count = this.mSkippedScrap.size();
            for (int i = 0; i < count; i++) {
                AbsHListView.access$2500(this.this$0, (View) this.mSkippedScrap.get(i), false);
            }
            this.mSkippedScrap.clear();
        }
    }

    @SuppressLint({"NewApi"})
    public void scrapActiveViews() {
        boolean hasListener;
        boolean multipleScraps;
        View[] activeViews = this.mActiveViews;
        if (this.mRecyclerListener != null) {
            hasListener = true;
        } else {
            hasListener = false;
        }
        if (this.mViewTypeCount > 1) {
            multipleScraps = true;
        } else {
            multipleScraps = false;
        }
        ArrayList<View> scrapViews = this.mCurrentScrap;
        for (int i = activeViews.length - 1; i >= 0; i--) {
            View victim = activeViews[i];
            if (victim != null) {
                boolean scrapHasTransientState;
                AbsHListView$LayoutParams lp = (AbsHListView$LayoutParams) victim.getLayoutParams();
                int whichScrap = lp.viewType;
                activeViews[i] = null;
                if (VERSION.SDK_INT >= 16) {
                    scrapHasTransientState = victim.hasTransientState();
                } else {
                    scrapHasTransientState = false;
                }
                if (!shouldRecycleViewType(whichScrap) || scrapHasTransientState) {
                    if (whichScrap != -2 || scrapHasTransientState) {
                        AbsHListView.access$2600(this.this$0, victim, false);
                    }
                    if (scrapHasTransientState) {
                        if (this.mTransientStateViews == null) {
                            this.mTransientStateViews = new SparseArrayCompat();
                        }
                        this.mTransientStateViews.put(this.mFirstActivePosition + i, victim);
                    }
                } else {
                    if (multipleScraps) {
                        scrapViews = this.mScrapViews[whichScrap];
                    }
                    victim.onStartTemporaryDetach();
                    lp.scrappedFromPosition = this.mFirstActivePosition + i;
                    scrapViews.add(victim);
                    if (VERSION.SDK_INT >= 14) {
                        victim.setAccessibilityDelegate(null);
                    }
                    if (hasListener) {
                        this.mRecyclerListener.onMovedToScrapHeap(victim);
                    }
                }
            }
        }
        pruneScrapViews();
    }

    @SuppressLint({"NewApi"})
    private void pruneScrapViews() {
        int i;
        int maxViews = this.mActiveViews.length;
        int viewTypeCount = this.mViewTypeCount;
        ArrayList<View>[] scrapViews = this.mScrapViews;
        for (i = 0; i < viewTypeCount; i++) {
            ArrayList<View> scrapPile = scrapViews[i];
            int size = scrapPile.size();
            int extras = size - maxViews;
            int j = 0;
            int size2 = size - 1;
            while (j < extras) {
                size = size2 - 1;
                AbsHListView.access$2700(this.this$0, (View) scrapPile.remove(size2), false);
                j++;
                size2 = size;
            }
        }
        if (this.mTransientStateViews != null) {
            i = 0;
            while (i < this.mTransientStateViews.size()) {
                if (!((View) this.mTransientStateViews.valueAt(i)).hasTransientState()) {
                    this.mTransientStateViews.removeAt(i);
                    i--;
                }
                i++;
            }
        }
    }

    void reclaimScrapViews(List<View> views) {
        if (this.mViewTypeCount == 1) {
            views.addAll(this.mCurrentScrap);
            return;
        }
        int viewTypeCount = this.mViewTypeCount;
        ArrayList<View>[] scrapViews = this.mScrapViews;
        for (int i = 0; i < viewTypeCount; i++) {
            views.addAll(scrapViews[i]);
        }
    }

    void setCacheColorHint(int color) {
        int i;
        ArrayList<View> scrap;
        int scrapCount;
        if (this.mViewTypeCount == 1) {
            scrap = this.mCurrentScrap;
            scrapCount = scrap.size();
            for (i = 0; i < scrapCount; i++) {
                ((View) scrap.get(i)).setDrawingCacheBackgroundColor(color);
            }
        } else {
            int typeCount = this.mViewTypeCount;
            for (i = 0; i < typeCount; i++) {
                scrap = this.mScrapViews[i];
                scrapCount = scrap.size();
                for (int j = 0; j < scrapCount; j++) {
                    ((View) scrap.get(j)).setDrawingCacheBackgroundColor(color);
                }
            }
        }
        for (View victim : this.mActiveViews) {
            if (victim != null) {
                victim.setDrawingCacheBackgroundColor(color);
            }
        }
    }
}
