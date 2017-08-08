package com.MCWorld.ui.loginAndRegister;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.MCWorld.bbs.b.f;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.bbs.b.m;
import com.MCWorld.data.HTUploadInfo;
import com.MCWorld.data.j;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.http.loginAndRegister.c;
import com.MCWorld.http.other.e;
import com.MCWorld.http.other.h;
import com.MCWorld.t;
import com.MCWorld.ui.base.HTBaseActivity;
import com.MCWorld.utils.UtilsFile;
import com.MCWorld.utils.UtilsMenu;
import com.MCWorld.utils.ab;
import com.MCWorld.utils.ae;
import com.MCWorld.utils.at;
import com.MCWorld.utils.aw;
import com.MCWorld.utils.q;
import com.MCWorld.widget.dialog.d;
import com.MCWorld.widget.dialog.n;
import com.MCWorld.widget.dialog.n.a;
import com.MCWorld.widget.dialog.o;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import org.apache.tools.ant.util.DateUtils;

public class RegisterByMiActivity extends HTBaseActivity {
    private static int aYX = 0;
    private static int aYY = 1;
    private h aLT = new h();
    private View aXQ;
    private e aYU = new e();
    private c aYV = new c();
    private String aYW;
    private int aYZ = aYY;
    private PaintView aYn = null;
    private View aYo;
    private SimpleDateFormat aYq = new SimpleDateFormat(DateUtils.ISO8601_DATE_PATTERN, Locale.getDefault());
    private RelativeLayout aZa;
    private RelativeLayout aZb;
    private RadioGroup aZc;
    private OnClickListener aZd = new OnClickListener(this) {
        final /* synthetic */ RegisterByMiActivity aZf;

        {
            this.aZf = this$0;
        }

        public void onClick(View arg0) {
            this.aZf.Ic();
        }
    };
    private OnCheckedChangeListener aZe = new OnCheckedChangeListener(this) {
        final /* synthetic */ RegisterByMiActivity aZf;

        {
            this.aZf = this$0;
        }

        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == g.rb_mi) {
                this.aZf.cP(false);
            } else if (checkedId == g.rb_hlx) {
                this.aZf.cP(true);
            }
        }
    };
    private long sP;
    private int tf = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.aIs.setVisibility(8);
        this.tf = getIntent().getIntExtra("flag", 0);
        this.sP = getIntent().getLongExtra("miUid", 0);
        this.aYW = getIntent().getStringExtra("miNick");
        this.aXQ = LayoutInflater.from(this).inflate(i.activity_registermi, null);
        this.aYo = LayoutInflater.from(this).inflate(i.activity_profile_edit, null);
        this.aYo.findViewById(g.profile_username_layout).setVisibility(0);
        this.aYU.bb(0);
        this.aYU.I(this.sP);
        this.aYU.a(this);
        this.aLT.bb(1);
        this.aLT.a(this);
        this.aYV.bb(2);
        this.aYV.a(this);
        Ig();
    }

    private void Ig() {
        setContentView(this.aXQ);
        this.aIT.setVisibility(0);
        this.aIT.setText(m.nextstep);
        this.aIT.setOnClickListener(this.aZd);
        this.aIR.setVisibility(0);
        this.aIS.setVisibility(8);
        this.aZa = (RelativeLayout) findViewById(g.rl_account);
        this.aZb = (RelativeLayout) findViewById(g.rl_passwd);
        this.aZc = (RadioGroup) findViewById(g.bind_radios);
        this.aZc.setOnCheckedChangeListener(this.aZe);
    }

    private boolean Ic() {
        if (this.aYZ == aYX) {
            Ih();
            return true;
        }
        String email = ((TextView) findViewById(g.uin_edit_text)).getText().toString();
        String password = ((TextView) findViewById(g.blackberry_edit_text)).getText().toString();
        if (!aw.validEmail(email.trim())) {
            t.n(this, "账号错误，请填写正确的邮箱格式");
            return false;
        } else if (password.length() < 6) {
            t.n(this, "密码错误，密码不能小于6位");
            return false;
        } else {
            at.hideInputMethod(this.aXQ);
            this.aYU.aR(email.trim());
            this.aYU.setPassword(password);
            this.aYU.eY();
            return true;
        }
    }

    private void Ih() {
        setContentView(this.aYo);
        ((TextView) this.aYo.findViewById(g.profile_user_name)).setText(this.aYW);
        this.aIT.setVisibility(0);
        this.aIT.setText(m.finished);
        this.aIT.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ RegisterByMiActivity aZf;

            {
                this.aZf = this$0;
            }

            public void onClick(View v) {
                this.aZf.Id();
            }
        });
        this.aIR.setVisibility(8);
        this.aIS.setVisibility(0);
        this.aIS.setText(m.prevstep);
        this.aIS.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ RegisterByMiActivity aZf;

            {
                this.aZf = this$0;
            }

            public void onClick(View v) {
                this.aZf.Ig();
            }
        });
        this.aYn = (PaintView) findViewById(g.profile_user_header);
        this.aYn.radius(10.0f);
        this.aYn.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ RegisterByMiActivity aZf;

            {
                this.aZf = this$0;
            }

            public void onClick(View v) {
                q.showPhotoMenu(this.aZf);
            }
        });
        final TextView tv_sex = (TextView) findViewById(g.profile_sex_desc);
        final ImageView iv_sex = (ImageView) findViewById(g.profile_sex_icon);
        final n dialogMenu = UtilsMenu.bk(this);
        dialogMenu.a(new a(this) {
            final /* synthetic */ RegisterByMiActivity aZf;

            public void a(o m) {
                if (((Integer) m.getTag()).intValue() == 1) {
                    this.aZf.aYV.setGender(1);
                    tv_sex.setText("女");
                    iv_sex.setImageResource(f.g_icon_girl);
                } else {
                    this.aZf.aYV.setGender(2);
                    tv_sex.setText("男");
                    iv_sex.setImageResource(f.g_icon_boy);
                }
                dialogMenu.dismiss();
            }
        });
        ((RelativeLayout) findViewById(g.profile_sex_layout)).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ RegisterByMiActivity aZf;

            public void onClick(View v) {
                dialogMenu.show();
            }
        });
        ((RelativeLayout) findViewById(g.profile_birthday_layout)).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ RegisterByMiActivity aZf;

            {
                this.aZf = this$0;
            }

            public void onClick(View v) {
                TextView birthdayView = (TextView) this.aZf.findViewById(g.profile_birthday_desc);
                Calendar birthCal = Calendar.getInstance();
                try {
                    birthCal.setTime(this.aZf.aYq.parse(birthdayView.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                d wjDialog = new d(birthCal.get(1), birthCal.get(2) + 1, birthCal.get(5));
                wjDialog.lT(1920);
                wjDialog.lU(2010);
                View dialogView = wjDialog.bF(this.aZf);
                Builder builder = new Builder(this.aZf);
                ((TextView) dialogView.findViewById(g.tv_title)).setText("修改生日");
                builder.setCancelable(true);
                AlertDialog dialog = builder.create();
                dialog.setView(dialogView, 0, 0, 0, 0);
                dialog.show();
                dialogView.findViewById(g.tv_confirm).setOnClickListener(new 1(this, dialog, wjDialog, birthdayView));
            }
        });
    }

    private boolean Id() {
        String nick = ((TextView) findViewById(g.profile_user_name)).getText().toString();
        if (!UtilsFile.isExist(this.aLT.getFilename())) {
            t.n(this, "请先上传头像");
            return false;
        } else if (nick.trim().length() < 2) {
            t.n(this, "昵称不能小于2个字符");
            return false;
        } else if (nick.trim().length() > 8) {
            t.n(this, "昵称不能大于8个字符");
            return false;
        } else {
            if (((TextView) findViewById(g.profile_sex_desc)).getText().toString().compareTo("女") == 0) {
                this.aYV.setGender(1);
            } else {
                this.aYV.setGender(2);
            }
            try {
                this.aYV.setBirthday(this.aYq.parse(((TextView) findViewById(g.profile_birthday_desc)).getText().toString()).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            this.aYV.G(this.sP);
            this.aYV.setNick(nick);
            this.aLT.eY();
            at.hideInputMethod(this.aYo);
            return true;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1 && data != null && !data.equals("")) {
            String cropPath = q.a(resultCode, requestCode, data, (Activity) this, null, true);
            if (UtilsFile.isExist(cropPath)) {
                this.aLT.setFilename(cropPath);
                Bitmap myBitmap = BitmapFactory.decodeFile(cropPath);
                Bitmap newBitMap = Bitmap.createScaledBitmap(myBitmap, 160, 160, true);
                myBitmap.recycle();
                Bitmap roundBitmap = ae.getRoundedCornerBitmap(newBitMap, 5.0f);
                newBitMap.recycle();
                this.aYn.setImageBitmap(roundBitmap);
            }
        }
    }

    public void a(com.MCWorld.http.base.d response) {
        if (response.fe() == 0) {
            ek("验证账号");
        } else if (response.fe() == 1) {
            ek("上传头像");
        } else if (response.fe() == 2) {
            ek("提交资料");
        }
        cs(true);
    }

    public void b(com.MCWorld.http.base.d response) {
        if (response.fe() == 0) {
            t.n(this, "验证账号失败\n网络问题");
        } else if (response.fe() == 1) {
            t.n(this, "上传头像失败\n网络问题");
        } else if (response.fe() == 2) {
            t.n(this, "提交资料失败\n网络问题");
        }
        cs(false);
    }

    public void c(com.MCWorld.http.base.d response) {
        cs(false);
        if (response.getStatus() != 1) {
            t.n(this, ab.n(response.fg(), response.fh()));
        } else if (response.fe() == 1) {
            this.aYV.setAvatar_fid(((HTUploadInfo) response.getData()).getFid());
            this.aYV.eY();
        } else if (response.fe() == 0 || response.fe() == 2) {
            t.o(this, "登陆成功");
            com.MCWorld.service.i.EE();
            com.MCWorld.service.i.EF();
            if (j.ep().et() != null) {
                com.MCWorld.http.other.a bind = new com.MCWorld.http.other.a();
                bind.ay(j.ep().et());
                bind.eY();
            }
            setResult(this.tf, new Intent());
            finish();
        }
    }

    private void cP(boolean show) {
        if (this.aZa != null && this.aZb != null) {
            if (show) {
                this.aZa.setVisibility(0);
                this.aZb.setVisibility(0);
                this.aYZ = aYY;
                return;
            }
            this.aZa.setVisibility(8);
            this.aZb.setVisibility(8);
            this.aYZ = aYX;
        }
    }
}
