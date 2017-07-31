package com.huluxia.ui.home;

import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huluxia.HTApplication;
import com.huluxia.bbs.b.c;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.m;
import com.huluxia.data.TableListParc;
import com.huluxia.data.category.TopicCategory;
import com.huluxia.data.message.MsgCounts;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.widget.title.TitleBar;
import com.huluxia.login.LoginError.LoginErrCode;
import com.huluxia.login.e;
import com.huluxia.module.h;
import com.huluxia.module.n;
import com.huluxia.module.topic.a;
import com.huluxia.service.i;
import com.huluxia.t;
import com.huluxia.ui.base.BaseLoadingFragment;
import com.huluxia.ui.bbs.BbsTitle;
import com.huluxia.ui.itemadapter.category.ClassListAdapter;
import com.huluxia.widget.Constants;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;
import com.simple.colorful.setter.k;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class BbsFragment extends BaseLoadingFragment {
    private static final String KEY_CONTENT = "BbsFragment:tableList";
    private static BbsFragment aRR;
    private PullToRefreshListView aEq;
    private TextView aIg;
    protected OnClickListener aIh = new 2(this);
    private BbsTitle aRS;
    private TableListParc aRT;
    private ClassListAdapter aRU;
    private BroadcastReceiver aRV;
    private BroadcastReceiver aRW;
    private BroadcastReceiver aRX;
    private ImageButton aRY;
    private MsgtipReciver aRZ;
    private ClearMsgReciver aSa;
    private CallbackHandler aky = new CallbackHandler(this) {
        final /* synthetic */ BbsFragment aSb;

        {
            this.aSb = this$0;
        }

        @MessageHandler(message = 640)
        public void onTopicCategoryAddOrAbolish(String flag) {
            HLog.debug("onTopicCategoryAddOrAbolish", "flag is " + flag, new Object[0]);
            this.aSb.Gy();
        }
    };
    private CallbackHandler mCallback;
    private View view;

    private class b extends CallbackHandler {
        final /* synthetic */ BbsFragment aSb;
        WeakReference<BbsFragment> aSc;

        b(BbsFragment bbsFragment, BbsFragment fragment) {
            this.aSb = bbsFragment;
            this.aSc = new WeakReference(fragment);
        }

        @MessageHandler(message = 293)
        public void onRecevTopicCateList(boolean succ, TableListParc tableList) {
            BbsFragment fragment = (BbsFragment) this.aSc.get();
            if (fragment != null) {
                fragment.aEq.onRefreshComplete();
                if (succ) {
                    this.aSb.FC();
                    fragment.aRT.setHasMore(tableList.getHasMore());
                    fragment.aRT.setStart(tableList.getStart());
                    fragment.aRT.setExtData(tableList.getExtData());
                    TopicCategory emptyTag = new TopicCategory(-2);
                    TopicCategory emptyCate = new TopicCategory(-3);
                    TableListParc objs = new TableListParc();
                    for (int i = 0; i < tableList.size(); i++) {
                        TopicCategory cate = (TopicCategory) tableList.get(i);
                        if (cate.getType() == 2) {
                            objs.add(cate);
                        } else if (cate.getType() == 1) {
                            if (objs.size() % 2 == 0) {
                                objs.add(cate);
                                objs.add(emptyTag);
                            } else {
                                objs.add(emptyCate);
                                objs.add(cate);
                                objs.add(emptyTag);
                            }
                        }
                    }
                    if (HTApplication.DEBUG) {
                        TopicCategory cateTopic = new TopicCategory(2);
                        cateTopic.categoryID = Constants.bsT;
                        cateTopic.title = "MC广告宣传";
                        cateTopic.tags = new ArrayList();
                        cateTopic.description = "广告宣传";
                        cateTopic.icon = "http://cdn.u2.huluxia.com//g1/M00/55/2C/wKgBB1XJnk-ARUDqAAAhP-sbpcc674.jpg";
                        objs.add(cateTopic);
                    }
                    if (objs.size() > 0 && objs.size() % 2 == 1) {
                        objs.add(emptyCate);
                    }
                    fragment.aRT.clear();
                    fragment.aRT.addAll(objs);
                    fragment.aRU.notifyDataSetChanged();
                } else if (this.aSb.getCurrentPage() == 0) {
                    this.aSb.FB();
                } else {
                    t.n(fragment.getActivity(), fragment.getResources().getString(m.load_failed_please_retry));
                }
            }
        }

        @MessageHandler(message = 1026)
        public void onRecevAutoLogin(boolean succ, String client, LoginErrCode errCode) {
            BbsFragment fragment = (BbsFragment) this.aSc.get();
            if (fragment != null && succ) {
                fragment.Gy();
            }
        }

        @MessageHandler(message = 1025)
        public void onRecevLogin(boolean succ, String client, String mail, String encryptPwd, String openId, int code, String msg, LoginErrCode errCode) {
            BbsFragment fragment = (BbsFragment) this.aSc.get();
            if (fragment != null && succ) {
                fragment.Gy();
            }
        }
    }

    public static BbsFragment Hq() {
        return new BbsFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        aRR = this;
        this.mCallback = new b(this, this);
        EventNotifyCenter.add(n.class, this.mCallback);
        EventNotifyCenter.add(e.class, this.mCallback);
        EventNotifyCenter.add(h.class, this.aky);
        this.aRT = new TableListParc();
        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_CONTENT)) {
            this.aRT = (TableListParc) savedInstanceState.getParcelable(KEY_CONTENT);
        }
        this.aRV = new a(this, null);
        this.aRW = new a(this, null);
        this.aRX = new c(this, null);
        i.c(this.aRV);
        i.d(this.aRW);
        i.k(this.aRX);
        this.aRZ = new MsgtipReciver(this);
        this.aSa = new ClearMsgReciver(this);
        i.e(this.aRZ);
        i.f(this.aSa);
    }

    public void onResume() {
        super.onResume();
        if (HTApplication.fO) {
            HTApplication.fO = false;
            Gy();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.aRV != null) {
            i.unregisterReceiver(this.aRV);
            this.aRV = null;
        }
        if (this.aRW != null) {
            i.unregisterReceiver(this.aRW);
            this.aRW = null;
        }
        if (this.aRX != null) {
            i.unregisterReceiver(this.aRX);
            this.aRX = null;
        }
        if (this.aRZ != null) {
            i.unregisterReceiver(this.aRZ);
            this.aRZ = null;
        }
        if (this.aSa != null) {
            i.unregisterReceiver(this.aSa);
            this.aSa = null;
        }
        EventNotifyCenter.remove(this.mCallback);
        EventNotifyCenter.remove(this.aky);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mCallback = new b(this, this);
        this.view = inflater.inflate(com.huluxia.bbs.b.i.include_default_pulllist, container, false);
        this.aEq = (PullToRefreshListView) this.view.findViewById(g.list);
        this.aRU = new ClassListAdapter(this.view.getContext(), this.aRT);
        this.aRS = new BbsTitle(aRR.getActivity());
        ((ListView) this.aEq.getRefreshableView()).addHeaderView(this.aRS);
        ((ListView) this.aEq.getRefreshableView()).setSelector(17170445);
        this.aEq.setOnRefreshListener(new 1(this));
        this.aEq.setAdapter(this.aRU);
        if (savedInstanceState == null || (this.aRT != null && this.aRT.isEmpty())) {
            Fy();
            Gy();
        }
        this.view.setBackgroundColor(d.getColor(aRR.getContext(), c.backgroundDefault));
        return this.view;
    }

    protected void a(TitleBar titleBar) {
        titleBar.setLeftLayout(com.huluxia.bbs.b.i.home_left_btn);
        titleBar.setRightLayout(com.huluxia.bbs.b.i.home_right_btn);
        this.aIg = (TextView) titleBar.findViewById(g.tv_msg);
        ((TextView) titleBar.findViewById(g.header_title)).setText("社区");
        titleBar.findViewById(g.img_msg).setOnClickListener(this.aIh);
        this.aRY = (ImageButton) titleBar.findViewById(g.sys_header_flright_img);
        this.aRY.setVisibility(0);
        this.aRY.setImageDrawable(d.o(getActivity(), c.drawableTitleAddBoard));
        this.aRY.setOnClickListener(this.aIh);
    }

    public void cr(boolean show) {
        super.cr(false);
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    protected void EX() {
        super.EX();
        Gy();
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_CONTENT, this.aRT);
    }

    public void Gy() {
        a.Ed().Ee();
    }

    protected void Fr() {
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

    protected void Fs() {
        this.aIg.setVisibility(8);
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        if (this.aRU != null) {
            k setter = new j((ViewGroup) this.aEq.getRefreshableView());
            setter.a(this.aRU);
            builder.a(setter);
        }
        builder.i(this.view, c.backgroundDefault).a((TextView) FD().findViewById(g.header_title), 16842809).c((ImageView) FD().findViewById(g.sys_header_left_img), c.drawableTitleLogo).j(FD().findViewById(g.img_msg), c.backgroundTitleBarButton).c((ImageView) FD().findViewById(g.img_msg), c.drawableTitleMsg).j(this.aRY, c.backgroundTitleBarButton).c(this.aRY, c.drawableTitleAddBoard).a((TextView) FD().findViewById(g.header_title), c.drawableTitleLogo, 1).a(this.aRS);
    }
}
