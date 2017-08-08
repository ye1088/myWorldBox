package com.MCWorld.widget.wheel.adapters;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

/* compiled from: WheelViewAdapter */
public interface f {
    int Qa();

    View a(int i, View view, ViewGroup viewGroup);

    View a(View view, ViewGroup viewGroup);

    void registerDataSetObserver(DataSetObserver dataSetObserver);

    void unregisterDataSetObserver(DataSetObserver dataSetObserver);
}
