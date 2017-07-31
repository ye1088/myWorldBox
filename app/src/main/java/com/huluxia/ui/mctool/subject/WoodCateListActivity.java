package com.huluxia.ui.mctool.subject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.k;
import com.huluxia.module.o;
import com.huluxia.ui.itemadapter.map.DownAdapter;
import com.huluxia.ui.itemadapter.wood.WoodDownAdapter;
import com.huluxia.ui.mctool.ResourceCommonListLayout.a;

public class WoodCateListActivity extends MapCateListActivity {
    protected void IM() {
        o.jg(this.bcK);
    }

    protected void onCreate(Bundle savedInstanceState) {
        kW(3);
        super.onCreate(savedInstanceState);
        ej("材质分类");
        this.aIQ.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ WoodCateListActivity bcX;

            {
                this.bcX = this$0;
            }

            public void onClick(View arg0) {
                k.S(this.bcX);
            }
        });
    }

    public TabListResourceFragment ay(int id, int pos) {
        return TabListResourceFragment.aA(id, pos);
    }

    public DownAdapter az(int id, int pos) {
        return new WoodDownAdapter(this);
    }

    public a kV(final int id) {
        if (id != -1) {
            return new a(this) {
                final /* synthetic */ WoodCateListActivity bcX;

                public void Z(int tagId, int start, int count) {
                    o.b(tagId, this.bcX.bcC, id, start, count);
                }
            };
        }
        return new a(this) {
            final /* synthetic */ WoodCateListActivity bcX;

            {
                this.bcX = this$0;
            }

            public void Z(int tagId, int start, int count) {
                o.e(tagId, this.bcX.bcC, start, count);
            }
        };
    }
}
