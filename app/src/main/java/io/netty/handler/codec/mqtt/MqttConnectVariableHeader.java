package io.netty.handler.codec.mqtt;

import io.netty.util.internal.StringUtil;

public final class MqttConnectVariableHeader {
    private final boolean hasPassword;
    private final boolean hasUserName;
    private final boolean isCleanSession;
    private final boolean isWillFlag;
    private final boolean isWillRetain;
    private final int keepAliveTimeSeconds;
    private final String name;
    private final int version;
    private final int willQos;

    public MqttConnectVariableHeader(String name, int version, boolean hasUserName, boolean hasPassword, boolean isWillRetain, int willQos, boolean isWillFlag, boolean isCleanSession, int keepAliveTimeSeconds) {
        this.name = name;
        this.version = version;
        this.hasUserName = hasUserName;
        this.hasPassword = hasPassword;
        this.isWillRetain = isWillRetain;
        this.willQos = willQos;
        this.isWillFlag = isWillFlag;
        this.isCleanSession = isCleanSession;
        this.keepAliveTimeSeconds = keepAliveTimeSeconds;
    }

    public String name() {
        return this.name;
    }

    public int version() {
        return this.version;
    }

    public boolean hasUserName() {
        return this.hasUserName;
    }

    public boolean hasPassword() {
        return this.hasPassword;
    }

    public boolean isWillRetain() {
        return this.isWillRetain;
    }

    public int willQos() {
        return this.willQos;
    }

    public boolean isWillFlag() {
        return this.isWillFlag;
    }

    public boolean isCleanSession() {
        return this.isCleanSession;
    }

    public int keepAliveTimeSeconds() {
        return this.keepAliveTimeSeconds;
    }

    public String toString() {
        return new StringBuilder(StringUtil.simpleClassName((Object) this)).append('[').append("name=").append(this.name).append(", version=").append(this.version).append(", hasUserName=").append(this.hasUserName).append(", hasPassword=").append(this.hasPassword).append(", isWillRetain=").append(this.isWillRetain).append(", isWillFlag=").append(this.isWillFlag).append(", isCleanSession=").append(this.isCleanSession).append(", keepAliveTimeSeconds=").append(this.keepAliveTimeSeconds).append(']').toString();
    }
}
