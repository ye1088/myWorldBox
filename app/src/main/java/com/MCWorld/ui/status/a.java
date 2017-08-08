package com.MCWorld.ui.status;

import android.support.v4.app.FragmentActivity;
import com.MCWorld.framework.base.widget.status.StatusActivityPage;
import com.MCWorld.framework.base.widget.status.StatusActivityPage.StatusPageActivityBuilder;

/* compiled from: McActivityStatusBuilder */
public class a extends StatusPageActivityBuilder {
    public a(FragmentActivity page) {
        super(page);
    }

    protected StatusActivityPage newPage() {
        return new d((FragmentActivity) this.mPage);
    }
}
