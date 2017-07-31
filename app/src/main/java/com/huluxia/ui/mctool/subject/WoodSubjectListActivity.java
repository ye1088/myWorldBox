package com.huluxia.ui.mctool.subject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.k;
import com.huluxia.module.o;
import com.huluxia.ui.itemadapter.map.DownAdapter;
import com.huluxia.ui.itemadapter.wood.WoodDownAdapter;
import com.huluxia.ui.mctool.ResourceCommonListLayout.a;

public class WoodSubjectListActivity extends MapSubjectListActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.aIQ.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ WoodSubjectListActivity bcZ;

            {
                this.bcZ = this$0;
            }

            public void onClick(View arg0) {
                k.S(this.bcZ);
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
        return new a(this) {
            final /* synthetic */ WoodSubjectListActivity bcZ;

            public void Z(int tagId, int start, int count) {
                if (this.bcZ.bcO == 0) {
                    o.t(tagId, id, start, count);
                } else {
                    o.b(tagId, this.bcZ.bcC, id, start, count);
                }
            }
        };
    }
}
