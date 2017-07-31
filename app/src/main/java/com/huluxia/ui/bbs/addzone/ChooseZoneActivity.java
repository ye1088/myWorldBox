package com.huluxia.ui.bbs.addzone;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huluxia.bbs.b.f;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.utils.Supplier;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.base.widget.status.StatusActivityPage.StatusPageActivityBuilder;
import com.huluxia.framework.base.widget.status.StatusBasePage;
import com.huluxia.framework.base.widget.title.TitleBar;
import com.huluxia.module.h;
import com.huluxia.module.topic.c;
import com.huluxia.module.topic.d;
import com.huluxia.module.topic.k;
import com.huluxia.module.topic.m;
import com.huluxia.module.topic.n;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseThemeActivity;
import com.simple.colorful.b;
import com.simple.colorful.setter.j;
import java.util.ArrayList;

public class ChooseZoneActivity extends HTBaseThemeActivity {
    private static final String TAG = "ChooseZoneActivity";
    private static final String aQK = "ZONE_CATEGORY";
    private static final String aQL = "SELECTED_ITEM";
    private TitleBar aIw;
    private a aQM;
    private PullToRefreshListView aQN;
    private ZoneSubCategoryAdapter aQO;
    private View aQP;
    private View aQQ;
    private ArrayList<n> aQR;
    private ArrayList<d> aQS;
    private n aQT;
    private OnClickListener aQU = new OnClickListener(this) {
        final /* synthetic */ ChooseZoneActivity aQV;

        {
            this.aQV = this$0;
        }

        public void onClick(View v) {
            if (this.aQV.aQR == null) {
                k.Ej().El();
                this.aQV.mPage.showLoading(t.dz());
                return;
            }
            this.aQV.cD(true);
        }
    };
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ ChooseZoneActivity aQV;

        {
            this.aQV = this$0;
        }

        @MessageHandler(message = 661)
        public void onRecvCategory(boolean succ, m zoneCategory) {
            this.aQV.mPage.hideStatus();
            if (succ && zoneCategory != null && zoneCategory.isSucc() && !UtilsFunction.empty(zoneCategory.categoryforum)) {
                this.aQV.aQR = (ArrayList) zoneCategory.categoryforum;
                if (this.aQV.aQT == null) {
                    this.aQV.aQT = (n) this.aQV.aQR.get(0);
                }
                if (this.aQV.aQM == null) {
                    this.aQV.aQM = new a();
                    this.aQV.mListView.setAdapter(this.aQV.aQM);
                }
                this.aQV.aQM.notifyDataSetChanged();
                this.aQV.cD(true);
            } else if (UtilsFunction.empty(this.aQV.aQR)) {
                this.aQV.mPage.showReload(t.dA());
            } else {
                Toast.makeText(this.aQV, "获取板块分类失败，请稍后重试", 0).show();
            }
        }

        @MessageHandler(message = 662)
        public void onRecvSubCategory(boolean succ, c zoneCategory, int id) {
            if (this.aQV.aQT != null && this.aQV.aQT.id == id) {
                this.aQV.mPage.hideStatus();
                this.aQV.aQN.onRefreshComplete();
                if (!succ || zoneCategory == null || !zoneCategory.isSucc() || UtilsFunction.empty(zoneCategory.categories)) {
                    this.aQV.mPage.showReload(t.dA());
                    return;
                }
                this.aQV.aQS = (ArrayList) zoneCategory.categories;
                if (this.aQV.aQO == null) {
                    this.aQV.aQO = new ZoneSubCategoryAdapter(this.aQV);
                    this.aQV.aQN.setAdapter(this.aQV.aQO);
                }
                this.aQV.aQO.c(this.aQV.aQS, true);
            }
        }

        @MessageHandler(message = 640)
        public void onCateSubscribeOrNot(String flag) {
            this.aQV.aQN.setRefreshing(false);
        }

