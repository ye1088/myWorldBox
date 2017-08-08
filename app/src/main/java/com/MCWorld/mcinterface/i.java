package com.MCWorld.mcinterface;

/* compiled from: MCMapBiont */
public class i {
    private int Ov;
    private int ajq;
    private int id;

    public void y(int in_id, int in_cnt, int in_tmpCnt) {
        this.id = in_id;
        this.Ov = in_cnt;
        this.ajq = in_tmpCnt;
    }

    public i(int in_id, int in_cnt, int in_tmpCnt) {
        y(in_id, in_cnt, in_tmpCnt);
    }

    public void xb() {
        this.Ov = this.ajq;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int qm() {
        return this.Ov;
    }

    public void cU(int cnt) {
        this.Ov = cnt;
    }

    public void zT() {
        this.ajq++;
    }

    public void zU() {
        if (this.ajq > 0) {
            this.ajq--;
        }
    }

    public int zV() {
        return this.ajq;
    }

    public void hu(int tmpCnt) {
        this.ajq = tmpCnt;
    }
}
