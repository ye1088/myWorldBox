package com.MCWorld.framework.base.widget.hlistview;

class HListView$ArrowScrollFocusResult {
    private int mAmountToScroll;
    private int mSelectedPosition;

    private HListView$ArrowScrollFocusResult() {
    }

    void populate(int selectedPosition, int amountToScroll) {
        this.mSelectedPosition = selectedPosition;
        this.mAmountToScroll = amountToScroll;
    }

    public int getSelectedPosition() {
        return this.mSelectedPosition;
    }

    public int getAmountToScroll() {
        return this.mAmountToScroll;
    }
}
