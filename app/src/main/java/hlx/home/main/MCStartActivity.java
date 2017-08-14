package hlx.home.main;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.MCWorld.framework.R;
import com.MCWorld.mcinterface.h;
import com.MCWorld.utils.UtilsFile;
import com.MCWorld.widget.Constants.ReStartSoftFlag;
import hlx.launch.game.c;
import hlx.mcstorymode.e;
import java.lang.ref.WeakReference;

public class MCStartActivity extends Activity {
    protected Handler Vo;
    private Boolean bQQ = Boolean.valueOf(true);

    static class a extends Handler {
        WeakReference<MCStartActivity> akD;

        a(MCStartActivity activity) {
            this.akD = new WeakReference(activity);
        }

        public void handleMessage(Message msg) {
            MCStartActivity activity = (MCStartActivity) this.akD.get();
            if (activity != null) {
                switch (msg.what) {
                    case 1:
                        activity.bQQ = Boolean.valueOf(false);
                        activity.Sd();
                        return;
                    default:
                        return;
                }
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        this.Vo = new a(this);
        UtilsFile.Kx();
        UtilsFile.mkdir(e.TD());
        UtilsFile.mkdir(e.TE());
        h.gg(0);
        h.bj(false);
        McApplication.j(getIntent().getIntExtra("restart", 0));
        if (ReStartSoftFlag.MC_RESTART_NORMAL.Value() == McApplication.bA()) {
            setContentView(R.layout.activity_app_start);
            this.Vo.sendMessageDelayed(this.Vo.obtainMessage(1), 800);
        } else if (ReStartSoftFlag.MC_RESTART_BLACK.Value() == McApplication.bA()) {
            McApplication.j(0);
            setContentView(R.layout.activity_app_black);
            this.bQQ = Boolean.valueOf(false);
            Sd();
        } else {
            this.bQQ = Boolean.valueOf(false);
            Sc();
        }
    }

    protected void Sc() {
        hlx.ui.a.bV(this);
        finish();
    }

    protected void Sd() {
        c.Sg().mI(-1);
        if (-1 == c.Sg().Sh()) {
            hlx.ui.a.bW(this);
        } else {
            hlx.ui.a.bV(this);
        }
        finish();
    }

    protected void onResume() {
        super.onResume();
        if (!this.bQQ.booleanValue()) {
            Sc();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
    }
}
