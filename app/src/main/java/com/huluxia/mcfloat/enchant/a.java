package com.huluxia.mcfloat.enchant;

/* compiled from: EnchantAttr */
public class a {
    private int abJ;
    private int abK;
    private String abL;
    private int abM;

    public a(int in_enchantAttr, int in_maxLevel, String in_enchantName, int in_enchantLevelAttrId) {
        this.abJ = in_enchantAttr;
        this.abK = in_maxLevel;
        this.abL = in_enchantName;
        this.abM = in_enchantLevelAttrId;
    }

    public int uq() {
        return this.abJ;
    }

    public void fE(int mEnchantAttr) {
        this.abJ = mEnchantAttr;
    }

    public int ur() {
        return this.abK;
    }

    public void fF(int mMaxLevel) {
        this.abK = mMaxLevel;
    }

    public String us() {
        return this.abL;
    }

    public void cp(String mEnchantName) {
        this.abL = mEnchantName;
    }
}
