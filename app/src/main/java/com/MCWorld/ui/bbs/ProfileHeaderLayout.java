package com.MCWorld.ui.bbs;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.ViewAnimator;
import android.widget.ViewSwitcher;
import com.MCWorld.bbs.b;
import com.MCWorld.bbs.b.d;
import com.MCWorld.bbs.b.e;
import com.MCWorld.bbs.b.f;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.bbs.b.m;
import com.MCWorld.data.Medal;
import com.MCWorld.data.UserBaseInfo;
import com.MCWorld.data.j;
import com.MCWorld.data.profile.PhotoInfo;
import com.MCWorld.data.profile.ProfileInfo;
import com.MCWorld.framework.base.image.Config.NetFormat;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.utils.UtilUri;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.base.utils.UtilsScreen;
import com.MCWorld.l;
import com.MCWorld.module.n;
import com.MCWorld.r;
import com.MCWorld.t;
import com.MCWorld.ui.profile.ProfileScoreActivity;
import com.MCWorld.utils.as;
import com.MCWorld.utils.aw;
import com.MCWorld.utils.y;
import com.MCWorld.widget.SliceWallpaper;
import com.MCWorld.widget.arcprogressbar.ArcProgressBar;
import com.MCWorld.widget.banner.BannerGallery;
import com.MCWorld.widget.banner.SimpleImageAdapter;
import com.MCWorld.widget.textview.EmojiTextView;
import com.simple.colorful.c;
import hlx.data.tongji.a;
import java.util.ArrayList;
import java.util.List;

public class ProfileHeaderLayout extends ViewSwitcher implements c {
    private int aDX;
    private BannerGallery aGX;
    private ProfileInfo aKG;
    private boolean aKH;
    private ArcProgressBar aKI;
    private TextView aKJ;
    private TextView aKK;
    private EmojiTextView aKL;
    private TextView aKM;
    private TextView aKN;
    private TextView aKO;
    private TextView aKP;
    private ImageView aKQ;
    private ImageView aKR;
    private List<PaintView> aKS;
    private CheckedTextView aKT;
    private ImageView aKU;
    private ImageView aKV;
    private ViewAnimator aKW;
    private PaintView aKX;
    private SliceWallpaper aKY;
    private View aKZ;
    private PaintView aKq;
    private View aLa;
    private View aLb;
    private boolean aLc;
    private int aLd;
    private int aLe;
    private boolean aLf;
    private int aLg;
    private View aLh;
    private View aLi;
    private OnGlobalLayoutListener aLj;
    private TextView eN;
    private OnClickListener mClickListener;
    private Context mContext;

    public ProfileHeaderLayout(Context context, boolean isOther, boolean fromHome) {
        this(context, null, isOther, fromHome);
    }

