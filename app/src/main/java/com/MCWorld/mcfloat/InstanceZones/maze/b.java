package com.MCWorld.mcfloat.InstanceZones.maze;

import com.MCWorld.mcgame.a;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/* compiled from: MazeRouteGenerater */
public class b {
    public static ArrayList<a> Yp = new ArrayList(2);
    private static b Yq;
    private int Yc;
    private int Yd;
    private int Yn = 2;
    private boolean Yo = false;

    public static synchronized b tF() {
        b bVar;
        synchronized (b.class) {
            if (Yq == null) {
                Yq = new b();
                Yq.Yd = 25;
                Yq.Yc = 25;
            }
            bVar = Yq;
        }
        return bVar;
    }

    private boolean a(ArrayList<com.MCWorld.mcfloat.InstanceZones.trap.b> inputList, int inputX, int inputZ) {
        for (int i = 0; i < inputList.size(); i++) {
            com.MCWorld.mcfloat.InstanceZones.trap.b _tmpMCMapItemObj = (com.MCWorld.mcfloat.InstanceZones.trap.b) inputList.get(i);
            if (_tmpMCMapItemObj.ZM == inputX && _tmpMCMapItemObj.ZN == inputZ) {
                return _tmpMCMapItemObj.ZO;
            }
        }
        return false;
    }

    private void a(ArrayList<com.MCWorld.mcfloat.InstanceZones.trap.b> inputList, boolean isTrap, int inputX, int inputZ) {
        if (inputList != null) {
            for (int i = 0; i < inputList.size(); i++) {
                com.MCWorld.mcfloat.InstanceZones.trap.b _tmpMCMapItemObj = (com.MCWorld.mcfloat.InstanceZones.trap.b) inputList.get(i);
                if (_tmpMCMapItemObj.ZM == inputX && _tmpMCMapItemObj.ZN == inputZ) {
                    _tmpMCMapItemObj.ZO = isTrap;
                    return;
                }
            }
            inputList.add(new com.MCWorld.mcfloat.InstanceZones.trap.b(inputX, inputZ, isTrap));
        }
    }

    private ArrayList<com.MCWorld.mcfloat.InstanceZones.trap.b> D(int inputX, int inputZ) {
        ArrayList<com.MCWorld.mcfloat.InstanceZones.trap.b> inList = new ArrayList();
        int b = 0;
        while (b < this.Yd) {
            int a = 0;
            while (a < this.Yc) {
                if (a + b <= 1 || a + b >= (this.Yd + this.Yc) - 3) {
                    inList.add(new com.MCWorld.mcfloat.InstanceZones.trap.b(inputZ + b, inputX + a, false));
                } else {
                    inList.add(new com.MCWorld.mcfloat.InstanceZones.trap.b(inputZ + b, inputX + a, true));
                }
                a++;
            }
            b++;
        }
        Yp.clear();
        Yp.add(new a(1, 0, 1, 2, 0, 1));
        Yp.add(new a(1, 0, 1, 1, 0, 2));
        this.Yn = 2;
        do {
            if (this.Yn < 1) {
                this.Yn = 3;
            }
            if (this.Yn > Yp.size()) {
                this.Yn = Yp.size() - 1;
            }
            int rndm = (int) Math.floor(((double) (this.Yo ? this.Yn : Yp.size())) * Math.random());
            if (this.Yo) {
                rndm = (Yp.size() - rndm) - 1;
            }
            this.Yn = 0;
            a _tmpDT3DPointObj = (a) Yp.get(rndm);
            if (_tmpDT3DPointObj != null) {
                if (a(inList, ((_tmpDT3DPointObj.uU() * 2) + inputX) - 1, ((_tmpDT3DPointObj.uW() * 2) + inputZ) - 1)) {
                    a(inList, false, ((_tmpDT3DPointObj.uU() * 2) + inputX) - 1, ((_tmpDT3DPointObj.uW() * 2) + inputZ) - 1);
                    a(inList, false, ((_tmpDT3DPointObj.uR() + inputX) + _tmpDT3DPointObj.uU()) - 1, ((_tmpDT3DPointObj.uT() + inputZ) + _tmpDT3DPointObj.uW()) - 1);
                    if (a(inList, ((_tmpDT3DPointObj.uU() * 2) + inputX) + 1, ((_tmpDT3DPointObj.uW() * 2) + inputZ) - 1)) {
                        Yp.add(new a(_tmpDT3DPointObj.uU(), 0, _tmpDT3DPointObj.uW(), _tmpDT3DPointObj.uU() + 1, 0, _tmpDT3DPointObj.uW()));
                        this.Yn++;
                    }
                    if (a(inList, ((_tmpDT3DPointObj.uU() * 2) + inputX) - 3, ((_tmpDT3DPointObj.uW() * 2) + inputZ) - 1)) {
                        Yp.add(new a(_tmpDT3DPointObj.uU(), 0, _tmpDT3DPointObj.uW(), _tmpDT3DPointObj.uU() - 1, 0, _tmpDT3DPointObj.uW()));
                        this.Yn++;
                    }
                    if (a(inList, ((_tmpDT3DPointObj.uU() * 2) + inputX) - 1, ((_tmpDT3DPointObj.uW() * 2) + inputZ) + 1)) {
                        Yp.add(new a(_tmpDT3DPointObj.uU(), 0, _tmpDT3DPointObj.uW(), _tmpDT3DPointObj.uU(), 0, _tmpDT3DPointObj.uW() + 1));
                        this.Yn++;
                    }
                    if (a(inList, ((_tmpDT3DPointObj.uU() * 2) + inputX) - 1, ((_tmpDT3DPointObj.uW() * 2) + inputZ) - 3)) {
                        Yp.add(new a(_tmpDT3DPointObj.uU(), 0, _tmpDT3DPointObj.uW(), _tmpDT3DPointObj.uU(), 0, _tmpDT3DPointObj.uW() - 1));
                        this.Yn++;
                    }
                    Yp.remove(rndm);
                } else {
                    Yp.remove(rndm);
                    this.Yn = 3;
                }
            }
        } while (Yp.size() != 0);
        return inList;
    }

    public LinkedList<hlx.utils.a> E(int posX, int posY) {
        LinkedList<hlx.utils.a> safeList = new LinkedList();
        Iterator it = D(posX, posY).iterator();
        while (it.hasNext()) {
            com.MCWorld.mcfloat.InstanceZones.trap.b _tmp = (com.MCWorld.mcfloat.InstanceZones.trap.b) it.next();
            if (!_tmp.ZO) {
                safeList.add(new hlx.utils.a(_tmp.ZM, _tmp.ZN));
            }
        }
        return safeList;
    }
}
