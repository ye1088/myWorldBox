package com.huluxia.framework.base.widget.status;

import android.view.View.OnClickListener;

public interface IStatusFragment {
    void setLoadListener(OnClickListener onClickListener);

    void setNoDataListener(OnClickListener onClickListener);
}
