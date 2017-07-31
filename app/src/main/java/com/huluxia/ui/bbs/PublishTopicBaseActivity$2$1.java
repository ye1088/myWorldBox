package com.huluxia.ui.bbs;

import android.widget.Toast;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.ui.bbs.PublishTopicBaseActivity.2;
import com.huluxia.video.camera.VideoLibLoader.a;

class PublishTopicBaseActivity$2$1 implements a {
    final /* synthetic */ 2 aMj;

    PublishTopicBaseActivity$2$1(2 this$1) {
        this.aMj = this$1;
    }

    public void cw(boolean succ) {
        HLog.info("PublishTopicBaseActivity", "2 load video plugin succ " + succ, new Object[0]);
        PublishTopicBaseActivity.e(this.aMj.aMg).dismissDialog();
        if (this.aMj.aMh) {
            if (succ) {
                Toast.makeText(this.aMj.aMg, "加载视频插件失败", 0).show();
            } else {
                this.aMj.val$runnable.run();
            }
        } else if (succ) {
            Toast.makeText(this.aMj.aMg, "加载视频插件失败", 0).show();
        } else {
            this.aMj.val$runnable.run();
        }
    }
}
