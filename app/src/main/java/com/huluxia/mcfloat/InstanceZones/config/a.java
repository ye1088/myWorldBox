package com.huluxia.mcfloat.InstanceZones.config;

import com.huluxia.mcfb.b;
import java.util.ArrayList;
import java.util.List;

/* compiled from: Equipment */
public class a {
    List<b> XH;
    List<b> XI;
    List<b> XJ;

    public void a(b item) {
        if (this.XH == null) {
            this.XH = new ArrayList();
        }
        this.XH.add(item);
    }

    public List<b> sW() {
        return this.XH;
    }

    public void b(b item) {
        if (this.XI == null) {
            this.XI = new ArrayList();
        }
        this.XI.add(item);
    }

    public List<b> sX() {
        return this.XI;
    }

    public void c(b item) {
        if (this.XJ == null) {
            this.XJ = new ArrayList();
        }
        this.XJ.add(item);
    }

    public List<b> sY() {
        return this.XJ;
    }
}
