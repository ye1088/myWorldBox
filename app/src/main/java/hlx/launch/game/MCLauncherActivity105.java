package hlx.launch.game;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import com.huluxia.HTApplication;
import com.huluxia.mcfloat.enchant.e;
import com.huluxia.mcfloat.q;
import com.huluxia.mcinterface.h;
import com.huluxia.r;
import com.huluxia.utils.ah;
import com.huluxia.utils.ai;
import com.huluxia.utils.aj;
import com.huluxia.utils.j;
import com.huluxia.utils.o;
import com.mojang.minecraftpe.MainActivity;
import hlx.data.tongji.a;
import hlx.home.main.HomeActivity;
import hlx.mcfairy.b;
import java.io.File;
import org.bytedeco.javacpp.avformat;
import org.bytedeco.javacpp.swscale;

public class MCLauncherActivity105 extends MainActivity {
    private q bRb;
    private b bRc;

    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            St();
        }
        r.ck().di();
        aj.Mu();
        e.uy();
        h.zE();
        Sp();
        switch (c.Sg().Sl()) {
            case 0:
                r.ck().K(a.bOh);
                break;
            case 1:
                r.ck().K(a.bOi);
                break;
            case 2:
                r.ck().K(a.bOj);
                break;
        }
        So();
        Sq();
        if (c.Sg().Sh() != 7) {
            Sr();
        }
        Ss();
        super.onCreate(savedInstanceState);
    }

    public static void So() {
        String md5 = com.huluxia.utils.h.getMD5String(o.getDeviceId());
        h.cX(String.format("%x", new Object[]{Integer.valueOf(md5.hashCode())}));
    }

    private void Sp() {
        if (h.zq()) {
            com.huluxia.mcfloat.a.aN(this);
            this.bRb = new q(this, getWindow().getDecorView());
        }
        this.bRc = new b(this, ah.KZ().j(hlx.data.localstore.a.bLv, true));
    }

    private void Sq() {
        File[] _files = new File(j.cT(true)).listFiles();
        if (c.Sg().Sl() != 0 && _files != null) {
            for (File file : _files) {
                String _fileName = file.getName().toLowerCase();
                if (_fileName.endsWith(hlx.data.localstore.a.bJY)) {
                    String _resName = _fileName.substring(0, _fileName.indexOf(hlx.data.localstore.a.bJY));
                    String _ver = ai.q("js", _resName, ai.version);
                    String _state = ai.q("js", _resName, ai.bmi);
                    if (c.Sg().Sl() == 1 && c.d(_ver, c.Sg().Sh(), true) && _state.trim().equals("1")) {
                        r.ck().L(_resName);
                        h.dc(file.getPath());
                    } else if (c.Sg().Sl() == 2 && _state.trim().equals("1")) {
                        r.ck().L(_resName);
                        h.dc(file.getPath());
                    }
                }
            }
        }
    }

    private void Sr() {
        String _wood = ah.KZ().LJ();
        h.db(null);
        if (c.Sg().Sl() != 0 && _wood != null && j.isExist(_wood)) {
            String _woodName = j.eV(_wood);
            if (_woodName.endsWith(".zip")) {
                String _resName = _woodName.substring(0, _woodName.indexOf(".zip"));
                String _ver = ai.q("wood", _resName, ai.version);
                if (c.Sg().Sl() == 1 && c.d(_ver, c.Sg().Sh(), false)) {
                    r.ck().M(_resName);
                    h.db(_wood);
                } else if (c.Sg().Sl() == 2) {
                    r.ck().M(_resName);
                    h.db(_wood);
                }
            }
        }
    }

    private void Ss() {
        String _skin = ah.KZ().LI();
        h.da(null);
        if (c.Sg().Sl() != 0 && _skin != null && j.isExist(_skin)) {
            String _skinName = j.eV(_skin);
            if (_skinName.endsWith(hlx.data.localstore.a.bKa)) {
                String _resName = _skinName.substring(0, _skinName.indexOf(hlx.data.localstore.a.bKa));
                String _ver = ai.q("skin", _resName, ai.version);
                if (c.Sg().Sl() == 1 && c.d(_ver, c.Sg().Sh(), false)) {
                    r.ck().N(_resName);
                    h.da(_skin);
                } else if (c.Sg().Sl() == 2) {
                    r.ck().N(_resName);
                    h.da(_skin);
                }
            }
        }
    }

    private void St() {
        int i;
        int i2 = 1;
        if (c.Sg().Sh() == 4) {
            com.huluxia.mcsdk.dtlib.h.CW().iG(1);
            h.hq(3);
        } else if (c.Sg().Sh() == 5) {
            com.huluxia.mcsdk.dtlib.h.CW().iG(0);
            h.hq(7);
        } else {
            com.huluxia.mcsdk.dtlib.h.CW().iG(0);
            h.hq(c.Sg().Sh());
        }
        if (c.Sg().Si()) {
            i = 1;
        } else {
            i = 0;
        }
        h.hn(i);
        if (!c.Sg().Sj()) {
            i2 = 0;
        }
        h.ho(i2);
        h.bK(c.Sg().Sk());
        String mDataDataGamePackPath = j.m(c.Sg().Sh(), getApplicationContext().getFilesDir().getAbsolutePath());
        h.A(mDataDataGamePackPath, mDataDataGamePackPath + File.separator + "lib" + File.separator + "armeabi-v7a");
    }

    public void onPause() {
        super.onPause();
    }

    public void onDestroy() {
        r.ck().dj();
        try {
            this.bRb.recycle();
            this.bRc.recycle();
        } catch (Exception e) {
        }
        super.onDestroy();
    }

    public static void t(Context ctx, int flag) {
        Intent localIntent = ctx.getPackageManager().getLaunchIntentForPackage(ctx.getPackageName());
        localIntent.addFlags(avformat.AVFMT_SEEK_TO_PTS);
        localIntent.putExtra("restart", flag);
        ctx.startActivity(localIntent);
        Process.killProcess(Process.myPid());
    }

    public static void mJ(int flag) {
        Context ctx = HTApplication.getAppContext();
        Intent mStartActivity = new Intent(ctx, HomeActivity.class);
        mStartActivity.putExtra("restart", flag);
        ((AlarmManager) ctx.getSystemService("alarm")).set(1, System.currentTimeMillis() + 1500, PendingIntent.getActivity(ctx, swscale.SWS_PARAM_DEFAULT, mStartActivity, 268435456));
        System.exit(0);
    }
}
