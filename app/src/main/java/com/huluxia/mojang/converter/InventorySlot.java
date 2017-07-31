package com.huluxia.mojang.converter;

import java.io.Serializable;

public class InventorySlot implements Serializable {
    private ItemStack contents;
    private byte slot;

    public InventorySlot(byte paramByte, ItemStack paramItemStack) {
        this.slot = paramByte;
        this.contents = paramItemStack;
    }

    public ItemStack getContents() {
        return this.contents;
    }

    public byte getSlot() {
        return this.slot;
    }

    public void setContents(ItemStack paramItemStack) {
        this.contents = paramItemStack;
    }

    public void setSlot(byte paramByte) {
        this.slot = paramByte;
    }

    public String toString() {
        return "";
    }
}
