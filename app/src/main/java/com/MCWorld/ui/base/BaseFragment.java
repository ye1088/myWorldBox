package com.MCWorld.ui.base;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import com.MCWorld.framework.base.widget.pager.PagerFragment;

public class BaseFragment extends PagerFragment {
    public void Fx() {
        View v = getActivity().getCurrentFocus();
        if (v != null) {
            f(v);
        }
    }

    public void f(View v) {
        getActivity().getWindow().setSoftInputMode(20);
        ((InputMethodManager) getActivity().getSystemService("input_method")).hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void g(View vv) {
        View v = vv;
        if (v == null) {
            v = getActivity().getCurrentFocus();
            if (v == null) {
                return;
            }
        }
        ((InputMethodManager) getActivity().getSystemService("input_method")).showSoftInput(v, 2);
    }
}
