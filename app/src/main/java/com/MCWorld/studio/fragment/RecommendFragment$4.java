package com.MCWorld.studio.fragment;

import android.support.v4.util.SparseArrayCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.MCWorld.data.studio.c.a;
import com.MCWorld.k;
import com.MCWorld.t;
import com.MCWorld.ui.mctool.d;

class RecommendFragment$4 implements OnItemClickListener {
    final /* synthetic */ RecommendFragment aGD;

    RecommendFragment$4(RecommendFragment this$0) {
        this.aGD = this$0;
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        a bannerItem = (a) parent.getAdapter().getItem(position);
        switch (bannerItem.type_id) {
            case 1:
                switch (bannerItem.mc_type) {
                    case 2:
                        k.c(RecommendFragment.a(this.aGD), bannerItem.id, bannerItem.postID);
                        return;
                    case 3:
                        k.d(RecommendFragment.a(this.aGD), bannerItem.id, bannerItem.postID);
                        return;
                    case 4:
                        k.e(RecommendFragment.a(this.aGD), bannerItem.id, bannerItem.postID);
                        return;
                    default:
                        k.a(RecommendFragment.a(this.aGD), bannerItem.id, bannerItem.postID, "default");
                        return;
                }
            case 2:
                t.f(RecommendFragment.a(this.aGD), bannerItem.openUrl, bannerItem.title);
                return;
            case 3:
                SparseArrayCompat<String> CLASS_NAME = new SparseArrayCompat();
                CLASS_NAME.put(1, "map_subject");
                CLASS_NAME.put(2, "js_subject");
                CLASS_NAME.put(3, "skin_subject");
                CLASS_NAME.put(4, "wood_subject");
                d.b(RecommendFragment.a(this.aGD), (String) CLASS_NAME.get(bannerItem.mc_type), (int) bannerItem.id, bannerItem.title);
                return;
            default:
                return;
        }
    }
}
