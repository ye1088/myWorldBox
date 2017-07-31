package com.huluxia.ui.mctool.subject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.huluxia.k;
import com.huluxia.module.o;
import com.huluxia.ui.itemadapter.js.JsDownAdapter;
import com.huluxia.ui.itemadapter.map.DownAdapter;
import com.huluxia.ui.mctool.ResourceCommonListLayout.a;

public class JsRankListActivity extends MapRankListActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ej("ＪＳ");
        this.aIQ.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ JsRankListActivity bcI;

            {
                this.bcI = this$0;
            }

            public void onClick(View arg0) {
                k.R(this.bcI);
            }
        });
        k(2, "click_js_more_tab");
    }

    public TabListResourceFragment ay(int id, int pos) {
        return TabListResourceFragment.aA(id, pos);
    }

    public DownAdapter az(int id, int pos) {
        return new JsDownAdapter(this);
    }

    public a kV(int id) {
        if (id == 1) {
            return new a(this) {
                final /* synthetic */ JsRankListActivity bcI;

                {
                    this.bcI = this$0;
                }

                public void Z(int tagId, int start, int count) {
                    o.a(tagId, this.bcI.bcC, start, count);
                }
            };
        }
        if (id == 2) {
            return new a(this) {
                final /* synthetic */ JsRankListActivity bcI;

                {
                    this.bcI = this$0;
                }

                public void Z(int tagId, int start, int count) {
                    o.b(tagId, this.bcI.bcC, start, count);
                }
            };
        }
        if (id == 3) {
            return new a(this) {
                final /* synthetic */ JsRankListActivity bcI;

                {
                    this.bcI = this$0;
                }

                public void Z(int tagId, int start, int count) {
                    o.c(tagId, this.bcI.bcC, start, count);
                }
            };
        }
        return null;
    }
}
