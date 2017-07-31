package com.huluxia.widget.wheel;

import android.view.View;
import android.widget.LinearLayout;
import java.util.LinkedList;
import java.util.List;

/* compiled from: WheelRecycle */
public class f {
    private List<View> bFM;
    private WheelView bFN;
    private List<View> items;

    public f(WheelView wheel) {
        this.bFN = wheel;
    }

    public int a(LinearLayout layout, int firstItem, a range) {
        int index = firstItem;
        int i = 0;
        while (i < layout.getChildCount()) {
            if (range.contains(index)) {
                i++;
            } else {
                g(layout.getChildAt(i), index);
                layout.removeViewAt(i);
                if (i == 0) {
                    firstItem++;
                }
            }
            index++;
        }
        return firstItem;
    }

    public View Qc() {
        return O(this.items);
    }

    public View Qd() {
        return O(this.bFM);
    }

    public void clearAll() {
        if (this.items != null) {
            this.items.clear();
        }
        if (this.bFM != null) {
            this.bFM.clear();
        }
    }

    private List<View> a(View view, List<View> cache) {
        if (cache == null) {
            cache = new LinkedList();
        }
        cache.add(view);
        return cache;
    }

    private void g(View view, int index) {
        int count = this.bFN.getViewAdapter().Qa();
        if ((index < 0 || index >= count) && !this.bFN.Qo()) {
            this.bFM = a(view, this.bFM);
            return;
        }
        while (index < 0) {
            index += count;
        }
        index %= count;
        this.items = a(view, this.items);
    }

    private View O(List<View> cache) {
        if (cache == null || cache.size() <= 0) {
            return null;
        }
        View view = (View) cache.get(0);
        cache.remove(0);
        return view;
    }
}
