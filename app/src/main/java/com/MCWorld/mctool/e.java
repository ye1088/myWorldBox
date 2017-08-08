package com.MCWorld.mctool;

import android.content.Context;
import android.content.SharedPreferences;
import com.MCWorld.HTApplication;
import com.MCWorld.data.j;
import com.MCWorld.mctool.structure.a;
import com.MCWorld.utils.ag;

/* compiled from: PlayerArchieveLocalStore */
public class e {
    private static e apG = new e();
    private static long pM = -1;
    private Context context = HTApplication.getAppContext();

    public static synchronized e Dk() {
        e eVar;
        synchronized (e.class) {
            if (apG == null) {
                apG = new e();
            }
            pM = j.ep().getUserid();
            eVar = apG;
        }
        return eVar;
    }

    private SharedPreferences pX() {
        return this.context.getSharedPreferences("config", 0);
    }

    public void a(a _CLD) {
        if (_CLD != null) {
            pX().edit().putString(b.apc + _CLD.pM, ag.toJsonString(_CLD)).commit();
        }
    }

    public a Dl() {
        return (a) ag.toObjectNoExp(pX().getString(b.apc + pM, ""), a.class);
    }

    public void fw(int increSecond) {
        pX().edit().putInt(b.apd + pM, pX().getInt(b.apd + pM, 0) + increSecond).commit();
    }

    public int wx() {
        return pX().getInt(b.apd + pM, 0);
    }

    public void iK(int increCount) {
        pX().edit().putInt(b.ape + pM, pX().getInt(b.ape + pM, 0) + increCount).commit();
    }

    public int Dm() {
        return pX().getInt(b.ape + pM, 0);
    }

    public void iL(int increSecond) {
        pX().edit().putInt(b.apf + pM, pX().getInt(b.apf + pM, 0) + increSecond).commit();
    }

    public int Dn() {
        return pX().getInt(b.apf + pM, 0);
    }

    public void iM(int increKillMonsterCount) {
        pX().edit().putInt(b.apg + pM, pX().getInt(b.apg + pM, 0) + increKillMonsterCount).commit();
    }

    public int Do() {
        return pX().getInt(b.apg + pM, 0);
    }

    public void iN(int increPlayDieCount) {
        pX().edit().putInt(b.aph + pM, pX().getInt(b.aph + pM, 0) + increPlayDieCount).commit();
    }

    public int Dp() {
        return pX().getInt(b.aph + pM, 0);
    }

    public void iO(int increDigBlockCount) {
        pX().edit().putInt(b.api + pM, pX().getInt(b.api + pM, 0) + increDigBlockCount).commit();
    }

    public int Dq() {
        return pX().getInt(b.api + pM, 0);
    }

    public void iP(int increPutBlockCount) {
        pX().edit().putInt(b.apj + pM, pX().getInt(b.apj + pM, 0) + increPutBlockCount).commit();
    }

    public int Dr() {
        return pX().getInt(b.apj + pM, 0);
    }

    public void iQ(int increIownMapCount) {
        pX().edit().putInt(b.apl + pM, pX().getInt(b.apl + pM, 0) + increIownMapCount).commit();
    }

    public int Ds() {
        return pX().getInt(b.apl + pM, 0);
    }

    public void iR(int increDownJsCount) {
        pX().edit().putInt(b.apm + pM, pX().getInt(b.apm + pM, 0) + increDownJsCount).commit();
    }

    public int Dt() {
        return pX().getInt(b.apm + pM, 0);
    }

    public void iS(int increDownSkinCount) {
        pX().edit().putInt(b.apo + pM, pX().getInt(b.apo + pM, 0) + increDownSkinCount).commit();
    }

    public int Du() {
        return pX().getInt(b.apo + pM, 0);
    }

    public void iT(int increDownWoodCount) {
        pX().edit().putInt(b.apq + pM, pX().getInt(b.apq + pM, 0) + increDownWoodCount).commit();
    }

    public int Dv() {
        return pX().getInt(b.apq + pM, 0);
    }
}
