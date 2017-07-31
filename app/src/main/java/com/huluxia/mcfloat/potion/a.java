package com.huluxia.mcfloat.potion;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.huluxia.framework.R;

/* compiled from: PotionDlg */
public class a extends Dialog {
    private static String acr = "";
    private static String acs = "";
    private OnClickListener abv = new OnClickListener(this) {
        final /* synthetic */ a acu;

        {
            this.acu = this$0;
        }

        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.enchantReadMeOK:
                    this.acu.dismiss();
                    return;
                default:
                    return;
            }
        }
    };
    private TextView act;
    private Context mCtx;

    public a(Context context, String inputTitile, String inputContext) {
        super(context, R.style.Dialog);
        this.mCtx = context;
        acr = inputTitile;
        acs = inputContext;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.float_dialog_enchant_read);
        ((TextView) findViewById(R.id.enchantTitle)).setText(acr);
        ((TextView) findViewById(R.id.enchantReadMe)).setText(acs);
        findViewById(R.id.enchantReadMeOK).setOnClickListener(this.abv);
    }

    public void showDialog() {
        super.show();
    }
}
