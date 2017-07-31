package io.netty.handler.codec.mqtt;

import io.netty.util.internal.StringUtil;

public final class MqttTopicSubscription {
    private final MqttQoS qualityOfService;
    private final String topicFilter;

    public MqttTopicSubscription(String topicFilter, MqttQoS qualityOfService) {
        this.topicFilter = topicFilter;
        this.qualityOfService = qualityOfService;
    }

    public String topicName() {
        return this.topicFilter;
    }

    public MqttQoS qualityOfService() {
        return this.qualityOfService;
    }

    public String toString() {
        return new StringBuilder(StringUtil.simpleClassName((Object) this)).append('[').append("topicFilter=").append(this.topicFilter).append(", qualityOfService=").append(this.qualityOfService).append(']').toString();
    }
}
