package io.netty.handler.codec.mqtt;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.EmptyArrays;
import java.util.List;

@Sharable
public final class MqttEncoder extends MessageToMessageEncoder<MqttMessage> {
    public static final MqttEncoder INSTANCE = new MqttEncoder();

    private MqttEncoder() {
    }

    protected void encode(ChannelHandlerContext ctx, MqttMessage msg, List<Object> out) throws Exception {
        out.add(doEncode(ctx.alloc(), msg));
    }

    static ByteBuf doEncode(ByteBufAllocator byteBufAllocator, MqttMessage message) {
        switch (1.$SwitchMap$io$netty$handler$codec$mqtt$MqttMessageType[message.fixedHeader().messageType().ordinal()]) {
            case 1:
                return encodeConnectMessage(byteBufAllocator, (MqttConnectMessage) message);
            case 2:
                return encodeConnAckMessage(byteBufAllocator, (MqttConnAckMessage) message);
            case 3:
                return encodePublishMessage(byteBufAllocator, (MqttPublishMessage) message);
            case 4:
                return encodeSubscribeMessage(byteBufAllocator, (MqttSubscribeMessage) message);
            case 5:
                return encodeUnsubscribeMessage(byteBufAllocator, (MqttUnsubscribeMessage) message);
            case 6:
                return encodeSubAckMessage(byteBufAllocator, (MqttSubAckMessage) message);
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                return encodeMessageWithOnlySingleByteFixedHeaderAndMessageId(byteBufAllocator, message);
            case 12:
            case 13:
            case 14:
                return encodeMessageWithOnlySingleByteFixedHeader(byteBufAllocator, message);
            default:
                throw new IllegalArgumentException("Unknown message type: " + message.fixedHeader().messageType().value());
        }
    }

    private static ByteBuf encodeConnectMessage(ByteBufAllocator byteBufAllocator, MqttConnectMessage message) {
        MqttFixedHeader mqttFixedHeader = message.fixedHeader();
        MqttConnectVariableHeader variableHeader = message.variableHeader();
        MqttConnectPayload payload = message.payload();
        MqttVersion mqttVersion = MqttVersion.fromProtocolNameAndLevel(variableHeader.name(), (byte) variableHeader.version());
        String clientIdentifier = payload.clientIdentifier();
        if (MqttCodecUtil.isValidClientId(mqttVersion, clientIdentifier)) {
            byte[] clientIdentifierBytes = encodeStringUtf8(clientIdentifier);
            int payloadBufferSize = 0 + (clientIdentifierBytes.length + 2);
            String willTopic = payload.willTopic();
            byte[] willTopicBytes = willTopic != null ? encodeStringUtf8(willTopic) : EmptyArrays.EMPTY_BYTES;
            String willMessage = payload.willMessage();
            byte[] willMessageBytes = willMessage != null ? encodeStringUtf8(willMessage) : EmptyArrays.EMPTY_BYTES;
            if (variableHeader.isWillFlag()) {
                payloadBufferSize = (payloadBufferSize + (willTopicBytes.length + 2)) + (willMessageBytes.length + 2);
            }
            String userName = payload.userName();
            byte[] userNameBytes = userName != null ? encodeStringUtf8(userName) : EmptyArrays.EMPTY_BYTES;
            if (variableHeader.hasUserName()) {
                payloadBufferSize += userNameBytes.length + 2;
            }
            String password = payload.password();
            byte[] passwordBytes = password != null ? encodeStringUtf8(password) : EmptyArrays.EMPTY_BYTES;
            if (variableHeader.hasPassword()) {
                payloadBufferSize += passwordBytes.length + 2;
            }
            byte[] protocolNameBytes = mqttVersion.protocolNameBytes();
            int variablePartSize = ((protocolNameBytes.length + 2) + 4) + payloadBufferSize;
            ByteBuf buf = byteBufAllocator.buffer((getVariableLengthInt(variablePartSize) + 1) + variablePartSize);
            buf.writeByte(getFixedHeaderByte1(mqttFixedHeader));
            writeVariableLengthInt(buf, variablePartSize);
            buf.writeShort(protocolNameBytes.length);
            buf.writeBytes(protocolNameBytes);
            buf.writeByte(variableHeader.version());
            buf.writeByte(getConnVariableHeaderFlag(variableHeader));
            buf.writeShort(variableHeader.keepAliveTimeSeconds());
            buf.writeShort(clientIdentifierBytes.length);
            buf.writeBytes(clientIdentifierBytes, 0, clientIdentifierBytes.length);
            if (variableHeader.isWillFlag()) {
                buf.writeShort(willTopicBytes.length);
                buf.writeBytes(willTopicBytes, 0, willTopicBytes.length);
                buf.writeShort(willMessageBytes.length);
                buf.writeBytes(willMessageBytes, 0, willMessageBytes.length);
            }
            if (variableHeader.hasUserName()) {
                buf.writeShort(userNameBytes.length);
                buf.writeBytes(userNameBytes, 0, userNameBytes.length);
            }
            if (variableHeader.hasPassword()) {
                buf.writeShort(passwordBytes.length);
                buf.writeBytes(passwordBytes, 0, passwordBytes.length);
            }
            return buf;
        }
        throw new MqttIdentifierRejectedException("invalid clientIdentifier: " + clientIdentifier);
    }

