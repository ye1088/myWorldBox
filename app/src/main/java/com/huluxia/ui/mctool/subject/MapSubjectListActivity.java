package com.huluxia.ui.mctool.subject;

import android.os.Bundle;
import com.huluxia.data.map.MapCateItem;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.module.n;
import com.huluxia.module.o;
import com.huluxia.ui.itemadapter.map.DownAdapter;
import com.huluxia.ui.mctool.ResourceCommonListLayout;
import hlx.data.localstore.a;
import java.util.ArrayList;

public class MapSubjectListActivity extends CommonListActivity {
    protected int bcO = 0;
    protected int bcP;
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ MapSubjectListActivity bcQ;

        {
            this.bcQ = this$0;
        }

        @MessageHandler(message = 3076)
        public void onRecvLoadingAnimationInfo(boolean succ) {
            if (succ) {
                this.bcQ.FC();
            } else {
                this.bcQ.FB();
            }
        }
    };

    void l(int subjectId, String subjectName) {
        ArrayList<MapCateItem> itemList = new ArrayList();
        itemList.add(new MapCateItem((long) subjectId, subjectName));
        o(itemList);
    }

    protected void onCreate(Bundle savedInstanceState) {
        String subjectName;
        super.onCreate(savedInstanceState);
        EventNotifyCenter.add(n.class, this.mCallback);
        if (savedInstanceState == null) {
            this.bcO = getIntent().getExtras().getInt("subject_type");
            this.bcP = getIntent().getExtras().getInt("subject_id");
            subjectName = getIntent().getExtras().getString("subject_name");
        } else {
            this.bcO = getIntent().getExtras().getInt("subject_type");
            this.bcP = savedInstanceState.getInt("subject_id");
            subjectName = getIntent().getExtras().getString("subject_name");
        }
        ej(subjectName);
        this.baN.setVisibility(8);
        l(this.bcP, subjectName);
        Fy();
    }

    protected void EX() {
        super.EX();
        EventNotifyCenter.notifyEvent(n.class, 3077, new Object[0]);
    }

    protected void onStart() {
        super.onStart();
        a.bLG = 4;
    }

    protected void onStop() {
        super.onStop();
        a.bLG = 1;
    }

    protected void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
    }

    public DownAdapter az(int id, int pos) {
        return new DownAdapter(this);
    }

    public ResourceCommonListLayout.a kV(final int id) {
        return new ResourceCommonListLayout.a(this) {
            final /* synthetic */ MapSubjectListActivity bcQ;

            public void Z(int tagId, int start, int count) {
                if (this.bcQ.bcO == 0) {
                    o.r(tagId, id, start, count);
                } else {
                    o.q(tagId, id, start, count);
                }
            }
        };
    }

    public TabListResourceFragment ay(int id, int pos) {
        return TabListResourceFragment.aA(id, pos);
    }
}
