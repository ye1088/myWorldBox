package com.MCWorld.framework.base.widget.hlistview;

import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;

public class AbsHListView$PositionScroller implements Runnable {
    private static final int MOVE_DOWN_BOUND = 3;
    private static final int MOVE_DOWN_POS = 1;
    private static final int MOVE_OFFSET = 5;
    private static final int MOVE_UP_BOUND = 4;
    private static final int MOVE_UP_POS = 2;
    private static final int SCROLL_DURATION = 200;
    private int mBoundPos;
    private final int mExtraScroll;
    private int mLastSeenPos;
    private int mMode;
    private int mOffsetFromLeft;
    private int mScrollDuration;
    private int mTargetPos;
    final /* synthetic */ AbsHListView this$0;

    AbsHListView$PositionScroller(AbsHListView this$0) {
        this.this$0 = this$0;
        this.mExtraScroll = ViewConfiguration.get(this$0.getContext()).getScaledFadingEdgeLength();
    }

    void start(final int position) {
        stop();
        if (this.this$0.mDataChanged) {
            this.this$0.mPositionScrollAfterLayout = new Runnable() {
                public void run() {
                    AbsHListView$PositionScroller.this.start(position);
                }
            };
            return;
        }
        int childCount = this.this$0.getChildCount();
        if (childCount != 0) {
            int viewTravelCount;
            int firstPos = this.this$0.mFirstPosition;
            int lastPos = (firstPos + childCount) - 1;
            int clampedPosition = Math.max(0, Math.min(this.this$0.getCount() - 1, position));
            if (clampedPosition < firstPos) {
                viewTravelCount = (firstPos - clampedPosition) + 1;
                this.mMode = 2;
            } else if (clampedPosition > lastPos) {
                viewTravelCount = (clampedPosition - lastPos) + 1;
                this.mMode = 1;
            } else {
                scrollToVisible(clampedPosition, -1, 200);
                return;
            }
            if (viewTravelCount > 0) {
                this.mScrollDuration = 200 / viewTravelCount;
            } else {
                this.mScrollDuration = 200;
            }
            this.mTargetPos = clampedPosition;
            this.mBoundPos = -1;
            this.mLastSeenPos = -1;
            this.this$0.mViewHelper.postOnAnimation(this);
        }
    }

    void start(final int position, final int boundPosition) {
        stop();
        if (boundPosition == -1) {
            start(position);
        } else if (this.this$0.mDataChanged) {
            this.this$0.mPositionScrollAfterLayout = new Runnable() {
                public void run() {
                    AbsHListView$PositionScroller.this.start(position, boundPosition);
                }
            };
        } else {
            int childCount = this.this$0.getChildCount();
            if (childCount != 0) {
                int viewTravelCount;
                int firstPos = this.this$0.mFirstPosition;
                int lastPos = (firstPos + childCount) - 1;
                int clampedPosition = Math.max(0, Math.min(this.this$0.getCount() - 1, position));
                int posTravel;
                int boundTravel;
                if (clampedPosition < firstPos) {
                    int boundPosFromLast = lastPos - boundPosition;
                    if (boundPosFromLast >= 1) {
                        posTravel = (firstPos - clampedPosition) + 1;
                        boundTravel = boundPosFromLast - 1;
                        if (boundTravel < posTravel) {
                            viewTravelCount = boundTravel;
                            this.mMode = 4;
                        } else {
                            viewTravelCount = posTravel;
                            this.mMode = 2;
                        }
                    } else {
                        return;
                    }
                } else if (clampedPosition > lastPos) {
                    int boundPosFromFirst = boundPosition - firstPos;
                    if (boundPosFromFirst >= 1) {
                        posTravel = (clampedPosition - lastPos) + 1;
                        boundTravel = boundPosFromFirst - 1;
                        if (boundTravel < posTravel) {
                            viewTravelCount = boundTravel;
                            this.mMode = 3;
                        } else {
                            viewTravelCount = posTravel;
                            this.mMode = 1;
                        }
                    } else {
                        return;
                    }
                } else {
                    scrollToVisible(clampedPosition, boundPosition, 200);
                    return;
                }
                if (viewTravelCount > 0) {
                    this.mScrollDuration = 200 / viewTravelCount;
                } else {
                    this.mScrollDuration = 200;
                }
                this.mTargetPos = clampedPosition;
                this.mBoundPos = boundPosition;
                this.mLastSeenPos = -1;
                this.this$0.mViewHelper.postOnAnimation(this);
            }
        }
    }

    void startWithOffset(int position, int offset) {
        startWithOffset(position, offset, 200);
    }

