package com.tencent.mm.sdk.platformtools;

import android.os.Process;
import java.io.File;
import java.util.Scanner;

public final class TrafficStats {
    public static final String DEV_FILE = "/proc/self/net/dev";
    public static final String GPRSLINE = "rmnet0";
    public static final String WIFILINE = "tiwlan0";
    private static long br;
    private static long bs;
    private static long bt;
    private static long bu;
    private static long bv;
    private static long bw;
    private static long bx;
    private static long by;

    private TrafficStats() {
    }

    public static long getMobileRx(long j) {
        return bw > j ? bw : j;
    }

    public static long getMobileTx(long j) {
        return bv > j ? bv : j;
    }

    public static long getWifiRx(long j) {
        return by > j ? by : j;
    }

    public static long getWifiTx(long j) {
        return bx > j ? bx : j;
    }

    public static void reset() {
        br = -1;
        bs = -1;
        bt = -1;
        bu = -1;
        update();
    }

    public static void update() {
        long j = 0;
        long j2 = 0;
        long j3 = 0;
        long j4 = 0;
        try {
            Scanner scanner = new Scanner(new File("/proc/" + Process.myPid() + "/net/dev"));
            scanner.nextLine();
            scanner.nextLine();
            while (scanner.hasNext()) {
                String[] split = scanner.nextLine().split("[ :\t]+");
                int i = split[0].length() == 0 ? 1 : 0;
                if (!split[0].equals("lo") && split[i + 0].startsWith("rmnet")) {
                    j += Long.parseLong(split[i + 9]);
                    j2 += Long.parseLong(split[i + 1]);
                }
                if (!(split[i + 0].equals("lo") || split[i + 0].startsWith("rmnet"))) {
                    j3 += Long.parseLong(split[i + 9]);
                    j4 += Long.parseLong(split[i + 1]);
                }
            }
            scanner.close();
            if (br < 0) {
                br = j;
                Log.v("MicroMsg.SDK.TrafficStats", "fix loss newMobileTx %d", Long.valueOf(j));
            }
            if (bs < 0) {
                bs = j2;
                Log.v("MicroMsg.SDK.TrafficStats", "fix loss newMobileRx %d", Long.valueOf(j2));
            }
            if (bt < 0) {
                bt = j3;
                Log.v("MicroMsg.SDK.TrafficStats", "fix loss newWifiTx %d", Long.valueOf(j3));
            }
            if (bu < 0) {
                bu = j4;
                Log.v("MicroMsg.SDK.TrafficStats", "fix loss newWifiRx %d", Long.valueOf(j4));
            }
            if (j4 - bu < 0) {
                Log.v("MicroMsg.SDK.TrafficStats", "minu %d", Long.valueOf(j4 - bu));
            }
            if (j3 - bt < 0) {
                Log.v("MicroMsg.SDK.TrafficStats", "minu %d", Long.valueOf(j3 - bt));
            }
            bv = j >= br ? j - br : j;
            bw = j2 >= bs ? j2 - bs : j2;
            bx = j3 >= bt ? j3 - bt : j3;
            by = j4 >= bu ? j4 - bu : j4;
            br = j;
            bs = j2;
            bt = j3;
            bu = j4;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("MicroMsg.SDK.TrafficStats", "current system traffic: wifi rx/tx=%d/%d, mobile rx/tx=%d/%d", Long.valueOf(by), Long.valueOf(bx), Long.valueOf(bw), Long.valueOf(bv));
    }

    public static long updateMobileRx(long j) {
        update();
        return getMobileRx(j);
    }

    public static long updateMobileTx(long j) {
        update();
        return getMobileTx(j);
    }

    public static long updateWifiRx(long j) {
        update();
        return getWifiRx(j);
    }

    public static long updateWifiTx(long j) {
        update();
        return getWifiTx(j);
    }
}
