package com.MCWorld.mcgame;

import com.MCWorld.mcinterface.j;
import com.MCWorld.mcsdk.DTSDKManagerEx;

/* compiled from: DTMapItem */
public class q {
    public static void a(j inputMCMapItem) {
        try {
            int coor_x = (int) inputMCMapItem.zW();
            int coor_y = (int) inputMCMapItem.zX();
            int coor_z = (int) inputMCMapItem.zY();
            int itemId = DTSDKManagerEx.C(coor_x, coor_y, coor_z);
            int itemDmg = DTSDKManagerEx.D(coor_x, coor_y, coor_z);
            inputMCMapItem.setId(itemId);
            inputMCMapItem.cT(itemDmg);
        } catch (Exception e) {
            inputMCMapItem.setId(0);
            inputMCMapItem.cT(0);
        }
    }

    public static void b(j inputMCMapItem) {
        try {
            DTSDKManagerEx.l((int) inputMCMapItem.zW(), (int) inputMCMapItem.zX(), (int) inputMCMapItem.zY(), inputMCMapItem.getId(), inputMCMapItem.ql());
        } catch (Exception e) {
        }
    }

    public static void c(j inputMCMapItem) {
        try {
            if (DTSDKManagerEx.C((int) inputMCMapItem.zW(), (int) inputMCMapItem.zX(), (int) inputMCMapItem.zY()) >= 0) {
                DTSDKManagerEx.l((int) inputMCMapItem.zW(), (int) inputMCMapItem.zX(), (int) inputMCMapItem.zY(), 0, 0);
            }
        } catch (Exception e) {
        }
    }
}
