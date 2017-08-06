package io.netty.util;

import io.netty.util.internal.StringUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

public final class ReferenceCountUtil {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(ReferenceCountUtil.class);

    private static final class ReleasingTask implements Runnable {
        private final int decrement;
        private final ReferenceCounted obj;

        ReleasingTask(ReferenceCounted obj, int decrement) {
            this.obj = obj;
            this.decrement = decrement;
        }

        public void run() {
            try {
                if (this.obj.release(this.decrement)) {
                    ReferenceCountUtil.logger.debug("Released: {}", (Object) this);
                } else {
                    ReferenceCountUtil.logger.warn("Non-zero refCnt: {}", (Object) this);
                }
            } catch (Exception ex) {
                ReferenceCountUtil.logger.warn("Failed to release an object: {}", this.obj, ex);
            }
        }

        public String toString() {
            return StringUtil.simpleClassName(this.obj) + ".release(" + this.decrement + ") refCnt: " + this.obj.refCnt();
        }
    }

    public static <T> T retain(T t) {
        if (t instanceof ReferenceCounted) {
            return ((ReferenceCounted) t).retain();
        }
        return t;
    }

    public static <T> T retain(T t, int increment) {
        if (t instanceof ReferenceCounted) {
            return ((ReferenceCounted) t).retain(increment);
        }
        return t;
    }

    public static <T> T touch(T t) {
        if (t instanceof ReferenceCounted) {
            return ((ReferenceCounted) t).touch();
        }
        return t;
    }

    public static <T> T touch(T t, Object hint) {
        if (t instanceof ReferenceCounted) {
            return ((ReferenceCounted) t).touch(hint);
        }
        return t;
    }

    public static boolean release(Object msg) {
        if (msg instanceof ReferenceCounted) {
            return ((ReferenceCounted) msg).release();
        }
        return false;
    }

    public static boolean release(Object msg, int decrement) {
        if (msg instanceof ReferenceCounted) {
            return ((ReferenceCounted) msg).release(decrement);
        }
        return false;
    }

    public static void safeRelease(Object msg) {
        try {
            release(msg);
        } catch (Throwable t) {
            logger.warn("Failed to release a_isRightVersion message: {}", msg, t);
        }
    }

    public static void safeRelease(Object msg, int decrement) {
        try {
            release(msg, decrement);
        } catch (Throwable t) {
            if (logger.isWarnEnabled()) {
                logger.warn("Failed to release a_isRightVersion message: {} (decrement: {})", msg, Integer.valueOf(decrement), t);
            }
        }
    }

    public static <T> T releaseLater(T msg) {
        return releaseLater(msg, 1);
    }

    public static <T> T releaseLater(T msg, int decrement) {
        if (msg instanceof ReferenceCounted) {
            ThreadDeathWatcher.watch(Thread.currentThread(), new ReleasingTask((ReferenceCounted) msg, decrement));
        }
        return msg;
    }

    private ReferenceCountUtil() {
    }
}
