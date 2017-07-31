package com.google.protobuf;

import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.EnumValueDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor.JavaType;
import com.google.protobuf.Descriptors.FieldDescriptor.Type;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.ExtensionRegistry.ExtensionInfo;
import com.google.protobuf.Message.Builder;
import com.google.protobuf.UnknownFieldSet.Field;
import com.google.protobuf.WireFormat.FieldType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

class MessageReflection {

    interface MergeTarget {

        public enum ContainerType {
            MESSAGE,
            EXTENSION_SET
        }

        MergeTarget addRepeatedField(FieldDescriptor fieldDescriptor, Object obj);

        MergeTarget clearField(FieldDescriptor fieldDescriptor);

        MergeTarget clearOneof(OneofDescriptor oneofDescriptor);

        ExtensionInfo findExtensionByName(ExtensionRegistry extensionRegistry, String str);

        ExtensionInfo findExtensionByNumber(ExtensionRegistry extensionRegistry, Descriptor descriptor, int i);

        Object finish();

        ContainerType getContainerType();

        Descriptor getDescriptorForType();

        Object getField(FieldDescriptor fieldDescriptor);

        FieldDescriptor getOneofFieldDescriptor(OneofDescriptor oneofDescriptor);

        boolean hasField(FieldDescriptor fieldDescriptor);

        boolean hasOneof(OneofDescriptor oneofDescriptor);

        MergeTarget newMergeTargetForField(FieldDescriptor fieldDescriptor, Message message);

        Object parseGroup(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite, FieldDescriptor fieldDescriptor, Message message) throws IOException;

        Object parseMessage(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite, FieldDescriptor fieldDescriptor, Message message) throws IOException;

        Object parseMessageFromBytes(ByteString byteString, ExtensionRegistryLite extensionRegistryLite, FieldDescriptor fieldDescriptor, Message message) throws IOException;

        Object readPrimitiveField(CodedInputStream codedInputStream, FieldType fieldType, boolean z) throws IOException;

        MergeTarget setField(FieldDescriptor fieldDescriptor, Object obj);

        MergeTarget setRepeatedField(FieldDescriptor fieldDescriptor, int i, Object obj);
    }

    static class BuilderAdapter implements MergeTarget {
        private final Builder builder;

        public Descriptor getDescriptorForType() {
            return this.builder.getDescriptorForType();
        }

        public BuilderAdapter(Builder builder) {
            this.builder = builder;
        }

        public Object getField(FieldDescriptor field) {
            return this.builder.getField(field);
        }

        public boolean hasField(FieldDescriptor field) {
            return this.builder.hasField(field);
        }

        public MergeTarget setField(FieldDescriptor field, Object value) {
            this.builder.setField(field, value);
            return this;
        }

        public MergeTarget clearField(FieldDescriptor field) {
            this.builder.clearField(field);
            return this;
        }

        public MergeTarget setRepeatedField(FieldDescriptor field, int index, Object value) {
            this.builder.setRepeatedField(field, index, value);
            return this;
        }

        public MergeTarget addRepeatedField(FieldDescriptor field, Object value) {
            this.builder.addRepeatedField(field, value);
            return this;
        }

        public boolean hasOneof(OneofDescriptor oneof) {
            return this.builder.hasOneof(oneof);
        }

        public MergeTarget clearOneof(OneofDescriptor oneof) {
            this.builder.clearOneof(oneof);
            return this;
        }

        public FieldDescriptor getOneofFieldDescriptor(OneofDescriptor oneof) {
            return this.builder.getOneofFieldDescriptor(oneof);
        }

        public ContainerType getContainerType() {
            return ContainerType.MESSAGE;
        }

        public ExtensionInfo findExtensionByName(ExtensionRegistry registry, String name) {
            return registry.findImmutableExtensionByName(name);
        }

        public ExtensionInfo findExtensionByNumber(ExtensionRegistry registry, Descriptor containingType, int fieldNumber) {
            return registry.findImmutableExtensionByNumber(containingType, fieldNumber);
        }

