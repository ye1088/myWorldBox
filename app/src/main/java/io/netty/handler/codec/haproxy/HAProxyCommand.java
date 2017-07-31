package io.netty.handler.codec.haproxy;

public enum HAProxyCommand {
    LOCAL((byte) 0),
    PROXY((byte) 1);
    
    private static final byte COMMAND_MASK = (byte) 15;
    private final byte byteValue;

    private HAProxyCommand(byte byteValue) {
        this.byteValue = byteValue;
    }

    public static HAProxyCommand valueOf(byte verCmdByte) {
        int cmd = verCmdByte & 15;
        switch ((byte) cmd) {
            case (byte) 0:
                return LOCAL;
            case (byte) 1:
                return PROXY;
            default:
                throw new IllegalArgumentException("unknown command: " + cmd);
        }
    }

    public byte byteValue() {
        return this.byteValue;
    }
}
