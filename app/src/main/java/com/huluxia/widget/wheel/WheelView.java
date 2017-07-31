package com.huluxia.widget.wheel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import com.huluxia.bbs.b;
import com.huluxia.widget.wheel.adapters.f;
import com.huluxia.widget.wheel.g.a;
import java.util.LinkedList;
import java.util.List;

public class WheelView extends View {
    private static final int[] bGb = new int[]{-15658735, 11184810, 11184810};
    private static final int bGc = 10;
    private static final int bGd = 10;
    private static final int bGe = 5;
    private int bFA = 0;
    private boolean bFV;
    private int bGf = 5;
    private int bGg = 0;
    private Drawable bGh;
    private GradientDrawable bGi;
    private GradientDrawable bGj;
    private g bGk;
    private int bGl;
    boolean bGm = false;
    private LinearLayout bGn;
    private int bGo;
    private f bGp;
    private f bGq = new f(this);
    private List<b> bGr = new LinkedList();
    private List<d> bGs = new LinkedList();
    private List<c> bGt = new LinkedList();
    a bGu = new a(this) {
        final /* synthetic */ WheelView bGw;

        {
            this.bGw = this$0;
        }

        public void Qj() {
            this.bGw.bFV = true;
            this.bGw.Qm();
        }

        public void mr(int distance) {
            this.bGw.mt(distance);
            int height = this.bGw.getHeight();
            if (this.bGw.bGl > height) {
                this.bGw.bGl = height;
                this.bGw.bGk.Qe();
            } else if (this.bGw.bGl < (-height)) {
                this.bGw.bGl = -height;
                this.bGw.bGk.Qe();
            }
        }

        public void Qk() {
            if (this.bGw.bFV) {
                this.bGw.Qn();
                this.bGw.bFV = false;
            }
            this.bGw.bGl = 0;
            this.bGw.invalidate();
        }

        public void Ql() {
            if (Math.abs(this.bGw.bGl) > 1) {
                this.bGw.bGk.aU(this.bGw.bGl, 0);
            }
        }
    };
    private DataSetObserver bGv = new DataSetObserver(this) {
        final /* synthetic */ WheelView bGw;

        {
            this.bGw = this$0;
        }

        public void onChanged() {
            this.bGw.dr(false);
        }

        public void onInvalidated() {
            this.bGw.dr(true);
        }
    };

