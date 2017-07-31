package io.netty.channel.epoll;

final class NativeStaticallyReferencedJniMethods {
    static native int epollerr();

    static native int epollet();

    static native int epollin();

    static native int epollout();

    static native int epollrdhup();

    static native int iovMax();

    static native boolean isSupportingSendmmsg();

    static native boolean isSupportingTcpFastopen();

    static native String kernelVersion();

    static native long ssizeMax();

    static native int tcpMd5SigMaxKeyLen();

    static native int uioMaxIov();

    private NativeStaticallyReferencedJniMethods() {
    }
}
