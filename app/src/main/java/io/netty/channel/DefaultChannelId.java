package io.netty.channel;

import io.netty.buffer.ByteBufUtil;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.MacAddressUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.ThreadLocalRandom;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public final class DefaultChannelId implements ChannelId {
    static final /* synthetic */ boolean $assertionsDisabled = (!DefaultChannelId.class.desiredAssertionStatus());
    private static final byte[] MACHINE_ID;
    private static final int MACHINE_ID_LEN = 8;
    private static final Pattern MACHINE_ID_PATTERN = Pattern.compile("^(?:[0-9a-fA-F][:-]?){6,8}$");
    private static final int MAX_PROCESS_ID = 4194304;
    private static final int PROCESS_ID;
    private static final int PROCESS_ID_LEN = 4;
    private static final int RANDOM_LEN = 4;
    private static final int SEQUENCE_LEN = 4;
    private static final int TIMESTAMP_LEN = 8;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultChannelId.class);
    private static final AtomicInteger nextSequence = new AtomicInteger();
    private static final long serialVersionUID = 3884076183504074063L;
    private final byte[] data = new byte[28];
    private int hashCode;
    private transient String longValue;
    private transient String shortValue;

    static {
        int processId = -1;
        String customProcessId = SystemPropertyUtil.get("io.netty.processId");
        if (customProcessId != null) {
            try {
                processId = Integer.parseInt(customProcessId);
            } catch (NumberFormatException e) {
            }
            if (processId < 0 || processId > 4194304) {
                processId = -1;
                logger.warn("-Dio.netty.processId: {} (malformed)", customProcessId);
            } else if (logger.isDebugEnabled()) {
                logger.debug("-Dio.netty.processId: {} (user-set)", Integer.valueOf(processId));
            }
        }
        if (processId < 0) {
            processId = defaultProcessId();
            if (logger.isDebugEnabled()) {
                logger.debug("-Dio.netty.processId: {} (auto-detected)", Integer.valueOf(processId));
            }
        }
        PROCESS_ID = processId;
        byte[] machineId = null;
        String customMachineId = SystemPropertyUtil.get("io.netty.machineId");
        if (customMachineId != null) {
            if (MACHINE_ID_PATTERN.matcher(customMachineId).matches()) {
                machineId = parseMachineId(customMachineId);
                logger.debug("-Dio.netty.machineId: {} (user-set)", customMachineId);
            } else {
                logger.warn("-Dio.netty.machineId: {} (malformed)", customMachineId);
            }
        }
        if (machineId == null) {
            machineId = defaultMachineId();
            if (logger.isDebugEnabled()) {
                logger.debug("-Dio.netty.machineId: {} (auto-detected)", MacAddressUtil.formatAddress(machineId));
            }
        }
        MACHINE_ID = machineId;
    }

    public static DefaultChannelId newInstance() {
        DefaultChannelId id = new DefaultChannelId();
        id.init();
        return id;
    }

    private static byte[] parseMachineId(String value) {
        value = value.replaceAll("[:-]", "");
        byte[] machineId = new byte[8];
        for (int i = 0; i < value.length(); i += 2) {
            machineId[i] = (byte) Integer.parseInt(value.substring(i, i + 2), 16);
        }
        return machineId;
    }

    private static byte[] defaultMachineId() {
        byte[] bestMacAddr = MacAddressUtil.bestAvailableMac();
        if (bestMacAddr != null) {
            return bestMacAddr;
        }
        bestMacAddr = new byte[8];
        ThreadLocalRandom.current().nextBytes(bestMacAddr);
        logger.warn("Failed to find a_isRightVersion usable hardware address from the network interfaces; using random bytes: {}", MacAddressUtil.formatAddress(bestMacAddr));
        return bestMacAddr;
    }

    private static int defaultProcessId() {
        String value;
        int pid;
        ClassLoader loader = PlatformDependent.getClassLoader(DefaultChannelId.class);
        try {
            Class<?> mgmtFactoryType = Class.forName("java.lang.management.ManagementFactory", true, loader);
            Class<?> runtimeMxBeanType = Class.forName("java.lang.management.RuntimeMXBean", true, loader);
            value = (String) runtimeMxBeanType.getMethod("getName", EmptyArrays.EMPTY_CLASSES).invoke(mgmtFactoryType.getMethod("getRuntimeMXBean", EmptyArrays.EMPTY_CLASSES).invoke(null, EmptyArrays.EMPTY_OBJECTS), EmptyArrays.EMPTY_OBJECTS);
        } catch (Exception e) {
            logger.debug("Could not invoke ManagementFactory.getRuntimeMXBean().getName(); Android?", e);
            try {
                value = Class.forName("android.os.Process", true, loader).getMethod("myPid", EmptyArrays.EMPTY_CLASSES).invoke(null, EmptyArrays.EMPTY_OBJECTS).toString();
            } catch (Exception e2) {
                logger.debug("Could not invoke Process.myPid(); not Android?", e2);
                value = "";
            }
        }
        int atIndex = value.indexOf(64);
        if (atIndex >= 0) {
            value = value.substring(0, atIndex);
        }
        try {
            pid = Integer.parseInt(value);
        } catch (NumberFormatException e3) {
            pid = -1;
        }
        if (pid >= 0 && pid <= 4194304) {
            return pid;
        }
        pid = ThreadLocalRandom.current().nextInt(4194305);
        logger.warn("Failed to find the current process ID from '{}'; using a_isRightVersion random value: {}", value, Integer.valueOf(pid));
        return pid;
    }

    private DefaultChannelId() {
    }

    private void init() {
        System.arraycopy(MACHINE_ID, 0, this.data, 0, 8);
        int i = writeLong(writeInt(writeInt(0 + 8, PROCESS_ID), nextSequence.getAndIncrement()), Long.reverse(System.nanoTime()) ^ System.currentTimeMillis());
        int random = ThreadLocalRandom.current().nextInt();
        this.hashCode = random;
        i = writeInt(i, random);
        if (!$assertionsDisabled && i != this.data.length) {
            throw new AssertionError();
        }
    }

    private int writeInt(int i, int value) {
        int i2 = i + 1;
        this.data[i] = (byte) (value >>> 24);
        i = i2 + 1;
        this.data[i2] = (byte) (value >>> 16);
        i2 = i + 1;
        this.data[i] = (byte) (value >>> 8);
        i = i2 + 1;
        this.data[i2] = (byte) value;
        return i;
    }

    private int writeLong(int i, long value) {
        int i2 = i + 1;
        this.data[i] = (byte) ((int) (value >>> 56));
        i = i2 + 1;
        this.data[i2] = (byte) ((int) (value >>> 48));
        i2 = i + 1;
        this.data[i] = (byte) ((int) (value >>> 40));
        i = i2 + 1;
        this.data[i2] = (byte) ((int) (value >>> 32));
        i2 = i + 1;
        this.data[i] = (byte) ((int) (value >>> 24));
        i = i2 + 1;
        this.data[i2] = (byte) ((int) (value >>> 16));
        i2 = i + 1;
        this.data[i] = (byte) ((int) (value >>> 8));
        i = i2 + 1;
        this.data[i2] = (byte) ((int) value);
        return i;
    }

    public String asShortText() {
        String shortValue = this.shortValue;
        if (shortValue != null) {
            return shortValue;
        }
        shortValue = ByteBufUtil.hexDump(this.data, 24, 4);
        this.shortValue = shortValue;
        return shortValue;
    }

    public String asLongText() {
        String longValue = this.longValue;
        if (longValue != null) {
            return longValue;
        }
        longValue = newLongValue();
        this.longValue = longValue;
        return longValue;
    }

    private String newLongValue() {
        StringBuilder buf = new StringBuilder((this.data.length * 2) + 5);
        int i = appendHexDumpField(buf, appendHexDumpField(buf, appendHexDumpField(buf, appendHexDumpField(buf, appendHexDumpField(buf, 0, 8), 4), 4), 8), 4);
        if ($assertionsDisabled || i == this.data.length) {
            return buf.substring(0, buf.length() - 1);
        }
        throw new AssertionError();
    }

    private int appendHexDumpField(StringBuilder buf, int i, int length) {
        buf.append(ByteBufUtil.hexDump(this.data, i, length));
        buf.append('-');
        return i + length;
    }

    public int hashCode() {
        return this.hashCode;
    }

    public int compareTo(ChannelId o) {
        return 0;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof DefaultChannelId) {
            return Arrays.equals(this.data, ((DefaultChannelId) obj).data);
        }
        return false;
    }

    public String toString() {
        return asShortText();
    }
}
