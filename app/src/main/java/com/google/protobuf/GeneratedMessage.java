package com.google.protobuf;

import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.EnumValueDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor.JavaType;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.Internal.EnumLite;
import com.google.protobuf.WireFormat.FieldType;
import com.huluxia.service.i;
import java.io.IOException;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import org.apache.tools.ant.taskdefs.optional.ejb.EjbJar.NamingScheme;

public abstract class GeneratedMessage extends AbstractMessage implements Serializable {
    protected static boolean alwaysUseFieldBuilders = false;
    private static final long serialVersionUID = 1;

    public static abstract class Builder<BuilderType extends Builder> extends com.google.protobuf.AbstractMessage.Builder<BuilderType> {
        private BuilderParent builderParent;
        private boolean isClean;
        private BuilderParentImpl meAsParent;
        private UnknownFieldSet unknownFields;

        private class BuilderParentImpl implements BuilderParent {
            private BuilderParentImpl() {
            }

            public void markDirty() {
                Builder.this.onChanged();
            }
        }

        protected abstract FieldAccessorTable internalGetFieldAccessorTable();

        protected Builder() {
            this(null);
        }

        protected Builder(BuilderParent builderParent) {
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
            this.builderParent = builderParent;
        }

        void dispose() {
            this.builderParent = null;
        }

        protected void onBuilt() {
            if (this.builderParent != null) {
                markClean();
            }
        }

        protected void markClean() {
            this.isClean = true;
        }

        protected boolean isClean() {
            return this.isClean;
        }

        public BuilderType clone() {
            throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
        }

        public BuilderType clear() {
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
            onChanged();
            return this;
        }

        public Descriptor getDescriptorForType() {
            return internalGetFieldAccessorTable().descriptor;
        }

        public Map<FieldDescriptor, Object> getAllFields() {
            return Collections.unmodifiableMap(getAllFieldsMutable());
        }

        private Map<FieldDescriptor, Object> getAllFieldsMutable() {
            TreeMap<FieldDescriptor, Object> result = new TreeMap();
            for (FieldDescriptor field : internalGetFieldAccessorTable().descriptor.getFields()) {
                if (field.isRepeated()) {
                    List value = (List) getField(field);
                    if (!value.isEmpty()) {
                        result.put(field, value);
                    }
                } else if (hasField(field)) {
                    result.put(field, getField(field));
                }
            }
            return result;
        }

        public com.google.protobuf.Message.Builder newBuilderForField(FieldDescriptor field) {
            return internalGetFieldAccessorTable().getField(field).newBuilder();
        }

        public com.google.protobuf.Message.Builder getFieldBuilder(FieldDescriptor field) {
            return internalGetFieldAccessorTable().getField(field).getBuilder(this);
        }

        public boolean hasOneof(OneofDescriptor oneof) {
            return internalGetFieldAccessorTable().getOneof(oneof).has(this);
        }

        public FieldDescriptor getOneofFieldDescriptor(OneofDescriptor oneof) {
            return internalGetFieldAccessorTable().getOneof(oneof).get(this);
        }

        public boolean hasField(FieldDescriptor field) {
            return internalGetFieldAccessorTable().getField(field).has(this);
        }

        public Object getField(FieldDescriptor field) {
            Object obj = internalGetFieldAccessorTable().getField(field).get(this);
            if (field.isRepeated()) {
                return Collections.unmodifiableList((List) obj);
            }
            return obj;
        }

        public BuilderType setField(FieldDescriptor field, Object value) {
            internalGetFieldAccessorTable().getField(field).set(this, value);
            return this;
        }

        public BuilderType clearField(FieldDescriptor field) {
            internalGetFieldAccessorTable().getField(field).clear(this);
            return this;
        }

        public BuilderType clearOneof(OneofDescriptor oneof) {
            internalGetFieldAccessorTable().getOneof(oneof).clear(this);
            return this;
        }

        public int getRepeatedFieldCount(FieldDescriptor field) {
            return internalGetFieldAccessorTable().getField(field).getRepeatedCount(this);
        }

        public Object getRepeatedField(FieldDescriptor field, int index) {
            return internalGetFieldAccessorTable().getField(field).getRepeated(this, index);
        }

        public BuilderType setRepeatedField(FieldDescriptor field, int index, Object value) {
            internalGetFieldAccessorTable().getField(field).setRepeated(this, index, value);
            return this;
        }

        public BuilderType addRepeatedField(FieldDescriptor field, Object value) {
            internalGetFieldAccessorTable().getField(field).addRepeated(this, value);
            return this;
        }

        public final BuilderType setUnknownFields(UnknownFieldSet unknownFields) {
            this.unknownFields = unknownFields;
            onChanged();
            return this;
        }

        public final BuilderType mergeUnknownFields(UnknownFieldSet unknownFields) {
            this.unknownFields = UnknownFieldSet.newBuilder(this.unknownFields).mergeFrom(unknownFields).build();
            onChanged();
            return this;
        }

        public boolean isInitialized() {
            for (FieldDescriptor field : getDescriptorForType().getFields()) {
                if (field.isRequired() && !hasField(field)) {
                    return false;
                }
                if (field.getJavaType() == JavaType.MESSAGE) {
                    if (field.isRepeated()) {
                        for (Message element : (List) getField(field)) {
                            if (!element.isInitialized()) {
                                return false;
                            }
                        }
                        continue;
                    } else if (hasField(field) && !((Message) getField(field)).isInitialized()) {
                        return false;
                    }
                }
            }
            return true;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        protected boolean parseUnknownField(CodedInputStream input, com.google.protobuf.UnknownFieldSet.Builder unknownFields, ExtensionRegistryLite extensionRegistry, int tag) throws IOException {
            return unknownFields.mergeFieldFrom(tag, input);
        }

        protected BuilderParent getParentForChildren() {
            if (this.meAsParent == null) {
                this.meAsParent = new BuilderParentImpl();
            }
            return this.meAsParent;
        }

        protected final void onChanged() {
            if (this.isClean && this.builderParent != null) {
                this.builderParent.markDirty();
                this.isClean = false;
            }
        }
    }

    public interface ExtendableMessageOrBuilder<MessageType extends ExtendableMessage> extends MessageOrBuilder {
        Message getDefaultInstanceForType();

        <Type> Type getExtension(Extension<MessageType, Type> extension);

        <Type> Type getExtension(Extension<MessageType, List<Type>> extension, int i);

        <Type> int getExtensionCount(Extension<MessageType, List<Type>> extension);

        <Type> boolean hasExtension(Extension<MessageType, Type> extension);
    }

    public static abstract class ExtendableBuilder<MessageType extends ExtendableMessage, BuilderType extends ExtendableBuilder> extends Builder<BuilderType> implements ExtendableMessageOrBuilder<MessageType> {
        private FieldSet<FieldDescriptor> extensions = FieldSet.emptySet();

