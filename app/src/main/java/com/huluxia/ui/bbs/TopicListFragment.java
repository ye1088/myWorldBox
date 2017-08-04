package com.huluxia.ui.bbs;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huluxia.HTApplication;
import com.huluxia.bbs.b.m;
import com.huluxia.data.TagInfo;
import com.huluxia.data.category.TopicCategory;
import com.huluxia.data.j;
import com.huluxia.data.message.MsgCounts;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.base.utils.UtilsScreen;
import com.huluxia.framework.base.utils.UtilsSystem;
import com.huluxia.framework.base.widget.title.TitleBar;
import com.huluxia.http.bbs.category.f;
import com.huluxia.http.bbs.category.i;
import com.huluxia.module.h;
import com.huluxia.module.n;
import com.huluxia.module.topic.TopicModule;
import com.huluxia.module.topic.b;
import com.huluxia.module.topic.g;
import com.huluxia.module.topic.k;
import com.huluxia.r;
import com.huluxia.t;
import com.huluxia.ui.base.BaseLoadingFragment;
import com.huluxia.utils.UtilsMenu.MENU_TOPIC_LIST;
import com.huluxia.utils.a;
import com.huluxia.utils.aa;
import com.huluxia.utils.ab;
import com.huluxia.utils.ah;
import com.huluxia.utils.ba;
import com.huluxia.widget.caseview.CaseView;
import com.simple.colorful.d;
import java.util.ArrayList;
import java.util.List;

public class TopicListFragment extends BaseLoadingFragment implements OnClickListener {
    private static final int PAGE_SIZE = 20;
    private static final String TAG = "TopicListFragment";
    protected PullToRefreshListView aHX;
    protected aa aHb;
    private TextView aIg;
    private BroadcastReceiver aIn;
    private BroadcastReceiver aIo;
    private TopicCategory aPE;
    private TopicListTitle aPF;
    private BaseAdapter aPG = null;
    private List<TagInfo> aPH = new ArrayList();
    private b aPI = new b();
    private long aPJ;
    private long aPK;
    private RelativeLayout aPL;
    private Button aPM;
    private LinearLayout aPN;
    private Button aPO;
    private LinearLayout aPP;
    private CheckedTextView aPQ;
    private PopupWindow aPR;
    private TextView aPS;
    private TextView aPT;
    private TextView aPU;
    private boolean aPV = false;
    private ImageButton aPW;
    private f aPX = new f();
    private i aPY = new i();
    private com.huluxia.http.bbs.category.c aPZ = new com.huluxia.http.bbs.category.c();
    private boolean aPg = false;
    boolean aQa = false;
    private LinearLayout aQb;
    private LinearLayout aQc;
    private LinearLayout aQd;
    private TextView aQe;
    private TextView aQf;
    private RelativeLayout aQg;
    private TextView aQh;
    private boolean aQi;
    private ImageView aQj;
    private ImageView aQk;
    private a aQl = new a();
    private MENU_TOPIC_LIST aQm = MENU_TOPIC_LIST.FILTER_ACTIVE_TIME;
    private c aQn;
    private CallbackHandler aQo = new CallbackHandler(this) {
        final /* synthetic */ TopicListFragment aQp;

        {
            this.aQp = this$0;
        }

        @MessageHandler(message = 297)
        public void onRecvMapCateDetail(boolean succ, TopicCategory topicCategory) {
            if (this.aQp.aPE == null) {
                if (succ) {
                    this.aQp.aPE = topicCategory;
                    this.aQp.Hc();
                    this.aQp.es("0");
                    return;
                }
                t.n(this.aQp.mContext, "访问失败");
            }
        }

        @MessageHandler(message = 2325)
        public void onRecvMapListChanged(boolean changed) {
            if (this.aQp.aPE != null && this.aQp.aPE.getCategoryID() == 68) {
                this.aQp.es("0");
            }
        }
    };
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ TopicListFragment aQp;

        {
            this.aQp = this$0;
        }