        public Object parseGroup(CodedInputStream input, ExtensionRegistryLite extensionRegistry, FieldDescriptor field, Message defaultInstance) throws IOException {
            Builder subBuilder;
            if (defaultInstance != null) {
                subBuilder = defaultInstance.newBuilderForType();
            } else {
                subBuilder = this.builder.newBuilderForField(field);
            }
            if (!field.isRepeated()) {
                Message originalMessage = (Message) getField(field);
                if (originalMessage != null) {
                    subBuilder.mergeFrom(originalMessage);
                }
            }
            input.readGroup(field.getNumber(), subBuilder, extensionRegistry);
            return subBuilder.buildPartial();
        }

        public Object parseMessage(CodedInputStream input, ExtensionRegistryLite extensionRegistry, FieldDescriptor field, Message defaultInstance) throws IOException {
            Builder subBuilder;
            if (defaultInstance != null) {
                subBuilder = defaultInstance.newBuilderForType();
            } else {
                subBuilder = this.builder.newBuilderForField(field);
            }
            if (!field.isRepeated()) {
                Message originalMessage = (Message) getField(field);
                if (originalMessage != null) {
                    subBuilder.mergeFrom(originalMessage);
                }
            }
            input.readMessage(subBuilder, extensionRegistry);
            return subBuilder.buildPartial();
        }

        public Object parseMessageFromBytes(ByteString bytes, ExtensionRegistryLite extensionRegistry, FieldDescriptor field, Message defaultInstance) throws IOException {
            Builder subBuilder;
            if (defaultInstance != null) {
                subBuilder = defaultInstance.newBuilderForType();
            } else {
                subBuilder = this.builder.newBuilderForField(field);
            }
            if (!field.isRepeated()) {
                Message originalMessage = (Message) getField(field);
                if (originalMessage != null) {
                    subBuilder.mergeFrom(originalMessage);
                }
            }
            subBuilder.mergeFrom(bytes, extensionRegistry);
            return subBuilder.buildPartial();
        }

        public MergeTarget newMergeTargetForField(FieldDescriptor field, Message defaultInstance) {
            if (defaultInstance != null) {
                return new BuilderAdapter(defaultInstance.newBuilderForType());
            }
            return new BuilderAdapter(this.builder.newBuilderForField(field));
        }

        public Object readPrimitiveField(CodedInputStream input, FieldType type, boolean checkUtf8) throws IOException {
            return FieldSet.readPrimitiveField(input, type, checkUtf8);
        }

        public Object finish() {
            return this.builder.buildPartial();
        }
    }

    static class ExtensionAdapter implements MergeTarget {
        private final FieldSet<FieldDescriptor> extensions;

        ExtensionAdapter(FieldSet<FieldDescriptor> extensions) {
            this.extensions = extensions;
        }

        public Descriptor getDescriptorForType() {
            throw new UnsupportedOperationException("getDescriptorForType() called on FieldSet object");
        }

        public Object getField(FieldDescriptor field) {
            return this.extensions.getField(field);
        }

        public boolean hasField(FieldDescriptor field) {
            return this.extensions.hasField(field);
        }

        public MergeTarget setField(FieldDescriptor field, Object value) {
            this.extensions.setField(field, value);
            return this;
        }

        public MergeTarget clearField(FieldDescriptor field) {
            this.extensions.clearField(field);
            return this;
        }

        public MergeTarget setRepeatedField(FieldDescriptor field, int index, Object value) {
            this.extensions.setRepeatedField(field, index, value);
            return this;
        }

        public MergeTarget addRepeatedField(FieldDescriptor field, Object value) {
            this.extensions.addRepeatedField(field, value);
            return this;
        }

        public boolean hasOneof(OneofDescriptor oneof) {
            return false;
        }

        public MergeTarget clearOneof(OneofDescriptor oneof) {
            return this;
        }

        public FieldDescriptor getOneofFieldDescriptor(OneofDescriptor oneof) {
            return null;
        }

        public ContainerType getContainerType() {
            return ContainerType.EXTENSION_SET;
        }

        public ExtensionInfo findExtensionByName(ExtensionRegistry registry, String name) {
            return registry.findImmutableExtensionByName(name);
        }

        public ExtensionInfo findExtensionByNumber(ExtensionRegistry registry, Descriptor containingType, int fieldNumber) {
            return registry.findImmutableExtensionByNumber(containingType, fieldNumber);
        }

