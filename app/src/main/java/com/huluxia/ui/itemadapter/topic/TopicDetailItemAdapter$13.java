package com.huluxia.ui.itemadapter.topic;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import com.huluxia.framework.base.log.HLog;

class TopicDetailItemAdapter$13 implements OnCompletionListener {
    final /* synthetic */ TopicDetailItemAdapter aXA;

    TopicDetailItemAdapter$13(TopicDetailItemAdapter this$0) {
        this.aXA = this$0;
    }

    public void onCompletion(MediaPlayer mp) {
        HLog.debug("TopicDetailItemAdapter", "video view complete", new Object[0]);
        TopicDetailItemAdapter.d(this.aXA).setVisibility(8);
        TopicDetailItemAdapter.f(this.aXA).setVisibility(0);
        TopicDetailItemAdapter.f(this.aXA).setImageBitmap(TopicDetailItemAdapter.g(this.aXA));
        TopicDetailItemAdapter.h(this.aXA).setVisibility(0);
        TopicDetailItemAdapter.a(this.aXA, 0);
        TopicDetailItemAdapter.a(this.aXA, false);
    }
}
