package com.MCWorld.framework.base.widget.status;

import android.os.Bundle;
import android.support.annotation.z;
import android.view.View;
import com.MCWorld.framework.base.utils.Supplier;
import com.MCWorld.framework.base.widget.pager.PagerFragment;

public abstract class StatusAbsFragment extends PagerFragment {
    StatusBasePage mPage;

    abstract Supplier<StatusBasePage<?>> getStatusPage();

    public void onCreate(@z Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mPage = (StatusBasePage) getStatusPage().get();
    }

    public void onViewCreated(View view, @z Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mPage.showPending();
    }

    public void onDestroy() {
        super.onDestroy();
        this.mPage = null;
    }

    public void onResume() {
        super.onResume();
        this.mPage.restore();
    }
}