        @MessageHandler(message = 601)
        public void onRecvTopicList(boolean succ, String start, b info, long cateId, long tagId) {
            if (cateId == this.aQp.aPJ && tagId == this.aQp.aPK) {
                this.aQp.cs(false);
                this.aQp.aHX.onRefreshComplete();
                if (succ && this.aQp.aPG != null && info != null && info.isSucc()) {
                    this.aQp.aHb.onLoadComplete();
                    this.aQp.aPI.start = info.start;
                    this.aQp.aPI.more = info.more;
                    if (start == null || start.equals("0")) {
                        this.aQp.aPI.posts.clear();
                        this.aQp.aPI.posts.addAll(info.posts);
                    } else {
                        this.aQp.aPI.posts.addAll(info.posts);
                    }
                    this.aQp.aPG.notifyDataSetChanged();
                    this.aQp.setCategory(info.category);
                    this.aQp.FC();
                    if (this.aQp.aPJ == 0) {
                        ah.KZ().bw(0);
                    }
                } else if (this.aQp.getCurrentPage() == 0) {
                    this.aQp.FB();
                } else {
                    this.aQp.aHb.KN();
                    t.n(this.aQp.mContext, "数据请求失败，请下拉刷新重试");
                }
            }
        }

        @MessageHandler(message = 640)
        public void onCateSubscribeOrNot(String flag) {
            HLog.debug("TopicListFragment onTopicCategoryAddOrAbolish", "flag is " + flag, new Object[0]);
        }

