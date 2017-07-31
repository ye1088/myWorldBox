package com.huluxia.framework.base.widget.datetimepicker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.Vibrator;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.huluxia.framework.R;
import com.huluxia.framework.R$string;
import com.huluxia.framework.base.widget.datetimepicker.SimpleMonthAdapter.CalendarDay;
import com.nineoldandroids.animation.ObjectAnimator;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

public class DatePickerDialog extends DialogFragment implements OnClickListener, DatePickerController {
    public static final int ANIMATION_DELAY = 500;
    private static SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("dd", Locale.getDefault());
    public static final String KEY_CURRENT_VIEW = "current_view";
    public static final String KEY_LIST_POSITION = "list_position";
    public static final String KEY_LIST_POSITION_OFFSET = "list_position_offset";
    private static final String KEY_SELECTED_DAY = "day";
    private static final String KEY_SELECTED_MONTH = "month";
    private static final String KEY_SELECTED_YEAR = "year";
    private static final String KEY_VIBRATE = "vibrate";
    public static final String KEY_WEEK_START = "week_start";
    public static final String KEY_YEAR_END = "year_end";
    public static final String KEY_YEAR_START = "year_start";
    private static final int MAX_YEAR = 2037;
    private static final int MIN_YEAR = 1902;
    private static final int MONTH_AND_DAY_VIEW = 0;
    private static final int UNINITIALIZED = -1;
    private static SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy", Locale.getDefault());
    private static final int YEAR_VIEW = 1;
    private AccessibleDateAnimator mAnimator;
    private final Calendar mCalendar = Calendar.getInstance();
    private OnDateSetListener mCallBack;
    private boolean mCloseOnSingleTapDay = true;
    private int mCurrentView = -1;
    private DateFormatSymbols mDateFormatSymbols = new DateFormatSymbols();
    private TextView mDayOfWeekView;
    private String mDayPickerDescription;
    private DayPickerView mDayPickerView;
    private boolean mDelayAnimation = true;
    private Button mDoneButton;
    private long mLastVibrate;
    private HashSet<OnDateChangedListener> mListeners = new HashSet();
    private int mMaxYear = MAX_YEAR;
    private int mMinYear = MIN_YEAR;
    private LinearLayout mMonthAndDayView;
    private String mSelectDay;
    private String mSelectYear;
    private TextView mSelectedDayTextView;
    private TextView mSelectedMonthTextView;
    private boolean mVibrate = true;
    private Vibrator mVibrator;
    private int mWeekStart = this.mCalendar.getFirstDayOfWeek();
    private String mYearPickerDescription;
    private YearPickerView mYearPickerView;
    private TextView mYearView;

    interface OnDateChangedListener {
        void onDateChanged();
    }

    public interface OnDateSetListener {
        void onDateSet(DatePickerDialog datePickerDialog, int i, int i2, int i3);
    }

    private void adjustDayInMonthIfNeeded(int month, int year) {
        int day = this.mCalendar.get(5);
        int daysInMonth = Utils.getDaysInMonth(month, year);
        if (day > daysInMonth) {
            this.mCalendar.set(5, daysInMonth);
        }
    }

    public static DatePickerDialog newInstance(OnDateSetListener onDateSetListener, int year, int month, int day) {
        return newInstance(onDateSetListener, year, month, day, true);
    }

    public static DatePickerDialog newInstance(OnDateSetListener onDateSetListener, int year, int month, int day, boolean vibrate) {
        DatePickerDialog datePickerDialog = new DatePickerDialog();
        datePickerDialog.initialize(onDateSetListener, year, month, day, vibrate);
        return datePickerDialog;
    }

    public void setVibrate(boolean vibrate) {
        this.mVibrate = vibrate;
    }

    private void setCurrentView(int currentView) {
        setCurrentView(currentView, false);
    }

