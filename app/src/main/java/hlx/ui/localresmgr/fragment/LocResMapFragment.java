package hlx.ui.localresmgr.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.z;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.huluxia.framework.R;
import com.huluxia.framework.base.async.AsyncTaskCenter;
import com.huluxia.framework.base.async.AsyncTaskCenter.RunnableCallback;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.CommonMenuDialogListener;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.ResMenuItem;
import com.huluxia.mojang.Mojang;
import com.huluxia.mojang.MojangMessage;
import com.huluxia.r;
import com.huluxia.service.i;
import com.huluxia.t;
import com.huluxia.ui.base.BaseLoadingFragment;
import com.huluxia.utils.UtilsFile;
import com.huluxia.utils.ai;
import com.huluxia.widget.Constants.DownFileType;
import com.huluxia.widget.dialog.e;
import com.huluxia.widget.dialog.g;
import com.huluxia.widget.dialog.k;
import com.simple.colorful.d;
import hlx.ui.localresmgr.adapter.MapResMgrItemAdapter;
import java.util.ArrayList;
import java.util.List;

public class LocResMapFragment extends BaseLoadingFragment implements hlx.ui.localresmgr.adapter.MapResMgrItemAdapter.a {
    private static final String TAG = "LocResMapFragment";
    private static final int akH = 0;
    private static final int caH = 1;
    private static final int caI = 1;
    private View Pu;
    private Runnable akA = new Runnable(this) {
        final /* synthetic */ LocResMapFragment caO;

        {
            this.caO = this$0;
        }

        public void run() {
            try {
                this.caO.ako = hlx.ui.localresmgr.cache.b.Us().nF(this.caO.cak);
            } catch (Exception e) {
            }
        }
    };
    private int akI = 0;
    private List<Object> akJ = new ArrayList();
    private ArrayList<Object> akW = new ArrayList();
    private List<com.huluxia.data.map.b> ako;
    private k bSg;
    private ArrayList<Object> bbA = new ArrayList();
    private LinearLayout caA;
    private BroadcastReceiver caE = new BroadcastReceiver(this) {
        final /* synthetic */ LocResMapFragment caO;

        {
            this.caO = this$0;
        }

        public void onReceive(Context context, Intent intent) {
            if (DownFileType.defaultType.Value() == intent.getIntExtra("type", 0)) {
                this.caO.UL();
            }
        }
    };
    private MapResMgrItemAdapter caJ = null;
    private k caK;
    private a caL;
    private com.huluxia.data.map.b caM;
    private CommonMenuDialog caN;
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
    private LinearLayout caz;
    private Activity mActivity;
    private CallbackHandler mCallback = new 2(this);
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ LocResMapFragment caO;

