package com.huluxia.ui.itemadapter.topic;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.huluxia.bbs.b.m;

class TopicDetailItemAdapter$5 implements OnClickListener {
    final /* synthetic */ long aCH;
    boolean aWO = this.aWP;
    final /* synthetic */ boolean aWP;
    final /* synthetic */ TextView aWQ;
    final /* synthetic */ TextView aWR;
    final /* synthetic */ TextView aWS;
    final /* synthetic */ TopicDetailItemAdapter aXA;

    TopicDetailItemAdapter$5(TopicDetailItemAdapter this$0, boolean z, long j, TextView textView, TextView textView2, TextView textView3) {
        this.aXA = this$0;
        this.aWP = z;
        this.aCH = j;
        this.aWQ = textView;
        this.aWR = textView2;
        this.aWS = textView3;
    }

    public void onClick(View arg0) {
        this.aWO = !this.aWO;
        TopicDetailItemAdapter.l(this.aXA).put(Long.valueOf(this.aCH), Boolean.valueOf(this.aWO));
        if (this.aWO) {
            this.aWQ.setVisibility(8);
            this.aWR.setVisibility(0);
            this.aWS.setText(m.content_shrinkup);
            return;
        }
        this.aWQ.setVisibility(0);
        this.aWR.setVisibility(8);
        this.aWS.setText(m.content_spread);
    }
}
