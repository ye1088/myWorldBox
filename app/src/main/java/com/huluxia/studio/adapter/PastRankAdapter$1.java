package com.huluxia.studio.adapter;

import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.studio.utils.b$a;
import hlx.ui.a;

class PastRankAdapter$1 implements OnClickListener {
    final /* synthetic */ b$a aFC;
    final /* synthetic */ PastRankAdapter aFD;

    PastRankAdapter$1(PastRankAdapter this$0, b$a com_huluxia_studio_utils_b_a) {
        this.aFD = this$0;
        this.aFC = com_huluxia_studio_utils_b_a;
    }

    public void onClick(View v) {
        a.a(PastRankAdapter.a(this.aFD), true, this.aFC.month + "月", this.aFC.aGW);
    }
}
