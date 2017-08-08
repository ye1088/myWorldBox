package com.MCWorld.framework.base.widget.stagger;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.Scroller;
import java.util.ArrayList;
import java.util.Iterator;

public abstract class ExtendableListView extends AbsListView {
    private static final boolean DBG = false;
    private static final int INVALID_POINTER = -1;
    private static final int LAYOUT_FORCE_TOP = 1;
    private static final int LAYOUT_NORMAL = 0;
    private static final int LAYOUT_SYNC = 2;
    private static final String TAG = "ExtendableListView";
    private static final int TOUCH_MODE_DONE_WAITING = 5;
    private static final int TOUCH_MODE_DOWN = 3;
    private static final int TOUCH_MODE_FLINGING = 2;
    private static final int TOUCH_MODE_IDLE = 0;
    private static final int TOUCH_MODE_SCROLLING = 1;
    private static final int TOUCH_MODE_TAP = 4;
    private int mActivePointerId = -1;
    ListAdapter mAdapter;
    private boolean mBlockLayoutRequests = false;
    protected boolean mClipToPadding;
    private boolean mDataChanged;
    protected int mFirstPosition;
    private FlingRunnable mFlingRunnable;
    private int mFlingVelocity;
    private ArrayList<FixedViewInfo> mFooterViewInfos;
    protected ArrayList<FixedViewInfo> mHeaderViewInfos;
    private boolean mInLayout;
    private boolean mIsAttached;
    final boolean[] mIsScrap = new boolean[1];
    private int mItemCount;
    private int mLastY;
    private int mLayoutMode;
    private int mMaximumVelocity;
    private int mMotionCorrection;
    private int mMotionPosition;
    private int mMotionX;
    private int mMotionY;
    boolean mNeedSync = false;
    private AdapterDataSetObserver mObserver;
    private int mOldItemCount;
    private OnScrollListener mOnScrollListener;
    private CheckForLongPress mPendingCheckForLongPress;
    private Runnable mPendingCheckForTap;
    private PerformClick mPerformClick;
    private RecycleBin mRecycleBin;
    private int mScrollState = 0;
    protected int mSpecificTop;
    long mSyncHeight;
    protected int mSyncPosition;
    long mSyncRowId = Long.MIN_VALUE;
    private ListSavedState mSyncState;
    private int mTouchMode;
    private int mTouchSlop;
    private VelocityTracker mVelocityTracker = null;
    private int mWidthMeasureSpec;

    public static class LayoutParams extends android.widget.AbsListView.LayoutParams {
        long itemId = -1;
        int position;
        boolean recycledHeaderFooter;
        int viewType;

        public LayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
        }

        public LayoutParams(int w, int h) {
            super(w, h);
        }

