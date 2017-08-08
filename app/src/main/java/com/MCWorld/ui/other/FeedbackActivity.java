package com.MCWorld.ui.other;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.MCWorld.bbs.b.c;
import com.MCWorld.bbs.b.g;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.base.widget.dialog.DialogManager;
import com.MCWorld.http.other.f;
import com.MCWorld.module.feedback.a;
import com.MCWorld.module.h;
import com.MCWorld.module.picture.b;
import com.MCWorld.t;
import com.MCWorld.ui.bbs.PublishTopicBaseActivity;
import com.MCWorld.utils.ab;
import com.MCWorld.utils.o;
import com.MCWorld.widget.Constants.FeedBackType;
import com.simple.colorful.d;
import com.xiaomi.mipush.sdk.MiPushClient;
import org.json.JSONObject;

public class FeedbackActivity extends PublishTopicBaseActivity {
    private RadioGroup aZc;
    private f bda = new f();
    private RadioGroup bdb;
    private DialogManager bdc;
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ FeedbackActivity bdd;

        {
            this.bdd = this$0;
        }

        @MessageHandler(message = 547)
        public void onSubmitLog(boolean succ, String response, Object context) {
            if (context.equals(this.bdd)) {
                String logUrl = "";
                if (succ) {
                    try {
                        logUrl = new JSONObject(response).getString("url");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                this.bdd.eI(logUrl);
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventNotifyCenter.add(h.class, this.mCallback);
        ej("意见反馈");
        this.bda.bb(2);
        this.aZc = (RadioGroup) findViewById(g.radios_bug);
        this.bdb = (RadioGroup) findViewById(g.radios_contact);
        findViewById(g.title_Text).setVisibility(8);
        findViewById(g.img_emotion).setVisibility(8);
        findViewById(g.ly_contact).setVisibility(0);
        findViewById(g.contact_Text).setVisibility(0);
        findViewById(g.contact_split).setVisibility(0);
        findViewById(g.img_remind).setVisibility(8);
        findViewById(g.header_tips).setVisibility(0);
        this.bdb.setVisibility(0);
        this.bdc = new DialogManager(this);
        this.aIT.setTextColor(d.getColor(this, c.backText));
    }

    protected void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
    }

    public void FR() {
        this.bdc.showProgressDialog(this, "正在提交...", false);
        a.DZ().as(this);
    }

    public void eI(String logUrl) {
        String detail = this.aLw.getText().toString();
        String contact = this.aLx.getText().toString();
        this.bda.getImages().clear();
        for (b photo : this.aLY.getExistPhotos()) {
            if (photo.fid != null) {
                this.bda.getImages().add(photo.fid);
            }
        }
        this.bda.setFlag(FeedBackType.MCBUG.Value());
        int id = ((RadioButton) findViewById(this.bdb.getCheckedRadioButtonId())).getId();
        if (id == g.rb_wx) {
            contact = "weixin:" + contact;
        } else if (id == g.rb_mobile) {
            contact = "mobile:" + contact;
        } else {
            contact = "qq:" + contact;
        }
        this.bda.setExt(s(contact));
        if (!UtilsFunction.empty((CharSequence) logUrl)) {
            detail = detail + "   <a_isRightVersion href=" + logUrl + ">反馈日志</a_isRightVersion>";
        }
        this.bda.setText(detail);
        this.bda.a(this);
        this.bda.eY();
    }

    public void c(com.MCWorld.http.base.d response) {
        super.c(response);
        this.bdc.dismissDialog();
        if (response.fe() == 2) {
            this.aIT.setEnabled(true);
            if (response.getStatus() == 1) {
                setResult(-1);
                cv(true);
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
        sb.append(MiPushClient.ACCEPT_TIME_SEPARATOR).append(com.MCWorld.http.base.c.ff());
        return sb.toString();
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        builder.ba(g.tv_contact, 16842808).ba(g.rb_qq, 16842808).ba(g.rb_wx, 16842808).ba(g.rb_mobile, 16842808).ba(g.contact_Text, 16842806).ba(g.bug, 16842808).ba(g.suggestion, 16842808).bd(g.contact_Text, 16842906).aY(g.contact_split, c.splitColor);
    }
}
