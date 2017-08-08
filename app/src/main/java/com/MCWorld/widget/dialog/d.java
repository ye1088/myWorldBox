package com.MCWorld.widget.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.MCWorld.bbs.b.f;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.bbs.b.m;
import com.MCWorld.utils.at;
import java.util.Calendar;
import java.util.Date;

/* compiled from: DatePickerDialogAdapter */
public class d implements OnClickListener {
    private static final int bwp = 12;
    private static final int bwq = 1;
    private static final int[] bwr = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static final int bws = 1;
    private int MAX_YEAR = 2100;
    private int MIN_YEAR = 1900;
    private ImageButton bwA;
    private EditText bwB;
    private ImageButton bwt;
    private ImageButton bwu;
    private EditText bwv;
    private ImageButton bww;
    private ImageButton bwx;
    private EditText bwy;
    private ImageButton bwz;
    private int day;
    private int month;
    private int year;

    /* compiled from: DatePickerDialogAdapter */
    static class a {
        public int background;
        public int buttonText;
        public int bwC;
        public int bwD;
        public int bwE;
        public int bwF;
        public int bwG;
        public int icon;

        a() {
        }
    }

    public d(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public View bF(Context context) {
        aa(this.year, this.month, this.day);
        a res = Ol();
        View llBody = LayoutInflater.from(context).inflate(i.include_dialog_datepicker, null);
        LinearLayout llDatepicker = (LinearLayout) llBody.findViewById(g.ly_datepicker);
        LinearLayout llYear = new LinearLayout(context);
        llYear.setOrientation(1);
        LayoutParams lpYear = new LayoutParams(-2, -1);
        lpYear.weight = 29.0f;
        llYear.setLayoutParams(lpYear);
        llDatepicker.addView(llYear);
        this.bwt = new ImageButton(context);
        this.bwt.setScaleType(ScaleType.CENTER_INSIDE);
        LayoutParams lpButton = new LayoutParams(-1, -2);
        this.bwt.setLayoutParams(lpButton);
        this.bwt.setOnClickListener(this);
        this.bwt.setImageResource(res.bwC);
        this.bwt.setBackgroundResource(res.bwD);
        llYear.addView(this.bwt);
        this.bwv = new EditText(context);
        this.bwv.setBackgroundResource(res.bwE);
        this.bwv.setGravity(17);
        this.bwv.setText(String.valueOf(this.year));
        this.bwv.setInputType(0);
        this.bwv.setSingleLine();
        this.bwv.setMinEms(4);
        this.bwv.setMaxEms(4);
        int dp_5 = at.dipToPx(context, 5);
        this.bwv.setPadding(dp_5, dp_5, dp_5, dp_5);
        LayoutParams lpEditText = new LayoutParams(-1, -2);
        lpEditText.weight = 1.0f;
        this.bwv.setLayoutParams(lpEditText);
        llYear.addView(this.bwv);
        this.bwu = new ImageButton(context);
        this.bwu.setScaleType(ScaleType.CENTER_INSIDE);
        this.bwu.setLayoutParams(lpButton);
        this.bwu.setOnClickListener(this);
        this.bwu.setImageResource(res.bwF);
        this.bwu.setBackgroundResource(res.bwG);
        llYear.addView(this.bwu);
        LinearLayout llMonth = new LinearLayout(context);
        llMonth.setOrientation(1);
        LayoutParams lpMonth = new LayoutParams(-2, -1);
        lpMonth.weight = 23.0f;
        lpMonth.setMargins(11, 0, 0, 0);
        llMonth.setLayoutParams(lpMonth);
        llDatepicker.addView(llMonth);
        this.bww = new ImageButton(context);
        this.bww.setScaleType(ScaleType.CENTER_INSIDE);
        this.bww.setLayoutParams(lpButton);
        this.bww.setOnClickListener(this);
        this.bww.setImageResource(res.bwC);
        this.bww.setBackgroundResource(res.bwD);
        llMonth.addView(this.bww);
        this.bwy = new EditText(context);
        this.bwy.setBackgroundResource(res.bwE);
        this.bwy.setGravity(17);
        this.bwy.setInputType(0);
        this.bwy.setSingleLine();
        this.bwy.setMinEms(2);
        this.bwy.setMaxEms(2);
        this.bwy.setText(String.valueOf(this.month));
        this.bwy.setPadding(dp_5, dp_5, dp_5, dp_5);
        this.bwy.setLayoutParams(lpEditText);
        llMonth.addView(this.bwy);
        this.bwx = new ImageButton(context);
        this.bwx.setScaleType(ScaleType.CENTER_INSIDE);
        this.bwx.setLayoutParams(lpButton);
        this.bwx.setOnClickListener(this);
        this.bwx.setImageResource(res.bwF);
        this.bwx.setBackgroundResource(res.bwG);
        llMonth.addView(this.bwx);
        LinearLayout llDay = new LinearLayout(context);
        llDay.setOrientation(1);
        llDay.setLayoutParams(lpMonth);
        llDatepicker.addView(llDay);
        this.bwz = new ImageButton(context);
        this.bwz.setScaleType(ScaleType.CENTER_INSIDE);
        this.bwz.setLayoutParams(lpButton);
        this.bwz.setOnClickListener(this);
        this.bwz.setImageResource(res.bwC);
        this.bwz.setBackgroundResource(res.bwD);
        llDay.addView(this.bwz);
        this.bwB = new EditText(context);
        this.bwB.setBackgroundResource(res.bwE);
        this.bwB.setGravity(17);
        this.bwB.setInputType(0);
        this.bwB.setSingleLine();
        this.bwB.setMinEms(2);
        this.bwB.setMaxEms(2);
        this.bwB.setText(String.valueOf(this.day));
        this.bwB.setPadding(dp_5, dp_5, dp_5, dp_5);
        this.bwB.setLayoutParams(lpEditText);
        llDay.addView(this.bwB);
        this.bwA = new ImageButton(context);
        this.bwA.setScaleType(ScaleType.CENTER_INSIDE);
        this.bwA.setLayoutParams(lpButton);
        this.bwA.setOnClickListener(this);
        this.bwA.setImageResource(res.bwF);
        this.bwA.setBackgroundResource(res.bwG);
        llDay.addView(this.bwA);
        return llBody;
    }

    private void aa(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
        Ok();
    }

    private void Ok() {
        if (this.year < this.MIN_YEAR) {
            this.year = this.MIN_YEAR;
        } else if (this.year > this.MAX_YEAR) {
            this.year = this.MAX_YEAR;
        }
        if (this.month < 1) {
            this.month = 1;
        } else if (this.month > 12) {
            this.month = 12;
        }
        int maxDay = bwr[this.month - 1];
        if (this.day < 1) {
            this.day = 1;
        } else if (this.day > maxDay) {
            this.day = maxDay;
        }
    }

    private a Ol() {
        a res = new a();
        res.icon = f.app_icon;
        res.buttonText = m.done;
        res.background = com.MCWorld.bbs.b.d.dialog_back;
        res.bwC = f.dp_add;
        res.bwD = f.dp_add_bg;
        res.bwE = f.dp_dig_bg;
        res.bwF = f.dp_sub;
        res.bwG = f.dp_sub_bg;
        return res;
    }

    public void onClick(View view) {
        if (this.bwt.equals(view)) {
            this.year++;
            Ok();
            this.bwv.setText(String.valueOf(this.year));
        } else if (this.bwu.equals(view)) {
            this.year--;
            Ok();
            this.bwv.setText(String.valueOf(this.year));
        } else if (this.bww.equals(view)) {
            this.month++;
            Ok();
            this.bwy.setText(String.valueOf(this.month));
        } else if (this.bwx.equals(view)) {
            this.month--;
            Ok();
            this.bwy.setText(String.valueOf(this.month));
        } else if (this.bwz.equals(view)) {
            this.day++;
            Ok();
            this.bwB.setText(String.valueOf(this.day));
        } else if (this.bwA.equals(view)) {
            this.day--;
            Ok();
            this.bwB.setText(String.valueOf(this.day));
        }
    }

    public Date getDate() {
        Calendar cal = Calendar.getInstance();
        cal.set(1, this.year);
        cal.set(2, this.month - 1);
        cal.set(5, this.day);
        return cal.getTime();
    }

    public void lT(int year) {
        this.MIN_YEAR = year;
    }

    public void lU(int year) {
        this.MAX_YEAR = year;
    }
}
