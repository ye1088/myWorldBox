package com.huluxia.framework.base.widget.status;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.MarginLayoutParams;
import com.huluxia.framework.R;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.widget.status.state.LoadingFragment;
import com.huluxia.framework.base.widget.status.state.LoadingStatement;
import com.huluxia.framework.base.widget.status.state.NetworkErrorFragment;
import com.huluxia.framework.base.widget.status.state.NetworkErrorStatement;
import com.huluxia.framework.base.widget.status.state.NoDataFragment;
import com.huluxia.framework.base.widget.status.state.NoDataStatement;
import com.huluxia.framework.base.widget.status.state.ReloadFragment;
import com.huluxia.framework.base.widget.status.state.ReloadStatement;

public abstract class StatusBasePage<T> implements IDataStatus, IStatusPage {
    private static final String STATUS_TAG = "STATUS_TAG";
    private FragmentManager mFm;
    private Fragment mFragment;
    private T mPage;
    private FragmentActivity mParent;
    private PendingArguement<?> mPending;

    abstract T page();

    public abstract void setBoundedView(int i);

    private void assureContext() {
        if (this.mParent == null) {
            if (this.mPage == null) {
                this.mPage = page();
            }
            if (this.mPage == null) {
                throw new NullPointerException("page is NULL");
            } else if (this.mPage == null) {
            } else {
                if (this.mPage instanceof Fragment) {
                    internalAttach((Fragment) this.mPage);
                } else if (this.mPage instanceof FragmentActivity) {
                    internalAttach((FragmentActivity) this.mPage);
                } else {
                    throw new IllegalArgumentException("page must be fragment or activity");
                }
            }
        }
    }

    void internalAttach(FragmentActivity activity) {
        this.mFm = activity.getSupportFragmentManager();
        this.mParent = activity;
    }

    void internalAttach(Fragment fragment) {
        this.mFm = fragment.getChildFragmentManager();
        this.mFragment = fragment;
        this.mParent = fragment.getActivity();
    }

    public OnClickListener getLoadListener() {
        return null;
    }

    public OnClickListener getNoDataListener() {
        return null;
    }

    public void showLoading() {
        assureContext();
        if (this.mFragment != null) {
            showLoading(this.mFragment.getView());
        } else if (this.mParent != null) {
            showLoading(this.mParent.getWindow().getDecorView());
        }
    }

    public void showLoading(LoadingStatement statement) {
        assureContext();
        if (this.mFragment != null) {
            showLoading(this.mFragment.getView(), statement);
        } else if (this.mParent != null) {
            showLoading(this.mParent.getWindow().getDecorView(), statement);
        }
    }

    public void showReload() {
        assureContext();
        if (this.mFragment != null) {
            showReload(this.mFragment.getView());
        } else if (this.mParent != null) {
            showReload(this.mParent.getWindow().getDecorView());
        }
    }

    public void showReload(ReloadStatement statement) {
        assureContext();
        if (this.mFragment != null) {
            showReload(this.mFragment.getView(), statement);
        } else if (this.mParent != null) {
            showReload(this.mParent.getWindow().getDecorView(), statement);
        }
    }

    public void showNoData() {
        assureContext();
        if (this.mFragment != null) {
            showNoData(this.mFragment.getView());
        } else if (this.mParent != null) {
            showNoData(this.mParent.getWindow().getDecorView());
        }
    }

    public void showNoData(NoDataStatement statement) {
        assureContext();
        if (this.mFragment != null) {
            showNoData(this.mFragment.getView(), statement);
        } else if (this.mParent != null) {
            showNoData(this.mParent.getWindow().getDecorView(), statement);
        }
    }

    public void showNetworkErr() {
        assureContext();
        if (this.mFragment != null) {
            showNetworkErr(this.mFragment.getView());
        } else if (this.mParent != null) {
            showNetworkErr(this.mParent.getWindow().getDecorView());
        }
    }

    public void showLoading(View view) {
        showLoading(view, LoadingStatement.generateDefault());
    }

    public void showLoading(View view, LoadingStatement statement) {
        if (view == null) {
            this.mPending = PendingArguementFactory.newArguement(statement);
            return;
        }
        assureContext();
        if (checkActivityValid()) {
            View status = view.findViewById(R.id.status_layout);
            if (status == null || status.getId() <= 0) {
                HLog.error(this, "showLoading had not set layout id ", new Object[0]);
                return;
            }
            this.mFm.beginTransaction().replace(status.getId(), getLoadingFragment(statement), STATUS_TAG).commitAllowingStateLoss();
        }
    }

    public void showReload(View view) {
        showReload(view, ReloadStatement.generateDefault());
    }

