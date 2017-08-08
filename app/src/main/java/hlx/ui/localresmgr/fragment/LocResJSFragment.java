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
import com.MCWorld.data.map.b;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.async.AsyncTaskCenter;
import com.MCWorld.framework.base.async.AsyncTaskCenter.RunnableCallback;
import com.MCWorld.framework.base.widget.dialog.CommonMenuDialog;
import com.MCWorld.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.CommonMenuDialogListener;
import com.MCWorld.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.ResMenuItem;
import com.MCWorld.r;
import com.MCWorld.service.i;
import com.MCWorld.ui.base.BaseLoadingFragment;
import com.MCWorld.utils.ai;
import com.MCWorld.utils.j;
import com.MCWorld.widget.Constants.DownFileType;
import com.simple.colorful.d;
import hlx.ui.localresmgr.adapter.JsResMgrItemAdapter;
import hlx.ui.localresmgr.adapter.JsResMgrItemAdapter.a;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LocResJSFragment extends BaseLoadingFragment implements a {
    private static final String TAG = "LocResJSFragment";
    private View Pu;
    private ListView aZh;
    private List<Object> akJ = new ArrayList();
    private ArrayList<Object> arrayList = new ArrayList();
    private boolean bYS = false;
    private ArrayList<Object> bbA = new ArrayList();
    private LinearLayout caA;
    private List<b> caB;
    private CommonMenuDialog caC;
    private Runnable caD = new Runnable(this) {
        final /* synthetic */ LocResJSFragment caF;

        {
            this.caF = this$0;
        }

        public void run() {
            try {
                this.caF.caB = hlx.ui.localresmgr.cache.b.Us().nE(this.caF.cak);
            } catch (Exception e) {
            }
        }
    };
    private BroadcastReceiver caE = new BroadcastReceiver(this) {
        final /* synthetic */ LocResJSFragment caF;

        {
            this.caF = this$0;
        }

        public void onReceive(Context context, Intent intent) {
            if (DownFileType.Js.Value() == intent.getIntExtra("type", 0)) {
                this.caF.UC();
            }
        }
    };
    private JsResMgrItemAdapter caj = null;
    private int cak = 1;
    private TextView cal;
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
    private Activity mActivity;
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ LocResJSFragment caF;

        {
            this.caF = this$0;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tvConfirmDel:
                    this.caF.UF();
                    return;
                case R.id.tvConfirmCancle:
                    this.caF.UE();
                    return;
                case R.id.tvConfirmAll:
                    this.caF.UG();
                    return;
                case R.id.tvLocalResMgrSort:
                    this.caF.IA();
                    return;
                case R.id.tvLocalResMgrDel:
                    this.caF.UD();
                    r.ck().K_umengEvent(hlx.data.tongji.a.bNf);
                    return;
                case R.id.tvLocalResMgrImport:
                    hlx.ui.a.G(this.caF.mActivity);
                    r.ck().K_umengEvent(hlx.data.tongji.a.bNk);
                    return;
                case R.id.tvLocalResMgrRecoverDefault:
                    this.caF.UH();
                    this.caF.cay.setVisibility(8);
                    return;
                case R.id.tvNoFilesTips:
                    hlx.ui.a.bZ(this.caF.mActivity);
                    return;
                default:
                    return;
            }
        }
    };

    public static LocResJSFragment Ux() {
        return new LocResJSFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ce(true);
    }

    public void onResume() {
        super.onResume();
        Uz();
    }

    public void onDestroy() {
        super.onDestroy();
        ce(false);
        if (this.caD != null) {
            AsyncTaskCenter.getInstance().cancel(this.caD);
            this.caD = null;
        }
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

    public void onActivityCreated(@z Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void Sw() {
        this.caj = new JsResMgrItemAdapter(this.mActivity, this.arrayList);
        this.caj.a((a) this);
    }

    private void sJ() {
        this.cal = (TextView) this.Pu.findViewById(R.id.tvLocalResMgrRecoverDefault);
        this.cal.setText(this.mActivity.getString(R.string.local_resmgr_js_tips));
        this.cal.setVisibility(0);
        this.cal.setOnClickListener(this.mClickListener);
        this.can = (TextView) this.Pu.findViewById(R.id.tvNoFilesTips);
        this.can.setText(R.string.TipMCNoneJs);
        this.can.setOnClickListener(this.mClickListener);
        this.aZh = (ListView) this.Pu.findViewById(R.id.lvLocalResMgr);
        this.aZh.setAdapter(this.caj);
        this.caj.S(this.akJ);
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

    private void IB() {
        CommonMenuDialogListener mSkinMenuListener = new CommonMenuDialogListener(this) {
            final /* synthetic */ LocResJSFragment caF;

            {
                this.caF = this$0;
            }

            public void pressMenuById(int inIndex, Object inItem) {
                this.caF.cak = inIndex;
                this.caF.Uy();
                this.caF.UC();
                this.caF.caC.dismissDialog();
            }
        };
        this.bbA.add(new ResMenuItem(this.mActivity.getString(R.string.local_resmgr_sort_name), 0, R.color.locmgr_unfocus_btn_color));
        this.bbA.add(new ResMenuItem(this.mActivity.getString(R.string.local_resmgr_sort_time), 1, R.color.locmgr_unfocus_btn_color));
        this.bbA.add(new ResMenuItem(this.mActivity.getString(R.string.local_resmgr_open), 2, R.color.locmgr_unfocus_btn_color));
        this.bbA.add(new ResMenuItem(this.mActivity.getString(R.string.local_resmgr_unopen), 3, R.color.locmgr_unfocus_btn_color));
        this.caC = new CommonMenuDialog(this.mActivity, this.bbA, mSkinMenuListener, d.RB());
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
                this.cam.setText(this.mActivity.getString(R.string.local_resmgr_open));
                return;
            case 3:
                this.cam.setText(this.mActivity.getString(R.string.local_resmgr_unopen));
                return;
            default:
                return;
        }
    }

    private void Uz() {
        AsyncTaskCenter.getInstance().execute(this.caD, new RunnableCallback(this) {
            final /* synthetic */ LocResJSFragment caF;

            {
                this.caF = this$0;
            }

            public void onCallback() {
                this.caF.getActivity().runOnUiThread(new Runnable(this) {
                    final /* synthetic */ AnonymousClass3 caG;

                    {
                        this.caG = this$1;
                    }

                    public void run() {
                        this.caG.caF.UB();
                    }
                });
            }
        });
    }

    private void UA() {
        boolean haveOpenItem = false;
        if (this.caB != null) {
            for (int i = 0; i < this.caB.size(); i++) {
                b _tmpItem = (b) this.caB.get(i);
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

    private void UB() {
        G(this.caB);
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
        this.caj.notifyDataSetChanged();
    }

    public void UC() {
        this.arrayList.clear();
        this.caj.notifyDataSetChanged();
        Uz();
    }

    private void IA() {
        this.caC.updateCurFocusIndex(this.cak);
        this.caC.showMenu(null, null);
    }

    private void UD() {
        this.bYS = true;
        this.caj.dI(this.bYS);
        this.caA.setVisibility(0);
        this.caz.setVisibility(8);
    }

    private void UE() {
        this.bYS = false;
        this.caj.dI(this.bYS);
        this.caA.setVisibility(8);
        this.caz.setVisibility(0);
    }

    private void UF() {
        for (b item : this.akJ) {
            hlx.ui.localresmgr.cache.b.Us().p(item);
        }
        this.bYS = false;
        this.caj.dI(this.bYS);
        this.caA.setVisibility(8);
        this.caz.setVisibility(0);
        UC();
    }

    private void UG() {
        this.caj.Ud();
    }

    public void aL(String name, String path) {
        String zipPath = j.Kt() + name + ".zip";
        String filePath = j.cT(true) + name + hlx.data.localstore.a.bJY;
        String filePathIni = j.cT(true) + name + hlx.data.localstore.a.bKb;
        j.deleteFile(filePath);
        j.deleteFile(zipPath);
        j.deleteFile(filePathIni);
    }

    private void UH() {
        Iterator it = this.arrayList.iterator();
        while (it.hasNext()) {
            b _tmp = (b) it.next();
            if (_tmp.state == 1) {
                _tmp.state = 0;
                ai.f("js", _tmp.name, ai.bmi, String.valueOf(_tmp.state));
            }
        }
        this.caj.notifyDataSetChanged();
    }

    public void ce(boolean init) {
        if (init) {
            i.n(this.caE);
        } else {
            i.unregisterReceiver(this.caE);
        }
    }

    public void dJ(boolean bool) {
        Uz();
        UA();
    }
}
