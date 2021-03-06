package com.MCWorld.framework.base.widget.status;

import android.view.View.OnClickListener;
import com.MCWorld.framework.base.widget.status.state.LoadingStatement;
import com.MCWorld.framework.base.widget.status.state.NoDataStatement;
import com.MCWorld.framework.base.widget.status.state.ReloadStatement;

public interface IDataStatus {
    OnClickListener getLoadListener();

    OnClickListener getNoDataListener();

    void hideStatus();

    void showLoading();

    void showLoading(LoadingStatement loadingStatement);

    void showNetworkErr();

    void showNoData();

    void showNoData(NoDataStatement noDataStatement);

    void showReload();

    void showReload(ReloadStatement reloadStatement);
}
