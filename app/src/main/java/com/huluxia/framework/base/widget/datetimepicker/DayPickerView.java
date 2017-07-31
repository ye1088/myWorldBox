package com.huluxia.framework.base.widget.datetimepicker;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import com.huluxia.framework.base.widget.datetimepicker.SimpleMonthAdapter.CalendarDay;

public class DayPickerView extends ListView implements OnScrollListener, OnDateChangedListener {
    protected static final int GOTO_SCROLL_DURATION = 250;
    public static int LIST_TOP_OFFSET = -1;
    protected static final int SCROLL_CHANGE_DELAY = 40;
    protected SimpleMonthAdapter mAdapter;
    protected Context mContext;
    private final DatePickerController mController;
    protected int mCurrentMonthDisplayed;
    protected int mCurrentScrollState = 0;
    protected int mDaysPerWeek = 7;
    protected float mFriction = 1.0f;
    protected Handler mHandler = new Handler();
    protected int mNumWeeks = 6;
    private boolean mPerformingScroll;
    protected long mPreviousScrollPosition;
    protected int mPreviousScrollState = 0;
    protected ScrollStateRunnable mScrollStateChangedRunnable = new ScrollStateRunnable();
    protected CalendarDay mSelectedDay = new CalendarDay();
    protected boolean mShowWeekNumber = false;
    protected CalendarDay mTempDay = new CalendarDay();

    protected class ScrollStateRunnable implements Runnable {
        private int mNewState;

        protected ScrollStateRunnable() {
        }

        public void doScrollStateChange(AbsListView view, int scrollState) {
            DayPickerView.this.mHandler.removeCallbacks(this);
            this.mNewState = scrollState;
            DayPickerView.this.mHandler.postDelayed(this, 40);
        }

        public void run() {
            boolean scroll = true;
            DayPickerView.this.mCurrentScrollState = this.mNewState;
            if (this.mNewState != 0 || DayPickerView.this.mPreviousScrollState == 0 || DayPickerView.this.mPreviousScrollState == 1) {
                DayPickerView.this.mPreviousScrollState = this.mNewState;
                return;
            }
            DayPickerView.this.mPreviousScrollState = this.mNewState;
            int i = 0;
            View child = DayPickerView.this.getChildAt(0);
            while (child != null && child.getBottom() <= 0) {
                i++;
                child = DayPickerView.this.getChildAt(i);
            }
            if (child != null) {
                int firstPosition = DayPickerView.this.getFirstVisiblePosition();
                int lastPosition = DayPickerView.this.getLastVisiblePosition();
                if (firstPosition == 0 || lastPosition == DayPickerView.this.getCount() - 1) {
                    scroll = false;
                }
                int top = child.getTop();
                int bottom = child.getBottom();
                int midpoint = DayPickerView.this.getHeight() / 2;
                if (scroll && top < DayPickerView.LIST_TOP_OFFSET) {
                    if (bottom > midpoint) {
                        DayPickerView.this.smoothScrollBy(top, 250);
                    } else {
                        DayPickerView.this.smoothScrollBy(bottom, 250);
                    }
                }
            }
        }
    }

    public DayPickerView(Context context, DatePickerController datePickerController) {
        super(context);
        this.mController = datePickerController;
        this.mController.registerOnDateChangedListener(this);
        setLayoutParams(new LayoutParams(-1, -1));
        setDrawSelectorOnTop(false);
        init(context);
        onDateChanged();
    }

    public int getMostVisiblePosition() {
        int firstPosition = getFirstVisiblePosition();
        int height = getHeight();
        int maxDisplayedHeight = 0;
        int mostVisibleIndex = 0;
        int i = 0;
        int bottom = 0;
        while (bottom < height) {
            View child = getChildAt(i);
            if (child == null) {
                break;
            }
            bottom = child.getBottom();
            int displayedHeight = Math.min(bottom, height) - Math.max(0, child.getTop());
            if (displayedHeight > maxDisplayedHeight) {
                mostVisibleIndex = i;
                maxDisplayedHeight = displayedHeight;
            }
            i++;
        }
        return firstPosition + mostVisibleIndex;
    }

    @TargetApi(11)
    public boolean goTo(CalendarDay day, boolean animate, boolean setSelected, boolean forceScroll) {
        int selectedPosition;
        if (setSelected) {
            this.mSelectedDay.set(day);
        }
        this.mTempDay.set(day);
        int position = ((day.year - this.mController.getMinYear()) * 12) + day.month;
        int i = 0;
        while (true) {
            int i2 = i + 1;
            View child = getChildAt(i);
            if (child != null && child.getTop() < 0) {
                i = i2;
            }
        }
        if (child != null) {
            selectedPosition = getPositionForView(child);
        } else {
            selectedPosition = 0;
        }
        if (setSelected) {
            this.mAdapter.setSelectedDay(this.mSelectedDay);
        }
        if (position != selectedPosition || forceScroll) {
            setMonthDisplayed(this.mTempDay);
            this.mPreviousScrollState = 2;
            if (!animate || VERSION.SDK_INT < 11) {
                postSetSelection(position);
            } else {
                smoothScrollToPositionFromTop(position, LIST_TOP_OFFSET, 250);
                return true;
            }
        } else if (setSelected) {
            setMonthDisplayed(this.mSelectedDay);
        }
        return false;
    }

    public void init(Context paramContext) {
        this.mContext = paramContext;
        setUpListView();
        setUpAdapter();
        setAdapter(this.mAdapter);
    }

    protected void layoutChildren() {
        super.layoutChildren();
        if (this.mPerformingScroll) {
            this.mPerformingScroll = false;
        }
    }

    public void onChange() {
        setUpAdapter();
        setAdapter(this.mAdapter);
    }

    public void onDateChanged() {
        goTo(this.mController.getSelectedDay(), false, true, true);
    }

    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        SimpleMonthView child = (SimpleMonthView) view.getChildAt(0);
        if (child != null) {
            this.mPreviousScrollPosition = (long) ((view.getFirstVisiblePosition() * child.getHeight()) - child.getBottom());
            this.mPreviousScrollState = this.mCurrentScrollState;
        }
    }

    public void onScrollStateChanged(AbsListView absListView, int scroll) {
        this.mScrollStateChangedRunnable.doScrollStateChange(absListView, scroll);
    }

    public void postSetSelection(final int position) {
        clearFocus();
        post(new Runnable() {
            public void run() {
                DayPickerView.this.setSelection(position);
            }
        });
        onScrollStateChanged(this, 0);
    }

    protected void setMonthDisplayed(CalendarDay calendarDay) {
        this.mCurrentMonthDisplayed = calendarDay.month;
        invalidateViews();
    }

    protected void setUpAdapter() {
        if (this.mAdapter == null) {
            this.mAdapter = new SimpleMonthAdapter(getContext(), this.mController);
        }
        this.mAdapter.setSelectedDay(this.mSelectedDay);
        this.mAdapter.notifyDataSetChanged();
    }

    protected void setUpListView() {
        setCacheColorHint(0);
        setDivider(null);
        setItemsCanFocus(true);
        setFastScrollEnabled(false);
        setVerticalScrollBarEnabled(false);
        setOnScrollListener(this);
        setFadingEdgeLength(0);
        setFrictionIfSupported(ViewConfiguration.getScrollFriction() * this.mFriction);
    }

    @TargetApi(11)
    void setFrictionIfSupported(float friction) {
        if (VERSION.SDK_INT >= 11) {
            setFriction(friction);
        }
    }
}
