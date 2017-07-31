package com.huluxia.ui.mctool;

import android.os.Bundle;
import android.support.annotation.z;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.huluxia.data.map.MapProfileInfo;
import com.huluxia.framework.R;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.CommonMenuDialogListener;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.ResMenuItem;
import com.huluxia.module.k;
import com.huluxia.module.n;
import com.huluxia.ui.base.BaseLoadingFragment;
import com.huluxia.ui.itemadapter.map.ProfileResAdapter;
import com.huluxia.utils.aa;
import com.huluxia.utils.aa.a;
import com.simple.colorful.d;
import java.util.ArrayList;

public class ProfileResFragment extends BaseLoadingFragment {
    private static final int PAGE_SIZE = 20;
    private static final String TAG = "ProfileResFragment";
    private static final String bbv = "PARAM_RES_TYPE";
    private aa aHb;
    private PullToRefreshListView aQB;
    private int axr = 1;
    private ArrayList<Object> bbA = new ArrayList();
    private CommonMenuDialog bbB;
    private int bbC = 0;
    private Integer[] bbD = new Integer[]{Integer.valueOf(3), Integer.valueOf(5), Integer.valueOf(6)};
    private ProfileResFragment bbw;
    private TextView bbx;
    private ProfileResAdapter bby;
    private MapProfileInfo bbz;
    private CallbackHandler mCallback = new 1(this);
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ ProfileResFragment bbE;

        {
            this.bbE = this$0;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.arrow:
                    this.bbE.IA();
                    return;
                default:
                    return;
            }
        }
    };

    public static ProfileResFragment kR(int resType) {
        ProfileResFragment fragment = new ProfileResFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(bbv, resType);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.bbw = this;
        if (savedInstanceState == null) {
            this.axr = getArguments().getInt(bbv);
        } else {
            this.axr = savedInstanceState.getInt(bbv);
        }
        EventNotifyCenter.add(n.class, this.mCallback);
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            outState.putInt(bbv, this.axr);
        }
    }

    public void onActivityCreated(@z Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            this.axr = savedInstanceState.getInt(bbv);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_res, container, false);
        cq(false);
        cr(false);
        this.bbx = (TextView) view.findViewById(R.id.arrow);
        this.bbx.setOnClickListener(this.mClickListener);
        this.aQB = (PullToRefreshListView) view.findViewById(R.id.listview);
        IB();
        kT(this.bbC);
        Fo();
        reload();
        return view;
    }

    private void Fo() {
        this.bby = new ProfileResAdapter(getActivity());
        this.bby.kI(this.axr);
        this.aQB.setOnRefreshListener(new OnRefreshListener<ListView>(this) {
            final /* synthetic */ ProfileResFragment bbE;

            {
                this.bbE = this$0;
            }

            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                this.bbE.reload();
            }
        });
        this.aQB.setOnItemClickListener(new OnItemClickListener(this) {
            final /* synthetic */ ProfileResFragment bbE;

            {
                this.bbE = this$0;
            }

            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            }
        });
        this.aQB.setAdapter(this.bby);
        this.aHb = new aa((ListView) this.aQB.getRefreshableView());
        this.aHb.a(new a(this) {
            final /* synthetic */ ProfileResFragment bbE;

            {
                this.bbE = this$0;
            }

            public void onLoadData() {
                this.bbE.Fc();
            }

            public boolean shouldLoadData() {
                if (this.bbE.bbz == null) {
                    this.bbE.aHb.onLoadComplete();
                    return false;
                } else if (this.bbE.bbz.more > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        this.aQB.setOnScrollListener(this.aHb);
    }

    private void reload() {
        this.bbz = null;
        k.a(this.axr, this.bbC, kS(this.bbC), 0, 20, this.bbw);
    }

    private void Fc() {
        if (this.bbz != null) {
            k.a(this.axr, this.bbC, kS(this.bbC), this.bbz.start, 20, this.bbw);
        }
    }

    private void IA() {
        this.bbB.updateCurFocusIndex(this.bbC);
        this.bbB.showMenu(null, null);
    }

    private void IB() {
        CommonMenuDialogListener mMenuListener = new CommonMenuDialogListener(this) {
            final /* synthetic */ ProfileResFragment bbE;

            {
                this.bbE = this$0;
            }

            public void pressMenuById(int inIndex, Object inItem) {
                this.bbE.bbC = inIndex;
                this.bbE.kT(this.bbE.bbC);
                this.bbE.cs(true);
                this.bbE.reload();
                this.bbE.bbB.dismissDialog();
            }
        };
        this.bbA.add(new ResMenuItem(this.bbw.getString(R.string.my_res_contribute), 0, R.color.locmgr_unfocus_btn_color));
        this.bbA.add(new ResMenuItem(this.bbw.getString(R.string.my_res_accept), 1, R.color.locmgr_unfocus_btn_color));
        this.bbA.add(new ResMenuItem(this.bbw.getString(R.string.my_res_deny), 2, R.color.locmgr_unfocus_btn_color));
        this.bbB = new CommonMenuDialog(this.bbw.getActivity(), this.bbA, mMenuListener, d.RB());
    }

    private int kS(int index) {
        if (index < 0 || index >= this.bbD.length) {
            return 3;
        }
        return this.bbD[index].intValue();
    }

    private void kT(int index) {
        switch (index) {
            case 0:
                this.bbx.setText(this.bbw.getString(R.string.my_res_contribute));
                return;
            case 1:
                this.bbx.setText(this.bbw.getString(R.string.my_res_accept));
                return;
            case 2:
                this.bbx.setText(this.bbw.getString(R.string.my_res_deny));
                return;
            default:
                return;
        }
    }
}
