package com.MCWorld.framework.base.widget.datetimepicker;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.MCWorld.framework.R;
import com.MCWorld.framework.R$string;
import com.MCWorld.framework.base.widget.datetimepicker.RadialPickerLayout.OnValueSelectedListener;
import com.nineoldandroids.animation.ObjectAnimator;
import io.netty.handler.codec.http.HttpConstants;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

public class TimePickerDialog extends DialogFragment implements OnValueSelectedListener {
    public static final int AM = 0;
    public static final int AMPM_INDEX = 2;
    public static final int ENABLE_PICKER_INDEX = 3;
    public static final int HOUR_INDEX = 0;
    private static final String KEY_CURRENT_ITEM_SHOWING = "current_item_showing";
    private static final String KEY_HOUR_OF_DAY = "hour_of_day";
    private static final String KEY_IN_KB_MODE = "in_kb_mode";
    private static final String KEY_IS_24_HOUR_VIEW = "is_24_hour_view";
    private static final String KEY_MINUTE = "minute";
    private static final String KEY_TYPED_TIMES = "typed_times";
    private static final String KEY_VIBRATE = "vibrate";
    public static final int MINUTE_INDEX = 1;
    public static final int PM = 1;
    private static final int PULSE_ANIMATOR_DELAY = 300;
    private static final String TAG = "TimePickerDialog";
    private boolean mAllowAutoAdvance;
    private int mAmKeyCode;
    private View mAmPmHitspace;
    private TextView mAmPmTextView;
    private String mAmText;
    private int mBlack;
    private int mBlue;
    private OnTimeSetListener mCallback;
    private boolean mCloseOnSingleTapMinute = true;
    private String mDeletedKeyFormat;
    private TextView mDoneButton;
    private String mDoublePlaceholderText;
    private String mHourPickerDescription;
    private TextView mHourSpaceView;
    private TextView mHourView;
    private boolean mInKbMode;
    private int mInitialHourOfDay;
    private int mInitialMinute;
    private boolean mIs24HourMode;
    private Node mLegalTimesTree;
    private String mMinutePickerDescription;
    private TextView mMinuteSpaceView;
    private TextView mMinuteView;
    private char mPlaceholderText;
    private int mPmKeyCode;
    private String mPmText;
    private String mSelectHours;
    private String mSelectMinutes;
    private RadialPickerLayout mTimePicker;
    private ArrayList<Integer> mTypedTimes;
    private boolean mVibrate = true;