    public WheelView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        bN(context);
    }

    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        bN(context);
    }

    public WheelView(Context context) {
        super(context);
        bN(context);
    }

    private void bN(Context context) {
        this.bGk = new g(getContext(), this.bGu);
    }

    public void setInterpolator(Interpolator interpolator) {
        this.bGk.setInterpolator(interpolator);
    }

    public int getVisibleItems() {
        return this.bGf;
    }

    public void setVisibleItems(int count) {
        this.bGf = count;
    }

    public f getViewAdapter() {
        return this.bGp;
    }

    public void setViewAdapter(f viewAdapter) {
        if (this.bGp != null) {
            this.bGp.unregisterDataSetObserver(this.bGv);
        }
        this.bGp = viewAdapter;
        if (this.bGp != null) {
            this.bGp.registerDataSetObserver(this.bGv);
        }
        dr(true);
    }

    public void a(b listener) {
        this.bGr.add(listener);
    }

    public void b(b listener) {
        this.bGr.remove(listener);
    }

    protected void aV(int oldValue, int newValue) {
        for (b listener : this.bGr) {
            listener.a(this, oldValue, newValue);
        }
    }

    public void a(d listener) {
        this.bGs.add(listener);
    }

    public void b(d listener) {
        this.bGs.remove(listener);
    }

    protected void Qm() {
        for (d listener : this.bGs) {
            listener.a(this);
        }
    }

    protected void Qn() {
        for (d listener : this.bGs) {
            listener.b(this);
        }
    }

    public void a(c listener) {
        this.bGt.add(listener);
    }

    public void b(c listener) {
        this.bGt.remove(listener);
    }

    protected void ms(int item) {
        for (c listener : this.bGt) {
            listener.a(this, item);
        }
    }

    public int getCurrentItem() {
        return this.bFA;
    }

    public void setCurrentItem(int index, boolean animated) {
        if (this.bGp != null && this.bGp.Qa() != 0) {
            int itemCount = this.bGp.Qa();
            if (index < 0 || index >= itemCount) {
                if (this.bGm) {
                    while (index < 0) {
                        index += itemCount;
                    }
                    index %= itemCount;
                } else {
                    return;
                }
            }
            if (index == this.bFA) {
                return;
            }
            if (animated) {
                int itemsToScroll = index - this.bFA;
                if (this.bGm) {
                    int scroll = (Math.min(index, this.bFA) + itemCount) - Math.max(index, this.bFA);
                    if (scroll < Math.abs(itemsToScroll)) {
                        itemsToScroll = itemsToScroll < 0 ? scroll : -scroll;
                    }
                }
                aU(itemsToScroll, 0);
                return;
            }
            this.bGl = 0;
            int old = this.bFA;
            this.bFA = index;
            aV(old, this.bFA);
            invalidate();
        }
    }

    public void setCurrentItem(int index) {
        setCurrentItem(index, false);
    }

    public boolean Qo() {
        return this.bGm;
    }

    public void setCyclic(boolean isCyclic) {
        this.bGm = isCyclic;
        dr(false);
    }

    public void dr(boolean clearCaches) {
        if (clearCaches) {
            this.bGq.clearAll();
            if (this.bGn != null) {
                this.bGn.removeAllViews();
            }
            this.bGl = 0;
        } else if (this.bGn != null) {
            this.bGq.a(this.bGn, this.bGo, new a());
        }
        invalidate();
    }

    private void Qp() {
        if (this.bGh == null) {
            this.bGh = getContext().getResources().getDrawable(b.f.style_wheel_value);
        }
        if (this.bGi == null) {
            this.bGi = new GradientDrawable(Orientation.TOP_BOTTOM, bGb);
        }
        if (this.bGj == null) {
            this.bGj = new GradientDrawable(Orientation.BOTTOM_TOP, bGb);
        }
        setBackgroundResource(b.f.style_wheel_bg);
    }

    private int a(LinearLayout layout) {
        if (!(layout == null || layout.getChildAt(0) == null)) {
            this.bGg = layout.getChildAt(0).getMeasuredHeight();
        }
        return Math.max((this.bGg * this.bGf) - ((this.bGg * 10) / 50), getSuggestedMinimumHeight());
    }

    private int getItemHeight() {
        if (this.bGg != 0) {
            return this.bGg;
        }
        if (this.bGn == null || this.bGn.getChildAt(0) == null) {
            return getHeight() / this.bGf;
        }
        this.bGg = this.bGn.getChildAt(0).getHeight();
        return this.bGg;
    }

    private int aW(int widthSize, int mode) {
        Qp();
        this.bGn.setLayoutParams(new LayoutParams(-2, -2));
        this.bGn.measure(MeasureSpec.makeMeasureSpec(widthSize, 0), MeasureSpec.makeMeasureSpec(0, 0));
        int width = this.bGn.getMeasuredWidth();
        if (mode == 1073741824) {
            width = widthSize;
        } else {
            width = Math.max(width + 20, getSuggestedMinimumWidth());
            if (mode == Integer.MIN_VALUE && widthSize < width) {
                width = widthSize;
            }
        }
        this.bGn.measure(MeasureSpec.makeMeasureSpec(width - 20, 1073741824), MeasureSpec.makeMeasureSpec(0, 0));
        return width;
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        Qs();
        int width = aW(widthSize, widthMode);
        if (heightMode == 1073741824) {
            height = heightSize;
        } else {
            height = a(this.bGn);
            if (heightMode == Integer.MIN_VALUE) {
                height = Math.min(height, heightSize);
            }
        }
        setMeasuredDimension(width, height);
    }

    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        aX(r - l, b - t);
    }

    private void aX(int width, int height) {
        this.bGn.layout(0, 0, width - 20, height);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.bGp != null && this.bGp.Qa() > 0) {
            FF();
            k(canvas);
            l(canvas);
        }
        j(canvas);
    }

    private void j(Canvas canvas) {
        int height = (int) (1.5d * ((double) getItemHeight()));
        this.bGi.setBounds(0, 0, getWidth(), height);
        this.bGi.draw(canvas);
        this.bGj.setBounds(0, getHeight() - height, getWidth(), getHeight());
        this.bGj.draw(canvas);
    }

    private void k(Canvas canvas) {
        canvas.save();
        canvas.translate(10.0f, (float) ((-(((this.bFA - this.bGo) * getItemHeight()) + ((getItemHeight() - getHeight()) / 2))) + this.bGl));
        this.bGn.draw(canvas);
        canvas.restore();
    }

    private void l(Canvas canvas) {
        int center = getHeight() / 2;
        int offset = (int) (((double) (getItemHeight() / 2)) * 1.2d);
        this.bGh.setBounds(0, center - offset, getWidth(), center + offset);
        this.bGh.draw(canvas);
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled() || getViewAdapter() == null) {
            return true;
        }
        switch (event.getAction()) {
            case 1:
                if (!this.bFV) {
                    int distance = ((int) event.getY()) - (getHeight() / 2);
                    if (distance > 0) {
                        distance += getItemHeight() / 2;
                    } else {
                        distance -= getItemHeight() / 2;
                    }
                    int items = distance / getItemHeight();
                    if (items != 0 && mu(this.bFA + items)) {
                        ms(this.bFA + items);
                        break;
                    }
                }
                break;
            case 2:
                if (getParent() != null) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                    break;
                }
                break;
        }
        return this.bGk.onTouchEvent(event);
    }

    private void mt(int delta) {
        this.bGl += delta;
        int itemHeight = getItemHeight();
        int count = this.bGl / itemHeight;
        int pos = this.bFA - count;
        int itemCount = this.bGp.Qa();
        int fixPos = this.bGl % itemHeight;
        if (Math.abs(fixPos) <= itemHeight / 2) {
            fixPos = 0;
        }
        if (this.bGm && itemCount > 0) {
            if (fixPos > 0) {
                pos--;
                count++;
            } else if (fixPos < 0) {
                pos++;
                count--;
            }
            while (pos < 0) {
                pos += itemCount;
            }
            pos %= itemCount;
        } else if (pos < 0) {
            count = this.bFA;
            pos = 0;
        } else if (pos >= itemCount) {
            count = (this.bFA - itemCount) + 1;
            pos = itemCount - 1;
        } else if (pos > 0 && fixPos > 0) {
            pos--;
            count++;
        } else if (pos < itemCount - 1 && fixPos < 0) {
            pos++;
            count--;
        }
        int offset = this.bGl;
        if (pos != this.bFA) {
            setCurrentItem(pos, false);
        } else {
            invalidate();
        }
        this.bGl = offset - (count * itemHeight);
        if (this.bGl > getHeight()) {
            this.bGl = (this.bGl % getHeight()) + getHeight();
        }
    }

    public void aU(int itemsToScroll, int time) {
        this.bGk.aU((getItemHeight() * itemsToScroll) - this.bGl, time);
    }

    private a getItemsRange() {
        if (getItemHeight() == 0) {
            return null;
        }
        int first = this.bFA;
        int count = 1;
        while (getItemHeight() * count < getHeight()) {
            first--;
            count += 2;
        }
        if (this.bGl != 0) {
            if (this.bGl > 0) {
                first--;
            }
            int emptyItems = this.bGl / getItemHeight();
            first -= emptyItems;
            count = (int) (((double) (count + 1)) + Math.asin((double) emptyItems));
        }
        return new a(first, count);
    }

    private boolean Qq() {
        int first;
        boolean updated;
        int i;
        a range = getItemsRange();
        if (this.bGn != null) {
            first = this.bGq.a(this.bGn, this.bGo, range);
            if (this.bGo != first) {
                updated = true;
            } else {
                updated = false;
            }
            this.bGo = first;
        } else {
            Qr();
            updated = true;
        }
        if (!updated) {
            if (this.bGo == range.getFirst() && this.bGn.getChildCount() == range.getCount()) {
                updated = false;
            } else {
                updated = true;
            }
        }
        if (this.bGo <= range.getFirst() || this.bGo > range.getLast()) {
            this.bGo = range.getFirst();
        } else {
            i = this.bGo - 1;
            while (i >= range.getFirst() && l(i, true)) {
                this.bGo = i;
                i--;
            }
        }
        first = this.bGo;
        for (i = this.bGn.getChildCount(); i < range.getCount(); i++) {
            if (!l(this.bGo + i, false) && this.bGn.getChildCount() == 0) {
                first++;
            }
        }
        this.bGo = first;
        return updated;
    }

    private void FF() {
        if (Qq()) {
            aW(getWidth(), 1073741824);
            aX(getWidth(), getHeight());
        }
    }

    private void Qr() {
        if (this.bGn == null) {
            this.bGn = new LinearLayout(getContext());
            this.bGn.setOrientation(1);
        }
    }

    private void Qs() {
        if (this.bGn != null) {
            this.bGq.a(this.bGn, this.bGo, new a());
        } else {
            Qr();
        }
        int addItems = this.bGf / 2;
        for (int i = this.bFA + addItems; i >= this.bFA - addItems; i--) {
            if (l(i, true)) {
                this.bGo = i;
            }
        }
    }

    private boolean l(int index, boolean first) {
        View view = mv(index);
        if (view == null) {
            return false;
        }
        if (first) {
            this.bGn.addView(view, 0);
        } else {
            this.bGn.addView(view);
        }
        return true;
    }

    private boolean mu(int index) {
        return this.bGp != null && this.bGp.Qa() > 0 && (this.bGm || (index >= 0 && index < this.bGp.Qa()));
    }

    private View mv(int index) {
        if (this.bGp == null || this.bGp.Qa() == 0) {
            return null;
        }
        int count = this.bGp.Qa();
        if (!mu(index)) {
            return this.bGp.a(this.bGq.Qd(), this.bGn);
        }
        while (index < 0) {
            index += count;
        }
        return this.bGp.a(index % count, this.bGq.Qc(), this.bGn);
    }

    public void Qe() {
        this.bGk.Qe();
    }
}
