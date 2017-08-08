package com.MCWorld.mcinterface;

import android.graphics.Bitmap;

/* compiled from: MCBagItem */
public class c {
    private int Ou;
    private int aha;
    private int ahb;
    private int ahc;
    private String ahd;
    private Bitmap ahe;
    private String ahf;
    private int ahg;
    private int count;
    private int id;
    private int tf;
    private int type;

    public void ze() {
        this.type = 0;
        this.id = 0;
        this.count = 0;
        this.Ou = 0;
        this.tf = 0;
        this.aha = 0;
        this.ahf = null;
        this.ahd = null;
        this.ahe = null;
    }

    public c(int in_type, int in_Id, int in_Count, int in_Dmg, int in_flag, String in_idx_name, String in_item_name) {
        this.type = in_type;
        this.id = in_Id;
        this.count = in_Count;
        this.Ou = in_Dmg;
        this.tf = in_flag;
        this.aha = 0;
        this.ahf = in_idx_name;
        this.ahd = in_item_name;
        this.ahe = null;
    }

    public c(int in_type, int in_Id, int in_Count, int in_Dmg, int in_flag) {
        this.type = in_type;
        this.id = in_Id;
        this.count = in_Count;
        this.Ou = in_Dmg;
        this.tf = in_flag;
        this.aha = 0;
        this.ahe = null;
    }

    public c(c src) {
        this.id = src.id;
        this.count = src.count;
        this.Ou = src.Ou;
        this.tf = src.tf;
        this.type = src.type;
        this.ahb = src.ahb;
        this.aha = src.aha;
    }

    public c() {
        this.type = 0;
        this.id = 0;
        this.count = 0;
        this.Ou = 0;
        this.tf = 0;
        this.aha = 0;
        this.ahb = -1;
        this.ahe = null;
    }

    public void a(c src) {
        this.id = src.id;
        this.count = src.count;
        this.Ou = src.Ou;
        this.tf = src.tf;
        this.type = src.type;
        this.ahb = src.ahb;
        this.aha = src.aha;
    }

    public void j(int in_type, int in_Id, int in_Count, int in_Dmg, int in_flag) {
        this.type = in_type;
        this.id = in_Id;
        this.count = in_Count;
        this.Ou = in_Dmg;
        this.tf = in_flag;
        this.aha = 0;
        this.ahe = null;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int ql() {
        return this.Ou;
    }

    public void cT(int dmg) {
        this.Ou = dmg;
    }

    public int getFlag() {
        return this.tf;
    }

    public void setFlag(int flag) {
        this.tf = flag;
    }

    public int zf() {
        return this.aha;
    }

    public void hi(int addPlusFlag) {
        this.aha = addPlusFlag;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String zg() {
        return this.ahf;
    }

    public void cU(String idx_name) {
        this.ahf = idx_name;
    }

    public String zh() {
        return this.ahd;
    }

    public void cV(String item_name) {
        this.ahd = item_name;
    }

    public int zi() {
        return this.ahg;
    }

    public void hj(int son_type) {
        this.ahg = son_type;
    }

    public Bitmap zj() {
        return this.ahe;
    }

    public void p(Bitmap item_bmp) {
        this.ahe = item_bmp;
    }

    public int zk() {
        return this.ahb;
    }

    public void hk(int bagIndex) {
        this.ahb = bagIndex;
    }

    public int zl() {
        return this.ahc;
    }

    public void hl(int gameBagIndex) {
        this.ahc = gameBagIndex;
    }
}