    private static int getConnVariableHeaderFlag(MqttConnectVariableHeader variableHeader) {
        int flagByte = 0;
        if (variableHeader.hasUserName()) {
            flagByte = 0 | 128;
        }
        if (variableHeader.hasPassword()) {
            flagByte |= 64;
        }
        if (variableHeader.isWillRetain()) {
            flagByte |= 32;
        }
        flagByte |= (variableHeader.willQos() & 3) << 3;
        if (variableHeader.isWillFlag()) {
            flagByte |= 4;
        }
        if (variableHeader.isCleanSession()) {
            return flagByte | 2;
        }
        return flagByte;
    }

    private static ByteBuf encodeConnAckMessage(ByteBufAllocator byteBufAllocator, MqttConnAckMessage message) {
        ByteBuf buf = byteBufAllocator.buffer(4);
        buf.writeByte(getFixedHeaderByte1(message.fixedHeader()));
        buf.writeByte(2);
        buf.writeByte(message.variableHeader().isSessionPresent() ? 1 : 0);
        buf.writeByte(message.variableHeader().connectReturnCode().byteValue());
        return buf;
    }

    private static ByteBuf encodeSubscribeMessage(ByteBufAllocator byteBufAllocator, MqttSubscribeMessage message) {
        int payloadBufferSize = 0;
        MqttFixedHeader mqttFixedHeader = message.fixedHeader();
        MqttMessageIdVariableHeader variableHeader = message.variableHeader();
        MqttSubscribePayload payload = message.payload();
        for (MqttTopicSubscription topic : payload.topicSubscriptions()) {
            payloadBufferSize = (payloadBufferSize + (encodeStringUtf8(topic.topicName()).length + 2)) + 1;
        }
        int variablePartSize = 2 + payloadBufferSize;
        ByteBuf buf = byteBufAllocator.buffer((getVariableLengthInt(variablePartSize) + 1) + variablePartSize);
        buf.writeByte(getFixedHeaderByte1(mqttFixedHeader));
        writeVariableLengthInt(buf, variablePartSize);
        buf.writeShort(variableHeader.messageId());
        for (MqttTopicSubscription topic2 : payload.topicSubscriptions()) {
            byte[] topicNameBytes = encodeStringUtf8(topic2.topicName());
            buf.writeShort(topicNameBytes.length);
            buf.writeBytes(topicNameBytes, 0, topicNameBytes.length);
            buf.writeByte(topic2.qualityOfService().value());
        }
        return buf;
    }

    private static ByteBuf encodeUnsubscribeMessage(ByteBufAllocator byteBufAllocator, MqttUnsubscribeMessage message) {
        int payloadBufferSize = 0;
        MqttFixedHeader mqttFixedHeader = message.fixedHeader();
        MqttMessageIdVariableHeader variableHeader = message.variableHeader();
        MqttUnsubscribePayload payload = message.payload();
        for (String topicName : payload.topics()) {
            payloadBufferSize += encodeStringUtf8(topicName).length + 2;
        }
        int variablePartSize = 2 + payloadBufferSize;
        ByteBuf buf = byteBufAllocator.buffer((getVariableLengthInt(variablePartSize) + 1) + variablePartSize);
        buf.writeByte(getFixedHeaderByte1(mqttFixedHeader));
        writeVariableLengthInt(buf, variablePartSize);
        buf.writeShort(variableHeader.messageId());
        for (String topicName2 : payload.topics()) {
            byte[] topicNameBytes = encodeStringUtf8(topicName2);
            buf.writeShort(topicNameBytes.length);
            buf.writeBytes(topicNameBytes, 0, topicNameBytes.length);
        }
        return buf;
    }

