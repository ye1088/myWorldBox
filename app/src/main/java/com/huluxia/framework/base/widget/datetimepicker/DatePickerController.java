package com.huluxia.framework.base.widget.datetimepicker;

import com.huluxia.framework.base.widget.datetimepicker.SimpleMonthAdapter.CalendarDay;

interface DatePickerController {
    int getFirstDayOfWeek();

    int getMaxYear();

    int getMinYear();

    CalendarDay getSelectedDay();

    void onDayOfMonthSelected(int i, int i2, int i3);

    void onYearSelected(int i);

    void registerOnDateChangedListener(OnDateChangedListener onDateChangedListener);

    void tryVibrate();
}
