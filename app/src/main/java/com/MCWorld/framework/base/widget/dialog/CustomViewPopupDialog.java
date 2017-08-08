package com.MCWorld.framework.base.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import com.MCWorld.framework.R$style;

public class CustomViewPopupDialog extends Dialog {
    private View mRootView;

    public CustomViewPopupDialog(Context context, View customView) {
        super(context, R$style.Dialog_Input);
        this.mRootView = customView;
        setContentView(this.mRootView);
        Window window = getWindow();
        LayoutParams params = window.getAttributes();
        params.height = -2;
        params.width = -1;
        window.setGravity(80);
        window.setAttributes(params);
        window.setWindowAnimations(R$style.DialogAnimation);
    }

    public CustomViewPopupDialog(Context context, View customView, boolean animation) {
        super(context, R$style.Dialog_Input);
        this.mRootView = customView;
        setContentView(this.mRootView);
        Window window = getWindow();
        LayoutParams params = window.getAttributes();
        params.height = -2;
        params.width = -2;
        window.setAttributes(params);
        if (animation) {
            window.setWindowAnimations(R$style.DialogAnimation);
        }
    }
}
