package com.MCWorld.framework.base.widget.status;

import android.os.Bundle;
import android.support.annotation.z;
import android.support.v4.app.Fragment;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.MCWorld.framework.AppConfig;
import com.MCWorld.framework.R$string;

public abstract class AbsStatusFragment<T extends Statement> extends Fragment implements IStatusFragment {
    protected static final String KEY_STATEMENT = "STATEMENT";
    protected OnClickListener mLoadListener;
    protected OnClickListener mNoDataListener;
    protected T mStatement;

    public void onCreate(@z Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Bundle arguement = getArguments();
            if (arguement != null) {
                this.mStatement = (Statement) arguement.getParcelable(KEY_STATEMENT);
                return;
            }
            return;
        }
        this.mStatement = (Statement) savedInstanceState.getParcelable(KEY_STATEMENT);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_STATEMENT, this.mStatement);
    }

    public void setNoDataListener(OnClickListener listener) {
        this.mNoDataListener = listener;
    }

    public void setLoadListener(OnClickListener listener) {
        this.mLoadListener = listener;
    }

    public void checkNetToast() {
        Toast.makeText(AppConfig.getInstance().getAppContext(), R$string.str_network_not_capable, 0).show();
    }
}
