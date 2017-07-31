package hlx.ui.localresmgr.fragment;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.z;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.huluxia.data.map.b;
import com.huluxia.framework.R;
import com.huluxia.framework.base.async.AsyncTaskCenter;
import com.huluxia.framework.base.async.AsyncTaskCenter.RunnableCallback;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.CommonMenuDialogListener;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.ResMenuItem;
import com.huluxia.r;
import com.huluxia.service.i;
import com.huluxia.ui.base.BaseLoadingFragment;
import com.huluxia.utils.ah;
import com.huluxia.widget.Constants.DownFileType;
import com.simple.colorful.d;
import hlx.ui.localresmgr.adapter.WoodResMgrItemAdapter;
import hlx.ui.localresmgr.adapter.WoodResMgrItemAdapter.a;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LocResWoodFragment extends BaseLoadingFragment implements a {
    private static final String TAG = "LocResWoodFragment";
    private View Pu;
    private ListView aZh;
    private List<Object> akJ = new ArrayList();
    private ArrayList<Object> arrayList = new ArrayList();
    private boolean bYS = false;
    private ArrayList<Object> bbA = new ArrayList();
    private LinearLayout caA;
    private BroadcastReceiver caE = new BroadcastReceiver(this) {
        final /* synthetic */ LocResWoodFragment cbd;

        {
            this.cbd = this$0;
        }

        public void onReceive(Context context, Intent intent) {
            if (DownFileType.Wood.Value() == intent.getIntExtra("type", 0)) {
                this.cbd.UC();
            }
        }
    };
    private TextView caV;
    private List<b> caZ;
    private int cak = 1;
    private TextView cam;
    private TextView can;
    private TextView cao;
    private TextView cap;
    private TextView caq;
    private TextView car;
    private TextView cau;
    private TextView cav;
    private TextView caw;
    private TextView cax;
    private LinearLayout cay;
    private LinearLayout caz;
    private WoodResMgrItemAdapter cba = null;
    private CommonMenuDialog cbb;
    private Runnable cbc = new Runnable(this) {
        final /* synthetic */ LocResWoodFragment cbd;

        {
            this.cbd = this$0;
        }

        public void run() {
            try {
                this.cbd.caZ = hlx.ui.localresmgr.cache.b.Us().nD(this.cbd.cak);
            } catch (Exception e) {
            }
        }
    };
    private Activity mActivity;
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ LocResWoodFragment cbd;

        {
            this.cbd = this$0;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tvConfirmDel:
                    this.cbd.UF();
                    return;
                case R.id.tvConfirmCancle:
                    this.cbd.UE();
                    return;
                case R.id.tvConfirmAll:
                    this.cbd.UG();
                    return;
                case R.id.tvLocalResMgrSort:
                    this.cbd.IA();
                    return;
                case R.id.tvLocalResMgrDel:
                    this.cbd.UD();
                    r.ck().K(hlx.data.tongji.a.bNg);
                    return;
                case R.id.tvLocalResMgrImport:
                    hlx.ui.a.H(this.cbd.mActivity);
                    r.ck().K(hlx.data.tongji.a.bNl);
                    return;
                case R.id.tvLocalResMgrRecoverDefault:
                    this.cbd.UR();
                    this.cbd.cay.setVisibility(8);
                    return;
                case R.id.tvNoFilesTips:
                    hlx.ui.a.ca(this.cbd.mActivity);
                    return;
                default:
                    return;
            }
        }
    };

    public static LocResWoodFragment UQ() {
        return new LocResWoodFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ce(true);
    }

    public void onResume() {
        super.onResume();
        US();
    }

    public void onDestroy() {
        super.onDestroy();
        ce(false);
        if (this.cbc != null) {
            AsyncTaskCenter.getInstance().cancel(this.cbc);
            this.cbc = null;
        }
    }

    public void onActivityCreated(@z Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.Pu = inflater.inflate(R.layout.lyt_home_localresmgr, container, false);
        this.mActivity = getActivity();
        Sw();
        sJ();
        cq(false);
        cr(false);
        return this.Pu;
    }

    private void Sw() {
        this.cba = new WoodResMgrItemAdapter(this.mActivity, this.arrayList);
        this.cba.a((a) this);
    }

    private void sJ() {
        this.can = (TextView) this.Pu.findViewById(R.id.tvNoFilesTips);
        this.can.setText(R.string.TipMCNoneWood);
        this.can.setOnClickListener(this.mClickListener);
        this.aZh = (ListView) this.Pu.findViewById(R.id.lvLocalResMgr);
        this.aZh.setAdapter(this.cba);
        this.cba.S(this.akJ);
        this.caV = (TextView) this.Pu.findViewById(R.id.tvLocalResMgrRecoverDefault);
        this.caV.setVisibility(0);
        this.caV.setOnClickListener(this.mClickListener);
        this.cao = (TextView) this.Pu.findViewById(R.id.tvLocalResMgrDel);
        this.cao.setOnClickListener(this.mClickListener);
        this.cap = (TextView) this.Pu.findViewById(R.id.tvLocalResMgrBackup);
        this.cap.setOnClickListener(this.mClickListener);
        this.caq = (TextView) this.Pu.findViewById(R.id.tvLocalResMgrImport);
        this.caq.setOnClickListener(this.mClickListener);
        this.car = (TextView) this.Pu.findViewById(R.id.tvLocalResMgrExport);
        this.car.setOnClickListener(this.mClickListener);
        this.cau = (TextView) this.Pu.findViewById(R.id.tvLocalResMgrTutorial);
        this.cau.setOnClickListener(this.mClickListener);
        this.caz = (LinearLayout) this.Pu.findViewById(R.id.llyBottomTabs);
        this.caA = (LinearLayout) this.Pu.findViewById(R.id.llyLocalResMgrConfirmBox);
        this.caw = (TextView) this.Pu.findViewById(R.id.tvConfirmCancle);
        this.caw.setOnClickListener(this.mClickListener);
        this.cav = (TextView) this.Pu.findViewById(R.id.tvConfirmDel);
        this.cav.setOnClickListener(this.mClickListener);
        this.cax = (TextView) this.Pu.findViewById(R.id.tvConfirmAll);
        this.cax.setOnClickListener(this.mClickListener);
        this.cam = (TextView) this.Pu.findViewById(R.id.tvLocalResMgrSort);
        this.cam.setText(this.mActivity.getString(R.string.local_resmgr_sort_name));
        this.cam.setOnClickListener(this.mClickListener);
        this.cay = (LinearLayout) this.Pu.findViewById(R.id.ly_headButton);
        this.cay.setVisibility(8);
        IB();
        Uy();
    }

    private void UR() {
        ah.KZ().gc(null);
        Iterator it = this.arrayList.iterator();
        while (it.hasNext()) {
            b _tmp = (b) it.next();
            if (_tmp.state == 1) {
                _tmp.state = 0;
            }
        }
        this.cba.notifyDataSetChanged();
        r.ck().K(hlx.data.tongji.a.bMY);
    }

    private void UD() {
        this.bYS = true;
        this.cba.dI(this.bYS);
        this.caA.setVisibility(0);
        this.caz.setVisibility(8);
    }

    private void UE() {
        this.bYS = false;
        this.cba.dI(this.bYS);
        this.caA.setVisibility(8);
        this.caz.setVisibility(0);
    }

    private void UF() {
        for (b item : this.akJ) {
            hlx.ui.localresmgr.cache.b.Us().o(item);
        }
        this.bYS = false;
        this.cba.dI(this.bYS);
        this.caA.setVisibility(8);
        this.caz.setVisibility(0);
        UC();
    }

    private void UG() {
        this.cba.Ud();
    }

    private void IA() {
        this.cbb.updateCurFocusIndex(this.cak);
        this.cbb.showMenu(null, null);
    }

    private void IB() {
        CommonMenuDialogListener mWoodMenuListener = new CommonMenuDialogListener(this) {
            final /* synthetic */ LocResWoodFragment cbd;

            {
                this.cbd = this$0;
            }

            public void pressMenuById(int inIndex, Object inItem) {
                this.cbd.cak = inIndex;
                this.cbd.Uy();
                this.cbd.UC();
                this.cbd.cbb.dismissDialog();
            }
        };
        this.bbA.add(new ResMenuItem(this.mActivity.getString(R.string.local_resmgr_sort_name), 0, R.color.locmgr_unfocus_btn_color));
        this.bbA.add(new ResMenuItem(this.mActivity.getString(R.string.local_resmgr_sort_time), 1, R.color.locmgr_unfocus_btn_color));
        this.bbA.add(new ResMenuItem(this.mActivity.getString(R.string.local_resmgr_sort_size), 2, R.color.locmgr_unfocus_btn_color));
        this.cbb = new CommonMenuDialog(this.mActivity, this.bbA, mWoodMenuListener, d.RB());
    }

    private void Uy() {
        switch (this.cak) {
            case 0:
                this.cam.setText(this.mActivity.getString(R.string.local_resmgr_sort_name));
                return;
            case 1:
                this.cam.setText(this.mActivity.getString(R.string.local_resmgr_sort_time));
                return;
            case 2:
                this.cam.setText(this.mActivity.getString(R.string.local_resmgr_sort_size));
                return;
            default:
                return;
        }
    }

    private void US() {
        AsyncTaskCenter.getInstance().execute(this.cbc, new RunnableCallback(this) {
            final /* synthetic */ LocResWoodFragment cbd;

            {
                this.cbd = this$0;
            }

            public void onCallback() {
                this.cbd.getActivity().runOnUiThread(new Runnable(this) {
                    final /* synthetic */ AnonymousClass3 cbe;

                    {
                        this.cbe = this$1;
                    }

                    public void run() {
                        this.cbe.cbd.UT();
                    }
                });
            }
        });
    }

    private void UT() {
        G(this.caZ);
        UA();
    }

    public void G(List<b> data) {
        if (data == null || data.size() == 0) {
            this.can.setVisibility(0);
            this.aZh.setVisibility(8);
            return;
        }
        this.can.setVisibility(8);
        this.aZh.setVisibility(0);
        this.arrayList.clear();
        this.arrayList.addAll(data);
        this.cba.notifyDataSetChanged();
    }

    public void UC() {
        this.arrayList.clear();
        this.cba.notifyDataSetChanged();
        US();
    }

    public void ce(boolean init) {
        if (init) {
            i.n(this.caE);
        } else {
            i.unregisterReceiver(this.caE);
        }
    }

    private void UA() {
        boolean haveOpenItem = false;
        if (this.caZ != null) {
            for (int i = 0; i < this.caZ.size(); i++) {
                b _tmpItem = (b) this.caZ.get(i);
                if (_tmpItem != null && _tmpItem.state == 1) {
                    haveOpenItem = true;
                    break;
                }
            }
            if (haveOpenItem) {
                this.cay.setVisibility(0);
            } else {
                this.cay.setVisibility(8);
            }
        }
    }

    public void dL(boolean bool) {
        US();
        UA();
    }
}
