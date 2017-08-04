package hlx.launch.game;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.huluxia.framework.R;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.mcgame.g;
import com.huluxia.mcinterface.h;
import com.huluxia.r;
import com.huluxia.service.i;
import com.huluxia.t;
import com.huluxia.utils.ah;
import com.huluxia.utils.j;
import com.huluxia.utils.u;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;

public class MCPreLauncherActivity extends Activity {
    private static final boolean DEBUG = false;
    private static final String TAG = "MCPreLauncherActivity";
    private static final int bRe = 1;
    private static final int bRf = 2;
    private static final int bRg = 3;
    protected Handler Vo;
    private BroadcastReceiver aDe = new b();
    private Activity aMn;
    private String bRh;
    private String bRi;
    private String bRj = "";
    private boolean bRk = true;
    private View bRl;

    public class CheckGameDataAsnycTask extends AsyncTask<String, Integer, String> {
        final /* synthetic */ MCPreLauncherActivity bRm;

        public CheckGameDataAsnycTask(MCPreLauncherActivity this$0) {
            this.bRm = this$0;
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return c((String[]) objArr);
        }

        protected /* synthetic */ void onPostExecute(Object obj) {
            ce((String) obj);
        }

        protected void onPreExecute() {
        }

        protected String c(String... params) {
            if (h.cY(this.bRm.bRh)) {
                if (this.bRm.Vo != null) {
                    this.bRm.Vo.sendEmptyMessage(1);
                }
                return "success";
            }
            if (this.bRm.Vo != null) {
                this.bRm.Vo.sendEmptyMessage(2);
            }
            return "fails";
        }

        protected void ce(String result) {
        }
    }

    static class a extends Handler {
        WeakReference<MCPreLauncherActivity> akD;

        a(MCPreLauncherActivity activity) {
            this.akD = new WeakReference(activity);
        }

