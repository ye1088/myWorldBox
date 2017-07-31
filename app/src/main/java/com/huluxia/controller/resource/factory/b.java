package com.huluxia.controller.resource.factory;

import com.huluxia.controller.resource.ResourceCtrl;
import com.huluxia.controller.resource.bean.ResTaskInfo;
import com.huluxia.controller.resource.handler.impl.c;
import com.huluxia.controller.resource.handler.impl.g;
import com.huluxia.controller.resource.handler.impl.i;
import com.huluxia.controller.resource.handler.impl.k;
import com.huluxia.controller.resource.handler.impl.l;
import com.huluxia.controller.resource.handler.impl.n;
import com.huluxia.controller.resource.handler.impl.r;
import com.huluxia.controller.resource.handler.impl.s;
import com.huluxia.controller.resource.handler.impl.t;
import com.huluxia.controller.resource.handler.impl.u;
import com.huluxia.controller.resource.handler.segments.impl.d;
import com.huluxia.controller.resource.handler.segments.impl.e;
import com.huluxia.controller.resource.handler.segments.impl.f;
import com.huluxia.controller.resource.handler.segments.impl.h;
import com.huluxia.controller.resource.handler.segments.impl.j;
import com.huluxia.controller.resource.handler.segments.impl.m;
import com.huluxia.controller.resource.handler.segments.impl.o;
import com.huluxia.controller.resource.handler.segments.impl.p;
import com.huluxia.framework.base.log.HLog;
import java.lang.annotation.Annotation;

/* compiled from: ResHandlerFactory */
public class b<T extends ResTaskInfo> implements a<T> {

    /* compiled from: ResHandlerFactory */
    public static class a {
        public static final int NAME = 16;
        public static final int nA = 1000003;
        public static final int nf = 0;
        public static final int ng = 1;
        public static final int nh = 2;
        public static final int ni = 5;
        public static final int nj = 6;
        public static final int nk = 7;
        public static final int nl = 8;
        public static final int nm = 9;
        public static final int nn = 10;
        public static final int no = 11;
        public static final int np = 12;
        public static final int nq = 13;
        public static final int nr = 14;
        public static final int ns = 15;
        public static final int nt = 17;
        public static final int nu = 18;
        public static final int nv = 20;
        public static final int nw = 22;
        public static final int nx = 1000000;
        public static final int ny = 1000001;
        public static final int nz = 1000002;
    }

    public com.huluxia.controller.resource.handler.base.a a(T info) {
        switch (info.mM) {
            case 0:
                if (info.mZ == null) {
                    return new com.huluxia.controller.resource.handler.impl.a(info);
                }
                return new com.huluxia.controller.resource.handler.segments.impl.a(info);
            case 1:
                if (info.mZ == null) {
                    return new r(info);
                }
                return new m(info);
            case 2:
                if (info.mZ == null) {
                    return new k(info);
                }
                return new h(info);
            case 5:
                if (info.mZ == null) {
                    return new g(info);
                }
                return new e(info);
            case 6:
                if (info.mZ == null) {
                    return new i(info);
                }
                return new f(info);
            case 7:
                if (info.mZ == null) {
                    return new c(info);
                }
                return new com.huluxia.controller.resource.handler.segments.impl.b(info);
            case 8:
                if (info.mZ == null) {
                    return new com.huluxia.controller.resource.handler.impl.e(info);
                }
                return new com.huluxia.controller.resource.handler.segments.impl.c(info);
            case 9:
                if (info.mZ == null) {
                    return new com.huluxia.controller.resource.handler.impl.f(info);
                }
                return new d(info);
            case 10:
                if (info.mZ == null) {
                    return new com.huluxia.controller.resource.handler.impl.m(info);
                }
                return new j(info);
            case 11:
                if (info.mZ == null) {
                    return new n(info);
                }
                return new com.huluxia.controller.resource.handler.segments.impl.k(info);
            case 12:
                if (info.mZ == null) {
                    return new s(info);
                }
                return new com.huluxia.controller.resource.handler.segments.impl.n(info);
            case 13:
                if (info.mZ == null) {
                    return new t(info);
                }
                return new o(info);
            case 14:
                if (info.mZ == null) {
                    return new l(info);
                }
                return new com.huluxia.controller.resource.handler.segments.impl.i(info);
            case 15:
                if (info.mZ == null) {
                    return new com.huluxia.controller.resource.handler.impl.o(info);
                }
                return new com.huluxia.controller.resource.handler.segments.impl.l(info);
            case 20:
                if (info.mZ == null) {
                    return new com.huluxia.controller.resource.handler.impl.j(info);
                }
                return new com.huluxia.controller.resource.handler.segments.impl.g(info);
            case 100:
                if (info.mZ == null) {
                    return new u(info);
                }
                return new p(info);
            default:
                try {
                    Class<? extends com.huluxia.controller.resource.handler.base.b<? extends ResTaskInfo>> handlerClass = ResourceCtrl.getInstance().getHandleClz(info.mM);
                    if (handlerClass != null) {
                        com.huluxia.controller.resource.handler.base.b bVar = (com.huluxia.controller.resource.handler.base.b) handlerClass.getDeclaredConstructor(new Class[]{info.getClass()}).newInstance(new Object[]{info});
                        if (info.mZ == null) {
                            return bVar;
                        }
                        Annotation[] annotations = bVar.getClass().getDeclaredAnnotations();
                        if (annotations == null || annotations.length <= 0) {
                            return bVar;
                        }
                        for (Annotation annotation : annotations) {
                            if (com.huluxia.controller.resource.handler.segments.e.class.equals(annotation.annotationType())) {
                                return (com.huluxia.controller.resource.handler.base.b) ((com.huluxia.controller.resource.handler.segments.e) annotation).dT().getDeclaredConstructor(new Class[]{info.getClass()}).newInstance(new Object[]{info});
                            }
                        }
                        return bVar;
                    }
                } catch (Exception e) {
                    HLog.error(this, "handle new instance failed, info %s", new Object[]{info});
                }
                return null;
        }
    }
}
