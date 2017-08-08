package com.MCWorld.controller.resource.handler;

import com.MCWorld.controller.resource.ResourceCtrl;
import com.MCWorld.controller.resource.handler.impl.b;

/* compiled from: CtrlAdder */
public class a {
    private static final int nB = 16;
    private static final int nC = 17;
    private static final int nD = 18;
    private static final int nE = 22;

    public static void dO() {
        ResourceCtrl.getInstance().registerHandler(22, b.class);
    }
}
