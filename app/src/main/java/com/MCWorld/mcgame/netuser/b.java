package com.MCWorld.mcgame.netuser;

/* compiled from: MCNetUser */
public class b {
    private float afN;
    private float afO;
    private float afP;
    private long agJ;
    private boolean agK;
    private String name;

    public b(long inputEntityId, int inputX, int inputY, int inputZ, String inputName, boolean inputValid) {
        this.agJ = inputEntityId;
        this.afN = (float) inputX;
        this.afO = (float) inputY;
        this.afP = (float) inputZ;
        this.name = inputName;
        this.agK = inputValid;
    }

    public long yX() {
        return this.agJ;
    }

    public void Y(long entityId) {
        this.agJ = entityId;
    }

    public float yj() {
        return this.afN;
    }

    public void u(float cur_x) {
        this.afN = cur_x;
    }

    public float yk() {
        return this.afO;
    }

    public void v(float cur_y) {
        this.afO = cur_y;
    }

    public float yl() {
        return this.afP;
    }

    public void w(float cur_z) {
        this.afP = cur_z;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isValid() {
        return this.agK;
    }

    public void bI(boolean valid) {
        this.agK = valid;
    }
}