    public ProfileHeaderLayout(Context context, AttributeSet attrs, boolean isOther, boolean fromHome) {
        super(context, attrs);
        this.aKH = false;
        this.aLc = false;
        this.aLj = new OnGlobalLayoutListener(this) {
            final /* synthetic */ ProfileHeaderLayout aLk;

            {
                this.aLk = this$0;
            }

            public void onGlobalLayout() {
                this.aLk.aLg = this.aLk.findViewById(g.rl_profile_info).getHeight();
                this.aLk.Gd();
                this.aLk.uninit();
            }
        };
        this.mClickListener = new OnClickListener(this) {
            final /* synthetic */ ProfileHeaderLayout aLk;

            {
                this.aLk = this$0;
            }

            public void onClick(View v) {
                int id = v.getId();
                if (id == g.tv_login || id == g.iv_login) {
                    t.an(this.aLk.getContext());
                } else if (id == g.wallpaper_empty || id == g.iv_wallpaper_default) {
                    if (this.aLk.aKG != null && !this.aLk.aKH) {
                        t.a(this.aLk.getContext(), 0, this.aLk.aKG, this.aLk.aLf);
                    }
                } else if (id == g.avatar) {
                    if (!this.aLk.aKH && this.aLk.aKG != null) {
                        t.a(this.aLk.getContext(), this.aLk.aKG);
                    }
                } else if (id == g.profile_hulu) {
                    t.a(this.aLk.getContext(), this.aLk.aKG, ProfileScoreActivity.bhe);
                } else if (id == g.profile_integral_title) {
                    t.a(this.aLk.getContext(), this.aLk.aKG, ProfileScoreActivity.bhd);
                } else if (g.iv_medal1 == id || g.iv_medal2 == id || g.iv_medal3 == id || g.iv_medal4 == id) {
                    t.f(this.aLk.getContext(), 1);
                } else if (id == g.studio && this.aLk.aDX != 0) {
                    r.ck().K_umengEvent(a.bMl);
                    EventNotifyCenter.notifyEventUiThread(n.class, n.axl, new Object[]{this.aLk.mContext, Integer.valueOf(this.aLk.aDX)});
                }
            }
        };
        this.mContext = context;
        this.aKH = isOther;
        init(context);
        this.aLf = fromHome;
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View notLoginView = inflater.inflate(i.include_profile_header_not_login, this, false);
        View loginView = inflater.inflate(i.include_profile_header_logined, this, false);
        addView(notLoginView);
        addView(loginView);
        getViewTreeObserver().addOnGlobalLayoutListener(this.aLj);
        this.aKV = (ImageView) notLoginView.findViewById(g.iv_wallpaper_not_login);
        this.aKW = (ViewAnimator) findViewById(g.container_wallpaper);
        this.aKX = (PaintView) this.aKW.getChildAt(0);
        this.aKX.setOnClickListener(this.mClickListener);
        this.aGX = (BannerGallery) this.aKW.getChildAt(1);
        this.aGX.setIndicatorVisible(false);
        this.aGX.getGallery().setLoader(new SimpleImageAdapter.a(this) {
            final /* synthetic */ ProfileHeaderLayout aLk;

            {
                this.aLk = this$0;
            }

            public void a(String imageUrl, PaintView imageView) {
                imageView.setUri(UtilUri.getUriOrNull(imageUrl)).scaleType(ScaleType.CENTER_CROP).placeHolder(f.loading_grey).fadeDuration(0).setImageLoader(l.cb().getImageLoader());
            }
        });
        this.aGX.getGallery().setInterval(3000);
        this.aGX.setOnItemClickListener(new OnItemClickListener(this) {
            final /* synthetic */ ProfileHeaderLayout aLk;

            {
                this.aLk = this$0;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                this.aLk.kt(position % this.aLk.aKG.getPhotos().size());
            }
        });
        this.aKY = (SliceWallpaper) this.aKW.getChildAt(2);
        this.aKY.setImageClickListener(new SliceWallpaper.a(this) {
            final /* synthetic */ ProfileHeaderLayout aLk;

            {
                this.aLk = this$0;
            }

            public void a(int position, List<String> list) {
                this.aLk.kt(position);
            }
        });
        this.aKZ = this.aKW.getChildAt(3);
        this.aKZ.setOnClickListener(this.mClickListener);
        this.aLh = notLoginView.findViewById(g.tv_login);
        this.aLi = notLoginView.findViewById(g.iv_login);
        this.aLh.setOnClickListener(this.mClickListener);
        this.aLi.setOnClickListener(this.mClickListener);
        this.aLa = notLoginView.findViewById(g.split_header);
        this.aLb = loginView.findViewById(g.split_header);
        this.aKq = (PaintView) loginView.findViewById(g.avatar);
        this.aKq.placeHolder(f.app_icon).oval().borderColor(getResources().getColor(d.white), UtilsScreen.convertDpToPixel(2.0f, getContext())).fadeDuration(0);
        this.aKI = (ArcProgressBar) loginView.findViewById(g.progress_lv);
        this.aKJ = (TextView) loginView.findViewById(g.profile_exp);
        this.aKK = (TextView) loginView.findViewById(g.tv_lv);
        this.aKL = (EmojiTextView) loginView.findViewById(g.profile_nick);
        this.aKM = (TextView) loginView.findViewById(g.profile_gender);
        this.eN = (TextView) loginView.findViewById(g.profile_title);
        this.aKN = (TextView) loginView.findViewById(g.profile_integral_title);
        this.aKO = (TextView) loginView.findViewById(g.profile_hulu);
        this.aKQ = (ImageView) loginView.findViewById(g.iv_cover);
        this.aKP = (TextView) loginView.findViewById(g.studio);
        this.aKS = new ArrayList();
        this.aKS.add((PaintView) loginView.findViewById(g.iv_medal1));
        this.aKS.add((PaintView) loginView.findViewById(g.iv_medal2));
        this.aKS.add((PaintView) loginView.findViewById(g.iv_medal3));
        this.aKS.add((PaintView) loginView.findViewById(g.iv_medal4));
        this.aKS.add((PaintView) loginView.findViewById(g.iv_medal5));
        this.aKS.add((PaintView) loginView.findViewById(g.iv_medal6));
        this.aKT = (CheckedTextView) loginView.findViewById(g.tv_follow);
        this.aKU = (ImageView) loginView.findViewById(g.iv_complaint);
        if (this.aKH) {
            this.aKT.setVisibility(0);
            this.aKU.setVisibility(0);
        } else {
            this.aKT.setVisibility(8);
            this.aKU.setVisibility(8);
        }
        this.aKq.setOnClickListener(this.mClickListener);
        this.aKP.setOnClickListener(this.mClickListener);
        this.aKO.setOnClickListener(this.mClickListener);
        this.aKN.setOnClickListener(this.mClickListener);
        int dimensionPixelOffset = getResources().getDimensionPixelOffset(e.profile_header_height);
        this.aLd = dimensionPixelOffset;
        this.aLe = dimensionPixelOffset;
        Ge();
        Gb();
    }

