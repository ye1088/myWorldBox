package com.MCWorld.mcgame.biont;

/* compiled from: MCBiont */
public class a {
    int Ox;
    int agA;
    int agB;
    int agC;
    int agD;
    int agz;
    int count;
    int id;
    String name;
    float x;
    float y;
    float z;

    public void b(float pos_x, float pos_y, float pos_z, int biological_id, int biological_count, String inputName, int inputMaxHealth, int inputHat, int inputCoat, int inputTrousers, int inputShoes, int inputWeapon) {
        this.x = pos_x;
        this.y = pos_y;
        this.z = pos_z;
        this.id = biological_id;
        this.count = biological_count;
        this.name = inputName;
        this.Ox = inputMaxHealth;
        this.agz = inputHat;
        this.agA = inputCoat;
        this.agB = inputTrousers;
        this.agC = inputShoes;
        this.agD = inputWeapon;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getX() {
        return this.x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return this.z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaxHealth() {
        return this.Ox;
    }

    public void gZ(int maxHealth) {
        this.Ox = maxHealth;
    }

    public int yL() {
        return this.agz;
    }

    public void ha(int wearHat) {
        this.agz = wearHat;
    }

    public int yM() {
        return this.agA;
    }

    public void hb(int wearCoat) {
        this.agA = wearCoat;
    }

    public int yN() {
        return this.agB;
    }

    public void hc(int wearTrousers) {
        this.agB = wearTrousers;
    }

    public int yO() {
        return this.agC;
    }

    public void hd(int wearShoes) {
        this.agC = wearShoes;
    }

    public int yP() {
        return this.agD;
    }

    public void he(int wearWeapon) {
        this.agD = wearWeapon;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
