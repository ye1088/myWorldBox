package com.MCWorld.ui.itemadapter.category;

import android.widget.TextView;
import com.MCWorld.bbs.b.m;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.http.base.d;
import com.MCWorld.http.base.f;
import com.MCWorld.t;
import com.MCWorld.ui.itemadapter.category.CategoryVoteItemAdapter.a;

class CategoryVoteItemAdapter$a$2 implements f {
    final /* synthetic */ a aSE;
    final /* synthetic */ TextView aSF;

    CategoryVoteItemAdapter$a$2(a this$1, TextView textView) {
        this.aSE = this$1;
        this.aSF = textView;
    }

    public void a(d response) {
    }

    public void b(d response) {
        CategoryVoteItemAdapter.a(this.aSE.aSD).cn(false);
        HLog.error("VoteOnClickListener.onFailure", response.fh() + "", new Object[0]);
    }

    public void c(d response) {
        if (response.getStatus() == 1) {
            t.o(CategoryVoteItemAdapter.a(this.aSE.aSD), "投票成功!");
            CategoryVoteItemAdapter.a(this.aSE.aSD).cn(false);
            this.aSF.setText(m.voted_btn);
            this.aSF.setEnabled(false);
            a.a(this.aSE).setIsVoted(1);
            a.a(this.aSE).setVoteCount(a.a(this.aSE).getVoteCount() + 1);
            this.aSE.aSD.notifyDataSetChanged();
        }
    }
}
