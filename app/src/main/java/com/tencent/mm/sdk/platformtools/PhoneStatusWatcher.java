package com.tencent.mm.sdk.platformtools;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import com.huluxia.data.profile.a;
import java.util.LinkedList;
import java.util.List;

public class PhoneStatusWatcher {
    private static boolean aD;
    private TelephonyManager aE;
    private PhoneStateListener aF;
    private List<PhoneCallListener> aG = new LinkedList();

    public interface PhoneCallListener {
        void onPhoneCall(int i);
    }

    public static boolean isCalling() {
        return aD;
    }

    public void addPhoneCallListener(PhoneCallListener phoneCallListener) {
        this.aG.add(phoneCallListener);
    }

    public void begin(Context context) {
        if (this.aE == null) {
            this.aE = (TelephonyManager) context.getSystemService(a.qe);
        }
        if (this.aF == null) {
            this.aF = new PhoneStateListener(this) {
                final /* synthetic */ PhoneStatusWatcher aH;

                {
                    this.aH = r1;
                }

                public void onCallStateChanged(int i, String str) {
                    for (PhoneCallListener onPhoneCall : this.aH.aG) {
                        onPhoneCall.onPhoneCall(i);
                    }
                    super.onCallStateChanged(i, str);
                    switch (i) {
                        case 0:
                            PhoneStatusWatcher.aD = false;
                            return;
                        case 1:
                        case 2:
                            PhoneStatusWatcher.aD = true;
                            return;
                        default:
                            return;
                    }
                }
            };
        }
        this.aE.listen(this.aF, 32);
    }

    public void clearPhoneCallListener() {
        this.aG.clear();
    }

    public void end() {
        if (this.aE != null) {
            this.aE.listen(this.aF, 0);
            this.aF = null;
        }
    }

    public void removePhoneCallListener(PhoneCallListener phoneCallListener) {
        this.aG.remove(phoneCallListener);
    }
}
