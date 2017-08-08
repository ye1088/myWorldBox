package com.MCWorld.mojang.converter;

import android.graphics.Bitmap;
import java.io.Serializable;

public class ItemStack implements Serializable {
    private static final long serialVersionUID = 8918321225553688992L;
    private int amount;
    private short durability;
    private short id;
    private int mInBagIndex;
    private int mInGameBagIndex;
    private boolean mIsAutoBuild;
    private int mItemCount;
    private int mItemDmg;
    private int mItemId;
    private int mItemImgId;
    private String mItemName;
    private Bitmap mJsBitmap = null;

    public ItemStack(ItemStack newItem) {
        setItemInfo(newItem);
    }

    public ItemStack(short id, short damage, int amount) {
        this.id = id;
        this.durability = damage;
        this.amount = amount;
    }

    public int getAmount() {
        return this.amount;
    }

    public short getDurability() {
        return this.durability;
    }

    public short getTypeId() {
        return this.id;
    }

    public void setAmount(int paramInt) {
        this.amount = paramInt;
    }

    public void setDurability(short paramShort) {
        this.durability = paramShort;
    }

    public void setTypeId(short paramShort) {
        this.id = paramShort;
    }

    public ItemStack(int id, int damage, boolean isAuto, String name, int imgId) {
        setItemInfo(id, damage, isAuto, name, imgId);
    }

    public ItemStack(int id, int damage, boolean isAuto, String name, int imgId, int decode_index) {
        setItemInfo((decode_index + 30772) ^ id, (decode_index + 17013) ^ damage, isAuto, name, imgId);
    }

    public void clearItemInfo() {
        this.mItemName = "";
        this.mJsBitmap = null;
        this.mIsAutoBuild = false;
        this.mInBagIndex = -1;
        this.mItemImgId = 0;
        this.mItemDmg = 0;
        this.mItemId = 0;
        this.mItemCount = 0;
    }

    public void setItemInfo(int id, int damage, boolean isAuto, String name, int imgId) {
        this.mInBagIndex = 0;
        this.mItemCount = 0;
        this.mItemId = id;
        this.mItemDmg = damage;
        this.mItemName = name;
        this.mItemImgId = imgId;
        this.mIsAutoBuild = isAuto;
        this.id = (short) id;
        this.durability = (short) damage;
        this.amount = 1;
    }

    public void setItemInfo(ItemStack newItem) {
        if (newItem != null) {
            this.mItemId = newItem.mItemId;
            this.mItemDmg = newItem.mItemDmg;
            this.mItemName = newItem.mItemName;
            this.mJsBitmap = newItem.mJsBitmap;
            this.mItemImgId = newItem.mItemImgId;
            this.mItemCount = newItem.mItemCount;
            this.mInBagIndex = newItem.mInBagIndex;
            this.mIsAutoBuild = newItem.mIsAutoBuild;
        }
    }

    public int getItemId() {
        return this.mItemId;
    }

    public int getItemDmg() {
        return this.mItemDmg;
    }

    public int getItemImgId() {
        return this.mItemImgId;
    }

    public int getItemCount() {
        return this.mItemCount;
    }

    public int getItemBagIdx() {
        return this.mInBagIndex;
    }

    public String getItemName() {
        return this.mItemName;
    }

    public Bitmap getItemImage() {
        return this.mJsBitmap;
    }

    public boolean getItemBuild() {
        return this.mIsAutoBuild;
    }

    public void setItemBitmap(Bitmap b) {
        this.mJsBitmap = b;
    }

    public void setItemBagIdx(int idx) {
        this.mInBagIndex = idx;
    }

    public void setItemCount(int count) {
        this.mItemCount = count;
    }

    public int getmInGameBagIndex() {
        return this.mInGameBagIndex;
    }

    public void setmInGameBagIndex(int mInGameBagIndex) {
        this.mInGameBagIndex = mInGameBagIndex;
    }
}
