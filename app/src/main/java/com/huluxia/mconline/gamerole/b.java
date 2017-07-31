package com.huluxia.mconline.gamerole;

import android.content.Context;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.mcgame.options.e;
import com.huluxia.mcinterface.g;
import com.huluxia.mcinterface.h;
import com.huluxia.mconline.gameloc.http.f;
import com.huluxia.mojang.Mojang;
import com.huluxia.utils.UtilsFile;
import com.huluxia.utils.ah;
import hlx.data.localstore.a;
import hlx.launch.game.d;

/* compiled from: RoomCreator */
public class b implements g {
    private static b amg;
    public volatile String alO = "";
    public volatile int alP = 0;
    public volatile int alQ = 0;

    public static synchronized b Bh() {
        b bVar;
        synchronized (b.class) {
            if (amg == null) {
                amg = new b();
            }
            bVar = amg;
        }
        return bVar;
    }

    public boolean h(com.huluxia.data.map.b item) {
        if (!UtilsFile.isExist(UtilsFile.eQ(item.name)) && !UtilsFile.isExist(UtilsFile.fS(item.name))) {
            return false;
        }
        try {
            Mojang.instance().init(item.name, 0, null);
            HLog.verbose("TAG", "DTPrint getLastPlayed is " + Mojang.instance().getLastPlayed(), new Object[0]);
            Mojang.instance().setLastPlayed(System.currentTimeMillis() / 1000);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void a(Context inputContext, String inputOnlineServerIP, int inputOnlineServerPort, f roomCreateItem) {
        new Thread(new 1(this, inputOnlineServerIP, inputOnlineServerPort, roomCreateItem)).start();
        new Thread(new 2(this, roomCreateItem)).start();
        ah.KZ().Q(a.bKr, 0);
        e.bJ(false);
        h.gg(2);
        h.bj(true);
        d.bR(inputContext);
    }

    public String Bi() {
        return this.alO;
    }

    public void dj(String selfWebIP) {
        this.alO = selfWebIP;
    }

    public int Bj() {
        return this.alP;
    }

    public void hQ(int selfWebTCPPort) {
        this.alP = selfWebTCPPort;
    }

    public int Bk() {
        return this.alQ;
    }

    public void hR(int roomID) {
        this.alQ = roomID;
    }

    public void cd(String mapName) {
        com.huluxia.mconline.proto.b.BU().a(this.alQ, this.alO, this.alP);
    }

    public void cg(String mapName) {
    }

    public void sd() {
    }
}
