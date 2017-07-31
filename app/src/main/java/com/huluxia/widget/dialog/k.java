package com.huluxia.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;

/* compiled from: HTProgressDialog */
public class k extends Dialog {
    private View bxp;
    private TextView bxq = ((TextView) this.bxp.findViewById(g.msgTextView));
    private Context mContext;

    public void gL(String msgText) {
        this.bxq.setText(msgText);
    }

    public k(Context context) {
        super(context, 16973840);
        this.mContext = context;
        this.bxp = LayoutInflater.from(context).inflate(i.progress_dialog, (ViewGroup) findViewById(g.dialog_layout_root));
        this.bxq.setText("请求处理中");
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(this.bxp);
        Window window = getWindow();
        window.setLayout(-2, -2);
        window.setGravity(17);
    }

    public void gM(String text) {
        this.bxq.setText(text);
    }

    public void show() {
        if (this.mContext == null) {
            return;
        }
        if (!(this.mContext instanceof Activity)) {
            super.show();
        } else if (!((Activity) this.mContext).isFinishing()) {
            super.show();
        }
    }

    public void dismiss() {
        if (this.mContext == null) {
            return;
        }
        if (!(this.mContext instanceof Activity)) {
            super.dismiss();
        } else if (!((Activity) this.mContext).isFinishing()) {
            super.dismiss();
        }
    }
}
