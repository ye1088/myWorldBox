package com.huluxia.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.data.topic.TopicItem;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.module.h;
import com.huluxia.module.topic.b;
import com.huluxia.t;
import com.huluxia.ui.base.BaseLoadingFragment;
import com.huluxia.utils.aa;
import com.huluxia.utils.aa.a;
import com.huluxia.utils.ba;
import com.simple.colorful.setter.j;
import java.util.ArrayList;

public class TopicFavorFragment extends BaseLoadingFragment {
    private static final int PAGE_SIZE = 20;
    private static final String aHW = "ARG_USER_ID";
    private PullToRefreshListView aHX;
    protected aa aHb;
    private View aIa;
    private b aPI = new b();
    private BaseAdapter biX;
    private CallbackHandler mCallback = new 4(this);
    private long pM;

    public static TopicFavorFragment bq(long userId) {
        TopicFavorFragment fragment = new TopicFavorFragment();
        Bundle args = new Bundle();
        args.putLong(aHW, userId);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.pM = getArguments().getLong(aHW);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.aIa = inflater.inflate(i.include_default_pulllist, container, false);
        this.aHX = (PullToRefreshListView) this.aIa.findViewById(g.list);
        Fo();
        EventNotifyCenter.add(h.class, this.mCallback);
        Fy();
        reload();
        cq(false);
        return this.aIa;
    }

    public void onDestroyView() {
        super.onDestroyView();
        EventNotifyCenter.remove(this.mCallback);
    }

    private void Fo() {
        this.biX = ba.c(getActivity(), (ArrayList) this.aPI.posts);
        this.aHX.setAdapter(this.biX);
        this.aHX.setOnRefreshListener(new OnRefreshListener<ListView>(this) {
            final /* synthetic */ TopicFavorFragment biY;

            {
                this.biY = this$0;
            }

            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                this.biY.reload();
            }
        });
        this.aHX.setOnItemClickListener(new OnItemClickListener(this) {
            final /* synthetic */ TopicFavorFragment biY;

            {
                this.biY = this$0;
            }

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TopicItem data = (TopicItem) parent.getAdapter().getItem(position);
                if (data != null) {
                    if (data.getCategory() != null) {
                        data.setCategoryName(data.getCategory().getTitle());
                    }
                    t.a(this.biY.getActivity(), data, 0);
                }
            }
        });
        this.aHb = new aa((ListView) this.aHX.getRefreshableView());
        this.aHb.a(new a(this) {
            final /* synthetic */ TopicFavorFragment biY;

            {
                this.biY = this$0;
            }

            public void onLoadData() {
                this.biY.Fc();
            }

            public boolean shouldLoadData() {
                if (this.biY.aPI == null) {
                    this.biY.aHb.onLoadComplete();
                    return false;
                } else if (this.biY.aPI.more > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        this.aHX.setOnScrollListener(this.aHb);
    }

    private void Fc() {
        com.huluxia.module.profile.g.Eb().a(this.aPI.start, 20, this.pM);
    }

    private void reload() {
        com.huluxia.module.profile.g.Eb().a("0", 20, this.pM);
    }

    protected void EX() {
        super.EX();
        reload();
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        if (this.biX != null && (this.biX instanceof com.simple.colorful.b)) {
            j setter = new j((ViewGroup) this.aHX.getRefreshableView());
            setter.a((com.simple.colorful.b) this.biX);
            builder.a(setter);
        }
    }
}
