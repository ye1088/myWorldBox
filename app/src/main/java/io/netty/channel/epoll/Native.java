package io.netty.channel.epoll;

import io.netty.channel.DefaultFileRegion;
import io.netty.channel.unix.Errors;
import io.netty.channel.unix.Errors.NativeIoException;
import io.netty.channel.unix.FileDescriptor;
import io.netty.channel.unix.NativeInetAddress;
import io.netty.util.internal.NativeLibraryLoader;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.ThrowableUtil;
import java.io.IOException;
import java.net.InetAddress;
import java.nio.channels.ClosedChannelException;
import java.util.Locale;

public final class Native {
    public static final int EPOLLERR = NativeStaticallyReferencedJniMethods.epollerr();
    public static final int EPOLLET = NativeStaticallyReferencedJniMethods.epollet();
    public static final int EPOLLIN = NativeStaticallyReferencedJniMethods.epollin();
    public static final int EPOLLOUT = NativeStaticallyReferencedJniMethods.epollout();
    public static final int EPOLLRDHUP = NativeStaticallyReferencedJniMethods.epollrdhup();
    public static final int IOV_MAX = NativeStaticallyReferencedJniMethods.iovMax();
    public static final boolean IS_SUPPORTING_SENDMMSG = NativeStaticallyReferencedJniMethods.isSupportingSendmmsg();
    public static final boolean IS_SUPPORTING_TCP_FASTOPEN = NativeStaticallyReferencedJniMethods.isSupportingTcpFastopen();
    public static final String KERNEL_VERSION = NativeStaticallyReferencedJniMethods.kernelVersion();
    private static final ClosedChannelException SENDFILE_CLOSED_CHANNEL_EXCEPTION = ((ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), Native.class, "sendfile(...)"));
    private static final NativeIoException SENDFILE_CONNECTION_RESET_EXCEPTION = Errors.newConnectionResetException("syscall:sendfile(...)", Errors.ERRNO_EPIPE_NEGATIVE);
    private static final ClosedChannelException SENDMMSG_CLOSED_CHANNEL_EXCEPTION = ((ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), Native.class, "sendmmsg(...)"));
    private static final NativeIoException SENDMMSG_CONNECTION_RESET_EXCEPTION = Errors.newConnectionResetException("syscall:sendmmsg(...)", Errors.ERRNO_EPIPE_NEGATIVE);
    private static final ClosedChannelException SPLICE_CLOSED_CHANNEL_EXCEPTION = ((ClosedChannelException) ThrowableUtil.unknownStackTrace(new ClosedChannelException(), Native.class, "splice(...)"));
    private static final NativeIoException SPLICE_CONNECTION_RESET_EXCEPTION = Errors.newConnectionResetException("syscall:splice(...)", Errors.ERRNO_EPIPE_NEGATIVE);
    public static final long SSIZE_MAX = NativeStaticallyReferencedJniMethods.ssizeMax();
    public static final int TCP_MD5SIG_MAXKEYLEN = NativeStaticallyReferencedJniMethods.tcpMd5SigMaxKeyLen();
    public static final int UIO_MAX_IOV = NativeStaticallyReferencedJniMethods.uioMaxIov();

    private static native int epollCreate();

    private static native int epollCtlAdd0(int i, int i2, int i3);

    private static native int epollCtlDel0(int i, int i2);

    private static native int epollCtlMod0(int i, int i2, int i3);

    private static native int epollWait0(int i, long j, int i2, int i3);

    private static native int eventFd();

    public static native void eventFdRead(int i);

    public static native void eventFdWrite(int i, long j);

    public static native int getTcpKeepCnt(int i) throws IOException;

    public static native int getTcpKeepIdle(int i) throws IOException;

    public static native int getTcpKeepIntvl(int i) throws IOException;

    public static native int getTcpNotSentLowAt(int i) throws IOException;

    public static native int getTcpUserTimeout(int i) throws IOException;

    public static native int getTrafficClass(int i) throws IOException;

    public static native int isBroadcast(int i) throws IOException;

    public static native int isIpFreeBind(int i) throws IOException;

    public static native int isReuseAddress(int i) throws IOException;

    public static native int isReusePort(int i) throws IOException;

    public static native int offsetofEpollData();

    private static native int recvFd0(int i);

    private static native int sendFd0(int i, int i2);

    private static native long sendfile0(int i, DefaultFileRegion defaultFileRegion, long j, long j2, long j3) throws IOException;

    private static native int sendmmsg0(int i, NativeDatagramPacket[] nativeDatagramPacketArr, int i2, int i3);

    public static native void setBroadcast(int i, int i2) throws IOException;

    public static native void setIpFreeBind(int i, int i2) throws IOException;

    public static native void setReuseAddress(int i, int i2) throws IOException;

    public static native void setReusePort(int i, int i2) throws IOException;

    public static native void setTcpFastopen(int i, int i2) throws IOException;

    public static native void setTcpKeepCnt(int i, int i2) throws IOException;

    public static native void setTcpKeepIdle(int i, int i2) throws IOException;

    public static native void setTcpKeepIntvl(int i, int i2) throws IOException;

    private static native void setTcpMd5Sig0(int i, byte[] bArr, int i2, byte[] bArr2) throws IOException;

    public static native void setTcpNotSentLowAt(int i, int i2) throws IOException;

    public static native void setTcpUserTimeout(int i, int i2) throws IOException;

    public static native void setTrafficClass(int i, int i2) throws IOException;

    public static native int sizeofEpollEvent();

    private static native int splice0(int i, long j, int i2, long j2, long j3);

    private static native void tcpInfo0(int i, int[] iArr) throws IOException;

    static {
        try {
            offsetofEpollData();
        } catch (UnsatisfiedLinkError e) {
            loadNativeLibrary();
        }
    }

    public static FileDescriptor newEventFd() {
        return new FileDescriptor(eventFd());
    }

    public static FileDescriptor newEpollCreate() {
        return new FileDescriptor(epollCreate());
    }

    public static int epollWait(int efd, EpollEventArray events, int timeout) throws IOException {
        int ready = epollWait0(efd, events.memoryAddress(), events.length(), timeout);
        if (ready >= 0) {
            return ready;
        }
        throw Errors.newIOException("epoll_wait", ready);
    }

    public static void epollCtlAdd(int efd, int fd, int flags) throws IOException {
        int res = epollCtlAdd0(efd, fd, flags);
        if (res < 0) {
            throw Errors.newIOException("epoll_ctl", res);
        }
    }

    public static void epollCtlMod(int efd, int fd, int flags) throws IOException {
        int res = epollCtlMod0(efd, fd, flags);
        if (res < 0) {
            throw Errors.newIOException("epoll_ctl", res);
        }
    }

    public static void epollCtlDel(int efd, int fd) throws IOException {
        int res = epollCtlDel0(efd, fd);
        if (res < 0) {
            throw Errors.newIOException("epoll_ctl", res);
        }
    }

    public static int splice(int fd, long offIn, int fdOut, long offOut, long len) throws IOException {
        int res = splice0(fd, offIn, fdOut, offOut, len);
        return res >= 0 ? res : Errors.ioResult("splice", res, SPLICE_CONNECTION_RESET_EXCEPTION, SPLICE_CLOSED_CHANNEL_EXCEPTION);
    }

    public static long sendfile(int dest, DefaultFileRegion src, long baseOffset, long offset, long length) throws IOException {
        src.open();
        long res = sendfile0(dest, src, baseOffset, offset, length);
        return res >= 0 ? res : (long) Errors.ioResult("sendfile", (int) res, SENDFILE_CONNECTION_RESET_EXCEPTION, SENDFILE_CLOSED_CHANNEL_EXCEPTION);
    }

    public static int sendmmsg(int fd, NativeDatagramPacket[] msgs, int offset, int len) throws IOException {
        int res = sendmmsg0(fd, msgs, offset, len);
        return res >= 0 ? res : Errors.ioResult("sendmmsg", res, SENDMMSG_CONNECTION_RESET_EXCEPTION, SENDMMSG_CLOSED_CHANNEL_EXCEPTION);
    }

    public static int recvFd(int fd) throws IOException {
        int res = recvFd0(fd);
        if (res > 0) {
            return res;
        }
        if (res == 0) {
            return -1;
        }
        if (res == Errors.ERRNO_EAGAIN_NEGATIVE || res == Errors.ERRNO_EWOULDBLOCK_NEGATIVE) {
            return 0;
        }
        throw Errors.newIOException("recvFd", res);
    }

    public static int sendFd(int socketFd, int fd) throws IOException {
        int res = sendFd0(socketFd, fd);
        if (res >= 0) {
            return res;
        }
        if (res == Errors.ERRNO_EAGAIN_NEGATIVE || res == Errors.ERRNO_EWOULDBLOCK_NEGATIVE) {
            return -1;
        }
        throw Errors.newIOException("sendFd", res);
    }

    public static void tcpInfo(int fd, EpollTcpInfo info) throws IOException {
        tcpInfo0(fd, info.info);
    }

    public static void setTcpMd5Sig(int fd, InetAddress address, byte[] key) throws IOException {
        NativeInetAddress a = NativeInetAddress.newInstance(address);
        setTcpMd5Sig0(fd, a.address(), a.scopeId(), key);
    }

    private Native() {
    }

    private static void loadNativeLibrary() {
        if (SystemPropertyUtil.get("os.name").toLowerCase(Locale.UK).trim().startsWith("linux")) {
            NativeLibraryLoader.load(SystemPropertyUtil.get("io.netty.packagePrefix", "").replace('.', '-') + "netty-transport-native-epoll", PlatformDependent.getClassLoader(Native.class));
            return;
        }
        throw new IllegalStateException("Only supported on Linux");
    }
}
