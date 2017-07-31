package com.huluxia.ui.mctool.subject;

import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import com.huluxia.data.map.MapCateItem;
import com.huluxia.module.o;
import com.huluxia.r;
import com.huluxia.r.a;
import com.huluxia.ui.itemadapter.map.DownAdapter;
import com.huluxia.ui.mctool.ResourceCommonListLayout;
import java.util.ArrayList;

public class MapRankListActivity extends CommonListActivity {
    protected int bcK = 1;
    protected String bcM = "click_map_more_tab";

    void IN() {
        ArrayList<MapCateItem> itemList = new ArrayList();
        itemList.add(new MapCateItem(2, "精选"));
        itemList.add(new MapCateItem(3, "最新"));
        itemList.add(new MapCateItem(1, "周榜"));
        o(itemList);
    }

    protected void k(int cateTag, String cateStr) {
        this.bcK = cateTag;
        this.bcM = cateStr;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ej("地图");
        IN();
        this.baN.setOnPageChangeListener(new OnPageChangeListener(this) {
            final /* synthetic */ MapRankListActivity bcN;

            {
                this.bcN = this$0;
            }

            public void onPageScrolled(int i, float v, int i2) {
            }

            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        r.ck().j(this.bcN.bcM, "recommend");
                        return;
                    case 1:
                        r.ck().j(this.bcN.bcM, r.gM);
                        return;
                    case 2:
                        r.ck().j(this.bcN.bcM, r.gP);
                        return;
                    default:
                        return;
                }
            }

            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    public TabListResourceFragment ay(int id, int pos) {
        return TabListResourceFragment.aA(id, pos);
    }

    public DownAdapter az(int id, int pos) {
        if (pos == 0) {
            return new DownAdapter(this, a.jn);
        }
        return new DownAdapter(this);
    }

    public ResourceCommonListLayout.a kV(int id) {
        if (id == 1) {
            return new ResourceCommonListLayout.a(this) {
                final /* synthetic */ MapRankListActivity bcN;

                {
                    this.bcN = this$0;
                }

                public void Z(int tagId, int start, int count) {
                    o.P(tagId, start, count);
                }
            };
        }
        if (id == 2) {
            return new ResourceCommonListLayout.a(this) {
                final /* synthetic */ MapRankListActivity bcN;

                {
                    this.bcN = this$0;
                }

                public void Z(int tagId, int start, int count) {
                    o.R(tagId, start, count);
                }
            };
        }
        if (id == 3) {
            return new ResourceCommonListLayout.a(this) {
                final /* synthetic */ MapRankListActivity bcN;

                {
                    this.bcN = this$0;
                }

                public void Z(int tagId, int start, int count) {
                    o.Q(tagId, start, count);
                }
            };
        }
        return null;
    }
}
