package com.MCWorld.ui.itemadapter.topic;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.MCWorld.bbs.b.m;

class ResTopicDetailItemAdapter$7 implements OnClickListener {
    final /* synthetic */ long aCH;
    final /* synthetic */ ResTopicDetailItemAdapter aWL;
    boolean aWO = this.aWP;
    final /* synthetic */ boolean aWP;
    final /* synthetic */ TextView aWQ;
    final /* synthetic */ TextView aWR;
    final /* synthetic */ TextView aWS;

    ResTopicDetailItemAdapter$7(ResTopicDetailItemAdapter this$0, boolean z, long j, TextView textView, TextView textView2, TextView textView3) {
        this.aWL = this$0;
        this.aWP = z;
        this.aCH = j;
        this.aWQ = textView;
        this.aWR = textView2;
        this.aWS = textView3;
    }

    public void onClick(View arg0) {
        this.aWO = !this.aWO;
        ResTopicDetailItemAdapter.f(this.aWL).put(Long.valueOf(this.aCH), Boolean.valueOf(this.aWO));
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
