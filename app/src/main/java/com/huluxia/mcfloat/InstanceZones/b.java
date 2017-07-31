package com.huluxia.mcfloat.InstanceZones;

/* compiled from: InsZoneMissionPara */
public class b {
    private int Wk;
    private int Wl;
    private int Wm;
    private int Wn;
    private int Wo;
    private int Wp;
    private int Wq;
    private int Wr;

    public void dL(int insZoneMode) {
        this.Wk = insZoneMode;
    }

    public int sn() {
        return this.Wk;
    }

    public void so() {
        this.Wl++;
    }

    public int sp() {
        return this.Wl;
    }

    public void sq() {
        this.Wm++;
    }

    public int sr() {
        return this.Wm;
    }

    public void y(int waitTime, int challengeTime) {
        this.Wp = waitTime;
        this.Wn = waitTime;
        this.Wq = challengeTime;
        this.Wo = challengeTime;
        this.Wr = 0;
        this.Wl = 0;
        this.Wm = 0;
    }

    public void z(int waitTime, int challengeTimeIncre) {
        this.Wp = waitTime;
        this.Wn = waitTime;
        this.Wq += challengeTimeIncre;
        this.Wo = this.Wq;
        this.Wr = 0;
        this.Wl = 0;
        this.Wm = 0;
    }

    public void ss() {
        if (this.Wp > 0) {
            this.Wp--;
        } else if (this.Wq > 0) {
            this.Wq--;
        }
        this.Wr++;
    }

    public int st() {
        return this.Wn - this.Wp;
    }

    public int su() {
        return this.Wp;
    }

    public int sv() {
        return this.Wo - this.Wq;
    }

    public int sw() {
        return this.Wq;
    }

    public int sx() {
        return this.Wr;
    }

    public boolean sy() {
        return this.Wp <= 0;
    }

    public boolean sz() {
        return this.Wq <= 0;
    }
}