    @SuppressLint({"NewApi"})
    private void setCurrentView(int currentView, boolean forceRefresh) {
        long timeInMillis = this.mCalendar.getTimeInMillis();
        switch (currentView) {
            case 0:
                ObjectAnimator monthDayAnim = Utils.getPulseAnimator(this.mMonthAndDayView, 0.9f, 1.05f);
                if (this.mDelayAnimation) {
                    monthDayAnim.setStartDelay(500);
                    this.mDelayAnimation = false;
                }
                this.mDayPickerView.onDateChanged();
                if (this.mCurrentView != currentView || forceRefresh) {
                    this.mMonthAndDayView.setSelected(true);
                    this.mYearView.setSelected(false);
                    this.mAnimator.setDisplayedChild(0);
                    this.mCurrentView = currentView;
                }
                monthDayAnim.start();
                this.mAnimator.setContentDescription(this.mDayPickerDescription + ": " + DateUtils.formatDateTime(getActivity(), timeInMillis, 16));
                Utils.tryAccessibilityAnnounce(this.mAnimator, this.mSelectDay);
                return;
            case 1:
                ObjectAnimator yearAnim = Utils.getPulseAnimator(this.mYearView, 0.85f, 1.1f);
                if (this.mDelayAnimation) {
                    yearAnim.setStartDelay(500);
                    this.mDelayAnimation = false;
                }
                this.mYearPickerView.onDateChanged();
                if (this.mCurrentView != currentView || forceRefresh) {
                    this.mMonthAndDayView.setSelected(false);
                    this.mYearView.setSelected(true);
                    this.mAnimator.setDisplayedChild(1);
                    this.mCurrentView = currentView;
                }
                yearAnim.start();
                this.mAnimator.setContentDescription(this.mYearPickerDescription + ": " + YEAR_FORMAT.format(Long.valueOf(timeInMillis)));
                Utils.tryAccessibilityAnnounce(this.mAnimator, this.mSelectYear);
                return;
            default:
                return;
        }
    }

    @SuppressLint({"NewApi"})
    private void updateDisplay(boolean announce) {
        if (this.mDayOfWeekView != null) {
            this.mCalendar.setFirstDayOfWeek(1);
            this.mDayOfWeekView.setText(this.mDateFormatSymbols.getWeekdays()[this.mCalendar.get(7)].toUpperCase(Locale.getDefault()));
        }
        if (this.mSelectedMonthTextView != null) {
            this.mSelectedMonthTextView.setText(this.mDateFormatSymbols.getMonths()[this.mCalendar.get(2)].toUpperCase(Locale.getDefault()));
        }
        if (this.mSelectedDayTextView != null) {
            this.mSelectedDayTextView.setText(DAY_FORMAT.format(this.mCalendar.getTime()));
        }
        if (this.mYearView != null) {
            this.mYearView.setText(YEAR_FORMAT.format(this.mCalendar.getTime()));
        }
        long millis = this.mCalendar.getTimeInMillis();
        this.mAnimator.setDateMillis(millis);
        this.mMonthAndDayView.setContentDescription(DateUtils.formatDateTime(getActivity(), millis, 24));
        if (announce) {
            Utils.tryAccessibilityAnnounce(this.mAnimator, DateUtils.formatDateTime(getActivity(), millis, 20));
        }
    }

