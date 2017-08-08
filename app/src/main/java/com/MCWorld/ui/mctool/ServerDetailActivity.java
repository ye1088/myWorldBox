package com.MCWorld.ui.mctool;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.MCWorld.HTApplication;
import com.MCWorld.data.message.MsgCounts;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.framework.base.utils.UtilUri;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.base.widget.dialog.DialogManager;
import com.MCWorld.framework.base.widget.pager.PagerFragment;
import com.MCWorld.framework.base.widget.pager.PagerSelectedAdapter;
import com.MCWorld.l;
import com.MCWorld.module.n;
import com.MCWorld.module.v;
import com.MCWorld.r;
import com.MCWorld.service.i;
import com.MCWorld.t;
import com.MCWorld.ui.base.HTBaseLoadingActivity;
import com.MCWorld.utils.at;
import com.MCWorld.utils.e;
import com.MCWorld.utils.k;
import com.MCWorld.utils.y;
import com.MCWorld.widget.banner.BannerGallery;
import com.MCWorld.widget.title.TitleBar;
import com.MCWorld.widget.viewpager.PagerSlidingTabStrip;
import com.simple.colorful.d;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ServerDetailActivity extends HTBaseLoadingActivity {
    public static final String bbM = "server_data";
    private BannerGallery aGX;
    private DialogManager aWo;
    private TitleBar bbN;
    private ImageButton bbO;
    private TextView bbP;
    private Button bbQ;
    private MsgtipReciver bbR;
    private ClearMsgReceiver bbS;
    TextView bbT;
    TextView bbU;
    TextView bbV;
    TextView bbW;
    PagerSlidingTabStrip bbX;
    private com.MCWorld.data.server.a.a bbY;
    private CallbackHandler bbZ = new CallbackHandler(this) {
        final /* synthetic */ ServerDetailActivity bca;

        {
            this.bca = this$0;
        }

        @MessageHandler(message = 258)
        public void onRecvDetailData(boolean succ, com.MCWorld.data.server.a.a info) {
            if (info == null || !succ) {
                this.bca.FB();
                return;
            }
            this.bca.aIU.setVisibility(8);
            if (e.empty(info.resourceList)) {
                this.bca.bbN.setBackgroundColor(this.bca.getResources().getColor(R.color.green));
            } else {
                this.bca.J(new a(info.resourceList).bcd);
            }
            this.bca.bbY = info;
            this.bca.II();
            this.bca.FC();
        }
    };
    private Activity mContext;
    ViewPager mPager;

    protected class ClearMsgReceiver extends BroadcastReceiver {
        final /* synthetic */ ServerDetailActivity bca;

        protected ClearMsgReceiver(ServerDetailActivity this$0) {
            this.bca = this$0;
        }

        public void onReceive(Context context, Intent intent) {
            this.bca.IH();
        }
    }

    protected class MsgtipReciver extends BroadcastReceiver {
        final /* synthetic */ ServerDetailActivity bca;

        protected MsgtipReciver(ServerDetailActivity this$0) {
            this.bca = this$0;
        }

        public void onReceive(Context context, Intent intent) {
            this.bca.IG();
        }
    }

    private static class a extends com.MCWorld.widget.banner.a {
        public List<com.MCWorld.widget.banner.a> bcd;

        private a() {
        }

        public a(List<String> urlStringList) {
            this.bcd = new ArrayList();
            if (!UtilsFunction.empty((Collection) urlStringList)) {
                for (int i = 0; i < urlStringList.size(); i++) {
                    com.MCWorld.widget.banner.a info = new a();
                    info.url = (String) urlStringList.get(i);
                    this.bcd.add(info);
                }
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        int i = 1;
        super.onCreate(savedInstanceState);
        EventNotifyCenter.add(n.class, this.bbZ);
        setContentView((int) R.layout.layout_server_detail);
        this.bbR = new MsgtipReciver(this);
        this.bbS = new ClearMsgReceiver(this);
        i.e(this.bbR);
        i.f(this.bbS);
        if (savedInstanceState == null) {
            this.bbY = (com.MCWorld.data.server.a.a) getIntent().getExtras().getParcelable(bbM);
        } else {
            this.bbY = (com.MCWorld.data.server.a.a) savedInstanceState.getParcelable(bbM);
        }
        if (this.bbY != null) {
            int i2;
            this.mContext = this;
            this.aWo = new DialogManager(this.mContext);
            ej("服务器详情");
            IE();
            IF();
            this.mPager = (ViewPager) findViewById(R.id.map_viewpager);
            this.bbT = (TextView) findViewById(R.id.server_version);
            this.bbU = (TextView) findViewById(R.id.server_name);
            this.bbV = (TextView) findViewById(R.id.server_online_status);
            this.bbW = (TextView) findViewById(R.id.server_online_count);
            this.bbX = (PagerSlidingTabStrip) findViewById(R.id.map_detail_tab);
            this.bbX.setTextColorResource(R.color.text_color);
            this.bbX.setTextSize(at.dipToPx(this, 15));
            this.bbX.setIndicatorColor(getResources().getColor(R.color.text_color_green));
            this.bbX.setIndicatorTextColor(true);
            this.bbX.setDividerColor(getResources().getColor(R.color.background_normal_2));
            this.bbX.setShouldExpand(true);
            this.mPager.setOffscreenPageLimit(2);
            this.mPager.setAdapter(new PagerSelectedAdapter(this, getSupportFragmentManager()) {
                final /* synthetic */ ServerDetailActivity bca;

                public PagerFragment getItem(int position) {
                    switch (position) {
                        case 0:
                            return ServerDetailFragment.b(this.bca.bbY);
                        default:
                            return null;
                    }
                }

                public int getCount() {
                    return 1;
                }

                public CharSequence getPageTitle(int position) {
                    switch (position) {
                        case 0:
                            return "详情";
                        default:
                            return super.getPageTitle(position);
                    }
                }
            });
            this.bbX.setViewPager(this.mPager);
            v.DL().jl(this.bbY.id);
            this.aIQ.setImageResource(d.isDayMode() ? R.drawable.btn_share_selector : R.drawable.btn_share_selector_night);
            if (this.bbY.shareUrl != null) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            if (this.bbY.shareUrl == "") {
                i = 0;
            }
            if ((i2 & i) != 0) {
                this.aIQ.setVisibility(0);
            } else {
                this.aIQ.setVisibility(8);
            }
            this.aIQ.setOnClickListener(new OnClickListener(this) {
                final /* synthetic */ ServerDetailActivity bca;

                {
                    this.bca = this$0;
                }

                public void onClick(View arg0) {
                    if (this.bca.bbY != null) {
                        k.a(this.bca, this.bca.bbY);
                    }
                }
            });
            Fy();
        }
    }

    private void IE() {
        findViewById(R.id.search_header).setVisibility(8);
        this.aGX = (BannerGallery) findViewById(R.id.banner_gallery);
    }

    protected void EX() {
        super.EX();
        v.DL().jl(this.bbY.id);
    }

    private void IF() {
        this.bbN = (TitleBar) findViewById(R.id.title_bar);
        this.bbN.setLeftLayout(R.layout.include_header);
        this.bbN.findViewById(R.id.include_header_root_view).setBackgroundResource(R.color.transparent);
        this.bbQ = (Button) this.bbN.findViewById(R.id.sys_header_back);
        this.bbQ.setBackgroundResource(R.drawable.sl_title_bar_button);
        this.bbQ.setText("服务器详情");
        this.bbQ.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(d.r(this.mContext, R.attr.back)), null, null, null);
        this.bbQ.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ServerDetailActivity bca;

            {
                this.bca = this$0;
            }

            public void onClick(View v) {
                this.bca.finish();
            }
        });
        this.bbP = (TextView) this.bbN.findViewById(R.id.tv_msg);
        this.bbO = (ImageButton) this.bbN.findViewById(R.id.img_msg);
        this.bbO.setBackgroundResource(R.drawable.sl_title_bar_button);
        this.bbO.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ServerDetailActivity bca;

            {
                this.bca = this$0;
            }

            public void onClick(View v) {
                t.a(this.bca, HTApplication.bM());
            }
        });
    }

    protected void IG() {
        MsgCounts msgCounts = HTApplication.bM();
        long allCount = msgCounts == null ? 0 : msgCounts.getAll();
        if (allCount > 0) {
            this.bbP.setVisibility(0);
            if (allCount > 99) {
                this.bbP.setText("99+");
                return;
            } else {
                this.bbP.setText(String.valueOf(msgCounts.getAll()));
                return;
            }
        }
        this.bbP.setVisibility(8);
    }

    protected void IH() {
        this.bbP.setVisibility(8);
    }

    private void J(final List<com.MCWorld.widget.banner.a> bannerInfos) {
        if (!UtilsFunction.empty((Collection) bannerInfos)) {
            this.aGX.setVisibility(0);
            this.aGX.setIndicatorVisible(false);
            this.aGX.getGallery().setLoader(new com.MCWorld.widget.banner.SimpleImageAdapter.a(this) {
                final /* synthetic */ ServerDetailActivity bca;

                {
                    this.bca = this$0;
                }

                public void a(String imageUrl, PaintView imageView) {
                    imageView.setUri(UtilUri.getUriOrNull(imageUrl)).scaleType(ScaleType.CENTER_CROP).placeHolder(R.drawable.banner_gallery_default_picture).fadeDuration(0).setImageLoader(l.cb().getImageLoader());
                }
            });
            this.aGX.getGallery().setInterval(5000);
            this.aGX.setData(bannerInfos);
            this.aGX.setOnItemClickListener(new OnItemClickListener(this) {
                final /* synthetic */ ServerDetailActivity bca;

                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    this.bca.b(position % bannerInfos.size(), bannerInfos);
                }
            });
        }
    }

    private void b(int index, List<com.MCWorld.widget.banner.a> bannerInfos) {
        ArrayList urls = new ArrayList();
        for (com.MCWorld.widget.banner.a info : bannerInfos) {
            urls.add(info.url);
        }
        t.a((Context) this, urls, index);
    }

    protected void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.bbZ);
        if (this.bbR != null) {
            i.unregisterReceiver(this.bbR);
            this.bbR = null;
        }
        if (this.bbS != null) {
            i.unregisterReceiver(this.bbS);
            this.bbS = null;
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(bbM, this.bbY);
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }

    private void II() {
        this.bbT.setText(this.bbY.ver);
        this.bbU.setText(this.bbY.name);
        this.bbV.setText(this.bbY.status > 0 ? "在线" : "离线");
        if (this.bbY.status > 0) {
            this.bbV.setBackgroundResource(R.drawable.style_bg_oval_green);
            if (this.bbY.maxCount > 0) {
                this.bbW.setText("最大人数:" + String.format("%d", new Object[]{Integer.valueOf(this.bbY.maxCount)}));
                this.bbW.setVisibility(0);
            } else {
                this.bbW.setVisibility(4);
            }
        } else {
            this.bbV.setBackgroundResource(R.drawable.style_bg_oval_gray);
            this.bbW.setVisibility(4);
        }
        TextView tag1 = (TextView) findViewById(R.id.tag1);
        TextView tag2 = (TextView) findViewById(R.id.tag2);
        TextView tag3 = (TextView) findViewById(R.id.tag3);
        TextView tag4 = (TextView) findViewById(R.id.tag4);
        ((TextView) findViewById(R.id.join)).setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ ServerDetailActivity bca;

            {
                this.bca = this$0;
            }

            public void onClick(View v) {
                int colorGreen = d.getColor(this.bca.mContext, R.attr.textColorGreen);
                this.bca.aWo.showOkCancelColorDialog("服务器相关指南", d.getColor(this.bca.mContext, R.attr.dialog_title_label_color), LayoutInflater.from(this.bca.mContext).inflate(R.layout.dialog_server, null), this.bca.mContext.getString(R.string.done), colorGreen, this.bca.mContext.getString(R.string.btn_cancel), colorGreen, true, new 1(this));
                r.ck().j(hlx.data.tongji.a.bMT, String.valueOf(this.bca.bbY.id));
            }
        });
        if (this.bbY.tagList != null) {
            String[] tagList = this.bbY.tagList.split(MiPushClient.ACCEPT_TIME_SEPARATOR);
            TextView[] viewList = new TextView[]{tag1, tag2, tag3, tag4};
            for (int i = 0; i < 4; i++) {
                if (i < tagList.length) {
                    viewList[i].setText(tagList[i]);
                    viewList[i].setVisibility(0);
                    viewList[i].setBackgroundDrawable(y.H(this, tagList[i]));
                } else {
                    viewList[i].setVisibility(4);
                }
            }
        } else {
            tag1.setVisibility(4);
            tag2.setVisibility(4);
            tag3.setVisibility(4);
            tag4.setVisibility(4);
        }
        if (((this.bbY.shareUrl != null ? 1 : 0) & (this.bbY.shareUrl != "" ? 1 : 0)) != 0) {
            this.aIQ.setVisibility(0);
        } else {
            this.aIQ.setVisibility(8);
        }
    }
}
