package com.huluxia.ui.bbs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.TextView;
import com.huluxia.bbs.b;
import com.huluxia.bbs.b.c;
import com.huluxia.bbs.b.i;
import com.huluxia.data.TableListParc;
import com.huluxia.data.category.TopicCategory;
import com.huluxia.data.topic.TopicItem;
import com.huluxia.http.base.d;
import com.huluxia.http.bbs.category.g;
import com.huluxia.http.bbs.topic.k;
import com.huluxia.r;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseTableActivity;
import com.huluxia.ui.itemadapter.category.MoveClassItemAdapter;
import com.huluxia.utils.UtilsMenu;
import com.huluxia.utils.ab;
import com.huluxia.widget.dialog.n;
import com.huluxia.widget.dialog.n.a;
import com.huluxia.widget.dialog.o;
import com.huluxia.widget.pulltorefresh.PullToRefreshListView;
import com.huluxia.widget.pulltorefresh.PullToRefreshListView.Mode;
import com.simple.colorful.setter.j;

public class MoveTopicActivity extends HTBaseTableActivity {
    private g aKA = new g();
    private k aKB = new k();
    private TopicCategory aKC = null;
    private n aKD = null;
    private MoveClassItemAdapter aKE;
    private long sH = 0;
    private TopicItem sK = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(i.activity_topic_list);
        ej("选择移动话题的版块");
        this.aIs.setVisibility(8);
        this.sK = (TopicItem) getIntent().getSerializableExtra(r.gO);
        Log.i("MoveTopicActivity", Long.toString(this.sK.getPostID()));
        this.aKA.a(this);
        this.aKB.a(this);
        this.aJh = (PullToRefreshListView) findViewById(b.g.listViewData);
        this.aJh.setMode(Mode.PULL_FROM_START);
        this.aKE = new MoveClassItemAdapter(this, this.arrayList, false);
        super.a(b.g.listViewData, this.aKE);
        this.aJh.PT();
    }

    public void a(AdapterView<?> adapterView, View arg1, int position, long arg3) {
        this.aKC = (TopicCategory) this.arrayList.get(position);
        if (this.aKC.getModel() != 0) {
            t.l(this, "不能移动到此版块");
        } else if (this.aKC.getTags().size() > 0) {
            this.aKD = UtilsMenu.c((Context) this, this.aKC.getTags());
            this.aKD.show();
            this.aKD.a(new a(this) {
                final /* synthetic */ MoveTopicActivity aKF;

                {
                    this.aKF = this$0;
                }

                public void a(o m) {
                    this.aKF.sH = Long.valueOf(String.valueOf(m.getTag())).longValue();
                    this.aKF.aKD.dismiss();
                    this.aKF.el(m.getTitle());
                }
            });
        } else {
            el("");
        }
    }

    public void b(d response) {
        super.b(response);
        if (response.fe() == 2) {
            t.n(this, "移动主题失败");
        }
    }

    public void c(d response) {
        cs(false);
        if (response.getStatus() != 1) {
            t.n(this, ab.n(response.fg(), response.fh()));
        } else if (response.fe() == 0) {
            TableListParc tableList = (TableListParc) response.getData();
            if (this.aJh.getCurrentMode() == Mode.PULL_FROM_START) {
                this.aJh.onRefreshComplete();
                this.arrayList.clear();
                this.arrayList.addAll(tableList);
            } else {
                this.arrayList.addAll(tableList);
            }
            this.aJh.setHasMore(tableList.getHasMore());
            this.aJi.notifyDataSetChanged();
        } else if (response.fe() == 2) {
            t.o(this, "移动主题成功");
            setResult(-1);
            finish();
        }
    }

    public void reload() {
        this.aKA.execute();
    }

    public void EY() {
    }

    private void el(String tagName) {
        String tip = this.aKC.getTitle();
        if (!"".equals(tagName)) {
            tip = this.aKC.getTitle() + "-" + tagName;
        }
        final Dialog dialog = new Dialog(this, com.simple.colorful.d.RD());
        View layout = LayoutInflater.from(this).inflate(i.include_dialog_two, null);
        ((TextView) layout.findViewById(b.g.tv_msg)).setText("确认移动话题到 " + tip + " 版吗？");
        dialog.setCancelable(false);
        dialog.setContentView(layout);
        dialog.show();
        layout.findViewById(b.g.tv_cancel).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ MoveTopicActivity aKF;

            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        layout.findViewById(b.g.tv_confirm).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ MoveTopicActivity aKF;

            public void onClick(View arg0) {
                dialog.dismiss();
                this.aKF.aKB.x(this.aKF.sK.getPostID());
                this.aKF.aKB.D(this.aKF.sH);
                this.aKF.aKB.bb(2);
                this.aKF.aKB.v(this.aKF.aKC.getCategoryID());
                this.aKF.aKB.execute();
            }
        });
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        if (this.aKE != null) {
            com.simple.colorful.setter.k setter = new j(this.aJh);
            setter.a(this.aKE);
            builder.a(setter);
        }
        builder.aY(16908290, c.backgroundDefault);
    }
}
