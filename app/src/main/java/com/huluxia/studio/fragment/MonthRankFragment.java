package com.huluxia.studio.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huluxia.data.studio.f;
import com.huluxia.framework.R;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.widget.title.TitleBar;
import com.huluxia.module.h;
import com.huluxia.module.z;
import com.huluxia.studio.adapter.MonthReviewRankAdapter;
import com.huluxia.ui.base.BaseLoadingFragment;
import com.simple.colorful.a.a;
import com.simple.colorful.b;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;
import com.simple.colorful.setter.k;

public class MonthRankFragment extends BaseLoadingFragment {
    public static final String aGi = "KEY_IS_PAST_PERIOD";
    public static final String aGj = "KEY_CURRENT_MONTH";
    public static final String aGk = "KEY_PAST_TIME";
    private final int PAGE_SIZE = 20;
    private final String TAG = "MonthRankFragment";
    protected String aEJ = "";
    protected PullToRefreshListView aEq;
    protected TextView aGd;
    private OnClickListener aGg = new 1(this);
    protected boolean aGl = false;
    protected String aGm = "";
    protected Button aGn;
    protected MonthReviewRankAdapter aGs;
    protected f aGt;
    private boolean aGu = true;
    private CallbackHandler aky = new CallbackHandler(this) {
        final /* synthetic */ MonthRankFragment aGv;

        {
            this.aGv = this$0;
        }

        @MessageHandler(message = 800)
        public void recvMonthRankInfo(int tag, boolean succ, f info) {
            this.aGv.aEq.onRefreshComplete();
            if (tag != this.aGv.mType || !this.aGv.aGu) {
                return;
            }
            if (succ && info != null) {
                this.aGv.aGt = info;
                this.aGv.aGs.c(this.aGv.aGt.list, true);
                this.aGv.FC();
            } else if (this.aGv.aGt == null) {
                this.aGv.FB();
            }
        }
    };
    private Context mContext;
    private int mType = 2;

    public static MonthRankFragment b(boolean isPastPeriod, String currentMonth, String pastTime) {
        MonthRankFragment f = new MonthRankFragment();
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
        this.aGs = new MonthReviewRankAdapter(this.mContext);
        this.aEq.setAdapter(this.aGs);
        this.aEq.setOnRefreshListener(new 2(this));
    }

    private void EZ() {
        if (this.aGl) {
            z.DO();
            z.g(this.mType, this.aGm);
            return;
        }
        z.DO();
        z.jo(this.mType);
    }

    protected void EX() {
        super.EX();
        EZ();
    }

    private void reload() {
        EZ();
    }

    public void onStart() {
        super.onStart();
        this.aGu = true;
    }

    public void onStop() {
        super.onStop();
        this.aGu = false;
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
        if (this.aGs != null && (this.aGs instanceof b)) {
            k setter = new j((ViewGroup) this.aEq.getRefreshableView());
            setter.a(this.aGs);
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
