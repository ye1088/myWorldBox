package io.netty.util;

public interface TimerTask {
    void run(Timeout timeout) throws Exception;
}
