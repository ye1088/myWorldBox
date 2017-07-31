package com.huluxia.framework.base.widget.datetimepicker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import com.huluxia.framework.R;
import com.huluxia.framework.R$dimen;
import com.huluxia.framework.R$string;
import com.huluxia.framework.base.widget.datetimepicker.SimpleMonthAdapter.CalendarDay;
import java.security.InvalidParameterException;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Locale;

public class SimpleMonthView extends View {
    protected static int DAY_SELECTED_CIRCLE_SIZE = 0;
    protected static int DAY_SEPARATOR_WIDTH = 1;
    protected static int DEFAULT_HEIGHT = 32;
    protected static final int DEFAULT_NUM_ROWS = 6;
    protected static int MINI_DAY_NUMBER_TEXT_SIZE = 0;
    protected static int MIN_HEIGHT = 10;
    protected static int MONTH_DAY_LABEL_TEXT_SIZE = 0;
    protected static int MONTH_HEADER_SIZE = 0;
    protected static int MONTH_LABEL_TEXT_SIZE = 0;
    private static final int SELECTED_CIRCLE_ALPHA = 60;
    public static final String VIEW_PARAMS_FOCUS_MONTH = "focus_month";
    public static final String VIEW_PARAMS_HEIGHT = "height";
    public static final String VIEW_PARAMS_MONTH = "month";
    public static final String VIEW_PARAMS_NUM_DAYS = "num_days";
    public static final String VIEW_PARAMS_SELECTED_DAY = "selected_day";
    public static final String VIEW_PARAMS_SHOW_WK_NUM = "show_wk_num";
    public static final String VIEW_PARAMS_WEEK_START = "week_start";
    public static final String VIEW_PARAMS_YEAR = "year";
    protected static float mScale = 0.0f;
    private final Calendar mCalendar;
    private DateFormatSymbols mDateFormatSymbols = new DateFormatSymbols();
    private final Calendar mDayLabelCalendar;
    private int mDayOfWeekStart = 0;
    private String mDayOfWeekTypeface;
    protected int mDayTextColor;
    protected int mFirstJulianDay = -1;
    protected int mFirstMonth = -1;
    private final Formatter mFormatter;
    protected boolean mHasToday = false;
    protected int mLastMonth = -1;
    protected int mMonth;
    protected Paint mMonthDayLabelPaint;
    protected Paint mMonthNumPaint;
    protected int mMonthTitleBGColor;
    protected Paint mMonthTitleBGPaint;
    protected int mMonthTitleColor;
    protected Paint mMonthTitlePaint;
    private String mMonthTitleTypeface;
    protected int mNumCells = this.mNumDays;
    protected int mNumDays = 7;
    private int mNumRows = 6;
    private OnDayClickListener mOnDayClickListener;
    protected int mPadding = 0;
    protected int mRowHeight = DEFAULT_HEIGHT;
    protected Paint mSelectedCirclePaint;
    protected int mSelectedDay = -1;
    protected int mSelectedLeft = -1;
    protected int mSelectedRight = -1;
    private final StringBuilder mStringBuilder;
    protected int mToday = -1;
    protected int mTodayNumberColor;
    protected int mWeekStart = 1;
    protected int mWidth;
    protected int mYear;

    public interface OnDayClickListener {
        void onDayClick(SimpleMonthView simpleMonthView, CalendarDay calendarDay);
    }

    public SimpleMonthView(Context context) {
        super(context);
        Resources resources = context.getResources();
        this.mDayLabelCalendar = Calendar.getInstance();
        this.mCalendar = Calendar.getInstance();
        this.mDayOfWeekTypeface = resources.getString(R$string.day_of_week_label_typeface);
        this.mMonthTitleTypeface = resources.getString(R$string.sans_serif);
        this.mDayTextColor = resources.getColor(R.color.date_picker_text_normal);
        this.mTodayNumberColor = resources.getColor(R.color.blue);
        this.mMonthTitleColor = resources.getColor(R.color.white);
        this.mMonthTitleBGColor = resources.getColor(R.color.circle_background);
        this.mStringBuilder = new StringBuilder(50);
        this.mFormatter = new Formatter(this.mStringBuilder, Locale.getDefault());
        MINI_DAY_NUMBER_TEXT_SIZE = resources.getDimensionPixelSize(R$dimen.day_number_size);
        MONTH_LABEL_TEXT_SIZE = resources.getDimensionPixelSize(R$dimen.month_label_size);
        MONTH_DAY_LABEL_TEXT_SIZE = resources.getDimensionPixelSize(R$dimen.month_day_label_text_size);
        MONTH_HEADER_SIZE = resources.getDimensionPixelOffset(R$dimen.month_list_item_header_height);
        DAY_SELECTED_CIRCLE_SIZE = resources.getDimensionPixelSize(R$dimen.day_number_select_circle_radius);
        this.mRowHeight = (resources.getDimensionPixelOffset(R$dimen.date_picker_view_animator_height) - MONTH_HEADER_SIZE) / 6;
        initView();
    }

