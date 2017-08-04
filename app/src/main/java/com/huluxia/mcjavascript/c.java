package com.huluxia.mcjavascript;

import com.huluxia.mcsdk.DTSDKManagerEx;
import com.huluxia.mcsdk.dtlib.a;
import com.huluxia.mcsdk.dtlib.h;

/* compiled from: DTBlockHostObjectEx */
public class c {
    public static Object a(double paramDouble1, double paramDouble2, double paramDouble3, int paramInt, String paramString) {
        if (DTSDKManagerEx.dy(paramString)) {
            paramString = null;
        }
        if (h.CW().CX() == 0) {
            return Integer.valueOf(DTSDKManagerEx.b((float) paramDouble1, (float) paramDouble2, (float) paramDouble3, paramInt, paramString));
        }
        if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
            return Long.valueOf(DTSDKManagerEx.c((float) paramDouble1, (float) paramDouble2, (float) paramDouble3, paramInt, paramString));
        }
        return Integer.valueOf(0);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static double P(java.lang.Object r5) {
        /*
        r4 = 1;
        r0 = 0;
        if (r5 == 0) goto L_0x0009;
    L_0x0005:
        r2 = r5 instanceof java.lang.Number;	 Catch:{ Exception -> 0x005c }
        if (r2 != 0) goto L_0x0009;
    L_0x0009:
        r2 = com.huluxia.mcsdk.dtlib.h.CW();	 Catch:{ Exception -> 0x005c }
        r2 = r2.CX();	 Catch:{ Exception -> 0x005c }
        if (r2 != 0) goto L_0x001d;
    L_0x0013:
        r2 = com.huluxia.mcsdk.DTSDKManagerEx.Cp();	 Catch:{ Exception -> 0x005c }
        r0 = com.huluxia.mcsdk.DTSDKManagerEx.im(r2);	 Catch:{ Exception -> 0x005c }
        r0 = (double) r0;	 Catch:{ Exception -> 0x005c }
    L_0x001c:
        return r0;
    L_0x001d:
        r2 = com.huluxia.mcsdk.dtlib.h.CW();	 Catch:{ Exception -> 0x005c }
        r2 = r2.CX();	 Catch:{ Exception -> 0x005c }
        if (r2 == r4) goto L_0x0052;
    L_0x0027:
        r2 = com.huluxia.mcsdk.dtlib.h.CW();	 Catch:{ Exception -> 0x005c }
        r2 = r2.CX();	 Catch:{ Exception -> 0x005c }
        r3 = 2;
        if (r2 == r3) goto L_0x0052;
    L_0x0032:
        r2 = com.huluxia.mcsdk.dtlib.h.CW();	 Catch:{ Exception -> 0x005c }
        r2 = r2.CX();	 Catch:{ Exception -> 0x005c }
        r3 = 3;
        if (r2 == r3) goto L_0x0052;
    L_0x003d:
        r2 = com.huluxia.mcsdk.dtlib.h.CW();	 Catch:{ Exception -> 0x005c }
        r2 = r2.CX();	 Catch:{ Exception -> 0x005c }
        r3 = 5;
        if (r2 == r3) goto L_0x0052;
    L_0x0048:
        r2 = com.huluxia.mcsdk.dtlib.h.CW();	 Catch:{ Exception -> 0x005c }
        r2 = r2.CZ();	 Catch:{ Exception -> 0x005c }
        if (r2 != r4) goto L_0x001c;
    L_0x0052:
        r2 = com.huluxia.mcsdk.DTSDKManagerEx.Cq();	 Catch:{ Exception -> 0x005c }
        r0 = com.huluxia.mcsdk.DTSDKManagerEx.ah(r2);	 Catch:{ Exception -> 0x005c }
        r0 = (double) r0;
        goto L_0x001c;
    L_0x005c:
        r2 = move-exception;
        goto L_0x001c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huluxia.mcjavascript.c.get_config_sp_intVal(java.lang.Object):double");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static double Q(java.lang.Object r5) {
        /*
        r4 = 1;
        r0 = 0;
        if (r5 == 0) goto L_0x0009;
    L_0x0005:
        r2 = r5 instanceof java.lang.Number;	 Catch:{ Exception -> 0x005c }
        if (r2 != 0) goto L_0x0009;
    L_0x0009:
        r2 = com.huluxia.mcsdk.dtlib.h.CW();	 Catch:{ Exception -> 0x005c }
        r2 = r2.CX();	 Catch:{ Exception -> 0x005c }
        if (r2 != 0) goto L_0x001d;
    L_0x0013:
        r2 = com.huluxia.mcsdk.DTSDKManagerEx.Cp();	 Catch:{ Exception -> 0x005c }
        r0 = com.huluxia.mcsdk.DTSDKManagerEx.iz(r2);	 Catch:{ Exception -> 0x005c }
        r0 = (double) r0;	 Catch:{ Exception -> 0x005c }
    L_0x001c:
        return r0;
    L_0x001d:
        r2 = com.huluxia.mcsdk.dtlib.h.CW();	 Catch:{ Exception -> 0x005c }
        r2 = r2.CX();	 Catch:{ Exception -> 0x005c }
        if (r2 == r4) goto L_0x0052;
    L_0x0027:
        r2 = com.huluxia.mcsdk.dtlib.h.CW();	 Catch:{ Exception -> 0x005c }
        r2 = r2.CX();	 Catch:{ Exception -> 0x005c }
        r3 = 2;
        if (r2 == r3) goto L_0x0052;
    L_0x0032:
        r2 = com.huluxia.mcsdk.dtlib.h.CW();	 Catch:{ Exception -> 0x005c }
        r2 = r2.CX();	 Catch:{ Exception -> 0x005c }
        r3 = 3;
        if (r2 == r3) goto L_0x0052;
    L_0x003d:
        r2 = com.huluxia.mcsdk.dtlib.h.CW();	 Catch:{ Exception -> 0x005c }
        r2 = r2.CX();	 Catch:{ Exception -> 0x005c }
        r3 = 5;
        if (r2 == r3) goto L_0x0052;
    L_0x0048:
        r2 = com.huluxia.mcsdk.dtlib.h.CW();	 Catch:{ Exception -> 0x005c }
        r2 = r2.CZ();	 Catch:{ Exception -> 0x005c }
        if (r2 != r4) goto L_0x001c;
    L_0x0052:
        r2 = com.huluxia.mcsdk.DTSDKManagerEx.Cq();	 Catch:{ Exception -> 0x005c }
        r0 = com.huluxia.mcsdk.DTSDKManagerEx.au(r2);	 Catch:{ Exception -> 0x005c }
        r0 = (double) r0;
        goto L_0x001c;
    L_0x005c:
        r2 = move-exception;
        goto L_0x001c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huluxia.mcjavascript.c.Q(java.lang.Object):double");
    }

    public static void c(Object paramObject1, Object paramObject2) {
        try {
            if (h.CW().CX() == 0) {
                DTSDKManagerEx.ad(a.ar(paramObject1), a.ar(paramObject2));
            } else if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                DTSDKManagerEx.d(a.aq(paramObject1), a.aq(paramObject2));
            }
        } catch (Exception e) {
        }
    }

    public static void a(Object paramObject, double paramDouble1, double paramDouble2, double paramDouble3) {
        try {
            if (h.CW().CX() == 0) {
                DTSDKManagerEx.a(a.ar(paramObject), (float) paramDouble1, (float) paramDouble2, (float) paramDouble3);
            } else if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                DTSDKManagerEx.a(a.aq(paramObject), (float) paramDouble1, (float) paramDouble2, (float) paramDouble3);
            }
        } catch (Exception e) {
            com.huluxia.mcsdk.log.a.verbose("TAG", "DTPrint setPositionEx Error！---", new Object[0]);
        }
    }

    public static void b(Object paramObject, double paramDouble1, double paramDouble2, double paramDouble3) {
        try {
            if (h.CW().CX() == 0) {
                DTSDKManagerEx.b(a.ar(paramObject), (float) paramDouble1, (float) paramDouble2, (float) paramDouble3);
            } else if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                DTSDKManagerEx.b(a.aq(paramObject), (float) paramDouble1, (float) paramDouble2, (float) paramDouble3);
            }
        } catch (Exception e) {
        }
    }

    public static void a(Object paramObject, double paramDouble1, double paramDouble2) {
        try {
            if (h.CW().CX() == 0) {
                DTSDKManagerEx.a(a.ar(paramObject), (float) paramDouble1, (float) paramDouble2);
            } else if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                DTSDKManagerEx.b(a.aq(paramObject), (float) paramDouble1, (float) paramDouble2);
            }
        } catch (Exception e) {
        }
    }

    public static void a(Object paramObject, double paramDouble) {
        try {
            if (h.CW().CX() == 0) {
                DTSDKManagerEx.a(a.ar(paramObject), (float) paramDouble, 0);
            } else if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                DTSDKManagerEx.a(a.aq(paramObject), (float) paramDouble, 0);
            }
        } catch (Exception e) {
        }
    }

    public static void b(Object paramObject, double paramDouble) {
        try {
            if (h.CW().CX() == 0) {
                DTSDKManagerEx.a(a.ar(paramObject), (float) paramDouble, 1);
            } else if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                DTSDKManagerEx.a(a.aq(paramObject), (float) paramDouble, 1);
            }
        } catch (Exception e) {
            com.huluxia.mcsdk.log.a.verbose("TAG", "DTPrint setVelY dtNativeSetVel_V0110 Error ...", new Object[0]);
        }
    }

    public static void c(Object paramObject, double paramDouble) {
        try {
            if (h.CW().CX() == 0) {
                DTSDKManagerEx.a(a.ar(paramObject), (float) paramDouble, 2);
            } else if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                DTSDKManagerEx.a(a.aq(paramObject), (float) paramDouble, 2);
            }
        } catch (Exception e) {
        }
    }
}
