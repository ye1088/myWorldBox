package com.huluxia.ui.bbs;

import android.content.Context;
import android.os.Bundle;
import com.huluxia.data.TagInfo;
import com.huluxia.data.j;
import com.huluxia.data.topic.c;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.http.base.d;
import com.huluxia.http.bbs.topic.m;
import com.huluxia.module.aa;
import com.huluxia.module.n;
import com.huluxia.module.picture.b;
import com.huluxia.module.r;
import com.huluxia.t;
import com.huluxia.utils.ab;
import com.huluxia.utils.at;
import com.huluxia.utils.o;
import com.huluxia.widget.Constants;
import com.simple.colorful.a.a;
import com.tencent.mm.sdk.platformtools.SpecilApiUtil;
import java.util.ArrayList;
import java.util.Iterator;

public class PublishTopicActivity extends PublishTopicBaseActivity {
    private m aLl = new m();
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ PublishTopicActivity aLm;

        {
            this.aLm = this$0;
        }

        @MessageHandler(message = 2565)
        public void onUploadForumPost(boolean success, String msg) {
            if (success) {
                t.o(this.aLm.mContext, "发布成功");
            } else {
                t.download_toast(this.aLm.mContext, msg);
            }
            this.aLm.finish();
        }
    };
    private Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ej("发布新话题");
        this.aLl.bb(2);
        this.mContext = this;
        EventNotifyCenter.add(n.class, this.mCallback);
    }

    public void FR() {
        if (this.sm != Constants.bsT) {
            String title = this.aLv.getText().toString();
            String detail = this.aLw.getText().toString();
            String patcha = this.aLB.getText().toString();
            this.aLl.getImages().clear();
            for (b photo : this.aLY.getExistPhotos()) {
                if (photo.fid != null) {
                    this.aLl.getImages().add(photo.fid);
                    HLog.verbose("PublishTopicActivity", "fid(%s)", photo.fid);
                }
            }
            if (this.sm == 66) {
                StringBuilder sb = new StringBuilder();
                sb.append("手机型号：").append(o.getModel()).append(SpecilApiUtil.LINE_SEP);
                sb.append("软件版本：").append(o.ff()).append(SpecilApiUtil.LINE_SEP);
                sb.append("安卓版本：").append(o.getVersion()).append(SpecilApiUtil.LINE_SEP);
                sb.append("运行内存：").append(o.u(this)).append(SpecilApiUtil.LINE_SEP);
                sb.append("问题描述：").append(detail);
                detail = sb.toString();
            }
            this.aLl.v(this.sm);
            this.aLl.D(this.sH);
            this.aLl.setTitle(title);
            this.aLl.setDetail(detail);
            this.aLl.aM(patcha);
            this.aLl.setRemindUsers(this.aLZ);
            this.aLl.a(this);
            this.aLl.eY();
            return;
        }
        Ga();
    }

    public void Ga() {
        String title = this.aLv.getText().toString();
        String detail = this.aLw.getText().toString();
        String patcha = this.aLB.getText().toString();
        this.aLl.getImages().clear();
        r resContent = new r();
        resContent.images.clear();
        for (b photo : this.aLY.getExistPhotos()) {
            if (photo.fid != null) {
                resContent.images.add(photo.fid);
            }
        }
        resContent.sm = this.sH;
        resContent.title = title;
        resContent.detail = detail;
        resContent.sx = patcha;
        aa.DQ().a(resContent);
    }

    public void c(d response) {
        super.c(response);
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
            if (response.fg() == 106) {
                Gk();
            }
        }
    }

    protected String FS() {
        return "PublishTopic";
    }

    protected void a(c draft) {
        draft.aC(this.aLw.getText().toString());
        draft.setTitle(this.aLv.getText().toString());
        draft.n(this.sH);
        draft.f(this.aLY.getPhotos());
        draft.setRemindUsers(this.aLZ);
        draft.o(j.ep().getUserid());
    }

    protected void b(c draft) {
        if (draft != null && draft.eH() == j.ep().getUserid()) {
            String text = draft.eE();
            if (text != null) {
                this.aLw.setText(com.huluxia.widget.emoInput.d.Ou().c(this.aLw.getContext(), text, at.dipToPx(this, 22), 0));
                this.aLw.setSelection(text.length());
            }
            this.aLv.setText(draft.getTitle());
            long tagId = draft.eG();
            Iterator it = this.aLu.iterator();
            while (it.hasNext()) {
                TagInfo tag = (TagInfo) it.next();
                if (tag.getID() == tagId) {
                    this.sH = tagId;
                    this.aLP.setText(tag.getName());
                }
            }
            if (!UtilsFunction.empty(draft.getRemindUsers()) && UtilsFunction.empty(this.aLZ)) {
                this.aLZ = (ArrayList) draft.getRemindUsers();
            }
            if (UtilsFunction.empty(this.aLZ) && UtilsFunction.empty(this.aMa)) {
                this.aLF.setVisibility(8);
            } else {
                this.aLF.setVisibility(0);
            }
            if (!UtilsFunction.empty(draft.getPhotos())) {
                this.aLY.e(draft.getPhotos(), true);
                this.aLL.setVisibility(0);
                this.aLN.setVisibility(8);
            }
        }
    }

    protected void a(a builder) {
        super.a(builder);
        builder.a(this.aLP, com.huluxia.bbs.b.c.textColorGreen);
    }

    public void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
    }
}
