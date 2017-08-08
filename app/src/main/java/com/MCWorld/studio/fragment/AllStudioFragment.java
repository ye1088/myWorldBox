package com.MCWorld.studio.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.MCWorld.data.studio.a;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.framework.base.widget.title.TitleBar;
import com.MCWorld.module.h;
import com.MCWorld.module.z;
import com.MCWorld.studio.adapter.AllStudioAdapter;
import com.MCWorld.ui.base.BaseLoadingFragment;
import com.MCWorld.utils.c;
import com.simple.colorful.b;
import com.simple.colorful.setter.j;
import com.simple.colorful.setter.k;

public class AllStudioFragment extends BaseLoadingFragment {
    private final int PAGE_SIZE = 20;
    private final String TAG = "AllStudioFragment";
    private c aEV;
    protected PullToRefreshListView aEq;
    protected TextView aGd;
    protected AllStudioAdapter aGe;
    protected a aGf;
    private OnClickListener aGg = new 1(this);
    private CallbackHandler aky = new CallbackHandler(this) {
        final /* synthetic */ AllStudioFragment aGh;

        {
            this.aGh = this$0;
        }

        @MessageHandler(message = 792)
        public void recvAllStudioList(int tag, boolean succ, a info) {
            this.aGh.aEq.onRefreshComplete();
            this.aGh.aEV.onLoadComplete();
            if (tag != this.aGh.mType) {
                return;
            }
            if (succ && info != null) {
                if (info.start <= 20) {
                    this.aGh.aGf = info;
                } else if (this.aGh.aGf != null) {
                    this.aGh.aGf.start = info.start;
                    this.aGh.aGf.more = info.more;
                    this.aGh.aGf.list.addAll(info.list);
                } else {
                    return;
                }
                this.aGh.aGe.c(this.aGh.aGf.list, true);
                this.aGh.FC();
            } else if (this.aGh.aGf == null) {
                this.aGh.FB();
            }
        }
    };
    private Context mContext;
    private int mType = 4;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
        EventNotifyCenter.add(h.class, this.aky);
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
        this.aGd.setText("工作室");
        this.aGd.setOnClickListener(this.aGg);
    }

    private void d(View view) {
        this.aEq = (PullToRefreshListView) view.findViewById(R.id.lv_list_view);
    }

    private void EQ() {
        this.aGe = new AllStudioAdapter(this.mContext);
        this.aEq.setAdapter(this.aGe);
        this.aEq.setOnRefreshListener(new 2(this));
        this.aEV = new c((ListView) this.aEq.getRefreshableView());
        this.aEV.a(new 3(this));
        this.aEq.setOnScrollListener(this.aEV);
    }

    private void EZ() {
        z.DO();
        z.V(this.mType, 0, 20);
    }

    private void reload() {
        EZ();
    }

    private void EY() {
        z.DO();
        z.V(this.mType, this.aGf.start, 20);
    }

    protected void EX() {
        super.EX();
        EZ();
    }

    public void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.aky);
    }

    protected void kj(int themeId) {
        super.kj(themeId);
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        if (this.aGe != null && (this.aGe instanceof b)) {
            k setter = new j((ViewGroup) this.aEq.getRefreshableView());
            setter.a(this.aGe);
            builder.a(setter);
        }
        builder.a(this.aGd, R.attr.backText).a(this.aGd, R.attr.back, 1).j(this.aGd, R.attr.backgroundTitleBarButton).aY(R.id.root_view, R.attr.backgroundDefault);
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }
}
