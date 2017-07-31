package com.huluxia.ui.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class HTActivity extends FragmentActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        a.Ft().n(this);
    }

    protected void a(Bundle savedInstanceState, boolean portrait) {
        super.onCreate(savedInstanceState);
        a.Ft().n(this);
    }

    protected void onDestroy() {
        super.onDestroy();
        a.Ft().o(this);
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }
}
