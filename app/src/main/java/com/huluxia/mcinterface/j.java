package com.huluxia.mcinterface;

/* compiled from: MCMapItem */
public class j {
    private int Ou;
    private int Ox;
    private int afa;
    private int afb;
    private int afc;
    private int ajr;
    private int ajs;
    private int gameType;
    private int id;

    public j(j item) {
        c(item.id, item.Ou, item.afa, item.afb, item.afc, item.ajr);
    }

    public j(int in_id, int in_dmg, int in_pos_x, int in_pos_y, int in_pos_z) {
        c(in_id, in_dmg, in_pos_x, in_pos_y, in_pos_z, 0);
    }

    public j(int in_id, int in_dmg, int in_pos_x, int in_pos_y, int in_pos_z, int in_operType, int in_funcType, int in_maxHealth, int in_gameType) {
        a(in_id, in_dmg, in_pos_x, in_pos_y, in_pos_z, in_operType, in_funcType, in_maxHealth, in_gameType);
    }

    public j(int in_id, int in_dmg, int in_pos_x, int in_pos_y, int in_pos_z, int in_funcType) {
        b(in_id, in_dmg, in_pos_x, in_pos_y, in_pos_z, 0, in_funcType);
    }

    public j(int in_id, int in_dmg, int in_pos_x, int in_pos_y, int in_pos_z, int in_funcType, int in_maxHealth) {
        a(in_id, in_dmg, in_pos_x, in_pos_y, in_pos_z, 0, in_funcType, in_maxHealth);
    }

    public void d(j src) {
        this.id = src.id;
        this.Ou = src.Ou;
        this.afa = src.afa;
        this.afb = src.afb;
        this.afc = src.afc;
        this.ajr = src.ajr;
        this.ajs = src.ajs;
        this.Ox = src.Ox;
        this.gameType = src.gameType;
    }

    public void c(int in_id, int in_dmg, int in_pos_x, int in_pos_y, int in_pos_z, int in_operType) {
        this.id = in_id;
        this.Ou = in_dmg;
        this.afa = in_pos_x;
        this.afb = in_pos_y;
        this.afc = in_pos_z;
        this.ajr = in_operType;
        this.ajs = 0;
        this.Ox = 0;
        this.gameType = 0;
    }

    public void b(int in_id, int in_dmg, int in_pos_x, int in_pos_y, int in_pos_z, int in_operType, int in_funcType) {
        this.id = in_id;
        this.Ou = in_dmg;
        this.afa = in_pos_x;
        this.afb = in_pos_y;
        this.afc = in_pos_z;
        this.ajr = in_operType;
        this.ajs = in_funcType;
        this.Ox = 0;
        this.gameType = 0;
    }

    public void a(int in_id, int in_dmg, int in_pos_x, int in_pos_y, int in_pos_z, int in_operType, int in_funcType, int in_maxHealth) {
        this.id = in_id;
        this.Ou = in_dmg;
        this.afa = in_pos_x;
        this.afb = in_pos_y;
        this.afc = in_pos_z;
        this.ajr = in_operType;
        this.ajs = in_funcType;
        this.Ox = in_maxHealth;
        this.gameType = 0;
    }

    public void a(int in_id, int in_dmg, int in_pos_x, int in_pos_y, int in_pos_z, int in_operType, int in_funcType, int in_maxHealth, int in_gameType) {
        this.id = in_id;
        this.Ou = in_dmg;
        this.afa = in_pos_x;
        this.afb = in_pos_y;
        this.afc = in_pos_z;
        this.ajr = in_operType;
        this.ajs = in_funcType;
        this.Ox = in_maxHealth;
        this.gameType = in_gameType;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int ql() {
        return this.Ou;
    }

    public void cT(int dmg) {
        this.Ou = dmg;
    }

    public float zW() {
        return (float) this.afa;
    }

    public void gB(int pos_x) {
        this.afa = pos_x;
    }

    public float zX() {
        return (float) this.afb;
    }

    public void gC(int pos_y) {
        this.afb = pos_y;
    }

    public float zY() {
        return (float) this.afc;
    }

    public void gD(int pos_z) {
        this.afc = pos_z;
    }

    public int zZ() {
        return this.ajr;
    }

    public void hv(int operType) {
        this.ajr = operType;
    }

    public int Aa() {
        return this.ajs;
    }

    public void hw(int funcType) {
        this.ajs = funcType;
    }

    public int getMaxHealth() {
        return this.Ox;
    }

    public int getGameType() {
        return this.gameType;
    }

    public void setGameType(int gameType) {
        this.gameType = gameType;
    }
}
