package com.huluxia.studio.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huluxia.data.map.f;
import com.huluxia.framework.BaseEvent;
import com.huluxia.framework.R;
import com.huluxia.framework.base.http.module.ProgressInfo;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.module.h;
import com.huluxia.module.z;
import com.huluxia.ui.itemadapter.js.JsDownAdapter;
import com.huluxia.ui.itemadapter.map.DownAdapter;
import com.huluxia.ui.itemadapter.skin.SkinDownAdapter;
import com.huluxia.ui.itemadapter.wood.WoodDownAdapter;
import com.huluxia.utils.c;
import com.simple.colorful.a.a;
import com.simple.colorful.setter.j;
import com.simple.colorful.setter.k;
import hlx.home.fragment.ScrollableFragment;

public class StudioResourceFragment extends ScrollableFragment {
    private static final int PAGE_SIZE = 20;
    private static String TAG = "StrudioResourceFragment";
    public static final String aGI = "RES_TYPE";
    public static final String aGJ = "KEY_STUDIO_ID";
    private static final String aGK = "DATA";
    private int aDX = 0;
    private c aET;
    private PullToRefreshListView aEq;
    private boolean aGE;
    private boolean aGF = false;
    private DownAdapter aGG;
    private f aGH;
    private View aGL;
    CallbackHandler aGM = new CallbackHandler(this) {
        final /* synthetic */ StudioResourceFragment aGP;

        {
            this.aGP = this$0;
        }

        @MessageHandler(message = 790)
        public void onRecvMapListInfo(int studioId, int resType, boolean succ, f info) {
            this.aGP.aEq.onRefreshComplete();
            this.aGP.aET.onLoadComplete();
            if (studioId != this.aGP.aDX || this.aGP.axr != resType) {
                return;
            }
            if (succ && info != null) {
                if (info.start <= 20) {
                    this.aGP.aGH = info;
                    if (UtilsFunction.empty(info.mapList)) {
                        this.aGP.aGL.setVisibility(0);
                    } else {
                        this.aGP.aGL.setVisibility(8);
                    }
                } else if (this.aGP.aGH != null) {
                    this.aGP.aGH.start = info.start;
                    this.aGP.aGH.more = info.more;
                    this.aGP.aGH.mapList.addAll(info.mapList);
                } else {
                    return;
                }
                this.aGP.aGG.a(this.aGP.aGH.mapList, true);
            } else if (this.aGP.aGH != null) {
            }
        }
    };
    private CallbackHandler aGN = new CallbackHandler(this) {
        final /* synthetic */ StudioResourceFragment aGP;

        {
            this.aGP = this$0;
        }

        @MessageHandler(message = 258)
        public void onProgress(String url, String path, ProgressInfo progressInfo) {
            this.aGP.Ff();
        }

        @MessageHandler(message = 256)
        public void onDownloadSucc(String url, String path) {
            switch (this.aGP.axr) {
                case 1:
                case 2:
                case 3:
                case 4:
                    if (this.aGP.aGG != null && true == this.aGP.aGE && true == this.aGP.aGF) {
                        this.aGP.aGG.eu(url);
                        return;
                    } else if (this.aGP.aGG != null) {
                        this.aGP.aGG.notifyDataSetChanged();
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }

        @MessageHandler(message = 259)
        public void onDownloadCancel(String url, String path) {
            this.aGP.Ff();
        }

        @MessageHandler(message = 257)
        public void onDownloadError(String url, String path, Object context) {
            this.aGP.Ff();
        }
    };
    CallbackHandler aGO = new CallbackHandler(this) {
        final /* synthetic */ StudioResourceFragment aGP;

        {
            this.aGP = this$0;
        }

        @MessageHandler(message = 256)
        public void onWorkPrepare(String url) {
            this.aGP.Ff();
        }

        @MessageHandler(message = 257)
        public void onWorkWait(String url) {
            this.aGP.Ff();
        }

        @MessageHandler(message = 262)
        public void onUnzipComplete(String url) {
            this.aGP.Ff();
        }
    };
    private int axr = 1;
    private Context mContext;

    public static StudioResourceFragment av(int resourceType, int sid) {
        StudioResourceFragment f = new StudioResourceFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(aGI, resourceType);
        bundle.putInt(aGJ, sid);
        f.setArguments(bundle);
        return f;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
        if (savedInstanceState != null) {
            this.axr = savedInstanceState.getInt(aGI, 1);
            this.aDX = savedInstanceState.getInt(aGJ, 0);
            this.aGH = (f) savedInstanceState.getParcelable(aGK);
        } else {
            this.axr = getArguments().getInt(aGI, 1);
            this.aDX = getArguments().getInt(aGJ, 0);
        }
        EventNotifyCenter.add(h.class, this.aGM);
        EventNotifyCenter.add(BaseEvent.class, this.aGN);
        EventNotifyCenter.add(com.huluxia.controller.c.class, this.aGO);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tap_strip, container, false);
        d(view);
        EQ();
        EZ();
        return view;
    }

    public void onStart() {
        super.onStart();
        this.aGF = true;
    }

    public void onStop() {
        super.onStop();
        this.aGF = false;
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(aGI, this.axr);
        outState.putInt(aGJ, this.aDX);
        outState.putParcelable(aGK, this.aGH);
    }

    public void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.aGM);
        EventNotifyCenter.remove(this.aGN);
        EventNotifyCenter.remove(this.aGO);
    }

    private void d(View view) {
        this.aEq = (PullToRefreshListView) view.findViewById(R.id.lv_tap_strip);
        this.aET = new c((ListView) this.aEq.getRefreshableView());
        this.aGL = view.findViewById(R.id.tv_no_resource_tip);
    }

    private void EQ() {
        this.aET.a(new 1(this));
        this.aEq.setOnScrollListener(this.aET);
        this.aGG = Fe();
        this.aEq.setAdapter(this.aGG);
        if (this.aGH != null) {
            this.aGG.a(this.aGH.mapList, true);
        }
    }

    private void EZ() {
        if (this.aGH == null) {
            reload();
        }
    }

    public void reload() {
        z.DO();
        z.w(this.aDX, this.axr, 0, 20);
    }

    private void EY() {
        z.DO();
        z.w(this.aDX, this.axr, this.aGH.start, 20);
    }

    public void onSelected(int position) {
        super.onSelected(position);
        this.aGE = true;
    }

    public void onUnSelected(int position) {
        super.onUnSelected(position);
        this.aGE = false;
    }

    private DownAdapter Fe() {
        switch (this.axr) {
            case 2:
                return new JsDownAdapter((Activity) this.mContext);
            case 3:
                return new SkinDownAdapter((Activity) this.mContext);
            case 4:
                return new WoodDownAdapter((Activity) this.mContext);
            default:
                return new DownAdapter((Activity) this.mContext);
        }
    }

    private void Ff() {
        switch (this.axr) {
            case 1:
            case 2:
            case 3:
            case 4:
                if (this.aGG != null) {
                    this.aGG.notifyDataSetChanged();
                    return;
                }
                return;
            default:
                return;
        }
    }

    public boolean canScrollVertically(int direction) {
        int scrollY = 0;
        boolean canScroll = false;
        if (!(this.aEq == null || this.aEq.getRefreshableView() == null)) {
            View firstChild = ((ListView) this.aEq.getRefreshableView()).getChildAt(0);
            if (firstChild != null) {
                scrollY = firstChild.getTop();
            }
            canScroll = ViewCompat.canScrollVertically(this.aEq.getRefreshableView(), direction);
        }
        if (canScroll || scrollY < 0) {
            return true;
        }
        return false;
    }

    public void f(int y, long duration) {
        if (this.aEq != null && this.aEq.getRefreshableView() != null) {
            ((ListView) this.aEq.getRefreshableView()).smoothScrollBy(y, (int) duration);
        }
    }

    protected void a(a builder) {
        super.a(builder);
        k setter = new j((ViewGroup) this.aEq.getRefreshableView());
        setter.a(this.aGG);
        builder.a(setter);
        builder.aY(R.id.container, R.attr.backgroundDefault).ba(R.id.tv_no_resource_tip, 16843282).ab(R.id.tv_no_resource_tip, R.attr.drawableSearchNoResult, 1);
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }
}
