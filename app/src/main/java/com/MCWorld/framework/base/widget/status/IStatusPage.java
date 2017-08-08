package com.MCWorld.framework.base.widget.status;

import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.framework.base.widget.status.state.LoadingStatement;
import com.MCWorld.framework.base.widget.status.state.NoDataStatement;
import com.MCWorld.framework.base.widget.status.state.ReloadStatement;

public interface IStatusPage {
    OnClickListener getLoadListener();

    OnClickListener getNoDataListener();

    void hideStatus();

    void showLoading(View view);

    void showLoading(View view, LoadingStatement loadingStatement);

    void showNetworkErr(View view);

    void showNoData(View view);

    void showNoData(View view, NoDataStatement noDataStatement);

    void showReload(View view);

    void showReload(View view, ReloadStatement reloadStatement);
}
