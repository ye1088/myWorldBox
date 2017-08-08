package com.MCWorld.mcjavascript;

import com.MCWorld.mcgame.g;
import com.MCWorld.mcsdk.DTSDKManagerEx;
import com.MCWorld.mcsdk.dtlib.a;
import com.MCWorld.mcsdk.dtlib.h;
import net.zhuoweizhang.mcpelauncher.ScriptManager;
import net.zhuoweizhang.mcpelauncher.api.modpe.MobEffect;

/* compiled from: DTNativeEntityApiEx */
public class j {
    private static final boolean DEBUG = false;

    public static Object b(double paramDouble1, double paramDouble2, double paramDouble3, int paramInt, String paramString) {
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

    public static void a(Object paramObject, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2) {
        try {
            if ((h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5) && h.CW().CZ() != 1) {
                int i = ScriptManager.nativeGetEntityTypeId(a.aq(paramObject));
                if (i > 0 && i < 64) {
                    ScriptManager.nativeMobAddEffect(a.aq(paramObject), paramInt1, paramInt2, paramInt3, paramBoolean1, paramBoolean2);
                }
            }
        } catch (Exception e) {
            com.MCWorld.mcsdk.log.a.verbose(com.MCWorld.mcsdk.log.a.aoP, "DTPrint is " + e, new Object[0]);
        }
    }

    public static Object[] As() {
        try {
            int i;
            if (h.CW().CX() == 0) {
                Object[] objArr = new Object[g.adZ.size()];
                for (i = 0; objArr.length > i; i++) {
                    objArr[i] = Integer.valueOf(((Integer) g.adZ.get(i)).intValue());
                }
                return objArr;
            } else if (h.CW().CX() != 1 && h.CW().CX() != 2 && h.CW().CX() != 3 && h.CW().CX() != 5 && h.CW().CZ() != 1) {
                return null;
            } else {
                Object[] arrayOfLong = new Object[g.aea.size()];
                for (i = 0; arrayOfLong.length > i; i++) {
                    arrayOfLong[i] = Long.valueOf(((Long) g.aea.get(i)).longValue());
                }
                return arrayOfLong;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static int R(Object paramObject) {
        try {
            if (h.CW().CX() == 0) {
                return DTSDKManagerEx.is(a.ar(paramObject));
            }
            if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                return DTSDKManagerEx.an(a.aq(paramObject));
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public static int S(Object paramObject) {
        try {
            if (h.CW().CX() == 0) {
                return DTSDKManagerEx.in(a.ar(paramObject));
            }
            if (h.CW().CX() == 1) {
                return DTSDKManagerEx.ai(a.aq(paramObject));
            }
            if (h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                return DTSDKManagerEx.ai(a.aq(paramObject));
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public static int T(Object paramObject) {
        try {
            if (h.CW().CX() == 0) {
                return DTSDKManagerEx.io(a.ar(paramObject));
            }
            if (h.CW().CX() == 1) {
                return DTSDKManagerEx.al(a.aq(paramObject));
            }
            if (h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                return DTSDKManagerEx.al(a.aq(paramObject));
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public static String U(Object paramObject) {
        try {
            if (h.CW().CX() == 0) {
                return DTSDKManagerEx.iq(a.ar(paramObject));
            }
            if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                return DTSDKManagerEx.am(a.aq(paramObject));
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public static String V(Object paramObject) {
        try {
            if (h.CW().CX() == 0) {
                return DTSDKManagerEx.ix(a.ar(paramObject));
            }
            if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                return DTSDKManagerEx.as(a.aq(paramObject));
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public static double Q(Object paramObject) {
        try {
            if (h.CW().CX() == 0) {
                return (double) DTSDKManagerEx.iz(a.ar(paramObject));
            }
            if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                return (double) DTSDKManagerEx.au(a.aq(paramObject));
            }
            return 0.0d;
        } catch (Exception e) {
            return 0.0d;
        }
    }

    public static int W(Object paramObject) {
        try {
            if (h.CW().CX() == 0) {
                return DTSDKManagerEx.iw(a.ar(paramObject));
            }
            if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                return DTSDKManagerEx.ar(a.aq(paramObject));
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public static int X(Object paramObject) {
        try {
            if (h.CW().CX() == 0) {
                return DTSDKManagerEx.iv(a.ar(paramObject));
            }
            if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                return DTSDKManagerEx.aq(a.aq(paramObject));
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public static int Y(Object paramObject) {
        try {
            if (h.CW().CX() == 0) {
                return DTSDKManagerEx.iu(a.ar(paramObject));
            }
            if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                return DTSDKManagerEx.ap(a.aq(paramObject));
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    public static String Z(Object paramObject) {
        try {
            if (h.CW().CX() == 0) {
                return DTSDKManagerEx.iB(a.ar(paramObject));
            }
            if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                return DTSDKManagerEx.ax(a.aq(paramObject));
            }
            return "";
        } catch (Exception e) {
            return "";
        }
    }

    public static double aa(Object paramObject) {
        try {
            if (h.CW().CX() == 0) {
                return (double) DTSDKManagerEx.ab(a.ar(paramObject), 0);
            }
            if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                return (double) DTSDKManagerEx.k(a.aq(paramObject), 0);
            }
            return 0.0d;
        } catch (Exception e) {
            return 0.0d;
        }
    }

    public static double ab(Object paramObject) {
        try {
            if (h.CW().CX() == 0) {
                return (double) DTSDKManagerEx.ab(a.ar(paramObject), 1);
            }
            if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                return (double) DTSDKManagerEx.k(a.aq(paramObject), 1);
            }
            return 0.0d;
        } catch (Exception e) {
            return 0.0d;
        }
    }

    public static double ac(Object paramObject) {
        try {
            if (h.CW().CX() == 0) {
                return (double) DTSDKManagerEx.ab(a.ar(paramObject), 2);
            }
            if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                return (double) DTSDKManagerEx.k(a.aq(paramObject), 2);
            }
            return 0.0d;
        } catch (Exception e) {
            return 0.0d;
        }
    }

    public static double ad(Object paramObject) {
        try {
            if (h.CW().CX() == 0) {
                return (double) DTSDKManagerEx.W(a.ar(paramObject), 0);
            }
            if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                return (double) DTSDKManagerEx.e(a.aq(paramObject), 0);
            }
            return 0.0d;
        } catch (Exception e) {
            return 0.0d;
        }
    }

    public static double ae(Object paramObject) {
        try {
            if (h.CW().CX() == 0) {
                return (double) DTSDKManagerEx.W(a.ar(paramObject), 1);
            }
            if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                return (double) DTSDKManagerEx.e(a.aq(paramObject), 1);
            }
            return 0.0d;
        } catch (Exception e) {
            return 0.0d;
        }
    }

    public static double P(Object paramObject) {
        try {
            if (h.CW().CX() == 0) {
                return (double) DTSDKManagerEx.im(a.ar(paramObject));
            }
            if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                return (double) DTSDKManagerEx.ah(a.aq(paramObject));
            }
            return 0.0d;
        } catch (Exception e) {
            return 0.0d;
        }
    }

    public static double af(Object paramObject) {
        try {
            if (h.CW().CX() == 0) {
                return (double) DTSDKManagerEx.W(a.ar(paramObject), 2);
            }
            if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                return (double) DTSDKManagerEx.e(a.aq(paramObject), 2);
            }
            return 0.0d;
        } catch (Exception e) {
            return 0.0d;
        }
    }

    public static void ag(Object paramObject) {
        try {
            if (h.CW().CX() == 0) {
                DTSDKManagerEx.it(a.ar(paramObject));
            } else if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                DTSDKManagerEx.ao(a.aq(paramObject));
            }
        } catch (Exception e) {
        }
    }

    public static void c(Object paramObject, Object paramInt2) {
        try {
            if (h.CW().CX() == 0) {
                DTSDKManagerEx.ad(a.ar(paramObject), a.ar(paramInt2));
            } else if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                DTSDKManagerEx.d(a.aq(paramObject), a.aq(paramInt2));
            }
        } catch (Exception e) {
        }
    }

    public static void a(Object paramObject, int paramInt2) {
        try {
            if (h.CW().CX() == 0) {
                DTSDKManagerEx.X(a.ar(paramObject), paramInt2);
            } else if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                DTSDKManagerEx.f(a.aq(paramObject), paramInt2);
            }
        } catch (Exception e) {
        }
    }

    public static void d(Object paramObject, String paramString) {
        try {
            if ((h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) && (paramObject instanceof Integer)) {
                int i = DTSDKManagerEx.ai(a.aq(paramObject));
                if (i >= 32 && i < 64) {
                    DTSDKManagerEx.e(a.aq(paramObject), paramString);
                }
            }
        } catch (Exception e) {
        }
    }

    public static void a(Object paramObject, int paramInt2, int paramInt3, int paramInt4) {
        try {
            if (h.CW().CX() == 0) {
                DTSDKManagerEx.h(a.ar(paramObject), paramInt2, paramInt3, paramInt4);
            } else if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                DTSDKManagerEx.a(a.aq(paramObject), paramInt2, paramInt3, paramInt4);
            }
        } catch (Exception e) {
        }
    }

    public static void b(Object paramObject, double paramDouble1, double paramDouble2) {
        try {
            if (h.CW().CX() == 0) {
                DTSDKManagerEx.dtNativeEntitySetSize(a.ar(paramObject), (float) paramDouble1, (float) paramDouble2);
            } else if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                DTSDKManagerEx.a(a.aq(paramObject), (float) paramDouble1, (float) paramDouble2);
            }
        } catch (Exception e) {
        }
    }

    public static void b(Object paramObject, int paramInt2) {
        try {
            if (h.CW().CX() == 0) {
                DTSDKManagerEx.Z(a.ar(paramObject), paramInt2);
            } else if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                DTSDKManagerEx.h(a.aq(paramObject), paramInt2);
            }
        } catch (Exception e) {
        }
    }

    public static void c(Object paramObject, int paramInt2) {
        try {
            if (h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CX() == 7 || h.CW().CZ() == 1) {
                DTSDKManagerEx.j(a.aq(paramObject), paramInt2);
            }
        } catch (Exception e) {
        }
    }

    public static void d(Object paramObject, int paramInt2) {
        try {
            if (h.CW().CX() == 0) {
                DTSDKManagerEx.aa(a.ar(paramObject), paramInt2);
            } else if (h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                DTSDKManagerEx.i(a.aq(paramObject), paramInt2);
            }
        } catch (Exception e) {
        }
    }

    public static void e(Object paramObject, String paramString) {
        try {
            if (h.CW().CX() == 0) {
                DTSDKManagerEx.h(Integer.valueOf(a.ar(paramObject)), paramString);
            } else if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                DTSDKManagerEx.g(a.aq(paramObject), paramString);
            }
        } catch (Exception e) {
        }
    }

    public static void f(Object paramObject, String paramString) {
        try {
            if (h.CW().CX() == 0) {
                if (DTSDKManagerEx.in(a.ar(paramObject)) >= 64) {
                    throw new IllegalArgumentException("setNameTag only works on mobs");
                }
                DTSDKManagerEx.e(a.ar(paramObject), paramString);
            } else if (h.CW().CX() != 1 && h.CW().CX() != 2 && h.CW().CX() != 3 && h.CW().CX() != 5 && h.CW().CZ() != 1) {
            } else {
                if (DTSDKManagerEx.ai(a.aq(paramObject)) >= 64) {
                    throw new IllegalArgumentException("setNameTag only works on mobs");
                }
                DTSDKManagerEx.f(a.aq(paramObject), paramString);
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
            com.MCWorld.mcsdk.log.a.verbose("TAG", "DTPrint setPositionEx Error!\n", new Object[0]);
        }
    }

    public static void b(Object paramObject, double paramDouble1, double paramDouble2, double paramDouble3) {
        try {
            if (h.CW().CX() == 0) {
                if (paramObject instanceof Integer) {
                    DTSDKManagerEx.b(a.ar(paramObject), (float) paramDouble1, (float) paramDouble2, (float) paramDouble3);
                }
            } else if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                DTSDKManagerEx.b(a.aq(paramObject), (float) paramDouble1, (float) paramDouble2, (float) paramDouble3);
            }
        } catch (Exception e) {
        }
    }

    public static void e(Object paramObject, int paramInt2) {
        try {
            if (h.CW().CX() == 0) {
                DTSDKManagerEx.Y(a.ar(paramObject), paramInt2);
            } else if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                DTSDKManagerEx.g(a.aq(paramObject), paramInt2);
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

    public static void f(Object paramObject, boolean paramBoolean) {
        try {
            if (h.CW().CX() == 0) {
                DTSDKManagerEx.h(a.ar(paramObject), paramBoolean);
            } else if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                DTSDKManagerEx.a(a.aq(paramObject), paramBoolean);
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
            com.MCWorld.mcsdk.log.a.verbose("TAG", "DTPrint setVelY ex is error \n", new Object[0]);
        }
    }

    public static void c(Object paramObject, double paramDouble) {
        try {
            if (h.CW().CX() == 0) {
                if (paramObject instanceof Integer) {
                    DTSDKManagerEx.a(a.ar(paramObject), (float) paramDouble, 2);
                }
            } else if (h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) {
                DTSDKManagerEx.a(a.aq(paramObject), (float) paramDouble, 2);
            }
        } catch (Exception e) {
        }
    }

    public static int ah(Object paramObject) {
        return 0;
    }

    public static int ai(Object paramObject) {
        return 0;
    }

    public static int aj(Object paramObject) {
        return 0;
    }

    public static void ak(Object paramObject) {
        long l = a.aq(paramObject);
        int i = DTSDKManagerEx.ai(l);
        if (i > 0 && i < 64) {
            DTSDKManagerEx.aj(l);
        }
    }

    public static void f(Object paramObject, int paramInt) {
        long l = a.aq(paramObject);
        int i = DTSDKManagerEx.ai(l);
        if (i > 0 && i < 64 && MobEffect.effectIds.get(Integer.valueOf(paramInt)) != null) {
            DTSDKManagerEx.d(l, paramInt);
        }
    }
}