    public void showReload(View view, ReloadStatement statement) {
        if (view == null) {
            this.mPending = PendingArguementFactory.newArguement(statement);
            return;
        }
        assureContext();
        if (checkActivityValid()) {
            View status = view.findViewById(R.id.status_layout);
            if (status == null || status.getId() <= 0) {
                HLog.error(this, "showReload had not set layout id ", new Object[0]);
                return;
            }
            ReloadFragment fragment = getReloadFragment(statement);
            fragment.setLoadListener(getLoadListener());
            this.mFm.beginTransaction().replace(status.getId(), fragment, STATUS_TAG).commitAllowingStateLoss();
        }
    }

    public void showNoData(View view) {
        showNoData(view, NoDataStatement.generateDefault());
    }

    public void showNoData(View view, NoDataStatement statement) {
        if (view == null) {
            this.mPending = PendingArguementFactory.newArguement(statement);
            return;
        }
        assureContext();
        if (checkActivityValid()) {
            View status = view.findViewById(R.id.status_layout);
            if (status == null || status.getId() <= 0) {
                HLog.error(this, "showNoData had not set layout id ", new Object[0]);
                return;
            }
            NoDataFragment fragment = getNoDataFragment(statement);
            fragment.setNoDataListener(getNoDataListener());
            this.mFm.beginTransaction().replace(status.getId(), fragment, STATUS_TAG).commitAllowingStateLoss();
        }
    }

    public void showNetworkErr(View view) {
        if (view == null) {
            this.mPending = PendingArguementFactory.newArguement(NetworkErrorStatement.generateDefault());
            return;
        }
        assureContext();
        if (checkActivityValid()) {
            View status = view.findViewById(R.id.status_layout);
            if (status == null || status.getId() <= 0) {
                HLog.error(this, "showNoData had not set layout id ", new Object[0]);
                return;
            }
            NetworkErrorFragment fragment = getNetworkErrorFragment(null);
            fragment.setLoadListener(getLoadListener());
            this.mFm.beginTransaction().replace(status.getId(), fragment, STATUS_TAG).commitAllowingStateLoss();
        }
    }

    public void showPending() {
        if (this.mPending != null) {
            switch (this.mPending.type) {
                case 0:
                    showLoading((LoadingStatement) this.mPending.statement);
                    break;
                case 1:
                    showReload((ReloadStatement) this.mPending.statement);
                    break;
                case 2:
                    showNoData((NoDataStatement) this.mPending.statement);
                    break;
                case 3:
                    showNetworkErr();
                    break;
            }
            this.mPending = null;
        }
    }

    public void hideStatus() {
        assureContext();
        Fragment fragment = this.mFm.findFragmentByTag(STATUS_TAG);
        if (fragment != null) {
            this.mFm.beginTransaction().remove(fragment).commitAllowingStateLoss();
        }
    }

    public void restore() {
        assureContext();
        Fragment fragment = this.mFm.findFragmentByTag(STATUS_TAG);
        if (fragment == null) {
            return;
        }
        if (fragment instanceof ReloadFragment) {
            ((ReloadFragment) fragment).setLoadListener(getLoadListener());
        } else if (fragment instanceof NoDataFragment) {
            ((NoDataFragment) fragment).setNoDataListener(getNoDataListener());
        } else if (fragment instanceof NetworkErrorFragment) {
            ((NetworkErrorFragment) fragment).setLoadListener(getLoadListener());
        }
    }

    public void setMargin(int left, int top, int right, int bottom) {
        assureContext();
        View statusView = null;
        if (this.mFragment != null) {
            statusView = this.mFragment.getView();
        } else if (this.mParent != null) {
            statusView = this.mParent.getWindow().getDecorView();
        }
        if (statusView != null) {
            View status = statusView.findViewById(R.id.status_layout);
            if (status == null || status.getId() <= 0) {
                HLog.error(this, "setMargin had not set layout id ", new Object[0]);
                return;
            }
            MarginLayoutParams param = (MarginLayoutParams) status.getLayoutParams();
            if (param.leftMargin != left || param.topMargin != top || param.rightMargin != right || param.bottomMargin != bottom) {
                param.leftMargin = left;
                param.topMargin = top;
                param.rightMargin = right;
                param.bottomMargin = bottom;
                status.requestLayout();
            }
        }
    }

    @TargetApi(17)
    protected boolean checkActivityValid() {
        if (this.mParent == null || this.mParent.isFinishing()) {
            return false;
        }
        if (VERSION.SDK_INT < 17 || !this.mParent.isDestroyed()) {
            return true;
        }
        return false;
    }

    protected NoDataFragment getNoDataFragment(NoDataStatement statement) {
        return NoDataFragment.newInstance(statement);
    }

    protected LoadingFragment getLoadingFragment(LoadingStatement statement) {
        return LoadingFragment.newInstance(statement);
    }

    protected ReloadFragment getReloadFragment(ReloadStatement statement) {
        return ReloadFragment.newInstance(statement);
    }

    protected NetworkErrorFragment getNetworkErrorFragment(NetworkErrorStatement statement) {
        return NetworkErrorFragment.newInstance(statement);
    }
}
