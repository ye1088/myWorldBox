package com.huluxia.ui.discovery;

import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import com.huluxia.HTApplication;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.m;
import com.huluxia.data.e;
import com.huluxia.data.n;
import com.huluxia.framework.base.utils.UtilsVersion;
import com.huluxia.http.base.d;
import com.huluxia.http.base.f;
import com.huluxia.http.other.j;
import com.huluxia.r;
import com.huluxia.service.i;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseActivity;
import com.huluxia.utils.UtilsDownloadFile;
import com.huluxia.utils.UtilsDownloadFile$a;
import com.huluxia.utils.UtilsFile;
import com.huluxia.utils.ah;
import com.huluxia.version.VersionDialog;
import com.huluxia.widget.Constants;
import com.huluxia.widget.dialog.p;

public class SettingsActivity extends HTBaseActivity implements OnCheckedChangeListener {
    private CheckBox aRJ;
    private TextView aRK;
    private SettingsActivity aRL;
    private j aRM;
    OnClickListener aRN = new OnClickListener(this) {
        final /* synthetic */ SettingsActivity aRP;

        {
            this.aRP = this$0;
        }

        public void onClick(View v) {
            int id = v.getId();
            if (g.rlv_netmod == id) {
                new p(this.aRP.aRL, ah.KZ().Lm(), com.huluxia.utils.ah.b.bme, this.aRP.aRO).show();
            } else if (g.tv_feedback == id) {
                t.aj(this.aRP.aRL);
            } else if (g.tv_logout == id) {
                ((NotificationManager) this.aRP.getSystemService("notification")).cancel(Integer.MAX_VALUE);
                com.huluxia.module.account.a.DU().DW();
                com.huluxia.data.j.ep().clear();
                i.EG();
                t.an(this.aRP.aRL);
                this.aRP.findViewById(g.tv_logout).setVisibility(8);
                this.aRP.aRL.finish();
            } else if (g.tv_version == id) {
                this.aRP.Hp();
            } else if (id == g.tv_notification) {
                t.av(this.aRP.aRL);
            } else if (id == g.tv_rookie_guide) {
                t.aw(this.aRP.aRL);
                r.ck().K_umengEvent(hlx.data.tongji.a.bLM);
            } else if (id == g.tv_app_comment) {
                ah.KZ().LE();
                com.huluxia.widget.dialog.a aVar = new com.huluxia.widget.dialog.a(this.aRP.aRL, new a(), null);
            } else if (id == g.tv_present_calabash) {
                t.f(this.aRP.aRL, Constants.brR, "致葫芦丝");
            }
        }
    };
    com.huluxia.widget.dialog.p.a aRO = new com.huluxia.widget.dialog.p.a(this) {
        final /* synthetic */ SettingsActivity aRP;

        {
            this.aRP = this$0;
        }

        public void kE(int val) {
            if (com.huluxia.utils.ah.a.ALL == val) {
                this.aRP.aRK.setText(this.aRP.aRL.getResources().getString(m.netmod_all));
            } else if (com.huluxia.utils.ah.a.bmc == val) {
                this.aRP.aRK.setText(this.aRP.aRL.getResources().getString(m.netmod_onlywifi));
            } else if (com.huluxia.utils.ah.a.bmd == val) {
                this.aRP.aRK.setText(this.aRP.aRL.getResources().getString(m.netmod_none));
            }
        }

        public void rb() {
        }
    };

    public static class a implements com.huluxia.widget.dialog.a.a {
        private Context mContext = null;
        private Intent mIntent = null;

