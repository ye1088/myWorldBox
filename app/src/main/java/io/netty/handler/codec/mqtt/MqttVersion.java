package io.netty.handler.codec.mqtt;

import io.netty.util.CharsetUtil;
import io.netty.util.internal.ObjectUtil;

public enum MqttVersion {
    MQTT_3_1("MQIsdp", (byte) 3),
    MQTT_3_1_1("MQTT", (byte) 4);
    
    private final byte level;
    private final String name;

    private MqttVersion(String protocolName, byte protocolLevel) {
        this.name = (String) ObjectUtil.checkNotNull(protocolName, "protocolName");
        this.level = protocolLevel;
    }

    public String protocolName() {
        return this.name;
    }

    public byte[] protocolNameBytes() {
        return this.name.getBytes(CharsetUtil.UTF_8);
    }

    public byte protocolLevel() {
        return this.level;
    }

    public static MqttVersion fromProtocolNameAndLevel(String protocolName, byte protocolLevel) {
        MqttVersion[] arr$ = values();
        int len$ = arr$.length;
        int i$ = 0;
        while (i$ < len$) {
            MqttVersion mv = arr$[i$];
            if (!mv.name.equals(protocolName)) {
                i$++;
            } else if (mv.level == protocolLevel) {
                return mv;
            } else {
                throw new MqttUnacceptableProtocolVersionException(protocolName + " and " + protocolLevel + " are not match");
            }
        }
        throw new MqttUnacceptableProtocolVersionException(protocolName + "is unknown protocol name");
    }
}
