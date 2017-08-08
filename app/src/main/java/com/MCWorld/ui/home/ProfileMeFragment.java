package com.MCWorld.ui.home;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.MCWorld.HTApplication;
import com.MCWorld.bbs.b.c;
import com.MCWorld.bbs.b.d;
import com.MCWorld.data.j;
import com.MCWorld.data.message.MsgCounts;
import com.MCWorld.data.profile.PhotoInfo;
import com.MCWorld.data.profile.ProfileInfo;
import com.MCWorld.framework.base.image.Config;
import com.MCWorld.framework.base.image.PipelineView;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.framework.base.utils.UtilUri;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.base.utils.UtilsScreen;
import com.MCWorld.module.picture.b;
import com.MCWorld.module.profile.f;
import com.MCWorld.module.profile.g;
import com.MCWorld.module.profile.h;
import com.MCWorld.module.y;
import com.MCWorld.module.z;
import com.MCWorld.service.i;
import com.MCWorld.ui.base.BaseLoadingLayout;
import com.MCWorld.ui.base.BaseThemeFragment;
import com.MCWorld.ui.bbs.ProfileHeaderLayout;
import com.MCWorld.ui.itemadapter.profile.ProfileSpaceAdapter;
import com.MCWorld.ui.itemadapter.profile.ProfileSpaceAdapter$a;
import com.MCWorld.utils.at;
import com.MCWorld.widget.HtImageView;
import com.simple.colorful.a.a;
import com.simple.colorful.setter.k;
import java.util.ArrayList;
import java.util.List;

public class ProfileMeFragment extends BaseThemeFragment implements ProfileSpaceAdapter$a {
    private static final String TAG = "ProfileMeFragment";
    private static final String aSd = "ARG_FROM_HOME";
    private static final String aSe = "ARG_STATUS_HEIGHT";
    protected Handler Vo = new 7(this);
    private TextView aID;
    private TextView aIg;
    private BaseLoadingLayout aIy;
    private View aJc;
    private ProfileInfo aKG;
    private BroadcastReceiver aKb;
    private boolean aSf;
    private int aSg;
    private ProfileSpaceAdapter aSh;
    private ProfileHeaderLayout aSi;
    private BroadcastReceiver aSj;
    private BroadcastReceiver aSk;
    private MsgtipReciver aSl;
    private ClearMsgReciver aSm;
    private PipelineView aSn;
    private HtImageView aSo;
    private HtImageView aSp;
    private boolean aSq = false;
    private boolean aSr = false;
    private boolean aSs = false;
    private CallbackHandler aSt = new CallbackHandler(this) {
        final /* synthetic */ ProfileMeFragment aSu;

        {
            this.aSu = this$0;
        }

        @MessageHandler(message = 1)
        public void onRecvSpaceStyleChanged(h spaceStyle) {
            this.aSu.aSr = true;
            if (j.ep().ey() && this.aSu.aKG != null && spaceStyle != null) {
                this.aSu.aKG.space = spaceStyle;
                this.aSu.aSi.setProfileInfo(this.aSu.aKG);
                this.aSu.aSh.g(this.aSu.aKG);
                this.aSu.Ht();
            }
        }

        @MessageHandler(message = 2)
        public void onRecvApplyNormalStyle(boolean useSlices, List<b> photos) {
            int i = 1;
            this.aSu.aSr = true;
            if (j.ep().ey() && this.aSu.aKG != null) {
                ProfileInfo e = this.aSu.aKG;
                if (!useSlices) {
                    i = 0;
                }
                e.model = i;
                List<PhotoInfo> photoInfo = new ArrayList();
                for (b unit : photos) {
                    if (!UtilsFunction.empty(unit.fid)) {
                        photoInfo.add(new PhotoInfo(Long.parseLong(unit.fid), unit.url));
                    }
                }
                this.aSu.aKG.setPhoto(photoInfo);
                this.aSu.aSi.setProfileInfo(this.aSu.aKG);
                this.aSu.aSh.g(this.aSu.aKG);
                this.aSu.Ht();
            }
        }

        @MessageHandler(message = 3)
        public void onRecvUpdatePhoto(List<b> photos) {
            this.aSu.aSr = true;
            if (j.ep().ey() && this.aSu.aKG != null) {
                List<PhotoInfo> photoInfo = new ArrayList();
                for (b unit : photos) {
                    if (!UtilsFunction.empty(unit.fid)) {
                        photoInfo.add(new PhotoInfo(Long.parseLong(unit.fid), unit.url));
                    }
                }
                this.aSu.aKG.setPhoto(photoInfo);
                this.aSu.aSi.setProfileInfo(this.aSu.aKG);
            }
        }
    };
    private CallbackHandler aky = new CallbackHandler(this) {
        final /* synthetic */ ProfileMeFragment aSu;

        {
            this.aSu = this$0;
        }

        @MessageHandler(message = 550)
        public void onRecvProfileInfo(boolean succ, ProfileInfo info, long userId) {
            this.aSu.aSs = false;
            this.aSu.e(false, null);
            if (!j.ep().ey() || j.ep().getUserid() != userId) {
                return;
            }
            if (succ && info != null) {
                this.aSu.aSr = true;
                this.aSu.aKG = info;
                this.aSu.aSi.setProfileInfo(this.aSu.aKG);
                this.aSu.aSi.Gj();
                this.aSu.aSh.g(this.aSu.aKG);
                this.aSu.Ht();
            } else if (this.aSu.aIy.getCurrentPage() == 0) {
                this.aSu.aIy.FB();
            }
        }

        @MessageHandler(message = 776)
        public void onRecvStudioId(boolean succ, y info, long userId, Object ctx) {
            if (!j.ep().ey() || j.ep().getUserid() != userId) {
                return;
            }
            if (!succ || info == null) {
                HLog.error("ProfileMeFragment.onRecvStudioId", "获取用户工作室id失败", new Object[0]);
            } else {
                this.aSu.aSi.ku(info.getSid());
            }
        }
    };
    private OnClickListener mClickListener = new 4(this);
    private ListView mListView;
    private View mView;

