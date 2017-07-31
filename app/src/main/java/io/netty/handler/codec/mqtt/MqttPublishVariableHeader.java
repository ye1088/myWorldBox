package io.netty.handler.codec.mqtt;

import io.netty.util.internal.StringUtil;

public final class MqttPublishVariableHeader {
    private final int messageId;
    private final String topicName;

    public MqttPublishVariableHeader(String topicName, int messageId) {
        this.topicName = topicName;
        this.messageId = messageId;
    }

    public String topicName() {
        return this.topicName;
    }

    public int messageId() {
        return this.messageId;
    }

    public String toString() {
        return new StringBuilder(StringUtil.simpleClassName((Object) this)).append('[').append("topicName=").append(this.topicName).append(", messageId=").append(this.messageId).append(']').toString();
    }
}
