package com.MCWorld.ui.profile;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.MCWorld.bbs.b.c;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.data.topic.SimpleTopicItem;
import com.MCWorld.t;
import com.MCWorld.ui.base.HTBaseActivity;
import com.MCWorld.ui.itemadapter.topic.SimpleTopicItemAdapter;
import com.MCWorld.utils.ao;
import com.MCWorld.utils.ba;
import com.simple.colorful.a.a;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;
import com.simple.colorful.setter.k;
import java.util.ArrayList;

public class ProfileReadTopicListActivity extends HTBaseActivity {
    private Activity aMn;
    private ListView aZh;
    private SimpleTopicItemAdapter bgV;
    private ArrayList bgW = new ArrayList();
    private long bgX = 0;
    private final int bgY = 1;
    private final int bgZ = 110;
    private OnItemClickListener bha = new OnItemClickListener(this) {
        final /* synthetic */ ProfileReadTopicListActivity bhc;

        {
            this.bhc = this$0;
        }

        public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
            SimpleTopicItem data = (SimpleTopicItem) ((ListView) arg0).getItemAtPosition(arg2);
            if (data != null) {
                t.a(this.bhc.aMn, data.postID, 1);
            }
        }
    };
    private OnClickListener bhb = new OnClickListener(this) {
        final /* synthetic */ ProfileReadTopicListActivity bhc;

        {
            this.bhc = this$0;
        }

        public void onClick(View view) {
            this.bhc.eB("您确定要清空所有浏览历史吗？");
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.aMn = this;
        setContentView(i.activity_readtopic_list);
        this.aIs.setVisibility(8);
        this.aIT.setText("清空");
        this.aIT.setVisibility(0);
        this.aIT.setOnClickListener(this.bhb);
        ej("浏览历史");
        this.aZh = (ListView) findViewById(g.list);
        this.bgV = (SimpleTopicItemAdapter) ba.d((Context) this, this.bgW);
        this.aZh.setAdapter(this.bgV);
        this.aZh.setOnItemClickListener(this.bha);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Activity activity = this.aMn;
            if (resultCode == -1 && data != null) {
                this.bgX = data.getLongExtra("delPostId", 0);
            }
        }
    }

    protected void onResume() {
        super.onResume();
        ao.Mx();
        this.bgW = (ArrayList) ao.by(this.bgX);
        this.bgV.setData(this.bgW);
    }

    public void eB(String msg) {
        final Dialog dialog = new Dialog(this, d.RD());
        View layout = LayoutInflater.from(this).inflate(i.include_dialog_two, null);
        ((TextView) layout.findViewById(g.tv_msg)).setText(msg);
        dialog.setCancelable(false);
        dialog.setContentView(layout);
        dialog.show();
        layout.findViewById(g.tv_cancel).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ProfileReadTopicListActivity bhc;

            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        layout.findViewById(g.tv_confirm).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ProfileReadTopicListActivity bhc;

            public void onClick(View arg0) {
                dialog.dismiss();
                ao.Mx().Mz();
                this.bhc.aMn.finish();
            }
        });
    }

    protected void a(a builder) {
        super.a(builder);
        if (this.bgV != null) {
            k setter = new j(this.aZh);
            setter.a(this.bgV);
            builder.a(setter);
        }
        builder.aY(16908290, c.backgroundDefault);
    }
}