        @MessageHandler(message = 613)
        public void onRecvLogin(com.huluxia.data.k info, String msg) {
            HLog.debug(ChooseZoneActivity.TAG, "choose zone recv " + info, new Object[0]);
            if (info != null && info.isSucc()) {
                this.aQV.aQN.setRefreshing(false);
            }
        }
    };
    private ListView mListView;
    private StatusBasePage mPage;

    private class a extends BaseAdapter implements b {
        final /* synthetic */ ChooseZoneActivity aQV;

        private a(ChooseZoneActivity chooseZoneActivity) {
            this.aQV = chooseZoneActivity;
        }

        public /* synthetic */ Object getItem(int i) {
            return kC(i);
        }

        public int getCount() {
            return UtilsFunction.size(this.aQV.aQR);
        }

        public n kC(int position) {
            return (n) this.aQV.aQR.get(position);
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            a holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(this.aQV).inflate(i.item_zone_category, parent, false);
                holder = new a(this, null);
                holder.aPx = convertView.findViewById(g.container);
                holder.aQW = convertView.findViewById(g.indicator);
                holder.aQY = convertView.findViewById(g.split);
                holder.aQZ = convertView.findViewById(g.horizontal_split);
                holder.aQX = (TextView) convertView.findViewById(g.cate);
                convertView.setTag(holder);
            } else {
                holder = (a) convertView.getTag();
            }
            n item = kC(position);
            holder.aQX.setText(item.title);
            if (com.simple.colorful.d.isDayMode()) {
                holder.aQZ.setBackgroundResource(com.huluxia.bbs.b.d.SplitLine);
                if (item.equals(this.aQV.aQT)) {
                    holder.aQW.setBackgroundResource(com.huluxia.bbs.b.d.green_download1);
                    holder.aQX.setTextColor(this.aQV.getResources().getColor(com.huluxia.bbs.b.d.green_download1));
                    holder.aPx.setBackgroundResource(com.huluxia.bbs.b.d.background_normal);
                    holder.aQY.setVisibility(4);
                } else {
                    holder.aQW.setBackgroundResource(com.huluxia.bbs.b.d.transparent);
                    holder.aQX.setTextColor(this.aQV.getResources().getColor(com.huluxia.bbs.b.d.download_game_desc));
                    holder.aPx.setBackgroundResource(com.huluxia.bbs.b.d.transparent);
                    holder.aQY.setVisibility(0);
                }
            } else {
                holder.aQZ.setBackgroundResource(com.huluxia.bbs.b.d.color_split_night);
                if (item.equals(this.aQV.aQT)) {
                    holder.aQW.setBackgroundResource(com.huluxia.bbs.b.d.color_primary_green_night2);
                    holder.aQX.setTextColor(this.aQV.getResources().getColor(com.huluxia.bbs.b.d.color_primary_green_night2));
                    holder.aPx.setBackgroundResource(com.huluxia.bbs.b.d.text_color_topic_score);
                    holder.aQY.setVisibility(4);
                } else {
                    holder.aQW.setBackgroundResource(com.huluxia.bbs.b.d.transparent);
                    holder.aQX.setTextColor(this.aQV.getResources().getColor(com.huluxia.bbs.b.d.text_color_tertiary_night));
                    holder.aPx.setBackgroundResource(com.huluxia.bbs.b.d.transparent);
                    holder.aQY.setVisibility(0);
                }
            }
            return convertView;
        }

        public void a(j setter) {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventNotifyCenter.add(h.class, this.mCallback);
        setContentView(i.activity_choose_zone);
        EP();
        this.mListView = (ListView) findViewById(g.zone_category);
        this.aQN = (PullToRefreshListView) findViewById(g.zone_content);
        this.aQM = new a();
        this.mListView.setAdapter(this.aQM);
        this.aQO = new ZoneSubCategoryAdapter(this);
        this.aQN.setAdapter(this.aQO);
        this.mPage = ((StatusPageActivityBuilder) ((StatusPageActivityBuilder) com.huluxia.ui.status.c.a((FragmentActivity) this).setBoundedView(g.zone_choose_container)).setClickListener(new Supplier<OnClickListener>(this) {
            final /* synthetic */ ChooseZoneActivity aQV;

            {
                this.aQV = this$0;
            }

            public /* synthetic */ Object get() {
                return Hn();
            }

            public OnClickListener Hn() {
                return this.aQV.aQU;
            }
        })).build().inject();
        if (savedInstanceState == null) {
            k.Ej().El();
            this.mPage.showLoading(t.dz());
        } else {
            this.aQT = (n) savedInstanceState.getParcelable(aQL);
            this.aQR = savedInstanceState.getParcelableArrayList(aQK);
            if (UtilsFunction.empty(this.aQR)) {
                k.Ej().El();
                this.mPage.showLoading(t.dz());
            } else {
                this.aQM.notifyDataSetChanged();
            }
            cD(true);
        }
        this.mListView.setOnItemClickListener(new OnItemClickListener(this) {
            final /* synthetic */ ChooseZoneActivity aQV;

            {
                this.aQV = this$0;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (this.aQV.aQM != null) {
                    n item = this.aQV.aQM.kC(position);
                    if (!item.equals(this.aQV.aQT)) {
                        this.aQV.aQT = item;
                        this.aQV.aQM.notifyDataSetChanged();
                        this.aQV.cD(true);
                    }
                }
            }
        });
        this.aQN.setOnRefreshListener(new OnRefreshListener<ListView>(this) {
            final /* synthetic */ ChooseZoneActivity aQV;

            {
                this.aQV = this$0;
            }

            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                this.aQV.cD(false);
            }
        });
        ((ListView) this.aQN.getRefreshableView()).setOnItemClickListener(new OnItemClickListener(this) {
            final /* synthetic */ ChooseZoneActivity aQV;

            {
                this.aQV = this$0;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (position <= this.aQV.aQO.getCount()) {
                    d data = this.aQV.aQO.kD(position - 1);
                    if (data != null) {
                        t.a(this.aQV, data.convert());
                    }
                }
            }
        });
        ((ListView) this.aQN.getRefreshableView()).setSelector(f.transparent);
    }

    private void cD(boolean loading) {
        if (this.aQT != null) {
            if (loading) {
                this.mPage.setBoundedView(g.zone_content);
                this.mPage.showLoading(t.dz());
            }
            k.Ej().ke(this.aQT.id);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(aQK, this.aQR);
        outState.putParcelable(aQL, this.aQT);
    }

    private void EP() {
        this.aIw = (TitleBar) findViewById(g.title_bar);
        this.aIw.setVisibility(0);
        this.aIw.setLeftLayout(i.layout_title_left_icon_and_text, new LayoutParams(-2, -1));
        this.aIw.setRightLayout(i.layout_title_right_icon_and_text, new LayoutParams(-2, -1));
        TextView headerTitle = (TextView) this.aIw.findViewById(g.header_title);
        headerTitle.setText("添加板块");
        headerTitle.setTextColor(com.simple.colorful.d.getColor(this, 16842809));
        ((ImageView) this.aIw.findViewById(g.sys_header_back)).setImageDrawable(com.simple.colorful.d.o(this, com.huluxia.bbs.b.c.drawableTitleBack));
        this.aQP = findViewById(g.rl_header_back);
        this.aQP.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ChooseZoneActivity aQV;

            {
                this.aQV = this$0;
            }

            public void onClick(View v) {
                this.aQV.finish();
            }
        });
        headerTitle = (TextView) this.aIw.findViewById(g.right_title);
        this.aIw.findViewById(g.right_img).setVisibility(8);
        this.aQQ = this.aIw.findViewById(g.rl_right_container);
        headerTitle.setText("投票");
        headerTitle.setTextColor(com.simple.colorful.d.getColor(this, 16842809));
        this.aQQ.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ChooseZoneActivity aQV;

            {
                this.aQV = this$0;
            }

            public void onClick(View v) {
                t.ai(this.aQV);
            }
        });
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        j(com.simple.colorful.d.Lo(), true);
    }

    protected void kj(int themeId) {
        super.kj(themeId);
        if (this.aQM != null) {
            this.aQM.notifyDataSetChanged();
        }
        if (this.aQO != null) {
            this.aQO.notifyDataSetChanged();
        }
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        if (!(this.aQM == null || this.mListView == null)) {
            com.simple.colorful.setter.k setter = new j(this.mListView);
            setter.a(this.aQM);
            builder.a(setter);
        }
        if (!(this.aQO == null || this.aQN == null)) {
            setter = new j(this.aQN);
            setter.a(this.aQO);
            builder.a(setter);
        }
        builder.aY(g.title_bar, com.huluxia.bbs.b.c.backgroundTitleBar).aY(g.split_top, com.huluxia.bbs.b.c.splitColorDim).j(this.aQP, com.huluxia.bbs.b.c.backgroundTitleBarButton).aY(g.split, com.huluxia.bbs.b.c.splitZoneCategory).aY(g.cate_container, com.huluxia.bbs.b.c.zoneCategoryBg).aY(g.zone_choose_container, com.huluxia.bbs.b.c.contentCategoryBg).aZ(g.rl_right_container, com.huluxia.bbs.b.c.backgroundTitleBarButton);
    }
}
