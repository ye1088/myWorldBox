package com.huluxia.ui.base;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.huluxia.HTApplication;
import com.huluxia.bbs.b.g;
import com.huluxia.data.j;
import com.huluxia.data.message.MsgCounts;
import com.huluxia.service.i;
import com.huluxia.t;

public class BaseActivity extends HTActivity {
    private static int aIm = 0;
    protected TextView aIg;
    private boolean aIl = false;
    private BroadcastReceiver aIn;
    private BroadcastReceiver aIo;
    private BroadcastReceiver aIp;
    private BroadcastReceiver aIq;
    private boolean aIr;
    protected View aIs;
    protected OnClickListener aIt = new OnClickListener(this) {
        final /* synthetic */ BaseActivity aIu;

        {
            this.aIu = this$0;
        }

        public void onClick(View v) {
            t.a(this.aIu, HTApplication.bM());
        }
    };

    private class a extends BroadcastReceiver {
        final /* synthetic */ BaseActivity aIu;

        private a(BaseActivity baseActivity) {
            this.aIu = baseActivity;
        }

        public void onReceive(Context context, Intent intent) {
            this.aIu.Fs();
            if (this.aIu.aIr && this.aIu.aIs != null) {
                this.aIu.aIs.setVisibility(4);
            }
        }
    }

    private class b extends BroadcastReceiver {
        final /* synthetic */ BaseActivity aIu;

        private b(BaseActivity baseActivity) {
            this.aIu = baseActivity;
        }

        public void onReceive(Context context, Intent intent) {
        }
    }

    private class c extends BroadcastReceiver {
        final /* synthetic */ BaseActivity aIu;

        private c(BaseActivity baseActivity) {
            this.aIu = baseActivity;
        }

        public void onReceive(Context context, Intent intent) {
            long id = intent.getLongExtra("id", -1);
            String content = intent.getStringExtra("content");
            if (id != -1 && content != null && j.ep().ey() && j.ep().getUserid() == id) {
                j.ep().eq();
                com.huluxia.login.d.pR().logout(this.aIu);
                this.aIu.bP(content);
            }
        }
    }

    private class d extends BroadcastReceiver {
        final /* synthetic */ BaseActivity aIu;

        private d(BaseActivity baseActivity) {
            this.aIu = baseActivity;
        }

        public void onReceive(Context context, Intent intent) {
            this.aIu.Fr();
            if (this.aIu.aIr && this.aIu.aIs != null) {
                this.aIu.aIs.setVisibility(0);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.aIn = new d();
        this.aIo = new a();
        this.aIp = new c();
        this.aIq = new b();
        i.e(this.aIn);
        i.f(this.aIo);
        i.g(this.aIp);
        i.h(this.aIq);
        Fr();
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.aIn != null) {
            i.unregisterReceiver(this.aIn);
            this.aIn = null;
        }
        if (this.aIo != null) {
            i.unregisterReceiver(this.aIo);
            this.aIo = null;
        }
        if (this.aIp != null) {
            i.unregisterReceiver(this.aIp);
            this.aIp = null;
        }
        if (this.aIq != null) {
            i.unregisterReceiver(this.aIq);
            this.aIq = null;
        }
    }

    public void cp(boolean bShowMsgTipTemp) {
        this.aIr = bShowMsgTipTemp;
    }

    private void bP(String szMessage) {
        Builder builder = new Builder(this);
        builder.setInverseBackgroundForced(true);
        View layout = LayoutInflater.from(this).inflate(com.huluxia.bbs.b.i.include_dialog_one, null);
        ((TextView) layout.findViewById(g.tv_msg)).setText(szMessage);
        builder.setCancelable(false);
        final AlertDialog dialog = builder.create();
        dialog.setView(layout, 0, 0, 0, 0);
        if (!isFinishing()) {
            dialog.show();
        }
        TextView tv_confirm = (TextView) layout.findViewById(g.tv_confirm);
        tv_confirm.setText("确认");
        tv_confirm.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ BaseActivity aIu;

            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
    }

    private void Fs() {
        if (this.aIg != null) {
            this.aIg.setVisibility(8);
        }
    }

    protected void Fr() {
        if (this.aIg != null) {
            MsgCounts msgCounts = HTApplication.bM();
            long allCount = msgCounts == null ? 0 : msgCounts.getAll();
            if (allCount > 0) {
                this.aIg.setVisibility(0);
                if (allCount > 99) {
                    this.aIg.setText("99+");
                    return;
                } else {
                    this.aIg.setText(String.valueOf(msgCounts.getAll()));
                    return;
                }
            }
            this.aIg.setVisibility(8);
        }
    }

    protected void onResume() {
        super.onResume();
        this.aIl = true;
        aIm++;
    }

    protected void onPause() {
        super.onPause();
        this.aIl = false;
        aIm--;
    }

    public boolean Fb() {
        return this.aIl;
    }

    public static boolean bU() {
        return aIm > 0;
    }
}
