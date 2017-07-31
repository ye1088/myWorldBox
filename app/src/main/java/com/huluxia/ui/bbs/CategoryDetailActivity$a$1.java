package com.huluxia.ui.bbs;

import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.data.UserBaseInfo;
import com.huluxia.t;
import com.huluxia.ui.bbs.CategoryDetailActivity.a;

class CategoryDetailActivity$a$1 implements OnClickListener {
    final /* synthetic */ a aJV;

    CategoryDetailActivity$a$1(a this$1) {
        this.aJV = this$1;
    }

    public void onClick(View v) {
        UserBaseInfo info = (UserBaseInfo) v.getTag();
        if (info == null) {
            a.a(this.aJV);
        } else {
            t.a(a.b(this.aJV), info.userID, info);
        }
    }
}
