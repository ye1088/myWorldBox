package com.huluxia.ui.status;

import android.support.v4.app.Fragment;
import com.huluxia.framework.base.widget.status.StatusFragmentPage;
import com.huluxia.framework.base.widget.status.state.LoadingFragment;
import com.huluxia.framework.base.widget.status.state.LoadingStatement;
import com.huluxia.framework.base.widget.status.state.NetworkErrorFragment;
import com.huluxia.framework.base.widget.status.state.NetworkErrorStatement;
import com.huluxia.framework.base.widget.status.state.NoDataFragment;
import com.huluxia.framework.base.widget.status.state.NoDataStatement;
import com.huluxia.framework.base.widget.status.state.ReloadFragment;
import com.huluxia.framework.base.widget.status.state.ReloadStatement;
import com.huluxia.ui.status.page.McLoadingFragment;
import com.huluxia.ui.status.page.McNetworkErrorFragment;
import com.huluxia.ui.status.page.McNoDataFragment;
import com.huluxia.ui.status.page.McReloadFragment;

/* compiled from: SFPage */
public class e extends StatusFragmentPage {
    public e(Fragment fragment) {
        super(fragment);
    }

    protected LoadingFragment getLoadingFragment(LoadingStatement statement) {
        return McLoadingFragment.a(statement);
    }

    protected NetworkErrorFragment getNetworkErrorFragment(NetworkErrorStatement statement) {
        return McNetworkErrorFragment.a(statement);
    }

    protected NoDataFragment getNoDataFragment(NoDataStatement statement) {
        return McNoDataFragment.a(statement);
    }

    protected ReloadFragment getReloadFragment(ReloadStatement statement) {
        return McReloadFragment.a(statement);
    }
}
