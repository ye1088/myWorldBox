package com.MCWorld.mcsdk.dtlib;

import com.MCWorld.mcgame.g;

public class DTThread extends Thread {
    public void run() {
        int leaveGameTickCount = 0;
        while (g.wa()) {
            try {
                if (g.vX() == 1) {
                    leaveGameTickCount++;
                    if (leaveGameTickCount >= 5) {
                        g.gl(0);
                        h.Dd();
                        return;
                    }
                }
                leaveGameTickCount = 0;
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
