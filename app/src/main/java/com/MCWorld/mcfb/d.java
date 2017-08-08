package com.MCWorld.mcfb;

import java.util.ArrayList;

/* compiled from: MCFBMonsterList */
public class d {
    private static ArrayList<c> Pc = new ArrayList();
    private static d Pd;

    public static synchronized d qq() {
        d dVar;
        synchronized (d.class) {
            if (Pd == null) {
                Pd = new d();
                Pc.clear();
                Pc.add(new c(0, 10, 0));
                Pc.add(new c(1, 11, 0));
                Pc.add(new c(2, 12, 0));
                Pc.add(new c(3, 13, 0));
                Pc.add(new c(4, 14, 0));
                Pc.add(new c(5, 15, 0));
                Pc.add(new c(6, 16, 0));
                Pc.add(new c(7, 32, 0));
                Pc.add(new c(8, 33, 0));
                Pc.add(new c(9, 34, 0));
                Pc.add(new c(10, 35, 0));
                Pc.add(new c(11, 36, 0));
                Pc.add(new c(12, 37, 0));
                Pc.add(new c(13, 38, 0));
                Pc.add(new c(14, 39, 0));
                Pc.add(new c(15, 41, 0));
                Pc.add(new c(16, 17, 0));
                Pc.add(new c(17, 19, 0));
                Pc.add(new c(18, 20, 0));
                Pc.add(new c(19, 21, 0));
                Pc.add(new c(20, 22, 0));
                Pc.add(new c(21, 40, 0));
                Pc.add(new c(22, 42, 0));
                Pc.add(new c(23, 43, 0));
                Pc.add(new c(24, 44, 0));
                Pc.add(new c(25, 18, 0));
            }
            dVar = Pd;
        }
        return dVar;
    }

    public static void qr() {
        for (int i = 0; i < Pc.size(); i++) {
            c _tmpMonsterItem = (c) Pc.get(i);
            if (_tmpMonsterItem != null) {
                _tmpMonsterItem.setCount(0);
            }
        }
    }

    public static ArrayList<c> qs() {
        return Pc;
    }

    public static void cW(int inputId) {
        for (int i = 0; i < Pc.size(); i++) {
            c _tmpMonsterItem = (c) Pc.get(i);
            if (_tmpMonsterItem != null && _tmpMonsterItem.getId() == inputId) {
                _tmpMonsterItem.qo();
            }
        }
    }
}