        @MessageHandler(message = 648)
        public void onRecConf(g info, String tag, boolean next, Object clickitem) {
            HLog.verbose(TopicListFragment.TAG, "info " + info, new Object[0]);
            if (this.aQp.aPg) {
                this.aQp.aPg = false;
                if (tag == null || !tag.equals(TopicListFragment.TAG) || !next) {
                    return;
                }
                if (info == null || !info.isSucc()) {
                    if (info != null) {
                        t.n(this.aQp.mContext, ab.n(info.code, info.msg));
                        return;
                    }
                    t.a(this.aQp.mContext, this.aQp.aPE.getCategoryID(), this.aQp.aPH, 0);
                } else if (info.ispower == 1) {
                    t.a(this.aQp.mContext, this.aQp.aPE.getCategoryID(), this.aQp.aPH, info.isvideo);
                } else {
                    this.aQp.T(info.title, info.message);
                }
            }
        }
    };
    private Activity mContext;
    private int subscribeType = ESubscribeType.Invalid.ordinal();

    public interface c {
        void Hb();

        void c(List<Long> list, List<String> list2);

        void cz(boolean z);
    }

    public static TopicListFragment a(TopicCategory category) {
        TopicListFragment fragment = new TopicListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("category", category);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventNotifyCenter.add(h.class, this.mCallback);
        EventNotifyCenter.add(n.class, this.aQo);
        this.mContext = getActivity();
        this.aIn = new b(this, null);
        this.aIo = new a(this, null);
        com.huluxia.service.i.e(this.aIn);
        com.huluxia.service.i.f(this.aIo);
        if (savedInstanceState == null) {
            this.aPE = (TopicCategory) getArguments().getParcelable("category");
        } else {
            this.aPE = (TopicCategory) savedInstanceState.getParcelable("category");
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(com.huluxia.bbs.b.i.fragment_topic_list, container, false);
        if (this.aPE != null) {
            Hc();
        }
        e(view);
        r.ck().J(String.valueOf(this.aPE.getCategoryID()));
        this.aQk = (ImageView) view.findViewById(com.huluxia.bbs.b.g.btn_top);
        this.aQk.setOnClickListener(this);
        this.aQj = (ImageView) view.findViewById(com.huluxia.bbs.b.g.btn_sidebar);
        this.aQj.setOnClickListener(this);
        this.aQj.setVisibility(this.aPJ == 0 ? 8 : 0);
        if (savedInstanceState == null) {
            Fy();
            es("0");
        } else {
            this.aPI = (b) savedInstanceState.getParcelable("topic_info");
            this.aPG.notifyDataSetChanged();
        }
        return view;
    }

    private void Hc() {
        this.aPJ = this.aPE.categoryID;
        cB(this.aPJ == 0);
        r.ck().J(String.valueOf(this.aPE.getCategoryID()));
        this.aPF = new TopicListTitle(this.mContext);
        this.aPF.setTopicCategory(this.aPE);
        this.aQg = (RelativeLayout) this.aPF.findViewById(com.huluxia.bbs.b.g.rly_header);
        this.aQg.setOnClickListener(this);
        this.aQf = (TextView) this.aPF.findViewById(com.huluxia.bbs.b.g.topic_title);
        this.aQf.setText(this.aPE.getTitle());
        if (this.aPJ != 0) {
            this.aQh = (TextView) this.aPF.findViewById(com.huluxia.bbs.b.g.ic_add_class);
            this.aQh.setOnClickListener(this);
            this.aQb = (LinearLayout) this.aPF.findViewById(com.huluxia.bbs.b.g.btn_daren);
            this.aQb.setOnClickListener(this);
            this.aQc = (LinearLayout) this.aPF.findViewById(com.huluxia.bbs.b.g.btn_signin);
            this.aQe = (TextView) this.aPF.findViewById(com.huluxia.bbs.b.g.tv_signin);
            this.aQc.setOnClickListener(this);
            this.aQd = (LinearLayout) this.aPF.findViewById(com.huluxia.bbs.b.g.btn_search);
            this.aQd.setOnClickListener(this);
            this.aPX.bb(1);
            this.aPX.v(this.aPE.getCategoryID());
            this.aPX.w(j.ep().getUserid());
            this.aPX.a(this);
            if (j.ep().ey()) {
                this.aPX.execute();
            }
            this.aPY.bb(2);
            this.aPY.v(this.aPE.getCategoryID());
            this.aPY.w(j.ep().getUserid());
            this.aPY.a(this);
            this.aPZ.bb(3);
            this.aPZ.a(this);
        }
    }

    private void cB(boolean isFollow) {
        int i;
        int i2 = 8;
        ImageButton imageButton = this.aPW;
        if (isFollow) {
            i = 8;
        } else {
            i = 0;
        }
        imageButton.setVisibility(i);
        RelativeLayout relativeLayout = this.aPL;
        if (isFollow) {
            i = 0;
        } else {
            i = 8;
        }
        relativeLayout.setVisibility(i);
        LinearLayout linearLayout = this.aPN;
        if (!isFollow) {
            i2 = 0;
        }
        linearLayout.setVisibility(i2);
    }

    protected void a(TitleBar titleBar) {
        super.a(titleBar);
        cr(false);
        titleBar.setLeftLayout(com.huluxia.bbs.b.i.include_topiclist_titlebar_left);
        titleBar.setRightLayout(com.huluxia.bbs.b.i.include_topiclist_titlebar_right);
        this.aPL = (RelativeLayout) titleBar.findViewById(com.huluxia.bbs.b.g.rl_header_back);
        this.aPM = (Button) titleBar.findViewById(com.huluxia.bbs.b.g.sys_header_back);
        this.aPM.setBackgroundResource(com.huluxia.bbs.b.f.sl_title_bar_button);
        this.aPM.setOnClickListener(new 1(this));
        this.aPN = (LinearLayout) titleBar.findViewById(com.huluxia.bbs.b.g.ll_topic_left);
        this.aPO = (Button) titleBar.findViewById(com.huluxia.bbs.b.g.topic_back);
        this.aPP = (LinearLayout) titleBar.findViewById(com.huluxia.bbs.b.g.filter_ll);
        this.aPQ = (CheckedTextView) titleBar.findViewById(com.huluxia.bbs.b.g.filter_tv);
        this.aPO.setOnClickListener(new 8(this));
        this.aPQ.setText(getResources().getString(m.filter_activetime));
        this.aPP.setOnClickListener(new 9(this));
        this.aIg = (TextView) titleBar.findViewById(com.huluxia.bbs.b.g.tv_msg);
        ImageButton imgMsg = (ImageButton) titleBar.findViewById(com.huluxia.bbs.b.g.img_msg);
        imgMsg.setBackgroundResource(com.huluxia.bbs.b.f.sl_title_bar_button);
        imgMsg.setVisibility(0);
        imgMsg.setOnClickListener(new 10(this));
        this.aPW = (ImageButton) titleBar.findViewById(com.huluxia.bbs.b.g.sys_header_flright_img);
        this.aPW.setBackgroundResource(com.huluxia.bbs.b.f.sl_title_bar_button);
        this.aPW.setImageDrawable(d.o(this.mContext, com.huluxia.bbs.b.c.drawableTitlePost));
        if (this.aPE.getCategoryID() != 0) {
            this.aPW.setVisibility(0);
        }
        this.aPW.setOnClickListener(new 11(this));
        Fr();
    }

    private void Hd() {
        if (this.aPJ == 0) {
            t.a(this.mContext, this.aPE.getCategoryID(), this.aPH, 0);
        } else if (!this.aPg) {
            g info = ah.KZ().bu(j.ep().getUserid());
            String nowHour = UtilsSystem.getHourStr();
            HLog.verbose(TAG, "nowHour " + nowHour + " CreatePowerInfo " + info, new Object[0]);
            if (info != null && info.topicCats != null && info.topicCats.contains(Long.valueOf(this.aPJ)) && info.topicHours != null && info.topicHours.containsKey(Long.valueOf(this.aPJ)) && info.topicHours.get(Long.valueOf(this.aPJ)) != null && ((String) info.topicHours.get(Long.valueOf(this.aPJ))).equals(nowHour)) {
                t.a(this.mContext, this.aPE.getCategoryID(), this.aPH, info.isvideo);
                d(this.aPJ, false);
            } else if (info == null || info.topicTipMsg == null || info.topicTipTitle == null || info.topicHours == null || !info.topicHours.containsKey(Long.valueOf(this.aPJ)) || info.topicHours.get(Long.valueOf(this.aPJ)) == null || !((String) info.topicHours.get(Long.valueOf(this.aPJ))).equals(nowHour)) {
                d(this.aPJ, true);
            } else {
                T(info.topicTipTitle, info.topicTipMsg);
                d(this.aPJ, false);
            }
        }
    }

    private void e(View view) {
        this.aHX = (PullToRefreshListView) view.findViewById(com.huluxia.bbs.b.g.list);
        if (this.aPJ != 0) {
            ((ListView) this.aHX.getRefreshableView()).addHeaderView(this.aPF);
        }
        this.aPG = ba.c(this.mContext, (ArrayList) this.aPI.posts);
        this.aHX.setOnRefreshListener(new 12(this));
        this.aHX.setOnItemClickListener(new 13(this));
        this.aHX.setAdapter(this.aPG);
        this.aHb = new aa((ListView) this.aHX.getRefreshableView());
        this.aHb.a(new 14(this));
        this.aHX.setOnScrollListener(this.aHb);
        ((ListView) this.aHX.getRefreshableView()).setOnTouchListener(new 15(this, this.mContext));
    }

    private void GC() {
        if (this.aPR != null && this.aPR.isShowing()) {
            this.aPR.dismiss();
            this.aPR = null;
        }
        this.aPV = false;
        this.aPQ.setChecked(false);
    }

    private void He() {
        View view = LayoutInflater.from(this.mContext).inflate(com.huluxia.bbs.b.i.include_topic_filter, null);
        this.aPT = (TextView) view.findViewById(com.huluxia.bbs.b.g.tv_filter_active_time);
        this.aPS = (TextView) view.findViewById(com.huluxia.bbs.b.g.tv_filter_marrow);
        this.aPU = (TextView) view.findViewById(com.huluxia.bbs.b.g.tv_filter_create_time);
        Hf();
        this.aPT.setOnClickListener(new 2(this));
        this.aPS.setOnClickListener(new 3(this));
        this.aPU.setOnClickListener(new 4(this));
        this.aPR = new PopupWindow(view, -2, -2);
        this.aPR.setBackgroundDrawable(getResources().getDrawable(com.huluxia.bbs.b.f.bg_topic_filter));
        this.aPR.setFocusable(true);
        this.aPR.setOutsideTouchable(true);
        this.aPR.setOnDismissListener(new 5(this));
        this.aPP.getLocationInWindow(new int[2]);
        this.aPR.showAsDropDown(this.aPP, 0, 0);
    }

    private void a(View v, MENU_TOPIC_LIST filterType) {
        Hg();
        int id = v.getId();
        String filterName = "";
        if (id == com.huluxia.bbs.b.g.tv_filter_active_time) {
            this.aPT.setTextColor(getResources().getColor(com.huluxia.bbs.b.d.green_font));
            filterName = this.aPT.getText().toString();
        } else if (id == com.huluxia.bbs.b.g.tv_filter_marrow) {
            this.aPS.setTextColor(getResources().getColor(com.huluxia.bbs.b.d.green_font));
            filterName = this.aPS.getText().toString();
        } else if (id == com.huluxia.bbs.b.g.tv_filter_create_time) {
            this.aPU.setTextColor(getResources().getColor(com.huluxia.bbs.b.d.green_font));
            filterName = this.aPU.getText().toString();
        }
        this.aPQ.setText(filterName);
        a(filterType);
        GC();
    }

    public void a(MENU_TOPIC_LIST filterType) {
        this.aQm = filterType;
        this.aHX.setRefreshing(true);
        es("0");
    }

    private void Hf() {
        int color = d.getColor(this.mContext, com.huluxia.bbs.b.c.textColorGreen);
        if (this.aQm == MENU_TOPIC_LIST.FILTER_MARROW) {
            this.aPS.setTextColor(color);
        } else if (this.aQm == MENU_TOPIC_LIST.FILTER_ACTIVE_TIME) {
            this.aPT.setTextColor(color);
        } else {
            this.aPU.setTextColor(color);
        }
    }

    private void Hg() {
        int color = d.getColor(this.mContext, 16842809);
        this.aPS.setTextColor(color);
        this.aPT.setTextColor(color);
        this.aPU.setTextColor(color);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("category", this.aPE);
        outState.putParcelable("topic_info", this.aPI);
    }

    public void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
        EventNotifyCenter.remove(this.aQo);
        if (this.aIn != null) {
            com.huluxia.service.i.unregisterReceiver(this.aIn);
            this.aIn = null;
        }
        if (this.aIo != null) {
            com.huluxia.service.i.unregisterReceiver(this.aIo);
            this.aIo = null;
        }
    }

    public void b(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            this.aHX.setRefreshing(true);
        } else if (requestCode == 528 && resultCode == 529 && data != null && data.getBooleanExtra("ok", false) && !this.aQa) {
            this.aQa = true;
            this.aPX.w(j.ep().getUserid());
            this.aPX.execute();
        }
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == com.huluxia.bbs.b.g.ic_add_class) {
            Hi();
        } else if (id == com.huluxia.bbs.b.g.rly_header) {
            t.c(this.mContext, this.aPE.getCategoryID());
        } else if (id == com.huluxia.bbs.b.g.btn_daren) {
            t.d(this.mContext, this.aPE.getCategoryID());
        } else if (id == com.huluxia.bbs.b.g.btn_signin) {
            if (!j.ep().ey()) {
                t.b(this.mContext, 528, 529);
            } else if (this.aQe.getText().toString().equals("已签到")) {
                t.download_toast(this.mContext, "今日已签到，请明天再来");
            } else {
                this.aQc.setClickable(false);
                this.aPY.w(j.ep().getUserid());
                this.aPY.execute();
            }
        } else if (id == com.huluxia.bbs.b.g.btn_top) {
            this.aHX.setRefreshing(true);
            this.aQl.b(this.aQk, 500, 0);
        } else if (id == com.huluxia.bbs.b.g.btn_sidebar) {
            this.aQn.Hb();
        } else if (id != com.huluxia.bbs.b.g.btn_search) {
        } else {
            if (!j.ep().ey()) {
                t.an(this.mContext);
            } else if (j.ep().getLevel() < this.aPE.getIsSearch()) {
                t.download_toast(this.mContext, "抱歉！目前搜索只对" + this.aPE.getIsSearch() + "级以上的葫芦娃开放。");
            } else {
                t.b(this.mContext, this.aPE);
            }
        }
    }

    public void bl(long tag_id) {
        this.aPK = tag_id;
        this.aHX.setRefreshing(true);
        es("0");
    }

    private void Hh() {
        if (!j.ep().ey() || this.aPE == null) {
            this.aQh.setVisibility(4);
            return;
        }
        this.subscribeType = this.aPE.getSubscribeType();
        if (this.subscribeType == ESubscribeType.Invalid.ordinal() || this.subscribeType == ESubscribeType.ALWAYS.ordinal()) {
            this.aQh.setVisibility(4);
        } else if (this.aQi) {
            this.aQh.setVisibility(4);
        } else {
            this.aQh.setVisibility(0);
        }
    }

    private void Hi() {
        this.aQi = !this.aQi;
        this.aQh.setClickable(false);
        this.aPZ.G(this.aQi);
        this.aPZ.v(this.aPE.getCategoryID());
        this.aPZ.execute();
    }

    public void a(com.huluxia.http.base.d response) {
        super.a(response);
    }

    public void b(com.huluxia.http.base.d response) {
        boolean z = true;
        super.b(response);
        if (response.fe() == 3) {
            this.aQh.setClickable(true);
            if (this.aQi) {
                z = false;
            }
            this.aQi = z;
            Hh();
        }
    }

    public void cC(boolean isSubscribe) {
        this.aQi = isSubscribe;
        Hh();
    }

    public void c(com.huluxia.http.base.d response) {
        super.c(response);
        if (response.getStatus() != 1) {
            t.n(this.mContext, ab.n(response.fg(), response.fh()));
        } else if (response.fe() == 1) {
            this.aQa = true;
            if (this.aPX.fq()) {
                this.aQc.setClickable(true);
                this.aQe.setText(m.signed);
                return;
            }
            this.aQc.setClickable(true);
            this.aQe.setText(m.signin);
        } else if (response.fe() == 2) {
            if (response.getStatus() == 1) {
                this.aQc.setClickable(true);
                this.aQe.setText(m.signed);
                t.o(this.mContext, "签到成功！增加20经验值！");
                return;
            }
            this.aQc.setClickable(true);
            this.aQe.setText(m.signin);
        } else if (response.fe() == 3) {
            if (this.aQi) {
                t.o(this.mContext, "关注成功");
                this.aQh.setVisibility(4);
            } else {
                t.o(this.mContext, "已取消关注");
            }
            this.aQh.setClickable(true);
            this.aQn.cz(this.aQi);
        }
    }

    private void Hj() {
        int[] location = new int[2];
        this.aPW.getLocationInWindow(location);
        int top = location[1] + UtilsScreen.dipToPx(this.mContext, 46);
        new CaseView(this.mContext).a(new RectF(0.0f, (float) top, (float) UtilsScreen.getScreenWidth(this.mContext), (float) (UtilsScreen.dipToPx(this.mContext, 91) + top)), com.huluxia.bbs.b.f.img_guide_forum, UtilsScreen.dipToPx(this.mContext, 24), UtilsScreen.dipToPx(this.mContext, 45)).show();
    }

    private void es(String start) {
        if (UtilsFunction.empty((CharSequence) start)) {
            start = "0";
        }
        if (this.aPE == null) {
            Hk();
        } else if (this.aQm == null) {
            k.Ej().a(this.aPJ, this.aPK, 0, start, 20);
        } else {
            k.Ej().a(this.aPJ, this.aPK, this.aQm.ordinal(), start, 20);
        }
    }

    private void Hk() {
        if (this.aPE == null) {
            TopicModule.Ef().aZ(68);
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.aQn = (c) activity;
    }

    public void onDetach() {
        super.onDetach();
    }

    private void Fs() {
        if (this.aIg != null) {
            this.aIg.setVisibility(8);
        }
    }

    protected void Fr() {
        if (this.aIg != null) {
            MsgCounts msgCounts = HTApplication.bM();
            long allCount = msgCounts == null ? 0 : msgCounts.getAll();
            if (allCount > 0) {
                this.aIg.setVisibility(0);
                if (allCount > 99) {
                    this.aIg.setText("99+");
                    return;
                } else {
                    this.aIg.setText(String.valueOf(msgCounts.getAll()));
                    return;
                }
            }
            this.aIg.setVisibility(8);
        }
    }

    private void setCategory(TopicCategory tc) {
        boolean z = true;
        if (tc != null) {
            this.aPH.clear();
            this.aPE = tc;
            this.aPF.setTopicCategory(tc);
            if (this.aPE.getIsSubscribe() != 1) {
                z = false;
            }
            this.aQi = z;
            Hh();
            if (tc.getTags() == null || tc.getTags().size() <= 0) {
                this.aQn.c(null, null);
            } else {
                ArrayList<String> titles = new ArrayList();
                ArrayList<Long> ids = new ArrayList();
                for (int i = 0; i < tc.getTags().size(); i++) {
                    TagInfo data = (TagInfo) tc.getTags().get(i);
                    titles.add(data.getName());
                    ids.add(Long.valueOf(data.getID()));
                    if (0 != data.getID()) {
                        this.aPH.add(data);
                    }
                }
                this.aQn.c(ids, titles);
            }
            if (ah.KZ().Lp()) {
                Hj();
                ah.KZ().cY(false);
            }
        }
    }

    protected void EX() {
        super.EX();
        es("0");
        if (j.ep().ey()) {
            this.aPX.w(j.ep().getUserid());
            this.aPX.execute();
        }
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        if (this.aPG != null && (this.aPG instanceof com.simple.colorful.b)) {
            com.simple.colorful.setter.k setter = new com.simple.colorful.setter.j((ViewGroup) this.aHX.getRefreshableView());
            setter.a((com.simple.colorful.b) this.aPG);
            builder.a(setter);
        }
        builder.aY(16908290, com.huluxia.bbs.b.c.backgroundDefault).i(this.aIw, com.huluxia.bbs.b.c.backgroundTitleBar).a((TextView) this.aPN.findViewById(com.huluxia.bbs.b.g.topic_back), com.huluxia.bbs.b.c.drawableTitleBack, 1).a(this.aPQ, 16842809).a(this.aPQ, com.huluxia.bbs.b.c.drawableTopicSpinner, 2).c(this.aPW, com.huluxia.bbs.b.c.drawableTitlePost).bc(com.huluxia.bbs.b.g.img_msg, com.huluxia.bbs.b.c.drawableTitleMsg).a(this.aPF).j(this.aQg, com.huluxia.bbs.b.c.listSelector).c(this.aQk, com.huluxia.bbs.b.c.drawableReturnTop).c(this.aQj, com.huluxia.bbs.b.c.drawableRightSidebar);
    }

    protected void kj(int themeId) {
        super.kj(themeId);
        this.aPG.notifyDataSetChanged();
        this.aPM.setBackgroundResource(com.huluxia.bbs.b.f.sl_title_bar_button);
    }

    private void T(String title, String msg) {
        com.huluxia.widget.dialog.i dia = new com.huluxia.widget.dialog.i(this.mContext, null);
        dia.az(title, msg);
        dia.gK("朕知道了");
        dia.showDialog();
    }

    private void d(long cat_id, boolean isnext) {
        if (!this.aPg) {
            this.aPg = true;
            k.Ej().a(this.mContext, cat_id, TAG, isnext, null);
        }
    }
}