        public Object parseGroup(CodedInputStream input, ExtensionRegistryLite registry, FieldDescriptor field, Message defaultInstance) throws IOException {
            Builder subBuilder = defaultInstance.newBuilderForType();
            if (!field.isRepeated()) {
                Message originalMessage = (Message) getField(field);
                if (originalMessage != null) {
                    subBuilder.mergeFrom(originalMessage);
                }
            }
            input.readGroup(field.getNumber(), subBuilder, registry);
            return subBuilder.buildPartial();
        }

        public Object parseMessage(CodedInputStream input, ExtensionRegistryLite registry, FieldDescriptor field, Message defaultInstance) throws IOException {
            Builder subBuilder = defaultInstance.newBuilderForType();
            if (!field.isRepeated()) {
                Message originalMessage = (Message) getField(field);
                if (originalMessage != null) {
                    subBuilder.mergeFrom(originalMessage);
                }
            }
            input.readMessage(subBuilder, registry);
            return subBuilder.buildPartial();
        }

        public Object parseMessageFromBytes(ByteString bytes, ExtensionRegistryLite registry, FieldDescriptor field, Message defaultInstance) throws IOException {
            Builder subBuilder = defaultInstance.newBuilderForType();
            if (!field.isRepeated()) {
                Message originalMessage = (Message) getField(field);
                if (originalMessage != null) {
                    subBuilder.mergeFrom(originalMessage);
                }
            }
            subBuilder.mergeFrom(bytes, registry);
            return subBuilder.buildPartial();
        }

        public MergeTarget newMergeTargetForField(FieldDescriptor descriptor, Message defaultInstance) {
            throw new UnsupportedOperationException("newMergeTargetForField() called on FieldSet object");
        }

        public Object readPrimitiveField(CodedInputStream input, FieldType type, boolean checkUtf8) throws IOException {
            return FieldSet.readPrimitiveField(input, type, checkUtf8);
        }

        public Object finish() {
            throw new UnsupportedOperationException("finish() called on FieldSet object");
        }
    }

    MessageReflection() {
    }

    static void writeMessageTo(Message message, CodedOutputStream output, boolean alwaysWriteRequiredFields) throws IOException {
        boolean isMessageSet = message.getDescriptorForType().getOptions().getMessageSetWireFormat();
        Map<FieldDescriptor, Object> fields = message.getAllFields();
        if (alwaysWriteRequiredFields) {
            Map<FieldDescriptor, Object> fields2 = new TreeMap(fields);
            for (FieldDescriptor field : message.getDescriptorForType().getFields()) {
                FieldDescriptor field2;
                if (field2.isRequired() && !fields2.containsKey(field2)) {
                    fields2.put(field2, message.getField(field2));
                }
            }
            fields = fields2;
        }
        for (Entry<FieldDescriptor, Object> entry : fields.entrySet()) {
            field2 = (FieldDescriptor) entry.getKey();
            Object value = entry.getValue();
            if (isMessageSet && field2.isExtension() && field2.getType() == Type.MESSAGE && !field2.isRepeated()) {
                output.writeMessageSetExtension(field2.getNumber(), (Message) value);
            } else {
                FieldSet.writeField(field2, value, output);
            }
        }
        UnknownFieldSet unknownFields = message.getUnknownFields();
        if (isMessageSet) {
            unknownFields.writeAsMessageSetTo(output);
        } else {
            unknownFields.writeTo(output);
        }
    }

    static int getSerializedSize(Message message) {
        int size = 0;
        boolean isMessageSet = message.getDescriptorForType().getOptions().getMessageSetWireFormat();
        for (Entry<FieldDescriptor, Object> entry : message.getAllFields().entrySet()) {
            FieldDescriptor field = (FieldDescriptor) entry.getKey();
            Object value = entry.getValue();
            if (isMessageSet && field.isExtension() && field.getType() == Type.MESSAGE && !field.isRepeated()) {
                size += CodedOutputStream.computeMessageSetExtensionSize(field.getNumber(), (Message) value);
            } else {
                size += FieldSet.computeFieldSize(field, value);
            }
        }
        UnknownFieldSet unknownFields = message.getUnknownFields();
        if (isMessageSet) {
            return size + unknownFields.getSerializedSizeAsMessageSet();
        }
        return size + unknownFields.getSerializedSize();
    }

