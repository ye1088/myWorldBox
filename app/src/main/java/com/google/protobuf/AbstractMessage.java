package com.google.protobuf;

import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor.JavaType;
import com.google.protobuf.Descriptors.FieldDescriptor.Type;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.Internal.EnumLite;
import com.huluxia.module.h$a;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class AbstractMessage extends AbstractMessageLite implements Message {
    private int memoizedSize = -1;

    public static abstract class Builder<BuilderType extends Builder> extends com.google.protobuf.AbstractMessageLite.Builder<BuilderType> implements com.google.protobuf.Message.Builder {
        public abstract BuilderType clone();

        public boolean hasOneof(OneofDescriptor oneof) {
            throw new UnsupportedOperationException("hasOneof() is not implemented.");
        }

        public FieldDescriptor getOneofFieldDescriptor(OneofDescriptor oneof) {
            throw new UnsupportedOperationException("getOneofFieldDescriptor() is not implemented.");
        }

        public BuilderType clearOneof(OneofDescriptor oneof) {
            throw new UnsupportedOperationException("clearOneof() is not implemented.");
        }

        public BuilderType clear() {
            for (Entry<FieldDescriptor, Object> entry : getAllFields().entrySet()) {
                clearField((FieldDescriptor) entry.getKey());
            }
            return this;
        }

        public List<String> findInitializationErrors() {
            return MessageReflection.findMissingFields(this);
        }

        public String getInitializationErrorString() {
            return MessageReflection.delimitWithCommas(findInitializationErrors());
        }

        public BuilderType mergeFrom(Message other) {
            if (other.getDescriptorForType() != getDescriptorForType()) {
                throw new IllegalArgumentException("mergeFrom(Message) can only merge messages of the same type.");
            }
            for (Entry<FieldDescriptor, Object> entry : other.getAllFields().entrySet()) {
                FieldDescriptor field = (FieldDescriptor) entry.getKey();
                if (field.isRepeated()) {
                    for (Object element : (List) entry.getValue()) {
                        addRepeatedField(field, element);
                    }
                } else if (field.getJavaType() == JavaType.MESSAGE) {
                    Message existingValue = (Message) getField(field);
                    if (existingValue == existingValue.getDefaultInstanceForType()) {
                        setField(field, entry.getValue());
                    } else {
                        setField(field, existingValue.newBuilderForType().mergeFrom(existingValue).mergeFrom((Message) entry.getValue()).build());
                    }
                } else {
                    setField(field, entry.getValue());
                }
            }
            mergeUnknownFields(other.getUnknownFields());
            return this;
        }

        public BuilderType mergeFrom(CodedInputStream input) throws IOException {
            return mergeFrom(input, ExtensionRegistry.getEmptyRegistry());
        }

        public BuilderType mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder(getUnknownFields());
            int tag;
            do {
                tag = input.readTag();
                if (tag == 0) {
                    break;
                }
            } while (MessageReflection.mergeFieldFrom(input, unknownFields, extensionRegistry, getDescriptorForType(), new BuilderAdapter(this), tag));
            setUnknownFields(unknownFields.build());
            return this;
        }

        public BuilderType mergeUnknownFields(UnknownFieldSet unknownFields) {
            setUnknownFields(UnknownFieldSet.newBuilder(getUnknownFields()).mergeFrom(unknownFields).build());
            return this;
        }

        public com.google.protobuf.Message.Builder getFieldBuilder(FieldDescriptor field) {
            throw new UnsupportedOperationException("getFieldBuilder() called on an unsupported message type.");
        }

        public String toString() {
            return TextFormat.printToString((MessageOrBuilder) this);
        }

        protected static UninitializedMessageException newUninitializedMessageException(Message message) {
            return new UninitializedMessageException(MessageReflection.findMissingFields(message));
        }

        public BuilderType mergeFrom(ByteString data) throws InvalidProtocolBufferException {
            return (Builder) super.mergeFrom(data);
        }

        public BuilderType mergeFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Builder) super.mergeFrom(data, extensionRegistry);
        }

        public BuilderType mergeFrom(byte[] data) throws InvalidProtocolBufferException {
            return (Builder) super.mergeFrom(data);
        }

        public BuilderType mergeFrom(byte[] data, int off, int len) throws InvalidProtocolBufferException {
            return (Builder) super.mergeFrom(data, off, len);
        }

        public BuilderType mergeFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Builder) super.mergeFrom(data, extensionRegistry);
        }

        public BuilderType mergeFrom(byte[] data, int off, int len, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Builder) super.mergeFrom(data, off, len, extensionRegistry);
        }

        public BuilderType mergeFrom(InputStream input) throws IOException {
            return (Builder) super.mergeFrom(input);
        }

        public BuilderType mergeFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Builder) super.mergeFrom(input, extensionRegistry);
        }

        public boolean mergeDelimitedFrom(InputStream input) throws IOException {
            return super.mergeDelimitedFrom(input);
        }

        public boolean mergeDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return super.mergeDelimitedFrom(input, extensionRegistry);
        }
    }

    public boolean isInitialized() {
        return MessageReflection.isInitialized(this);
    }

    public List<String> findInitializationErrors() {
        return MessageReflection.findMissingFields(this);
    }

    public String getInitializationErrorString() {
        return MessageReflection.delimitWithCommas(findInitializationErrors());
    }

    public boolean hasOneof(OneofDescriptor oneof) {
        throw new UnsupportedOperationException("hasOneof() is not implemented.");
    }

    public FieldDescriptor getOneofFieldDescriptor(OneofDescriptor oneof) {
        throw new UnsupportedOperationException("getOneofFieldDescriptor() is not implemented.");
    }

    public final String toString() {
        return TextFormat.printToString((MessageOrBuilder) this);
    }

    public void writeTo(CodedOutputStream output) throws IOException {
        MessageReflection.writeMessageTo(this, output, false);
    }

    public int getSerializedSize() {
        int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        this.memoizedSize = MessageReflection.getSerializedSize(this);
        return this.memoizedSize;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Message)) {
            return false;
        }
        Message otherMessage = (Message) other;
        if (getDescriptorForType() != otherMessage.getDescriptorForType()) {
            return false;
        }
        if (compareFields(getAllFields(), otherMessage.getAllFields()) && getUnknownFields().equals(otherMessage.getUnknownFields())) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int hash = this.memoizedHashCode;
        if (hash != 0) {
            return hash;
        }
        hash = (hashFields(getDescriptorForType().hashCode() + h$a.asU, getAllFields()) * 29) + getUnknownFields().hashCode();
        this.memoizedHashCode = hash;
        return hash;
    }

    private static ByteString toByteString(Object value) {
        if (value instanceof byte[]) {
            return ByteString.copyFrom((byte[]) value);
        }
        return (ByteString) value;
    }

    private static boolean compareBytes(Object a, Object b) {
        if ((a instanceof byte[]) && (b instanceof byte[])) {
            return Arrays.equals((byte[]) a, (byte[]) b);
        }
        return toByteString(a).equals(toByteString(b));
    }

    static boolean compareFields(Map<FieldDescriptor, Object> a, Map<FieldDescriptor, Object> b) {
        if (a.size() != b.size()) {
            return false;
        }
        for (FieldDescriptor descriptor : a.keySet()) {
            if (!b.containsKey(descriptor)) {
                return false;
            }
            List value1 = a.get(descriptor);
            List value2 = b.get(descriptor);
            if (descriptor.getType() == Type.BYTES) {
                if (descriptor.isRepeated()) {
                    List list1 = value1;
                    List list2 = value2;
                    if (list1.size() != list2.size()) {
                        return false;
                    }
                    for (int i = 0; i < list1.size(); i++) {
                        if (!compareBytes(list1.get(i), list2.get(i))) {
                            return false;
                        }
                    }
                    continue;
                } else if (!compareBytes(value1, value2)) {
                    return false;
                }
            } else if (!value1.equals(value2)) {
                return false;
            }
        }
        return true;
    }

    protected static int hashFields(int hash, Map<FieldDescriptor, Object> map) {
        for (Entry<FieldDescriptor, Object> entry : map.entrySet()) {
            FieldDescriptor field = (FieldDescriptor) entry.getKey();
            List<? extends EnumLite> value = entry.getValue();
            hash = (hash * 37) + field.getNumber();
            if (field.getType() != Type.ENUM) {
                hash = (hash * 53) + value.hashCode();
            } else if (field.isRepeated()) {
                hash = (hash * 53) + Internal.hashEnumList(value);
            } else {
                hash = (hash * 53) + Internal.hashEnum((EnumLite) value);
            }
        }
        return hash;
    }

    UninitializedMessageException newUninitializedMessageException() {
        return Builder.newUninitializedMessageException(this);
    }
}
