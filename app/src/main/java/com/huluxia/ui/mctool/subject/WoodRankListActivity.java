package com.huluxia.ui.mctool.subject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.k;
import com.huluxia.module.o;
import com.huluxia.ui.itemadapter.map.DownAdapter;
import com.huluxia.ui.itemadapter.wood.WoodDownAdapter;
import com.huluxia.ui.mctool.ResourceCommonListLayout.a;

public class WoodRankListActivity extends MapRankListActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ej("材质");
        this.aIQ.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ WoodRankListActivity bcY;

            {
                this.bcY = this$0;
            }

            public void onClick(View arg0) {
                k.S(this.bcY);
            }
        });
        k(3, "click_wood_more_tab");
    }

    public TabListResourceFragment ay(int id, int pos) {
        return TabListResourceFragment.aA(id, pos);
    }

    public DownAdapter az(int id, int pos) {
        return new WoodDownAdapter(this);
    }

    public a kV(int id) {
        if (id == 1) {
            return new a(this) {
                final /* synthetic */ WoodRankListActivity bcY;

                {
                    this.bcY = this$0;
                }

                public void Z(int tagId, int start, int count) {
                    o.d(tagId, this.bcY.bcC, start, count);
                }
            };
        }
        if (id == 2) {
            return new a(this) {
                final /* synthetic */ WoodRankListActivity bcY;

                {
                    this.bcY = this$0;
                }

                public void Z(int tagId, int start, int count) {
                    o.e(tagId, this.bcY.bcC, start, count);
                }
            };
        }
        if (id == 3) {
            return new a(this) {
                final /* synthetic */ WoodRankListActivity bcY;

                {
                    this.bcY = this$0;
                }

                public void Z(int tagId, int start, int count) {
                    o.f(tagId, this.bcY.bcC, start, count);
                }
            };
        }
        return null;
    }
}
