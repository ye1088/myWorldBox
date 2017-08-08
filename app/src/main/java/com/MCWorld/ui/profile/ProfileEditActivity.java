package com.MCWorld.ui.profile;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.SpannableString;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.MCWorld.bbs.b;
import com.MCWorld.bbs.b.f;
import com.MCWorld.bbs.b.i;
import com.MCWorld.bbs.b.m;
import com.MCWorld.data.HTUploadInfo;
import com.MCWorld.data.profile.ProfileInfo;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.framework.base.utils.UtilsCamera;
import com.MCWorld.framework.base.utils.UtilsImage;
import com.MCWorld.framework.base.widget.dialog.DialogManager;
import com.MCWorld.framework.base.widget.dialog.DialogManager.OkCancelDialogListener;
import com.MCWorld.http.other.h;
import com.MCWorld.http.profile.g;
import com.MCWorld.module.profile.e;
import com.MCWorld.t;
import com.MCWorld.ui.base.HTBaseActivity;
import com.MCWorld.ui.crop.CropImageActivity;
import com.MCWorld.utils.UtilsFile;
import com.MCWorld.utils.UtilsMenu;
import com.MCWorld.utils.ab;
import com.MCWorld.utils.at;
import com.MCWorld.utils.r;
import com.MCWorld.widget.dialog.d;
import com.MCWorld.widget.dialog.n;
import com.MCWorld.widget.dialog.n.a;
import com.MCWorld.widget.dialog.o;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import org.apache.tools.ant.util.DateUtils;

