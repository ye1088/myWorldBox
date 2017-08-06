package com.huluxia.widget.wheel.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/* compiled from: AbstractWheelTextAdapter */
public abstract class b extends a {
    public static final int bGA = -15724528;
    public static final int bGB = -9437072;
    public static final int bGC = 24;
    public static final int bGy = -1;
    protected static final int bGz = 0;
    protected LayoutInflater bGD;
    protected int bGE;
    protected int bGF;
    protected int bGG;
    protected Context context;
    private int textColor;
    private int textSize;

    protected abstract CharSequence mz(int i);

    protected b(Context context) {
        this(context, -1);
    }

    protected b(Context context, int itemResource) {
        this(context, itemResource, 0);
    }

    protected b(Context context, int itemResource, int itemTextResource) {
        this.textColor = bGA;
        this.textSize = 24;
        this.context = context;
        this.bGE = itemResource;
        this.bGF = itemTextResource;
        this.bGD = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    public int getTextColor() {
        return this.textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextSize() {
        return this.textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }

    public int Qv() {
        return this.bGE;
    }

    public void mw(int itemResourceId) {
        this.bGE = itemResourceId;
    }

    public int Qw() {
        return this.bGF;
    }

    public void mx(int itemTextResourceId) {
        this.bGF = itemTextResourceId;
    }

    public int Qx() {
        return this.bGG;
    }

    public void my(int emptyItemResourceId) {
        this.bGG = emptyItemResourceId;
    }

    public View a(int index, View convertView, ViewGroup parent) {
        if (index < 0 || index >= Qa()) {
            return null;
        }
        if (convertView == null) {
            convertView = getView(this.bGE, parent);
        }
        TextView textView = h(convertView, this.bGF);
        if (textView != null) {
            CharSequence text = mz(index);
            if (text == null) {
                text = "";
            }
            textView.setText(text);
            if (this.bGE == -1) {
                c(textView);
            }
        }
        return convertView;
    }

    public View a(View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = getView(this.bGG, parent);
        }
        if (this.bGG == -1 && (convertView instanceof TextView)) {
            c((TextView) convertView);
        }
        return convertView;
    }

    protected void c(TextView view) {
        view.setTextColor(this.textColor);
        view.setGravity(17);
        view.setTextSize((float) this.textSize);
        view.setLines(1);
        view.setTypeface(Typeface.SANS_SERIF, 1);
    }

    private TextView h(View view, int textResource) {
        if (textResource == 0) {
            try {
                if (view instanceof TextView) {
                    return (TextView) view;
                }
            } catch (ClassCastException e) {
                Log.e("AbstractWheelAdapter", "You must supply a_isRightVersion resource ID for a_isRightVersion TextView");
                throw new IllegalStateException("AbstractWheelAdapter requires the resource ID to be a_isRightVersion TextView", e);
            }
        }
        if (textResource != 0) {
            return (TextView) view.findViewById(textResource);
        }
        return null;
    }

    private View getView(int resource, ViewGroup parent) {
        switch (resource) {
            case -1:
                return new TextView(this.context);
            case 0:
                return null;
            default:
                return this.bGD.inflate(resource, parent, false);
        }
    }
}
