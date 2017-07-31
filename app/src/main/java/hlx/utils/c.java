package hlx.utils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.huluxia.framework.R;

/* compiled from: DoubleBtnDialog */
public class c extends Dialog {
    private TextView boi;
    private TextView bwJ;
    private TextView bwK;
    private a cgK = null;
    private c cgL;
    private TextView eN;
    private OnClickListener mClickListener = new 1(this);
    private Activity mContext = null;

    /* compiled from: DoubleBtnDialog */
    public interface a {
        void ra();

        void rb();

        void rd();

        void sM();
    }

    public c(Activity context, a callback) {
        super(context, R.style.theme_dialog_normal);
        this.mContext = context;
        this.cgK = callback;
        this.cgL = this;
        if (this.mContext != null && !this.mContext.isFinishing()) {
            show();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_global);
        findViewById(R.id.cb_tip).setOnClickListener(this.mClickListener);
        findViewById(R.id.tv_cancel).setOnClickListener(this.mClickListener);
        findViewById(R.id.tv_confirm).setOnClickListener(this.mClickListener);
        this.eN = (TextView) findViewById(R.id.tv_title);
        this.boi = (TextView) findViewById(R.id.tv_msg);
        this.bwJ = (TextView) findViewById(R.id.tv_cancel);
        this.bwK = (TextView) findViewById(R.id.tv_confirm);
    }

    public void dismiss() {
        if (this.cgK != null) {
            this.cgK.sM();
        }
        super.dismiss();
    }

    public void az(String title, String msg) {
        if (title == null) {
            this.eN.setVisibility(8);
        } else {
            this.eN.setText(title);
        }
        if (msg == null) {
            this.boi.setVisibility(8);
        } else {
            this.boi.setText(msg);
        }
    }

    public void a(String title, CharSequence msg) {
        if (title == null) {
            this.eN.setVisibility(8);
        } else {
            this.eN.setText(title);
        }
        if (msg == null) {
            this.boi.setVisibility(8);
        } else {
            this.boi.setText(msg);
        }
    }

    public void aA(String cancle, String confirm) {
        if (cancle == null) {
            this.bwJ.setVisibility(8);
            findViewById(R.id.split_cancle).setVisibility(8);
        } else {
            this.bwJ.setVisibility(0);
            findViewById(R.id.split_cancle).setVisibility(0);
            this.bwJ.setText(cancle);
        }
        if (confirm == null) {
            this.bwK.setVisibility(8);
            findViewById(R.id.split_cancle).setVisibility(8);
            return;
        }
        this.bwK.setVisibility(0);
        findViewById(R.id.split_cancle).setVisibility(0);
        this.bwK.setText(confirm);
    }

    public void showDialog() {
        super.show();
    }

    public void Oo() {
        findViewById(R.id.cb_tip).setVisibility(0);
    }

    protected void Op() {
        if (!(this.mContext == null || this.mContext.isFinishing())) {
            this.cgL.dismiss();
        }
        if (this.cgK != null) {
            this.cgK.rb();
        }
    }

    protected void sK() {
        if (!(this.mContext == null || this.mContext.isFinishing())) {
            this.cgL.dismiss();
        }
        if (this.cgK != null) {
            this.cgK.rd();
        }
    }
}
