package hlx.ui.mapseed;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huluxia.HTApplication;
import com.huluxia.data.PageList;
import com.huluxia.data.UserBaseInfo;
import com.huluxia.data.g;
import com.huluxia.data.map.f.a;
import com.huluxia.data.topic.CommentItem;
import com.huluxia.data.topic.TopicItem;
import com.huluxia.data.topic.e;
import com.huluxia.framework.R;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.CommonMenuDialogListener;
import com.huluxia.framework.base.widget.dialog.DialogManager;
import com.huluxia.http.base.f;
import com.huluxia.http.bbs.topic.q;
import com.huluxia.jni.UtilsEncrypt;
import com.huluxia.login.LoginError.LoginErrCode;
import com.huluxia.module.h;
import com.huluxia.module.n;
import com.huluxia.module.topic.TopicModule;
import com.huluxia.module.topic.j;
import com.huluxia.module.topic.k;
import com.huluxia.module.u;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseLoadingActivity;
import com.huluxia.ui.bbs.ResTopicDetailTitle;
import com.huluxia.ui.bbs.TopicDetailPageTurnLayout;
import com.huluxia.ui.itemadapter.topic.ResTopicDetailItemAdapter;
import com.huluxia.ui.itemadapter.topic.ResTopicDetailItemAdapter.c;
import com.huluxia.utils.UtilsMenu;
import com.huluxia.utils.UtilsMenu.MENU_VALUE;
import com.huluxia.utils.ab;
import com.huluxia.utils.ad;
import com.huluxia.utils.at;
import com.huluxia.utils.au;
import com.huluxia.utils.aw;
import com.huluxia.utils.o;
import com.huluxia.widget.viewpager.PagerSlidingTabStrip;
import com.huluxia.widget.viewpager.WrapContentHeightViewPager;
import hlx.gameoperator.b;
import hlx.launch.game.d;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapSeedDetatilActivity extends HTBaseLoadingActivity implements OnClickListener, c {
    protected static final String TAG = "MapSeedDetailActivity";
    private static final int aNb = 0;
    private static final int aNc = 1;
    private static final int aNd = 2;
    private final int PAGE_SIZE = 20;
    private com.huluxia.utils.c aEV;
    private PullToRefreshListView aEq;
    private CommonMenuDialog aHI = null;
    private int aIE = 1;
    private ArrayList<UserBaseInfo> aLZ;
    private ImageButton aMA;
    private ImageButton aMB;
    private ImageButton aMC;
    private Button aMD;
    private Button aME;
    private boolean aMG = false;
    private CommentItem aMH;
    protected e aMQ;
    protected long aMR = 0;
    private TopicItem aMT;
    protected a aMX;
    protected Activity aMn;
    protected ResTopicDetailTitle aMq;
    private RelativeLayout aMt;
    private TextView aMu;
    private ResTopicDetailItemAdapter aMx;
    private ImageButton aMz;
    private int aNA = 0;
    private int aNB = 0;
    private int aNC = 0;
    OnClickListener aND = new OnClickListener(this) {
        final /* synthetic */ MapSeedDetatilActivity cbr;

        {
            this.cbr = this$0;
        }

        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.tvFirstPageBtn) {
                this.cbr.b(1, this.cbr.aMG, 0);
                this.cbr.GC();
            } else if (i == R.id.tvEndPageBtn) {
                this.cbr.b(this.cbr.aMx.getPageList().getTotalPage(), this.cbr.aMG, 0);
                this.cbr.GC();
            }
        }
    };
    TopicDetailPageTurnLayout.a aNE = new TopicDetailPageTurnLayout.a(this) {
        final /* synthetic */ MapSeedDetatilActivity cbr;

        {
            this.cbr = this$0;
        }

        public void kz(int index) {
            this.cbr.b(index, this.cbr.aMG, 0);
            this.cbr.GC();
        }
    };
    private OnPageChangeListener aNF = new OnPageChangeListener(this) {
        final /* synthetic */ MapSeedDetatilActivity cbr;

        {
            this.cbr = this$0;
        }

        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageSelected(int position) {
            if (!this.cbr.aMn.isFinishing()) {
                this.cbr.mPos = position;
            }
        }
    };
    private CommonMenuDialog aNG = null;
    private OnClickListener aNH = new OnClickListener(this) {
        final /* synthetic */ MapSeedDetatilActivity cbr;

        {
            this.cbr = this$0;
        }

        public void onClick(View v) {
            this.cbr.a(null, true);
        }
    };
    private OnClickListener aNI = new OnClickListener(this) {
        final /* synthetic */ MapSeedDetatilActivity cbr;

        {
            this.cbr = this$0;
        }

        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.num1) {
                this.cbr.aNK.setSelected(true);
                this.cbr.aNL.setSelected(false);
                this.cbr.aNM.setSelected(false);
                this.cbr.aNN.setSelected(false);
                this.cbr.GG();
                this.cbr.aNJ = "1";
            } else if (id == R.id.num2) {
                this.cbr.aNK.setSelected(false);
                this.cbr.aNL.setSelected(true);
                this.cbr.aNM.setSelected(false);
                this.cbr.aNN.setSelected(false);
                this.cbr.GG();
                this.cbr.aNJ = "2";
            } else if (id == R.id.num5) {
                this.cbr.aNK.setSelected(false);
                this.cbr.aNL.setSelected(false);
                this.cbr.aNM.setSelected(true);
                this.cbr.aNN.setSelected(false);
                this.cbr.GG();
                this.cbr.aNJ = "5";
            }
            this.cbr.GF();
        }
    };
    private String aNJ;
    private RadioButton aNK;
    private RadioButton aNL;
    private RadioButton aNM;
    private EditText aNN;
    private PopupWindow aNO;
    private TextView aNP;
    private String aNQ;
    private OnClickListener aNR = new OnClickListener(this) {
        final /* synthetic */ MapSeedDetatilActivity cbr;

        {
            this.cbr = this$0;
        }

        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.tv_reason1) {
                this.cbr.aNQ = this.cbr.aMn.getResources().getString(R.string.topic_complaint_reason1);
            } else if (id == R.id.tv_reason2) {
                this.cbr.aNQ = this.cbr.aMn.getResources().getString(R.string.topic_complaint_reason2);
            } else if (id == R.id.tv_reason3) {
                this.cbr.aNQ = this.cbr.aMn.getResources().getString(R.string.topic_complaint_reason3);
            } else if (id == R.id.tv_reason4) {
                this.cbr.aNQ = this.cbr.aMn.getResources().getString(R.string.topic_complaint_reason4);
            } else if (id == R.id.tv_reason5) {
                this.cbr.aNQ = this.cbr.aMn.getResources().getString(R.string.topic_complaint_reason5);
            } else if (id == R.id.tv_reason6) {
                this.cbr.aNQ = this.cbr.aMn.getResources().getString(R.string.topic_complaint_reason6);
            } else if (id == R.id.tv_reason7) {
                this.cbr.aNQ = this.cbr.aMn.getResources().getString(R.string.topic_complaint_reason7);
            } else if (id == R.id.tv_reason8) {
                this.cbr.aNQ = this.cbr.aMn.getResources().getString(R.string.topic_complaint_reason8);
            }
            this.cbr.aNP.setText(this.cbr.aNQ);
            if (this.cbr.aNO != null && this.cbr.aNO.isShowing()) {
                this.cbr.aNO.dismiss();
            }
        }
    };
    private int[] aNa = new int[0];
    private int aNe = 0;
    private int aNf = 0;
    private q aNm = new q();
    private float aNx = 0.0f;
    private PopupWindow aNy;
    private WrapContentHeightViewPager aNz;
    private DialogManager aSB;
    private CallbackHandler aky = new CallbackHandler(this) {
        final /* synthetic */ MapSeedDetatilActivity cbr;

        {
            this.cbr = this$0;
        }

        @MessageHandler(message = 768)
        public void onRecvTopicinfo(boolean succ, j info, int position, long postId, Context context) {
            if (context == this.cbr.aMn || postId == this.cbr.aMR) {
                this.cbr.cs(false);
                if (!succ || info == null) {
                    if (info != null && info.code == 104) {
                        t.n(this.cbr.aMn, ab.n(info.code, info.msg));
                        this.cbr.finish();
                    }
                    if (this.cbr.aNa[1] != 2) {
                        this.cbr.aNa[1] = 0;
                    }
                    this.cbr.GA();
                    return;
                }
                this.cbr.aNa[1] = 2;
                this.cbr.a(info.getPageList(), info.remindUsers, position);
            }
        }

        @MessageHandler(message = 600)
        public void onCompliant(boolean succ, String msg) {
            this.cbr.aSB.hideProgressDialog();
            if (succ) {
                t.o(this.cbr.aMn, msg);
            } else {
                t.n(this.cbr.aMn, msg);
            }
        }
    };
    private TextView cbl;
    private Button cbm;
    private LinearLayout cbn;
    private long cbo;
    protected b.a cbp;
    private OnClickListener cbq = new OnClickListener(this) {
        final /* synthetic */ MapSeedDetatilActivity cbr;

        {
            this.cbr = this$0;
        }

        public void onClick(View v) {
            if (this.cbr.cbp != null) {
                b.RI().a(this.cbr.aMn, (long) this.cbr.cbp.id, this.cbr.cbp.name, this.cbr.cbp.seedValue, this.cbr.cbp.SpawnX, this.cbr.cbp.SpawnY, this.cbr.cbp.SpawnZ);
                d.bR(this.cbr.aMn);
            }
        }
    };
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ MapSeedDetatilActivity cbr;

        {
            this.cbr = this$0;
        }

        @MessageHandler(message = 299)
        public void onRecvDetailData(boolean succ, int requestCode, b.a info) {
            if (succ && info != null && requestCode == this.cbr.mRequestCode) {
                this.cbr.aMX = this.cbr.a(info);
                this.cbr.cbp = info;
                this.cbr.aMq.b(this.cbr.aMX, 1);
                this.cbr.b(this.cbr.aMX);
                this.cbr.aNa[0] = 2;
                if (this.cbr.Gx()) {
                    this.cbr.FC();
                    return;
                }
                return;
            }
            this.cbr.aNa[0] = 0;
            this.cbr.FB();
        }

        @MessageHandler(message = 2304)
        public void onRecvDetailData(boolean succ, e info, long postId) {
            boolean z = true;
            this.cbr.aEq.onRefreshComplete();
            this.cbr.aEV.onLoadComplete();
            if (postId == this.cbr.aMR) {
                if (info != null && this.cbr.aMx != null && info.isSucc()) {
                    this.cbr.aNa[1] = 2;
                    if (info.currPageNo > 1) {
                        this.cbr.aMQ.currPageNo = info.currPageNo;
                        this.cbr.aMQ.pageSize = info.pageSize;
                        this.cbr.aMQ.totalPage = info.totalPage;
                        this.cbr.aMQ.comments.addAll(info.comments);
                    } else {
                        this.cbr.aMQ = info;
                    }
                    PageList pageList = new PageList(info.currPageNo, info.totalPage, info.pageSize);
                    if (info.post != null) {
                        pageList.add(info.post);
                    }
                    pageList.addAll(info.comments);
                    MapSeedDetatilActivity mapSeedDetatilActivity = this.cbr;
                    if (info.currPageNo > 1) {
                        z = false;
                    }
                    mapSeedDetatilActivity.a(pageList, z);
                    this.cbr.FC();
                } else if (this.cbr.aNa[1] != 2) {
                    this.cbr.FB();
                    this.cbr.aNa[1] = 0;
                }
            }
        }

        @MessageHandler(message = 2320)
        public void onFavoriteTopicCheck(boolean succ, long id, boolean isFavor) {
            if (succ && id == this.cbr.aMR) {
                this.cbr.st = isFavor;
                this.cbr.Gv();
            }
        }

        @MessageHandler(message = 2313)
        public void onFavoriteTopic(boolean succ, long id, boolean isFavor) {
            if (id == this.cbr.aMR) {
                if (succ) {
                    this.cbr.st = isFavor;
                    this.cbr.Gv();
                    t.o(this.cbr.aMn, isFavor ? "收藏成功" : "取消收藏成功");
                    return;
                }
                t.n(this.cbr.aMn, isFavor ? "收藏失败\n网络问题" : "取消收藏失败\n网络问题");
            }
        }

        @MessageHandler(message = 2312)
        public void onTopicShareAddress(boolean succ, String address) {
            if (succ && address != null) {
                HTApplication.fA = address;
            }
        }

        @MessageHandler(message = 2309)
        public void onTopicRemove(boolean succ, long id, long seqs) {
            if (id == this.cbr.aMR) {
                if (succ) {
                    t.o(this.cbr.aMn, "删除话题成功");
                } else {
                    t.n(this.cbr.aMn, "删除话题失败\n网络问题");
                }
            }
        }

        @MessageHandler(message = 2310)
        public void onCommentRemove(boolean succ, long id, long seq) {
            if (succ) {
                this.cbr.k(this.cbr.aMx.getPageList().getCurrPageNo(), this.cbr.aMG);
            } else {
                t.n(this.cbr.aMn, "删除回复失败\n网络问题");
            }
        }

        @MessageHandler(message = 2307)
        public void onTopicLock(boolean succ, long id) {
            if (succ) {
                t.o(this.cbr.aMn, "锁定话题成功");
            } else {
                t.n(this.cbr.aMn, "锁定话题失败\n网络问题");
            }
        }

        @MessageHandler(message = 2308)
        public void onTopicUnLock(boolean succ, long id) {
            if (succ) {
                t.o(this.cbr.aMn, "解锁话题成功");
            } else {
                t.n(this.cbr.aMn, "解锁话题失败\n网络问题");
            }
        }

        @MessageHandler(message = 2311)
        public void onCompliant(boolean succ) {
            if (succ) {
                t.o(this.cbr.aMn, "举报成功，等待处理");
            } else {
                t.n(this.cbr.aMn, "举报失败，请重试");
            }
        }

        @MessageHandler(message = 2306)
        public void onCreditTransfer(boolean succ, com.huluxia.data.topic.b info) {
            if (!succ || info == null) {
                t.n(this.cbr.aMn, "赠送葫芦失败\n网络问题");
                return;
            }
            String msg = info.msg;
            if (info.isSucc()) {
                t.o(this.cbr.aMn, msg);
            } else {
                t.n(this.cbr.aMn, msg);
            }
        }
    };
    private View mEmptyView;
    private int mPos = 0;
    private int mRequestCode;
    private CallbackHandler pl = new CallbackHandler(this) {
        final /* synthetic */ MapSeedDetatilActivity cbr;

        {
            this.cbr = this$0;
        }

        @MessageHandler(message = 1025)
        public void onLogin(boolean succ, String client, String mail, String encryptPwd, String openId, int code, String msg, LoginErrCode errCoder) {
            if (succ) {
                this.cbr.cs(true);
                TopicModule.Ef().Eh();
            }
        }

        @MessageHandler(message = 1026)
        public void onAutoLogin(boolean succ, String clientid, LoginErrCode error) {
            if (succ) {
                this.cbr.cs(true);
                TopicModule.Ef().Eh();
            }
        }
    };
    private boolean st = false;

    private class TopicDetailViewPagerAdapter extends PagerAdapter {
        public List<View> aOg;
        public List<String> aOh;
        final /* synthetic */ MapSeedDetatilActivity cbr;

        public TopicDetailViewPagerAdapter(MapSeedDetatilActivity mapSeedDetatilActivity, List<View> mListViews, List<String> mListTabTitle) {
            this.cbr = mapSeedDetatilActivity;
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

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_res_topic);
        EventNotifyCenter.add(n.class, this.mCallback);
        EventNotifyCenter.add(com.huluxia.login.e.class, this.pl);
        EventNotifyCenter.add(h.class, this.aky);
        this.aMn = this;
        this.aSB = new DialogManager(this);
        this.aNm.bb(6);
        this.aNm.x(this.aMR);
        this.aNm.a(new f(this) {
            final /* synthetic */ MapSeedDetatilActivity cbr;

            {
                this.cbr = this$0;
            }

            public void a(com.huluxia.http.base.d response) {
            }

            public void b(com.huluxia.http.base.d response) {
                this.cbr.aSB.hideProgressDialog();
                if (response.fe() == 6) {
                    t.o(this.cbr.aMn, "锁定话题成功");
                    if (this.cbr.aMT != null) {
                        this.cbr.aMT.setState(3);
                    }
                    this.cbr.cx(this.cbr.aMG);
                }
            }

            public void c(com.huluxia.http.base.d response) {
                this.cbr.aSB.hideProgressDialog();
                if (response.fe() == 6) {
                    t.n(this.cbr.aMn, "锁定话题失败");
                }
            }
        });
        Sw();
        FM();
        Fa();
        Gs();
        this.aNa = new int[]{0, 0, 2};
        if (HTApplication.fA == null) {
            TopicModule.Ef().Eg();
        }
        Gy();
        Fy();
    }

    private void Sw() {
        this.cbo = getIntent().getLongExtra("seedId", 0);
        this.aMR = getIntent().getLongExtra("postId", 0);
        this.mRequestCode = 0;
        this.aMx = new ResTopicDetailItemAdapter(this);
        this.aMx.a((c) this);
        this.aMq = new ResTopicDetailTitle(this);
    }

    private void FM() {
        this.aMz = (ImageButton) findViewById(R.id.sys_header_flright_img);
        this.aMz.setVisibility(0);
        this.aMz.setOnClickListener(this);
        Gv();
        Gt();
        ej("地图种子");
        this.mEmptyView = findViewById(R.id.empty_48);
        this.aMt = (RelativeLayout) findViewById(R.id.rly_float_header);
        this.aMu = (TextView) findViewById(R.id.tv_map_name);
        this.cbl = (TextView) findViewById(R.id.tv_generate);
        this.cbm = (Button) findViewById(R.id.btn_generate);
        this.cbm.setOnClickListener(this.cbq);
    }

    private void Fa() {
        this.cbn = new LinearLayout(this);
        this.cbn.setOrientation(1);
        this.cbn.addView(this.aMq);
        this.aEq = (PullToRefreshListView) findViewById(R.id.restopiclistview);
        ((ListView) this.aEq.getRefreshableView()).addHeaderView(this.cbn);
        this.aEq.setAdapter(this.aMx);
        this.aEq.setOnRefreshListener(new OnRefreshListener<ListView>(this) {
            final /* synthetic */ MapSeedDetatilActivity cbr;

            {
                this.cbr = this$0;
            }

            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                this.cbr.Gz();
            }
        });
        this.aEV = new com.huluxia.utils.c((ListView) this.aEq.getRefreshableView());
        this.aEV.a(new com.huluxia.utils.c.a(this) {
            final /* synthetic */ MapSeedDetatilActivity cbr;

            {
                this.cbr = this$0;
            }

            public void onLoadData() {
                this.cbr.Gz();
            }

            public boolean shouldLoadData() {
                if (this.cbr.aMQ == null) {
                    this.cbr.aEV.onLoadComplete();
                    return false;
                } else if (this.cbr.aMQ.totalPage > this.cbr.aMQ.currPageNo) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        this.aEV.setParentOnScrollListener(new OnScrollListener(this) {
            final /* synthetic */ MapSeedDetatilActivity cbr;

            {
                this.cbr = this$0;
            }

            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (this.cbr.aIE == 1) {
                    int dip_48 = at.dipToPx(this.cbr.aMn, 48);
                    View splitView = this.cbr.aMq.getSplitTopView();
                    int originHeight = at.dipToPx(this.cbr.aMn, 104);
                    if (splitView != null) {
                        float scrollDistance = (float) (originHeight - (this.cbr.i(splitView) - dip_48));
                        if (scrollDistance >= 0.0f) {
                            this.cbr.aNx = scrollDistance;
                            float distanceDelta = this.cbr.aNx - ((float) originHeight);
                            if (distanceDelta >= 0.0f) {
                                this.cbr.aMt.setVisibility(0);
                                if (distanceDelta >= ((float) originHeight)) {
                                    ViewCompat.setAlpha(this.cbr.cbm, 1.0f);
                                    return;
                                } else {
                                    ViewCompat.setAlpha(this.cbr.cbm, distanceDelta / ((float) originHeight));
                                    return;
                                }
                            }
                            this.cbr.aMt.setVisibility(8);
                        }
                    }
                }
            }
        });
        this.aEq.setOnScrollListener(this.aEV);
    }

    private void Gs() {
        this.aMB = (ImageButton) findViewById(R.id.btn_prev);
        this.aMC = (ImageButton) findViewById(R.id.btn_next);
        this.aMD = (Button) findViewById(R.id.btn_page);
        this.aME = (Button) findViewById(R.id.btn_comment);
        this.aME.setOnClickListener(this);
        this.aMB.setOnClickListener(this);
        this.aMC.setOnClickListener(this);
        this.aMD.setOnClickListener(this);
        this.aMD.setText("1/1");
        this.aMq.getDownButton().setText("生成");
        this.aMq.getDownButton().setBackgroundDrawable(com.simple.colorful.d.o(this.aMn, R.attr.drawableResTopicGreen));
        this.aMq.getDownButton().setOnClickListener(this.cbq);
    }

    public int i(View view) {
        if (view == null) {
            return 0;
        }
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return location[1];
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
    }

    protected void EX() {
        super.EX();
        Gy();
    }

    public void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
        EventNotifyCenter.remove(this.pl);
        EventNotifyCenter.remove(this.aky);
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

    private void Gt() {
        if (com.huluxia.data.j.ep().ey()) {
            TopicModule.Ef().aY(this.aMR);
        }
    }

    private void Gu() {
        if (com.huluxia.data.j.ep().ey()) {
            ek("请求处理中..");
            TopicModule.Ef().c(this.aMR, !this.st);
            return;
        }
        t.an(this);
    }

    private void Gv() {
        if (this.st) {
            this.aMz.setImageResource(com.simple.colorful.d.r(this, R.attr.drawableTitleFavorChecked));
        } else {
            this.aMz.setImageResource(com.simple.colorful.d.r(this, R.attr.drawableTitleFavor));
        }
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.sys_header_flright_img) {
            Gu();
        } else if (id == R.id.btn_comment) {
            a(null, true);
        } else if (id == R.id.btn_prev) {
            b(this.aMx.getPageList().getCurrPageNo() - 1, this.aMG, 0);
        } else if (id == R.id.btn_next) {
            b(this.aMx.getPageList().getCurrPageNo() + 1, this.aMG, 0);
        } else if (id == R.id.btn_page && this.aMx.getPageList().getTotalPage() > 1) {
            GD();
        }
    }

    private boolean Gx() {
        for (int i : this.aNa) {
            if (i != 2) {
                return false;
            }
        }
        return true;
    }

    private void Gy() {
        if (this.aNa[0] == 0) {
            u.b(0, this.cbo);
            this.aNa[0] = 1;
        }
        if (this.aNa[1] == 0) {
            k(1, this.aMG);
            this.aNa[1] = 1;
        }
    }

    private void cx(boolean isOnlyFloorHost) {
        k(isOnlyFloorHost ? this.aNf : this.aNe, isOnlyFloorHost);
    }

    private void k(int page, boolean isOnlyFloorHost) {
        int position = 0;
        if (this.aEq.getRefreshableView() != null) {
            position = ((ListView) this.aEq.getRefreshableView()).getFirstVisiblePosition();
        }
        b(page, isOnlyFloorHost, position);
    }

    private void b(int page, boolean isOnlyFloorHost, int position) {
        if (getCurrentPage() == 2) {
            cs(true);
        }
        if (isOnlyFloorHost) {
            this.aNf = page;
            k.Ej().b(this.aMR, this.aNf, 20, true, position, this.aMn);
        } else {
            this.aNe = page;
            k.Ej().b(this.aMR, this.aNe, 20, false, position, this.aMn);
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
        if (this.aNa[0] == 0) {
            u.b(0, this.cbo);
            this.aNa[0] = 1;
        }
        this.aMB.setEnabled(false);
        this.aMC.setEnabled(false);
        this.aMD.setEnabled(false);
        this.aME.setEnabled(false);
        if (this.aMG) {
            page = this.aNf;
            k.Ej().b(this.aMR, this.aNf, 20, true, 0, this.aMn);
        } else {
            page = this.aNe;
            k.Ej().b(this.aMR, this.aNe, 20, false, 0, this.aMn);
        }
        ek(String.format(Locale.getDefault(), "加载第%d页", new Object[]{Integer.valueOf(page)}));
    }

    private void GA() {
        int currPage = this.aMx.getPageList().getCurrPageNo();
        int totalPage = this.aMx.getPageList().getTotalPage();
        if (currPage > 1) {
            this.aMB.setEnabled(true);
        }
        if (currPage < totalPage) {
            this.aMC.setEnabled(true);
        }
        if (totalPage > 1) {
            this.aMD.setEnabled(true);
        }
        b(this.aMT);
        if (getCurrentPage() == 0) {
            FB();
        } else {
            t.n(this, "加载评论失败\n网络问题");
        }
    }

    private void a(PageList page, List<UserBaseInfo> users, int position) {
        int i;
        int i2 = 8;
        this.aEq.onRefreshComplete();
        this.aEV.onLoadComplete();
        this.aMx.getPageList().clear();
        this.aMx.HG();
        if (page.size() > 0 && (page.get(0) instanceof TopicItem)) {
            this.aMT = (TopicItem) page.get(0);
            this.aLZ = (ArrayList) users;
            this.aMx.setTopicCategory(this.aMT.getCategory());
        }
        int currPage = page.getCurrPageNo();
        this.aIE = currPage;
        int totalPage = page.getTotalPage() < 1 ? 1 : page.getTotalPage();
        this.cbn.removeView(this.aMq);
        if (currPage == 1) {
            this.cbn.addView(this.aMq);
        } else {
            this.cbn.removeView(this.aMq);
        }
        View view = this.mEmptyView;
        if (currPage == 1) {
            i = 8;
        } else {
            i = 0;
        }
        view.setVisibility(i);
        RelativeLayout relativeLayout = this.aMt;
        if (currPage != 1) {
            i2 = 0;
        }
        relativeLayout.setVisibility(i2);
        if (currPage > 1) {
            ViewCompat.setAlpha(this.cbm, 1.0f);
        }
        this.aMx.getPageList().addAll(page);
        this.aMx.getPageList().setCurrPageNo(currPage);
        this.aMx.getPageList().setTotalPage(totalPage);
        this.aMx.getPageList().setPageSize(page.getPageSize());
        this.aMx.getPageList().setRemindUsers(page.getRemindUsers());
        this.aMx.notifyDataSetChanged();
        this.aMD.setText(String.format(Locale.getDefault(), "%d/%d", new Object[]{Integer.valueOf(currPage), Integer.valueOf(totalPage)}));
        if (currPage > 1) {
            this.aMB.setEnabled(true);
        }
        if (currPage < totalPage) {
            this.aMC.setEnabled(true);
        }
        this.aMD.setEnabled(true);
        b(this.aMT);
        if (this.aEq.getRefreshableView() != null && ((ListView) this.aEq.getRefreshableView()).getChildCount() > position) {
            ((ListView) this.aEq.getRefreshableView()).setSelection(position);
        }
        if (getCurrentPage() == 0) {
            FC();
        }
    }

    private void b(a mapItem) {
        this.aMu.setText(aw.W(mapItem.name, 10));
        this.cbl.setText("生成：" + mapItem.downCount);
    }

    private void a(PageList page, boolean isRefresh) {
        if (isRefresh) {
            this.aMx.getPageList().clear();
            this.aMx.HG();
        }
        if (page.size() > 0 && (page.get(0) instanceof TopicItem)) {
            this.aMT = (TopicItem) page.get(0);
        }
        this.aMx.getPageList().addAll(page);
        this.aMx.getPageList().setCurrPageNo(page.getCurrPageNo());
        this.aMx.getPageList().setTotalPage(page.getTotalPage());
        this.aMx.getPageList().setPageSize(page.getPageSize());
        this.aMx.notifyDataSetChanged();
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

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);
        if (keyCode == 4) {
        }
        return true;
    }

    private a a(b.a info) {
        if (info == null) {
            return null;
        }
        a tmpItem = new a();
        tmpItem.id = info.id;
        tmpItem.postID = info.postID;
        tmpItem.name = info.name;
        tmpItem.author = info.author;
        tmpItem.cateName = info.cateName;
        tmpItem.createTime = info.createTime;
        tmpItem.downCount = info.downCount;
        tmpItem.icon = info.icon;
        tmpItem.language = info.language;
        tmpItem.mapDesc = info.mapDesc;
        tmpItem.resourceList = info.resourceList == null ? new ArrayList() : info.resourceList;
        tmpItem.source = info.source;
        tmpItem.version = info.version;
        return tmpItem;
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
        int mCurIndex = this.aMx.getPageList().getCurrPageNo();
        int mDataCount = this.aMx.getPageList().getTotalPage();
        GC();
        View pageTurnView = getLayoutInflater().inflate(R.layout.topic_detail_popupwindow_page_turn, null);
        this.aNy = new PopupWindow(this);
        this.aNy.setWidth(-1);
        this.aNy.setHeight(-2);
        this.aNy.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparent)));
        this.aNy.setContentView(pageTurnView);
        this.aNy.setFocusable(true);
        this.aNy.setAnimationStyle(R.style.topic_detail_pageturn_popwindow_anim_style);
        this.aNy.showAtLocation(this.aMn.getWindow().getDecorView(), 80, 0, 0);
        this.aNy.setTouchable(true);
        this.aNy.setOutsideTouchable(true);
        this.aNz = (WrapContentHeightViewPager) pageTurnView.findViewById(R.id.vpLocalResMgrViewPager);
        this.aNz.setShowHighMultiple(4);
        TextView mTvEndPageBtn = (TextView) pageTurnView.findViewById(R.id.tvEndPageBtn);
        ((TextView) pageTurnView.findViewById(R.id.tvFirstPageBtn)).setOnClickListener(this.aND);
        mTvEndPageBtn.setOnClickListener(this.aND);
        PagerSlidingTabStrip mLocalResMgrTabs = (PagerSlidingTabStrip) pageTurnView.findViewById(R.id.pstsLocalResMgrTabs);
        mLocalResMgrTabs.setTextColorResource(R.color.TabStripTextColor);
        mLocalResMgrTabs.setTextSize(at.dipToPx(this, 15));
        if (com.simple.colorful.d.isDayMode()) {
            mLocalResMgrTabs.setIndicatorColor(getResources().getColor(R.color.TabStripIndicatorColor));
        } else {
            mLocalResMgrTabs.setIndicatorColor(getResources().getColor(R.color.TabStripIndicatorNightColor));
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

    public void a(final boolean isFloor, final CommentItem item) {
        if (this.aMT != null) {
            if (this.aHI == null || !this.aHI.isDialogShowing()) {
                CommonMenuDialogListener menuListener = new CommonMenuDialogListener(this) {
                    final /* synthetic */ MapSeedDetatilActivity cbr;

                    public void pressMenuById(int inIndex, Object object) {
                        this.cbr.aHI.dismissDialog();
                        if (inIndex == MENU_VALUE.COMMENT.ordinal()) {
                            this.cbr.a(item, true);
                        } else if (inIndex == MENU_VALUE.SEND_HULU.ordinal()) {
                            if (com.huluxia.data.j.ep().ey()) {
                                this.cbr.a(isFloor, item, this.cbr.aMR);
                            } else {
                                t.an(this.cbr.aMn);
                            }
                        } else if (inIndex == MENU_VALUE.SHAREWIXIN.ordinal()) {
                            if (HTApplication.fA != null) {
                                this.cbr.GE();
                            } else {
                                t.download_toast(this.cbr.aMn, "暂时无法分享");
                            }
                        } else if (inIndex == MENU_VALUE.MOVETOPIC.ordinal()) {
                            t.a(this.cbr.aMn, this.cbr.aMT);
                        } else if (inIndex == MENU_VALUE.UNLOCK_TOPIC.ordinal()) {
                            this.cbr.aSB.showProgressDialog(this.cbr.aMn, "请求处理中..");
                            TopicModule.Ef().aW(this.cbr.aMR);
                        } else if (inIndex == MENU_VALUE.EDITTOPIC.ordinal()) {
                            t.b(this.cbr.aMn, this.cbr.aMT);
                        } else if (inIndex == MENU_VALUE.LOCK_TOPIC.ordinal()) {
                            this.cbr.GJ();
                        } else if (inIndex == MENU_VALUE.REMOVE_TOPIC.ordinal() || inIndex == MENU_VALUE.REMOVE_COMMENT.ordinal()) {
                            this.cbr.ky(inIndex);
                        } else if (inIndex == MENU_VALUE.REPLY.ordinal()) {
                            this.cbr.a(this.cbr.aMH, false);
                        } else if (inIndex == MENU_VALUE.COPY_TEXT.ordinal()) {
                            if (this.cbr.aMH != null) {
                                o.bV(this.cbr.aMH.getText());
                            } else {
                                o.bV(this.cbr.aMT.getDetail());
                            }
                            t.o(this.cbr.aMn, "已经复制到剪切板");
                        } else if (inIndex == MENU_VALUE.REPORT_TOPIC.ordinal()) {
                            this.cbr.a(this.cbr.aMT, null);
                        } else if (inIndex == MENU_VALUE.REPORT_COMMENT.ordinal()) {
                            this.cbr.a(this.cbr.aMT, this.cbr.aMH);
                        }
                    }
                };
                if (isFloor) {
                    this.aHI = UtilsMenu.a((Context) this, this.aMT, true, menuListener);
                    this.aMH = null;
                } else {
                    this.aMH = item;
                    if (this.aMH.getState() != 2) {
                        this.aHI = UtilsMenu.a((Context) this, this.aMT, this.aMH, menuListener);
                    } else {
                        return;
                    }
                }
                this.aHI.updateCurFocusIndex(-1);
                this.aHI.showMenu(null, null);
            }
        }
    }

    private void GE() {
        int randomFactor = UtilsEncrypt.radomInt();
        au.MD().a(this.aMn, this.aMT, (long) randomFactor, UtilsEncrypt.encrpytEmailForLastLogin(String.valueOf(this.aMT.getPostID() ^ 193186672) + "_" + randomFactor));
    }

    private void GF() {
        Drawable o;
        RadioButton radioButton = this.aNK;
        if (this.aNK.isSelected()) {
            o = com.simple.colorful.d.o(this.aMn, R.attr.drawableTopicSendhuluSelected);
        } else {
            o = com.simple.colorful.d.o(this.aMn, R.attr.drawableTopicSendhulu);
        }
        radioButton.setBackgroundDrawable(o);
        radioButton = this.aNL;
        if (this.aNL.isSelected()) {
            o = com.simple.colorful.d.o(this.aMn, R.attr.drawableTopicSendhuluSelected);
        } else {
            o = com.simple.colorful.d.o(this.aMn, R.attr.drawableTopicSendhulu);
        }
        radioButton.setBackgroundDrawable(o);
        radioButton = this.aNM;
        if (this.aNM.isSelected()) {
            o = com.simple.colorful.d.o(this.aMn, R.attr.drawableTopicSendhuluSelected);
        } else {
            o = com.simple.colorful.d.o(this.aMn, R.attr.drawableTopicSendhulu);
        }
        radioButton.setBackgroundDrawable(o);
        EditText editText = this.aNN;
        if (this.aNN.isSelected()) {
            o = com.simple.colorful.d.o(this.aMn, R.attr.drawableTopicSendhuluSelected);
        } else {
            o = com.simple.colorful.d.o(this.aMn, R.attr.drawableTopicSendhulu);
        }
        editText.setBackgroundDrawable(o);
    }

    private void GG() {
        this.aNN.clearFocus();
        this.aNN.getEditableText().clear();
        this.aNN.getEditableText().clearSpans();
        this.aNN.setText("");
    }

    private void a(boolean isFloor, CommentItem item, long postId) {
        final Dialog dialog = new Dialog(this, com.simple.colorful.d.RD());
        View layout = LayoutInflater.from(this).inflate(R.layout.include_credit_send, null);
        this.aNK = (RadioButton) layout.findViewById(R.id.num1);
        this.aNL = (RadioButton) layout.findViewById(R.id.num2);
        this.aNM = (RadioButton) layout.findViewById(R.id.num5);
        this.aNK.setSelected(true);
        this.aNJ = "1";
        this.aNN = (EditText) layout.findViewById(R.id.other_num);
        this.aNN.setVisibility(8);
        GF();
        if (com.huluxia.data.j.ep().ey()) {
            g info = com.huluxia.data.j.ep().ew();
            HLog.info(TAG, "isgold %d", Integer.valueOf(info.isgold));
            if (info != null && info.isgold == 1) {
                this.aNN.setVisibility(0);
            }
        }
        this.aNK.setOnClickListener(this.aNI);
        this.aNL.setOnClickListener(this.aNI);
        this.aNM.setOnClickListener(this.aNI);
        this.aNN.setOnFocusChangeListener(new OnFocusChangeListener(this) {
            final /* synthetic */ MapSeedDetatilActivity cbr;

            {
                this.cbr = this$0;
            }

            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    this.cbr.aNN.setSelected(true);
                    this.cbr.aNK.setSelected(false);
                    this.cbr.aNL.setSelected(false);
                    this.cbr.aNM.setSelected(false);
                }
                this.cbr.GF();
            }
        });
        final EditText reason = (EditText) layout.findViewById(R.id.content_text);
        dialog.setContentView(layout);
        dialog.show();
        layout.findViewById(R.id.tv_cancel).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ MapSeedDetatilActivity cbr;

            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        final boolean z = isFloor;
        final long j = postId;
        final CommentItem commentItem = item;
        layout.findViewById(R.id.tv_confirm).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ MapSeedDetatilActivity cbr;

            public void onClick(View arg0) {
                if (this.cbr.aNN.isSelected()) {
                    String input = this.cbr.aNN.getText().toString();
                    if (ad.empty((CharSequence) input)) {
                        this.cbr.aNJ = "";
                    } else {
                        this.cbr.aNJ = input;
                    }
                }
                if (this.cbr.aNJ.length() <= 0 || "0".equals(this.cbr.aNJ)) {
                    t.n(this.cbr.aMn, "请填入正确数字");
                    return;
                }
                String score_txt = reason.getText() == null ? "" : reason.getText().toString();
                if (score_txt.trim().length() < 5) {
                    t.n(this.cbr.aMn, "理由不能少于5个字符");
                    return;
                }
                long id = z ? j : commentItem.getCommentID();
                this.cbr.cs(true);
                HLog.debug(MapSeedDetatilActivity.TAG, "hulu is : " + this.cbr.aNJ, new Object[0]);
                TopicModule.Ef().a(z, id, this.cbr.aNJ, score_txt);
                dialog.dismiss();
            }
        });
    }

    private void GJ() {
        final Dialog dialog = new Dialog(this, com.simple.colorful.d.RD());
        View layout = LayoutInflater.from(this).inflate(R.layout.include_topic_lock_dialog, null);
        final LinearLayout reasonLayout = (LinearLayout) layout.findViewById(R.id.ll_reason);
        final ImageView arrowImg = (ImageView) layout.findViewById(R.id.iv_arrow);
        this.aNP = (TextView) layout.findViewById(R.id.tv_reason);
        this.aNQ = this.aMn.getResources().getString(R.string.topic_complaint_reason1);
        reasonLayout.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ MapSeedDetatilActivity cbr;

            public void onClick(View v) {
                if (this.cbr.aNO == null || !this.cbr.aNO.isShowing()) {
                    View popupView = this.cbr.getLayoutInflater().inflate(R.layout.include_topic_lock_reason, null);
                    popupView.findViewById(R.id.tv_reason1).setOnClickListener(this.cbr.aNR);
                    popupView.findViewById(R.id.tv_reason2).setOnClickListener(this.cbr.aNR);
                    popupView.findViewById(R.id.tv_reason3).setOnClickListener(this.cbr.aNR);
                    popupView.findViewById(R.id.tv_reason4).setOnClickListener(this.cbr.aNR);
                    popupView.findViewById(R.id.tv_reason5).setOnClickListener(this.cbr.aNR);
                    popupView.findViewById(R.id.tv_reason6).setOnClickListener(this.cbr.aNR);
                    popupView.findViewById(R.id.tv_reason7).setOnClickListener(this.cbr.aNR);
                    popupView.findViewById(R.id.tv_reason8).setOnClickListener(this.cbr.aNR);
                    arrowImg.setImageDrawable(com.simple.colorful.d.o(this.cbr.aMn, R.attr.drawableComplaintUp));
                    this.cbr.aNO = new PopupWindow(popupView, reasonLayout.getWidth(), at.dipToPx(this.cbr.aMn, 150));
                    this.cbr.aNO.update();
                    this.cbr.aNO.setTouchable(true);
                    this.cbr.aNO.setOutsideTouchable(true);
                    this.cbr.aNO.setBackgroundDrawable(new BitmapDrawable());
                    this.cbr.aNO.setFocusable(true);
                    this.cbr.aNO.setClippingEnabled(false);
                    this.cbr.aNO.showAsDropDown(reasonLayout, 0, at.dipToPx(this.cbr.aMn, 5));
                    this.cbr.aNO.setOnDismissListener(new 1(this));
                    return;
                }
                this.cbr.aNO.dismiss();
            }
        });
        dialog.setContentView(layout);
        dialog.show();
        layout.findViewById(R.id.tv_cancel).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ MapSeedDetatilActivity cbr;

            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        layout.findViewById(R.id.tv_confirm).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ MapSeedDetatilActivity cbr;

            public void onClick(View arg0) {
                this.cbr.aSB.showProgressDialog(this.cbr.aMn, "请求处理中..");
                this.cbr.aNm.x(this.cbr.aMR);
                this.cbr.aNm.aP(this.cbr.aNQ);
                this.cbr.aNm.eY();
                dialog.dismiss();
            }
        });
    }

    private void a(final TopicItem topic, final CommentItem comment) {
        this.aNG = UtilsMenu.a((Context) this, false, new CommonMenuDialogListener(this) {
            final /* synthetic */ MapSeedDetatilActivity cbr;

            public void pressMenuById(int inIndex, Object inItem) {
                this.cbr.aNG.dismissDialog();
                if (comment == null) {
                    this.cbr.aSB.showProgressDialog(this.cbr.aMn, "正在提交举报");
                    com.huluxia.module.profile.g.Eb().m(topic.getPostID(), inIndex);
                    return;
                }
                this.cbr.aSB.showProgressDialog(this.cbr.aMn, "正在提交举报");
                com.huluxia.module.profile.g.Eb().n(comment.getCommentID(), inIndex);
            }
        });
        this.aNG.updateCurFocusIndex(-1);
        this.aNG.showMenu(null, null);
    }

    private void ky(final int opt) {
        if (opt == MENU_VALUE.LOCK_TOPIC.ordinal() || opt == MENU_VALUE.REMOVE_TOPIC.ordinal() || opt == MENU_VALUE.REMOVE_COMMENT.ordinal()) {
            final Dialog dialog = new Dialog(this.aMn, com.simple.colorful.d.RD());
            View layout = LayoutInflater.from(this.aMn).inflate(R.layout.include_dialog_two, null);
            TextView tv_msg = (TextView) layout.findViewById(R.id.tv_msg);
            if (opt == MENU_VALUE.LOCK_TOPIC.ordinal()) {
                tv_msg.setText("确认锁定话题吗？");
            } else if (opt == MENU_VALUE.REMOVE_TOPIC.ordinal()) {
                tv_msg.setText("确认删除话题吗？");
            } else if (opt == MENU_VALUE.REMOVE_COMMENT.ordinal()) {
                tv_msg.setText("确认删除回复吗？");
            }
            dialog.setCancelable(false);
            dialog.setContentView(layout);
            dialog.show();
            layout.findViewById(R.id.tv_cancel).setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ MapSeedDetatilActivity cbr;

                public void onClick(View arg0) {
                    dialog.dismiss();
                }
            });
            layout.findViewById(R.id.tv_confirm).setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ MapSeedDetatilActivity cbr;

                public void onClick(View arg0) {
                    dialog.dismiss();
                    if (opt == MENU_VALUE.LOCK_TOPIC.ordinal()) {
                        TopicModule.Ef().aV(this.cbr.aMR);
                    } else if (opt == MENU_VALUE.REMOVE_TOPIC.ordinal()) {
                        TopicModule.Ef().aX(this.cbr.aMR);
                    } else if (opt == MENU_VALUE.REMOVE_COMMENT.ordinal()) {
                        TopicModule.Ef().g(this.cbr.aMH.getCommentID(), this.cbr.aMH.seq);
                    }
                }
            });
        }
    }

    private void a(CommentItem clickItem, boolean isReplyTopic) {
        if (this.aMT == null) {
            t.download_toast(this.aMn, "数据为空，请先下拉刷新本页面");
        } else if (!com.huluxia.data.j.ep().ey()) {
            t.an(this.aMn);
        } else if (this.aMT.state != 1) {
            t.download_toast(this.aMn, "帖子已经被删除，无法评论");
        } else if (isReplyTopic) {
            t.a(this.aMn, this.aMT, this.aMT != null ? this.aMT.getUserInfo() : null);
        } else {
            t.a(this.aMn, this.aMT, this.aMH, true);
        }
    }

    protected void kj(int themeId) {
        super.kj(themeId);
        Gv();
        this.aMx.notifyDataSetChanged();
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        if (this.aMx != null) {
            com.simple.colorful.setter.k childSetter = new com.simple.colorful.setter.j((ViewGroup) this.aEq.getRefreshableView());
            childSetter.a(this.aMx);
            builder.a(childSetter);
        }
        builder.aY(R.id.restopic, R.attr.backgroundDefault).aY(R.id.footer_container, R.attr.backgroundDim).aY(R.id.topic_bottom_split, R.attr.splitColorDim).j(this.aMB, R.attr.backgroundPagePre).j(this.aMC, R.attr.backgroundPageNext).j(this.aMD, R.attr.backgroundTopicButton).j(this.aME, R.attr.backgroundTopicButton).a(this.aMD, R.attr.textColorTopicButton).a(this.aME, R.attr.textColorTopicButton).aY(R.id.rly_float_header, R.attr.backgroundDim6).ba(R.id.tv_map_name, 16842808).ba(R.id.tv_generate, 16842808).aZ(R.id.btn_generate, R.attr.drawableResTopicGreen).ba(R.id.btn_generate, 16842809).a(this.aMq);
    }
}
