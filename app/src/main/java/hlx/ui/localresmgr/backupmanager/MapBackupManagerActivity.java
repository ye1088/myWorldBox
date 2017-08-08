package hlx.ui.localresmgr.backupmanager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.MCWorld.data.map.b;
import com.MCWorld.framework.R;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.ui.base.HTBaseLoadingActivity;
import com.MCWorld.utils.UtilsFile;
import com.MCWorld.utils.ai;
import com.MCWorld.utils.j;
import hlx.ui.localresmgr.module.a;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class MapBackupManagerActivity extends HTBaseLoadingActivity {
    private OnItemClickListener Pz = new OnItemClickListener(this) {
        final /* synthetic */ MapBackupManagerActivity bZt;

        {
            this.bZt = this$0;
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            if (this.bZt.bZr.Um() == 1) {
                ((a) this.bZt.aab.get(position)).UY();
                this.bZt.bZr.notifyDataSetChanged();
                return;
            }
            hlx.ui.a.R(this.bZt.SE, ((a) this.bZt.aab.get(position)).UX());
        }
    };
    private Activity SE;
    private List<a> aab;
    private ListView bZo;
    private TextView bZp;
    private LinearLayout bZq;
    private MapBackupManagerListAdapter bZr;
    private List<String> bZs;
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ MapBackupManagerActivity bZt;

        {
            this.bZt = this$0;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tvMapBackupMgrDel:
                    this.bZt.bZp.setVisibility(8);
                    this.bZt.Uk();
                    this.bZt.bZq.setVisibility(0);
                    this.bZt.bZr.nB(1);
                    return;
                case R.id.tvConfirmDel:
                    this.bZt.bZp.setVisibility(0);
                    this.bZt.bZq.setVisibility(8);
                    this.bZt.bZr.nB(0);
                    this.bZt.Uj();
                    return;
                case R.id.tvConfirmCancle:
                    this.bZt.bZp.setVisibility(0);
                    this.bZt.bZq.setVisibility(8);
                    this.bZt.bZr.nB(0);
                    return;
                case R.id.tvConfirmAll:
                    this.bZt.Ul();
                    return;
                default:
                    return;
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.aty_local_res_map_backup_manager);
        Sw();
        sJ();
        Fy();
    }

    protected int AN() {
        return R.style.McAppTheme;
    }

    protected int AO() {
        return R.style.McAppTheme.Night;
    }

    protected void onResume() {
        super.onResume();
        Gy();
    }

    private void Sw() {
        this.SE = this;
        this.aab = new ArrayList();
        this.bZs = new ArrayList();
        this.bZr = new MapBackupManagerListAdapter(this.SE);
    }

    private void sJ() {
        ej("备份管理");
        this.bZo = (ListView) findViewById(R.id.lvLocalResMapBackupManager);
        this.bZo.setAdapter(this.bZr);
        this.bZo.setOnItemClickListener(this.Pz);
        findViewById(R.id.vMapBackupManagerSplit).setVisibility(0);
        findViewById(R.id.llyMapBackupManager).setVisibility(0);
        this.bZp = (TextView) findViewById(R.id.tvMapBackupMgrDel);
        this.bZp.setOnClickListener(this.mClickListener);
        this.bZq = (LinearLayout) findViewById(R.id.llyMapBackupMgrConfirmBox);
        findViewById(R.id.tvConfirmCancle).setOnClickListener(this.mClickListener);
        findViewById(R.id.tvConfirmDel).setOnClickListener(this.mClickListener);
        findViewById(R.id.tvConfirmAll).setOnClickListener(this.mClickListener);
    }

    private void Gy() {
        this.bZs.clear();
        this.aab.clear();
        File[] _Files = new File(j.cV(false)).listFiles();
        if (_Files != null && _Files.length != 0) {
            try {
                for (File _tmpFile : _Files) {
                    String _tmp;
                    if (_tmpFile.getName().toLowerCase().endsWith(".zip")) {
                        _tmp = _tmpFile.getName();
                        _tmp = _tmp.substring(0, _tmp.length() - 4);
                        if (!this.bZs.contains(_tmp)) {
                            this.bZs.add(_tmp);
                            a(_tmp, ai.l(_tmp, true));
                        }
                    } else if (_tmpFile.isDirectory()) {
                        _tmp = _tmpFile.getName();
                        if (!this.bZs.contains(_tmp)) {
                            this.bZs.add(_tmp);
                            a(_tmp, ai.l(_tmp, true));
                        }
                    }
                }
            } catch (Exception e) {
                HLog.verbose("Error", e.getMessage(), new Object[0]);
            }
            this.bZr.setData(this.aab);
            cs(false);
            FC();
        }
    }

    private void a(String mapName, ArrayList<b> fmList) {
        long alterLastTime = 0;
        String _tmpStrTime = "";
        Calendar cal = Calendar.getInstance();
        if (fmList != null && fmList.size() > 0) {
            a _tmpItem = new a();
            _tmpItem.hI(mapName);
            _tmpItem.hG(((b) fmList.get(0)).pR);
            Iterator it = fmList.iterator();
            while (it.hasNext()) {
                long _time = new File(((b) it.next()).path).lastModified();
                if (_time > alterLastTime) {
                    alterLastTime = _time;
                }
            }
            cal.setTimeInMillis(alterLastTime);
            _tmpItem.hH(cal.getTime().toLocaleString());
            _tmpItem.nG(fmList.size());
            this.aab.add(_tmpItem);
        }
    }

    private void Uj() {
        String _tmpPath = ai.Md();
        for (a item : this.aab) {
            if (item.isDel()) {
                String tmpMapName = item.UX();
                UtilsFile.deleteFile(_tmpPath + File.separator + tmpMapName + ".zip");
                UtilsFile.deleteFile(_tmpPath + File.separator + tmpMapName);
            }
        }
        Gy();
    }

    private void Uk() {
        for (a tmpItem : this.aab) {
            tmpItem.dM(false);
        }
    }

    private void Ul() {
        if (this.aab != null) {
            boolean bHaveOneIsNotDelStatus = false;
            for (a tmpItem : this.aab) {
                if (!tmpItem.isDel()) {
                    bHaveOneIsNotDelStatus = true;
                    break;
                }
            }
            if (bHaveOneIsNotDelStatus) {
                for (a tmpItem2 : this.aab) {
                    tmpItem2.dM(true);
                }
            } else {
                for (a tmpItem22 : this.aab) {
                    tmpItem22.dM(false);
                }
            }
            this.bZr.notifyDataSetChanged();
        }
    }

    private String hv(String mapFolderName) {
        String mMCMapPath = UtilsFile.Kq() + mapFolderName;
        if (UtilsFile.isExist(mMCMapPath + File.separator + "level.dat")) {
            String realleveldat = mMCMapPath + File.separator + hlx.data.localstore.a.bKd;
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
