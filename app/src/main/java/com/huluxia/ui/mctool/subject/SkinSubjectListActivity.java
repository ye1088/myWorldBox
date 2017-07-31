package com.huluxia.ui.mctool.subject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.k;
import com.huluxia.module.o;
import com.huluxia.ui.itemadapter.map.DownAdapter;
import com.huluxia.ui.itemadapter.skin.SkinDownAdapter;
import com.huluxia.ui.mctool.ResourceCommonListLayout.a;

public class SkinSubjectListActivity extends MapSubjectListActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.aIQ.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ SkinSubjectListActivity bcT;

            {
                this.bcT = this$0;
            }

            public void onClick(View arg0) {
                k.T(this.bcT);
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
        return new a(this) {
            final /* synthetic */ SkinSubjectListActivity bcT;

            public void Z(int tagId, int start, int count) {
                if (this.bcT.bcO == 0) {
                    o.u(tagId, id, start, count);
                } else {
                    o.c(tagId, this.bcT.bcC, id, start, count);
                }
            }
        };
    }
}
