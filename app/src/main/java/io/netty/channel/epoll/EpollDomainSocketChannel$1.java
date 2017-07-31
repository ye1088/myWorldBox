package io.netty.channel.epoll;

import io.netty.channel.unix.DomainSocketReadMode;

/* synthetic */ class EpollDomainSocketChannel$1 {
    static final /* synthetic */ int[] $SwitchMap$io$netty$channel$unix$DomainSocketReadMode = new int[DomainSocketReadMode.values().length];

    static {
        try {
            $SwitchMap$io$netty$channel$unix$DomainSocketReadMode[DomainSocketReadMode.BYTES.ordinal()] = 1;
        } catch (NoSuchFieldError e) {
        }
        try {
            $SwitchMap$io$netty$channel$unix$DomainSocketReadMode[DomainSocketReadMode.FILE_DESCRIPTORS.ordinal()] = 2;
        } catch (NoSuchFieldError e2) {
        }
    }
}
