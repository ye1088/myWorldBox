package com.huluxia.studio.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huluxia.data.studio.e;
import com.huluxia.framework.R;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.widget.title.TitleBar;
import com.huluxia.module.h;
import com.huluxia.module.z;
import com.huluxia.studio.adapter.DownloadRankAdapter;
import com.huluxia.ui.base.BaseLoadingFragment;
import com.huluxia.utils.c;
import com.simple.colorful.a.a;
import com.simple.colorful.b;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;
import com.simple.colorful.setter.k;

public class DownloadRankFragment extends BaseLoadingFragment {
    public static final String aGi = "KEY_IS_PAST_PERIOD";
    public static final String aGj = "KEY_CURRENT_MONTH";
    public static final String aGk = "KEY_PAST_TIME";
    private final int PAGE_SIZE = 20;
    private final String TAG = "DownloadRankFragment";
    protected String aEJ = "";
    private c aEV;
    protected PullToRefreshListView aEq;
    protected TextView aGd;
    private OnClickListener aGg = new 1(this);
    protected boolean aGl = false;
    protected String aGm = "";
    protected Button aGn;
    protected DownloadRankAdapter aGo;
    protected e aGp;
    private boolean aGq = true;
    private CallbackHandler aky = new CallbackHandler(this) {
        final /* synthetic */ DownloadRankFragment aGr;

        {
            this.aGr = this$0;
        }

        @MessageHandler(message = 802)
        public void recvDownloadRankInfo(int tag, boolean succ, e info) {
            this.aGr.aEq.onRefreshComplete();
            this.aGr.aEV.onLoadComplete();
            if (tag != this.aGr.mType || !this.aGr.aGq) {
                return;
            }
            if (succ && info != null) {
                if (info.start <= 20) {
                    this.aGr.aGp = info;
                } else if (this.aGr.aGp != null) {
                    this.aGr.aGp.start = info.start;
                    this.aGr.aGp.more = info.more;
                    this.aGr.aGp.mapList.addAll(info.mapList);
                } else {
                    return;
                }
                this.aGr.aGo.c(this.aGr.aGp.mapList, true);
                this.aGr.FC();
            } else if (this.aGr.aGp == null) {
                this.aGr.FB();
            }
        }
    };
    private Context mContext;
    private int mType = 3;

    public static DownloadRankFragment a(boolean isPastPeriod, String currentMonth, String pastTime) {
        DownloadRankFragment f = new DownloadRankFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("KEY_IS_PAST_PERIOD", isPastPeriod);
        bundle.putString("KEY_CURRENT_MONTH", currentMonth);
        bundle.putString("KEY_PAST_TIME", pastTime);
        f.setArguments(bundle);
        return f;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
        EventNotifyCenter.add(h.class, this.aky);
        if (savedInstanceState != null) {
            this.aGl = savedInstanceState.getBoolean("KEY_IS_PAST_PERIOD", false);
            this.aEJ = savedInstanceState.getString("KEY_CURRENT_MONTH");
            this.aGm = savedInstanceState.getString("KEY_PAST_TIME");
            return;
        }
        this.aGl = getArguments().getBoolean("KEY_IS_PAST_PERIOD", false);
        this.aEJ = getArguments().getString("KEY_CURRENT_MONTH");
        this.aGm = getArguments().getString("KEY_PAST_TIME");
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_studio_common, container, false);
        d(view);
        EQ();
        EZ();
        Fy();
        return view;
    }

    protected void a(TitleBar titleBar) {
        super.a(titleBar);
        titleBar.setLeftLayout(R.layout.include_header);
        titleBar.findViewById(R.id.fl_msg).setVisibility(8);
        this.aGd = (TextView) titleBar.findViewById(R.id.sys_header_back);
        this.aGn = (Button) titleBar.findViewById(R.id.sys_header_right);
        if (this.aGl) {
            this.aGd.setText(this.aEJ);
            this.aGn.setVisibility(8);
        } else {
            this.aGd.setText("工作室");
            this.aGn.setVisibility(0);
            this.aGn.setText(this.aEJ);
            this.aGn.setCompoundDrawablesWithIntrinsicBounds(null, null, d.o(this.mContext, R.attr.arrow_next), null);
        }
        this.aGd.setOnClickListener(this.aGg);
        this.aGn.setOnClickListener(this.aGg);
    }

    private void d(View view) {
        this.aEq = (PullToRefreshListView) view.findViewById(R.id.lv_list_view);
    }

    private void EQ() {
        this.aGo = new DownloadRankAdapter(this.mContext);
        this.aEq.setAdapter(this.aGo);
        this.aEq.setOnRefreshListener(new 2(this));
        this.aEV = new c((ListView) this.aEq.getRefreshableView());
        this.aEV.a(new 3(this));
        this.aEq.setOnScrollListener(this.aEV);
    }

    private void EZ() {
        if (this.aGl) {
            z.DO();
            z.a(this.mType, 0, 20, this.aGm);
            return;
        }
        z.DO();
        z.W(this.mType, 0, 20);
    }

    protected void EX() {
        super.EX();
        EZ();
    }

    private void reload() {
        EZ();
    }

    private void EY() {
        if (this.aGl) {
            z.DO();
            z.a(this.mType, this.aGp.start, 20, this.aGm);
            return;
        }
        z.DO();
        z.W(this.mType, this.aGp.start, 20);
    }

    public void onStart() {
        super.onStart();
        this.aGq = true;
    }

    public void onStop() {
        super.onStop();
        this.aGq = false;
    }

    public void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.aky);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("KEY_IS_PAST_PERIOD", this.aGl);
        outState.putString("KEY_CURRENT_MONTH", this.aEJ);
        outState.putString("KEY_PAST_TIME", this.aGm);
    }

    protected void kj(int themeId) {
        super.kj(themeId);
    }

    protected void a(a builder) {
        super.a(builder);
        if (this.aGo != null && (this.aGo instanceof b)) {
            k setter = new j((ViewGroup) this.aEq.getRefreshableView());
            setter.a(this.aGo);
            builder.a(setter);
        }
        builder.a(this.aGd, R.attr.backText).a(this.aGd, R.attr.back, 1).j(this.aGd, R.attr.backgroundTitleBarButton).a(this.aGn, R.attr.backText).j(this.aGn, R.attr.backgroundTitleBarButton).a(this.aGn, R.attr.arrow_next, 2).aY(R.id.root_view, R.attr.backgroundDefault);
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }
}
