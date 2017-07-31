package com.huluxia.mcfb;

/* compiled from: MCFBItem */
public class b {
    public static final int Oh = 0;
    public static final int Oi = 1;
    public static final int Oj = 2;
    public static final int Ok = 3;
    public static final int Ol = 7;
    public static final int Om = 0;
    public static final int On = 1;
    public static final int Oo = 2;
    public static final int Op = 3;
    public static final int Oq = 4;
    public static final int Or = 5;
    public static final int Os = 6;
    public static final int Ot = 99;
    private int Ou;
    private int Ov;
    private int Ow;
    private int Ox;
    private int id;
    private int type;

    public b(int inputType, int inputId, int inputDmg, int inputCnt, int inputCorner, int inputHealth) {
        this.type = inputType;
        this.id = inputId;
        this.Ou = inputDmg;
        this.Ov = inputCnt;
        this.Ow = inputCorner;
        this.Ox = inputHealth;
    }

    public b(int inputType, int inputId, int inputDmg, int inputCnt, int inputCorner) {
        this.type = inputType;
        this.id = inputId;
        this.Ou = inputDmg;
        this.Ov = inputCnt;
        this.Ow = inputCorner;
        this.Ox = 0;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int qm() {
        return this.Ov;
    }

    public void cU(int cnt) {
        this.Ov = cnt;
    }

    public int qn() {
        return this.Ow;
    }

    public void cV(int corner) {
        this.Ow = corner;
    }

    public int getMaxHealth() {
        return this.Ox;
    }
}
