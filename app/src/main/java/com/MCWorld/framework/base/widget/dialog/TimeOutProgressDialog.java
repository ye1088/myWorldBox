package com.MCWorld.framework.base.widget.dialog;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;
import com.MCWorld.framework.R$string;

public class TimeOutProgressDialog {
    private DialogManager dialogManager;
    private Handler handler = new Handler(Looper.myLooper());
    private Activity mContext;
    private String msg;
    private Runnable processProgressTimeoutTask = new Runnable() {
        public void run() {
            if (TimeOutProgressDialog.this.dialogManager != null) {
                TimeOutProgressDialog.this.dialogManager.dismissDialog();
            }
            Toast.makeText(TimeOutProgressDialog.this.mContext, R$string.str_network_not_capable, 1).show();
        }
    };
    private long time;

    public TimeOutProgressDialog(Activity context, String msg, long time) {
        this.mContext = context;
        this.msg = msg;
        this.time = time;
        this.dialogManager = new DialogManager(this.mContext);
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void showProcessProgress() {
        if (this.dialogManager != null && this.mContext != null) {
            hideProcessProgress();
            this.dialogManager.showProgressDialog(this.mContext, this.msg, false);
            this.handler.postDelayed(this.processProgressTimeoutTask, this.time);
        }
    }

    public void hideProcessProgress() {
        this.handler.removeCallbacks(this.processProgressTimeoutTask);
        if (this.dialogManager != null) {
            this.dialogManager.dismissDialog();
        }
    }
}
