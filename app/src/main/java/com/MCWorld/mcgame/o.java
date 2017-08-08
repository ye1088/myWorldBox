package com.MCWorld.mcgame;

/* compiled from: DTMapBiontEntity */
public class o {
    private int afa;
    private int afb;
    private int afc;
    private int id;
    private int index;

    public o(int in_index, int in_id, int in_pos_x, int in_pos_y, int in_pos_z) {
        g(in_index, in_id, in_pos_x, in_pos_y, in_pos_z);
    }

    private void g(int in_index, int in_id, int in_pos_x, int in_pos_y, int in_pos_z) {
        this.index = in_index;
        this.id = in_id;
        this.afa = in_pos_x;
        this.afb = in_pos_y;
        this.afc = in_pos_z;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int xf() {
        return this.afa;
    }

    public void gB(int pos_x) {
        this.afa = pos_x;
    }

    public int xg() {
        return this.afb;
    }

    public void gC(int pos_y) {
        this.afb = pos_y;
    }

    public int xh() {
        return this.afc;
    }

    public void gD(int pos_z) {
        this.afc = pos_z;
    }
}
