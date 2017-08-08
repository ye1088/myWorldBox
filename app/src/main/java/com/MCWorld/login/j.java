package com.MCWorld.login;

import com.MCWorld.login.utils.a;
import java.util.HashMap;
import java.util.Map;

/* compiled from: Session */
public class j {
    private static j Nt = null;
    private Map<String, k> Ns = new HashMap();

    public static j pV() {
        if (Nt == null) {
            Nt = new j();
        }
        return Nt;
    }

    public void a(String clientid, k sessionInfo) {
        if (sessionInfo != null && clientid != null) {
            this.Ns.put(clientid, sessionInfo);
            a.pW().a(clientid, sessionInfo);
        }
    }

    public k bS(String clientid) {
        if (clientid == null) {
            return null;
        }
        k info = (k) this.Ns.get(clientid);
        if (info == null) {
            return a.pW().bS(clientid);
        }
        return info;
    }

    public void clear(String clientid) {
        if (clientid != null) {
            this.Ns.remove(clientid);
            a.pW().bU(clientid);
        }
    }
}
