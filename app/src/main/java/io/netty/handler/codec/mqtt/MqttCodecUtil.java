package io.netty.handler.codec.mqtt;

import io.netty.handler.codec.DecoderException;

final class MqttCodecUtil {
    private static final int MAX_CLIENT_ID_LENGTH = 23;
    private static final int MIN_CLIENT_ID_LENGTH = 1;
    private static final char[] TOPIC_WILDCARDS = new char[]{'#', '+'};

    static boolean isValidPublishTopicName(String topicName) {
        for (char c : TOPIC_WILDCARDS) {
            if (topicName.indexOf(c) >= 0) {
                return false;
            }
        }
        return true;
    }

    static boolean isValidMessageId(int messageId) {
        return messageId != 0;
    }

    static boolean isValidClientId(MqttVersion mqttVersion, String clientId) {
        if (mqttVersion == MqttVersion.MQTT_3_1) {
            if (clientId == null || clientId.length() < 1 || clientId.length() > 23) {
                return false;
            }
            return true;
        } else if (mqttVersion != MqttVersion.MQTT_3_1_1) {
            throw new IllegalArgumentException(mqttVersion + " is unknown mqtt version");
        } else if (clientId == null) {
            return false;
        } else {
            return true;
        }
    }

    static MqttFixedHeader validateFixedHeader(MqttFixedHeader mqttFixedHeader) {
        switch (mqttFixedHeader.messageType()) {
            case PUBREL:
            case SUBSCRIBE:
            case UNSUBSCRIBE:
                if (mqttFixedHeader.qosLevel() != MqttQoS.AT_LEAST_ONCE) {
                    throw new DecoderException(mqttFixedHeader.messageType().name() + " message must have QoS 1");
                }
                break;
        }
        return mqttFixedHeader;
    }

    static MqttFixedHeader resetUnusedFields(MqttFixedHeader mqttFixedHeader) {
        switch (mqttFixedHeader.messageType()) {
            case PUBREL:
            case SUBSCRIBE:
            case UNSUBSCRIBE:
                if (!mqttFixedHeader.isRetain()) {
                    return mqttFixedHeader;
                }
                return new MqttFixedHeader(mqttFixedHeader.messageType(), mqttFixedHeader.isDup(), mqttFixedHeader.qosLevel(), false, mqttFixedHeader.remainingLength());
            case CONNECT:
            case CONNACK:
            case PUBACK:
            case PUBREC:
            case PUBCOMP:
            case SUBACK:
            case UNSUBACK:
            case PINGREQ:
            case PINGRESP:
            case DISCONNECT:
                if (!mqttFixedHeader.isDup() && mqttFixedHeader.qosLevel() == MqttQoS.AT_MOST_ONCE && !mqttFixedHeader.isRetain()) {
                    return mqttFixedHeader;
                }
                return new MqttFixedHeader(mqttFixedHeader.messageType(), false, MqttQoS.AT_MOST_ONCE, false, mqttFixedHeader.remainingLength());
            default:
                return mqttFixedHeader;
        }
    }

    private MqttCodecUtil() {
    }
}
