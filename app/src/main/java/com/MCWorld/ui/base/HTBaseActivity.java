package com.MCWorld.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.MCWorld.HTApplication;
import com.MCWorld.bbs.b.c;
import com.MCWorld.bbs.b.d;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.http.base.f;
import com.MCWorld.t;
import com.simple.colorful.a.a;

public abstract class HTBaseActivity extends HTBaseThemeActivity implements f {
    protected ImageButton aIQ = null;
    protected Button aIR = null;
    protected Button aIS = null;
    protected Button aIT = null;
    protected RelativeLayout aIU = null;
    protected FrameLayout aIV = null;
    protected RelativeLayout aIW = null;
    protected Button aIX = null;
    protected EditText aIY = null;
    protected ImageView aIZ = null;
    private boolean aIr = false;
    protected ImageView aJa = null;
    protected boolean aJb = false;
    private View aJc;
    private TextView aJd;
    protected View aJe;
    private LayoutInflater mInflater = null;
    private ViewGroup mRootView = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mInflater = LayoutInflater.from(this);
        FI();
        this.aIg = (TextView) findViewById(g.tv_msg);
        Fr();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }

    public void setContentView(int layoutResID) {
        setContentView(this.mInflater.inflate(layoutResID, null));
    }

    public void setContentView(View view) {
        if (this.mRootView.getChildCount() > 0) {
            this.mRootView.removeAllViews();
        }
        this.mRootView.addView(view);
    }

    public void FI() {
        super.setContentView(i.activity_framework);
        this.aIV = (FrameLayout) findViewById(g.childPage);
        this.aIU = (RelativeLayout) findViewById(g.header);
        this.mRootView = (ViewGroup) findViewById(g.childPage);
        this.aJe = findViewById(g.split_top);
        this.aJe.setVisibility(8);
        this.aIW = (RelativeLayout) findViewById(g.rl_header_back);
        this.aIR = (Button) findViewById(g.sys_header_back);
        this.aIS = (Button) findViewById(g.sys_header_left);
        this.aIT = (Button) findViewById(g.sys_header_right);
        this.aIQ = (ImageButton) findViewById(g.sys_header_right_img);
        this.aIR.setVisibility(0);
        this.aIR.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ HTBaseActivity aJf;

            {
                this.aJf = this$0;
            }

            public void onClick(View v) {
                this.aJf.finish();
                if (!this.aJf.aJb) {
                }
            }
        });
        this.aIs = findViewById(g.fl_msg);
        this.aIs.setVisibility(0);
        ImageButton imgMsg = (ImageButton) this.aIs.findViewById(g.img_msg);
        imgMsg.setVisibility(0);
        imgMsg.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ HTBaseActivity aJf;

            {
                this.aJf = this$0;
            }

            public void onClick(View v) {
                t.a(this.aJf, HTApplication.bM());
            }
        });
        this.aIX = (Button) findViewById(g.search_back);
        this.aIY = (EditText) findViewById(g.edtSearch);
        this.aIZ = (ImageView) findViewById(g.imgClear);
        this.aJa = (ImageView) findViewById(g.imgSearch);
        this.aIX.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ HTBaseActivity aJf;

            {
                this.aJf = this$0;
            }

            public void onClick(View v) {
                this.aJf.finish();
            }
        });
        this.aJc = findViewById(g.loading);
        this.aJc.setVisibility(8);
        this.aJd = (TextView) findViewById(g.progressTxt);
    }

    public void FJ() {
        this.aIU.setBackgroundColor(getResources().getColor(d.transparent));
        LayoutParams params = new LayoutParams(-1, -1);
        params.setMargins(0, 0, 0, 0);
        this.aIV.setLayoutParams(params);
        this.aIR.setBackgroundResource(0);
        this.aIS.setBackgroundResource(0);
        this.aIT.setBackgroundResource(0);
        this.aIQ.setBackgroundResource(0);
    }

    public void goBack() {
        cs(false);
        finish();
    }

    protected void ej(String text) {
        if (text == null) {
            this.aIR.setText("");
        } else {
            this.aIR.setText(text);
        }
    }

    public void a(com.MCWorld.http.base.d response) {
        cs(true);
    }

    public void b(com.MCWorld.http.base.d response) {
        cs(false);
        t.n(this, "访问错误");
    }

    public void c(com.MCWorld.http.base.d response) {
        cs(false);
    }

    protected void onDestroy() {
        super.onDestroy();
        cs(false);
    }

    public void ct(boolean flag) {
        if (flag) {
            findViewById(g.search_header).setVisibility(0);
            findViewById(g.header).setVisibility(4);
            return;
        }
        findViewById(g.search_header).setVisibility(4);
        findViewById(g.header).setVisibility(0);
    }

    protected void cs(boolean show) {
        if (this.aJc != null) {
            if (show) {
                this.aJc.setVisibility(0);
            } else {
                this.aJc.setVisibility(8);
            }
        }
    }

    protected void ek(String txt) {
        this.aJd.setText(txt);
    }

    protected void a(a builder) {
        super.a(builder);
        builder.i(this.aIU, c.backgroundTitleBar).aY(g.split_top, c.splitColorDim).a(this.aIR, c.backText).a(this.aIR, c.back, 1).j(this.aIR, c.backgroundTitleBarButton).j(this.aIT, c.backgroundTitleBarButton).a(this.aIT, c.backText).bc(g.img_msg, c.drawableTitleMsg).aZ(g.img_msg, c.backgroundTitleBarButton).aZ(g.sys_header_flright_img, c.backgroundTitleBarButton).aZ(g.header_flright_second_img, c.backgroundTitleBarButton).aZ(g.sys_header_right_img, c.backgroundTitleBarButton).aZ(g.sys_header_right_second_img, c.backgroundTitleBarButton).aZ(g.sys_header_right_third_img, c.backgroundTitleBarButton);
    }
}
