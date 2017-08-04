package com.huluxia.ui.loginAndRegister;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.huluxia.HTApplication;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.bbs.b.m;
import com.huluxia.data.LoginErrCode;
import com.huluxia.data.j;
import com.huluxia.data.k;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.base.utils.UtilsMD5;
import com.huluxia.framework.base.utils.UtilsString;
import com.huluxia.framework.base.widget.dialog.DialogManager.OkCancelDialogListener;
import com.huluxia.http.base.d;
import com.huluxia.module.h;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseActivity;
import com.huluxia.utils.ab;
import com.huluxia.utils.ah;
import com.huluxia.widget.dialog.c;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import org.json.JSONObject;

public class LoginActivity extends HTBaseActivity {
    private static final String aXG = "PARA_OPENID";
    private static final String aXI = "PARA_ACCOUNT";
    private static final String aXJ = "PARA_PASSWORD";
    private static final String aYh = "PARA_QQTOKEN";
    private static final String aYi = "PARA_QQINFO";
    private static final String aYj = "PARA_QQLOGIN";
    private static final String aYk = "flag";
    public Tencent aDA;
    private EditText aXS;
    IUiListener aXX = new a(this) {
        final /* synthetic */ LoginActivity aYl;

        {
            this.aYl = this$0;
        }

        protected void d(JSONObject values) {
            String token = null;
            String expires = null;
            String openId = null;
            try {
                token = values.getString("access_token");
                expires = values.getString(Constants.PARAM_EXPIRES_IN);
                openId = values.getString("openid");
            } catch (Exception e) {
                HLog.error("LoginActivity", e.toString(), new Object[0]);
            }
            if (TextUtils.isEmpty(token) || TextUtils.isEmpty(expires) || TextUtils.isEmpty(openId) || this.aYl.aDA == null) {
                t.show_toast(this.aYl.aYf, "QQ验证失败。请重试。");
                return;
            }
            this.aYl.aDA.setAccessToken(token, expires);
            this.aYl.aDA.setOpenId(openId);
            if (this.aYl.aXS.getText().toString() != null) {
                this.aYl.aYb = openId;
                this.aYl.aYc = token;
                if (this.aYl.aYg) {
                    this.aYl.cs(true);
                    HLog.verbose("LoginActivity", "testQQLogin begin", new Object[0]);
                    com.huluxia.module.account.a.DU().Q(openId, token);
                    return;
                }
                this.aYl.cs(true);
                this.aYl.k(openId, token, "LoginActivity");
            }
        }
    };
    private com.huluxia.http.loginAndRegister.a aXZ = new com.huluxia.http.loginAndRegister.a();
    private EditText aYa;
    private String aYb;
    private String aYc;
    private String aYd;
    private int aYe = 0;
    private LoginActivity aYf;
    private boolean aYg = false;
    private String appId = "100580922";
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ LoginActivity aYl;

        {
            this.aYl = this$0;
        }

        @MessageHandler(message = 599)
        public void onRecvQinfo(boolean succ, String qinfo, String context, String openid, String qtoken) {
            this.aYl.cs(false);
            if ("LoginActivity".equals(context)) {
                this.aYl.l(qinfo, openid, qtoken);
            }
        }

