package com.huluxia.ui.profile;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.huluxia.bbs.b.c;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.data.profile.GiftInfo;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.module.h;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseActivity;
import com.huluxia.utils.at;
import hlx.data.localstore.a;
import org.json.JSONException;

public class ProfileExchangeSubmitActivity extends HTBaseActivity implements OnClickListener {
    public static final String bgp = "EXTRA_GIFT_INFO";
    public static final String bgq = "EXTRA_USER_CREDITS";
    private GiftInfo bgr;
    private long bgs;
    private EditText bgt;
    private EditText bgu;
    private EditText bgv;
    private EditText bgw;
    private EditText bgx;
    private EditText bgy;
    private TextView bgz;
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ ProfileExchangeSubmitActivity bgA;

        {
            this.bgA = this$0;
        }

        @MessageHandler(message = 552)
        public void onRecvSubmitResult(boolean succ, String errorMsg) {
            this.bgA.cs(false);
            if (succ) {
                this.bgA.Jq();
            } else {
                t.n(this.bgA, errorMsg);
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.activity_profile_exchange_submit);
        this.bgr = (GiftInfo) getIntent().getSerializableExtra(bgp);
        this.bgs = getIntent().getLongExtra(bgq, 0);
        this.aIs.setVisibility(8);
        switch (this.bgr.getCashType()) {
            case 1:
                Jp();
                break;
            case 2:
                Js();
                break;
            case 3:
                Jt();
                break;
            case 4:
                Jr();
                break;
        }
        ((TextView) findViewById(g.tv_gift_name)).setText(this.bgr.getName());
        ((TextView) findViewById(g.tv_hulu_comsume)).setText(String.valueOf(this.bgr.getCredits()));
        this.bgz = (TextView) findViewById(g.tv_submit);
        this.bgz.setEnabled(this.bgs >= this.bgr.getCredits());
        this.bgz.setOnClickListener(this);
        EventNotifyCenter.add(h.class, this.mCallback);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (MotionEventCompat.getActionMasked(ev) == 0) {
            View view = getCurrentFocus();
            if (a(view, ev)) {
                at.hideInputMethod(view);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean a(View v, MotionEvent ev) {
        if (v == null || !(v instanceof EditText)) {
            return false;
        }
        int[] l = new int[]{0, 0};
        v.getLocationInWindow(l);
        int left = l[0];
        int top = l[1];
        int bottom = top + v.getHeight();
        int right = left + v.getWidth();
        if (ev.getX() <= ((float) left) || ev.getX() >= ((float) right) || ev.getY() <= ((float) top) || ev.getY() >= ((float) bottom)) {
            return true;
        }
        return false;
    }

    private void Jp() {
        this.bgt = (EditText) findViewById(g.et_qq_num);
        this.bgt.setVisibility(0);
        ej("兑换Q币");
    }

    private void Jq() {
        Builder builder = new Builder(this);
        builder.setInverseBackgroundForced(true);
        builder.setView(LayoutInflater.from(this).inflate(i.layout_exchange_submit, null));
        builder.setCancelable(false);
        builder.setPositiveButton(a.bKC_bt_ok, new DialogInterface.OnClickListener(this) {
            final /* synthetic */ ProfileExchangeSubmitActivity bgA;

            {
                this.bgA = this$0;
            }

            public void onClick(DialogInterface dialog, int which) {
                this.bgA.finish();
            }
        });
        builder.show();
    }

    private void Jr() {
        this.bgu = (EditText) findViewById(g.et_tel_num);
        this.bgu.setVisibility(0);
        this.bgv = (EditText) findViewById(g.et_name);
        this.bgv.setVisibility(0);
        this.bgw = (EditText) findViewById(g.et_address);
        this.bgw.setVisibility(0);
        ej("兑换实物");
    }

    private void Js() {
        this.bgu = (EditText) findViewById(g.et_tel_num);
        this.bgu.setVisibility(0);
        ej("兑换话费");
    }

    private void Jt() {
        this.bgx = (EditText) findViewById(g.et_alipay_account);
        this.bgx.setVisibility(0);
        this.bgy = (EditText) findViewById(g.et_alipay_nick);
        this.bgy.setVisibility(0);
        ej("兑换支付宝");
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void onClick(View v) {
        if (v.getId() == g.tv_submit) {
            Gm();
        }
    }

    private void Gm() {
        switch (this.bgr.getCashType()) {
            case 1:
                Jv();
                return;
            case 2:
                Jx();
                return;
            case 3:
                Ju();
                return;
            case 4:
                Jw();
                return;
            default:
                return;
        }
    }

    private void Ju() {
        String alipayAccount = this.bgx.getText().toString().trim();
        String alipayNick = this.bgy.getText().toString().trim();
        if (TextUtils.isEmpty(alipayAccount)) {
            t.n(this, "支付宝帐号不能为空");
        } else if (TextUtils.isEmpty(alipayNick)) {
            t.n(this, "支付宝昵称不能为空");
        } else {
            try {
                com.huluxia.module.profile.g.Eb().h(this.bgr.getGUID(), com.huluxia.data.profile.a.a(this.bgr, alipayAccount, alipayNick));
                cs(true);
            } catch (JSONException e) {
            }
        }
    }

    private void Jv() {
        String qq = this.bgt.getText().toString().trim();
        if (TextUtils.isEmpty(qq)) {
            t.n(this, "qq号不能为空");
            return;
        }
        try {
            com.huluxia.module.profile.g.Eb().h(this.bgr.getGUID(), com.huluxia.data.profile.a.a(this.bgr, qq));
            cs(true);
        } catch (JSONException e) {
        }
    }

    private void Jw() {
        String name = this.bgv.getText().toString().trim();
        String tel = this.bgu.getText().toString().trim();
        String addr = this.bgw.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            t.n(this, "收货人不能为空");
        } else if (TextUtils.isEmpty(tel)) {
            t.n(this, "手机号不能为空");
        } else if (TextUtils.isEmpty(addr)) {
            t.n(this, "收货地址不能为空");
        } else if (this.bgw.getLineCount() > 5) {
            t.n(this, "收货地址不能超过5行");
        } else {
            try {
                com.huluxia.module.profile.g.Eb().h(this.bgr.getGUID(), com.huluxia.data.profile.a.a(this.bgr, name, tel, addr));
                cs(true);
            } catch (JSONException e) {
            }
        }
    }

    private void Jx() {
        String tel = this.bgu.getText().toString().trim();
        if (TextUtils.isEmpty(tel)) {
            t.n(this, "手机号不能为空");
            return;
        }
        try {
            com.huluxia.module.profile.g.Eb().h(this.bgr.getGUID(), com.huluxia.data.profile.a.b(this.bgr, tel));
            cs(true);
        } catch (JSONException e) {
        }
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        builder.aY(16908290, c.backgroundDefault).a(this.bgt, 16842806).a(this.bgu, 16842806).a(this.bgv, 16842806).a(this.bgw, 16842806).a(this.bgx, 16842806).a(this.bgy, 16842806).b(this.bgt, 16842906).b(this.bgu, 16842906).b(this.bgv, 16842906).b(this.bgw, 16842906).b(this.bgx, 16842906).b(this.bgy, 16842906).j(this.bgt, c.backgroundEditTextExchanged).j(this.bgu, c.backgroundEditTextExchanged).j(this.bgv, c.backgroundEditTextExchanged).j(this.bgw, c.backgroundEditTextExchanged).j(this.bgx, c.backgroundEditTextExchanged).j(this.bgy, c.backgroundEditTextExchanged).aY(g.split1, c.splitColor).aY(g.split2, c.splitColor).ba(g.tv_gift_text, 16842808).ba(g.tv_gift_name, 16842808).ba(g.tv_gift_mall_note, 16842808).j(this.bgz, c.backgroundButtonExchanged).a(this.bgz, 16842809);
    }
}
