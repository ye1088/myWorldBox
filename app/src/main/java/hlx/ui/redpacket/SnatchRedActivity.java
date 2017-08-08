package hlx.ui.redpacket;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.MCWorld.framework.R;
import com.MCWorld.r;
import com.MCWorld.utils.ah;
import hlx.data.localstore.a;
import java.util.List;

public class SnatchRedActivity extends Activity {
    private OnCheckedChangeListener QH = new OnCheckedChangeListener(this) {
        final /* synthetic */ SnatchRedActivity cfS;

        {
            this.cfS = this$0;
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            switch (buttonView.getId()) {
                case R.id.snatchRed:
                    ah.KZ().k(a.bLw, isChecked);
                    a.VJ().g(isChecked, isChecked);
                    if (!this.cfS.cfR && isChecked) {
                        this.cfS.VO();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };
    private Activity SE;
    private TextView cfO;
    private CheckBox cfP;
    private LinearLayout cfQ;
    private boolean cfR = false;
    private OnClickListener mClickListener = new OnClickListener(this) {
        final /* synthetic */ SnatchRedActivity cfS;

        {
            this.cfS = this$0;
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ivSnatchRedBack:
                    this.cfS.finish();
                    return;
                case R.id.tvStartSnatch:
                    r.ck().K_umengEvent(hlx.data.tongji.a.bOp);
                    this.cfS.VO();
                    return;
                case R.id.llySnatchRedDetail:
                    hlx.ui.a.cj(this.cfS.SE);
                    return;
                default:
                    return;
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_snatch_red);
        this.SE = this;
        r.ck().K_umengEvent(hlx.data.tongji.a.bOo);
        sJ();
    }

    public static boolean d(Context context, int checkServiceNumber, String serviceClassName) {
        List<RunningServiceInfo> mServiceList = ((ActivityManager) context.getSystemService("activity")).getRunningServices(checkServiceNumber);
        for (int i = 0; i < mServiceList.size(); i++) {
            if (serviceClassName.equals(((RunningServiceInfo) mServiceList.get(i)).service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    private void sJ() {
        findViewById(R.id.ivSnatchRedBack).setOnClickListener(this.mClickListener);
        this.cfO = (TextView) findViewById(R.id.tvStartSnatch);
        this.cfP = (CheckBox) findViewById(R.id.snatchRed);
        this.cfO.setOnClickListener(this.mClickListener);
        boolean isOpenSnatchRed = ah.KZ().j(a.bLw, false);
        this.cfP.setChecked(isOpenSnatchRed);
        a.VJ().g(isOpenSnatchRed, isOpenSnatchRed);
        this.cfP.setOnCheckedChangeListener(this.QH);
        this.cfQ = (LinearLayout) findViewById(R.id.llySnatchRedDetail);
        this.cfQ.setOnClickListener(this.mClickListener);
    }

    private void VO() {
        try {
            startActivity(new Intent("android.settings.ACCESSIBILITY_SETTINGS"));
            Toast.makeText(this.SE, "找到“盒子红包助手”，然后开启服务即可", 1).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void onResume() {
        super.onResume();
        this.cfR = d(this.SE, 100, "hlx.ui.redpacket.RedpacketService");
        this.cfO.setText(this.cfR ? "已设置" : "去开启");
        if (!this.cfR) {
            this.cfP.setChecked(false);
        }
    }

    public static void VP() {
        boolean isOpenSnatchRed = ah.KZ().j(a.bLw, false);
        if (isOpenSnatchRed) {
            r.ck().K_umengEvent(hlx.data.tongji.a.bOq);
        } else {
            r.ck().K_umengEvent(hlx.data.tongji.a.bOr);
        }
        a.VJ().g(isOpenSnatchRed, isOpenSnatchRed);
    }
}
