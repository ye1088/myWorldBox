package com.huluxia.ui.bbs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huluxia.HTApplication;
import com.huluxia.bbs.b;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.m;
import com.huluxia.bbs.b.n;
import com.huluxia.data.PageList;
import com.huluxia.data.UserBaseInfo;
import com.huluxia.data.topic.CommentItem;
import com.huluxia.data.topic.SimpleTopicItem;
import com.huluxia.data.topic.TopicItem;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.utils.UtilsSystem;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.CommonMenuDialogListener;
import com.huluxia.http.base.f;
import com.huluxia.http.bbs.topic.d;
import com.huluxia.http.bbs.topic.i;
import com.huluxia.http.bbs.topic.q;
import com.huluxia.http.bbs.topic.r;
import com.huluxia.http.bbs.topic.s;
import com.huluxia.jni.UtilsEncrypt;
import com.huluxia.module.h;
import com.huluxia.module.topic.j;
import com.huluxia.module.topic.k;
import com.huluxia.module.w;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseLoadingActivity;
import com.huluxia.ui.bbs.TopicDetailPageTurnLayout.a;
import com.huluxia.ui.itemadapter.topic.TopicDetailItemAdapter;
import com.huluxia.ui.itemadapter.topic.TopicDetailItemAdapter.c;
import com.huluxia.utils.UtilsMenu;
import com.huluxia.utils.UtilsMenu.MENU_VALUE;
import com.huluxia.utils.ab;
import com.huluxia.utils.ad;
import com.huluxia.utils.ah;
import com.huluxia.utils.ao;
import com.huluxia.utils.as;
import com.huluxia.utils.at;
import com.huluxia.utils.au;
import com.huluxia.utils.o;
import com.huluxia.widget.Constants;
import com.huluxia.widget.Constants.PushMsgType;
import com.huluxia.widget.viewpager.PagerSlidingTabStrip;
import com.huluxia.widget.viewpager.WrapContentHeightViewPager;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TopicDetailActivity extends HTBaseLoadingActivity implements OnClickListener, OnItemClickListener, c {
    private static final String TAG = "TopicDetailActivity";
    public static final String aOM = "postID";
    public static final String aON = "PARA_HULU_TOPIC";
    public static final String aOO = "PARA_HULU_ID";
    private final int PAGE_SIZE = 20;
    private CommonMenuDialog aHI = null;
    private PullToRefreshListView aHX;
    private ArrayList<UserBaseInfo> aLZ;
    private ImageButton aMB;
    private ImageButton aMC;
    private Button aMD;
    private Button aME;
    private boolean aMG = false;
    private Activity aMn;
    private int aNA = 0;
    private int aNB = 0;
    private int aNC = 0;
    OnClickListener aND = new OnClickListener(this) {
        final /* synthetic */ TopicDetailActivity aPk;

        {
            this.aPk = this$0;
        }

        public void onClick(View v) {
            int i = v.getId();
            if (i == g.tvFirstPageBtn) {
                this.aPk.b(1, this.aPk.aMG, 0);
                this.aPk.GC();
            } else if (i == g.tvEndPageBtn) {
                this.aPk.b(this.aPk.aOU.getPageList().getTotalPage(), this.aPk.aMG, 0);
                this.aPk.GC();
            }
        }
    };
    a aNE = new a(this) {
        final /* synthetic */ TopicDetailActivity aPk;

        {
            this.aPk = this$0;
        }

        public void kz(int index) {
            this.aPk.b(index, this.aPk.aMG, 0);
            this.aPk.GC();
        }
    };
    private OnPageChangeListener aNF = new OnPageChangeListener(this) {
        final /* synthetic */ TopicDetailActivity aPk;

        {
            this.aPk = this$0;
        }

        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageSelected(int position) {
            if (!this.aPk.aMn.isFinishing()) {
                this.aPk.mPos = position;
            }
        }
    };
    private CommonMenuDialog aNG = null;
    private OnClickListener aNI = new OnClickListener(this) {
        final /* synthetic */ TopicDetailActivity aPk;

        {
            this.aPk = this$0;
        }

        public void onClick(View v) {
            int id = v.getId();
            if (id == g.num1) {
                this.aPk.aNK.setSelected(true);
                this.aPk.aNL.setSelected(false);
                this.aPk.aNM.setSelected(false);
                this.aPk.aNN.setSelected(false);
                this.aPk.GG();
                this.aPk.aNJ = "1";
            } else if (id == g.num2) {
                this.aPk.aNK.setSelected(false);
                this.aPk.aNL.setSelected(true);
                this.aPk.aNM.setSelected(false);
                this.aPk.aNN.setSelected(false);
                this.aPk.GG();
                this.aPk.aNJ = "2";
            } else if (id == g.num5) {
                this.aPk.aNK.setSelected(false);
                this.aPk.aNL.setSelected(false);
                this.aPk.aNM.setSelected(true);
                this.aPk.aNN.setSelected(false);
                this.aPk.GG();
                this.aPk.aNJ = "5";
            }
            this.aPk.GF();
        }
    };
    private String aNJ;
    private RadioButton aNK;
    private RadioButton aNL;
    private RadioButton aNM;
    private EditText aNN;
    private PopupWindow aNO;
    private String aNQ;
    private OnClickListener aNR = new OnClickListener(this) {
        final /* synthetic */ TopicDetailActivity aPk;

        {
            this.aPk = this$0;
        }

        public void onClick(View v) {
            int id = v.getId();
            if (id == g.tv_reason1) {
                this.aPk.aNQ = this.aPk.aMn.getResources().getString(m.topic_complaint_reason1);
            } else if (id == g.tv_reason2) {
                this.aPk.aNQ = this.aPk.aMn.getResources().getString(m.topic_complaint_reason2);
            } else if (id == g.tv_reason3) {
                this.aPk.aNQ = this.aPk.aMn.getResources().getString(m.topic_complaint_reason3);
            } else if (id == g.tv_reason4) {
                this.aPk.aNQ = this.aPk.aMn.getResources().getString(m.topic_complaint_reason4);
            } else if (id == g.tv_reason5) {
                this.aPk.aNQ = this.aPk.aMn.getResources().getString(m.topic_complaint_reason5);
            } else if (id == g.tv_reason6) {
                this.aPk.aNQ = this.aPk.aMn.getResources().getString(m.topic_complaint_reason6);
            } else if (id == g.tv_reason7) {
                this.aPk.aNQ = this.aPk.aMn.getResources().getString(m.topic_complaint_reason7);
            } else if (id == g.tv_reason8) {
                this.aPk.aNQ = this.aPk.aMn.getResources().getString(m.topic_complaint_reason8);
            }
            this.aPk.aPi.setText(this.aPk.aNQ);
            this.aPk.aPi.setSelection(this.aPk.aNQ.length());
            if (this.aPk.aNO != null && this.aPk.aNO.isShowing()) {
                this.aPk.aNO.dismiss();
            }
        }
    };
    private int aNe = 0;
    private int aNf = 0;
    private q aNm = new q();
    private PopupWindow aNy;
    private WrapContentHeightViewPager aNz;
    public final String aOP = "PARA_PAGENO";
    public final String aOQ = "PARA_PAGENO_HOST";
    public final String aOR = "PARA_ONLYHOST";
    public final String aOS = "PARA_REMINDUSERS";
    private TopicDetailTitle aOT;
    private TopicDetailItemAdapter aOU;
    private r aOV = new r();
    private d aOW = new d();
    private com.huluxia.http.bbs.topic.a aOX = new com.huluxia.http.bbs.topic.a();
    private i aOY = new i();
    private s aOZ = new s();
    private com.huluxia.http.other.g aPa = new com.huluxia.http.other.g();
    private ImageButton aPb;
    private ImageButton aPc;
    private boolean aPd = false;
    private boolean aPe = true;
    private long aPf;
    private boolean aPg = false;
    private CommentItem aPh = null;
    private EditText aPi;
    private CallbackHandler aPj = new CallbackHandler(this) {
        final /* synthetic */ TopicDetailActivity aPk;

        {
            this.aPk = this$0;
        }

        @MessageHandler(message = 600)
        public void onCompliant(boolean succ, String msg) {
            this.aPk.cs(false);
            if (succ) {
                t.o(this.aPk.aMn, msg);
            } else {
                t.n(this.aPk.aMn, msg);
            }
        }

        @MessageHandler(message = 626)
        public void onRecvTopicAuth(boolean succ, w info, long postId, boolean isAuth) {
            if (this.aPk.sK != null && this.aPk.sK.getPostID() == postId && isAuth != this.aPk.sK.isAuthention()) {
                this.aPk.cs(false);
                String msg;
                if (succ) {
                    msg = this.aPk.getResources().getString(isAuth ? m.topic_auth_succ : m.topic_unauth_succ);
                    this.aPk.sK.setAuthentication(isAuth);
                    t.o(this.aPk.aMn, msg);
                    this.aPk.k(1, this.aPk.aMG);
                    return;
                }
                if (info == null) {
                    msg = this.aPk.getResources().getString(isAuth ? m.topic_auth_failed : m.topic_unauth_failed);
                } else {
                    msg = info.msg;
                }
                t.n(this.aPk.aMn, msg);
            }
        }

        @MessageHandler(message = 644)
        public void onRecvTopicAuth(boolean succ, w info) {
            this.aPk.cs(false);
            if (info != null && info.isSucc()) {
                t.o(this.aPk.aMn, "赠送成功");
                this.aPk.k(this.aPk.aOU.getPageList().getCurrPageNo(), this.aPk.aMG);
            }
        }

        @MessageHandler(message = 648)
        public void onRecConf(com.huluxia.module.topic.g info, String tag, boolean next, Object clickitem) {
            boolean z = true;
            HLog.verbose(TopicDetailActivity.TAG, "info " + info, new Object[0]);
            if (this.aPk.aPg) {
                this.aPk.aPg = false;
                if (tag != null && tag.equals(TopicDetailActivity.TAG) && next) {
                    CommentItem commentItem = null;
                    if (clickitem != null && (clickitem instanceof CommentItem)) {
                        commentItem = (CommentItem) clickitem;
                    }
                    TopicDetailActivity topicDetailActivity;
                    TopicItem o;
                    if (info == null || !info.isSucc()) {
                        if (info != null) {
                            t.n(this.aPk.aMn, ab.n(info.code, info.msg));
                            return;
                        }
                        topicDetailActivity = this.aPk;
                        o = this.aPk.sK;
                        if (commentItem != null) {
                            z = false;
                        }
                        topicDetailActivity.a(o, commentItem, z);
                    } else if (info.ispower == 1) {
                        topicDetailActivity = this.aPk;
                        o = this.aPk.sK;
                        if (commentItem != null) {
                            z = false;
                        }
                        topicDetailActivity.a(o, commentItem, z);
                    } else {
                        this.aPk.T(info.title, info.message);
                    }
                }
            }
        }

        @MessageHandler(message = 768)
        public void onRecvTopicinfo(boolean succ, j info, int position, Context context) {
            if (context == this.aPk.aMn) {
                this.aPk.cs(false);
                if (!succ || info == null) {
                    if (info != null && info.code == 104) {
                        t.n(this.aPk.aMn, ab.n(info.code, info.msg));
                        this.aPk.finish();
                    }
                    this.aPk.GA();
                    return;
                }
                this.aPk.a(info.getPageList(), info.remindUsers, position);
            }
        }
    };
    private String categoryName;
    private int mPos = 0;
    private long postID = 0;
    private TopicItem sK;
    private boolean st = false;

    private class TopicDetailViewPagerAdapter extends PagerAdapter {
        public List<View> aOg;
        public List<String> aOh;
        final /* synthetic */ TopicDetailActivity aPk;

        public TopicDetailViewPagerAdapter(TopicDetailActivity topicDetailActivity, List<View> mListViews, List<String> mListTabTitle) {
            this.aPk = topicDetailActivity;
            this.aOg = mListViews;
            this.aOh = mListTabTitle;
        }

        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPager) arg0).removeView((View) this.aOg.get(arg1));
        }

        public int getCount() {
            return this.aOg == null ? 0 : this.aOg.size();
        }

        public Object instantiateItem(View arg0, int arg1) {
            ((ViewPager) arg0).addView((View) this.aOg.get(arg1), 0);
            return this.aOg.get(arg1);
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        public CharSequence getPageTitle(int position) {
            return (CharSequence) this.aOh.get(position);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(-3);
        this.aMn = this;
        getWindow().setFormat(-3);
        setContentView(b.i.activity_topic_content);
        if (savedInstanceState != null) {
            this.aPe = savedInstanceState.getBoolean(aON);
            this.aPf = savedInstanceState.getLong(aOO);
            this.aNe = savedInstanceState.getInt("PARA_PAGENO");
            this.aNf = savedInstanceState.getInt("PARA_PAGENO_HOST");
            this.aMG = savedInstanceState.getBoolean("PARA_ONLYHOST");
            this.aLZ = savedInstanceState.getParcelableArrayList("PARA_REMINDUSERS");
        }
        EventNotifyCenter.add(h.class, this.aPj);
        this.aJb = getIntent().getBooleanExtra(Constants.brP, false);
        this.sK = (TopicItem) getIntent().getSerializableExtra(com.huluxia.r.gO);
        if (this.sK == null) {
            this.postID = getIntent().getLongExtra(aOM, 0);
            this.categoryName = getIntent().getStringExtra("categoryName");
        } else {
            this.postID = this.sK.getPostID();
            this.categoryName = this.sK.getCategoryName();
        }
        ej(this.categoryName);
        int model = getIntent().getIntExtra(Constants.brO, 0);
        if (model != 0) {
            com.huluxia.service.h.EC().bh(this.postID);
            com.huluxia.r.ck().i(model, PushMsgType.TOPIC.Value());
        }
        Log.i(TAG, Long.toString(this.postID));
        this.aOU = new TopicDetailItemAdapter(this);
        this.aOU.a((c) this);
        this.aOT = new TopicDetailTitle(this);
        this.aOT.setTopicDetail(this.sK);
        this.aHX = (PullToRefreshListView) findViewById(g.listViewData);
        ((ListView) this.aHX.getRefreshableView()).addHeaderView(this.aOT);
        this.aHX.setAdapter(this.aOU);
        this.aHX.setOnItemClickListener(this);
        this.aHX.setOnRefreshListener(new OnRefreshListener<ListView>(this) {
            final /* synthetic */ TopicDetailActivity aPk;

            {
                this.aPk = this$0;
            }

            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                this.aPk.Gz();
                if (this.aPk.aOU.HS()) {
                    this.aPk.aOU.HQ();
                }
                if (this.aPk.aOU.HR()) {
                    this.aPk.aOU.HK();
                    this.aPk.aOU.HQ();
                }
            }
        });
        ((ListView) this.aHX.getRefreshableView()).setOnTouchListener(new com.huluxia.widget.horizontalscroller.a(this, this.aMn) {
            final /* synthetic */ TopicDetailActivity aPk;

            public void GZ() {
                int videoHeight = this.aPk.GV();
                int titleBarHeight = at.dipToPx(this.aPk.aMn, 46);
                if (videoHeight != 0 && videoHeight > titleBarHeight) {
                    this.aPk.GX();
                }
            }

            public void Ha() {
                int videoHeight = this.aPk.GV();
                int titleBarHeight = at.dipToPx(this.aPk.aMn, 46);
                if (videoHeight != 0 && videoHeight < titleBarHeight) {
                    this.aPk.GW();
                }
                if (videoHeight < (-at.dipToPx(this.aPk.aMn, com.huluxia.video.recorder.b.bpd))) {
                    this.aPk.GY();
                }
            }
        });
        this.aOV.bb(2);
        this.aOV.a(this);
        this.aOW.bb(3);
        this.aOW.a(this);
        this.aOY.bb(5);
        this.aOY.a(this);
        this.aOY.x(this.postID);
        this.aNm.bb(6);
        this.aNm.x(this.postID);
        this.aNm.a(this);
        this.aOZ.bb(7);
        this.aOZ.x(this.postID);
        this.aOZ.a(this);
        FM();
        GQ();
        Fy();
        k(1, this.aMG);
        if (HTApplication.bV() == null) {
            this.aPa = new com.huluxia.http.other.g();
            this.aPa.bb(11);
            this.aPa.a(this);
            this.aPa.eX();
        }
        c(this.sK, this.categoryName);
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(aON, this.aPe);
        savedInstanceState.putLong(aOO, this.aPf);
        savedInstanceState.putInt("PARA_PAGENO", this.aNe);
        savedInstanceState.putInt("PARA_PAGENO_HOST", this.aNf);
        savedInstanceState.putBoolean("PARA_ONLYHOST", this.aMG);
        savedInstanceState.putParcelableArrayList("PARA_REMINDUSERS", this.aLZ);
    }

    public void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.aPj);
        GY();
        this.aOU.HL();
    }

    protected void onStop() {
        super.onStop();
        GY();
    }

    private void FM() {
        this.aPb = (ImageButton) findViewById(g.sys_header_flright_img);
        this.aPb.setVisibility(0);
        this.aPb.setOnClickListener(this);
        Gv();
        Gt();
        this.aPc = (ImageButton) findViewById(g.header_flright_second_img);
        this.aPc.setVisibility(0);
        this.aPc.setOnClickListener(this);
        GS();
    }

    private void Gt() {
        if (com.huluxia.data.j.ep().ey()) {
            this.aOX.a(this);
            this.aOX.x(this.postID);
            this.aOX.a(new f(this) {
                final /* synthetic */ TopicDetailActivity aPk;

                {
                    this.aPk = this$0;
                }

                public void a(com.huluxia.http.base.d response) {
                }

                public void b(com.huluxia.http.base.d response) {
                }

                public void c(com.huluxia.http.base.d response) {
                    this.aPk.st = this.aPk.aOX.isFavorite();
                    this.aPk.Gv();
                }
            });
            this.aOX.execute();
        }
    }

    private void GQ() {
        findViewById(g.btn_comment).setOnClickListener(this);
        this.aMB = (ImageButton) findViewById(g.btn_prev);
        this.aMC = (ImageButton) findViewById(g.btn_next);
        this.aMD = (Button) findViewById(g.btn_page);
        this.aME = (Button) findViewById(g.btn_comment);
        this.aMB.setOnClickListener(this);
        this.aMC.setOnClickListener(this);
        this.aMD.setOnClickListener(this);
        this.aMD.setText("1/1");
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != -1) {
            return;
        }
        if (this.aMG) {
            cx(this.aMG);
        } else {
            cx(this.aMG);
        }
    }

    private void cx(boolean isOnlyFloorHost) {
        k(isOnlyFloorHost ? this.aNf : this.aNe, isOnlyFloorHost);
    }

    private void k(int page, boolean isOnlyFloorHost) {
        int position = 0;
        if (this.aHX.getRefreshableView() != null) {
            position = ((ListView) this.aHX.getRefreshableView()).getFirstVisiblePosition();
        }
        b(page, isOnlyFloorHost, position);
    }

    private void b(int page, boolean isOnlyFloorHost, int position) {
        if (getCurrentPage() == 2) {
            cs(true);
        }
        if (isOnlyFloorHost) {
            this.aNf = page;
            k.Ej().a(this.postID, this.aNf, 20, true, position, this.aMn);
        } else {
            this.aNe = page;
            k.Ej().a(this.postID, this.aNe, 20, false, position, this.aMn);
        }
        this.aMB.setEnabled(false);
        this.aMC.setEnabled(false);
        this.aMD.setEnabled(false);
        this.aME.setEnabled(false);
        ek(String.format(Locale.getDefault(), "加载第%d页", new Object[]{Integer.valueOf(page)}));
    }

    private void Gz() {
        int page;
        if (getCurrentPage() == 2) {
            cs(true);
        }
        this.aMB.setEnabled(false);
        this.aMC.setEnabled(false);
        this.aMD.setEnabled(false);
        this.aME.setEnabled(false);
        if (this.aMG) {
            k.Ej().a(this.postID, this.aNf, 20, true, 0, this.aMn);
            page = this.aNf;
        } else {
            k.Ej().a(this.postID, this.aNe, 20, false, 0, this.aMn);
            page = this.aNe;
        }
        ek(String.format(Locale.getDefault(), "加载第%d页", new Object[]{Integer.valueOf(page)}));
    }

    private void GA() {
        int currPage = this.aOU.getPageList().getCurrPageNo();
        int totalPage = this.aOU.getPageList().getTotalPage();
        if (currPage > 1) {
            this.aMB.setEnabled(true);
        }
        if (currPage < totalPage) {
            this.aMC.setEnabled(true);
        }
        if (totalPage > 1) {
            this.aMD.setEnabled(true);
        }
        b(this.sK);
        if (getCurrentPage() == 0) {
            FB();
        } else {
            t.n(this, "加载评论失败\n网络问题");
        }
    }

    private void a(PageList page, List<UserBaseInfo> users, int position) {
        this.aHX.onRefreshComplete();
        this.aOU.getPageList().clear();
        this.aOU.HG();
        if (page.size() > 0 && (page.get(0) instanceof TopicItem)) {
            if (!(this.sK == null || this.categoryName == null)) {
                this.aPd = true;
            }
            this.sK = (TopicItem) page.get(0);
            this.aLZ = (ArrayList) users;
            ej(this.sK.getCategory().getTitle());
            this.aOT.setTopicDetail(this.sK);
            this.aOU.setTopicCategory(this.sK.getCategory());
            c(this.sK, this.sK.getCategory().getTitle());
        }
        int currPage = page.getCurrPageNo();
        int totalPage = page.getTotalPage() < 1 ? 1 : page.getTotalPage();
        this.aOU.getPageList().addAll(page);
        this.aOU.getPageList().setCurrPageNo(currPage);
        this.aOU.getPageList().setTotalPage(totalPage);
        this.aOU.getPageList().setPageSize(page.getPageSize());
        this.aOU.getPageList().setRemindUsers(page.getRemindUsers());
        this.aOU.notifyDataSetChanged();
        this.aMD.setText(String.format(Locale.getDefault(), "%d/%d", new Object[]{Integer.valueOf(currPage), Integer.valueOf(totalPage)}));
        if (currPage > 1) {
            this.aMB.setEnabled(true);
        }
        if (currPage < totalPage) {
            this.aMC.setEnabled(true);
        }
        this.aMD.setEnabled(true);
        if (this.aHX.getRefreshableView() != null && ((ListView) this.aHX.getRefreshableView()).getChildCount() > position) {
            ((ListView) this.aHX.getRefreshableView()).setSelection(position);
        }
        b(this.sK);
        if (getCurrentPage() == 0) {
            FC();
        }
    }

    private void b(TopicItem topic) {
        if (topic != null) {
            switch (topic.getState()) {
                case 1:
                    this.aME.setText("评论");
                    this.aME.setEnabled(true);
                    return;
                case 2:
                    this.aME.setText("已删除");
                    this.aME.setEnabled(false);
                    return;
                case 3:
                    this.aME.setText("已锁定");
                    this.aME.setEnabled(false);
                    return;
                default:
                    return;
            }
        }
    }

    public void b(com.huluxia.http.base.d response) {
        cs(false);
        this.aHX.onRefreshComplete();
        switch (response.fe()) {
            case 1:
            case 9:
                if (response.fg() == 104) {
                    t.n(this, ab.n(response.fg(), response.fh()));
                }
                GA();
                return;
            case 2:
                t.n(this, "删除话题失败\n网络问题");
                return;
            case 3:
                t.n(this, "删除回复失败\n网络问题");
                return;
            case 5:
                if (this.aOY.isFavorite()) {
                    t.n(this, "收藏失败\n网络问题");
                    return;
                } else {
                    t.n(this, "取消收藏失败\n网络问题");
                    return;
                }
            case 6:
                t.n(this, "锁定话题失败");
                return;
            case 7:
                t.n(this, "解锁话题失败");
                return;
            case 8:
                t.o(this, "举报失败，请重试");
                return;
            case 10:
                t.n(this, "赠送葫芦失败\n网络问题");
                return;
            default:
                return;
        }
    }

    public void c(com.huluxia.http.base.d response) {
        cs(false);
        this.aHX.onRefreshComplete();
        if (response.getStatus() == 1) {
            switch (response.fe()) {
                case 2:
                    t.o(this, "删除话题成功");
                    finish();
                    return;
                case 3:
                    t.o(this, "删除回复成功");
                    k(this.aOU.getPageList().getCurrPageNo(), this.aMG);
                    return;
                case 5:
                    if (this.aOY.isFavorite()) {
                        t.o(this, "收藏成功");
                    } else {
                        t.o(this, "取消收藏成功");
                    }
                    this.st = this.aOY.isFavorite();
                    Gv();
                    return;
                case 6:
                    t.o(this, "锁定话题成功");
                    if (this.sK != null) {
                        this.sK.setState(3);
                    }
                    cx(this.aMG);
                    return;
                case 7:
                    t.o(this, "解锁话题成功");
                    if (this.sK != null) {
                        this.sK.setState(1);
                    }
                    cx(this.aMG);
                    return;
                case 8:
                    t.o(this, "举报成功，等待处理");
                    return;
                case 10:
                    t.o(this, "赠送成功");
                    k(this.aOU.getPageList().getCurrPageNo(), this.aMG);
                    return;
                case 11:
                    HTApplication.A((String) response.getData());
                    return;
                default:
                    return;
            }
        } else if (response.fe() >= 2 && response.fe() <= 10) {
            t.n(this, ab.n(response.fg(), response.fh()));
        }
    }

    private void Gu() {
        if (com.huluxia.data.j.ep().ey()) {
            ek("请求处理中..");
            this.aOY.J(!this.st);
            this.aOY.execute();
            return;
        }
        t.an(this);
    }

    private void Gv() {
        if (this.st) {
            this.aPb.setImageResource(com.simple.colorful.d.r(this, b.c.drawableTitleFavorChecked));
        } else {
            this.aPb.setImageResource(com.simple.colorful.d.r(this, b.c.drawableTitleFavor));
        }
    }

    private void GR() {
        ek("请求处理中..");
        this.aMG = !this.aMG;
        GS();
        if (this.aMG) {
            b(1, this.aMG, 0);
        } else {
            b(1, this.aMG, 0);
        }
    }

    private void GS() {
        if (this.aMG) {
            this.aPc.setImageResource(com.simple.colorful.d.r(this, b.c.drawableTitleOnlyFloorChecked));
        } else {
            this.aPc.setImageResource(com.simple.colorful.d.r(this, b.c.drawableTitleOnlyFloor));
        }
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == g.sys_header_flright_img) {
            Gu();
        } else if (id == g.header_flright_second_img) {
            GR();
        } else if (id == g.btn_comment) {
            b(this.aPh, true);
        } else if (id == g.btn_prev) {
            b(this.aOU.getPageList().getCurrPageNo() - 1, this.aMG, 0);
        } else if (id == g.btn_next) {
            b(this.aOU.getPageList().getCurrPageNo() + 1, this.aMG, 0);
        } else if (id == g.btn_page && this.aOU.getPageList().getTotalPage() > 1) {
            GD();
        }
        GW();
    }

    public void GC() {
        if (this.aNy != null && this.aNy.isShowing()) {
            this.aNy.dismiss();
            this.aNy = null;
        }
    }

    private void GD() {
        List<View> mListOfView = new ArrayList();
        List<String> mListOfTabTitle = new ArrayList();
        int mCurIndex = this.aOU.getPageList().getCurrPageNo();
        int mDataCount = this.aOU.getPageList().getTotalPage();
        GC();
        View pageTurnView = getLayoutInflater().inflate(b.i.topic_detail_popupwindow_page_turn, null);
        this.aNy = new PopupWindow(this);
        this.aNy.setWidth(-1);
        this.aNy.setHeight(-2);
        this.aNy.setBackgroundDrawable(new ColorDrawable(getResources().getColor(b.d.transparent)));
        this.aNy.setContentView(pageTurnView);
        this.aNy.setFocusable(true);
        this.aNy.setAnimationStyle(n.topic_detail_pageturn_popwindow_anim_style);
        this.aNy.showAtLocation(this.aMn.getWindow().getDecorView(), 80, 0, 0);
        this.aNy.setTouchable(true);
        this.aNy.setOutsideTouchable(true);
        this.aNz = (WrapContentHeightViewPager) pageTurnView.findViewById(g.vpLocalResMgrViewPager);
        this.aNz.setShowHighMultiple(4);
        TextView mTvFirstPageBtn = (TextView) pageTurnView.findViewById(g.tvFirstPageBtn);
        TextView mTvEndPageBtn = (TextView) pageTurnView.findViewById(g.tvEndPageBtn);
        mTvFirstPageBtn.setOnClickListener(this.aND);
        mTvEndPageBtn.setOnClickListener(this.aND);
        PagerSlidingTabStrip mLocalResMgrTabs = (PagerSlidingTabStrip) pageTurnView.findViewById(g.pstsLocalResMgrTabs);
        mLocalResMgrTabs.setTextColorResource(b.d.TabStripTextColor);
        mLocalResMgrTabs.setTextSize(at.dipToPx(this, 15));
        if (com.simple.colorful.d.isDayMode()) {
            mLocalResMgrTabs.setIndicatorColor(getResources().getColor(b.d.TabStripIndicatorColor));
        } else {
            mLocalResMgrTabs.setIndicatorColor(getResources().getColor(b.d.TabStripIndicatorNightColor));
        }
        mLocalResMgrTabs.setDividerColor(0);
        mLocalResMgrTabs.setShouldExpand(false);
        mLocalResMgrTabs.setOnPageChangeListener(this.aNF);
        mLocalResMgrTabs.setDividerPadding(10);
        mLocalResMgrTabs.setIndicatorTextColor(true);
        mLocalResMgrTabs.setIndicatorHeight(8);
        int mDataPage = mDataCount / 20;
        int mDataPageTailCnt = mDataCount % 20;
        for (int i = 0; i < mDataPage; i++) {
            TopicDetailPageTurnLayout mPageTurnLayout01 = new TopicDetailPageTurnLayout(this.aMn, mCurIndex, (i * 20) + 1, (i * 20) + 20, 20);
            mPageTurnLayout01.setOnPageItemClick(this.aNE);
            mListOfView.add(mPageTurnLayout01);
            mListOfTabTitle.add(String.format("%d--%d", new Object[]{Integer.valueOf((i * 20) + 1), Integer.valueOf((i * 20) + 20)}));
        }
        if (mDataPageTailCnt > 0) {
            mPageTurnLayout01 = new TopicDetailPageTurnLayout(this.aMn, mCurIndex, (mDataPage * 20) + 1, (mDataPage * 20) + mDataPageTailCnt, 20);
            mPageTurnLayout01.setOnPageItemClick(this.aNE);
            mListOfView.add(mPageTurnLayout01);
            this.mPos = (mCurIndex - 1) / 20;
            this.aNz.setCurrentItem(this.mPos);
            if (mDataCount <= 20) {
                mLocalResMgrTabs.setVisibility(8);
            }
            mListOfTabTitle.add(String.format("%d--%d", new Object[]{Integer.valueOf((mDataPage * 20) + 1), Integer.valueOf((mDataPage * 20) + mDataPageTailCnt)}));
        }
        this.aNz.setAdapter(new TopicDetailViewPagerAdapter(this, mListOfView, mListOfTabTitle));
        mLocalResMgrTabs.setViewPager(this.aNz);
        this.mPos = (mCurIndex - 1) / 20;
        this.aNz.setCurrentItem(this.mPos);
        if (mDataCount <= 20) {
            mLocalResMgrTabs.setVisibility(8);
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View arg1, int position, long arg3) {
    }

    public void a(boolean isFloor, CommentItem item) {
        if (this.sK != null) {
            if (this.aHI == null || !this.aHI.isDialogShowing()) {
                CommonMenuDialogListener mMenuListener = new CommonMenuDialogListener(this) {
                    final /* synthetic */ TopicDetailActivity aPk;

                    {
                        this.aPk = this$0;
                    }

                    public void pressMenuById(int inIndex, Object inItem) {
                        this.aPk.aHI.dismissDialog();
                        if (inIndex == MENU_VALUE.COMMENT.ordinal()) {
                            this.aPk.b(this.aPk.aPh, true);
                        } else if (inIndex == MENU_VALUE.SEND_HULU.ordinal()) {
                            if (com.huluxia.data.j.ep().ey()) {
                                this.aPk.GT();
                            } else {
                                t.an(this.aPk);
                            }
                        } else if (inIndex == MENU_VALUE.SHAREWIXIN.ordinal()) {
                            if (HTApplication.bV() != null) {
                                this.aPk.GE();
                            } else {
                                t.show_toast(this.aPk, "暂时无法分享");
                            }
                        } else if (inIndex == MENU_VALUE.MOVETOPIC.ordinal()) {
                            t.a(this.aPk, this.aPk.sK);
                        } else if (inIndex == MENU_VALUE.UNLOCK_TOPIC.ordinal()) {
                            this.aPk.ek("请求处理中..");
                            this.aPk.aOZ.x(this.aPk.sK.getPostID());
                            this.aPk.aOZ.execute();
                        } else if (inIndex == MENU_VALUE.EDITTOPIC.ordinal()) {
                            t.a(this.aPk, this.aPk.sK, this.aPk.aLZ);
                        } else if (inIndex == MENU_VALUE.LOCK_TOPIC.ordinal()) {
                            this.aPk.GJ();
                        } else if (inIndex == MENU_VALUE.REMOVE_TOPIC.ordinal() || inIndex == MENU_VALUE.REMOVE_COMMENT.ordinal()) {
                            this.aPk.ky(inIndex);
                        } else if (inIndex == MENU_VALUE.REPLY.ordinal()) {
                            this.aPk.b(this.aPk.aPh, false);
                        } else if (inIndex == MENU_VALUE.COPY_TEXT.ordinal()) {
                            if (this.aPk.aPh != null) {
                                o.bV(this.aPk.aPh.getText());
                            } else {
                                o.bV(this.aPk.sK.getDetail());
                            }
                        } else if (inIndex == MENU_VALUE.REPORT_TOPIC.ordinal()) {
                            this.aPk.a(this.aPk.sK, null);
                        } else if (inIndex == MENU_VALUE.REPORT_COMMENT.ordinal()) {
                            this.aPk.a(this.aPk.sK, this.aPk.aPh);
                        } else if (inIndex != MENU_VALUE.AUTHENTICATE_TOPIC.ordinal()) {
                        } else {
                            if (this.aPk.sK.isAuthention()) {
                                this.aPk.c(this.aPk.sK);
                            } else {
                                this.aPk.ky(inIndex);
                            }
                        }
                    }
                };
                if (isFloor) {
                    this.aHI = UtilsMenu.a((Context) this, this.sK, false, mMenuListener);
                    this.aPh = null;
                    this.aPf = this.sK.getPostID();
                    this.aPe = true;
                } else {
                    this.aPh = item;
                    if (this.aPh.getState() != 2) {
                        this.aHI = UtilsMenu.a((Context) this, this.sK, this.aPh, mMenuListener);
                        this.aPf = this.aPh.getCommentID();
                        this.aPe = false;
                    } else {
                        return;
                    }
                }
                this.aHI.updateCurFocusIndex(-1);
                this.aHI.showMenu(null, null);
            }
        }
    }

    private void GF() {
        Drawable o;
        RadioButton radioButton = this.aNK;
        if (this.aNK.isSelected()) {
            o = com.simple.colorful.d.o(this.aMn, b.c.drawableTopicSendhuluSelected);
        } else {
            o = com.simple.colorful.d.o(this.aMn, b.c.drawableTopicSendhulu);
        }
        radioButton.setBackgroundDrawable(o);
        radioButton = this.aNL;
        if (this.aNL.isSelected()) {
            o = com.simple.colorful.d.o(this.aMn, b.c.drawableTopicSendhuluSelected);
        } else {
            o = com.simple.colorful.d.o(this.aMn, b.c.drawableTopicSendhulu);
        }
        radioButton.setBackgroundDrawable(o);
        radioButton = this.aNM;
        if (this.aNM.isSelected()) {
            o = com.simple.colorful.d.o(this.aMn, b.c.drawableTopicSendhuluSelected);
        } else {
            o = com.simple.colorful.d.o(this.aMn, b.c.drawableTopicSendhulu);
        }
        radioButton.setBackgroundDrawable(o);
        EditText editText = this.aNN;
        if (this.aNN.isSelected()) {
            o = com.simple.colorful.d.o(this.aMn, b.c.drawableTopicSendhuluSelected);
        } else {
            o = com.simple.colorful.d.o(this.aMn, b.c.drawableTopicSendhulu);
        }
        editText.setBackgroundDrawable(o);
    }

    private void GG() {
        this.aNN.clearFocus();
        this.aNN.getEditableText().clear();
        this.aNN.getEditableText().clearSpans();
        this.aNN.setText("");
    }

    private void GT() {
        final Dialog dialog = new Dialog(this, com.simple.colorful.d.RD());
        View layout = LayoutInflater.from(this).inflate(b.i.include_credit_send, null);
        this.aNK = (RadioButton) layout.findViewById(g.num1);
        this.aNL = (RadioButton) layout.findViewById(g.num2);
        this.aNM = (RadioButton) layout.findViewById(g.num5);
        this.aNK.setSelected(true);
        this.aNJ = "1";
        this.aNN = (EditText) layout.findViewById(g.other_num);
        this.aNN.setVisibility(8);
        GF();
        if (com.huluxia.data.j.ep().ey()) {
            com.huluxia.data.g info = com.huluxia.data.j.ep().ew();
            HLog.info(TAG, "isgold %d", Integer.valueOf(info.isgold));
            if (info != null && info.isgold == 1) {
                this.aNN.setVisibility(0);
            }
        }
        this.aNK.setOnClickListener(this.aNI);
        this.aNL.setOnClickListener(this.aNI);
        this.aNM.setOnClickListener(this.aNI);
        this.aNN.setOnFocusChangeListener(new OnFocusChangeListener(this) {
            final /* synthetic */ TopicDetailActivity aPk;

            {
                this.aPk = this$0;
            }

            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    this.aPk.aNN.setSelected(true);
                    this.aPk.aNK.setSelected(false);
                    this.aPk.aNL.setSelected(false);
                    this.aPk.aNM.setSelected(false);
                }
                this.aPk.GF();
            }
        });
        final EditText reason = (EditText) layout.findViewById(g.content_text);
        dialog.setContentView(layout);
        dialog.show();
        layout.findViewById(g.tv_cancel).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ TopicDetailActivity aPk;

            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        layout.findViewById(g.tv_confirm).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ TopicDetailActivity aPk;

            public void onClick(View arg0) {
                if (this.aPk.aNN.isSelected()) {
                    String input = this.aPk.aNN.getText().toString();
                    if (ad.empty((CharSequence) input)) {
                        this.aPk.aNJ = "";
                    } else {
                        this.aPk.aNJ = input;
                    }
                }
                if (this.aPk.aNJ.length() <= 0 || "0".equals(this.aPk.aNJ)) {
                    t.n(this.aPk, "请填入正确数字");
                    return;
                }
                String score_txt = reason.getText() == null ? "" : reason.getText().toString();
                if (score_txt.trim().length() < 5) {
                    t.n(this.aPk, "理由不能少于5个字符");
                    return;
                }
                this.aPk.cs(true);
                HLog.debug(TopicDetailActivity.TAG, "hulu is : " + this.aPk.aNJ, new Object[0]);
                com.huluxia.module.account.a.DU().a(this.aPk.aPf, this.aPk.aPe, this.aPk.aNJ, score_txt);
                dialog.dismiss();
            }
        });
    }

    private void GJ() {
        final Dialog dialog = new Dialog(this, com.simple.colorful.d.RD());
        View layout = LayoutInflater.from(this).inflate(b.i.include_topic_lock_dialog, null);
        final LinearLayout reasonLayout = (LinearLayout) layout.findViewById(g.ll_reason);
        final ImageView arrowImg = (ImageView) layout.findViewById(g.iv_arrow);
        this.aPi = (EditText) layout.findViewById(g.tv_reason);
        this.aPi.setText("");
        this.aNQ = this.aMn.getResources().getString(m.topic_complaint_reason1);
        arrowImg.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ TopicDetailActivity aPk;

            public void onClick(View v) {
                this.aPk.aPi.requestFocus();
                at.hideInputMethod(this.aPk.aPi);
                if (this.aPk.aNO == null || !this.aPk.aNO.isShowing()) {
                    View popupView = this.aPk.getLayoutInflater().inflate(b.i.include_topic_lock_reason, null);
                    popupView.findViewById(g.tv_reason1).setOnClickListener(this.aPk.aNR);
                    popupView.findViewById(g.tv_reason2).setOnClickListener(this.aPk.aNR);
                    popupView.findViewById(g.tv_reason3).setOnClickListener(this.aPk.aNR);
                    popupView.findViewById(g.tv_reason4).setOnClickListener(this.aPk.aNR);
                    popupView.findViewById(g.tv_reason5).setOnClickListener(this.aPk.aNR);
                    popupView.findViewById(g.tv_reason6).setOnClickListener(this.aPk.aNR);
                    popupView.findViewById(g.tv_reason7).setOnClickListener(this.aPk.aNR);
                    popupView.findViewById(g.tv_reason8).setOnClickListener(this.aPk.aNR);
                    arrowImg.setImageDrawable(com.simple.colorful.d.o(this.aPk.aMn, b.c.drawableComplaintUp));
                    this.aPk.aNO = new PopupWindow(popupView, reasonLayout.getWidth(), at.dipToPx(this.aPk.aMn, 150));
                    this.aPk.aNO.update();
                    this.aPk.aNO.setTouchable(true);
                    this.aPk.aNO.setOutsideTouchable(true);
                    this.aPk.aNO.setBackgroundDrawable(new BitmapDrawable());
                    this.aPk.aNO.setFocusable(true);
                    this.aPk.aNO.setClippingEnabled(false);
                    this.aPk.aNO.showAsDropDown(reasonLayout, 0, at.dipToPx(this.aPk.aMn, 5));
                    this.aPk.aNO.setOnDismissListener(new 1(this));
                    return;
                }
                this.aPk.aNO.dismiss();
            }
        });
        dialog.setContentView(layout);
        dialog.show();
        layout.findViewById(g.tv_cancel).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ TopicDetailActivity aPk;

            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        layout.findViewById(g.tv_confirm).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ TopicDetailActivity aPk;

            public void onClick(View arg0) {
                if (this.aPk.aPi.getText().toString().trim().length() < 2) {
                    t.n(this.aPk, "理由不能少于2个字符");
                    return;
                }
                this.aPk.ek("请求处理中..");
                this.aPk.aNm.x(this.aPk.postID);
                this.aPk.aNm.aP(this.aPk.aPi.getText().toString().trim());
                this.aPk.aNm.eY();
                dialog.dismiss();
            }
        });
    }

    private void ky(final int opt) {
        if (opt == MENU_VALUE.REMOVE_TOPIC.ordinal() || opt == MENU_VALUE.REMOVE_COMMENT.ordinal() || opt == MENU_VALUE.AUTHENTICATE_TOPIC.ordinal()) {
            boolean isModerator = as.a(com.huluxia.data.j.ep().getUserid(), this.sK.getCategory().getModerator());
            if ((com.huluxia.data.j.ep().getRole() == 1 || isModerator) && opt == MENU_VALUE.REMOVE_TOPIC.ordinal()) {
                GU();
                return;
            }
            final Dialog dialog = new Dialog(this, com.simple.colorful.d.RD());
            View layout = LayoutInflater.from(this).inflate(b.i.include_dialog_two, null);
            TextView tv_msg = (TextView) layout.findViewById(g.tv_msg);
            if (opt == MENU_VALUE.REMOVE_TOPIC.ordinal()) {
                tv_msg.setText("确认删除话题吗？");
            } else if (opt == MENU_VALUE.REMOVE_COMMENT.ordinal()) {
                tv_msg.setText("确认删除回复吗？");
            } else if (opt == MENU_VALUE.AUTHENTICATE_TOPIC.ordinal()) {
                tv_msg.setText("是否对此贴认证？");
            }
            dialog.setContentView(layout);
            dialog.show();
            layout.findViewById(g.tv_cancel).setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ TopicDetailActivity aPk;

                public void onClick(View arg0) {
                    dialog.dismiss();
                }
            });
            layout.findViewById(g.tv_confirm).setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ TopicDetailActivity aPk;

                public void onClick(View arg0) {
                    dialog.dismiss();
                    if (opt == MENU_VALUE.REMOVE_TOPIC.ordinal()) {
                        this.aPk.ek("请求处理中..");
                        this.aPk.aOV.x(this.aPk.postID);
                        this.aPk.aOV.execute();
                    } else if (opt == MENU_VALUE.REMOVE_COMMENT.ordinal()) {
                        this.aPk.ek("请求处理中..");
                        this.aPk.aOW.y(this.aPk.aPh.getCommentID());
                        this.aPk.aOW.execute();
                    } else if (opt == MENU_VALUE.AUTHENTICATE_TOPIC.ordinal()) {
                        this.aPk.c(this.aPk.sK);
                    }
                }
            });
        }
    }

    private void a(final TopicItem topic, final CommentItem comment) {
        this.aNG = UtilsMenu.a((Context) this, false, new CommonMenuDialogListener(this) {
            final /* synthetic */ TopicDetailActivity aPk;

            public void pressMenuById(int inIndex, Object inItem) {
                this.aPk.aNG.dismissDialog();
                if (comment == null) {
                    this.aPk.ek("正在提交举报");
                    this.aPk.cs(true);
                    com.huluxia.module.profile.g.Eb().m(topic.getPostID(), inIndex);
                    return;
                }
                this.aPk.ek("正在提交举报");
                this.aPk.cs(true);
                com.huluxia.module.profile.g.Eb().n(comment.getCommentID(), inIndex);
            }
        });
        this.aNG.updateCurFocusIndex(-1);
        this.aNG.showMenu(null, null);
    }

    private void GU() {
        final Dialog dialog = new Dialog(this, com.simple.colorful.d.RD());
        View layout = LayoutInflater.from(this).inflate(b.i.include_topic_lock_dialog, null);
        final LinearLayout reasonLayout = (LinearLayout) layout.findViewById(g.ll_reason);
        final ImageView arrowImg = (ImageView) layout.findViewById(g.iv_arrow);
        ((TextView) layout.findViewById(g.title)).setText("删除帖子");
        this.aPi = (EditText) layout.findViewById(g.tv_reason);
        this.aPi.setText("");
        arrowImg.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ TopicDetailActivity aPk;

            public void onClick(View v) {
                this.aPk.aPi.requestFocus();
                at.hideInputMethod(this.aPk.aPi);
                if (this.aPk.aNO == null || !this.aPk.aNO.isShowing()) {
                    View popupView = this.aPk.getLayoutInflater().inflate(b.i.include_topic_lock_reason, null);
                    popupView.findViewById(g.tv_reason1).setOnClickListener(this.aPk.aNR);
                    popupView.findViewById(g.tv_reason2).setOnClickListener(this.aPk.aNR);
                    popupView.findViewById(g.tv_reason3).setOnClickListener(this.aPk.aNR);
                    popupView.findViewById(g.tv_reason4).setOnClickListener(this.aPk.aNR);
                    popupView.findViewById(g.tv_reason5).setOnClickListener(this.aPk.aNR);
                    popupView.findViewById(g.tv_reason6).setOnClickListener(this.aPk.aNR);
                    popupView.findViewById(g.tv_reason7).setOnClickListener(this.aPk.aNR);
                    popupView.findViewById(g.tv_reason8).setOnClickListener(this.aPk.aNR);
                    arrowImg.setImageDrawable(com.simple.colorful.d.o(this.aPk.aMn, b.c.drawableComplaintUp));
                    this.aPk.aNO = new PopupWindow(popupView, reasonLayout.getWidth(), at.dipToPx(this.aPk.aMn, 150));
                    this.aPk.aNO.update();
                    this.aPk.aNO.setTouchable(true);
                    this.aPk.aNO.setOutsideTouchable(true);
                    this.aPk.aNO.setBackgroundDrawable(new BitmapDrawable());
                    this.aPk.aNO.setFocusable(true);
                    this.aPk.aNO.setClippingEnabled(false);
                    this.aPk.aNO.showAsDropDown(reasonLayout, 0, at.dipToPx(this.aPk.aMn, 5));
                    this.aPk.aNO.setOnDismissListener(new 1(this));
                    return;
                }
                this.aPk.aNO.dismiss();
            }
        });
        dialog.setContentView(layout);
        dialog.show();
        layout.findViewById(g.tv_cancel).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ TopicDetailActivity aPk;

            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        layout.findViewById(g.tv_confirm).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ TopicDetailActivity aPk;

            public void onClick(View arg0) {
                if (this.aPk.aPi.getText().toString().trim().length() < 2) {
                    t.n(this.aPk, "理由不能少于2个字符");
                    return;
                }
                this.aPk.ek("请求处理中..");
                this.aPk.aOV.x(this.aPk.postID);
                this.aPk.aOV.aP(this.aPk.aPi.getText().toString().trim());
                this.aPk.aOV.eY();
                dialog.dismiss();
            }
        });
    }

    public void c(TopicItem item) {
        boolean z = true;
        if (item != null) {
            cs(true);
            k Ej = k.Ej();
            if (item.isAuthention()) {
                z = false;
            }
            Ej.a(item, z);
        }
    }

    private int GV() {
        View videoContainer = this.aOU.HH();
        if (videoContainer == null) {
            return 0;
        }
        int[] location = new int[2];
        videoContainer.getLocationInWindow(location);
        return location[1];
    }

    private void GW() {
        if (this.aOU != null && this.aOU.HS()) {
            this.aOU.HP();
        }
    }

    private void GX() {
        if (this.aOU != null && this.aOU.HR()) {
            this.aOU.HK();
        }
    }

    private void GY() {
        if (this.aOU == null) {
            return;
        }
        if (this.aOU.HS() || this.aOU.HR()) {
            this.aOU.HQ();
        }
    }

    private void c(TopicItem topicItem, String categoryName) {
        if (!this.aPd && topicItem != null && categoryName != null) {
            if (topicItem.getState() == 2) {
                d("该话题已经被删除", topicItem.getPostID());
                return;
            }
            SimpleTopicItem simple = SimpleTopicItem.convertToSimpleTopic(topicItem, categoryName);
            ao.Mx();
            ao.k(String.valueOf(topicItem.getPostID()), simple);
            this.aPd = true;
        }
    }

    public void d(String msg, final long delPostId) {
        final Dialog dialog = new Dialog(this, com.simple.colorful.d.RD());
        View layout = LayoutInflater.from(this).inflate(b.i.include_dialog_one, null);
        ((TextView) layout.findViewById(g.tv_msg)).setText(msg);
        dialog.setCancelable(false);
        dialog.setContentView(layout);
        dialog.show();
        layout.findViewById(g.tv_confirm).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ TopicDetailActivity aPk;

            public void onClick(View arg0) {
                dialog.dismiss();
                Intent intent = new Intent();
                intent.putExtra("delPostId", delPostId);
                TopicDetailActivity topicDetailActivity = this.aPk;
                this.aPk.aMn;
                topicDetailActivity.setResult(-1, intent);
                this.aPk.aMn.finish();
            }
        });
    }

    private void GE() {
        int randomFactor = UtilsEncrypt.radomInt();
        au.MD().a(this, this.sK, (long) randomFactor, UtilsEncrypt.encrpytEmailForLastLogin(String.valueOf(this.sK.getPostID() ^ 193186672) + "_" + randomFactor));
    }

    protected void EX() {
        super.EX();
        Gz();
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        if (this.aOU != null) {
            com.simple.colorful.setter.k childSetter = new com.simple.colorful.setter.j((ViewGroup) this.aHX.getRefreshableView());
            childSetter.a(this.aOU);
            builder.a(childSetter);
        }
        builder.aY(g.lytopic, b.c.backgroundDefault).aY(g.topic_bottom_split, b.c.splitColorDim).aY(g.bottom_bar, b.c.backgroundDim).j(this.aMB, b.c.backgroundPagePre).j(this.aMC, b.c.backgroundPageNext).j(this.aMD, b.c.backgroundTopicButton).j(this.aME, b.c.backgroundTopicButton).a(this.aMD, b.c.textColorTopicButton).a(this.aME, b.c.textColorTopicButton).a(this.aOT);
    }

    protected void kj(int themeId) {
        super.kj(themeId);
        Gv();
        GS();
        this.aOU.notifyDataSetChanged();
    }

    private void b(CommentItem commentItem, boolean isReplyTopic) {
        Object obj = null;
        if (this.sK != null) {
            if (com.huluxia.data.j.ep().ey()) {
                long mCat = 0;
                if (this.sK.getCategory() != null) {
                    mCat = this.sK.getCategory().categoryID;
                }
                if (mCat == 0) {
                    a(this.sK, commentItem, isReplyTopic);
                    return;
                } else if (!this.aPg) {
                    com.huluxia.module.topic.g info = ah.KZ().bu(com.huluxia.data.j.ep().getUserid());
                    String nowHour = UtilsSystem.getHourStr();
                    HLog.verbose(TAG, "nowHour " + nowHour + " CreatePowerInfo " + info, new Object[0]);
                    Activity activity;
                    CommentItem commentItem2;
                    if (info != null && info.commentCats != null && info.commentCats.contains(Long.valueOf(mCat)) && info.commentHours != null && info.commentHours.containsKey(Long.valueOf(mCat)) && info.commentHours.get(Long.valueOf(mCat)) != null && ((String) info.commentHours.get(Long.valueOf(mCat))).equals(nowHour)) {
                        a(this.sK, commentItem, isReplyTopic);
                        activity = this.aMn;
                        if (!isReplyTopic) {
                            commentItem2 = commentItem;
                        }
                        a(activity, mCat, false, obj);
                        return;
                    } else if (info == null || info.commentTipMsg == null || info.commentTipTitle == null || info.commentHours == null || !info.commentHours.containsKey(Long.valueOf(mCat)) || info.commentHours.get(Long.valueOf(mCat)) == null || !((String) info.commentHours.get(Long.valueOf(mCat))).equals(nowHour)) {
                        activity = this.aMn;
                        if (!isReplyTopic) {
                            commentItem2 = commentItem;
                        }
                        a(activity, mCat, true, obj);
                        return;
                    } else {
                        T(info.commentTipTitle, info.commentTipMsg);
                        activity = this.aMn;
                        if (!isReplyTopic) {
                            commentItem2 = commentItem;
                        }
                        a(activity, mCat, false, obj);
                        return;
                    }
                } else {
                    return;
                }
            }
            t.an(this.aMn);
        }
    }

    private void a(TopicItem topic, CommentItem comment, boolean isReplyTopic) {
        if (isReplyTopic) {
            t.a(this.aMn, topic, topic != null ? topic.getUserInfo() : null);
        } else {
            t.a(this.aMn, topic, comment, true);
        }
    }

    private void a(Activity activity, long cat_id, boolean isnext, Object item) {
        if (!this.aPg) {
            this.aPg = true;
            k.Ej().b(activity, cat_id, TAG, isnext, item);
        }
    }

    private void T(String title, String msg) {
        com.huluxia.widget.dialog.i dia = new com.huluxia.widget.dialog.i(this.aMn, null);
        dia.az(title, msg);
        dia.gK("朕知道了");
        dia.showDialog();
    }
}