        protected ExtendableBuilder() {
        }

        protected ExtendableBuilder(BuilderParent parent) {
            super(parent);
        }

        void internalSetExtensionSet(FieldSet<FieldDescriptor> extensions) {
            this.extensions = extensions;
        }

        public BuilderType clear() {
            this.extensions = FieldSet.emptySet();
            return (ExtendableBuilder) super.clear();
        }

        public BuilderType clone() {
            throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
        }

        private void ensureExtensionsIsMutable() {
            if (this.extensions.isImmutable()) {
                this.extensions = this.extensions.clone();
            }
        }

        private void verifyExtensionContainingType(Extension<MessageType, ?> extension) {
            if (extension.getDescriptor().getContainingType() != getDescriptorForType()) {
                throw new IllegalArgumentException("Extension is for type \"" + extension.getDescriptor().getContainingType().getFullName() + "\" which does not match message type \"" + getDescriptorForType().getFullName() + "\".");
            }
        }

        public final <Type> boolean hasExtension(Extension<MessageType, Type> extension) {
            verifyExtensionContainingType(extension);
            return this.extensions.hasField(extension.getDescriptor());
        }

        public final <Type> int getExtensionCount(Extension<MessageType, List<Type>> extension) {
            verifyExtensionContainingType(extension);
            return this.extensions.getRepeatedFieldCount(extension.getDescriptor());
        }

        public final <Type> Type getExtension(Extension<MessageType, Type> extension) {
            verifyExtensionContainingType(extension);
            FieldDescriptor descriptor = extension.getDescriptor();
            Object value = this.extensions.getField(descriptor);
            if (value != null) {
                return extension.fromReflectionType(value);
            }
            if (descriptor.isRepeated()) {
                return Collections.emptyList();
            }
            if (descriptor.getJavaType() == JavaType.MESSAGE) {
                return extension.getMessageDefaultInstance();
            }
            return extension.fromReflectionType(descriptor.getDefaultValue());
        }

        public final <Type> Type getExtension(Extension<MessageType, List<Type>> extension, int index) {
            verifyExtensionContainingType(extension);
            return extension.singularFromReflectionType(this.extensions.getRepeatedField(extension.getDescriptor(), index));
        }

        public final <Type> BuilderType setExtension(Extension<MessageType, Type> extension, Type value) {
            verifyExtensionContainingType(extension);
            ensureExtensionsIsMutable();
            this.extensions.setField(extension.getDescriptor(), extension.toReflectionType(value));
            onChanged();
            return this;
        }

        public final <Type> BuilderType setExtension(Extension<MessageType, List<Type>> extension, int index, Type value) {
            verifyExtensionContainingType(extension);
            ensureExtensionsIsMutable();
            this.extensions.setRepeatedField(extension.getDescriptor(), index, extension.singularToReflectionType(value));
            onChanged();
            return this;
        }

        public final <Type> BuilderType addExtension(Extension<MessageType, List<Type>> extension, Type value) {
            verifyExtensionContainingType(extension);
            ensureExtensionsIsMutable();
            this.extensions.addRepeatedField(extension.getDescriptor(), extension.singularToReflectionType(value));
            onChanged();
            return this;
        }

        public final <Type> BuilderType clearExtension(Extension<MessageType, ?> extension) {
            verifyExtensionContainingType(extension);
            ensureExtensionsIsMutable();
            this.extensions.clearField(extension.getDescriptor());
            onChanged();
            return this;
        }

        protected boolean extensionsAreInitialized() {
            return this.extensions.isInitialized();
        }

        private FieldSet<FieldDescriptor> buildExtensions() {
            this.extensions.makeImmutable();
            return this.extensions;
        }

        public boolean isInitialized() {
            return super.isInitialized() && extensionsAreInitialized();
        }

        protected boolean parseUnknownField(CodedInputStream input, com.google.protobuf.UnknownFieldSet.Builder unknownFields, ExtensionRegistryLite extensionRegistry, int tag) throws IOException {
            return MessageReflection.mergeFieldFrom(input, unknownFields, extensionRegistry, getDescriptorForType(), new BuilderAdapter(this), tag);
        }

        public Map<FieldDescriptor, Object> getAllFields() {
            Map<FieldDescriptor, Object> result = getAllFieldsMutable();
            result.putAll(this.extensions.getAllFields());
            return Collections.unmodifiableMap(result);
        }

        public Object getField(FieldDescriptor field) {
            if (!field.isExtension()) {
                return super.getField(field);
            }
            verifyContainingType(field);
            Object value = this.extensions.getField(field);
            if (value != null) {
                return value;
            }
            if (field.getJavaType() == JavaType.MESSAGE) {
                return DynamicMessage.getDefaultInstance(field.getMessageType());
            }
            return field.getDefaultValue();
        }

        public int getRepeatedFieldCount(FieldDescriptor field) {
            if (!field.isExtension()) {
                return super.getRepeatedFieldCount(field);
            }
            verifyContainingType(field);
            return this.extensions.getRepeatedFieldCount(field);
        }

        public Object getRepeatedField(FieldDescriptor field, int index) {
            if (!field.isExtension()) {
                return super.getRepeatedField(field, index);
            }
            verifyContainingType(field);
            return this.extensions.getRepeatedField(field, index);
        }

        public boolean hasField(FieldDescriptor field) {
            if (!field.isExtension()) {
                return super.hasField(field);
            }
            verifyContainingType(field);
            return this.extensions.hasField(field);
        }

        public BuilderType setField(FieldDescriptor field, Object value) {
            if (!field.isExtension()) {
                return (ExtendableBuilder) super.setField(field, value);
            }
            verifyContainingType(field);
            ensureExtensionsIsMutable();
            this.extensions.setField(field, value);
            onChanged();
            return this;
        }

        public BuilderType clearField(FieldDescriptor field) {
            if (!field.isExtension()) {
                return (ExtendableBuilder) super.clearField(field);
            }
            verifyContainingType(field);
            ensureExtensionsIsMutable();
            this.extensions.clearField(field);
            onChanged();
            return this;
        }

        public BuilderType setRepeatedField(FieldDescriptor field, int index, Object value) {
            if (!field.isExtension()) {
                return (ExtendableBuilder) super.setRepeatedField(field, index, value);
            }
            verifyContainingType(field);
            ensureExtensionsIsMutable();
            this.extensions.setRepeatedField(field, index, value);
            onChanged();
            return this;
        }

