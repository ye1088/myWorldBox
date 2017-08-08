package com.MCWorld.ui.bbs;

import android.content.Context;
import android.os.Bundle;
import com.MCWorld.data.TagInfo;
import com.MCWorld.data.UserBaseInfo;
import com.MCWorld.data.topic.TopicItem;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.http.base.d;
import com.MCWorld.http.bbs.topic.j;
import com.MCWorld.module.aa;
import com.MCWorld.module.n;
import com.MCWorld.module.picture.b;
import com.MCWorld.r;
import com.MCWorld.t;
import com.MCWorld.utils.ab;
import com.MCWorld.widget.Constants;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class ModifyTopicActivity extends PublishTopicBaseActivity {
    public static final String aKv = "PUBLISH_POST_AUTHOR";
    private final String aKw = r.gO;
    private j aKx = new j();
    private UserBaseInfo aKy;
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ ModifyTopicActivity aKz;

        {
            this.aKz = this$0;
        }

        @MessageHandler(message = 2566)
        public void onUpdateForumPost(boolean success, String msg) {
            if (success) {
                this.aKz.setResult(-1);
                this.aKz.finish();
                return;
            }
            t.show_toast(this.aKz.mContext, msg);
        }
    };
    private Context mContext;
    private TopicItem sK;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        ej("修改话题");
        this.aKx.bb(2);
        HLog.verbose("ModifyTopicActivity", "onCreate", new Object[0]);
        if (savedInstanceState != null) {
            this.sK = (TopicItem) savedInstanceState.getParcelable(r.gO);
            this.aKy = (UserBaseInfo) savedInstanceState.getParcelable(aKv);
        } else {
            this.sK = (TopicItem) getIntent().getParcelableExtra(r.gO);
            this.aKy = (UserBaseInfo) getIntent().getParcelableExtra(aKv);
        }
        this.sH = this.sK.getTagid();
        this.aLu = (ArrayList) this.sK.getCategory().getTags();
        FQ();
        EventNotifyCenter.add(n.class, this.mCallback);
    }

    private void FQ() {
        this.aLv.setText(this.sK.getTitle());
        this.aLw.setText(this.sK.getDetail());
        for (String szUrl : this.sK.getImages()) {
            b pn = new b();
            pn.url = szUrl;
            try {
                String path = new URL(szUrl).getPath();
                if (path != null && path.length() > 1 && path.startsWith("/")) {
                    path = path.substring(1);
                }
                HLog.verbose("ModifyTopicActivity", "initUI fid(%s) szUrl(%s)", path, szUrl);
                pn.fid = path;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            this.aLY.e(pn);
        }
        if (this.aLu != null && this.aLu.size() > 0) {
            String tagName = null;
            Iterator it = this.aLu.iterator();
            while (it.hasNext()) {
                TagInfo tag = (TagInfo) it.next();
                if (0 == tag.getID()) {
                    it.remove();
                } else if (this.sH == tag.getID()) {
                    tagName = tag.getName();
                }
            }
            if (this.aLu.size() > 0) {
                this.aLP.setVisibility(0);
                if (tagName != null) {
                    this.aLP.setText(tagName);
                }
            }
        }
        if (this.aKy != null && this.aKy.userID != com.MCWorld.data.j.ep().getUserid()) {
            this.aLF.setVisibility(8);
            this.aLO.setVisibility(8);
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        HLog.verbose("ModifyTopicActivity", "onSaveInstanceState", new Object[0]);
        outState.putParcelable(r.gO, this.sK);
        outState.putParcelable(aKv, this.aKy);
        super.onSaveInstanceState(outState);
    }

    protected void ks(int index) {
        List<b> photos = this.aLY.getExistPhotos();
        boolean isUploadFinished = false;
        if (index < photos.size()) {
            b photo = (b) photos.get(index);
            if (photo.id == -1 || photo.url != null) {
                ks(index + 1);
            } else {
                this.aLT.setIndex(index);
                this.aLT.setFilename(photo.localPath);
                this.aLT.a(this);
                this.aLT.eY();
            }
        } else {
            isUploadFinished = true;
        }
        if (isUploadFinished) {
            FR();
        }
    }

    public void FR() {
        if (this.sm != Constants.bsT) {
            Iterator it;
            String title = this.aLv.getText().toString();
            String detail = this.aLw.getText().toString();
            this.aKx.getImages().clear();
            for (b photo : this.aLY.getPhotos()) {
                if (photo.fid != null) {
                    this.aKx.getImages().add(photo.fid);
                    HLog.verbose("ModifyTopicActivity", "fid(%s)", photo.fid);
                }
            }
            HashSet<Long> addRemindUserSet = new HashSet();
            if (!UtilsFunction.empty(this.aLZ)) {
                it = this.aLZ.iterator();
                while (it.hasNext()) {
                    addRemindUserSet.add(Long.valueOf(((UserBaseInfo) it.next()).userID));
                }
            }
            if (!UtilsFunction.empty(this.aMa)) {
                it = this.aMa.iterator();
                while (it.hasNext()) {
                    addRemindUserSet.remove(Long.valueOf(((UserBaseInfo) it.next()).userID));
                }
            }
            this.aKx.fB().clear();
            Iterator it2 = addRemindUserSet.iterator();
            while (it2.hasNext()) {
                this.aKx.fB().add(String.valueOf(((Long) it2.next()).longValue()));
            }
            this.aKx.x(this.sK.getPostID());
            this.aKx.D(this.sH);
            this.aKx.setTitle(title);
            this.aKx.setDetail(detail);
            this.aKx.a(this);
            this.aKx.eY();
            return;
        }
        Ga();
    }

    private void Ga() {
        String title = this.aLv.getText().toString();
        String detail = this.aLw.getText().toString();
        com.MCWorld.module.r resContent = new com.MCWorld.module.r();
        resContent.images.clear();
        for (b photo : this.aLY.getPhotos()) {
            if (photo.fid != null) {
                resContent.images.add(photo.fid);
            }
        }
        resContent.id = this.sK.getPostID();
        resContent.sm = this.sH;
        resContent.title = title;
        resContent.detail = detail;
        aa.DQ().b(resContent);
    }

    public void c(d response) {
        super.c(response);
        if (response.fe() == 2) {
            this.aIT.setEnabled(true);
            if (response.getStatus() == 1) {
                t.o(this, (String) response.getData());
                setResult(-1);
                finish();
                return;
            }
            t.n(this, ab.n(response.fg(), response.fh()));
        }
    }

    public void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
    }
}
