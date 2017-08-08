package com.MCWorld.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class VerticalScrollView extends ScrollView {
    private GestureDetector buE;
    OnTouchListener buF;

    class a extends SimpleOnGestureListener {
        final /* synthetic */ VerticalScrollView buG;

        a(VerticalScrollView this$0) {
            this.buG = this$0;
        }

        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return Math.abs(distanceY) > Math.abs(distanceX);
        }
    }

    public VerticalScrollView(Context context) {
        super(context);
        init(context);
    }

    public VerticalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VerticalScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev) && this.buE.onTouchEvent(ev);
    }

    private void init(Context context) {
        this.buE = new GestureDetector(context, new a(this));
        setFadingEdgeLength(0);
    }
}
