package io.netty.util;

public interface Timeout {
    boolean cancel();

    boolean isCancelled();

    boolean isExpired();

    TimerTask task();

    Timer timer();
}
