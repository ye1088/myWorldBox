package com.MCWorld.ui.loginAndRegister;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.MCWorld.HTApplication;
import com.MCWorld.bbs.b.f;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.bbs.b.m;
import com.MCWorld.data.HTUploadInfo;
import com.MCWorld.data.LoginErrCode;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.framework.base.utils.UtilsCamera;
import com.MCWorld.framework.base.utils.UtilsFile;
import com.MCWorld.framework.base.utils.UtilsImage;
import com.MCWorld.framework.base.utils.UtilsScreen;
import com.MCWorld.framework.base.utils.UtilsString;
import com.MCWorld.http.loginAndRegister.d;
import com.MCWorld.http.other.h;
import com.MCWorld.module.account.c;
import com.MCWorld.module.w;
import com.MCWorld.t;
import com.MCWorld.ui.base.HTBaseActivity;
import com.MCWorld.ui.crop.CropImageActivity;
import com.MCWorld.utils.UtilsMenu;
import com.MCWorld.utils.ab;
import com.MCWorld.utils.ad;
import com.MCWorld.utils.at;
import com.MCWorld.utils.aw;
import com.MCWorld.utils.r;
import com.MCWorld.widget.b;
import com.MCWorld.widget.dialog.n;
import com.MCWorld.widget.dialog.o;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import org.apache.tools.ant.util.DateUtils;
import org.json.JSONObject;