        public BuilderType addRepeatedField(FieldDescriptor field, Object value) {
            if (!field.isExtension()) {
                return (ExtendableBuilder) super.addRepeatedField(field, value);
            }
            verifyContainingType(field);
            ensureExtensionsIsMutable();
            this.extensions.addRepeatedField(field, value);
            onChanged();
            return this;
        }

        protected final void mergeExtensionFields(ExtendableMessage other) {
            ensureExtensionsIsMutable();
            this.extensions.mergeFrom(other.extensions);
            onChanged();
        }

        private void verifyContainingType(FieldDescriptor field) {
            if (field.getContainingType() != getDescriptorForType()) {
                throw new IllegalArgumentException("FieldDescriptor does not match message type.");
            }
        }
    }

    public static abstract class ExtendableMessage<MessageType extends ExtendableMessage> extends GeneratedMessage implements ExtendableMessageOrBuilder<MessageType> {
        private final FieldSet<FieldDescriptor> extensions;

        protected class ExtensionWriter {
            private final Iterator<Entry<FieldDescriptor, Object>> iter;
            private final boolean messageSetWireFormat;
            private Entry<FieldDescriptor, Object> next;

            private ExtensionWriter(boolean messageSetWireFormat) {
                this.iter = ExtendableMessage.this.extensions.iterator();
                if (this.iter.hasNext()) {
                    this.next = (Entry) this.iter.next();
                }
                this.messageSetWireFormat = messageSetWireFormat;
            }

            public void writeUntil(int end, CodedOutputStream output) throws IOException {
                while (this.next != null && ((FieldDescriptor) this.next.getKey()).getNumber() < end) {
                    FieldDescriptor descriptor = (FieldDescriptor) this.next.getKey();
                    if (!this.messageSetWireFormat || descriptor.getLiteJavaType() != WireFormat.JavaType.MESSAGE || descriptor.isRepeated()) {
                        FieldSet.writeField(descriptor, this.next.getValue(), output);
                    } else if (this.next instanceof LazyEntry) {
                        output.writeRawMessageSetExtension(descriptor.getNumber(), ((LazyEntry) this.next).getField().toByteString());
                    } else {
                        output.writeMessageSetExtension(descriptor.getNumber(), (Message) this.next.getValue());
                    }
                    if (this.iter.hasNext()) {
                        this.next = (Entry) this.iter.next();
                    } else {
                        this.next = null;
                    }
                }
            }
        }

        protected ExtendableMessage() {
            this.extensions = FieldSet.newFieldSet();
        }

        protected ExtendableMessage(ExtendableBuilder<MessageType, ?> builder) {
            super(builder);
            this.extensions = builder.buildExtensions();
        }

        private void verifyExtensionContainingType(Extension<MessageType, ?> extension) {
            if (extension.getDescriptor().getContainingType() != getDescriptorForType()) {
                throw new IllegalArgumentException("Extension is for type \"" + extension.getDescriptor().getContainingType().getFullName() + "\" which does not match message type \"" + getDescriptorForType().getFullName() + "\".");
            }
        }

        public final <Type> boolean hasExtension(Extension<MessageType, Type> extension) {
            verifyExtensionContainingType(extension);
            return this.extensions.hasField(extension.getDescriptor());
        }

        public final <Type> int getExtensionCount(Extension<MessageType, List<Type>> extension) {
            verifyExtensionContainingType(extension);
            return this.extensions.getRepeatedFieldCount(extension.getDescriptor());
        }

        public final <Type> Type getExtension(Extension<MessageType, Type> extension) {
            verifyExtensionContainingType(extension);
            FieldDescriptor descriptor = extension.getDescriptor();
            Object value = this.extensions.getField(descriptor);
            if (value != null) {
                return extension.fromReflectionType(value);
            }
            if (descriptor.isRepeated()) {
                return Collections.emptyList();
            }
            if (descriptor.getJavaType() == JavaType.MESSAGE) {
                return extension.getMessageDefaultInstance();
            }
            return extension.fromReflectionType(descriptor.getDefaultValue());
        }

        public final <Type> Type getExtension(Extension<MessageType, List<Type>> extension, int index) {
            verifyExtensionContainingType(extension);
            return extension.singularFromReflectionType(this.extensions.getRepeatedField(extension.getDescriptor(), index));
        }

        protected boolean extensionsAreInitialized() {
            return this.extensions.isInitialized();
        }

        public boolean isInitialized() {
            return super.isInitialized() && extensionsAreInitialized();
        }

        protected boolean parseUnknownField(CodedInputStream input, com.google.protobuf.UnknownFieldSet.Builder unknownFields, ExtensionRegistryLite extensionRegistry, int tag) throws IOException {
            return MessageReflection.mergeFieldFrom(input, unknownFields, extensionRegistry, getDescriptorForType(), new ExtensionAdapter(this.extensions), tag);
        }

        protected void makeExtensionsImmutable() {
            this.extensions.makeImmutable();
        }

        protected ExtensionWriter newExtensionWriter() {
            return new ExtensionWriter(false);
        }

        protected ExtensionWriter newMessageSetExtensionWriter() {
            return new ExtensionWriter(true);
        }

        protected int extensionsSerializedSize() {
            return this.extensions.getSerializedSize();
        }

        protected int extensionsSerializedSizeAsMessageSet() {
            return this.extensions.getMessageSetSerializedSize();
        }

        protected Map<FieldDescriptor, Object> getExtensionFields() {
            return this.extensions.getAllFields();
        }

        public Map<FieldDescriptor, Object> getAllFields() {
            Map<FieldDescriptor, Object> result = getAllFieldsMutable();
            result.putAll(getExtensionFields());
            return Collections.unmodifiableMap(result);
        }

        public boolean hasField(FieldDescriptor field) {
            if (!field.isExtension()) {
                return super.hasField(field);
            }
            verifyContainingType(field);
            return this.extensions.hasField(field);
        }

        public Object getField(FieldDescriptor field) {
            if (!field.isExtension()) {
                return super.getField(field);
            }
            verifyContainingType(field);
            Object value = this.extensions.getField(field);
            if (value != null) {
                return value;
            }
            if (field.getJavaType() == JavaType.MESSAGE) {
                return DynamicMessage.getDefaultInstance(field.getMessageType());
            }
            return field.getDefaultValue();
        }

        public int getRepeatedFieldCount(FieldDescriptor field) {
            if (!field.isExtension()) {
                return super.getRepeatedFieldCount(field);
            }
            verifyContainingType(field);
            return this.extensions.getRepeatedFieldCount(field);
        }

        public Object getRepeatedField(FieldDescriptor field, int index) {
            if (!field.isExtension()) {
                return super.getRepeatedField(field, index);
            }
            verifyContainingType(field);
            return this.extensions.getRepeatedField(field, index);
        }