    public void uninit() {
        getViewTreeObserver().removeGlobalOnLayoutListener(this.aLj);
    }

    private void kt(int position) {
        if (this.aKH) {
            ArrayList<String> urls = new ArrayList();
            for (com.MCWorld.widget.banner.a info : this.aKG.getPhotos()) {
                urls.add(info.url);
            }
            t.a(getContext(), urls, position);
            return;
        }
        t.a(getContext(), 1, this.aKG, this.aLf);
    }

    private void setTranslucent(boolean translucent) {
        if (this.aLc != translucent) {
            this.aLc = translucent;
            Gb();
        }
    }

    private void Gb() {
        Gc();
        Gd();
        if (this.aLc) {
            this.aKW.setDisplayedChild(3);
        }
        this.aKQ.setBackgroundColor(com.simple.colorful.d.getColor(getContext(), this.aLc ? b.c.backgroundProfileHeaderTranslucent : b.c.backgroundProfileHeader));
        this.aKI.setBackgroundArcColor(com.simple.colorful.d.getColor(getContext(), this.aLc ? b.c.backgroundColorProfileExpTranslucent : b.c.backgroundColorProfileExp));
        this.aKI.setProgressBgColor(com.simple.colorful.d.getColor(getContext(), this.aLc ? b.c.backgroundColorProfileExpNextTranslucent : b.c.backgroundColorProfileExpNext));
        this.aKI.setProgressArcColor(com.simple.colorful.d.getColor(getContext(), b.c.backgroundColorProfileExpNow));
        int splitColor = com.simple.colorful.d.getColor(getContext(), this.aLc ? b.c.splitColorProfileTranslucent : b.c.splitColor);
        this.aLa.setBackgroundColor(splitColor);
        this.aLb.setBackgroundColor(splitColor);
    }

    private void Gc() {
        getChildAt(1).setLayoutParams(new LayoutParams(-1, this.aLc ? this.aLd : this.aLe));
    }

    private void Gd() {
        this.aLg = findViewById(g.rl_profile_info).getHeight();
        int dp4 = UtilsScreen.dipToPx(getContext(), 4);
        if (this.aLc) {
            this.aKQ.setMinimumHeight((this.aLg - this.aKL.getBottom()) - dp4);
        } else {
            this.aKQ.setMinimumHeight((this.aLg - this.aKL.getTop()) + dp4);
        }
    }

    public void Ge() {
        if (this.aKH || j.ep().ey()) {
            setDisplayedChild(1);
        } else {
            setDisplayedChild(0);
        }
    }

