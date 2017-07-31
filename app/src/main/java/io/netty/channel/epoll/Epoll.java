package io.netty.channel.epoll;

import io.netty.channel.unix.FileDescriptor;
import io.netty.util.internal.PlatformDependent;

public final class Epoll {
    private static final Throwable UNAVAILABILITY_CAUSE;

    static {
        Throwable cause = null;
        FileDescriptor epollFd = null;
        FileDescriptor eventFd = null;
        try {
            epollFd = Native.newEpollCreate();
            eventFd = Native.newEventFd();
            if (epollFd != null) {
                try {
                    epollFd.close();
                } catch (Exception e) {
                }
            }
            if (eventFd != null) {
                try {
                    eventFd.close();
                } catch (Exception e2) {
                }
            }
        } catch (Throwable th) {
            if (epollFd != null) {
                try {
                    epollFd.close();
                } catch (Exception e3) {
                }
            }
            if (eventFd != null) {
                try {
                    eventFd.close();
                } catch (Exception e4) {
                }
            }
        }
        if (cause != null) {
            UNAVAILABILITY_CAUSE = cause;
        } else {
            UNAVAILABILITY_CAUSE = PlatformDependent.hasUnsafe() ? null : new IllegalStateException("sun.misc.Unsafe not available");
        }
    }

    public static boolean isAvailable() {
        return UNAVAILABILITY_CAUSE == null;
    }

    public static void ensureAvailability() {
        if (UNAVAILABILITY_CAUSE != null) {
            throw ((Error) new UnsatisfiedLinkError("failed to load the required native library").initCause(UNAVAILABILITY_CAUSE));
        }
    }

    public static Throwable unavailabilityCause() {
        return UNAVAILABILITY_CAUSE;
    }

    private Epoll() {
    }
}