    static String delimitWithCommas(List<String> parts) {
        StringBuilder result = new StringBuilder();
        for (String part : parts) {
            if (result.length() > 0) {
                result.append(", ");
            }
            result.append(part);
        }
        return result.toString();
    }

    static boolean isInitialized(MessageOrBuilder message) {
        FieldDescriptor field;
        for (FieldDescriptor field2 : message.getDescriptorForType().getFields()) {
            if (field2.isRequired() && !message.hasField(field2)) {
                return false;
            }
        }
        for (Entry<FieldDescriptor, Object> entry : message.getAllFields().entrySet()) {
            field2 = (FieldDescriptor) entry.getKey();
            if (field2.getJavaType() == JavaType.MESSAGE) {
                if (field2.isRepeated()) {
                    for (Message element : (List) entry.getValue()) {
                        if (!element.isInitialized()) {
                            return false;
                        }
                    }
                    continue;
                } else if (!((Message) entry.getValue()).isInitialized()) {
                    return false;
                }
            }
        }
        return true;
    }

    private static String subMessagePrefix(String prefix, FieldDescriptor field, int index) {
        StringBuilder result = new StringBuilder(prefix);
        if (field.isExtension()) {
            result.append('(').append(field.getFullName()).append(')');
        } else {
            result.append(field.getName());
        }
        if (index != -1) {
            result.append('[').append(index).append(']');
        }
        result.append('.');
        return result.toString();
    }

    private static void findMissingFields(MessageOrBuilder message, String prefix, List<String> results) {
        FieldDescriptor field;
        for (FieldDescriptor field2 : message.getDescriptorForType().getFields()) {
            if (field2.isRequired() && !message.hasField(field2)) {
                results.add(prefix + field2.getName());
            }
        }
        for (Entry<FieldDescriptor, Object> entry : message.getAllFields().entrySet()) {
            field2 = (FieldDescriptor) entry.getKey();
            Object value = entry.getValue();
            if (field2.getJavaType() == JavaType.MESSAGE) {
                if (field2.isRepeated()) {
                    int i = 0;
                    for (MessageOrBuilder findMissingFields : (List) value) {
                        int i2 = i + 1;
                        findMissingFields(findMissingFields, subMessagePrefix(prefix, field2, i), results);
                        i = i2;
                    }
                } else if (message.hasField(field2)) {
                    findMissingFields((MessageOrBuilder) value, subMessagePrefix(prefix, field2, -1), results);
                }
            }
        }
    }

    static List<String> findMissingFields(MessageOrBuilder message) {
        List<String> results = new ArrayList();
        findMissingFields(message, "", results);
        return results;
    }

    static boolean mergeFieldFrom(CodedInputStream input, UnknownFieldSet.Builder unknownFields, ExtensionRegistryLite extensionRegistry, Descriptor type, MergeTarget target, int tag) throws IOException {
        if (type.getOptions().getMessageSetWireFormat() && tag == WireFormat.MESSAGE_SET_ITEM_TAG) {
            mergeMessageSetExtensionFromCodedStream(input, unknownFields, extensionRegistry, type, target);
            return true;
        }
        FieldDescriptor field;
        int wireType = WireFormat.getTagWireType(tag);
        int fieldNumber = WireFormat.getTagFieldNumber(tag);
        Message defaultInstance = null;
        if (!type.isExtensionNumber(fieldNumber)) {
            field = target.getContainerType() == ContainerType.MESSAGE ? type.findFieldByNumber(fieldNumber) : null;
        } else if (extensionRegistry instanceof ExtensionRegistry) {
            ExtensionInfo extension = target.findExtensionByNumber((ExtensionRegistry) extensionRegistry, type, fieldNumber);
            if (extension == null) {
                field = null;
            } else {
                field = extension.descriptor;
                defaultInstance = extension.defaultInstance;
                if (defaultInstance == null && field.getJavaType() == JavaType.MESSAGE) {
                    throw new IllegalStateException("Message-typed extension lacked default instance: " + field.getFullName());
                }
            }
        } else {
            field = null;
        }
        boolean unknown = false;
        boolean packed = false;
        if (field == null) {
            unknown = true;
        } else if (wireType == FieldSet.getWireFormatForFieldType(field.getLiteType(), false)) {
            packed = false;
        } else if (field.isPackable() && wireType == FieldSet.getWireFormatForFieldType(field.getLiteType(), true)) {
            packed = true;
        } else {
            unknown = true;
        }
        if (unknown) {
            return unknownFields.mergeFieldFrom(tag, input);
        }
        if (packed) {
            int limit = input.pushLimit(input.readRawVarint32());
            if (field.getLiteType() == FieldType.ENUM) {
                while (input.getBytesUntilLimit() > 0) {
                    EnumValueDescriptor value = field.getEnumType().findValueByNumber(input.readEnum());
                    if (value == null) {
                        return true;
                    }
                    target.addRepeatedField(field, value);
                }
            } else {
                while (input.getBytesUntilLimit() > 0) {
                    target.addRepeatedField(field, target.readPrimitiveField(input, field.getLiteType(), field.needsUtf8Check()));
                }
            }
            input.popLimit(limit);
        } else {
            Object parseGroup;
            switch (field.getType()) {
                case GROUP:
                    parseGroup = target.parseGroup(input, extensionRegistry, field, defaultInstance);
                    break;
                case MESSAGE:
                    parseGroup = target.parseMessage(input, extensionRegistry, field, defaultInstance);
                    break;
                case ENUM:
                    int rawValue = input.readEnum();
                    parseGroup = field.getEnumType().findValueByNumber(rawValue);
                    if (parseGroup == null) {
                        unknownFields.mergeVarintField(fieldNumber, rawValue);
                        return true;
                    }
                    break;
                default:
                    parseGroup = target.readPrimitiveField(input, field.getLiteType(), field.needsUtf8Check());
                    break;
            }
            if (field.isRepeated()) {
                target.addRepeatedField(field, parseGroup);
            } else {
                target.setField(field, parseGroup);
            }
        }
        return true;
    }

