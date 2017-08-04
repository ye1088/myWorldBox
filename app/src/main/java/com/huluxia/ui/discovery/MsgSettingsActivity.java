package com.huluxia.ui.discovery;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.huluxia.bbs.b.c;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.data.e;
import com.huluxia.data.j;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.module.account.a.a;
import com.huluxia.module.h;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseActivity;
import com.huluxia.utils.ah;
import com.simple.colorful.setter.k;

public class MsgSettingsActivity extends HTBaseActivity implements OnCheckedChangeListener {
    private static final String aRw = "EXTRA_NOTIFICATION";
    private static final String aRx = "EXTRA_SOUND";
    private static final String aRy = "EXTRA_VIBRATION";
    private static final String aRz = "EXTRA_ANTIANOY";
    private View aRA;
    private View aRB;
    private CheckBox aRC;
    private CheckBox aRD;
    private CheckBox aRE;
    private CheckBox aRF;
    private MsgSettingsActivity aRG;
    private CallbackHandler aRH = new CallbackHandler(this) {
        final /* synthetic */ MsgSettingsActivity aRI;

        {
            this.aRI = this$0;
        }

        @MessageHandler(message = 641)
        public void onCheckMsgNotification(boolean succ, a info) {
            if (succ && info != null) {
                this.aRI.aRC.setOnCheckedChangeListener(null);
                this.aRI.aRC.setChecked(info.isNotify());
                this.aRI.aRC.setOnCheckedChangeListener(this.aRI);
                this.aRI.aRD.setOnCheckedChangeListener(null);
                this.aRI.aRD.setChecked(info.isHarry());
                this.aRI.aRD.setOnCheckedChangeListener(this.aRI);
                this.aRI.aRE.setOnCheckedChangeListener(null);
                this.aRI.aRE.setChecked(info.isSound());
                this.aRI.aRE.setOnCheckedChangeListener(this.aRI);
                this.aRI.aRF.setOnCheckedChangeListener(null);
                this.aRI.aRF.setChecked(info.isVibration());
                this.aRI.aRF.setOnCheckedChangeListener(this.aRI);
                if (info.isNotify()) {
                    this.aRI.aRA.setVisibility(0);
                    this.aRI.aRB.setVisibility(0);
                    return;
                }
                this.aRI.aRA.setVisibility(8);
                this.aRI.aRB.setVisibility(8);
            }
        }

        @MessageHandler(message = 642)
        public void onSetMsgNotication(boolean succ, boolean isNotification, int type_id) {
            boolean z = true;
            if (succ) {
                if (type_id == 0) {
                    if (isNotification) {
                        this.aRI.aRA.setVisibility(0);
                        this.aRI.aRB.setVisibility(0);
                    } else {
                        this.aRI.aRA.setVisibility(8);
                        this.aRI.aRB.setVisibility(8);
                    }
                }
                if (j.ep().ey()) {
                    e msgRemind = new e();
                    msgRemind.A(this.aRI.aRC.isChecked());
                    msgRemind.C(this.aRI.aRE.isChecked());
                    msgRemind.x(this.aRI.aRF.isChecked());
                    msgRemind.B(this.aRI.aRD.isChecked());
                    ah.KZ().a(j.ep().getUserid(), msgRemind);
                    return;
                }
                return;
            }
            t.show_toast(this.aRI.aRG, "设置失败, 网络问题");
            CheckBox b;
            if (type_id == 1) {
                this.aRI.aRD.setOnCheckedChangeListener(null);
                b = this.aRI.aRD;
                if (isNotification) {
                    z = false;
                }
                b.setChecked(z);
                this.aRI.aRD.setOnCheckedChangeListener(this.aRI);
            } else if (type_id == 2) {
                this.aRI.aRE.setOnCheckedChangeListener(null);
                b = this.aRI.aRE;
                if (isNotification) {
                    z = false;
                }
                b.setChecked(z);
                this.aRI.aRE.setOnCheckedChangeListener(this.aRI);
            } else if (type_id == 3) {
                this.aRI.aRF.setOnCheckedChangeListener(null);
                b = this.aRI.aRF;
                if (isNotification) {
                    z = false;
                }
                b.setChecked(z);
                this.aRI.aRF.setOnCheckedChangeListener(this.aRI);
            } else {
                this.aRI.aRC.setOnCheckedChangeListener(null);
                b = this.aRI.aRC;
                if (isNotification) {
                    z = false;
                }
                b.setChecked(z);
                this.aRI.aRC.setOnCheckedChangeListener(this.aRI);
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.activity_message_settings);
        this.aRG = this;
        this.aIs.setVisibility(8);
        ej("消息设置");
        EventNotifyCenter.add(h.class, this.aRH);
        this.aRA = findViewById(g.ly_sound);
        this.aRB = findViewById(g.ly_vibration);
        this.aRC = (CheckBox) findViewById(g.msg_notification);
        this.aRE = (CheckBox) findViewById(g.msg_sound);
        this.aRF = (CheckBox) findViewById(g.vibration);
        this.aRD = (CheckBox) findViewById(g.antianoy);
        if (savedInstanceState != null) {
            this.aRC.setChecked(savedInstanceState.getBoolean(aRw));
            this.aRE.setChecked(savedInstanceState.getBoolean(aRx));
            this.aRF.setChecked(savedInstanceState.getBoolean(aRy));
            this.aRD.setChecked(savedInstanceState.getBoolean(aRz));
        } else if (j.ep().ey()) {
            e msgRemind = ah.KZ().bI();
            if (msgRemind != null) {
                this.aRC.setChecked(msgRemind.ei());
                this.aRE.setChecked(msgRemind.isSound());
                this.aRF.setChecked(msgRemind.isVibration());
                this.aRD.setChecked(msgRemind.ej());
            }
        }
        if (this.aRC.isChecked()) {
            this.aRA.setVisibility(0);
            this.aRB.setVisibility(0);
        } else {
            this.aRA.setVisibility(8);
            this.aRB.setVisibility(8);
        }
        this.aRC.setOnCheckedChangeListener(this);
        this.aRE.setOnCheckedChangeListener(this);
        this.aRF.setOnCheckedChangeListener(this);
        this.aRD.setOnCheckedChangeListener(this);
        if (j.ep().ey()) {
            com.huluxia.module.account.a.DU().DX();
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(aRw, this.aRC.isChecked());
        outState.putBoolean(aRx, this.aRE.isChecked());
        outState.putBoolean(aRy, this.aRF.isChecked());
        outState.putBoolean(aRz, this.aRD.isChecked());
        super.onSaveInstanceState(outState);
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.aRH);
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        if (id == g.msg_notification) {
            cE(isChecked);
        } else if (id == g.msg_sound) {
            cG(isChecked);
        } else if (id == g.vibration) {
            cH(isChecked);
        } else if (id == g.antianoy) {
            cF(isChecked);
        }
    }

    private void cE(boolean isChecked) {
        boolean z = false;
        if (j.ep().ey()) {
            com.huluxia.module.account.a.DU().a(isChecked, 0);
            return;
        }
        if (this.aRC != null) {
            this.aRC.setOnCheckedChangeListener(null);
            CheckBox checkBox = this.aRC;
            if (!isChecked) {
                z = true;
            }
            checkBox.setChecked(z);
            this.aRC.setOnCheckedChangeListener(this);
        }
        t.an(this.aRG);
    }

    private void cF(boolean isChecked) {
        boolean z = true;
        if (j.ep().ey()) {
            com.huluxia.module.account.a.DU().a(isChecked, 1);
            return;
        }
        if (this.aRD != null) {
            this.aRD.setOnCheckedChangeListener(null);
            CheckBox checkBox = this.aRD;
            if (isChecked) {
                z = false;
            }
            checkBox.setChecked(z);
            this.aRD.setOnCheckedChangeListener(this);
        }
        t.an(this.aRG);
    }

    private void cG(boolean isChecked) {
        if (j.ep().ey()) {
            com.huluxia.module.account.a.DU().a(isChecked, 2);
            return;
        }
        if (this.aRE != null) {
            this.aRE.setOnCheckedChangeListener(null);
            this.aRE.setChecked(!isChecked);
            this.aRE.setOnCheckedChangeListener(this);
        }
        t.an(this.aRG);
    }

    private void cH(boolean isChecked) {
        if (j.ep().ey()) {
            com.huluxia.module.account.a.DU().a(isChecked, 3);
            return;
        }
        if (this.aRF != null) {
            this.aRF.setOnCheckedChangeListener(null);
            this.aRF.setChecked(!isChecked);
            this.aRF.setOnCheckedChangeListener(this);
        }
        t.an(this.aRG);
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        k setter = new com.simple.colorful.setter.j((ViewGroup) findViewById(16908290));
        setter.bf(g.split, c.splitColor).bf(g.split_block, c.splitColorDim).bf(g.block_split_top, c.splitColor).bf(g.block_split_bottom, c.splitColor).bf(g.view_divider, c.splitColorDim);
        builder.a(setter).aY(g.root_view, c.splitColorDim).aY(g.ly_child, c.backgroundDefault).aY(g.tv_message, c.splitColorDim).ba(g.tv_message, c.textColorGreen).ba(g.tv_notification, 16842806).bb(g.msg_notification, c.drawableCompoundButtonSetting).ba(g.tv_sound, 16842806).bb(g.msg_sound, c.drawableCompoundButtonSetting).ba(g.tv_vibration, 16842806).bb(g.vibration, c.drawableCompoundButtonSetting).ba(g.tv_antianoy, 16842806).bb(g.antianoy, c.drawableCompoundButtonSetting).ba(g.tv_antianoy_tip, 16843282);
    }

    protected void kj(int themeId) {
        super.kj(themeId);
    }
}
