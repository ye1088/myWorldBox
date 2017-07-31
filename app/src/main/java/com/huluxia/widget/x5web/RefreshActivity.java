package com.huluxia.widget.x5web;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;

public class RefreshActivity extends Activity {
    TextView aRh;
    X5WebView bHi;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.refresh_layout);
        this.bHi = (X5WebView) findViewById(g.web_filechooser);
        this.aRh = (TextView) findViewById(g.refreshText);
        this.bHi.setTitle(this.aRh);
        this.bHi.loadUrl("http://app.html5.qq.com/navi/index");
        QD();
    }

    private void QD() {
        ((Button) findViewById(g.bt_filechooser_flush)).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ RefreshActivity bHj;

            {
                this.bHj = this$0;
            }

            public void onClick(View v) {
                this.bHj.bHi.reload();
                this.bHj.bHi.setDayOrNight(false);
            }
        });
        ((Button) findViewById(g.bt_filechooser_back)).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ RefreshActivity bHj;

            {
                this.bHj = this$0;
            }

            public void onClick(View v) {
                this.bHj.bHi.goBack();
            }
        });
        ((Button) findViewById(g.bt_filechooser_home)).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ RefreshActivity bHj;

            {
                this.bHj = this$0;
            }

            public void onClick(View v) {
                this.bHj.bHi.loadUrl("http://app.html5.qq.com/navi/index");
            }
        });
    }
}
