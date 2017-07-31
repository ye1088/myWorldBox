package com.huluxia.framework.base.widget.hlistview;

import android.view.VelocityTracker;
import android.view.View;

class AbsHListView$FlingRunnable implements Runnable {
    private static final int FLYWHEEL_TIMEOUT = 40;
    private final Runnable mCheckFlywheel = new Runnable() {
        public void run() {
            int activeId = AbsHListView.access$800(AbsHListView$FlingRunnable.this.this$0);
            VelocityTracker vt = AbsHListView.access$900(AbsHListView$FlingRunnable.this.this$0);
            OverScroller scroller = AbsHListView$FlingRunnable.this.mScroller;
            if (vt != null && activeId != -1) {
                vt.computeCurrentVelocity(1000, (float) AbsHListView.access$1100(AbsHListView$FlingRunnable.this.this$0));
                float xvel = -vt.getXVelocity(activeId);
                if (Math.abs(xvel) < ((float) AbsHListView.access$1200(AbsHListView$FlingRunnable.this.this$0)) || !scroller.isScrollingInDirection(xvel, 0.0f)) {
                    AbsHListView$FlingRunnable.this.endFling();
                    AbsHListView$FlingRunnable.this.this$0.mTouchMode = 3;
                    AbsHListView$FlingRunnable.this.this$0.reportScrollStateChange(1);
                    return;
                }
                AbsHListView$FlingRunnable.this.this$0.postDelayed(this, 40);
            }
        }
    };
    private int mLastFlingX;
    private final OverScroller mScroller;
    final /* synthetic */ AbsHListView this$0;

    AbsHListView$FlingRunnable(AbsHListView absHListView) {
        this.this$0 = absHListView;
        this.mScroller = new OverScroller(absHListView.getContext());
    }