        {
            this.caO = this$0;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tvConfirmDel:
                    this.caO.UF();
                    return;
                case R.id.tvConfirmCancle:
                    this.caO.UK();
                    return;
                case R.id.tvConfirmAll:
                    this.caO.UG();
                    return;
                case R.id.tvLocalResMgrSort:
                    this.caO.IA();
                    return;
                case R.id.tvLocalResMgrBackupManager:
                    hlx.ui.a.J(this.caO.mActivity);
                    r.ck().K(hlx.data.tongji.a.bNi);
                    return;
                case R.id.tvLocalResMgrDel:
                    this.caO.UD();
                    r.ck().K(hlx.data.tongji.a.bNe);
                    return;
                case R.id.tvLocalResMgrImport:
                    hlx.ui.a.F(this.caO.mActivity);
                    r.ck().K(hlx.data.tongji.a.bNj);
                    return;
                case R.id.tvNoFilesTips:
                    hlx.ui.a.bY(this.caO.mActivity);
                    return;
                default:
                    return;
            }
        }
    };
    private ListView mListView;

    public class ProcessRecoverMapTask extends AsyncTask<String, Integer, String> {
        final /* synthetic */ LocResMapFragment caO;

        public ProcessRecoverMapTask(LocResMapFragment this$0) {
            this.caO = this$0;
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return c((String[]) objArr);
        }

        protected /* synthetic */ void onPostExecute(Object obj) {
            ce((String) obj);
        }

        protected void onPreExecute() {
            this.caO.caK.show();
            this.caO.caK.setCancelable(false);
        }

        protected String c(String... params) {
            String retVal = "success";
            try {
                ai.a(params[0], params[1], this.caO.mActivity);
                return retVal;
            } catch (Exception e) {
                e.printStackTrace();
                return String.format("还原地图失败! , 错误原因:%s", new Object[]{e.getMessage()});
            }
        }

        protected void ce(String result) {
            this.caO.caK.cancel();
            if (result.equals("success")) {
                t.o(this.caO.mActivity.getApplicationContext(), this.caO.mActivity.getString(R.string.map_recover_success));
            } else {
                t.n(this.caO.mActivity.getApplicationContext(), result);
            }
            r.ck().j("recover_map", result.equals("success") ? "success" : "fail");
        }
    }

    public class a extends Dialog {
        private ListView aZh;
        final /* synthetic */ LocResMapFragment caO;
        private MapResMgrItemAdapter caQ;
        private Context context;
        private String title;

        public a(LocResMapFragment this$0, Context context, String title) {
            this.caO = this$0;
            super(context, 16973840);
            this.context = context;
            this.title = title;
        }

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        public void show() {
            getWindow().setGravity(17);
            super.show();
        }

        public void U(List<com.huluxia.data.map.b> items) {
            View layout = ((LayoutInflater) this.context.getSystemService("layout_inflater")).inflate(R.layout.dialog_menu_img, (ViewGroup) findViewById(R.id.dialog_layout_root));
            setContentView(layout);
            ((TextView) layout.findViewById(R.id.dialog_menu_title)).setText(this.title);
            this.aZh = (ListView) layout.findViewById(R.id.listview);
            List<Object> args = new ArrayList();
            args.addAll(items);
            this.caQ = new MapResMgrItemAdapter(getContext(), args);
            this.caQ.hG(1);
            this.caQ.a(this.caO);
            this.aZh.setAdapter(this.caQ);
            layout.setOnTouchListener(new OnTouchListener(this) {
                final /* synthetic */ a caR;

                {
                    this.caR = this$1;
                }

                public boolean onTouch(View v, MotionEvent event) {
                    this.caR.dismiss();
                    return false;
                }
            });
        }
    }

    private class b implements com.huluxia.widget.dialog.e.a {
        final /* synthetic */ LocResMapFragment caO;

        private b(LocResMapFragment locResMapFragment) {
            this.caO = locResMapFragment;
        }

        public void rb() {
        }

        public void cf(String msg) {
            if (msg.trim().length() >= 1) {
                String levelName = null;
                if (!(Mojang.instance().getLevel() == null || Mojang.instance().getLevel().getLevelName() == null)) {
                    levelName = Mojang.instance().getLevel().getLevelName();
                }
                if (levelName == null) {
                    t.show_toast(this.caO.mActivity, this.caO.mActivity.getString(R.string.local_resmgr_notexist_objectfile_updatefails));
                } else if (Mojang.instance().getWorldItem() == null) {
                    t.show_toast(this.caO.mActivity, this.caO.mActivity.getString(R.string.local_resmgr_notexist_objectfile_updatefails));
                } else {
                    try {
                        if (!levelName.equals(msg)) {
                            Mojang.instance().renameGame(msg);
                            hlx.ui.localresmgr.cache.b.Us().a(this.caO.caM, this.caO.caM.path, msg);
                        }
                        this.caO.UL();
                    } catch (Exception e) {
                        t.show_toast(this.caO.mActivity, this.caO.mActivity.getString(R.string.local_resmgr_update_filename_fails));
                    }
                }
            }
        }
    }

    public static LocResMapFragment UI() {
        return new LocResMapFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ce(true);
    }

    public void onResume() {
        super.onResume();
        AL();
    }

    public void onDestroy() {
        super.onDestroy();
        ce(false);
        AsyncTaskCenter.getInstance().cancel(this.akA);
        this.akA = null;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.Pu = inflater.inflate(R.layout.lyt_home_localresmgr, container, false);
        this.mActivity = getActivity();
        Sw();
        sJ();
        this.caJ.a((hlx.ui.localresmgr.adapter.MapResMgrItemAdapter.a) this);
        cq(false);
        cr(false);
        return this.Pu;
    }

    public void onActivityCreated(@z Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void ce(boolean init) {
        if (init) {
            i.n(this.caE);
            EventNotifyCenter.add(MojangMessage.class, this.mCallback);
            return;
        }
        i.unregisterReceiver(this.caE);
        EventNotifyCenter.remove(this.mCallback);
    }

    public void UJ() {
        if (Mojang.instance().getLevel() != null) {
            e dialog = new e(this.mActivity, new b());
            dialog.aA(this.mActivity.getString(R.string.local_resmgr_dlg_cancle), this.mActivity.getString(R.string.local_resmgr_dlg_save));
            dialog.setText(Mojang.instance().getLevel().getLevelName());
            dialog.showDialog();
        }
    }

    private void AL() {
        AsyncTaskCenter.getInstance().execute(this.akA, new RunnableCallback(this) {
            final /* synthetic */ LocResMapFragment caO;

            {
                this.caO = this$0;
            }

            public void onCallback() {
                this.caO.getActivity().runOnUiThread(new Runnable(this) {
                    final /* synthetic */ AnonymousClass3 caP;

                    {
                        this.caP = this$1;
                    }

                    public void run() {
                        this.caP.caO.AM();
                    }
                });
            }
        });
    }

    private void AM() {
        G(this.ako);
    }

    public void G(List<com.huluxia.data.map.b> data) {
        if (data == null || data.size() == 0) {
            this.can.setVisibility(0);
            this.mListView.setVisibility(8);
            return;
        }
        this.can.setVisibility(8);
        this.mListView.setVisibility(0);
        this.akW.clear();
        this.akW.addAll(data);
        this.caJ.notifyDataSetChanged();
    }

    private void Sw() {
        this.caJ = new MapResMgrItemAdapter(getContext(), this.akW);
        this.bSg = new k(getContext());
        this.caK = new k(getContext());
    }

    private void sJ() {
        this.can = (TextView) this.Pu.findViewById(R.id.tvNoFilesTips);
        this.can.setText(R.string.TipMCNoneMap);
        this.can.setOnClickListener(this.mClickListener);
        this.mListView = (ListView) this.Pu.findViewById(R.id.lvLocalResMgr);
        this.mListView.setAdapter(this.caJ);
        this.caJ.S(this.akJ);
        this.cao = (TextView) this.Pu.findViewById(R.id.tvLocalResMgrDel);
        this.cao.setOnClickListener(this.mClickListener);
        this.cap = (TextView) this.Pu.findViewById(R.id.tvLocalResMgrBackup);
        this.cap.setOnClickListener(this.mClickListener);
        this.Pu.findViewById(R.id.tvLocalResMgrBackupManager).setVisibility(0);
        this.Pu.findViewById(R.id.tvLocalResMgrBackupManager).setOnClickListener(this.mClickListener);
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
        ((LinearLayout) this.Pu.findViewById(R.id.ly_headButton)).setVisibility(8);
        IB();
        Uy();
    }

    private void IB() {
        CommonMenuDialogListener mSkinMenuListener = new CommonMenuDialogListener(this) {
            final /* synthetic */ LocResMapFragment caO;

            {
                this.caO = this$0;
            }

            public void pressMenuById(int inIndex, Object inItem) {
                this.caO.cak = inIndex;
                this.caO.Uy();
                this.caO.UL();
                this.caO.caN.dismissDialog();
            }
        };
        this.bbA.add(new ResMenuItem(this.mActivity.getString(R.string.local_resmgr_sort_name), 0, R.color.locmgr_unfocus_btn_color));
        this.bbA.add(new ResMenuItem(this.mActivity.getString(R.string.local_resmgr_sort_time), 1, R.color.locmgr_unfocus_btn_color));
        this.bbA.add(new ResMenuItem(this.mActivity.getString(R.string.local_resmgr_sort_size), 2, R.color.locmgr_unfocus_btn_color));
        this.caN = new CommonMenuDialog(this.mActivity, this.bbA, mSkinMenuListener, d.RB());
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

    private void IA() {
        this.caN.updateCurFocusIndex(this.cak);
        this.caN.showMenu(null, null);
    }

    private void UD() {
        this.akI = 1;
        this.caJ.nB(this.akI);
        this.caA.setVisibility(0);
        this.caz.setVisibility(8);
    }

    private void UK() {
        this.akI = 0;
        this.caJ.nB(this.akI);
        this.caA.setVisibility(8);
        this.caz.setVisibility(0);
    }

    private void UF() {
        for (com.huluxia.data.map.b item : this.akJ) {
            hlx.ui.localresmgr.cache.b.Us().q(item);
        }
        this.akI = 0;
        this.caJ.nB(this.akI);
        this.caA.setVisibility(8);
        this.caz.setVisibility(0);
        UL();
    }

    private void UG() {
        this.caJ.Ud();
    }

    public void UL() {
        this.akW.clear();
        this.caJ.notifyDataSetChanged();
        AL();
    }

    public void l(com.huluxia.data.map.b item) {
        v(item);
    }

    public void Ug() {
        AL();
    }

    public void m(final com.huluxia.data.map.b item) {
        String srcName = item.name;
        e dialog = new e(this.mActivity, new com.huluxia.widget.dialog.e.a(this) {
            final /* synthetic */ LocResMapFragment caO;

            public void rb() {
            }

            public void cf(String msg) {
                hlx.ui.localresmgr.cache.b.Us().a(item, msg);
                this.caO.UL();
            }
        });
        dialog.aA(this.mActivity.getString(R.string.local_resmgr_dlg_cancle), this.mActivity.getString(R.string.local_resmgr_dlg_save));
        int i = 1;
        while (i < 1000) {
            String showName = item.name + "(" + this.mActivity.getString(R.string.backup) + String.valueOf(i) + ")";
            if (UtilsFile.isExist(UtilsFile.Kq() + showName)) {
                i++;
            } else {
                dialog.setText(showName);
                dialog.showDialog();
                return;
            }
        }
    }

    public void ht(String name) {
        com.huluxia.k.d(this.mActivity, UtilsFile.Kq() + name, name);
    }

    public void aH(String name, String mapGameDir) {
        ArrayList<com.huluxia.data.map.b> fmList = ai.l(name, true);
        if (fmList.size() < 1) {
            t.show_toast(this.mActivity, this.mActivity.getString(R.string.local_resmgr_backup_onfloatwindows_tips));
            return;
        }
        for (int i = 0; i < fmList.size(); i++) {
            com.huluxia.data.map.b fm = (com.huluxia.data.map.b) fmList.get(i);
            fm.pP = mapGameDir;
            fmList.set(i, fm);
        }
        this.caL = new a(this, this.mActivity, String.format("%s--选择备份", new Object[]{name}));
        this.caL.U(fmList);
        this.caL.show();
    }

    public void aI(String name, String path) {
        this.caL.dismiss();
        String[] arrayOfString = new String[]{name, path};
        this.caK.gM(this.mActivity.getString(R.string.local_resmgr_recovering_map));
        new ProcessRecoverMapTask(this).execute(arrayOfString);
    }

    public void l(long topic_id, long map_id) {
        com.huluxia.k.b(this.mActivity, map_id, topic_id);
    }

    public int hu(String name) {
        return ai.l(name, false).size();
    }

    private void hF(String name) {
        g dia = new g(this.mActivity, null);
        dia.az(this.mActivity.getString(R.string.prompt), name + this.mActivity.getString(R.string.file_notcomplete));
        dia.u(null, null, this.mActivity.getString(R.string.confirm));
    }

    private boolean v(com.huluxia.data.map.b item) {
        if (UtilsFile.isExist(UtilsFile.eQ(item.name)) || UtilsFile.isExist(UtilsFile.fS(item.name))) {
            this.bSg.gM(this.mActivity.getString(R.string.map_reading));
            if (!this.mActivity.isFinishing()) {
                this.bSg.show();
            }
            this.caM = item;
            try {
                Mojang.instance().init(item.name, 0, null);
                return true;
            } catch (Exception e) {
                if (this.bSg.isShowing()) {
                    this.bSg.cancel();
                }
                hF(item.name);
                return false;
            }
        }
        hF(item.name);
        return false;
    }
}
