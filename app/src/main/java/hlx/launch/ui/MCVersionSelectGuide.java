package hlx.launch.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.MCWorld.framework.R;
import com.MCWorld.r;
import com.MCWorld.t;
import com.MCWorld.utils.ah;
import hlx.data.localstore.a;
import hlx.launch.game.c;
import hlx.launch.game.d;

public class MCVersionSelectGuide extends Activity {
    private boolean bTp = false;
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ MCVersionSelectGuide bTq;

        {
            this.bTq = this$0;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnNewUserGuide:
                    ah.KZ().k(a.bLg, true);
                    t.aw(this.bTq.mContext);
                    r.ck().K_umengEvent(hlx.data.tongji.a.bLM);
                    this.bTq.bTp = true;
                    return;
                case R.id.tvGameVersionGuideSkip:
                    if (VERSION.SDK_INT < 11) {
                        c.Sg().setStartGameVersion(3);
                    } else {
                        c.Sg().setStartGameVersion(7);
                    }
                    hlx.ui.a.bV(this.bTq.mContext);
                    r.ck().K_umengEvent(hlx.data.tongji.a.bLN);
                    this.bTq.finish();
                    return;
                default:
                    return;
            }
        }
    };
    private Context mContext;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_version_select_guide);
        this.mContext = this;
        findViewById(R.id.btnNewUserGuide).setOnClickListener(this.mClickListener);
        findViewById(R.id.tvGameVersionGuideSkip).setOnClickListener(this.mClickListener);
    }

    protected void onResume() {
        super.onResume();
        if (this.bTp) {
            findViewById(R.id.btnNewUserGuide).setEnabled(false);
            findViewById(R.id.tvGameVersionGuideSkip).setEnabled(false);
            if (VERSION.SDK_INT < 11) {
                if (d.mK(3)) {
                    c.Sg().setStartGameVersion(3);
                }
            } else if (d.mK(5)) {
                c.Sg().setStartGameVersion(5);
            }
            hlx.ui.a.bV(this.mContext);
            finish();
        }
    }
}
