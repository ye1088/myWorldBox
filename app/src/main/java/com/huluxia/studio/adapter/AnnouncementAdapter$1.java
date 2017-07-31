package com.huluxia.studio.adapter;

import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.data.studio.b.a;
import com.huluxia.k;

class AnnouncementAdapter$1 implements OnClickListener {
    final /* synthetic */ a aFl;
    final /* synthetic */ AnnouncementAdapter aFm;

    AnnouncementAdapter$1(AnnouncementAdapter this$0, a aVar) {
        this.aFm = this$0;
        this.aFl = aVar;
    }

    public void onClick(View v) {
        k.a(AnnouncementAdapter.a(this.aFm), AnnouncementAdapter.b(this.aFm), AnnouncementAdapter.c(this.aFm), this.aFl);
    }
}
