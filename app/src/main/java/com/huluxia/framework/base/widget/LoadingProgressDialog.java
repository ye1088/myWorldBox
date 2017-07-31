package com.huluxia.framework.base.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.drawable.AnimationDrawable;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.huluxia.framework.R;
import com.huluxia.framework.R$style;

public class LoadingProgressDialog extends Dialog {
    public LoadingProgressDialog(Context context) {
        super(context);
    }

    public LoadingProgressDialog(Context context, int theme) {
        super(context, theme);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        ((AnimationDrawable) ((ImageView) findViewById(R.id.spinnerImageView)).getBackground()).start();
    }

    public void setMessage(CharSequence message) {
        if (message != null && message.length() > 0) {
            findViewById(R.id.message).setVisibility(0);
            TextView txt = (TextView) findViewById(R.id.message);
            txt.setText(message);
            txt.invalidate();
        }
    }

    public static LoadingProgressDialog show(Context context, CharSequence message, boolean cancelable, OnCancelListener cancelListener) {
        LoadingProgressDialog dialog = new LoadingProgressDialog(context, R$style.Custom_Progress);
        dialog.setTitle("");
        dialog.setContentView(R.layout.progress_custom);
        if (message == null || message.length() == 0) {
            dialog.findViewById(R.id.message).setVisibility(8);
        } else {
            ((TextView) dialog.findViewById(R.id.message)).setText(message);
        }
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        dialog.getWindow().getAttributes().gravity = 17;
        LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount = 0.2f;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
        return dialog;
    }
}
