package com.huluxia.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huluxia.HTApplication;
import com.huluxia.bbs.b;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.bbs.b.m;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.module.account.d;
import com.huluxia.module.h;
import com.huluxia.module.w;
import com.huluxia.t;
import com.huluxia.ui.base.BaseLoadingFragment;
import com.huluxia.ui.itemadapter.message.SysMsgItemAdapter;
import com.huluxia.utils.aa;
import com.huluxia.utils.aa.a;
import com.huluxia.utils.ab;
import com.simple.colorful.setter.j;
import com.simple.colorful.setter.k;

public class SysMsgFragment extends BaseLoadingFragment {
    private static final int PAGE_SIZE = 20;
    private static final String TAG = "SysMsgFragment";
    private static final String biQ = "SYS_MSG_DATA";
    private aa aHb;
    private PullToRefreshListView aQB;
    private SysMsgItemAdapter biR;
    private SysMsgFragment biS;
    private d biT;
    private int biU = 0;
    private boolean biV = false;
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ SysMsgFragment biW;

        {
            this.biW = this$0;
        }

        @MessageHandler(message = 645)
        public void onRecvMsg(d info, String start) {
            this.biW.aQB.onRefreshComplete();
            if (this.biW.biR == null || info == null || !info.isSucc()) {
                this.biW.aHb.KN();
                if (info != null && info.code == 103) {
                    ((MessageHistoryActivity) this.biW.biS.getActivity()).eJ("登录信息过期，请重新登录");
                    return;
                } else if (this.biW.getCurrentPage() == 0) {
                    this.biW.FB();
                    return;
                } else {
                    t.n(this.biW.getActivity(), this.biW.getResources().getString(m.loading_failed_please_retry));
                    return;
                }
            }
            this.biW.aHb.onLoadComplete();
            if (this.biW.getCurrentPage() == 0) {
                this.biW.FC();
            }
            if (start == null || start.equals("0")) {
                this.biW.biT = info;
                EventNotifyCenter.notifyEvent(h.class, 770, new Object[0]);
            } else {
                this.biW.biT.start = info.start;
                this.biW.biT.more = info.more;
                this.biW.biT.datas.addAll(info.datas);
            }
            this.biW.biR.setData(this.biW.biT.datas);
        }

        @MessageHandler(message = 644)
        public void onRecvTransferRet(boolean succ, w info) {
            if (info != null && info.isSucc()) {
                t.o(this.biW.biS.getActivity(), "赠送成功");
            } else if (info != null) {
                t.download_toast(this.biW.biS.getActivity(), ab.n(info.code, info.msg));
            } else {
                t.n(this.biW.biS.getActivity(), "赠送失败，请稍后重试");
            }
        }
    };

    public static SysMsgFragment JY() {
        return new SysMsgFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HLog.verbose(this, "UserMsgFragMent create", new Object[0]);
        EventNotifyCenter.add(h.class, this.mCallback);
        this.biS = this;
    }

    public void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
    }

    public void onResume() {
        super.onResume();
        if (this.biR != null) {
            this.biR.notifyDataSetChanged();
        }
    }

    public void JZ() {
        if (this.aQB != null && this.aQB.getRefreshableView() != null) {
            this.aQB.scrollTo(0, 0);
            ((ListView) this.aQB.getRefreshableView()).setSelection(0);
        }
    }

    protected void kj(int newTheme) {
        super.kj(newTheme);
        if (this.biR != null) {
            this.biR.notifyDataSetChanged();
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(i.activity_message_history, container, false);
        this.aQB = (PullToRefreshListView) view.findViewById(g.list);
        ((ListView) this.aQB.getRefreshableView()).setSelector(b.d.transparent);
        this.biR = new SysMsgItemAdapter(getActivity());
        this.aQB.setOnRefreshListener(new OnRefreshListener<ListView>(this) {
            final /* synthetic */ SysMsgFragment biW;

            {
                this.biW = this$0;
            }

            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                this.biW.reload();
            }
        });
        this.aQB.setAdapter(this.biR);
        this.aHb = new aa((ListView) this.aQB.getRefreshableView());
        this.aHb.a(new a(this) {
            final /* synthetic */ SysMsgFragment biW;

            {
                this.biW = this$0;
            }

            public void onLoadData() {
                com.huluxia.module.account.a.DU().L(this.biW.biT == null ? "0" : this.biW.biT.start, 20);
            }

            public boolean shouldLoadData() {
                if (this.biW.biT == null) {
                    this.biW.aHb.onLoadComplete();
                    return false;
                } else if (this.biW.biT.more > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        this.aQB.setOnScrollListener(this.aHb);
        if (savedInstanceState != null) {
            this.biT = (d) savedInstanceState.getParcelable(biQ);
            if (this.biT != null) {
                this.biR.setData(this.biT.datas);
            }
        }
        cq(false);
        return view;
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(biQ, this.biT);
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        if (builder != null && this.biR != null && this.aQB != null) {
            k setter = new j((ViewGroup) this.aQB.getRefreshableView());
            setter.a(this.biR);
            builder.a(setter);
        }
    }

    protected void EX() {
        super.EX();
        reload();
    }

    public void reload() {
        com.huluxia.module.account.a.DU().L("0", 20);
        HTApplication.a(null);
        com.huluxia.service.h.EC().ED();
        com.huluxia.service.i.EI();
    }

    public void Ka() {
        int i = this.biU;
        this.biU = i + 1;
        if (i < 1) {
            Fy();
            reload();
        }
    }
}