    private void updatePickers() {
        Iterator<OnDateChangedListener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            ((OnDateChangedListener) iterator.next()).onDateChanged();
        }
    }

    public int getFirstDayOfWeek() {
        return this.mWeekStart;
    }

    public int getMaxYear() {
        return this.mMaxYear;
    }

    public int getMinYear() {
        return this.mMinYear;
    }

    public CalendarDay getSelectedDay() {
        return new CalendarDay(this.mCalendar);
    }

    public void initialize(OnDateSetListener onDateSetListener, int year, int month, int day, boolean vibrate) {
        if (year > MAX_YEAR) {
            throw new IllegalArgumentException("year end must < 2037");
        } else if (year < MIN_YEAR) {
            throw new IllegalArgumentException("year end must > 1902");
        } else {
            this.mCallBack = onDateSetListener;
            this.mCalendar.set(1, year);
            this.mCalendar.set(2, month);
            this.mCalendar.set(5, day);
            this.mVibrate = vibrate;
        }
    }

    public void onClick(View view) {
        tryVibrate();
        if (view.getId() == R.id.date_picker_year) {
            setCurrentView(1);
        } else if (view.getId() == R.id.date_picker_month_and_day) {
            setCurrentView(0);
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Activity activity = getActivity();
        activity.getWindow().setSoftInputMode(3);
        this.mVibrator = (Vibrator) activity.getSystemService("vibrator");
        if (bundle != null) {
            this.mCalendar.set(1, bundle.getInt("year"));
            this.mCalendar.set(2, bundle.getInt("month"));
            this.mCalendar.set(5, bundle.getInt("day"));
            this.mVibrate = bundle.getBoolean(KEY_VIBRATE);
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup parent, Bundle bundle) {
        getDialog().getWindow().requestFeature(1);
        View view = layoutInflater.inflate(R.layout.date_picker_dialog, null);
        this.mDayOfWeekView = (TextView) view.findViewById(R.id.date_picker_header);
        this.mMonthAndDayView = (LinearLayout) view.findViewById(R.id.date_picker_month_and_day);
        this.mMonthAndDayView.setOnClickListener(this);
        this.mSelectedMonthTextView = (TextView) view.findViewById(R.id.date_picker_month);
        this.mSelectedDayTextView = (TextView) view.findViewById(R.id.date_picker_day);
        this.mYearView = (TextView) view.findViewById(R.id.date_picker_year);
        this.mYearView.setOnClickListener(this);
        int listPosition = -1;
        int currentView = 0;
        int listPositionOffset = 0;
        if (bundle != null) {
            this.mWeekStart = bundle.getInt("week_start");
            this.mMinYear = bundle.getInt(KEY_YEAR_START);
            this.mMaxYear = bundle.getInt(KEY_YEAR_END);
            currentView = bundle.getInt(KEY_CURRENT_VIEW);
            listPosition = bundle.getInt(KEY_LIST_POSITION);
            listPositionOffset = bundle.getInt(KEY_LIST_POSITION_OFFSET);
        }
        Activity activity = getActivity();
        this.mDayPickerView = new DayPickerView(activity, this);
        this.mYearPickerView = new YearPickerView(activity, this);
        Resources resources = getResources();
        this.mDayPickerDescription = resources.getString(R$string.day_picker_description);
        this.mSelectDay = resources.getString(R$string.select_day);
        this.mYearPickerDescription = resources.getString(R$string.year_picker_description);
        this.mSelectYear = resources.getString(R$string.select_year);
        this.mAnimator = (AccessibleDateAnimator) view.findViewById(R.id.animator);
        this.mAnimator.addView(this.mDayPickerView);
        this.mAnimator.addView(this.mYearPickerView);
        this.mAnimator.setDateMillis(this.mCalendar.getTimeInMillis());
        AlphaAnimation inAlphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        inAlphaAnimation.setDuration(300);
        this.mAnimator.setInAnimation(inAlphaAnimation);
        AlphaAnimation outAlphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        outAlphaAnimation.setDuration(300);
        this.mAnimator.setOutAnimation(outAlphaAnimation);
        this.mDoneButton = (Button) view.findViewById(R.id.done);
        this.mDoneButton.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                DatePickerDialog.this.onDoneButtonClick();
            }
        });
        updateDisplay(false);
        setCurrentView(currentView, true);
        if (listPosition != -1) {
            if (currentView == 0) {
                this.mDayPickerView.postSetSelection(listPosition);
            }
            if (currentView == 1) {
                this.mYearPickerView.postSetSelectionFromTop(listPosition, listPositionOffset);
            }
        }
        return view;
    }

    private void onDoneButtonClick() {
        tryVibrate();
        if (this.mCallBack != null) {
            this.mCallBack.onDateSet(this, this.mCalendar.get(1), this.mCalendar.get(2) + 1, this.mCalendar.get(5));
        }
        dismiss();
    }

    public void onDayOfMonthSelected(int year, int month, int day) {
        this.mCalendar.set(1, year);
        this.mCalendar.set(2, month);
        this.mCalendar.set(5, day);
        updatePickers();
        updateDisplay(true);
        if (this.mCloseOnSingleTapDay) {
            onDoneButtonClick();
        }
    }

    public void initDate(int year, int month, int day) {
        this.mCalendar.set(1, year);
        this.mCalendar.set(2, month);
        this.mCalendar.set(5, day);
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("year", this.mCalendar.get(1));
        bundle.putInt("month", this.mCalendar.get(2));
        bundle.putInt("day", this.mCalendar.get(5));
        bundle.putInt("week_start", this.mWeekStart);
        bundle.putInt(KEY_YEAR_START, this.mMinYear);
        bundle.putInt(KEY_YEAR_END, this.mMaxYear);
        bundle.putInt(KEY_CURRENT_VIEW, this.mCurrentView);
        int listPosition = -1;
        if (this.mCurrentView == 0) {
            listPosition = this.mDayPickerView.getMostVisiblePosition();
        }
        if (this.mCurrentView == 1) {
            listPosition = this.mYearPickerView.getFirstVisiblePosition();
            bundle.putInt(KEY_LIST_POSITION_OFFSET, this.mYearPickerView.getFirstPositionOffset());
        }
        bundle.putInt(KEY_LIST_POSITION, listPosition);
        bundle.putBoolean(KEY_VIBRATE, this.mVibrate);
    }

    public void onYearSelected(int year) {
        adjustDayInMonthIfNeeded(this.mCalendar.get(2), year);
        this.mCalendar.set(1, year);
        updatePickers();
        setCurrentView(0);
        updateDisplay(true);
    }

    public void registerOnDateChangedListener(OnDateChangedListener onDateChangedListener) {
        this.mListeners.add(onDateChangedListener);
    }

    public void setFirstDayOfWeek(int startOfWeek) {
        if (startOfWeek < 1 || startOfWeek > 7) {
            throw new IllegalArgumentException("Value must be between Calendar.SUNDAY and Calendar.SATURDAY");
        }
        this.mWeekStart = startOfWeek;
        if (this.mDayPickerView != null) {
            this.mDayPickerView.onChange();
        }
    }

    public void setOnDateSetListener(OnDateSetListener onDateSetListener) {
        this.mCallBack = onDateSetListener;
    }

    public void setYearRange(int minYear, int maxYear) {
        if (maxYear <= minYear) {
            throw new IllegalArgumentException("Year end must be larger than year start");
        } else if (maxYear > MAX_YEAR) {
            throw new IllegalArgumentException("max year end must < 2037");
        } else if (minYear < MIN_YEAR) {
            throw new IllegalArgumentException("min year end must > 1902");
        } else {
            this.mMinYear = minYear;
            this.mMaxYear = maxYear;
            if (this.mDayPickerView != null) {
                this.mDayPickerView.onChange();
            }
        }
    }

    public void tryVibrate() {
        if (this.mVibrator != null && this.mVibrate) {
            long timeInMillis = SystemClock.uptimeMillis();
            if (timeInMillis - this.mLastVibrate >= 125) {
                this.mVibrator.vibrate(5);
                this.mLastVibrate = timeInMillis;
            }
        }
    }

    public void setCloseOnSingleTapDay(boolean closeOnSingleTapDay) {
        this.mCloseOnSingleTapDay = closeOnSingleTapDay;
    }

    public void setDateOnClickListener(Context context, int setDataButton, int minYear, int maxYear) {
        setDateOnClickListener(context, ((FragmentActivity) context).findViewById(setDataButton), minYear, maxYear);
    }

    public void setDateOnClickListener(final Context context, View setDataView, final int minYear, final int maxYear) {
        Calendar calendar = Calendar.getInstance();
        initialize((OnDateSetListener) context, calendar.get(1), calendar.get(2), calendar.get(5), true);
        setDataView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                DatePickerDialog.this.setYearRange(minYear, maxYear);
                if (!DatePickerDialog.this.isAdded()) {
                    FragmentTransaction ft = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    ft.add(DatePickerDialog.this, "date_picker");
                    ft.commit();
                }
            }
        });
    }

    public void setDateOnClickListener(final Context context, OnDateSetListener listener, View setDataView, final int minYear, final int maxYear) {
        Calendar calendar = Calendar.getInstance();
        initialize(listener, calendar.get(1), calendar.get(2), calendar.get(5), true);
        setDataView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                DatePickerDialog.this.setYearRange(minYear, maxYear);
                if (!DatePickerDialog.this.isAdded()) {
                    FragmentTransaction ft = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    ft.add(DatePickerDialog.this, "date_picker");
                    ft.commit();
                }
            }
        });
    }
}
