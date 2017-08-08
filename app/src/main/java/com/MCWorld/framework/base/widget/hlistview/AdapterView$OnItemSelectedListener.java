package com.MCWorld.framework.base.widget.hlistview;

import android.view.View;

public interface AdapterView$OnItemSelectedListener {
    void onItemSelected(AdapterView<?> adapterView, View view, int i, long j);

    void onNothingSelected(AdapterView<?> adapterView);
}
