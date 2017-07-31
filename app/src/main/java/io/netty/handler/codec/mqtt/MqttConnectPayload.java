package io.netty.handler.codec.mqtt;

import io.netty.util.internal.StringUtil;

public final class MqttConnectPayload {
    private final String clientIdentifier;
    private final String password;
    private final String userName;
    private final String willMessage;
    private final String willTopic;

    public MqttConnectPayload(String clientIdentifier, String willTopic, String willMessage, String userName, String password) {
        this.clientIdentifier = clientIdentifier;
        this.willTopic = willTopic;
        this.willMessage = willMessage;
        this.userName = userName;
        this.password = password;
    }

    public String clientIdentifier() {
        return this.clientIdentifier;
    }

    public String willTopic() {
        return this.willTopic;
    }

    public String willMessage() {
        return this.willMessage;
    }

    public String userName() {
        return this.userName;
    }

    public String password() {
        return this.password;
    }

    public String toString() {
        return new StringBuilder(StringUtil.simpleClassName((Object) this)).append('[').append("clientIdentifier=").append(this.clientIdentifier).append(", willTopic=").append(this.willTopic).append(", willMessage=").append(this.willMessage).append(", userName=").append(this.userName).append(", password=").append(this.password).append(']').toString();
    }
}