        public void handleMessage(Message msg) {
            MCPreLauncherActivity activity = (MCPreLauncherActivity) this.akD.get();
            if (activity != null) {
                switch (msg.what) {
                    case 1:
                        if (!activity.isFinishing()) {
                            activity.findViewById(R.id.llyLoading).setVisibility(8);
                            activity.findViewById(R.id.tvLoadOver).setVisibility(0);
                            activity.Vo.sendEmptyMessageDelayed(3, 200);
                            return;
                        }
                        return;
                    case 2:
                        if (!activity.isFinishing()) {
                            if (activity.bRk) {
                                activity.bRk = false;
                                i.i(activity.aDe);
                                com.huluxia.widget.h.NV().t("unzipGameZip", c.Sg().Sn(), activity.bRh + File.separator);
                                return;
                            }
                            t.n(activity, "抱歉，请切换版本重试.");
                            activity.finish();
                            return;
                        }
                        return;
                    case 3:
                        if (!activity.isFinishing()) {
                            h.A(activity.bRh, activity.bRj);
                            hlx.ui.a.b(activity, false);
                            activity.Sz();
                            activity.Sv().postDelayed(new 1(this, activity), 1000);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    }

    private class b extends BroadcastReceiver {
        final /* synthetic */ MCPreLauncherActivity bRm;

        private b(MCPreLauncherActivity mCPreLauncherActivity) {
            this.bRm = mCPreLauncherActivity;
        }

        public void onReceive(Context context, Intent intent) {
            String taskId = intent.getStringExtra("taskid");
            int status = intent.getIntExtra("success", 0);
            if (taskId != null && status == 1 && this.bRm.aMn != null && !this.bRm.aMn.isFinishing() && taskId.equals("unzipGameZip")) {
                this.bRm.CM();
                h.A(this.bRm.bRh, this.bRm.bRj);
                new CheckGameDataAsnycTask(this.bRm).execute(new String[0]);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_prelaungame);
        if (1 == ah.KZ().get_config_sp_intVal(g.adF, 0)) {
            ah.KZ().Q(hlx.data.localstore.a.bKr, 0);
        }
        TextView mGameTipsMarquee = (TextView) findViewById(R.id.tvGameTipsMarquee);
        mGameTipsMarquee.setVisibility(0);
        mGameTipsMarquee.setText(hlx.tips.a.D(this));
        mGameTipsMarquee.setSelected(true);
        if (c.Sg().Sh() == -1) {
            t.n(this.aMn, "抱歉！当前版本不合适启动，请切换版本！");
            finish();
        } else {
            Sw();
            Sx();
            if (c.Sg().Sh() == 7 || c.Sg().Sh() == 5) {
                this.Vo.sendEmptyMessage(1);
            } else {
                new CheckGameDataAsnycTask(this).execute(new String[0]);
            }
        }
        this.bRl = findViewById(R.id.black_surface);
    }

    public View Sv() {
        return this.bRl;
    }

    private void Sw() {
        this.aMn = this;
        this.bRi = this.aMn.getApplicationContext().getFilesDir().getAbsolutePath();
        this.bRh = j.m(c.Sg().Sh(), this.bRi);
        this.bRj = this.bRh + File.separator + "lib" + File.separator + "armeabi-v7a";
        this.Vo = new a(this);
    }

    private void Sx() {
        int i;
        int i2 = 1;
        Sy();
        mL(c.Sg().Sh());
        if (c.Sg().Sh() == 4) {
            com.huluxia.mcsdk.dtlib.h.CW().iG(1);
            h.hq(3);
        } else if (c.Sg().Sh() == 5) {
            com.huluxia.mcsdk.dtlib.h.CW().iG(0);
            h.hq(5);
            h.hp(1);
        } else if (c.Sg().Sh() == 7) {
            com.huluxia.mcsdk.dtlib.h.CW().iG(3);
            h.hq(7);
            h.hp(1);
        } else {
            com.huluxia.mcsdk.dtlib.h.CW().iG(0);
            h.hq(c.Sg().Sh());
        }
        if (c.Sg().Sj()) {
            i = 1;
        } else {
            i = 0;
        }
        h.ho(i);
        r.ck().K(c.Sg().Sj() ? hlx.data.tongji.a.bNW : hlx.data.tongji.a.bNX);
        if (!c.Sg().Si()) {
            i2 = 0;
        }
        h.hn(i2);
        r.ck().K(c.Sg().Si() ? hlx.data.tongji.a.bNU : hlx.data.tongji.a.bNV);
        h.bK(c.Sg().Sk());
        r.ck().K(c.Sg().Sk() ? hlx.data.tongji.a.bNY : hlx.data.tongji.a.bNZ);
        h.zE();
    }

    public void Sy() {
        if (u.bW("com.mojang.minecraftpe") != 0) {
            HLog.debug("Home", "mc is running", new Object[0]);
            u.dq("com.mojang.minecraftpe");
        }
    }

    private void mL(int chooseVersion) {
        String _gameresPath_V105 = j.m(0, this.bRi);
        String _gameresPath_V111 = j.m(1, this.bRi);
        String _gameresPath_V121 = j.m(2, this.bRi);
        String _gameresPath_V130 = j.m(3, this.bRi);
        String _gameresPath_V131 = j.m(4, this.bRi);
        String _gameresPath_V143 = j.m(7, this.bRi);
        String _gameresPath_V141 = j.m(5, this.bRi);
        if (chooseVersion != 0) {
            j.deleteFile(_gameresPath_V105);
        }
        if (1 != chooseVersion) {
            j.deleteFile(_gameresPath_V111);
        }
        if (2 != chooseVersion) {
            j.deleteFile(_gameresPath_V121);
        }
        if (3 != chooseVersion) {
            j.deleteFile(_gameresPath_V130);
        }
        if (4 != chooseVersion) {
            j.deleteFile(_gameresPath_V131);
        }
        if (7 != chooseVersion) {
            j.deleteFile(_gameresPath_V143);
        }
        if (5 != chooseVersion) {
            j.deleteFile(_gameresPath_V141);
        }
    }

    private void Sz() {
        String path = this.bRh + File.separator;
        j.deleteFile(path + "META-INF");
        j.deleteFile(path + "res");
        j.deleteFile(path + "AndroidManifest.xml");
        j.deleteFile(path + "classes.dex");
        j.deleteFile(path + "resources.arsc");
    }

    private void CM() {
        File file = new File(this.bRh + File.separator + "assets" + File.separator + "lang" + File.separator + "en_US.lang");
        try {
            InputStream in = this.aMn.getAssets().open("en_US.lang");
            FileOutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            while (true) {
                int length = in.read(buf);
                if (length != -1) {
                    out.write(buf, 0, length);
                } else {
                    out.flush();
                    in.close();
                    out.close();
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        com.huluxia.mcsdk.dtlib.h.aY(this.aMn.getApplicationContext());
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.Vo != null) {
            this.Vo.removeCallbacksAndMessages(null);
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ((AnimationDrawable) ((ImageView) findViewById(R.id.ivPreLaunchLoading)).getBackground()).start();
    }
}