    private int calculateNumRows() {
        int offset = findDayOffset();
        return ((this.mNumCells + offset) % this.mNumDays > 0 ? 1 : 0) + ((this.mNumCells + offset) / this.mNumDays);
    }

    private void drawMonthDayLabels(Canvas canvas) {
        int y = MONTH_HEADER_SIZE - (MONTH_DAY_LABEL_TEXT_SIZE / 2);
        int dayWidthHalf = (this.mWidth - (this.mPadding * 2)) / (this.mNumDays * 2);
        for (int i = 0; i < this.mNumDays; i++) {
            int x = (((i * 2) + 1) * dayWidthHalf) + this.mPadding;
            this.mDayLabelCalendar.set(7, (this.mWeekStart + i) % this.mNumDays);
            canvas.drawText(this.mDateFormatSymbols.getShortWeekdays()[this.mDayLabelCalendar.get(7)].toUpperCase(Locale.getDefault()), (float) x, (float) y, this.mMonthDayLabelPaint);
        }
    }

    private void drawMonthTitle(Canvas canvas) {
        canvas.drawText(getMonthAndYearString(), (float) ((this.mWidth + (this.mPadding * 2)) / 2), (float) (((MONTH_HEADER_SIZE - MONTH_DAY_LABEL_TEXT_SIZE) / 2) + (MONTH_LABEL_TEXT_SIZE / 3)), this.mMonthTitlePaint);
    }

    private int findDayOffset() {
        return (this.mDayOfWeekStart < this.mWeekStart ? this.mDayOfWeekStart + this.mNumDays : this.mDayOfWeekStart) - this.mWeekStart;
    }

    @SuppressLint({"NewApi"})
    private String getMonthAndYearString() {
        this.mStringBuilder.setLength(0);
        long millis = this.mCalendar.getTimeInMillis();
        return DateUtils.formatDateRange(getContext(), millis, millis, 52);
    }

    private void onDayClick(CalendarDay calendarDay) {
        if (this.mOnDayClickListener != null) {
            this.mOnDayClickListener.onDayClick(this, calendarDay);
        }
    }

    @SuppressLint({"NewApi"})
    private boolean sameDay(int monthDay, Time time) {
        return this.mYear == time.year && this.mMonth == time.month && monthDay == time.monthDay;
    }

    protected void drawMonthNums(Canvas canvas) {
        int y = (((this.mRowHeight + MINI_DAY_NUMBER_TEXT_SIZE) / 2) - DAY_SEPARATOR_WIDTH) + MONTH_HEADER_SIZE;
        int paddingDay = (this.mWidth - (this.mPadding * 2)) / (this.mNumDays * 2);
        int dayOffset = findDayOffset();
        int day = 1;
        while (day <= this.mNumCells) {
            int x = (((dayOffset * 2) + 1) * paddingDay) + this.mPadding;
            if (this.mSelectedDay == day) {
                canvas.drawCircle((float) x, (float) (y - (MINI_DAY_NUMBER_TEXT_SIZE / 3)), (float) DAY_SELECTED_CIRCLE_SIZE, this.mSelectedCirclePaint);
            }
            if (this.mHasToday && this.mToday == day) {
                this.mMonthNumPaint.setColor(this.mTodayNumberColor);
            } else {
                this.mMonthNumPaint.setColor(this.mDayTextColor);
            }
            canvas.drawText(String.format("%d", new Object[]{Integer.valueOf(day)}), (float) x, (float) y, this.mMonthNumPaint);
            dayOffset++;
            if (dayOffset == this.mNumDays) {
                dayOffset = 0;
                y += this.mRowHeight;
            }
            day++;
        }
    }

    public CalendarDay getDayFromLocation(float x, float y) {
        int padding = this.mPadding;
        if (x < ((float) padding) || x > ((float) (this.mWidth - this.mPadding))) {
            return null;
        }
        return new CalendarDay(this.mYear, this.mMonth, ((((int) (((x - ((float) padding)) * ((float) this.mNumDays)) / ((float) ((this.mWidth - padding) - this.mPadding)))) - findDayOffset()) + 1) + (this.mNumDays * (((int) (y - ((float) MONTH_HEADER_SIZE))) / this.mRowHeight)));
    }

