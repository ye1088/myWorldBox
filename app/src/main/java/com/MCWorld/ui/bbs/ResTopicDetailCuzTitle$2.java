package com.MCWorld.ui.bbs;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.MCWorld.data.map.f.a;

class ResTopicDetailCuzTitle$2 implements OnItemClickListener {
    final /* synthetic */ ResTopicDetailCuzTitle aOk;
    final /* synthetic */ a aOl;

    ResTopicDetailCuzTitle$2(ResTopicDetailCuzTitle this$0, a aVar) {
        this.aOk = this$0;
        this.aOl = aVar;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        ResTopicDetailCuzTitle.a(this.aOk, this.aOl, position % this.aOl.resourceList.size());
    }
}
