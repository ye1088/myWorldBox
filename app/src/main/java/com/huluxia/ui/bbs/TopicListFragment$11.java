package com.huluxia.ui.bbs;

import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.b;
import com.huluxia.data.j;
import com.huluxia.i;
import com.huluxia.t;

class TopicListFragment$11 implements OnClickListener {
    final /* synthetic */ TopicListFragment aQp;

    TopicListFragment$11(TopicListFragment this$0) {
        this.aQp = this$0;
    }

    public void onClick(View v) {
        if (!j.ep().ey()) {
            t.an(TopicListFragment.e(this.aQp));
        } else if (68 == TopicListFragment.f(this.aQp).getCategoryID() || 69 == TopicListFragment.f(this.aQp).getCategoryID()) {
            i callback = b.bq().bs();
            if (callback != null) {
                callback.e(TopicListFragment.e(this.aQp));
            }
        } else {
            TopicListFragment.g(this.aQp);
        }
    }
}
