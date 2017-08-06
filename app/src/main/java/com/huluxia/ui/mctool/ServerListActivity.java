package com.huluxia.ui.mctool;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.r;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseActivity;
import hlx.data.tongji.a;

public class ServerListActivity extends HTBaseActivity {
    private ServerListRecommendLayout[] bcl = new ServerListRecommendLayout[]{null, null};
    private int bcm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ej("服务器联机");
        this.aIT.setText("+申请开服");
        this.aIT.setVisibility(0);
        this.aIs.setVisibility(8);
        this.aIT.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ServerListActivity bcn;

            {
                this.bcn = this$0;
            }

            public void onClick(View arg0) {
                r.ck().K_umengEvent(a.bMR);
                t.a(this.bcn, 5243116, false);
            }
        });
        this.bcl[0] = new ServerListRecommendLayout((Context) this, 0);
        this.bcl[1] = new ServerListRecommendLayout((Context) this, 1);
        IJ();
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.bcm != -1) {
            this.bcl[this.bcm].ce(false);
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void IJ() {
        this.bcm = -1;
        kU(0);
    }

    public void kU(int dst) {
        if (this.bcm >= 0) {
            this.bcl[this.bcm].setVisibility(8);
            this.bcl[this.bcm].ce(false);
        }
        this.bcm = dst;
        this.bcl[this.bcm].ce(true);
        this.bcl[this.bcm].setVisibility(0);
        setContentView(this.bcl[this.bcm]);
        this.bcl[this.bcm].IL();
        if (this.bcm == 0) {
            r.ck().K_umengEvent(a.bMP);
        } else if (this.bcm == 1) {
            r.ck().K_umengEvent(a.bMQ);
        }
    }
}
