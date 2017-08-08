package com.MCWorld.ui.bbs;

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
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.ViewSwitcher;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.MCWorld.HTApplication;
import com.MCWorld.b;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.m;
import com.MCWorld.controller.resource.ResourceCtrl;
import com.MCWorld.controller.resource.bean.ResTaskInfo;
import com.MCWorld.controller.resource.bean.ResTaskInfo.State;
import com.MCWorld.data.PageList;
import com.MCWorld.data.UserBaseInfo;
import com.MCWorld.data.map.MapItem;
import com.MCWorld.data.map.f.a;
import com.MCWorld.data.topic.CommentItem;
import com.MCWorld.data.topic.TopicItem;
import com.MCWorld.data.topic.e;
import com.MCWorld.framework.BaseEvent;
import com.MCWorld.framework.base.http.module.ProgressInfo;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.framework.base.widget.dialog.CommonMenuDialog;
import com.MCWorld.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.CommonMenuDialogListener;
import com.MCWorld.framework.base.widget.title.TitleBar;
import com.MCWorld.framework.http.HttpMgr;
import com.MCWorld.h;
import com.MCWorld.http.base.f;
import com.MCWorld.http.bbs.topic.q;
import com.MCWorld.i;
import com.MCWorld.jni.UtilsEncrypt;
import com.MCWorld.login.LoginError.LoginErrCode;
import com.MCWorld.module.n;
import com.MCWorld.module.topic.TopicModule;
import com.MCWorld.module.topic.k;
import com.MCWorld.r;
import com.MCWorld.t;
import com.MCWorld.ui.base.BaseLoadingLayout;
import com.MCWorld.ui.base.HTBaseThemeActivity;
import com.MCWorld.ui.itemadapter.topic.ResTopicDetailItemAdapter;
import com.MCWorld.ui.itemadapter.topic.ResTopicDetailItemAdapter.c;
import com.MCWorld.ui.itemadapter.topic.ResTopicDetailItemAdapter.d;
import com.MCWorld.utils.UtilsMenu;
import com.MCWorld.utils.UtilsMenu.MENU_VALUE;
import com.MCWorld.utils.ab;
import com.MCWorld.utils.ad;
import com.MCWorld.utils.at;
import com.MCWorld.utils.au;
import com.MCWorld.utils.aw;
import com.MCWorld.utils.j;
import com.MCWorld.utils.o;
import com.MCWorld.widget.Constants;
import com.MCWorld.widget.Constants.DownFileType;
import com.MCWorld.widget.viewpager.PagerSlidingTabStrip;
import com.MCWorld.widget.viewpager.WrapContentHeightViewPager;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ResTopicDetailActivity extends HTBaseThemeActivity implements OnClickListener, c, d {
    protected static final String TAG = "ResTopicDetailActivity";
    private static final int aNb = 0;
    private static final int aNc = 1;
    private static final int aNd = 2;
    private final int PAGE_SIZE = 20;
    private com.MCWorld.utils.c aEV;
    private PullToRefreshListView aEq;
    private CallbackHandler aGO = new CallbackHandler(this) {
        final /* synthetic */ ResTopicDetailActivity aNS;

        {
            this.aNS = this$0;
        }

        @MessageHandler(message = 257)
        public void onDownloadComplete(String url) {
            this.aNS.GL();
        }

        @MessageHandler(message = 262)
        public void onUnzipComplete(String url) {
            this.aNS.GL();
        }
    };
    private CommonMenuDialog aHI = null;
    private int aIE = 1;
    protected TitleBar aIw;
    protected BaseLoadingLayout aIy;
    private View aJc;
    private TextView aJd;
    private OnClickListener aKt = new OnClickListener(this) {
        final /* synthetic */ ResTopicDetailActivity aNS;

        {
            this.aNS = this$0;
        }

        public void onClick(View v) {
            if (this.aNS.aMX != null) {
                h cb = b.bq().br();
                if (cb == null) {
                    return;
                }
                if (this.aNS.a(this.aNS.aMX)) {
                    cb.a(this.aNS.aMn, this.aNS.aMX.version, this.aNS.FX());
                    return;
                }
                this.aNS.GK();
                MapItem item = a.convertMapItem(this.aNS.aMX);
                i mapCb = b.bq().bs();
                if (j.eT(j.eM(this.aNS.aMX.name)) && j.Z(this.aNS.aMX.name, this.aNS.aMX.md5)) {
                    mapCb.a(this.aNS.aMn, item, DownFileType.defaultType.Value());
                    return;
                }
                mapCb.a(this.aNS.aMn, item, DownFileType.defaultType.Value());
                if (68 == this.aNS.aMV) {
                    r.ck().O(r.a.jo);
                } else if (69 == this.aNS.aMV) {
                    r.ck().O(r.a.jp);
                } else if (this.aNS.aMW != null && !this.aNS.aMW.equals("default")) {
                    r.ck().O(this.aNS.aMW);
                }
            }
        }
    };
    protected ArrayList<UserBaseInfo> aLZ;
    private ImageButton aMA;
    private ImageButton aMB;
    private ImageButton aMC;
    private Button aMD;
    private Button aME;
    private ViewSwitcher aMF;
    private boolean aMG = false;
    private CommentItem aMH;
    protected TextView aMI;
    protected TextView aMJ;
    protected TextView aMK;
    protected ProgressBar aML;
    protected ProgressBar aMM;
    protected ProgressBar aMN;
    protected ProgressBar aMO;
    protected LinearLayout aMP;
    protected e aMQ;
    protected long aMR;
    protected long aMS;
    protected TopicItem aMT;
    protected long aMU;
    protected long aMV;
    protected String aMW;
    protected a aMX;
    private boolean aMY = false;
    private boolean aMZ = false;
    protected Activity aMn;
    protected ResTopicDetailTitle aMq;
    protected ResTopicDetailCuzTitle aMr;
    protected ImageView aMs;
    private RelativeLayout aMt;
    private TextView aMu;
    private TextView aMv;
    public Button aMw;
    public ResTopicDetailItemAdapter aMx;
    private TextView aMy;
    private ImageButton aMz;
    private int aNA = 0;
    private int aNB = 0;
    private int aNC = 0;
    OnClickListener aND = new OnClickListener(this) {
        final /* synthetic */ ResTopicDetailActivity aNS;

        {
            this.aNS = this$0;
        }

        public void onClick(View v) {
            int i = v.getId();
            if (i == g.tvFirstPageBtn) {
                this.aNS.b(1, this.aNS.aMG, 0);
                this.aNS.GC();
            } else if (i == g.tvEndPageBtn) {
                this.aNS.b(this.aNS.aMx.getPageList().getTotalPage(), this.aNS.aMG, 0);
                this.aNS.GC();
            }
        }
    };
    TopicDetailPageTurnLayout.a aNE = new TopicDetailPageTurnLayout.a(this) {
        final /* synthetic */ ResTopicDetailActivity aNS;

        {
            this.aNS = this$0;
        }

        public void kz(int index) {
            this.aNS.b(index, this.aNS.aMG, 0);
            this.aNS.GC();
        }
    };
    private OnPageChangeListener aNF = new OnPageChangeListener(this) {
        final /* synthetic */ ResTopicDetailActivity aNS;

        {
            this.aNS = this$0;
        }

        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        public void onPageSelected(int position) {
            if (!this.aNS.aMn.isFinishing()) {
                this.aNS.mPos = position;
            }
        }
    };
    private CommonMenuDialog aNG = null;
    private OnClickListener aNH = new OnClickListener(this) {
        final /* synthetic */ ResTopicDetailActivity aNS;

        {
            this.aNS = this$0;
        }

        public void onClick(View v) {
            if (this.aNS.aMX == null || !this.aNS.a(this.aNS.aMX)) {
                t.show_toast(this.aNS.aMn, "下载后才能回复哦~");
            } else {
                this.aNS.a(null, true);
            }
        }
    };
    private OnClickListener aNI = new OnClickListener(this) {
        final /* synthetic */ ResTopicDetailActivity aNS;

        {
            this.aNS = this$0;
        }

        public void onClick(View v) {
            int id = v.getId();
            if (id == g.num1) {
                this.aNS.aNK.setSelected(true);
                this.aNS.aNL.setSelected(false);
                this.aNS.aNM.setSelected(false);
                this.aNS.aNN.setSelected(false);
                this.aNS.GG();
                this.aNS.aNJ = "1";
            } else if (id == g.num2) {
                this.aNS.aNK.setSelected(false);
                this.aNS.aNL.setSelected(true);
                this.aNS.aNM.setSelected(false);
                this.aNS.aNN.setSelected(false);
                this.aNS.GG();
                this.aNS.aNJ = "2";
            } else if (id == g.num5) {
                this.aNS.aNK.setSelected(false);
                this.aNS.aNL.setSelected(false);
                this.aNS.aNM.setSelected(true);
                this.aNS.aNN.setSelected(false);
                this.aNS.GG();
                this.aNS.aNJ = "5";
            }
            this.aNS.GF();
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
        final /* synthetic */ ResTopicDetailActivity aNS;

        {
            this.aNS = this$0;
        }

        public void onClick(View v) {
            int id = v.getId();
            if (id == g.tv_reason1) {
                this.aNS.aNQ = this.aNS.aMn.getResources().getString(m.topic_complaint_reason1);
            } else if (id == g.tv_reason2) {
                this.aNS.aNQ = this.aNS.aMn.getResources().getString(m.topic_complaint_reason2);
            } else if (id == g.tv_reason3) {
                this.aNS.aNQ = this.aNS.aMn.getResources().getString(m.topic_complaint_reason3);
            } else if (id == g.tv_reason4) {
                this.aNS.aNQ = this.aNS.aMn.getResources().getString(m.topic_complaint_reason4);
            } else if (id == g.tv_reason5) {
                this.aNS.aNQ = this.aNS.aMn.getResources().getString(m.topic_complaint_reason5);
            } else if (id == g.tv_reason6) {
                this.aNS.aNQ = this.aNS.aMn.getResources().getString(m.topic_complaint_reason6);
            } else if (id == g.tv_reason7) {
                this.aNS.aNQ = this.aNS.aMn.getResources().getString(m.topic_complaint_reason7);
            } else if (id == g.tv_reason8) {
                this.aNS.aNQ = this.aNS.aMn.getResources().getString(m.topic_complaint_reason8);
            }
            this.aNS.aNP.setText(this.aNS.aNQ);
            if (this.aNS.aNO != null && this.aNS.aNO.isShowing()) {
                this.aNS.aNO.dismiss();
            }
        }
    };
    private int[] aNa = new int[0];
    private int aNe = 0;
    private int aNf = 0;
    public int aNg = 0;
    private float aNh = 0.0f;
    private Drawable aNi;
    private LinearLayout aNj;
    private RelativeLayout aNk;
    private RelativeLayout aNl;
    private q aNm = new q();
    private ImageButton aNn;
    private boolean aNo = true;
    protected String aNp = "地图";
    protected String aNq = "地图投稿";
    protected String aNr = "JS";
    protected String aNs = "JS投稿";
    protected String aNt = "皮肤";
    protected String aNu = "皮肤投稿";
    protected String aNv = "材质";
    protected String aNw = "材质投稿";
    private float aNx = 0.0f;
    private PopupWindow aNy;
    private WrapContentHeightViewPager aNz;
    private CallbackHandler aky = new CallbackHandler(this) {
        final /* synthetic */ ResTopicDetailActivity aNS;

        {
            this.aNS = this$0;
        }

        @MessageHandler(message = 768)
        public void onRecvTopicinfo(boolean succ, com.MCWorld.module.topic.j info, int position, long postId, Context context) {
            if (context == this.aNS.aMn || postId == this.aNS.aMR) {
                this.aNS.cs(false);
                if (!succ || info == null) {
                    if (info != null && info.code == 104) {
                        t.n(this.aNS.aMn, ab.n(info.code, info.msg));
                        this.aNS.finish();
                    }
                    if (this.aNS.aNa[1] != 2) {
                        this.aNS.aNa[1] = 0;
                    }
                    this.aNS.GA();
                    HLog.warn(ResTopicDetailActivity.TAG, "Load topic detail failed !!!", new Object[0]);
                    return;
                }
                this.aNS.aNa[1] = 2;
                this.aNS.a(info.getPageList(), info.remindUsers, position);
            }
        }

        @MessageHandler(message = 600)
        public void onCompliant(boolean succ, String msg) {
            this.aNS.cs(false);
            if (succ) {
                t.o(this.aNS.aMn, msg);
            } else {
                t.n(this.aNS.aMn, msg);
            }
        }
    };
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ ResTopicDetailActivity aNS;

        {
            this.aNS = this$0;
        }

        @MessageHandler(message = 513)
        public void onRecvMapDetailData(boolean succ, com.MCWorld.data.map.e info) {
            if (!this.aNS.aNo) {
                return;
            }
            if (succ && info != null && info.isSucc() && info.mapDetailInfo != null && ((long) info.mapDetailInfo.id) == this.aNS.aMU) {
                this.aNS.aMX = info.mapDetailInfo;
                this.aNS.aMq.setInfo(this.aNS.aMX);
                this.aNS.aMr.setMapGallery(this.aNS.aMX);
                this.aNS.b(this.aNS.aMX);
                this.aNS.aMx.e(this.aNS.aMX);
                this.aNS.aNa[0] = 2;
                this.aNS.GL();
                if (this.aNS.Gx()) {
                    this.aNS.FC();
                    return;
                }
                return;
            }
            if (info != null && info.code == 104) {
                t.n(this.aNS.aMn, ab.n(info.code, info.msg));
                this.aNS.finish();
            }
            this.aNS.aNa[0] = 0;
            this.aNS.FB();
            HLog.warn(ResTopicDetailActivity.TAG, "Load map detail failed !!!", new Object[0]);
        }

        @MessageHandler(message = 1538)
        public void onRecvSkinDetailData(boolean succ, com.MCWorld.data.map.e info) {
            if (!this.aNS.aNo) {
                return;
            }
            if (succ && info != null && info.isSucc() && info.mapDetailInfo != null && ((long) info.mapDetailInfo.id) == this.aNS.aMU) {
                this.aNS.aMX = info.mapDetailInfo;
                this.aNS.aMq.setInfo(this.aNS.aMX);
                this.aNS.aMr.setMapGallery(this.aNS.aMX);
                this.aNS.b(this.aNS.aMX);
                this.aNS.aMx.e(this.aNS.aMX);
                this.aNS.aNa[0] = 2;
                this.aNS.GL();
                if (this.aNS.Gx()) {
                    this.aNS.FC();
                    return;
                }
                return;
            }
            if (info != null && info.code == 104) {
                t.n(this.aNS.aMn, ab.n(info.code, info.msg));
                this.aNS.finish();
            }
            this.aNS.aNa[0] = 0;
            this.aNS.FB();
            HLog.warn(ResTopicDetailActivity.TAG, "Load skin detail failed !!!", new Object[0]);
        }

        @MessageHandler(message = 1794)
        public void onRecvWoodDetailData(boolean succ, com.MCWorld.data.map.e info) {
            if (!this.aNS.aNo) {
                return;
            }
            if (succ && info != null && info.isSucc() && info.mapDetailInfo != null && ((long) info.mapDetailInfo.id) == this.aNS.aMU) {
                this.aNS.aMX = info.mapDetailInfo;
                this.aNS.aMq.setInfo(this.aNS.aMX);
                this.aNS.aMr.setMapGallery(this.aNS.aMX);
                this.aNS.b(this.aNS.aMX);
                this.aNS.aMx.e(this.aNS.aMX);
                this.aNS.aNa[0] = 2;
                this.aNS.GL();
                if (this.aNS.Gx()) {
                    this.aNS.FC();
                    return;
                }
                return;
            }
            if (info != null && info.code == 104) {
                t.n(this.aNS.aMn, ab.n(info.code, info.msg));
                this.aNS.finish();
            }
            this.aNS.aNa[0] = 0;
            this.aNS.FB();
            HLog.warn(ResTopicDetailActivity.TAG, "Load wood detail failed !!!", new Object[0]);
        }

        @MessageHandler(message = 2320)
        public void onFavoriteTopicCheck(boolean succ, long id, boolean isFavor) {
            if (succ && id == this.aNS.aMR) {
                this.aNS.cs(false);
                this.aNS.st = isFavor;
                this.aNS.Gv();
            }
        }

        @MessageHandler(message = 2313)
        public void onFavoriteTopic(boolean succ, long id, boolean isFavor) {
            if (id == this.aNS.aMR) {
                this.aNS.cs(false);
                if (succ) {
                    this.aNS.st = isFavor;
                    this.aNS.Gv();
                    t.o(this.aNS.aMn, isFavor ? "收藏成功" : "取消收藏成功");
                    return;
                }
                t.n(this.aNS.aMn, isFavor ? "收藏失败\n网络问题" : "取消收藏失败\n网络问题");
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
            if (id == this.aNS.aMR) {
                this.aNS.cs(false);
                if (succ) {
                    t.o(this.aNS.aMn, "删除话题成功");
                } else {
                    t.n(this.aNS.aMn, "删除话题失败\n网络问题");
                }
            }
        }

        @MessageHandler(message = 2310)
        public void onCommentRemove(boolean succ, long id, long seq) {
            this.aNS.cs(false);
            if (succ) {
                t.o(this.aNS.aMn, "删除回复成功");
                this.aNS.k(this.aNS.aMx.getPageList().getCurrPageNo(), this.aNS.aMG);
                return;
            }
            t.n(this.aNS.aMn, "删除回复失败\n网络问题");
        }

        @MessageHandler(message = 2307)
        public void onTopicLock(boolean succ, long id) {
            this.aNS.cs(false);
            if (succ) {
                t.o(this.aNS.aMn, "锁定话题成功");
            } else {
                t.n(this.aNS.aMn, "锁定话题失败\n网络问题");
            }
        }

        @MessageHandler(message = 2308)
        public void onTopicUnLock(boolean succ, long id) {
            this.aNS.cs(false);
            if (succ) {
                t.o(this.aNS.aMn, "解锁话题成功");
            } else {
                t.n(this.aNS.aMn, "解锁话题失败\n网络问题");
            }
        }

        @MessageHandler(message = 2311)
        public void onCompliant(boolean succ) {
            this.aNS.cs(false);
            if (succ) {
                t.o(this.aNS.aMn, "举报成功，等待处理");
            } else {
                t.n(this.aNS.aMn, "举报失败，请重试");
            }
        }

        @MessageHandler(message = 2306)
        public void onCreditTransfer(boolean succ, com.MCWorld.data.topic.b info) {
            this.aNS.cs(false);
            if (!succ || info == null) {
                t.n(this.aNS.aMn, "赠送葫芦失败\n网络问题");
                return;
            }
            String msg = info.msg;
            if (info.isSucc()) {
                t.o(this.aNS.aMn, msg);
            } else {
                t.n(this.aNS.aMn, msg);
            }
        }

        @MessageHandler(message = 2323)
        public void onRecvReviewAuth(boolean succ, boolean authorized) {
            this.aNS.cs(false);
            if (!this.aNS.aNo) {
                return;
            }
            if (succ) {
                this.aNS.aNa[2] = 2;
                if (authorized) {
                    this.aNS.aMY = true;
                    this.aNS.GB();
                    this.aNS.GL();
                }
                if (this.aNS.Gx()) {
                    this.aNS.FC();
                    return;
                }
                return;
            }
            this.aNS.aNa[2] = 0;
            this.aNS.FB();
            HLog.warn(ResTopicDetailActivity.TAG, "Load review auth failed !!!", new Object[0]);
        }

        @MessageHandler(message = 2324)
        public void onRecvReviewResult(boolean succ, boolean handled, String errorMsg) {
            this.aNS.cs(false);
            if (succ) {
                this.aNS.aMZ = true;
                this.aNS.GL();
                t.o(this.aNS.aMn, "提交审核成功");
                EventNotifyCenter.notifyEvent(n.class, n.awB, Boolean.valueOf(true));
                return;
            }
            if (handled) {
                this.aNS.aMZ = true;
                this.aNS.GL();
            }
            t.n(this.aNS.aMn, errorMsg);
        }
    };
    private CallbackHandler mDownloadCallback = new CallbackHandler(this) {
        final /* synthetic */ ResTopicDetailActivity aNS;

        {
            this.aNS = this$0;
        }

        @MessageHandler(message = 258)
        public void onProgress(String url, String path, ProgressInfo progressInfo) {
            HLog.debug(this, "onProgress info = " + progressInfo + ", url = " + url, new Object[0]);
            this.aNS.GL();
        }

        @MessageHandler(message = 256)
        public void onDownloadSucc(String url, String path) {
            if (true == this.aNS.aNo) {
                if (this.aNS.aNp.equals(this.aNS.FT()) || this.aNS.aNq.equals(this.aNS.FU())) {
                    r.ck().K_umengEvent(hlx.data.tongji.a.bMI);
                    com.MCWorld.mctool.e.Dk().iQ(1);
                } else if (this.aNS.aNr.equals(this.aNS.FT()) || this.aNS.aNs.equals(this.aNS.FU())) {
                    r.ck().K_umengEvent(hlx.data.tongji.a.bMJ);
                    com.MCWorld.mctool.e.Dk().iR(1);
                } else if (this.aNS.aNt.equals(this.aNS.FT()) || this.aNS.aNu.equals(this.aNS.FU())) {
                    r.ck().K_umengEvent(hlx.data.tongji.a.bML);
                    com.MCWorld.mctool.e.Dk().iS(1);
                } else if (this.aNS.aNv.equals(this.aNS.FT()) || this.aNS.aNw.equals(this.aNS.FU())) {
                    r.ck().K_umengEvent(hlx.data.tongji.a.bMK);
                    com.MCWorld.mctool.e.Dk().iT(1);
                }
            }
            this.aNS.GL();
        }

        @MessageHandler(message = 259)
        public void onDownloadCancel(String url, String path) {
            HLog.debug(this, "recv download cancel url = " + url, new Object[0]);
            this.aNS.GL();
        }

        @MessageHandler(message = 257)
        public void onDownloadError(String url, String path, Object context) {
            this.aNS.GL();
        }
    };
    private int mPos = 0;
    private View mRightView;
    private CallbackHandler pl = new CallbackHandler(this) {
        final /* synthetic */ ResTopicDetailActivity aNS;

        {
            this.aNS = this$0;
        }

        @MessageHandler(message = 1025)
        public void onLogin(boolean succ, String client, String mail, String encryptPwd, String openId, int code, String msg, LoginErrCode errCoder) {
            if (succ) {
                this.aNS.cs(true);
                this.aNS.ek("");
                TopicModule.Ef().Eh();
            }
        }

        @MessageHandler(message = 1026)
        public void onAutoLogin(boolean succ, String clientid, LoginErrCode error) {
            if (succ) {
                this.aNS.cs(true);
                this.aNS.ek("");
                TopicModule.Ef().Eh();
            }
        }
    };
    private boolean st = false;

    private class TopicDetailViewPagerAdapter extends PagerAdapter {
        final /* synthetic */ ResTopicDetailActivity aNS;
        public List<View> aOg;
        public List<String> aOh;

        public TopicDetailViewPagerAdapter(ResTopicDetailActivity resTopicDetailActivity, List<View> mListViews, List<String> mListTabTitle) {
            this.aNS = resTopicDetailActivity;
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
        setContentView(com.MCWorld.bbs.b.i.activity_res_topic_cuz);
        EventNotifyCenter.add(n.class, this.mCallback);
        EventNotifyCenter.add(BaseEvent.class, this.mDownloadCallback);
        EventNotifyCenter.add(com.MCWorld.controller.c.class, this.aGO);
        EventNotifyCenter.add(com.MCWorld.login.e.class, this.pl);
        EventNotifyCenter.add(com.MCWorld.module.h.class, this.aky);
        this.aMn = this;
        this.aNk = (RelativeLayout) findViewById(g.empty_48);
        this.aNl = (RelativeLayout) findViewById(g.empty_96);
        this.aMW = getIntent().getStringExtra("src");
        this.aMV = getIntent().getLongExtra("catId", 0);
        this.aMX = (a) getIntent().getParcelableExtra(r.gO);
        if (this.aMX != null) {
            this.aMU = (long) this.aMX.id;
            this.aMR = this.aMX.postID;
        } else {
            this.aMU = getIntent().getLongExtra("mapId", 0);
            this.aMR = getIntent().getLongExtra("postId", 0);
            this.aMS = getIntent().getLongExtra("tagId", 0);
        }
        GM();
        this.aIy = (BaseLoadingLayout) findViewById(g.loading_layout);
        this.aIy.setRetryClickListener(new BaseLoadingLayout.b(this) {
            final /* synthetic */ ResTopicDetailActivity aNS;

            {
                this.aNS = this$0;
            }

            public void onRetryClick(View view) {
                this.aNS.Fy();
                this.aNS.Gy();
            }
        });
        this.aNm.bb(6);
        this.aNm.x(this.aMR);
        this.aNm.a(new f(this) {
            final /* synthetic */ ResTopicDetailActivity aNS;

            {
                this.aNS = this$0;
            }

            public void a(com.MCWorld.http.base.d response) {
            }

            public void b(com.MCWorld.http.base.d response) {
                this.aNS.cs(false);
                if (response.fe() == 6) {
                    t.o(this.aNS.aMn, "锁定话题成功");
                    if (this.aNS.aMT != null) {
                        this.aNS.aMT.setState(3);
                    }
                    this.aNS.cx(this.aNS.aMG);
                }
            }

            public void c(com.MCWorld.http.base.d response) {
                this.aNS.cs(false);
                if (response.fe() == 6) {
                    t.n(this.aNS.aMn, "锁定话题失败");
                }
            }
        });
        this.aMx = new ResTopicDetailItemAdapter(this);
        this.aMx.a((c) this);
        this.aMx.a((d) this);
        EP();
        Fa();
        Gs();
        int[] iArr = new int[3];
        iArr[0] = 0;
        iArr[1] = 0;
        int i = (com.MCWorld.data.j.ep().ey() && GO()) ? 0 : 2;
        iArr[2] = i;
        this.aNa = iArr;
        if (HTApplication.fA == null) {
            TopicModule.Ef().Eg();
        }
        Gr();
        Fy();
        Gy();
        this.aJc = findViewById(g.loading);
        this.aJc.setVisibility(8);
        this.aJd = (TextView) findViewById(g.progressTxt);
        Fr();
    }

    private void Gr() {
        final Button downButton = this.aMq.getDownButton();
        downButton.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener(this) {
            final /* synthetic */ ResTopicDetailActivity aNS;

            public void onGlobalLayout() {
                this.aNS.aMM.getLayoutParams().width = downButton.getWidth();
                if (this.aNS.aML != null) {
                    this.aNS.aML.getLayoutParams().width = downButton.getWidth();
                }
                this.aNS.aMN.getLayoutParams().width = downButton.getWidth();
                downButton.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
    }

    protected void cs(boolean show) {
        if (this.aJc != null) {
            if (show) {
                this.aJc.setVisibility(0);
            } else {
                this.aJc.setVisibility(8);
            }
        }
    }

    private void EP() {
        this.aIw = (TitleBar) findViewById(g.title_bar);
        this.aIw.setLeftLayout(com.MCWorld.bbs.b.i.layout_title_left_icon_and_text, new LayoutParams(-2, -1));
        this.aNi = this.aIw.getBackground();
        this.aNi = this.aNi.mutate();
        this.aMM = this.aMq.getmProgressTop();
        this.aMy = (TextView) this.aIw.findViewById(g.header_title);
        this.aMy.setTextColor(com.simple.colorful.d.getColor(this, 16842809));
        this.aMs = (ImageView) this.aIw.findViewById(g.sys_header_back);
        this.aMs.setImageDrawable(com.simple.colorful.d.o(this.aMn, com.MCWorld.bbs.b.c.back));
        View backView = findViewById(g.rl_header_back);
        backView.setBackgroundDrawable(com.simple.colorful.d.o(this.aMn, com.MCWorld.bbs.b.c.backgroundTitleBarButton));
        backView.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ResTopicDetailActivity aNS;

            {
                this.aNS = this$0;
            }

            public void onClick(View v) {
                this.aNS.finish();
            }
        });
        this.aIw.setRightLayout(com.MCWorld.bbs.b.i.include_topiclist_titlebar_right);
        this.mRightView = this.aIw.getRightView();
        this.aMz = (ImageButton) this.aIw.findViewById(g.sys_header_flright_img);
        this.aMz.setBackgroundResource(com.MCWorld.bbs.b.f.sl_title_bar_button);
        this.aMz.setVisibility(0);
        this.aMz.setOnClickListener(this);
        Gv();
        Gt();
        this.aMA = (ImageButton) this.aIw.findViewById(g.header_flright_second_img);
        this.aMA.setBackgroundResource(com.MCWorld.bbs.b.f.sl_title_bar_button);
        this.aMA.setVisibility(0);
        Gw();
        this.aMA.setOnClickListener(this);
        this.aIg = (TextView) this.aIw.findViewById(g.tv_msg);
        this.aNn = (ImageButton) this.aIw.findViewById(g.img_msg);
        this.aNn.setBackgroundResource(com.MCWorld.bbs.b.f.sl_title_bar_button);
        this.aNn.setVisibility(0);
        this.aNn.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ResTopicDetailActivity aNS;

            {
                this.aNS = this$0;
            }

            public void onClick(View v) {
                t.a(this.aNS.aMn, HTApplication.bM());
            }
        });
        if (GO()) {
            eo(FU());
        } else {
            eo(FT());
        }
        this.aMt = (RelativeLayout) findViewById(g.rly_float_header);
        this.aMu = (TextView) findViewById(g.tv_map_name);
        this.aMv = (TextView) findViewById(g.tv_map_size);
        this.aMw = (Button) findViewById(g.btn_float_download);
    }

    private void eo(String title) {
        this.aMy.setText(title);
    }

    private void b(a mapItem) {
        this.aMu.setText(aw.W(mapItem.name, 10));
        if (mapItem.mapSize == null || !aw.validNumber(mapItem.mapSize)) {
            this.aMv.setText("未知大小");
        } else {
            this.aMv.setText(aw.bA(Long.valueOf(mapItem.mapSize).longValue()));
        }
    }

    private void Fa() {
        this.aEq = (PullToRefreshListView) findViewById(g.restopiclistview);
        this.aNj = new LinearLayout(this);
        this.aNj.setOrientation(1);
        this.aNj.addView(this.aMq);
        ((ListView) this.aEq.getRefreshableView()).addHeaderView(this.aNj);
        this.aEq.setAdapter(this.aMx);
        this.aEq.setOnRefreshListener(new OnRefreshListener<ListView>(this) {
            final /* synthetic */ ResTopicDetailActivity aNS;

            {
                this.aNS = this$0;
            }

            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                this.aNS.Gz();
                if (this.aNS.GO() && !this.aNS.aMZ && com.MCWorld.data.j.ep().ey()) {
                    TopicModule.Ef().Eh();
                }
            }
        });
        this.aEV = new com.MCWorld.utils.c((ListView) this.aEq.getRefreshableView());
        this.aEV.a(new com.MCWorld.utils.c.a(this) {
            final /* synthetic */ ResTopicDetailActivity aNS;

            {
                this.aNS = this$0;
            }

            public void onLoadData() {
                this.aNS.Gz();
            }

            public boolean shouldLoadData() {
                if (this.aNS.aMQ == null) {
                    this.aNS.aEV.onLoadComplete();
                    return false;
                } else if (this.aNS.aMQ.totalPage > this.aNS.aMQ.currPageNo) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        this.aEV.setParentOnScrollListener(new OnScrollListener(this) {
            final /* synthetic */ ResTopicDetailActivity aNS;

            {
                this.aNS = this$0;
            }

            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (this.aNS.aIE != 1) {
                    this.aNS.GI();
                    return;
                }
                int dip_48 = at.dipToPx(this.aNS.aMn, 48);
                float scrollDistance;
                float distanceDelta;
                if (this.aNS.aNg == 1) {
                    View downBtn = this.aNS.aMx.getDownButton();
                    if (downBtn != null) {
                        int currHeight = this.aNS.i(downBtn) - dip_48;
                        if (this.aNS.aNh == 0.0f) {
                            if (currHeight > 0) {
                                this.aNS.aNh = (float) currHeight;
                            } else {
                                return;
                            }
                        }
                        scrollDistance = this.aNS.aNh - ((float) currHeight);
                        if (scrollDistance >= 0.0f) {
                            this.aNS.aNx = scrollDistance;
                            distanceDelta = this.aNS.aNx - this.aNS.aNh;
                            if (distanceDelta >= 0.0f) {
                                this.aNS.GI();
                                this.aNS.aNi.setAlpha(255);
                                this.aNS.aMt.setVisibility(0);
                                if (distanceDelta >= ((float) downBtn.getMeasuredHeight())) {
                                    ViewCompat.setAlpha(this.aNS.aMw, 1.0f);
                                    return;
                                } else {
                                    ViewCompat.setAlpha(this.aNS.aMw, distanceDelta / ((float) downBtn.getMeasuredHeight()));
                                    return;
                                }
                            }
                            this.aNS.GH();
                            this.aNS.aNi.setAlpha((int) ((this.aNS.aNx / this.aNS.aNh) * 255.0f));
                            this.aNS.aMt.setVisibility(8);
                            return;
                        }
                        return;
                    }
                    return;
                }
                View splitView = this.aNS.aMq.getSplitTopView();
                int originHeight = at.dipToPx(this.aNS.aMn, 104);
                if (splitView != null) {
                    scrollDistance = (float) (originHeight - (this.aNS.i(splitView) - dip_48));
                    if (scrollDistance >= 0.0f) {
                        this.aNS.aNx = scrollDistance;
                        distanceDelta = this.aNS.aNx - ((float) originHeight);
                        if (distanceDelta >= 0.0f) {
                            this.aNS.aMt.setVisibility(0);
                            if (distanceDelta >= ((float) originHeight)) {
                                ViewCompat.setAlpha(this.aNS.aMw, 1.0f);
                                return;
                            } else {
                                ViewCompat.setAlpha(this.aNS.aMw, distanceDelta / ((float) originHeight));
                                return;
                            }
                        }
                        this.aNS.aMt.setVisibility(8);
                    }
                }
            }
        });
        this.aEq.setOnScrollListener(this.aEV);
    }

    private void Gs() {
        this.aMF = (ViewSwitcher) findViewById(g.footer_switcher);
        this.aMF.setDisplayedChild(GO() ? 0 : 1);
        this.aMB = (ImageButton) findViewById(g.btn_prev);
        this.aMC = (ImageButton) findViewById(g.btn_next);
        this.aMD = (Button) findViewById(g.btn_page);
        this.aME = (Button) findViewById(g.btn_comment);
        this.aME.setOnClickListener(this);
        this.aMB.setOnClickListener(this);
        this.aMC.setOnClickListener(this);
        this.aMD.setOnClickListener(this);
        this.aMD.setText("1/1");
        this.aMI = (TextView) findViewById(g.tv_download);
        this.aMO = (ProgressBar) findViewById(g.AppInfoProgress);
        this.aMN = (ProgressBar) findViewById(g.progress_bar_float_download);
        this.aMP = (LinearLayout) findViewById(g.ly_review);
        this.aMJ = (TextView) findViewById(g.tv_review_ok);
        this.aMK = (TextView) findViewById(g.tv_review_reject);
        GB();
        FY();
        GL();
        this.aMJ.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ResTopicDetailActivity aNS;

            {
                this.aNS = this$0;
            }

            public void onClick(View v) {
                this.aNS.cy(true);
            }
        });
        this.aMK.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ResTopicDetailActivity aNS;

            {
                this.aNS = this$0;
            }

            public void onClick(View v) {
                this.aNS.cy(false);
            }
        });
    }

    public int i(View view) {
        if (view == null) {
            return 0;
        }
        int[] location = new int[2];
        view.getLocationInWindow(location);
        return location[1];
    }

    private void Fy() {
        this.aIy.setCurrentPage(0);
    }

    private void FB() {
        this.aIy.setCurrentPage(1);
    }

    private void FC() {
        this.aIy.setCurrentPage(2);
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onStart() {
        super.onStart();
        this.aNo = true;
    }

    protected void onStop() {
        super.onStop();
        this.aNo = false;
    }

    public void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
        EventNotifyCenter.remove(this.mDownloadCallback);
        EventNotifyCenter.remove(this.aGO);
        EventNotifyCenter.remove(this.pl);
        EventNotifyCenter.remove(this.aky);
    }

    protected int FW() {
        return 1000000;
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

    protected String FT() {
        return "地图";
    }

    protected String FU() {
        return "地图投稿";
    }

    private void Gt() {
        if (com.MCWorld.data.j.ep().ey()) {
            TopicModule.Ef().aY(this.aMR);
        }
    }

    private void Gu() {
        boolean z = true;
        if (com.MCWorld.data.j.ep().ey()) {
            cs(true);
            ek("请求处理中..");
            TopicModule Ef = TopicModule.Ef();
            long j = this.aMR;
            if (this.st) {
                z = false;
            }
            Ef.c(j, z);
            return;
        }
        t.an(this);
    }

    private void Gv() {
        if (this.st) {
            this.aMz.setImageResource(com.simple.colorful.d.r(this, com.MCWorld.bbs.b.c.drawableTitleFavorChecked));
        } else {
            this.aMz.setImageResource(com.simple.colorful.d.r(this, com.MCWorld.bbs.b.c.drawableTitleFavor));
        }
    }

    private void Gw() {
        this.aMA.setImageDrawable(com.simple.colorful.d.o(this, com.MCWorld.bbs.b.c.drawableTitleShare));
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
            GN();
            this.aNa[0] = 1;
        }
        if (this.aNa[1] == 0) {
            k(1, this.aMG);
            this.aNa[1] = 1;
        }
        if (this.aNa[2] == 0) {
            TopicModule.Ef().Eh();
            this.aNa[2] = 1;
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
        if (this.aNa[0] == 0) {
            GN();
            this.aNa[0] = 1;
        }
        if (this.aNa[2] == 0) {
            TopicModule.Ef().Eh();
            this.aNa[2] = 1;
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
        String szMsg = String.format(Locale.getDefault(), "加载第%d页", new Object[]{Integer.valueOf(page)});
        int currentPage = this.aIy.getCurrentPage();
        BaseLoadingLayout baseLoadingLayout = this.aIy;
        if (currentPage == 2) {
            cs(true);
            ek(szMsg);
        }
    }

    private void Gz() {
        int page;
        if (this.aNa[0] == 0) {
            GN();
            this.aNa[0] = 1;
        }
        if (this.aNa[2] == 0) {
            TopicModule.Ef().Eh();
            this.aNa[2] = 1;
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
        String szMsg = String.format(Locale.getDefault(), "加载第%d页", new Object[]{Integer.valueOf(page)});
        int currentPage = this.aIy.getCurrentPage();
        BaseLoadingLayout baseLoadingLayout = this.aIy;
        if (currentPage == 2) {
            cs(true);
            ek(szMsg);
        }
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
        int currentPage = this.aIy.getCurrentPage();
        BaseLoadingLayout baseLoadingLayout = this.aIy;
        if (currentPage == 0) {
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
        this.aNg = page.getIsTmp();
        this.aNj.removeView(this.aMq);
        this.aNj.removeView(this.aMr);
        RelativeLayout relativeLayout;
        RelativeLayout relativeLayout2;
        if (this.aNg == 1) {
            if (currPage == 1) {
                GH();
            } else {
                GI();
            }
            this.aNi.setAlpha(currPage == 1 ? 0 : 255);
            relativeLayout = this.aMt;
            if (currPage == 1) {
                i = 8;
            } else {
                i = 0;
            }
            relativeLayout.setVisibility(i);
            ResTopicDetailCuzTitle resTopicDetailCuzTitle = this.aMr;
            if (currPage == 1) {
                i = 0;
            } else {
                i = 8;
            }
            resTopicDetailCuzTitle.setGalleryVisibility(i);
            this.aNj.addView(this.aMr);
            this.aNk.setVisibility(8);
            relativeLayout2 = this.aNl;
            if (currPage != 1) {
                i2 = 0;
            }
            relativeLayout2.setVisibility(i2);
        } else {
            GI();
            this.aNj.addView(this.aMq);
            ResTopicDetailTitle resTopicDetailTitle = this.aMq;
            if (currPage == 1) {
                i = 0;
            } else {
                i = 8;
            }
            resTopicDetailTitle.setVisibility(i);
            relativeLayout = this.aMt;
            if (currPage == 1) {
                i = 8;
            } else {
                i = 0;
            }
            relativeLayout.setVisibility(i);
            relativeLayout = this.aNk;
            if (currPage == 1) {
                i = 0;
            } else {
                i = 8;
            }
            relativeLayout.setVisibility(i);
            relativeLayout2 = this.aNl;
            if (currPage != 1) {
                i2 = 0;
            }
            relativeLayout2.setVisibility(i2);
        }
        this.aMq.setStudioName(page.getStudioInfo() != null ? page.getStudioInfo().name : "无");
        if (currPage > 1) {
            ViewCompat.setAlpha(this.aMw, 1.0f);
        }
        this.aMx.getPageList().addAll(page);
        this.aMx.getPageList().setCurrPageNo(currPage);
        this.aMx.getPageList().setTotalPage(totalPage);
        this.aMx.getPageList().setPageSize(page.getPageSize());
        this.aMx.getPageList().setIsTmp(page.getIsTmp());
        this.aMx.getPageList().setStudioInfo(page.getStudioInfo());
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
        i = this.aIy.getCurrentPage();
        BaseLoadingLayout baseLoadingLayout = this.aIy;
        if (i == 0) {
            this.aIy.FC();
        }
        FY();
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

    protected int FX() {
        return 1;
    }

    private void cy(final boolean reviewOk) {
        final Dialog dialog = new Dialog(this.aMn, com.simple.colorful.d.RD());
        View view = LayoutInflater.from(this).inflate(com.MCWorld.bbs.b.i.dialog_review_submit, null);
        TextView title = (TextView) view.findViewById(g.title);
        if (reviewOk) {
            title.setText("审核通过理由");
        } else {
            title.setText("审核不通过理由");
        }
        final EditText etReson = (EditText) view.findViewById(g.et_reson);
        View ok = view.findViewById(g.tv_ok);
        View cancel = view.findViewById(g.tv_cancel);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.show();
        ok.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ResTopicDetailActivity aNS;

            public void onClick(View v) {
                String reason = etReson.getText().toString().trim();
                if (TextUtils.isEmpty(reason) || reason.length() < 10) {
                    t.n(this.aNS, "审核理由不能低于10个字符");
                } else if (etReson.getLineCount() > 8) {
                    t.n(this.aNS, "审核理由不能超过8行");
                } else {
                    this.aNS.cs(true);
                    this.aNS.ek("加载中");
                    TopicModule.Ef().a(reviewOk, this.aNS.aMR, reason, this.aNS.FX());
                    dialog.dismiss();
                }
            }
        });
        cancel.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ResTopicDetailActivity aNS;

            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void GB() {
        if (this.aMX == null) {
            this.aMI.setText("请稍候");
        } else if (GO() && this.aMY) {
            this.aMI.setText("下载之后进行审核操作");
        }
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == g.sys_header_flright_img) {
            Gu();
        } else if (id == g.header_flright_second_img) {
            String src = "default";
            if (68 == this.aMV) {
                r.ck().O(r.a.jo);
                src = r.a.jv;
            } else if (69 == this.aMV) {
                r.ck().O(r.a.jp);
                src = r.a.jz;
            } else if (!(this.aMW == null || this.aMW.equals("default"))) {
                r.ck().O(this.aMW);
                src = r.a.jq;
            }
            i mapCb = b.bq().bs();
            if (this.aMX != null && mapCb != null) {
                mapCb.a(this.aMn, this.aMX, src);
            }
        } else if (id == g.btn_comment) {
            a(null, true);
        } else if (id == g.btn_prev) {
            b(this.aMx.getPageList().getCurrPageNo() - 1, this.aMG, 0);
        } else if (id == g.btn_next) {
            b(this.aMx.getPageList().getCurrPageNo() + 1, this.aMG, 0);
        } else if (id == g.btn_page && this.aMx.getPageList().getTotalPage() > 1) {
            GD();
        }
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
        View pageTurnView = getLayoutInflater().inflate(com.MCWorld.bbs.b.i.topic_detail_popupwindow_page_turn, null);
        this.aNy = new PopupWindow(this);
        this.aNy.setWidth(-1);
        this.aNy.setHeight(-2);
        this.aNy.setBackgroundDrawable(new ColorDrawable(getResources().getColor(com.MCWorld.bbs.b.d.transparent)));
        this.aNy.setContentView(pageTurnView);
        this.aNy.setFocusable(true);
        this.aNy.setAnimationStyle(com.MCWorld.bbs.b.n.topic_detail_pageturn_popwindow_anim_style);
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
        mLocalResMgrTabs.setTextColorResource(com.MCWorld.bbs.b.d.TabStripTextColor);
        mLocalResMgrTabs.setTextSize(at.dipToPx(this, 15));
        if (com.simple.colorful.d.isDayMode()) {
            mLocalResMgrTabs.setIndicatorColor(getResources().getColor(com.MCWorld.bbs.b.d.TabStripIndicatorColor));
        } else {
            mLocalResMgrTabs.setIndicatorColor(getResources().getColor(com.MCWorld.bbs.b.d.TabStripIndicatorNightColor));
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
                    final /* synthetic */ ResTopicDetailActivity aNS;

                    public void pressMenuById(int inIndex, Object object) {
                        this.aNS.aHI.dismissDialog();
                        if (inIndex == MENU_VALUE.COMMENT.ordinal()) {
                            this.aNS.a(item, true);
                        } else if (inIndex == MENU_VALUE.SEND_HULU.ordinal()) {
                            if (com.MCWorld.data.j.ep().ey()) {
                                this.aNS.a(isFloor, item, this.aNS.aMR);
                            } else {
                                t.an(this.aNS.aMn);
                            }
                        } else if (inIndex == MENU_VALUE.SHAREWIXIN.ordinal()) {
                            if (HTApplication.fA != null) {
                                this.aNS.GE();
                            } else {
                                t.show_toast(this.aNS.aMn, "暂时无法分享");
                            }
                        } else if (inIndex == MENU_VALUE.MOVETOPIC.ordinal()) {
                            t.a(this.aNS.aMn, this.aNS.aMT);
                        } else if (inIndex == MENU_VALUE.UNLOCK_TOPIC.ordinal()) {
                            this.aNS.cs(true);
                            this.aNS.ek("请求处理中..");
                            TopicModule.Ef().aW(this.aNS.aMR);
                        } else if (inIndex == MENU_VALUE.EDITTOPIC.ordinal()) {
                            t.b(this.aNS.aMn, this.aNS.aMT);
                        } else if (inIndex == MENU_VALUE.LOCK_TOPIC.ordinal()) {
                            this.aNS.GJ();
                        } else if (inIndex == MENU_VALUE.REMOVE_TOPIC.ordinal() || inIndex == MENU_VALUE.REMOVE_COMMENT.ordinal()) {
                            this.aNS.ky(inIndex);
                        } else if (inIndex == MENU_VALUE.REPLY.ordinal()) {
                            this.aNS.a(this.aNS.aMH, false);
                        } else if (inIndex == MENU_VALUE.COPY_TEXT.ordinal()) {
                            if (this.aNS.aMH != null) {
                                o.bV(this.aNS.aMH.getText());
                            } else {
                                o.bV(this.aNS.aMT.getDetail());
                            }
                            t.o(this.aNS.aMn, "已经复制到剪切板");
                        } else if (inIndex == MENU_VALUE.REPORT_TOPIC.ordinal()) {
                            this.aNS.a(this.aNS.aMT, null);
                        } else if (inIndex == MENU_VALUE.REPORT_COMMENT.ordinal()) {
                            this.aNS.a(this.aNS.aMT, this.aNS.aMH);
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
            o = com.simple.colorful.d.o(this.aMn, com.MCWorld.bbs.b.c.drawableTopicSendhuluSelected);
        } else {
            o = com.simple.colorful.d.o(this.aMn, com.MCWorld.bbs.b.c.drawableTopicSendhulu);
        }
        radioButton.setBackgroundDrawable(o);
        radioButton = this.aNL;
        if (this.aNL.isSelected()) {
            o = com.simple.colorful.d.o(this.aMn, com.MCWorld.bbs.b.c.drawableTopicSendhuluSelected);
        } else {
            o = com.simple.colorful.d.o(this.aMn, com.MCWorld.bbs.b.c.drawableTopicSendhulu);
        }
        radioButton.setBackgroundDrawable(o);
        radioButton = this.aNM;
        if (this.aNM.isSelected()) {
            o = com.simple.colorful.d.o(this.aMn, com.MCWorld.bbs.b.c.drawableTopicSendhuluSelected);
        } else {
            o = com.simple.colorful.d.o(this.aMn, com.MCWorld.bbs.b.c.drawableTopicSendhulu);
        }
        radioButton.setBackgroundDrawable(o);
        EditText editText = this.aNN;
        if (this.aNN.isSelected()) {
            o = com.simple.colorful.d.o(this.aMn, com.MCWorld.bbs.b.c.drawableTopicSendhuluSelected);
        } else {
            o = com.simple.colorful.d.o(this.aMn, com.MCWorld.bbs.b.c.drawableTopicSendhulu);
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
        View layout = LayoutInflater.from(this).inflate(com.MCWorld.bbs.b.i.include_credit_send, null);
        this.aNK = (RadioButton) layout.findViewById(g.num1);
        this.aNL = (RadioButton) layout.findViewById(g.num2);
        this.aNM = (RadioButton) layout.findViewById(g.num5);
        this.aNK.setSelected(true);
        this.aNJ = "1";
        this.aNN = (EditText) layout.findViewById(g.other_num);
        this.aNN.setVisibility(8);
        GF();
        if (com.MCWorld.data.j.ep().ey()) {
            com.MCWorld.data.g info = com.MCWorld.data.j.ep().ew();
            HLog.info(TAG, "isgold %d", Integer.valueOf(info.isgold));
            if (info != null && info.isgold == 1) {
                this.aNN.setVisibility(0);
            }
        }
        this.aNK.setOnClickListener(this.aNI);
        this.aNL.setOnClickListener(this.aNI);
        this.aNM.setOnClickListener(this.aNI);
        this.aNN.setOnFocusChangeListener(new OnFocusChangeListener(this) {
            final /* synthetic */ ResTopicDetailActivity aNS;

            {
                this.aNS = this$0;
            }

            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    this.aNS.aNN.setSelected(true);
                    this.aNS.aNK.setSelected(false);
                    this.aNS.aNL.setSelected(false);
                    this.aNS.aNM.setSelected(false);
                }
                this.aNS.GF();
            }
        });
        final EditText reason = (EditText) layout.findViewById(g.content_text);
        dialog.setContentView(layout);
        dialog.show();
        layout.findViewById(g.tv_cancel).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ResTopicDetailActivity aNS;

            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        final boolean z = isFloor;
        final long j = postId;
        final CommentItem commentItem = item;
        layout.findViewById(g.tv_confirm).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ResTopicDetailActivity aNS;

            public void onClick(View arg0) {
                if (this.aNS.aNN.isSelected()) {
                    String input = this.aNS.aNN.getText().toString();
                    if (ad.empty((CharSequence) input)) {
                        this.aNS.aNJ = "";
                    } else {
                        this.aNS.aNJ = input;
                    }
                }
                if (this.aNS.aNJ.length() <= 0 || "0".equals(this.aNS.aNJ)) {
                    t.n(this.aNS.aMn, "请填入正确数字");
                    return;
                }
                String score_txt = reason.getText() == null ? "" : reason.getText().toString();
                if (score_txt.trim().length() < 5) {
                    t.n(this.aNS.aMn, "理由不能少于5个字符");
                    return;
                }
                long id = z ? j : commentItem.getCommentID();
                this.aNS.cs(true);
                this.aNS.ek("加载中...");
                HLog.debug(ResTopicDetailActivity.TAG, "hulu is : " + this.aNS.aNJ, new Object[0]);
                TopicModule.Ef().a(z, id, this.aNS.aNJ, score_txt);
                dialog.dismiss();
            }
        });
    }

    private void GH() {
        if (com.simple.colorful.d.RB()) {
            this.aMy.setTextColor(-1);
            this.aMs.setImageDrawable(getResources().getDrawable(com.MCWorld.bbs.b.f.spectial_back_selector));
            this.aMA.setImageDrawable(getResources().getDrawable(com.MCWorld.bbs.b.f.spectial_share_selector));
            this.aNn.setImageDrawable(getResources().getDrawable(com.MCWorld.bbs.b.f.spectial_message_selector));
            this.aMz.setImageDrawable(getResources().getDrawable(com.MCWorld.bbs.b.f.spectial_fav_selector));
        }
    }

    private void GI() {
        this.aMs.setImageDrawable(com.simple.colorful.d.o(this.aMn, com.MCWorld.bbs.b.c.back));
        this.aMy.setTextColor(com.simple.colorful.d.getColor(this.aMn, 16842809));
        this.aNn.setImageResource(com.simple.colorful.d.r(this.aMn, com.MCWorld.bbs.b.c.drawableTitleMsg));
        Gv();
        Gw();
    }

    private void GJ() {
        final Dialog dialog = new Dialog(this, com.simple.colorful.d.RD());
        View layout = LayoutInflater.from(this).inflate(com.MCWorld.bbs.b.i.include_topic_lock_dialog, null);
        final LinearLayout reasonLayout = (LinearLayout) layout.findViewById(g.ll_reason);
        final ImageView arrowImg = (ImageView) layout.findViewById(g.iv_arrow);
        this.aNP = (TextView) layout.findViewById(g.tv_reason);
        this.aNQ = this.aMn.getResources().getString(m.topic_complaint_reason1);
        reasonLayout.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ResTopicDetailActivity aNS;

            public void onClick(View v) {
                if (this.aNS.aNO == null || !this.aNS.aNO.isShowing()) {
                    View popupView = this.aNS.getLayoutInflater().inflate(com.MCWorld.bbs.b.i.include_topic_lock_reason, null);
                    popupView.findViewById(g.tv_reason1).setOnClickListener(this.aNS.aNR);
                    popupView.findViewById(g.tv_reason2).setOnClickListener(this.aNS.aNR);
                    popupView.findViewById(g.tv_reason3).setOnClickListener(this.aNS.aNR);
                    popupView.findViewById(g.tv_reason4).setOnClickListener(this.aNS.aNR);
                    popupView.findViewById(g.tv_reason5).setOnClickListener(this.aNS.aNR);
                    popupView.findViewById(g.tv_reason6).setOnClickListener(this.aNS.aNR);
                    popupView.findViewById(g.tv_reason7).setOnClickListener(this.aNS.aNR);
                    popupView.findViewById(g.tv_reason8).setOnClickListener(this.aNS.aNR);
                    arrowImg.setImageDrawable(com.simple.colorful.d.o(this.aNS.aMn, com.MCWorld.bbs.b.c.drawableComplaintUp));
                    this.aNS.aNO = new PopupWindow(popupView, reasonLayout.getWidth(), at.dipToPx(this.aNS.aMn, 150));
                    this.aNS.aNO.update();
                    this.aNS.aNO.setTouchable(true);
                    this.aNS.aNO.setOutsideTouchable(true);
                    this.aNS.aNO.setBackgroundDrawable(new BitmapDrawable());
                    this.aNS.aNO.setFocusable(true);
                    this.aNS.aNO.setClippingEnabled(false);
                    this.aNS.aNO.showAsDropDown(reasonLayout, 0, at.dipToPx(this.aNS.aMn, 5));
                    this.aNS.aNO.setOnDismissListener(new 1(this));
                    return;
                }
                this.aNS.aNO.dismiss();
            }
        });
        dialog.setContentView(layout);
        dialog.show();
        layout.findViewById(g.tv_cancel).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ResTopicDetailActivity aNS;

            public void onClick(View arg0) {
                dialog.dismiss();
            }
        });
        layout.findViewById(g.tv_confirm).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ResTopicDetailActivity aNS;

            public void onClick(View arg0) {
                this.aNS.cs(true);
                this.aNS.ek("请求处理中..");
                this.aNS.aNm.x(this.aNS.aMR);
                this.aNS.aNm.aP(this.aNS.aNQ);
                this.aNS.aNm.eY();
                dialog.dismiss();
            }
        });
    }

    private void ky(final int opt) {
        if (opt == MENU_VALUE.LOCK_TOPIC.ordinal() || opt == MENU_VALUE.REMOVE_TOPIC.ordinal() || opt == MENU_VALUE.REMOVE_COMMENT.ordinal()) {
            final Dialog dialog = new Dialog(this.aMn, com.simple.colorful.d.RD());
            View layout = LayoutInflater.from(this.aMn).inflate(com.MCWorld.bbs.b.i.include_dialog_two, null);
            TextView tv_msg = (TextView) layout.findViewById(g.tv_msg);
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
            layout.findViewById(g.tv_cancel).setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ ResTopicDetailActivity aNS;

                public void onClick(View arg0) {
                    dialog.dismiss();
                }
            });
            layout.findViewById(g.tv_confirm).setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ ResTopicDetailActivity aNS;

                public void onClick(View arg0) {
                    dialog.dismiss();
                    this.aNS.cs(true);
                    this.aNS.ek("请求处理中..");
                    if (opt == MENU_VALUE.LOCK_TOPIC.ordinal()) {
                        TopicModule.Ef().aV(this.aNS.aMR);
                    } else if (opt == MENU_VALUE.REMOVE_TOPIC.ordinal()) {
                        TopicModule.Ef().aX(this.aNS.aMR);
                    } else if (opt == MENU_VALUE.REMOVE_COMMENT.ordinal()) {
                        TopicModule.Ef().g(this.aNS.aMH.getCommentID(), this.aNS.aMH.seq);
                    }
                }
            });
        }
    }

    private void a(CommentItem commentItem, boolean isReplyTopic) {
        if (this.aMT == null) {
            t.show_toast(this.aMn, "数据为空，请先下拉刷新本页面");
        } else if (!com.MCWorld.data.j.ep().ey()) {
            t.an(this.aMn);
        } else if (this.aMT.state != 1) {
            t.show_toast(this.aMn, "帖子已经被删除，无法评论");
        } else if (isReplyTopic) {
            t.a(this.aMn, this.aMT, this.aMT != null ? this.aMT.getUserInfo() : null);
        } else {
            t.a(this.aMn, this.aMT, commentItem, true);
        }
    }

    private void a(final TopicItem topic, final CommentItem comment) {
        this.aNG = UtilsMenu.a((Context) this, false, new CommonMenuDialogListener(this) {
            final /* synthetic */ ResTopicDetailActivity aNS;

            public void pressMenuById(int inIndex, Object inItem) {
                this.aNS.aNG.dismissDialog();
                if (comment == null) {
                    this.aNS.cs(true);
                    this.aNS.ek("正在提交举报");
                    com.MCWorld.module.profile.g.Eb().m(topic.getPostID(), inIndex);
                    return;
                }
                this.aNS.cs(true);
                this.aNS.ek("正在提交举报");
                com.MCWorld.module.profile.g.Eb().n(comment.getCommentID(), inIndex);
            }
        });
        this.aNG.updateCurFocusIndex(-1);
        this.aNG.showMenu(null, null);
    }

    private void GK() {
        if (this.aMX != null && ResourceCtrl.getInstance().getTaskInfo(this.aMX.downUrl, FW()) == null) {
            ep("等待中");
        }
    }

    protected void GL() {
        if (this.aMX != null) {
            this.aMI.setClickable(true);
            if (a(this.aMX)) {
                eq("打开");
                this.aMI.setVisibility(4);
                this.aMO.setVisibility(4);
                this.aMM.setVisibility(4);
                if (this.aML != null) {
                    this.aML.setVisibility(4);
                }
                this.aMN.setVisibility(4);
                if (this.aMY && GO() && !this.aMZ) {
                    this.aMF.setDisplayedChild(0);
                    this.aMP.setVisibility(0);
                    eo(FU());
                    return;
                }
                eo(FT());
                this.aMF.setDisplayedChild(1);
                this.aMP.setVisibility(8);
                return;
            }
            ResTaskInfo info = ResourceCtrl.getInstance().getTaskInfo(this.aMX.downUrl, FW());
            if (info != null) {
                if (info.state == State.DOWNLOAD_PAUSE.ordinal() || info.state == State.DOWNLOAD_ERROR.ordinal()) {
                    ep("继续");
                } else if (info.state == State.DOWNLOAD_PROGRESS.ordinal() || info.state == State.DOWNLOAD_ERROR_RETRY.ordinal()) {
                    ep("下载中");
                } else if (info.state == State.WAITING.ordinal() || info.state == State.PREPARE.ordinal()) {
                    this.aMI.setClickable(false);
                    ep("等待中");
                } else if (info.state == State.UNZIP_PROGRESSING.ordinal()) {
                    this.aMI.setClickable(false);
                    ep("解压中");
                } else if (info.state == State.FILE_DELETE.ordinal()) {
                    eq("下载");
                }
                if (((long) this.aMM.getMax()) != info.mN.total) {
                    this.aMM.setMax((int) info.mN.total);
                }
                this.aMM.setProgress((int) info.mN.progress);
                this.aMM.setVisibility(0);
                if (this.aML != null) {
                    if (((long) this.aML.getMax()) != info.mN.total) {
                        this.aML.setMax((int) info.mN.total);
                    }
                    this.aML.setProgress((int) info.mN.progress);
                    this.aML.setVisibility(0);
                }
                if (((long) this.aMN.getMax()) != info.mN.total) {
                    this.aMN.setMax((int) info.mN.total);
                }
                this.aMN.setProgress((int) info.mN.progress);
                this.aMN.setVisibility(0);
                if (((long) this.aMO.getMax()) != info.mN.total) {
                    this.aMO.setMax((int) info.mN.total);
                }
                this.aMO.setProgress((int) info.mN.progress);
                this.aMO.setVisibility(0);
                this.aMP.setVisibility(4);
                this.aMI.setVisibility(0);
                return;
            }
            HLog.verbose(TAG, "refresh download file has not downloaded", new Object[0]);
            if (this.aMY && GO() && !this.aMZ) {
                this.aMF.setDisplayedChild(0);
                GB();
                this.aMP.setVisibility(4);
                this.aMI.setVisibility(0);
                return;
            }
            this.aMF.setDisplayedChild(1);
        }
    }

    private void ep(String progress) {
        if (this.aNg == 0) {
            a(this.aMq.getDownButton(), progress, true);
        } else {
            a(this.aMx.getDownButton(), progress, true);
        }
        a(this.aMw, progress, true);
        TextView textView = this.aMI;
        if (progress.equals("下载中")) {
            progress = "暂停";
        }
        textView.setText(progress);
        this.aMI.setTextColor(com.simple.colorful.d.getColor(this.aMn, com.MCWorld.bbs.b.c.progress_loading_text_color));
        this.aMI.setBackgroundResource(com.MCWorld.bbs.b.f.list_selector_transition);
    }

    private void eq(String complete) {
        b(this.aMw, complete, true);
        if (this.aNg == 0) {
            b(this.aMq.getDownButton(), complete, true);
        } else {
            b(this.aMx.getDownButton(), complete, true);
        }
    }

    private void a(Button downButton, String status, boolean clickable) {
        downButton.setClickable(clickable);
        downButton.setText(status);
        downButton.setTextColor(com.simple.colorful.d.getColor(this.aMn, com.MCWorld.bbs.b.c.progress_loading_text_color));
        downButton.setBackgroundResource(com.MCWorld.bbs.b.f.list_selector_transition);
    }

    private void b(Button downButton, String status, boolean clickable) {
        downButton.setClickable(clickable);
        downButton.setText(status);
        downButton.setTextColor(com.simple.colorful.d.getColor(this.aMn, 16842809));
        downButton.setBackgroundDrawable(com.simple.colorful.d.o(this.aMn, com.MCWorld.bbs.b.c.drawableResTopicGreen));
    }

    protected boolean a(a info) {
        if (j.eS(info.name)) {
            return true;
        }
        return false;
    }

    protected String er(String name) {
        return name + ".zip";
    }

    protected String FV() {
        return HttpMgr.getInstance().getGameDownloadPath(DownFileType.defaultType).getAbsolutePath();
    }

    public void FZ() {
        this.aMx.getDownButton().setOnClickListener(this.aKt);
        this.aML = this.aMx.HF();
        GL();
    }

    protected void FY() {
        this.aMI.setOnClickListener(this.aKt);
        if (this.aNg == 0) {
            this.aMw.setOnClickListener(this.aKt);
            this.aMq.getDownButton().setOnClickListener(this.aKt);
            return;
        }
        this.aMw.setOnClickListener(this.aKt);
        if (this.aMx.getDownButton() != null) {
            this.aMx.getDownButton().setOnClickListener(this.aKt);
        }
    }

    protected void GM() {
        this.aMq = new ResTopicDetailTitle(this);
        this.aMr = new ResTopicDetailCuzTitle(this);
        if (this.aMX == null) {
            return;
        }
        if (this.aNg == 0) {
            this.aMq.setInfo(this.aMX);
        } else {
            this.aMr.setMapGallery(this.aMX);
        }
    }

    protected void GN() {
        i mapCb = b.bq().bs();
        if (mapCb != null && this.aMX == null) {
            mapCb.n((int) this.aMU);
        }
        GL();
    }

    private boolean GO() {
        return this.aMS == Constants.btr || this.aMS == Constants.bts || this.aMS == Constants.btu || this.aMS == Constants.btt;
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
        builder.aY(g.restopic, com.MCWorld.bbs.b.c.backgroundDefault).i(this.aIw, com.MCWorld.bbs.b.c.backgroundTitleBar).c(this.aMs, com.MCWorld.bbs.b.c.drawableTitleBack).aY(g.footer_container, com.MCWorld.bbs.b.c.backgroundDim).aY(g.topic_bottom_split, com.MCWorld.bbs.b.c.splitColorDim).j(this.aMB, com.MCWorld.bbs.b.c.backgroundPagePre).j(this.aMC, com.MCWorld.bbs.b.c.backgroundPageNext).j(this.aMD, com.MCWorld.bbs.b.c.backgroundTopicButton).j(this.aME, com.MCWorld.bbs.b.c.backgroundTopicButton).a(this.aMD, com.MCWorld.bbs.b.c.textColorTopicButton).a(this.aME, com.MCWorld.bbs.b.c.textColorTopicButton).ba(g.tv_download, 16842809).ba(g.tv_download, com.MCWorld.bbs.b.c.progress_loading_text_color).aZ(g.tv_download, com.MCWorld.bbs.b.c.backgroundButtonLogin).aY(g.rly_float_header, com.MCWorld.bbs.b.c.backgroundDim6).ba(g.tv_map_name, 16842808).ba(g.tv_map_size, 16842808).aZ(g.btn_float_download, com.MCWorld.bbs.b.c.drawableResTopicGreen).ba(g.btn_float_download, 16842809).aY(g.split_vertical, 16842808).aY(g.split_item, com.MCWorld.bbs.b.c.pageturn_split_line_color).a(this.aMq);
    }

    protected void ek(String txt) {
        this.aJd.setText(txt);
    }
}
