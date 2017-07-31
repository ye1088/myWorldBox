package com.huluxia.mconline.gamerole;

import com.huluxia.mconline.gameloc.http.f;
import com.huluxia.mconline.gameloc.udp.creator.b;
import com.huluxia.mconline.gameloc.udp.creator.c;

/* compiled from: RoomCreator */
class b$2 implements Runnable {
    final /* synthetic */ f amh;
    final /* synthetic */ b ami;

    b$2(b this$0, f fVar) {
        this.ami = this$0;
        this.amh = fVar;
    }

    public void run() {
        try {
            b.AX().AY();
            c cVar = new c(this.amh.server_ip, this.amh.server_port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
