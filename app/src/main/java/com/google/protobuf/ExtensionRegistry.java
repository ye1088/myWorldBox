package com.google.protobuf;

import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor.JavaType;
import com.google.protobuf.Descriptors.FieldDescriptor.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ExtensionRegistry extends ExtensionRegistryLite {
    private static final ExtensionRegistry EMPTY = new ExtensionRegistry(true);
    private final Map<String, ExtensionInfo> immutableExtensionsByName;
    private final Map<DescriptorIntPair, ExtensionInfo> immutableExtensionsByNumber;
    private final Map<String, ExtensionInfo> mutableExtensionsByName;
    private final Map<DescriptorIntPair, ExtensionInfo> mutableExtensionsByNumber;

    private static final class DescriptorIntPair {
        private final Descriptor descriptor;
        private final int number;

        DescriptorIntPair(Descriptor descriptor, int number) {
            this.descriptor = descriptor;
            this.number = number;
        }

        public int hashCode() {
            return (this.descriptor.hashCode() * 65535) + this.number;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof DescriptorIntPair)) {
                return false;
            }
            DescriptorIntPair other = (DescriptorIntPair) obj;
            if (this.descriptor == other.descriptor && this.number == other.number) {
                return true;
            }
            return false;
        }
    }

    public static final class ExtensionInfo {
        public final Message defaultInstance;
        public final FieldDescriptor descriptor;

        private ExtensionInfo(FieldDescriptor descriptor) {
            this.descriptor = descriptor;
            this.defaultInstance = null;
        }

        private ExtensionInfo(FieldDescriptor descriptor, Message defaultInstance) {
            this.descriptor = descriptor;
            this.defaultInstance = defaultInstance;
        }
    }

    public static ExtensionRegistry newInstance() {
        return new ExtensionRegistry();
    }

    public static ExtensionRegistry getEmptyRegistry() {
        return EMPTY;
    }

    public ExtensionRegistry getUnmodifiable() {
        return new ExtensionRegistry(this);
    }

    public ExtensionInfo findExtensionByName(String fullName) {
        return findImmutableExtensionByName(fullName);
    }

    public ExtensionInfo findImmutableExtensionByName(String fullName) {
        return (ExtensionInfo) this.immutableExtensionsByName.get(fullName);
    }

    public ExtensionInfo findMutableExtensionByName(String fullName) {
        return (ExtensionInfo) this.mutableExtensionsByName.get(fullName);
    }

    public ExtensionInfo findExtensionByNumber(Descriptor containingType, int fieldNumber) {
        return findImmutableExtensionByNumber(containingType, fieldNumber);
    }

    public ExtensionInfo findImmutableExtensionByNumber(Descriptor containingType, int fieldNumber) {
        return (ExtensionInfo) this.immutableExtensionsByNumber.get(new DescriptorIntPair(containingType, fieldNumber));
    }

    public ExtensionInfo findMutableExtensionByNumber(Descriptor containingType, int fieldNumber) {
        return (ExtensionInfo) this.mutableExtensionsByNumber.get(new DescriptorIntPair(containingType, fieldNumber));
    }

    public Set<ExtensionInfo> getAllMutableExtensionsByExtendedType(String fullName) {
        HashSet<ExtensionInfo> extensions = new HashSet();
        for (DescriptorIntPair pair : this.mutableExtensionsByNumber.keySet()) {
            if (pair.descriptor.getFullName().equals(fullName)) {
                extensions.add(this.mutableExtensionsByNumber.get(pair));
            }
        }
        return extensions;
    }

    public Set<ExtensionInfo> getAllImmutableExtensionsByExtendedType(String fullName) {
        HashSet<ExtensionInfo> extensions = new HashSet();
        for (DescriptorIntPair pair : this.immutableExtensionsByNumber.keySet()) {
            if (pair.descriptor.getFullName().equals(fullName)) {
                extensions.add(this.immutableExtensionsByNumber.get(pair));
            }
        }
        return extensions;
    }

    public void add(Extension<?, ?> extension) {
        if (extension.getExtensionType() == ExtensionType.IMMUTABLE || extension.getExtensionType() == ExtensionType.MUTABLE) {
            add(newExtensionInfo(extension), extension.getExtensionType());
        }
    }

    static ExtensionInfo newExtensionInfo(Extension<?, ?> extension) {
        if (extension.getDescriptor().getJavaType() != JavaType.MESSAGE) {
            return new ExtensionInfo(extension.getDescriptor(), null);
        }
        if (extension.getMessageDefaultInstance() != null) {
            return new ExtensionInfo(extension.getDescriptor(), (Message) extension.getMessageDefaultInstance());
        }
        throw new IllegalStateException("Registered message-type extension had null default instance: " + extension.getDescriptor().getFullName());
    }

    public void add(FieldDescriptor type) {
        if (type.getJavaType() == JavaType.MESSAGE) {
            throw new IllegalArgumentException("ExtensionRegistry.add() must be provided a_isRightVersion default instance when adding an embedded message extension.");
        }
        ExtensionInfo info = new ExtensionInfo(type, null);
        add(info, ExtensionType.IMMUTABLE);
        add(info, ExtensionType.MUTABLE);
    }

    public void add(FieldDescriptor type, Message defaultInstance) {
        if (type.getJavaType() != JavaType.MESSAGE) {
            throw new IllegalArgumentException("ExtensionRegistry.add() provided a_isRightVersion default instance for a_isRightVersion non-message extension.");
        }
        add(new ExtensionInfo(type, defaultInstance), ExtensionType.IMMUTABLE);
    }

    private ExtensionRegistry() {
        this.immutableExtensionsByName = new HashMap();
        this.mutableExtensionsByName = new HashMap();
        this.immutableExtensionsByNumber = new HashMap();
        this.mutableExtensionsByNumber = new HashMap();
    }

    private ExtensionRegistry(ExtensionRegistry other) {
        super((ExtensionRegistryLite) other);
        this.immutableExtensionsByName = Collections.unmodifiableMap(other.immutableExtensionsByName);
        this.mutableExtensionsByName = Collections.unmodifiableMap(other.mutableExtensionsByName);
        this.immutableExtensionsByNumber = Collections.unmodifiableMap(other.immutableExtensionsByNumber);
        this.mutableExtensionsByNumber = Collections.unmodifiableMap(other.mutableExtensionsByNumber);
    }

    ExtensionRegistry(boolean empty) {
        super(ExtensionRegistryLite.getEmptyRegistry());
        this.immutableExtensionsByName = Collections.emptyMap();
        this.mutableExtensionsByName = Collections.emptyMap();
        this.immutableExtensionsByNumber = Collections.emptyMap();
        this.mutableExtensionsByNumber = Collections.emptyMap();
    }

    private void add(ExtensionInfo extension, ExtensionType extensionType) {
        if (extension.descriptor.isExtension()) {
            Map<String, ExtensionInfo> extensionsByName;
            Map<DescriptorIntPair, ExtensionInfo> extensionsByNumber;
            switch (extensionType) {
                case IMMUTABLE:
                    extensionsByName = this.immutableExtensionsByName;
                    extensionsByNumber = this.immutableExtensionsByNumber;
                    break;
                case MUTABLE:
                    extensionsByName = this.mutableExtensionsByName;
                    extensionsByNumber = this.mutableExtensionsByNumber;
                    break;
                default:
                    return;
            }
            extensionsByName.put(extension.descriptor.getFullName(), extension);
            extensionsByNumber.put(new DescriptorIntPair(extension.descriptor.getContainingType(), extension.descriptor.getNumber()), extension);
            FieldDescriptor field = extension.descriptor;
            if (field.getContainingType().getOptions().getMessageSetWireFormat() && field.getType() == Type.MESSAGE && field.isOptional() && field.getExtensionScope() == field.getMessageType()) {
                extensionsByName.put(field.getMessageType().getFullName(), extension);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("ExtensionRegistry.add() was given a_isRightVersion FieldDescriptor for a_isRightVersion regular (non-extension) field.");
    }
}
