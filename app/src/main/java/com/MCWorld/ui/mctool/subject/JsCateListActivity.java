package com.MCWorld.ui.mctool.subject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.k;
import com.MCWorld.module.o;
import com.MCWorld.ui.itemadapter.js.JsDownAdapter;
import com.MCWorld.ui.itemadapter.map.DownAdapter;
import com.MCWorld.ui.mctool.ResourceCommonListLayout.a;

public class JsCateListActivity extends MapCateListActivity {
    protected void IM() {
        o.jf(this.bcK);
    }

    protected void onCreate(Bundle savedInstanceState) {
        kW(2);
        super.onCreate(savedInstanceState);
        ej("JS分类");
        this.aIQ.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ JsCateListActivity bcH;

            {
                this.bcH = this$0;
            }

            public void onClick(View arg0) {
                k.R(this.bcH);
            }
        });
    }

    public TabListResourceFragment ay(int id, int pos) {
        return TabListResourceFragment.aA(id, pos);
    }

    public DownAdapter az(int id, int pos) {
        return new JsDownAdapter(this);
    }

    public a kV(final int id) {
        if (id != -1) {
            return new a(this) {
                final /* synthetic */ JsCateListActivity bcH;

                public void Z(int tagId, int start, int count) {
                    o.a(tagId, this.bcH.bcC, id, start, count);
                }
            };
        }
        return new a(this) {
            final /* synthetic */ JsCateListActivity bcH;

            {
                this.bcH = this$0;
            }

            public void Z(int tagId, int start, int count) {
                o.b(tagId, this.bcH.bcC, start, count);
            }
        };
    }
}
