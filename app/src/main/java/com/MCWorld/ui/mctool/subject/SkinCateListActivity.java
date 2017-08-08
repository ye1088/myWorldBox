package com.MCWorld.ui.mctool.subject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.k;
import com.MCWorld.module.o;
import com.MCWorld.ui.itemadapter.map.DownAdapter;
import com.MCWorld.ui.itemadapter.skin.SkinDownAdapter;
import com.MCWorld.ui.mctool.ResourceCommonListLayout.a;

public class SkinCateListActivity extends MapCateListActivity {
    protected void IM() {
        o.jh(this.bcK);
    }

    protected void onCreate(Bundle savedInstanceState) {
        kW(4);
        super.onCreate(savedInstanceState);
        ej("皮肤分类");
        this.aIQ.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ SkinCateListActivity bcR;

            {
                this.bcR = this$0;
            }

            public void onClick(View arg0) {
                k.T(this.bcR);
            }
        });
    }

    public TabListResourceFragment ay(int id, int pos) {
        return TabListResourceFragment.aA(id, pos);
    }

    public DownAdapter az(int id, int pos) {
        return new SkinDownAdapter(this);
    }

    public a kV(final int id) {
        if (id != -1) {
            return new a(this) {
                final /* synthetic */ SkinCateListActivity bcR;

                public void Z(int tagId, int start, int count) {
                    o.c(tagId, this.bcR.bcC, id, start, count);
                }
            };
        }
        return new a(this) {
            final /* synthetic */ SkinCateListActivity bcR;

            {
                this.bcR = this$0;
            }

            public void Z(int tagId, int start, int count) {
                o.h(tagId, this.bcR.bcC, start, count);
            }
        };
    }
}
