package com.MCWorld.ui.profile;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.MCWorld.HTApplication;
import com.MCWorld.bbs.b.d;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.m;
import com.MCWorld.data.UserBaseInfo;
import com.MCWorld.data.message.MsgCounts;
import com.MCWorld.data.profile.ProfileInfo;
import com.MCWorld.data.profile.c;
import com.MCWorld.data.profile.e.a;
import com.MCWorld.framework.base.image.Config;
import com.MCWorld.framework.base.image.PipelineView;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.framework.base.utils.UtilUri;
import com.MCWorld.framework.base.utils.UtilsScreen;
import com.MCWorld.framework.base.widget.dialog.CommonMenuDialog;
import com.MCWorld.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.ResMenuItem;
import com.MCWorld.http.base.f;
import com.MCWorld.http.profile.b;
import com.MCWorld.module.h;
import com.MCWorld.module.w;
import com.MCWorld.module.y;
import com.MCWorld.module.z;
import com.MCWorld.service.i;
import com.MCWorld.t;
import com.MCWorld.ui.base.BaseLoadingLayout;
import com.MCWorld.ui.base.BaseThemeFragment;
import com.MCWorld.ui.bbs.ProfileHeaderLayout;
import com.MCWorld.ui.itemadapter.profile.ProfileSpaceAdapter;
import com.MCWorld.widget.HtImageView;
import com.simple.colorful.setter.j;
import com.simple.colorful.setter.k;
import java.util.ArrayList;

public class ProfileOtherFragment extends BaseThemeFragment implements f {
    private static final String aHW = "ARG_USER_ID";
    private static final String aSe = "ARG_STATUS_HEIGHT";
    private static final String bgH = "ARG_USER_BASE_INFO";
    protected Handler Vo = new 3(this);
    private TextView aIg;
    private BaseLoadingLayout aIy;
    private View aJc;
    private ProfileInfo aKG = null;
    private UserBaseInfo aKy;
    private int aSg;
    private ProfileSpaceAdapter aSh = null;
    private ProfileHeaderLayout aSi = null;
    private PipelineView aSn;
    private HtImageView aSo;
    private HtImageView aSp;
    private CallbackHandler aky = new CallbackHandler(this) {
        final /* synthetic */ ProfileOtherFragment bgR;

        {
            this.bgR = this$0;
        }

        @MessageHandler(message = 550)
        public void onRecvProfileInfo(boolean succ, ProfileInfo info, long userId) {
            if (this.bgR.pM == userId) {
                if (succ && info != null) {
                    this.bgR.setProfileInfo(info);
                } else if (this.bgR.aIy.getCurrentPage() == 0) {
                    this.bgR.aIy.FB();
                } else {
                    t.n(this.bgR.getActivity(), this.bgR.getResources().getString(m.fetch_profile_failed));
                }
            }
        }

        @MessageHandler(message = 776)
        public void onRecvStudioId(boolean succ, y info, long userId, Object ctx) {
            if (this.bgR.pM == userId) {
                if (!succ || info == null) {
                    HLog.error("ProfileOtherFragment.onRecvStudioId", "获取用户工作室id失败", new Object[0]);
                } else {
                    this.bgR.aSi.ku(info.getSid());
                }
            }
        }

        @MessageHandler(message = 777)
        public void onRecRemoveMembers(boolean succ, w info, a user, int studioId) {
            z.DO();
            z.b(this.bgR.pM, null);
        }

        @MessageHandler(message = 784)
        public void acceptStudioInfo(boolean isSucc, c info, int studioId, Object ctx) {
            if (info == null || info.studioInfo == null) {
                z.DO();
                z.b(this.bgR.pM, null);
            }
        }
    };
    private boolean bgI = false;
    private int bgJ = 3;
    private b bgK = new b();
    private com.MCWorld.http.profile.c bgL = new com.MCWorld.http.profile.c();
    private CheckedTextView bgM;
    private View bgN;
    private MsgtipReciver bgO;
    private ClearMsgReciver bgP;
    private CommonMenuDialog bgQ;
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ ProfileOtherFragment bgR;

        {
            this.bgR = this$0;
        }

