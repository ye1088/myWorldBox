package com.huluxia.framework.base.widget.status;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import com.huluxia.framework.base.widget.status.StatusActivityPage.StatusPageActivityBuilder;
import com.huluxia.framework.base.widget.status.StatusFragmentPage.StatusPageFragmentBuilder;

public class StatusPageBuilderFactory {
    public static StatusPageActivityBuilder newBuilder(FragmentActivity page) {
        return new StatusPageActivityBuilder(page);
    }

    public static StatusPageFragmentBuilder newBuilder(Fragment page) {
        return new StatusPageFragmentBuilder(page);
    }
}