public class ProfileEditActivity extends HTBaseActivity {
    private h aLT = new h();
    private CallbackHandler aPj = new CallbackHandler(this) {
        final /* synthetic */ ProfileEditActivity bfR;

        {
            this.bfR = this$0;
        }

        @MessageHandler(message = 548)
        public void onRecvFreeNickChangeNum(boolean succ, e info) {
            if (succ) {
                this.bfR.bfP = info.isFree();
                this.bfR.bfK.setVisibility(0);
                if (this.bfR.bfO == null) {
                    return;
                }
                if (this.bfR.bfO.getCredits() >= 100 || this.bfR.bfP) {
                    this.bfR.bfL.setEnabled(true);
                    this.bfR.bfL.setSelection(this.bfR.bfO.getNick().length());
                    this.bfR.bfK.setVisibility(8);
                    return;
                }
                this.bfR.bfM.setBackgroundResource(f.input_box_style4_bg_pressed);
                return;
            }
            t.n(this.bfR, "检查改名失败\n网络问题");
        }

        @MessageHandler(message = 549)
        public void onRecvUpdateNick(boolean succ, String newNick, String errorMsg) {
            this.bfR.cs(false);
            if (succ) {
                this.bfR.Jk();
            } else {
                t.n(this.bfR, errorMsg);
            }
        }
    };
    private PaintView aYn = null;
    private SimpleDateFormat aYq = new SimpleDateFormat(DateUtils.ISO8601_DATE_PATTERN, Locale.getDefault());
    private g bfH = new g();
    private TextView bfI;
    private TextView bfJ;
    private TextView bfK;
    private EditText bfL;
    private RelativeLayout bfM;
    private ImageView bfN;
    private ProfileInfo bfO;
    private boolean bfP;
    private Context mContext;
    private int updateType = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.activity_profile_edit);
        this.mContext = this;
        EventNotifyCenter.add(com.MCWorld.module.h.class, this.aPj);
        this.bfO = (ProfileInfo) getIntent().getSerializableExtra("profileInfo");
        this.aLT.bb(1);
        this.aLT.a(this);
        this.bfH.bb(2);
        this.bfH.a(this);
        com.MCWorld.module.profile.g.Eb().Ec();
        h(this.bfO);
    }

    private void h(final ProfileInfo profileInfo) {
        this.aIs.setVisibility(8);
        this.aIT.setVisibility(0);
        this.aIT.setText(m.finished);
        this.aIT.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ProfileEditActivity bfR;

            public void onClick(View v) {
                if (!this.bfR.bfL.isEnabled() || profileInfo == null || profileInfo.getNick() == null || profileInfo.getNick().equals(this.bfR.bfL.getText().toString())) {
                    this.bfR.Jk();
                    return;
                }
                if (this.bfR.eK(this.bfR.bfL.getText().toString())) {
                    this.bfR.f(this.bfR.bfP, this.bfR.bfL.getText().toString());
                }
            }
        });
        this.aYn = (PaintView) findViewById(b.g.profile_user_header);
        this.bfL = (EditText) findViewById(b.g.profile_user_name);
        this.bfK = (TextView) findViewById(b.g.tip_nick_unvaliable);
        this.bfM = (RelativeLayout) findViewById(b.g.profile_username_layout);
        this.bfJ = (TextView) findViewById(b.g.profile_sex_desc);
        this.bfN = (ImageView) findViewById(b.g.profile_sex_icon);
        this.bfI = (TextView) findViewById(b.g.profile_birthday_desc);
        if (profileInfo != null) {
            t.b(this.aYn, profileInfo.getAvatar(), (float) t.dipToPx(this.mContext, 10));
            this.bfH.setAvatar_fid(profileInfo.getAvatar_fid());
            this.bfL.setText(profileInfo.getNick());
            if (profileInfo.getGender() == 1) {
                this.bfH.setGender(1);
                this.bfJ.setText("女");
                this.bfN.setImageResource(f.g_icon_girl);
            } else {
                this.bfH.setGender(2);
                this.bfJ.setText("男");
                this.bfN.setImageResource(f.g_icon_boy);
            }
            this.bfI.setText(this.aYq.format(Long.valueOf(profileInfo.getBirthday())));
            this.bfH.setBirthday(profileInfo.getBirthday());
        }
        this.aYn.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ProfileEditActivity bfR;

            {
                this.bfR = this$0;
            }

            public void onClick(View v) {
                r.showPhotoMenu(this.bfR);
            }
        });
        final n dialogMenu = UtilsMenu.bk(this);
        dialogMenu.a(new a(this) {
            final /* synthetic */ ProfileEditActivity bfR;

            public void a(o m) {
                if (((Integer) m.getTag()).intValue() == 1) {
                    this.bfR.bfH.setGender(1);
                    this.bfR.bfJ.setText("女");
                    this.bfR.bfN.setImageResource(f.g_icon_girl);
                } else {
                    this.bfR.bfH.setGender(2);
                    this.bfR.bfJ.setText("男");
                    this.bfR.bfN.setImageResource(f.g_icon_boy);
                }
                dialogMenu.dismiss();
            }
        });
        ((RelativeLayout) findViewById(b.g.profile_sex_layout)).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ProfileEditActivity bfR;

            public void onClick(View v) {
                dialogMenu.show();
            }
        });
        ((RelativeLayout) findViewById(b.g.profile_birthday_layout)).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ProfileEditActivity bfR;

            {
                this.bfR = this$0;
            }

            public void onClick(View v) {
                Calendar birthCal = Calendar.getInstance();
                try {
                    birthCal.setTime(this.bfR.aYq.parse(this.bfR.bfI.getText().toString()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                d wjDialog = new d(birthCal.get(1), birthCal.get(2) + 1, birthCal.get(5));
                wjDialog.lT(1920);
                wjDialog.lU(2010);
                View dialogView = wjDialog.bF(this.bfR);
                Builder builder = new Builder(this.bfR);
                ((TextView) dialogView.findViewById(b.g.tv_title)).setText("修改生日");
                builder.setCancelable(true);
                AlertDialog dialog = builder.create();
                dialog.setView(dialogView, 0, 0, 0, 0);
                dialog.show();
                dialogView.findViewById(b.g.tv_confirm).setOnClickListener(new 1(this, dialog, wjDialog));
            }
        });
    }

    private boolean Jk() {
        this.bfH.setNick("我就是我");
        if (UtilsFile.isExist(this.aLT.getFilename())) {
            this.aLT.eY();
        } else {
            this.bfH.eY();
        }
        at.hideInputMethod(findViewById(b.g.profile_user_name));
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            String cropPath = UtilsCamera.onPickResultToCrop(resultCode, requestCode, data, this, CropImageActivity.class, null, true);
            if (UtilsFile.isExist(cropPath)) {
                this.aLT.setFilename(cropPath);
                Bitmap myBitmap = BitmapFactory.decodeFile(cropPath);
                Bitmap newBitMap = Bitmap.createScaledBitmap(myBitmap, 160, 160, true);
                myBitmap.recycle();
                Bitmap roundBitmap = UtilsImage.getRoundedCornerBitmap(newBitMap, 5.0f);
                newBitMap.recycle();
                this.aYn.setImageBitmap(roundBitmap);
            }
        }
    }

    public void a(com.MCWorld.http.base.d response) {
        if (response.fe() == 1) {
            ek("上传头像");
        } else {
            ek("修改个人信息");
        }
        cs(true);
    }

    public void b(com.MCWorld.http.base.d response) {
        if (response.fe() == 1) {
            t.n(this, "上传头像失败\n网络错误");
        } else {
            t.n(this, "修改个人信息失败\n网络错误");
        }
        cs(false);
    }

    public void c(com.MCWorld.http.base.d response) {
        cs(false);
        if (response.fe() == 1) {
            this.bfH.setAvatar_fid(((HTUploadInfo) response.getData()).getFid());
            this.bfH.eY();
        } else if (response.getStatus() == 1) {
            if (this.updateType == 0) {
                t.o(this, "修改个人信息成功，本次修改不涉及昵称，不扣葫芦");
            } else {
                t.o(this, "修改个人信息成功");
            }
            finish();
            com.MCWorld.service.i.EJ();
        } else {
            t.n(this, ab.n(response.fg(), response.fh()));
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.aPj);
    }

    private void f(boolean isFree, final String newNick) {
        new DialogManager(this.mContext).showOkCancelColorDialog(null, new SpannableString(isFree ? getResources().getString(m.dialog_msg_nick_change_free) : getResources().getString(m.dialog_msg_nick_change_nofree)), "改昵称", getResources().getColor(b.d.green), "不改昵称", getResources().getColor(b.d.gray), true, new OkCancelDialogListener(this) {
            final /* synthetic */ ProfileEditActivity bfR;

            public void onCancel() {
                this.bfR.updateType = 0;
                this.bfR.Jk();
            }

            public void onOk() {
                this.bfR.cs(true);
                this.bfR.updateType = 1;
                com.MCWorld.module.profile.g.Eb().ec(newNick);
            }
        });
    }

    private boolean eK(String nick) {
        if (nick.trim().length() < 2) {
            t.n(this.mContext, "昵称不能小于2个字符");
            return false;
        } else if (nick.trim().length() > 8) {
            t.n(this.mContext, "昵称不能大于8个字符");
            return false;
        } else if (nick.matches("[0-9a-zA-Z一-龥].+")) {
            return true;
        } else {
            t.n(this.mContext, "昵称第一个字符不能为符号，表情。");
            return false;
        }
    }
}
