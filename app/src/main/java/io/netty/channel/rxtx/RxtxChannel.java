package io.netty.channel.rxtx;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import io.netty.channel.AbstractChannel.AbstractUnsafe;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelPromise;
import io.netty.channel.oio.OioByteStreamChannel;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;
import org.apache.tools.mail.MailMessage;

public class RxtxChannel extends OioByteStreamChannel {
    private static final RxtxDeviceAddress LOCAL_ADDRESS = new RxtxDeviceAddress(MailMessage.DEFAULT_HOST);
    private final RxtxChannelConfig config = new DefaultRxtxChannelConfig(this);
    private RxtxDeviceAddress deviceAddress;
    private boolean open = true;
    private SerialPort serialPort;

    private final class RxtxUnsafe extends AbstractUnsafe {
        private RxtxUnsafe() {
            super(RxtxChannel.this);
        }

        public void connect(SocketAddress remoteAddress, SocketAddress localAddress, final ChannelPromise promise) {
            if (promise.setUncancellable() && ensureOpen(promise)) {
                try {
                    final boolean wasActive = RxtxChannel.this.isActive();
                    RxtxChannel.this.doConnect(remoteAddress, localAddress);
                    int waitTime = ((Integer) RxtxChannel.this.config().getOption(RxtxChannelOption.WAIT_TIME)).intValue();
                    if (waitTime > 0) {
                        RxtxChannel.this.eventLoop().schedule(new Runnable() {
                            public void run() {
                                try {
                                    RxtxChannel.this.doInit();
                                    RxtxUnsafe.this.safeSetSuccess(promise);
                                    if (!wasActive && RxtxChannel.this.isActive()) {
                                        RxtxChannel.this.pipeline().fireChannelActive();
                                    }
                                } catch (Throwable t) {
                                    RxtxUnsafe.this.safeSetFailure(promise, t);
                                    RxtxUnsafe.this.closeIfClosed();
                                }
                            }
                        }, (long) waitTime, TimeUnit.MILLISECONDS);
                        return;
                    }
                    RxtxChannel.this.doInit();
                    safeSetSuccess(promise);
                    if (!wasActive && RxtxChannel.this.isActive()) {
                        RxtxChannel.this.pipeline().fireChannelActive();
                    }
                } catch (Throwable t) {
                    safeSetFailure(promise, t);
                    closeIfClosed();
                }
            }
        }
    }

    public RxtxChannel() {
        super(null);
    }

    public RxtxChannelConfig config() {
        return this.config;
    }

    public boolean isOpen() {
        return this.open;
    }

    protected AbstractUnsafe newUnsafe() {
        return new RxtxUnsafe();
    }

    protected void doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
        RxtxDeviceAddress remote = (RxtxDeviceAddress) remoteAddress;
        CommPort commPort = CommPortIdentifier.getPortIdentifier(remote.value()).open(getClass().getName(), 1000);
        commPort.enableReceiveTimeout(((Integer) config().getOption(RxtxChannelOption.READ_TIMEOUT)).intValue());
        this.deviceAddress = remote;
        this.serialPort = (SerialPort) commPort;
    }

    protected void doInit() throws Exception {
        this.serialPort.setSerialPortParams(((Integer) config().getOption(RxtxChannelOption.BAUD_RATE)).intValue(), ((RxtxChannelConfig$Databits) config().getOption(RxtxChannelOption.DATA_BITS)).value(), ((RxtxChannelConfig$Stopbits) config().getOption(RxtxChannelOption.STOP_BITS)).value(), ((RxtxChannelConfig$Paritybit) config().getOption(RxtxChannelOption.PARITY_BIT)).value());
        this.serialPort.setDTR(((Boolean) config().getOption(RxtxChannelOption.DTR)).booleanValue());
        this.serialPort.setRTS(((Boolean) config().getOption(RxtxChannelOption.RTS)).booleanValue());
        activate(this.serialPort.getInputStream(), this.serialPort.getOutputStream());
    }

    public RxtxDeviceAddress localAddress() {
        return (RxtxDeviceAddress) super.localAddress();
    }

    public RxtxDeviceAddress remoteAddress() {
        return (RxtxDeviceAddress) super.remoteAddress();
    }

    protected RxtxDeviceAddress localAddress0() {
        return LOCAL_ADDRESS;
    }

    protected RxtxDeviceAddress remoteAddress0() {
        return this.deviceAddress;
    }

    protected void doBind(SocketAddress localAddress) throws Exception {
        throw new UnsupportedOperationException();
    }

    protected void doDisconnect() throws Exception {
        doClose();
    }

    protected void doClose() throws Exception {
        this.open = false;
        try {
            super.doClose();
        } finally {
            if (this.serialPort != null) {
                this.serialPort.removeEventListener();
                this.serialPort.close();
                this.serialPort = null;
            }
        }
    }

    protected boolean isInputShutdown() {
        return !this.open;
    }

    protected ChannelFuture shutdownInput() {
        return newFailedFuture(new UnsupportedOperationException("shutdownInput"));
    }
}
