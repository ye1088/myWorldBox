package com.MCWorld.mcfloat.build;

import com.MCWorld.mcinterface.j;
import java.util.ArrayList;

/* compiled from: BuildBase */
public abstract class a {
    protected boolean aaR = false;

    public abstract void d(int i, int i2, int i3, int i4, int i5);

    public abstract void e(int i, int i2, int i3, int i4, int i5);

    public abstract ArrayList<j> ui();

    public abstract ArrayList<j> uj();

    public boolean uh() {
        return this.aaR;
    }

    public void aO(boolean b) {
        this.aaR = b;
    }
}