    private class KeyboardListener implements OnKeyListener {
        private KeyboardListener() {
        }

        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == 1) {
                return TimePickerDialog.this.processKeyUp(keyCode);
            }
            return false;
        }
    }

    private class Node {
        private ArrayList<Node> mChildren = new ArrayList();
        private int[] mLegalKeys;

        public Node(int... legalKeys) {
            this.mLegalKeys = legalKeys;
        }

        public void addChild(Node child) {
            this.mChildren.add(child);
        }

        public boolean containsKey(int key) {
            for (int i : this.mLegalKeys) {
                if (i == key) {
                    return true;
                }
            }
            return false;
        }

        public Node canReach(int key) {
            if (this.mChildren == null) {
                return null;
            }
            Iterator it = this.mChildren.iterator();
            while (it.hasNext()) {
                Node child = (Node) it.next();
                if (child.containsKey(key)) {
                    return child;
                }
            }
            return null;
        }
    }

    public interface OnTimeSetListener {
        void onTimeSet(RadialPickerLayout radialPickerLayout, int i, int i2);
    }

    public static TimePickerDialog newInstance(OnTimeSetListener callback, int hourOfDay, int minute, boolean is24HourMode) {
        return newInstance(callback, hourOfDay, minute, is24HourMode, true);
    }

    public static TimePickerDialog newInstance(OnTimeSetListener callback, int hourOfDay, int minute, boolean is24HourMode, boolean vibrate) {
        TimePickerDialog ret = new TimePickerDialog();
        ret.initialize(callback, hourOfDay, minute, is24HourMode, vibrate);
        return ret;
    }

    public void initialize(OnTimeSetListener callback, int hourOfDay, int minute, boolean is24HourMode, boolean vibrate) {
        this.mCallback = callback;
        this.mInitialHourOfDay = hourOfDay;
        this.mInitialMinute = minute;
        this.mIs24HourMode = is24HourMode;
        this.mInKbMode = false;
        this.mVibrate = vibrate;
    }

    public void setOnTimeSetListener(OnTimeSetListener callback) {
        this.mCallback = callback;
    }

    public void setStartTime(int hourOfDay, int minute) {
        this.mInitialHourOfDay = hourOfDay;
        this.mInitialMinute = minute;
        this.mInKbMode = false;
    }

    public void setVibrate(boolean vibrate) {
        this.mVibrate = vibrate;
        if (this.mTimePicker != null) {
            this.mTimePicker.setVibrate(vibrate);
        }
    }

    public void setCloseOnSingleTapMinute(boolean closeOnSingleTapMinute) {
        this.mCloseOnSingleTapMinute = closeOnSingleTapMinute;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_HOUR_OF_DAY) && savedInstanceState.containsKey("minute") && savedInstanceState.containsKey(KEY_IS_24_HOUR_VIEW)) {
            this.mInitialHourOfDay = savedInstanceState.getInt(KEY_HOUR_OF_DAY);
            this.mInitialMinute = savedInstanceState.getInt("minute");
            this.mIs24HourMode = savedInstanceState.getBoolean(KEY_IS_24_HOUR_VIEW);
            this.mInKbMode = savedInstanceState.getBoolean(KEY_IN_KB_MODE);
            this.mVibrate = savedInstanceState.getBoolean(KEY_VIBRATE);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(1);
        View view = inflater.inflate(R.layout.time_picker_dialog, null);
        KeyboardListener keyboardListener = new KeyboardListener();
        view.findViewById(R.id.time_picker_dialog).setOnKeyListener(keyboardListener);
        Resources res = getResources();
        this.mHourPickerDescription = res.getString(R$string.hour_picker_description);
        this.mSelectHours = res.getString(R$string.select_hours);
        this.mMinutePickerDescription = res.getString(R$string.minute_picker_description);
        this.mSelectMinutes = res.getString(R$string.select_minutes);
        this.mBlue = res.getColor(R.color.blue);
        this.mBlack = res.getColor(R.color.numbers_text_color);
        this.mHourView = (TextView) view.findViewById(R.id.hours);
        this.mHourView.setOnKeyListener(keyboardListener);
        this.mHourSpaceView = (TextView) view.findViewById(R.id.hour_space);
        this.mMinuteSpaceView = (TextView) view.findViewById(R.id.minutes_space);
        this.mMinuteView = (TextView) view.findViewById(R.id.minutes);
        this.mMinuteView.setOnKeyListener(keyboardListener);
        this.mAmPmTextView = (TextView) view.findViewById(R.id.ampm_label);
        this.mAmPmTextView.setOnKeyListener(keyboardListener);
        if (VERSION.SDK_INT <= 14) {
            this.mAmPmTextView.setTransformationMethod(new TransformationMethod() {
                private final Locale locale = TimePickerDialog.this.getResources().getConfiguration().locale;

                public CharSequence getTransformation(CharSequence source, View view) {
                    return source != null ? source.toString().toUpperCase(this.locale) : null;
                }

                public void onFocusChanged(View view, CharSequence sourceText, boolean focused, int direction, Rect previouslyFocusedRect) {
                }
            });
        }
        String[] amPmTexts = new DateFormatSymbols().getAmPmStrings();
        this.mAmText = amPmTexts[0];
        this.mPmText = amPmTexts[1];
        this.mTimePicker = (RadialPickerLayout) view.findViewById(R.id.time_picker);
        this.mTimePicker.setOnValueSelectedListener(this);
        this.mTimePicker.setOnKeyListener(keyboardListener);
        this.mTimePicker.initialize(getActivity(), this.mInitialHourOfDay, this.mInitialMinute, this.mIs24HourMode, this.mVibrate);
        int currentItemShowing = 0;
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_CURRENT_ITEM_SHOWING)) {
                currentItemShowing = savedInstanceState.getInt(KEY_CURRENT_ITEM_SHOWING);
            }
        }
        setCurrentItemShowing(currentItemShowing, false, true, true);
        this.mTimePicker.invalidate();
        this.mHourView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                TimePickerDialog.this.setCurrentItemShowing(0, true, false, true);
                TimePickerDialog.this.mTimePicker.tryVibrate();
            }
        });
        this.mMinuteView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                TimePickerDialog.this.setCurrentItemShowing(1, true, false, true);
                TimePickerDialog.this.mTimePicker.tryVibrate();
            }
        });
        this.mDoneButton = (TextView) view.findViewById(R.id.done_button);
        this.mDoneButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                TimePickerDialog.this.onDoneButtonClick();
            }
        });
        this.mDoneButton.setOnKeyListener(keyboardListener);
        this.mAmPmHitspace = view.findViewById(R.id.ampm_hitspace);
        if (this.mIs24HourMode) {
            this.mAmPmTextView.setVisibility(8);
            LayoutParams paramsSeparator = new LayoutParams(-2, -2);
            paramsSeparator.addRule(13);
            ((TextView) view.findViewById(R.id.separator)).setLayoutParams(paramsSeparator);
        } else {
            this.mAmPmTextView.setVisibility(0);
            updateAmPmDisplay(this.mInitialHourOfDay < 12 ? 0 : 1);
            this.mAmPmHitspace.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    TimePickerDialog.this.mTimePicker.tryVibrate();
                    int amOrPm = TimePickerDialog.this.mTimePicker.getIsCurrentlyAmOrPm();
                    if (amOrPm == 0) {
                        amOrPm = 1;
                    } else if (amOrPm == 1) {
                        amOrPm = 0;
                    }
                    TimePickerDialog.this.updateAmPmDisplay(amOrPm);
                    TimePickerDialog.this.mTimePicker.setAmOrPm(amOrPm);
                }
            });
        }
        this.mAllowAutoAdvance = true;
        setHour(this.mInitialHourOfDay, true);
        setMinute(this.mInitialMinute);
        this.mDoublePlaceholderText = res.getString(R$string.time_placeholder);
        this.mDeletedKeyFormat = res.getString(R$string.deleted_key);
        this.mPlaceholderText = this.mDoublePlaceholderText.charAt(0);
        this.mPmKeyCode = -1;
        this.mAmKeyCode = -1;
        generateLegalTimesTree();
        if (this.mInKbMode) {
            this.mTypedTimes = savedInstanceState.getIntegerArrayList(KEY_TYPED_TIMES);
            tryStartingKbMode(-1);
            this.mHourView.invalidate();
        } else if (this.mTypedTimes == null) {
            this.mTypedTimes = new ArrayList();
        }
        return view;
    }

    private void onDoneButtonClick() {
        if (this.mInKbMode && isTypedTimeFullyLegal()) {
            finishKbMode(false);
        } else {
            this.mTimePicker.tryVibrate();
        }
        if (this.mCallback != null) {
            this.mCallback.onTimeSet(this.mTimePicker, this.mTimePicker.getHours(), this.mTimePicker.getMinutes());
        }
        dismiss();
    }

    private void updateAmPmDisplay(int amOrPm) {
        if (amOrPm == 0) {
            this.mAmPmTextView.setText(this.mAmText);
            Utils.tryAccessibilityAnnounce(this.mTimePicker, this.mAmText);
            this.mAmPmHitspace.setContentDescription(this.mAmText);
        } else if (amOrPm == 1) {
            this.mAmPmTextView.setText(this.mPmText);
            Utils.tryAccessibilityAnnounce(this.mTimePicker, this.mPmText);
            this.mAmPmHitspace.setContentDescription(this.mPmText);
        } else {
            this.mAmPmTextView.setText(this.mDoublePlaceholderText);
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        if (this.mTimePicker != null) {
            outState.putInt(KEY_HOUR_OF_DAY, this.mTimePicker.getHours());
            outState.putInt("minute", this.mTimePicker.getMinutes());
            outState.putBoolean(KEY_IS_24_HOUR_VIEW, this.mIs24HourMode);
            outState.putInt(KEY_CURRENT_ITEM_SHOWING, this.mTimePicker.getCurrentItemShowing());
            outState.putBoolean(KEY_IN_KB_MODE, this.mInKbMode);
            if (this.mInKbMode) {
                outState.putIntegerArrayList(KEY_TYPED_TIMES, this.mTypedTimes);
            }
            outState.putBoolean(KEY_VIBRATE, this.mVibrate);
        }
    }

    public void onValueSelected(int pickerIndex, int newValue, boolean autoAdvance) {
        if (pickerIndex == 0) {
            setHour(newValue, false);
            String announcement = String.format("%d", new Object[]{Integer.valueOf(newValue)});
            if (this.mAllowAutoAdvance && autoAdvance) {
                setCurrentItemShowing(1, true, true, false);
                announcement = announcement + ". " + this.mSelectMinutes;
            }
            Utils.tryAccessibilityAnnounce(this.mTimePicker, announcement);
        } else if (pickerIndex == 1) {
            setMinute(newValue);
            if (this.mCloseOnSingleTapMinute) {
                onDoneButtonClick();
            }
        } else if (pickerIndex == 2) {
            updateAmPmDisplay(newValue);
        } else if (pickerIndex == 3) {
            if (!isTypedTimeFullyLegal()) {
                this.mTypedTimes.clear();
            }
            finishKbMode(true);
        }
    }

    private void setHour(int value, boolean announce) {
        String format;
        if (this.mIs24HourMode) {
            format = "%02d";
        } else {
            format = "%d";
            value %= 12;
            if (value == 0) {
                value = 12;
            }
        }
        CharSequence text = String.format(format, new Object[]{Integer.valueOf(value)});
        this.mHourView.setText(text);
        this.mHourSpaceView.setText(text);
        if (announce) {
            Utils.tryAccessibilityAnnounce(this.mTimePicker, text);
        }
    }

    private void setMinute(int value) {
        if (value == 60) {
            value = 0;
        }
        CharSequence text = String.format(Locale.getDefault(), "%02d", new Object[]{Integer.valueOf(value)});
        Utils.tryAccessibilityAnnounce(this.mTimePicker, text);
        this.mMinuteView.setText(text);
        this.mMinuteSpaceView.setText(text);
    }

    private void setCurrentItemShowing(int index, boolean animateCircle, boolean delayLabelAnimate, boolean announce) {
        TextView labelToAnimate;
        this.mTimePicker.setCurrentItemShowing(index, animateCircle);
        if (index == 0) {
            int hours = this.mTimePicker.getHours();
            if (!this.mIs24HourMode) {
                hours %= 12;
            }
            this.mTimePicker.setContentDescription(this.mHourPickerDescription + ": " + hours);
            if (announce) {
                Utils.tryAccessibilityAnnounce(this.mTimePicker, this.mSelectHours);
            }
            labelToAnimate = this.mHourView;
        } else {
            this.mTimePicker.setContentDescription(this.mMinutePickerDescription + ": " + this.mTimePicker.getMinutes());
            if (announce) {
                Utils.tryAccessibilityAnnounce(this.mTimePicker, this.mSelectMinutes);
            }
            labelToAnimate = this.mMinuteView;
        }
        int hourColor = index == 0 ? this.mBlue : this.mBlack;
        int minuteColor = index == 1 ? this.mBlue : this.mBlack;
        this.mHourView.setTextColor(hourColor);
        this.mMinuteView.setTextColor(minuteColor);
        ObjectAnimator pulseAnimator = Utils.getPulseAnimator(labelToAnimate, 0.85f, 1.1f);
        if (delayLabelAnimate) {
            pulseAnimator.setStartDelay(300);
        }
        pulseAnimator.start();
    }

    private boolean processKeyUp(int keyCode) {
        if (keyCode == 111 || keyCode == 4) {
            dismiss();
            return true;
        }
        if (keyCode == 61) {
            if (this.mInKbMode) {
                if (!isTypedTimeFullyLegal()) {
                    return true;
                }
                finishKbMode(true);
                return true;
            }
        } else if (keyCode == 66) {
            if (this.mInKbMode) {
                if (!isTypedTimeFullyLegal()) {
                    return true;
                }
                finishKbMode(false);
            }
            if (this.mCallback != null) {
                this.mCallback.onTimeSet(this.mTimePicker, this.mTimePicker.getHours(), this.mTimePicker.getMinutes());
            }
            dismiss();
            return true;
        } else if (keyCode == 67) {
            if (this.mInKbMode && !this.mTypedTimes.isEmpty()) {
                String deletedKeyStr;
                int deleted = deleteLastTypedKey();
                if (deleted == getAmOrPmKeyCode(0)) {
                    deletedKeyStr = this.mAmText;
                } else if (deleted == getAmOrPmKeyCode(1)) {
                    deletedKeyStr = this.mPmText;
                } else {
                    deletedKeyStr = String.format("%d", new Object[]{Integer.valueOf(getValFromKeyCode(deleted))});
                }
                Utils.tryAccessibilityAnnounce(this.mTimePicker, String.format(this.mDeletedKeyFormat, new Object[]{deletedKeyStr}));
                updateDisplay(true);
            }
        } else if (keyCode == 7 || keyCode == 8 || keyCode == 9 || keyCode == 10 || keyCode == 11 || keyCode == 12 || keyCode == 13 || keyCode == 14 || keyCode == 15 || keyCode == 16 || (!this.mIs24HourMode && (keyCode == getAmOrPmKeyCode(0) || keyCode == getAmOrPmKeyCode(1)))) {
            if (this.mInKbMode) {
                if (!addKeyIfLegal(keyCode)) {
                    return true;
                }
                updateDisplay(false);
                return true;
            } else if (this.mTimePicker == null) {
                Log.e(TAG, "Unable to initiate keyboard mode, TimePicker was null.");
                return true;
            } else {
                this.mTypedTimes.clear();
                tryStartingKbMode(keyCode);
                return true;
            }
        }
        return false;
    }

    private void tryStartingKbMode(int keyCode) {
        if (!this.mTimePicker.trySettingInputEnabled(false)) {
            return;
        }
        if (keyCode == -1 || addKeyIfLegal(keyCode)) {
            this.mInKbMode = true;
            this.mDoneButton.setEnabled(false);
            updateDisplay(false);
        }
    }

    private boolean addKeyIfLegal(int keyCode) {
        if (this.mIs24HourMode && this.mTypedTimes.size() == 4) {
            return false;
        }
        if (!this.mIs24HourMode && isTypedTimeFullyLegal()) {
            return false;
        }
        this.mTypedTimes.add(Integer.valueOf(keyCode));
        if (isTypedTimeLegalSoFar()) {
            int val = getValFromKeyCode(keyCode);
            Utils.tryAccessibilityAnnounce(this.mTimePicker, String.format("%d", new Object[]{Integer.valueOf(val)}));
            if (isTypedTimeFullyLegal()) {
                if (!this.mIs24HourMode && this.mTypedTimes.size() <= 3) {
                    this.mTypedTimes.add(this.mTypedTimes.size() - 1, Integer.valueOf(7));
                    this.mTypedTimes.add(this.mTypedTimes.size() - 1, Integer.valueOf(7));
                }
                this.mDoneButton.setEnabled(true);
            }
            return true;
        }
        deleteLastTypedKey();
        return false;
    }

    private boolean isTypedTimeLegalSoFar() {
        Node node = this.mLegalTimesTree;
        Iterator it = this.mTypedTimes.iterator();
        while (it.hasNext()) {
            node = node.canReach(((Integer) it.next()).intValue());
            if (node == null) {
                return false;
            }
        }
        return true;
    }

    private boolean isTypedTimeFullyLegal() {
        boolean z = false;
        if (this.mIs24HourMode) {
            int[] values = getEnteredTime(null);
            if (values[0] < 0 || values[1] < 0 || values[1] >= 60) {
                return false;
            }
            return true;
        }
        if (this.mTypedTimes.contains(Integer.valueOf(getAmOrPmKeyCode(0))) || this.mTypedTimes.contains(Integer.valueOf(getAmOrPmKeyCode(1)))) {
            z = true;
        }
        return z;
    }

    private int deleteLastTypedKey() {
        int deleted = ((Integer) this.mTypedTimes.remove(this.mTypedTimes.size() - 1)).intValue();
        if (!isTypedTimeFullyLegal()) {
            this.mDoneButton.setEnabled(false);
        }
        return deleted;
    }

    private void finishKbMode(boolean updateDisplays) {
        this.mInKbMode = false;
        if (!this.mTypedTimes.isEmpty()) {
            int[] values = getEnteredTime(null);
            this.mTimePicker.setTime(values[0], values[1]);
            if (!this.mIs24HourMode) {
                this.mTimePicker.setAmOrPm(values[2]);
            }
            this.mTypedTimes.clear();
        }
        if (updateDisplays) {
            updateDisplay(false);
            this.mTimePicker.trySettingInputEnabled(true);
        }
    }

    private void updateDisplay(boolean allowEmptyDisplay) {
        if (allowEmptyDisplay || !this.mTypedTimes.isEmpty()) {
            String hourStr;
            String minuteStr;
            Boolean[] enteredZeros = new Boolean[]{Boolean.valueOf(false), Boolean.valueOf(false)};
            int[] values = getEnteredTime(enteredZeros);
            String hourFormat = enteredZeros[0].booleanValue() ? "%02d" : "%2d";
            String minuteFormat = enteredZeros[1].booleanValue() ? "%02d" : "%2d";
            if (values[0] == -1) {
                hourStr = this.mDoublePlaceholderText;
            } else {
                hourStr = String.format(hourFormat, new Object[]{Integer.valueOf(values[0])}).replace(HttpConstants.SP_CHAR, this.mPlaceholderText);
            }
            if (values[1] == -1) {
                minuteStr = this.mDoublePlaceholderText;
            } else {
                minuteStr = String.format(minuteFormat, new Object[]{Integer.valueOf(values[1])}).replace(HttpConstants.SP_CHAR, this.mPlaceholderText);
            }
            this.mHourView.setText(hourStr);
            this.mHourSpaceView.setText(hourStr);
            this.mHourView.setTextColor(this.mBlack);
            this.mMinuteView.setText(minuteStr);
            this.mMinuteSpaceView.setText(minuteStr);
            this.mMinuteView.setTextColor(this.mBlack);
            if (!this.mIs24HourMode) {
                updateAmPmDisplay(values[2]);
                return;
            }
            return;
        }
        int hour = this.mTimePicker.getHours();
        int minute = this.mTimePicker.getMinutes();
        setHour(hour, true);
        setMinute(minute);
        if (!this.mIs24HourMode) {
            updateAmPmDisplay(hour < 12 ? 0 : 1);
        }
        setCurrentItemShowing(this.mTimePicker.getCurrentItemShowing(), true, true, true);
        this.mDoneButton.setEnabled(true);
    }

    private int getValFromKeyCode(int keyCode) {
        switch (keyCode) {
            case 7:
                return 0;
            case 8:
                return 1;
            case 9:
                return 2;
            case 10:
                return 3;
            case 11:
                return 4;
            case 12:
                return 5;
            case 13:
                return 6;
            case 14:
                return 7;
            case 15:
                return 8;
            case 16:
                return 9;
            default:
                return -1;
        }
    }

    private int[] getEnteredTime(Boolean[] enteredZeros) {
        int amOrPm = -1;
        int startIndex = 1;
        if (!this.mIs24HourMode && isTypedTimeFullyLegal()) {
            int keyCode = ((Integer) this.mTypedTimes.get(this.mTypedTimes.size() - 1)).intValue();
            if (keyCode == getAmOrPmKeyCode(0)) {
                amOrPm = 0;
            } else if (keyCode == getAmOrPmKeyCode(1)) {
                amOrPm = 1;
            }
            startIndex = 2;
        }
        int minute = -1;
        int hour = -1;
        for (int i = startIndex; i <= this.mTypedTimes.size(); i++) {
            int val = getValFromKeyCode(((Integer) this.mTypedTimes.get(this.mTypedTimes.size() - i)).intValue());
            if (i == startIndex) {
                minute = val;
            } else if (i == startIndex + 1) {
                minute += val * 10;
                if (enteredZeros != null && val == 0) {
                    enteredZeros[1] = Boolean.valueOf(true);
                }
            } else if (i == startIndex + 2) {
                hour = val;
            } else if (i == startIndex + 3) {
                hour += val * 10;
                if (enteredZeros != null && val == 0) {
                    enteredZeros[0] = Boolean.valueOf(true);
                }
            }
        }
        return new int[]{hour, minute, amOrPm};
    }

    private int getAmOrPmKeyCode(int amOrPm) {
        if (this.mAmKeyCode == -1 || this.mPmKeyCode == -1) {
            KeyCharacterMap kcm = KeyCharacterMap.load(-1);
            int i = 0;
            while (i < Math.max(this.mAmText.length(), this.mPmText.length())) {
                if (this.mAmText.toLowerCase(Locale.getDefault()).charAt(i) != this.mPmText.toLowerCase(Locale.getDefault()).charAt(i)) {
                    KeyEvent[] events = kcm.getEvents(new char[]{this.mAmText.toLowerCase(Locale.getDefault()).charAt(i), this.mPmText.toLowerCase(Locale.getDefault()).charAt(i)});
                    if (events == null || events.length != 4) {
                        Log.e(TAG, "Unable to find keycodes for AM and PM.");
                    } else {
                        this.mAmKeyCode = events[0].getKeyCode();
                        this.mPmKeyCode = events[2].getKeyCode();
                    }
                } else {
                    i++;
                }
            }
        }
        if (amOrPm == 0) {
            return this.mAmKeyCode;
        }
        if (amOrPm == 1) {
            return this.mPmKeyCode;
        }
        return -1;
    }

    private void generateLegalTimesTree() {
        this.mLegalTimesTree = new Node(new int[0]);
        if (this.mIs24HourMode) {
            Node node = new Node(7, 8, 9, 10, 11, 12);
            node = new Node(7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
            node.addChild(node);
            Node firstDigit = new Node(7, 8);
            this.mLegalTimesTree.addChild(firstDigit);
            node = new Node(7, 8, 9, 10, 11, 12);
            firstDigit.addChild(node);
            node.addChild(node);
            node.addChild(new Node(13, 14, 15, 16));
            node = new Node(13, 14, 15, 16);
            firstDigit.addChild(node);
            node.addChild(node);
            firstDigit = new Node(9);
            this.mLegalTimesTree.addChild(firstDigit);
            node = new Node(7, 8, 9, 10);
            firstDigit.addChild(node);
            node.addChild(node);
            node = new Node(11, 12);
            firstDigit.addChild(node);
            node.addChild(node);
            firstDigit = new Node(10, 11, 12, 13, 14, 15, 16);
            this.mLegalTimesTree.addChild(firstDigit);
            firstDigit.addChild(node);
            return;
        }
        Node ampm = new Node(getAmOrPmKeyCode(0), getAmOrPmKeyCode(1));
        firstDigit = new Node(8);
        this.mLegalTimesTree.addChild(firstDigit);
        firstDigit.addChild(ampm);
        node = new Node(7, 8, 9);
        firstDigit.addChild(node);
        node.addChild(ampm);
        node = new Node(7, 8, 9, 10, 11, 12);
        node.addChild(node);
        node.addChild(ampm);
        Node fourthDigit = new Node(7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        node.addChild(fourthDigit);
        fourthDigit.addChild(ampm);
        node = new Node(13, 14, 15, 16);
        node.addChild(node);
        node.addChild(ampm);
        node = new Node(10, 11, 12);
        firstDigit.addChild(node);
        node = new Node(7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        node.addChild(node);
        node.addChild(ampm);
        firstDigit = new Node(9, 10, 11, 12, 13, 14, 15, 16);
        this.mLegalTimesTree.addChild(firstDigit);
        firstDigit.addChild(ampm);
        node = new Node(7, 8, 9, 10, 11, 12);
        firstDigit.addChild(node);
        node = new Node(7, 8, 9, 10, 11, 12, 13, 14, 15, 16);
        node.addChild(node);
        node.addChild(ampm);
    }

    public void setTimeOnClickListener(Context context, int setTimeButton, boolean is24HourMode) {
        setTimeOnClickListener(context, ((FragmentActivity) context).findViewById(setTimeButton), is24HourMode);
    }

    public void setTimeOnClickListener(final Context context, View setTimeView, boolean is24HourMode) {
        Calendar calendar = Calendar.getInstance();
        initialize((OnTimeSetListener) context, calendar.get(11), calendar.get(12), is24HourMode, true);
        setTimeView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (!TimePickerDialog.this.isAdded()) {
                    FragmentTransaction ft = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
                    ft.add(TimePickerDialog.this, "time_picker");
                    ft.commit();
                }
            }
        });
    }
}
