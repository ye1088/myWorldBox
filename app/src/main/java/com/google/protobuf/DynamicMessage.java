package com.google.protobuf;

import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.EnumValueDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor.JavaType;
import com.google.protobuf.Descriptors.FieldDescriptor.Type;
import com.google.protobuf.Descriptors.OneofDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class DynamicMessage extends AbstractMessage {
    private final FieldSet<FieldDescriptor> fields;
    private int memoizedSize = -1;
    private final FieldDescriptor[] oneofCases;
    private final Descriptor type;
    private final UnknownFieldSet unknownFields;

    public static final class Builder extends com.google.protobuf.AbstractMessage.Builder<Builder> {
        private FieldSet<FieldDescriptor> fields;
        private final FieldDescriptor[] oneofCases;
        private final Descriptor type;
        private UnknownFieldSet unknownFields;

        private Builder(Descriptor type) {
            this.type = type;
            this.fields = FieldSet.newFieldSet();
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
            this.oneofCases = new FieldDescriptor[type.toProto().getOneofDeclCount()];
        }

        public Builder clear() {
            if (this.fields.isImmutable()) {
                this.fields = FieldSet.newFieldSet();
            } else {
                this.fields.clear();
            }
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
            return this;
        }

        public Builder mergeFrom(Message other) {
            if (!(other instanceof DynamicMessage)) {
                return (Builder) super.mergeFrom(other);
            }
            DynamicMessage otherDynamicMessage = (DynamicMessage) other;
            if (otherDynamicMessage.type != this.type) {
                throw new IllegalArgumentException("mergeFrom(Message) can only merge messages of the same type.");
            }
            ensureIsMutable();
            this.fields.mergeFrom(otherDynamicMessage.fields);
            mergeUnknownFields(otherDynamicMessage.unknownFields);
            int i = 0;
            while (i < this.oneofCases.length) {
                if (this.oneofCases[i] == null) {
                    this.oneofCases[i] = otherDynamicMessage.oneofCases[i];
                } else if (!(otherDynamicMessage.oneofCases[i] == null || this.oneofCases[i] == otherDynamicMessage.oneofCases[i])) {
                    this.fields.clearField(this.oneofCases[i]);
                    this.oneofCases[i] = otherDynamicMessage.oneofCases[i];
                }
                i++;
            }
            return this;
        }

        public DynamicMessage build() {
            if (isInitialized()) {
                return buildPartial();
            }
            throw com.google.protobuf.AbstractMessage.Builder.newUninitializedMessageException(new DynamicMessage(this.type, this.fields, (FieldDescriptor[]) Arrays.copyOf(this.oneofCases, this.oneofCases.length), this.unknownFields));
        }

        private DynamicMessage buildParsed() throws InvalidProtocolBufferException {
            if (isInitialized()) {
                return buildPartial();
            }
            throw com.google.protobuf.AbstractMessage.Builder.newUninitializedMessageException(new DynamicMessage(this.type, this.fields, (FieldDescriptor[]) Arrays.copyOf(this.oneofCases, this.oneofCases.length), this.unknownFields)).asInvalidProtocolBufferException();
        }

        public DynamicMessage buildPartial() {
            this.fields.makeImmutable();
            return new DynamicMessage(this.type, this.fields, (FieldDescriptor[]) Arrays.copyOf(this.oneofCases, this.oneofCases.length), this.unknownFields);
        }

        public Builder clone() {
            Builder result = new Builder(this.type);
            result.fields.mergeFrom(this.fields);
            result.mergeUnknownFields(this.unknownFields);
            System.arraycopy(this.oneofCases, 0, result.oneofCases, 0, this.oneofCases.length);
            return result;
        }

        public boolean isInitialized() {
            return DynamicMessage.isInitialized(this.type, this.fields);
        }

        public Descriptor getDescriptorForType() {
            return this.type;
        }

        public DynamicMessage getDefaultInstanceForType() {
            return DynamicMessage.getDefaultInstance(this.type);
        }

        public Map<FieldDescriptor, Object> getAllFields() {
            return this.fields.getAllFields();
        }

        public Builder newBuilderForField(FieldDescriptor field) {
            verifyContainingType(field);
            if (field.getJavaType() == JavaType.MESSAGE) {
                return new Builder(field.getMessageType());
            }
            throw new IllegalArgumentException("newBuilderForField is only valid for fields with message type.");
        }

        public boolean hasOneof(OneofDescriptor oneof) {
            verifyOneofContainingType(oneof);
            if (this.oneofCases[oneof.getIndex()] == null) {
                return false;
            }
            return true;
        }

        public FieldDescriptor getOneofFieldDescriptor(OneofDescriptor oneof) {
            verifyOneofContainingType(oneof);
            return this.oneofCases[oneof.getIndex()];
        }

        public Builder clearOneof(OneofDescriptor oneof) {
            verifyOneofContainingType(oneof);
            FieldDescriptor field = this.oneofCases[oneof.getIndex()];
            if (field != null) {
                clearField(field);
            }
            return this;
        }

        public boolean hasField(FieldDescriptor field) {
            verifyContainingType(field);
            return this.fields.hasField(field);
        }

        public Object getField(FieldDescriptor field) {
            verifyContainingType(field);
            Object result = this.fields.getField(field);
            if (result != null) {
                return result;
            }
            if (field.isRepeated()) {
                return Collections.emptyList();
            }
            if (field.getJavaType() == JavaType.MESSAGE) {
                return DynamicMessage.getDefaultInstance(field.getMessageType());
            }
            return field.getDefaultValue();
        }

        public Builder setField(FieldDescriptor field, Object value) {
            verifyContainingType(field);
            ensureIsMutable();
            if (field.getType() == Type.ENUM) {
                ensureEnumValueDescriptor(field, value);
            }
            OneofDescriptor oneofDescriptor = field.getContainingOneof();
            if (oneofDescriptor != null) {
                int index = oneofDescriptor.getIndex();
                FieldDescriptor oldField = this.oneofCases[index];
                if (!(oldField == null || oldField == field)) {
                    this.fields.clearField(oldField);
                }
                this.oneofCases[index] = field;
            }
            this.fields.setField(field, value);
            return this;
        }

        public Builder clearField(FieldDescriptor field) {
            verifyContainingType(field);
            ensureIsMutable();
            OneofDescriptor oneofDescriptor = field.getContainingOneof();
            if (oneofDescriptor != null) {
                int index = oneofDescriptor.getIndex();
                if (this.oneofCases[index] == field) {
                    this.oneofCases[index] = null;
                }
            }
            this.fields.clearField(field);
            return this;
        }

        public int getRepeatedFieldCount(FieldDescriptor field) {
            verifyContainingType(field);
            return this.fields.getRepeatedFieldCount(field);
        }

        public Object getRepeatedField(FieldDescriptor field, int index) {
            verifyContainingType(field);
            return this.fields.getRepeatedField(field, index);
        }

        public Builder setRepeatedField(FieldDescriptor field, int index, Object value) {
            verifyContainingType(field);
            ensureIsMutable();
            this.fields.setRepeatedField(field, index, value);
            return this;
        }

        public Builder addRepeatedField(FieldDescriptor field, Object value) {
            verifyContainingType(field);
            ensureIsMutable();
            this.fields.addRepeatedField(field, value);
            return this;
        }

        public UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        public Builder setUnknownFields(UnknownFieldSet unknownFields) {
            this.unknownFields = unknownFields;
            return this;
        }

        public Builder mergeUnknownFields(UnknownFieldSet unknownFields) {
            this.unknownFields = UnknownFieldSet.newBuilder(this.unknownFields).mergeFrom(unknownFields).build();
            return this;
        }

        private void verifyContainingType(FieldDescriptor field) {
            if (field.getContainingType() != this.type) {
                throw new IllegalArgumentException("FieldDescriptor does not match message type.");
            }
        }

        private void verifyOneofContainingType(OneofDescriptor oneof) {
            if (oneof.getContainingType() != this.type) {
                throw new IllegalArgumentException("OneofDescriptor does not match message type.");
            }
        }

        private void ensureSingularEnumValueDescriptor(FieldDescriptor field, Object value) {
            if (value == null) {
                throw new NullPointerException();
            } else if (!(value instanceof EnumValueDescriptor)) {
                throw new IllegalArgumentException("DynamicMessage should use EnumValueDescriptor to set Enum Value.");
            } else if (field.getEnumType() != ((EnumValueDescriptor) value).getType()) {
                throw new IllegalArgumentException("EnumValueDescriptor doesn't much Enum Field.");
            }
        }

        private void ensureEnumValueDescriptor(FieldDescriptor field, Object value) {
            if (field.isRepeated()) {
                for (Object item : (List) value) {
                    ensureSingularEnumValueDescriptor(field, item);
                }
                return;
            }
            ensureSingularEnumValueDescriptor(field, value);
        }

        private void ensureIsMutable() {
            if (this.fields.isImmutable()) {
                this.fields = this.fields.clone();
            }
        }

        public com.google.protobuf.Message.Builder getFieldBuilder(FieldDescriptor field) {
            throw new UnsupportedOperationException("getFieldBuilder() called on a_isRightVersion dynamic message type.");
        }
    }

    DynamicMessage(Descriptor type, FieldSet<FieldDescriptor> fields, FieldDescriptor[] oneofCases, UnknownFieldSet unknownFields) {
        this.type = type;
        this.fields = fields;
        this.oneofCases = oneofCases;
        this.unknownFields = unknownFields;
    }

    public static DynamicMessage getDefaultInstance(Descriptor type) {
        return new DynamicMessage(type, FieldSet.emptySet(), new FieldDescriptor[type.toProto().getOneofDeclCount()], UnknownFieldSet.getDefaultInstance());
    }

    public static DynamicMessage parseFrom(Descriptor type, CodedInputStream input) throws IOException {
        return ((Builder) newBuilder(type).mergeFrom(input)).buildParsed();
    }

    public static DynamicMessage parseFrom(Descriptor type, CodedInputStream input, ExtensionRegistry extensionRegistry) throws IOException {
        return ((Builder) newBuilder(type).mergeFrom(input, (ExtensionRegistryLite) extensionRegistry)).buildParsed();
    }

    public static DynamicMessage parseFrom(Descriptor type, ByteString data) throws InvalidProtocolBufferException {
        return ((Builder) newBuilder(type).mergeFrom(data)).buildParsed();
    }

    public static DynamicMessage parseFrom(Descriptor type, ByteString data, ExtensionRegistry extensionRegistry) throws InvalidProtocolBufferException {
        return ((Builder) newBuilder(type).mergeFrom(data, (ExtensionRegistryLite) extensionRegistry)).buildParsed();
    }

    public static DynamicMessage parseFrom(Descriptor type, byte[] data) throws InvalidProtocolBufferException {
        return ((Builder) newBuilder(type).mergeFrom(data)).buildParsed();
    }

    public static DynamicMessage parseFrom(Descriptor type, byte[] data, ExtensionRegistry extensionRegistry) throws InvalidProtocolBufferException {
        return ((Builder) newBuilder(type).mergeFrom(data, (ExtensionRegistryLite) extensionRegistry)).buildParsed();
    }

    public static DynamicMessage parseFrom(Descriptor type, InputStream input) throws IOException {
        return ((Builder) newBuilder(type).mergeFrom(input)).buildParsed();
    }

    public static DynamicMessage parseFrom(Descriptor type, InputStream input, ExtensionRegistry extensionRegistry) throws IOException {
        return ((Builder) newBuilder(type).mergeFrom(input, (ExtensionRegistryLite) extensionRegistry)).buildParsed();
    }

    public static Builder newBuilder(Descriptor type) {
        return new Builder(type);
    }

    public static Builder newBuilder(Message prototype) {
        return new Builder(prototype.getDescriptorForType()).mergeFrom(prototype);
    }

    public Descriptor getDescriptorForType() {
        return this.type;
    }

    public DynamicMessage getDefaultInstanceForType() {
        return getDefaultInstance(this.type);
    }

    public Map<FieldDescriptor, Object> getAllFields() {
        return this.fields.getAllFields();
    }

    public boolean hasOneof(OneofDescriptor oneof) {
        verifyOneofContainingType(oneof);
        if (this.oneofCases[oneof.getIndex()] == null) {
            return false;
        }
        return true;
    }

    public FieldDescriptor getOneofFieldDescriptor(OneofDescriptor oneof) {
        verifyOneofContainingType(oneof);
        return this.oneofCases[oneof.getIndex()];
    }

    public boolean hasField(FieldDescriptor field) {
        verifyContainingType(field);
        return this.fields.hasField(field);
    }

    public Object getField(FieldDescriptor field) {
        verifyContainingType(field);
        Object result = this.fields.getField(field);
        if (result != null) {
            return result;
        }
        if (field.isRepeated()) {
            return Collections.emptyList();
        }
        if (field.getJavaType() == JavaType.MESSAGE) {
            return getDefaultInstance(field.getMessageType());
        }
        return field.getDefaultValue();
    }

    public int getRepeatedFieldCount(FieldDescriptor field) {
        verifyContainingType(field);
        return this.fields.getRepeatedFieldCount(field);
    }

    public Object getRepeatedField(FieldDescriptor field, int index) {
        verifyContainingType(field);
        return this.fields.getRepeatedField(field, index);
    }

    public UnknownFieldSet getUnknownFields() {
        return this.unknownFields;
    }

    static boolean isInitialized(Descriptor type, FieldSet<FieldDescriptor> fields) {
        for (FieldDescriptor field : type.getFields()) {
            if (field.isRequired() && !fields.hasField(field)) {
                return false;
            }
        }
        return fields.isInitialized();
    }

    public boolean isInitialized() {
        return isInitialized(this.type, this.fields);
    }

    public void writeTo(CodedOutputStream output) throws IOException {
        if (this.type.getOptions().getMessageSetWireFormat()) {
            this.fields.writeMessageSetTo(output);
            this.unknownFields.writeAsMessageSetTo(output);
            return;
        }
        this.fields.writeTo(output);
        this.unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
        int size = this.memoizedSize;
        if (size != -1) {
            return size;
        }
        if (this.type.getOptions().getMessageSetWireFormat()) {
            size = this.fields.getMessageSetSerializedSize() + this.unknownFields.getSerializedSizeAsMessageSet();
        } else {
            size = this.fields.getSerializedSize() + this.unknownFields.getSerializedSize();
        }
        this.memoizedSize = size;
        return size;
    }

    public Builder newBuilderForType() {
        return new Builder(this.type);
    }

    public Builder toBuilder() {
        return newBuilderForType().mergeFrom((Message) this);
    }

    public Parser<DynamicMessage> getParserForType() {
        return new AbstractParser<DynamicMessage>() {
            public DynamicMessage parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                Builder builder = DynamicMessage.newBuilder(DynamicMessage.this.type);
                try {
                    builder.mergeFrom(input, extensionRegistry);
                    return builder.buildPartial();
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(builder.buildPartial());
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(builder.buildPartial());
                }
            }
        };
    }

    private void verifyContainingType(FieldDescriptor field) {
        if (field.getContainingType() != this.type) {
            throw new IllegalArgumentException("FieldDescriptor does not match message type.");
        }
    }

    private void verifyOneofContainingType(OneofDescriptor oneof) {
        if (oneof.getContainingType() != this.type) {
            throw new IllegalArgumentException("OneofDescriptor does not match message type.");
        }
    }
}