    private static ByteBuf encodeSubAckMessage(ByteBufAllocator byteBufAllocator, MqttSubAckMessage message) {
        int variablePartSize = 2 + message.payload().grantedQoSLevels().size();
        ByteBuf buf = byteBufAllocator.buffer((getVariableLengthInt(variablePartSize) + 1) + variablePartSize);
        buf.writeByte(getFixedHeaderByte1(message.fixedHeader()));
        writeVariableLengthInt(buf, variablePartSize);
        buf.writeShort(message.variableHeader().messageId());
        for (Integer intValue : message.payload().grantedQoSLevels()) {
            buf.writeByte(intValue.intValue());
        }
        return buf;
    }

    private static ByteBuf encodePublishMessage(ByteBufAllocator byteBufAllocator, MqttPublishMessage message) {
        MqttFixedHeader mqttFixedHeader = message.fixedHeader();
        MqttPublishVariableHeader variableHeader = message.variableHeader();
        ByteBuf payload = message.payload().duplicate();
        byte[] topicNameBytes = encodeStringUtf8(variableHeader.topicName());
        int variablePartSize = ((topicNameBytes.length + 2) + (mqttFixedHeader.qosLevel().value() > 0 ? 2 : 0)) + payload.readableBytes();
        ByteBuf buf = byteBufAllocator.buffer((getVariableLengthInt(variablePartSize) + 1) + variablePartSize);
        buf.writeByte(getFixedHeaderByte1(mqttFixedHeader));
        writeVariableLengthInt(buf, variablePartSize);
        buf.writeShort(topicNameBytes.length);
        buf.writeBytes(topicNameBytes);
        if (mqttFixedHeader.qosLevel().value() > 0) {
            buf.writeShort(variableHeader.messageId());
        }
        buf.writeBytes(payload);
        return buf;
    }

    private static ByteBuf encodeMessageWithOnlySingleByteFixedHeaderAndMessageId(ByteBufAllocator byteBufAllocator, MqttMessage message) {
        MqttFixedHeader mqttFixedHeader = message.fixedHeader();
        int msgId = ((MqttMessageIdVariableHeader) message.variableHeader()).messageId();
        ByteBuf buf = byteBufAllocator.buffer((getVariableLengthInt(2) + 1) + 2);
        buf.writeByte(getFixedHeaderByte1(mqttFixedHeader));
        writeVariableLengthInt(buf, 2);
        buf.writeShort(msgId);
        return buf;
    }

    private static ByteBuf encodeMessageWithOnlySingleByteFixedHeader(ByteBufAllocator byteBufAllocator, MqttMessage message) {
        MqttFixedHeader mqttFixedHeader = message.fixedHeader();
        ByteBuf buf = byteBufAllocator.buffer(2);
        buf.writeByte(getFixedHeaderByte1(mqttFixedHeader));
        buf.writeByte(0);
        return buf;
    }

    private static int getFixedHeaderByte1(MqttFixedHeader header) {
        int ret = 0 | (header.messageType().value() << 4);
        if (header.isDup()) {
            ret |= 8;
        }
        ret |= header.qosLevel().value() << 1;
        if (header.isRetain()) {
            return ret | 1;
        }
        return ret;
    }

    private static void writeVariableLengthInt(ByteBuf buf, int num) {
        do {
            int digit = num % 128;
            num /= 128;
            if (num > 0) {
                digit |= 128;
            }
            buf.writeByte(digit);
        } while (num > 0);
    }

    private static int getVariableLengthInt(int num) {
        int count = 0;
        do {
            num /= 128;
            count++;
        } while (num > 0);
        return count;
    }

    private static byte[] encodeStringUtf8(String s) {
        return s.getBytes(CharsetUtil.UTF_8);
    }
}