        private void verifyContainingType(FieldDescriptor field) {
            if (field.getContainingType() != getDescriptorForType()) {
                throw new IllegalArgumentException("FieldDescriptor does not match message type.");
            }
        }
    }

    interface ExtensionDescriptorRetriever {
        FieldDescriptor getDescriptor();
    }

    private static abstract class CachedDescriptorRetriever implements ExtensionDescriptorRetriever {
        private volatile FieldDescriptor descriptor;

        protected abstract FieldDescriptor loadDescriptor();

        private CachedDescriptorRetriever() {
        }

        public FieldDescriptor getDescriptor() {
            if (this.descriptor == null) {
                synchronized (this) {
                    if (this.descriptor == null) {
                        this.descriptor = loadDescriptor();
                    }
                }
            }
            return this.descriptor;
        }
    }

    protected interface BuilderParent {
        void markDirty();
    }

    public static final class FieldAccessorTable {
        private String[] camelCaseNames;
        private final Descriptor descriptor;
        private final FieldAccessor[] fields;
        private volatile boolean initialized;
        private final OneofAccessor[] oneofs;

        private interface FieldAccessor {
            void addRepeated(Builder builder, Object obj);

            void clear(Builder builder);

            Object get(Builder builder);

            Object get(GeneratedMessage generatedMessage);

            com.google.protobuf.Message.Builder getBuilder(Builder builder);

            Object getRepeated(Builder builder, int i);

            Object getRepeated(GeneratedMessage generatedMessage, int i);

            int getRepeatedCount(Builder builder);

            int getRepeatedCount(GeneratedMessage generatedMessage);

            boolean has(Builder builder);

            boolean has(GeneratedMessage generatedMessage);

            com.google.protobuf.Message.Builder newBuilder();

            void set(Builder builder, Object obj);

            void setRepeated(Builder builder, int i, Object obj);
        }

        private static class OneofAccessor {
            private final Method caseMethod;
            private final Method caseMethodBuilder;
            private final Method clearMethod;
            private final Descriptor descriptor;

            OneofAccessor(Descriptor descriptor, String camelCaseName, Class<? extends GeneratedMessage> messageClass, Class<? extends Builder> builderClass) {
                this.descriptor = descriptor;
                this.caseMethod = GeneratedMessage.getMethodOrDie(messageClass, "get" + camelCaseName + "Case", new Class[0]);
                this.caseMethodBuilder = GeneratedMessage.getMethodOrDie(builderClass, "get" + camelCaseName + "Case", new Class[0]);
                this.clearMethod = GeneratedMessage.getMethodOrDie(builderClass, "clear" + camelCaseName, new Class[0]);
            }

            public boolean has(GeneratedMessage message) {
                if (((EnumLite) GeneratedMessage.invokeOrDie(this.caseMethod, message, new Object[0])).getNumber() == 0) {
                    return false;
                }
                return true;
            }

            public boolean has(Builder builder) {
                if (((EnumLite) GeneratedMessage.invokeOrDie(this.caseMethodBuilder, builder, new Object[0])).getNumber() == 0) {
                    return false;
                }
                return true;
            }

            public FieldDescriptor get(GeneratedMessage message) {
                int fieldNumber = ((EnumLite) GeneratedMessage.invokeOrDie(this.caseMethod, message, new Object[0])).getNumber();
                if (fieldNumber > 0) {
                    return this.descriptor.findFieldByNumber(fieldNumber);
                }
                return null;
            }

            public FieldDescriptor get(Builder builder) {
                int fieldNumber = ((EnumLite) GeneratedMessage.invokeOrDie(this.caseMethodBuilder, builder, new Object[0])).getNumber();
                if (fieldNumber > 0) {
                    return this.descriptor.findFieldByNumber(fieldNumber);
                }
                return null;
            }

            public void clear(Builder builder) {
                GeneratedMessage.invokeOrDie(this.clearMethod, builder, new Object[0]);
            }
        }

        private static class RepeatedFieldAccessor implements FieldAccessor {
            protected final Method addRepeatedMethod;
            protected final Method clearMethod;
            protected final Method getCountMethod;
            protected final Method getCountMethodBuilder;
            protected final Method getMethod;
            protected final Method getMethodBuilder;
            protected final Method getRepeatedMethod;
            protected final Method getRepeatedMethodBuilder;
            protected final Method setRepeatedMethod;
            protected final Class type = this.getRepeatedMethod.getReturnType();

            RepeatedFieldAccessor(FieldDescriptor descriptor, String camelCaseName, Class<? extends GeneratedMessage> messageClass, Class<? extends Builder> builderClass) {
                this.getMethod = GeneratedMessage.getMethodOrDie(messageClass, "get" + camelCaseName + "List", new Class[0]);
                this.getMethodBuilder = GeneratedMessage.getMethodOrDie(builderClass, "get" + camelCaseName + "List", new Class[0]);
                this.getRepeatedMethod = GeneratedMessage.getMethodOrDie(messageClass, "get" + camelCaseName, Integer.TYPE);
                this.getRepeatedMethodBuilder = GeneratedMessage.getMethodOrDie(builderClass, "get" + camelCaseName, Integer.TYPE);
                this.setRepeatedMethod = GeneratedMessage.getMethodOrDie(builderClass, "set" + camelCaseName, Integer.TYPE, this.type);
                this.addRepeatedMethod = GeneratedMessage.getMethodOrDie(builderClass, i.aDy + camelCaseName, this.type);
                this.getCountMethod = GeneratedMessage.getMethodOrDie(messageClass, "get" + camelCaseName + "Count", new Class[0]);
                this.getCountMethodBuilder = GeneratedMessage.getMethodOrDie(builderClass, "get" + camelCaseName + "Count", new Class[0]);
                this.clearMethod = GeneratedMessage.getMethodOrDie(builderClass, "clear" + camelCaseName, new Class[0]);
            }

            public Object get(GeneratedMessage message) {
                return GeneratedMessage.invokeOrDie(this.getMethod, message, new Object[0]);
            }

            public Object get(Builder builder) {
                return GeneratedMessage.invokeOrDie(this.getMethodBuilder, builder, new Object[0]);
            }

            public void set(Builder builder, Object value) {
                clear(builder);
                for (Object element : (List) value) {
                    addRepeated(builder, element);
                }
            }

            public Object getRepeated(GeneratedMessage message, int index) {
                return GeneratedMessage.invokeOrDie(this.getRepeatedMethod, message, Integer.valueOf(index));
            }

            public Object getRepeated(Builder builder, int index) {
                return GeneratedMessage.invokeOrDie(this.getRepeatedMethodBuilder, builder, Integer.valueOf(index));
            }

            public void setRepeated(Builder builder, int index, Object value) {
                GeneratedMessage.invokeOrDie(this.setRepeatedMethod, builder, Integer.valueOf(index), value);
            }

