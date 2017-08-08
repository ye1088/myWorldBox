package com.MCWorld.ui.mctool.subject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.k;
import com.MCWorld.module.o;
import com.MCWorld.ui.itemadapter.js.JsDownAdapter;
import com.MCWorld.ui.itemadapter.map.DownAdapter;
import com.MCWorld.ui.mctool.ResourceCommonListLayout.a;

public class JsSubjectListActivity extends MapSubjectListActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.aIQ.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ JsSubjectListActivity bcJ;

            {
                this.bcJ = this$0;
            }

            public void onClick(View arg0) {
                k.R(this.bcJ);
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
        return new a(this) {
            final /* synthetic */ JsSubjectListActivity bcJ;

            public void Z(int tagId, int start, int count) {
                if (this.bcJ.bcO == 0) {
                    o.s(tagId, id, start, count);
                } else {
                    o.a(tagId, this.bcJ.bcC, id, start, count);
                }
            }
        };
    }
}