public class RegisterActivity extends HTBaseActivity {
    private static final String aXG = "open_id";
    private static final String aXH = "qq_token";
    private static final String aXI = "PARA_ACCOUNT";
    private static final String aXJ = "PARA_PASSWORD";
    private static final String aXK = "IS_FIRST_STEP";
    private static final String aYC = "IS_FIRST_ENTER";
    private static final String aYD = "PARA_VCODE";
    private static final String aYE = "PARA_NICK";
    private static final String aYF = "PARA_GENDER";
    private static final String aYG = "PARA_BIRTHDAY";
    private static final String aYH = "PARA_FILENAME";
    private static final String aYI = "PARA_FID";
    private static final String aYk = "flag";
    public Tencent aDA;
    private h aLT = new h();
    private OnClickListener aRN = new OnClickListener(this) {
        final /* synthetic */ RegisterActivity aYN;

        {
            this.aYN = this$0;
        }

        public void onClick(View v) {
            if (v.getId() == g.btn_vcode) {
                this.aYN.Ie();
            } else if (v.getId() == g.tv_register) {
                this.aYN.If();
            } else if (v.getId() == g.tv_policy) {
                t.aq(this.aYN);
            }
        }
    };
    private String aXL;
    private String aXM;
    private String aXN;
    private String aXO;
    private boolean aXP = true;
    private View aXQ;
    private EditText aXS;
    private EditText aXT;
    private OnClickListener aXW = new OnClickListener(this) {
        final /* synthetic */ RegisterActivity aYN;

        {
            this.aYN = this$0;
        }

        public void onClick(View v) {
            this.aYN.aXP = true;
            this.aYN.HX();
        }
    };
    IUiListener aXX = new a(this) {
        final /* synthetic */ RegisterActivity aYN;

        {
            this.aYN = this$0;
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
                t.show_toast(this.aYN, "QQ验证失败。请重试。");
                return;
            }
            this.aYN.aXN = openId;
            this.aYN.aXO = token;
            this.aYN.cs(true);
            this.aYN.V(openId, token);
        }
    };
    private String aYA;
    private String aYB;
    private Button aYJ;
    private b aYK = null;
    private CheckBox aYL;
    private OnClickListener aYM = new OnClickListener(this) {
        final /* synthetic */ RegisterActivity aYN;

        {
            this.aYN = this$0;
        }

        public void onClick(View v) {
            if (this.aYN.aXP) {
                this.aYN.Ic();
            } else {
                this.aYN.Id();
            }
        }
    };
    private RegisterActivity aYm;
    private PaintView aYn = null;
    private View aYo;
    private d aYp = new d();
    private SimpleDateFormat aYq = new SimpleDateFormat(DateUtils.ISO8601_DATE_PATTERN, Locale.getDefault());
    private TextView aYr;
    private TextView aYs;
    private EditText aYt;
    private int aYu = 0;
    private boolean aYv = true;
    private String aYw;
    private String aYx;
    private String aYy;
    private String aYz;
    private String appId = "100580922";
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ RegisterActivity aYN;

        {
            this.aYN = this$0;
        }

        @MessageHandler(message = 599)
        public void onRecvQinfo(boolean succ, String qinfo, String context, String openid, String qtoken) {
            this.aYN.cs(false);
            if ("RegisterActivity".equals(context)) {
                this.aYN.eD(qinfo);
            }
        }

        @MessageHandler(message = 632)
        public void onChkOpenId(boolean succ, w info) {
            this.aYN.cs(false);
            if (succ && info != null) {
                if (info.isSucc()) {
                    t.o(this.aYN, "QQ验证成功");
                    this.aYN.HX();
                } else if (info.code == 109) {
                    this.aYN.eB("QQ验证失败，请重试");
                } else if (info.code == 110) {
                    this.aYN.eB("该QQ已经被其他账号关联了，请换另外的QQ号并重试");
                } else {
                    t.o(this.aYN, "QQ验证成功");
                    this.aYN.HX();
                }
            }
        }

        @MessageHandler(message = 647)
        public void onValidateEmail(boolean succ, w info) {
            this.aYN.cs(false);
            if (succ) {
                this.aYN.aXP = false;
                this.aYN.HY();
            } else if (info == null || info.code != 120) {
                t.n(this.aYN, "检查失败，网络问题");
            } else {
                t.n(this.aYN, "该邮箱已被注册");
            }
        }

        @MessageHandler(message = 649)
        public void onVoiceVerify(boolean succ, c info) {
            this.aYN.cs(false);
            if (succ) {
                if (this.aYN.aYK == null && info != null) {
                    int hintColor = com.MCWorld.bbs.b.d.white;
                    this.aYN.aYK = new b(info.countTime == 0 ? b.btD : info.countTime, this.aYN.aYJ, m.getVcode, hintColor, hintColor);
                }
                if (this.aYN.aYK != null) {
                    this.aYN.aYK.start();
                }
            } else if (info == null) {
                t.n(this.aYN, "请求失败, 网络问题");
            } else if (info.code == LoginErrCode.ERR_OPENID.Value()) {
                this.aYN.eB(info.msg);
            } else {
                t.n(this.aYN.aYm, ab.n(info.code, info.msg));
            }
        }

        @MessageHandler(message = 656)
        public void onCheckPhone(boolean succ, w info) {
            this.aYN.cs(false);
            if (succ) {
                this.aYN.aXP = false;
                this.aYN.HY();
            } else if (info == null) {
                t.n(this.aYN, "检查失败，网络问题");
            } else if (info.code == 109) {
                this.aYN.eB(info.msg);
            } else if (info.code == 110) {
                this.aYN.eB(info.msg);
            } else {
                t.n(this.aYN.aYm, ab.n(info.code, info.msg));
            }
        }
    };

    private class a implements IUiListener {
        final /* synthetic */ RegisterActivity aYN;

        private a(RegisterActivity registerActivity) {
            this.aYN = registerActivity;
        }

        protected void d(JSONObject values) {
        }

        public void onComplete(Object response) {
            this.aYN.cs(false);
            if (response == null) {
                t.show_toast(this.aYN, "QQ验证失败，请重试。");
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (jsonResponse == null || jsonResponse.length() != 0) {
                d((JSONObject) response);
            } else {
                t.show_toast(this.aYN, "QQ验证失败，请重试。");
            }
        }

        public void onError(UiError e) {
            t.show_toast(this.aYN, "onError: " + e.errorDetail);
            this.aYN.cs(false);
            this.aYN.cO(false);
        }

        public void onCancel() {
            t.show_toast(this.aYN, "取消验证");
            this.aYN.cs(false);
            this.aYN.cO(false);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.aYm = this;
        this.aIs.setVisibility(8);
        if (savedInstanceState != null) {
            this.aXN = savedInstanceState.getString(aXI);
            this.aXO = savedInstanceState.getString(aXJ);
            this.aYu = savedInstanceState.getInt("flag");
            this.aXP = savedInstanceState.getBoolean(aXK);
            this.aYv = savedInstanceState.getBoolean(aYC);
            this.aXL = savedInstanceState.getString(aXI);
            this.aXM = savedInstanceState.getString(aXJ);
            this.aYw = savedInstanceState.getString(aYD);
            this.aYx = savedInstanceState.getString(aYE);
            this.aYy = savedInstanceState.getString(aYF);
            this.aYz = savedInstanceState.getString(aYG);
            this.aYA = savedInstanceState.getString(aYH);
            this.aYB = savedInstanceState.getString(aYI);
        } else {
            this.aXN = getIntent().getStringExtra(aXG);
            this.aXO = getIntent().getStringExtra(aXH);
            this.aYu = getIntent().getIntExtra("flag", 0);
        }
        this.aXQ = LayoutInflater.from(this).inflate(i.activity_register, null);
        this.aYo = LayoutInflater.from(this).inflate(i.activity_profile_edit, null);
        this.aYo.findViewById(g.profile_username_layout).setVisibility(0);
        this.aYo.findViewById(g.profile_user_name).setEnabled(true);
        this.aXS = (EditText) this.aXQ.findViewById(g.uin_edit_text);
        this.aXT = (EditText) this.aXQ.findViewById(g.blackberry_edit_text);
        this.aYt = (EditText) this.aXQ.findViewById(g.et_vcode);
        this.aYr = (TextView) this.aXQ.findViewById(g.tv_register);
        this.aYr.setOnClickListener(this.aRN);
        this.aYs = (TextView) this.aXQ.findViewById(g.tv_policy);
        this.aYs.setOnClickListener(this.aRN);
        this.aYL = (CheckBox) this.aXQ.findViewById(g.soft_permision_checkbox);
        this.aLT.bb(1);
        this.aLT.a(this);
        this.aYp.bb(2);
        this.aYp.a(this);
        EventNotifyCenter.add(com.MCWorld.module.h.class, this.mCallback);
        this.aIS.setOnClickListener(this.aXW);
        this.aIT.setOnClickListener(this.aYM);
        this.aYJ = (Button) this.aXQ.findViewById(g.btn_vcode);
        this.aYJ.setOnClickListener(this.aRN);
        HX();
        if (this.aYv && this.aXN == null && this.aXO == null) {
            Ia();
            this.aYv = false;
        }
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(aXG, this.aXN);
        savedInstanceState.putString(aXH, this.aXO);
        savedInstanceState.putInt("flag", this.aYu);
        savedInstanceState.putBoolean(aXK, this.aXP);
        savedInstanceState.putBoolean(aYC, this.aYv);
        savedInstanceState.putString(aXI, this.aXL);
        savedInstanceState.putString(aXJ, this.aXM);
        savedInstanceState.putString(aYD, this.aYw);
        savedInstanceState.putString(aYE, this.aYx);
        savedInstanceState.putString(aYF, this.aYy);
        savedInstanceState.putString(aYG, this.aYz);
        savedInstanceState.putString(aYH, this.aYA);
        savedInstanceState.putString(aYI, this.aYB);
    }

    protected void onResume() {
        super.onResume();
        cs(false);
    }

    protected void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
    }

    private void HX() {
        setContentView(this.aXQ);
        HZ();
    }

    private void HZ() {
        this.aIs.setVisibility(8);
        if (this.aXP) {
            this.aIT.setVisibility(8);
            this.aIR.setVisibility(0);
            this.aIS.setVisibility(8);
            return;
        }
        this.aIT.setVisibility(0);
        this.aIT.setText(m.finished);
        this.aIR.setVisibility(8);
        this.aIS.setVisibility(0);
        this.aIS.setText(m.prevstep);
    }

    private boolean Ic() {
        String email = this.aXS.getText().toString();
        String password = this.aXT.getText().toString();
        if (aw.validEmail(email.trim())) {
            this.aXL = email.trim();
            this.aXM = password.trim();
            cs(true);
            com.MCWorld.module.account.a.DU().eb(email.trim());
            return true;
        }
        t.n(this, "邮箱不合法");
        return false;
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

    private boolean Id() {
        TextView tv_name = (TextView) findViewById(g.profile_user_name);
        if (tv_name == null || tv_name.getText() == null) {
            t.n(this, "数据异常请重试");
            return false;
        }
        String nick = tv_name.getText().toString();
        if (!UtilsFile.isExist(this.aYA)) {
            t.n(this, "必须上传头像");
            return false;
        } else if (!eC(nick)) {
            return false;
        } else {
            if (((TextView) findViewById(g.profile_sex_desc)).getText().toString().compareTo("女") == 0) {
                this.aYy = "1";
            } else {
                this.aYy = "2";
            }
            try {
                this.aYz = String.valueOf(this.aYq.parse(((TextView) findViewById(g.profile_birthday_desc)).getText().toString()).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            this.aYx = nick;
            this.aLT.setFilename(this.aYA);
            this.aLT.eY();
            at.hideInputMethod(this.aYo);
            return true;
        }
    }

    private void HY() {
        setContentView(this.aYo);
        HZ();
        this.aYn = (PaintView) findViewById(g.profile_user_header);
        this.aYn.radius(10.0f);
        this.aYn.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ RegisterActivity aYN;

            {
                this.aYN = this$0;
            }

            public void onClick(View v) {
                r.showPhotoMenu(this.aYN);
            }
        });
        final TextView tv_sex = (TextView) findViewById(g.profile_sex_desc);
        final ImageView iv_sex = (ImageView) findViewById(g.profile_sex_icon);
        final n dialogMenu = UtilsMenu.bk(this);
        dialogMenu.a(new com.MCWorld.widget.dialog.n.a(this) {
            final /* synthetic */ RegisterActivity aYN;

            public void a(o m) {
                if (((Integer) m.getTag()).intValue() == 1) {
                    this.aYN.aYy = "1";
                    tv_sex.setText("女");
                    iv_sex.setImageResource(f.g_icon_girl);
                } else {
                    this.aYN.aYy = "2";
                    tv_sex.setText("男");
                    iv_sex.setImageResource(f.g_icon_boy);
                }
                dialogMenu.dismiss();
            }
        });
        ((RelativeLayout) findViewById(g.profile_sex_layout)).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ RegisterActivity aYN;

            public void onClick(View v) {
                dialogMenu.show();
            }
        });
        ((RelativeLayout) findViewById(g.profile_birthday_layout)).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ RegisterActivity aYN;

            {
                this.aYN = this$0;
            }

            public void onClick(View v) {
                TextView birthdayView = (TextView) this.aYN.findViewById(g.profile_birthday_desc);
                Calendar birthCal = Calendar.getInstance();
                try {
                    birthCal.setTime(this.aYN.aYq.parse(birthdayView.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                com.MCWorld.widget.dialog.d wjDialog = new com.MCWorld.widget.dialog.d(birthCal.get(1), birthCal.get(2) + 1, birthCal.get(5));
                wjDialog.lT(1920);
                wjDialog.lU(2010);
                View dialogView = wjDialog.bF(this.aYN);
                Builder builder = new Builder(this.aYN);
                ((TextView) dialogView.findViewById(g.tv_title)).setText("修改生日");
                builder.setCancelable(true);
                AlertDialog dialog = builder.create();
                dialog.setView(dialogView, 0, 0, 0, 0);
                dialog.show();
                dialogView.findViewById(g.tv_confirm).setOnClickListener(new 1(this, dialog, wjDialog, birthdayView));
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_LOGIN || requestCode == Constants.REQUEST_APPBAR) {
            cs(false);
            Tencent.onActivityResultData(requestCode, resultCode, data, this.aXX);
        } else if (resultCode == -1) {
            String cropPath = UtilsCamera.onPickResultToCrop(resultCode, requestCode, data, this, CropImageActivity.class, null, true);
            if (UtilsFile.isExist(cropPath)) {
                this.aYA = cropPath;
                Bitmap myBitmap = BitmapFactory.decodeFile(cropPath);
                if (myBitmap != null) {
                    Bitmap newBitMap = Bitmap.createScaledBitmap(myBitmap, 160, 160, true);
                    myBitmap.recycle();
                    Bitmap roundBitmap = UtilsImage.getRoundedCornerBitmap(newBitMap, 5.0f);
                    newBitMap.recycle();
                    if (this.aYn != null) {
                        this.aYn.setImageBitmap(roundBitmap);
                    }
                }
            }
        }
    }

    public void a(com.MCWorld.http.base.d response) {
        if (response.fe() == 1) {
            ek("上传头像");
        } else {
            ek("提交注册信息");
        }
        cs(true);
    }

    public void b(com.MCWorld.http.base.d response) {
        if (response.fe() == 1) {
            t.n(this, "上传头像失败\n网络错误");
        } else {
            t.n(this, "提交注册失败\n网络错误");
        }
        cs(false);
    }

    public void c(com.MCWorld.http.base.d response) {
        cs(false);
        if (response.getStatus() != 1) {
            t.n(this, ab.n(response.fg(), response.fh()));
        } else if (response.fe() == 1) {
            this.aYB = ((HTUploadInfo) response.getData()).getFid();
            cs(true);
            k(this.aXN, this.aXO, "RegisterActivity");
        } else if (response.fe() == 2) {
            t.o(this, "注册成功，自动登录");
            cO(true);
        } else if (response.fe() == 3) {
            t.o(this, "登录成功");
            com.MCWorld.service.i.EF();
            HTApplication.bR();
            com.MCWorld.module.account.a.DU().DY();
            cO(true);
        }
    }

    private void Ia() {
        if (this.aDA == null) {
            this.aDA = Tencent.createInstance(this.appId, HTApplication.getAppContext());
        }
        if (this.aDA.isSessionValid()) {
            this.aDA.logout(this);
        }
        cs(true);
        this.aDA.login((Activity) this, "all", this.aXX);
    }

    private boolean eC(String nick) {
        if (nick.trim().length() < 2) {
            t.n(this, "昵称不能小于2个字符");
            return false;
        } else if (nick.trim().length() > 8) {
            t.n(this, "昵称不能大于8个字符");
            return false;
        } else if (nick.matches("[0-9a-zA-Z一-龥].+")) {
            return true;
        } else {
            t.n(this, "昵称第一个字符不能为符号，表情。");
            return false;
        }
    }

    private void k(String openid, String qToekn, String context) {
        com.MCWorld.module.profile.g.Eb().k(openid, qToekn, context);
    }

    private void eD(String qqInfo) {
        this.aYp.aW(qqInfo);
        this.aYp.setAvatar_fid(this.aYB);
        this.aYp.aR(this.aXL);
        this.aYp.setPassword(this.aXM);
        this.aYp.aX(this.aYw);
        this.aYp.aU(this.aXN);
        this.aYp.aV(this.aXO);
        this.aYp.aT(this.aYz);
        this.aYp.aS(this.aYy);
        this.aYp.setNick(this.aYx);
        this.aYp.eY();
    }

    private void V(String openId, String accessToken) {
        com.MCWorld.module.account.a.DU().P(openId, accessToken);
    }

    public void eB(String msg) {
        final Dialog dialog = new Dialog(this.aYm, com.simple.colorful.d.RD());
        View layout = LayoutInflater.from(this).inflate(i.include_dialog_two, null);
        ((TextView) layout.findViewById(g.tv_msg)).setText(msg);
        dialog.setCancelable(false);
        dialog.setContentView(layout);
        dialog.show();
        layout.findViewById(g.tv_cancel).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ RegisterActivity aYN;

            public void onClick(View arg0) {
                dialog.dismiss();
                this.aYN.cO(false);
            }
        });
        layout.findViewById(g.tv_confirm).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ RegisterActivity aYN;

            public void onClick(View arg0) {
                dialog.dismiss();
                this.aYN.Ia();
            }
        });
    }

    private void Ie() {
        String email = this.aXS.getText().toString() != null ? this.aXS.getText().toString() : "";
        this.aXL = email.trim();
        if (UtilsString.validPhone(this.aXL)) {
            cs(true);
            com.MCWorld.module.account.a.DU().i(this.aXL, this.aXN, this.aXO);
            return;
        }
        t.show_toast(this.aYm, "请输入合法的手机号");
        this.aXS.requestFocus();
        this.aXS.setSelection(email.length());
    }

    private void If() {
        String email = this.aXS.getText().toString() != null ? this.aXS.getText().toString() : "";
        this.aXL = email.trim();
        String pwd = this.aXT.getText().toString() != null ? this.aXT.getText().toString() : "";
        this.aXM = pwd.trim();
        String vcode = this.aYt.getText().toString() != null ? this.aYt.getText().toString() : "";
        this.aYw = vcode.trim();
        if (!UtilsString.validPhone(this.aXL)) {
            t.show_toast(this.aYm, "手机号无效");
            this.aXS.requestFocus();
            this.aXS.setSelection(email.length());
        } else if (!eA(this.aXM)) {
            this.aXT.requestFocus();
            this.aXT.setSelection(pwd.length());
        } else if (this.aYw == null || this.aYw.length() < 2) {
            t.show_toast(this.aYm, "验证码无效");
            this.aYt.requestFocus();
            this.aYt.setSelection(vcode.length());
        } else if (this.aYL.isChecked()) {
            UtilsScreen.hideInputMethod(this.aXS);
            UtilsScreen.hideInputMethod(this.aXT);
            UtilsScreen.hideInputMethod(this.aYt);
            cs(true);
            com.MCWorld.module.account.a.DU().R(this.aXL, this.aYw);
        } else {
            t.show_toast(this.aYm, "必须勾选同意使用条款才能注册");
        }
    }

    private void cO(boolean isOk) {
        Intent data = new Intent();
        data.putExtra("ok", isOk);
        setResult(this.aYu, data);
        finish();
    }

    public void onBackPressed() {
        Intent data = new Intent();
        data.putExtra("ok", false);
        setResult(this.aYu, data);
        super.onBackPressed();
    }
}
