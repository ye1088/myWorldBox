package hlx.ui.heroslist;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.module.n;
import com.MCWorld.r;
import com.MCWorld.t;
import com.MCWorld.utils.c;
import hlx.data.tongji.a;

public class HerosListActivity extends Activity {
    private static final int PAGE_SIZE = 20;
    private static final String TAG = "HerosListActivity";
    private OnCheckedChangeListener Qm = new OnCheckedChangeListener(this) {
        final /* synthetic */ HerosListActivity bYk;

        {
            this.bYk = this$0;
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.rdobtnRankingListExperience:
                    if (true == isChecked) {
                        this.bYk.ny(1);
                        return;
                    }
                    return;
                case R.id.rdobtnRankingListNewGuide:
                    if (true == isChecked) {
                        this.bYk.ny(2);
                        return;
                    }
                    return;
                case R.id.rdobtnRankingListBreakthrough:
                    if (true == isChecked) {
                        this.bYk.ny(3);
                        return;
                    }
                    return;
                case R.id.rdobtnRankingListUnExhaustible:
                    if (true == isChecked) {
                        this.bYk.ny(4);
                        return;
                    }
                    return;
                case R.id.rdobtnRankingListMaze:
                    if (true == isChecked) {
                        this.bYk.ny(5);
                        return;
                    }
                    return;
                case R.id.rdobtnRankingListTrap:
                    if (true == isChecked) {
                        this.bYk.ny(6);
                        return;
                    }
                    return;
                case R.id.rdobtnRankingListParkour:
                    if (true == isChecked) {
                        this.bYk.ny(7);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };
    private c aEV;
    private ImageView bXW;
    private ImageView bXX;
    private RadioButton bXY;
    private RadioButton bXZ;
    private RadioButton bYa;
    private RadioButton bYb;
    private RadioButton bYc;
    private RadioButton bYd;
    private RadioButton bYe;
    private PullToRefreshListView bYf;
    private c bYg;
    private HerosListAdapter bYh;
    private int bYi;
    private int bYj;
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ HerosListActivity bYk;

        {
            this.bYk = this$0;
        }

        @MessageHandler(message = 2564)
        public void onRecvServerInfoData(boolean succ, int type, c info) {
            if (type == this.bYk.bYi) {
                this.bYk.bYf.onRefreshComplete();
                this.bYk.aEV.onLoadComplete();
                if (info == null || !succ) {
                    HLog.error(this.bYk.mContext, "onRecvHeroRankInfo info is NULL ", new Object[0]);
                } else if (this.bYk.bYh != null && info.isSucc()) {
                    if (info.start > 20) {
                        this.bYk.bYg.start = info.start;
                        this.bYk.bYg.more = info.more;
                        this.bYk.bYg.heroRankList.addAll(info.heroRankList);
                    } else {
                        this.bYk.bYg = info;
                    }
                    this.bYk.bYh.a(this.bYk.bYg.heroRankList, this.bYk.bYj, true);
                }
            }
        }
    };
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ HerosListActivity bYk;

        {
            this.bYk = this$0;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivHeroRankingBack:
                    this.bYk.finish();
                    return;
                case R.id.ivHeroRankingFeedback:
                    t.a(this.bYk.mContext, 10759344, false);
                    r.ck().K_umengEvent(a.bOm);
                    return;
                default:
                    return;
            }
        }
    };
    private Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_ranking_list);
        EventNotifyCenter.add(n.class, this.mCallback);
        Sw();
        sJ();
    }

    public void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
    }

    private void Sw() {
        this.mContext = this;
        this.bYh = new HerosListAdapter(this.mContext);
    }

    private void sJ() {
        this.bXW = (ImageView) findViewById(R.id.ivHeroRankingBack);
        this.bXW.setOnClickListener(this.mClickListener);
        this.bXX = (ImageView) findViewById(R.id.ivHeroRankingFeedback);
        this.bXX.setOnClickListener(this.mClickListener);
        this.bXY = (RadioButton) findViewById(R.id.rdobtnRankingListExperience);
        this.bXY.setOnCheckedChangeListener(this.Qm);
        this.bXZ = (RadioButton) findViewById(R.id.rdobtnRankingListNewGuide);
        this.bXZ.setOnCheckedChangeListener(this.Qm);
        this.bYa = (RadioButton) findViewById(R.id.rdobtnRankingListBreakthrough);
        this.bYa.setOnCheckedChangeListener(this.Qm);
        this.bYa.setChecked(true);
        this.bYb = (RadioButton) findViewById(R.id.rdobtnRankingListUnExhaustible);
        this.bYb.setOnCheckedChangeListener(this.Qm);
        this.bYc = (RadioButton) findViewById(R.id.rdobtnRankingListMaze);
        this.bYc.setOnCheckedChangeListener(this.Qm);
        this.bYd = (RadioButton) findViewById(R.id.rdobtnRankingListTrap);
        this.bYd.setOnCheckedChangeListener(this.Qm);
        this.bYe = (RadioButton) findViewById(R.id.rdobtnRankingListParkour);
        this.bYe.setOnCheckedChangeListener(this.Qm);
        this.bYf = (PullToRefreshListView) findViewById(R.id.lvHerosRankingList);
        this.bYf.setAdapter(this.bYh);
        this.bYf.setOnRefreshListener(new OnRefreshListener<ListView>(this) {
            final /* synthetic */ HerosListActivity bYk;

            {
                this.bYk = this$0;
            }

            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                d.Ua().C(this.bYk.bYi, 0, 20, this.bYk.bYj);
            }
        });
        this.aEV = new c((ListView) this.bYf.getRefreshableView());
        this.aEV.a(new c.a(this) {
            final /* synthetic */ HerosListActivity bYk;

            {
                this.bYk = this$0;
            }

            public void onLoadData() {
                if (this.bYk.bYg != null) {
                    d.Ua().C(this.bYk.bYi, this.bYk.bYg.start, 20, this.bYk.bYj);
                }
            }

            public boolean shouldLoadData() {
                if (this.bYk.bYg == null) {
                    this.bYk.aEV.onLoadComplete();
                    return false;
                } else if (this.bYk.bYg.more > 0) {
                    return true;
                } else {
                    return false;
                }
            }
        });
        this.bYf.setOnScrollListener(this.aEV);
        ny(3);
    }

    private void ny(int inputCatId) {
        this.bYi = inputCatId;
        this.bYh.HA();
        switch (inputCatId) {
            case 1:
            case 2:
            case 3:
            case 4:
                this.bYj = 0;
                break;
            case 5:
            case 6:
            case 7:
                this.bYj = 1;
                break;
            default:
                this.bYj = 0;
                break;
        }
        d.Ua().C(this.bYi, 0, 20, this.bYj);
    }

    protected void onResume() {
        super.onResume();
        EventNotifyCenter.add(n.class, this.mCallback);
    }

    protected void onPause() {
        super.onPause();
        EventNotifyCenter.remove(this.mCallback);
    }
}