    private static void mergeMessageSetExtensionFromCodedStream(CodedInputStream input, UnknownFieldSet.Builder unknownFields, ExtensionRegistryLite extensionRegistry, Descriptor type, MergeTarget target) throws IOException {
        int typeId = 0;
        ByteString rawBytes = null;
        ExtensionInfo extension = null;
        while (true) {
            int tag = input.readTag();
            if (tag == 0) {
                break;
            } else if (tag == WireFormat.MESSAGE_SET_TYPE_ID_TAG) {
                typeId = input.readUInt32();
                if (typeId != 0 && (extensionRegistry instanceof ExtensionRegistry)) {
                    extension = target.findExtensionByNumber((ExtensionRegistry) extensionRegistry, type, typeId);
                }
            } else if (tag == WireFormat.MESSAGE_SET_MESSAGE_TAG) {
                if (typeId == 0 || extension == null || !ExtensionRegistryLite.isEagerlyParseMessageSets()) {
                    rawBytes = input.readBytes();
                } else {
                    eagerlyMergeMessageSetExtension(input, extension, extensionRegistry, target);
                    rawBytes = null;
                }
            } else if (!input.skipField(tag)) {
                break;
            }
        }
        input.checkLastTagWas(WireFormat.MESSAGE_SET_ITEM_END_TAG);
        if (rawBytes != null && typeId != 0) {
            if (extension != null) {
                mergeMessageSetExtensionFromBytes(rawBytes, extension, extensionRegistry, target);
            } else if (rawBytes != null) {
                unknownFields.mergeField(typeId, Field.newBuilder().addLengthDelimited(rawBytes).build());
            }
        }
    }

    private static void mergeMessageSetExtensionFromBytes(ByteString rawBytes, ExtensionInfo extension, ExtensionRegistryLite extensionRegistry, MergeTarget target) throws IOException {
        FieldDescriptor field = extension.descriptor;
        if (target.hasField(field) || ExtensionRegistryLite.isEagerlyParseMessageSets()) {
            target.setField(field, target.parseMessageFromBytes(rawBytes, extensionRegistry, field, extension.defaultInstance));
        } else {
            target.setField(field, new LazyField(extension.defaultInstance, extensionRegistry, rawBytes));
        }
    }

    private static void eagerlyMergeMessageSetExtension(CodedInputStream input, ExtensionInfo extension, ExtensionRegistryLite extensionRegistry, MergeTarget target) throws IOException {
        FieldDescriptor field = extension.descriptor;
        target.setField(field, target.parseMessage(input, extensionRegistry, field, extension.defaultInstance));
    }
}