    public void setUserBaseInfo(UserBaseInfo info) {
        if (info != null) {
            this.aKq.setUri(UtilUri.getUriOrNull(info.getAvatar()), NetFormat.FORMAT_160).setImageLoader(l.cb().getImageLoader());
            this.aKL.setText(aw.W(info.getNick(), 8));
            this.aKM.setText(String.valueOf(info.getAge()));
            this.aKM.setTextColor(as.m(getContext(), info.getGender()));
            this.aKM.setCompoundDrawablesWithIntrinsicBounds(as.l(getContext(), info.getGender()), null, null, null);
            this.aKK.setText(getContext().getResources().getString(m.profile_level, new Object[]{Integer.valueOf(info.getLevel())}));
            if (UtilsFunction.empty(info.getIdentityTitle())) {
                this.eN.setVisibility(8);
            } else {
                this.eN.setVisibility(0);
                this.eN.setText(info.getIdentityTitle());
                y.a(this.eN, y.e(getContext(), info.getIdentityColor(), 2));
            }
            this.aKO.setText(getResources().getString(m.format_hulu_count, new Object[]{Integer.valueOf(info.getCredits())}));
        }
    }

    public void setProfileInfo(ProfileInfo info) {
        if (info != null) {
            String nickTitle;
            d(info);
            this.aKG = info;
            this.aKq.setUri(UtilUri.getUriOrNull(info.getAvatar()), NetFormat.FORMAT_160).setImageLoader(l.cb().getImageLoader());
            this.aKI.setMaxValue(info.getNextExp());
            this.aKI.setCurrentValue(info.getExp());
            this.aKJ.setText(getResources().getString(m.profile_exp, new Object[]{Integer.valueOf(info.getExp()), Integer.valueOf(info.getNextExp())}));
            this.aKK.setText(getContext().getResources().getString(m.profile_level, new Object[]{Integer.valueOf(info.getLevel())}));
            this.aKL.setText(aw.W(info.getNick(), 8));
            if (this.aKG.model != 2 || this.aKG.space == null) {
                this.aKL.setTextColor(com.simple.colorful.d.getColor(getContext(), 16842808));
            } else {
                this.aKL.setTextColor(em(this.aKG.space.color));
            }
            this.aKM.setText(String.valueOf(info.getAge()));
            this.aKM.setTextColor(as.m(getContext(), info.getGender()));
            this.aKM.setCompoundDrawablesWithIntrinsicBounds(as.l(getContext(), info.getGender()), null, null, null);
            if (UtilsFunction.empty(this.aKG.integralNick)) {
                nickTitle = String.valueOf(this.aKG.getIntegral() < 0 ? 0 : this.aKG.getIntegral());
            } else {
                nickTitle = this.aKG.integralNick;
            }
            if (UtilsFunction.empty(this.aKG.getIdentityTitle())) {
                this.eN.setVisibility(8);
            } else {
                this.eN.setVisibility(0);
                this.eN.setText(this.aKG.getIdentityTitle());
                y.a(this.eN, y.e(getContext(), (int) this.aKG.getIdentityColor(), 2));
            }
            this.aKN.setText(nickTitle);
            this.aKO.setText(getResources().getString(m.format_hulu_count, new Object[]{Long.valueOf(info.getCredits())}));
            Gh();
            Ge();
            Gc();
            Gd();
        }
    }

    private int em(String color) {
        try {
            return Color.parseColor(color);
        } catch (Exception e) {
            return com.simple.colorful.d.getColor(getContext(), 16842808);
        }
    }

    private void d(ProfileInfo info) {
        if (info != null) {
            int mode = info.model;
            if (mode == 0) {
                f(info);
            } else if (mode == 1) {
                e(info);
            } else if (mode == 2 && info.space != null) {
                setTranslucent(true);
            }
        }
    }

    public boolean Gf() {
        return this.aLc;
    }

    private void Gg() {
        this.aKW.setDisplayedChild(0);
        setTranslucent(false);
    }

    private void e(ProfileInfo info) {
        if (info == null || info.getPhotos() == null || info.getPhotos().size() <= 2) {
            f(info);
            return;
        }
        setTranslucent(false);
        this.aKW.setDisplayedChild(2);
        List<PhotoInfo> photos = info.getPhotos();
        List<String> urls = new ArrayList();
        for (PhotoInfo photo : photos) {
            urls.add(photo.url);
        }
        this.aKY.c(urls, false);
        this.aKY.startAnimation();
    }

