package com.MCWorld.framework.base.widget.pager;

public interface IPagerPosition {
    void onPageScrollComplete(int i);

    void onSelected(int i);

    void onUnSelected(int i);

    void setPosition(int i);
}