        public LayoutParams(int w, int h, int viewType) {
            super(w, h);
            this.viewType = viewType;
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams source) {
            super(source);
        }
    }

    public static class ListSavedState extends ClassLoaderSavedState {
        public static final Creator<ListSavedState> CREATOR = new Creator<ListSavedState>() {
            public ListSavedState createFromParcel(Parcel in) {
                return new ListSavedState(in);
            }

            public ListSavedState[] newArray(int size) {
                return new ListSavedState[size];
            }
        };
        protected long firstId;
        protected int height;
        protected int position;
        protected long selectedId;
        protected int viewTop;

        public ListSavedState(Parcelable superState) {
            super(superState, AbsListView.class.getClassLoader());
        }

        public ListSavedState(Parcel in) {
            super(in);
            this.selectedId = in.readLong();
            this.firstId = in.readLong();
            this.viewTop = in.readInt();
            this.position = in.readInt();
            this.height = in.readInt();
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeLong(this.selectedId);
            out.writeLong(this.firstId);
            out.writeInt(this.viewTop);
            out.writeInt(this.position);
            out.writeInt(this.height);
        }

        public String toString() {
            return "ExtendableListView.ListSavedState{" + Integer.toHexString(System.identityHashCode(this)) + " selectedId=" + this.selectedId + " firstId=" + this.firstId + " viewTop=" + this.viewTop + " position=" + this.position + " height=" + this.height + "}";
        }
    }

    class AdapterDataSetObserver extends DataSetObserver {
        private Parcelable mInstanceState = null;

        AdapterDataSetObserver() {
        }

        public void onChanged() {
            ExtendableListView.this.mDataChanged = true;
            ExtendableListView.this.mOldItemCount = ExtendableListView.this.mItemCount;
            ExtendableListView.this.mItemCount = ExtendableListView.this.getAdapter().getCount();
            ExtendableListView.this.mRecycleBin.clearTransientStateViews();
            if (!ExtendableListView.this.getAdapter().hasStableIds() || this.mInstanceState == null || ExtendableListView.this.mOldItemCount != 0 || ExtendableListView.this.mItemCount <= 0) {
                ExtendableListView.this.rememberSyncState();
            } else {
                ExtendableListView.this.onRestoreInstanceState(this.mInstanceState);
                this.mInstanceState = null;
            }
            ExtendableListView.this.updateEmptyStatus();
            ExtendableListView.this.requestLayout();
        }

        public void onInvalidated() {
            ExtendableListView.this.mDataChanged = true;
            if (ExtendableListView.this.getAdapter().hasStableIds()) {
                this.mInstanceState = ExtendableListView.this.onSaveInstanceState();
            }
            ExtendableListView.this.mOldItemCount = ExtendableListView.this.mItemCount;
            ExtendableListView.this.mItemCount = 0;
            ExtendableListView.this.mNeedSync = false;
            ExtendableListView.this.updateEmptyStatus();
            ExtendableListView.this.requestLayout();
        }

        public void clearSavedState() {
            this.mInstanceState = null;
        }
    }

    private class WindowRunnnable {
        private int mOriginalAttachCount;

        private WindowRunnnable() {
        }

        public void rememberWindowAttachCount() {
            this.mOriginalAttachCount = ExtendableListView.this.getWindowAttachCount();
        }

        public boolean sameWindow() {
            return ExtendableListView.this.hasWindowFocus() && ExtendableListView.this.getWindowAttachCount() == this.mOriginalAttachCount;
        }
    }

    private class CheckForLongPress extends WindowRunnnable implements Runnable {
        private CheckForLongPress() {
            super();
        }

        public void run() {
            View child = ExtendableListView.this.getChildAt(ExtendableListView.this.mMotionPosition);
            if (child != null) {
                int longPressPosition = ExtendableListView.this.mMotionPosition;
                long longPressId = ExtendableListView.this.mAdapter.getItemId(ExtendableListView.this.mMotionPosition + ExtendableListView.this.mFirstPosition);
                boolean handled = false;
                if (sameWindow() && !ExtendableListView.this.mDataChanged) {
                    handled = ExtendableListView.this.performLongPress(child, ExtendableListView.this.mFirstPosition + longPressPosition, longPressId);
                }
                if (handled) {
                    ExtendableListView.this.mTouchMode = 0;
                    ExtendableListView.this.setPressed(false);
                    child.setPressed(false);
                    return;
                }
                ExtendableListView.this.mTouchMode = 5;
            }
        }
    }

    final class CheckForTap implements Runnable {
        CheckForTap() {
        }

        public void run() {
            if (ExtendableListView.this.mTouchMode == 3) {
                ExtendableListView.this.mTouchMode = 4;
                View child = ExtendableListView.this.getChildAt(ExtendableListView.this.mMotionPosition);
                if (child != null && !child.hasFocusable()) {
                    ExtendableListView.this.mLayoutMode = 0;
                    if (ExtendableListView.this.mDataChanged) {
                        ExtendableListView.this.mTouchMode = 5;
                        return;
                    }
                    ExtendableListView.this.layoutChildren();
                    child.setPressed(true);
                    ExtendableListView.this.setPressed(true);
                    int longPressTimeout = ViewConfiguration.getLongPressTimeout();
                    if (ExtendableListView.this.isLongClickable()) {
                        if (ExtendableListView.this.mPendingCheckForLongPress == null) {
                            ExtendableListView.this.mPendingCheckForLongPress = new CheckForLongPress();
                        }
                        ExtendableListView.this.mPendingCheckForLongPress.rememberWindowAttachCount();
                        ExtendableListView.this.postDelayed(ExtendableListView.this.mPendingCheckForLongPress, (long) longPressTimeout);
                        return;
                    }
                    ExtendableListView.this.mTouchMode = 5;
                }
            }
        }
    }

    public class FixedViewInfo {
        public Object data;
        public boolean isSelectable;
        public View view;
    }

    private class FlingRunnable implements Runnable {
        private int mLastFlingY;
        private final Scroller mScroller;

        FlingRunnable() {
            this.mScroller = new Scroller(ExtendableListView.this.getContext());
        }

        void start(int initialVelocity) {
            int initialY;
            if (initialVelocity < 0) {
                initialY = Integer.MAX_VALUE;
            } else {
                initialY = 0;
            }
            this.mLastFlingY = initialY;
            this.mScroller.forceFinished(true);
            this.mScroller.fling(0, initialY, 0, initialVelocity, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
            ExtendableListView.this.mTouchMode = 2;
            ExtendableListView.this.postOnAnimate(this);
        }

        void startScroll(int distance, int duration) {
            int initialY;
            if (distance < 0) {
                initialY = Integer.MAX_VALUE;
            } else {
                initialY = 0;
            }
            this.mLastFlingY = initialY;
            this.mScroller.startScroll(0, initialY, 0, distance, duration);
            ExtendableListView.this.mTouchMode = 2;
            ExtendableListView.this.postOnAnimate(this);
        }

        private void endFling() {
            this.mLastFlingY = 0;
            ExtendableListView.this.mTouchMode = 0;
            ExtendableListView.this.reportScrollStateChange(0);
            ExtendableListView.this.removeCallbacks(this);
            this.mScroller.forceFinished(true);
        }

        public void run() {
            switch (ExtendableListView.this.mTouchMode) {
                case 2:
                    if (ExtendableListView.this.mItemCount == 0 || ExtendableListView.this.getChildCount() == 0) {
                        endFling();
                        return;
                    }
                    Scroller scroller = this.mScroller;
                    boolean more = scroller.computeScrollOffset();
                    int y = scroller.getCurrY();
                    int delta = this.mLastFlingY - y;
                    if (delta > 0) {
                        ExtendableListView.this.mMotionPosition = ExtendableListView.this.mFirstPosition;
                        delta = Math.min(((ExtendableListView.this.getHeight() - ExtendableListView.this.getPaddingBottom()) - ExtendableListView.this.getPaddingTop()) - 1, delta);
                    } else {
                        ExtendableListView.this.mMotionPosition = ExtendableListView.this.mFirstPosition + (ExtendableListView.this.getChildCount() - 1);
                        delta = Math.max(-(((ExtendableListView.this.getHeight() - ExtendableListView.this.getPaddingBottom()) - ExtendableListView.this.getPaddingTop()) - 1), delta);
                    }
                    boolean atEnd = ExtendableListView.this.moveTheChildren(delta, delta);
                    if (!more || atEnd) {
                        endFling();
                        return;
                    }
                    ExtendableListView.this.invalidate();
                    this.mLastFlingY = y;
                    ExtendableListView.this.postOnAnimate(this);
                    return;
                default:
                    return;
            }
        }
    }

    private class PerformClick extends WindowRunnnable implements Runnable {
        int mClickMotionPosition;

        private PerformClick() {
            super();
        }

        public void run() {
            if (!ExtendableListView.this.mDataChanged) {
                ListAdapter adapter = ExtendableListView.this.mAdapter;
                int motionPosition = this.mClickMotionPosition;
                if (adapter != null && ExtendableListView.this.mItemCount > 0 && motionPosition != -1 && motionPosition < adapter.getCount() && sameWindow()) {
                    View view = ExtendableListView.this.getChildAt(motionPosition);
                    if (view != null) {
                        int clickPosition = motionPosition + ExtendableListView.this.mFirstPosition;
                        ExtendableListView.this.performItemClick(view, clickPosition, adapter.getItemId(clickPosition));
                    }
                }
            }
        }
    }

    class RecycleBin {
        private View[] mActiveViews = new View[0];
        private ArrayList<View> mCurrentScrap;
        private int mFirstActivePosition;
        private ArrayList<View>[] mScrapViews;
        private ArrayList<View> mSkippedScrap;
        private SparseArrayCompat<View> mTransientStateViews;
        private int mViewTypeCount;

        RecycleBin() {
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

        void clear() {
            ArrayList<View> scrap;
            int scrapCount;
            int i;
            if (this.mViewTypeCount == 1) {
                scrap = this.mCurrentScrap;
                scrapCount = scrap.size();
                for (i = 0; i < scrapCount; i++) {
                    ExtendableListView.this.removeDetachedView((View) scrap.remove((scrapCount - 1) - i), false);
                }
            } else {
                int typeCount = this.mViewTypeCount;
                for (i = 0; i < typeCount; i++) {
                    scrap = this.mScrapViews[i];
                    scrapCount = scrap.size();
                    for (int j = 0; j < scrapCount; j++) {
                        ExtendableListView.this.removeDetachedView((View) scrap.remove((scrapCount - 1) - j), false);
                    }
                }
            }
            if (this.mTransientStateViews != null) {
                this.mTransientStateViews.clear();
            }
        }

        void fillActiveViews(int childCount, int firstActivePosition) {
            if (this.mActiveViews.length < childCount) {
                this.mActiveViews = new View[childCount];
            }
            this.mFirstActivePosition = firstActivePosition;
            View[] activeViews = this.mActiveViews;
            for (int i = 0; i < childCount; i++) {
                View child = ExtendableListView.this.getChildAt(i);
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                if (!(lp == null || lp.viewType == -2)) {
                    activeViews[i] = child;
                }
            }
        }

        View getActiveView(int position) {
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
                return ExtendableListView.retrieveFromScrap(this.mCurrentScrap, position);
            }
            int whichScrap = ExtendableListView.this.mAdapter.getItemViewType(position);
            if (whichScrap < 0 || whichScrap >= this.mScrapViews.length) {
                return null;
            }
            return ExtendableListView.retrieveFromScrap(this.mScrapViews[whichScrap], position);
        }

        void addScrapView(View scrap, int position) {
            LayoutParams lp = (LayoutParams) scrap.getLayoutParams();
            if (lp != null) {
                lp.position = position;
                int viewType = lp.viewType;
                boolean scrapHasTransientState = ViewCompat.hasTransientState(scrap);
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
                        this.mTransientStateViews.put(position, scrap);
                    }
                } else if (this.mViewTypeCount == 1) {
                    this.mCurrentScrap.add(scrap);
                } else {
                    this.mScrapViews[viewType].add(scrap);
                }
            }
        }

        void removeSkippedScrap() {
            if (this.mSkippedScrap != null) {
                int count = this.mSkippedScrap.size();
                for (int i = 0; i < count; i++) {
                    ExtendableListView.this.removeDetachedView((View) this.mSkippedScrap.get(i), false);
                }
                this.mSkippedScrap.clear();
            }
        }

        void scrapActiveViews() {
            boolean multipleScraps = true;
            View[] activeViews = this.mActiveViews;
            if (this.mViewTypeCount <= 1) {
                multipleScraps = false;
            }
            ArrayList<View> scrapViews = this.mCurrentScrap;
            for (int i = activeViews.length - 1; i >= 0; i--) {
                View victim = activeViews[i];
                if (victim != null) {
                    LayoutParams lp = (LayoutParams) victim.getLayoutParams();
                    activeViews[i] = null;
                    boolean scrapHasTransientState = ViewCompat.hasTransientState(victim);
                    int viewType = lp.viewType;
                    if (!shouldRecycleViewType(viewType) || scrapHasTransientState) {
                        if (viewType != -2 || scrapHasTransientState) {
                            ExtendableListView.this.removeDetachedView(victim, false);
                        }
                        if (scrapHasTransientState) {
                            if (this.mTransientStateViews == null) {
                                this.mTransientStateViews = new SparseArrayCompat();
                            }
                            this.mTransientStateViews.put(this.mFirstActivePosition + i, victim);
                        }
                    } else {
                        if (multipleScraps) {
                            scrapViews = this.mScrapViews[viewType];
                        }
                        lp.position = this.mFirstActivePosition + i;
                        scrapViews.add(victim);
                    }
                }
            }
            pruneScrapViews();
        }

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
                    ExtendableListView.this.removeDetachedView((View) scrapPile.remove(size2), false);
                    j++;
                    size2 = size;
                }
            }
            if (this.mTransientStateViews != null) {
                i = 0;
                while (i < this.mTransientStateViews.size()) {
                    if (!ViewCompat.hasTransientState((View) this.mTransientStateViews.valueAt(i))) {
                        this.mTransientStateViews.removeAt(i);
                        i--;
                    }
                    i++;
                }
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

    public ExtendableListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setWillNotDraw(false);
        setClipToPadding(false);
        setFocusableInTouchMode(false);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.mTouchSlop = viewConfiguration.getScaledTouchSlop();
        this.mMaximumVelocity = viewConfiguration.getScaledMaximumFlingVelocity();
        this.mFlingVelocity = viewConfiguration.getScaledMinimumFlingVelocity();
        this.mRecycleBin = new RecycleBin();
        this.mObserver = new AdapterDataSetObserver();
        this.mHeaderViewInfos = new ArrayList();
        this.mFooterViewInfos = new ArrayList();
        this.mLayoutMode = 0;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mAdapter != null) {
            this.mDataChanged = true;
            this.mOldItemCount = this.mItemCount;
            this.mItemCount = this.mAdapter.getCount();
        }
        this.mIsAttached = true;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mRecycleBin.clear();
        if (this.mFlingRunnable != null) {
            removeCallbacks(this.mFlingRunnable);
        }
        this.mIsAttached = false;
    }

    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        onSizeChanged(w, h);
    }

    protected void onSizeChanged(int w, int h) {
        if (getChildCount() > 0) {
            stopFlingRunnable();
            this.mRecycleBin.clear();
            this.mDataChanged = true;
            rememberSyncState();
        }
    }

    public ListAdapter getAdapter() {
        return this.mAdapter;
    }

    public void setAdapter(ListAdapter adapter) {
        if (this.mAdapter != null) {
            this.mAdapter.unregisterDataSetObserver(this.mObserver);
        }
        if (this.mHeaderViewInfos.size() > 0 || this.mFooterViewInfos.size() > 0) {
            this.mAdapter = new HeaderViewListAdapter(this.mHeaderViewInfos, this.mFooterViewInfos, adapter);
        } else {
            this.mAdapter = adapter;
        }
        this.mDataChanged = true;
        this.mItemCount = this.mAdapter != null ? this.mAdapter.getCount() : 0;
        if (this.mAdapter != null) {
            this.mAdapter.registerDataSetObserver(this.mObserver);
            this.mRecycleBin.setViewTypeCount(this.mAdapter.getViewTypeCount());
        }
        requestLayout();
    }

    public int getCount() {
        return this.mItemCount;
    }

    public View getSelectedView() {
        return null;
    }

    public void setSelection(int position) {
        if (position >= 0) {
            this.mLayoutMode = 2;
            this.mSpecificTop = getListPaddingTop();
            this.mFirstPosition = 0;
            if (this.mNeedSync) {
                this.mSyncPosition = position;
                this.mSyncRowId = this.mAdapter.getItemId(position);
            }
            requestLayout();
        }
    }

    public void addHeaderView(View v, Object data, boolean isSelectable) {
        if (this.mAdapter == null || (this.mAdapter instanceof HeaderViewListAdapter)) {
            FixedViewInfo info = new FixedViewInfo();
            info.view = v;
            info.data = data;
            info.isSelectable = isSelectable;
            this.mHeaderViewInfos.add(info);
            if (this.mAdapter != null && this.mObserver != null) {
                this.mObserver.onChanged();
                return;
            }
            return;
        }
        throw new IllegalStateException("Cannot add header view to list -- setAdapter has already been called.");
    }

    public void addHeaderView(View v) {
        addHeaderView(v, null, true);
    }

    public int getHeaderViewsCount() {
        return this.mHeaderViewInfos.size();
    }

    public boolean removeHeaderView(View v) {
        if (this.mHeaderViewInfos.size() <= 0) {
            return false;
        }
        boolean result = false;
        if (this.mAdapter != null && ((HeaderViewListAdapter) this.mAdapter).removeHeader(v)) {
            if (this.mObserver != null) {
                this.mObserver.onChanged();
            }
            result = true;
        }
        removeFixedViewInfo(v, this.mHeaderViewInfos);
        return result;
    }

    private void removeFixedViewInfo(View v, ArrayList<FixedViewInfo> where) {
        int len = where.size();
        for (int i = 0; i < len; i++) {
            if (((FixedViewInfo) where.get(i)).view == v) {
                where.remove(i);
                return;
            }
        }
    }

    public void addFooterView(View v, Object data, boolean isSelectable) {
        FixedViewInfo info = new FixedViewInfo();
        info.view = v;
        info.data = data;
        info.isSelectable = isSelectable;
        this.mFooterViewInfos.add(info);
        if (this.mAdapter != null && this.mObserver != null) {
            this.mObserver.onChanged();
        }
    }

    public void addFooterView(View v) {
        addFooterView(v, null, true);
    }

    public int getFooterViewsCount() {
        return this.mFooterViewInfos.size();
    }

    public boolean removeFooterView(View v) {
        if (this.mFooterViewInfos.size() <= 0) {
            return false;
        }
        boolean result = false;
        if (this.mAdapter != null && ((HeaderViewListAdapter) this.mAdapter).removeFooter(v)) {
            if (this.mObserver != null) {
                this.mObserver.onChanged();
            }
            result = true;
        }
        removeFixedViewInfo(v, this.mFooterViewInfos);
        return result;
    }

    public void setClipToPadding(boolean clipToPadding) {
        super.setClipToPadding(clipToPadding);
        this.mClipToPadding = clipToPadding;
    }

    public void requestLayout() {
        if (!this.mBlockLayoutRequests && !this.mInLayout) {
            super.requestLayout();
        }
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (this.mAdapter != null) {
            if (changed) {
                int childCount = getChildCount();
                for (int i = 0; i < childCount; i++) {
                    getChildAt(i).forceLayout();
                }
                this.mRecycleBin.markChildrenDirty();
            }
            this.mInLayout = true;
            layoutChildren();
            this.mInLayout = false;
        }
    }

    protected void layoutChildren() {
        if (!this.mBlockLayoutRequests) {
            this.mBlockLayoutRequests = true;
            try {
                super.layoutChildren();
                invalidate();
                if (this.mAdapter == null) {
                    clearState();
                    invokeOnItemScrollListener();
                    return;
                }
                int childrenTop = getListPaddingTop();
                int childCount = getChildCount();
                View oldFirst = null;
                if (this.mLayoutMode == 0) {
                    oldFirst = getChildAt(0);
                }
                boolean dataChanged = this.mDataChanged;
                if (dataChanged) {
                    handleDataChanged();
                }
                if (this.mItemCount == 0) {
                    clearState();
                    invokeOnItemScrollListener();
                    this.mBlockLayoutRequests = false;
                } else if (this.mItemCount != this.mAdapter.getCount()) {
                    throw new IllegalStateException("The content of the adapter has changed but ExtendableListView did not receive a_isRightVersion notification. Make sure the content of your adapter is not modified from a_isRightVersion background thread, but only from the UI thread. [in ExtendableListView(" + getId() + ", " + getClass() + ") with Adapter(" + this.mAdapter.getClass() + ")]");
                } else {
                    int firstPosition = this.mFirstPosition;
                    RecycleBin recycleBin = this.mRecycleBin;
                    if (dataChanged) {
                        for (int i = 0; i < childCount; i++) {
                            recycleBin.addScrapView(getChildAt(i), firstPosition + i);
                        }
                    } else {
                        recycleBin.fillActiveViews(childCount, firstPosition);
                    }
                    detachAllViewsFromParent();
                    recycleBin.removeSkippedScrap();
                    switch (this.mLayoutMode) {
                        case 1:
                            this.mFirstPosition = 0;
                            resetToTop();
                            adjustViewsUpOrDown();
                            fillFromTop(childrenTop);
                            adjustViewsUpOrDown();
                            break;
                        case 2:
                            fillSpecific(this.mSyncPosition, this.mSpecificTop);
                            break;
                        default:
                            if (childCount != 0) {
                                if (this.mFirstPosition >= this.mItemCount) {
                                    fillSpecific(0, childrenTop);
                                    break;
                                }
                                int i2 = this.mFirstPosition;
                                if (oldFirst != null) {
                                    childrenTop = oldFirst.getTop();
                                }
                                fillSpecific(i2, childrenTop);
                                break;
                            }
                            fillFromTop(childrenTop);
                            break;
                    }
                    recycleBin.scrapActiveViews();
                    this.mDataChanged = false;
                    this.mNeedSync = false;
                    this.mLayoutMode = 0;
                    invokeOnItemScrollListener();
                    this.mBlockLayoutRequests = false;
                }
            } finally {
                this.mBlockLayoutRequests = false;
            }
        }
    }

    protected void handleDataChanged() {
        super.handleDataChanged();
        int count = this.mItemCount;
        if (count <= 0 || !this.mNeedSync) {
            this.mLayoutMode = 1;
            this.mNeedSync = false;
            this.mSyncState = null;
            return;
        }
        this.mNeedSync = false;
        this.mSyncState = null;
        this.mLayoutMode = 2;
        this.mSyncPosition = Math.min(Math.max(0, this.mSyncPosition), count - 1);
    }

    public void resetToTop() {
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
        this.mWidthMeasureSpec = widthMeasureSpec;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (isEnabled()) {
            initVelocityTrackerIfNotExists();
            this.mVelocityTracker.addMovement(event);
            if (!hasChildren()) {
                return false;
            }
            boolean handled;
            switch (event.getAction() & 255) {
                case 0:
                    handled = onTouchDown(event);
                    break;
                case 1:
                    handled = onTouchUp(event);
                    break;
                case 2:
                    handled = onTouchMove(event);
                    break;
                case 3:
                    handled = onTouchCancel(event);
                    break;
                case 6:
                    handled = onTouchPointerUp(event);
                    break;
                default:
                    handled = false;
                    break;
            }
            notifyTouchMode();
            return handled;
        } else if (isClickable() || isLongClickable()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (!this.mIsAttached) {
            return false;
        }
        int y;
        switch (action & 255) {
            case 0:
                int touchMode = this.mTouchMode;
                int x = (int) ev.getX();
                y = (int) ev.getY();
                this.mActivePointerId = ev.getPointerId(0);
                int motionPosition = findMotionRow(y);
                if (touchMode != 2 && motionPosition >= 0) {
                    this.mMotionX = x;
                    this.mMotionY = y;
                    this.mMotionPosition = motionPosition;
                    this.mTouchMode = 3;
                }
                this.mLastY = Integer.MIN_VALUE;
                initOrResetVelocityTracker();
                this.mVelocityTracker.addMovement(ev);
                if (touchMode == 2) {
                    return true;
                }
                return false;
            case 1:
            case 3:
                this.mTouchMode = 0;
                this.mActivePointerId = -1;
                recycleVelocityTracker();
                reportScrollStateChange(0);
                return false;
            case 2:
                switch (this.mTouchMode) {
                    case 3:
                        int pointerIndex = ev.findPointerIndex(this.mActivePointerId);
                        if (pointerIndex == -1) {
                            pointerIndex = 0;
                            this.mActivePointerId = ev.getPointerId(0);
                        }
                        y = (int) ev.getY(pointerIndex);
                        initVelocityTrackerIfNotExists();
                        this.mVelocityTracker.addMovement(ev);
                        if (startScrollIfNeeded(y)) {
                            return true;
                        }
                        return false;
                    default:
                        return false;
                }
            case 6:
                onSecondaryPointerUp(ev);
                return false;
            default:
                return false;
        }
    }

    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        if (disallowIntercept) {
            recycleVelocityTracker();
        }
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    private boolean onTouchDown(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int motionPosition = pointToPosition(x, y);
        this.mVelocityTracker.clear();
        this.mActivePointerId = MotionEventCompat.getPointerId(event, 0);
        if (this.mTouchMode != 2 && !this.mDataChanged && motionPosition >= 0 && getAdapter().isEnabled(motionPosition)) {
            this.mTouchMode = 3;
            if (this.mPendingCheckForTap == null) {
                this.mPendingCheckForTap = new CheckForTap();
            }
            postDelayed(this.mPendingCheckForTap, (long) ViewConfiguration.getTapTimeout());
            if (event.getEdgeFlags() != 0 && motionPosition < 0) {
                return false;
            }
        } else if (this.mTouchMode == 2) {
            this.mTouchMode = 1;
            this.mMotionCorrection = 0;
            motionPosition = findMotionRow(y);
        }
        this.mMotionX = x;
        this.mMotionY = y;
        this.mMotionPosition = motionPosition;
        this.mLastY = Integer.MIN_VALUE;
        return true;
    }

    private boolean onTouchMove(MotionEvent event) {
        int index = MotionEventCompat.findPointerIndex(event, this.mActivePointerId);
        if (index < 0) {
            Log.e(TAG, "onTouchMove could not find pointer with id " + this.mActivePointerId + " - did ExtendableListView receive an inconsistent " + "event stream?");
            return false;
        }
        int y = (int) MotionEventCompat.getY(event, index);
        if (this.mDataChanged) {
            layoutChildren();
        }
        switch (this.mTouchMode) {
            case 1:
                scrollIfNeeded(y);
                break;
            case 3:
            case 4:
            case 5:
                startScrollIfNeeded(y);
                break;
        }
        return true;
    }

    private boolean onTouchCancel(MotionEvent event) {
        this.mTouchMode = 0;
        setPressed(false);
        invalidate();
        Handler handler = getHandler();
        if (handler != null) {
            handler.removeCallbacks(this.mPendingCheckForLongPress);
        }
        recycleVelocityTracker();
        this.mActivePointerId = -1;
        return true;
    }

    private boolean onTouchUp(MotionEvent event) {
        switch (this.mTouchMode) {
            case 1:
                return onTouchUpScrolling(event);
            case 3:
            case 4:
            case 5:
                return onTouchUpTap(event);
            default:
                setPressed(false);
                invalidate();
                Handler handler = getHandler();
                if (handler != null) {
                    handler.removeCallbacks(this.mPendingCheckForLongPress);
                }
                recycleVelocityTracker();
                this.mActivePointerId = -1;
                return true;
        }
    }

    private boolean onTouchUpScrolling(MotionEvent event) {
        if (hasChildren()) {
            boolean atEdge;
            int top = getFirstChildTop();
            int bottom = getLastChildBottom();
            if (this.mFirstPosition != 0 || top < getListPaddingTop() || this.mFirstPosition + getChildCount() >= this.mItemCount || bottom > getHeight() - getListPaddingBottom()) {
                atEdge = false;
            } else {
                atEdge = true;
            }
            if (!atEdge) {
                this.mVelocityTracker.computeCurrentVelocity(1000, (float) this.mMaximumVelocity);
                float velocity = this.mVelocityTracker.getYVelocity(this.mActivePointerId);
                if (Math.abs(velocity) > ((float) this.mFlingVelocity)) {
                    startFlingRunnable(velocity);
                    this.mTouchMode = 2;
                    this.mMotionY = 0;
                    invalidate();
                    return true;
                }
            }
        }
        stopFlingRunnable();
        recycleVelocityTracker();
        this.mTouchMode = 0;
        return true;
    }

    private boolean onTouchUpTap(MotionEvent event) {
        int motionPosition = this.mMotionPosition;
        if (motionPosition >= 0) {
            final View child = getChildAt(motionPosition);
            if (!(child == null || child.hasFocusable())) {
                if (this.mTouchMode != 3) {
                    child.setPressed(false);
                }
                if (this.mPerformClick == null) {
                    invalidate();
                    this.mPerformClick = new PerformClick();
                }
                final PerformClick performClick = this.mPerformClick;
                performClick.mClickMotionPosition = motionPosition;
                performClick.rememberWindowAttachCount();
                if (this.mTouchMode == 3 || this.mTouchMode == 4) {
                    Handler handler = getHandler();
                    if (handler != null) {
                        handler.removeCallbacks(this.mTouchMode == 3 ? this.mPendingCheckForTap : this.mPendingCheckForLongPress);
                    }
                    this.mLayoutMode = 0;
                    if (this.mDataChanged || motionPosition < 0 || !this.mAdapter.isEnabled(motionPosition)) {
                        this.mTouchMode = 0;
                    } else {
                        this.mTouchMode = 4;
                        layoutChildren();
                        child.setPressed(true);
                        setPressed(true);
                        postDelayed(new Runnable() {
                            public void run() {
                                child.setPressed(false);
                                ExtendableListView.this.setPressed(false);
                                if (!ExtendableListView.this.mDataChanged) {
                                    ExtendableListView.this.post(performClick);
                                }
                                ExtendableListView.this.mTouchMode = 0;
                            }
                        }, (long) ViewConfiguration.getPressedStateDuration());
                    }
                    return true;
                } else if (!this.mDataChanged && motionPosition >= 0 && this.mAdapter.isEnabled(motionPosition)) {
                    post(performClick);
                }
            }
        }
        this.mTouchMode = 0;
        return true;
    }

    private boolean onTouchPointerUp(MotionEvent event) {
        onSecondaryPointerUp(event);
        int x = this.mMotionX;
        int y = this.mMotionY;
        int motionPosition = pointToPosition(x, y);
        if (motionPosition >= 0) {
            this.mMotionPosition = motionPosition;
        }
        this.mLastY = y;
        return true;
    }

    private void onSecondaryPointerUp(MotionEvent event) {
        int pointerIndex = (event.getAction() & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8;
        if (event.getPointerId(pointerIndex) == this.mActivePointerId) {
            int newPointerIndex = pointerIndex == 0 ? 1 : 0;
            this.mMotionX = (int) event.getX(newPointerIndex);
            this.mMotionY = (int) event.getY(newPointerIndex);
            this.mActivePointerId = event.getPointerId(newPointerIndex);
            recycleVelocityTracker();
        }
    }

    private boolean startScrollIfNeeded(int y) {
        int deltaY = y - this.mMotionY;
        if (Math.abs(deltaY) <= this.mTouchSlop) {
            return false;
        }
        int i;
        this.mTouchMode = 1;
        if (deltaY > 0) {
            i = this.mTouchSlop;
        } else {
            i = -this.mTouchSlop;
        }
        this.mMotionCorrection = i;
        Handler handler = getHandler();
        if (handler != null) {
            handler.removeCallbacks(this.mPendingCheckForLongPress);
        }
        setPressed(false);
        View motionView = getChildAt(this.mMotionPosition - this.mFirstPosition);
        if (motionView != null) {
            motionView.setPressed(false);
        }
        ViewParent parent = getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(true);
        }
        scrollIfNeeded(y);
        return true;
    }

    private void scrollIfNeeded(int y) {
        int incrementalDeltaY;
        int rawDeltaY = y - this.mMotionY;
        int deltaY = rawDeltaY - this.mMotionCorrection;
        if (this.mLastY != Integer.MIN_VALUE) {
            incrementalDeltaY = y - this.mLastY;
        } else {
            incrementalDeltaY = deltaY;
        }
        if (this.mTouchMode == 1 && y != this.mLastY) {
            int motionIndex;
            if (Math.abs(rawDeltaY) > this.mTouchSlop) {
                ViewParent parent = getParent();
                if (parent != null) {
                    parent.requestDisallowInterceptTouchEvent(true);
                }
            }
            if (this.mMotionPosition >= 0) {
                motionIndex = this.mMotionPosition - this.mFirstPosition;
            } else {
                motionIndex = getChildCount() / 2;
            }
            boolean atEdge = false;
            if (incrementalDeltaY != 0) {
                atEdge = moveTheChildren(deltaY, incrementalDeltaY);
            }
            if (getChildAt(motionIndex) != null) {
                if (atEdge) {
                    this.mMotionY = y;
                } else {
                    this.mMotionY = y;
                }
            }
            this.mLastY = y;
        }
    }

    private int findMotionRow(int y) {
        int childCount = getChildCount();
        if (childCount > 0) {
            for (int i = 0; i < childCount; i++) {
                if (y <= getChildAt(i).getBottom()) {
                    return this.mFirstPosition + i;
                }
            }
        }
        return -1;
    }

    private boolean moveTheChildren(int deltaY, int incrementalDeltaY) {
        if (!hasChildren()) {
            return true;
        }
        boolean cannotScrollUp;
        int firstTop = getHighestChildTop();
        int lastBottom = getLowestChildBottom();
        int effectivePaddingTop = 0;
        int effectivePaddingBottom = 0;
        if (this.mClipToPadding) {
            effectivePaddingTop = getListPaddingTop();
            effectivePaddingBottom = getListPaddingBottom();
        }
        int gridHeight = getHeight();
        int spaceAbove = effectivePaddingTop - getFirstChildTop();
        int spaceBelow = getLastChildBottom() - (gridHeight - effectivePaddingBottom);
        int height = (gridHeight - getListPaddingBottom()) - getListPaddingTop();
        if (incrementalDeltaY < 0) {
            incrementalDeltaY = Math.max(-(height - 1), incrementalDeltaY);
        } else {
            incrementalDeltaY = Math.min(height - 1, incrementalDeltaY);
        }
        int firstPosition = this.mFirstPosition;
        int maxTop = getListPaddingTop();
        int maxBottom = gridHeight - getListPaddingBottom();
        int childCount = getChildCount();
        boolean cannotScrollDown = firstPosition == 0 && firstTop >= maxTop && incrementalDeltaY >= 0;
        if (firstPosition + childCount != this.mItemCount || lastBottom > maxBottom || incrementalDeltaY > 0) {
            cannotScrollUp = false;
        } else {
            cannotScrollUp = true;
        }
        if (cannotScrollDown) {
            if (incrementalDeltaY != 0) {
                return true;
            }
            return false;
        } else if (cannotScrollUp) {
            return incrementalDeltaY != 0;
        } else {
            boolean isDown = incrementalDeltaY < 0;
            int headerViewsCount = getHeaderViewsCount();
            int footerViewsStart = this.mItemCount - getFooterViewsCount();
            int start = 0;
            int count = 0;
            int i;
            View child;
            int position;
            if (!isDown) {
                int bottom = gridHeight - incrementalDeltaY;
                if (this.mClipToPadding) {
                    bottom -= getListPaddingBottom();
                }
                for (i = childCount - 1; i >= 0; i--) {
                    child = getChildAt(i);
                    if (child.getTop() <= bottom) {
                        break;
                    }
                    start = i;
                    count++;
                    position = firstPosition + i;
                    if (position >= headerViewsCount && position < footerViewsStart) {
                        this.mRecycleBin.addScrapView(child, position);
                    }
                }
            } else {
                int top = -incrementalDeltaY;
                if (this.mClipToPadding) {
                    top += getListPaddingTop();
                }
                for (i = 0; i < childCount; i++) {
                    child = getChildAt(i);
                    if (child.getBottom() >= top) {
                        break;
                    }
                    count++;
                    position = firstPosition + i;
                    if (position >= headerViewsCount && position < footerViewsStart) {
                        this.mRecycleBin.addScrapView(child, position);
                    }
                }
            }
            this.mBlockLayoutRequests = true;
            if (count > 0) {
                detachViewsFromParent(start, count);
                this.mRecycleBin.removeSkippedScrap();
                onChildrenDetached(start, count);
            }
            if (!awakenScrollBars()) {
                invalidate();
            }
            offsetChildrenTopAndBottom(incrementalDeltaY);
            if (isDown) {
                this.mFirstPosition += count;
            }
            int absIncrementalDeltaY = Math.abs(incrementalDeltaY);
            if (spaceAbove < absIncrementalDeltaY || spaceBelow < absIncrementalDeltaY) {
                fillGap(isDown);
            }
            this.mBlockLayoutRequests = false;
            invokeOnItemScrollListener();
            return false;
        }
    }

    protected void onChildrenDetached(int start, int count) {
    }

    protected void fillGap(boolean down) {
        int count = getChildCount();
        int position;
        if (down) {
            position = this.mFirstPosition + count;
            fillDown(position, getChildTop(position));
        } else {
            position = this.mFirstPosition - 1;
            fillUp(position, getChildBottom(position));
        }
        adjustViewsAfterFillGap(down);
    }

    protected void adjustViewsAfterFillGap(boolean down) {
        if (down) {
            correctTooHigh(getChildCount());
        } else {
            correctTooLow(getChildCount());
        }
    }

    private View fillDown(int pos, int nextTop) {
        int end = getHeight();
        if (this.mClipToPadding) {
            end -= getListPaddingBottom();
        }
        while (true) {
            if ((nextTop < end || hasSpaceDown()) && pos < this.mItemCount) {
                makeAndAddView(pos, nextTop, true, false);
                pos++;
                nextTop = getNextChildDownsTop(pos);
            }
        }
        return null;
    }

    protected boolean hasSpaceDown() {
        return false;
    }

    private View fillUp(int pos, int nextBottom) {
        int end;
        if (this.mClipToPadding) {
            end = getListPaddingTop();
        } else {
            end = 0;
        }
        while (true) {
            if ((nextBottom > end || hasSpaceUp()) && pos >= 0) {
                makeAndAddView(pos, nextBottom, false, false);
                pos--;
                nextBottom = getNextChildUpsBottom(pos);
            }
        }
        this.mFirstPosition = pos + 1;
        return null;
    }

    protected boolean hasSpaceUp() {
        return false;
    }

    private View fillFromTop(int nextTop) {
        this.mFirstPosition = Math.min(this.mFirstPosition, this.mItemCount - 1);
        if (this.mFirstPosition < 0) {
            this.mFirstPosition = 0;
        }
        return fillDown(this.mFirstPosition, nextTop);
    }

    private View fillSpecific(int position, int top) {
        View temp = makeAndAddView(position, top, true, false);
        this.mFirstPosition = position;
        int nextBottom = getNextChildUpsBottom(position - 1);
        int nextTop = getNextChildDownsTop(position + 1);
        View above = fillUp(position - 1, nextBottom);
        adjustViewsUpOrDown();
        View below = fillDown(position + 1, nextTop);
        int childCount = getChildCount();
        if (childCount > 0) {
            correctTooHigh(childCount);
        }
        if (null != null) {
            return temp;
        }
        if (above != null) {
            return above;
        }
        return below;
    }

    private View makeAndAddView(int position, int y, boolean flowDown, boolean selected) {
        View child;
        onChildCreated(position, flowDown);
        if (!this.mDataChanged) {
            child = this.mRecycleBin.getActiveView(position);
            if (child != null) {
                setupChild(child, position, y, flowDown, selected, true);
                return child;
            }
        }
        child = obtainView(position, this.mIsScrap);
        setupChild(child, position, y, flowDown, selected, this.mIsScrap[0]);
        return child;
    }

    private void setupChild(View child, int position, int y, boolean flowDown, boolean selected, boolean recycled) {
        LayoutParams layoutParams;
        boolean updateChildSelected = child.isSelected();
        int mode = this.mTouchMode;
        boolean isPressed = mode > 3 && mode < 1 && this.mMotionPosition == position;
        boolean updateChildPressed = isPressed != child.isPressed();
        boolean needToMeasure = !recycled || updateChildSelected || child.isLayoutRequested();
        int itemViewType = this.mAdapter.getItemViewType(position);
        if (itemViewType == -2) {
            layoutParams = generateWrapperLayoutParams(child);
        } else {
            layoutParams = generateChildLayoutParams(child);
        }
        layoutParams.viewType = itemViewType;
        layoutParams.position = position;
        if (recycled || (layoutParams.recycledHeaderFooter && layoutParams.viewType == -2)) {
            attachViewToParent(child, flowDown ? -1 : 0, layoutParams);
        } else {
            if (layoutParams.viewType == -2) {
                layoutParams.recycledHeaderFooter = true;
            }
            addViewInLayout(child, flowDown ? -1 : 0, layoutParams, true);
        }
        if (updateChildSelected) {
            child.setSelected(false);
        }
        if (updateChildPressed) {
            child.setPressed(isPressed);
        }
        if (needToMeasure) {
            onMeasureChild(child, layoutParams);
        } else {
            cleanupLayoutState(child);
        }
        int w = child.getMeasuredWidth();
        int h = child.getMeasuredHeight();
        int childTop = flowDown ? y : y - h;
        int childrenLeft = getChildLeft(position);
        if (needToMeasure) {
            onLayoutChild(child, position, flowDown, childrenLeft, childTop, childrenLeft + w, childTop + h);
        } else {
            onOffsetChild(child, position, flowDown, childrenLeft, childTop);
        }
    }

    protected LayoutParams generateChildLayoutParams(View child) {
        return generateWrapperLayoutParams(child);
    }

    protected LayoutParams generateWrapperLayoutParams(View child) {
        LayoutParams layoutParams = null;
        android.view.ViewGroup.LayoutParams childParams = child.getLayoutParams();
        if (childParams != null) {
            if (childParams instanceof LayoutParams) {
                layoutParams = (LayoutParams) childParams;
            } else {
                layoutParams = new LayoutParams(childParams);
            }
        }
        if (layoutParams == null) {
            return generateDefaultLayoutParams();
        }
        return layoutParams;
    }

    protected void onMeasureChild(View child, LayoutParams layoutParams) {
        int childHeightSpec;
        int childWidthSpec = ViewGroup.getChildMeasureSpec(this.mWidthMeasureSpec, getListPaddingLeft() + getListPaddingRight(), layoutParams.width);
        int lpHeight = layoutParams.height;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, 1073741824);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, 0);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    protected LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -2, 0);
    }

    protected LayoutParams generateHeaderFooterLayoutParams(View child) {
        return new LayoutParams(-1, -2, 0);
    }

    private View obtainView(int position, boolean[] isScrap) {
        isScrap[0] = false;
        View scrapView = this.mRecycleBin.getScrapView(position);
        if (scrapView == null) {
            return this.mAdapter.getView(position, null, this);
        }
        View child = this.mAdapter.getView(position, scrapView, this);
        if (child != scrapView) {
            this.mRecycleBin.addScrapView(scrapView, position);
            return child;
        }
        isScrap[0] = true;
        return child;
    }

    private void correctTooHigh(int childCount) {
        if ((this.mFirstPosition + childCount) - 1 == this.mItemCount - 1 && childCount > 0) {
            int bottomOffset = ((getBottom() - getTop()) - getListPaddingBottom()) - getLowestChildBottom();
            int firstTop = getHighestChildTop();
            if (bottomOffset <= 0) {
                return;
            }
            if (this.mFirstPosition > 0 || firstTop < getListPaddingTop()) {
                if (this.mFirstPosition == 0) {
                    bottomOffset = Math.min(bottomOffset, getListPaddingTop() - firstTop);
                }
                offsetChildrenTopAndBottom(bottomOffset);
                if (this.mFirstPosition > 0) {
                    int previousPosition = this.mFirstPosition - 1;
                    fillUp(previousPosition, getNextChildUpsBottom(previousPosition));
                    adjustViewsUpOrDown();
                }
            }
        }
    }

    private void correctTooLow(int childCount) {
        if (this.mFirstPosition == 0 && childCount > 0) {
            int end = (getTop() - getBottom()) - getListPaddingBottom();
            int topOffset = getHighestChildTop() - getListPaddingTop();
            int lastBottom = getLowestChildBottom();
            int lastPosition = (this.mFirstPosition + childCount) - 1;
            if (topOffset <= 0) {
                return;
            }
            if (lastPosition < this.mItemCount - 1 || lastBottom > end) {
                if (lastPosition == this.mItemCount - 1) {
                    topOffset = Math.min(topOffset, lastBottom - end);
                }
                offsetChildrenTopAndBottom(-topOffset);
                if (lastPosition < this.mItemCount - 1) {
                    int nextPosition = lastPosition + 1;
                    fillDown(nextPosition, getNextChildDownsTop(nextPosition));
                    adjustViewsUpOrDown();
                }
            } else if (lastPosition == this.mItemCount - 1) {
                adjustViewsUpOrDown();
            }
        }
    }

    private void adjustViewsUpOrDown() {
        if (getChildCount() > 0) {
            int delta = getHighestChildTop() - getListPaddingTop();
            if (delta < 0) {
                delta = 0;
            }
            if (delta != 0) {
                offsetChildrenTopAndBottom(-delta);
            }
        }
    }

    protected void onChildCreated(int position, boolean flowDown) {
    }

    protected void onLayoutChild(View child, int position, boolean flowDown, int childrenLeft, int childTop, int childRight, int childBottom) {
        child.layout(childrenLeft, childTop, childRight, childBottom);
    }

    protected void onOffsetChild(View child, int position, boolean flowDown, int childrenLeft, int childTop) {
        child.offsetLeftAndRight(childrenLeft - child.getLeft());
        child.offsetTopAndBottom(childTop - child.getTop());
    }

    protected int getChildLeft(int position) {
        return getListPaddingLeft();
    }

    protected int getChildTop(int position) {
        int count = getChildCount();
        int paddingTop = 0;
        if (this.mClipToPadding) {
            paddingTop = getListPaddingTop();
        }
        return count > 0 ? getChildAt(count - 1).getBottom() : paddingTop;
    }

    protected int getChildBottom(int position) {
        int count = getChildCount();
        int paddingBottom = 0;
        if (this.mClipToPadding) {
            paddingBottom = getListPaddingBottom();
        }
        return count > 0 ? getChildAt(0).getTop() : getHeight() - paddingBottom;
    }

    protected int getNextChildDownsTop(int position) {
        int count = getChildCount();
        return count > 0 ? getChildAt(count - 1).getBottom() : 0;
    }

    protected int getNextChildUpsBottom(int position) {
        int count = getChildCount();
        if (count != 0 && count > 0) {
            return getChildAt(0).getTop();
        }
        return 0;
    }

    protected int getFirstChildTop() {
        return hasChildren() ? getChildAt(0).getTop() : 0;
    }

    protected int getHighestChildTop() {
        return hasChildren() ? getChildAt(0).getTop() : 0;
    }

    protected int getLastChildBottom() {
        return hasChildren() ? getChildAt(getChildCount() - 1).getBottom() : 0;
    }

    protected int getLowestChildBottom() {
        return hasChildren() ? getChildAt(getChildCount() - 1).getBottom() : 0;
    }

    protected boolean hasChildren() {
        return getChildCount() > 0;
    }

    protected void offsetChildrenTopAndBottom(int offset) {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            getChildAt(i).offsetTopAndBottom(offset);
        }
    }

    public int getFirstVisiblePosition() {
        return Math.max(0, this.mFirstPosition - getHeaderViewsCount());
    }

    public int getLastVisiblePosition() {
        return Math.min((this.mFirstPosition + getChildCount()) - 1, this.mAdapter != null ? this.mAdapter.getCount() - 1 : 0);
    }

    private void initOrResetVelocityTracker() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        } else {
            this.mVelocityTracker.clear();
        }
    }

    private void initVelocityTrackerIfNotExists() {
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = VelocityTracker.obtain();
        }
    }

    private void recycleVelocityTracker() {
        if (this.mVelocityTracker != null) {
            this.mVelocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    private void startFlingRunnable(float velocity) {
        if (this.mFlingRunnable == null) {
            this.mFlingRunnable = new FlingRunnable();
        }
        this.mFlingRunnable.start((int) (-velocity));
    }

    private void stopFlingRunnable() {
        if (this.mFlingRunnable != null) {
            this.mFlingRunnable.endFling();
        }
    }

    private void postOnAnimate(Runnable runnable) {
        ViewCompat.postOnAnimation(this, runnable);
    }

    public void notifyTouchMode() {
        switch (this.mTouchMode) {
            case 0:
                reportScrollStateChange(0);
                return;
            case 1:
                reportScrollStateChange(1);
                return;
            case 2:
                reportScrollStateChange(2);
                return;
            default:
                return;
        }
    }

    public void setOnScrollListener(OnScrollListener scrollListener) {
        super.setOnScrollListener(scrollListener);
        this.mOnScrollListener = scrollListener;
    }

    void reportScrollStateChange(int newState) {
        if (newState != this.mScrollState) {
            this.mScrollState = newState;
            if (this.mOnScrollListener != null) {
                this.mOnScrollListener.onScrollStateChanged(this, newState);
            }
        }
    }

    void invokeOnItemScrollListener() {
        if (this.mOnScrollListener != null) {
            this.mOnScrollListener.onScroll(this, this.mFirstPosition, getChildCount(), this.mItemCount);
        }
    }

    private void updateEmptyStatus() {
        boolean empty;
        if (getAdapter() == null || getAdapter().isEmpty()) {
            empty = true;
        } else {
            empty = false;
        }
        if (isInFilterMode()) {
            empty = false;
        }
        View emptyView = getEmptyView();
        if (empty) {
            if (emptyView != null) {
                emptyView.setVisibility(0);
                setVisibility(8);
            } else {
                setVisibility(0);
            }
            if (this.mDataChanged) {
                layout(getLeft(), getTop(), getRight(), getBottom());
                return;
            }
            return;
        }
        if (emptyView != null) {
            emptyView.setVisibility(8);
        }
        setVisibility(0);
    }

    static View retrieveFromScrap(ArrayList<View> scrapViews, int position) {
        int size = scrapViews.size();
        if (size <= 0) {
            return null;
        }
        for (int i = 0; i < size; i++) {
            View view = (View) scrapViews.get(i);
            if (((LayoutParams) view.getLayoutParams()).position == position) {
                scrapViews.remove(i);
                return view;
            }
        }
        return (View) scrapViews.remove(size - 1);
    }

    void rememberSyncState() {
        if (getChildCount() > 0) {
            this.mNeedSync = true;
            this.mSyncHeight = (long) getHeight();
            View v = getChildAt(0);
            ListAdapter adapter = getAdapter();
            if (this.mFirstPosition < 0 || this.mFirstPosition >= adapter.getCount()) {
                this.mSyncRowId = -1;
            } else {
                this.mSyncRowId = adapter.getItemId(this.mFirstPosition);
            }
            if (v != null) {
                this.mSpecificTop = v.getTop();
            }
            this.mSyncPosition = this.mFirstPosition;
        }
    }

    private void clearState() {
        clearRecycledState(this.mHeaderViewInfos);
        clearRecycledState(this.mFooterViewInfos);
        removeAllViewsInLayout();
        this.mFirstPosition = 0;
        this.mDataChanged = false;
        this.mRecycleBin.clear();
        this.mNeedSync = false;
        this.mSyncState = null;
        this.mLayoutMode = 0;
        invalidate();
    }

    private void clearRecycledState(ArrayList<FixedViewInfo> infos) {
        if (infos != null) {
            Iterator it = infos.iterator();
            while (it.hasNext()) {
                android.view.ViewGroup.LayoutParams p = ((FixedViewInfo) it.next()).view.getLayoutParams();
                if (p instanceof LayoutParams) {
                    ((LayoutParams) p).recycledHeaderFooter = false;
                }
            }
        }
    }

    public Parcelable onSaveInstanceState() {
        ListSavedState ss = new ListSavedState(super.onSaveInstanceState());
        if (this.mSyncState != null) {
            ss.selectedId = this.mSyncState.selectedId;
            ss.firstId = this.mSyncState.firstId;
            ss.viewTop = this.mSyncState.viewTop;
            ss.position = this.mSyncState.position;
            ss.height = this.mSyncState.height;
        } else {
            boolean haveChildren;
            if (getChildCount() <= 0 || this.mItemCount <= 0) {
                haveChildren = false;
            } else {
                haveChildren = true;
            }
            ss.selectedId = getSelectedItemId();
            ss.height = getHeight();
            if (!haveChildren || this.mFirstPosition <= 0) {
                ss.viewTop = 0;
                ss.firstId = -1;
                ss.position = 0;
            } else {
                ss.viewTop = getChildAt(0).getTop();
                int firstPos = this.mFirstPosition;
                if (firstPos >= this.mItemCount) {
                    firstPos = this.mItemCount - 1;
                }
                ss.position = firstPos;
                ss.firstId = this.mAdapter.getItemId(firstPos);
            }
        }
        return ss;
    }

    public void onRestoreInstanceState(Parcelable state) {
        ListSavedState ss = (ListSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        this.mDataChanged = true;
        this.mSyncHeight = (long) ss.height;
        if (ss.firstId >= 0) {
            this.mNeedSync = true;
            this.mSyncState = ss;
            this.mSyncRowId = ss.firstId;
            this.mSyncPosition = ss.position;
            this.mSpecificTop = ss.viewTop;
        }
        requestLayout();
    }

    private boolean performLongPress(View child, int longPressPosition, long longPressId) {
        boolean handled = false;
        OnItemLongClickListener onItemLongClickListener = getOnItemLongClickListener();
        if (onItemLongClickListener != null) {
            handled = onItemLongClickListener.onItemLongClick(this, child, longPressPosition, longPressId);
        }
        if (handled) {
            performHapticFeedback(0);
        }
        return handled;
    }
}