    protected void initView() {
        this.mMonthTitlePaint = new Paint();
        this.mMonthTitlePaint.setFakeBoldText(true);
        this.mMonthTitlePaint.setAntiAlias(true);
        this.mMonthTitlePaint.setTextSize((float) MONTH_LABEL_TEXT_SIZE);
        this.mMonthTitlePaint.setTypeface(Typeface.create(this.mMonthTitleTypeface, 1));
        this.mMonthTitlePaint.setColor(this.mDayTextColor);
        this.mMonthTitlePaint.setTextAlign(Align.CENTER);
        this.mMonthTitlePaint.setStyle(Style.FILL);
        this.mMonthTitleBGPaint = new Paint();
        this.mMonthTitleBGPaint.setFakeBoldText(true);
        this.mMonthTitleBGPaint.setAntiAlias(true);
        this.mMonthTitleBGPaint.setColor(this.mMonthTitleBGColor);
        this.mMonthTitleBGPaint.setTextAlign(Align.CENTER);
        this.mMonthTitleBGPaint.setStyle(Style.FILL);
        this.mSelectedCirclePaint = new Paint();
        this.mSelectedCirclePaint.setFakeBoldText(true);
        this.mSelectedCirclePaint.setAntiAlias(true);
        this.mSelectedCirclePaint.setColor(this.mTodayNumberColor);
        this.mSelectedCirclePaint.setTextAlign(Align.CENTER);
        this.mSelectedCirclePaint.setStyle(Style.FILL);
        this.mSelectedCirclePaint.setAlpha(60);
        this.mMonthDayLabelPaint = new Paint();
        this.mMonthDayLabelPaint.setAntiAlias(true);
        this.mMonthDayLabelPaint.setTextSize((float) MONTH_DAY_LABEL_TEXT_SIZE);
        this.mMonthDayLabelPaint.setColor(this.mDayTextColor);
        this.mMonthDayLabelPaint.setTypeface(Typeface.create(this.mDayOfWeekTypeface, 0));
        this.mMonthDayLabelPaint.setStyle(Style.FILL);
        this.mMonthDayLabelPaint.setTextAlign(Align.CENTER);
        this.mMonthDayLabelPaint.setFakeBoldText(true);
        this.mMonthNumPaint = new Paint();
        this.mMonthNumPaint.setAntiAlias(true);
        this.mMonthNumPaint.setTextSize((float) MINI_DAY_NUMBER_TEXT_SIZE);
        this.mMonthNumPaint.setStyle(Style.FILL);
        this.mMonthNumPaint.setTextAlign(Align.CENTER);
        this.mMonthNumPaint.setFakeBoldText(false);
    }

    protected void onDraw(Canvas canvas) {
        drawMonthTitle(canvas);
        drawMonthDayLabels(canvas);
        drawMonthNums(canvas);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), (this.mRowHeight * this.mNumRows) + MONTH_HEADER_SIZE);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.mWidth = w;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == 1) {
            CalendarDay calendarDay = getDayFromLocation(event.getX(), event.getY());
            if (calendarDay != null) {
                onDayClick(calendarDay);
            }
        }
        return true;
    }

    public void reuse() {
        this.mNumRows = 6;
        requestLayout();
    }

    @SuppressLint({"NewApi"})
    public void setMonthParams(HashMap<String, Integer> params) {
        if (params.containsKey(VIEW_PARAMS_MONTH) || params.containsKey(VIEW_PARAMS_YEAR)) {
            setTag(params);
            if (params.containsKey("height")) {
                this.mRowHeight = ((Integer) params.get("height")).intValue();
                if (this.mRowHeight < MIN_HEIGHT) {
                    this.mRowHeight = MIN_HEIGHT;
                }
            }
            if (params.containsKey(VIEW_PARAMS_SELECTED_DAY)) {
                this.mSelectedDay = ((Integer) params.get(VIEW_PARAMS_SELECTED_DAY)).intValue();
            }
            this.mMonth = ((Integer) params.get(VIEW_PARAMS_MONTH)).intValue();
            this.mYear = ((Integer) params.get(VIEW_PARAMS_YEAR)).intValue();
            Time today = new Time(Time.getCurrentTimezone());
            today.setToNow();
            this.mHasToday = false;
            this.mToday = -1;
            this.mCalendar.set(2, this.mMonth);
            this.mCalendar.set(1, this.mYear);
            this.mCalendar.set(5, 1);
            this.mDayOfWeekStart = this.mCalendar.get(7);
            if (params.containsKey("week_start")) {
                this.mWeekStart = ((Integer) params.get("week_start")).intValue();
            } else {
                this.mWeekStart = this.mCalendar.getFirstDayOfWeek();
            }
            this.mNumCells = Utils.getDaysInMonth(this.mMonth, this.mYear);
            for (int i = 0; i < this.mNumCells; i++) {
                int day = i + 1;
                if (sameDay(day, today)) {
                    this.mHasToday = true;
                    this.mToday = day;
                }
            }
            this.mNumRows = calculateNumRows();
            return;
        }
        throw new InvalidParameterException("You must specify month and year for this view");
    }

    public void setOnDayClickListener(OnDayClickListener onDayClickListener) {
        this.mOnDayClickListener = onDayClickListener;
    }
}
