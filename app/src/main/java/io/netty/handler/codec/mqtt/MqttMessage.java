package io.netty.handler.codec.mqtt;

import io.netty.handler.codec.DecoderResult;
import io.netty.util.internal.StringUtil;

public class MqttMessage {
    private final DecoderResult decoderResult;
    private final MqttFixedHeader mqttFixedHeader;
    private final Object payload;
    private final Object variableHeader;

    public MqttMessage(MqttFixedHeader mqttFixedHeader) {
        this(mqttFixedHeader, null, null);
    }

    public MqttMessage(MqttFixedHeader mqttFixedHeader, Object variableHeader) {
        this(mqttFixedHeader, variableHeader, null);
    }

    public MqttMessage(MqttFixedHeader mqttFixedHeader, Object variableHeader, Object payload) {
        this(mqttFixedHeader, variableHeader, payload, DecoderResult.SUCCESS);
    }

    public MqttMessage(MqttFixedHeader mqttFixedHeader, Object variableHeader, Object payload, DecoderResult decoderResult) {
        this.mqttFixedHeader = mqttFixedHeader;
        this.variableHeader = variableHeader;
        this.payload = payload;
        this.decoderResult = decoderResult;
    }

    public MqttFixedHeader fixedHeader() {
        return this.mqttFixedHeader;
    }

    public Object variableHeader() {
        return this.variableHeader;
    }

    public Object payload() {
        return this.payload;
    }

    public DecoderResult decoderResult() {
        return this.decoderResult;
    }

    public String toString() {
        return new StringBuilder(StringUtil.simpleClassName((Object) this)).append('[').append("fixedHeader=").append(fixedHeader() != null ? fixedHeader().toString() : "").append(", variableHeader=").append(variableHeader() != null ? this.variableHeader.toString() : "").append(", payload=").append(payload() != null ? this.payload.toString() : "").append(']').toString();
    }
}
