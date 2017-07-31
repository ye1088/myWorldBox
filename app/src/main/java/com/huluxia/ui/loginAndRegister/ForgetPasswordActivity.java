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
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.utils.UtilsString;
import com.huluxia.framework.base.widget.dialog.DialogManager.OkCancelDialogListener;
import com.huluxia.http.base.d;
import com.huluxia.module.account.c;
import com.huluxia.module.h;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseActivity;
import com.huluxia.utils.ab;
import com.huluxia.utils.ad;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import org.json.JSONObject;

public class ForgetPasswordActivity extends HTBaseActivity {
    private static final String aXG = "open_id";
    private static final String aXH = "qq_token";
    private static final String aXI = "PARA_ACCOUNT";
    private static final String aXJ = "PARA_PASSWORD";
    private static final String aXK = "IS_FIRST_STEP";
    public Tencent aDA;
    private OnClickListener aRN = new OnClickListener(this) {
        final /* synthetic */ ForgetPasswordActivity aXY;

        {
            this.aXY = this$0;
        }

        public void onClick(View v) {
            if (v.getId() == g.tv_next) {
                this.aXY.HV();
            } else if (v.getId() == g.tv_submit) {
                this.aXY.HW();
            }
        }
    };
    private ForgetPasswordActivity aXF;
    private String aXL;
    private String aXM;
    private String aXN;
    private String aXO;
    private boolean aXP = true;
    private View aXQ;
    private View aXR;
    private EditText aXS;
    private EditText aXT;
    private TextView aXU;
    private TextView aXV;
    private OnClickListener aXW = new OnClickListener(this) {
        final /* synthetic */ ForgetPasswordActivity aXY;

        {
            this.aXY = this$0;
        }

        public void onClick(View v) {
            this.aXY.aXP = true;
            this.aXY.HX();
        }
    };
    IUiListener aXX = new a(this) {
        final /* synthetic */ ForgetPasswordActivity aXY;

        {
            this.aXY = this$0;
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
            }
            if (TextUtils.isEmpty(token) || TextUtils.isEmpty(expires) || TextUtils.isEmpty(openId)) {
                this.aXY.eB("QQ验证失败。请重试。");
                return;
            }
            this.aXY.aXN = openId;
            this.aXY.aXO = token;
            this.aXY.HX();
            this.aXY.aXL = (this.aXY.aXS.getText().toString() != null ? this.aXY.aXS.getText().toString() : "").trim();
            if (!TextUtils.isEmpty(this.aXY.aXL)) {
                this.aXY.HV();
            }
        }
    };
    private String appId = "100580922";
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ ForgetPasswordActivity aXY;

        {
            this.aXY = this$0;
        }

        @MessageHandler(message = 657)
        public void onPWdReset(boolean succ, c info) {
            this.aXY.cs(false);
            if (info == null) {
                t.n(this.aXY.aXF, "请求失败, 网络问题");
            } else if (info.isSucc() && info.isNext == 1) {
                this.aXY.aXP = false;
                this.aXY.aXF.HY();
            } else if (info.isSucc()) {
                t.o(this.aXY.aXF, "已将重置密码邮件发往您的邮箱" + this.aXY.aXL + "。请登录邮箱重新设置密码。");
                this.aXY.aXF.finish();
            } else if (info.code != 109) {
                t.n(this.aXY.aXF, ab.n(info.code, info.msg));
            } else if (info.qqinfo == null || info.qqinfo.nickname == null) {
                this.aXY.eB(info.msg);
            } else {
                this.aXY.a(info.qqinfo);
            }
        }

        @MessageHandler(message = 658)
        public void onPWdUpdate(boolean succ, c info) {
            this.aXY.cs(false);
            if (info == null) {
                t.n(this.aXY.aXF, "请求失败, 网络问题");
            } else if (info.isSucc()) {
                t.o(this.aXY.aXF, "设置成功。请记住新密码。");
                this.aXY.aXF.finish();
            } else if (info.code != 109) {
                t.n(this.aXY.aXF, ab.n(info.code, info.msg));
            } else if (info.qqinfo == null || info.qqinfo.nickname == null) {
                this.aXY.eB(info.msg);
            } else {
                this.aXY.a(info.qqinfo);
            }
        }
    };

    private class a implements IUiListener {
        final /* synthetic */ ForgetPasswordActivity aXY;

        private a(ForgetPasswordActivity forgetPasswordActivity) {
            this.aXY = forgetPasswordActivity;
        }

        protected void d(JSONObject values) {
        }

        public void onComplete(Object response) {
            this.aXY.aXF.cs(false);
            if (response == null) {
                t.l(this.aXY.aXF, "QQ验证失败，请重试。");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (jsonResponse == null || jsonResponse.length() != 0) {
                d((JSONObject) response);
            } else {
                t.l(this.aXY.aXF, "QQ验证失败，请重试。");
            }
        }

        public void onError(UiError e) {
            t.l(this.aXY.aXF, "onError: " + e.errorDetail);
            this.aXY.aXF.cs(false);
            this.aXY.aXF.finish();
        }

        public void onCancel() {
            t.l(this.aXY.aXF, "取消验证");
            this.aXY.aXF.cs(false);
            this.aXY.aXF.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.aXF = this;
        this.aIs.setVisibility(8);
        if (savedInstanceState != null) {
            this.aXN = savedInstanceState.getString(aXI);
            this.aXO = savedInstanceState.getString(aXJ);
            this.aXP = savedInstanceState.getBoolean(aXK);
            this.aXL = savedInstanceState.getString(aXI);
            this.aXM = savedInstanceState.getString(aXJ);
        } else {
            this.aXN = getIntent().getStringExtra(aXG);
            this.aXO = getIntent().getStringExtra(aXH);
        }
        this.aXQ = LayoutInflater.from(this).inflate(i.activity_forget_password, null);
        this.aXR = LayoutInflater.from(this).inflate(i.activity_reset_password, null);
        this.aXS = (EditText) this.aXQ.findViewById(g.et_account);
        this.aXT = (EditText) this.aXR.findViewById(g.et_passwd);
        this.aXU = (TextView) this.aXQ.findViewById(g.tv_next);
        this.aXV = (TextView) this.aXR.findViewById(g.tv_submit);
        this.aXU.setOnClickListener(this.aRN);
        this.aXV.setOnClickListener(this.aRN);
        this.aIS.setOnClickListener(this.aXW);
        EventNotifyCenter.add(h.class, this.mCallback);
        HX();
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(aXG, this.aXN);
        savedInstanceState.putString(aXH, this.aXO);
        savedInstanceState.putBoolean(aXK, this.aXP);
        savedInstanceState.putString(aXI, this.aXL);
        savedInstanceState.putString(aXJ, this.aXM);
    }

    protected void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_LOGIN || requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode, resultCode, data, this.aXX);
        } else if (resultCode == -1) {
        }
    }

    private void HV() {
        String account = this.aXS.getText().toString() != null ? this.aXS.getText().toString() : "";
        this.aXL = account.trim();
        if (TextUtils.isEmpty(this.aXL)) {
            t.l(this.aXF, "请输入正确的账号");
            this.aXS.requestFocus();
            this.aXS.setSelection(account.length());
        } else if (UtilsString.validEmail(this.aXL) || UtilsString.validPhone(this.aXL)) {
            cs(true);
            com.huluxia.module.account.a.DU().j(this.aXL, this.aXN, this.aXO);
        } else {
            t.l(this.aXF, "账号格式错误");
            this.aXS.requestFocus();
            this.aXS.setSelection(account.length());
        }
    }

    private void HW() {
        String account = this.aXS.getText().toString() != null ? this.aXS.getText().toString() : "";
        this.aXL = account.trim();
        String pwd = this.aXT.getText().toString() != null ? this.aXT.getText().toString() : "";
        this.aXM = pwd.trim();
        if (!UtilsString.validEmail(this.aXL) && !UtilsString.validPhone(this.aXL)) {
            t.l(this.aXF, "账号格式错误");
            HX();
            this.aXS.requestFocus();
            this.aXS.setSelection(account.length());
        } else if (eA(this.aXM)) {
            cs(true);
            com.huluxia.module.account.a.DU().d(this.aXL, this.aXM, this.aXN, this.aXO);
        } else {
            this.aXT.requestFocus();
            this.aXT.setSelection(pwd.length());
        }
    }

    private boolean eA(String password) {
        if (ad.empty((CharSequence) password)) {
            t.n(this, "密码不能为空");
            return false;
        } else if (password.length() < 6) {
            t.n(this, "密码不能小于6位");
            return false;
        } else if (UtilsString.validPwd(password)) {
            return true;
        } else {
            t.n(this, "密码过于简单");
            return false;
        }
    }

    public void b(d response) {
        super.b(response);
        t.n(this, "失败\n网络错误");
    }

    public void c(d response) {
        super.c(response);
        if (response.getStatus() == 1) {
            t.o(this, "成功,请登陆邮箱找回密码");
            finish();
            return;
        }
        t.n(this, ab.n(response.fg(), response.fh()));
    }

    private void HX() {
        setContentView(this.aXQ);
        HZ();
    }

    private void HY() {
        setContentView(this.aXR);
        HZ();
    }

    private void HZ() {
        if (this.aXP) {
            this.aIT.setVisibility(8);
            this.aIR.setVisibility(0);
            this.aIS.setVisibility(8);
            return;
        }
        this.aIT.setVisibility(8);
        this.aIR.setVisibility(8);
        this.aIS.setVisibility(0);
        this.aIS.setText(m.prevstep);
    }

    public void a(com.huluxia.data.i qqinfo) {
        String msg = "请用注册时关联的QQ进行验证。";
        com.huluxia.widget.dialog.c dialogManager = new com.huluxia.widget.dialog.c(this.aXF);
        if (qqinfo != null) {
            dialogManager.a("账号登录保护", msg, "现在验证", "不验证", true, qqinfo.figureurl_qq_1, qqinfo.nickname, new OkCancelDialogListener(this) {
                final /* synthetic */ ForgetPasswordActivity aXY;

                {
                    this.aXY = this$0;
                }

                public void onCancel() {
                }

                public void onOk() {
                    this.aXY.Ia();
                }
            });
        } else {
            dialogManager.showOkCancelDialog("账号登录保护", (CharSequence) msg, (CharSequence) "现在验证", (CharSequence) "不验证", true, new OkCancelDialogListener(this) {
                final /* synthetic */ ForgetPasswordActivity aXY;

                {
                    this.aXY = this$0;
                }

                public void onCancel() {
                }

                public void onOk() {
                    this.aXY.Ia();
                }
            });
        }
    }

    public void eB(String msg) {
        final Dialog dialog = new Dialog(this.aXF, com.simple.colorful.d.RD());
        View layout = LayoutInflater.from(this).inflate(i.include_dialog_two, null);
        ((TextView) layout.findViewById(g.tv_msg)).setText(msg);
        dialog.setCancelable(false);
        dialog.setContentView(layout);
        dialog.show();
        layout.findViewById(g.tv_cancel).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ForgetPasswordActivity aXY;

            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        layout.findViewById(g.tv_confirm).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ForgetPasswordActivity aXY;

            public void onClick(View arg0) {
                dialog.dismiss();
                this.aXY.Ia();
            }
        });
    }

    private void Ia() {
        if (this.aDA == null) {
            this.aDA = Tencent.createInstance(this.appId, HTApplication.getAppContext());
        }
        if (this.aDA.isSessionValid()) {
            this.aDA.logout(this);
        }
        this.aXF.cs(true);
        this.aDA.login((Activity) this, "all", this.aXX);
    }
}