    public static ProfileMeFragment c(boolean fromHome, int statusHeight) {
        ProfileMeFragment fragment = new ProfileMeFragment();
        Bundle args = new Bundle();
        args.putBoolean(aSd, fromHome);
        args.putInt(aSe, statusHeight);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.aSf = getArguments().getBoolean(aSd, false);
            this.aSg = getArguments().getInt(aSe);
        }
        this.aKb = new a(this, null);
        this.aSj = new b(this, null);
        this.aSk = new c(this, null);
        i.c(this.aKb);
        i.d(this.aSj);
        i.j(this.aSk);
        this.aSl = new MsgtipReciver(this);
        this.aSm = new ClearMsgReciver(this);
        i.e(this.aSl);
        i.f(this.aSm);
        EventNotifyCenter.add(com.MCWorld.module.h.class, this.aky);
        EventNotifyCenter.add(f.class, this.aSt);
    }

    public void onResume() {
        super.onResume();
        Hs();
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null && isVisibleToUser && isResumed()) {
            Hs();
        }
    }

    private void Hs() {
        if (!j.ep().ey()) {
            this.aIy.FC();
            this.aKG = null;
            this.aSi.logout();
            this.aSh.g(this.aKG);
            Ht();
        } else if (this.aKG == null) {
            reload();
        } else {
            reload();
        }
    }

    private void reload() {
        if (j.ep().ey()) {
            if (g.Eb().aU(j.ep().getUserid()) == null) {
                e(true, "获取用户信息...");
            }
            if (!this.aSs) {
                this.aSs = true;
                g.Eb().aS(j.ep().getUserid());
                z.DO();
                z.b(j.ep().getUserid(), null);
            }
        }
    }

    public void onDestroyView() {
        super.onDestroyView();
        if (this.aSi != null) {
            this.aSi.uninit();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.aKb != null) {
            i.unregisterReceiver(this.aKb);
            this.aKb = null;
        }
        if (this.aSj != null) {
            i.unregisterReceiver(this.aSj);
            this.aSj = null;
        }
        if (this.aSk != null) {
            i.unregisterReceiver(this.aSk);
            this.aSk = null;
        }
        if (this.aSl != null) {
            i.unregisterReceiver(this.aSl);
            this.aSl = null;
        }
        if (this.aSm != null) {
            i.unregisterReceiver(this.aSm);
            this.aSm = null;
        }
        EventNotifyCenter.remove(this.aky);
        EventNotifyCenter.remove(this.aSt);
    }

    @android.support.annotation.z
    public View onCreateView(LayoutInflater inflater, @android.support.annotation.z ViewGroup container, @android.support.annotation.z Bundle savedInstanceState) {
        this.mView = inflater.inflate(com.MCWorld.bbs.b.i.fragment_me, container, false);
        this.aIy = (BaseLoadingLayout) this.mView.findViewById(com.MCWorld.bbs.b.g.loading_layout);
        this.aIy.setRetryClickListener(new 1(this));
        this.aJc = this.mView.findViewById(com.MCWorld.bbs.b.g.loading);
        this.aID = (TextView) this.mView.findViewById(com.MCWorld.bbs.b.g.progressTxt);
        this.aSn = (PipelineView) this.mView.findViewById(com.MCWorld.bbs.b.g.iv_space_background);
        this.aSp = (HtImageView) this.mView.findViewById(com.MCWorld.bbs.b.g.iv_back);
        if (!this.aSf) {
            this.aSp.setVisibility(0);
            this.aSp.setOnClickListener(this.mClickListener);
        }
        this.aSo = (HtImageView) this.mView.findViewById(com.MCWorld.bbs.b.g.iv_msg);
        this.aSo.setOnClickListener(this.mClickListener);
        this.aIg = (TextView) this.mView.findViewById(com.MCWorld.bbs.b.g.tv_msg);
        if (!this.aSf && this.aSg > 0) {
            RelativeLayout rly_title = (RelativeLayout) this.mView.findViewById(com.MCWorld.bbs.b.g.rly_title);
            LayoutParams lp = new LayoutParams(-2, -2);
            lp.setMargins(0, this.aSg, 0, 0);
            rly_title.setLayoutParams(lp);
        }
        j(this.mView);
        return this.mView;
    }

    private void j(View view) {
        this.mListView = (ListView) view.findViewById(com.MCWorld.bbs.b.g.list);
        this.aSi = new ProfileHeaderLayout(getActivity(), false, this.aSf);
        this.aSi.setTranslucentHeight(at.getScreenHeight(getActivity()) - at.dipToPx(getActivity(), 74));
        this.mListView.addHeaderView(this.aSi);
        this.aSh = new ProfileSpaceAdapter(getActivity(), false, this);
        this.mListView.setAdapter(this.aSh);
    }

    private void Ht() {
        if (j.ep().ey()) {
            this.aSo.setVisibility(0);
            Fr();
        } else {
            this.aSo.setVisibility(8);
            this.aIg.setVisibility(8);
        }
        if (!j.ep().ey() || this.aKG == null || this.aKG.model != 2 || this.aKG.space == null) {
            this.aIy.FC();
            this.aSn.setVisibility(8);
            this.aSn.setImageResource(com.MCWorld.bbs.b.f.bg_profile);
            return;
        }
        Hu();
    }

    private void Hu() {
        Config config = Config.defaultConfig();
        config.placeHolder = com.MCWorld.bbs.b.f.bg_profile;
        config.errorHolder = d.white;
        this.aSn.setUri(UtilUri.parseUriOrNull(this.aKG.space.imgurl), config, new 2(this));
        if (this.aSq) {
            this.Vo.sendMessageDelayed(this.Vo.obtainMessage(1), 500);
        }
    }

    private void Hv() {
        if (this.aIy.getCurrentPage() != 2) {
            this.aIy.FC();
        }
        Activity activity = getActivity();
        if (activity != null && !activity.isFinishing()) {
            this.aSq = true;
            this.Vo.sendMessageDelayed(this.Vo.obtainMessage(1), 500);
            FrameLayout.LayoutParams llp = (FrameLayout.LayoutParams) this.aSn.getLayoutParams();
            llp.width = UtilsScreen.getScreenWidth(activity);
            llp.height = UtilsScreen.getScreenHeight(activity);
            this.aSn.setLayoutParams(llp);
            this.aSn.setVisibility(0);
        }
    }

    private void Hw() {
        this.aIy.FB();
        this.aIy.setRetryClickListener(new 3(this));
    }

    public void Hx() {
        this.aSr = false;
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

    protected void a(a builder) {
        super.a(builder);
        k setter = new com.simple.colorful.setter.j(this.mListView);
        setter.a(this.aSh);
        builder.a(setter).a(this.aSi).d(this.aSn, c.valBrightness).c(this.aSo, c.drawableProfileTitleMsg).d(this.aSo, c.valBrightness);
    }

    protected void kj(int themeId) {
        super.kj(themeId);
        this.aSh.notifyDataSetChanged();
        this.aSi.FG();
    }

    public void cI(boolean flag) {
        this.aSr = flag;
    }

    private void e(boolean show, String _msg) {
        String msg;
        if (_msg == null) {
            msg = "努力加载中...";
        } else {
            msg = _msg;
        }
        if (show) {
            this.aSi.cu(false);
            this.aID.setText(msg);
            this.aJc.setVisibility(0);
            return;
        }
        this.aSi.cu(true);
        this.aJc.setVisibility(8);
    }
}
