package hlx.ui.localresmgr.backupmanager;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import com.huluxia.data.map.b;
import com.huluxia.framework.R;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.CommonMenuDialogListener;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.ResMenuItem;
import com.huluxia.framework.base.widget.dialog.DialogManager;
import com.huluxia.framework.base.widget.dialog.DialogManager.OkCancelDialogListener;
import com.huluxia.t;
import com.huluxia.ui.base.HTBaseActivity;
import com.huluxia.utils.UtilsFile;
import com.huluxia.utils.ai;
import com.simple.colorful.d;
import hlx.data.localstore.a;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SpecifyMapBackupManagerActivity extends HTBaseActivity {
    private OnItemClickListener Pz = new OnItemClickListener(this) {
        final /* synthetic */ SpecifyMapBackupManagerActivity bZC;

        {
            this.bZC = this$0;
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            this.bZC.k((b) this.bZC.aab.get(position));
        }
    };
    private Activity SE;
    private List<b> aab;
    private DialogManager akK;
    private String bZA;
    private CommonMenuDialog bZB;
    private ListView bZo;
    private SpecifyMapBackupManagerListAdapter bZz;
    private String bat;
    private LayoutInflater mInflater = null;
    private ArrayList<Object> mMenuItemArrayList = new ArrayList();

    public class ProcessRecoverMapTask extends AsyncTask<String, Integer, String> {
        final /* synthetic */ SpecifyMapBackupManagerActivity bZC;

        public ProcessRecoverMapTask(SpecifyMapBackupManagerActivity this$0) {
            this.bZC = this$0;
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return c((String[]) objArr);
        }

        protected /* synthetic */ void onPostExecute(Object obj) {
            ce((String) obj);
        }

        protected void onPreExecute() {
            this.bZC.cs(true);
        }

        protected String c(String... params) {
            String retVal = "success";
            try {
                ai.a(params[0], params[1], this.bZC.SE);
                return retVal;
            } catch (Exception e) {
                e.printStackTrace();
                return String.format("还原地图失败! , 错误原因:%s", new Object[]{e.getMessage()});
            }
        }

        protected void ce(String result) {
            this.bZC.cs(false);
            if (result.equals("success")) {
                t.o(this.bZC.SE.getApplicationContext(), this.bZC.SE.getString(R.string.map_recover_success));
            } else {
                t.n(this.bZC.SE.getApplicationContext(), result);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.aty_local_res_map_backup_manager);
        this.mInflater = LayoutInflater.from(this);
        Sw();
        sJ();
        Gy();
        Uf();
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }

    private void j(b item) {
        int colorMsg = d.getColor(this.SE, R.attr.dialog_msg_label_color);
        int colorRed = this.SE.getResources().getColor(R.color.dialog_ok_btn_color);
        int colorGray = d.getColor(this.SE, 16842808);
        int colorTitle = d.getColor(this.SE, R.attr.dialog_title_label_color);
        View customView = this.mInflater.inflate(R.layout.dialog_delete_resource, null);
        TextView tip = (TextView) customView.findViewById(R.id.tv_tip);
        tip.setTextColor(colorMsg);
        tip.setText("该删除操作不可恢复，是否继续？");
        this.akK.showOkCancelColorDialog("温馨提示", colorTitle, customView, this.SE.getString(R.string.delete), colorRed, this.SE.getString(R.string.btn_cancel), colorGray, true, new OkCancelDialogListener(this) {
            final /* synthetic */ SpecifyMapBackupManagerActivity bZC;

            {
                this.bZC = this$0;
            }

            public void onCancel() {
                this.bZC.akK.dismissDialog();
            }

            public void onOk() {
                UtilsFile.deleteFile(this.bZC.bZA);
                this.bZC.Gy();
            }
        });
    }

    private void Uf() {
        this.akK = new DialogManager(this.SE);
        CommonMenuDialogListener mBackupMenuMenuListener = new CommonMenuDialogListener(this) {
            final /* synthetic */ SpecifyMapBackupManagerActivity bZC;

            {
                this.bZC = this$0;
            }

            public void pressMenuById(int inIndex, Object object) {
                b item = (b) object;
                switch (inIndex) {
                    case 6:
                        this.bZC.j(item);
                        this.bZC.bZB.dismissDialog();
                        return;
                    case 7:
                        new ProcessRecoverMapTask(this.bZC).execute(new String[]{UtilsFile.Kq() + this.bZC.bat, this.bZC.bZA});
                        this.bZC.bZB.dismissDialog();
                        return;
                    default:
                        return;
                }
            }
        };
        if (d.RB()) {
            this.mMenuItemArrayList.add(new ResMenuItem(this.SE.getString(R.string.MenuRecover), 7, R.color.locmgr_menu_res_red_color_night));
            this.mMenuItemArrayList.add(new ResMenuItem(this.SE.getString(R.string.MenuDel), 6, R.color.locmgr_menu_res_all_color_night));
        } else {
            this.mMenuItemArrayList.add(new ResMenuItem(this.SE.getString(R.string.MenuRecover), 7, R.color.locmgr_menu_res_red_color_day));
            this.mMenuItemArrayList.add(new ResMenuItem(this.SE.getString(R.string.MenuDel), 6, R.color.locmgr_menu_res_all_color_day));
        }
        this.bZB = new CommonMenuDialog(this.SE, this.mMenuItemArrayList, mBackupMenuMenuListener, d.RB());
    }

    private void k(b item) {
        this.bZA = item.path;
        this.bZB.showMenu(item, item.name);
    }

    private void Sw() {
        this.SE = this;
        this.bat = getIntent().getStringExtra("mapName");
        this.aab = new ArrayList();
        this.bZz = new SpecifyMapBackupManagerListAdapter(this.SE);
    }

    private void sJ() {
        ej(hv(this.bat));
        this.bZo = (ListView) findViewById(R.id.lvLocalResMapBackupManager);
        this.bZo.setAdapter(this.bZz);
        this.bZo.setOnItemClickListener(this.Pz);
    }

    private void Gy() {
        this.aab = ai.l(this.bat, true);
        this.bZz.setData(this.aab);
    }

    private String hv(String mapFolderName) {
        String mMCMapPath = UtilsFile.Kq() + mapFolderName;
        if (UtilsFile.isExist(mMCMapPath + File.separator + "level.dat")) {
            String realleveldat = mMCMapPath + File.separator + a.bKd;
            if (UtilsFile.isExist(realleveldat)) {
                try {
                    BufferedReader bf = new BufferedReader(new FileReader(realleveldat));
                    String fname = bf.readLine();
                    bf.close();
                    if (fname != null) {
                        mapFolderName = fname.trim();
                    }
                } catch (IOException e) {
                }
            }
        }
        return mapFolderName;
    }
}
