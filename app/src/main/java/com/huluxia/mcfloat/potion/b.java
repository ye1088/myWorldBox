package com.huluxia.mcfloat.potion;

/* compiled from: PotionItem */
public class b {
    int abK;
    int acv;
    int acw;
    String acx;
    String acy;
    int mId;

    public b(int inputId, int inputMaxLevel, int inputMaxTime, int inputImageId, String inputName, String inputDetail) {
        this.mId = inputId;
        this.abK = inputMaxLevel;
        this.acv = inputMaxTime;
        this.acw = inputImageId;
        this.acx = inputName;
        this.acy = inputDetail;
    }

    public int uJ() {
        return this.mId;
    }

    public void fQ(int mId) {
        this.mId = mId;
    }

    public int ur() {
        return this.abK;
    }

    public void fF(int mMaxLevel) {
        this.abK = mMaxLevel;
    }

    public int uK() {
        return this.acv;
    }

    public void fR(int mMaxTime) {
        this.acv = mMaxTime;
    }

    public String uL() {
        return this.acx;
    }

    public void cv(String mPotionName) {
        this.acx = mPotionName;
    }

    public String uM() {
        return this.acy;
    }

    public void cw(String mPotionDetail) {
        this.acy = mPotionDetail;
    }

    public int uN() {
        return this.acw;
    }

    public void fS(int mImageId) {
        this.acw = mImageId;
    }
}
