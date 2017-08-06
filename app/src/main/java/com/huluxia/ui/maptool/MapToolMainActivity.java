package com.huluxia.ui.maptool;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.huluxia.framework.R;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.huluxia.mcmap.c;
import com.huluxia.mojang.Mojang;
import com.huluxia.mojang.MojangMessage;
import com.huluxia.mojang.WorldItem;
import com.huluxia.mojang.util.Vector3f;
import com.huluxia.t;
import com.huluxia.utils.UtilsFile;
import com.huluxia.utils.ai;
import com.huluxia.utils.aj;
import com.huluxia.utils.j;
import com.huluxia.widget.ImageSwitch;
import com.huluxia.widget.TextImageButton;
import com.huluxia.widget.dialog.e;
import com.huluxia.widget.dialog.k_dialog_class;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MapToolMainActivity extends Activity {
    private static final boolean alz = false;
    private OnCheckedChangeListener QH = new OnCheckedChangeListener(this) {
        final /* synthetic */ MapToolMainActivity baF;

        {
            this.baF = this$0;
        }

        public void onCheckedChanged(CompoundButton checkbox, boolean checked) {
            switch (checkbox.getId()) {
                case R.id.mapConfigChkFloatWindow:
                    Toast.makeText(this.baF.mContext, "响应：悬浮窗（游戏内修改）", 1).show();
                    return;
                case R.id.mapConfigChkFlyMode:
                    try {
                        com.huluxia.mcmap.a.cb(checked);
                        return;
                    } catch (Exception e) {
                        Toast.makeText(this.baF.mContext, "飞行模式异常", 1).show();
                        return;
                    }
                case R.id.mapConfigChkViewMode:
                    try {
                        com.huluxia.mcmap.a.cc(checked);
                        return;
                    } catch (Exception e2) {
                        Toast.makeText(this.baF.mContext, "视角模式异常", 1).show();
                        return;
                    }
                case R.id.mapConfigChkInvincibleMode:
                    try {
                        com.huluxia.mcmap.a.cd(checked);
                        return;
                    } catch (Exception e3) {
                        Toast.makeText(this.baF.mContext, "无敌模式异常", 1).show();
                        return;
                    }
                default:
                    return;
            }
        }
    };
    private k_dialog_class aRr;
    private SimpleDateFormat aVq = new SimpleDateFormat(hlx.data.localstore.a.bKc);
    private ImageButton aZZ;
    private e baA;
    private CallbackHandler baB = new CallbackHandler(this) {
        final /* synthetic */ MapToolMainActivity baF;

        {
            this.baF = this$0;
        }

        @MessageHandler(message = 264)
        public void onRecvOptions() {
            HLog.verbose(this, "onRecvOptions", new Object[0]);
        }

        @MessageHandler(message = 265)
        public void onRecvLevelData(boolean succ) {
            if (this.baF.aRr.isShowing()) {
                this.baF.aRr.cancel();
            }
            if (succ) {
                HLog.verbose(this, "onRecvLevelData", new Object[0]);
                this.baF.In();
                this.baF.Iq();
            }
        }
    };
    private OnClickListener baC = new OnClickListener(this) {
        final /* synthetic */ MapToolMainActivity baF;

        {
            this.baF = this$0;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mapConfigTexGameMode:
                    int i;
                    MapToolMainActivity mapToolMainActivity = this.baF;
                    if (this.baF.baf.isFirst()) {
                        i = 0;
                    } else {
                        i = 1;
                    }
                    mapToolMainActivity.bav = i;
                    try {
                        com.huluxia.mcmap.a.setGameMode(this.baF.bav);
                    } catch (Exception e) {
                        Toast.makeText(this.baF.mContext, "游戏模式异常", 1).show();
                    }
                    int _tmpGameMode = com.huluxia.mcmap.a.getGameMode();
                    boolean _tmpTimeMode = com.huluxia.mcmap.a.AD();
                    return;
                case R.id.mapConfigTexTimeMode:
                    this.baF.baw = this.baF.bag.isFirst();
                    try {
                        com.huluxia.mcmap.a.ca(this.baF.baw);
                        return;
                    } catch (Exception e2) {
                        Toast.makeText(this.baF.mContext, "时间模式异常", 1).show();
                        return;
                    }
                default:
                    return;
            }
        }
    };
    private OnClickListener baD = new OnClickListener(this) {
        final /* synthetic */ MapToolMainActivity baF;

        {
            this.baF = this$0;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mapConfigBarCloseButton:
                    this.baF.finish();
                    return;
                case R.id.mapConfigSwitchMap:
                    a _mapListDialog = new a(this.baF.mActivity, new a());
                    return;
                case R.id.gameBegin:
                    Toast.makeText(this.baF.mContext, "响应：开始游戏", 1).show();
                    return;
                default:
                    return;
            }
        }
    };
    private OnClickListener baE = new OnClickListener(this) {
        final /* synthetic */ MapToolMainActivity baF;

        {
            this.baF = this$0;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mapConfigTexRepairStartBlankScreen:
                    this.baF.bf(this.baF.mContext);
                    return;
                case R.id.mapConfigTexLevelExperienceAlter:
                    this.baF.bg(this.baF.mContext);
                    return;
                case R.id.mapConfigTexBackpack:
                    Toast.makeText(this.baF.mContext, "响应：背包", 1).show();
                    c.aR(this.baF);
                    return;
                case R.id.mapConfigTexAnimal:
                    c.aS(this.baF);
                    return;
                case R.id.mapConfigTexRebirthPlace:
                    Toast.makeText(this.baF.mContext, "响应：重生点", 1).show();
                    try {
                        Mojang.instance().getSpawnPoint(new Vector3f());
                        HLog.verbose("TAG", "DTPrint getSpawnPoint is [%f,%f,%f]\n", Float.valueOf(_tmpSpawnPoint.x), Float.valueOf(_tmpSpawnPoint.y), Float.valueOf(_tmpSpawnPoint.z));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    try {
                        Mojang.instance().setSpawnPoint(123, 123, 123);
                        return;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return;
                    }
                case R.id.mapConfigTexMapName:
                    this.baF.baA = new e(this.baF.mActivity, new b());
                    this.baF.baA.aA(hlx.data.localstore.a.bKB_bt_cancel, "保存");
                    this.baF.baA.setText(Mojang.instance().getLevel().getLevelName());
                    this.baF.baA.showDialog();
                    return;
                default:
                    return;
            }
        }
    };
    private TextView baa;
    private TextView bab;
    private TextView bac;
    private TextView bad;
    private TextView bae;
    private ImageSwitch baf;
    private ImageSwitch bag;
    private CheckBox bah;
    private TextImageButton bai;
    private TextImageButton baj;
    private CheckBox bak;
    private CheckBox bal;
    private CheckBox bam;
    private TextImageButton ban;
    private TextImageButton bao;
    private TextImageButton bap;
    private TextImageButton baq;
    private LinearLayout bar;
    private boolean bas = false;
    private String bat;
    private String bau;
    private int bav = 0;
    private boolean baw = true;
    private boolean bax;
    private boolean bay;
    private boolean baz;
    private Activity mActivity;
    private Context mContext;
    private String qk;

    private class a implements a.c {
        final /* synthetic */ MapToolMainActivity baF;

        private a(MapToolMainActivity mapToolMainActivity) {
            this.baF = mapToolMainActivity;
        }

        public void m(String mapName, String time, String size) {
            this.baF.bat = mapName;
            this.baF.qk = time;
            this.baF.bau = size;
            this.baF.In();
            this.baF.Iq();
        }
    }

    private class b implements com.huluxia.widget.dialog.e.a {
        final /* synthetic */ MapToolMainActivity baF;

        private b(MapToolMainActivity mapToolMainActivity) {
            this.baF = mapToolMainActivity;
        }

        public void rb() {
        }

        public void cf(String msg) {
            if (msg.trim().length() >= 1) {
                this.baF.eE(msg);
            }
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        cQ(false);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localmap_main_activity);
        aj.Mu();
        this.aRr = new k_dialog_class(this);
        Ip();
        Ir();
        cQ(true);
        Im();
    }

    public void cQ(boolean init) {
        if (init) {
            EventNotifyCenter.add(MojangMessage.class, this.baB);
        } else {
            EventNotifyCenter.remove(this.baB);
        }
    }

    private void Im() {
        try {
            this.mContext = this;
            this.mActivity = this;
            this.bat = getIntent().getStringExtra("mapName");
            this.aRr.gM_setText("地图读取中...");
            boolean bReadMapOK = com.huluxia.mcmap.a.di(this.bat);
            if (!this.mActivity.isFinishing()) {
                this.aRr.show();
            }
            Io();
            if (!bReadMapOK && this.aRr.isShowing()) {
                this.aRr.cancel();
            }
        } catch (Exception e) {
            if (this.aRr.isShowing()) {
                this.aRr.cancel();
            }
        }
    }

    private void In() {
        this.bav = com.huluxia.mcmap.a.getGameMode();
        this.baw = com.huluxia.mcmap.a.AD();
        this.bax = com.huluxia.mcmap.a.AE();
        this.bay = com.huluxia.mcmap.a.AF();
        this.baz = com.huluxia.mcmap.a.AG();
    }

    private void Io() {
        File _file = new File(UtilsFile.Kq() + File.separator + this.bat);
        if (UtilsFile.isExist(_file.getPath() + File.separator + "level.dat")) {
            String _date = this.aVq.format(new Date(_file.lastModified()));
            long _size = j.getFileSizes(_file);
            this.qk = _date;
            this.bau = String.valueOf(_size % 1048576 == 0 ? _size / 1048576 : (_size / 1048576) + 1) + "MB";
        }
    }

    private void Ip() {
        this.aZZ = (ImageButton) findViewById(R.id.mapConfigBarCloseButton);
        this.baa = (TextView) findViewById(R.id.mapConfigTitleMapName);
        this.bab = (TextView) findViewById(R.id.mapConfigSwitchMap);
        this.bac = (TextView) findViewById(R.id.mapConfigCurrentMapName);
        this.bad = (TextView) findViewById(R.id.mapConfigTexTime);
        this.bae = (TextView) findViewById(R.id.mapConfigTexSize);
        this.baf = (ImageSwitch) findViewById(R.id.mapConfigTexGameMode);
        this.bag = (ImageSwitch) findViewById(R.id.mapConfigTexTimeMode);
        this.baf.g(R.drawable.ic_mode_create, R.drawable.ic_mode_survive, this.bav == 0);
        this.bag.g(R.drawable.ic_time_day, R.drawable.ic_time_night, this.baw);
        this.bah = (CheckBox) findViewById(R.id.mapConfigChkFloatWindow);
        this.bai = (TextImageButton) findViewById(R.id.mapConfigTexRepairStartBlankScreen);
        this.baj = (TextImageButton) findViewById(R.id.mapConfigTexLevelExperienceAlter);
        this.bak = (CheckBox) findViewById(R.id.mapConfigChkFlyMode);
        this.bal = (CheckBox) findViewById(R.id.mapConfigChkViewMode);
        this.bam = (CheckBox) findViewById(R.id.mapConfigChkInvincibleMode);
        this.ban = (TextImageButton) findViewById(R.id.mapConfigTexBackpack);
        this.bao = (TextImageButton) findViewById(R.id.mapConfigTexAnimal);
        this.bap = (TextImageButton) findViewById(R.id.mapConfigTexRebirthPlace);
        this.baq = (TextImageButton) findViewById(R.id.mapConfigTexMapName);
        this.bar = (LinearLayout) findViewById(R.id.gameBegin);
    }

    private void Iq() {
        this.baa.setText(this.bat);
        this.bac.setText(this.bat);
        this.bad.setText(this.qk);
        this.bae.setText(this.bau);
        this.baf.setFirstFlag(this.bav != 0);
        this.bag.setFirstFlag(this.baw);
        this.bak.setChecked(this.bax);
        this.bal.setChecked(this.bay);
        this.bam.setChecked(this.baz);
    }

    private void Ir() {
        this.aZZ.setOnClickListener(this.baD);
        this.bab.setOnClickListener(this.baD);
        this.baf.setOnClickListener(this.baC);
        this.bag.setOnClickListener(this.baC);
        this.bah.setOnCheckedChangeListener(this.QH);
        this.bai.setOnClickListener(this.baE);
        this.baj.setOnClickListener(this.baE);
        this.bak.setOnCheckedChangeListener(this.QH);
        this.bal.setOnCheckedChangeListener(this.QH);
        this.bam.setOnCheckedChangeListener(this.QH);
        this.ban.setOnClickListener(this.baE);
        this.bao.setOnClickListener(this.baE);
        this.bap.setOnClickListener(this.baE);
        this.baq.setOnClickListener(this.baE);
        this.bar.setOnClickListener(this.baD);
    }

    public void bf(Context context) {
    }

    public void bg(Context context) {
    }

    private void eE(String mapName) {
        String levelName = null;
        if (!(Mojang.instance().getLevel() == null || Mojang.instance().getLevel().getLevelName() == null)) {
            levelName = Mojang.instance().getLevel().getLevelName();
        }
        if (levelName == null) {
            t.show_toast(this.mActivity, "目标文件不存在，无法更新");
            return;
        }
        WorldItem item = Mojang.instance().getWorldItem();
        if (item == null) {
            t.show_toast(this.mActivity, "目标文件不存在，无法更新");
            return;
        }
        String from = UtilsFile.Kq() + item.getFileName();
        String to = UtilsFile.Kq() + mapName;
        if (!levelName.equals(mapName)) {
            try {
                Mojang.instance().renameGame(mapName);
            } catch (Exception e) {
                HLog.error(hlx.data.localstore.a.bKf, "renameMap error", e, new Object[0]);
                t.show_toast(this.mActivity, "更新存档名出错");
                return;
            }
        }
        if (UtilsFile.rename(from, to)) {
            String realleveldat = to + File.separator + hlx.data.localstore.a.bKd;
            if (UtilsFile.isExist(realleveldat)) {
                try {
                    FileWriter f = new FileWriter(realleveldat);
                    f.write(mapName);
                    f.close();
                    UtilsFile.rename(ai.Md() + File.separator + item.getFileName(), ai.Md() + File.separator + mapName);
                } catch (IOException e2) {
                }
            }
        }
        this.bat = mapName;
        this.baa.setText(mapName);
        this.bac.setText(mapName);
    }
}
