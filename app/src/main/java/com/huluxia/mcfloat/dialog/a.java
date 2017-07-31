package com.huluxia.mcfloat.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.huluxia.framework.R;
import com.huluxia.widget.EditTextWithDelete;

/* compiled from: FloatDialogBuildEllipseSet */
public class a extends Dialog {
    private b aaX = null;
    private a abu = null;
    private OnClickListener abv = new OnClickListener(this) {
        final /* synthetic */ a abw;

        {
            this.abw = this$0;
        }

        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.floatDlgbuildCancel:
                    this.abw.dismiss();
                    return;
                case R.id.floatDlgbuildOK:
                    this.abw.up();
                    return;
                case R.id.floatDlgbuildRectTypeHollow:
                case R.id.floatDlgbuildRectTypeSolid:
                case R.id.floatDlgbuildRectTypeLine:
                    TextView txtView = (TextView) this.abw.findViewById(R.id.floatDlgbuildRectType);
                    txtView.setTag(view.getTag());
                    txtView.setText(((TextView) view).getText());
                    return;
                default:
                    return;
            }
        }
    };

    /* compiled from: FloatDialogBuildEllipseSet */
    public interface a {
        void a(b bVar);
    }

    /* compiled from: FloatDialogBuildEllipseSet */
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

    public a(Context context) {
        super(context, R.style.Dialog);
    }

    public void up() {
        TextView errText = (TextView) findViewById(R.id.floatDlgbuildError);
        int x = ((EditTextWithDelete) findViewById(R.id.floatDlgbuildEditRectLineX)).lL(0);
        if (x == 0) {
            errText.setText("* 请输入椭圆柱的长轴x");
            return;
        }
        int z = ((EditTextWithDelete) findViewById(R.id.floatDlgbuildEditRectLineZ)).lL(0);
        if (z == 0) {
            errText.setText("* 请输入椭圆柱的短轴z");
            return;
        }
        int y = ((EditTextWithDelete) findViewById(R.id.floatDlgbuildEditRectLineY)).lL(0);
        if (y == 0) {
            errText.setText("* 请输入椭圆柱的高度y");
            return;
        }
        this.aaX.x = x;
        this.aaX.y = y;
        this.aaX.z = z;
        this.aaX.type = ((Integer) findViewById(R.id.floatDlgbuildRectType).getTag()).intValue();
        this.abu.a(this.aaX);
        super.dismiss();
    }

    public void a(b info, a cb) {
        this.abu = cb;
        this.aaX = new b(info);
        super.show();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.float_dialog_build_rect);
        findViewById(R.id.floatDlgbuildOK).setOnClickListener(this.abv);
        findViewById(R.id.floatDlgbuildCancel).setOnClickListener(this.abv);
        ((TextView) findViewById(R.id.tvBuildX)).setText("输入长轴x:");
        ((TextView) findViewById(R.id.tvBuildZ)).setText("输入短轴z:");
        ((TextView) findViewById(R.id.tvBuildY)).setText("输入高度y:");
        TextView btn = (TextView) findViewById(R.id.floatDlgbuildRectTypeHollow);
        btn.setOnClickListener(this.abv);
        btn.setTag(Integer.valueOf(0));
        if (this.aaX.type == 0) {
            this.abv.onClick(btn);
        }
        btn = (TextView) findViewById(R.id.floatDlgbuildRectTypeSolid);
        btn.setOnClickListener(this.abv);
        btn.setTag(Integer.valueOf(1));
        if (this.aaX.type == 1) {
            this.abv.onClick(btn);
        }
        btn = (TextView) findViewById(R.id.floatDlgbuildRectTypeLine);
        btn.setOnClickListener(this.abv);
        btn.setTag(Integer.valueOf(2));
        if (this.aaX.type == 2) {
            this.abv.onClick(btn);
        }
        ((EditText) findViewById(R.id.floatDlgbuildEditRectLineX)).setText("" + this.aaX.x);
        ((EditText) findViewById(R.id.floatDlgbuildEditRectLineY)).setText("" + this.aaX.y);
        ((EditText) findViewById(R.id.floatDlgbuildEditRectLineZ)).setText("" + this.aaX.z);
    }
}
