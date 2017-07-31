package hlx.launch.game;

import android.content.Context;
import com.huluxia.framework.base.widget.dialog.DialogManager.OkCancelDialogListener;
import hlx.ui.a;

/* compiled from: MCPreLaunchCheck */
class d$2 implements OkCancelDialogListener {
    final /* synthetic */ Context val$context;

    d$2(Context context) {
        this.val$context = context;
    }

    public void onCancel() {
    }

    public void onOk() {
        a.e(this.val$context, false);
    }
}
