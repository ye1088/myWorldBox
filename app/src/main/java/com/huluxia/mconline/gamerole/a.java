package com.huluxia.mconline.gamerole;

import android.content.Context;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.mcgame.ad;
import com.huluxia.mcgame.options.e;
import com.huluxia.mcinterface.h;
import com.huluxia.mconline.gameloc.config.b;
import com.huluxia.module.n;
import com.huluxia.utils.ah;
import hlx.launch.game.d;

/* compiled from: NormalPlayer */
public class a {
    private static a alV;
    public volatile int alW = 0;
    public volatile String alX = null;
    public volatile int alY = 0;
    public volatile int alZ = 0;
    public String ama = "127.0.0.1";
    public int serverPort = b.alt;

    public static synchronized a Bf() {
        a aVar;
        synchronized (a.class) {
            if (alV == null) {
                alV = new a();
            }
            aVar = alV;
        }
        return aVar;
    }

    public void a(Context inputContext, final String inputOnlineServerIP, final int inputOnlineServerPort, final String inputServerIP, final int inputServerPort, int inputRoomId) {
        HLog.verbose("TAG", "DTPrint start normal player 000", new Object[0]);
        hP(inputRoomId);
        new Thread(new Runnable(this) {
            final /* synthetic */ a amd;

            public void run() {
                try {
                    com.huluxia.mconline.gameloc.tcp.player.a.AU().F(inputOnlineServerIP, inputOnlineServerPort);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(new Runnable(this) {
            final /* synthetic */ a amd;

            public void run() {
                try {
                    com.huluxia.mconline.gameloc.udp.player.a.Ba();
                    com.huluxia.mconline.gameloc.udp.player.a.I(inputServerIP, inputServerPort);
                    com.huluxia.mconline.gameloc.udp.player.a.Ba().Bb();
                    com.huluxia.mconline.gameloc.udp.player.b.Bd();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        ad.d(b.als, com.huluxia.mconline.utils.a.getHostAddress(), String.valueOf(b.alt));
        ah.KZ().Q(hlx.data.localstore.a.bKr, 0);
        e.bJ(false);
        h.gg(1);
        h.bj(true);
        EventNotifyCenter.notifyEvent(n.class, n.axj, new Object[]{Boolean.valueOf(false)});
        d.bR(inputContext);
    }

    public int Bg() {
        return this.alZ;
    }

    public void hP(int roomid) {
        this.alZ = roomid;
    }
}