    void startWithOffset(final int position, int offset, final int duration) {
        stop();
        if (this.this$0.mDataChanged) {
            final int postOffset = offset;
            this.this$0.mPositionScrollAfterLayout = new Runnable() {
                public void run() {
                    AbsHListView$PositionScroller.this.startWithOffset(position, postOffset, duration);
                }
            };
            return;
        }
        int childCount = this.this$0.getChildCount();
        if (childCount != 0) {
            int viewTravelCount;
            offset += this.this$0.getPaddingLeft();
            this.mTargetPos = Math.max(0, Math.min(this.this$0.getCount() - 1, position));
            this.mOffsetFromLeft = offset;
            this.mBoundPos = -1;
            this.mLastSeenPos = -1;
            this.mMode = 5;
            int firstPos = this.this$0.mFirstPosition;
            int lastPos = (firstPos + childCount) - 1;
            if (this.mTargetPos < firstPos) {
                viewTravelCount = firstPos - this.mTargetPos;
            } else if (this.mTargetPos > lastPos) {
                viewTravelCount = this.mTargetPos - lastPos;
            } else {
                this.this$0.smoothScrollBy(this.this$0.getChildAt(this.mTargetPos - firstPos).getLeft() - offset, duration, false);
                return;
            }
            float screenTravelCount = ((float) viewTravelCount) / ((float) childCount);
            if (screenTravelCount >= 1.0f) {
                duration = (int) (((float) duration) / screenTravelCount);
            }
            this.mScrollDuration = duration;
            this.mLastSeenPos = -1;
            this.this$0.mViewHelper.postOnAnimation(this);
        }
    }

    void scrollToVisible(int targetPos, int boundPos, int duration) {
        int firstPos = this.this$0.mFirstPosition;
        int lastPos = (firstPos + this.this$0.getChildCount()) - 1;
        int paddedLeft = this.this$0.mListPadding.left;
        int paddedRight = this.this$0.getWidth() - this.this$0.mListPadding.right;
        if (targetPos < firstPos || targetPos > lastPos) {
            Log.w("AbsListView", "scrollToVisible called with targetPos " + targetPos + " not visible [" + firstPos + ", " + lastPos + "]");
        }
        if (boundPos < firstPos || boundPos > lastPos) {
            boundPos = -1;
        }
        View targetChild = this.this$0.getChildAt(targetPos - firstPos);
        int targetLeft = targetChild.getLeft();
        int targetRight = targetChild.getRight();
        int scrollBy = 0;
        if (targetRight > paddedRight) {
            scrollBy = targetRight - paddedRight;
        }
        if (targetLeft < paddedLeft) {
            scrollBy = targetLeft - paddedLeft;
        }
        if (scrollBy != 0) {
            if (boundPos >= 0) {
                View boundChild = this.this$0.getChildAt(boundPos - firstPos);
                int boundLeft = boundChild.getLeft();
                int boundRight = boundChild.getRight();
                int absScroll = Math.abs(scrollBy);
                if (scrollBy < 0 && boundRight + absScroll > paddedRight) {
                    scrollBy = Math.max(0, boundRight - paddedRight);
                } else if (scrollBy > 0 && boundLeft - absScroll < paddedLeft) {
                    scrollBy = Math.min(0, boundLeft - paddedLeft);
                }
            }
            this.this$0.smoothScrollBy(scrollBy, duration);
        }
    }

    public void stop() {
        this.this$0.removeCallbacks(this);
    }

