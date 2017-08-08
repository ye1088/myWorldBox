package com.MCWorld.framework.base.widget.datetimepicker;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import com.MCWorld.framework.base.widget.datetimepicker.SimpleMonthView.OnDayClickListener;
import java.util.Calendar;
import java.util.HashMap;

public class SimpleMonthAdapter extends BaseAdapter implements OnDayClickListener {
    protected static final int MONTHS_IN_YEAR = 12;
    protected static int WEEK_7_OVERHANG_HEIGHT = 7;
    private final Context mContext;
    private final DatePickerController mController;
    private CalendarDay mSelectedDay;

    public static class CalendarDay {
        private Calendar calendar;
        int day;
        int month;
        int year;

        public CalendarDay() {
            setTime(System.currentTimeMillis());
        }

        public CalendarDay(int year, int month, int day) {
            setDay(year, month, day);
        }

        public CalendarDay(long timeInMillis) {
            setTime(timeInMillis);
        }

        public CalendarDay(Calendar calendar) {
            this.year = calendar.get(1);
            this.month = calendar.get(2);
            this.day = calendar.get(5);
        }

        private void setTime(long timeInMillis) {
            if (this.calendar == null) {
                this.calendar = Calendar.getInstance();
            }
            this.calendar.setTimeInMillis(timeInMillis);
            this.month = this.calendar.get(2);
            this.year = this.calendar.get(1);
            this.day = this.calendar.get(5);
        }

        public void set(CalendarDay calendarDay) {
            this.year = calendarDay.year;
            this.month = calendarDay.month;
            this.day = calendarDay.day;
        }

        public void setDay(int year, int month, int day) {
            this.year = year;
            this.month = month;
            this.day = day;
        }
    }

    public SimpleMonthAdapter(Context context, DatePickerController datePickerController) {
        this.mContext = context;
        this.mController = datePickerController;
        init();
        setSelectedDay(this.mController.getSelectedDay());
    }

    private boolean isSelectedDayInMonth(int year, int month) {
        return this.mSelectedDay.year == year && this.mSelectedDay.month == month;
    }

    public int getCount() {
        return ((this.mController.getMaxYear() - this.mController.getMinYear()) + 1) * 12;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        SimpleMonthView v;
        HashMap<String, Integer> hashMap = null;
        if (convertView != null) {
            v = (SimpleMonthView) convertView;
            hashMap = (HashMap) v.getTag();
        } else {
            v = new SimpleMonthView(this.mContext);
            v.setLayoutParams(new LayoutParams(-1, -1));
            v.setClickable(true);
            v.setOnDayClickListener(this);
        }
        if (hashMap == null) {
            hashMap = new HashMap();
        }
        hashMap.clear();
        int month = position % 12;
        int year = (position / 12) + this.mController.getMinYear();
        int selectedDay = -1;
        if (isSelectedDayInMonth(year, month)) {
            selectedDay = this.mSelectedDay.day;
        }
        v.reuse();
        hashMap.put(SimpleMonthView.VIEW_PARAMS_SELECTED_DAY, Integer.valueOf(selectedDay));
        hashMap.put(SimpleMonthView.VIEW_PARAMS_YEAR, Integer.valueOf(year));
        hashMap.put(SimpleMonthView.VIEW_PARAMS_MONTH, Integer.valueOf(month));
        hashMap.put("week_start", Integer.valueOf(this.mController.getFirstDayOfWeek()));
        v.setMonthParams(hashMap);
        v.invalidate();
        return v;
    }

    protected void init() {
        this.mSelectedDay = new CalendarDay(System.currentTimeMillis());
    }

    public void onDayClick(SimpleMonthView simpleMonthView, CalendarDay calendarDay) {
        if (calendarDay != null) {
            onDayTapped(calendarDay);
        }
    }

    protected void onDayTapped(CalendarDay calendarDay) {
        this.mController.tryVibrate();
        this.mController.onDayOfMonthSelected(calendarDay.year, calendarDay.month, calendarDay.day);
        setSelectedDay(calendarDay);
    }

    public void setSelectedDay(CalendarDay calendarDay) {
        this.mSelectedDay = calendarDay;
        notifyDataSetChanged();
    }
}
