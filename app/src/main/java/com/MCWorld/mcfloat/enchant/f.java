package com.MCWorld.mcfloat.enchant;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.MCWorld.framework.R;

/* compiled from: EnchantOptionDlg */
public class f extends Dialog {
    private static int acb = 0;
    private static int acc = 0;
    private OnClickListener abv = new OnClickListener(this) {
        final /* synthetic */ f ace;

        {
            this.ace = this$0;
        }

        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.enchantReadMeOK:
                    this.ace.dismiss();
                    return;
                default:
                    return;
            }
        }
    };
    private a aca = null;
    private TextView acd;
    private Context mCtx;

    /* compiled from: EnchantOptionDlg */
    public interface a {
        void rd();
    }

    public f(Context context, int inputChoose) {
        super(context, R.style.Dialog);
        this.mCtx = context;
        acc = inputChoose;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.float_dialog_enchant_read);
        this.acd = (TextView) findViewById(R.id.enchantReadMe);
        switch (acc) {
            case 0:
                this.acd.setText(R.string.enchantContextAttr00);
                break;
            case 1:
                this.acd.setText(R.string.enchantContextAttr01);
                break;
            case 2:
                this.acd.setText(R.string.enchantContextAttr02);
                break;
            case 3:
                this.acd.setText(R.string.enchantContextAttr03);
                break;
            case 4:
                this.acd.setText(R.string.enchantContextAttr04);
                break;
            case 5:
                this.acd.setText(R.string.enchantContextAttr05);
                break;
            case 6:
                this.acd.setText(R.string.enchantContextAttr06);
                break;
            case 7:
                this.acd.setText(R.string.enchantContextAttr07);
                break;
            case 8:
                this.acd.setText(R.string.enchantContextAttr08);
                break;
            case 9:
                this.acd.setText(R.string.enchantContextAttr09);
                break;
            case 10:
                this.acd.setText(R.string.enchantContextAttr10);
                break;
            case 11:
                this.acd.setText(R.string.enchantContextAttr11);
                break;
            case 12:
                this.acd.setText(R.string.enchantContextAttr12);
                break;
            case 13:
                this.acd.setText(R.string.enchantContextAttr13);
                break;
            case 14:
                this.acd.setText(R.string.enchantContextAttr14);
                break;
            case 15:
                this.acd.setText(R.string.enchantContextAttr15);
                break;
            case 16:
                this.acd.setText(R.string.enchantContextAttr16);
                break;
            case 17:
                this.acd.setText(R.string.enchantContextAttr17);
                break;
            case 18:
                this.acd.setText(R.string.enchantContextAttr18);
                break;
            case 19:
                this.acd.setText(R.string.enchantContextAttr19);
                break;
            case 20:
                this.acd.setText(R.string.enchantContextAttr20);
                break;
            case 21:
                this.acd.setText(R.string.enchantContextAttr21);
                break;
            case 22:
                this.acd.setText(R.string.enchantContextAttr22);
                break;
            case 23:
                this.acd.setText(R.string.enchantContextAttr23);
                break;
            case 24:
                this.acd.setText(R.string.enchantContextAttr24);
                break;
        }
        findViewById(R.id.enchantReadMeOK).setOnClickListener(this.abv);
    }

    public void showDialog() {
        super.show();
    }

    public static int uz() {
        return acb;
    }

    public static void fO(int chooseOption) {
        acb = chooseOption;
    }
}