            public void addRepeated(Builder builder, Object value) {
                GeneratedMessage.invokeOrDie(this.addRepeatedMethod, builder, value);
            }

            public boolean has(GeneratedMessage message) {
                throw new UnsupportedOperationException("hasField() called on a_isRightVersion repeated field.");
            }

            public boolean has(Builder builder) {
                throw new UnsupportedOperationException("hasField() called on a_isRightVersion repeated field.");
            }

            public int getRepeatedCount(GeneratedMessage message) {
                return ((Integer) GeneratedMessage.invokeOrDie(this.getCountMethod, message, new Object[0])).intValue();
            }

            public int getRepeatedCount(Builder builder) {
                return ((Integer) GeneratedMessage.invokeOrDie(this.getCountMethodBuilder, builder, new Object[0])).intValue();
            }

            public void clear(Builder builder) {
                GeneratedMessage.invokeOrDie(this.clearMethod, builder, new Object[0]);
            }

            public com.google.protobuf.Message.Builder newBuilder() {
                throw new UnsupportedOperationException("newBuilderForField() called on a_isRightVersion non-Message type.");
            }

            public com.google.protobuf.Message.Builder getBuilder(Builder builder) {
                throw new UnsupportedOperationException("getFieldBuilder() called on a_isRightVersion non-Message type.");
            }
        }

        private static final class RepeatedEnumFieldAccessor extends RepeatedFieldAccessor {
            private final Method getValueDescriptorMethod = GeneratedMessage.getMethodOrDie(this.type, "getValueDescriptor", new Class[0]);
            private final Method valueOfMethod = GeneratedMessage.getMethodOrDie(this.type, "valueOf", EnumValueDescriptor.class);

            RepeatedEnumFieldAccessor(FieldDescriptor descriptor, String camelCaseName, Class<? extends GeneratedMessage> messageClass, Class<? extends Builder> builderClass) {
                super(descriptor, camelCaseName, messageClass, builderClass);
            }

            public Object get(GeneratedMessage message) {
                List newList = new ArrayList();
                for (Object element : (List) super.get(message)) {
                    newList.add(GeneratedMessage.invokeOrDie(this.getValueDescriptorMethod, element, new Object[0]));
                }
                return Collections.unmodifiableList(newList);
            }

            public Object get(Builder builder) {
                List newList = new ArrayList();
                for (Object element : (List) super.get(builder)) {
                    newList.add(GeneratedMessage.invokeOrDie(this.getValueDescriptorMethod, element, new Object[0]));
                }
                return Collections.unmodifiableList(newList);
            }

            public Object getRepeated(GeneratedMessage message, int index) {
                return GeneratedMessage.invokeOrDie(this.getValueDescriptorMethod, super.getRepeated(message, index), new Object[0]);
            }

            public Object getRepeated(Builder builder, int index) {
                return GeneratedMessage.invokeOrDie(this.getValueDescriptorMethod, super.getRepeated(builder, index), new Object[0]);
            }

            public void setRepeated(Builder builder, int index, Object value) {
                super.setRepeated(builder, index, GeneratedMessage.invokeOrDie(this.valueOfMethod, null, value));
            }

            public void addRepeated(Builder builder, Object value) {
                super.addRepeated(builder, GeneratedMessage.invokeOrDie(this.valueOfMethod, null, value));
            }
        }

        private static final class RepeatedMessageFieldAccessor extends RepeatedFieldAccessor {
            private final Method newBuilderMethod = GeneratedMessage.getMethodOrDie(this.type, "newBuilder", new Class[0]);

            RepeatedMessageFieldAccessor(FieldDescriptor descriptor, String camelCaseName, Class<? extends GeneratedMessage> messageClass, Class<? extends Builder> builderClass) {
                super(descriptor, camelCaseName, messageClass, builderClass);
            }

            private Object coerceType(Object value) {
                return this.type.isInstance(value) ? value : ((com.google.protobuf.Message.Builder) GeneratedMessage.invokeOrDie(this.newBuilderMethod, null, new Object[0])).mergeFrom((Message) value).build();
            }

            public void setRepeated(Builder builder, int index, Object value) {
                super.setRepeated(builder, index, coerceType(value));
            }

            public void addRepeated(Builder builder, Object value) {
                super.addRepeated(builder, coerceType(value));
            }

            public com.google.protobuf.Message.Builder newBuilder() {
                return (com.google.protobuf.Message.Builder) GeneratedMessage.invokeOrDie(this.newBuilderMethod, null, new Object[0]);
            }
        }

        private static class SingularFieldAccessor implements FieldAccessor {
            protected final Method caseMethod;
            protected final Method caseMethodBuilder;
            protected final Method clearMethod;
            protected final FieldDescriptor field;
            protected final Method getMethod;
            protected final Method getMethodBuilder;
            protected final boolean hasHasMethod;
            protected final Method hasMethod;
            protected final Method hasMethodBuilder;
            protected final boolean isOneofField;
            protected final Method setMethod;
            protected final Class<?> type;

            SingularFieldAccessor(FieldDescriptor descriptor, String camelCaseName, Class<? extends GeneratedMessage> messageClass, Class<? extends Builder> builderClass, String containingOneofCamelCaseName) {
                boolean z;
                Method access$1000;
                Method method = null;
                this.field = descriptor;
                this.isOneofField = descriptor.getContainingOneof() != null;
                if (FieldAccessorTable.supportFieldPresence(descriptor.getFile()) || (!this.isOneofField && descriptor.getJavaType() == JavaType.MESSAGE)) {
                    z = true;
                } else {
                    z = false;
                }
                this.hasHasMethod = z;
                this.getMethod = GeneratedMessage.getMethodOrDie(messageClass, "get" + camelCaseName, new Class[0]);
                this.getMethodBuilder = GeneratedMessage.getMethodOrDie(builderClass, "get" + camelCaseName, new Class[0]);
                this.type = this.getMethod.getReturnType();
                this.setMethod = GeneratedMessage.getMethodOrDie(builderClass, "set" + camelCaseName, this.type);
                if (this.hasHasMethod) {
                    access$1000 = GeneratedMessage.getMethodOrDie(messageClass, "has" + camelCaseName, new Class[0]);
                } else {
                    access$1000 = null;
                }
                this.hasMethod = access$1000;
                if (this.hasHasMethod) {
                    access$1000 = GeneratedMessage.getMethodOrDie(builderClass, "has" + camelCaseName, new Class[0]);
                } else {
                    access$1000 = null;
                }
                this.hasMethodBuilder = access$1000;
                this.clearMethod = GeneratedMessage.getMethodOrDie(builderClass, "clear" + camelCaseName, new Class[0]);
                if (this.isOneofField) {
                    access$1000 = GeneratedMessage.getMethodOrDie(messageClass, "get" + containingOneofCamelCaseName + "Case", new Class[0]);
                } else {
                    access$1000 = null;
                }
                this.caseMethod = access$1000;
                if (this.isOneofField) {
                    method = GeneratedMessage.getMethodOrDie(builderClass, "get" + containingOneofCamelCaseName + "Case", new Class[0]);
                }
                this.caseMethodBuilder = method;
            }

