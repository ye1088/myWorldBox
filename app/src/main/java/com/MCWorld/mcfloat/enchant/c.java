package com.MCWorld.mcfloat.enchant;

import android.graphics.Bitmap;

/* compiled from: EnchantItem */
public class c {
    public static final int abR = 25;
    public static final int abS = 1;
    public static final int abT = 2;
    public static final int abU = 3;
    public static final int abV = 4;
    public static final int abW = 5;
    private int abP;
    private int abQ;
    private int[] abX;
    private int mInBagIndex;
    private int mInGameBagIndex;
    private boolean mIsAutoBuild;
    private int mItemCount;
    private int mItemDmg;
    private int mItemId;
    private int mItemImgId;
    private String mItemName;
    private Bitmap mJsBitmap;

    public c(c newItem) {
        this.mJsBitmap = null;
        this.abX = new int[25];
        b(newItem);
    }

    public c(int id, int damage, boolean isAuto, String name, int imgId, int inputSupportEnchantCnt, int[] inputArray) {
        this.mJsBitmap = null;
        this.abX = new int[25];
        a(id, damage, isAuto, name, imgId, inputSupportEnchantCnt, inputArray);
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

    public void a(int id, int damage, boolean isAuto, String name, int imgId, int inputSupportEnchantCnt, int[] inputArray) {
        this.mInBagIndex = 0;
        this.mItemCount = 0;
        this.mItemId = id;
        this.mItemDmg = damage;
        this.mItemName = name;
        this.mItemImgId = imgId;
        this.mIsAutoBuild = isAuto;
        this.abQ = inputSupportEnchantCnt;
        System.arraycopy(inputArray, 0, this.abX, 0, 25);
    }

    public void b(c newItem) {
        this.mItemId = newItem.mItemId;
        this.mItemDmg = newItem.mItemDmg;
        this.mItemName = newItem.mItemName;
        this.mJsBitmap = newItem.mJsBitmap;
        this.mItemImgId = newItem.mItemImgId;
        this.mItemCount = newItem.mItemCount;
        this.mInBagIndex = newItem.mInBagIndex;
        this.mIsAutoBuild = newItem.mIsAutoBuild;
        this.abX = newItem.abX;
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

    public int uu() {
        return this.abP;
    }

    public void fJ(int mItemType) {
        this.abP = mItemType;
    }

    public int uv() {
        return this.abQ;
    }

    public void fK(int mSupEnchantCnt) {
        this.abQ = mSupEnchantCnt;
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

    public int[] uw() {
        return this.abX;
    }

    public int fL(int in_index) {
        return this.abX[in_index];
    }

    public void a(int[] supportAttr) {
        this.abX = supportAttr;
    }
}
