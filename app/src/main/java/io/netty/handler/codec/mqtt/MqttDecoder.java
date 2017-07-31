package io.netty.handler.codec.mqtt;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.ReplayingDecoder;
import io.netty.util.CharsetUtil;
import java.util.ArrayList;
import java.util.List;
import org.bytedeco.javacpp.avutil;

public final class MqttDecoder extends ReplayingDecoder<DecoderState> {
    private static final int DEFAULT_MAX_BYTES_IN_MESSAGE = 8092;
    private int bytesRemainingInVariablePart;
    private final int maxBytesInMessage;
    private MqttFixedHeader mqttFixedHeader;
    private Object payload;
    private Object variableHeader;

    enum DecoderState {
        READ_FIXED_HEADER,
        READ_VARIABLE_HEADER,
        READ_PAYLOAD,
        BAD_MESSAGE
    }

    private static final class Result<T> {
        private final int numberOfBytesConsumed;
        private final T value;

        Result(T value, int numberOfBytesConsumed) {
            this.value = value;
            this.numberOfBytesConsumed = numberOfBytesConsumed;
        }
    }

    public MqttDecoder() {
        this(DEFAULT_MAX_BYTES_IN_MESSAGE);
    }

    public MqttDecoder(int maxBytesInMessage) {
        super(DecoderState.READ_FIXED_HEADER);
        this.maxBytesInMessage = maxBytesInMessage;
    }

    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
        switch ((DecoderState) state()) {
            case READ_FIXED_HEADER:
                this.mqttFixedHeader = decodeFixedHeader(buffer);
                this.bytesRemainingInVariablePart = this.mqttFixedHeader.remainingLength();
                checkpoint(DecoderState.READ_VARIABLE_HEADER);
                break;
            case READ_VARIABLE_HEADER:
                break;
            case READ_PAYLOAD:
                break;
            case BAD_MESSAGE:
                buffer.skipBytes(actualReadableBytes());
                return;
            default:
                throw new Error();
        }
        try {
            if (this.bytesRemainingInVariablePart > this.maxBytesInMessage) {
                throw new DecoderException("too large message: " + this.bytesRemainingInVariablePart + " bytes");
            }
            Result<?> decodedVariableHeader = decodeVariableHeader(buffer, this.mqttFixedHeader);
            this.variableHeader = decodedVariableHeader.value;
            this.bytesRemainingInVariablePart -= decodedVariableHeader.numberOfBytesConsumed;
            checkpoint(DecoderState.READ_PAYLOAD);
            try {
                Result<?> decodedPayload = decodePayload(buffer, this.mqttFixedHeader.messageType(), this.bytesRemainingInVariablePart, this.variableHeader);
                this.payload = decodedPayload.value;
                this.bytesRemainingInVariablePart -= decodedPayload.numberOfBytesConsumed;
                if (this.bytesRemainingInVariablePart != 0) {
                    throw new DecoderException("non-zero remaining payload bytes: " + this.bytesRemainingInVariablePart + " (" + this.mqttFixedHeader.messageType() + ')');
                }
                checkpoint(DecoderState.READ_FIXED_HEADER);
                MqttMessage message = MqttMessageFactory.newMessage(this.mqttFixedHeader, this.variableHeader, this.payload);
                this.mqttFixedHeader = null;
                this.variableHeader = null;
                this.payload = null;
                out.add(message);
            } catch (Exception cause) {
                out.add(invalidMessage(cause));
            }
        } catch (Exception cause2) {
            out.add(invalidMessage(cause2));
        }
    }

    private MqttMessage invalidMessage(Throwable cause) {
        checkpoint(DecoderState.BAD_MESSAGE);
        return MqttMessageFactory.newInvalidMessage(cause);
    }

    private static MqttFixedHeader decodeFixedHeader(ByteBuf buffer) {
        boolean dupFlag;
        boolean retain = true;
        short b1 = buffer.readUnsignedByte();
        MqttMessageType messageType = MqttMessageType.valueOf(b1 >> 4);
        if ((b1 & 8) == 8) {
            dupFlag = true;
        } else {
            dupFlag = false;
        }
        int qosLevel = (b1 & 6) >> 1;
        if ((b1 & 1) == 0) {
            retain = false;
        }
        int remainingLength = 0;
        int multiplier = 1;
        int loops = 0;
        do {
            short digit = buffer.readUnsignedByte();
            remainingLength += (digit & 127) * multiplier;
            multiplier *= 128;
            loops++;
            if ((digit & 128) == 0) {
                break;
            }
        } while (loops < 4);
        if (loops != 4 || (digit & 128) == 0) {
            return MqttCodecUtil.validateFixedHeader(MqttCodecUtil.resetUnusedFields(new MqttFixedHeader(messageType, dupFlag, MqttQoS.valueOf(qosLevel), retain, remainingLength)));
        }
        throw new DecoderException("remaining length exceeds 4 digits (" + messageType + ')');
    }

    private static Result<?> decodeVariableHeader(ByteBuf buffer, MqttFixedHeader mqttFixedHeader) {
        switch (mqttFixedHeader.messageType()) {
            case CONNECT:
                return decodeConnectionVariableHeader(buffer);
            case CONNACK:
                return decodeConnAckVariableHeader(buffer);
            case SUBSCRIBE:
            case UNSUBSCRIBE:
            case SUBACK:
            case UNSUBACK:
            case PUBACK:
            case PUBREC:
            case PUBCOMP:
            case PUBREL:
                return decodeMessageIdVariableHeader(buffer);
            case PUBLISH:
                return decodePublishVariableHeader(buffer, mqttFixedHeader);
            case PINGREQ:
            case PINGRESP:
            case DISCONNECT:
                return new Result(null, 0);
            default:
                return new Result(null, 0);
        }
    }

    private static Result<MqttConnectVariableHeader> decodeConnectionVariableHeader(ByteBuf buffer) {
        Result<String> protoString = decodeString(buffer);
        int numberOfBytesConsumed = protoString.numberOfBytesConsumed + 1;
        MqttVersion mqttVersion = MqttVersion.fromProtocolNameAndLevel((String) protoString.value, buffer.readByte());
        int b1 = buffer.readUnsignedByte();
        numberOfBytesConsumed++;
        Result<Integer> keepAlive = decodeMsbLsb(buffer);
        numberOfBytesConsumed += keepAlive.numberOfBytesConsumed;
        boolean hasUserName = (b1 & 128) == 128;
        boolean hasPassword = (b1 & 64) == 64;
        boolean willRetain = (b1 & 32) == 32;
        int willQos = (b1 & 24) >> 3;
        boolean willFlag = (b1 & 4) == 4;
        boolean cleanSession = (b1 & 2) == 2;
        if (mqttVersion == MqttVersion.MQTT_3_1_1) {
            if (!((b1 & 1) == 0)) {
                throw new DecoderException("non-zero reserved flag");
            }
        }
        return new Result(new MqttConnectVariableHeader(mqttVersion.protocolName(), mqttVersion.protocolLevel(), hasUserName, hasPassword, willRetain, willQos, willFlag, cleanSession, ((Integer) keepAlive.value).intValue()), numberOfBytesConsumed);
    }

    private static Result<MqttConnAckVariableHeader> decodeConnAckVariableHeader(ByteBuf buffer) {
        boolean sessionPresent = true;
        if ((buffer.readUnsignedByte() & 1) != 1) {
            sessionPresent = false;
        }
        return new Result(new MqttConnAckVariableHeader(MqttConnectReturnCode.valueOf(buffer.readByte()), sessionPresent), 2);
    }

    private static Result<MqttMessageIdVariableHeader> decodeMessageIdVariableHeader(ByteBuf buffer) {
        Result<Integer> messageId = decodeMessageId(buffer);
        return new Result(MqttMessageIdVariableHeader.from(((Integer) messageId.value).intValue()), messageId.numberOfBytesConsumed);
    }

    private static Result<MqttPublishVariableHeader> decodePublishVariableHeader(ByteBuf buffer, MqttFixedHeader mqttFixedHeader) {
        Result<String> decodedTopic = decodeString(buffer);
        if (MqttCodecUtil.isValidPublishTopicName((String) decodedTopic.value)) {
            int numberOfBytesConsumed = decodedTopic.numberOfBytesConsumed;
            int messageId = -1;
            if (mqttFixedHeader.qosLevel().value() > 0) {
                Result<Integer> decodedMessageId = decodeMessageId(buffer);
                messageId = ((Integer) decodedMessageId.value).intValue();
                numberOfBytesConsumed += decodedMessageId.numberOfBytesConsumed;
            }
            return new Result(new MqttPublishVariableHeader((String) decodedTopic.value, messageId), numberOfBytesConsumed);
        }
        throw new DecoderException("invalid publish topic name: " + ((String) decodedTopic.value) + " (contains wildcards)");
    }

    private static Result<Integer> decodeMessageId(ByteBuf buffer) {
        Result<Integer> messageId = decodeMsbLsb(buffer);
        if (MqttCodecUtil.isValidMessageId(((Integer) messageId.value).intValue())) {
            return messageId;
        }
        throw new DecoderException("invalid messageId: " + messageId.value);
    }

    private static Result<?> decodePayload(ByteBuf buffer, MqttMessageType messageType, int bytesRemainingInVariablePart, Object variableHeader) {
        switch (messageType) {
            case CONNECT:
                return decodeConnectionPayload(buffer, (MqttConnectVariableHeader) variableHeader);
            case SUBSCRIBE:
                return decodeSubscribePayload(buffer, bytesRemainingInVariablePart);
            case UNSUBSCRIBE:
                return decodeUnsubscribePayload(buffer, bytesRemainingInVariablePart);
            case SUBACK:
                return decodeSubackPayload(buffer, bytesRemainingInVariablePart);
            case PUBLISH:
                return decodePublishPayload(buffer, bytesRemainingInVariablePart);
            default:
                return new Result(null, 0);
        }
    }

    private static Result<MqttConnectPayload> decodeConnectionPayload(ByteBuf buffer, MqttConnectVariableHeader mqttConnectVariableHeader) {
        String str = null;
        Result<String> decodedClientId = decodeString(buffer);
        String decodedClientIdValue = (String) decodedClientId.value;
        if (MqttCodecUtil.isValidClientId(MqttVersion.fromProtocolNameAndLevel(mqttConnectVariableHeader.name(), (byte) mqttConnectVariableHeader.version()), decodedClientIdValue)) {
            String str2;
            int numberOfBytesConsumed = decodedClientId.numberOfBytesConsumed;
            Result<String> decodedWillTopic = null;
            Result<String> decodedWillMessage = null;
            if (mqttConnectVariableHeader.isWillFlag()) {
                decodedWillTopic = decodeString(buffer, 0, avutil.FF_LAMBDA_MAX);
                numberOfBytesConsumed += decodedWillTopic.numberOfBytesConsumed;
                decodedWillMessage = decodeAsciiString(buffer);
                numberOfBytesConsumed += decodedWillMessage.numberOfBytesConsumed;
            }
            Result<String> decodedUserName = null;
            Result<String> decodedPassword = null;
            if (mqttConnectVariableHeader.hasUserName()) {
                decodedUserName = decodeString(buffer);
                numberOfBytesConsumed += decodedUserName.numberOfBytesConsumed;
            }
            if (mqttConnectVariableHeader.hasPassword()) {
                decodedPassword = decodeString(buffer);
                numberOfBytesConsumed += decodedPassword.numberOfBytesConsumed;
            }
            String str3 = (String) decodedClientId.value;
            if (decodedWillTopic != null) {
                str2 = (String) decodedWillTopic.value;
            } else {
                str2 = null;
            }
            String str4 = decodedWillMessage != null ? (String) decodedWillMessage.value : null;
            String str5 = decodedUserName != null ? (String) decodedUserName.value : null;
            if (decodedPassword != null) {
                str = (String) decodedPassword.value;
            }
            return new Result(new MqttConnectPayload(str3, str2, str4, str5, str), numberOfBytesConsumed);
        }
        throw new MqttIdentifierRejectedException("invalid clientIdentifier: " + decodedClientIdValue);
    }

    private static Result<MqttSubscribePayload> decodeSubscribePayload(ByteBuf buffer, int bytesRemainingInVariablePart) {
        List<MqttTopicSubscription> subscribeTopics = new ArrayList();
        int numberOfBytesConsumed = 0;
        while (numberOfBytesConsumed < bytesRemainingInVariablePart) {
            Result<String> decodedTopicName = decodeString(buffer);
            numberOfBytesConsumed = (numberOfBytesConsumed + decodedTopicName.numberOfBytesConsumed) + 1;
            subscribeTopics.add(new MqttTopicSubscription((String) decodedTopicName.value, MqttQoS.valueOf(buffer.readUnsignedByte() & 3)));
        }
        return new Result(new MqttSubscribePayload(subscribeTopics), numberOfBytesConsumed);
    }

    private static Result<MqttSubAckPayload> decodeSubackPayload(ByteBuf buffer, int bytesRemainingInVariablePart) {
        Iterable grantedQos = new ArrayList();
        int numberOfBytesConsumed = 0;
        while (numberOfBytesConsumed < bytesRemainingInVariablePart) {
            numberOfBytesConsumed++;
            grantedQos.add(Integer.valueOf(buffer.readUnsignedByte() & 3));
        }
        return new Result(new MqttSubAckPayload(grantedQos), numberOfBytesConsumed);
    }

    private static Result<MqttUnsubscribePayload> decodeUnsubscribePayload(ByteBuf buffer, int bytesRemainingInVariablePart) {
        List<String> unsubscribeTopics = new ArrayList();
        int numberOfBytesConsumed = 0;
        while (numberOfBytesConsumed < bytesRemainingInVariablePart) {
            Result<String> decodedTopicName = decodeString(buffer);
            numberOfBytesConsumed += decodedTopicName.numberOfBytesConsumed;
            unsubscribeTopics.add(decodedTopicName.value);
        }
        return new Result(new MqttUnsubscribePayload(unsubscribeTopics), numberOfBytesConsumed);
    }

    private static Result<ByteBuf> decodePublishPayload(ByteBuf buffer, int bytesRemainingInVariablePart) {
        return new Result(buffer.readRetainedSlice(bytesRemainingInVariablePart), bytesRemainingInVariablePart);
    }

    private static Result<String> decodeString(ByteBuf buffer) {
        return decodeString(buffer, 0, Integer.MAX_VALUE);
    }

    private static Result<String> decodeAsciiString(ByteBuf buffer) {
        Result<String> result = decodeString(buffer, 0, Integer.MAX_VALUE);
        String s = (String) result.value;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) > '') {
                return new Result(null, result.numberOfBytesConsumed);
            }
        }
        return new Result(s, result.numberOfBytesConsumed);
    }

    private static Result<String> decodeString(ByteBuf buffer, int minBytes, int maxBytes) {
        Result<Integer> decodedSize = decodeMsbLsb(buffer);
        int size = ((Integer) decodedSize.value).intValue();
        int numberOfBytesConsumed = decodedSize.numberOfBytesConsumed;
        if (size < minBytes || size > maxBytes) {
            buffer.skipBytes(size);
            return new Result(null, numberOfBytesConsumed + size);
        }
        String s = buffer.toString(buffer.readerIndex(), size, CharsetUtil.UTF_8);
        buffer.skipBytes(size);
        return new Result(s, numberOfBytesConsumed + size);
    }

    private static Result<Integer> decodeMsbLsb(ByteBuf buffer) {
        return decodeMsbLsb(buffer, 0, 65535);
    }

    private static Result<Integer> decodeMsbLsb(ByteBuf buffer, int min, int max) {
        int result = (buffer.readUnsignedByte() << 8) | buffer.readUnsignedByte();
        if (result < min || result > max) {
            result = -1;
        }
        return new Result(Integer.valueOf(result), 2);
    }
}
