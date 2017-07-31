package hlx.ui.redpacket;

import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.PendingIntent.CanceledException;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@SuppressLint({"NewApi"})
public class RedpacketService extends AccessibilityService {
    private static final boolean DEBUG = false;
    static final String TAG = "QiangHongBao";
    private static long UL = 0;
    private static final long UM = 1000;
    private static boolean cfB = false;
    private static final int cfC = 10;
    private static int cfD = 0;
    private static boolean cfE = false;
    private static ArrayList<AccessibilityNodeInfo> cfF = new ArrayList(10);
    private static ArrayList<AccessibilityNodeInfo> cfG = new ArrayList(10);
    static final String cfH = "com.tencent.mm";
    static final String cfI = "[微信红包]";
    static final String cfJ = "[QQ红包]";
    private static final int cfL = 1;
    private boolean cfK = false;
    protected Handler cfM = new Handler(this) {
        final /* synthetic */ RedpacketService cfN;

        {
            this.cfN = this$0;
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    long curTimeMillis = System.currentTimeMillis();
                    if (curTimeMillis > RedpacketService.VN() + 1000) {
                        RedpacketService.bP(curTimeMillis);
                        this.cfN.dQ(true);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    };
    Handler handler = new Handler();

    public static void X(List<AccessibilityNodeInfo> inputWxList) {
        if (inputWxList != null && inputWxList.size() <= 10) {
            if (a.VJ().VK() || a.VJ().VL()) {
                cfF.clear();
                for (int i = 0; i < inputWxList.size(); i++) {
                    cfF.add(inputWxList.get(i));
                }
            }
        }
    }

    private static boolean a(AccessibilityNodeInfo inputItem) {
        for (int i = 0; i < cfF.size(); i++) {
            if (((AccessibilityNodeInfo) cfF.get(i)).equals(inputItem)) {
                return true;
            }
        }
        return false;
    }

    public static boolean Y(List<AccessibilityNodeInfo> inputWxList) {
        if (inputWxList == null || inputWxList.size() > 10) {
            return false;
        }
        for (int i = 0; i < inputWxList.size(); i++) {
            if (!a((AccessibilityNodeInfo) inputWxList.get(i))) {
                return true;
            }
        }
        return false;
    }

    public static void Z(List<AccessibilityNodeInfo> inputQQList) {
        if (inputQQList != null && inputQQList.size() <= 10) {
            if (a.VJ().VK() || a.VJ().VL()) {
                cfG.clear();
                for (int i = 0; i < inputQQList.size(); i++) {
                    cfG.add(inputQQList.get(i));
                }
            }
        }
    }

    private static boolean b(AccessibilityNodeInfo inputItem) {
        for (int i = 0; i < cfG.size(); i++) {
            if (((AccessibilityNodeInfo) cfG.get(i)).equals(inputItem)) {
                return true;
            }
        }
        return false;
    }

    public static boolean aa(List<AccessibilityNodeInfo> inputQQList) {
        if (inputQQList == null || inputQQList.size() > 10) {
            return false;
        }
        for (int i = 0; i < inputQQList.size(); i++) {
            if (!b((AccessibilityNodeInfo) inputQQList.get(i))) {
                return true;
            }
        }
        return false;
    }

    public void onAccessibilityEvent(AccessibilityEvent event) {
        int eventType = event.getEventType();
        if (eventType == 64) {
            List<CharSequence> texts = event.getText();
            if (!texts.isEmpty()) {
                for (CharSequence t : texts) {
                    String text = String.valueOf(t);
                    if (!text.contains(cfI)) {
                        if (text.contains(cfJ)) {
                        }
                    }
                    cfG.clear();
                    a(event);
                    cfB = true;
                    cfE = true;
                    cfD = 0;
                    return;
                }
            }
        } else if (eventType == 1) {
            cfD = 2;
            if (cfE) {
                b(event);
            }
        } else if (eventType == 32) {
            b(event);
        } else {
            this.cfM.sendMessage(this.cfM.obtainMessage(1));
        }
    }

    public void onInterrupt() {
        Toast.makeText(this, "中断抢红包服务", 0).show();
    }

    protected void onServiceConnected() {
        super.onServiceConnected();
        Toast.makeText(this, "连接抢红包服务", 0).show();
    }

    @TargetApi(16)
    private void a(AccessibilityEvent event) {
        if (event.getParcelableData() != null && (event.getParcelableData() instanceof Notification)) {
            try {
                ((Notification) event.getParcelableData()).contentIntent.send();
            } catch (CanceledException e) {
                e.printStackTrace();
            }
        }
    }

    @TargetApi(16)
    private void b(AccessibilityEvent event) {
        CharSequence className = event.getClassName();
        cm(getApplicationContext());
        if ("com.tencent.mm.plugin.luckymoney.ui.LuckyMoneyReceiveUI".equals(className)) {
            VM();
        } else if (!"com.tencent.mm.ui.LauncherUI".equals(className) && !"com.tencent.mobileqq.activity.ChatActivity".equals(className)) {
            dQ(true);
        } else if (cfB) {
            dQ(false);
        } else {
            dQ(true);
        }
    }

    private void cm(Context context) {
        if (!((PowerManager) context.getSystemService("power")).isScreenOn()) {
            cn(context);
        }
    }

    private void cn(Context context) {
        ((KeyguardManager) context.getSystemService("keyguard")).newKeyguardLock("unLock").disableKeyguard();
        WakeLock wl = ((PowerManager) context.getSystemService("power")).newWakeLock(268435462, "bright");
        wl.acquire();
        wl.release();
    }

    @TargetApi(16)
    private void VM() {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo == null) {
            System.out.println("DTPrint checkKey1 001 \n");
            return;
        }
        List<AccessibilityNodeInfo> list = nodeInfo.findAccessibilityNodeInfosByText("拆红包");
        if (list == null || list.size() == 0) {
            list = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/b43");
        }
        for (AccessibilityNodeInfo n : list) {
            n.performAction(16);
        }
    }

    @TargetApi(16)
    private void dQ(boolean bCheckRedpacketCntFlag) {
        AccessibilityNodeInfo nodeInfo = getRootInActiveWindow();
        if (nodeInfo == null) {
            Log.w(TAG, "rootWindow为空");
            return;
        }
        List<AccessibilityNodeInfo> wxList = nodeInfo.findAccessibilityNodeInfosByText("领取红包");
        List<AccessibilityNodeInfo> qqList = nodeInfo.findAccessibilityNodeInfosByText("QQ红包");
        if (true == cfB) {
            X(wxList);
            cfB = false;
        }
        if (!qqList.isEmpty()) {
            qqList = nodeInfo.findAccessibilityNodeInfosByText("QQ红包");
            if (bCheckRedpacketCntFlag) {
                if (aa(qqList)) {
                    Z(qqList);
                    if (!cfE) {
                        return;
                    }
                }
                cfE = false;
                return;
            }
            Iterator it = qqList.iterator();
            if (it.hasNext()) {
                AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo) it.next();
            }
        }
        int i;
        AccessibilityNodeInfo parent;
        if (!wxList.isEmpty()) {
            cfE = true;
            if (a.VJ().VK() || a.VJ().VL()) {
                int totalCount = wxList.size();
                if (bCheckRedpacketCntFlag) {
                    if (Y(wxList)) {
                        X(wxList);
                    } else {
                        return;
                    }
                }
                for (i = totalCount - 1; i >= 0; i--) {
                    parent = ((AccessibilityNodeInfo) wxList.get(i)).getParent();
                    if (parent != null) {
                        parent.performAction(16);
                        return;
                    }
                }
            }
        } else if (!qqList.isEmpty()) {
            if (a.VJ().VK() || a.VJ().VL()) {
                for (i = qqList.size() - 1; i >= 0; i--) {
                    parent = ((AccessibilityNodeInfo) qqList.get(i)).getParent();
                    if (parent != null) {
                        parent.performAction(16);
                        return;
                    }
                }
            }
        }
    }

    public static long VN() {
        return UL;
    }

    public static void bP(long nPerMirTickCallBack) {
        UL = nPerMirTickCallBack;
    }

    public static void nN(int nMSeconds) {
        try {
            Thread.currentThread();
            Thread.sleep((long) nMSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
