package io.netty.channel.udt.nio;

import com.barchart.udt.SocketUDT;
import com.barchart.udt.TypeUDT;
import com.barchart.udt.nio.ChannelUDT;
import com.barchart.udt.nio.KindUDT;
import com.barchart.udt.nio.RendezvousChannelUDT;
import com.barchart.udt.nio.SelectorProviderUDT;
import com.barchart.udt.nio.ServerSocketChannelUDT;
import com.barchart.udt.nio.SocketChannelUDT;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFactory;
import io.netty.channel.udt.UdtChannel;
import io.netty.channel.udt.UdtServerChannel;
import java.io.IOException;
import java.nio.channels.spi.SelectorProvider;

public final class NioUdtProvider<T extends UdtChannel> implements ChannelFactory<T> {
    public static final ChannelFactory<UdtServerChannel> BYTE_ACCEPTOR = new NioUdtProvider(TypeUDT.STREAM, KindUDT.ACCEPTOR);
    public static final ChannelFactory<UdtChannel> BYTE_CONNECTOR = new NioUdtProvider(TypeUDT.STREAM, KindUDT.CONNECTOR);
    public static final SelectorProvider BYTE_PROVIDER = SelectorProviderUDT.STREAM;
    public static final ChannelFactory<UdtChannel> BYTE_RENDEZVOUS = new NioUdtProvider(TypeUDT.STREAM, KindUDT.RENDEZVOUS);
    public static final ChannelFactory<UdtServerChannel> MESSAGE_ACCEPTOR = new NioUdtProvider(TypeUDT.DATAGRAM, KindUDT.ACCEPTOR);
    public static final ChannelFactory<UdtChannel> MESSAGE_CONNECTOR = new NioUdtProvider(TypeUDT.DATAGRAM, KindUDT.CONNECTOR);
    public static final SelectorProvider MESSAGE_PROVIDER = SelectorProviderUDT.DATAGRAM;
    public static final ChannelFactory<UdtChannel> MESSAGE_RENDEZVOUS = new NioUdtProvider(TypeUDT.DATAGRAM, KindUDT.RENDEZVOUS);
    private final KindUDT kind;
    private final TypeUDT type;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$barchart$udt$TypeUDT = new int[TypeUDT.values().length];
        static final /* synthetic */ int[] $SwitchMap$com$barchart$udt$nio$KindUDT = new int[KindUDT.values().length];

        static {
            try {
                $SwitchMap$com$barchart$udt$nio$KindUDT[KindUDT.ACCEPTOR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$barchart$udt$nio$KindUDT[KindUDT.CONNECTOR.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$barchart$udt$nio$KindUDT[KindUDT.RENDEZVOUS.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$barchart$udt$TypeUDT[TypeUDT.DATAGRAM.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$com$barchart$udt$TypeUDT[TypeUDT.STREAM.ordinal()] = 2;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public static ChannelUDT channelUDT(Channel channel) {
        if (channel instanceof NioUdtByteAcceptorChannel) {
            return ((NioUdtByteAcceptorChannel) channel).javaChannel();
        }
        if (channel instanceof NioUdtByteRendezvousChannel) {
            return ((NioUdtByteRendezvousChannel) channel).javaChannel();
        }
        if (channel instanceof NioUdtByteConnectorChannel) {
            return ((NioUdtByteConnectorChannel) channel).javaChannel();
        }
        if (channel instanceof NioUdtMessageAcceptorChannel) {
            return ((NioUdtMessageAcceptorChannel) channel).javaChannel();
        }
        if (channel instanceof NioUdtMessageRendezvousChannel) {
            return ((NioUdtMessageRendezvousChannel) channel).javaChannel();
        }
        if (channel instanceof NioUdtMessageConnectorChannel) {
            return ((NioUdtMessageConnectorChannel) channel).javaChannel();
        }
        return null;
    }

    static ServerSocketChannelUDT newAcceptorChannelUDT(TypeUDT type) {
        try {
            return SelectorProviderUDT.from(type).openServerSocketChannel();
        } catch (IOException e) {
            throw new ChannelException("failed to open a_isRightVersion server socket channel", e);
        }
    }

    static SocketChannelUDT newConnectorChannelUDT(TypeUDT type) {
        try {
            return SelectorProviderUDT.from(type).openSocketChannel();
        } catch (IOException e) {
            throw new ChannelException("failed to open a_isRightVersion socket channel", e);
        }
    }

    static RendezvousChannelUDT newRendezvousChannelUDT(TypeUDT type) {
        try {
            return SelectorProviderUDT.from(type).openRendezvousChannel();
        } catch (IOException e) {
            throw new ChannelException("failed to open a_isRightVersion rendezvous channel", e);
        }
    }

    public static SocketUDT socketUDT(Channel channel) {
        ChannelUDT channelUDT = channelUDT(channel);
        if (channelUDT == null) {
            return null;
        }
        return channelUDT.socketUDT();
    }

    private NioUdtProvider(TypeUDT type, KindUDT kind) {
        this.type = type;
        this.kind = kind;
    }

    public KindUDT kind() {
        return this.kind;
    }

    public T newChannel() {
        switch (AnonymousClass1.$SwitchMap$com$barchart$udt$nio$KindUDT[this.kind.ordinal()]) {
            case 1:
                switch (AnonymousClass1.$SwitchMap$com$barchart$udt$TypeUDT[this.type.ordinal()]) {
                    case 1:
                        return new NioUdtMessageAcceptorChannel();
                    case 2:
                        return new NioUdtByteAcceptorChannel();
                    default:
                        throw new IllegalStateException("wrong type=" + this.type);
                }
            case 2:
                switch (AnonymousClass1.$SwitchMap$com$barchart$udt$TypeUDT[this.type.ordinal()]) {
                    case 1:
                        return new NioUdtMessageConnectorChannel();
                    case 2:
                        return new NioUdtByteConnectorChannel();
                    default:
                        throw new IllegalStateException("wrong type=" + this.type);
                }
            case 3:
                switch (AnonymousClass1.$SwitchMap$com$barchart$udt$TypeUDT[this.type.ordinal()]) {
                    case 1:
                        return new NioUdtMessageRendezvousChannel();
                    case 2:
                        return new NioUdtByteRendezvousChannel();
                    default:
                        throw new IllegalStateException("wrong type=" + this.type);
                }
            default:
                throw new IllegalStateException("wrong kind=" + this.kind);
        }
    }

    public TypeUDT type() {
        return this.type;
    }
}