            private int getOneofFieldNumber(GeneratedMessage message) {
                return ((EnumLite) GeneratedMessage.invokeOrDie(this.caseMethod, message, new Object[0])).getNumber();
            }

            private int getOneofFieldNumber(Builder builder) {
                return ((EnumLite) GeneratedMessage.invokeOrDie(this.caseMethodBuilder, builder, new Object[0])).getNumber();
            }

            public Object get(GeneratedMessage message) {
                return GeneratedMessage.invokeOrDie(this.getMethod, message, new Object[0]);
            }

            public Object get(Builder builder) {
                return GeneratedMessage.invokeOrDie(this.getMethodBuilder, builder, new Object[0]);
            }

            public void set(Builder builder, Object value) {
                GeneratedMessage.invokeOrDie(this.setMethod, builder, value);
            }

            public Object getRepeated(GeneratedMessage message, int index) {
                throw new UnsupportedOperationException("getRepeatedField() called on a_isRightVersion singular field.");
            }

            public Object getRepeated(Builder builder, int index) {
                throw new UnsupportedOperationException("getRepeatedField() called on a_isRightVersion singular field.");
            }

            public void setRepeated(Builder builder, int index, Object value) {
                throw new UnsupportedOperationException("setRepeatedField() called on a_isRightVersion singular field.");
            }

            public void addRepeated(Builder builder, Object value) {
                throw new UnsupportedOperationException("addRepeatedField() called on a_isRightVersion singular field.");
            }

            public boolean has(GeneratedMessage message) {
                if (this.hasHasMethod) {
                    return ((Boolean) GeneratedMessage.invokeOrDie(this.hasMethod, message, new Object[0])).booleanValue();
                }
                if (this.isOneofField) {
                    if (getOneofFieldNumber(message) == this.field.getNumber()) {
                        return true;
                    }
                    return false;
                } else if (get(message).equals(this.field.getDefaultValue())) {
                    return false;
                } else {
                    return true;
                }
            }

            public boolean has(Builder builder) {
                if (this.hasHasMethod) {
                    return ((Boolean) GeneratedMessage.invokeOrDie(this.hasMethodBuilder, builder, new Object[0])).booleanValue();
                }
                if (this.isOneofField) {
                    if (getOneofFieldNumber(builder) == this.field.getNumber()) {
                        return true;
                    }
                    return false;
                } else if (get(builder).equals(this.field.getDefaultValue())) {
                    return false;
                } else {
                    return true;
                }
            }

            public int getRepeatedCount(GeneratedMessage message) {
                throw new UnsupportedOperationException("getRepeatedFieldSize() called on a_isRightVersion singular field.");
            }

            public int getRepeatedCount(Builder builder) {
                throw new UnsupportedOperationException("getRepeatedFieldSize() called on a_isRightVersion singular field.");
            }

            public void clear(Builder builder) {
                GeneratedMessage.invokeOrDie(this.clearMethod, builder, new Object[0]);
            }

            public com.google.protobuf.Message.Builder newBuilder() {
                throw new UnsupportedOperationException("newBuilderForField() called on a_isRightVersion non-Message type.");
            }

            public com.google.protobuf.Message.Builder getBuilder(Builder builder) {
                throw new UnsupportedOperationException("getFieldBuilder() called on a_isRightVersion non-Message type.");
            }
        }

        private static final class SingularEnumFieldAccessor extends SingularFieldAccessor {
            private Method getValueDescriptorMethod = GeneratedMessage.getMethodOrDie(this.type, "getValueDescriptor", new Class[0]);
            private Method valueOfMethod = GeneratedMessage.getMethodOrDie(this.type, "valueOf", EnumValueDescriptor.class);

            SingularEnumFieldAccessor(FieldDescriptor descriptor, String camelCaseName, Class<? extends GeneratedMessage> messageClass, Class<? extends Builder> builderClass, String containingOneofCamelCaseName) {
                super(descriptor, camelCaseName, messageClass, builderClass, containingOneofCamelCaseName);
            }

            public Object get(GeneratedMessage message) {
                return GeneratedMessage.invokeOrDie(this.getValueDescriptorMethod, super.get(message), new Object[0]);
            }

            public Object get(Builder builder) {
                return GeneratedMessage.invokeOrDie(this.getValueDescriptorMethod, super.get(builder), new Object[0]);
            }

            public void set(Builder builder, Object value) {
                super.set(builder, GeneratedMessage.invokeOrDie(this.valueOfMethod, null, value));
            }
        }

        private static final class SingularMessageFieldAccessor extends SingularFieldAccessor {
            private final Method getBuilderMethodBuilder;
            private final Method newBuilderMethod = GeneratedMessage.getMethodOrDie(this.type, "newBuilder", new Class[0]);

            SingularMessageFieldAccessor(FieldDescriptor descriptor, String camelCaseName, Class<? extends GeneratedMessage> messageClass, Class<? extends Builder> builderClass, String containingOneofCamelCaseName) {
                super(descriptor, camelCaseName, messageClass, builderClass, containingOneofCamelCaseName);
                this.getBuilderMethodBuilder = GeneratedMessage.getMethodOrDie(builderClass, "get" + camelCaseName + "Builder", new Class[0]);
            }

            private Object coerceType(Object value) {
                return this.type.isInstance(value) ? value : ((com.google.protobuf.Message.Builder) GeneratedMessage.invokeOrDie(this.newBuilderMethod, null, new Object[0])).mergeFrom((Message) value).buildPartial();
            }

            public void set(Builder builder, Object value) {
                super.set(builder, coerceType(value));
            }

            public com.google.protobuf.Message.Builder newBuilder() {
                return (com.google.protobuf.Message.Builder) GeneratedMessage.invokeOrDie(this.newBuilderMethod, null, new Object[0]);
            }

            public com.google.protobuf.Message.Builder getBuilder(Builder builder) {
                return (com.google.protobuf.Message.Builder) GeneratedMessage.invokeOrDie(this.getBuilderMethodBuilder, builder, new Object[0]);
            }
        }

        public FieldAccessorTable(Descriptor descriptor, String[] camelCaseNames, Class<? extends GeneratedMessage> messageClass, Class<? extends Builder> builderClass) {
            this(descriptor, camelCaseNames);
            ensureFieldAccessorsInitialized(messageClass, builderClass);
        }