    void start(int initialVelocity) {
        int initialX;
        if (initialVelocity < 0) {
            initialX = Integer.MAX_VALUE;
        } else {
            initialX = 0;
        }
        this.mLastFlingX = initialX;
        this.mScroller.setInterpolator(null);
        this.mScroller.fling(initialX, 0, initialVelocity, 0, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
        this.this$0.mTouchMode = 4;
        this.this$0.mViewHelper.postOnAnimation(this);
    }

    void startSpringback() {
        if (this.mScroller.springBack(this.this$0.getScrollX(), 0, 0, 0, 0, 0)) {
            this.this$0.mTouchMode = 6;
            this.this$0.invalidate();
            this.this$0.mViewHelper.postOnAnimation(this);
            return;
        }
        this.this$0.mTouchMode = -1;
        this.this$0.reportScrollStateChange(0);
    }

    void startOverfling(int initialVelocity) {
        this.mScroller.setInterpolator(null);
        this.mScroller.fling(this.this$0.getScrollX(), 0, initialVelocity, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0, this.this$0.getWidth(), 0);
        this.this$0.mTouchMode = 6;
        this.this$0.invalidate();
        this.this$0.mViewHelper.postOnAnimation(this);
    }

    void edgeReached(int delta) {
        this.mScroller.notifyHorizontalEdgeReached(this.this$0.getScrollX(), 0, this.this$0.mOverflingDistance);
        int overscrollMode = this.this$0.getOverScrollMode();
        if (overscrollMode == 0 || (overscrollMode == 1 && !AbsHListView.access$1300(this.this$0))) {
            this.this$0.mTouchMode = 6;
            int vel = (int) this.mScroller.getCurrVelocity();
            if (delta > 0) {
                AbsHListView.access$1400(this.this$0).onAbsorb(vel);
            } else {
                AbsHListView.access$1500(this.this$0).onAbsorb(vel);
            }
        } else {
            this.this$0.mTouchMode = -1;
            if (this.this$0.mPositionScroller != null) {
                this.this$0.mPositionScroller.stop();
            }
        }
        this.this$0.invalidate();
        this.this$0.mViewHelper.postOnAnimation(this);
    }

    void startScroll(int distance, int duration, boolean linear) {
        int initialX;
        if (distance < 0) {
            initialX = Integer.MAX_VALUE;
        } else {
            initialX = 0;
        }
        this.mLastFlingX = initialX;
        this.mScroller.setInterpolator(linear ? AbsHListView.sLinearInterpolator : null);
        this.mScroller.startScroll(initialX, 0, distance, 0, duration);
        this.this$0.mTouchMode = 4;
        this.this$0.mViewHelper.postOnAnimation(this);
    }

    void endFling() {
        this.this$0.mTouchMode = -1;
        this.this$0.removeCallbacks(this);
        this.this$0.removeCallbacks(this.mCheckFlywheel);
        this.this$0.reportScrollStateChange(0);
        AbsHListView.access$1600(this.this$0);
        this.mScroller.abortAnimation();
        AbsHListView.access$1700(this.this$0, 0, 0, 0, 0, 0, 0, 0, 0, false);
    }

    void flywheelTouch() {
        this.this$0.postDelayed(this.mCheckFlywheel, 40);
    }

    public void run() {
        OverScroller scroller;
        switch (this.this$0.mTouchMode) {
            case 3:
                if (this.mScroller.isFinished()) {
                    return;
                }
                break;
            case 4:
                break;
            case 6:
                scroller = this.mScroller;
                if (scroller.computeScrollOffset()) {
                    int scrollX = this.this$0.getScrollX();
                    int currX = scroller.getCurrX();
                    if (AbsHListView.access$1900(this.this$0, currX - scrollX, 0, scrollX, 0, 0, 0, this.this$0.mOverflingDistance, 0, false)) {
                        boolean crossRight = scrollX <= 0 && currX > 0;
                        boolean crossLeft = scrollX >= 0 && currX < 0;
                        if (crossRight || crossLeft) {
                            int velocity = (int) scroller.getCurrVelocity();
                            if (crossLeft) {
                                velocity = -velocity;
                            }
                            scroller.abortAnimation();
                            start(velocity);
                            return;
                        }
                        startSpringback();
                        return;
                    }
                    this.this$0.invalidate();
                    this.this$0.mViewHelper.postOnAnimation(this);
                    return;
                }
                endFling();
                return;
            default:
                endFling();
                return;
        }
        if (this.this$0.mDataChanged) {
            this.this$0.layoutChildren();
        }
        if (this.this$0.mItemCount == 0 || this.this$0.getChildCount() == 0) {
            endFling();
            return;
        }
        scroller = this.mScroller;
        boolean more = scroller.computeScrollOffset();
        int x = scroller.getCurrX();
        int delta = this.mLastFlingX - x;
        if (delta > 0) {
            this.this$0.mMotionPosition = this.this$0.mFirstPosition;
            this.this$0.mMotionViewOriginalLeft = this.this$0.getChildAt(0).getLeft();
            delta = Math.min(((this.this$0.getWidth() - this.this$0.getPaddingRight()) - this.this$0.getPaddingLeft()) - 1, delta);
        } else {
            int offsetToLast = this.this$0.getChildCount() - 1;
            this.this$0.mMotionPosition = this.this$0.mFirstPosition + offsetToLast;
            this.this$0.mMotionViewOriginalLeft = this.this$0.getChildAt(offsetToLast).getLeft();
            delta = Math.max(-(((this.this$0.getWidth() - this.this$0.getPaddingRight()) - this.this$0.getPaddingLeft()) - 1), delta);
        }
        View motionView = this.this$0.getChildAt(this.this$0.mMotionPosition - this.this$0.mFirstPosition);
        int oldLeft = 0;
        if (motionView != null) {
            oldLeft = motionView.getLeft();
        }
        boolean atEdge = this.this$0.trackMotionScroll(delta, delta);
        boolean atEnd = atEdge && delta != 0;
        if (atEnd) {
            if (motionView != null) {
                AbsHListView.access$1800(this.this$0, -(delta - (motionView.getLeft() - oldLeft)), 0, this.this$0.getScrollX(), 0, 0, 0, this.this$0.mOverflingDistance, 0, false);
            }
            if (more) {
                edgeReached(delta);
            }
        } else if (!more || atEnd) {
            endFling();
        } else {
            if (atEdge) {
                this.this$0.invalidate();
            }
            this.mLastFlingX = x;
            this.this$0.mViewHelper.postOnAnimation(this);
        }
    }
}
