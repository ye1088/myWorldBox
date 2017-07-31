package com.huluxia.mcmap;

import com.huluxia.mojang.Mojang;
import com.huluxia.mojang.converter.InventorySlot;
import java.util.List;

/* compiled from: McMapInventory */
public class d {
    public static List<InventorySlot> readInventory() {
        try {
            return Mojang.instance().readInventory();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void addInventory(List<InventorySlot> inventorySlots) {
        try {
            Mojang.instance().asynAddInventory(inventorySlots);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void f(byte itemId) {
        try {
            Mojang.instance().deleteInventory(itemId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
