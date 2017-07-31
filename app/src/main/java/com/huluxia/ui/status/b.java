package com.huluxia.ui.status;

import android.support.v4.app.Fragment;
import com.huluxia.framework.base.widget.status.StatusFragmentPage;
import com.huluxia.framework.base.widget.status.StatusFragmentPage.StatusPageFragmentBuilder;

/* compiled from: McFragmentStatusBuilder */
public class b extends StatusPageFragmentBuilder {
    public b(Fragment page) {
        super(page);
    }

    protected StatusFragmentPage newPage() {
        return new e((Fragment) this.mPage);
    }
}