        @MessageHandler(message = 613)
        public void onRecvLogin(k info, String msg) {
            HLog.verbose("LoginActivity", "testQQLogin recv info " + info, new Object[0]);
            this.aYl.cs(false);
            if (info == null) {
                t.n(this.aYl.aYf, msg);
            } else if (info.isSucc()) {
                HLog.verbose("LoginActivity", "testQQLogin toast " + info.isSucc(), new Object[0]);
                t.o(this.aYl.aYf, "登录成功");
                this.aYl.cO(true);
            } else if (info.code == LoginErrCode.ERR_OPENID.Value()) {
                if (info.qqinfo != null) {
                    this.aYl.a(info.qqinfo);
                } else {
                    this.aYl.j(info.code, LoginErrCode.ERR_OPENID.Msg());
                }
            } else if (info.code == LoginErrCode.ERR_121.Value()) {
                t.o(this.aYl.aYf, "该QQ未绑定，请先注册");
                t.a(this.aYl, 528, 529, this.aYl.aYb, this.aYl.aYc);
            } else {
                t.n(this.aYl.aYf, ab.n(info.code, info.msg));
            }
        }
    };

    private class a implements IUiListener {
        final /* synthetic */ LoginActivity aYl;

        private a(LoginActivity loginActivity) {
            this.aYl = loginActivity;
        }

        protected void d(JSONObject values) {
        }

        public void onComplete(Object response) {
            this.aYl.aYf.cs(false);
            if (response == null) {
                t.show_toast(this.aYl.aYf, "QQ验证失败，请重试。");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (jsonResponse == null || jsonResponse.length() != 0) {
                d((JSONObject) response);
            } else {
                t.show_toast(this.aYl.aYf, "QQ验证失败，请重试。");
            }
        }

        public void onError(UiError e) {
            HLog.error("LoginActivity", "BaseUiListener onError " + e.errorMessage, new Object[0]);
            this.aYl.aYf.cs(false);
        }

        public void onCancel() {
            this.aYl.aYf.cs(false);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.aYf = this;
        setContentView(i.activity_login);
        ej(getResources().getString(m.login));
        this.aIs.setVisibility(8);
        this.aXS = (EditText) findViewById(g.uin_edit_text);
        this.aYa = (EditText) findViewById(g.blackberry_edit_text);
        if (savedInstanceState != null) {
            this.aYe = savedInstanceState.getInt("flag");
            String account = savedInstanceState.getString(aXI);
            String password = savedInstanceState.getString(aXJ);
            String openid = savedInstanceState.getString(aXG);
            String qqtoken = savedInstanceState.getString(aYh);
            String qqinfo = savedInstanceState.getString(aYi);
            boolean qqlogin = savedInstanceState.getBoolean(aYj);
            this.aXS.setText(account);
            this.aYa.setText(password);
            this.aYb = openid;
            this.aYc = qqtoken;
            this.aYd = qqinfo;
            this.aYg = qqlogin;
        } else {
            this.aYe = getIntent().getIntExtra("flag", 0);
        }
        findViewById(g.tv_login).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ LoginActivity aYl;

            {
                this.aYl = this$0;
            }

            public void onClick(View v) {
                this.aYl.l(null, this.aYl.aYb, this.aYl.aYc);
            }
        });
        findViewById(g.rly_login2).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ LoginActivity aYl;

            {
                this.aYl = this$0;
            }

            public void onClick(View v) {
                this.aYl.Ib();
            }
        });
        findViewById(g.tv_register).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ LoginActivity aYl;

            {
                this.aYl = this$0;
            }

            public void onClick(View v) {
                this.aYl.cs(true);
                t.a(this.aYl, 528, 529, null, null);
            }
        });
        findViewById(g.tv_forgot_password).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ LoginActivity aYl;

            {
                this.aYl = this$0;
            }

            public void onClick(View v) {
                t.ak(this.aYl);
            }
        });
        findViewById(g.btn_qqlogin).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ LoginActivity aYl;

            {
                this.aYl = this$0;
            }

            public void onClick(View v) {
                this.aYl.cN(true);
            }
        });
        this.aXZ.a(this);
        this.aXZ.bb(1);
        EventNotifyCenter.add(h.class, this.mCallback);
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(aXI, this.aXS.getText().toString());
        savedInstanceState.putString(aXJ, this.aYa.getText().toString());
        savedInstanceState.putString(aXG, this.aYb);
        savedInstanceState.putString(aYh, this.aYc);
        savedInstanceState.putString(aYi, this.aYd);
        savedInstanceState.putBoolean(aYj, this.aYg);
        savedInstanceState.putInt("flag", this.aYe);
    }

    public void onResume() {
        super.onResume();
        CharSequence etEmail = this.aXS.getText().toString();
        CharSequence etPassword = this.aYa.getText().toString();
        if (!UtilsFunction.empty(etEmail) && !UtilsFunction.empty(etPassword)) {
            this.aYa.requestFocus();
            this.aYa.setSelection(etPassword.length());
        } else if (UtilsFunction.empty(etEmail)) {
            CharSequence email = ah.KZ().pZ();
            if (!UtilsFunction.empty(email)) {
                this.aXS.setText(email);
                this.aYa.requestFocus();
            }
        } else {
            this.aYa.requestFocus();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
    }

    private boolean l(String qqInfo, String openid, String qtoken) {
        String email = this.aXS.getText().toString();
        String password = this.aYa.getText().toString();
        if (!UtilsString.validEmail(email.trim()) && !UtilsString.validPhone(email.trim())) {
            t.n(this, "邮箱不合法");
            return false;
        } else if (password.length() < 1) {
            t.n(this, "密码不能为空");
            return false;
        } else {
            com.huluxia.module.account.a.DU().a(email, UtilsMD5.getMD5String(password), openid, qtoken, qqInfo);
            return true;
        }
    }

    private void Ib() {
    }

    public void a(d response) {
        ek("正在登录");
        cs(true);
    }

    public void b(d response) {
        cs(false);
        t.n(this, "登录失败\n网络错误");
    }

    public void c(d response) {
        cs(false);
        if (response.getStatus() != 1) {
            t.n(this, ab.n(response.fg(), response.fh()));
        } else if (response.fe() == 1) {
            com.huluxia.data.h info = (com.huluxia.data.h) response.getData();
            if (info.getCode() != 200 || (info.en().booleanValue() && info.eo() == null)) {
                t.show_toast(this, "验证失效，请重新登陆");
            } else if (info.en().booleanValue()) {
                t.o(this, "登录成功");
                j.ep().a(info.eo());
                com.huluxia.service.i.EF();
                HTApplication.bR();
                com.huluxia.module.account.a.DU().DY();
                cO(true);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_LOGIN || requestCode == Constants.REQUEST_APPBAR) {
            cs(false);
            Tencent.onActivityResultData(requestCode, resultCode, data, this.aXX);
        } else if (requestCode == 528 && resultCode == 529) {
            cs(false);
            if (data != null && data.getBooleanExtra("ok", false)) {
                cO(true);
            }
        }
    }

    private void cN(boolean boolQQLogin) {
        this.aYg = boolQQLogin;
        if (this.aDA == null) {
            this.aDA = Tencent.createInstance(this.appId, HTApplication.getAppContext());
        }
        if (this.aDA.isSessionValid()) {
            this.aDA.logout(this);
        }
        this.aYf.cs(true);
        this.aDA.login((Activity) this, "all", this.aXX);
    }

    public void a(com.huluxia.data.i qqinfo) {
        String msg = "请用注册时关联的QQ进行验证。";
        c dialogManager = new c(this);
        if (qqinfo != null) {
            dialogManager.a("账号登录保护", msg, "现在验证", "不验证", true, qqinfo.figureurl_qq_1, qqinfo.nickname, new OkCancelDialogListener(this) {
                final /* synthetic */ LoginActivity aYl;

                {
                    this.aYl = this$0;
                }

                public void onCancel() {
                }

                public void onOk() {
                    this.aYl.cN(false);
                }
            });
        } else {
            dialogManager.showOkCancelDialog("账号登录保护", (CharSequence) msg, (CharSequence) "现在验证", (CharSequence) "不验证", true, new OkCancelDialogListener(this) {
                final /* synthetic */ LoginActivity aYl;

                {
                    this.aYl = this$0;
                }

                public void onCancel() {
                }

                public void onOk() {
                    this.aYl.cN(false);
                }
            });
        }
    }

    public void j(int code, String msg) {
        final Dialog dialog = new Dialog(this);
        View layout = LayoutInflater.from(this).inflate(i.include_dialog_two, null);
        ((TextView) layout.findViewById(g.tv_msg)).setText(msg);
        dialog.setCancelable(false);
        dialog.setContentView(layout);
        dialog.show();
        layout.findViewById(g.tv_cancel).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ LoginActivity aYl;

            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        layout.findViewById(g.tv_confirm).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ LoginActivity aYl;

            public void onClick(View arg0) {
                dialog.dismiss();
                this.aYl.cN(false);
            }
        });
    }

    private void k(String openid, String qToekn, String context) {
        com.huluxia.module.profile.g.Eb().k(openid, qToekn, context);
    }

    private void cO(boolean isOK) {
        Intent data = new Intent();
        data.putExtra("ok", isOK);
        setResult(this.aYe, data);
        finish();
    }
}
