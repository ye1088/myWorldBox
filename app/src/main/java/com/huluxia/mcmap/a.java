package com.huluxia.mcmap;

import com.huluxia.mojang.converter.InventorySlot;
import java.util.List;

/* compiled from: MCMapInterface */
public class a {
    public static boolean di(String mapName) {
        return b.di(mapName);
    }

    public static void setGameMode(int mode) {
        b.setGameMode(mode);
    }

    public static int getGameMode() {
        return b.getGameMode();
    }

    public static void ca(boolean dayEnable) {
        b.ca(dayEnable);
    }

    public static boolean AD() {
        return b.AD();
    }

    public static boolean AE() {
        return b.AE();
    }

    public static void cb(boolean flyMode) {
        b.cb(flyMode);
    }

    public static boolean AF() {
        return b.AF();
    }

    public static void cc(boolean enable) {
        b.cc(enable);
    }

    public static boolean AG() {
        return b.AG();
    }

    public static void cd(boolean enable) {
        b.cd(enable);
    }

    public static List<InventorySlot> readInventory() {
        return d.readInventory();
    }

    public static void addInventory(List<InventorySlot> inventorySlots) {
        d.addInventory(inventorySlots);
    }

    public static void f(byte itemId) {
        d.f(itemId);
    }
}