    public void run() {
        int listWidth = this.this$0.getWidth();
        int firstPos = this.this$0.mFirstPosition;
        int lastViewIndex;
        int lastPos;
        View lastView;
        int lastViewWidth;
        int lastViewPixelsShowing;
        int extraScroll;
        int childCount;
        switch (this.mMode) {
            case 1:
                lastViewIndex = this.this$0.getChildCount() - 1;
                lastPos = firstPos + lastViewIndex;
                if (lastViewIndex < 0) {
                    return;
                }
                if (lastPos == this.mLastSeenPos) {
                    this.this$0.mViewHelper.postOnAnimation(this);
                    return;
                }
                lastView = this.this$0.getChildAt(lastViewIndex);
                lastViewWidth = lastView.getWidth();
                lastViewPixelsShowing = listWidth - lastView.getLeft();
                if (lastPos < this.this$0.mItemCount - 1) {
                    extraScroll = Math.max(this.this$0.mListPadding.right, this.mExtraScroll);
                } else {
                    extraScroll = this.this$0.mListPadding.right;
                }
                int scrollBy = (lastViewWidth - lastViewPixelsShowing) + extraScroll;
                this.this$0.smoothScrollBy(scrollBy, this.mScrollDuration, true);
                this.mLastSeenPos = lastPos;
                if (lastPos < this.mTargetPos) {
                    this.this$0.mViewHelper.postOnAnimation(this);
                    return;
                }
                return;
            case 2:
                if (firstPos == this.mLastSeenPos) {
                    this.this$0.mViewHelper.postOnAnimation(this);
                    return;
                }
                View firstView = this.this$0.getChildAt(0);
                if (firstView != null) {
                    int firstViewLeft = firstView.getLeft();
                    if (firstPos > 0) {
                        extraScroll = Math.max(this.mExtraScroll, this.this$0.mListPadding.left);
                    } else {
                        extraScroll = this.this$0.mListPadding.left;
                    }
                    this.this$0.smoothScrollBy(firstViewLeft - extraScroll, this.mScrollDuration, true);
                    this.mLastSeenPos = firstPos;
                    if (firstPos > this.mTargetPos) {
                        this.this$0.mViewHelper.postOnAnimation(this);
                        return;
                    }
                    return;
                }
                return;
            case 3:
                childCount = this.this$0.getChildCount();
                if (firstPos != this.mBoundPos && childCount > 1 && firstPos + childCount < this.this$0.mItemCount) {
                    int nextPos = firstPos + 1;
                    if (nextPos == this.mLastSeenPos) {
                        this.this$0.mViewHelper.postOnAnimation(this);
                        return;
                    }
                    View nextView = this.this$0.getChildAt(1);
                    int nextViewWidth = nextView.getWidth();
                    int nextViewLeft = nextView.getLeft();
                    extraScroll = Math.max(this.this$0.mListPadding.right, this.mExtraScroll);
                    if (nextPos < this.mBoundPos) {
                        this.this$0.smoothScrollBy(Math.max(0, (nextViewWidth + nextViewLeft) - extraScroll), this.mScrollDuration, true);
                        this.mLastSeenPos = nextPos;
                        this.this$0.mViewHelper.postOnAnimation(this);
                        return;
                    } else if (nextViewLeft > extraScroll) {
                        this.this$0.smoothScrollBy(nextViewLeft - extraScroll, this.mScrollDuration, true);
                        return;
                    } else {
                        return;
                    }
                }
                return;
            case 4:
                lastViewIndex = this.this$0.getChildCount() - 2;
                if (lastViewIndex >= 0) {
                    lastPos = firstPos + lastViewIndex;
                    if (lastPos == this.mLastSeenPos) {
                        this.this$0.mViewHelper.postOnAnimation(this);
                        return;
                    }
                    lastView = this.this$0.getChildAt(lastViewIndex);
                    lastViewWidth = lastView.getWidth();
                    int lastViewLeft = lastView.getLeft();
                    lastViewPixelsShowing = listWidth - lastViewLeft;
                    extraScroll = Math.max(this.this$0.mListPadding.left, this.mExtraScroll);
                    this.mLastSeenPos = lastPos;
                    if (lastPos > this.mBoundPos) {
                        this.this$0.smoothScrollBy(-(lastViewPixelsShowing - extraScroll), this.mScrollDuration, true);
                        this.this$0.mViewHelper.postOnAnimation(this);
                        return;
                    }
                    int right = listWidth - extraScroll;
                    int lastViewRight = lastViewLeft + lastViewWidth;
                    if (right > lastViewRight) {
                        this.this$0.smoothScrollBy(-(right - lastViewRight), this.mScrollDuration, true);
                        return;
                    }
                    return;
                }
                return;
            case 5:
                if (this.mLastSeenPos == firstPos) {
                    this.this$0.mViewHelper.postOnAnimation(this);
                    return;
                }
                this.mLastSeenPos = firstPos;
                childCount = this.this$0.getChildCount();
                int position = this.mTargetPos;
                lastPos = (firstPos + childCount) - 1;
                int viewTravelCount = 0;
                if (position < firstPos) {
                    viewTravelCount = (firstPos - position) + 1;
                } else if (position > lastPos) {
                    viewTravelCount = position - lastPos;
                }
                float modifier = Math.min(Math.abs(((float) viewTravelCount) / ((float) childCount)), 1.0f);
                if (position < firstPos) {
                    this.this$0.smoothScrollBy((int) (((float) (-this.this$0.getWidth())) * modifier), (int) (((float) this.mScrollDuration) * modifier), true);
                    this.this$0.mViewHelper.postOnAnimation(this);
                    return;
                } else if (position > lastPos) {
                    this.this$0.smoothScrollBy((int) (((float) this.this$0.getWidth()) * modifier), (int) (((float) this.mScrollDuration) * modifier), true);
                    this.this$0.mViewHelper.postOnAnimation(this);
                    return;
                } else {
                    int distance = this.this$0.getChildAt(position - firstPos).getLeft() - this.mOffsetFromLeft;
                    this.this$0.smoothScrollBy(distance, (int) (((float) this.mScrollDuration) * (((float) Math.abs(distance)) / ((float) this.this$0.getWidth()))), true);
                    return;
                }
            default:
                return;
        }
    }
}
