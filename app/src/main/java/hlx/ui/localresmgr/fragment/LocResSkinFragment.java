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
import com.huluxia.utils.j;
import com.huluxia.widget.Constants.DownFileType;
import com.simple.colorful.d;
import hlx.ui.localresmgr.adapter.SkinResMgrItemAdapter;
import hlx.ui.localresmgr.adapter.SkinResMgrItemAdapter.a;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LocResSkinFragment extends BaseLoadingFragment implements a {
    private static final String TAG = "LocResSkinFragment";
    private View Pu;
    private ListView aZh;
    private List<Object> akJ = new ArrayList();
    private ArrayList<Object> arrayList = new ArrayList();
    private boolean bYS = false;
    private ArrayList<Object> bbA = new ArrayList();
    private LinearLayout caA;
    private CommonMenuDialog caC;
    private BroadcastReceiver caE = new BroadcastReceiver(this) {
        final /* synthetic */ LocResSkinFragment caX;

        {
            this.caX = this$0;
        }

        public void onReceive(Context context, Intent intent) {
            if (DownFileType.Skin.Value() == intent.getIntExtra("type", 0)) {
                this.caX.UC();
            }
        }
    };
    private List<b> caS;
    private SkinResMgrItemAdapter caT = null;
    private TextView caU;
    private TextView caV;
    private Runnable caW = new Runnable(this) {
        final /* synthetic */ LocResSkinFragment caX;

        {
            this.caX = this$0;
        }

        public void run() {
            try {
                this.caX.caS = hlx.ui.localresmgr.cache.b.Us().nC(this.caX.cak);
            } catch (Exception e) {
            }
        }
    };
    private int cak = 1;
    private TextView cam;
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
        final /* synthetic */ LocResSkinFragment caX;

        {
            this.caX = this$0;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tvConfirmDel:
                    this.caX.UF();
                    return;
                case R.id.tvConfirmCancle:
                    this.caX.UE();
                    return;
                case R.id.tvConfirmAll:
                    this.caX.UG();
                    return;
                case R.id.tvLocalResMgrSort:
                    this.caX.IA();
                    return;
                case R.id.tvLocalResMgrDel:
                    this.caX.UD();
                    r.ck().K(hlx.data.tongji.a.bNh);
                    return;
                case R.id.tvLocalResMgrImport:
                    hlx.ui.a.I(this.caX.mActivity);
                    r.ck().K(hlx.data.tongji.a.bNm);
                    return;
                case R.id.tvLocalResMgrRecoverDefault:
                    this.caX.UN();
                    this.caX.cay.setVisibility(8);
                    return;
                case R.id.tvNoFilesTips:
                    hlx.ui.a.cb(this.caX.mActivity);
                    return;
                default:
                    return;
            }
        }
    };

    public static LocResSkinFragment UM() {
        return new LocResSkinFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ce(true);
    }

    public void onResume() {
        super.onResume();
        UO();
    }

    public void onDestroy() {
        super.onDestroy();
        ce(false);
        if (this.caW != null) {
            AsyncTaskCenter.getInstance().cancel(this.caW);
            this.caW = null;
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
        this.caT = new SkinResMgrItemAdapter(this.mActivity, this.arrayList);
        this.caT.a((a) this);
    }

    private void sJ() {
        this.caU = (TextView) this.Pu.findViewById(R.id.tvNoFilesTips);
        this.caU.setText(R.string.TipMCNoneSkin);
        this.caU.setOnClickListener(this.mClickListener);
        this.aZh = (ListView) this.Pu.findViewById(R.id.lvLocalResMgr);
        this.aZh.setAdapter(this.caT);
        this.caT.S(this.akJ);
        this.caV = (TextView) this.Pu.findViewById(R.id.tvLocalResMgrRecoverDefault);
        this.caV.setText("恢复默认皮肤");
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

    private void UN() {
        ah.KZ().gb(null);
        Iterator it = this.arrayList.iterator();
        while (it.hasNext()) {
            b _tmp = (b) it.next();
            if (_tmp.state == 1) {
                _tmp.state = 0;
            }
        }
        this.caT.notifyDataSetChanged();
        r.ck().K(hlx.data.tongji.a.bMZ);
    }

    private void UD() {
        this.bYS = true;
        this.caT.dI(this.bYS);
        this.caA.setVisibility(0);
        this.caz.setVisibility(8);
    }

    private void UE() {
        this.bYS = false;
        this.caT.dI(this.bYS);
        this.caA.setVisibility(8);
        this.caz.setVisibility(0);
    }

    private void UF() {
        for (b item : this.akJ) {
            hlx.ui.localresmgr.cache.b.Us().n(item);
        }
        this.bYS = false;
        this.caT.dI(this.bYS);
        this.caA.setVisibility(8);
        this.caz.setVisibility(0);
        UC();
    }

    private void UG() {
        this.caT.Ud();
    }

    public void aM(String name, String path) {
        j.deleteFile(j.Ku() + name + ".zip");
        j.deleteFile(j.cU(true) + name + hlx.data.localstore.a.bKa);
        j.deleteFile(j.cU(true) + name + hlx.data.localstore.a.bKb);
        String usingSkin = ah.KZ().LI();
        if (usingSkin != null && usingSkin.equalsIgnoreCase(path)) {
            ah.KZ().gb(null);
        }
    }

    private void IA() {
        this.caC.updateCurFocusIndex(this.cak);
        this.caC.showMenu(null, null);
    }

    private void IB() {
        CommonMenuDialogListener mSkinMenuListener = new CommonMenuDialogListener(this) {
            final /* synthetic */ LocResSkinFragment caX;

            {
                this.caX = this$0;
            }

            public void pressMenuById(int inIndex, Object inItem) {
                this.caX.cak = inIndex;
                this.caX.Uy();
                this.caX.UC();
                this.caX.caC.dismissDialog();
            }
        };
        this.bbA.add(new ResMenuItem(this.mActivity.getString(R.string.local_resmgr_sort_name), 0, R.color.locmgr_unfocus_btn_color));
        this.bbA.add(new ResMenuItem(this.mActivity.getString(R.string.local_resmgr_sort_time), 1, R.color.locmgr_unfocus_btn_color));
        this.bbA.add(new ResMenuItem(this.mActivity.getString(R.string.local_resmgr_sort_size), 2, R.color.locmgr_unfocus_btn_color));
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
                this.cam.setText(this.mActivity.getString(R.string.local_resmgr_sort_size));
                return;
            default:
                return;
        }
    }

    private void UO() {
        AsyncTaskCenter.getInstance().execute(this.caW, new RunnableCallback(this) {
            final /* synthetic */ LocResSkinFragment caX;

            {
                this.caX = this$0;
            }

            public void onCallback() {
                if (this.caX.getActivity() != null) {
                    this.caX.getActivity().runOnUiThread(new Runnable(this) {
                        final /* synthetic */ AnonymousClass3 caY;

                        {
                            this.caY = this$1;
                        }

                        public void run() {
                            this.caY.caX.UP();
                        }
                    });
                }
            }
        });
    }

    private void UP() {
        G(this.caS);
        UA();
    }

    public void G(List<b> data) {
        if (data == null || data.size() == 0) {
            this.caU.setVisibility(0);
            this.aZh.setVisibility(8);
            return;
        }
        this.caU.setVisibility(8);
        this.aZh.setVisibility(0);
        this.arrayList.clear();
        this.arrayList.addAll(data);
        this.caT.notifyDataSetChanged();
    }

    public void UC() {
        this.arrayList.clear();
        this.caT.notifyDataSetChanged();
        UO();
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
        if (this.caS != null) {
            for (int i = 0; i < this.caS.size(); i++) {
                b _tmpItem = (b) this.caS.get(i);
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

    public void dK(boolean bool) {
        UO();
        UA();
    }
}
