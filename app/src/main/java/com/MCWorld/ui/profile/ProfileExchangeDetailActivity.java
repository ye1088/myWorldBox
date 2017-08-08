package com.MCWorld.ui.profile;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import com.MCWorld.bbs.b.c;
import com.MCWorld.bbs.b.d;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.data.profile.ExchangeRecord;
import com.MCWorld.data.profile.a;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.t;
import com.MCWorld.ui.base.HTBaseActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class ProfileExchangeDetailActivity extends HTBaseActivity {
    public static final String bgh = "EXTRA_RECORD";
    private ExchangeRecord bgi;
    private TextView bgj;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.activity_profile_exchange_detail);
        EP();
        this.bgi = (ExchangeRecord) getIntent().getSerializableExtra(bgh);
        initView();
    }

    @SuppressLint({"SimpleDateFormat"})
    private void initView() {
        t.a((PaintView) findViewById(g.img_gift), this.bgi.icon, (float) t.dipToPx(this, 3));
        TextView statusView = (TextView) findViewById(g.tv_handle_status);
        statusView.setText(this.bgi.statusDesc);
        TextView huluMsgView = (TextView) findViewById(g.tv_hulu_msg);
        int color;
        if (this.bgi.statusDesc.equals("提交中")) {
            color = getResources().getColor(d.exchange_green);
            statusView.setTextColor(color);
            huluMsgView.setTextColor(color);
            huluMsgView.setText("兑换正在审核中,请耐心等待审核结果。");
        } else if (this.bgi.statusDesc.equals("兑换成功")) {
            color = getResources().getColor(d.exchange_green_dark);
            statusView.setTextColor(color);
            huluMsgView.setTextColor(color);
            huluMsgView.setText("恭喜您兑换成功，请查收。");
        } else {
            color = getResources().getColor(d.exchange_orange);
            statusView.setTextColor(getResources().getColor(d.exchange_orange));
            huluMsgView.setTextColor(color);
            huluMsgView.setText("对不起，兑换失败。");
        }
        Jo();
        ((TextView) findViewById(g.tv_date)).setText(new SimpleDateFormat("yyyy.MM.dd HH:mm").format(new Date(Long.parseLong(this.bgi.createTime))));
        this.bgj = (TextView) findViewById(g.btn_contact_huluge);
        this.bgj.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ProfileExchangeDetailActivity bgk;

            {
                this.bgk = this$0;
            }

            public void onClick(View v) {
                this.bgk.Jn();
            }
        });
    }

    private void Jn() {
        final Dialog dialog = new Dialog(this, com.simple.colorful.d.RD());
        View layout = LayoutInflater.from(this).inflate(i.include_dialog_one, null);
        layout.findViewById(g.tv_confirm).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ProfileExchangeDetailActivity bgk;

            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ((TextView) layout.findViewById(g.tv_msg)).setText("亲，遇到什么问题了，葫芦哥可以帮你哦。（QQ:215715960");
        dialog.setContentView(layout);
        dialog.show();
    }

    private void Jo() {
        TextView giftNameView = (TextView) findViewById(g.tv_gift_name);
        HashMap<String, String> ext = this.bgi.ext;
        if (ext.containsKey(a.qb)) {
            giftNameView.setText((String) ext.get(a.pZ));
            TextView typeView = (TextView) findViewById(g.tv_msg_type1);
            TextView infoView = (TextView) findViewById(g.tv_msg1);
            TextView typeView2 = (TextView) findViewById(g.tv_msg_type2);
            TextView infoView2 = (TextView) findViewById(g.tv_msg2);
            TextView typeView3 = (TextView) findViewById(g.tv_msg_type3);
            TextView infoView3 = (TextView) findViewById(g.tv_msg3);
            switch (Integer.parseInt((String) ext.get(a.qb))) {
                case 1:
                    typeView.setText("QQ号：");
                    infoView.setText((CharSequence) ext.get("QQ"));
                    break;
                case 2:
                    typeView.setText("手机号：");
                    infoView.setText((CharSequence) ext.get(a.qe));
                    break;
                case 3:
                    typeView.setText("支付宝帐号：");
                    infoView.setText((CharSequence) ext.get(a.qg));
                    typeView2.setVisibility(0);
                    infoView2.setVisibility(0);
                    typeView2.setText("支付宝昵称：");
                    infoView2.setText((CharSequence) ext.get(a.qh));
                    break;
                case 4:
                    typeView.setText("收货人：");
                    infoView.setText((CharSequence) ext.get(a.qd));
                    typeView2.setVisibility(0);
                    infoView2.setVisibility(0);
                    typeView2.setText("联系电话：");
                    infoView2.setText((CharSequence) ext.get(a.qe));
                    typeView3.setVisibility(0);
                    infoView3.setVisibility(0);
                    typeView3.setText("地址：");
                    infoView3.setText((CharSequence) ext.get(a.qf));
                    getWindow().getDecorView().requestLayout();
                    break;
            }
        }
        ((TextView) findViewById(g.tv_hulu)).setText((CharSequence) ext.get(a.qa));
    }

    private void EP() {
        this.aIs.setVisibility(8);
        this.aIR.setVisibility(0);
        ((ImageButton) findViewById(g.sys_header_right_img)).setVisibility(8);
        ej("兑换详情");
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        builder.aY(16908290, c.backgroundDefault).ba(g.tv_gift_name, 16842808).ba(g.exchange_msg_text, 16842808).ba(g.tv_msg_type1, 16842808).ba(g.tv_msg_type2, 16842808).ba(g.tv_msg_type3, 16842808).ba(g.tv_msg1, 16842808).ba(g.tv_msg2, 16842808).ba(g.tv_msg3, 16842808).ba(g.tv_hulu_text, 16842808).ba(g.tv_date_text, 16842808).ba(g.tv_date, 16842808).ba(g.tv_hulu_ge, 16842808).ba(g.tv_hulu_ge, 16842808).aY(g.split1, c.splitColor).aY(g.split2, c.splitColor).j(this.bgj, c.backgroundButtonExchanged).a(this.bgj, 16842809);
    }
}
