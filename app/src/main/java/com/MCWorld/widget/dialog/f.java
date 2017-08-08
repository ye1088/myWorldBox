package com.MCWorld.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;

import com.MCWorld.framework.R;
import com.MCWorld.mcinterface.h;

/* compiled from: EnterServerPromptDialog */
public class f extends Dialog {
    private a bwN = null;
    private f bwO;
    private int checked = 0;
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ f bwP;

        {
            this.bwP = this$0;
        }

        public void onClick(View view) {
            int i = 1;
            int i2 = 0;
            switch (view.getId()) {
                case R.id.tv_confirm:
                    if (this.bwP.checked != 0) {
                        i2 = 1;
                    }
                    h.ho(i2);
                    this.bwP.sK();
                    return;
                case R.id.menu_check:
                    f fVar = this.bwP;
                    if (this.bwP.checked != 0) {
                        i = 0;
                    }
                    fVar.checked = i;
                    return;
                default:
                    return;
            }
        }
    };
    private Activity mContext = null;

    /* compiled from: EnterServerPromptDialog */
    public interface a {
        void rd();
    }

    public f(Activity context, a callback) {
        super(context, R.style.theme_dialog_normal);
        this.mContext = context;
        this.bwO = this;
        this.bwN = callback;
        show();
    }

    public int On() {
        return this.checked;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_image_alert);
        findViewById(R.id.tv_confirm).setOnClickListener(this.mClickListener);
        findViewById(R.id.menu_check).setOnClickListener(this.mClickListener);
    }

    protected void sK() {
        dismiss();
        if (this.bwN != null) {
            this.bwN.rd();
        }
    }
}
