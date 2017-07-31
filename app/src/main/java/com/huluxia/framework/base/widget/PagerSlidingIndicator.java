package com.huluxia.framework.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.huluxia.framework.R;
import java.util.Locale;

public class PagerSlidingIndicator extends HorizontalScrollView {
    private static final int[] ATTRS = new int[]{16842901, 16842904};
    private boolean checkedTabWidths;
    private int currentPosition;
    private float currentPositionOffset;
    private LayoutParams defaultTabLayoutParams;
    public OnPageChangeListener delegatePageListener;
    private int dividerColor;
    private int dividerPadding;
    private Paint dividerPaint;
    private int dividerWidth;
    private LayoutParams expandedTabLayoutParams;
    private int indicatorColor;
    private int indicatorHeight;
    private boolean indicatorOffset;
    private boolean indicatorTextColor;
    private IndicatorTextSizeChange indicatorTextSize;
    private int lastScrollX;
    private Locale locale;
    private float mMeasureText;
    private final PageListener pageListener;
    protected ViewPager pager;
    private Paint rectPaint;
    private int scrollOffset;
    private boolean shouldExpand;
    private int tabBackgroundResId;
    private int tabCount;
    private int tabPadding;
    private int tabTextColor;
    private int tabTextSize;
    private Typeface tabTypeface;
    private int tabTypefaceStyle;
    private LinearLayout tabsContainer;
    private boolean textAllCaps;
    private int underlineColor;
    private int underlineHeight;
    private boolean underlineTop;

    public interface IndicatorTextSizeChange {
        int getTextSizePx();
    }

    public PagerSlidingIndicator(Context context) {
        this(context, null);
    }