    public void f(ProfileInfo info) {
        if (info == null || UtilsFunction.empty(info.getPhotos())) {
            Gg();
            return;
        }
        setTranslucent(false);
        List<com.MCWorld.widget.banner.a> bannerInfo = new ArrayList();
        this.aKW.setDisplayedChild(1);
        bannerInfo.addAll(info.getPhotos());
        this.aGX.setData(bannerInfo);
    }

    private void Gh() {
        int i;
        if (this.aKG == null || UtilsFunction.empty(this.aKG.getMedalList())) {
            for (i = 0; i < this.aKS.size(); i++) {
                ((PaintView) this.aKS.get(i)).setVisibility(4);
            }
            return;
        }
        List<Medal> medals = this.aKG.getMedalList();
        for (i = 0; i < this.aKS.size(); i++) {
            PaintView medalView = (PaintView) this.aKS.get(i);
            if (i < medals.size()) {
                medalView.setVisibility(0);
                medalView.setImageUrl(((Medal) medals.get(i)).getUrl(), l.cb().getImageLoader());
                medalView.setOnClickListener(this.mClickListener);
            } else {
                medalView.setVisibility(4);
            }
        }
    }

    public void logout() {
        setDisplayedChild(0);
        this.aKG = null;
        setTranslucent(false);
    }

    public void setTranslucentHeight(int height) {
        this.aLd = height;
        Gc();
    }

    public void startAnimation() {
        if (this.aKW.getDisplayedChild() == 2 && !this.aLc) {
            this.aKY.startAnimation();
        }
    }

    public void Gi() {
        this.aKY.Gi();
    }

    public com.simple.colorful.a.a b(com.simple.colorful.a.a builder) {
        builder.d(this.aKX, b.c.valBrightness).be(g.iv_login, b.c.valBrightness).be(g.iv_slice_1, b.c.valBrightness).be(g.iv_slice_2, b.c.valBrightness).be(g.iv_slice_3, b.c.valBrightness).d(this.aKV, b.c.valBrightness).d(this.aKq, b.c.valBrightness).a(this.aKK, 16842809).j(this.aKK, b.c.backgroundProfileButtonLv).a(this.eN, 16842809).a(this.aKP, b.c.studio_text).a(this.aKN, b.c.textColorProfileTitle).a(this.aKN, b.c.drawableProfileTitle, 1).a(this.aKP, b.c.studio_drawable_left, 1).a(this.aKO, b.c.textColorProfileHulu).a(this.aKO, b.c.drawableProfileHulu, 1).a(this.aKT, b.c.attention_text_color_type_1).a(this.aKT, b.c.attention_text_color_type_2).j(this.aKT, b.c.bg_attention_fans_type_1).j(this.aKT, b.c.bg_attention_fans_type_2).a(this.aKT, b.c.space_ic_follow, 1).a(this.aKT, b.c.space_ic_followed, 1).a(this.aKT, b.c.space_ic_mutual_follow, 1).j(this.aKJ, b.c.backgroundProfileExp).j(this.aKP, b.c.studio_bg).be(g.iv_medal1, b.c.valBrightness).be(g.iv_medal2, b.c.valBrightness).be(g.iv_medal3, b.c.valBrightness).be(g.iv_medal4, b.c.valBrightness).be(g.iv_triangle_exp, b.c.valBrightness);
        return builder;
    }

    public void FG() {
        Gb();
        if (this.aKG != null) {
            this.aKM.setTextColor(as.m(getContext(), this.aKG.getGender()));
            this.aKM.setCompoundDrawablesWithIntrinsicBounds(as.l(getContext(), this.aKG.getGender()), null, null, null);
        }
        this.aGX.FG();
    }

    public void cu(boolean enable) {
        if (enable) {
            this.aLh.setOnClickListener(this.mClickListener);
            this.aLi.setOnClickListener(this.mClickListener);
            this.aLh.setVisibility(0);
            return;
        }
        this.aLh.setOnClickListener(null);
        this.aLi.setOnClickListener(null);
        this.aLh.setVisibility(4);
    }

    public void Gj() {
        if (this.aLg == 0) {
            getViewTreeObserver().addOnGlobalLayoutListener(this.aLj);
        }
    }

    public void ku(int sid) {
        this.aDX = sid;
        this.aKP.setVisibility(sid == 0 ? 8 : 0);
    }
}
