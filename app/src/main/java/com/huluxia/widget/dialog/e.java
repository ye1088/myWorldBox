package com.huluxia.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.huluxia.framework.R;
import com.huluxia.utils.at;

/* compiled from: EditDialog */
public class e extends Dialog {
    private a bwH = null;
    private e bwI;
    private TextView bwJ;
    private TextView bwK;
    private EditText bwL;
    private TextView eN;
    private OnClickListener mClickListener = new 1(this);
    private Activity mContext = null;

    /* compiled from: EditDialog */
    public interface a {
        void cf(String str);

        void rb();
    }

    public e(Activity context, a callback) {
        super(context, R.style.theme_dialog_normal);
        this.mContext = context;
        this.bwH = callback;
        this.bwI = this;
        if (this.mContext != null && !this.mContext.isFinishing()) {
            show();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edittext);
        findViewById(R.id.tv_cancel).setOnClickListener(this.mClickListener);
        findViewById(R.id.tv_confirm).setOnClickListener(this.mClickListener);
        this.eN = (TextView) findViewById(R.id.tv_title);
        this.bwL = (EditText) findViewById(R.id.tv_msg);
        this.bwJ = (TextView) findViewById(R.id.tv_cancel);
        this.bwK = (TextView) findViewById(R.id.tv_confirm);
        Om();
    }

    public void showDialog() {
    }

    public void dismiss() {
        super.dismiss();
    }

    public void az(String title, String msg) {
        if (title == null) {
            this.eN.setVisibility(8);
        } else {
            this.eN.setText(title);
        }
        if (msg == null) {
            this.bwL.setVisibility(8);
        } else {
            this.bwL.setText(msg);
        }
    }

    public void a(String title, CharSequence msg) {
        if (title == null) {
            this.eN.setVisibility(8);
        } else {
            this.eN.setText(title);
        }
        if (msg == null) {
            this.bwL.setVisibility(8);
        } else {
            this.bwL.setText(msg);
        }
    }

    public void aA(String cancle, String confirm) {
        if (cancle == null) {
            this.bwJ.setVisibility(8);
            findViewById(R.id.split_cancle).setVisibility(8);
        } else {
            this.bwJ.setVisibility(0);
            this.bwJ.setText(cancle);
        }
        if (confirm != null) {
            this.bwK.setText(confirm);
        }
    }

    public void gJ(String msg) {
        this.bwL.setHint(msg);
    }

    public void setText(String msg) {
        this.bwL.setText(msg);
        this.bwL.requestFocus();
        this.bwL.requestFocusFromTouch();
    }

    private void Om() {
        at.showInputMethod(this.bwL, 200);
    }
}