    public PagerSlidingIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PagerSlidingIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mMeasureText = 0.0f;
        this.pageListener = new PageListener(this, null);
        this.currentPosition = 0;
        this.currentPositionOffset = 0.0f;
        this.checkedTabWidths = false;
        this.indicatorColor = -10066330;
        this.underlineColor = 436207616;
        this.dividerColor = 436207616;
        this.indicatorTextColor = false;
        this.shouldExpand = false;
        this.textAllCaps = true;
        this.underlineTop = false;
        this.indicatorOffset = false;
        this.scrollOffset = 52;
        this.indicatorHeight = 2;
        this.underlineHeight = 1;
        this.dividerPadding = 12;
        this.tabPadding = 24;
        this.dividerWidth = 1;
        this.tabTextSize = 13;
        this.tabTextColor = -10066330;
        this.tabTypeface = null;
        this.tabTypefaceStyle = 0;
        this.lastScrollX = 0;
        this.tabBackgroundResId = 0;
        setFillViewport(true);
        setWillNotDraw(false);
        this.tabsContainer = new LinearLayout(context);
        this.tabsContainer.setOrientation(0);
        this.tabsContainer.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        addView(this.tabsContainer);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        this.scrollOffset = (int) TypedValue.applyDimension(1, (float) this.scrollOffset, dm);
        this.indicatorHeight = (int) TypedValue.applyDimension(1, (float) this.indicatorHeight, dm);
        this.underlineHeight = (int) TypedValue.applyDimension(1, (float) this.underlineHeight, dm);
        this.dividerPadding = (int) TypedValue.applyDimension(1, (float) this.dividerPadding, dm);
        this.tabPadding = (int) TypedValue.applyDimension(1, (float) this.tabPadding, dm);
        this.dividerWidth = (int) TypedValue.applyDimension(1, (float) this.dividerWidth, dm);
        this.tabTextSize = (int) TypedValue.applyDimension(2, (float) this.tabTextSize, dm);
        TypedArray a = context.obtainStyledAttributes(attrs, ATTRS);
        this.tabTextSize = a.getDimensionPixelSize(0, this.tabTextSize);
        this.tabTextColor = a.getColor(1, this.tabTextColor);
        a.recycle();
        this.rectPaint = new Paint();
        this.rectPaint.setAntiAlias(true);
        this.rectPaint.setStyle(Style.FILL);
        this.dividerPaint = new Paint();
        this.dividerPaint.setAntiAlias(true);
        this.dividerPaint.setStrokeWidth((float) this.dividerWidth);
        this.defaultTabLayoutParams = new LayoutParams(-2, -1);
        this.expandedTabLayoutParams = new LayoutParams(0, -1, 1.0f);
        if (this.locale == null) {
            this.locale = getResources().getConfiguration().locale;
        }
    }

    public void setViewPager(ViewPager pager) {
        this.pager = pager;
        if (pager.getAdapter() == null) {
            throw new IllegalStateException("ViewPager does not have adapter instance.");
        }
        pager.setOnPageChangeListener(this.pageListener);
        notifyDataSetChanged();
    }

    public void setOnPageChangeListener(OnPageChangeListener listener) {
        this.delegatePageListener = listener;
    }

    public void notifyDataSetChanged() {
        this.tabsContainer.removeAllViews();
        this.tabCount = this.pager.getAdapter().getCount();
        for (int i = 0; i < this.tabCount; i++) {
            if (this.pager.getAdapter() instanceof IconTabProvider) {
                addIconTab(i, ((IconTabProvider) this.pager.getAdapter()).getPageIconResId(i));
            } else if (this.pager.getAdapter() instanceof TextRedDotProvider) {
                addTextDotTab(i, this.pager.getAdapter().getPageTitle(i).toString(), ((TextRedDotProvider) this.pager.getAdapter()).showRedDot(i));
            } else {
                addTextTab(i, this.pager.getAdapter().getPageTitle(i).toString());
            }
        }
        updateTabStyles();
        this.checkedTabWidths = false;
        getViewTreeObserver().addOnGlobalLayoutListener(new 1(this));
    }

    public void updateTabTitle(int position, String title) {
        for (int i = 0; i < this.tabCount; i++) {
            if (position == i) {
                View v = this.tabsContainer.getChildAt(i);
                if (v instanceof TextView) {
                    ((TextView) v).setText(title);
                    return;
                } else if (v.findViewById(R.id.red_dot) != null) {
                    ((TextView) v.findViewById(R.id.text)).setText(title);
                    return;
                } else {
                    return;
                }
            }
        }
    }

    private void addTextTab(int position, String title) {
        TextView tab = new TextView(getContext());
        tab.setText(title);
        tab.setFocusable(true);
        tab.setGravity(17);
        tab.setSingleLine();
        tab.setOnClickListener(new 2(this, position));
        this.tabsContainer.addView(tab);
    }

    private void addTextDotTab(int position, String title, boolean showRedDot) {
        View tabView = LayoutInflater.from(getContext()).inflate(R.layout.tab_strip_red_dot, null, false);
        TextView tab = (TextView) tabView.findViewById(R.id.text);
        tab.setText(title);
        tab.setFocusable(true);
        tab.setGravity(17);
        tab.setSingleLine();
        View redDot = tabView.findViewById(R.id.red_dot);
        if (showRedDot) {
            redDot.setVisibility(0);
        } else {
            redDot.setVisibility(8);
        }
        tab.setOnClickListener(new 3(this, position));
        this.tabsContainer.addView(tabView);
    }

    private void addIconTab(int position, int resId) {
        ImageButton tab = new ImageButton(getContext());
        tab.setFocusable(true);
        tab.setImageResource(resId);
        tab.setOnClickListener(new 4(this, position));
        this.tabsContainer.addView(tab);
    }

    private void updateTabStyles() {
        for (int i = 0; i < this.tabCount; i++) {
            View v = this.tabsContainer.getChildAt(i);
            v.setLayoutParams(this.defaultTabLayoutParams);
            v.setBackgroundResource(this.tabBackgroundResId);
            if (this.shouldExpand) {
                v.setPadding(0, 0, 0, 0);
            } else {
                v.setPadding(this.tabPadding, 0, this.tabPadding, 0);
            }
            TextView tab;
            if (v instanceof TextView) {
                tab = (TextView) v;
                tab.setTextSize(0, (float) this.tabTextSize);
                tab.setTypeface(this.tabTypeface, this.tabTypefaceStyle);
                if (i == this.currentPosition && this.indicatorTextColor) {
                    tab.setTextColor(this.indicatorColor);
                    if (this.indicatorTextSize != null) {
                        ((TextView) v).setTextSize(0, (float) this.indicatorTextSize.getTextSizePx());
                    }
                } else {
                    tab.setTextColor(this.tabTextColor);
                }
                if (i == this.currentPosition && this.indicatorOffset) {
                    measureTextWidth(tab);
                }
                if (this.textAllCaps) {
                    if (VERSION.SDK_INT >= 14) {
                        tab.setAllCaps(true);
                    } else {
                        tab.setText(tab.getText().toString().toUpperCase(this.locale));
                    }
                }
            } else if (v.findViewById(R.id.red_dot) != null) {
                tab = (TextView) v.findViewById(R.id.text);
                tab.setTextSize(0, (float) this.tabTextSize);
                tab.setTypeface(this.tabTypeface, this.tabTypefaceStyle);
                tab.setTextColor(this.tabTextColor);
                if (this.textAllCaps) {
                    if (VERSION.SDK_INT >= 14) {
                        tab.setAllCaps(true);
                    } else {
                        tab.setText(tab.getText().toString().toUpperCase(this.locale));
                    }
                }
            }
        }
    }

    private void measureTextWidth(TextView tab) {
        TextPaint paint = new TextPaint();
        if (this.indicatorTextSize != null) {
            paint.setTextSize((float) this.indicatorTextSize.getTextSizePx());
        } else {
            paint.setTextSize((float) this.tabTextSize);
        }
        this.mMeasureText = paint.measureText(tab.getText().toString());
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (this.shouldExpand && MeasureSpec.getMode(widthMeasureSpec) != 0) {
            int i;
            int myWidth = getMeasuredWidth();
            int childWidth = 0;
            for (i = 0; i < this.tabCount; i++) {
                childWidth += this.tabsContainer.getChildAt(i).getMeasuredWidth();
            }
            if (!this.checkedTabWidths && childWidth > 0 && myWidth > 0) {
                if (childWidth <= myWidth) {
                    for (i = 0; i < this.tabCount; i++) {
                        this.tabsContainer.getChildAt(i).setLayoutParams(this.expandedTabLayoutParams);
                    }
                }
                this.checkedTabWidths = true;
            }
        }
    }

    private void scrollToChild(int position, int offset) {
        if (this.tabCount != 0) {
            int newScrollX = this.tabsContainer.getChildAt(position).getLeft() + offset;
            if (position > 0 || offset > 0) {
                newScrollX -= this.scrollOffset;
            }
            if (newScrollX != this.lastScrollX) {
                this.lastScrollX = newScrollX;
                scrollTo(newScrollX, 0);
            }
        }
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInEditMode() && this.tabCount != 0) {
            int height = getHeight();
            this.rectPaint.setColor(this.indicatorColor);
            View currentTab = this.tabsContainer.getChildAt(this.currentPosition);
            float lineLeft = (float) currentTab.getLeft();
            float lineRight = (float) currentTab.getRight();
            if (this.currentPositionOffset > 0.0f && this.currentPosition < this.tabCount - 1) {
                View nextTab = this.tabsContainer.getChildAt(this.currentPosition + 1);
                lineLeft = (this.currentPositionOffset * ((float) nextTab.getLeft())) + ((1.0f - this.currentPositionOffset) * lineLeft);
                lineRight = (this.currentPositionOffset * ((float) nextTab.getRight())) + ((1.0f - this.currentPositionOffset) * lineRight);
            }
            float offset;
            if (this.underlineTop) {
                if (this.indicatorOffset) {
                    offset = ((lineRight - lineLeft) - this.mMeasureText) / 2.0f;
                    canvas.drawRect(lineLeft + offset, 0.0f, lineRight - offset, (float) this.indicatorHeight, this.rectPaint);
                } else {
                    canvas.drawRect(lineLeft, 0.0f, lineRight, (float) this.indicatorHeight, this.rectPaint);
                }
            } else if (this.indicatorOffset) {
                offset = ((lineRight - lineLeft) - this.mMeasureText) / 2.0f;
                canvas.drawRect(lineLeft + offset, (float) (height - this.indicatorHeight), lineRight - offset, (float) height, this.rectPaint);
            } else {
                canvas.drawRect(lineLeft, (float) (height - this.indicatorHeight), lineRight, (float) height, this.rectPaint);
            }
            this.rectPaint.setColor(this.underlineColor);
            if (this.underlineTop) {
                canvas.drawRect(0.0f, 0.0f, (float) this.tabsContainer.getWidth(), (float) this.underlineHeight, this.rectPaint);
            } else {
                canvas.drawRect(0.0f, (float) (height - this.underlineHeight), (float) this.tabsContainer.getWidth(), (float) height, this.rectPaint);
            }
            this.dividerPaint.setColor(this.dividerColor);
            for (int i = 0; i < this.tabCount - 1; i++) {
                View tab = this.tabsContainer.getChildAt(i);
                canvas.drawLine((float) tab.getRight(), (float) this.dividerPadding, (float) tab.getRight(), (float) (height - this.dividerPadding), this.dividerPaint);
            }
        }
    }

    private void updateTextColor(int index) {
        if (this.pager != null && this.pager.getAdapter() != null) {
            int i = 0;
            while (i < this.pager.getAdapter().getCount()) {
                View v = this.tabsContainer.getChildAt(i);
                if (v != null) {
                    if (v instanceof TextView) {
                        if (i == index && this.indicatorTextColor) {
                            ((TextView) v).setTextColor(this.indicatorColor);
                            if (this.indicatorTextSize != null) {
                                ((TextView) v).setTextSize(0, (float) this.indicatorTextSize.getTextSizePx());
                                measureTextWidth((TextView) v);
                            }
                        } else {
                            ((TextView) v).setTextColor(this.tabTextColor);
                            if (this.indicatorTextSize != null) {
                                ((TextView) v).setTextSize(0, (float) this.tabTextSize);
                            }
                        }
                    } else if (v.findViewById(R.id.red_dot) != null) {
                        TextView tab = (TextView) v.findViewById(R.id.text);
                        v.findViewById(R.id.red_dot).setVisibility(((TextRedDotProvider) this.pager.getAdapter()).showRedDot(i) ? 0 : 4);
                        if (i == index && this.indicatorTextColor) {
                            tab.setTextColor(this.indicatorColor);
                        } else {
                            tab.setTextColor(this.tabTextColor);
                        }
                    }
                    i++;
                } else {
                    return;
                }
            }
        }
    }

    public void setIndicatorTextSize(IndicatorTextSizeChange indicatorTextSize) {
        this.indicatorTextSize = indicatorTextSize;
    }

    public void setIndicatorColor(int indicatorColor) {
        this.indicatorColor = indicatorColor;
        invalidate();
        updateTabStyles();
    }

    public void setIndicatorColorResource(int resId) {
        this.indicatorColor = getResources().getColor(resId);
        invalidate();
        updateTabStyles();
    }

    public int getIndicatorColor() {
        return this.indicatorColor;
    }

    public void setIndicatorHeight(int indicatorLineHeightPx) {
        this.indicatorHeight = indicatorLineHeightPx;
        invalidate();
    }

    public int getIndicatorHeight() {
        return this.indicatorHeight;
    }

    public void setUnderlineColor(int underlineColor) {
        this.underlineColor = underlineColor;
        invalidate();
    }

    public void setUnderlineColorResource(int resId) {
        this.underlineColor = getResources().getColor(resId);
        invalidate();
    }

    public int getUnderlineColor() {
        return this.underlineColor;
    }

    public void setDividerColor(int dividerColor) {
        this.dividerColor = dividerColor;
        invalidate();
    }

    public void setDividerColorResource(int resId) {
        this.dividerColor = getResources().getColor(resId);
        invalidate();
    }

    public int getDividerColor() {
        return this.dividerColor;
    }

    public void setUnderlineHeight(int underlineHeightPx) {
        this.underlineHeight = underlineHeightPx;
        invalidate();
    }

    public int getUnderlineHeight() {
        return this.underlineHeight;
    }

    public void setDividerPadding(int dividerPaddingPx) {
        this.dividerPadding = dividerPaddingPx;
        invalidate();
    }

    public int getDividerPadding() {
        return this.dividerPadding;
    }

    public boolean isUnderlineTop() {
        return this.underlineTop;
    }

    public void setUnderlineTop(boolean underlineTop) {
        this.underlineTop = underlineTop;
        invalidate();
    }

    public void setScrollOffset(int scrollOffsetPx) {
        this.scrollOffset = scrollOffsetPx;
        invalidate();
    }

    public int getScrollOffset() {
        return this.scrollOffset;
    }

    public void setIndicatorTextColor(boolean indicatorTextColor) {
        this.indicatorTextColor = indicatorTextColor;
        updateTextColor(this.currentPosition);
    }

    public boolean isIndicatorTextColor() {
        return this.indicatorTextColor;
    }

    public void setShouldExpand(boolean shouldExpand) {
        this.shouldExpand = shouldExpand;
        requestLayout();
    }

    public boolean getShouldExpand() {
        return this.shouldExpand;
    }

    public boolean isTextAllCaps() {
        return this.textAllCaps;
    }

    public void setAllCaps(boolean textAllCaps) {
        this.textAllCaps = textAllCaps;
    }

    public void setTextSize(int textSizePx) {
        this.tabTextSize = textSizePx;
        updateTabStyles();
    }

    public int getTextSize() {
        return this.tabTextSize;
    }

    public void setTextColor(int textColor) {
        this.tabTextColor = textColor;
        updateTabStyles();
    }

    public void setTextColorResource(int resId) {
        this.tabTextColor = getResources().getColor(resId);
        updateTabStyles();
    }

    public void setIndicatorOffset(boolean indicatorOffset) {
        this.indicatorOffset = indicatorOffset;
    }

    public int getTextColor() {
        return this.tabTextColor;
    }

    public void setTypeface(Typeface typeface, int style) {
        this.tabTypeface = typeface;
        this.tabTypefaceStyle = style;
        updateTabStyles();
    }

    public void setTabBackground(int resId) {
        this.tabBackgroundResId = resId;
    }

    public int getTabBackground() {
        return this.tabBackgroundResId;
    }

    public void setTabPaddingLeftRight(int paddingPx) {
        this.tabPadding = paddingPx;
        updateTabStyles();
    }

    public int getTabPaddingLeftRight() {
        return this.tabPadding;
    }

    public void onRestoreInstanceState(Parcelable state) {
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.currentPosition = savedState.currentPosition;
        requestLayout();
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.currentPosition = this.currentPosition;
        return savedState;
    }
}
