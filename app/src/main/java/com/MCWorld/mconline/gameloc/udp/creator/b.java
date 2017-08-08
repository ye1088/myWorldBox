package com.MCWorld.mconline.gameloc.udp.creator;

import com.MCWorld.mconline.utils.a;
import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: ProxyGamePlayerManager */
public class b {
    private static b alF;
    public a alG;
    public ArrayList<a> alH = new ArrayList();

    public static synchronized b AX() {
        b bVar;
        synchronized (b.class) {
            if (alF == null) {
                alF = new b();
                alF.alG = new a(a.BW(), com.MCWorld.mconline.gameloc.config.b.alt);
                alF.alH.add(alF.alG);
            }
            bVar = alF;
        }
        return bVar;
    }

    public a H(String inputIP, int inputPort) {
        a _tmpProxyGamePlayer;
        Iterator it = this.alH.iterator();
        while (it.hasNext()) {
            _tmpProxyGamePlayer = (a) it.next();
            if (_tmpProxyGamePlayer != null && _tmpProxyGamePlayer.isValid() && _tmpProxyGamePlayer.alD.equals(inputIP) && _tmpProxyGamePlayer.alC == inputPort) {
                return _tmpProxyGamePlayer;
            }
        }
        _tmpProxyGamePlayer = new a(inputIP, inputPort);
        if (_tmpProxyGamePlayer == null || !_tmpProxyGamePlayer.isValid()) {
            return null;
        }
        this.alH.add(_tmpProxyGamePlayer);
        return _tmpProxyGamePlayer;
    }

    public void a(a inputProxyGamePlayer) {
        if (inputProxyGamePlayer != null) {
            this.alH.remove(inputProxyGamePlayer);
        }
    }

    public void AY() {
        Iterator it = this.alH.iterator();
        while (it.hasNext()) {
            System.out.println((a) it.next());
        }
    }
}
