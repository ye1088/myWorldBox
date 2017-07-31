package com.huluxia.mcfloat.potion;

import android.content.Context;
import com.huluxia.framework.R;
import java.util.ArrayList;
import java.util.List;

/* compiled from: PotionMcMap */
public class c {
    public static final int acA = 14;
    private static List<b> acB = null;
    public static final int acC = 9;
    private static List<b> acD = null;
    private static Context acz = null;

    public static List<b> uO() {
        return acB;
    }

    public static List<b> uP() {
        return acD;
    }

    public static void init(Context inputContext) {
        acz = inputContext;
    }

    public static b fT(int inputId) {
        int i;
        uQ();
        for (i = 0; i < acB.size(); i++) {
            if (((b) acB.get(i)).uJ() == inputId) {
                return (b) acB.get(i);
            }
        }
        for (i = 0; i < acD.size(); i++) {
            if (((b) acD.get(i)).uJ() == inputId) {
                return (b) acD.get(i);
            }
        }
        return null;
    }

    public static String fU(int inputId) {
        int i;
        uQ();
        for (i = 0; i < acB.size(); i++) {
            if (((b) acB.get(i)).uJ() == inputId) {
                return ((b) acB.get(i)).acy;
            }
        }
        for (i = 0; i < acD.size(); i++) {
            if (((b) acD.get(i)).uJ() == inputId) {
                return ((b) acD.get(i)).acy;
            }
        }
        return null;
    }

    public static void uQ() {
        if (acB == null && acD == null && acz != null) {
            acB = new ArrayList(14);
            acB.clear();
            acD = new ArrayList(9);
            acD.clear();
            acB.add(new b(5, 20, 10000, R.drawable.ic_launcher, acz.getResources().getString(R.string.potion_strength_name), acz.getResources().getString(R.string.potion_strength_detail)));
            acB.add(new b(11, 5, 10000, R.drawable.ic_launcher, acz.getResources().getString(R.string.potion_resistance_name), acz.getResources().getString(R.string.potion_resistance_detail)));
            acB.add(new b(14, 1, 10000, R.drawable.ic_launcher, acz.getResources().getString(R.string.potion_invisibility_name), acz.getResources().getString(R.string.potion_invisibility_detail)));
            acB.add(new b(16, 1, 10000, R.drawable.ic_launcher, acz.getResources().getString(R.string.potion_night_vision_name), acz.getResources().getString(R.string.potion_night_vision_detail)));
            acB.add(new b(1, 100, 10000, R.drawable.ic_launcher, acz.getResources().getString(R.string.potion_speed_name), acz.getResources().getString(R.string.potion_speed_detail)));
            acB.add(new b(8, 100, 10000, R.drawable.ic_launcher, acz.getResources().getString(R.string.potion_jump_boost_name), acz.getResources().getString(R.string.potion_jump_boost_detail)));
            acB.add(new b(3, 100, 10000, R.drawable.ic_launcher, acz.getResources().getString(R.string.potion_haste_name), acz.getResources().getString(R.string.potion_haste_detail)));
            acB.add(new b(12, 1, 10000, R.drawable.ic_launcher, acz.getResources().getString(R.string.potion_fire_resistance_name), acz.getResources().getString(R.string.potion_fire_resistance_detail)));
            acB.add(new b(13, 1, 10000, R.drawable.ic_launcher, acz.getResources().getString(R.string.potion_water_breathing_name), acz.getResources().getString(R.string.potion_water_breathing_detail)));
            acB.add(new b(23, 100, 10000, R.drawable.ic_launcher, acz.getResources().getString(R.string.potion_saturation_name), acz.getResources().getString(R.string.potion_saturation_detail)));
            acB.add(new b(6, 10, 10000, R.drawable.ic_launcher, acz.getResources().getString(R.string.potion_instant_health_name), acz.getResources().getString(R.string.potion_instant_health_detail)));
            acB.add(new b(21, 100, 10000, R.drawable.ic_launcher, acz.getResources().getString(R.string.potion_health_boost_name), acz.getResources().getString(R.string.potion_health_boost_detail)));
            acB.add(new b(22, 100, 10000, R.drawable.ic_launcher, acz.getResources().getString(R.string.potion_absorption_name), acz.getResources().getString(R.string.potion_absorption_detail)));
            acB.add(new b(10, 7, 10000, R.drawable.ic_launcher, acz.getResources().getString(R.string.potion_regeneration_name), acz.getResources().getString(R.string.potion_regeneration_detail)));
            acD.add(new b(9, 1, 10000, R.drawable.ic_launcher, acz.getResources().getString(R.string.potion_nausea_name), acz.getResources().getString(R.string.potion_nausea_detail)));
            acD.add(new b(15, 1, 10000, R.drawable.ic_launcher, acz.getResources().getString(R.string.potion_blindness_name), acz.getResources().getString(R.string.potion_blindness_detail)));
            acD.add(new b(20, 100, 10000, R.drawable.ic_launcher, acz.getResources().getString(R.string.potion_wither_name), acz.getResources().getString(R.string.potion_wither_detail)));
            acD.add(new b(19, 100, 10000, R.drawable.ic_launcher, acz.getResources().getString(R.string.potion_poison_name), acz.getResources().getString(R.string.potion_poison_detail)));
            acD.add(new b(18, 20, 10000, R.drawable.ic_launcher, acz.getResources().getString(R.string.potion_weakness_name), acz.getResources().getString(R.string.potion_weakness_detail)));
            acD.add(new b(2, 7, 10000, R.drawable.ic_launcher, acz.getResources().getString(R.string.potion_slowness_name), acz.getResources().getString(R.string.potion_slowness_detail)));
            acD.add(new b(4, 5, 10000, R.drawable.ic_launcher, acz.getResources().getString(R.string.potion_mining_fatigue_name), acz.getResources().getString(R.string.potion_mining_fatigue_detail)));
            acD.add(new b(7, 3, 10000, R.drawable.ic_launcher, acz.getResources().getString(R.string.potion_instant_damage_name), acz.getResources().getString(R.string.potion_instant_damage_detail)));
            acD.add(new b(17, 100, 10000, R.drawable.ic_launcher, acz.getResources().getString(R.string.potion_hunger_name), acz.getResources().getString(R.string.potion_hunger_detail)));
        }
    }
}
