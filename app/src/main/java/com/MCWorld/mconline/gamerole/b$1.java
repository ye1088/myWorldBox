package com.MCWorld.mconline.gamerole;

import com.MCWorld.mconline.gameloc.http.f;
import com.MCWorld.mconline.gameloc.tcp.creator.a;
import com.MCWorld.mconline.proto.b;

/* compiled from: RoomCreator */
class b$1 implements Runnable {
    final /* synthetic */ String amb;
    final /* synthetic */ int amc;
    final /* synthetic */ f amh;
    final /* synthetic */ b ami;

    b$1(b this$0, String str, int i, f fVar) {
        this.ami = this$0;
        this.amb = str;
        this.amc = i;
        this.amh = fVar;
    }

    public void run() {
        try {
            a.AT().F(this.amb, this.amc);
            b.BU().b(this.amh);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
