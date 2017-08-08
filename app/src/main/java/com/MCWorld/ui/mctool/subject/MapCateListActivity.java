package com.MCWorld.ui.mctool.subject;

import android.os.Bundle;
import com.MCWorld.data.map.MapCateItem;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.module.n;
import com.MCWorld.module.o;
import com.MCWorld.ui.itemadapter.map.DownAdapter;
import com.MCWorld.ui.mctool.ResourceCommonListLayout.a;
import java.util.ArrayList;

public class MapCateListActivity extends CommonListActivity {
    protected int bcK = 1;
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ MapCateListActivity bcL;

        {
            this.bcL = this$0;
        }

        @MessageHandler(message = 290)
        public void onRecvCateListInfo(boolean succ, int src_tag, ArrayList<MapCateItem> info) {
            HLog.verbose(this, "onRecvCateListInfo", new Object[0]);
            if (info == null) {
                this.bcL.FB();
                return;
            }
            if (this.bcL.bcK == src_tag) {
                ArrayList<MapCateItem> itemList = new ArrayList();
                itemList.add(new MapCateItem(-1, "精选"));
                itemList.addAll(info);
                this.bcL.o(itemList);
            }
            this.bcL.FC();
        }
    };

    protected void kW(int tagValue) {
        this.bcK = tagValue;
    }

    void cS(boolean init) {
        if (init) {
            EventNotifyCenter.add(n.class, this.mCallback);
        } else {
            EventNotifyCenter.remove(this.mCallback);
        }
    }

    protected void IM() {
        o.je(this.bcK);
    }

    void IN() {
        ArrayList<MapCateItem> itemList = new ArrayList();
        itemList.add(new MapCateItem(-1, "精选"));
        o(itemList);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ej("地图分类");
        cS(true);
        IN();
        Fy();
        IM();
    }

    protected void EX() {
        super.EX();
        IM();
    }

    protected void onDestroy() {
        super.onDestroy();
        cS(false);
    }

    public TabListResourceFragment ay(int id, int pos) {
        return TabListResourceFragment.aA(id, pos);
    }

    public DownAdapter az(int id, int pos) {
        return new DownAdapter(this);
    }

    public a kV(final int id) {
        if (id != -1) {
            return new a(this) {
                final /* synthetic */ MapCateListActivity bcL;

                public void Z(int tagId, int start, int count) {
                    o.q(tagId, id, start, count);
                }
            };
        }
        return new a(this) {
            final /* synthetic */ MapCateListActivity bcL;

            {
                this.bcL = this$0;
            }

            public void Z(int tagId, int start, int count) {
                o.R(tagId, start, count);
            }
        };
    }
}
