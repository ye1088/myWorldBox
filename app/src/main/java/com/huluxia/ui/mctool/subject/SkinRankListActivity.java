package com.huluxia.ui.mctool.subject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.k;
import com.huluxia.module.o;
import com.huluxia.ui.itemadapter.map.DownAdapter;
import com.huluxia.ui.itemadapter.skin.SkinDownAdapter;
import com.huluxia.ui.mctool.ResourceCommonListLayout.a;

public class SkinRankListActivity extends MapRankListActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ej("皮肤");
        this.aIQ.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ SkinRankListActivity bcS;

            {
                this.bcS = this$0;
            }

            public void onClick(View arg0) {
                k.T(this.bcS);
            }
        });
        k(4, "click_skin_more_tab");
    }

    public TabListResourceFragment ay(int id, int pos) {
        return TabListResourceFragment.aA(id, pos);
    }

    public DownAdapter az(int id, int pos) {
        return new SkinDownAdapter(this);
    }

    public a kV(int id) {
        if (id == 1) {
            return new a(this) {
                final /* synthetic */ SkinRankListActivity bcS;

                {
                    this.bcS = this$0;
                }

                public void Z(int tagId, int start, int count) {
                    o.g(tagId, this.bcS.bcC, start, count);
                }
            };
        }
        if (id == 2) {
            return new a(this) {
                final /* synthetic */ SkinRankListActivity bcS;

                {
                    this.bcS = this$0;
                }

                public void Z(int tagId, int start, int count) {
                    o.h(tagId, this.bcS.bcC, start, count);
                }
            };
        }
        if (id == 3) {
            return new a(this) {
                final /* synthetic */ SkinRankListActivity bcS;

                {
                    this.bcS = this$0;
                }

                public void Z(int tagId, int start, int count) {
                    o.i(tagId, this.bcS.bcC, start, count);
                }
            };
        }
        return null;
    }
}