        public Intent be(Context context) {
            if (this.mIntent != null) {
                return this.mIntent;
            }
            this.mContext = context;
            this.mIntent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + this.mContext.getPackageName()));
            this.mIntent.addFlags(268435456);
            return this.mIntent;
        }

        public void a(com.huluxia.widget.dialog.a.b info) {
            this.mIntent.setAction("android.intent.action.VIEW");
            this.mIntent.setData(Uri.parse("market://details?id=" + this.mContext.getPackageName()));
            this.mIntent.setPackage(info.bwj);
            this.mContext.startActivity(this.mIntent);
        }

        public boolean et(String pakName) {
            if (pakName.equals("com.baidu.appsearch") || pakName.equals("com.dragon.android.pandaspace") || pakName.equals("com.hiapk.marketpho") || pakName.equals("com.xiaomi.market") || pakName.equals("cn.richinfo.mmassistantphone") || pakName.equals("com.tencent.android.qqdownloader") || pakName.equals("com.netease.apper") || pakName.equals("com.huawei.appmarket") || pakName.equals("com.lenovo.leos.appstore") || pakName.equals("com.wandoujia.phoenix2") || pakName.equals("com.sogou.appmall") || pakName.equals("com.infinit.wostore.ui") || pakName.equals("com.mappn.gfan") || pakName.equals("cn.goapk.market") || pakName.equals("com.mumayi.market.ui") || pakName.equals("com.yingyonghui.market") || pakName.equals("com.eshore.ezone")) {
                return true;
            }
            return false;
        }

        public void rb() {
        }

        public boolean a(PackageInfo info) {
            return false;
        }
    }

    private class b implements UtilsDownloadFile$a {
        final /* synthetic */ SettingsActivity aRP;

        private b(SettingsActivity settingsActivity) {
            this.aRP = settingsActivity;
        }

        public void au(int cur, int max) {
        }

        public void h(int state, String path) {
            if (state == 3) {
                t.U(path);
            }
        }
    }

    private class c implements com.huluxia.widget.dialog.g.a {
        final /* synthetic */ SettingsActivity aRP;

        private c(SettingsActivity settingsActivity) {
            this.aRP = settingsActivity;
        }

        public void ra() {
        }

        public void rb() {
            this.aRP.aRJ.setChecked(true);
        }

        public void rc() {
        }

        public void rd() {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.huluxia.bbs.b.i.activity_settings);
        this.aRL = this;
        this.aIs.setVisibility(8);
        ej("系统设置");
        findViewById(g.tv_notification).setOnClickListener(this.aRN);
        this.aRJ = (CheckBox) findViewById(g.browser);
        e msgRemind = HTApplication.bI();
        this.aRJ.setChecked(msgRemind.eh());
        this.aRJ.setOnCheckedChangeListener(this);
        TextView tv_version = (TextView) findViewById(g.tv_version);
        tv_version.setText("检测新版本（本机" + UtilsVersion.getVersionString(this) + "）");
        tv_version.setOnClickListener(this.aRN);
        ((CheckBox) findViewById(g.chat_msg_vibrate)).setChecked(msgRemind.eg());
        ((CheckBox) findViewById(g.chat_msg_vibrate)).setOnCheckedChangeListener(this);
        findViewById(g.rlv_netmod).setOnClickListener(this.aRN);
        this.aRK = (TextView) findViewById(g.tv_topicpic_op);
        Ho();
        findViewById(g.tv_rookie_guide).setOnClickListener(this.aRN);
        findViewById(g.tv_feedback).setOnClickListener(this.aRN);
        findViewById(g.tv_app_comment).setOnClickListener(this.aRN);
        findViewById(g.tv_present_calabash).setOnClickListener(this.aRN);
        findViewById(g.tv_logout).setOnClickListener(this.aRN);
        if (com.huluxia.data.j.ep().ey()) {
            findViewById(g.tv_logout).setVisibility(0);
        }
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        e msgRemind = HTApplication.bI();
        int id = buttonView.getId();
        if (id == g.browser) {
            a(msgRemind, isChecked);
        } else if (id == g.chat_msg_vibrate) {
            msgRemind.y(isChecked);
            ah.KZ().a(msgRemind);
        }
    }

    private void a(e msgRemind, boolean isChecked) {
        if (isChecked) {
            com.huluxia.widget.dialog.g dia = new com.huluxia.widget.dialog.g(this, new c());
            dia.az("重要提示", "浏览器下载对大型游戏和PSP游戏无效");
            dia.u(null, null, "确定更改");
            dia.showDialog();
        }
        msgRemind.z(isChecked);
        ah.KZ().a(msgRemind);
    }

    private void Ho() {
        int val = ah.KZ().Lm();
        if (com.huluxia.utils.ah.a.ALL == val) {
            this.aRK.setText(this.aRL.getResources().getString(m.netmod_all));
        } else if (com.huluxia.utils.ah.a.bmc == val) {
            this.aRK.setText(this.aRL.getResources().getString(m.netmod_onlywifi));
        } else if (com.huluxia.utils.ah.a.bmd == val) {
            this.aRK.setText(this.aRL.getResources().getString(m.netmod_none));
        }
    }

    private void Hp() {
        if (ah.KZ().LZ()) {
            VersionDialog.a(null).show(this.aRL.getSupportFragmentManager(), null);
            return;
        }
        this.aRM = new j();
        this.aRM.a(new f(this) {
            final /* synthetic */ SettingsActivity aRP;

            {
                this.aRP = this$0;
            }

            public void a(d response) {
                this.aRP.aRL.cs(true);
            }

            public void b(d response) {
                this.aRP.aRL.cs(false);
                t.n(this.aRP.aRL, "网络错误");
            }

            public void c(d response) {
                this.aRP.aRL.cs(false);
                if (response.getStatus() == 1) {
                    n verInfo = (n) response.getData();
                    if (verInfo.getVersionCode() <= UtilsVersion.getVersionCode(this.aRP.aRL)) {
                        t.show_toast(this.aRP.aRL, "当前没有可更新的版本。");
                    } else if (verInfo.ez() > 0) {
                        this.aRP.U(verInfo.getMessage(), verInfo.getAddress());
                    }
                }
            }
        });
        this.aRM.eX();
    }

    private void U(String szMessage, final String szUrl) {
        if (!isFinishing()) {
            final Dialog dialog = new Dialog(this.aRL, com.simple.colorful.d.RD());
            View layout = LayoutInflater.from(this).inflate(com.huluxia.bbs.b.i.include_dialog_three, null);
            ((TextView) layout.findViewById(g.tv_title)).setText("版本更新");
            ((TextView) layout.findViewById(g.tv_msg)).setText(szMessage);
            dialog.setCancelable(false);
            dialog.setContentView(layout);
            dialog.show();
            layout.findViewById(g.tv_cancel).setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ SettingsActivity aRP;

                public void onClick(View arg0) {
                    dialog.dismiss();
                }
            });
            layout.findViewById(g.tv_other).setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ SettingsActivity aRP;

                public void onClick(View arg0) {
                    dialog.dismiss();
                    t.q(this.aRP.aRL, szUrl);
                }
            });
            layout.findViewById(g.tv_confirm).setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ SettingsActivity aRP;

                public void onClick(View arg0) {
                    dialog.dismiss();
                    new UtilsDownloadFile(this.aRP.aRL, UtilsFile.get_mctool_path() + "mctool.apk", true, new b()).execute(new String[]{szUrl});
                }
            });
        }
    }
}
