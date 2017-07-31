package com.huluxia.mcgame;

import com.huluxia.mcsdk.DTSDKManagerEx;
import net.zhuoweizhang.mcpelauncher.ScriptManager;

/* compiled from: DTBagItem */
public class c {
    public static int fV(int in_bagIndex) {
        return DTSDKManagerEx.V(in_bagIndex, 0);
    }

    public static int fW(int in_bagIndex) {
        return DTSDKManagerEx.V(in_bagIndex, 1);
    }

    public static int fX(int in_bagIndex) {
        return DTSDKManagerEx.V(in_bagIndex, 2);
    }

    public static void a(int in_ItemId, int in_ItemDmg, int in_ItemCnt, int[] in_enchantAttr, int[] in_enchantLevel) {
        DTSDKManagerEx.e(in_ItemId, in_ItemDmg, in_ItemCnt, in_enchantAttr, in_enchantLevel);
    }

    public static void fY(int in_bagIndex) {
        DTSDKManagerEx.ir(in_bagIndex);
    }

    public static void o(int in_id, int in_cnt, int in_dmg) {
        DTSDKManagerEx.A(in_id, in_cnt, in_dmg);
    }

    public static void a(float pos_x, float pos_y, float pos_z, int biological_id, String name) {
        DTSDKManagerEx.b(pos_x, pos_y, pos_z, biological_id, name);
    }

    public static void a(float pos_x, float pos_y, float pos_z, int biological_id, String name, int inputMaxHealth) {
        long _tmpBiologicalId = DTSDKManagerEx.c(pos_x, pos_y, pos_z, biological_id, name);
        if (inputMaxHealth > 0) {
            DTSDKManagerEx.j(_tmpBiologicalId, inputMaxHealth);
            DTSDKManagerEx.i(_tmpBiologicalId, inputMaxHealth);
        }
    }

    public static void a(float pos_x, float pos_y, float pos_z, int biological_id, String inputName, int inputMaxHealth, int inputHat, int inputCoat, int inputTrousers, int inputShoes, int inputWeapon) {
        try {
            long _tmpBiologicalId = DTSDKManagerEx.c(pos_x, pos_y, pos_z, biological_id, null);
            if (inputMaxHealth > 0) {
                DTSDKManagerEx.j(_tmpBiologicalId, inputMaxHealth);
                DTSDKManagerEx.i(_tmpBiologicalId, inputMaxHealth);
            }
            if (inputHat > 0) {
                ScriptManager.nativeMobSetArmor(_tmpBiologicalId, 0, inputHat, 0);
            }
            if (inputCoat > 0) {
                ScriptManager.nativeMobSetArmor(_tmpBiologicalId, 1, inputCoat, 0);
            }
            if (inputTrousers > 0) {
                ScriptManager.nativeMobSetArmor(_tmpBiologicalId, 2, inputTrousers, 0);
            }
            if (inputShoes > 0) {
                ScriptManager.nativeMobSetArmor(_tmpBiologicalId, 3, inputShoes, 0);
            }
            if (inputWeapon > 0) {
                ScriptManager.nativeSetCarriedItem(_tmpBiologicalId, inputWeapon, 1, 0);
            }
            if (inputName != null) {
                ScriptManager.nativeEntitySetNameTag(_tmpBiologicalId, inputName);
            }
        } catch (Exception e) {
        }
    }

    public static int fZ(int index) {
        return DTSDKManagerEx.ac(index, 0);
    }

    public static void G(int index, int armor) {
        DTSDKManagerEx.B(index, armor, 0);
    }
}
