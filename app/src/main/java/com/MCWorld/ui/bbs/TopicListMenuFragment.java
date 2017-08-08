package com.MCWorld.ui.bbs;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.z;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.data.category.TopicCategory;
import com.MCWorld.data.j;
import com.MCWorld.http.bbs.category.c;
import com.MCWorld.t;
import com.MCWorld.ui.base.BaseLoadingFragment;
import com.MCWorld.utils.ab;
import com.MCWorld.utils.ad;
import com.simple.colorful.a.a;
import com.simple.colorful.d;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TopicListMenuFragment extends BaseLoadingFragment implements OnClickListener {
    private static final String TAG = "TopicListMenuFragment";
    private TopicCategory aJO;
    private c aPZ = new c();
    private boolean aQi;
    private RelativeLayout aQq;
    private LinearLayout aQr;
    private CheckBox aQs;
    private TextView aQt;
    private a aQu;
    private b aQv;
    private c aQw;
    private List<c> aQx = new ArrayList();
    private Activity mContext;
    private ListView mListView;
    private int subscribeType = ESubscribeType.Invalid.ordinal();

    public interface b {
        void bk(long j);

        void cA(boolean z);
    }

    public static TopicListMenuFragment b(TopicCategory category) {
        TopicListMenuFragment fragment = new TopicListMenuFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("category", category);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onCreate(@z Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            this.aJO = (TopicCategory) getArguments().getParcelable("category");
        } else {
            this.aJO = (TopicCategory) savedInstanceState.getParcelable("category");
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("category", this.aJO);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        boolean z = true;
        View view = inflater.inflate(i.layout_topic_list_menu, null, false);
        this.mContext = getActivity();
        cq(false);
        cr(false);
        this.aQq = (RelativeLayout) view.findViewById(g.rly_topic_search);
        this.aQq.setOnClickListener(this);
        this.aQr = (LinearLayout) view.findViewById(g.ll_filter);
        this.aQs = (CheckBox) view.findViewById(g.cb_night_mode);
        this.aQs.setChecked(!d.isDayMode());
        this.aQs.setOnCheckedChangeListener(new 1(this));
        this.aQt = (TextView) view.findViewById(g.btn_follow);
        this.aQt.setOnClickListener(this);
        this.mListView = (ListView) view.findViewById(g.listview);
        this.aQu = new a(this, null);
        this.mListView.setAdapter(this.aQu);
        this.mListView.setOnItemClickListener(new 2(this));
        this.aPZ.bb(3);
        this.aPZ.a(this);
        if (this.aJO.getIsSubscribe() != 1) {
            z = false;
        }
        this.aQi = z;
        Hh();
        return view;
    }

    private void Hl() {
        for (c menuInfo : this.aQx) {
            if (menuInfo.id == this.aQw.id) {
                menuInfo.selected = true;
            } else {
                menuInfo.selected = false;
            }
        }
        this.aQu.notifyDataSetChanged();
    }

    public void onResume() {
        super.onResume();
        this.aQu.notifyDataSetChanged();
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.aQv = (b) activity;
    }

    public void onDetach() {
        super.onDetach();
        this.aQv = null;
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onClick(View v) {
        int id = v.getId();
        if (id == g.rly_topic_search) {
            if (!j.ep().ey()) {
                t.an(this.mContext);
            } else if (j.ep().getLevel() < this.aJO.getIsSearch()) {
                t.show_toast(this.mContext, "抱歉！目前搜索只对" + this.aJO.getIsSearch() + "级以上的葫芦娃开放。");
            } else {
                t.b(this.mContext, this.aJO);
            }
        } else if (id == g.btn_follow) {
            Hi();
        }
    }

    private void Hi() {
        if (!j.ep().ey()) {
            t.an(this.mContext);
        } else if (this.subscribeType == ESubscribeType.Invalid.ordinal() || this.subscribeType == ESubscribeType.ALWAYS.ordinal()) {
            t.show_toast(this.mContext, "亲,该版块是不能取消的");
        } else {
            this.aQi = !this.aQi;
            this.aQt.setClickable(false);
            this.aPZ.G(this.aQi);
            this.aPZ.v(this.aJO.getCategoryID());
            this.aPZ.execute();
        }
    }

    public void a(com.MCWorld.http.base.d response) {
        super.a(response);
    }

    public void b(com.MCWorld.http.base.d response) {
        boolean z = true;
        super.b(response);
        if (response.fe() == 3) {
            this.aQt.setClickable(true);
            if (this.aQi) {
                z = false;
            }
            this.aQi = z;
            Hh();
        }
    }

    private void Hh() {
        if (j.ep().ey() && this.aJO != null) {
            this.subscribeType = this.aJO.getSubscribeType();
            if (this.subscribeType == ESubscribeType.Invalid.ordinal() || this.subscribeType == ESubscribeType.ALWAYS.ordinal()) {
                this.aQt.setText("取消关注");
                this.aQt.setTextColor(getResources().getColor(com.MCWorld.bbs.b.d.topic_menu_filter_title));
                this.aQt.setBackgroundDrawable(d.o(this.mContext, com.MCWorld.bbs.b.c.backgroundCreditCount));
            } else if (this.aQi) {
                this.aQt.setText("取消关注");
                this.aQt.setTextColor(getResources().getColor(com.MCWorld.bbs.b.d.topic_menu_filter_title));
                this.aQt.setBackgroundDrawable(d.o(this.mContext, com.MCWorld.bbs.b.c.backgroundCreditCount));
            } else {
                this.aQt.setText("关注");
                this.aQt.setTextColor(getResources().getColor(com.MCWorld.bbs.b.d.white));
                this.aQt.setBackgroundDrawable(d.o(this.mContext, com.MCWorld.bbs.b.c.drawableTopicFollow));
            }
        }
    }

    public void cC(boolean isSubscribe) {
        this.aQi = isSubscribe;
        Hh();
    }

    public void c(com.MCWorld.http.base.d response) {
        super.c(response);
        if (response.getStatus() != 1) {
            t.n(this.mContext, ab.n(response.fg(), response.fh()));
        } else if (response.fe() == 3) {
            if (this.aQi) {
                t.o(this.mContext, "关注成功");
            } else {
                t.o(this.mContext, "已取消关注");
            }
            this.aQv.cA(this.aQi);
            this.aQt.setClickable(true);
            Hh();
        }
    }

    public void d(List<Long> ids, List<String> titles) {
        if (ad.empty((Collection) ids) || ad.empty((Collection) titles)) {
            this.aQr.setVisibility(8);
            return;
        }
        this.aQx.clear();
        for (int i = 0; i < ids.size(); i++) {
            boolean z;
            c menuInfo = new c();
            menuInfo.id = ((Long) ids.get(i)).longValue();
            menuInfo.text = (String) titles.get(i);
            if (i == 0) {
                z = true;
            } else {
                z = false;
            }
            menuInfo.selected = z;
            if (i == 0 && this.aQw == null) {
                this.aQw = menuInfo;
            }
            this.aQx.add(menuInfo);
        }
        this.aQr.setVisibility(0);
        Hl();
    }

    protected void a(a builder) {
        super.a(builder);
        builder.bb(g.cb_night_mode, com.MCWorld.bbs.b.c.drawableCompoundButtonSetting);
    }
}