        public FieldAccessorTable(Descriptor descriptor, String[] camelCaseNames) {
            this.descriptor = descriptor;
            this.camelCaseNames = camelCaseNames;
            this.fields = new FieldAccessor[descriptor.getFields().size()];
            this.oneofs = new OneofAccessor[descriptor.getOneofs().size()];
            this.initialized = false;
        }

        public FieldAccessorTable ensureFieldAccessorsInitialized(Class<? extends GeneratedMessage> messageClass, Class<? extends Builder> builderClass) {
            if (!this.initialized) {
                synchronized (this) {
                    if (this.initialized) {
                    } else {
                        int i;
                        int fieldsSize = this.fields.length;
                        for (i = 0; i < fieldsSize; i++) {
                            FieldDescriptor field = (FieldDescriptor) this.descriptor.getFields().get(i);
                            String containingOneofCamelCaseName = null;
                            if (field.getContainingOneof() != null) {
                                containingOneofCamelCaseName = this.camelCaseNames[field.getContainingOneof().getIndex() + fieldsSize];
                            }
                            if (field.isRepeated()) {
                                if (field.getJavaType() == JavaType.MESSAGE) {
                                    this.fields[i] = new RepeatedMessageFieldAccessor(field, this.camelCaseNames[i], messageClass, builderClass);
                                } else if (field.getJavaType() == JavaType.ENUM) {
                                    this.fields[i] = new RepeatedEnumFieldAccessor(field, this.camelCaseNames[i], messageClass, builderClass);
                                } else {
                                    this.fields[i] = new RepeatedFieldAccessor(field, this.camelCaseNames[i], messageClass, builderClass);
                                }
                            } else if (field.getJavaType() == JavaType.MESSAGE) {
                                this.fields[i] = new SingularMessageFieldAccessor(field, this.camelCaseNames[i], messageClass, builderClass, containingOneofCamelCaseName);
                            } else if (field.getJavaType() == JavaType.ENUM) {
                                this.fields[i] = new SingularEnumFieldAccessor(field, this.camelCaseNames[i], messageClass, builderClass, containingOneofCamelCaseName);
                            } else {
                                this.fields[i] = new SingularFieldAccessor(field, this.camelCaseNames[i], messageClass, builderClass, containingOneofCamelCaseName);
                            }
                        }
                        int oneofsSize = this.oneofs.length;
                        for (i = 0; i < oneofsSize; i++) {
                            this.oneofs[i] = new OneofAccessor(this.descriptor, this.camelCaseNames[i + fieldsSize], messageClass, builderClass);
                        }
                        this.initialized = true;
                        this.camelCaseNames = null;
                    }
                }
            }
            return this;
        }

        private FieldAccessor getField(FieldDescriptor field) {
            if (field.getContainingType() != this.descriptor) {
                throw new IllegalArgumentException("FieldDescriptor does not match message type.");
            } else if (!field.isExtension()) {
                return this.fields[field.getIndex()];
            } else {
                throw new IllegalArgumentException("This type does not have extensions.");
            }
        }

        private OneofAccessor getOneof(OneofDescriptor oneof) {
            if (oneof.getContainingType() == this.descriptor) {
                return this.oneofs[oneof.getIndex()];
            }
            throw new IllegalArgumentException("OneofDescriptor does not match message type.");
        }

        private static boolean supportFieldPresence(FileDescriptor file) {
            return true;
        }
    }

    public static class GeneratedExtension<ContainingType extends Message, Type> extends Extension<ContainingType, Type> {
        private ExtensionDescriptorRetriever descriptorRetriever;
        private final Method enumGetValueDescriptor;
        private final Method enumValueOf;
        private final ExtensionType extensionType;
        private final Message messageDefaultInstance;
        private final Class singularType;

        GeneratedExtension(ExtensionDescriptorRetriever descriptorRetriever, Class singularType, Message messageDefaultInstance, ExtensionType extensionType) {
            if (!Message.class.isAssignableFrom(singularType) || singularType.isInstance(messageDefaultInstance)) {
                this.descriptorRetriever = descriptorRetriever;
                this.singularType = singularType;
                this.messageDefaultInstance = messageDefaultInstance;
                if (ProtocolMessageEnum.class.isAssignableFrom(singularType)) {
                    this.enumValueOf = GeneratedMessage.getMethodOrDie(singularType, "valueOf", EnumValueDescriptor.class);
                    this.enumGetValueDescriptor = GeneratedMessage.getMethodOrDie(singularType, "getValueDescriptor", new Class[0]);
                } else {
                    this.enumValueOf = null;
                    this.enumGetValueDescriptor = null;
                }
                this.extensionType = extensionType;
                return;
            }
            throw new IllegalArgumentException("Bad messageDefaultInstance for " + singularType.getName());
        }

        public void internalInit(final FieldDescriptor descriptor) {
            if (this.descriptorRetriever != null) {
                throw new IllegalStateException("Already initialized.");
            }
            this.descriptorRetriever = new ExtensionDescriptorRetriever() {
                public FieldDescriptor getDescriptor() {
                    return descriptor;
                }
            };
        }

        public FieldDescriptor getDescriptor() {
            if (this.descriptorRetriever != null) {
                return this.descriptorRetriever.getDescriptor();
            }
            throw new IllegalStateException("getDescriptor() called before internalInit()");
        }

        public Message getMessageDefaultInstance() {
            return this.messageDefaultInstance;
        }

        protected ExtensionType getExtensionType() {
            return this.extensionType;
        }

        protected Object fromReflectionType(Object value) {
            FieldDescriptor descriptor = getDescriptor();
            if (!descriptor.isRepeated()) {
                return singularFromReflectionType(value);
            }
            if (descriptor.getJavaType() != JavaType.MESSAGE && descriptor.getJavaType() != JavaType.ENUM) {
                return value;
            }
            List result = new ArrayList();
            for (Object element : (List) value) {
                result.add(singularFromReflectionType(element));
            }
            return result;
        }

        protected Object singularFromReflectionType(Object value) {
            switch (getDescriptor().getJavaType()) {
                case MESSAGE:
                    if (this.singularType.isInstance(value)) {
                        return value;
                    }
                    return this.messageDefaultInstance.newBuilderForType().mergeFrom((Message) value).build();
                case ENUM:
                    return GeneratedMessage.invokeOrDie(this.enumValueOf, null, (EnumValueDescriptor) value);
                default:
                    return value;
            }
        }

        protected Object toReflectionType(Object value) {
            FieldDescriptor descriptor = getDescriptor();
            if (!descriptor.isRepeated()) {
                return singularToReflectionType(value);
            }
            if (descriptor.getJavaType() != JavaType.ENUM) {
                return value;
            }
            Object arrayList = new ArrayList();
            for (Object element : (List) value) {
                arrayList.add(singularToReflectionType(element));
            }
            return arrayList;
        }

