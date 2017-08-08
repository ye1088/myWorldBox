package com.MCWorld.mcfloat.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.MCWorld.framework.R;
import com.MCWorld.widget.EditTextWithDelete;

/* compiled from: FloatDialogBuildRectSet */
public class c extends Dialog {
    private a abH = null;
    private b abh = null;
    private OnClickListener abv = new OnClickListener(this) {
        final /* synthetic */ c abI;

        {
            this.abI = this$0;
        }

        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.floatDlgbuildCancel:
                    this.abI.dismiss();
                    return;
                case R.id.floatDlgbuildOK:
                    this.abI.up();
                    return;
                case R.id.floatDlgbuildRectTypeHollow:
                case R.id.floatDlgbuildRectTypeSolid:
                case R.id.floatDlgbuildRectTypeLine:
                    TextView txtView = (TextView) this.abI.findViewById(R.id.floatDlgbuildRectType);
                    txtView.setTag(view.getTag());
                    txtView.setText(((TextView) view).getText());
                    return;
                default:
                    return;
            }
        }
    };

    /* compiled from: FloatDialogBuildRectSet */
    public interface a {
        void a(b bVar);
    }

    /* compiled from: FloatDialogBuildRectSet */
    public static class b {
        public int type;
        public int x;
        public int y;
        public int z;

        public b() {
            this.z = 5;
            this.y = 5;
            this.x = 5;
            this.type = 5;
        }

        public b(b info) {
            this.type = info.type;
            this.x = info.x;
            this.y = info.y;
            this.z = info.z;
        }

        public void b(b info) {
            this.type = info.type;
            this.x = info.x;
            this.y = info.y;
            this.z = info.z;
        }

        public void d(int type, int x, int y, int z) {
            this.type = type;
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    public c(Context context) {
        super(context, R.style.Dialog);
    }

    public void up() {
        TextView errText = (TextView) findViewById(R.id.floatDlgbuildError);
        int x = ((EditTextWithDelete) findViewById(R.id.floatDlgbuildEditRectLineX)).lL(0);
        if (x == 0) {
            errText.setText("* 请输入长方形的边长x");
            return;
        }
        int z = ((EditTextWithDelete) findViewById(R.id.floatDlgbuildEditRectLineZ)).lL(0);
        if (z == 0) {
            errText.setText("* 请输入长方形的边长z");
            return;
        }
        int y = ((EditTextWithDelete) findViewById(R.id.floatDlgbuildEditRectLineY)).lL(0);
        if (y == 0) {
            errText.setText("* 请输入长方形的高度y");
            return;
        }
        this.abh.x = x;
        this.abh.y = y;
        this.abh.z = z;
        this.abh.type = ((Integer) findViewById(R.id.floatDlgbuildRectType).getTag()).intValue();
        this.abH.a(this.abh);
        super.dismiss();
    }

    public void a(b info, a cb) {
        this.abH = cb;
        this.abh = new b(info);
        super.show();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.float_dialog_build_rect);
        findViewById(R.id.floatDlgbuildOK).setOnClickListener(this.abv);
        findViewById(R.id.floatDlgbuildCancel).setOnClickListener(this.abv);
        TextView btn = (TextView) findViewById(R.id.floatDlgbuildRectTypeHollow);
        btn.setOnClickListener(this.abv);
        btn.setTag(Integer.valueOf(0));
        if (this.abh.type == 0) {
            this.abv.onClick(btn);
        }
        btn = (TextView) findViewById(R.id.floatDlgbuildRectTypeSolid);
        btn.setOnClickListener(this.abv);
        btn.setTag(Integer.valueOf(1));
        if (this.abh.type == 1) {
            this.abv.onClick(btn);
        }
        btn = (TextView) findViewById(R.id.floatDlgbuildRectTypeLine);
        btn.setOnClickListener(this.abv);
        btn.setTag(Integer.valueOf(2));
        if (this.abh.type == 2) {
            this.abv.onClick(btn);
        }
        ((EditText) findViewById(R.id.floatDlgbuildEditRectLineX)).setText("" + this.abh.x);
        ((EditText) findViewById(R.id.floatDlgbuildEditRectLineY)).setText("" + this.abh.y);
        ((EditText) findViewById(R.id.floatDlgbuildEditRectLineZ)).setText("" + this.abh.z);
    }
}
