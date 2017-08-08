package com.MCWorld.framework.base.widget.pager;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class PagerFragment extends Fragment implements IPagerPosition {
    private int mPos = -1;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setPosition(int position) {
        this.mPos = position;
    }

    public void onSelected(int position) {
    }

    public void onUnSelected(int position) {
    }

    public void onPageScrollComplete(int position) {
    }
}
