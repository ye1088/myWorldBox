package com.mojang.android;

import android.widget.TextView;

/* compiled from: TextViewReader */
public class b implements a {
    private TextView bHQ;

    public b(TextView paramTextView) {
        this.bHQ = paramTextView;
    }

    public String QR() {
        return this.bHQ.getText().toString();
    }
}
