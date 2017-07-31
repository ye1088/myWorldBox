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

/* compiled from: FloatDialogBuildLineset */
public class b extends Dialog {
    public static final int abA = 3;
    public static final int abB = 4;
    public static final int abC = 5;
    public static final int abD = 6;
    public static final int abx = 0;
    public static final int aby = 1;
    public static final int abz = 2;
    private a abE = null;
    private b abd = null;
    private OnClickListener abv = new OnClickListener(this) {
        final /* synthetic */ b abF;

        {
            this.abF = this$0;
        }

        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.floatDlgbuildLineAuto:
                    this.abF.fD(0);
                    return;
                case R.id.floatDlgbuildLineAddX:
                    this.abF.fD(1);
                    return;
                case R.id.floatDlgbuildLineAddY:
                    this.abF.fD(3);
                    return;
                case R.id.floatDlgbuildLineAddZ:
                    this.abF.fD(5);
                    return;
                case R.id.floatDlgbuildLineSubX:
                    this.abF.fD(2);
                    return;
                case R.id.floatDlgbuildLineSubY:
                    this.abF.fD(4);
                    return;
                case R.id.floatDlgbuildLineSubZ:
                    this.abF.fD(6);
                    return;
                case R.id.floatDlgbuildCancel:
                    this.abF.dismiss();
                    return;
                case R.id.floatDlgbuildOK:
                    this.abF.up();
                    return;
                default:
                    return;
            }
        }
    };

    /* compiled from: FloatDialogBuildLineset */
    public interface a {
        void a(b bVar);
    }

    /* compiled from: FloatDialogBuildLineset */
    public static class b {
        public int Ov;
        public int abG;
        public int orientation;

        public b() {
            this.orientation = 0;
            this.abG = 0;
            this.Ov = 0;
        }

        public b(b info) {
            this.Ov = info.Ov;
            this.abG = info.abG;
            this.orientation = info.orientation;
        }

        public void b(b info) {
            this.Ov = info.Ov;
            this.abG = info.abG;
            this.orientation = info.orientation;
        }

        public void n(int c, int s, int o) {
            this.Ov = c;
            this.abG = s;
            this.orientation = o;
        }
    }

    public b(Context context) {
        super(context, R.style.Dialog);
    }

    public void up() {
        TextView errText = (TextView) findViewById(R.id.floatDlgbuildError);
        int cnt = ((EditTextWithDelete) findViewById(R.id.floatDlgbuildEditCount)).lL(0);
        if (cnt == 0) {
            errText.setText("* 请输入自动处理数量");
            return;
        }
        this.abd.Ov = cnt;
        EditTextWithDelete edit = (EditTextWithDelete) findViewById(R.id.floatDlgbuildEditStep);
        this.abd.abG = edit.lL(0);
        this.abE.a(this.abd);
        super.dismiss();
    }

    public void a(b info, a cb) {
        this.abE = cb;
        this.abd = new b(info);
        super.show();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.float_dialog_build_line);
        ((EditText) findViewById(R.id.floatDlgbuildEditStep)).setText("" + this.abd.abG);
        ((EditText) findViewById(R.id.floatDlgbuildEditCount)).setText("" + this.abd.Ov);
        fD(this.abd.orientation);
        findViewById(R.id.floatDlgbuildLineAuto).setOnClickListener(this.abv);
        findViewById(R.id.floatDlgbuildLineAddX).setOnClickListener(this.abv);
        findViewById(R.id.floatDlgbuildLineAddY).setOnClickListener(this.abv);
        findViewById(R.id.floatDlgbuildLineAddZ).setOnClickListener(this.abv);
        findViewById(R.id.floatDlgbuildLineSubX).setOnClickListener(this.abv);
        findViewById(R.id.floatDlgbuildLineSubY).setOnClickListener(this.abv);
        findViewById(R.id.floatDlgbuildLineSubZ).setOnClickListener(this.abv);
        findViewById(R.id.floatDlgbuildOK).setOnClickListener(this.abv);
        findViewById(R.id.floatDlgbuildCancel).setOnClickListener(this.abv);
    }

    private void fD(int orientation) {
        this.abd.orientation = orientation;
        String text = "";
        switch (orientation) {
            case 0:
                text = "自动";
                break;
            case 1:
                text = "X 增加";
                break;
            case 2:
                text = "X 减小";
                break;
            case 3:
                text = "Y 增加";
                break;
            case 4:
                text = "Y 减小";
                break;
            case 5:
                text = "Z 增加";
                break;
            case 6:
                text = "Z 减小";
                break;
        }
        ((TextView) findViewById(R.id.floatDlgbuildLineResult)).setText(text);
    }
}