        @MessageHandler(message = 600)
        public void onCompliant(boolean succ, String msg) {
            this.bgR.cs(false);
            if (succ) {
                t.o(this.bgR.getActivity(), msg);
            } else {
                t.n(this.bgR.getActivity(), msg);
            }
        }
    };
    private OnClickListener mClickListener = new 5(this);
    private Context mContext;
    private ListView mListView = null;
    private View mView;
    private long pM;

    public static ProfileOtherFragment a(long userId, UserBaseInfo userBaseInfo, int statusHeight) {
        ProfileOtherFragment fragment = new ProfileOtherFragment();
        Bundle args = new Bundle();
        args.putLong(aHW, userId);
        args.putParcelable(bgH, userBaseInfo);
        args.putInt(aSe, statusHeight);
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
        if (getArguments() != null) {
            this.pM = getArguments().getLong(aHW);
            this.aKy = (UserBaseInfo) getArguments().getParcelable(bgH);
            this.aSg = getArguments().getInt(aSe);
        }
        EventNotifyCenter.add(h.class, this.aky);
        EventNotifyCenter.add(h.class, this.mCallback);
        this.bgO = new MsgtipReciver(this);
        this.bgP = new ClearMsgReciver(this);
        i.e(this.bgO);
        i.f(this.bgP);
    }

    public void onDestroyView() {
        super.onDestroyView();
        EventNotifyCenter.remove(this.mCallback);
        EventNotifyCenter.remove(this.aky);
        if (this.bgO != null) {
            i.unregisterReceiver(this.bgO);
            this.bgO = null;
        }
        if (this.bgP != null) {
            i.unregisterReceiver(this.bgP);
            this.bgP = null;
        }
        if (this.aSi != null) {
            this.aSi.uninit();
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mView = inflater.inflate(com.MCWorld.bbs.b.i.fragment_me, container, false);
        this.aIy = (BaseLoadingLayout) this.mView.findViewById(g.loading_layout);
        this.aIy.setRetryClickListener(new 1(this));
        this.aSn = (PipelineView) this.mView.findViewById(g.iv_space_background);
        this.aJc = this.mView.findViewById(g.loading);
        RelativeLayout rly_title = (RelativeLayout) this.mView.findViewById(g.rly_title);
        LayoutParams lp = new LayoutParams(-2, -2);
        lp.setMargins(0, this.aSg, 0, 0);
        rly_title.setLayoutParams(lp);
        this.bgL.w(this.pM);
        this.bgL.bb(2);
        this.bgL.a(this);
        j(this.mView);
        this.bgK.w(this.pM);
        this.bgK.bb(1);
        this.bgK.a(this);
        this.bgK.execute();
        this.bgN = this.aSi.findViewById(g.iv_complaint);
        this.bgM = (CheckedTextView) this.aSi.findViewById(g.tv_follow);
        this.bgN.setOnClickListener(new 4(this));
        Jz();
        this.aSp = (HtImageView) this.mView.findViewById(g.iv_back);
        this.aSp.setVisibility(0);
        this.aSp.setOnClickListener(this.mClickListener);
        this.aSo = (HtImageView) this.mView.findViewById(g.iv_msg);
        this.aSo.setOnClickListener(this.mClickListener);
        this.aIg = (TextView) this.mView.findViewById(g.tv_msg);
        reload();
        Fr();
        return this.mView;
    }

    public void onResume() {
        super.onResume();
        this.aSi.startAnimation();
    }

    public void onPause() {
        super.onPause();
        this.aSi.Gi();
    }

    public void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.aky);
    }

    private void j(View view) {
        this.mListView = (ListView) view.findViewById(g.list);
        this.aSi = new ProfileHeaderLayout(getActivity(), true, false);
        this.aSi.setTranslucentHeight(UtilsScreen.getScreenHeight(getActivity()) - UtilsScreen.dipToPx(getActivity(), 15));
        this.aSi.setUserBaseInfo(this.aKy);
        this.mListView.addHeaderView(this.aSi);
        this.aSh = new ProfileSpaceAdapter(getActivity(), true);
        this.mListView.setAdapter(this.aSh);
    }

    private void reload() {
        z.DO();
        z.b(this.pM, null);
        ProfileInfo profileInfo = com.MCWorld.module.profile.g.Eb().aU(this.pM);
        if (profileInfo != null) {
            setProfileInfo(profileInfo);
        }
        com.MCWorld.module.profile.g.Eb().aR(this.pM);
    }

    private void Ht() {
        if (this.aKG == null || this.aKG.model != 2 || this.aKG.space == null) {
            this.aSn.setVisibility(8);
            this.aSn.setImageResource(com.MCWorld.bbs.b.f.bg_profile);
            return;
        }
        Hu();
    }

    private void Hu() {
        int width = UtilsScreen.getScreenWidth(getActivity());
        int height = UtilsScreen.getScreenHeight(getActivity());
        Config config = Config.defaultConfig();
        config.placeHolder = com.MCWorld.bbs.b.f.bg_profile;
        config.errorHolder = d.white;
        this.aSn.setUri(UtilUri.parseUriOrNull(this.aKG.space.imgurl), config, new 6(this, width, height));
    }

    public void a(com.MCWorld.http.base.d response) {
    }

    public void b(com.MCWorld.http.base.d response) {
        switch (response.fe()) {
            case 2:
                cs(false);
                this.bgM.setClickable(true);
                if (this.bgI) {
                    t.n(getActivity(), "关注失败，请稍后重试");
                    return;
                } else {
                    t.n(getActivity(), "取消关注失败，请稍后重试");
                    return;
                }
            case 3:
                cs(false);
                t.n(getActivity(), "举报失败，请稍后重试");
                return;
            default:
                return;
        }
    }

    public void c(com.MCWorld.http.base.d response) {
        if (response.getStatus() == 1) {
            switch (response.fe()) {
                case 1:
                    this.bgI = this.bgK.fS();
                    this.bgJ = this.bgK.fT();
                    Jy();
                    return;
                case 2:
                    cs(false);
                    this.bgI = !this.bgI;
                    if (this.bgJ == 0) {
                        this.bgJ = 2;
                    } else if (3 == this.bgJ) {
                        this.bgJ = 1;
                    } else if (1 == this.bgJ) {
                        this.bgJ = 3;
                    } else {
                        this.bgJ = 0;
                    }
                    this.bgM.setClickable(true);
                    if (this.bgI) {
                        t.o(getActivity(), "关注成功");
                    } else {
                        t.o(getActivity(), "取消关注成功");
                    }
                    Jy();
                    return;
                case 3:
                    cs(false);
                    t.o(getActivity(), "举报成功，等待处理");
                    return;
                default:
                    return;
            }
        }
        t.n(getActivity(), response.fh());
    }

    private void setProfileInfo(ProfileInfo profileInfo) {
        if (profileInfo != null) {
            this.aIy.FC();
            this.aKG = profileInfo;
            this.aSi.setProfileInfo(this.aKG);
            this.aSh.g(this.aKG);
            Ht();
        }
    }

    private void Jy() {
        if (1 == this.bgJ) {
            this.bgM.setText(m.followed);
            this.bgM.setTextColor(com.simple.colorful.d.getColor(this.mContext, com.MCWorld.bbs.b.c.attention_text_color_type_1));
            this.bgM.setBackgroundResource(com.simple.colorful.d.r(this.mContext, com.MCWorld.bbs.b.c.bg_attention_fans_type_1));
            this.bgM.setCompoundDrawablesWithIntrinsicBounds(this.mContext.getResources().getDrawable(com.simple.colorful.d.r(this.mContext, com.MCWorld.bbs.b.c.space_ic_followed)), null, null, null);
        } else if (2 == this.bgJ) {
            this.bgM.setText(m.mutual_follow);
            this.bgM.setTextColor(com.simple.colorful.d.getColor(this.mContext, com.MCWorld.bbs.b.c.attention_text_color_type_1));
            this.bgM.setBackgroundResource(com.simple.colorful.d.r(this.mContext, com.MCWorld.bbs.b.c.bg_attention_fans_type_1));
            this.bgM.setCompoundDrawablesWithIntrinsicBounds(this.mContext.getResources().getDrawable(com.simple.colorful.d.r(this.mContext, com.MCWorld.bbs.b.c.space_ic_mutual_follow)), null, null, null);
        } else {
            this.bgM.setText(m.by_followed);
            this.bgM.setTextColor(com.simple.colorful.d.getColor(this.mContext, com.MCWorld.bbs.b.c.attention_text_color_type_2));
            this.bgM.setBackgroundResource(com.simple.colorful.d.r(this.mContext, com.MCWorld.bbs.b.c.bg_attention_fans_type_2));
            this.bgM.setCompoundDrawablesWithIntrinsicBounds(this.mContext.getResources().getDrawable(com.simple.colorful.d.r(this.mContext, com.MCWorld.bbs.b.c.space_ic_follow)), null, null, null);
        }
    }

    public void Jz() {
        this.bgM.setOnClickListener(new 9(this));
    }

    private void JA() {
        ArrayList mMenuItemArrayList = new ArrayList();
        mMenuItemArrayList.add(new ResMenuItem("取消关注", 0, com.simple.colorful.d.isDayMode() ? d.locmgr_menu_res_red_color_day : d.locmgr_menu_res_red_color_night));
        this.bgQ = new CommonMenuDialog(getActivity(), mMenuItemArrayList, new 10(this), com.simple.colorful.d.RB());
        this.bgQ.showMenu(null, null);
    }

    private void bp(long userId) {
        Dialog dialog = new Dialog(getActivity(), com.simple.colorful.d.RD());
        View layout = LayoutInflater.from(getActivity()).inflate(com.MCWorld.bbs.b.i.include_dialog_two, null);
        ((TextView) layout.findViewById(g.tv_msg)).setText("是否举报该用户头像违规");
        dialog.setCancelable(false);
        dialog.setContentView(layout);
        dialog.show();
        ((TextView) layout.findViewById(g.tv_cancel)).setOnClickListener(new 11(this, dialog));
        ((TextView) layout.findViewById(g.tv_confirm)).setOnClickListener(new 2(this, dialog, userId));
    }

    private void cs(boolean show) {
        this.aJc.setVisibility(show ? 0 : 8);
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
        k setter = new j(this.mListView);
        setter.a(this.aSh);
        builder.a(setter).a(this.aSi).d(this.aSn, com.MCWorld.bbs.b.c.valBrightness).c(this.aSo, com.MCWorld.bbs.b.c.drawableProfileTitleMsg).d(this.aSo, com.MCWorld.bbs.b.c.valBrightness).c(this.aSp, com.MCWorld.bbs.b.c.drawableProfileTitleBack).d(this.aSp, com.MCWorld.bbs.b.c.valBrightness);
    }

    protected void kj(int themeId) {
        super.kj(themeId);
        this.aSh.notifyDataSetChanged();
        this.aSi.FG();
        Jy();
    }
}