        protected Object singularToReflectionType(Object value) {
            switch (getDescriptor().getJavaType()) {
                case ENUM:
                    return GeneratedMessage.invokeOrDie(this.enumGetValueDescriptor, value, new Object[0]);
                default:
                    return value;
            }
        }

        public int getNumber() {
            return getDescriptor().getNumber();
        }

        public FieldType getLiteType() {
            return getDescriptor().getLiteType();
        }

        public boolean isRepeated() {
            return getDescriptor().isRepeated();
        }

        public Type getDefaultValue() {
            if (isRepeated()) {
                return Collections.emptyList();
            }
            if (getDescriptor().getJavaType() == JavaType.MESSAGE) {
                return this.messageDefaultInstance;
            }
            return singularFromReflectionType(getDescriptor().getDefaultValue());
        }
    }

    protected abstract FieldAccessorTable internalGetFieldAccessorTable();

    protected abstract com.google.protobuf.Message.Builder newBuilderForType(BuilderParent builderParent);

    protected GeneratedMessage() {
    }

    protected GeneratedMessage(Builder<?> builder) {
    }

    public Parser<? extends GeneratedMessage> getParserForType() {
        throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
    }

    static void enableAlwaysUseFieldBuildersForTesting() {
        alwaysUseFieldBuilders = true;
    }

    public Descriptor getDescriptorForType() {
        return internalGetFieldAccessorTable().descriptor;
    }

    private Map<FieldDescriptor, Object> getAllFieldsMutable() {
        TreeMap<FieldDescriptor, Object> result = new TreeMap();
        for (FieldDescriptor field : internalGetFieldAccessorTable().descriptor.getFields()) {
            if (field.isRepeated()) {
                List<?> value = (List) getField(field);
                if (!value.isEmpty()) {
                    result.put(field, value);
                }
            } else if (hasField(field)) {
                result.put(field, getField(field));
            }
        }
        return result;
    }

    public boolean isInitialized() {
        for (FieldDescriptor field : getDescriptorForType().getFields()) {
            if (field.isRequired() && !hasField(field)) {
                return false;
            }
            if (field.getJavaType() == JavaType.MESSAGE) {
                if (field.isRepeated()) {
                    for (Message element : (List) getField(field)) {
                        if (!element.isInitialized()) {
                            return false;
                        }
                    }
                    continue;
                } else if (hasField(field) && !((Message) getField(field)).isInitialized()) {
                    return false;
                }
            }
        }
        return true;
    }

    public Map<FieldDescriptor, Object> getAllFields() {
        return Collections.unmodifiableMap(getAllFieldsMutable());
    }

    public boolean hasOneof(OneofDescriptor oneof) {
        return internalGetFieldAccessorTable().getOneof(oneof).has(this);
    }

    public FieldDescriptor getOneofFieldDescriptor(OneofDescriptor oneof) {
        return internalGetFieldAccessorTable().getOneof(oneof).get(this);
    }

    public boolean hasField(FieldDescriptor field) {
        return internalGetFieldAccessorTable().getField(field).has(this);
    }

    public Object getField(FieldDescriptor field) {
        return internalGetFieldAccessorTable().getField(field).get(this);
    }

    public int getRepeatedFieldCount(FieldDescriptor field) {
        return internalGetFieldAccessorTable().getField(field).getRepeatedCount(this);
    }

    public Object getRepeatedField(FieldDescriptor field, int index) {
        return internalGetFieldAccessorTable().getField(field).getRepeated(this, index);
    }

    public UnknownFieldSet getUnknownFields() {
        throw new UnsupportedOperationException("This is supposed to be overridden by subclasses.");
    }

    protected boolean parseUnknownField(CodedInputStream input, com.google.protobuf.UnknownFieldSet.Builder unknownFields, ExtensionRegistryLite extensionRegistry, int tag) throws IOException {
        return unknownFields.mergeFieldFrom(tag, input);
    }

    protected void makeExtensionsImmutable() {
    }

    public static <ContainingType extends Message, Type> GeneratedExtension<ContainingType, Type> newMessageScopedGeneratedExtension(final Message scope, final int descriptorIndex, Class singularType, Message defaultInstance) {
        return new GeneratedExtension(new CachedDescriptorRetriever() {
            public FieldDescriptor loadDescriptor() {
                return (FieldDescriptor) scope.getDescriptorForType().getExtensions().get(descriptorIndex);
            }
        }, singularType, defaultInstance, ExtensionType.IMMUTABLE);
    }

    public static <ContainingType extends Message, Type> GeneratedExtension<ContainingType, Type> newFileScopedGeneratedExtension(Class singularType, Message defaultInstance) {
        return new GeneratedExtension(null, singularType, defaultInstance, ExtensionType.IMMUTABLE);
    }

    public static <ContainingType extends Message, Type> GeneratedExtension<ContainingType, Type> newMessageScopedGeneratedExtension(final Message scope, final String name, Class singularType, Message defaultInstance) {
        return new GeneratedExtension(new CachedDescriptorRetriever() {
            protected FieldDescriptor loadDescriptor() {
                return scope.getDescriptorForType().findFieldByName(name);
            }
        }, singularType, defaultInstance, ExtensionType.MUTABLE);
    }

    public static <ContainingType extends Message, Type> GeneratedExtension<ContainingType, Type> newFileScopedGeneratedExtension(final Class singularType, Message defaultInstance, final String descriptorOuterClass, final String extensionName) {
        return new GeneratedExtension(new CachedDescriptorRetriever() {
            protected FieldDescriptor loadDescriptor() {
                try {
                    return ((FileDescriptor) singularType.getClassLoader().loadClass(descriptorOuterClass).getField(NamingScheme.DESCRIPTOR).get(null)).findExtensionByName(extensionName);
                } catch (Exception e) {
                    throw new RuntimeException("Cannot load descriptors: " + descriptorOuterClass + " is not a_isRightVersion valid descriptor class name", e);
                }
            }
        }, singularType, defaultInstance, ExtensionType.MUTABLE);
    }

    private static Method getMethodOrDie(Class clazz, String name, Class... params) {
        try {
            return clazz.getMethod(name, params);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Generated message class \"" + clazz.getName() + "\" missing method \"" + name + "\".", e);
        }
    }

    private static Object invokeOrDie(Method method, Object object, Object... params) {
        try {
            return method.invoke(object, params);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            } else if (cause instanceof Error) {
                throw ((Error) cause);
            } else {
                throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
            }
        }
    }

    protected Object writeReplace() throws ObjectStreamException {
        return new SerializedForm(this);
    }
}
