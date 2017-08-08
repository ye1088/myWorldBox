package com.MCWorld.ui.other;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.m;
import com.MCWorld.http.base.c;
import com.MCWorld.http.base.d;
import com.MCWorld.http.other.f;
import com.MCWorld.module.picture.b;
import com.MCWorld.t;
import com.MCWorld.ui.bbs.PublishTopicBaseActivity;
import com.MCWorld.utils.ab;
import com.MCWorld.utils.at;
import com.MCWorld.utils.o;
import com.MCWorld.widget.Constants.FeedBackType;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.HashSet;
import java.util.Set;

public class FeedbackGameActivity extends PublishTopicBaseActivity implements OnCheckedChangeListener {
    private f bda = new f();
    private RadioGroup bdb;
    private CheckBox bde;
    private CheckBox bdf;
    private CheckBox bdg;
    private CheckBox bdh;
    private CheckBox bdi;
    private CheckBox bdj;
    private CheckBox bdk;
    private CheckBox bdl;
    private Set<String> bdm = new HashSet();
    private FeedbackGameActivity bdn;
    private String id;
    private String name;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.bdn = this;
        this.id = getIntent().getStringExtra("id");
        this.name = getIntent().getStringExtra("name");
        ej("一键反馈");
        this.bda.bb(2);
        this.bdb = (RadioGroup) findViewById(g.radios_contact);
        findViewById(g.title_Text).setVisibility(8);
        findViewById(g.img_emotion).setVisibility(8);
        findViewById(g.ly_contact).setVisibility(0);
        findViewById(g.contact_Text).setVisibility(0);
        findViewById(g.contact_split).setVisibility(0);
        findViewById(g.ly_gamebug).setVisibility(0);
        this.bdb.setVisibility(0);
        this.bde = (CheckBox) findViewById(g.cb_privacy);
        this.bdf = (CheckBox) findViewById(g.cb_download_fail);
        this.bdg = (CheckBox) findViewById(g.cb_install_fail);
        this.bdh = (CheckBox) findViewById(g.cb_old_version);
        this.bdi = (CheckBox) findViewById(g.cb_update_fail);
        this.bdj = (CheckBox) findViewById(g.cb_fare);
        this.bdk = (CheckBox) findViewById(g.cb_poor_quality);
        this.bdl = (CheckBox) findViewById(g.cb_describle);
        this.bde.setOnCheckedChangeListener(this);
        this.bdf.setOnCheckedChangeListener(this);
        this.bdg.setOnCheckedChangeListener(this);
        this.bdh.setOnCheckedChangeListener(this);
        this.bdi.setOnCheckedChangeListener(this);
        this.bdj.setOnCheckedChangeListener(this);
        this.bdk.setOnCheckedChangeListener(this);
        this.bdl.setOnCheckedChangeListener(this);
        int dip = 5;
        if (VERSION.SDK_INT <= 16) {
            dip = 5 + 18;
        }
        int px = at.dipToPx(this.bdn, dip);
        a(this.bde, px);
        a(this.bdf, px);
        a(this.bdg, px);
        a(this.bdh, px);
        a(this.bdi, px);
        a(this.bdj, px);
        a(this.bdk, px);
        a(this.bdl, px);
    }

    private void a(CheckBox cb, int px) {
        cb.setPadding(px, 0, 0, 0);
    }

    protected void Gm() {
        String contact = this.aLx.getText().toString();
        String detail = this.aLw.getText().toString();
        if (this.aLu != null && this.aLu.size() > 0 && this.sH == 0) {
            t.n(this, "未选择标签");
        } else if (this.aLx.getVisibility() == 0 && (contact.trim().length() < 5 || contact.trim().length() > 50)) {
            t.n(this, "联系方式为5到50个字符。准确填写可以方便我们更好地为您解决问题。");
        } else if (detail.trim().length() == 0 && IO() == null) {
            t.n(this, "至少选择一种问题类型或者填写内容。");
        } else if (this.aLH.getVisibility() != 0 || this.aLB.getText().toString().length() > 1) {
            this.aIT.setEnabled(false);
            at.hideInputMethod(this.aLw);
            ks(0);
        } else {
            t.n(this, "验证码不能为空");
        }
    }

    public void FR() {
        String detail = this.aLw.getText().toString();
        String contact = this.aLx.getText().toString();
        this.bda.getImages().clear();
        for (b photo : this.aLY.getExistPhotos()) {
            if (photo.fid != null) {
                this.bda.getImages().add(photo.fid);
            }
        }
        this.bda.setFlag(FeedBackType.GAMEBUG.Value());
        int id = ((RadioButton) findViewById(this.bdb.getCheckedRadioButtonId())).getId();
        if (id == g.rb_wx) {
            contact = "weixin:" + contact;
        } else if (id == g.rb_mobile) {
            contact = "mobile:" + contact;
        } else {
            contact = "qq:" + contact;
        }
        this.bda.setExt(s(contact));
        this.bda.setText(detail.trim().length() == 0 ? "内容未填写" : detail.trim());
        this.bda.a(this);
        this.bda.eY();
    }

    public void c(d response) {
        super.c(response);
        if (response.fe() == 2) {
            this.aIT.setEnabled(true);
            if (response.getStatus() == 1) {
                setResult(-1);
                if (response.getCode() == 201) {
                    g((String) response.getData(), true);
                    return;
                }
                t.o(this, (String) response.getData());
                finish();
                return;
            }
            g(ab.n(response.fg(), response.fh()), false);
        }
    }

    private String s(String contact) {
        StringBuffer sb = new StringBuffer();
        sb.append(o.getModel());
        sb.append(MiPushClient.ACCEPT_TIME_SEPARATOR).append(o.getVersion());
        sb.append(MiPushClient.ACCEPT_TIME_SEPARATOR).append(contact);
        sb.append(MiPushClient.ACCEPT_TIME_SEPARATOR).append(c.ff());
        sb.append(MiPushClient.ACCEPT_TIME_SEPARATOR).append(this.id).append("_").append(this.name);
        String bug = IO();
        if (bug != null) {
            sb.append(MiPushClient.ACCEPT_TIME_SEPARATOR).append(bug);
        }
        return sb.toString();
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String bug;
        int id = buttonView.getId();
        if (id == g.cb_privacy) {
            bug = this.bdn.getResources().getString(m.bug_privacy);
        } else if (id == g.cb_download_fail) {
            bug = this.bdn.getResources().getString(m.bug_download);
        } else if (id == g.cb_install_fail) {
            bug = this.bdn.getResources().getString(m.bug_install);
        } else if (id == g.cb_old_version) {
            bug = this.bdn.getResources().getString(m.bug_version);
        } else if (id == g.cb_update_fail) {
            bug = this.bdn.getResources().getString(m.bug_update);
        } else if (id == g.cb_fare) {
            bug = this.bdn.getResources().getString(m.bug_fare);
        } else if (id == g.cb_poor_quality) {
            bug = this.bdn.getResources().getString(m.bug_quality);
        } else {
            bug = this.bdn.getResources().getString(m.bug_describe);
        }
        if (isChecked) {
            this.bdm.add(bug);
        } else {
            this.bdm.remove(bug);
        }
    }

    private String IO() {
        if (this.bdm.isEmpty()) {
            return null;
        }
        StringBuilder sbBug = new StringBuilder();
        for (String bug : this.bdm) {
            sbBug.append(bug).append("+");
        }
        return sbBug.toString();
    }
}
