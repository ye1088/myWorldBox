package com.google.protobuf;

import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.EnumDescriptor;
import com.google.protobuf.Descriptors.EnumValueDescriptor;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner;
import com.google.protobuf.GeneratedMessage.ExtendableBuilder;
import com.google.protobuf.GeneratedMessage.ExtendableMessage;
import com.google.protobuf.GeneratedMessage.ExtendableMessageOrBuilder;
import com.google.protobuf.GeneratedMessage.FieldAccessorTable;
import com.google.protobuf.Internal.EnumLiteMap;
import io.netty.handler.codec.http.cookie.CookieHeaderNames;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.tools.ant.taskdefs.Manifest;
import org.apache.tools.ant.taskdefs.optional.vss.MSVSSConstants;

public final class DescriptorProtos {
    private static FileDescriptor descriptor;
    private static final Descriptor internal_static_google_protobuf_DescriptorProto_ExtensionRange_descriptor = ((Descriptor) internal_static_google_protobuf_DescriptorProto_descriptor.getNestedTypes().get(0));
    private static FieldAccessorTable internal_static_google_protobuf_DescriptorProto_ExtensionRange_fieldAccessorTable = new FieldAccessorTable(internal_static_google_protobuf_DescriptorProto_ExtensionRange_descriptor, new String[]{"Start", "End"});
    private static final Descriptor internal_static_google_protobuf_DescriptorProto_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(2));
    private static FieldAccessorTable internal_static_google_protobuf_DescriptorProto_fieldAccessorTable = new FieldAccessorTable(internal_static_google_protobuf_DescriptorProto_descriptor, new String[]{Manifest.ATTRIBUTE_NAME, "Field", "Extension", "NestedType", "EnumType", "ExtensionRange", "OneofDecl", "Options"});
    private static final Descriptor internal_static_google_protobuf_EnumDescriptorProto_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(5));
    private static FieldAccessorTable internal_static_google_protobuf_EnumDescriptorProto_fieldAccessorTable = new FieldAccessorTable(internal_static_google_protobuf_EnumDescriptorProto_descriptor, new String[]{Manifest.ATTRIBUTE_NAME, "Value", "Options"});
    private static final Descriptor internal_static_google_protobuf_EnumOptions_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(12));
    private static FieldAccessorTable internal_static_google_protobuf_EnumOptions_fieldAccessorTable = new FieldAccessorTable(internal_static_google_protobuf_EnumOptions_descriptor, new String[]{"AllowAlias", "Deprecated", "UninterpretedOption"});
    private static final Descriptor internal_static_google_protobuf_EnumValueDescriptorProto_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(6));
    private static FieldAccessorTable internal_static_google_protobuf_EnumValueDescriptorProto_fieldAccessorTable = new FieldAccessorTable(internal_static_google_protobuf_EnumValueDescriptorProto_descriptor, new String[]{Manifest.ATTRIBUTE_NAME, "Number", "Options"});
    private static final Descriptor internal_static_google_protobuf_EnumValueOptions_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(13));
    private static FieldAccessorTable internal_static_google_protobuf_EnumValueOptions_fieldAccessorTable = new FieldAccessorTable(internal_static_google_protobuf_EnumValueOptions_descriptor, new String[]{"Deprecated", "UninterpretedOption"});
    private static final Descriptor internal_static_google_protobuf_FieldDescriptorProto_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(3));
    private static FieldAccessorTable internal_static_google_protobuf_FieldDescriptorProto_fieldAccessorTable = new FieldAccessorTable(internal_static_google_protobuf_FieldDescriptorProto_descriptor, new String[]{Manifest.ATTRIBUTE_NAME, "Number", MSVSSConstants.COMMAND_LABEL, "Type", "TypeName", "Extendee", "DefaultValue", "OneofIndex", "Options"});
    private static final Descriptor internal_static_google_protobuf_FieldOptions_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(11));
    private static FieldAccessorTable internal_static_google_protobuf_FieldOptions_fieldAccessorTable = new FieldAccessorTable(internal_static_google_protobuf_FieldOptions_descriptor, new String[]{"Ctype", "Packed", "Lazy", "Deprecated", "ExperimentalMapKey", "Weak", "UninterpretedOption"});
    private static final Descriptor internal_static_google_protobuf_FileDescriptorProto_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(1));
    private static FieldAccessorTable internal_static_google_protobuf_FileDescriptorProto_fieldAccessorTable = new FieldAccessorTable(internal_static_google_protobuf_FileDescriptorProto_descriptor, new String[]{Manifest.ATTRIBUTE_NAME, "Package", "Dependency", "PublicDependency", "WeakDependency", "MessageType", "EnumType", "Service", "Extension", "Options", "SourceCodeInfo"});
    private static final Descriptor internal_static_google_protobuf_FileDescriptorSet_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(0));
    private static FieldAccessorTable internal_static_google_protobuf_FileDescriptorSet_fieldAccessorTable = new FieldAccessorTable(internal_static_google_protobuf_FileDescriptorSet_descriptor, new String[]{"File"});
    private static final Descriptor internal_static_google_protobuf_FileOptions_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(9));
    private static FieldAccessorTable internal_static_google_protobuf_FileOptions_fieldAccessorTable = new FieldAccessorTable(internal_static_google_protobuf_FileOptions_descriptor, new String[]{"JavaPackage", "JavaOuterClassname", "JavaMultipleFiles", "JavaGenerateEqualsAndHash", "JavaStringCheckUtf8", "OptimizeFor", "GoPackage", "CcGenericServices", "JavaGenericServices", "PyGenericServices", "Deprecated", "UninterpretedOption"});
    private static final Descriptor internal_static_google_protobuf_MessageOptions_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(10));
    private static FieldAccessorTable internal_static_google_protobuf_MessageOptions_fieldAccessorTable = new FieldAccessorTable(internal_static_google_protobuf_MessageOptions_descriptor, new String[]{"MessageSetWireFormat", "NoStandardDescriptorAccessor", "Deprecated", "UninterpretedOption"});
    private static final Descriptor internal_static_google_protobuf_MethodDescriptorProto_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(8));
    private static FieldAccessorTable internal_static_google_protobuf_MethodDescriptorProto_fieldAccessorTable = new FieldAccessorTable(internal_static_google_protobuf_MethodDescriptorProto_descriptor, new String[]{Manifest.ATTRIBUTE_NAME, "InputType", "OutputType", "Options"});
    private static final Descriptor internal_static_google_protobuf_MethodOptions_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(15));
    private static FieldAccessorTable internal_static_google_protobuf_MethodOptions_fieldAccessorTable = new FieldAccessorTable(internal_static_google_protobuf_MethodOptions_descriptor, new String[]{"Deprecated", "UninterpretedOption"});
    private static final Descriptor internal_static_google_protobuf_OneofDescriptorProto_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(4));
    private static FieldAccessorTable internal_static_google_protobuf_OneofDescriptorProto_fieldAccessorTable = new FieldAccessorTable(internal_static_google_protobuf_OneofDescriptorProto_descriptor, new String[]{Manifest.ATTRIBUTE_NAME});
    private static final Descriptor internal_static_google_protobuf_ServiceDescriptorProto_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(7));
    private static FieldAccessorTable internal_static_google_protobuf_ServiceDescriptorProto_fieldAccessorTable = new FieldAccessorTable(internal_static_google_protobuf_ServiceDescriptorProto_descriptor, new String[]{Manifest.ATTRIBUTE_NAME, "Method", "Options"});
    private static final Descriptor internal_static_google_protobuf_ServiceOptions_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(14));
    private static FieldAccessorTable internal_static_google_protobuf_ServiceOptions_fieldAccessorTable = new FieldAccessorTable(internal_static_google_protobuf_ServiceOptions_descriptor, new String[]{"Deprecated", "UninterpretedOption"});
    private static final Descriptor internal_static_google_protobuf_SourceCodeInfo_Location_descriptor = ((Descriptor) internal_static_google_protobuf_SourceCodeInfo_descriptor.getNestedTypes().get(0));
    private static FieldAccessorTable internal_static_google_protobuf_SourceCodeInfo_Location_fieldAccessorTable = new FieldAccessorTable(internal_static_google_protobuf_SourceCodeInfo_Location_descriptor, new String[]{CookieHeaderNames.PATH, "Span", "LeadingComments", "TrailingComments"});
    private static final Descriptor internal_static_google_protobuf_SourceCodeInfo_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(17));
    private static FieldAccessorTable internal_static_google_protobuf_SourceCodeInfo_fieldAccessorTable = new FieldAccessorTable(internal_static_google_protobuf_SourceCodeInfo_descriptor, new String[]{"Location"});
    private static final Descriptor internal_static_google_protobuf_UninterpretedOption_NamePart_descriptor = ((Descriptor) internal_static_google_protobuf_UninterpretedOption_descriptor.getNestedTypes().get(0));
    private static FieldAccessorTable internal_static_google_protobuf_UninterpretedOption_NamePart_fieldAccessorTable = new FieldAccessorTable(internal_static_google_protobuf_UninterpretedOption_NamePart_descriptor, new String[]{"NamePart", "IsExtension"});
    private static final Descriptor internal_static_google_protobuf_UninterpretedOption_descriptor = ((Descriptor) getDescriptor().getMessageTypes().get(16));
    private static FieldAccessorTable internal_static_google_protobuf_UninterpretedOption_fieldAccessorTable = new FieldAccessorTable(internal_static_google_protobuf_UninterpretedOption_descriptor, new String[]{Manifest.ATTRIBUTE_NAME, "IdentifierValue", "PositiveIntValue", "NegativeIntValue", "DoubleValue", "StringValue", "AggregateValue"});

    public interface DescriptorProtoOrBuilder extends MessageOrBuilder {
        EnumDescriptorProto getEnumType(int i);

        int getEnumTypeCount();

        List<EnumDescriptorProto> getEnumTypeList();

        EnumDescriptorProtoOrBuilder getEnumTypeOrBuilder(int i);

        List<? extends EnumDescriptorProtoOrBuilder> getEnumTypeOrBuilderList();

        FieldDescriptorProto getExtension(int i);

        int getExtensionCount();

        List<FieldDescriptorProto> getExtensionList();

        FieldDescriptorProtoOrBuilder getExtensionOrBuilder(int i);

        List<? extends FieldDescriptorProtoOrBuilder> getExtensionOrBuilderList();

        ExtensionRange getExtensionRange(int i);

        int getExtensionRangeCount();

        List<ExtensionRange> getExtensionRangeList();

        ExtensionRangeOrBuilder getExtensionRangeOrBuilder(int i);

        List<? extends ExtensionRangeOrBuilder> getExtensionRangeOrBuilderList();

        FieldDescriptorProto getField(int i);

        int getFieldCount();

        List<FieldDescriptorProto> getFieldList();

        FieldDescriptorProtoOrBuilder getFieldOrBuilder(int i);

        List<? extends FieldDescriptorProtoOrBuilder> getFieldOrBuilderList();

        String getName();

        ByteString getNameBytes();

        DescriptorProto getNestedType(int i);

        int getNestedTypeCount();

        List<DescriptorProto> getNestedTypeList();

        DescriptorProtoOrBuilder getNestedTypeOrBuilder(int i);

        List<? extends DescriptorProtoOrBuilder> getNestedTypeOrBuilderList();

        OneofDescriptorProto getOneofDecl(int i);

        int getOneofDeclCount();

        List<OneofDescriptorProto> getOneofDeclList();

        OneofDescriptorProtoOrBuilder getOneofDeclOrBuilder(int i);

        List<? extends OneofDescriptorProtoOrBuilder> getOneofDeclOrBuilderList();

        MessageOptions getOptions();

        MessageOptionsOrBuilder getOptionsOrBuilder();

        boolean hasName();

        boolean hasOptions();
    }

    public static final class DescriptorProto extends GeneratedMessage implements DescriptorProtoOrBuilder {
        public static final int ENUM_TYPE_FIELD_NUMBER = 4;
        public static final int EXTENSION_FIELD_NUMBER = 6;
        public static final int EXTENSION_RANGE_FIELD_NUMBER = 5;
        public static final int FIELD_FIELD_NUMBER = 2;
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int NESTED_TYPE_FIELD_NUMBER = 3;
        public static final int ONEOF_DECL_FIELD_NUMBER = 8;
        public static final int OPTIONS_FIELD_NUMBER = 7;
        public static Parser<DescriptorProto> PARSER = new AbstractParser<DescriptorProto>() {
            public DescriptorProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new DescriptorProto(input, extensionRegistry);
            }
        };
        private static final DescriptorProto defaultInstance = new DescriptorProto(true);
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private List<EnumDescriptorProto> enumType_;
        private List<ExtensionRange> extensionRange_;
        private List<FieldDescriptorProto> extension_;
        private List<FieldDescriptorProto> field_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private Object name_;
        private List<DescriptorProto> nestedType_;
        private List<OneofDescriptorProto> oneofDecl_;
        private MessageOptions options_;
        private final UnknownFieldSet unknownFields;

        public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> implements DescriptorProtoOrBuilder {
            private int bitField0_;
            private RepeatedFieldBuilder<EnumDescriptorProto, Builder, EnumDescriptorProtoOrBuilder> enumTypeBuilder_;
            private List<EnumDescriptorProto> enumType_;
            private RepeatedFieldBuilder<FieldDescriptorProto, Builder, FieldDescriptorProtoOrBuilder> extensionBuilder_;
            private RepeatedFieldBuilder<ExtensionRange, Builder, ExtensionRangeOrBuilder> extensionRangeBuilder_;
            private List<ExtensionRange> extensionRange_;
            private List<FieldDescriptorProto> extension_;
            private RepeatedFieldBuilder<FieldDescriptorProto, Builder, FieldDescriptorProtoOrBuilder> fieldBuilder_;
            private List<FieldDescriptorProto> field_;
            private Object name_;
            private RepeatedFieldBuilder<DescriptorProto, Builder, DescriptorProtoOrBuilder> nestedTypeBuilder_;
            private List<DescriptorProto> nestedType_;
            private RepeatedFieldBuilder<OneofDescriptorProto, Builder, OneofDescriptorProtoOrBuilder> oneofDeclBuilder_;
            private List<OneofDescriptorProto> oneofDecl_;
            private SingleFieldBuilder<MessageOptions, Builder, MessageOptionsOrBuilder> optionsBuilder_;
            private MessageOptions options_;

            public static final Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_DescriptorProto_descriptor;
            }

            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_DescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(DescriptorProto.class, Builder.class);
            }

            private Builder() {
                this.name_ = "";
                this.field_ = Collections.emptyList();
                this.extension_ = Collections.emptyList();
                this.nestedType_ = Collections.emptyList();
                this.enumType_ = Collections.emptyList();
                this.extensionRange_ = Collections.emptyList();
                this.oneofDecl_ = Collections.emptyList();
                this.options_ = MessageOptions.getDefaultInstance();
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.name_ = "";
                this.field_ = Collections.emptyList();
                this.extension_ = Collections.emptyList();
                this.nestedType_ = Collections.emptyList();
                this.enumType_ = Collections.emptyList();
                this.extensionRange_ = Collections.emptyList();
                this.oneofDecl_ = Collections.emptyList();
                this.options_ = MessageOptions.getDefaultInstance();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getFieldFieldBuilder();
                    getExtensionFieldBuilder();
                    getNestedTypeFieldBuilder();
                    getEnumTypeFieldBuilder();
                    getExtensionRangeFieldBuilder();
                    getOneofDeclFieldBuilder();
                    getOptionsFieldBuilder();
                }
            }

            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                this.name_ = "";
                this.bitField0_ &= -2;
                if (this.fieldBuilder_ == null) {
                    this.field_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                } else {
                    this.fieldBuilder_.clear();
                }
                if (this.extensionBuilder_ == null) {
                    this.extension_ = Collections.emptyList();
                    this.bitField0_ &= -5;
                } else {
                    this.extensionBuilder_.clear();
                }
                if (this.nestedTypeBuilder_ == null) {
                    this.nestedType_ = Collections.emptyList();
                    this.bitField0_ &= -9;
                } else {
                    this.nestedTypeBuilder_.clear();
                }
                if (this.enumTypeBuilder_ == null) {
                    this.enumType_ = Collections.emptyList();
                    this.bitField0_ &= -17;
                } else {
                    this.enumTypeBuilder_.clear();
                }
                if (this.extensionRangeBuilder_ == null) {
                    this.extensionRange_ = Collections.emptyList();
                    this.bitField0_ &= -33;
                } else {
                    this.extensionRangeBuilder_.clear();
                }
                if (this.oneofDeclBuilder_ == null) {
                    this.oneofDecl_ = Collections.emptyList();
                    this.bitField0_ &= -65;
                } else {
                    this.oneofDeclBuilder_.clear();
                }
                if (this.optionsBuilder_ == null) {
                    this.options_ = MessageOptions.getDefaultInstance();
                } else {
                    this.optionsBuilder_.clear();
                }
                this.bitField0_ &= -129;
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_DescriptorProto_descriptor;
            }

            public DescriptorProto getDefaultInstanceForType() {
                return DescriptorProto.getDefaultInstance();
            }

            public DescriptorProto build() {
                DescriptorProto result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw com.google.protobuf.AbstractMessage.Builder.newUninitializedMessageException(result);
            }

            public DescriptorProto buildPartial() {
                DescriptorProto result = new DescriptorProto((com.google.protobuf.GeneratedMessage.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.name_ = this.name_;
                if (this.fieldBuilder_ == null) {
                    if ((this.bitField0_ & 2) == 2) {
                        this.field_ = Collections.unmodifiableList(this.field_);
                        this.bitField0_ &= -3;
                    }
                    result.field_ = this.field_;
                } else {
                    result.field_ = this.fieldBuilder_.build();
                }
                if (this.extensionBuilder_ == null) {
                    if ((this.bitField0_ & 4) == 4) {
                        this.extension_ = Collections.unmodifiableList(this.extension_);
                        this.bitField0_ &= -5;
                    }
                    result.extension_ = this.extension_;
                } else {
                    result.extension_ = this.extensionBuilder_.build();
                }
                if (this.nestedTypeBuilder_ == null) {
                    if ((this.bitField0_ & 8) == 8) {
                        this.nestedType_ = Collections.unmodifiableList(this.nestedType_);
                        this.bitField0_ &= -9;
                    }
                    result.nestedType_ = this.nestedType_;
                } else {
                    result.nestedType_ = this.nestedTypeBuilder_.build();
                }
                if (this.enumTypeBuilder_ == null) {
                    if ((this.bitField0_ & 16) == 16) {
                        this.enumType_ = Collections.unmodifiableList(this.enumType_);
                        this.bitField0_ &= -17;
                    }
                    result.enumType_ = this.enumType_;
                } else {
                    result.enumType_ = this.enumTypeBuilder_.build();
                }
                if (this.extensionRangeBuilder_ == null) {
                    if ((this.bitField0_ & 32) == 32) {
                        this.extensionRange_ = Collections.unmodifiableList(this.extensionRange_);
                        this.bitField0_ &= -33;
                    }
                    result.extensionRange_ = this.extensionRange_;
                } else {
                    result.extensionRange_ = this.extensionRangeBuilder_.build();
                }
                if (this.oneofDeclBuilder_ == null) {
                    if ((this.bitField0_ & 64) == 64) {
                        this.oneofDecl_ = Collections.unmodifiableList(this.oneofDecl_);
                        this.bitField0_ &= -65;
                    }
                    result.oneofDecl_ = this.oneofDecl_;
                } else {
                    result.oneofDecl_ = this.oneofDeclBuilder_.build();
                }
                if ((from_bitField0_ & 128) == 128) {
                    to_bitField0_ |= 2;
                }
                if (this.optionsBuilder_ == null) {
                    result.options_ = this.options_;
                } else {
                    result.options_ = (MessageOptions) this.optionsBuilder_.build();
                }
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof DescriptorProto) {
                    return mergeFrom((DescriptorProto) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(DescriptorProto other) {
                RepeatedFieldBuilder repeatedFieldBuilder = null;
                if (other != DescriptorProto.getDefaultInstance()) {
                    if (other.hasName()) {
                        this.bitField0_ |= 1;
                        this.name_ = other.name_;
                        onChanged();
                    }
                    if (this.fieldBuilder_ == null) {
                        if (!other.field_.isEmpty()) {
                            if (this.field_.isEmpty()) {
                                this.field_ = other.field_;
                                this.bitField0_ &= -3;
                            } else {
                                ensureFieldIsMutable();
                                this.field_.addAll(other.field_);
                            }
                            onChanged();
                        }
                    } else if (!other.field_.isEmpty()) {
                        if (this.fieldBuilder_.isEmpty()) {
                            this.fieldBuilder_.dispose();
                            this.fieldBuilder_ = null;
                            this.field_ = other.field_;
                            this.bitField0_ &= -3;
                            this.fieldBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? getFieldFieldBuilder() : null;
                        } else {
                            this.fieldBuilder_.addAllMessages(other.field_);
                        }
                    }
                    if (this.extensionBuilder_ == null) {
                        if (!other.extension_.isEmpty()) {
                            if (this.extension_.isEmpty()) {
                                this.extension_ = other.extension_;
                                this.bitField0_ &= -5;
                            } else {
                                ensureExtensionIsMutable();
                                this.extension_.addAll(other.extension_);
                            }
                            onChanged();
                        }
                    } else if (!other.extension_.isEmpty()) {
                        if (this.extensionBuilder_.isEmpty()) {
                            this.extensionBuilder_.dispose();
                            this.extensionBuilder_ = null;
                            this.extension_ = other.extension_;
                            this.bitField0_ &= -5;
                            this.extensionBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? getExtensionFieldBuilder() : null;
                        } else {
                            this.extensionBuilder_.addAllMessages(other.extension_);
                        }
                    }
                    if (this.nestedTypeBuilder_ == null) {
                        if (!other.nestedType_.isEmpty()) {
                            if (this.nestedType_.isEmpty()) {
                                this.nestedType_ = other.nestedType_;
                                this.bitField0_ &= -9;
                            } else {
                                ensureNestedTypeIsMutable();
                                this.nestedType_.addAll(other.nestedType_);
                            }
                            onChanged();
                        }
                    } else if (!other.nestedType_.isEmpty()) {
                        if (this.nestedTypeBuilder_.isEmpty()) {
                            this.nestedTypeBuilder_.dispose();
                            this.nestedTypeBuilder_ = null;
                            this.nestedType_ = other.nestedType_;
                            this.bitField0_ &= -9;
                            this.nestedTypeBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? getNestedTypeFieldBuilder() : null;
                        } else {
                            this.nestedTypeBuilder_.addAllMessages(other.nestedType_);
                        }
                    }
                    if (this.enumTypeBuilder_ == null) {
                        if (!other.enumType_.isEmpty()) {
                            if (this.enumType_.isEmpty()) {
                                this.enumType_ = other.enumType_;
                                this.bitField0_ &= -17;
                            } else {
                                ensureEnumTypeIsMutable();
                                this.enumType_.addAll(other.enumType_);
                            }
                            onChanged();
                        }
                    } else if (!other.enumType_.isEmpty()) {
                        if (this.enumTypeBuilder_.isEmpty()) {
                            this.enumTypeBuilder_.dispose();
                            this.enumTypeBuilder_ = null;
                            this.enumType_ = other.enumType_;
                            this.bitField0_ &= -17;
                            this.enumTypeBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? getEnumTypeFieldBuilder() : null;
                        } else {
                            this.enumTypeBuilder_.addAllMessages(other.enumType_);
                        }
                    }
                    if (this.extensionRangeBuilder_ == null) {
                        if (!other.extensionRange_.isEmpty()) {
                            if (this.extensionRange_.isEmpty()) {
                                this.extensionRange_ = other.extensionRange_;
                                this.bitField0_ &= -33;
                            } else {
                                ensureExtensionRangeIsMutable();
                                this.extensionRange_.addAll(other.extensionRange_);
                            }
                            onChanged();
                        }
                    } else if (!other.extensionRange_.isEmpty()) {
                        if (this.extensionRangeBuilder_.isEmpty()) {
                            this.extensionRangeBuilder_.dispose();
                            this.extensionRangeBuilder_ = null;
                            this.extensionRange_ = other.extensionRange_;
                            this.bitField0_ &= -33;
                            this.extensionRangeBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? getExtensionRangeFieldBuilder() : null;
                        } else {
                            this.extensionRangeBuilder_.addAllMessages(other.extensionRange_);
                        }
                    }
                    if (this.oneofDeclBuilder_ == null) {
                        if (!other.oneofDecl_.isEmpty()) {
                            if (this.oneofDecl_.isEmpty()) {
                                this.oneofDecl_ = other.oneofDecl_;
                                this.bitField0_ &= -65;
                            } else {
                                ensureOneofDeclIsMutable();
                                this.oneofDecl_.addAll(other.oneofDecl_);
                            }
                            onChanged();
                        }
                    } else if (!other.oneofDecl_.isEmpty()) {
                        if (this.oneofDeclBuilder_.isEmpty()) {
                            this.oneofDeclBuilder_.dispose();
                            this.oneofDeclBuilder_ = null;
                            this.oneofDecl_ = other.oneofDecl_;
                            this.bitField0_ &= -65;
                            if (GeneratedMessage.alwaysUseFieldBuilders) {
                                repeatedFieldBuilder = getOneofDeclFieldBuilder();
                            }
                            this.oneofDeclBuilder_ = repeatedFieldBuilder;
                        } else {
                            this.oneofDeclBuilder_.addAllMessages(other.oneofDecl_);
                        }
                    }
                    if (other.hasOptions()) {
                        mergeOptions(other.getOptions());
                    }
                    mergeUnknownFields(other.getUnknownFields());
                }
                return this;
            }

            public final boolean isInitialized() {
                int i;
                for (i = 0; i < getFieldCount(); i++) {
                    if (!getField(i).isInitialized()) {
                        return false;
                    }
                }
                for (i = 0; i < getExtensionCount(); i++) {
                    if (!getExtension(i).isInitialized()) {
                        return false;
                    }
                }
                for (i = 0; i < getNestedTypeCount(); i++) {
                    if (!getNestedType(i).isInitialized()) {
                        return false;
                    }
                }
                for (i = 0; i < getEnumTypeCount(); i++) {
                    if (!getEnumType(i).isInitialized()) {
                        return false;
                    }
                }
                if (!hasOptions() || getOptions().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                DescriptorProto parsedMessage = null;
                try {
                    parsedMessage = (DescriptorProto) DescriptorProto.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    parsedMessage = (DescriptorProto) e.getUnfinishedMessage();
                    throw e;
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
            }

            public boolean hasName() {
                return (this.bitField0_ & 1) == 1;
            }

            public String getName() {
                ByteString ref = this.name_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.name_ = s;
                return s;
            }

            public ByteString getNameBytes() {
                Object ref = this.name_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.name_ = b;
                return b;
            }

            public Builder setName(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.name_ = value;
                onChanged();
                return this;
            }

            public Builder clearName() {
                this.bitField0_ &= -2;
                this.name_ = DescriptorProto.getDefaultInstance().getName();
                onChanged();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.name_ = value;
                onChanged();
                return this;
            }

            private void ensureFieldIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.field_ = new ArrayList(this.field_);
                    this.bitField0_ |= 2;
                }
            }

            public List<FieldDescriptorProto> getFieldList() {
                if (this.fieldBuilder_ == null) {
                    return Collections.unmodifiableList(this.field_);
                }
                return this.fieldBuilder_.getMessageList();
            }

            public int getFieldCount() {
                if (this.fieldBuilder_ == null) {
                    return this.field_.size();
                }
                return this.fieldBuilder_.getCount();
            }

            public FieldDescriptorProto getField(int index) {
                if (this.fieldBuilder_ == null) {
                    return (FieldDescriptorProto) this.field_.get(index);
                }
                return (FieldDescriptorProto) this.fieldBuilder_.getMessage(index);
            }

            public Builder setField(int index, FieldDescriptorProto value) {
                if (this.fieldBuilder_ != null) {
                    this.fieldBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureFieldIsMutable();
                    this.field_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setField(int index, Builder builderForValue) {
                if (this.fieldBuilder_ == null) {
                    ensureFieldIsMutable();
                    this.field_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.fieldBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addField(FieldDescriptorProto value) {
                if (this.fieldBuilder_ != null) {
                    this.fieldBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureFieldIsMutable();
                    this.field_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addField(int index, FieldDescriptorProto value) {
                if (this.fieldBuilder_ != null) {
                    this.fieldBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureFieldIsMutable();
                    this.field_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addField(Builder builderForValue) {
                if (this.fieldBuilder_ == null) {
                    ensureFieldIsMutable();
                    this.field_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.fieldBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addField(int index, Builder builderForValue) {
                if (this.fieldBuilder_ == null) {
                    ensureFieldIsMutable();
                    this.field_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.fieldBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllField(Iterable<? extends FieldDescriptorProto> values) {
                if (this.fieldBuilder_ == null) {
                    ensureFieldIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.field_);
                    onChanged();
                } else {
                    this.fieldBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearField() {
                if (this.fieldBuilder_ == null) {
                    this.field_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                    onChanged();
                } else {
                    this.fieldBuilder_.clear();
                }
                return this;
            }

            public Builder removeField(int index) {
                if (this.fieldBuilder_ == null) {
                    ensureFieldIsMutable();
                    this.field_.remove(index);
                    onChanged();
                } else {
                    this.fieldBuilder_.remove(index);
                }
                return this;
            }

            public Builder getFieldBuilder(int index) {
                return (Builder) getFieldFieldBuilder().getBuilder(index);
            }

            public FieldDescriptorProtoOrBuilder getFieldOrBuilder(int index) {
                if (this.fieldBuilder_ == null) {
                    return (FieldDescriptorProtoOrBuilder) this.field_.get(index);
                }
                return (FieldDescriptorProtoOrBuilder) this.fieldBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends FieldDescriptorProtoOrBuilder> getFieldOrBuilderList() {
                if (this.fieldBuilder_ != null) {
                    return this.fieldBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.field_);
            }

            public Builder addFieldBuilder() {
                return (Builder) getFieldFieldBuilder().addBuilder(FieldDescriptorProto.getDefaultInstance());
            }

            public Builder addFieldBuilder(int index) {
                return (Builder) getFieldFieldBuilder().addBuilder(index, FieldDescriptorProto.getDefaultInstance());
            }

            public List<Builder> getFieldBuilderList() {
                return getFieldFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<FieldDescriptorProto, Builder, FieldDescriptorProtoOrBuilder> getFieldFieldBuilder() {
                if (this.fieldBuilder_ == null) {
                    this.fieldBuilder_ = new RepeatedFieldBuilder(this.field_, (this.bitField0_ & 2) == 2, getParentForChildren(), isClean());
                    this.field_ = null;
                }
                return this.fieldBuilder_;
            }

            private void ensureExtensionIsMutable() {
                if ((this.bitField0_ & 4) != 4) {
                    this.extension_ = new ArrayList(this.extension_);
                    this.bitField0_ |= 4;
                }
            }

            public List<FieldDescriptorProto> getExtensionList() {
                if (this.extensionBuilder_ == null) {
                    return Collections.unmodifiableList(this.extension_);
                }
                return this.extensionBuilder_.getMessageList();
            }

            public int getExtensionCount() {
                if (this.extensionBuilder_ == null) {
                    return this.extension_.size();
                }
                return this.extensionBuilder_.getCount();
            }

            public FieldDescriptorProto getExtension(int index) {
                if (this.extensionBuilder_ == null) {
                    return (FieldDescriptorProto) this.extension_.get(index);
                }
                return (FieldDescriptorProto) this.extensionBuilder_.getMessage(index);
            }

            public Builder setExtension(int index, FieldDescriptorProto value) {
                if (this.extensionBuilder_ != null) {
                    this.extensionBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureExtensionIsMutable();
                    this.extension_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setExtension(int index, Builder builderForValue) {
                if (this.extensionBuilder_ == null) {
                    ensureExtensionIsMutable();
                    this.extension_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.extensionBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addExtension(FieldDescriptorProto value) {
                if (this.extensionBuilder_ != null) {
                    this.extensionBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureExtensionIsMutable();
                    this.extension_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addExtension(int index, FieldDescriptorProto value) {
                if (this.extensionBuilder_ != null) {
                    this.extensionBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureExtensionIsMutable();
                    this.extension_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addExtension(Builder builderForValue) {
                if (this.extensionBuilder_ == null) {
                    ensureExtensionIsMutable();
                    this.extension_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.extensionBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addExtension(int index, Builder builderForValue) {
                if (this.extensionBuilder_ == null) {
                    ensureExtensionIsMutable();
                    this.extension_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.extensionBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllExtension(Iterable<? extends FieldDescriptorProto> values) {
                if (this.extensionBuilder_ == null) {
                    ensureExtensionIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.extension_);
                    onChanged();
                } else {
                    this.extensionBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearExtension() {
                if (this.extensionBuilder_ == null) {
                    this.extension_ = Collections.emptyList();
                    this.bitField0_ &= -5;
                    onChanged();
                } else {
                    this.extensionBuilder_.clear();
                }
                return this;
            }

            public Builder removeExtension(int index) {
                if (this.extensionBuilder_ == null) {
                    ensureExtensionIsMutable();
                    this.extension_.remove(index);
                    onChanged();
                } else {
                    this.extensionBuilder_.remove(index);
                }
                return this;
            }

            public Builder getExtensionBuilder(int index) {
                return (Builder) getExtensionFieldBuilder().getBuilder(index);
            }

            public FieldDescriptorProtoOrBuilder getExtensionOrBuilder(int index) {
                if (this.extensionBuilder_ == null) {
                    return (FieldDescriptorProtoOrBuilder) this.extension_.get(index);
                }
                return (FieldDescriptorProtoOrBuilder) this.extensionBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends FieldDescriptorProtoOrBuilder> getExtensionOrBuilderList() {
                if (this.extensionBuilder_ != null) {
                    return this.extensionBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.extension_);
            }

            public Builder addExtensionBuilder() {
                return (Builder) getExtensionFieldBuilder().addBuilder(FieldDescriptorProto.getDefaultInstance());
            }

            public Builder addExtensionBuilder(int index) {
                return (Builder) getExtensionFieldBuilder().addBuilder(index, FieldDescriptorProto.getDefaultInstance());
            }

            public List<Builder> getExtensionBuilderList() {
                return getExtensionFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<FieldDescriptorProto, Builder, FieldDescriptorProtoOrBuilder> getExtensionFieldBuilder() {
                if (this.extensionBuilder_ == null) {
                    this.extensionBuilder_ = new RepeatedFieldBuilder(this.extension_, (this.bitField0_ & 4) == 4, getParentForChildren(), isClean());
                    this.extension_ = null;
                }
                return this.extensionBuilder_;
            }

            private void ensureNestedTypeIsMutable() {
                if ((this.bitField0_ & 8) != 8) {
                    this.nestedType_ = new ArrayList(this.nestedType_);
                    this.bitField0_ |= 8;
                }
            }

            public List<DescriptorProto> getNestedTypeList() {
                if (this.nestedTypeBuilder_ == null) {
                    return Collections.unmodifiableList(this.nestedType_);
                }
                return this.nestedTypeBuilder_.getMessageList();
            }

            public int getNestedTypeCount() {
                if (this.nestedTypeBuilder_ == null) {
                    return this.nestedType_.size();
                }
                return this.nestedTypeBuilder_.getCount();
            }

            public DescriptorProto getNestedType(int index) {
                if (this.nestedTypeBuilder_ == null) {
                    return (DescriptorProto) this.nestedType_.get(index);
                }
                return (DescriptorProto) this.nestedTypeBuilder_.getMessage(index);
            }

            public Builder setNestedType(int index, DescriptorProto value) {
                if (this.nestedTypeBuilder_ != null) {
                    this.nestedTypeBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureNestedTypeIsMutable();
                    this.nestedType_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setNestedType(int index, Builder builderForValue) {
                if (this.nestedTypeBuilder_ == null) {
                    ensureNestedTypeIsMutable();
                    this.nestedType_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.nestedTypeBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addNestedType(DescriptorProto value) {
                if (this.nestedTypeBuilder_ != null) {
                    this.nestedTypeBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureNestedTypeIsMutable();
                    this.nestedType_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addNestedType(int index, DescriptorProto value) {
                if (this.nestedTypeBuilder_ != null) {
                    this.nestedTypeBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureNestedTypeIsMutable();
                    this.nestedType_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addNestedType(Builder builderForValue) {
                if (this.nestedTypeBuilder_ == null) {
                    ensureNestedTypeIsMutable();
                    this.nestedType_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.nestedTypeBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addNestedType(int index, Builder builderForValue) {
                if (this.nestedTypeBuilder_ == null) {
                    ensureNestedTypeIsMutable();
                    this.nestedType_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.nestedTypeBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllNestedType(Iterable<? extends DescriptorProto> values) {
                if (this.nestedTypeBuilder_ == null) {
                    ensureNestedTypeIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.nestedType_);
                    onChanged();
                } else {
                    this.nestedTypeBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearNestedType() {
                if (this.nestedTypeBuilder_ == null) {
                    this.nestedType_ = Collections.emptyList();
                    this.bitField0_ &= -9;
                    onChanged();
                } else {
                    this.nestedTypeBuilder_.clear();
                }
                return this;
            }

            public Builder removeNestedType(int index) {
                if (this.nestedTypeBuilder_ == null) {
                    ensureNestedTypeIsMutable();
                    this.nestedType_.remove(index);
                    onChanged();
                } else {
                    this.nestedTypeBuilder_.remove(index);
                }
                return this;
            }

            public Builder getNestedTypeBuilder(int index) {
                return (Builder) getNestedTypeFieldBuilder().getBuilder(index);
            }

            public DescriptorProtoOrBuilder getNestedTypeOrBuilder(int index) {
                if (this.nestedTypeBuilder_ == null) {
                    return (DescriptorProtoOrBuilder) this.nestedType_.get(index);
                }
                return (DescriptorProtoOrBuilder) this.nestedTypeBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends DescriptorProtoOrBuilder> getNestedTypeOrBuilderList() {
                if (this.nestedTypeBuilder_ != null) {
                    return this.nestedTypeBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.nestedType_);
            }

            public Builder addNestedTypeBuilder() {
                return (Builder) getNestedTypeFieldBuilder().addBuilder(DescriptorProto.getDefaultInstance());
            }

            public Builder addNestedTypeBuilder(int index) {
                return (Builder) getNestedTypeFieldBuilder().addBuilder(index, DescriptorProto.getDefaultInstance());
            }

            public List<Builder> getNestedTypeBuilderList() {
                return getNestedTypeFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<DescriptorProto, Builder, DescriptorProtoOrBuilder> getNestedTypeFieldBuilder() {
                if (this.nestedTypeBuilder_ == null) {
                    this.nestedTypeBuilder_ = new RepeatedFieldBuilder(this.nestedType_, (this.bitField0_ & 8) == 8, getParentForChildren(), isClean());
                    this.nestedType_ = null;
                }
                return this.nestedTypeBuilder_;
            }

            private void ensureEnumTypeIsMutable() {
                if ((this.bitField0_ & 16) != 16) {
                    this.enumType_ = new ArrayList(this.enumType_);
                    this.bitField0_ |= 16;
                }
            }

            public List<EnumDescriptorProto> getEnumTypeList() {
                if (this.enumTypeBuilder_ == null) {
                    return Collections.unmodifiableList(this.enumType_);
                }
                return this.enumTypeBuilder_.getMessageList();
            }

            public int getEnumTypeCount() {
                if (this.enumTypeBuilder_ == null) {
                    return this.enumType_.size();
                }
                return this.enumTypeBuilder_.getCount();
            }

            public EnumDescriptorProto getEnumType(int index) {
                if (this.enumTypeBuilder_ == null) {
                    return (EnumDescriptorProto) this.enumType_.get(index);
                }
                return (EnumDescriptorProto) this.enumTypeBuilder_.getMessage(index);
            }

            public Builder setEnumType(int index, EnumDescriptorProto value) {
                if (this.enumTypeBuilder_ != null) {
                    this.enumTypeBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureEnumTypeIsMutable();
                    this.enumType_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setEnumType(int index, Builder builderForValue) {
                if (this.enumTypeBuilder_ == null) {
                    ensureEnumTypeIsMutable();
                    this.enumType_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.enumTypeBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addEnumType(EnumDescriptorProto value) {
                if (this.enumTypeBuilder_ != null) {
                    this.enumTypeBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureEnumTypeIsMutable();
                    this.enumType_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addEnumType(int index, EnumDescriptorProto value) {
                if (this.enumTypeBuilder_ != null) {
                    this.enumTypeBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureEnumTypeIsMutable();
                    this.enumType_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addEnumType(Builder builderForValue) {
                if (this.enumTypeBuilder_ == null) {
                    ensureEnumTypeIsMutable();
                    this.enumType_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.enumTypeBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addEnumType(int index, Builder builderForValue) {
                if (this.enumTypeBuilder_ == null) {
                    ensureEnumTypeIsMutable();
                    this.enumType_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.enumTypeBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllEnumType(Iterable<? extends EnumDescriptorProto> values) {
                if (this.enumTypeBuilder_ == null) {
                    ensureEnumTypeIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.enumType_);
                    onChanged();
                } else {
                    this.enumTypeBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearEnumType() {
                if (this.enumTypeBuilder_ == null) {
                    this.enumType_ = Collections.emptyList();
                    this.bitField0_ &= -17;
                    onChanged();
                } else {
                    this.enumTypeBuilder_.clear();
                }
                return this;
            }

            public Builder removeEnumType(int index) {
                if (this.enumTypeBuilder_ == null) {
                    ensureEnumTypeIsMutable();
                    this.enumType_.remove(index);
                    onChanged();
                } else {
                    this.enumTypeBuilder_.remove(index);
                }
                return this;
            }

            public Builder getEnumTypeBuilder(int index) {
                return (Builder) getEnumTypeFieldBuilder().getBuilder(index);
            }

            public EnumDescriptorProtoOrBuilder getEnumTypeOrBuilder(int index) {
                if (this.enumTypeBuilder_ == null) {
                    return (EnumDescriptorProtoOrBuilder) this.enumType_.get(index);
                }
                return (EnumDescriptorProtoOrBuilder) this.enumTypeBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends EnumDescriptorProtoOrBuilder> getEnumTypeOrBuilderList() {
                if (this.enumTypeBuilder_ != null) {
                    return this.enumTypeBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.enumType_);
            }

            public Builder addEnumTypeBuilder() {
                return (Builder) getEnumTypeFieldBuilder().addBuilder(EnumDescriptorProto.getDefaultInstance());
            }

            public Builder addEnumTypeBuilder(int index) {
                return (Builder) getEnumTypeFieldBuilder().addBuilder(index, EnumDescriptorProto.getDefaultInstance());
            }

            public List<Builder> getEnumTypeBuilderList() {
                return getEnumTypeFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<EnumDescriptorProto, Builder, EnumDescriptorProtoOrBuilder> getEnumTypeFieldBuilder() {
                if (this.enumTypeBuilder_ == null) {
                    this.enumTypeBuilder_ = new RepeatedFieldBuilder(this.enumType_, (this.bitField0_ & 16) == 16, getParentForChildren(), isClean());
                    this.enumType_ = null;
                }
                return this.enumTypeBuilder_;
            }

            private void ensureExtensionRangeIsMutable() {
                if ((this.bitField0_ & 32) != 32) {
                    this.extensionRange_ = new ArrayList(this.extensionRange_);
                    this.bitField0_ |= 32;
                }
            }

            public List<ExtensionRange> getExtensionRangeList() {
                if (this.extensionRangeBuilder_ == null) {
                    return Collections.unmodifiableList(this.extensionRange_);
                }
                return this.extensionRangeBuilder_.getMessageList();
            }

            public int getExtensionRangeCount() {
                if (this.extensionRangeBuilder_ == null) {
                    return this.extensionRange_.size();
                }
                return this.extensionRangeBuilder_.getCount();
            }

            public ExtensionRange getExtensionRange(int index) {
                if (this.extensionRangeBuilder_ == null) {
                    return (ExtensionRange) this.extensionRange_.get(index);
                }
                return (ExtensionRange) this.extensionRangeBuilder_.getMessage(index);
            }

            public Builder setExtensionRange(int index, ExtensionRange value) {
                if (this.extensionRangeBuilder_ != null) {
                    this.extensionRangeBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureExtensionRangeIsMutable();
                    this.extensionRange_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setExtensionRange(int index, Builder builderForValue) {
                if (this.extensionRangeBuilder_ == null) {
                    ensureExtensionRangeIsMutable();
                    this.extensionRange_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.extensionRangeBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addExtensionRange(ExtensionRange value) {
                if (this.extensionRangeBuilder_ != null) {
                    this.extensionRangeBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureExtensionRangeIsMutable();
                    this.extensionRange_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addExtensionRange(int index, ExtensionRange value) {
                if (this.extensionRangeBuilder_ != null) {
                    this.extensionRangeBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureExtensionRangeIsMutable();
                    this.extensionRange_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addExtensionRange(Builder builderForValue) {
                if (this.extensionRangeBuilder_ == null) {
                    ensureExtensionRangeIsMutable();
                    this.extensionRange_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.extensionRangeBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addExtensionRange(int index, Builder builderForValue) {
                if (this.extensionRangeBuilder_ == null) {
                    ensureExtensionRangeIsMutable();
                    this.extensionRange_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.extensionRangeBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllExtensionRange(Iterable<? extends ExtensionRange> values) {
                if (this.extensionRangeBuilder_ == null) {
                    ensureExtensionRangeIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.extensionRange_);
                    onChanged();
                } else {
                    this.extensionRangeBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearExtensionRange() {
                if (this.extensionRangeBuilder_ == null) {
                    this.extensionRange_ = Collections.emptyList();
                    this.bitField0_ &= -33;
                    onChanged();
                } else {
                    this.extensionRangeBuilder_.clear();
                }
                return this;
            }

            public Builder removeExtensionRange(int index) {
                if (this.extensionRangeBuilder_ == null) {
                    ensureExtensionRangeIsMutable();
                    this.extensionRange_.remove(index);
                    onChanged();
                } else {
                    this.extensionRangeBuilder_.remove(index);
                }
                return this;
            }

            public Builder getExtensionRangeBuilder(int index) {
                return (Builder) getExtensionRangeFieldBuilder().getBuilder(index);
            }

            public ExtensionRangeOrBuilder getExtensionRangeOrBuilder(int index) {
                if (this.extensionRangeBuilder_ == null) {
                    return (ExtensionRangeOrBuilder) this.extensionRange_.get(index);
                }
                return (ExtensionRangeOrBuilder) this.extensionRangeBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends ExtensionRangeOrBuilder> getExtensionRangeOrBuilderList() {
                if (this.extensionRangeBuilder_ != null) {
                    return this.extensionRangeBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.extensionRange_);
            }

            public Builder addExtensionRangeBuilder() {
                return (Builder) getExtensionRangeFieldBuilder().addBuilder(ExtensionRange.getDefaultInstance());
            }

            public Builder addExtensionRangeBuilder(int index) {
                return (Builder) getExtensionRangeFieldBuilder().addBuilder(index, ExtensionRange.getDefaultInstance());
            }

            public List<Builder> getExtensionRangeBuilderList() {
                return getExtensionRangeFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<ExtensionRange, Builder, ExtensionRangeOrBuilder> getExtensionRangeFieldBuilder() {
                if (this.extensionRangeBuilder_ == null) {
                    this.extensionRangeBuilder_ = new RepeatedFieldBuilder(this.extensionRange_, (this.bitField0_ & 32) == 32, getParentForChildren(), isClean());
                    this.extensionRange_ = null;
                }
                return this.extensionRangeBuilder_;
            }

            private void ensureOneofDeclIsMutable() {
                if ((this.bitField0_ & 64) != 64) {
                    this.oneofDecl_ = new ArrayList(this.oneofDecl_);
                    this.bitField0_ |= 64;
                }
            }

            public List<OneofDescriptorProto> getOneofDeclList() {
                if (this.oneofDeclBuilder_ == null) {
                    return Collections.unmodifiableList(this.oneofDecl_);
                }
                return this.oneofDeclBuilder_.getMessageList();
            }

            public int getOneofDeclCount() {
                if (this.oneofDeclBuilder_ == null) {
                    return this.oneofDecl_.size();
                }
                return this.oneofDeclBuilder_.getCount();
            }

            public OneofDescriptorProto getOneofDecl(int index) {
                if (this.oneofDeclBuilder_ == null) {
                    return (OneofDescriptorProto) this.oneofDecl_.get(index);
                }
                return (OneofDescriptorProto) this.oneofDeclBuilder_.getMessage(index);
            }

            public Builder setOneofDecl(int index, OneofDescriptorProto value) {
                if (this.oneofDeclBuilder_ != null) {
                    this.oneofDeclBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureOneofDeclIsMutable();
                    this.oneofDecl_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setOneofDecl(int index, Builder builderForValue) {
                if (this.oneofDeclBuilder_ == null) {
                    ensureOneofDeclIsMutable();
                    this.oneofDecl_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.oneofDeclBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addOneofDecl(OneofDescriptorProto value) {
                if (this.oneofDeclBuilder_ != null) {
                    this.oneofDeclBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureOneofDeclIsMutable();
                    this.oneofDecl_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addOneofDecl(int index, OneofDescriptorProto value) {
                if (this.oneofDeclBuilder_ != null) {
                    this.oneofDeclBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureOneofDeclIsMutable();
                    this.oneofDecl_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addOneofDecl(Builder builderForValue) {
                if (this.oneofDeclBuilder_ == null) {
                    ensureOneofDeclIsMutable();
                    this.oneofDecl_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.oneofDeclBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addOneofDecl(int index, Builder builderForValue) {
                if (this.oneofDeclBuilder_ == null) {
                    ensureOneofDeclIsMutable();
                    this.oneofDecl_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.oneofDeclBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllOneofDecl(Iterable<? extends OneofDescriptorProto> values) {
                if (this.oneofDeclBuilder_ == null) {
                    ensureOneofDeclIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.oneofDecl_);
                    onChanged();
                } else {
                    this.oneofDeclBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearOneofDecl() {
                if (this.oneofDeclBuilder_ == null) {
                    this.oneofDecl_ = Collections.emptyList();
                    this.bitField0_ &= -65;
                    onChanged();
                } else {
                    this.oneofDeclBuilder_.clear();
                }
                return this;
            }

            public Builder removeOneofDecl(int index) {
                if (this.oneofDeclBuilder_ == null) {
                    ensureOneofDeclIsMutable();
                    this.oneofDecl_.remove(index);
                    onChanged();
                } else {
                    this.oneofDeclBuilder_.remove(index);
                }
                return this;
            }

            public Builder getOneofDeclBuilder(int index) {
                return (Builder) getOneofDeclFieldBuilder().getBuilder(index);
            }

            public OneofDescriptorProtoOrBuilder getOneofDeclOrBuilder(int index) {
                if (this.oneofDeclBuilder_ == null) {
                    return (OneofDescriptorProtoOrBuilder) this.oneofDecl_.get(index);
                }
                return (OneofDescriptorProtoOrBuilder) this.oneofDeclBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends OneofDescriptorProtoOrBuilder> getOneofDeclOrBuilderList() {
                if (this.oneofDeclBuilder_ != null) {
                    return this.oneofDeclBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.oneofDecl_);
            }

            public Builder addOneofDeclBuilder() {
                return (Builder) getOneofDeclFieldBuilder().addBuilder(OneofDescriptorProto.getDefaultInstance());
            }

            public Builder addOneofDeclBuilder(int index) {
                return (Builder) getOneofDeclFieldBuilder().addBuilder(index, OneofDescriptorProto.getDefaultInstance());
            }

            public List<Builder> getOneofDeclBuilderList() {
                return getOneofDeclFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<OneofDescriptorProto, Builder, OneofDescriptorProtoOrBuilder> getOneofDeclFieldBuilder() {
                if (this.oneofDeclBuilder_ == null) {
                    this.oneofDeclBuilder_ = new RepeatedFieldBuilder(this.oneofDecl_, (this.bitField0_ & 64) == 64, getParentForChildren(), isClean());
                    this.oneofDecl_ = null;
                }
                return this.oneofDeclBuilder_;
            }

            public boolean hasOptions() {
                return (this.bitField0_ & 128) == 128;
            }

            public MessageOptions getOptions() {
                if (this.optionsBuilder_ == null) {
                    return this.options_;
                }
                return (MessageOptions) this.optionsBuilder_.getMessage();
            }

            public Builder setOptions(MessageOptions value) {
                if (this.optionsBuilder_ != null) {
                    this.optionsBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.options_ = value;
                    onChanged();
                }
                this.bitField0_ |= 128;
                return this;
            }

            public Builder setOptions(Builder builderForValue) {
                if (this.optionsBuilder_ == null) {
                    this.options_ = builderForValue.build();
                    onChanged();
                } else {
                    this.optionsBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 128;
                return this;
            }

            public Builder mergeOptions(MessageOptions value) {
                if (this.optionsBuilder_ == null) {
                    if ((this.bitField0_ & 128) != 128 || this.options_ == MessageOptions.getDefaultInstance()) {
                        this.options_ = value;
                    } else {
                        this.options_ = MessageOptions.newBuilder(this.options_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.optionsBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 128;
                return this;
            }

            public Builder clearOptions() {
                if (this.optionsBuilder_ == null) {
                    this.options_ = MessageOptions.getDefaultInstance();
                    onChanged();
                } else {
                    this.optionsBuilder_.clear();
                }
                this.bitField0_ &= -129;
                return this;
            }

            public Builder getOptionsBuilder() {
                this.bitField0_ |= 128;
                onChanged();
                return (Builder) getOptionsFieldBuilder().getBuilder();
            }

            public MessageOptionsOrBuilder getOptionsOrBuilder() {
                if (this.optionsBuilder_ != null) {
                    return (MessageOptionsOrBuilder) this.optionsBuilder_.getMessageOrBuilder();
                }
                return this.options_;
            }

            private SingleFieldBuilder<MessageOptions, Builder, MessageOptionsOrBuilder> getOptionsFieldBuilder() {
                if (this.optionsBuilder_ == null) {
                    this.optionsBuilder_ = new SingleFieldBuilder(getOptions(), getParentForChildren(), isClean());
                    this.options_ = null;
                }
                return this.optionsBuilder_;
            }
        }

        public interface ExtensionRangeOrBuilder extends MessageOrBuilder {
            int getEnd();

            int getStart();

            boolean hasEnd();

            boolean hasStart();
        }

        public static final class ExtensionRange extends GeneratedMessage implements ExtensionRangeOrBuilder {
            public static final int END_FIELD_NUMBER = 2;
            public static Parser<ExtensionRange> PARSER = new AbstractParser<ExtensionRange>() {
                public ExtensionRange parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new ExtensionRange(input, extensionRegistry);
                }
            };
            public static final int START_FIELD_NUMBER = 1;
            private static final ExtensionRange defaultInstance = new ExtensionRange(true);
            private static final long serialVersionUID = 0;
            private int bitField0_;
            private int end_;
            private byte memoizedIsInitialized;
            private int memoizedSerializedSize;
            private int start_;
            private final UnknownFieldSet unknownFields;

            public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> implements ExtensionRangeOrBuilder {
                private int bitField0_;
                private int end_;
                private int start_;

                public static final Descriptor getDescriptor() {
                    return DescriptorProtos.internal_static_google_protobuf_DescriptorProto_ExtensionRange_descriptor;
                }

                protected FieldAccessorTable internalGetFieldAccessorTable() {
                    return DescriptorProtos.internal_static_google_protobuf_DescriptorProto_ExtensionRange_fieldAccessorTable.ensureFieldAccessorsInitialized(ExtensionRange.class, Builder.class);
                }

                private Builder() {
                    maybeForceBuilderInitialization();
                }

                private Builder(BuilderParent parent) {
                    super(parent);
                    maybeForceBuilderInitialization();
                }

                private void maybeForceBuilderInitialization() {
                    if (!GeneratedMessage.alwaysUseFieldBuilders) {
                    }
                }

                private static Builder create() {
                    return new Builder();
                }

                public Builder clear() {
                    super.clear();
                    this.start_ = 0;
                    this.bitField0_ &= -2;
                    this.end_ = 0;
                    this.bitField0_ &= -3;
                    return this;
                }

                public Builder clone() {
                    return create().mergeFrom(buildPartial());
                }

                public Descriptor getDescriptorForType() {
                    return DescriptorProtos.internal_static_google_protobuf_DescriptorProto_ExtensionRange_descriptor;
                }

                public ExtensionRange getDefaultInstanceForType() {
                    return ExtensionRange.getDefaultInstance();
                }

                public ExtensionRange build() {
                    ExtensionRange result = buildPartial();
                    if (result.isInitialized()) {
                        return result;
                    }
                    throw com.google.protobuf.AbstractMessage.Builder.newUninitializedMessageException(result);
                }

                public ExtensionRange buildPartial() {
                    ExtensionRange result = new ExtensionRange((com.google.protobuf.GeneratedMessage.Builder) this);
                    int from_bitField0_ = this.bitField0_;
                    int to_bitField0_ = 0;
                    if ((from_bitField0_ & 1) == 1) {
                        to_bitField0_ = 0 | 1;
                    }
                    result.start_ = this.start_;
                    if ((from_bitField0_ & 2) == 2) {
                        to_bitField0_ |= 2;
                    }
                    result.end_ = this.end_;
                    result.bitField0_ = to_bitField0_;
                    onBuilt();
                    return result;
                }

                public Builder mergeFrom(Message other) {
                    if (other instanceof ExtensionRange) {
                        return mergeFrom((ExtensionRange) other);
                    }
                    super.mergeFrom(other);
                    return this;
                }

                public Builder mergeFrom(ExtensionRange other) {
                    if (other != ExtensionRange.getDefaultInstance()) {
                        if (other.hasStart()) {
                            setStart(other.getStart());
                        }
                        if (other.hasEnd()) {
                            setEnd(other.getEnd());
                        }
                        mergeUnknownFields(other.getUnknownFields());
                    }
                    return this;
                }

                public final boolean isInitialized() {
                    return true;
                }

                public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                    ExtensionRange parsedMessage = null;
                    try {
                        parsedMessage = (ExtensionRange) ExtensionRange.PARSER.parsePartialFrom(input, extensionRegistry);
                        if (parsedMessage != null) {
                            mergeFrom(parsedMessage);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        parsedMessage = (ExtensionRange) e.getUnfinishedMessage();
                        throw e;
                    } catch (Throwable th) {
                        if (parsedMessage != null) {
                            mergeFrom(parsedMessage);
                        }
                    }
                }

                public boolean hasStart() {
                    return (this.bitField0_ & 1) == 1;
                }

                public int getStart() {
                    return this.start_;
                }

                public Builder setStart(int value) {
                    this.bitField0_ |= 1;
                    this.start_ = value;
                    onChanged();
                    return this;
                }

                public Builder clearStart() {
                    this.bitField0_ &= -2;
                    this.start_ = 0;
                    onChanged();
                    return this;
                }

                public boolean hasEnd() {
                    return (this.bitField0_ & 2) == 2;
                }

                public int getEnd() {
                    return this.end_;
                }

                public Builder setEnd(int value) {
                    this.bitField0_ |= 2;
                    this.end_ = value;
                    onChanged();
                    return this;
                }

                public Builder clearEnd() {
                    this.bitField0_ &= -3;
                    this.end_ = 0;
                    onChanged();
                    return this;
                }
            }

            private ExtensionRange(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
                super(builder);
                this.memoizedIsInitialized = (byte) -1;
                this.memoizedSerializedSize = -1;
                this.unknownFields = builder.getUnknownFields();
            }

            private ExtensionRange(boolean noInit) {
                this.memoizedIsInitialized = (byte) -1;
                this.memoizedSerializedSize = -1;
                this.unknownFields = UnknownFieldSet.getDefaultInstance();
            }

            public static ExtensionRange getDefaultInstance() {
                return defaultInstance;
            }

            public ExtensionRange getDefaultInstanceForType() {
                return defaultInstance;
            }

            public final UnknownFieldSet getUnknownFields() {
                return this.unknownFields;
            }

            private ExtensionRange(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                this.memoizedIsInitialized = (byte) -1;
                this.memoizedSerializedSize = -1;
                initFields();
                com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
                boolean done = false;
                while (!done) {
                    try {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0:
                                done = true;
                                break;
                            case 8:
                                this.bitField0_ |= 1;
                                this.start_ = input.readInt32();
                                break;
                            case 16:
                                this.bitField0_ |= 2;
                                this.end_ = input.readInt32();
                                break;
                            default:
                                if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                    done = true;
                                    break;
                                }
                                break;
                        }
                    } catch (InvalidProtocolBufferException e) {
                        throw e.setUnfinishedMessage(this);
                    } catch (IOException e2) {
                        throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                    } catch (Throwable th) {
                        this.unknownFields = unknownFields.build();
                        makeExtensionsImmutable();
                    }
                }
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
            }

            public static final Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_DescriptorProto_ExtensionRange_descriptor;
            }

            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_DescriptorProto_ExtensionRange_fieldAccessorTable.ensureFieldAccessorsInitialized(ExtensionRange.class, Builder.class);
            }

            static {
                defaultInstance.initFields();
            }

            public Parser<ExtensionRange> getParserForType() {
                return PARSER;
            }

            public boolean hasStart() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getStart() {
                return this.start_;
            }

            public boolean hasEnd() {
                return (this.bitField0_ & 2) == 2;
            }

            public int getEnd() {
                return this.end_;
            }

            private void initFields() {
                this.start_ = 0;
                this.end_ = 0;
            }

            public final boolean isInitialized() {
                byte isInitialized = this.memoizedIsInitialized;
                if (isInitialized == (byte) 1) {
                    return true;
                }
                if (isInitialized == (byte) 0) {
                    return false;
                }
                this.memoizedIsInitialized = (byte) 1;
                return true;
            }

            public void writeTo(CodedOutputStream output) throws IOException {
                getSerializedSize();
                if ((this.bitField0_ & 1) == 1) {
                    output.writeInt32(1, this.start_);
                }
                if ((this.bitField0_ & 2) == 2) {
                    output.writeInt32(2, this.end_);
                }
                getUnknownFields().writeTo(output);
            }

            public int getSerializedSize() {
                int size = this.memoizedSerializedSize;
                if (size != -1) {
                    return size;
                }
                size = 0;
                if ((this.bitField0_ & 1) == 1) {
                    size = 0 + CodedOutputStream.computeInt32Size(1, this.start_);
                }
                if ((this.bitField0_ & 2) == 2) {
                    size += CodedOutputStream.computeInt32Size(2, this.end_);
                }
                size += getUnknownFields().getSerializedSize();
                this.memoizedSerializedSize = size;
                return size;
            }

            protected Object writeReplace() throws ObjectStreamException {
                return super.writeReplace();
            }

            public static ExtensionRange parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (ExtensionRange) PARSER.parseFrom(data);
            }

            public static ExtensionRange parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (ExtensionRange) PARSER.parseFrom(data, extensionRegistry);
            }

            public static ExtensionRange parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (ExtensionRange) PARSER.parseFrom(data);
            }

            public static ExtensionRange parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (ExtensionRange) PARSER.parseFrom(data, extensionRegistry);
            }

            public static ExtensionRange parseFrom(InputStream input) throws IOException {
                return (ExtensionRange) PARSER.parseFrom(input);
            }

            public static ExtensionRange parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (ExtensionRange) PARSER.parseFrom(input, extensionRegistry);
            }

            public static ExtensionRange parseDelimitedFrom(InputStream input) throws IOException {
                return (ExtensionRange) PARSER.parseDelimitedFrom(input);
            }

            public static ExtensionRange parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (ExtensionRange) PARSER.parseDelimitedFrom(input, extensionRegistry);
            }

            public static ExtensionRange parseFrom(CodedInputStream input) throws IOException {
                return (ExtensionRange) PARSER.parseFrom(input);
            }

            public static ExtensionRange parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (ExtensionRange) PARSER.parseFrom(input, extensionRegistry);
            }

            public static Builder newBuilder() {
                return Builder.create();
            }

            public Builder newBuilderForType() {
                return newBuilder();
            }

            public static Builder newBuilder(ExtensionRange prototype) {
                return newBuilder().mergeFrom(prototype);
            }

            public Builder toBuilder() {
                return newBuilder(this);
            }

            protected Builder newBuilderForType(BuilderParent parent) {
                return new Builder(parent);
            }
        }

        private DescriptorProto(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private DescriptorProto(boolean noInit) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static DescriptorProto getDefaultInstance() {
            return defaultInstance;
        }

        public DescriptorProto getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private DescriptorProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 10:
                            ByteString bs = input.readBytes();
                            this.bitField0_ |= 1;
                            this.name_ = bs;
                            break;
                        case 18:
                            if ((mutable_bitField0_ & 2) != 2) {
                                this.field_ = new ArrayList();
                                mutable_bitField0_ |= 2;
                            }
                            this.field_.add(input.readMessage(FieldDescriptorProto.PARSER, extensionRegistry));
                            break;
                        case 26:
                            if ((mutable_bitField0_ & 8) != 8) {
                                this.nestedType_ = new ArrayList();
                                mutable_bitField0_ |= 8;
                            }
                            this.nestedType_.add(input.readMessage(PARSER, extensionRegistry));
                            break;
                        case 34:
                            if ((mutable_bitField0_ & 16) != 16) {
                                this.enumType_ = new ArrayList();
                                mutable_bitField0_ |= 16;
                            }
                            this.enumType_.add(input.readMessage(EnumDescriptorProto.PARSER, extensionRegistry));
                            break;
                        case 42:
                            if ((mutable_bitField0_ & 32) != 32) {
                                this.extensionRange_ = new ArrayList();
                                mutable_bitField0_ |= 32;
                            }
                            this.extensionRange_.add(input.readMessage(ExtensionRange.PARSER, extensionRegistry));
                            break;
                        case 50:
                            if ((mutable_bitField0_ & 4) != 4) {
                                this.extension_ = new ArrayList();
                                mutable_bitField0_ |= 4;
                            }
                            this.extension_.add(input.readMessage(FieldDescriptorProto.PARSER, extensionRegistry));
                            break;
                        case 58:
                            Builder subBuilder = null;
                            if ((this.bitField0_ & 2) == 2) {
                                subBuilder = this.options_.toBuilder();
                            }
                            this.options_ = (MessageOptions) input.readMessage(MessageOptions.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.options_);
                                this.options_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 2;
                            break;
                        case 66:
                            if ((mutable_bitField0_ & 64) != 64) {
                                this.oneofDecl_ = new ArrayList();
                                mutable_bitField0_ |= 64;
                            }
                            this.oneofDecl_.add(input.readMessage(OneofDescriptorProto.PARSER, extensionRegistry));
                            break;
                        default:
                            if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                                break;
                            }
                            break;
                    }
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                } catch (Throwable th) {
                    if ((mutable_bitField0_ & 2) == 2) {
                        this.field_ = Collections.unmodifiableList(this.field_);
                    }
                    if ((mutable_bitField0_ & 8) == 8) {
                        this.nestedType_ = Collections.unmodifiableList(this.nestedType_);
                    }
                    if ((mutable_bitField0_ & 16) == 16) {
                        this.enumType_ = Collections.unmodifiableList(this.enumType_);
                    }
                    if ((mutable_bitField0_ & 32) == 32) {
                        this.extensionRange_ = Collections.unmodifiableList(this.extensionRange_);
                    }
                    if ((mutable_bitField0_ & 4) == 4) {
                        this.extension_ = Collections.unmodifiableList(this.extension_);
                    }
                    if ((mutable_bitField0_ & 64) == 64) {
                        this.oneofDecl_ = Collections.unmodifiableList(this.oneofDecl_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                }
            }
            if ((mutable_bitField0_ & 2) == 2) {
                this.field_ = Collections.unmodifiableList(this.field_);
            }
            if ((mutable_bitField0_ & 8) == 8) {
                this.nestedType_ = Collections.unmodifiableList(this.nestedType_);
            }
            if ((mutable_bitField0_ & 16) == 16) {
                this.enumType_ = Collections.unmodifiableList(this.enumType_);
            }
            if ((mutable_bitField0_ & 32) == 32) {
                this.extensionRange_ = Collections.unmodifiableList(this.extensionRange_);
            }
            if ((mutable_bitField0_ & 4) == 4) {
                this.extension_ = Collections.unmodifiableList(this.extension_);
            }
            if ((mutable_bitField0_ & 64) == 64) {
                this.oneofDecl_ = Collections.unmodifiableList(this.oneofDecl_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_DescriptorProto_descriptor;
        }

        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_DescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(DescriptorProto.class, Builder.class);
        }

        static {
            defaultInstance.initFields();
        }

        public Parser<DescriptorProto> getParserForType() {
            return PARSER;
        }

        public boolean hasName() {
            return (this.bitField0_ & 1) == 1;
        }

        public String getName() {
            ByteString ref = this.name_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.name_ = s;
            }
            return s;
        }

        public ByteString getNameBytes() {
            Object ref = this.name_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.name_ = b;
            return b;
        }

        public List<FieldDescriptorProto> getFieldList() {
            return this.field_;
        }

        public List<? extends FieldDescriptorProtoOrBuilder> getFieldOrBuilderList() {
            return this.field_;
        }

        public int getFieldCount() {
            return this.field_.size();
        }

        public FieldDescriptorProto getField(int index) {
            return (FieldDescriptorProto) this.field_.get(index);
        }

        public FieldDescriptorProtoOrBuilder getFieldOrBuilder(int index) {
            return (FieldDescriptorProtoOrBuilder) this.field_.get(index);
        }

        public List<FieldDescriptorProto> getExtensionList() {
            return this.extension_;
        }

        public List<? extends FieldDescriptorProtoOrBuilder> getExtensionOrBuilderList() {
            return this.extension_;
        }

        public int getExtensionCount() {
            return this.extension_.size();
        }

        public FieldDescriptorProto getExtension(int index) {
            return (FieldDescriptorProto) this.extension_.get(index);
        }

        public FieldDescriptorProtoOrBuilder getExtensionOrBuilder(int index) {
            return (FieldDescriptorProtoOrBuilder) this.extension_.get(index);
        }

        public List<DescriptorProto> getNestedTypeList() {
            return this.nestedType_;
        }

        public List<? extends DescriptorProtoOrBuilder> getNestedTypeOrBuilderList() {
            return this.nestedType_;
        }

        public int getNestedTypeCount() {
            return this.nestedType_.size();
        }

        public DescriptorProto getNestedType(int index) {
            return (DescriptorProto) this.nestedType_.get(index);
        }

        public DescriptorProtoOrBuilder getNestedTypeOrBuilder(int index) {
            return (DescriptorProtoOrBuilder) this.nestedType_.get(index);
        }

        public List<EnumDescriptorProto> getEnumTypeList() {
            return this.enumType_;
        }

        public List<? extends EnumDescriptorProtoOrBuilder> getEnumTypeOrBuilderList() {
            return this.enumType_;
        }

        public int getEnumTypeCount() {
            return this.enumType_.size();
        }

        public EnumDescriptorProto getEnumType(int index) {
            return (EnumDescriptorProto) this.enumType_.get(index);
        }

        public EnumDescriptorProtoOrBuilder getEnumTypeOrBuilder(int index) {
            return (EnumDescriptorProtoOrBuilder) this.enumType_.get(index);
        }

        public List<ExtensionRange> getExtensionRangeList() {
            return this.extensionRange_;
        }

        public List<? extends ExtensionRangeOrBuilder> getExtensionRangeOrBuilderList() {
            return this.extensionRange_;
        }

        public int getExtensionRangeCount() {
            return this.extensionRange_.size();
        }

        public ExtensionRange getExtensionRange(int index) {
            return (ExtensionRange) this.extensionRange_.get(index);
        }

        public ExtensionRangeOrBuilder getExtensionRangeOrBuilder(int index) {
            return (ExtensionRangeOrBuilder) this.extensionRange_.get(index);
        }

        public List<OneofDescriptorProto> getOneofDeclList() {
            return this.oneofDecl_;
        }

        public List<? extends OneofDescriptorProtoOrBuilder> getOneofDeclOrBuilderList() {
            return this.oneofDecl_;
        }

        public int getOneofDeclCount() {
            return this.oneofDecl_.size();
        }

        public OneofDescriptorProto getOneofDecl(int index) {
            return (OneofDescriptorProto) this.oneofDecl_.get(index);
        }

        public OneofDescriptorProtoOrBuilder getOneofDeclOrBuilder(int index) {
            return (OneofDescriptorProtoOrBuilder) this.oneofDecl_.get(index);
        }

        public boolean hasOptions() {
            return (this.bitField0_ & 2) == 2;
        }

        public MessageOptions getOptions() {
            return this.options_;
        }

        public MessageOptionsOrBuilder getOptionsOrBuilder() {
            return this.options_;
        }

        private void initFields() {
            this.name_ = "";
            this.field_ = Collections.emptyList();
            this.extension_ = Collections.emptyList();
            this.nestedType_ = Collections.emptyList();
            this.enumType_ = Collections.emptyList();
            this.extensionRange_ = Collections.emptyList();
            this.oneofDecl_ = Collections.emptyList();
            this.options_ = MessageOptions.getDefaultInstance();
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == (byte) 1) {
                return true;
            }
            if (isInitialized == (byte) 0) {
                return false;
            }
            int i = 0;
            while (i < getFieldCount()) {
                if (getField(i).isInitialized()) {
                    i++;
                } else {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            i = 0;
            while (i < getExtensionCount()) {
                if (getExtension(i).isInitialized()) {
                    i++;
                } else {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            i = 0;
            while (i < getNestedTypeCount()) {
                if (getNestedType(i).isInitialized()) {
                    i++;
                } else {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            i = 0;
            while (i < getEnumTypeCount()) {
                if (getEnumType(i).isInitialized()) {
                    i++;
                } else {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (!hasOptions() || getOptions().isInitialized()) {
                this.memoizedIsInitialized = (byte) 1;
                return true;
            }
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            int i;
            getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeBytes(1, getNameBytes());
            }
            for (i = 0; i < this.field_.size(); i++) {
                output.writeMessage(2, (MessageLite) this.field_.get(i));
            }
            for (i = 0; i < this.nestedType_.size(); i++) {
                output.writeMessage(3, (MessageLite) this.nestedType_.get(i));
            }
            for (i = 0; i < this.enumType_.size(); i++) {
                output.writeMessage(4, (MessageLite) this.enumType_.get(i));
            }
            for (i = 0; i < this.extensionRange_.size(); i++) {
                output.writeMessage(5, (MessageLite) this.extensionRange_.get(i));
            }
            for (i = 0; i < this.extension_.size(); i++) {
                output.writeMessage(6, (MessageLite) this.extension_.get(i));
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeMessage(7, this.options_);
            }
            for (i = 0; i < this.oneofDecl_.size(); i++) {
                output.writeMessage(8, (MessageLite) this.oneofDecl_.get(i));
            }
            getUnknownFields().writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            int i;
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
                size = 0 + CodedOutputStream.computeBytesSize(1, getNameBytes());
            }
            for (i = 0; i < this.field_.size(); i++) {
                size += CodedOutputStream.computeMessageSize(2, (MessageLite) this.field_.get(i));
            }
            for (i = 0; i < this.nestedType_.size(); i++) {
                size += CodedOutputStream.computeMessageSize(3, (MessageLite) this.nestedType_.get(i));
            }
            for (i = 0; i < this.enumType_.size(); i++) {
                size += CodedOutputStream.computeMessageSize(4, (MessageLite) this.enumType_.get(i));
            }
            for (i = 0; i < this.extensionRange_.size(); i++) {
                size += CodedOutputStream.computeMessageSize(5, (MessageLite) this.extensionRange_.get(i));
            }
            for (i = 0; i < this.extension_.size(); i++) {
                size += CodedOutputStream.computeMessageSize(6, (MessageLite) this.extension_.get(i));
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeMessageSize(7, this.options_);
            }
            for (i = 0; i < this.oneofDecl_.size(); i++) {
                size += CodedOutputStream.computeMessageSize(8, (MessageLite) this.oneofDecl_.get(i));
            }
            size += getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = size;
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static DescriptorProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (DescriptorProto) PARSER.parseFrom(data);
        }

        public static DescriptorProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DescriptorProto) PARSER.parseFrom(data, extensionRegistry);
        }

        public static DescriptorProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (DescriptorProto) PARSER.parseFrom(data);
        }

        public static DescriptorProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DescriptorProto) PARSER.parseFrom(data, extensionRegistry);
        }

        public static DescriptorProto parseFrom(InputStream input) throws IOException {
            return (DescriptorProto) PARSER.parseFrom(input);
        }

        public static DescriptorProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DescriptorProto) PARSER.parseFrom(input, extensionRegistry);
        }

        public static DescriptorProto parseDelimitedFrom(InputStream input) throws IOException {
            return (DescriptorProto) PARSER.parseDelimitedFrom(input);
        }

        public static DescriptorProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DescriptorProto) PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static DescriptorProto parseFrom(CodedInputStream input) throws IOException {
            return (DescriptorProto) PARSER.parseFrom(input);
        }

        public static DescriptorProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DescriptorProto) PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(DescriptorProto prototype) {
            return newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return newBuilder(this);
        }

        protected Builder newBuilderForType(BuilderParent parent) {
            return new Builder(parent);
        }
    }

    public interface EnumDescriptorProtoOrBuilder extends MessageOrBuilder {
        String getName();

        ByteString getNameBytes();

        EnumOptions getOptions();

        EnumOptionsOrBuilder getOptionsOrBuilder();

        EnumValueDescriptorProto getValue(int i);

        int getValueCount();

        List<EnumValueDescriptorProto> getValueList();

        EnumValueDescriptorProtoOrBuilder getValueOrBuilder(int i);

        List<? extends EnumValueDescriptorProtoOrBuilder> getValueOrBuilderList();

        boolean hasName();

        boolean hasOptions();
    }

    public static final class EnumDescriptorProto extends GeneratedMessage implements EnumDescriptorProtoOrBuilder {
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int OPTIONS_FIELD_NUMBER = 3;
        public static Parser<EnumDescriptorProto> PARSER = new AbstractParser<EnumDescriptorProto>() {
            public EnumDescriptorProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new EnumDescriptorProto(input, extensionRegistry);
            }
        };
        public static final int VALUE_FIELD_NUMBER = 2;
        private static final EnumDescriptorProto defaultInstance = new EnumDescriptorProto(true);
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private Object name_;
        private EnumOptions options_;
        private final UnknownFieldSet unknownFields;
        private List<EnumValueDescriptorProto> value_;

        public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> implements EnumDescriptorProtoOrBuilder {
            private int bitField0_;
            private Object name_;
            private SingleFieldBuilder<EnumOptions, Builder, EnumOptionsOrBuilder> optionsBuilder_;
            private EnumOptions options_;
            private RepeatedFieldBuilder<EnumValueDescriptorProto, Builder, EnumValueDescriptorProtoOrBuilder> valueBuilder_;
            private List<EnumValueDescriptorProto> value_;

            public static final Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_EnumDescriptorProto_descriptor;
            }

            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_EnumDescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(EnumDescriptorProto.class, Builder.class);
            }

            private Builder() {
                this.name_ = "";
                this.value_ = Collections.emptyList();
                this.options_ = EnumOptions.getDefaultInstance();
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.name_ = "";
                this.value_ = Collections.emptyList();
                this.options_ = EnumOptions.getDefaultInstance();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getValueFieldBuilder();
                    getOptionsFieldBuilder();
                }
            }

            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                this.name_ = "";
                this.bitField0_ &= -2;
                if (this.valueBuilder_ == null) {
                    this.value_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                } else {
                    this.valueBuilder_.clear();
                }
                if (this.optionsBuilder_ == null) {
                    this.options_ = EnumOptions.getDefaultInstance();
                } else {
                    this.optionsBuilder_.clear();
                }
                this.bitField0_ &= -5;
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_EnumDescriptorProto_descriptor;
            }

            public EnumDescriptorProto getDefaultInstanceForType() {
                return EnumDescriptorProto.getDefaultInstance();
            }

            public EnumDescriptorProto build() {
                EnumDescriptorProto result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw com.google.protobuf.AbstractMessage.Builder.newUninitializedMessageException(result);
            }

            public EnumDescriptorProto buildPartial() {
                EnumDescriptorProto result = new EnumDescriptorProto((com.google.protobuf.GeneratedMessage.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.name_ = this.name_;
                if (this.valueBuilder_ == null) {
                    if ((this.bitField0_ & 2) == 2) {
                        this.value_ = Collections.unmodifiableList(this.value_);
                        this.bitField0_ &= -3;
                    }
                    result.value_ = this.value_;
                } else {
                    result.value_ = this.valueBuilder_.build();
                }
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 2;
                }
                if (this.optionsBuilder_ == null) {
                    result.options_ = this.options_;
                } else {
                    result.options_ = (EnumOptions) this.optionsBuilder_.build();
                }
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof EnumDescriptorProto) {
                    return mergeFrom((EnumDescriptorProto) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(EnumDescriptorProto other) {
                RepeatedFieldBuilder repeatedFieldBuilder = null;
                if (other != EnumDescriptorProto.getDefaultInstance()) {
                    if (other.hasName()) {
                        this.bitField0_ |= 1;
                        this.name_ = other.name_;
                        onChanged();
                    }
                    if (this.valueBuilder_ == null) {
                        if (!other.value_.isEmpty()) {
                            if (this.value_.isEmpty()) {
                                this.value_ = other.value_;
                                this.bitField0_ &= -3;
                            } else {
                                ensureValueIsMutable();
                                this.value_.addAll(other.value_);
                            }
                            onChanged();
                        }
                    } else if (!other.value_.isEmpty()) {
                        if (this.valueBuilder_.isEmpty()) {
                            this.valueBuilder_.dispose();
                            this.valueBuilder_ = null;
                            this.value_ = other.value_;
                            this.bitField0_ &= -3;
                            if (GeneratedMessage.alwaysUseFieldBuilders) {
                                repeatedFieldBuilder = getValueFieldBuilder();
                            }
                            this.valueBuilder_ = repeatedFieldBuilder;
                        } else {
                            this.valueBuilder_.addAllMessages(other.value_);
                        }
                    }
                    if (other.hasOptions()) {
                        mergeOptions(other.getOptions());
                    }
                    mergeUnknownFields(other.getUnknownFields());
                }
                return this;
            }

            public final boolean isInitialized() {
                for (int i = 0; i < getValueCount(); i++) {
                    if (!getValue(i).isInitialized()) {
                        return false;
                    }
                }
                if (!hasOptions() || getOptions().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                EnumDescriptorProto parsedMessage = null;
                try {
                    parsedMessage = (EnumDescriptorProto) EnumDescriptorProto.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    parsedMessage = (EnumDescriptorProto) e.getUnfinishedMessage();
                    throw e;
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
            }

            public boolean hasName() {
                return (this.bitField0_ & 1) == 1;
            }

            public String getName() {
                ByteString ref = this.name_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.name_ = s;
                return s;
            }

            public ByteString getNameBytes() {
                Object ref = this.name_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.name_ = b;
                return b;
            }

            public Builder setName(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.name_ = value;
                onChanged();
                return this;
            }

            public Builder clearName() {
                this.bitField0_ &= -2;
                this.name_ = EnumDescriptorProto.getDefaultInstance().getName();
                onChanged();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.name_ = value;
                onChanged();
                return this;
            }

            private void ensureValueIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.value_ = new ArrayList(this.value_);
                    this.bitField0_ |= 2;
                }
            }

            public List<EnumValueDescriptorProto> getValueList() {
                if (this.valueBuilder_ == null) {
                    return Collections.unmodifiableList(this.value_);
                }
                return this.valueBuilder_.getMessageList();
            }

            public int getValueCount() {
                if (this.valueBuilder_ == null) {
                    return this.value_.size();
                }
                return this.valueBuilder_.getCount();
            }

            public EnumValueDescriptorProto getValue(int index) {
                if (this.valueBuilder_ == null) {
                    return (EnumValueDescriptorProto) this.value_.get(index);
                }
                return (EnumValueDescriptorProto) this.valueBuilder_.getMessage(index);
            }

            public Builder setValue(int index, EnumValueDescriptorProto value) {
                if (this.valueBuilder_ != null) {
                    this.valueBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureValueIsMutable();
                    this.value_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setValue(int index, Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    ensureValueIsMutable();
                    this.value_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.valueBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addValue(EnumValueDescriptorProto value) {
                if (this.valueBuilder_ != null) {
                    this.valueBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureValueIsMutable();
                    this.value_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addValue(int index, EnumValueDescriptorProto value) {
                if (this.valueBuilder_ != null) {
                    this.valueBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureValueIsMutable();
                    this.value_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addValue(Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    ensureValueIsMutable();
                    this.value_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.valueBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addValue(int index, Builder builderForValue) {
                if (this.valueBuilder_ == null) {
                    ensureValueIsMutable();
                    this.value_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.valueBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllValue(Iterable<? extends EnumValueDescriptorProto> values) {
                if (this.valueBuilder_ == null) {
                    ensureValueIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.value_);
                    onChanged();
                } else {
                    this.valueBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearValue() {
                if (this.valueBuilder_ == null) {
                    this.value_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                    onChanged();
                } else {
                    this.valueBuilder_.clear();
                }
                return this;
            }

            public Builder removeValue(int index) {
                if (this.valueBuilder_ == null) {
                    ensureValueIsMutable();
                    this.value_.remove(index);
                    onChanged();
                } else {
                    this.valueBuilder_.remove(index);
                }
                return this;
            }

            public Builder getValueBuilder(int index) {
                return (Builder) getValueFieldBuilder().getBuilder(index);
            }

            public EnumValueDescriptorProtoOrBuilder getValueOrBuilder(int index) {
                if (this.valueBuilder_ == null) {
                    return (EnumValueDescriptorProtoOrBuilder) this.value_.get(index);
                }
                return (EnumValueDescriptorProtoOrBuilder) this.valueBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends EnumValueDescriptorProtoOrBuilder> getValueOrBuilderList() {
                if (this.valueBuilder_ != null) {
                    return this.valueBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.value_);
            }

            public Builder addValueBuilder() {
                return (Builder) getValueFieldBuilder().addBuilder(EnumValueDescriptorProto.getDefaultInstance());
            }

            public Builder addValueBuilder(int index) {
                return (Builder) getValueFieldBuilder().addBuilder(index, EnumValueDescriptorProto.getDefaultInstance());
            }

            public List<Builder> getValueBuilderList() {
                return getValueFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<EnumValueDescriptorProto, Builder, EnumValueDescriptorProtoOrBuilder> getValueFieldBuilder() {
                if (this.valueBuilder_ == null) {
                    this.valueBuilder_ = new RepeatedFieldBuilder(this.value_, (this.bitField0_ & 2) == 2, getParentForChildren(), isClean());
                    this.value_ = null;
                }
                return this.valueBuilder_;
            }

            public boolean hasOptions() {
                return (this.bitField0_ & 4) == 4;
            }

            public EnumOptions getOptions() {
                if (this.optionsBuilder_ == null) {
                    return this.options_;
                }
                return (EnumOptions) this.optionsBuilder_.getMessage();
            }

            public Builder setOptions(EnumOptions value) {
                if (this.optionsBuilder_ != null) {
                    this.optionsBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.options_ = value;
                    onChanged();
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder setOptions(Builder builderForValue) {
                if (this.optionsBuilder_ == null) {
                    this.options_ = builderForValue.build();
                    onChanged();
                } else {
                    this.optionsBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder mergeOptions(EnumOptions value) {
                if (this.optionsBuilder_ == null) {
                    if ((this.bitField0_ & 4) != 4 || this.options_ == EnumOptions.getDefaultInstance()) {
                        this.options_ = value;
                    } else {
                        this.options_ = EnumOptions.newBuilder(this.options_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.optionsBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder clearOptions() {
                if (this.optionsBuilder_ == null) {
                    this.options_ = EnumOptions.getDefaultInstance();
                    onChanged();
                } else {
                    this.optionsBuilder_.clear();
                }
                this.bitField0_ &= -5;
                return this;
            }

            public Builder getOptionsBuilder() {
                this.bitField0_ |= 4;
                onChanged();
                return (Builder) getOptionsFieldBuilder().getBuilder();
            }

            public EnumOptionsOrBuilder getOptionsOrBuilder() {
                if (this.optionsBuilder_ != null) {
                    return (EnumOptionsOrBuilder) this.optionsBuilder_.getMessageOrBuilder();
                }
                return this.options_;
            }

            private SingleFieldBuilder<EnumOptions, Builder, EnumOptionsOrBuilder> getOptionsFieldBuilder() {
                if (this.optionsBuilder_ == null) {
                    this.optionsBuilder_ = new SingleFieldBuilder(getOptions(), getParentForChildren(), isClean());
                    this.options_ = null;
                }
                return this.optionsBuilder_;
            }
        }

        private EnumDescriptorProto(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private EnumDescriptorProto(boolean noInit) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static EnumDescriptorProto getDefaultInstance() {
            return defaultInstance;
        }

        public EnumDescriptorProto getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private EnumDescriptorProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 10:
                            ByteString bs = input.readBytes();
                            this.bitField0_ |= 1;
                            this.name_ = bs;
                            break;
                        case 18:
                            if ((mutable_bitField0_ & 2) != 2) {
                                this.value_ = new ArrayList();
                                mutable_bitField0_ |= 2;
                            }
                            this.value_.add(input.readMessage(EnumValueDescriptorProto.PARSER, extensionRegistry));
                            break;
                        case 26:
                            Builder subBuilder = null;
                            if ((this.bitField0_ & 2) == 2) {
                                subBuilder = this.options_.toBuilder();
                            }
                            this.options_ = (EnumOptions) input.readMessage(EnumOptions.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.options_);
                                this.options_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 2;
                            break;
                        default:
                            if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                                break;
                            }
                            break;
                    }
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                } catch (Throwable th) {
                    if ((mutable_bitField0_ & 2) == 2) {
                        this.value_ = Collections.unmodifiableList(this.value_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                }
            }
            if ((mutable_bitField0_ & 2) == 2) {
                this.value_ = Collections.unmodifiableList(this.value_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_EnumDescriptorProto_descriptor;
        }

        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_EnumDescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(EnumDescriptorProto.class, Builder.class);
        }

        static {
            defaultInstance.initFields();
        }

        public Parser<EnumDescriptorProto> getParserForType() {
            return PARSER;
        }

        public boolean hasName() {
            return (this.bitField0_ & 1) == 1;
        }

        public String getName() {
            ByteString ref = this.name_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.name_ = s;
            }
            return s;
        }

        public ByteString getNameBytes() {
            Object ref = this.name_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.name_ = b;
            return b;
        }

        public List<EnumValueDescriptorProto> getValueList() {
            return this.value_;
        }

        public List<? extends EnumValueDescriptorProtoOrBuilder> getValueOrBuilderList() {
            return this.value_;
        }

        public int getValueCount() {
            return this.value_.size();
        }

        public EnumValueDescriptorProto getValue(int index) {
            return (EnumValueDescriptorProto) this.value_.get(index);
        }

        public EnumValueDescriptorProtoOrBuilder getValueOrBuilder(int index) {
            return (EnumValueDescriptorProtoOrBuilder) this.value_.get(index);
        }

        public boolean hasOptions() {
            return (this.bitField0_ & 2) == 2;
        }

        public EnumOptions getOptions() {
            return this.options_;
        }

        public EnumOptionsOrBuilder getOptionsOrBuilder() {
            return this.options_;
        }

        private void initFields() {
            this.name_ = "";
            this.value_ = Collections.emptyList();
            this.options_ = EnumOptions.getDefaultInstance();
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == (byte) 1) {
                return true;
            }
            if (isInitialized == (byte) 0) {
                return false;
            }
            int i = 0;
            while (i < getValueCount()) {
                if (getValue(i).isInitialized()) {
                    i++;
                } else {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (!hasOptions() || getOptions().isInitialized()) {
                this.memoizedIsInitialized = (byte) 1;
                return true;
            }
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeBytes(1, getNameBytes());
            }
            for (int i = 0; i < this.value_.size(); i++) {
                output.writeMessage(2, (MessageLite) this.value_.get(i));
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeMessage(3, this.options_);
            }
            getUnknownFields().writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
                size = 0 + CodedOutputStream.computeBytesSize(1, getNameBytes());
            }
            for (int i = 0; i < this.value_.size(); i++) {
                size += CodedOutputStream.computeMessageSize(2, (MessageLite) this.value_.get(i));
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeMessageSize(3, this.options_);
            }
            size += getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = size;
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static EnumDescriptorProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (EnumDescriptorProto) PARSER.parseFrom(data);
        }

        public static EnumDescriptorProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumDescriptorProto) PARSER.parseFrom(data, extensionRegistry);
        }

        public static EnumDescriptorProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (EnumDescriptorProto) PARSER.parseFrom(data);
        }

        public static EnumDescriptorProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumDescriptorProto) PARSER.parseFrom(data, extensionRegistry);
        }

        public static EnumDescriptorProto parseFrom(InputStream input) throws IOException {
            return (EnumDescriptorProto) PARSER.parseFrom(input);
        }

        public static EnumDescriptorProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumDescriptorProto) PARSER.parseFrom(input, extensionRegistry);
        }

        public static EnumDescriptorProto parseDelimitedFrom(InputStream input) throws IOException {
            return (EnumDescriptorProto) PARSER.parseDelimitedFrom(input);
        }

        public static EnumDescriptorProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumDescriptorProto) PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static EnumDescriptorProto parseFrom(CodedInputStream input) throws IOException {
            return (EnumDescriptorProto) PARSER.parseFrom(input);
        }

        public static EnumDescriptorProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumDescriptorProto) PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(EnumDescriptorProto prototype) {
            return newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return newBuilder(this);
        }

        protected Builder newBuilderForType(BuilderParent parent) {
            return new Builder(parent);
        }
    }

    public interface EnumOptionsOrBuilder extends ExtendableMessageOrBuilder<EnumOptions> {
        boolean getAllowAlias();

        boolean getDeprecated();

        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();

        UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int i);

        List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList();

        boolean hasAllowAlias();

        boolean hasDeprecated();
    }

    public static final class EnumOptions extends ExtendableMessage<EnumOptions> implements EnumOptionsOrBuilder {
        public static final int ALLOW_ALIAS_FIELD_NUMBER = 2;
        public static final int DEPRECATED_FIELD_NUMBER = 3;
        public static Parser<EnumOptions> PARSER = new AbstractParser<EnumOptions>() {
            public EnumOptions parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new EnumOptions(input, extensionRegistry);
            }
        };
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        private static final EnumOptions defaultInstance = new EnumOptions(true);
        private static final long serialVersionUID = 0;
        private boolean allowAlias_;
        private int bitField0_;
        private boolean deprecated_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private List<UninterpretedOption> uninterpretedOption_;
        private final UnknownFieldSet unknownFields;

        public static final class Builder extends ExtendableBuilder<EnumOptions, Builder> implements EnumOptionsOrBuilder {
            private boolean allowAlias_;
            private int bitField0_;
            private boolean deprecated_;
            private RepeatedFieldBuilder<UninterpretedOption, Builder, UninterpretedOptionOrBuilder> uninterpretedOptionBuilder_;
            private List<UninterpretedOption> uninterpretedOption_;

            public static final Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_EnumOptions_descriptor;
            }

            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_EnumOptions_fieldAccessorTable.ensureFieldAccessorsInitialized(EnumOptions.class, Builder.class);
            }

            private Builder() {
                this.uninterpretedOption_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.uninterpretedOption_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getUninterpretedOptionFieldBuilder();
                }
            }

            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                this.allowAlias_ = false;
                this.bitField0_ &= -2;
                this.deprecated_ = false;
                this.bitField0_ &= -3;
                if (this.uninterpretedOptionBuilder_ == null) {
                    this.uninterpretedOption_ = Collections.emptyList();
                    this.bitField0_ &= -5;
                } else {
                    this.uninterpretedOptionBuilder_.clear();
                }
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_EnumOptions_descriptor;
            }

            public EnumOptions getDefaultInstanceForType() {
                return EnumOptions.getDefaultInstance();
            }

            public EnumOptions build() {
                EnumOptions result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw com.google.protobuf.AbstractMessage.Builder.newUninitializedMessageException(result);
            }

            public EnumOptions buildPartial() {
                EnumOptions result = new EnumOptions((ExtendableBuilder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.allowAlias_ = this.allowAlias_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.deprecated_ = this.deprecated_;
                if (this.uninterpretedOptionBuilder_ == null) {
                    if ((this.bitField0_ & 4) == 4) {
                        this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
                        this.bitField0_ &= -5;
                    }
                    result.uninterpretedOption_ = this.uninterpretedOption_;
                } else {
                    result.uninterpretedOption_ = this.uninterpretedOptionBuilder_.build();
                }
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof EnumOptions) {
                    return mergeFrom((EnumOptions) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(EnumOptions other) {
                RepeatedFieldBuilder repeatedFieldBuilder = null;
                if (other != EnumOptions.getDefaultInstance()) {
                    if (other.hasAllowAlias()) {
                        setAllowAlias(other.getAllowAlias());
                    }
                    if (other.hasDeprecated()) {
                        setDeprecated(other.getDeprecated());
                    }
                    if (this.uninterpretedOptionBuilder_ == null) {
                        if (!other.uninterpretedOption_.isEmpty()) {
                            if (this.uninterpretedOption_.isEmpty()) {
                                this.uninterpretedOption_ = other.uninterpretedOption_;
                                this.bitField0_ &= -5;
                            } else {
                                ensureUninterpretedOptionIsMutable();
                                this.uninterpretedOption_.addAll(other.uninterpretedOption_);
                            }
                            onChanged();
                        }
                    } else if (!other.uninterpretedOption_.isEmpty()) {
                        if (this.uninterpretedOptionBuilder_.isEmpty()) {
                            this.uninterpretedOptionBuilder_.dispose();
                            this.uninterpretedOptionBuilder_ = null;
                            this.uninterpretedOption_ = other.uninterpretedOption_;
                            this.bitField0_ &= -5;
                            if (GeneratedMessage.alwaysUseFieldBuilders) {
                                repeatedFieldBuilder = getUninterpretedOptionFieldBuilder();
                            }
                            this.uninterpretedOptionBuilder_ = repeatedFieldBuilder;
                        } else {
                            this.uninterpretedOptionBuilder_.addAllMessages(other.uninterpretedOption_);
                        }
                    }
                    mergeExtensionFields(other);
                    mergeUnknownFields(other.getUnknownFields());
                }
                return this;
            }

            public final boolean isInitialized() {
                for (int i = 0; i < getUninterpretedOptionCount(); i++) {
                    if (!getUninterpretedOption(i).isInitialized()) {
                        return false;
                    }
                }
                if (extensionsAreInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                EnumOptions parsedMessage = null;
                try {
                    parsedMessage = (EnumOptions) EnumOptions.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    parsedMessage = (EnumOptions) e.getUnfinishedMessage();
                    throw e;
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
            }

            public boolean hasAllowAlias() {
                return (this.bitField0_ & 1) == 1;
            }

            public boolean getAllowAlias() {
                return this.allowAlias_;
            }

            public Builder setAllowAlias(boolean value) {
                this.bitField0_ |= 1;
                this.allowAlias_ = value;
                onChanged();
                return this;
            }

            public Builder clearAllowAlias() {
                this.bitField0_ &= -2;
                this.allowAlias_ = false;
                onChanged();
                return this;
            }

            public boolean hasDeprecated() {
                return (this.bitField0_ & 2) == 2;
            }

            public boolean getDeprecated() {
                return this.deprecated_;
            }

            public Builder setDeprecated(boolean value) {
                this.bitField0_ |= 2;
                this.deprecated_ = value;
                onChanged();
                return this;
            }

            public Builder clearDeprecated() {
                this.bitField0_ &= -3;
                this.deprecated_ = false;
                onChanged();
                return this;
            }

            private void ensureUninterpretedOptionIsMutable() {
                if ((this.bitField0_ & 4) != 4) {
                    this.uninterpretedOption_ = new ArrayList(this.uninterpretedOption_);
                    this.bitField0_ |= 4;
                }
            }

            public List<UninterpretedOption> getUninterpretedOptionList() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    return Collections.unmodifiableList(this.uninterpretedOption_);
                }
                return this.uninterpretedOptionBuilder_.getMessageList();
            }

            public int getUninterpretedOptionCount() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    return this.uninterpretedOption_.size();
                }
                return this.uninterpretedOptionBuilder_.getCount();
            }

            public UninterpretedOption getUninterpretedOption(int index) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    return (UninterpretedOption) this.uninterpretedOption_.get(index);
                }
                return (UninterpretedOption) this.uninterpretedOptionBuilder_.getMessage(index);
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption value) {
                if (this.uninterpretedOptionBuilder_ != null) {
                    this.uninterpretedOptionBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setUninterpretedOption(int index, Builder builderForValue) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption value) {
                if (this.uninterpretedOptionBuilder_ != null) {
                    this.uninterpretedOptionBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption value) {
                if (this.uninterpretedOptionBuilder_ != null) {
                    this.uninterpretedOptionBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addUninterpretedOption(Builder builderForValue) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addUninterpretedOption(int index, Builder builderForValue) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.uninterpretedOption_);
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearUninterpretedOption() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    this.uninterpretedOption_ = Collections.emptyList();
                    this.bitField0_ &= -5;
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.clear();
                }
                return this;
            }

            public Builder removeUninterpretedOption(int index) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.remove(index);
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.remove(index);
                }
                return this;
            }

            public Builder getUninterpretedOptionBuilder(int index) {
                return (Builder) getUninterpretedOptionFieldBuilder().getBuilder(index);
            }

            public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    return (UninterpretedOptionOrBuilder) this.uninterpretedOption_.get(index);
                }
                return (UninterpretedOptionOrBuilder) this.uninterpretedOptionBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
                if (this.uninterpretedOptionBuilder_ != null) {
                    return this.uninterpretedOptionBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.uninterpretedOption_);
            }

            public Builder addUninterpretedOptionBuilder() {
                return (Builder) getUninterpretedOptionFieldBuilder().addBuilder(UninterpretedOption.getDefaultInstance());
            }

            public Builder addUninterpretedOptionBuilder(int index) {
                return (Builder) getUninterpretedOptionFieldBuilder().addBuilder(index, UninterpretedOption.getDefaultInstance());
            }

            public List<Builder> getUninterpretedOptionBuilderList() {
                return getUninterpretedOptionFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<UninterpretedOption, Builder, UninterpretedOptionOrBuilder> getUninterpretedOptionFieldBuilder() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    this.uninterpretedOptionBuilder_ = new RepeatedFieldBuilder(this.uninterpretedOption_, (this.bitField0_ & 4) == 4, getParentForChildren(), isClean());
                    this.uninterpretedOption_ = null;
                }
                return this.uninterpretedOptionBuilder_;
            }
        }

        private EnumOptions(ExtendableBuilder<EnumOptions, ?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private EnumOptions(boolean noInit) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static EnumOptions getDefaultInstance() {
            return defaultInstance;
        }

        public EnumOptions getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private EnumOptions(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 16:
                            this.bitField0_ |= 1;
                            this.allowAlias_ = input.readBool();
                            break;
                        case 24:
                            this.bitField0_ |= 2;
                            this.deprecated_ = input.readBool();
                            break;
                        case 7994:
                            if ((mutable_bitField0_ & 4) != 4) {
                                this.uninterpretedOption_ = new ArrayList();
                                mutable_bitField0_ |= 4;
                            }
                            this.uninterpretedOption_.add(input.readMessage(UninterpretedOption.PARSER, extensionRegistry));
                            break;
                        default:
                            if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                                break;
                            }
                            break;
                    }
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                } catch (Throwable th) {
                    if ((mutable_bitField0_ & 4) == 4) {
                        this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                }
            }
            if ((mutable_bitField0_ & 4) == 4) {
                this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_EnumOptions_descriptor;
        }

        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_EnumOptions_fieldAccessorTable.ensureFieldAccessorsInitialized(EnumOptions.class, Builder.class);
        }

        static {
            defaultInstance.initFields();
        }

        public Parser<EnumOptions> getParserForType() {
            return PARSER;
        }

        public boolean hasAllowAlias() {
            return (this.bitField0_ & 1) == 1;
        }

        public boolean getAllowAlias() {
            return this.allowAlias_;
        }

        public boolean hasDeprecated() {
            return (this.bitField0_ & 2) == 2;
        }

        public boolean getDeprecated() {
            return this.deprecated_;
        }

        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        public UninterpretedOption getUninterpretedOption(int index) {
            return (UninterpretedOption) this.uninterpretedOption_.get(index);
        }

        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
            return (UninterpretedOptionOrBuilder) this.uninterpretedOption_.get(index);
        }

        private void initFields() {
            this.allowAlias_ = false;
            this.deprecated_ = false;
            this.uninterpretedOption_ = Collections.emptyList();
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == (byte) 1) {
                return true;
            }
            if (isInitialized == (byte) 0) {
                return false;
            }
            int i = 0;
            while (i < getUninterpretedOptionCount()) {
                if (getUninterpretedOption(i).isInitialized()) {
                    i++;
                } else {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (extensionsAreInitialized()) {
                this.memoizedIsInitialized = (byte) 1;
                return true;
            }
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            getSerializedSize();
            ExtensionWriter extensionWriter = newExtensionWriter();
            if ((this.bitField0_ & 1) == 1) {
                output.writeBool(2, this.allowAlias_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeBool(3, this.deprecated_);
            }
            for (int i = 0; i < this.uninterpretedOption_.size(); i++) {
                output.writeMessage(999, (MessageLite) this.uninterpretedOption_.get(i));
            }
            extensionWriter.writeUntil(536870912, output);
            getUnknownFields().writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
                size = 0 + CodedOutputStream.computeBoolSize(2, this.allowAlias_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeBoolSize(3, this.deprecated_);
            }
            for (int i = 0; i < this.uninterpretedOption_.size(); i++) {
                size += CodedOutputStream.computeMessageSize(999, (MessageLite) this.uninterpretedOption_.get(i));
            }
            size = (size + extensionsSerializedSize()) + getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = size;
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static EnumOptions parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (EnumOptions) PARSER.parseFrom(data);
        }

        public static EnumOptions parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumOptions) PARSER.parseFrom(data, extensionRegistry);
        }

        public static EnumOptions parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (EnumOptions) PARSER.parseFrom(data);
        }

        public static EnumOptions parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumOptions) PARSER.parseFrom(data, extensionRegistry);
        }

        public static EnumOptions parseFrom(InputStream input) throws IOException {
            return (EnumOptions) PARSER.parseFrom(input);
        }

        public static EnumOptions parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumOptions) PARSER.parseFrom(input, extensionRegistry);
        }

        public static EnumOptions parseDelimitedFrom(InputStream input) throws IOException {
            return (EnumOptions) PARSER.parseDelimitedFrom(input);
        }

        public static EnumOptions parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumOptions) PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static EnumOptions parseFrom(CodedInputStream input) throws IOException {
            return (EnumOptions) PARSER.parseFrom(input);
        }

        public static EnumOptions parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumOptions) PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(EnumOptions prototype) {
            return newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return newBuilder(this);
        }

        protected Builder newBuilderForType(BuilderParent parent) {
            return new Builder(parent);
        }
    }

    public interface EnumValueDescriptorProtoOrBuilder extends MessageOrBuilder {
        String getName();

        ByteString getNameBytes();

        int getNumber();

        EnumValueOptions getOptions();

        EnumValueOptionsOrBuilder getOptionsOrBuilder();

        boolean hasName();

        boolean hasNumber();

        boolean hasOptions();
    }

    public static final class EnumValueDescriptorProto extends GeneratedMessage implements EnumValueDescriptorProtoOrBuilder {
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int NUMBER_FIELD_NUMBER = 2;
        public static final int OPTIONS_FIELD_NUMBER = 3;
        public static Parser<EnumValueDescriptorProto> PARSER = new AbstractParser<EnumValueDescriptorProto>() {
            public EnumValueDescriptorProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new EnumValueDescriptorProto(input, extensionRegistry);
            }
        };
        private static final EnumValueDescriptorProto defaultInstance = new EnumValueDescriptorProto(true);
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private Object name_;
        private int number_;
        private EnumValueOptions options_;
        private final UnknownFieldSet unknownFields;

        public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> implements EnumValueDescriptorProtoOrBuilder {
            private int bitField0_;
            private Object name_;
            private int number_;
            private SingleFieldBuilder<EnumValueOptions, Builder, EnumValueOptionsOrBuilder> optionsBuilder_;
            private EnumValueOptions options_;

            public static final Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_EnumValueDescriptorProto_descriptor;
            }

            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_EnumValueDescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(EnumValueDescriptorProto.class, Builder.class);
            }

            private Builder() {
                this.name_ = "";
                this.options_ = EnumValueOptions.getDefaultInstance();
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.name_ = "";
                this.options_ = EnumValueOptions.getDefaultInstance();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getOptionsFieldBuilder();
                }
            }

            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                this.name_ = "";
                this.bitField0_ &= -2;
                this.number_ = 0;
                this.bitField0_ &= -3;
                if (this.optionsBuilder_ == null) {
                    this.options_ = EnumValueOptions.getDefaultInstance();
                } else {
                    this.optionsBuilder_.clear();
                }
                this.bitField0_ &= -5;
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_EnumValueDescriptorProto_descriptor;
            }

            public EnumValueDescriptorProto getDefaultInstanceForType() {
                return EnumValueDescriptorProto.getDefaultInstance();
            }

            public EnumValueDescriptorProto build() {
                EnumValueDescriptorProto result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw com.google.protobuf.AbstractMessage.Builder.newUninitializedMessageException(result);
            }

            public EnumValueDescriptorProto buildPartial() {
                EnumValueDescriptorProto result = new EnumValueDescriptorProto((com.google.protobuf.GeneratedMessage.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.name_ = this.name_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.number_ = this.number_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                if (this.optionsBuilder_ == null) {
                    result.options_ = this.options_;
                } else {
                    result.options_ = (EnumValueOptions) this.optionsBuilder_.build();
                }
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof EnumValueDescriptorProto) {
                    return mergeFrom((EnumValueDescriptorProto) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(EnumValueDescriptorProto other) {
                if (other != EnumValueDescriptorProto.getDefaultInstance()) {
                    if (other.hasName()) {
                        this.bitField0_ |= 1;
                        this.name_ = other.name_;
                        onChanged();
                    }
                    if (other.hasNumber()) {
                        setNumber(other.getNumber());
                    }
                    if (other.hasOptions()) {
                        mergeOptions(other.getOptions());
                    }
                    mergeUnknownFields(other.getUnknownFields());
                }
                return this;
            }

            public final boolean isInitialized() {
                if (!hasOptions() || getOptions().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                EnumValueDescriptorProto parsedMessage = null;
                try {
                    parsedMessage = (EnumValueDescriptorProto) EnumValueDescriptorProto.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    parsedMessage = (EnumValueDescriptorProto) e.getUnfinishedMessage();
                    throw e;
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
            }

            public boolean hasName() {
                return (this.bitField0_ & 1) == 1;
            }

            public String getName() {
                ByteString ref = this.name_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.name_ = s;
                return s;
            }

            public ByteString getNameBytes() {
                Object ref = this.name_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.name_ = b;
                return b;
            }

            public Builder setName(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.name_ = value;
                onChanged();
                return this;
            }

            public Builder clearName() {
                this.bitField0_ &= -2;
                this.name_ = EnumValueDescriptorProto.getDefaultInstance().getName();
                onChanged();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.name_ = value;
                onChanged();
                return this;
            }

            public boolean hasNumber() {
                return (this.bitField0_ & 2) == 2;
            }

            public int getNumber() {
                return this.number_;
            }

            public Builder setNumber(int value) {
                this.bitField0_ |= 2;
                this.number_ = value;
                onChanged();
                return this;
            }

            public Builder clearNumber() {
                this.bitField0_ &= -3;
                this.number_ = 0;
                onChanged();
                return this;
            }

            public boolean hasOptions() {
                return (this.bitField0_ & 4) == 4;
            }

            public EnumValueOptions getOptions() {
                if (this.optionsBuilder_ == null) {
                    return this.options_;
                }
                return (EnumValueOptions) this.optionsBuilder_.getMessage();
            }

            public Builder setOptions(EnumValueOptions value) {
                if (this.optionsBuilder_ != null) {
                    this.optionsBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.options_ = value;
                    onChanged();
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder setOptions(Builder builderForValue) {
                if (this.optionsBuilder_ == null) {
                    this.options_ = builderForValue.build();
                    onChanged();
                } else {
                    this.optionsBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder mergeOptions(EnumValueOptions value) {
                if (this.optionsBuilder_ == null) {
                    if ((this.bitField0_ & 4) != 4 || this.options_ == EnumValueOptions.getDefaultInstance()) {
                        this.options_ = value;
                    } else {
                        this.options_ = EnumValueOptions.newBuilder(this.options_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.optionsBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder clearOptions() {
                if (this.optionsBuilder_ == null) {
                    this.options_ = EnumValueOptions.getDefaultInstance();
                    onChanged();
                } else {
                    this.optionsBuilder_.clear();
                }
                this.bitField0_ &= -5;
                return this;
            }

            public Builder getOptionsBuilder() {
                this.bitField0_ |= 4;
                onChanged();
                return (Builder) getOptionsFieldBuilder().getBuilder();
            }

            public EnumValueOptionsOrBuilder getOptionsOrBuilder() {
                if (this.optionsBuilder_ != null) {
                    return (EnumValueOptionsOrBuilder) this.optionsBuilder_.getMessageOrBuilder();
                }
                return this.options_;
            }

            private SingleFieldBuilder<EnumValueOptions, Builder, EnumValueOptionsOrBuilder> getOptionsFieldBuilder() {
                if (this.optionsBuilder_ == null) {
                    this.optionsBuilder_ = new SingleFieldBuilder(getOptions(), getParentForChildren(), isClean());
                    this.options_ = null;
                }
                return this.optionsBuilder_;
            }
        }

        private EnumValueDescriptorProto(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private EnumValueDescriptorProto(boolean noInit) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static EnumValueDescriptorProto getDefaultInstance() {
            return defaultInstance;
        }

        public EnumValueDescriptorProto getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private EnumValueDescriptorProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 10:
                            ByteString bs = input.readBytes();
                            this.bitField0_ |= 1;
                            this.name_ = bs;
                            break;
                        case 16:
                            this.bitField0_ |= 2;
                            this.number_ = input.readInt32();
                            break;
                        case 26:
                            Builder subBuilder = null;
                            if ((this.bitField0_ & 4) == 4) {
                                subBuilder = this.options_.toBuilder();
                            }
                            this.options_ = (EnumValueOptions) input.readMessage(EnumValueOptions.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.options_);
                                this.options_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 4;
                            break;
                        default:
                            if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                                break;
                            }
                            break;
                    }
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                } catch (Throwable th) {
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                }
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_EnumValueDescriptorProto_descriptor;
        }

        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_EnumValueDescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(EnumValueDescriptorProto.class, Builder.class);
        }

        static {
            defaultInstance.initFields();
        }

        public Parser<EnumValueDescriptorProto> getParserForType() {
            return PARSER;
        }

        public boolean hasName() {
            return (this.bitField0_ & 1) == 1;
        }

        public String getName() {
            ByteString ref = this.name_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.name_ = s;
            }
            return s;
        }

        public ByteString getNameBytes() {
            Object ref = this.name_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.name_ = b;
            return b;
        }

        public boolean hasNumber() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getNumber() {
            return this.number_;
        }

        public boolean hasOptions() {
            return (this.bitField0_ & 4) == 4;
        }

        public EnumValueOptions getOptions() {
            return this.options_;
        }

        public EnumValueOptionsOrBuilder getOptionsOrBuilder() {
            return this.options_;
        }

        private void initFields() {
            this.name_ = "";
            this.number_ = 0;
            this.options_ = EnumValueOptions.getDefaultInstance();
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == (byte) 1) {
                return true;
            }
            if (isInitialized == (byte) 0) {
                return false;
            }
            if (!hasOptions() || getOptions().isInitialized()) {
                this.memoizedIsInitialized = (byte) 1;
                return true;
            }
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeBytes(1, getNameBytes());
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeInt32(2, this.number_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeMessage(3, this.options_);
            }
            getUnknownFields().writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
                size = 0 + CodedOutputStream.computeBytesSize(1, getNameBytes());
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeInt32Size(2, this.number_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size += CodedOutputStream.computeMessageSize(3, this.options_);
            }
            size += getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = size;
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static EnumValueDescriptorProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (EnumValueDescriptorProto) PARSER.parseFrom(data);
        }

        public static EnumValueDescriptorProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumValueDescriptorProto) PARSER.parseFrom(data, extensionRegistry);
        }

        public static EnumValueDescriptorProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (EnumValueDescriptorProto) PARSER.parseFrom(data);
        }

        public static EnumValueDescriptorProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumValueDescriptorProto) PARSER.parseFrom(data, extensionRegistry);
        }

        public static EnumValueDescriptorProto parseFrom(InputStream input) throws IOException {
            return (EnumValueDescriptorProto) PARSER.parseFrom(input);
        }

        public static EnumValueDescriptorProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumValueDescriptorProto) PARSER.parseFrom(input, extensionRegistry);
        }

        public static EnumValueDescriptorProto parseDelimitedFrom(InputStream input) throws IOException {
            return (EnumValueDescriptorProto) PARSER.parseDelimitedFrom(input);
        }

        public static EnumValueDescriptorProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumValueDescriptorProto) PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static EnumValueDescriptorProto parseFrom(CodedInputStream input) throws IOException {
            return (EnumValueDescriptorProto) PARSER.parseFrom(input);
        }

        public static EnumValueDescriptorProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumValueDescriptorProto) PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(EnumValueDescriptorProto prototype) {
            return newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return newBuilder(this);
        }

        protected Builder newBuilderForType(BuilderParent parent) {
            return new Builder(parent);
        }
    }

    public interface EnumValueOptionsOrBuilder extends ExtendableMessageOrBuilder<EnumValueOptions> {
        boolean getDeprecated();

        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();

        UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int i);

        List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList();

        boolean hasDeprecated();
    }

    public static final class EnumValueOptions extends ExtendableMessage<EnumValueOptions> implements EnumValueOptionsOrBuilder {
        public static final int DEPRECATED_FIELD_NUMBER = 1;
        public static Parser<EnumValueOptions> PARSER = new AbstractParser<EnumValueOptions>() {
            public EnumValueOptions parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new EnumValueOptions(input, extensionRegistry);
            }
        };
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        private static final EnumValueOptions defaultInstance = new EnumValueOptions(true);
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private boolean deprecated_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private List<UninterpretedOption> uninterpretedOption_;
        private final UnknownFieldSet unknownFields;

        public static final class Builder extends ExtendableBuilder<EnumValueOptions, Builder> implements EnumValueOptionsOrBuilder {
            private int bitField0_;
            private boolean deprecated_;
            private RepeatedFieldBuilder<UninterpretedOption, Builder, UninterpretedOptionOrBuilder> uninterpretedOptionBuilder_;
            private List<UninterpretedOption> uninterpretedOption_;

            public static final Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_EnumValueOptions_descriptor;
            }

            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_EnumValueOptions_fieldAccessorTable.ensureFieldAccessorsInitialized(EnumValueOptions.class, Builder.class);
            }

            private Builder() {
                this.uninterpretedOption_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.uninterpretedOption_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getUninterpretedOptionFieldBuilder();
                }
            }

            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                this.deprecated_ = false;
                this.bitField0_ &= -2;
                if (this.uninterpretedOptionBuilder_ == null) {
                    this.uninterpretedOption_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                } else {
                    this.uninterpretedOptionBuilder_.clear();
                }
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_EnumValueOptions_descriptor;
            }

            public EnumValueOptions getDefaultInstanceForType() {
                return EnumValueOptions.getDefaultInstance();
            }

            public EnumValueOptions build() {
                EnumValueOptions result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw com.google.protobuf.AbstractMessage.Builder.newUninitializedMessageException(result);
            }

            public EnumValueOptions buildPartial() {
                EnumValueOptions result = new EnumValueOptions((ExtendableBuilder) this);
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.deprecated_ = this.deprecated_;
                if (this.uninterpretedOptionBuilder_ == null) {
                    if ((this.bitField0_ & 2) == 2) {
                        this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
                        this.bitField0_ &= -3;
                    }
                    result.uninterpretedOption_ = this.uninterpretedOption_;
                } else {
                    result.uninterpretedOption_ = this.uninterpretedOptionBuilder_.build();
                }
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof EnumValueOptions) {
                    return mergeFrom((EnumValueOptions) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(EnumValueOptions other) {
                RepeatedFieldBuilder repeatedFieldBuilder = null;
                if (other != EnumValueOptions.getDefaultInstance()) {
                    if (other.hasDeprecated()) {
                        setDeprecated(other.getDeprecated());
                    }
                    if (this.uninterpretedOptionBuilder_ == null) {
                        if (!other.uninterpretedOption_.isEmpty()) {
                            if (this.uninterpretedOption_.isEmpty()) {
                                this.uninterpretedOption_ = other.uninterpretedOption_;
                                this.bitField0_ &= -3;
                            } else {
                                ensureUninterpretedOptionIsMutable();
                                this.uninterpretedOption_.addAll(other.uninterpretedOption_);
                            }
                            onChanged();
                        }
                    } else if (!other.uninterpretedOption_.isEmpty()) {
                        if (this.uninterpretedOptionBuilder_.isEmpty()) {
                            this.uninterpretedOptionBuilder_.dispose();
                            this.uninterpretedOptionBuilder_ = null;
                            this.uninterpretedOption_ = other.uninterpretedOption_;
                            this.bitField0_ &= -3;
                            if (GeneratedMessage.alwaysUseFieldBuilders) {
                                repeatedFieldBuilder = getUninterpretedOptionFieldBuilder();
                            }
                            this.uninterpretedOptionBuilder_ = repeatedFieldBuilder;
                        } else {
                            this.uninterpretedOptionBuilder_.addAllMessages(other.uninterpretedOption_);
                        }
                    }
                    mergeExtensionFields(other);
                    mergeUnknownFields(other.getUnknownFields());
                }
                return this;
            }

            public final boolean isInitialized() {
                for (int i = 0; i < getUninterpretedOptionCount(); i++) {
                    if (!getUninterpretedOption(i).isInitialized()) {
                        return false;
                    }
                }
                if (extensionsAreInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                EnumValueOptions parsedMessage = null;
                try {
                    parsedMessage = (EnumValueOptions) EnumValueOptions.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    parsedMessage = (EnumValueOptions) e.getUnfinishedMessage();
                    throw e;
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
            }

            public boolean hasDeprecated() {
                return (this.bitField0_ & 1) == 1;
            }

            public boolean getDeprecated() {
                return this.deprecated_;
            }

            public Builder setDeprecated(boolean value) {
                this.bitField0_ |= 1;
                this.deprecated_ = value;
                onChanged();
                return this;
            }

            public Builder clearDeprecated() {
                this.bitField0_ &= -2;
                this.deprecated_ = false;
                onChanged();
                return this;
            }

            private void ensureUninterpretedOptionIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.uninterpretedOption_ = new ArrayList(this.uninterpretedOption_);
                    this.bitField0_ |= 2;
                }
            }

            public List<UninterpretedOption> getUninterpretedOptionList() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    return Collections.unmodifiableList(this.uninterpretedOption_);
                }
                return this.uninterpretedOptionBuilder_.getMessageList();
            }

            public int getUninterpretedOptionCount() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    return this.uninterpretedOption_.size();
                }
                return this.uninterpretedOptionBuilder_.getCount();
            }

            public UninterpretedOption getUninterpretedOption(int index) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    return (UninterpretedOption) this.uninterpretedOption_.get(index);
                }
                return (UninterpretedOption) this.uninterpretedOptionBuilder_.getMessage(index);
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption value) {
                if (this.uninterpretedOptionBuilder_ != null) {
                    this.uninterpretedOptionBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setUninterpretedOption(int index, Builder builderForValue) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption value) {
                if (this.uninterpretedOptionBuilder_ != null) {
                    this.uninterpretedOptionBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption value) {
                if (this.uninterpretedOptionBuilder_ != null) {
                    this.uninterpretedOptionBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addUninterpretedOption(Builder builderForValue) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addUninterpretedOption(int index, Builder builderForValue) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.uninterpretedOption_);
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearUninterpretedOption() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    this.uninterpretedOption_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.clear();
                }
                return this;
            }

            public Builder removeUninterpretedOption(int index) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.remove(index);
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.remove(index);
                }
                return this;
            }

            public Builder getUninterpretedOptionBuilder(int index) {
                return (Builder) getUninterpretedOptionFieldBuilder().getBuilder(index);
            }

            public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    return (UninterpretedOptionOrBuilder) this.uninterpretedOption_.get(index);
                }
                return (UninterpretedOptionOrBuilder) this.uninterpretedOptionBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
                if (this.uninterpretedOptionBuilder_ != null) {
                    return this.uninterpretedOptionBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.uninterpretedOption_);
            }

            public Builder addUninterpretedOptionBuilder() {
                return (Builder) getUninterpretedOptionFieldBuilder().addBuilder(UninterpretedOption.getDefaultInstance());
            }

            public Builder addUninterpretedOptionBuilder(int index) {
                return (Builder) getUninterpretedOptionFieldBuilder().addBuilder(index, UninterpretedOption.getDefaultInstance());
            }

            public List<Builder> getUninterpretedOptionBuilderList() {
                return getUninterpretedOptionFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<UninterpretedOption, Builder, UninterpretedOptionOrBuilder> getUninterpretedOptionFieldBuilder() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    this.uninterpretedOptionBuilder_ = new RepeatedFieldBuilder(this.uninterpretedOption_, (this.bitField0_ & 2) == 2, getParentForChildren(), isClean());
                    this.uninterpretedOption_ = null;
                }
                return this.uninterpretedOptionBuilder_;
            }
        }

        private EnumValueOptions(ExtendableBuilder<EnumValueOptions, ?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private EnumValueOptions(boolean noInit) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static EnumValueOptions getDefaultInstance() {
            return defaultInstance;
        }

        public EnumValueOptions getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private EnumValueOptions(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 8:
                            this.bitField0_ |= 1;
                            this.deprecated_ = input.readBool();
                            break;
                        case 7994:
                            if ((mutable_bitField0_ & 2) != 2) {
                                this.uninterpretedOption_ = new ArrayList();
                                mutable_bitField0_ |= 2;
                            }
                            this.uninterpretedOption_.add(input.readMessage(UninterpretedOption.PARSER, extensionRegistry));
                            break;
                        default:
                            if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                                break;
                            }
                            break;
                    }
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                } catch (Throwable th) {
                    if ((mutable_bitField0_ & 2) == 2) {
                        this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                }
            }
            if ((mutable_bitField0_ & 2) == 2) {
                this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_EnumValueOptions_descriptor;
        }

        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_EnumValueOptions_fieldAccessorTable.ensureFieldAccessorsInitialized(EnumValueOptions.class, Builder.class);
        }

        static {
            defaultInstance.initFields();
        }

        public Parser<EnumValueOptions> getParserForType() {
            return PARSER;
        }

        public boolean hasDeprecated() {
            return (this.bitField0_ & 1) == 1;
        }

        public boolean getDeprecated() {
            return this.deprecated_;
        }

        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        public UninterpretedOption getUninterpretedOption(int index) {
            return (UninterpretedOption) this.uninterpretedOption_.get(index);
        }

        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
            return (UninterpretedOptionOrBuilder) this.uninterpretedOption_.get(index);
        }

        private void initFields() {
            this.deprecated_ = false;
            this.uninterpretedOption_ = Collections.emptyList();
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == (byte) 1) {
                return true;
            }
            if (isInitialized == (byte) 0) {
                return false;
            }
            int i = 0;
            while (i < getUninterpretedOptionCount()) {
                if (getUninterpretedOption(i).isInitialized()) {
                    i++;
                } else {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (extensionsAreInitialized()) {
                this.memoizedIsInitialized = (byte) 1;
                return true;
            }
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            getSerializedSize();
            ExtensionWriter extensionWriter = newExtensionWriter();
            if ((this.bitField0_ & 1) == 1) {
                output.writeBool(1, this.deprecated_);
            }
            for (int i = 0; i < this.uninterpretedOption_.size(); i++) {
                output.writeMessage(999, (MessageLite) this.uninterpretedOption_.get(i));
            }
            extensionWriter.writeUntil(536870912, output);
            getUnknownFields().writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
                size = 0 + CodedOutputStream.computeBoolSize(1, this.deprecated_);
            }
            for (int i = 0; i < this.uninterpretedOption_.size(); i++) {
                size += CodedOutputStream.computeMessageSize(999, (MessageLite) this.uninterpretedOption_.get(i));
            }
            size = (size + extensionsSerializedSize()) + getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = size;
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static EnumValueOptions parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (EnumValueOptions) PARSER.parseFrom(data);
        }

        public static EnumValueOptions parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumValueOptions) PARSER.parseFrom(data, extensionRegistry);
        }

        public static EnumValueOptions parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (EnumValueOptions) PARSER.parseFrom(data);
        }

        public static EnumValueOptions parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (EnumValueOptions) PARSER.parseFrom(data, extensionRegistry);
        }

        public static EnumValueOptions parseFrom(InputStream input) throws IOException {
            return (EnumValueOptions) PARSER.parseFrom(input);
        }

        public static EnumValueOptions parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumValueOptions) PARSER.parseFrom(input, extensionRegistry);
        }

        public static EnumValueOptions parseDelimitedFrom(InputStream input) throws IOException {
            return (EnumValueOptions) PARSER.parseDelimitedFrom(input);
        }

        public static EnumValueOptions parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumValueOptions) PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static EnumValueOptions parseFrom(CodedInputStream input) throws IOException {
            return (EnumValueOptions) PARSER.parseFrom(input);
        }

        public static EnumValueOptions parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (EnumValueOptions) PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(EnumValueOptions prototype) {
            return newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return newBuilder(this);
        }

        protected Builder newBuilderForType(BuilderParent parent) {
            return new Builder(parent);
        }
    }

    public interface FieldDescriptorProtoOrBuilder extends MessageOrBuilder {
        String getDefaultValue();

        ByteString getDefaultValueBytes();

        String getExtendee();

        ByteString getExtendeeBytes();

        Label getLabel();

        String getName();

        ByteString getNameBytes();

        int getNumber();

        int getOneofIndex();

        FieldOptions getOptions();

        FieldOptionsOrBuilder getOptionsOrBuilder();

        Type getType();

        String getTypeName();

        ByteString getTypeNameBytes();

        boolean hasDefaultValue();

        boolean hasExtendee();

        boolean hasLabel();

        boolean hasName();

        boolean hasNumber();

        boolean hasOneofIndex();

        boolean hasOptions();

        boolean hasType();

        boolean hasTypeName();
    }

    public static final class FieldDescriptorProto extends GeneratedMessage implements FieldDescriptorProtoOrBuilder {
        public static final int DEFAULT_VALUE_FIELD_NUMBER = 7;
        public static final int EXTENDEE_FIELD_NUMBER = 2;
        public static final int LABEL_FIELD_NUMBER = 4;
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int NUMBER_FIELD_NUMBER = 3;
        public static final int ONEOF_INDEX_FIELD_NUMBER = 9;
        public static final int OPTIONS_FIELD_NUMBER = 8;
        public static Parser<FieldDescriptorProto> PARSER = new AbstractParser<FieldDescriptorProto>() {
            public FieldDescriptorProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new FieldDescriptorProto(input, extensionRegistry);
            }
        };
        public static final int TYPE_FIELD_NUMBER = 5;
        public static final int TYPE_NAME_FIELD_NUMBER = 6;
        private static final FieldDescriptorProto defaultInstance = new FieldDescriptorProto(true);
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private Object defaultValue_;
        private Object extendee_;
        private Label label_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private Object name_;
        private int number_;
        private int oneofIndex_;
        private FieldOptions options_;
        private Object typeName_;
        private Type type_;
        private final UnknownFieldSet unknownFields;

        public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> implements FieldDescriptorProtoOrBuilder {
            private int bitField0_;
            private Object defaultValue_;
            private Object extendee_;
            private Label label_;
            private Object name_;
            private int number_;
            private int oneofIndex_;
            private SingleFieldBuilder<FieldOptions, Builder, FieldOptionsOrBuilder> optionsBuilder_;
            private FieldOptions options_;
            private Object typeName_;
            private Type type_;

            public static final Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_FieldDescriptorProto_descriptor;
            }

            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_FieldDescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(FieldDescriptorProto.class, Builder.class);
            }

            private Builder() {
                this.name_ = "";
                this.label_ = Label.LABEL_OPTIONAL;
                this.type_ = Type.TYPE_DOUBLE;
                this.typeName_ = "";
                this.extendee_ = "";
                this.defaultValue_ = "";
                this.options_ = FieldOptions.getDefaultInstance();
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.name_ = "";
                this.label_ = Label.LABEL_OPTIONAL;
                this.type_ = Type.TYPE_DOUBLE;
                this.typeName_ = "";
                this.extendee_ = "";
                this.defaultValue_ = "";
                this.options_ = FieldOptions.getDefaultInstance();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getOptionsFieldBuilder();
                }
            }

            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                this.name_ = "";
                this.bitField0_ &= -2;
                this.number_ = 0;
                this.bitField0_ &= -3;
                this.label_ = Label.LABEL_OPTIONAL;
                this.bitField0_ &= -5;
                this.type_ = Type.TYPE_DOUBLE;
                this.bitField0_ &= -9;
                this.typeName_ = "";
                this.bitField0_ &= -17;
                this.extendee_ = "";
                this.bitField0_ &= -33;
                this.defaultValue_ = "";
                this.bitField0_ &= -65;
                this.oneofIndex_ = 0;
                this.bitField0_ &= -129;
                if (this.optionsBuilder_ == null) {
                    this.options_ = FieldOptions.getDefaultInstance();
                } else {
                    this.optionsBuilder_.clear();
                }
                this.bitField0_ &= -257;
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_FieldDescriptorProto_descriptor;
            }

            public FieldDescriptorProto getDefaultInstanceForType() {
                return FieldDescriptorProto.getDefaultInstance();
            }

            public FieldDescriptorProto build() {
                FieldDescriptorProto result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw com.google.protobuf.AbstractMessage.Builder.newUninitializedMessageException(result);
            }

            public FieldDescriptorProto buildPartial() {
                FieldDescriptorProto result = new FieldDescriptorProto((com.google.protobuf.GeneratedMessage.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.name_ = this.name_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.number_ = this.number_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.label_ = this.label_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.type_ = this.type_;
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 16;
                }
                result.typeName_ = this.typeName_;
                if ((from_bitField0_ & 32) == 32) {
                    to_bitField0_ |= 32;
                }
                result.extendee_ = this.extendee_;
                if ((from_bitField0_ & 64) == 64) {
                    to_bitField0_ |= 64;
                }
                result.defaultValue_ = this.defaultValue_;
                if ((from_bitField0_ & 128) == 128) {
                    to_bitField0_ |= 128;
                }
                result.oneofIndex_ = this.oneofIndex_;
                if ((from_bitField0_ & 256) == 256) {
                    to_bitField0_ |= 256;
                }
                if (this.optionsBuilder_ == null) {
                    result.options_ = this.options_;
                } else {
                    result.options_ = (FieldOptions) this.optionsBuilder_.build();
                }
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof FieldDescriptorProto) {
                    return mergeFrom((FieldDescriptorProto) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(FieldDescriptorProto other) {
                if (other != FieldDescriptorProto.getDefaultInstance()) {
                    if (other.hasName()) {
                        this.bitField0_ |= 1;
                        this.name_ = other.name_;
                        onChanged();
                    }
                    if (other.hasNumber()) {
                        setNumber(other.getNumber());
                    }
                    if (other.hasLabel()) {
                        setLabel(other.getLabel());
                    }
                    if (other.hasType()) {
                        setType(other.getType());
                    }
                    if (other.hasTypeName()) {
                        this.bitField0_ |= 16;
                        this.typeName_ = other.typeName_;
                        onChanged();
                    }
                    if (other.hasExtendee()) {
                        this.bitField0_ |= 32;
                        this.extendee_ = other.extendee_;
                        onChanged();
                    }
                    if (other.hasDefaultValue()) {
                        this.bitField0_ |= 64;
                        this.defaultValue_ = other.defaultValue_;
                        onChanged();
                    }
                    if (other.hasOneofIndex()) {
                        setOneofIndex(other.getOneofIndex());
                    }
                    if (other.hasOptions()) {
                        mergeOptions(other.getOptions());
                    }
                    mergeUnknownFields(other.getUnknownFields());
                }
                return this;
            }

            public final boolean isInitialized() {
                if (!hasOptions() || getOptions().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                FieldDescriptorProto parsedMessage = null;
                try {
                    parsedMessage = (FieldDescriptorProto) FieldDescriptorProto.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    parsedMessage = (FieldDescriptorProto) e.getUnfinishedMessage();
                    throw e;
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
            }

            public boolean hasName() {
                return (this.bitField0_ & 1) == 1;
            }

            public String getName() {
                ByteString ref = this.name_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.name_ = s;
                return s;
            }

            public ByteString getNameBytes() {
                Object ref = this.name_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.name_ = b;
                return b;
            }

            public Builder setName(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.name_ = value;
                onChanged();
                return this;
            }

            public Builder clearName() {
                this.bitField0_ &= -2;
                this.name_ = FieldDescriptorProto.getDefaultInstance().getName();
                onChanged();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.name_ = value;
                onChanged();
                return this;
            }

            public boolean hasNumber() {
                return (this.bitField0_ & 2) == 2;
            }

            public int getNumber() {
                return this.number_;
            }

            public Builder setNumber(int value) {
                this.bitField0_ |= 2;
                this.number_ = value;
                onChanged();
                return this;
            }

            public Builder clearNumber() {
                this.bitField0_ &= -3;
                this.number_ = 0;
                onChanged();
                return this;
            }

            public boolean hasLabel() {
                return (this.bitField0_ & 4) == 4;
            }

            public Label getLabel() {
                return this.label_;
            }

            public Builder setLabel(Label value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 4;
                this.label_ = value;
                onChanged();
                return this;
            }

            public Builder clearLabel() {
                this.bitField0_ &= -5;
                this.label_ = Label.LABEL_OPTIONAL;
                onChanged();
                return this;
            }

            public boolean hasType() {
                return (this.bitField0_ & 8) == 8;
            }

            public Type getType() {
                return this.type_;
            }

            public Builder setType(Type value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 8;
                this.type_ = value;
                onChanged();
                return this;
            }

            public Builder clearType() {
                this.bitField0_ &= -9;
                this.type_ = Type.TYPE_DOUBLE;
                onChanged();
                return this;
            }

            public boolean hasTypeName() {
                return (this.bitField0_ & 16) == 16;
            }

            public String getTypeName() {
                ByteString ref = this.typeName_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.typeName_ = s;
                return s;
            }

            public ByteString getTypeNameBytes() {
                Object ref = this.typeName_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.typeName_ = b;
                return b;
            }

            public Builder setTypeName(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 16;
                this.typeName_ = value;
                onChanged();
                return this;
            }

            public Builder clearTypeName() {
                this.bitField0_ &= -17;
                this.typeName_ = FieldDescriptorProto.getDefaultInstance().getTypeName();
                onChanged();
                return this;
            }

            public Builder setTypeNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 16;
                this.typeName_ = value;
                onChanged();
                return this;
            }

            public boolean hasExtendee() {
                return (this.bitField0_ & 32) == 32;
            }

            public String getExtendee() {
                ByteString ref = this.extendee_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.extendee_ = s;
                return s;
            }

            public ByteString getExtendeeBytes() {
                Object ref = this.extendee_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.extendee_ = b;
                return b;
            }

            public Builder setExtendee(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 32;
                this.extendee_ = value;
                onChanged();
                return this;
            }

            public Builder clearExtendee() {
                this.bitField0_ &= -33;
                this.extendee_ = FieldDescriptorProto.getDefaultInstance().getExtendee();
                onChanged();
                return this;
            }

            public Builder setExtendeeBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 32;
                this.extendee_ = value;
                onChanged();
                return this;
            }

            public boolean hasDefaultValue() {
                return (this.bitField0_ & 64) == 64;
            }

            public String getDefaultValue() {
                ByteString ref = this.defaultValue_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.defaultValue_ = s;
                return s;
            }

            public ByteString getDefaultValueBytes() {
                Object ref = this.defaultValue_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.defaultValue_ = b;
                return b;
            }

            public Builder setDefaultValue(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 64;
                this.defaultValue_ = value;
                onChanged();
                return this;
            }

            public Builder clearDefaultValue() {
                this.bitField0_ &= -65;
                this.defaultValue_ = FieldDescriptorProto.getDefaultInstance().getDefaultValue();
                onChanged();
                return this;
            }

            public Builder setDefaultValueBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 64;
                this.defaultValue_ = value;
                onChanged();
                return this;
            }

            public boolean hasOneofIndex() {
                return (this.bitField0_ & 128) == 128;
            }

            public int getOneofIndex() {
                return this.oneofIndex_;
            }

            public Builder setOneofIndex(int value) {
                this.bitField0_ |= 128;
                this.oneofIndex_ = value;
                onChanged();
                return this;
            }

            public Builder clearOneofIndex() {
                this.bitField0_ &= -129;
                this.oneofIndex_ = 0;
                onChanged();
                return this;
            }

            public boolean hasOptions() {
                return (this.bitField0_ & 256) == 256;
            }

            public FieldOptions getOptions() {
                if (this.optionsBuilder_ == null) {
                    return this.options_;
                }
                return (FieldOptions) this.optionsBuilder_.getMessage();
            }

            public Builder setOptions(FieldOptions value) {
                if (this.optionsBuilder_ != null) {
                    this.optionsBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.options_ = value;
                    onChanged();
                }
                this.bitField0_ |= 256;
                return this;
            }

            public Builder setOptions(Builder builderForValue) {
                if (this.optionsBuilder_ == null) {
                    this.options_ = builderForValue.build();
                    onChanged();
                } else {
                    this.optionsBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 256;
                return this;
            }

            public Builder mergeOptions(FieldOptions value) {
                if (this.optionsBuilder_ == null) {
                    if ((this.bitField0_ & 256) != 256 || this.options_ == FieldOptions.getDefaultInstance()) {
                        this.options_ = value;
                    } else {
                        this.options_ = FieldOptions.newBuilder(this.options_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.optionsBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 256;
                return this;
            }

            public Builder clearOptions() {
                if (this.optionsBuilder_ == null) {
                    this.options_ = FieldOptions.getDefaultInstance();
                    onChanged();
                } else {
                    this.optionsBuilder_.clear();
                }
                this.bitField0_ &= -257;
                return this;
            }

            public Builder getOptionsBuilder() {
                this.bitField0_ |= 256;
                onChanged();
                return (Builder) getOptionsFieldBuilder().getBuilder();
            }

            public FieldOptionsOrBuilder getOptionsOrBuilder() {
                if (this.optionsBuilder_ != null) {
                    return (FieldOptionsOrBuilder) this.optionsBuilder_.getMessageOrBuilder();
                }
                return this.options_;
            }

            private SingleFieldBuilder<FieldOptions, Builder, FieldOptionsOrBuilder> getOptionsFieldBuilder() {
                if (this.optionsBuilder_ == null) {
                    this.optionsBuilder_ = new SingleFieldBuilder(getOptions(), getParentForChildren(), isClean());
                    this.options_ = null;
                }
                return this.optionsBuilder_;
            }
        }

        public enum Label implements ProtocolMessageEnum {
            LABEL_OPTIONAL(0, 1),
            LABEL_REQUIRED(1, 2),
            LABEL_REPEATED(2, 3);
            
            public static final int LABEL_OPTIONAL_VALUE = 1;
            public static final int LABEL_REPEATED_VALUE = 3;
            public static final int LABEL_REQUIRED_VALUE = 2;
            private static final Label[] VALUES = null;
            private static EnumLiteMap<Label> internalValueMap;
            private final int index;
            private final int value;

            static {
                internalValueMap = new EnumLiteMap<Label>() {
                    public Label findValueByNumber(int number) {
                        return Label.valueOf(number);
                    }
                };
                VALUES = values();
            }

            public final int getNumber() {
                return this.value;
            }

            public static Label valueOf(int value) {
                switch (value) {
                    case 1:
                        return LABEL_OPTIONAL;
                    case 2:
                        return LABEL_REQUIRED;
                    case 3:
                        return LABEL_REPEATED;
                    default:
                        return null;
                }
            }

            public static EnumLiteMap<Label> internalGetValueMap() {
                return internalValueMap;
            }

            public final EnumValueDescriptor getValueDescriptor() {
                return (EnumValueDescriptor) getDescriptor().getValues().get(this.index);
            }

            public final EnumDescriptor getDescriptorForType() {
                return getDescriptor();
            }

            public static final EnumDescriptor getDescriptor() {
                return (EnumDescriptor) FieldDescriptorProto.getDescriptor().getEnumTypes().get(1);
            }

            public static Label valueOf(EnumValueDescriptor desc) {
                if (desc.getType() == getDescriptor()) {
                    return VALUES[desc.getIndex()];
                }
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            }

            private Label(int index, int value) {
                this.index = index;
                this.value = value;
            }
        }

        public enum Type implements ProtocolMessageEnum {
            TYPE_DOUBLE(0, 1),
            TYPE_FLOAT(1, 2),
            TYPE_INT64(2, 3),
            TYPE_UINT64(3, 4),
            TYPE_INT32(4, 5),
            TYPE_FIXED64(5, 6),
            TYPE_FIXED32(6, 7),
            TYPE_BOOL(7, 8),
            TYPE_STRING(8, 9),
            TYPE_GROUP(9, 10),
            TYPE_MESSAGE(10, 11),
            TYPE_BYTES(11, 12),
            TYPE_UINT32(12, 13),
            TYPE_ENUM(13, 14),
            TYPE_SFIXED32(14, 15),
            TYPE_SFIXED64(15, 16),
            TYPE_SINT32(16, 17),
            TYPE_SINT64(17, 18);
            
            public static final int TYPE_BOOL_VALUE = 8;
            public static final int TYPE_BYTES_VALUE = 12;
            public static final int TYPE_DOUBLE_VALUE = 1;
            public static final int TYPE_ENUM_VALUE = 14;
            public static final int TYPE_FIXED32_VALUE = 7;
            public static final int TYPE_FIXED64_VALUE = 6;
            public static final int TYPE_FLOAT_VALUE = 2;
            public static final int TYPE_GROUP_VALUE = 10;
            public static final int TYPE_INT32_VALUE = 5;
            public static final int TYPE_INT64_VALUE = 3;
            public static final int TYPE_MESSAGE_VALUE = 11;
            public static final int TYPE_SFIXED32_VALUE = 15;
            public static final int TYPE_SFIXED64_VALUE = 16;
            public static final int TYPE_SINT32_VALUE = 17;
            public static final int TYPE_SINT64_VALUE = 18;
            public static final int TYPE_STRING_VALUE = 9;
            public static final int TYPE_UINT32_VALUE = 13;
            public static final int TYPE_UINT64_VALUE = 4;
            private static final Type[] VALUES = null;
            private static EnumLiteMap<Type> internalValueMap;
            private final int index;
            private final int value;

            static {
                internalValueMap = new EnumLiteMap<Type>() {
                    public Type findValueByNumber(int number) {
                        return Type.valueOf(number);
                    }
                };
                VALUES = values();
            }

            public final int getNumber() {
                return this.value;
            }

            public static Type valueOf(int value) {
                switch (value) {
                    case 1:
                        return TYPE_DOUBLE;
                    case 2:
                        return TYPE_FLOAT;
                    case 3:
                        return TYPE_INT64;
                    case 4:
                        return TYPE_UINT64;
                    case 5:
                        return TYPE_INT32;
                    case 6:
                        return TYPE_FIXED64;
                    case 7:
                        return TYPE_FIXED32;
                    case 8:
                        return TYPE_BOOL;
                    case 9:
                        return TYPE_STRING;
                    case 10:
                        return TYPE_GROUP;
                    case 11:
                        return TYPE_MESSAGE;
                    case 12:
                        return TYPE_BYTES;
                    case 13:
                        return TYPE_UINT32;
                    case 14:
                        return TYPE_ENUM;
                    case 15:
                        return TYPE_SFIXED32;
                    case 16:
                        return TYPE_SFIXED64;
                    case 17:
                        return TYPE_SINT32;
                    case 18:
                        return TYPE_SINT64;
                    default:
                        return null;
                }
            }

            public static EnumLiteMap<Type> internalGetValueMap() {
                return internalValueMap;
            }

            public final EnumValueDescriptor getValueDescriptor() {
                return (EnumValueDescriptor) getDescriptor().getValues().get(this.index);
            }

            public final EnumDescriptor getDescriptorForType() {
                return getDescriptor();
            }

            public static final EnumDescriptor getDescriptor() {
                return (EnumDescriptor) FieldDescriptorProto.getDescriptor().getEnumTypes().get(0);
            }

            public static Type valueOf(EnumValueDescriptor desc) {
                if (desc.getType() == getDescriptor()) {
                    return VALUES[desc.getIndex()];
                }
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            }

            private Type(int index, int value) {
                this.index = index;
                this.value = value;
            }
        }

        private FieldDescriptorProto(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private FieldDescriptorProto(boolean noInit) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static FieldDescriptorProto getDefaultInstance() {
            return defaultInstance;
        }

        public FieldDescriptorProto getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private FieldDescriptorProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    ByteString bs;
                    int rawValue;
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 10:
                            bs = input.readBytes();
                            this.bitField0_ |= 1;
                            this.name_ = bs;
                            break;
                        case 18:
                            bs = input.readBytes();
                            this.bitField0_ |= 32;
                            this.extendee_ = bs;
                            break;
                        case 24:
                            this.bitField0_ |= 2;
                            this.number_ = input.readInt32();
                            break;
                        case 32:
                            rawValue = input.readEnum();
                            Label value = Label.valueOf(rawValue);
                            if (value != null) {
                                this.bitField0_ |= 4;
                                this.label_ = value;
                                break;
                            }
                            unknownFields.mergeVarintField(4, rawValue);
                            break;
                        case 40:
                            rawValue = input.readEnum();
                            Type value2 = Type.valueOf(rawValue);
                            if (value2 != null) {
                                this.bitField0_ |= 8;
                                this.type_ = value2;
                                break;
                            }
                            unknownFields.mergeVarintField(5, rawValue);
                            break;
                        case 50:
                            bs = input.readBytes();
                            this.bitField0_ |= 16;
                            this.typeName_ = bs;
                            break;
                        case 58:
                            bs = input.readBytes();
                            this.bitField0_ |= 64;
                            this.defaultValue_ = bs;
                            break;
                        case 66:
                            Builder subBuilder = null;
                            if ((this.bitField0_ & 256) == 256) {
                                subBuilder = this.options_.toBuilder();
                            }
                            this.options_ = (FieldOptions) input.readMessage(FieldOptions.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.options_);
                                this.options_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 256;
                            break;
                        case 72:
                            this.bitField0_ |= 128;
                            this.oneofIndex_ = input.readInt32();
                            break;
                        default:
                            if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                                break;
                            }
                            break;
                    }
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                } catch (Throwable th) {
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                }
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_FieldDescriptorProto_descriptor;
        }

        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_FieldDescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(FieldDescriptorProto.class, Builder.class);
        }

        static {
            defaultInstance.initFields();
        }

        public Parser<FieldDescriptorProto> getParserForType() {
            return PARSER;
        }

        public boolean hasName() {
            return (this.bitField0_ & 1) == 1;
        }

        public String getName() {
            ByteString ref = this.name_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.name_ = s;
            }
            return s;
        }

        public ByteString getNameBytes() {
            Object ref = this.name_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.name_ = b;
            return b;
        }

        public boolean hasNumber() {
            return (this.bitField0_ & 2) == 2;
        }

        public int getNumber() {
            return this.number_;
        }

        public boolean hasLabel() {
            return (this.bitField0_ & 4) == 4;
        }

        public Label getLabel() {
            return this.label_;
        }

        public boolean hasType() {
            return (this.bitField0_ & 8) == 8;
        }

        public Type getType() {
            return this.type_;
        }

        public boolean hasTypeName() {
            return (this.bitField0_ & 16) == 16;
        }

        public String getTypeName() {
            ByteString ref = this.typeName_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.typeName_ = s;
            }
            return s;
        }

        public ByteString getTypeNameBytes() {
            Object ref = this.typeName_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.typeName_ = b;
            return b;
        }

        public boolean hasExtendee() {
            return (this.bitField0_ & 32) == 32;
        }

        public String getExtendee() {
            ByteString ref = this.extendee_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.extendee_ = s;
            }
            return s;
        }

        public ByteString getExtendeeBytes() {
            Object ref = this.extendee_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.extendee_ = b;
            return b;
        }

        public boolean hasDefaultValue() {
            return (this.bitField0_ & 64) == 64;
        }

        public String getDefaultValue() {
            ByteString ref = this.defaultValue_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.defaultValue_ = s;
            }
            return s;
        }

        public ByteString getDefaultValueBytes() {
            Object ref = this.defaultValue_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.defaultValue_ = b;
            return b;
        }

        public boolean hasOneofIndex() {
            return (this.bitField0_ & 128) == 128;
        }

        public int getOneofIndex() {
            return this.oneofIndex_;
        }

        public boolean hasOptions() {
            return (this.bitField0_ & 256) == 256;
        }

        public FieldOptions getOptions() {
            return this.options_;
        }

        public FieldOptionsOrBuilder getOptionsOrBuilder() {
            return this.options_;
        }

        private void initFields() {
            this.name_ = "";
            this.number_ = 0;
            this.label_ = Label.LABEL_OPTIONAL;
            this.type_ = Type.TYPE_DOUBLE;
            this.typeName_ = "";
            this.extendee_ = "";
            this.defaultValue_ = "";
            this.oneofIndex_ = 0;
            this.options_ = FieldOptions.getDefaultInstance();
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == (byte) 1) {
                return true;
            }
            if (isInitialized == (byte) 0) {
                return false;
            }
            if (!hasOptions() || getOptions().isInitialized()) {
                this.memoizedIsInitialized = (byte) 1;
                return true;
            }
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeBytes(1, getNameBytes());
            }
            if ((this.bitField0_ & 32) == 32) {
                output.writeBytes(2, getExtendeeBytes());
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeInt32(3, this.number_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeEnum(4, this.label_.getNumber());
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeEnum(5, this.type_.getNumber());
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeBytes(6, getTypeNameBytes());
            }
            if ((this.bitField0_ & 64) == 64) {
                output.writeBytes(7, getDefaultValueBytes());
            }
            if ((this.bitField0_ & 256) == 256) {
                output.writeMessage(8, this.options_);
            }
            if ((this.bitField0_ & 128) == 128) {
                output.writeInt32(9, this.oneofIndex_);
            }
            getUnknownFields().writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
                size = 0 + CodedOutputStream.computeBytesSize(1, getNameBytes());
            }
            if ((this.bitField0_ & 32) == 32) {
                size += CodedOutputStream.computeBytesSize(2, getExtendeeBytes());
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeInt32Size(3, this.number_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size += CodedOutputStream.computeEnumSize(4, this.label_.getNumber());
            }
            if ((this.bitField0_ & 8) == 8) {
                size += CodedOutputStream.computeEnumSize(5, this.type_.getNumber());
            }
            if ((this.bitField0_ & 16) == 16) {
                size += CodedOutputStream.computeBytesSize(6, getTypeNameBytes());
            }
            if ((this.bitField0_ & 64) == 64) {
                size += CodedOutputStream.computeBytesSize(7, getDefaultValueBytes());
            }
            if ((this.bitField0_ & 256) == 256) {
                size += CodedOutputStream.computeMessageSize(8, this.options_);
            }
            if ((this.bitField0_ & 128) == 128) {
                size += CodedOutputStream.computeInt32Size(9, this.oneofIndex_);
            }
            size += getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = size;
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static FieldDescriptorProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (FieldDescriptorProto) PARSER.parseFrom(data);
        }

        public static FieldDescriptorProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FieldDescriptorProto) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FieldDescriptorProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (FieldDescriptorProto) PARSER.parseFrom(data);
        }

        public static FieldDescriptorProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FieldDescriptorProto) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FieldDescriptorProto parseFrom(InputStream input) throws IOException {
            return (FieldDescriptorProto) PARSER.parseFrom(input);
        }

        public static FieldDescriptorProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FieldDescriptorProto) PARSER.parseFrom(input, extensionRegistry);
        }

        public static FieldDescriptorProto parseDelimitedFrom(InputStream input) throws IOException {
            return (FieldDescriptorProto) PARSER.parseDelimitedFrom(input);
        }

        public static FieldDescriptorProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FieldDescriptorProto) PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static FieldDescriptorProto parseFrom(CodedInputStream input) throws IOException {
            return (FieldDescriptorProto) PARSER.parseFrom(input);
        }

        public static FieldDescriptorProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FieldDescriptorProto) PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(FieldDescriptorProto prototype) {
            return newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return newBuilder(this);
        }

        protected Builder newBuilderForType(BuilderParent parent) {
            return new Builder(parent);
        }
    }

    public interface FieldOptionsOrBuilder extends ExtendableMessageOrBuilder<FieldOptions> {
        CType getCtype();

        boolean getDeprecated();

        String getExperimentalMapKey();

        ByteString getExperimentalMapKeyBytes();

        boolean getLazy();

        boolean getPacked();

        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();

        UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int i);

        List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList();

        boolean getWeak();

        boolean hasCtype();

        boolean hasDeprecated();

        boolean hasExperimentalMapKey();

        boolean hasLazy();

        boolean hasPacked();

        boolean hasWeak();
    }

    public static final class FieldOptions extends ExtendableMessage<FieldOptions> implements FieldOptionsOrBuilder {
        public static final int CTYPE_FIELD_NUMBER = 1;
        public static final int DEPRECATED_FIELD_NUMBER = 3;
        public static final int EXPERIMENTAL_MAP_KEY_FIELD_NUMBER = 9;
        public static final int LAZY_FIELD_NUMBER = 5;
        public static final int PACKED_FIELD_NUMBER = 2;
        public static Parser<FieldOptions> PARSER = new AbstractParser<FieldOptions>() {
            public FieldOptions parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new FieldOptions(input, extensionRegistry);
            }
        };
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        public static final int WEAK_FIELD_NUMBER = 10;
        private static final FieldOptions defaultInstance = new FieldOptions(true);
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private CType ctype_;
        private boolean deprecated_;
        private Object experimentalMapKey_;
        private boolean lazy_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private boolean packed_;
        private List<UninterpretedOption> uninterpretedOption_;
        private final UnknownFieldSet unknownFields;
        private boolean weak_;

        public static final class Builder extends ExtendableBuilder<FieldOptions, Builder> implements FieldOptionsOrBuilder {
            private int bitField0_;
            private CType ctype_;
            private boolean deprecated_;
            private Object experimentalMapKey_;
            private boolean lazy_;
            private boolean packed_;
            private RepeatedFieldBuilder<UninterpretedOption, Builder, UninterpretedOptionOrBuilder> uninterpretedOptionBuilder_;
            private List<UninterpretedOption> uninterpretedOption_;
            private boolean weak_;

            public static final Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_FieldOptions_descriptor;
            }

            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_FieldOptions_fieldAccessorTable.ensureFieldAccessorsInitialized(FieldOptions.class, Builder.class);
            }

            private Builder() {
                this.ctype_ = CType.STRING;
                this.experimentalMapKey_ = "";
                this.uninterpretedOption_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.ctype_ = CType.STRING;
                this.experimentalMapKey_ = "";
                this.uninterpretedOption_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getUninterpretedOptionFieldBuilder();
                }
            }

            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                this.ctype_ = CType.STRING;
                this.bitField0_ &= -2;
                this.packed_ = false;
                this.bitField0_ &= -3;
                this.lazy_ = false;
                this.bitField0_ &= -5;
                this.deprecated_ = false;
                this.bitField0_ &= -9;
                this.experimentalMapKey_ = "";
                this.bitField0_ &= -17;
                this.weak_ = false;
                this.bitField0_ &= -33;
                if (this.uninterpretedOptionBuilder_ == null) {
                    this.uninterpretedOption_ = Collections.emptyList();
                    this.bitField0_ &= -65;
                } else {
                    this.uninterpretedOptionBuilder_.clear();
                }
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_FieldOptions_descriptor;
            }

            public FieldOptions getDefaultInstanceForType() {
                return FieldOptions.getDefaultInstance();
            }

            public FieldOptions build() {
                FieldOptions result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw com.google.protobuf.AbstractMessage.Builder.newUninitializedMessageException(result);
            }

            public FieldOptions buildPartial() {
                FieldOptions result = new FieldOptions((ExtendableBuilder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.ctype_ = this.ctype_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.packed_ = this.packed_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.lazy_ = this.lazy_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.deprecated_ = this.deprecated_;
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 16;
                }
                result.experimentalMapKey_ = this.experimentalMapKey_;
                if ((from_bitField0_ & 32) == 32) {
                    to_bitField0_ |= 32;
                }
                result.weak_ = this.weak_;
                if (this.uninterpretedOptionBuilder_ == null) {
                    if ((this.bitField0_ & 64) == 64) {
                        this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
                        this.bitField0_ &= -65;
                    }
                    result.uninterpretedOption_ = this.uninterpretedOption_;
                } else {
                    result.uninterpretedOption_ = this.uninterpretedOptionBuilder_.build();
                }
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof FieldOptions) {
                    return mergeFrom((FieldOptions) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(FieldOptions other) {
                RepeatedFieldBuilder repeatedFieldBuilder = null;
                if (other != FieldOptions.getDefaultInstance()) {
                    if (other.hasCtype()) {
                        setCtype(other.getCtype());
                    }
                    if (other.hasPacked()) {
                        setPacked(other.getPacked());
                    }
                    if (other.hasLazy()) {
                        setLazy(other.getLazy());
                    }
                    if (other.hasDeprecated()) {
                        setDeprecated(other.getDeprecated());
                    }
                    if (other.hasExperimentalMapKey()) {
                        this.bitField0_ |= 16;
                        this.experimentalMapKey_ = other.experimentalMapKey_;
                        onChanged();
                    }
                    if (other.hasWeak()) {
                        setWeak(other.getWeak());
                    }
                    if (this.uninterpretedOptionBuilder_ == null) {
                        if (!other.uninterpretedOption_.isEmpty()) {
                            if (this.uninterpretedOption_.isEmpty()) {
                                this.uninterpretedOption_ = other.uninterpretedOption_;
                                this.bitField0_ &= -65;
                            } else {
                                ensureUninterpretedOptionIsMutable();
                                this.uninterpretedOption_.addAll(other.uninterpretedOption_);
                            }
                            onChanged();
                        }
                    } else if (!other.uninterpretedOption_.isEmpty()) {
                        if (this.uninterpretedOptionBuilder_.isEmpty()) {
                            this.uninterpretedOptionBuilder_.dispose();
                            this.uninterpretedOptionBuilder_ = null;
                            this.uninterpretedOption_ = other.uninterpretedOption_;
                            this.bitField0_ &= -65;
                            if (GeneratedMessage.alwaysUseFieldBuilders) {
                                repeatedFieldBuilder = getUninterpretedOptionFieldBuilder();
                            }
                            this.uninterpretedOptionBuilder_ = repeatedFieldBuilder;
                        } else {
                            this.uninterpretedOptionBuilder_.addAllMessages(other.uninterpretedOption_);
                        }
                    }
                    mergeExtensionFields(other);
                    mergeUnknownFields(other.getUnknownFields());
                }
                return this;
            }

            public final boolean isInitialized() {
                for (int i = 0; i < getUninterpretedOptionCount(); i++) {
                    if (!getUninterpretedOption(i).isInitialized()) {
                        return false;
                    }
                }
                if (extensionsAreInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                FieldOptions parsedMessage = null;
                try {
                    parsedMessage = (FieldOptions) FieldOptions.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    parsedMessage = (FieldOptions) e.getUnfinishedMessage();
                    throw e;
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
            }

            public boolean hasCtype() {
                return (this.bitField0_ & 1) == 1;
            }

            public CType getCtype() {
                return this.ctype_;
            }

            public Builder setCtype(CType value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.ctype_ = value;
                onChanged();
                return this;
            }

            public Builder clearCtype() {
                this.bitField0_ &= -2;
                this.ctype_ = CType.STRING;
                onChanged();
                return this;
            }

            public boolean hasPacked() {
                return (this.bitField0_ & 2) == 2;
            }

            public boolean getPacked() {
                return this.packed_;
            }

            public Builder setPacked(boolean value) {
                this.bitField0_ |= 2;
                this.packed_ = value;
                onChanged();
                return this;
            }

            public Builder clearPacked() {
                this.bitField0_ &= -3;
                this.packed_ = false;
                onChanged();
                return this;
            }

            public boolean hasLazy() {
                return (this.bitField0_ & 4) == 4;
            }

            public boolean getLazy() {
                return this.lazy_;
            }

            public Builder setLazy(boolean value) {
                this.bitField0_ |= 4;
                this.lazy_ = value;
                onChanged();
                return this;
            }

            public Builder clearLazy() {
                this.bitField0_ &= -5;
                this.lazy_ = false;
                onChanged();
                return this;
            }

            public boolean hasDeprecated() {
                return (this.bitField0_ & 8) == 8;
            }

            public boolean getDeprecated() {
                return this.deprecated_;
            }

            public Builder setDeprecated(boolean value) {
                this.bitField0_ |= 8;
                this.deprecated_ = value;
                onChanged();
                return this;
            }

            public Builder clearDeprecated() {
                this.bitField0_ &= -9;
                this.deprecated_ = false;
                onChanged();
                return this;
            }

            public boolean hasExperimentalMapKey() {
                return (this.bitField0_ & 16) == 16;
            }

            public String getExperimentalMapKey() {
                ByteString ref = this.experimentalMapKey_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.experimentalMapKey_ = s;
                return s;
            }

            public ByteString getExperimentalMapKeyBytes() {
                Object ref = this.experimentalMapKey_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.experimentalMapKey_ = b;
                return b;
            }

            public Builder setExperimentalMapKey(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 16;
                this.experimentalMapKey_ = value;
                onChanged();
                return this;
            }

            public Builder clearExperimentalMapKey() {
                this.bitField0_ &= -17;
                this.experimentalMapKey_ = FieldOptions.getDefaultInstance().getExperimentalMapKey();
                onChanged();
                return this;
            }

            public Builder setExperimentalMapKeyBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 16;
                this.experimentalMapKey_ = value;
                onChanged();
                return this;
            }

            public boolean hasWeak() {
                return (this.bitField0_ & 32) == 32;
            }

            public boolean getWeak() {
                return this.weak_;
            }

            public Builder setWeak(boolean value) {
                this.bitField0_ |= 32;
                this.weak_ = value;
                onChanged();
                return this;
            }

            public Builder clearWeak() {
                this.bitField0_ &= -33;
                this.weak_ = false;
                onChanged();
                return this;
            }

            private void ensureUninterpretedOptionIsMutable() {
                if ((this.bitField0_ & 64) != 64) {
                    this.uninterpretedOption_ = new ArrayList(this.uninterpretedOption_);
                    this.bitField0_ |= 64;
                }
            }

            public List<UninterpretedOption> getUninterpretedOptionList() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    return Collections.unmodifiableList(this.uninterpretedOption_);
                }
                return this.uninterpretedOptionBuilder_.getMessageList();
            }

            public int getUninterpretedOptionCount() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    return this.uninterpretedOption_.size();
                }
                return this.uninterpretedOptionBuilder_.getCount();
            }

            public UninterpretedOption getUninterpretedOption(int index) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    return (UninterpretedOption) this.uninterpretedOption_.get(index);
                }
                return (UninterpretedOption) this.uninterpretedOptionBuilder_.getMessage(index);
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption value) {
                if (this.uninterpretedOptionBuilder_ != null) {
                    this.uninterpretedOptionBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setUninterpretedOption(int index, Builder builderForValue) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption value) {
                if (this.uninterpretedOptionBuilder_ != null) {
                    this.uninterpretedOptionBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption value) {
                if (this.uninterpretedOptionBuilder_ != null) {
                    this.uninterpretedOptionBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addUninterpretedOption(Builder builderForValue) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addUninterpretedOption(int index, Builder builderForValue) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.uninterpretedOption_);
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearUninterpretedOption() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    this.uninterpretedOption_ = Collections.emptyList();
                    this.bitField0_ &= -65;
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.clear();
                }
                return this;
            }

            public Builder removeUninterpretedOption(int index) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.remove(index);
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.remove(index);
                }
                return this;
            }

            public Builder getUninterpretedOptionBuilder(int index) {
                return (Builder) getUninterpretedOptionFieldBuilder().getBuilder(index);
            }

            public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    return (UninterpretedOptionOrBuilder) this.uninterpretedOption_.get(index);
                }
                return (UninterpretedOptionOrBuilder) this.uninterpretedOptionBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
                if (this.uninterpretedOptionBuilder_ != null) {
                    return this.uninterpretedOptionBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.uninterpretedOption_);
            }

            public Builder addUninterpretedOptionBuilder() {
                return (Builder) getUninterpretedOptionFieldBuilder().addBuilder(UninterpretedOption.getDefaultInstance());
            }

            public Builder addUninterpretedOptionBuilder(int index) {
                return (Builder) getUninterpretedOptionFieldBuilder().addBuilder(index, UninterpretedOption.getDefaultInstance());
            }

            public List<Builder> getUninterpretedOptionBuilderList() {
                return getUninterpretedOptionFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<UninterpretedOption, Builder, UninterpretedOptionOrBuilder> getUninterpretedOptionFieldBuilder() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    this.uninterpretedOptionBuilder_ = new RepeatedFieldBuilder(this.uninterpretedOption_, (this.bitField0_ & 64) == 64, getParentForChildren(), isClean());
                    this.uninterpretedOption_ = null;
                }
                return this.uninterpretedOptionBuilder_;
            }
        }

        public enum CType implements ProtocolMessageEnum {
            STRING(0, 0),
            CORD(1, 1),
            STRING_PIECE(2, 2);
            
            public static final int CORD_VALUE = 1;
            public static final int STRING_PIECE_VALUE = 2;
            public static final int STRING_VALUE = 0;
            private static final CType[] VALUES = null;
            private static EnumLiteMap<CType> internalValueMap;
            private final int index;
            private final int value;

            static {
                internalValueMap = new EnumLiteMap<CType>() {
                    public CType findValueByNumber(int number) {
                        return CType.valueOf(number);
                    }
                };
                VALUES = values();
            }

            public final int getNumber() {
                return this.value;
            }

            public static CType valueOf(int value) {
                switch (value) {
                    case 0:
                        return STRING;
                    case 1:
                        return CORD;
                    case 2:
                        return STRING_PIECE;
                    default:
                        return null;
                }
            }

            public static EnumLiteMap<CType> internalGetValueMap() {
                return internalValueMap;
            }

            public final EnumValueDescriptor getValueDescriptor() {
                return (EnumValueDescriptor) getDescriptor().getValues().get(this.index);
            }

            public final EnumDescriptor getDescriptorForType() {
                return getDescriptor();
            }

            public static final EnumDescriptor getDescriptor() {
                return (EnumDescriptor) FieldOptions.getDescriptor().getEnumTypes().get(0);
            }

            public static CType valueOf(EnumValueDescriptor desc) {
                if (desc.getType() == getDescriptor()) {
                    return VALUES[desc.getIndex()];
                }
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            }

            private CType(int index, int value) {
                this.index = index;
                this.value = value;
            }
        }

        private FieldOptions(ExtendableBuilder<FieldOptions, ?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private FieldOptions(boolean noInit) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static FieldOptions getDefaultInstance() {
            return defaultInstance;
        }

        public FieldOptions getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private FieldOptions(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 8:
                            int rawValue = input.readEnum();
                            CType value = CType.valueOf(rawValue);
                            if (value != null) {
                                this.bitField0_ |= 1;
                                this.ctype_ = value;
                                break;
                            }
                            unknownFields.mergeVarintField(1, rawValue);
                            break;
                        case 16:
                            this.bitField0_ |= 2;
                            this.packed_ = input.readBool();
                            break;
                        case 24:
                            this.bitField0_ |= 8;
                            this.deprecated_ = input.readBool();
                            break;
                        case 40:
                            this.bitField0_ |= 4;
                            this.lazy_ = input.readBool();
                            break;
                        case 74:
                            ByteString bs = input.readBytes();
                            this.bitField0_ |= 16;
                            this.experimentalMapKey_ = bs;
                            break;
                        case 80:
                            this.bitField0_ |= 32;
                            this.weak_ = input.readBool();
                            break;
                        case 7994:
                            if ((mutable_bitField0_ & 64) != 64) {
                                this.uninterpretedOption_ = new ArrayList();
                                mutable_bitField0_ |= 64;
                            }
                            this.uninterpretedOption_.add(input.readMessage(UninterpretedOption.PARSER, extensionRegistry));
                            break;
                        default:
                            if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                                break;
                            }
                            break;
                    }
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                } catch (Throwable th) {
                    if ((mutable_bitField0_ & 64) == 64) {
                        this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                }
            }
            if ((mutable_bitField0_ & 64) == 64) {
                this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_FieldOptions_descriptor;
        }

        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_FieldOptions_fieldAccessorTable.ensureFieldAccessorsInitialized(FieldOptions.class, Builder.class);
        }

        static {
            defaultInstance.initFields();
        }

        public Parser<FieldOptions> getParserForType() {
            return PARSER;
        }

        public boolean hasCtype() {
            return (this.bitField0_ & 1) == 1;
        }

        public CType getCtype() {
            return this.ctype_;
        }

        public boolean hasPacked() {
            return (this.bitField0_ & 2) == 2;
        }

        public boolean getPacked() {
            return this.packed_;
        }

        public boolean hasLazy() {
            return (this.bitField0_ & 4) == 4;
        }

        public boolean getLazy() {
            return this.lazy_;
        }

        public boolean hasDeprecated() {
            return (this.bitField0_ & 8) == 8;
        }

        public boolean getDeprecated() {
            return this.deprecated_;
        }

        public boolean hasExperimentalMapKey() {
            return (this.bitField0_ & 16) == 16;
        }

        public String getExperimentalMapKey() {
            ByteString ref = this.experimentalMapKey_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.experimentalMapKey_ = s;
            }
            return s;
        }

        public ByteString getExperimentalMapKeyBytes() {
            Object ref = this.experimentalMapKey_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.experimentalMapKey_ = b;
            return b;
        }

        public boolean hasWeak() {
            return (this.bitField0_ & 32) == 32;
        }

        public boolean getWeak() {
            return this.weak_;
        }

        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        public UninterpretedOption getUninterpretedOption(int index) {
            return (UninterpretedOption) this.uninterpretedOption_.get(index);
        }

        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
            return (UninterpretedOptionOrBuilder) this.uninterpretedOption_.get(index);
        }

        private void initFields() {
            this.ctype_ = CType.STRING;
            this.packed_ = false;
            this.lazy_ = false;
            this.deprecated_ = false;
            this.experimentalMapKey_ = "";
            this.weak_ = false;
            this.uninterpretedOption_ = Collections.emptyList();
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == (byte) 1) {
                return true;
            }
            if (isInitialized == (byte) 0) {
                return false;
            }
            int i = 0;
            while (i < getUninterpretedOptionCount()) {
                if (getUninterpretedOption(i).isInitialized()) {
                    i++;
                } else {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (extensionsAreInitialized()) {
                this.memoizedIsInitialized = (byte) 1;
                return true;
            }
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            getSerializedSize();
            ExtensionWriter extensionWriter = newExtensionWriter();
            if ((this.bitField0_ & 1) == 1) {
                output.writeEnum(1, this.ctype_.getNumber());
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeBool(2, this.packed_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeBool(3, this.deprecated_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeBool(5, this.lazy_);
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeBytes(9, getExperimentalMapKeyBytes());
            }
            if ((this.bitField0_ & 32) == 32) {
                output.writeBool(10, this.weak_);
            }
            for (int i = 0; i < this.uninterpretedOption_.size(); i++) {
                output.writeMessage(999, (MessageLite) this.uninterpretedOption_.get(i));
            }
            extensionWriter.writeUntil(536870912, output);
            getUnknownFields().writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
                size = 0 + CodedOutputStream.computeEnumSize(1, this.ctype_.getNumber());
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeBoolSize(2, this.packed_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size += CodedOutputStream.computeBoolSize(3, this.deprecated_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size += CodedOutputStream.computeBoolSize(5, this.lazy_);
            }
            if ((this.bitField0_ & 16) == 16) {
                size += CodedOutputStream.computeBytesSize(9, getExperimentalMapKeyBytes());
            }
            if ((this.bitField0_ & 32) == 32) {
                size += CodedOutputStream.computeBoolSize(10, this.weak_);
            }
            for (int i = 0; i < this.uninterpretedOption_.size(); i++) {
                size += CodedOutputStream.computeMessageSize(999, (MessageLite) this.uninterpretedOption_.get(i));
            }
            size = (size + extensionsSerializedSize()) + getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = size;
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static FieldOptions parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (FieldOptions) PARSER.parseFrom(data);
        }

        public static FieldOptions parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FieldOptions) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FieldOptions parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (FieldOptions) PARSER.parseFrom(data);
        }

        public static FieldOptions parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FieldOptions) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FieldOptions parseFrom(InputStream input) throws IOException {
            return (FieldOptions) PARSER.parseFrom(input);
        }

        public static FieldOptions parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FieldOptions) PARSER.parseFrom(input, extensionRegistry);
        }

        public static FieldOptions parseDelimitedFrom(InputStream input) throws IOException {
            return (FieldOptions) PARSER.parseDelimitedFrom(input);
        }

        public static FieldOptions parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FieldOptions) PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static FieldOptions parseFrom(CodedInputStream input) throws IOException {
            return (FieldOptions) PARSER.parseFrom(input);
        }

        public static FieldOptions parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FieldOptions) PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(FieldOptions prototype) {
            return newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return newBuilder(this);
        }

        protected Builder newBuilderForType(BuilderParent parent) {
            return new Builder(parent);
        }
    }

    public interface FileDescriptorProtoOrBuilder extends MessageOrBuilder {
        String getDependency(int i);

        ByteString getDependencyBytes(int i);

        int getDependencyCount();

        ProtocolStringList getDependencyList();

        EnumDescriptorProto getEnumType(int i);

        int getEnumTypeCount();

        List<EnumDescriptorProto> getEnumTypeList();

        EnumDescriptorProtoOrBuilder getEnumTypeOrBuilder(int i);

        List<? extends EnumDescriptorProtoOrBuilder> getEnumTypeOrBuilderList();

        FieldDescriptorProto getExtension(int i);

        int getExtensionCount();

        List<FieldDescriptorProto> getExtensionList();

        FieldDescriptorProtoOrBuilder getExtensionOrBuilder(int i);

        List<? extends FieldDescriptorProtoOrBuilder> getExtensionOrBuilderList();

        DescriptorProto getMessageType(int i);

        int getMessageTypeCount();

        List<DescriptorProto> getMessageTypeList();

        DescriptorProtoOrBuilder getMessageTypeOrBuilder(int i);

        List<? extends DescriptorProtoOrBuilder> getMessageTypeOrBuilderList();

        String getName();

        ByteString getNameBytes();

        FileOptions getOptions();

        FileOptionsOrBuilder getOptionsOrBuilder();

        String getPackage();

        ByteString getPackageBytes();

        int getPublicDependency(int i);

        int getPublicDependencyCount();

        List<Integer> getPublicDependencyList();

        ServiceDescriptorProto getService(int i);

        int getServiceCount();

        List<ServiceDescriptorProto> getServiceList();

        ServiceDescriptorProtoOrBuilder getServiceOrBuilder(int i);

        List<? extends ServiceDescriptorProtoOrBuilder> getServiceOrBuilderList();

        SourceCodeInfo getSourceCodeInfo();

        SourceCodeInfoOrBuilder getSourceCodeInfoOrBuilder();

        int getWeakDependency(int i);

        int getWeakDependencyCount();

        List<Integer> getWeakDependencyList();

        boolean hasName();

        boolean hasOptions();

        boolean hasPackage();

        boolean hasSourceCodeInfo();
    }

    public static final class FileDescriptorProto extends GeneratedMessage implements FileDescriptorProtoOrBuilder {
        public static final int DEPENDENCY_FIELD_NUMBER = 3;
        public static final int ENUM_TYPE_FIELD_NUMBER = 5;
        public static final int EXTENSION_FIELD_NUMBER = 7;
        public static final int MESSAGE_TYPE_FIELD_NUMBER = 4;
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int OPTIONS_FIELD_NUMBER = 8;
        public static final int PACKAGE_FIELD_NUMBER = 2;
        public static Parser<FileDescriptorProto> PARSER = new AbstractParser<FileDescriptorProto>() {
            public FileDescriptorProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new FileDescriptorProto(input, extensionRegistry);
            }
        };
        public static final int PUBLIC_DEPENDENCY_FIELD_NUMBER = 10;
        public static final int SERVICE_FIELD_NUMBER = 6;
        public static final int SOURCE_CODE_INFO_FIELD_NUMBER = 9;
        public static final int WEAK_DEPENDENCY_FIELD_NUMBER = 11;
        private static final FileDescriptorProto defaultInstance = new FileDescriptorProto(true);
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private LazyStringList dependency_;
        private List<EnumDescriptorProto> enumType_;
        private List<FieldDescriptorProto> extension_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private List<DescriptorProto> messageType_;
        private Object name_;
        private FileOptions options_;
        private Object package_;
        private List<Integer> publicDependency_;
        private List<ServiceDescriptorProto> service_;
        private SourceCodeInfo sourceCodeInfo_;
        private final UnknownFieldSet unknownFields;
        private List<Integer> weakDependency_;

        public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> implements FileDescriptorProtoOrBuilder {
            private int bitField0_;
            private LazyStringList dependency_;
            private RepeatedFieldBuilder<EnumDescriptorProto, Builder, EnumDescriptorProtoOrBuilder> enumTypeBuilder_;
            private List<EnumDescriptorProto> enumType_;
            private RepeatedFieldBuilder<FieldDescriptorProto, Builder, FieldDescriptorProtoOrBuilder> extensionBuilder_;
            private List<FieldDescriptorProto> extension_;
            private RepeatedFieldBuilder<DescriptorProto, Builder, DescriptorProtoOrBuilder> messageTypeBuilder_;
            private List<DescriptorProto> messageType_;
            private Object name_;
            private SingleFieldBuilder<FileOptions, Builder, FileOptionsOrBuilder> optionsBuilder_;
            private FileOptions options_;
            private Object package_;
            private List<Integer> publicDependency_;
            private RepeatedFieldBuilder<ServiceDescriptorProto, Builder, ServiceDescriptorProtoOrBuilder> serviceBuilder_;
            private List<ServiceDescriptorProto> service_;
            private SingleFieldBuilder<SourceCodeInfo, Builder, SourceCodeInfoOrBuilder> sourceCodeInfoBuilder_;
            private SourceCodeInfo sourceCodeInfo_;
            private List<Integer> weakDependency_;

            public static final Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_FileDescriptorProto_descriptor;
            }

            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_FileDescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(FileDescriptorProto.class, Builder.class);
            }

            private Builder() {
                this.name_ = "";
                this.package_ = "";
                this.dependency_ = LazyStringArrayList.EMPTY;
                this.publicDependency_ = Collections.emptyList();
                this.weakDependency_ = Collections.emptyList();
                this.messageType_ = Collections.emptyList();
                this.enumType_ = Collections.emptyList();
                this.service_ = Collections.emptyList();
                this.extension_ = Collections.emptyList();
                this.options_ = FileOptions.getDefaultInstance();
                this.sourceCodeInfo_ = SourceCodeInfo.getDefaultInstance();
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.name_ = "";
                this.package_ = "";
                this.dependency_ = LazyStringArrayList.EMPTY;
                this.publicDependency_ = Collections.emptyList();
                this.weakDependency_ = Collections.emptyList();
                this.messageType_ = Collections.emptyList();
                this.enumType_ = Collections.emptyList();
                this.service_ = Collections.emptyList();
                this.extension_ = Collections.emptyList();
                this.options_ = FileOptions.getDefaultInstance();
                this.sourceCodeInfo_ = SourceCodeInfo.getDefaultInstance();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getMessageTypeFieldBuilder();
                    getEnumTypeFieldBuilder();
                    getServiceFieldBuilder();
                    getExtensionFieldBuilder();
                    getOptionsFieldBuilder();
                    getSourceCodeInfoFieldBuilder();
                }
            }

            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                this.name_ = "";
                this.bitField0_ &= -2;
                this.package_ = "";
                this.bitField0_ &= -3;
                this.dependency_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= -5;
                this.publicDependency_ = Collections.emptyList();
                this.bitField0_ &= -9;
                this.weakDependency_ = Collections.emptyList();
                this.bitField0_ &= -17;
                if (this.messageTypeBuilder_ == null) {
                    this.messageType_ = Collections.emptyList();
                    this.bitField0_ &= -33;
                } else {
                    this.messageTypeBuilder_.clear();
                }
                if (this.enumTypeBuilder_ == null) {
                    this.enumType_ = Collections.emptyList();
                    this.bitField0_ &= -65;
                } else {
                    this.enumTypeBuilder_.clear();
                }
                if (this.serviceBuilder_ == null) {
                    this.service_ = Collections.emptyList();
                    this.bitField0_ &= -129;
                } else {
                    this.serviceBuilder_.clear();
                }
                if (this.extensionBuilder_ == null) {
                    this.extension_ = Collections.emptyList();
                    this.bitField0_ &= -257;
                } else {
                    this.extensionBuilder_.clear();
                }
                if (this.optionsBuilder_ == null) {
                    this.options_ = FileOptions.getDefaultInstance();
                } else {
                    this.optionsBuilder_.clear();
                }
                this.bitField0_ &= -513;
                if (this.sourceCodeInfoBuilder_ == null) {
                    this.sourceCodeInfo_ = SourceCodeInfo.getDefaultInstance();
                } else {
                    this.sourceCodeInfoBuilder_.clear();
                }
                this.bitField0_ &= -1025;
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_FileDescriptorProto_descriptor;
            }

            public FileDescriptorProto getDefaultInstanceForType() {
                return FileDescriptorProto.getDefaultInstance();
            }

            public FileDescriptorProto build() {
                FileDescriptorProto result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw com.google.protobuf.AbstractMessage.Builder.newUninitializedMessageException(result);
            }

            public FileDescriptorProto buildPartial() {
                FileDescriptorProto result = new FileDescriptorProto((com.google.protobuf.GeneratedMessage.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.name_ = this.name_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.package_ = this.package_;
                if ((this.bitField0_ & 4) == 4) {
                    this.dependency_ = this.dependency_.getUnmodifiableView();
                    this.bitField0_ &= -5;
                }
                result.dependency_ = this.dependency_;
                if ((this.bitField0_ & 8) == 8) {
                    this.publicDependency_ = Collections.unmodifiableList(this.publicDependency_);
                    this.bitField0_ &= -9;
                }
                result.publicDependency_ = this.publicDependency_;
                if ((this.bitField0_ & 16) == 16) {
                    this.weakDependency_ = Collections.unmodifiableList(this.weakDependency_);
                    this.bitField0_ &= -17;
                }
                result.weakDependency_ = this.weakDependency_;
                if (this.messageTypeBuilder_ == null) {
                    if ((this.bitField0_ & 32) == 32) {
                        this.messageType_ = Collections.unmodifiableList(this.messageType_);
                        this.bitField0_ &= -33;
                    }
                    result.messageType_ = this.messageType_;
                } else {
                    result.messageType_ = this.messageTypeBuilder_.build();
                }
                if (this.enumTypeBuilder_ == null) {
                    if ((this.bitField0_ & 64) == 64) {
                        this.enumType_ = Collections.unmodifiableList(this.enumType_);
                        this.bitField0_ &= -65;
                    }
                    result.enumType_ = this.enumType_;
                } else {
                    result.enumType_ = this.enumTypeBuilder_.build();
                }
                if (this.serviceBuilder_ == null) {
                    if ((this.bitField0_ & 128) == 128) {
                        this.service_ = Collections.unmodifiableList(this.service_);
                        this.bitField0_ &= -129;
                    }
                    result.service_ = this.service_;
                } else {
                    result.service_ = this.serviceBuilder_.build();
                }
                if (this.extensionBuilder_ == null) {
                    if ((this.bitField0_ & 256) == 256) {
                        this.extension_ = Collections.unmodifiableList(this.extension_);
                        this.bitField0_ &= -257;
                    }
                    result.extension_ = this.extension_;
                } else {
                    result.extension_ = this.extensionBuilder_.build();
                }
                if ((from_bitField0_ & 512) == 512) {
                    to_bitField0_ |= 4;
                }
                if (this.optionsBuilder_ == null) {
                    result.options_ = this.options_;
                } else {
                    result.options_ = (FileOptions) this.optionsBuilder_.build();
                }
                if ((from_bitField0_ & 1024) == 1024) {
                    to_bitField0_ |= 8;
                }
                if (this.sourceCodeInfoBuilder_ == null) {
                    result.sourceCodeInfo_ = this.sourceCodeInfo_;
                } else {
                    result.sourceCodeInfo_ = (SourceCodeInfo) this.sourceCodeInfoBuilder_.build();
                }
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof FileDescriptorProto) {
                    return mergeFrom((FileDescriptorProto) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(FileDescriptorProto other) {
                RepeatedFieldBuilder repeatedFieldBuilder = null;
                if (other != FileDescriptorProto.getDefaultInstance()) {
                    if (other.hasName()) {
                        this.bitField0_ |= 1;
                        this.name_ = other.name_;
                        onChanged();
                    }
                    if (other.hasPackage()) {
                        this.bitField0_ |= 2;
                        this.package_ = other.package_;
                        onChanged();
                    }
                    if (!other.dependency_.isEmpty()) {
                        if (this.dependency_.isEmpty()) {
                            this.dependency_ = other.dependency_;
                            this.bitField0_ &= -5;
                        } else {
                            ensureDependencyIsMutable();
                            this.dependency_.addAll(other.dependency_);
                        }
                        onChanged();
                    }
                    if (!other.publicDependency_.isEmpty()) {
                        if (this.publicDependency_.isEmpty()) {
                            this.publicDependency_ = other.publicDependency_;
                            this.bitField0_ &= -9;
                        } else {
                            ensurePublicDependencyIsMutable();
                            this.publicDependency_.addAll(other.publicDependency_);
                        }
                        onChanged();
                    }
                    if (!other.weakDependency_.isEmpty()) {
                        if (this.weakDependency_.isEmpty()) {
                            this.weakDependency_ = other.weakDependency_;
                            this.bitField0_ &= -17;
                        } else {
                            ensureWeakDependencyIsMutable();
                            this.weakDependency_.addAll(other.weakDependency_);
                        }
                        onChanged();
                    }
                    if (this.messageTypeBuilder_ == null) {
                        if (!other.messageType_.isEmpty()) {
                            if (this.messageType_.isEmpty()) {
                                this.messageType_ = other.messageType_;
                                this.bitField0_ &= -33;
                            } else {
                                ensureMessageTypeIsMutable();
                                this.messageType_.addAll(other.messageType_);
                            }
                            onChanged();
                        }
                    } else if (!other.messageType_.isEmpty()) {
                        if (this.messageTypeBuilder_.isEmpty()) {
                            this.messageTypeBuilder_.dispose();
                            this.messageTypeBuilder_ = null;
                            this.messageType_ = other.messageType_;
                            this.bitField0_ &= -33;
                            this.messageTypeBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? getMessageTypeFieldBuilder() : null;
                        } else {
                            this.messageTypeBuilder_.addAllMessages(other.messageType_);
                        }
                    }
                    if (this.enumTypeBuilder_ == null) {
                        if (!other.enumType_.isEmpty()) {
                            if (this.enumType_.isEmpty()) {
                                this.enumType_ = other.enumType_;
                                this.bitField0_ &= -65;
                            } else {
                                ensureEnumTypeIsMutable();
                                this.enumType_.addAll(other.enumType_);
                            }
                            onChanged();
                        }
                    } else if (!other.enumType_.isEmpty()) {
                        if (this.enumTypeBuilder_.isEmpty()) {
                            this.enumTypeBuilder_.dispose();
                            this.enumTypeBuilder_ = null;
                            this.enumType_ = other.enumType_;
                            this.bitField0_ &= -65;
                            this.enumTypeBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? getEnumTypeFieldBuilder() : null;
                        } else {
                            this.enumTypeBuilder_.addAllMessages(other.enumType_);
                        }
                    }
                    if (this.serviceBuilder_ == null) {
                        if (!other.service_.isEmpty()) {
                            if (this.service_.isEmpty()) {
                                this.service_ = other.service_;
                                this.bitField0_ &= -129;
                            } else {
                                ensureServiceIsMutable();
                                this.service_.addAll(other.service_);
                            }
                            onChanged();
                        }
                    } else if (!other.service_.isEmpty()) {
                        if (this.serviceBuilder_.isEmpty()) {
                            this.serviceBuilder_.dispose();
                            this.serviceBuilder_ = null;
                            this.service_ = other.service_;
                            this.bitField0_ &= -129;
                            this.serviceBuilder_ = GeneratedMessage.alwaysUseFieldBuilders ? getServiceFieldBuilder() : null;
                        } else {
                            this.serviceBuilder_.addAllMessages(other.service_);
                        }
                    }
                    if (this.extensionBuilder_ == null) {
                        if (!other.extension_.isEmpty()) {
                            if (this.extension_.isEmpty()) {
                                this.extension_ = other.extension_;
                                this.bitField0_ &= -257;
                            } else {
                                ensureExtensionIsMutable();
                                this.extension_.addAll(other.extension_);
                            }
                            onChanged();
                        }
                    } else if (!other.extension_.isEmpty()) {
                        if (this.extensionBuilder_.isEmpty()) {
                            this.extensionBuilder_.dispose();
                            this.extensionBuilder_ = null;
                            this.extension_ = other.extension_;
                            this.bitField0_ &= -257;
                            if (GeneratedMessage.alwaysUseFieldBuilders) {
                                repeatedFieldBuilder = getExtensionFieldBuilder();
                            }
                            this.extensionBuilder_ = repeatedFieldBuilder;
                        } else {
                            this.extensionBuilder_.addAllMessages(other.extension_);
                        }
                    }
                    if (other.hasOptions()) {
                        mergeOptions(other.getOptions());
                    }
                    if (other.hasSourceCodeInfo()) {
                        mergeSourceCodeInfo(other.getSourceCodeInfo());
                    }
                    mergeUnknownFields(other.getUnknownFields());
                }
                return this;
            }

            public final boolean isInitialized() {
                int i;
                for (i = 0; i < getMessageTypeCount(); i++) {
                    if (!getMessageType(i).isInitialized()) {
                        return false;
                    }
                }
                for (i = 0; i < getEnumTypeCount(); i++) {
                    if (!getEnumType(i).isInitialized()) {
                        return false;
                    }
                }
                for (i = 0; i < getServiceCount(); i++) {
                    if (!getService(i).isInitialized()) {
                        return false;
                    }
                }
                for (i = 0; i < getExtensionCount(); i++) {
                    if (!getExtension(i).isInitialized()) {
                        return false;
                    }
                }
                if (!hasOptions() || getOptions().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                FileDescriptorProto parsedMessage = null;
                try {
                    parsedMessage = (FileDescriptorProto) FileDescriptorProto.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    parsedMessage = (FileDescriptorProto) e.getUnfinishedMessage();
                    throw e;
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
            }

            public boolean hasName() {
                return (this.bitField0_ & 1) == 1;
            }

            public String getName() {
                ByteString ref = this.name_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.name_ = s;
                return s;
            }

            public ByteString getNameBytes() {
                Object ref = this.name_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.name_ = b;
                return b;
            }

            public Builder setName(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.name_ = value;
                onChanged();
                return this;
            }

            public Builder clearName() {
                this.bitField0_ &= -2;
                this.name_ = FileDescriptorProto.getDefaultInstance().getName();
                onChanged();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.name_ = value;
                onChanged();
                return this;
            }

            public boolean hasPackage() {
                return (this.bitField0_ & 2) == 2;
            }

            public String getPackage() {
                ByteString ref = this.package_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.package_ = s;
                return s;
            }

            public ByteString getPackageBytes() {
                Object ref = this.package_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.package_ = b;
                return b;
            }

            public Builder setPackage(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.package_ = value;
                onChanged();
                return this;
            }

            public Builder clearPackage() {
                this.bitField0_ &= -3;
                this.package_ = FileDescriptorProto.getDefaultInstance().getPackage();
                onChanged();
                return this;
            }

            public Builder setPackageBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.package_ = value;
                onChanged();
                return this;
            }

            private void ensureDependencyIsMutable() {
                if ((this.bitField0_ & 4) != 4) {
                    this.dependency_ = new LazyStringArrayList(this.dependency_);
                    this.bitField0_ |= 4;
                }
            }

            public ProtocolStringList getDependencyList() {
                return this.dependency_.getUnmodifiableView();
            }

            public int getDependencyCount() {
                return this.dependency_.size();
            }

            public String getDependency(int index) {
                return (String) this.dependency_.get(index);
            }

            public ByteString getDependencyBytes(int index) {
                return this.dependency_.getByteString(index);
            }

            public Builder setDependency(int index, String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ensureDependencyIsMutable();
                this.dependency_.set(index, value);
                onChanged();
                return this;
            }

            public Builder addDependency(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ensureDependencyIsMutable();
                this.dependency_.add(value);
                onChanged();
                return this;
            }

            public Builder addAllDependency(Iterable<String> values) {
                ensureDependencyIsMutable();
                com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.dependency_);
                onChanged();
                return this;
            }

            public Builder clearDependency() {
                this.dependency_ = LazyStringArrayList.EMPTY;
                this.bitField0_ &= -5;
                onChanged();
                return this;
            }

            public Builder addDependencyBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                ensureDependencyIsMutable();
                this.dependency_.add(value);
                onChanged();
                return this;
            }

            private void ensurePublicDependencyIsMutable() {
                if ((this.bitField0_ & 8) != 8) {
                    this.publicDependency_ = new ArrayList(this.publicDependency_);
                    this.bitField0_ |= 8;
                }
            }

            public List<Integer> getPublicDependencyList() {
                return Collections.unmodifiableList(this.publicDependency_);
            }

            public int getPublicDependencyCount() {
                return this.publicDependency_.size();
            }

            public int getPublicDependency(int index) {
                return ((Integer) this.publicDependency_.get(index)).intValue();
            }

            public Builder setPublicDependency(int index, int value) {
                ensurePublicDependencyIsMutable();
                this.publicDependency_.set(index, Integer.valueOf(value));
                onChanged();
                return this;
            }

            public Builder addPublicDependency(int value) {
                ensurePublicDependencyIsMutable();
                this.publicDependency_.add(Integer.valueOf(value));
                onChanged();
                return this;
            }

            public Builder addAllPublicDependency(Iterable<? extends Integer> values) {
                ensurePublicDependencyIsMutable();
                com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.publicDependency_);
                onChanged();
                return this;
            }

            public Builder clearPublicDependency() {
                this.publicDependency_ = Collections.emptyList();
                this.bitField0_ &= -9;
                onChanged();
                return this;
            }

            private void ensureWeakDependencyIsMutable() {
                if ((this.bitField0_ & 16) != 16) {
                    this.weakDependency_ = new ArrayList(this.weakDependency_);
                    this.bitField0_ |= 16;
                }
            }

            public List<Integer> getWeakDependencyList() {
                return Collections.unmodifiableList(this.weakDependency_);
            }

            public int getWeakDependencyCount() {
                return this.weakDependency_.size();
            }

            public int getWeakDependency(int index) {
                return ((Integer) this.weakDependency_.get(index)).intValue();
            }

            public Builder setWeakDependency(int index, int value) {
                ensureWeakDependencyIsMutable();
                this.weakDependency_.set(index, Integer.valueOf(value));
                onChanged();
                return this;
            }

            public Builder addWeakDependency(int value) {
                ensureWeakDependencyIsMutable();
                this.weakDependency_.add(Integer.valueOf(value));
                onChanged();
                return this;
            }

            public Builder addAllWeakDependency(Iterable<? extends Integer> values) {
                ensureWeakDependencyIsMutable();
                com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.weakDependency_);
                onChanged();
                return this;
            }

            public Builder clearWeakDependency() {
                this.weakDependency_ = Collections.emptyList();
                this.bitField0_ &= -17;
                onChanged();
                return this;
            }

            private void ensureMessageTypeIsMutable() {
                if ((this.bitField0_ & 32) != 32) {
                    this.messageType_ = new ArrayList(this.messageType_);
                    this.bitField0_ |= 32;
                }
            }

            public List<DescriptorProto> getMessageTypeList() {
                if (this.messageTypeBuilder_ == null) {
                    return Collections.unmodifiableList(this.messageType_);
                }
                return this.messageTypeBuilder_.getMessageList();
            }

            public int getMessageTypeCount() {
                if (this.messageTypeBuilder_ == null) {
                    return this.messageType_.size();
                }
                return this.messageTypeBuilder_.getCount();
            }

            public DescriptorProto getMessageType(int index) {
                if (this.messageTypeBuilder_ == null) {
                    return (DescriptorProto) this.messageType_.get(index);
                }
                return (DescriptorProto) this.messageTypeBuilder_.getMessage(index);
            }

            public Builder setMessageType(int index, DescriptorProto value) {
                if (this.messageTypeBuilder_ != null) {
                    this.messageTypeBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureMessageTypeIsMutable();
                    this.messageType_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setMessageType(int index, Builder builderForValue) {
                if (this.messageTypeBuilder_ == null) {
                    ensureMessageTypeIsMutable();
                    this.messageType_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.messageTypeBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addMessageType(DescriptorProto value) {
                if (this.messageTypeBuilder_ != null) {
                    this.messageTypeBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureMessageTypeIsMutable();
                    this.messageType_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addMessageType(int index, DescriptorProto value) {
                if (this.messageTypeBuilder_ != null) {
                    this.messageTypeBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureMessageTypeIsMutable();
                    this.messageType_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addMessageType(Builder builderForValue) {
                if (this.messageTypeBuilder_ == null) {
                    ensureMessageTypeIsMutable();
                    this.messageType_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.messageTypeBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addMessageType(int index, Builder builderForValue) {
                if (this.messageTypeBuilder_ == null) {
                    ensureMessageTypeIsMutable();
                    this.messageType_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.messageTypeBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllMessageType(Iterable<? extends DescriptorProto> values) {
                if (this.messageTypeBuilder_ == null) {
                    ensureMessageTypeIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.messageType_);
                    onChanged();
                } else {
                    this.messageTypeBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearMessageType() {
                if (this.messageTypeBuilder_ == null) {
                    this.messageType_ = Collections.emptyList();
                    this.bitField0_ &= -33;
                    onChanged();
                } else {
                    this.messageTypeBuilder_.clear();
                }
                return this;
            }

            public Builder removeMessageType(int index) {
                if (this.messageTypeBuilder_ == null) {
                    ensureMessageTypeIsMutable();
                    this.messageType_.remove(index);
                    onChanged();
                } else {
                    this.messageTypeBuilder_.remove(index);
                }
                return this;
            }

            public Builder getMessageTypeBuilder(int index) {
                return (Builder) getMessageTypeFieldBuilder().getBuilder(index);
            }

            public DescriptorProtoOrBuilder getMessageTypeOrBuilder(int index) {
                if (this.messageTypeBuilder_ == null) {
                    return (DescriptorProtoOrBuilder) this.messageType_.get(index);
                }
                return (DescriptorProtoOrBuilder) this.messageTypeBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends DescriptorProtoOrBuilder> getMessageTypeOrBuilderList() {
                if (this.messageTypeBuilder_ != null) {
                    return this.messageTypeBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.messageType_);
            }

            public Builder addMessageTypeBuilder() {
                return (Builder) getMessageTypeFieldBuilder().addBuilder(DescriptorProto.getDefaultInstance());
            }

            public Builder addMessageTypeBuilder(int index) {
                return (Builder) getMessageTypeFieldBuilder().addBuilder(index, DescriptorProto.getDefaultInstance());
            }

            public List<Builder> getMessageTypeBuilderList() {
                return getMessageTypeFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<DescriptorProto, Builder, DescriptorProtoOrBuilder> getMessageTypeFieldBuilder() {
                if (this.messageTypeBuilder_ == null) {
                    this.messageTypeBuilder_ = new RepeatedFieldBuilder(this.messageType_, (this.bitField0_ & 32) == 32, getParentForChildren(), isClean());
                    this.messageType_ = null;
                }
                return this.messageTypeBuilder_;
            }

            private void ensureEnumTypeIsMutable() {
                if ((this.bitField0_ & 64) != 64) {
                    this.enumType_ = new ArrayList(this.enumType_);
                    this.bitField0_ |= 64;
                }
            }

            public List<EnumDescriptorProto> getEnumTypeList() {
                if (this.enumTypeBuilder_ == null) {
                    return Collections.unmodifiableList(this.enumType_);
                }
                return this.enumTypeBuilder_.getMessageList();
            }

            public int getEnumTypeCount() {
                if (this.enumTypeBuilder_ == null) {
                    return this.enumType_.size();
                }
                return this.enumTypeBuilder_.getCount();
            }

            public EnumDescriptorProto getEnumType(int index) {
                if (this.enumTypeBuilder_ == null) {
                    return (EnumDescriptorProto) this.enumType_.get(index);
                }
                return (EnumDescriptorProto) this.enumTypeBuilder_.getMessage(index);
            }

            public Builder setEnumType(int index, EnumDescriptorProto value) {
                if (this.enumTypeBuilder_ != null) {
                    this.enumTypeBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureEnumTypeIsMutable();
                    this.enumType_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setEnumType(int index, Builder builderForValue) {
                if (this.enumTypeBuilder_ == null) {
                    ensureEnumTypeIsMutable();
                    this.enumType_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.enumTypeBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addEnumType(EnumDescriptorProto value) {
                if (this.enumTypeBuilder_ != null) {
                    this.enumTypeBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureEnumTypeIsMutable();
                    this.enumType_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addEnumType(int index, EnumDescriptorProto value) {
                if (this.enumTypeBuilder_ != null) {
                    this.enumTypeBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureEnumTypeIsMutable();
                    this.enumType_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addEnumType(Builder builderForValue) {
                if (this.enumTypeBuilder_ == null) {
                    ensureEnumTypeIsMutable();
                    this.enumType_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.enumTypeBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addEnumType(int index, Builder builderForValue) {
                if (this.enumTypeBuilder_ == null) {
                    ensureEnumTypeIsMutable();
                    this.enumType_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.enumTypeBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllEnumType(Iterable<? extends EnumDescriptorProto> values) {
                if (this.enumTypeBuilder_ == null) {
                    ensureEnumTypeIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.enumType_);
                    onChanged();
                } else {
                    this.enumTypeBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearEnumType() {
                if (this.enumTypeBuilder_ == null) {
                    this.enumType_ = Collections.emptyList();
                    this.bitField0_ &= -65;
                    onChanged();
                } else {
                    this.enumTypeBuilder_.clear();
                }
                return this;
            }

            public Builder removeEnumType(int index) {
                if (this.enumTypeBuilder_ == null) {
                    ensureEnumTypeIsMutable();
                    this.enumType_.remove(index);
                    onChanged();
                } else {
                    this.enumTypeBuilder_.remove(index);
                }
                return this;
            }

            public Builder getEnumTypeBuilder(int index) {
                return (Builder) getEnumTypeFieldBuilder().getBuilder(index);
            }

            public EnumDescriptorProtoOrBuilder getEnumTypeOrBuilder(int index) {
                if (this.enumTypeBuilder_ == null) {
                    return (EnumDescriptorProtoOrBuilder) this.enumType_.get(index);
                }
                return (EnumDescriptorProtoOrBuilder) this.enumTypeBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends EnumDescriptorProtoOrBuilder> getEnumTypeOrBuilderList() {
                if (this.enumTypeBuilder_ != null) {
                    return this.enumTypeBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.enumType_);
            }

            public Builder addEnumTypeBuilder() {
                return (Builder) getEnumTypeFieldBuilder().addBuilder(EnumDescriptorProto.getDefaultInstance());
            }

            public Builder addEnumTypeBuilder(int index) {
                return (Builder) getEnumTypeFieldBuilder().addBuilder(index, EnumDescriptorProto.getDefaultInstance());
            }

            public List<Builder> getEnumTypeBuilderList() {
                return getEnumTypeFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<EnumDescriptorProto, Builder, EnumDescriptorProtoOrBuilder> getEnumTypeFieldBuilder() {
                if (this.enumTypeBuilder_ == null) {
                    this.enumTypeBuilder_ = new RepeatedFieldBuilder(this.enumType_, (this.bitField0_ & 64) == 64, getParentForChildren(), isClean());
                    this.enumType_ = null;
                }
                return this.enumTypeBuilder_;
            }

            private void ensureServiceIsMutable() {
                if ((this.bitField0_ & 128) != 128) {
                    this.service_ = new ArrayList(this.service_);
                    this.bitField0_ |= 128;
                }
            }

            public List<ServiceDescriptorProto> getServiceList() {
                if (this.serviceBuilder_ == null) {
                    return Collections.unmodifiableList(this.service_);
                }
                return this.serviceBuilder_.getMessageList();
            }

            public int getServiceCount() {
                if (this.serviceBuilder_ == null) {
                    return this.service_.size();
                }
                return this.serviceBuilder_.getCount();
            }

            public ServiceDescriptorProto getService(int index) {
                if (this.serviceBuilder_ == null) {
                    return (ServiceDescriptorProto) this.service_.get(index);
                }
                return (ServiceDescriptorProto) this.serviceBuilder_.getMessage(index);
            }

            public Builder setService(int index, ServiceDescriptorProto value) {
                if (this.serviceBuilder_ != null) {
                    this.serviceBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureServiceIsMutable();
                    this.service_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setService(int index, Builder builderForValue) {
                if (this.serviceBuilder_ == null) {
                    ensureServiceIsMutable();
                    this.service_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.serviceBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addService(ServiceDescriptorProto value) {
                if (this.serviceBuilder_ != null) {
                    this.serviceBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureServiceIsMutable();
                    this.service_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addService(int index, ServiceDescriptorProto value) {
                if (this.serviceBuilder_ != null) {
                    this.serviceBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureServiceIsMutable();
                    this.service_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addService(Builder builderForValue) {
                if (this.serviceBuilder_ == null) {
                    ensureServiceIsMutable();
                    this.service_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.serviceBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addService(int index, Builder builderForValue) {
                if (this.serviceBuilder_ == null) {
                    ensureServiceIsMutable();
                    this.service_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.serviceBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllService(Iterable<? extends ServiceDescriptorProto> values) {
                if (this.serviceBuilder_ == null) {
                    ensureServiceIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.service_);
                    onChanged();
                } else {
                    this.serviceBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearService() {
                if (this.serviceBuilder_ == null) {
                    this.service_ = Collections.emptyList();
                    this.bitField0_ &= -129;
                    onChanged();
                } else {
                    this.serviceBuilder_.clear();
                }
                return this;
            }

            public Builder removeService(int index) {
                if (this.serviceBuilder_ == null) {
                    ensureServiceIsMutable();
                    this.service_.remove(index);
                    onChanged();
                } else {
                    this.serviceBuilder_.remove(index);
                }
                return this;
            }

            public Builder getServiceBuilder(int index) {
                return (Builder) getServiceFieldBuilder().getBuilder(index);
            }

            public ServiceDescriptorProtoOrBuilder getServiceOrBuilder(int index) {
                if (this.serviceBuilder_ == null) {
                    return (ServiceDescriptorProtoOrBuilder) this.service_.get(index);
                }
                return (ServiceDescriptorProtoOrBuilder) this.serviceBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends ServiceDescriptorProtoOrBuilder> getServiceOrBuilderList() {
                if (this.serviceBuilder_ != null) {
                    return this.serviceBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.service_);
            }

            public Builder addServiceBuilder() {
                return (Builder) getServiceFieldBuilder().addBuilder(ServiceDescriptorProto.getDefaultInstance());
            }

            public Builder addServiceBuilder(int index) {
                return (Builder) getServiceFieldBuilder().addBuilder(index, ServiceDescriptorProto.getDefaultInstance());
            }

            public List<Builder> getServiceBuilderList() {
                return getServiceFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<ServiceDescriptorProto, Builder, ServiceDescriptorProtoOrBuilder> getServiceFieldBuilder() {
                if (this.serviceBuilder_ == null) {
                    this.serviceBuilder_ = new RepeatedFieldBuilder(this.service_, (this.bitField0_ & 128) == 128, getParentForChildren(), isClean());
                    this.service_ = null;
                }
                return this.serviceBuilder_;
            }

            private void ensureExtensionIsMutable() {
                if ((this.bitField0_ & 256) != 256) {
                    this.extension_ = new ArrayList(this.extension_);
                    this.bitField0_ |= 256;
                }
            }

            public List<FieldDescriptorProto> getExtensionList() {
                if (this.extensionBuilder_ == null) {
                    return Collections.unmodifiableList(this.extension_);
                }
                return this.extensionBuilder_.getMessageList();
            }

            public int getExtensionCount() {
                if (this.extensionBuilder_ == null) {
                    return this.extension_.size();
                }
                return this.extensionBuilder_.getCount();
            }

            public FieldDescriptorProto getExtension(int index) {
                if (this.extensionBuilder_ == null) {
                    return (FieldDescriptorProto) this.extension_.get(index);
                }
                return (FieldDescriptorProto) this.extensionBuilder_.getMessage(index);
            }

            public Builder setExtension(int index, FieldDescriptorProto value) {
                if (this.extensionBuilder_ != null) {
                    this.extensionBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureExtensionIsMutable();
                    this.extension_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setExtension(int index, Builder builderForValue) {
                if (this.extensionBuilder_ == null) {
                    ensureExtensionIsMutable();
                    this.extension_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.extensionBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addExtension(FieldDescriptorProto value) {
                if (this.extensionBuilder_ != null) {
                    this.extensionBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureExtensionIsMutable();
                    this.extension_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addExtension(int index, FieldDescriptorProto value) {
                if (this.extensionBuilder_ != null) {
                    this.extensionBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureExtensionIsMutable();
                    this.extension_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addExtension(Builder builderForValue) {
                if (this.extensionBuilder_ == null) {
                    ensureExtensionIsMutable();
                    this.extension_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.extensionBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addExtension(int index, Builder builderForValue) {
                if (this.extensionBuilder_ == null) {
                    ensureExtensionIsMutable();
                    this.extension_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.extensionBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllExtension(Iterable<? extends FieldDescriptorProto> values) {
                if (this.extensionBuilder_ == null) {
                    ensureExtensionIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.extension_);
                    onChanged();
                } else {
                    this.extensionBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearExtension() {
                if (this.extensionBuilder_ == null) {
                    this.extension_ = Collections.emptyList();
                    this.bitField0_ &= -257;
                    onChanged();
                } else {
                    this.extensionBuilder_.clear();
                }
                return this;
            }

            public Builder removeExtension(int index) {
                if (this.extensionBuilder_ == null) {
                    ensureExtensionIsMutable();
                    this.extension_.remove(index);
                    onChanged();
                } else {
                    this.extensionBuilder_.remove(index);
                }
                return this;
            }

            public Builder getExtensionBuilder(int index) {
                return (Builder) getExtensionFieldBuilder().getBuilder(index);
            }

            public FieldDescriptorProtoOrBuilder getExtensionOrBuilder(int index) {
                if (this.extensionBuilder_ == null) {
                    return (FieldDescriptorProtoOrBuilder) this.extension_.get(index);
                }
                return (FieldDescriptorProtoOrBuilder) this.extensionBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends FieldDescriptorProtoOrBuilder> getExtensionOrBuilderList() {
                if (this.extensionBuilder_ != null) {
                    return this.extensionBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.extension_);
            }

            public Builder addExtensionBuilder() {
                return (Builder) getExtensionFieldBuilder().addBuilder(FieldDescriptorProto.getDefaultInstance());
            }

            public Builder addExtensionBuilder(int index) {
                return (Builder) getExtensionFieldBuilder().addBuilder(index, FieldDescriptorProto.getDefaultInstance());
            }

            public List<Builder> getExtensionBuilderList() {
                return getExtensionFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<FieldDescriptorProto, Builder, FieldDescriptorProtoOrBuilder> getExtensionFieldBuilder() {
                if (this.extensionBuilder_ == null) {
                    this.extensionBuilder_ = new RepeatedFieldBuilder(this.extension_, (this.bitField0_ & 256) == 256, getParentForChildren(), isClean());
                    this.extension_ = null;
                }
                return this.extensionBuilder_;
            }

            public boolean hasOptions() {
                return (this.bitField0_ & 512) == 512;
            }

            public FileOptions getOptions() {
                if (this.optionsBuilder_ == null) {
                    return this.options_;
                }
                return (FileOptions) this.optionsBuilder_.getMessage();
            }

            public Builder setOptions(FileOptions value) {
                if (this.optionsBuilder_ != null) {
                    this.optionsBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.options_ = value;
                    onChanged();
                }
                this.bitField0_ |= 512;
                return this;
            }

            public Builder setOptions(Builder builderForValue) {
                if (this.optionsBuilder_ == null) {
                    this.options_ = builderForValue.build();
                    onChanged();
                } else {
                    this.optionsBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 512;
                return this;
            }

            public Builder mergeOptions(FileOptions value) {
                if (this.optionsBuilder_ == null) {
                    if ((this.bitField0_ & 512) != 512 || this.options_ == FileOptions.getDefaultInstance()) {
                        this.options_ = value;
                    } else {
                        this.options_ = FileOptions.newBuilder(this.options_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.optionsBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 512;
                return this;
            }

            public Builder clearOptions() {
                if (this.optionsBuilder_ == null) {
                    this.options_ = FileOptions.getDefaultInstance();
                    onChanged();
                } else {
                    this.optionsBuilder_.clear();
                }
                this.bitField0_ &= -513;
                return this;
            }

            public Builder getOptionsBuilder() {
                this.bitField0_ |= 512;
                onChanged();
                return (Builder) getOptionsFieldBuilder().getBuilder();
            }

            public FileOptionsOrBuilder getOptionsOrBuilder() {
                if (this.optionsBuilder_ != null) {
                    return (FileOptionsOrBuilder) this.optionsBuilder_.getMessageOrBuilder();
                }
                return this.options_;
            }

            private SingleFieldBuilder<FileOptions, Builder, FileOptionsOrBuilder> getOptionsFieldBuilder() {
                if (this.optionsBuilder_ == null) {
                    this.optionsBuilder_ = new SingleFieldBuilder(getOptions(), getParentForChildren(), isClean());
                    this.options_ = null;
                }
                return this.optionsBuilder_;
            }

            public boolean hasSourceCodeInfo() {
                return (this.bitField0_ & 1024) == 1024;
            }

            public SourceCodeInfo getSourceCodeInfo() {
                if (this.sourceCodeInfoBuilder_ == null) {
                    return this.sourceCodeInfo_;
                }
                return (SourceCodeInfo) this.sourceCodeInfoBuilder_.getMessage();
            }

            public Builder setSourceCodeInfo(SourceCodeInfo value) {
                if (this.sourceCodeInfoBuilder_ != null) {
                    this.sourceCodeInfoBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.sourceCodeInfo_ = value;
                    onChanged();
                }
                this.bitField0_ |= 1024;
                return this;
            }

            public Builder setSourceCodeInfo(Builder builderForValue) {
                if (this.sourceCodeInfoBuilder_ == null) {
                    this.sourceCodeInfo_ = builderForValue.build();
                    onChanged();
                } else {
                    this.sourceCodeInfoBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 1024;
                return this;
            }

            public Builder mergeSourceCodeInfo(SourceCodeInfo value) {
                if (this.sourceCodeInfoBuilder_ == null) {
                    if ((this.bitField0_ & 1024) != 1024 || this.sourceCodeInfo_ == SourceCodeInfo.getDefaultInstance()) {
                        this.sourceCodeInfo_ = value;
                    } else {
                        this.sourceCodeInfo_ = SourceCodeInfo.newBuilder(this.sourceCodeInfo_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.sourceCodeInfoBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 1024;
                return this;
            }

            public Builder clearSourceCodeInfo() {
                if (this.sourceCodeInfoBuilder_ == null) {
                    this.sourceCodeInfo_ = SourceCodeInfo.getDefaultInstance();
                    onChanged();
                } else {
                    this.sourceCodeInfoBuilder_.clear();
                }
                this.bitField0_ &= -1025;
                return this;
            }

            public Builder getSourceCodeInfoBuilder() {
                this.bitField0_ |= 1024;
                onChanged();
                return (Builder) getSourceCodeInfoFieldBuilder().getBuilder();
            }

            public SourceCodeInfoOrBuilder getSourceCodeInfoOrBuilder() {
                if (this.sourceCodeInfoBuilder_ != null) {
                    return (SourceCodeInfoOrBuilder) this.sourceCodeInfoBuilder_.getMessageOrBuilder();
                }
                return this.sourceCodeInfo_;
            }

            private SingleFieldBuilder<SourceCodeInfo, Builder, SourceCodeInfoOrBuilder> getSourceCodeInfoFieldBuilder() {
                if (this.sourceCodeInfoBuilder_ == null) {
                    this.sourceCodeInfoBuilder_ = new SingleFieldBuilder(getSourceCodeInfo(), getParentForChildren(), isClean());
                    this.sourceCodeInfo_ = null;
                }
                return this.sourceCodeInfoBuilder_;
            }
        }

        private FileDescriptorProto(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private FileDescriptorProto(boolean noInit) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static FileDescriptorProto getDefaultInstance() {
            return defaultInstance;
        }

        public FileDescriptorProto getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private FileDescriptorProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    ByteString bs;
                    int limit;
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 10:
                            bs = input.readBytes();
                            this.bitField0_ |= 1;
                            this.name_ = bs;
                            break;
                        case 18:
                            bs = input.readBytes();
                            this.bitField0_ |= 2;
                            this.package_ = bs;
                            break;
                        case 26:
                            bs = input.readBytes();
                            if ((mutable_bitField0_ & 4) != 4) {
                                this.dependency_ = new LazyStringArrayList();
                                mutable_bitField0_ |= 4;
                            }
                            this.dependency_.add(bs);
                            break;
                        case 34:
                            if ((mutable_bitField0_ & 32) != 32) {
                                this.messageType_ = new ArrayList();
                                mutable_bitField0_ |= 32;
                            }
                            this.messageType_.add(input.readMessage(DescriptorProto.PARSER, extensionRegistry));
                            break;
                        case 42:
                            if ((mutable_bitField0_ & 64) != 64) {
                                this.enumType_ = new ArrayList();
                                mutable_bitField0_ |= 64;
                            }
                            this.enumType_.add(input.readMessage(EnumDescriptorProto.PARSER, extensionRegistry));
                            break;
                        case 50:
                            if ((mutable_bitField0_ & 128) != 128) {
                                this.service_ = new ArrayList();
                                mutable_bitField0_ |= 128;
                            }
                            this.service_.add(input.readMessage(ServiceDescriptorProto.PARSER, extensionRegistry));
                            break;
                        case 58:
                            if ((mutable_bitField0_ & 256) != 256) {
                                this.extension_ = new ArrayList();
                                mutable_bitField0_ |= 256;
                            }
                            this.extension_.add(input.readMessage(FieldDescriptorProto.PARSER, extensionRegistry));
                            break;
                        case 66:
                            Builder subBuilder = null;
                            if ((this.bitField0_ & 4) == 4) {
                                subBuilder = this.options_.toBuilder();
                            }
                            this.options_ = (FileOptions) input.readMessage(FileOptions.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.options_);
                                this.options_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 4;
                            break;
                        case 74:
                            Builder subBuilder2 = null;
                            if ((this.bitField0_ & 8) == 8) {
                                subBuilder2 = this.sourceCodeInfo_.toBuilder();
                            }
                            this.sourceCodeInfo_ = (SourceCodeInfo) input.readMessage(SourceCodeInfo.PARSER, extensionRegistry);
                            if (subBuilder2 != null) {
                                subBuilder2.mergeFrom(this.sourceCodeInfo_);
                                this.sourceCodeInfo_ = subBuilder2.buildPartial();
                            }
                            this.bitField0_ |= 8;
                            break;
                        case 80:
                            if ((mutable_bitField0_ & 8) != 8) {
                                this.publicDependency_ = new ArrayList();
                                mutable_bitField0_ |= 8;
                            }
                            this.publicDependency_.add(Integer.valueOf(input.readInt32()));
                            break;
                        case 82:
                            limit = input.pushLimit(input.readRawVarint32());
                            if ((mutable_bitField0_ & 8) != 8 && input.getBytesUntilLimit() > 0) {
                                this.publicDependency_ = new ArrayList();
                                mutable_bitField0_ |= 8;
                            }
                            while (input.getBytesUntilLimit() > 0) {
                                this.publicDependency_.add(Integer.valueOf(input.readInt32()));
                            }
                            input.popLimit(limit);
                            break;
                        case 88:
                            if ((mutable_bitField0_ & 16) != 16) {
                                this.weakDependency_ = new ArrayList();
                                mutable_bitField0_ |= 16;
                            }
                            this.weakDependency_.add(Integer.valueOf(input.readInt32()));
                            break;
                        case 90:
                            limit = input.pushLimit(input.readRawVarint32());
                            if ((mutable_bitField0_ & 16) != 16 && input.getBytesUntilLimit() > 0) {
                                this.weakDependency_ = new ArrayList();
                                mutable_bitField0_ |= 16;
                            }
                            while (input.getBytesUntilLimit() > 0) {
                                this.weakDependency_.add(Integer.valueOf(input.readInt32()));
                            }
                            input.popLimit(limit);
                            break;
                        default:
                            if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                                break;
                            }
                            break;
                    }
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                } catch (Throwable th) {
                    if ((mutable_bitField0_ & 4) == 4) {
                        this.dependency_ = this.dependency_.getUnmodifiableView();
                    }
                    if ((mutable_bitField0_ & 32) == 32) {
                        this.messageType_ = Collections.unmodifiableList(this.messageType_);
                    }
                    if ((mutable_bitField0_ & 64) == 64) {
                        this.enumType_ = Collections.unmodifiableList(this.enumType_);
                    }
                    if ((mutable_bitField0_ & 128) == 128) {
                        this.service_ = Collections.unmodifiableList(this.service_);
                    }
                    if ((mutable_bitField0_ & 256) == 256) {
                        this.extension_ = Collections.unmodifiableList(this.extension_);
                    }
                    if ((mutable_bitField0_ & 8) == 8) {
                        this.publicDependency_ = Collections.unmodifiableList(this.publicDependency_);
                    }
                    if ((mutable_bitField0_ & 16) == 16) {
                        this.weakDependency_ = Collections.unmodifiableList(this.weakDependency_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                }
            }
            if ((mutable_bitField0_ & 4) == 4) {
                this.dependency_ = this.dependency_.getUnmodifiableView();
            }
            if ((mutable_bitField0_ & 32) == 32) {
                this.messageType_ = Collections.unmodifiableList(this.messageType_);
            }
            if ((mutable_bitField0_ & 64) == 64) {
                this.enumType_ = Collections.unmodifiableList(this.enumType_);
            }
            if ((mutable_bitField0_ & 128) == 128) {
                this.service_ = Collections.unmodifiableList(this.service_);
            }
            if ((mutable_bitField0_ & 256) == 256) {
                this.extension_ = Collections.unmodifiableList(this.extension_);
            }
            if ((mutable_bitField0_ & 8) == 8) {
                this.publicDependency_ = Collections.unmodifiableList(this.publicDependency_);
            }
            if ((mutable_bitField0_ & 16) == 16) {
                this.weakDependency_ = Collections.unmodifiableList(this.weakDependency_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_FileDescriptorProto_descriptor;
        }

        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_FileDescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(FileDescriptorProto.class, Builder.class);
        }

        static {
            defaultInstance.initFields();
        }

        public Parser<FileDescriptorProto> getParserForType() {
            return PARSER;
        }

        public boolean hasName() {
            return (this.bitField0_ & 1) == 1;
        }

        public String getName() {
            ByteString ref = this.name_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.name_ = s;
            }
            return s;
        }

        public ByteString getNameBytes() {
            Object ref = this.name_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.name_ = b;
            return b;
        }

        public boolean hasPackage() {
            return (this.bitField0_ & 2) == 2;
        }

        public String getPackage() {
            ByteString ref = this.package_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.package_ = s;
            }
            return s;
        }

        public ByteString getPackageBytes() {
            Object ref = this.package_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.package_ = b;
            return b;
        }

        public ProtocolStringList getDependencyList() {
            return this.dependency_;
        }

        public int getDependencyCount() {
            return this.dependency_.size();
        }

        public String getDependency(int index) {
            return (String) this.dependency_.get(index);
        }

        public ByteString getDependencyBytes(int index) {
            return this.dependency_.getByteString(index);
        }

        public List<Integer> getPublicDependencyList() {
            return this.publicDependency_;
        }

        public int getPublicDependencyCount() {
            return this.publicDependency_.size();
        }

        public int getPublicDependency(int index) {
            return ((Integer) this.publicDependency_.get(index)).intValue();
        }

        public List<Integer> getWeakDependencyList() {
            return this.weakDependency_;
        }

        public int getWeakDependencyCount() {
            return this.weakDependency_.size();
        }

        public int getWeakDependency(int index) {
            return ((Integer) this.weakDependency_.get(index)).intValue();
        }

        public List<DescriptorProto> getMessageTypeList() {
            return this.messageType_;
        }

        public List<? extends DescriptorProtoOrBuilder> getMessageTypeOrBuilderList() {
            return this.messageType_;
        }

        public int getMessageTypeCount() {
            return this.messageType_.size();
        }

        public DescriptorProto getMessageType(int index) {
            return (DescriptorProto) this.messageType_.get(index);
        }

        public DescriptorProtoOrBuilder getMessageTypeOrBuilder(int index) {
            return (DescriptorProtoOrBuilder) this.messageType_.get(index);
        }

        public List<EnumDescriptorProto> getEnumTypeList() {
            return this.enumType_;
        }

        public List<? extends EnumDescriptorProtoOrBuilder> getEnumTypeOrBuilderList() {
            return this.enumType_;
        }

        public int getEnumTypeCount() {
            return this.enumType_.size();
        }

        public EnumDescriptorProto getEnumType(int index) {
            return (EnumDescriptorProto) this.enumType_.get(index);
        }

        public EnumDescriptorProtoOrBuilder getEnumTypeOrBuilder(int index) {
            return (EnumDescriptorProtoOrBuilder) this.enumType_.get(index);
        }

        public List<ServiceDescriptorProto> getServiceList() {
            return this.service_;
        }

        public List<? extends ServiceDescriptorProtoOrBuilder> getServiceOrBuilderList() {
            return this.service_;
        }

        public int getServiceCount() {
            return this.service_.size();
        }

        public ServiceDescriptorProto getService(int index) {
            return (ServiceDescriptorProto) this.service_.get(index);
        }

        public ServiceDescriptorProtoOrBuilder getServiceOrBuilder(int index) {
            return (ServiceDescriptorProtoOrBuilder) this.service_.get(index);
        }

        public List<FieldDescriptorProto> getExtensionList() {
            return this.extension_;
        }

        public List<? extends FieldDescriptorProtoOrBuilder> getExtensionOrBuilderList() {
            return this.extension_;
        }

        public int getExtensionCount() {
            return this.extension_.size();
        }

        public FieldDescriptorProto getExtension(int index) {
            return (FieldDescriptorProto) this.extension_.get(index);
        }

        public FieldDescriptorProtoOrBuilder getExtensionOrBuilder(int index) {
            return (FieldDescriptorProtoOrBuilder) this.extension_.get(index);
        }

        public boolean hasOptions() {
            return (this.bitField0_ & 4) == 4;
        }

        public FileOptions getOptions() {
            return this.options_;
        }

        public FileOptionsOrBuilder getOptionsOrBuilder() {
            return this.options_;
        }

        public boolean hasSourceCodeInfo() {
            return (this.bitField0_ & 8) == 8;
        }

        public SourceCodeInfo getSourceCodeInfo() {
            return this.sourceCodeInfo_;
        }

        public SourceCodeInfoOrBuilder getSourceCodeInfoOrBuilder() {
            return this.sourceCodeInfo_;
        }

        private void initFields() {
            this.name_ = "";
            this.package_ = "";
            this.dependency_ = LazyStringArrayList.EMPTY;
            this.publicDependency_ = Collections.emptyList();
            this.weakDependency_ = Collections.emptyList();
            this.messageType_ = Collections.emptyList();
            this.enumType_ = Collections.emptyList();
            this.service_ = Collections.emptyList();
            this.extension_ = Collections.emptyList();
            this.options_ = FileOptions.getDefaultInstance();
            this.sourceCodeInfo_ = SourceCodeInfo.getDefaultInstance();
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == (byte) 1) {
                return true;
            }
            if (isInitialized == (byte) 0) {
                return false;
            }
            int i = 0;
            while (i < getMessageTypeCount()) {
                if (getMessageType(i).isInitialized()) {
                    i++;
                } else {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            i = 0;
            while (i < getEnumTypeCount()) {
                if (getEnumType(i).isInitialized()) {
                    i++;
                } else {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            i = 0;
            while (i < getServiceCount()) {
                if (getService(i).isInitialized()) {
                    i++;
                } else {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            i = 0;
            while (i < getExtensionCount()) {
                if (getExtension(i).isInitialized()) {
                    i++;
                } else {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (!hasOptions() || getOptions().isInitialized()) {
                this.memoizedIsInitialized = (byte) 1;
                return true;
            }
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            int i;
            getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeBytes(1, getNameBytes());
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeBytes(2, getPackageBytes());
            }
            for (i = 0; i < this.dependency_.size(); i++) {
                output.writeBytes(3, this.dependency_.getByteString(i));
            }
            for (i = 0; i < this.messageType_.size(); i++) {
                output.writeMessage(4, (MessageLite) this.messageType_.get(i));
            }
            for (i = 0; i < this.enumType_.size(); i++) {
                output.writeMessage(5, (MessageLite) this.enumType_.get(i));
            }
            for (i = 0; i < this.service_.size(); i++) {
                output.writeMessage(6, (MessageLite) this.service_.get(i));
            }
            for (i = 0; i < this.extension_.size(); i++) {
                output.writeMessage(7, (MessageLite) this.extension_.get(i));
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeMessage(8, this.options_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeMessage(9, this.sourceCodeInfo_);
            }
            for (i = 0; i < this.publicDependency_.size(); i++) {
                output.writeInt32(10, ((Integer) this.publicDependency_.get(i)).intValue());
            }
            for (i = 0; i < this.weakDependency_.size(); i++) {
                output.writeInt32(11, ((Integer) this.weakDependency_.get(i)).intValue());
            }
            getUnknownFields().writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            int i;
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
                size = 0 + CodedOutputStream.computeBytesSize(1, getNameBytes());
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeBytesSize(2, getPackageBytes());
            }
            int dataSize = 0;
            for (i = 0; i < this.dependency_.size(); i++) {
                dataSize += CodedOutputStream.computeBytesSizeNoTag(this.dependency_.getByteString(i));
            }
            size = (size + dataSize) + (getDependencyList().size() * 1);
            for (i = 0; i < this.messageType_.size(); i++) {
                size += CodedOutputStream.computeMessageSize(4, (MessageLite) this.messageType_.get(i));
            }
            for (i = 0; i < this.enumType_.size(); i++) {
                size += CodedOutputStream.computeMessageSize(5, (MessageLite) this.enumType_.get(i));
            }
            for (i = 0; i < this.service_.size(); i++) {
                size += CodedOutputStream.computeMessageSize(6, (MessageLite) this.service_.get(i));
            }
            for (i = 0; i < this.extension_.size(); i++) {
                size += CodedOutputStream.computeMessageSize(7, (MessageLite) this.extension_.get(i));
            }
            if ((this.bitField0_ & 4) == 4) {
                size += CodedOutputStream.computeMessageSize(8, this.options_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size += CodedOutputStream.computeMessageSize(9, this.sourceCodeInfo_);
            }
            dataSize = 0;
            for (i = 0; i < this.publicDependency_.size(); i++) {
                dataSize += CodedOutputStream.computeInt32SizeNoTag(((Integer) this.publicDependency_.get(i)).intValue());
            }
            size = (size + dataSize) + (getPublicDependencyList().size() * 1);
            dataSize = 0;
            for (i = 0; i < this.weakDependency_.size(); i++) {
                dataSize += CodedOutputStream.computeInt32SizeNoTag(((Integer) this.weakDependency_.get(i)).intValue());
            }
            size = ((size + dataSize) + (getWeakDependencyList().size() * 1)) + getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = size;
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static FileDescriptorProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (FileDescriptorProto) PARSER.parseFrom(data);
        }

        public static FileDescriptorProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FileDescriptorProto) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileDescriptorProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (FileDescriptorProto) PARSER.parseFrom(data);
        }

        public static FileDescriptorProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FileDescriptorProto) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileDescriptorProto parseFrom(InputStream input) throws IOException {
            return (FileDescriptorProto) PARSER.parseFrom(input);
        }

        public static FileDescriptorProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FileDescriptorProto) PARSER.parseFrom(input, extensionRegistry);
        }

        public static FileDescriptorProto parseDelimitedFrom(InputStream input) throws IOException {
            return (FileDescriptorProto) PARSER.parseDelimitedFrom(input);
        }

        public static FileDescriptorProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FileDescriptorProto) PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static FileDescriptorProto parseFrom(CodedInputStream input) throws IOException {
            return (FileDescriptorProto) PARSER.parseFrom(input);
        }

        public static FileDescriptorProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FileDescriptorProto) PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(FileDescriptorProto prototype) {
            return newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return newBuilder(this);
        }

        protected Builder newBuilderForType(BuilderParent parent) {
            return new Builder(parent);
        }
    }

    public interface FileDescriptorSetOrBuilder extends MessageOrBuilder {
        FileDescriptorProto getFile(int i);

        int getFileCount();

        List<FileDescriptorProto> getFileList();

        FileDescriptorProtoOrBuilder getFileOrBuilder(int i);

        List<? extends FileDescriptorProtoOrBuilder> getFileOrBuilderList();
    }

    public static final class FileDescriptorSet extends GeneratedMessage implements FileDescriptorSetOrBuilder {
        public static final int FILE_FIELD_NUMBER = 1;
        public static Parser<FileDescriptorSet> PARSER = new AbstractParser<FileDescriptorSet>() {
            public FileDescriptorSet parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new FileDescriptorSet(input, extensionRegistry);
            }
        };
        private static final FileDescriptorSet defaultInstance = new FileDescriptorSet(true);
        private static final long serialVersionUID = 0;
        private List<FileDescriptorProto> file_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private final UnknownFieldSet unknownFields;

        public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> implements FileDescriptorSetOrBuilder {
            private int bitField0_;
            private RepeatedFieldBuilder<FileDescriptorProto, Builder, FileDescriptorProtoOrBuilder> fileBuilder_;
            private List<FileDescriptorProto> file_;

            public static final Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_FileDescriptorSet_descriptor;
            }

            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_FileDescriptorSet_fieldAccessorTable.ensureFieldAccessorsInitialized(FileDescriptorSet.class, Builder.class);
            }

            private Builder() {
                this.file_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.file_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getFileFieldBuilder();
                }
            }

            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                if (this.fileBuilder_ == null) {
                    this.file_ = Collections.emptyList();
                    this.bitField0_ &= -2;
                } else {
                    this.fileBuilder_.clear();
                }
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_FileDescriptorSet_descriptor;
            }

            public FileDescriptorSet getDefaultInstanceForType() {
                return FileDescriptorSet.getDefaultInstance();
            }

            public FileDescriptorSet build() {
                FileDescriptorSet result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw com.google.protobuf.AbstractMessage.Builder.newUninitializedMessageException(result);
            }

            public FileDescriptorSet buildPartial() {
                FileDescriptorSet result = new FileDescriptorSet((com.google.protobuf.GeneratedMessage.Builder) this);
                int from_bitField0_ = this.bitField0_;
                if (this.fileBuilder_ == null) {
                    if ((this.bitField0_ & 1) == 1) {
                        this.file_ = Collections.unmodifiableList(this.file_);
                        this.bitField0_ &= -2;
                    }
                    result.file_ = this.file_;
                } else {
                    result.file_ = this.fileBuilder_.build();
                }
                onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof FileDescriptorSet) {
                    return mergeFrom((FileDescriptorSet) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(FileDescriptorSet other) {
                RepeatedFieldBuilder repeatedFieldBuilder = null;
                if (other != FileDescriptorSet.getDefaultInstance()) {
                    if (this.fileBuilder_ == null) {
                        if (!other.file_.isEmpty()) {
                            if (this.file_.isEmpty()) {
                                this.file_ = other.file_;
                                this.bitField0_ &= -2;
                            } else {
                                ensureFileIsMutable();
                                this.file_.addAll(other.file_);
                            }
                            onChanged();
                        }
                    } else if (!other.file_.isEmpty()) {
                        if (this.fileBuilder_.isEmpty()) {
                            this.fileBuilder_.dispose();
                            this.fileBuilder_ = null;
                            this.file_ = other.file_;
                            this.bitField0_ &= -2;
                            if (GeneratedMessage.alwaysUseFieldBuilders) {
                                repeatedFieldBuilder = getFileFieldBuilder();
                            }
                            this.fileBuilder_ = repeatedFieldBuilder;
                        } else {
                            this.fileBuilder_.addAllMessages(other.file_);
                        }
                    }
                    mergeUnknownFields(other.getUnknownFields());
                }
                return this;
            }

            public final boolean isInitialized() {
                for (int i = 0; i < getFileCount(); i++) {
                    if (!getFile(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                FileDescriptorSet parsedMessage = null;
                try {
                    parsedMessage = (FileDescriptorSet) FileDescriptorSet.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    parsedMessage = (FileDescriptorSet) e.getUnfinishedMessage();
                    throw e;
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
            }

            private void ensureFileIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.file_ = new ArrayList(this.file_);
                    this.bitField0_ |= 1;
                }
            }

            public List<FileDescriptorProto> getFileList() {
                if (this.fileBuilder_ == null) {
                    return Collections.unmodifiableList(this.file_);
                }
                return this.fileBuilder_.getMessageList();
            }

            public int getFileCount() {
                if (this.fileBuilder_ == null) {
                    return this.file_.size();
                }
                return this.fileBuilder_.getCount();
            }

            public FileDescriptorProto getFile(int index) {
                if (this.fileBuilder_ == null) {
                    return (FileDescriptorProto) this.file_.get(index);
                }
                return (FileDescriptorProto) this.fileBuilder_.getMessage(index);
            }

            public Builder setFile(int index, FileDescriptorProto value) {
                if (this.fileBuilder_ != null) {
                    this.fileBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureFileIsMutable();
                    this.file_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setFile(int index, Builder builderForValue) {
                if (this.fileBuilder_ == null) {
                    ensureFileIsMutable();
                    this.file_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.fileBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addFile(FileDescriptorProto value) {
                if (this.fileBuilder_ != null) {
                    this.fileBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureFileIsMutable();
                    this.file_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addFile(int index, FileDescriptorProto value) {
                if (this.fileBuilder_ != null) {
                    this.fileBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureFileIsMutable();
                    this.file_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addFile(Builder builderForValue) {
                if (this.fileBuilder_ == null) {
                    ensureFileIsMutable();
                    this.file_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.fileBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addFile(int index, Builder builderForValue) {
                if (this.fileBuilder_ == null) {
                    ensureFileIsMutable();
                    this.file_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.fileBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllFile(Iterable<? extends FileDescriptorProto> values) {
                if (this.fileBuilder_ == null) {
                    ensureFileIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.file_);
                    onChanged();
                } else {
                    this.fileBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearFile() {
                if (this.fileBuilder_ == null) {
                    this.file_ = Collections.emptyList();
                    this.bitField0_ &= -2;
                    onChanged();
                } else {
                    this.fileBuilder_.clear();
                }
                return this;
            }

            public Builder removeFile(int index) {
                if (this.fileBuilder_ == null) {
                    ensureFileIsMutable();
                    this.file_.remove(index);
                    onChanged();
                } else {
                    this.fileBuilder_.remove(index);
                }
                return this;
            }

            public Builder getFileBuilder(int index) {
                return (Builder) getFileFieldBuilder().getBuilder(index);
            }

            public FileDescriptorProtoOrBuilder getFileOrBuilder(int index) {
                if (this.fileBuilder_ == null) {
                    return (FileDescriptorProtoOrBuilder) this.file_.get(index);
                }
                return (FileDescriptorProtoOrBuilder) this.fileBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends FileDescriptorProtoOrBuilder> getFileOrBuilderList() {
                if (this.fileBuilder_ != null) {
                    return this.fileBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.file_);
            }

            public Builder addFileBuilder() {
                return (Builder) getFileFieldBuilder().addBuilder(FileDescriptorProto.getDefaultInstance());
            }

            public Builder addFileBuilder(int index) {
                return (Builder) getFileFieldBuilder().addBuilder(index, FileDescriptorProto.getDefaultInstance());
            }

            public List<Builder> getFileBuilderList() {
                return getFileFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<FileDescriptorProto, Builder, FileDescriptorProtoOrBuilder> getFileFieldBuilder() {
                boolean z = true;
                if (this.fileBuilder_ == null) {
                    List list = this.file_;
                    if ((this.bitField0_ & 1) != 1) {
                        z = false;
                    }
                    this.fileBuilder_ = new RepeatedFieldBuilder(list, z, getParentForChildren(), isClean());
                    this.file_ = null;
                }
                return this.fileBuilder_;
            }
        }

        private FileDescriptorSet(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private FileDescriptorSet(boolean noInit) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static FileDescriptorSet getDefaultInstance() {
            return defaultInstance;
        }

        public FileDescriptorSet getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private FileDescriptorSet(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 10:
                            if ((mutable_bitField0_ & 1) != 1) {
                                this.file_ = new ArrayList();
                                mutable_bitField0_ |= 1;
                            }
                            this.file_.add(input.readMessage(FileDescriptorProto.PARSER, extensionRegistry));
                            break;
                        default:
                            if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                                break;
                            }
                            break;
                    }
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                } catch (Throwable th) {
                    if ((mutable_bitField0_ & 1) == 1) {
                        this.file_ = Collections.unmodifiableList(this.file_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                }
            }
            if ((mutable_bitField0_ & 1) == 1) {
                this.file_ = Collections.unmodifiableList(this.file_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_FileDescriptorSet_descriptor;
        }

        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_FileDescriptorSet_fieldAccessorTable.ensureFieldAccessorsInitialized(FileDescriptorSet.class, Builder.class);
        }

        static {
            defaultInstance.initFields();
        }

        public Parser<FileDescriptorSet> getParserForType() {
            return PARSER;
        }

        public List<FileDescriptorProto> getFileList() {
            return this.file_;
        }

        public List<? extends FileDescriptorProtoOrBuilder> getFileOrBuilderList() {
            return this.file_;
        }

        public int getFileCount() {
            return this.file_.size();
        }

        public FileDescriptorProto getFile(int index) {
            return (FileDescriptorProto) this.file_.get(index);
        }

        public FileDescriptorProtoOrBuilder getFileOrBuilder(int index) {
            return (FileDescriptorProtoOrBuilder) this.file_.get(index);
        }

        private void initFields() {
            this.file_ = Collections.emptyList();
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == (byte) 1) {
                return true;
            }
            if (isInitialized == (byte) 0) {
                return false;
            }
            int i = 0;
            while (i < getFileCount()) {
                if (getFile(i).isInitialized()) {
                    i++;
                } else {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            getSerializedSize();
            for (int i = 0; i < this.file_.size(); i++) {
                output.writeMessage(1, (MessageLite) this.file_.get(i));
            }
            getUnknownFields().writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            for (int i = 0; i < this.file_.size(); i++) {
                size += CodedOutputStream.computeMessageSize(1, (MessageLite) this.file_.get(i));
            }
            size += getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = size;
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static FileDescriptorSet parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (FileDescriptorSet) PARSER.parseFrom(data);
        }

        public static FileDescriptorSet parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FileDescriptorSet) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileDescriptorSet parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (FileDescriptorSet) PARSER.parseFrom(data);
        }

        public static FileDescriptorSet parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FileDescriptorSet) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileDescriptorSet parseFrom(InputStream input) throws IOException {
            return (FileDescriptorSet) PARSER.parseFrom(input);
        }

        public static FileDescriptorSet parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FileDescriptorSet) PARSER.parseFrom(input, extensionRegistry);
        }

        public static FileDescriptorSet parseDelimitedFrom(InputStream input) throws IOException {
            return (FileDescriptorSet) PARSER.parseDelimitedFrom(input);
        }

        public static FileDescriptorSet parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FileDescriptorSet) PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static FileDescriptorSet parseFrom(CodedInputStream input) throws IOException {
            return (FileDescriptorSet) PARSER.parseFrom(input);
        }

        public static FileDescriptorSet parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FileDescriptorSet) PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(FileDescriptorSet prototype) {
            return newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return newBuilder(this);
        }

        protected Builder newBuilderForType(BuilderParent parent) {
            return new Builder(parent);
        }
    }

    public interface FileOptionsOrBuilder extends ExtendableMessageOrBuilder<FileOptions> {
        boolean getCcGenericServices();

        boolean getDeprecated();

        String getGoPackage();

        ByteString getGoPackageBytes();

        boolean getJavaGenerateEqualsAndHash();

        boolean getJavaGenericServices();

        boolean getJavaMultipleFiles();

        String getJavaOuterClassname();

        ByteString getJavaOuterClassnameBytes();

        String getJavaPackage();

        ByteString getJavaPackageBytes();

        boolean getJavaStringCheckUtf8();

        OptimizeMode getOptimizeFor();

        boolean getPyGenericServices();

        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();

        UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int i);

        List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList();

        boolean hasCcGenericServices();

        boolean hasDeprecated();

        boolean hasGoPackage();

        boolean hasJavaGenerateEqualsAndHash();

        boolean hasJavaGenericServices();

        boolean hasJavaMultipleFiles();

        boolean hasJavaOuterClassname();

        boolean hasJavaPackage();

        boolean hasJavaStringCheckUtf8();

        boolean hasOptimizeFor();

        boolean hasPyGenericServices();
    }

    public static final class FileOptions extends ExtendableMessage<FileOptions> implements FileOptionsOrBuilder {
        public static final int CC_GENERIC_SERVICES_FIELD_NUMBER = 16;
        public static final int DEPRECATED_FIELD_NUMBER = 23;
        public static final int GO_PACKAGE_FIELD_NUMBER = 11;
        public static final int JAVA_GENERATE_EQUALS_AND_HASH_FIELD_NUMBER = 20;
        public static final int JAVA_GENERIC_SERVICES_FIELD_NUMBER = 17;
        public static final int JAVA_MULTIPLE_FILES_FIELD_NUMBER = 10;
        public static final int JAVA_OUTER_CLASSNAME_FIELD_NUMBER = 8;
        public static final int JAVA_PACKAGE_FIELD_NUMBER = 1;
        public static final int JAVA_STRING_CHECK_UTF8_FIELD_NUMBER = 27;
        public static final int OPTIMIZE_FOR_FIELD_NUMBER = 9;
        public static Parser<FileOptions> PARSER = new AbstractParser<FileOptions>() {
            public FileOptions parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new FileOptions(input, extensionRegistry);
            }
        };
        public static final int PY_GENERIC_SERVICES_FIELD_NUMBER = 18;
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        private static final FileOptions defaultInstance = new FileOptions(true);
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private boolean ccGenericServices_;
        private boolean deprecated_;
        private Object goPackage_;
        private boolean javaGenerateEqualsAndHash_;
        private boolean javaGenericServices_;
        private boolean javaMultipleFiles_;
        private Object javaOuterClassname_;
        private Object javaPackage_;
        private boolean javaStringCheckUtf8_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private OptimizeMode optimizeFor_;
        private boolean pyGenericServices_;
        private List<UninterpretedOption> uninterpretedOption_;
        private final UnknownFieldSet unknownFields;

        public static final class Builder extends ExtendableBuilder<FileOptions, Builder> implements FileOptionsOrBuilder {
            private int bitField0_;
            private boolean ccGenericServices_;
            private boolean deprecated_;
            private Object goPackage_;
            private boolean javaGenerateEqualsAndHash_;
            private boolean javaGenericServices_;
            private boolean javaMultipleFiles_;
            private Object javaOuterClassname_;
            private Object javaPackage_;
            private boolean javaStringCheckUtf8_;
            private OptimizeMode optimizeFor_;
            private boolean pyGenericServices_;
            private RepeatedFieldBuilder<UninterpretedOption, Builder, UninterpretedOptionOrBuilder> uninterpretedOptionBuilder_;
            private List<UninterpretedOption> uninterpretedOption_;

            public static final Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_FileOptions_descriptor;
            }

            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_FileOptions_fieldAccessorTable.ensureFieldAccessorsInitialized(FileOptions.class, Builder.class);
            }

            private Builder() {
                this.javaPackage_ = "";
                this.javaOuterClassname_ = "";
                this.optimizeFor_ = OptimizeMode.SPEED;
                this.goPackage_ = "";
                this.uninterpretedOption_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.javaPackage_ = "";
                this.javaOuterClassname_ = "";
                this.optimizeFor_ = OptimizeMode.SPEED;
                this.goPackage_ = "";
                this.uninterpretedOption_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getUninterpretedOptionFieldBuilder();
                }
            }

            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                this.javaPackage_ = "";
                this.bitField0_ &= -2;
                this.javaOuterClassname_ = "";
                this.bitField0_ &= -3;
                this.javaMultipleFiles_ = false;
                this.bitField0_ &= -5;
                this.javaGenerateEqualsAndHash_ = false;
                this.bitField0_ &= -9;
                this.javaStringCheckUtf8_ = false;
                this.bitField0_ &= -17;
                this.optimizeFor_ = OptimizeMode.SPEED;
                this.bitField0_ &= -33;
                this.goPackage_ = "";
                this.bitField0_ &= -65;
                this.ccGenericServices_ = false;
                this.bitField0_ &= -129;
                this.javaGenericServices_ = false;
                this.bitField0_ &= -257;
                this.pyGenericServices_ = false;
                this.bitField0_ &= -513;
                this.deprecated_ = false;
                this.bitField0_ &= -1025;
                if (this.uninterpretedOptionBuilder_ == null) {
                    this.uninterpretedOption_ = Collections.emptyList();
                    this.bitField0_ &= -2049;
                } else {
                    this.uninterpretedOptionBuilder_.clear();
                }
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_FileOptions_descriptor;
            }

            public FileOptions getDefaultInstanceForType() {
                return FileOptions.getDefaultInstance();
            }

            public FileOptions build() {
                FileOptions result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw com.google.protobuf.AbstractMessage.Builder.newUninitializedMessageException(result);
            }

            public FileOptions buildPartial() {
                FileOptions result = new FileOptions((ExtendableBuilder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.javaPackage_ = this.javaPackage_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.javaOuterClassname_ = this.javaOuterClassname_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.javaMultipleFiles_ = this.javaMultipleFiles_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.javaGenerateEqualsAndHash_ = this.javaGenerateEqualsAndHash_;
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 16;
                }
                result.javaStringCheckUtf8_ = this.javaStringCheckUtf8_;
                if ((from_bitField0_ & 32) == 32) {
                    to_bitField0_ |= 32;
                }
                result.optimizeFor_ = this.optimizeFor_;
                if ((from_bitField0_ & 64) == 64) {
                    to_bitField0_ |= 64;
                }
                result.goPackage_ = this.goPackage_;
                if ((from_bitField0_ & 128) == 128) {
                    to_bitField0_ |= 128;
                }
                result.ccGenericServices_ = this.ccGenericServices_;
                if ((from_bitField0_ & 256) == 256) {
                    to_bitField0_ |= 256;
                }
                result.javaGenericServices_ = this.javaGenericServices_;
                if ((from_bitField0_ & 512) == 512) {
                    to_bitField0_ |= 512;
                }
                result.pyGenericServices_ = this.pyGenericServices_;
                if ((from_bitField0_ & 1024) == 1024) {
                    to_bitField0_ |= 1024;
                }
                result.deprecated_ = this.deprecated_;
                if (this.uninterpretedOptionBuilder_ == null) {
                    if ((this.bitField0_ & 2048) == 2048) {
                        this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
                        this.bitField0_ &= -2049;
                    }
                    result.uninterpretedOption_ = this.uninterpretedOption_;
                } else {
                    result.uninterpretedOption_ = this.uninterpretedOptionBuilder_.build();
                }
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof FileOptions) {
                    return mergeFrom((FileOptions) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(FileOptions other) {
                RepeatedFieldBuilder repeatedFieldBuilder = null;
                if (other != FileOptions.getDefaultInstance()) {
                    if (other.hasJavaPackage()) {
                        this.bitField0_ |= 1;
                        this.javaPackage_ = other.javaPackage_;
                        onChanged();
                    }
                    if (other.hasJavaOuterClassname()) {
                        this.bitField0_ |= 2;
                        this.javaOuterClassname_ = other.javaOuterClassname_;
                        onChanged();
                    }
                    if (other.hasJavaMultipleFiles()) {
                        setJavaMultipleFiles(other.getJavaMultipleFiles());
                    }
                    if (other.hasJavaGenerateEqualsAndHash()) {
                        setJavaGenerateEqualsAndHash(other.getJavaGenerateEqualsAndHash());
                    }
                    if (other.hasJavaStringCheckUtf8()) {
                        setJavaStringCheckUtf8(other.getJavaStringCheckUtf8());
                    }
                    if (other.hasOptimizeFor()) {
                        setOptimizeFor(other.getOptimizeFor());
                    }
                    if (other.hasGoPackage()) {
                        this.bitField0_ |= 64;
                        this.goPackage_ = other.goPackage_;
                        onChanged();
                    }
                    if (other.hasCcGenericServices()) {
                        setCcGenericServices(other.getCcGenericServices());
                    }
                    if (other.hasJavaGenericServices()) {
                        setJavaGenericServices(other.getJavaGenericServices());
                    }
                    if (other.hasPyGenericServices()) {
                        setPyGenericServices(other.getPyGenericServices());
                    }
                    if (other.hasDeprecated()) {
                        setDeprecated(other.getDeprecated());
                    }
                    if (this.uninterpretedOptionBuilder_ == null) {
                        if (!other.uninterpretedOption_.isEmpty()) {
                            if (this.uninterpretedOption_.isEmpty()) {
                                this.uninterpretedOption_ = other.uninterpretedOption_;
                                this.bitField0_ &= -2049;
                            } else {
                                ensureUninterpretedOptionIsMutable();
                                this.uninterpretedOption_.addAll(other.uninterpretedOption_);
                            }
                            onChanged();
                        }
                    } else if (!other.uninterpretedOption_.isEmpty()) {
                        if (this.uninterpretedOptionBuilder_.isEmpty()) {
                            this.uninterpretedOptionBuilder_.dispose();
                            this.uninterpretedOptionBuilder_ = null;
                            this.uninterpretedOption_ = other.uninterpretedOption_;
                            this.bitField0_ &= -2049;
                            if (GeneratedMessage.alwaysUseFieldBuilders) {
                                repeatedFieldBuilder = getUninterpretedOptionFieldBuilder();
                            }
                            this.uninterpretedOptionBuilder_ = repeatedFieldBuilder;
                        } else {
                            this.uninterpretedOptionBuilder_.addAllMessages(other.uninterpretedOption_);
                        }
                    }
                    mergeExtensionFields(other);
                    mergeUnknownFields(other.getUnknownFields());
                }
                return this;
            }

            public final boolean isInitialized() {
                for (int i = 0; i < getUninterpretedOptionCount(); i++) {
                    if (!getUninterpretedOption(i).isInitialized()) {
                        return false;
                    }
                }
                if (extensionsAreInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                FileOptions parsedMessage = null;
                try {
                    parsedMessage = (FileOptions) FileOptions.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    parsedMessage = (FileOptions) e.getUnfinishedMessage();
                    throw e;
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
            }

            public boolean hasJavaPackage() {
                return (this.bitField0_ & 1) == 1;
            }

            public String getJavaPackage() {
                ByteString ref = this.javaPackage_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.javaPackage_ = s;
                return s;
            }

            public ByteString getJavaPackageBytes() {
                Object ref = this.javaPackage_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.javaPackage_ = b;
                return b;
            }

            public Builder setJavaPackage(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.javaPackage_ = value;
                onChanged();
                return this;
            }

            public Builder clearJavaPackage() {
                this.bitField0_ &= -2;
                this.javaPackage_ = FileOptions.getDefaultInstance().getJavaPackage();
                onChanged();
                return this;
            }

            public Builder setJavaPackageBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.javaPackage_ = value;
                onChanged();
                return this;
            }

            public boolean hasJavaOuterClassname() {
                return (this.bitField0_ & 2) == 2;
            }

            public String getJavaOuterClassname() {
                ByteString ref = this.javaOuterClassname_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.javaOuterClassname_ = s;
                return s;
            }

            public ByteString getJavaOuterClassnameBytes() {
                Object ref = this.javaOuterClassname_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.javaOuterClassname_ = b;
                return b;
            }

            public Builder setJavaOuterClassname(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.javaOuterClassname_ = value;
                onChanged();
                return this;
            }

            public Builder clearJavaOuterClassname() {
                this.bitField0_ &= -3;
                this.javaOuterClassname_ = FileOptions.getDefaultInstance().getJavaOuterClassname();
                onChanged();
                return this;
            }

            public Builder setJavaOuterClassnameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.javaOuterClassname_ = value;
                onChanged();
                return this;
            }

            public boolean hasJavaMultipleFiles() {
                return (this.bitField0_ & 4) == 4;
            }

            public boolean getJavaMultipleFiles() {
                return this.javaMultipleFiles_;
            }

            public Builder setJavaMultipleFiles(boolean value) {
                this.bitField0_ |= 4;
                this.javaMultipleFiles_ = value;
                onChanged();
                return this;
            }

            public Builder clearJavaMultipleFiles() {
                this.bitField0_ &= -5;
                this.javaMultipleFiles_ = false;
                onChanged();
                return this;
            }

            public boolean hasJavaGenerateEqualsAndHash() {
                return (this.bitField0_ & 8) == 8;
            }

            public boolean getJavaGenerateEqualsAndHash() {
                return this.javaGenerateEqualsAndHash_;
            }

            public Builder setJavaGenerateEqualsAndHash(boolean value) {
                this.bitField0_ |= 8;
                this.javaGenerateEqualsAndHash_ = value;
                onChanged();
                return this;
            }

            public Builder clearJavaGenerateEqualsAndHash() {
                this.bitField0_ &= -9;
                this.javaGenerateEqualsAndHash_ = false;
                onChanged();
                return this;
            }

            public boolean hasJavaStringCheckUtf8() {
                return (this.bitField0_ & 16) == 16;
            }

            public boolean getJavaStringCheckUtf8() {
                return this.javaStringCheckUtf8_;
            }

            public Builder setJavaStringCheckUtf8(boolean value) {
                this.bitField0_ |= 16;
                this.javaStringCheckUtf8_ = value;
                onChanged();
                return this;
            }

            public Builder clearJavaStringCheckUtf8() {
                this.bitField0_ &= -17;
                this.javaStringCheckUtf8_ = false;
                onChanged();
                return this;
            }

            public boolean hasOptimizeFor() {
                return (this.bitField0_ & 32) == 32;
            }

            public OptimizeMode getOptimizeFor() {
                return this.optimizeFor_;
            }

            public Builder setOptimizeFor(OptimizeMode value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 32;
                this.optimizeFor_ = value;
                onChanged();
                return this;
            }

            public Builder clearOptimizeFor() {
                this.bitField0_ &= -33;
                this.optimizeFor_ = OptimizeMode.SPEED;
                onChanged();
                return this;
            }

            public boolean hasGoPackage() {
                return (this.bitField0_ & 64) == 64;
            }

            public String getGoPackage() {
                ByteString ref = this.goPackage_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.goPackage_ = s;
                return s;
            }

            public ByteString getGoPackageBytes() {
                Object ref = this.goPackage_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.goPackage_ = b;
                return b;
            }

            public Builder setGoPackage(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 64;
                this.goPackage_ = value;
                onChanged();
                return this;
            }

            public Builder clearGoPackage() {
                this.bitField0_ &= -65;
                this.goPackage_ = FileOptions.getDefaultInstance().getGoPackage();
                onChanged();
                return this;
            }

            public Builder setGoPackageBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 64;
                this.goPackage_ = value;
                onChanged();
                return this;
            }

            public boolean hasCcGenericServices() {
                return (this.bitField0_ & 128) == 128;
            }

            public boolean getCcGenericServices() {
                return this.ccGenericServices_;
            }

            public Builder setCcGenericServices(boolean value) {
                this.bitField0_ |= 128;
                this.ccGenericServices_ = value;
                onChanged();
                return this;
            }

            public Builder clearCcGenericServices() {
                this.bitField0_ &= -129;
                this.ccGenericServices_ = false;
                onChanged();
                return this;
            }

            public boolean hasJavaGenericServices() {
                return (this.bitField0_ & 256) == 256;
            }

            public boolean getJavaGenericServices() {
                return this.javaGenericServices_;
            }

            public Builder setJavaGenericServices(boolean value) {
                this.bitField0_ |= 256;
                this.javaGenericServices_ = value;
                onChanged();
                return this;
            }

            public Builder clearJavaGenericServices() {
                this.bitField0_ &= -257;
                this.javaGenericServices_ = false;
                onChanged();
                return this;
            }

            public boolean hasPyGenericServices() {
                return (this.bitField0_ & 512) == 512;
            }

            public boolean getPyGenericServices() {
                return this.pyGenericServices_;
            }

            public Builder setPyGenericServices(boolean value) {
                this.bitField0_ |= 512;
                this.pyGenericServices_ = value;
                onChanged();
                return this;
            }

            public Builder clearPyGenericServices() {
                this.bitField0_ &= -513;
                this.pyGenericServices_ = false;
                onChanged();
                return this;
            }

            public boolean hasDeprecated() {
                return (this.bitField0_ & 1024) == 1024;
            }

            public boolean getDeprecated() {
                return this.deprecated_;
            }

            public Builder setDeprecated(boolean value) {
                this.bitField0_ |= 1024;
                this.deprecated_ = value;
                onChanged();
                return this;
            }

            public Builder clearDeprecated() {
                this.bitField0_ &= -1025;
                this.deprecated_ = false;
                onChanged();
                return this;
            }

            private void ensureUninterpretedOptionIsMutable() {
                if ((this.bitField0_ & 2048) != 2048) {
                    this.uninterpretedOption_ = new ArrayList(this.uninterpretedOption_);
                    this.bitField0_ |= 2048;
                }
            }

            public List<UninterpretedOption> getUninterpretedOptionList() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    return Collections.unmodifiableList(this.uninterpretedOption_);
                }
                return this.uninterpretedOptionBuilder_.getMessageList();
            }

            public int getUninterpretedOptionCount() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    return this.uninterpretedOption_.size();
                }
                return this.uninterpretedOptionBuilder_.getCount();
            }

            public UninterpretedOption getUninterpretedOption(int index) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    return (UninterpretedOption) this.uninterpretedOption_.get(index);
                }
                return (UninterpretedOption) this.uninterpretedOptionBuilder_.getMessage(index);
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption value) {
                if (this.uninterpretedOptionBuilder_ != null) {
                    this.uninterpretedOptionBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setUninterpretedOption(int index, Builder builderForValue) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption value) {
                if (this.uninterpretedOptionBuilder_ != null) {
                    this.uninterpretedOptionBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption value) {
                if (this.uninterpretedOptionBuilder_ != null) {
                    this.uninterpretedOptionBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addUninterpretedOption(Builder builderForValue) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addUninterpretedOption(int index, Builder builderForValue) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.uninterpretedOption_);
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearUninterpretedOption() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    this.uninterpretedOption_ = Collections.emptyList();
                    this.bitField0_ &= -2049;
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.clear();
                }
                return this;
            }

            public Builder removeUninterpretedOption(int index) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.remove(index);
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.remove(index);
                }
                return this;
            }

            public Builder getUninterpretedOptionBuilder(int index) {
                return (Builder) getUninterpretedOptionFieldBuilder().getBuilder(index);
            }

            public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    return (UninterpretedOptionOrBuilder) this.uninterpretedOption_.get(index);
                }
                return (UninterpretedOptionOrBuilder) this.uninterpretedOptionBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
                if (this.uninterpretedOptionBuilder_ != null) {
                    return this.uninterpretedOptionBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.uninterpretedOption_);
            }

            public Builder addUninterpretedOptionBuilder() {
                return (Builder) getUninterpretedOptionFieldBuilder().addBuilder(UninterpretedOption.getDefaultInstance());
            }

            public Builder addUninterpretedOptionBuilder(int index) {
                return (Builder) getUninterpretedOptionFieldBuilder().addBuilder(index, UninterpretedOption.getDefaultInstance());
            }

            public List<Builder> getUninterpretedOptionBuilderList() {
                return getUninterpretedOptionFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<UninterpretedOption, Builder, UninterpretedOptionOrBuilder> getUninterpretedOptionFieldBuilder() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    this.uninterpretedOptionBuilder_ = new RepeatedFieldBuilder(this.uninterpretedOption_, (this.bitField0_ & 2048) == 2048, getParentForChildren(), isClean());
                    this.uninterpretedOption_ = null;
                }
                return this.uninterpretedOptionBuilder_;
            }
        }

        public enum OptimizeMode implements ProtocolMessageEnum {
            SPEED(0, 1),
            CODE_SIZE(1, 2),
            LITE_RUNTIME(2, 3);
            
            public static final int CODE_SIZE_VALUE = 2;
            public static final int LITE_RUNTIME_VALUE = 3;
            public static final int SPEED_VALUE = 1;
            private static final OptimizeMode[] VALUES = null;
            private static EnumLiteMap<OptimizeMode> internalValueMap;
            private final int index;
            private final int value;

            static {
                internalValueMap = new EnumLiteMap<OptimizeMode>() {
                    public OptimizeMode findValueByNumber(int number) {
                        return OptimizeMode.valueOf(number);
                    }
                };
                VALUES = values();
            }

            public final int getNumber() {
                return this.value;
            }

            public static OptimizeMode valueOf(int value) {
                switch (value) {
                    case 1:
                        return SPEED;
                    case 2:
                        return CODE_SIZE;
                    case 3:
                        return LITE_RUNTIME;
                    default:
                        return null;
                }
            }

            public static EnumLiteMap<OptimizeMode> internalGetValueMap() {
                return internalValueMap;
            }

            public final EnumValueDescriptor getValueDescriptor() {
                return (EnumValueDescriptor) getDescriptor().getValues().get(this.index);
            }

            public final EnumDescriptor getDescriptorForType() {
                return getDescriptor();
            }

            public static final EnumDescriptor getDescriptor() {
                return (EnumDescriptor) FileOptions.getDescriptor().getEnumTypes().get(0);
            }

            public static OptimizeMode valueOf(EnumValueDescriptor desc) {
                if (desc.getType() == getDescriptor()) {
                    return VALUES[desc.getIndex()];
                }
                throw new IllegalArgumentException("EnumValueDescriptor is not for this type.");
            }

            private OptimizeMode(int index, int value) {
                this.index = index;
                this.value = value;
            }
        }

        private FileOptions(ExtendableBuilder<FileOptions, ?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private FileOptions(boolean noInit) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static FileOptions getDefaultInstance() {
            return defaultInstance;
        }

        public FileOptions getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private FileOptions(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    ByteString bs;
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 10:
                            bs = input.readBytes();
                            this.bitField0_ |= 1;
                            this.javaPackage_ = bs;
                            break;
                        case 66:
                            bs = input.readBytes();
                            this.bitField0_ |= 2;
                            this.javaOuterClassname_ = bs;
                            break;
                        case 72:
                            int rawValue = input.readEnum();
                            OptimizeMode value = OptimizeMode.valueOf(rawValue);
                            if (value != null) {
                                this.bitField0_ |= 32;
                                this.optimizeFor_ = value;
                                break;
                            }
                            unknownFields.mergeVarintField(9, rawValue);
                            break;
                        case 80:
                            this.bitField0_ |= 4;
                            this.javaMultipleFiles_ = input.readBool();
                            break;
                        case 90:
                            bs = input.readBytes();
                            this.bitField0_ |= 64;
                            this.goPackage_ = bs;
                            break;
                        case 128:
                            this.bitField0_ |= 128;
                            this.ccGenericServices_ = input.readBool();
                            break;
                        case 136:
                            this.bitField0_ |= 256;
                            this.javaGenericServices_ = input.readBool();
                            break;
                        case 144:
                            this.bitField0_ |= 512;
                            this.pyGenericServices_ = input.readBool();
                            break;
                        case 160:
                            this.bitField0_ |= 8;
                            this.javaGenerateEqualsAndHash_ = input.readBool();
                            break;
                        case 184:
                            this.bitField0_ |= 1024;
                            this.deprecated_ = input.readBool();
                            break;
                        case 216:
                            this.bitField0_ |= 16;
                            this.javaStringCheckUtf8_ = input.readBool();
                            break;
                        case 7994:
                            if ((mutable_bitField0_ & 2048) != 2048) {
                                this.uninterpretedOption_ = new ArrayList();
                                mutable_bitField0_ |= 2048;
                            }
                            this.uninterpretedOption_.add(input.readMessage(UninterpretedOption.PARSER, extensionRegistry));
                            break;
                        default:
                            if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                                break;
                            }
                            break;
                    }
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                } catch (Throwable th) {
                    if ((mutable_bitField0_ & 2048) == 2048) {
                        this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                }
            }
            if ((mutable_bitField0_ & 2048) == 2048) {
                this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_FileOptions_descriptor;
        }

        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_FileOptions_fieldAccessorTable.ensureFieldAccessorsInitialized(FileOptions.class, Builder.class);
        }

        static {
            defaultInstance.initFields();
        }

        public Parser<FileOptions> getParserForType() {
            return PARSER;
        }

        public boolean hasJavaPackage() {
            return (this.bitField0_ & 1) == 1;
        }

        public String getJavaPackage() {
            ByteString ref = this.javaPackage_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.javaPackage_ = s;
            }
            return s;
        }

        public ByteString getJavaPackageBytes() {
            Object ref = this.javaPackage_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.javaPackage_ = b;
            return b;
        }

        public boolean hasJavaOuterClassname() {
            return (this.bitField0_ & 2) == 2;
        }

        public String getJavaOuterClassname() {
            ByteString ref = this.javaOuterClassname_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.javaOuterClassname_ = s;
            }
            return s;
        }

        public ByteString getJavaOuterClassnameBytes() {
            Object ref = this.javaOuterClassname_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.javaOuterClassname_ = b;
            return b;
        }

        public boolean hasJavaMultipleFiles() {
            return (this.bitField0_ & 4) == 4;
        }

        public boolean getJavaMultipleFiles() {
            return this.javaMultipleFiles_;
        }

        public boolean hasJavaGenerateEqualsAndHash() {
            return (this.bitField0_ & 8) == 8;
        }

        public boolean getJavaGenerateEqualsAndHash() {
            return this.javaGenerateEqualsAndHash_;
        }

        public boolean hasJavaStringCheckUtf8() {
            return (this.bitField0_ & 16) == 16;
        }

        public boolean getJavaStringCheckUtf8() {
            return this.javaStringCheckUtf8_;
        }

        public boolean hasOptimizeFor() {
            return (this.bitField0_ & 32) == 32;
        }

        public OptimizeMode getOptimizeFor() {
            return this.optimizeFor_;
        }

        public boolean hasGoPackage() {
            return (this.bitField0_ & 64) == 64;
        }

        public String getGoPackage() {
            ByteString ref = this.goPackage_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.goPackage_ = s;
            }
            return s;
        }

        public ByteString getGoPackageBytes() {
            Object ref = this.goPackage_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.goPackage_ = b;
            return b;
        }

        public boolean hasCcGenericServices() {
            return (this.bitField0_ & 128) == 128;
        }

        public boolean getCcGenericServices() {
            return this.ccGenericServices_;
        }

        public boolean hasJavaGenericServices() {
            return (this.bitField0_ & 256) == 256;
        }

        public boolean getJavaGenericServices() {
            return this.javaGenericServices_;
        }

        public boolean hasPyGenericServices() {
            return (this.bitField0_ & 512) == 512;
        }

        public boolean getPyGenericServices() {
            return this.pyGenericServices_;
        }

        public boolean hasDeprecated() {
            return (this.bitField0_ & 1024) == 1024;
        }

        public boolean getDeprecated() {
            return this.deprecated_;
        }

        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        public UninterpretedOption getUninterpretedOption(int index) {
            return (UninterpretedOption) this.uninterpretedOption_.get(index);
        }

        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
            return (UninterpretedOptionOrBuilder) this.uninterpretedOption_.get(index);
        }

        private void initFields() {
            this.javaPackage_ = "";
            this.javaOuterClassname_ = "";
            this.javaMultipleFiles_ = false;
            this.javaGenerateEqualsAndHash_ = false;
            this.javaStringCheckUtf8_ = false;
            this.optimizeFor_ = OptimizeMode.SPEED;
            this.goPackage_ = "";
            this.ccGenericServices_ = false;
            this.javaGenericServices_ = false;
            this.pyGenericServices_ = false;
            this.deprecated_ = false;
            this.uninterpretedOption_ = Collections.emptyList();
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == (byte) 1) {
                return true;
            }
            if (isInitialized == (byte) 0) {
                return false;
            }
            int i = 0;
            while (i < getUninterpretedOptionCount()) {
                if (getUninterpretedOption(i).isInitialized()) {
                    i++;
                } else {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (extensionsAreInitialized()) {
                this.memoizedIsInitialized = (byte) 1;
                return true;
            }
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            getSerializedSize();
            ExtensionWriter extensionWriter = newExtensionWriter();
            if ((this.bitField0_ & 1) == 1) {
                output.writeBytes(1, getJavaPackageBytes());
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeBytes(8, getJavaOuterClassnameBytes());
            }
            if ((this.bitField0_ & 32) == 32) {
                output.writeEnum(9, this.optimizeFor_.getNumber());
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeBool(10, this.javaMultipleFiles_);
            }
            if ((this.bitField0_ & 64) == 64) {
                output.writeBytes(11, getGoPackageBytes());
            }
            if ((this.bitField0_ & 128) == 128) {
                output.writeBool(16, this.ccGenericServices_);
            }
            if ((this.bitField0_ & 256) == 256) {
                output.writeBool(17, this.javaGenericServices_);
            }
            if ((this.bitField0_ & 512) == 512) {
                output.writeBool(18, this.pyGenericServices_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeBool(20, this.javaGenerateEqualsAndHash_);
            }
            if ((this.bitField0_ & 1024) == 1024) {
                output.writeBool(23, this.deprecated_);
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeBool(27, this.javaStringCheckUtf8_);
            }
            for (int i = 0; i < this.uninterpretedOption_.size(); i++) {
                output.writeMessage(999, (MessageLite) this.uninterpretedOption_.get(i));
            }
            extensionWriter.writeUntil(536870912, output);
            getUnknownFields().writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
                size = 0 + CodedOutputStream.computeBytesSize(1, getJavaPackageBytes());
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeBytesSize(8, getJavaOuterClassnameBytes());
            }
            if ((this.bitField0_ & 32) == 32) {
                size += CodedOutputStream.computeEnumSize(9, this.optimizeFor_.getNumber());
            }
            if ((this.bitField0_ & 4) == 4) {
                size += CodedOutputStream.computeBoolSize(10, this.javaMultipleFiles_);
            }
            if ((this.bitField0_ & 64) == 64) {
                size += CodedOutputStream.computeBytesSize(11, getGoPackageBytes());
            }
            if ((this.bitField0_ & 128) == 128) {
                size += CodedOutputStream.computeBoolSize(16, this.ccGenericServices_);
            }
            if ((this.bitField0_ & 256) == 256) {
                size += CodedOutputStream.computeBoolSize(17, this.javaGenericServices_);
            }
            if ((this.bitField0_ & 512) == 512) {
                size += CodedOutputStream.computeBoolSize(18, this.pyGenericServices_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size += CodedOutputStream.computeBoolSize(20, this.javaGenerateEqualsAndHash_);
            }
            if ((this.bitField0_ & 1024) == 1024) {
                size += CodedOutputStream.computeBoolSize(23, this.deprecated_);
            }
            if ((this.bitField0_ & 16) == 16) {
                size += CodedOutputStream.computeBoolSize(27, this.javaStringCheckUtf8_);
            }
            for (int i = 0; i < this.uninterpretedOption_.size(); i++) {
                size += CodedOutputStream.computeMessageSize(999, (MessageLite) this.uninterpretedOption_.get(i));
            }
            size = (size + extensionsSerializedSize()) + getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = size;
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static FileOptions parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (FileOptions) PARSER.parseFrom(data);
        }

        public static FileOptions parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FileOptions) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileOptions parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (FileOptions) PARSER.parseFrom(data);
        }

        public static FileOptions parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (FileOptions) PARSER.parseFrom(data, extensionRegistry);
        }

        public static FileOptions parseFrom(InputStream input) throws IOException {
            return (FileOptions) PARSER.parseFrom(input);
        }

        public static FileOptions parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FileOptions) PARSER.parseFrom(input, extensionRegistry);
        }

        public static FileOptions parseDelimitedFrom(InputStream input) throws IOException {
            return (FileOptions) PARSER.parseDelimitedFrom(input);
        }

        public static FileOptions parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FileOptions) PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static FileOptions parseFrom(CodedInputStream input) throws IOException {
            return (FileOptions) PARSER.parseFrom(input);
        }

        public static FileOptions parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (FileOptions) PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(FileOptions prototype) {
            return newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return newBuilder(this);
        }

        protected Builder newBuilderForType(BuilderParent parent) {
            return new Builder(parent);
        }
    }

    public interface MessageOptionsOrBuilder extends ExtendableMessageOrBuilder<MessageOptions> {
        boolean getDeprecated();

        boolean getMessageSetWireFormat();

        boolean getNoStandardDescriptorAccessor();

        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();

        UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int i);

        List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList();

        boolean hasDeprecated();

        boolean hasMessageSetWireFormat();

        boolean hasNoStandardDescriptorAccessor();
    }

    public static final class MessageOptions extends ExtendableMessage<MessageOptions> implements MessageOptionsOrBuilder {
        public static final int DEPRECATED_FIELD_NUMBER = 3;
        public static final int MESSAGE_SET_WIRE_FORMAT_FIELD_NUMBER = 1;
        public static final int NO_STANDARD_DESCRIPTOR_ACCESSOR_FIELD_NUMBER = 2;
        public static Parser<MessageOptions> PARSER = new AbstractParser<MessageOptions>() {
            public MessageOptions parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new MessageOptions(input, extensionRegistry);
            }
        };
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        private static final MessageOptions defaultInstance = new MessageOptions(true);
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private boolean deprecated_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private boolean messageSetWireFormat_;
        private boolean noStandardDescriptorAccessor_;
        private List<UninterpretedOption> uninterpretedOption_;
        private final UnknownFieldSet unknownFields;

        public static final class Builder extends ExtendableBuilder<MessageOptions, Builder> implements MessageOptionsOrBuilder {
            private int bitField0_;
            private boolean deprecated_;
            private boolean messageSetWireFormat_;
            private boolean noStandardDescriptorAccessor_;
            private RepeatedFieldBuilder<UninterpretedOption, Builder, UninterpretedOptionOrBuilder> uninterpretedOptionBuilder_;
            private List<UninterpretedOption> uninterpretedOption_;

            public static final Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_MessageOptions_descriptor;
            }

            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_MessageOptions_fieldAccessorTable.ensureFieldAccessorsInitialized(MessageOptions.class, Builder.class);
            }

            private Builder() {
                this.uninterpretedOption_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.uninterpretedOption_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getUninterpretedOptionFieldBuilder();
                }
            }

            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                this.messageSetWireFormat_ = false;
                this.bitField0_ &= -2;
                this.noStandardDescriptorAccessor_ = false;
                this.bitField0_ &= -3;
                this.deprecated_ = false;
                this.bitField0_ &= -5;
                if (this.uninterpretedOptionBuilder_ == null) {
                    this.uninterpretedOption_ = Collections.emptyList();
                    this.bitField0_ &= -9;
                } else {
                    this.uninterpretedOptionBuilder_.clear();
                }
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_MessageOptions_descriptor;
            }

            public MessageOptions getDefaultInstanceForType() {
                return MessageOptions.getDefaultInstance();
            }

            public MessageOptions build() {
                MessageOptions result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw com.google.protobuf.AbstractMessage.Builder.newUninitializedMessageException(result);
            }

            public MessageOptions buildPartial() {
                MessageOptions result = new MessageOptions((ExtendableBuilder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.messageSetWireFormat_ = this.messageSetWireFormat_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.noStandardDescriptorAccessor_ = this.noStandardDescriptorAccessor_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.deprecated_ = this.deprecated_;
                if (this.uninterpretedOptionBuilder_ == null) {
                    if ((this.bitField0_ & 8) == 8) {
                        this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
                        this.bitField0_ &= -9;
                    }
                    result.uninterpretedOption_ = this.uninterpretedOption_;
                } else {
                    result.uninterpretedOption_ = this.uninterpretedOptionBuilder_.build();
                }
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof MessageOptions) {
                    return mergeFrom((MessageOptions) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(MessageOptions other) {
                RepeatedFieldBuilder repeatedFieldBuilder = null;
                if (other != MessageOptions.getDefaultInstance()) {
                    if (other.hasMessageSetWireFormat()) {
                        setMessageSetWireFormat(other.getMessageSetWireFormat());
                    }
                    if (other.hasNoStandardDescriptorAccessor()) {
                        setNoStandardDescriptorAccessor(other.getNoStandardDescriptorAccessor());
                    }
                    if (other.hasDeprecated()) {
                        setDeprecated(other.getDeprecated());
                    }
                    if (this.uninterpretedOptionBuilder_ == null) {
                        if (!other.uninterpretedOption_.isEmpty()) {
                            if (this.uninterpretedOption_.isEmpty()) {
                                this.uninterpretedOption_ = other.uninterpretedOption_;
                                this.bitField0_ &= -9;
                            } else {
                                ensureUninterpretedOptionIsMutable();
                                this.uninterpretedOption_.addAll(other.uninterpretedOption_);
                            }
                            onChanged();
                        }
                    } else if (!other.uninterpretedOption_.isEmpty()) {
                        if (this.uninterpretedOptionBuilder_.isEmpty()) {
                            this.uninterpretedOptionBuilder_.dispose();
                            this.uninterpretedOptionBuilder_ = null;
                            this.uninterpretedOption_ = other.uninterpretedOption_;
                            this.bitField0_ &= -9;
                            if (GeneratedMessage.alwaysUseFieldBuilders) {
                                repeatedFieldBuilder = getUninterpretedOptionFieldBuilder();
                            }
                            this.uninterpretedOptionBuilder_ = repeatedFieldBuilder;
                        } else {
                            this.uninterpretedOptionBuilder_.addAllMessages(other.uninterpretedOption_);
                        }
                    }
                    mergeExtensionFields(other);
                    mergeUnknownFields(other.getUnknownFields());
                }
                return this;
            }

            public final boolean isInitialized() {
                for (int i = 0; i < getUninterpretedOptionCount(); i++) {
                    if (!getUninterpretedOption(i).isInitialized()) {
                        return false;
                    }
                }
                if (extensionsAreInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                MessageOptions parsedMessage = null;
                try {
                    parsedMessage = (MessageOptions) MessageOptions.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    parsedMessage = (MessageOptions) e.getUnfinishedMessage();
                    throw e;
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
            }

            public boolean hasMessageSetWireFormat() {
                return (this.bitField0_ & 1) == 1;
            }

            public boolean getMessageSetWireFormat() {
                return this.messageSetWireFormat_;
            }

            public Builder setMessageSetWireFormat(boolean value) {
                this.bitField0_ |= 1;
                this.messageSetWireFormat_ = value;
                onChanged();
                return this;
            }

            public Builder clearMessageSetWireFormat() {
                this.bitField0_ &= -2;
                this.messageSetWireFormat_ = false;
                onChanged();
                return this;
            }

            public boolean hasNoStandardDescriptorAccessor() {
                return (this.bitField0_ & 2) == 2;
            }

            public boolean getNoStandardDescriptorAccessor() {
                return this.noStandardDescriptorAccessor_;
            }

            public Builder setNoStandardDescriptorAccessor(boolean value) {
                this.bitField0_ |= 2;
                this.noStandardDescriptorAccessor_ = value;
                onChanged();
                return this;
            }

            public Builder clearNoStandardDescriptorAccessor() {
                this.bitField0_ &= -3;
                this.noStandardDescriptorAccessor_ = false;
                onChanged();
                return this;
            }

            public boolean hasDeprecated() {
                return (this.bitField0_ & 4) == 4;
            }

            public boolean getDeprecated() {
                return this.deprecated_;
            }

            public Builder setDeprecated(boolean value) {
                this.bitField0_ |= 4;
                this.deprecated_ = value;
                onChanged();
                return this;
            }

            public Builder clearDeprecated() {
                this.bitField0_ &= -5;
                this.deprecated_ = false;
                onChanged();
                return this;
            }

            private void ensureUninterpretedOptionIsMutable() {
                if ((this.bitField0_ & 8) != 8) {
                    this.uninterpretedOption_ = new ArrayList(this.uninterpretedOption_);
                    this.bitField0_ |= 8;
                }
            }

            public List<UninterpretedOption> getUninterpretedOptionList() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    return Collections.unmodifiableList(this.uninterpretedOption_);
                }
                return this.uninterpretedOptionBuilder_.getMessageList();
            }

            public int getUninterpretedOptionCount() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    return this.uninterpretedOption_.size();
                }
                return this.uninterpretedOptionBuilder_.getCount();
            }

            public UninterpretedOption getUninterpretedOption(int index) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    return (UninterpretedOption) this.uninterpretedOption_.get(index);
                }
                return (UninterpretedOption) this.uninterpretedOptionBuilder_.getMessage(index);
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption value) {
                if (this.uninterpretedOptionBuilder_ != null) {
                    this.uninterpretedOptionBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setUninterpretedOption(int index, Builder builderForValue) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption value) {
                if (this.uninterpretedOptionBuilder_ != null) {
                    this.uninterpretedOptionBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption value) {
                if (this.uninterpretedOptionBuilder_ != null) {
                    this.uninterpretedOptionBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addUninterpretedOption(Builder builderForValue) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addUninterpretedOption(int index, Builder builderForValue) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.uninterpretedOption_);
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearUninterpretedOption() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    this.uninterpretedOption_ = Collections.emptyList();
                    this.bitField0_ &= -9;
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.clear();
                }
                return this;
            }

            public Builder removeUninterpretedOption(int index) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.remove(index);
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.remove(index);
                }
                return this;
            }

            public Builder getUninterpretedOptionBuilder(int index) {
                return (Builder) getUninterpretedOptionFieldBuilder().getBuilder(index);
            }

            public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    return (UninterpretedOptionOrBuilder) this.uninterpretedOption_.get(index);
                }
                return (UninterpretedOptionOrBuilder) this.uninterpretedOptionBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
                if (this.uninterpretedOptionBuilder_ != null) {
                    return this.uninterpretedOptionBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.uninterpretedOption_);
            }

            public Builder addUninterpretedOptionBuilder() {
                return (Builder) getUninterpretedOptionFieldBuilder().addBuilder(UninterpretedOption.getDefaultInstance());
            }

            public Builder addUninterpretedOptionBuilder(int index) {
                return (Builder) getUninterpretedOptionFieldBuilder().addBuilder(index, UninterpretedOption.getDefaultInstance());
            }

            public List<Builder> getUninterpretedOptionBuilderList() {
                return getUninterpretedOptionFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<UninterpretedOption, Builder, UninterpretedOptionOrBuilder> getUninterpretedOptionFieldBuilder() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    this.uninterpretedOptionBuilder_ = new RepeatedFieldBuilder(this.uninterpretedOption_, (this.bitField0_ & 8) == 8, getParentForChildren(), isClean());
                    this.uninterpretedOption_ = null;
                }
                return this.uninterpretedOptionBuilder_;
            }
        }

        private MessageOptions(ExtendableBuilder<MessageOptions, ?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private MessageOptions(boolean noInit) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static MessageOptions getDefaultInstance() {
            return defaultInstance;
        }

        public MessageOptions getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private MessageOptions(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 8:
                            this.bitField0_ |= 1;
                            this.messageSetWireFormat_ = input.readBool();
                            break;
                        case 16:
                            this.bitField0_ |= 2;
                            this.noStandardDescriptorAccessor_ = input.readBool();
                            break;
                        case 24:
                            this.bitField0_ |= 4;
                            this.deprecated_ = input.readBool();
                            break;
                        case 7994:
                            if ((mutable_bitField0_ & 8) != 8) {
                                this.uninterpretedOption_ = new ArrayList();
                                mutable_bitField0_ |= 8;
                            }
                            this.uninterpretedOption_.add(input.readMessage(UninterpretedOption.PARSER, extensionRegistry));
                            break;
                        default:
                            if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                                break;
                            }
                            break;
                    }
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                } catch (Throwable th) {
                    if ((mutable_bitField0_ & 8) == 8) {
                        this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                }
            }
            if ((mutable_bitField0_ & 8) == 8) {
                this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_MessageOptions_descriptor;
        }

        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_MessageOptions_fieldAccessorTable.ensureFieldAccessorsInitialized(MessageOptions.class, Builder.class);
        }

        static {
            defaultInstance.initFields();
        }

        public Parser<MessageOptions> getParserForType() {
            return PARSER;
        }

        public boolean hasMessageSetWireFormat() {
            return (this.bitField0_ & 1) == 1;
        }

        public boolean getMessageSetWireFormat() {
            return this.messageSetWireFormat_;
        }

        public boolean hasNoStandardDescriptorAccessor() {
            return (this.bitField0_ & 2) == 2;
        }

        public boolean getNoStandardDescriptorAccessor() {
            return this.noStandardDescriptorAccessor_;
        }

        public boolean hasDeprecated() {
            return (this.bitField0_ & 4) == 4;
        }

        public boolean getDeprecated() {
            return this.deprecated_;
        }

        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        public UninterpretedOption getUninterpretedOption(int index) {
            return (UninterpretedOption) this.uninterpretedOption_.get(index);
        }

        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
            return (UninterpretedOptionOrBuilder) this.uninterpretedOption_.get(index);
        }

        private void initFields() {
            this.messageSetWireFormat_ = false;
            this.noStandardDescriptorAccessor_ = false;
            this.deprecated_ = false;
            this.uninterpretedOption_ = Collections.emptyList();
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == (byte) 1) {
                return true;
            }
            if (isInitialized == (byte) 0) {
                return false;
            }
            int i = 0;
            while (i < getUninterpretedOptionCount()) {
                if (getUninterpretedOption(i).isInitialized()) {
                    i++;
                } else {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (extensionsAreInitialized()) {
                this.memoizedIsInitialized = (byte) 1;
                return true;
            }
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            getSerializedSize();
            ExtensionWriter extensionWriter = newExtensionWriter();
            if ((this.bitField0_ & 1) == 1) {
                output.writeBool(1, this.messageSetWireFormat_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeBool(2, this.noStandardDescriptorAccessor_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeBool(3, this.deprecated_);
            }
            for (int i = 0; i < this.uninterpretedOption_.size(); i++) {
                output.writeMessage(999, (MessageLite) this.uninterpretedOption_.get(i));
            }
            extensionWriter.writeUntil(536870912, output);
            getUnknownFields().writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
                size = 0 + CodedOutputStream.computeBoolSize(1, this.messageSetWireFormat_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeBoolSize(2, this.noStandardDescriptorAccessor_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size += CodedOutputStream.computeBoolSize(3, this.deprecated_);
            }
            for (int i = 0; i < this.uninterpretedOption_.size(); i++) {
                size += CodedOutputStream.computeMessageSize(999, (MessageLite) this.uninterpretedOption_.get(i));
            }
            size = (size + extensionsSerializedSize()) + getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = size;
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static MessageOptions parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MessageOptions) PARSER.parseFrom(data);
        }

        public static MessageOptions parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MessageOptions) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MessageOptions parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MessageOptions) PARSER.parseFrom(data);
        }

        public static MessageOptions parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MessageOptions) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MessageOptions parseFrom(InputStream input) throws IOException {
            return (MessageOptions) PARSER.parseFrom(input);
        }

        public static MessageOptions parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MessageOptions) PARSER.parseFrom(input, extensionRegistry);
        }

        public static MessageOptions parseDelimitedFrom(InputStream input) throws IOException {
            return (MessageOptions) PARSER.parseDelimitedFrom(input);
        }

        public static MessageOptions parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MessageOptions) PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static MessageOptions parseFrom(CodedInputStream input) throws IOException {
            return (MessageOptions) PARSER.parseFrom(input);
        }

        public static MessageOptions parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MessageOptions) PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(MessageOptions prototype) {
            return newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return newBuilder(this);
        }

        protected Builder newBuilderForType(BuilderParent parent) {
            return new Builder(parent);
        }
    }

    public interface MethodDescriptorProtoOrBuilder extends MessageOrBuilder {
        String getInputType();

        ByteString getInputTypeBytes();

        String getName();

        ByteString getNameBytes();

        MethodOptions getOptions();

        MethodOptionsOrBuilder getOptionsOrBuilder();

        String getOutputType();

        ByteString getOutputTypeBytes();

        boolean hasInputType();

        boolean hasName();

        boolean hasOptions();

        boolean hasOutputType();
    }

    public static final class MethodDescriptorProto extends GeneratedMessage implements MethodDescriptorProtoOrBuilder {
        public static final int INPUT_TYPE_FIELD_NUMBER = 2;
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int OPTIONS_FIELD_NUMBER = 4;
        public static final int OUTPUT_TYPE_FIELD_NUMBER = 3;
        public static Parser<MethodDescriptorProto> PARSER = new AbstractParser<MethodDescriptorProto>() {
            public MethodDescriptorProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new MethodDescriptorProto(input, extensionRegistry);
            }
        };
        private static final MethodDescriptorProto defaultInstance = new MethodDescriptorProto(true);
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private Object inputType_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private Object name_;
        private MethodOptions options_;
        private Object outputType_;
        private final UnknownFieldSet unknownFields;

        public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> implements MethodDescriptorProtoOrBuilder {
            private int bitField0_;
            private Object inputType_;
            private Object name_;
            private SingleFieldBuilder<MethodOptions, Builder, MethodOptionsOrBuilder> optionsBuilder_;
            private MethodOptions options_;
            private Object outputType_;

            public static final Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_MethodDescriptorProto_descriptor;
            }

            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_MethodDescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MethodDescriptorProto.class, Builder.class);
            }

            private Builder() {
                this.name_ = "";
                this.inputType_ = "";
                this.outputType_ = "";
                this.options_ = MethodOptions.getDefaultInstance();
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.name_ = "";
                this.inputType_ = "";
                this.outputType_ = "";
                this.options_ = MethodOptions.getDefaultInstance();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getOptionsFieldBuilder();
                }
            }

            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                this.name_ = "";
                this.bitField0_ &= -2;
                this.inputType_ = "";
                this.bitField0_ &= -3;
                this.outputType_ = "";
                this.bitField0_ &= -5;
                if (this.optionsBuilder_ == null) {
                    this.options_ = MethodOptions.getDefaultInstance();
                } else {
                    this.optionsBuilder_.clear();
                }
                this.bitField0_ &= -9;
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_MethodDescriptorProto_descriptor;
            }

            public MethodDescriptorProto getDefaultInstanceForType() {
                return MethodDescriptorProto.getDefaultInstance();
            }

            public MethodDescriptorProto build() {
                MethodDescriptorProto result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw com.google.protobuf.AbstractMessage.Builder.newUninitializedMessageException(result);
            }

            public MethodDescriptorProto buildPartial() {
                MethodDescriptorProto result = new MethodDescriptorProto((com.google.protobuf.GeneratedMessage.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.name_ = this.name_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.inputType_ = this.inputType_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.outputType_ = this.outputType_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                if (this.optionsBuilder_ == null) {
                    result.options_ = this.options_;
                } else {
                    result.options_ = (MethodOptions) this.optionsBuilder_.build();
                }
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof MethodDescriptorProto) {
                    return mergeFrom((MethodDescriptorProto) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(MethodDescriptorProto other) {
                if (other != MethodDescriptorProto.getDefaultInstance()) {
                    if (other.hasName()) {
                        this.bitField0_ |= 1;
                        this.name_ = other.name_;
                        onChanged();
                    }
                    if (other.hasInputType()) {
                        this.bitField0_ |= 2;
                        this.inputType_ = other.inputType_;
                        onChanged();
                    }
                    if (other.hasOutputType()) {
                        this.bitField0_ |= 4;
                        this.outputType_ = other.outputType_;
                        onChanged();
                    }
                    if (other.hasOptions()) {
                        mergeOptions(other.getOptions());
                    }
                    mergeUnknownFields(other.getUnknownFields());
                }
                return this;
            }

            public final boolean isInitialized() {
                if (!hasOptions() || getOptions().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                MethodDescriptorProto parsedMessage = null;
                try {
                    parsedMessage = (MethodDescriptorProto) MethodDescriptorProto.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    parsedMessage = (MethodDescriptorProto) e.getUnfinishedMessage();
                    throw e;
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
            }

            public boolean hasName() {
                return (this.bitField0_ & 1) == 1;
            }

            public String getName() {
                ByteString ref = this.name_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.name_ = s;
                return s;
            }

            public ByteString getNameBytes() {
                Object ref = this.name_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.name_ = b;
                return b;
            }

            public Builder setName(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.name_ = value;
                onChanged();
                return this;
            }

            public Builder clearName() {
                this.bitField0_ &= -2;
                this.name_ = MethodDescriptorProto.getDefaultInstance().getName();
                onChanged();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.name_ = value;
                onChanged();
                return this;
            }

            public boolean hasInputType() {
                return (this.bitField0_ & 2) == 2;
            }

            public String getInputType() {
                ByteString ref = this.inputType_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.inputType_ = s;
                return s;
            }

            public ByteString getInputTypeBytes() {
                Object ref = this.inputType_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.inputType_ = b;
                return b;
            }

            public Builder setInputType(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.inputType_ = value;
                onChanged();
                return this;
            }

            public Builder clearInputType() {
                this.bitField0_ &= -3;
                this.inputType_ = MethodDescriptorProto.getDefaultInstance().getInputType();
                onChanged();
                return this;
            }

            public Builder setInputTypeBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.inputType_ = value;
                onChanged();
                return this;
            }

            public boolean hasOutputType() {
                return (this.bitField0_ & 4) == 4;
            }

            public String getOutputType() {
                ByteString ref = this.outputType_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.outputType_ = s;
                return s;
            }

            public ByteString getOutputTypeBytes() {
                Object ref = this.outputType_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.outputType_ = b;
                return b;
            }

            public Builder setOutputType(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 4;
                this.outputType_ = value;
                onChanged();
                return this;
            }

            public Builder clearOutputType() {
                this.bitField0_ &= -5;
                this.outputType_ = MethodDescriptorProto.getDefaultInstance().getOutputType();
                onChanged();
                return this;
            }

            public Builder setOutputTypeBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 4;
                this.outputType_ = value;
                onChanged();
                return this;
            }

            public boolean hasOptions() {
                return (this.bitField0_ & 8) == 8;
            }

            public MethodOptions getOptions() {
                if (this.optionsBuilder_ == null) {
                    return this.options_;
                }
                return (MethodOptions) this.optionsBuilder_.getMessage();
            }

            public Builder setOptions(MethodOptions value) {
                if (this.optionsBuilder_ != null) {
                    this.optionsBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.options_ = value;
                    onChanged();
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder setOptions(Builder builderForValue) {
                if (this.optionsBuilder_ == null) {
                    this.options_ = builderForValue.build();
                    onChanged();
                } else {
                    this.optionsBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder mergeOptions(MethodOptions value) {
                if (this.optionsBuilder_ == null) {
                    if ((this.bitField0_ & 8) != 8 || this.options_ == MethodOptions.getDefaultInstance()) {
                        this.options_ = value;
                    } else {
                        this.options_ = MethodOptions.newBuilder(this.options_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.optionsBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 8;
                return this;
            }

            public Builder clearOptions() {
                if (this.optionsBuilder_ == null) {
                    this.options_ = MethodOptions.getDefaultInstance();
                    onChanged();
                } else {
                    this.optionsBuilder_.clear();
                }
                this.bitField0_ &= -9;
                return this;
            }

            public Builder getOptionsBuilder() {
                this.bitField0_ |= 8;
                onChanged();
                return (Builder) getOptionsFieldBuilder().getBuilder();
            }

            public MethodOptionsOrBuilder getOptionsOrBuilder() {
                if (this.optionsBuilder_ != null) {
                    return (MethodOptionsOrBuilder) this.optionsBuilder_.getMessageOrBuilder();
                }
                return this.options_;
            }

            private SingleFieldBuilder<MethodOptions, Builder, MethodOptionsOrBuilder> getOptionsFieldBuilder() {
                if (this.optionsBuilder_ == null) {
                    this.optionsBuilder_ = new SingleFieldBuilder(getOptions(), getParentForChildren(), isClean());
                    this.options_ = null;
                }
                return this.optionsBuilder_;
            }
        }

        private MethodDescriptorProto(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private MethodDescriptorProto(boolean noInit) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static MethodDescriptorProto getDefaultInstance() {
            return defaultInstance;
        }

        public MethodDescriptorProto getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private MethodDescriptorProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    ByteString bs;
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 10:
                            bs = input.readBytes();
                            this.bitField0_ |= 1;
                            this.name_ = bs;
                            break;
                        case 18:
                            bs = input.readBytes();
                            this.bitField0_ |= 2;
                            this.inputType_ = bs;
                            break;
                        case 26:
                            bs = input.readBytes();
                            this.bitField0_ |= 4;
                            this.outputType_ = bs;
                            break;
                        case 34:
                            Builder subBuilder = null;
                            if ((this.bitField0_ & 8) == 8) {
                                subBuilder = this.options_.toBuilder();
                            }
                            this.options_ = (MethodOptions) input.readMessage(MethodOptions.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.options_);
                                this.options_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 8;
                            break;
                        default:
                            if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                                break;
                            }
                            break;
                    }
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                } catch (Throwable th) {
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                }
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_MethodDescriptorProto_descriptor;
        }

        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_MethodDescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(MethodDescriptorProto.class, Builder.class);
        }

        static {
            defaultInstance.initFields();
        }

        public Parser<MethodDescriptorProto> getParserForType() {
            return PARSER;
        }

        public boolean hasName() {
            return (this.bitField0_ & 1) == 1;
        }

        public String getName() {
            ByteString ref = this.name_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.name_ = s;
            }
            return s;
        }

        public ByteString getNameBytes() {
            Object ref = this.name_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.name_ = b;
            return b;
        }

        public boolean hasInputType() {
            return (this.bitField0_ & 2) == 2;
        }

        public String getInputType() {
            ByteString ref = this.inputType_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.inputType_ = s;
            }
            return s;
        }

        public ByteString getInputTypeBytes() {
            Object ref = this.inputType_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.inputType_ = b;
            return b;
        }

        public boolean hasOutputType() {
            return (this.bitField0_ & 4) == 4;
        }

        public String getOutputType() {
            ByteString ref = this.outputType_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.outputType_ = s;
            }
            return s;
        }

        public ByteString getOutputTypeBytes() {
            Object ref = this.outputType_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.outputType_ = b;
            return b;
        }

        public boolean hasOptions() {
            return (this.bitField0_ & 8) == 8;
        }

        public MethodOptions getOptions() {
            return this.options_;
        }

        public MethodOptionsOrBuilder getOptionsOrBuilder() {
            return this.options_;
        }

        private void initFields() {
            this.name_ = "";
            this.inputType_ = "";
            this.outputType_ = "";
            this.options_ = MethodOptions.getDefaultInstance();
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == (byte) 1) {
                return true;
            }
            if (isInitialized == (byte) 0) {
                return false;
            }
            if (!hasOptions() || getOptions().isInitialized()) {
                this.memoizedIsInitialized = (byte) 1;
                return true;
            }
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeBytes(1, getNameBytes());
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeBytes(2, getInputTypeBytes());
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeBytes(3, getOutputTypeBytes());
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeMessage(4, this.options_);
            }
            getUnknownFields().writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
                size = 0 + CodedOutputStream.computeBytesSize(1, getNameBytes());
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeBytesSize(2, getInputTypeBytes());
            }
            if ((this.bitField0_ & 4) == 4) {
                size += CodedOutputStream.computeBytesSize(3, getOutputTypeBytes());
            }
            if ((this.bitField0_ & 8) == 8) {
                size += CodedOutputStream.computeMessageSize(4, this.options_);
            }
            size += getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = size;
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static MethodDescriptorProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MethodDescriptorProto) PARSER.parseFrom(data);
        }

        public static MethodDescriptorProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MethodDescriptorProto) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MethodDescriptorProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MethodDescriptorProto) PARSER.parseFrom(data);
        }

        public static MethodDescriptorProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MethodDescriptorProto) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MethodDescriptorProto parseFrom(InputStream input) throws IOException {
            return (MethodDescriptorProto) PARSER.parseFrom(input);
        }

        public static MethodDescriptorProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MethodDescriptorProto) PARSER.parseFrom(input, extensionRegistry);
        }

        public static MethodDescriptorProto parseDelimitedFrom(InputStream input) throws IOException {
            return (MethodDescriptorProto) PARSER.parseDelimitedFrom(input);
        }

        public static MethodDescriptorProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MethodDescriptorProto) PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static MethodDescriptorProto parseFrom(CodedInputStream input) throws IOException {
            return (MethodDescriptorProto) PARSER.parseFrom(input);
        }

        public static MethodDescriptorProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MethodDescriptorProto) PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(MethodDescriptorProto prototype) {
            return newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return newBuilder(this);
        }

        protected Builder newBuilderForType(BuilderParent parent) {
            return new Builder(parent);
        }
    }

    public interface MethodOptionsOrBuilder extends ExtendableMessageOrBuilder<MethodOptions> {
        boolean getDeprecated();

        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();

        UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int i);

        List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList();

        boolean hasDeprecated();
    }

    public static final class MethodOptions extends ExtendableMessage<MethodOptions> implements MethodOptionsOrBuilder {
        public static final int DEPRECATED_FIELD_NUMBER = 33;
        public static Parser<MethodOptions> PARSER = new AbstractParser<MethodOptions>() {
            public MethodOptions parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new MethodOptions(input, extensionRegistry);
            }
        };
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        private static final MethodOptions defaultInstance = new MethodOptions(true);
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private boolean deprecated_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private List<UninterpretedOption> uninterpretedOption_;
        private final UnknownFieldSet unknownFields;

        public static final class Builder extends ExtendableBuilder<MethodOptions, Builder> implements MethodOptionsOrBuilder {
            private int bitField0_;
            private boolean deprecated_;
            private RepeatedFieldBuilder<UninterpretedOption, Builder, UninterpretedOptionOrBuilder> uninterpretedOptionBuilder_;
            private List<UninterpretedOption> uninterpretedOption_;

            public static final Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_MethodOptions_descriptor;
            }

            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_MethodOptions_fieldAccessorTable.ensureFieldAccessorsInitialized(MethodOptions.class, Builder.class);
            }

            private Builder() {
                this.uninterpretedOption_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.uninterpretedOption_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getUninterpretedOptionFieldBuilder();
                }
            }

            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                this.deprecated_ = false;
                this.bitField0_ &= -2;
                if (this.uninterpretedOptionBuilder_ == null) {
                    this.uninterpretedOption_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                } else {
                    this.uninterpretedOptionBuilder_.clear();
                }
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_MethodOptions_descriptor;
            }

            public MethodOptions getDefaultInstanceForType() {
                return MethodOptions.getDefaultInstance();
            }

            public MethodOptions build() {
                MethodOptions result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw com.google.protobuf.AbstractMessage.Builder.newUninitializedMessageException(result);
            }

            public MethodOptions buildPartial() {
                MethodOptions result = new MethodOptions((ExtendableBuilder) this);
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.deprecated_ = this.deprecated_;
                if (this.uninterpretedOptionBuilder_ == null) {
                    if ((this.bitField0_ & 2) == 2) {
                        this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
                        this.bitField0_ &= -3;
                    }
                    result.uninterpretedOption_ = this.uninterpretedOption_;
                } else {
                    result.uninterpretedOption_ = this.uninterpretedOptionBuilder_.build();
                }
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof MethodOptions) {
                    return mergeFrom((MethodOptions) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(MethodOptions other) {
                RepeatedFieldBuilder repeatedFieldBuilder = null;
                if (other != MethodOptions.getDefaultInstance()) {
                    if (other.hasDeprecated()) {
                        setDeprecated(other.getDeprecated());
                    }
                    if (this.uninterpretedOptionBuilder_ == null) {
                        if (!other.uninterpretedOption_.isEmpty()) {
                            if (this.uninterpretedOption_.isEmpty()) {
                                this.uninterpretedOption_ = other.uninterpretedOption_;
                                this.bitField0_ &= -3;
                            } else {
                                ensureUninterpretedOptionIsMutable();
                                this.uninterpretedOption_.addAll(other.uninterpretedOption_);
                            }
                            onChanged();
                        }
                    } else if (!other.uninterpretedOption_.isEmpty()) {
                        if (this.uninterpretedOptionBuilder_.isEmpty()) {
                            this.uninterpretedOptionBuilder_.dispose();
                            this.uninterpretedOptionBuilder_ = null;
                            this.uninterpretedOption_ = other.uninterpretedOption_;
                            this.bitField0_ &= -3;
                            if (GeneratedMessage.alwaysUseFieldBuilders) {
                                repeatedFieldBuilder = getUninterpretedOptionFieldBuilder();
                            }
                            this.uninterpretedOptionBuilder_ = repeatedFieldBuilder;
                        } else {
                            this.uninterpretedOptionBuilder_.addAllMessages(other.uninterpretedOption_);
                        }
                    }
                    mergeExtensionFields(other);
                    mergeUnknownFields(other.getUnknownFields());
                }
                return this;
            }

            public final boolean isInitialized() {
                for (int i = 0; i < getUninterpretedOptionCount(); i++) {
                    if (!getUninterpretedOption(i).isInitialized()) {
                        return false;
                    }
                }
                if (extensionsAreInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                MethodOptions parsedMessage = null;
                try {
                    parsedMessage = (MethodOptions) MethodOptions.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    parsedMessage = (MethodOptions) e.getUnfinishedMessage();
                    throw e;
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
            }

            public boolean hasDeprecated() {
                return (this.bitField0_ & 1) == 1;
            }

            public boolean getDeprecated() {
                return this.deprecated_;
            }

            public Builder setDeprecated(boolean value) {
                this.bitField0_ |= 1;
                this.deprecated_ = value;
                onChanged();
                return this;
            }

            public Builder clearDeprecated() {
                this.bitField0_ &= -2;
                this.deprecated_ = false;
                onChanged();
                return this;
            }

            private void ensureUninterpretedOptionIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.uninterpretedOption_ = new ArrayList(this.uninterpretedOption_);
                    this.bitField0_ |= 2;
                }
            }

            public List<UninterpretedOption> getUninterpretedOptionList() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    return Collections.unmodifiableList(this.uninterpretedOption_);
                }
                return this.uninterpretedOptionBuilder_.getMessageList();
            }

            public int getUninterpretedOptionCount() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    return this.uninterpretedOption_.size();
                }
                return this.uninterpretedOptionBuilder_.getCount();
            }

            public UninterpretedOption getUninterpretedOption(int index) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    return (UninterpretedOption) this.uninterpretedOption_.get(index);
                }
                return (UninterpretedOption) this.uninterpretedOptionBuilder_.getMessage(index);
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption value) {
                if (this.uninterpretedOptionBuilder_ != null) {
                    this.uninterpretedOptionBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setUninterpretedOption(int index, Builder builderForValue) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption value) {
                if (this.uninterpretedOptionBuilder_ != null) {
                    this.uninterpretedOptionBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption value) {
                if (this.uninterpretedOptionBuilder_ != null) {
                    this.uninterpretedOptionBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addUninterpretedOption(Builder builderForValue) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addUninterpretedOption(int index, Builder builderForValue) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.uninterpretedOption_);
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearUninterpretedOption() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    this.uninterpretedOption_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.clear();
                }
                return this;
            }

            public Builder removeUninterpretedOption(int index) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.remove(index);
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.remove(index);
                }
                return this;
            }

            public Builder getUninterpretedOptionBuilder(int index) {
                return (Builder) getUninterpretedOptionFieldBuilder().getBuilder(index);
            }

            public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    return (UninterpretedOptionOrBuilder) this.uninterpretedOption_.get(index);
                }
                return (UninterpretedOptionOrBuilder) this.uninterpretedOptionBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
                if (this.uninterpretedOptionBuilder_ != null) {
                    return this.uninterpretedOptionBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.uninterpretedOption_);
            }

            public Builder addUninterpretedOptionBuilder() {
                return (Builder) getUninterpretedOptionFieldBuilder().addBuilder(UninterpretedOption.getDefaultInstance());
            }

            public Builder addUninterpretedOptionBuilder(int index) {
                return (Builder) getUninterpretedOptionFieldBuilder().addBuilder(index, UninterpretedOption.getDefaultInstance());
            }

            public List<Builder> getUninterpretedOptionBuilderList() {
                return getUninterpretedOptionFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<UninterpretedOption, Builder, UninterpretedOptionOrBuilder> getUninterpretedOptionFieldBuilder() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    this.uninterpretedOptionBuilder_ = new RepeatedFieldBuilder(this.uninterpretedOption_, (this.bitField0_ & 2) == 2, getParentForChildren(), isClean());
                    this.uninterpretedOption_ = null;
                }
                return this.uninterpretedOptionBuilder_;
            }
        }

        private MethodOptions(ExtendableBuilder<MethodOptions, ?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private MethodOptions(boolean noInit) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static MethodOptions getDefaultInstance() {
            return defaultInstance;
        }

        public MethodOptions getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private MethodOptions(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 264:
                            this.bitField0_ |= 1;
                            this.deprecated_ = input.readBool();
                            break;
                        case 7994:
                            if ((mutable_bitField0_ & 2) != 2) {
                                this.uninterpretedOption_ = new ArrayList();
                                mutable_bitField0_ |= 2;
                            }
                            this.uninterpretedOption_.add(input.readMessage(UninterpretedOption.PARSER, extensionRegistry));
                            break;
                        default:
                            if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                                break;
                            }
                            break;
                    }
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                } catch (Throwable th) {
                    if ((mutable_bitField0_ & 2) == 2) {
                        this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                }
            }
            if ((mutable_bitField0_ & 2) == 2) {
                this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_MethodOptions_descriptor;
        }

        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_MethodOptions_fieldAccessorTable.ensureFieldAccessorsInitialized(MethodOptions.class, Builder.class);
        }

        static {
            defaultInstance.initFields();
        }

        public Parser<MethodOptions> getParserForType() {
            return PARSER;
        }

        public boolean hasDeprecated() {
            return (this.bitField0_ & 1) == 1;
        }

        public boolean getDeprecated() {
            return this.deprecated_;
        }

        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        public UninterpretedOption getUninterpretedOption(int index) {
            return (UninterpretedOption) this.uninterpretedOption_.get(index);
        }

        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
            return (UninterpretedOptionOrBuilder) this.uninterpretedOption_.get(index);
        }

        private void initFields() {
            this.deprecated_ = false;
            this.uninterpretedOption_ = Collections.emptyList();
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == (byte) 1) {
                return true;
            }
            if (isInitialized == (byte) 0) {
                return false;
            }
            int i = 0;
            while (i < getUninterpretedOptionCount()) {
                if (getUninterpretedOption(i).isInitialized()) {
                    i++;
                } else {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (extensionsAreInitialized()) {
                this.memoizedIsInitialized = (byte) 1;
                return true;
            }
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            getSerializedSize();
            ExtensionWriter extensionWriter = newExtensionWriter();
            if ((this.bitField0_ & 1) == 1) {
                output.writeBool(33, this.deprecated_);
            }
            for (int i = 0; i < this.uninterpretedOption_.size(); i++) {
                output.writeMessage(999, (MessageLite) this.uninterpretedOption_.get(i));
            }
            extensionWriter.writeUntil(536870912, output);
            getUnknownFields().writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
                size = 0 + CodedOutputStream.computeBoolSize(33, this.deprecated_);
            }
            for (int i = 0; i < this.uninterpretedOption_.size(); i++) {
                size += CodedOutputStream.computeMessageSize(999, (MessageLite) this.uninterpretedOption_.get(i));
            }
            size = (size + extensionsSerializedSize()) + getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = size;
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static MethodOptions parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (MethodOptions) PARSER.parseFrom(data);
        }

        public static MethodOptions parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MethodOptions) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MethodOptions parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (MethodOptions) PARSER.parseFrom(data);
        }

        public static MethodOptions parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (MethodOptions) PARSER.parseFrom(data, extensionRegistry);
        }

        public static MethodOptions parseFrom(InputStream input) throws IOException {
            return (MethodOptions) PARSER.parseFrom(input);
        }

        public static MethodOptions parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MethodOptions) PARSER.parseFrom(input, extensionRegistry);
        }

        public static MethodOptions parseDelimitedFrom(InputStream input) throws IOException {
            return (MethodOptions) PARSER.parseDelimitedFrom(input);
        }

        public static MethodOptions parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MethodOptions) PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static MethodOptions parseFrom(CodedInputStream input) throws IOException {
            return (MethodOptions) PARSER.parseFrom(input);
        }

        public static MethodOptions parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (MethodOptions) PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(MethodOptions prototype) {
            return newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return newBuilder(this);
        }

        protected Builder newBuilderForType(BuilderParent parent) {
            return new Builder(parent);
        }
    }

    public interface OneofDescriptorProtoOrBuilder extends MessageOrBuilder {
        String getName();

        ByteString getNameBytes();

        boolean hasName();
    }

    public static final class OneofDescriptorProto extends GeneratedMessage implements OneofDescriptorProtoOrBuilder {
        public static final int NAME_FIELD_NUMBER = 1;
        public static Parser<OneofDescriptorProto> PARSER = new AbstractParser<OneofDescriptorProto>() {
            public OneofDescriptorProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new OneofDescriptorProto(input, extensionRegistry);
            }
        };
        private static final OneofDescriptorProto defaultInstance = new OneofDescriptorProto(true);
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private Object name_;
        private final UnknownFieldSet unknownFields;

        public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> implements OneofDescriptorProtoOrBuilder {
            private int bitField0_;
            private Object name_;

            public static final Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_OneofDescriptorProto_descriptor;
            }

            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_OneofDescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(OneofDescriptorProto.class, Builder.class);
            }

            private Builder() {
                this.name_ = "";
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.name_ = "";
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (!GeneratedMessage.alwaysUseFieldBuilders) {
                }
            }

            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                this.name_ = "";
                this.bitField0_ &= -2;
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_OneofDescriptorProto_descriptor;
            }

            public OneofDescriptorProto getDefaultInstanceForType() {
                return OneofDescriptorProto.getDefaultInstance();
            }

            public OneofDescriptorProto build() {
                OneofDescriptorProto result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw com.google.protobuf.AbstractMessage.Builder.newUninitializedMessageException(result);
            }

            public OneofDescriptorProto buildPartial() {
                OneofDescriptorProto result = new OneofDescriptorProto((com.google.protobuf.GeneratedMessage.Builder) this);
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.name_ = this.name_;
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof OneofDescriptorProto) {
                    return mergeFrom((OneofDescriptorProto) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(OneofDescriptorProto other) {
                if (other != OneofDescriptorProto.getDefaultInstance()) {
                    if (other.hasName()) {
                        this.bitField0_ |= 1;
                        this.name_ = other.name_;
                        onChanged();
                    }
                    mergeUnknownFields(other.getUnknownFields());
                }
                return this;
            }

            public final boolean isInitialized() {
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                OneofDescriptorProto parsedMessage = null;
                try {
                    parsedMessage = (OneofDescriptorProto) OneofDescriptorProto.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    parsedMessage = (OneofDescriptorProto) e.getUnfinishedMessage();
                    throw e;
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
            }

            public boolean hasName() {
                return (this.bitField0_ & 1) == 1;
            }

            public String getName() {
                ByteString ref = this.name_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.name_ = s;
                return s;
            }

            public ByteString getNameBytes() {
                Object ref = this.name_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.name_ = b;
                return b;
            }

            public Builder setName(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.name_ = value;
                onChanged();
                return this;
            }

            public Builder clearName() {
                this.bitField0_ &= -2;
                this.name_ = OneofDescriptorProto.getDefaultInstance().getName();
                onChanged();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.name_ = value;
                onChanged();
                return this;
            }
        }

        private OneofDescriptorProto(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private OneofDescriptorProto(boolean noInit) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static OneofDescriptorProto getDefaultInstance() {
            return defaultInstance;
        }

        public OneofDescriptorProto getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private OneofDescriptorProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 10:
                            ByteString bs = input.readBytes();
                            this.bitField0_ |= 1;
                            this.name_ = bs;
                            break;
                        default:
                            if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                                break;
                            }
                            break;
                    }
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                } catch (Throwable th) {
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                }
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_OneofDescriptorProto_descriptor;
        }

        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_OneofDescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(OneofDescriptorProto.class, Builder.class);
        }

        static {
            defaultInstance.initFields();
        }

        public Parser<OneofDescriptorProto> getParserForType() {
            return PARSER;
        }

        public boolean hasName() {
            return (this.bitField0_ & 1) == 1;
        }

        public String getName() {
            ByteString ref = this.name_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.name_ = s;
            }
            return s;
        }

        public ByteString getNameBytes() {
            Object ref = this.name_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.name_ = b;
            return b;
        }

        private void initFields() {
            this.name_ = "";
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == (byte) 1) {
                return true;
            }
            if (isInitialized == (byte) 0) {
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeBytes(1, getNameBytes());
            }
            getUnknownFields().writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
                size = 0 + CodedOutputStream.computeBytesSize(1, getNameBytes());
            }
            size += getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = size;
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static OneofDescriptorProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (OneofDescriptorProto) PARSER.parseFrom(data);
        }

        public static OneofDescriptorProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (OneofDescriptorProto) PARSER.parseFrom(data, extensionRegistry);
        }

        public static OneofDescriptorProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (OneofDescriptorProto) PARSER.parseFrom(data);
        }

        public static OneofDescriptorProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (OneofDescriptorProto) PARSER.parseFrom(data, extensionRegistry);
        }

        public static OneofDescriptorProto parseFrom(InputStream input) throws IOException {
            return (OneofDescriptorProto) PARSER.parseFrom(input);
        }

        public static OneofDescriptorProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OneofDescriptorProto) PARSER.parseFrom(input, extensionRegistry);
        }

        public static OneofDescriptorProto parseDelimitedFrom(InputStream input) throws IOException {
            return (OneofDescriptorProto) PARSER.parseDelimitedFrom(input);
        }

        public static OneofDescriptorProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OneofDescriptorProto) PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static OneofDescriptorProto parseFrom(CodedInputStream input) throws IOException {
            return (OneofDescriptorProto) PARSER.parseFrom(input);
        }

        public static OneofDescriptorProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (OneofDescriptorProto) PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(OneofDescriptorProto prototype) {
            return newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return newBuilder(this);
        }

        protected Builder newBuilderForType(BuilderParent parent) {
            return new Builder(parent);
        }
    }

    public interface ServiceDescriptorProtoOrBuilder extends MessageOrBuilder {
        MethodDescriptorProto getMethod(int i);

        int getMethodCount();

        List<MethodDescriptorProto> getMethodList();

        MethodDescriptorProtoOrBuilder getMethodOrBuilder(int i);

        List<? extends MethodDescriptorProtoOrBuilder> getMethodOrBuilderList();

        String getName();

        ByteString getNameBytes();

        ServiceOptions getOptions();

        ServiceOptionsOrBuilder getOptionsOrBuilder();

        boolean hasName();

        boolean hasOptions();
    }

    public static final class ServiceDescriptorProto extends GeneratedMessage implements ServiceDescriptorProtoOrBuilder {
        public static final int METHOD_FIELD_NUMBER = 2;
        public static final int NAME_FIELD_NUMBER = 1;
        public static final int OPTIONS_FIELD_NUMBER = 3;
        public static Parser<ServiceDescriptorProto> PARSER = new AbstractParser<ServiceDescriptorProto>() {
            public ServiceDescriptorProto parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ServiceDescriptorProto(input, extensionRegistry);
            }
        };
        private static final ServiceDescriptorProto defaultInstance = new ServiceDescriptorProto(true);
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private List<MethodDescriptorProto> method_;
        private Object name_;
        private ServiceOptions options_;
        private final UnknownFieldSet unknownFields;

        public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> implements ServiceDescriptorProtoOrBuilder {
            private int bitField0_;
            private RepeatedFieldBuilder<MethodDescriptorProto, Builder, MethodDescriptorProtoOrBuilder> methodBuilder_;
            private List<MethodDescriptorProto> method_;
            private Object name_;
            private SingleFieldBuilder<ServiceOptions, Builder, ServiceOptionsOrBuilder> optionsBuilder_;
            private ServiceOptions options_;

            public static final Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_ServiceDescriptorProto_descriptor;
            }

            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_ServiceDescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(ServiceDescriptorProto.class, Builder.class);
            }

            private Builder() {
                this.name_ = "";
                this.method_ = Collections.emptyList();
                this.options_ = ServiceOptions.getDefaultInstance();
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.name_ = "";
                this.method_ = Collections.emptyList();
                this.options_ = ServiceOptions.getDefaultInstance();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getMethodFieldBuilder();
                    getOptionsFieldBuilder();
                }
            }

            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                this.name_ = "";
                this.bitField0_ &= -2;
                if (this.methodBuilder_ == null) {
                    this.method_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                } else {
                    this.methodBuilder_.clear();
                }
                if (this.optionsBuilder_ == null) {
                    this.options_ = ServiceOptions.getDefaultInstance();
                } else {
                    this.optionsBuilder_.clear();
                }
                this.bitField0_ &= -5;
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_ServiceDescriptorProto_descriptor;
            }

            public ServiceDescriptorProto getDefaultInstanceForType() {
                return ServiceDescriptorProto.getDefaultInstance();
            }

            public ServiceDescriptorProto build() {
                ServiceDescriptorProto result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw com.google.protobuf.AbstractMessage.Builder.newUninitializedMessageException(result);
            }

            public ServiceDescriptorProto buildPartial() {
                ServiceDescriptorProto result = new ServiceDescriptorProto((com.google.protobuf.GeneratedMessage.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.name_ = this.name_;
                if (this.methodBuilder_ == null) {
                    if ((this.bitField0_ & 2) == 2) {
                        this.method_ = Collections.unmodifiableList(this.method_);
                        this.bitField0_ &= -3;
                    }
                    result.method_ = this.method_;
                } else {
                    result.method_ = this.methodBuilder_.build();
                }
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 2;
                }
                if (this.optionsBuilder_ == null) {
                    result.options_ = this.options_;
                } else {
                    result.options_ = (ServiceOptions) this.optionsBuilder_.build();
                }
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof ServiceDescriptorProto) {
                    return mergeFrom((ServiceDescriptorProto) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ServiceDescriptorProto other) {
                RepeatedFieldBuilder repeatedFieldBuilder = null;
                if (other != ServiceDescriptorProto.getDefaultInstance()) {
                    if (other.hasName()) {
                        this.bitField0_ |= 1;
                        this.name_ = other.name_;
                        onChanged();
                    }
                    if (this.methodBuilder_ == null) {
                        if (!other.method_.isEmpty()) {
                            if (this.method_.isEmpty()) {
                                this.method_ = other.method_;
                                this.bitField0_ &= -3;
                            } else {
                                ensureMethodIsMutable();
                                this.method_.addAll(other.method_);
                            }
                            onChanged();
                        }
                    } else if (!other.method_.isEmpty()) {
                        if (this.methodBuilder_.isEmpty()) {
                            this.methodBuilder_.dispose();
                            this.methodBuilder_ = null;
                            this.method_ = other.method_;
                            this.bitField0_ &= -3;
                            if (GeneratedMessage.alwaysUseFieldBuilders) {
                                repeatedFieldBuilder = getMethodFieldBuilder();
                            }
                            this.methodBuilder_ = repeatedFieldBuilder;
                        } else {
                            this.methodBuilder_.addAllMessages(other.method_);
                        }
                    }
                    if (other.hasOptions()) {
                        mergeOptions(other.getOptions());
                    }
                    mergeUnknownFields(other.getUnknownFields());
                }
                return this;
            }

            public final boolean isInitialized() {
                for (int i = 0; i < getMethodCount(); i++) {
                    if (!getMethod(i).isInitialized()) {
                        return false;
                    }
                }
                if (!hasOptions() || getOptions().isInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                ServiceDescriptorProto parsedMessage = null;
                try {
                    parsedMessage = (ServiceDescriptorProto) ServiceDescriptorProto.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    parsedMessage = (ServiceDescriptorProto) e.getUnfinishedMessage();
                    throw e;
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
            }

            public boolean hasName() {
                return (this.bitField0_ & 1) == 1;
            }

            public String getName() {
                ByteString ref = this.name_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.name_ = s;
                return s;
            }

            public ByteString getNameBytes() {
                Object ref = this.name_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.name_ = b;
                return b;
            }

            public Builder setName(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.name_ = value;
                onChanged();
                return this;
            }

            public Builder clearName() {
                this.bitField0_ &= -2;
                this.name_ = ServiceDescriptorProto.getDefaultInstance().getName();
                onChanged();
                return this;
            }

            public Builder setNameBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.name_ = value;
                onChanged();
                return this;
            }

            private void ensureMethodIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.method_ = new ArrayList(this.method_);
                    this.bitField0_ |= 2;
                }
            }

            public List<MethodDescriptorProto> getMethodList() {
                if (this.methodBuilder_ == null) {
                    return Collections.unmodifiableList(this.method_);
                }
                return this.methodBuilder_.getMessageList();
            }

            public int getMethodCount() {
                if (this.methodBuilder_ == null) {
                    return this.method_.size();
                }
                return this.methodBuilder_.getCount();
            }

            public MethodDescriptorProto getMethod(int index) {
                if (this.methodBuilder_ == null) {
                    return (MethodDescriptorProto) this.method_.get(index);
                }
                return (MethodDescriptorProto) this.methodBuilder_.getMessage(index);
            }

            public Builder setMethod(int index, MethodDescriptorProto value) {
                if (this.methodBuilder_ != null) {
                    this.methodBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureMethodIsMutable();
                    this.method_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setMethod(int index, Builder builderForValue) {
                if (this.methodBuilder_ == null) {
                    ensureMethodIsMutable();
                    this.method_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.methodBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addMethod(MethodDescriptorProto value) {
                if (this.methodBuilder_ != null) {
                    this.methodBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureMethodIsMutable();
                    this.method_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addMethod(int index, MethodDescriptorProto value) {
                if (this.methodBuilder_ != null) {
                    this.methodBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureMethodIsMutable();
                    this.method_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addMethod(Builder builderForValue) {
                if (this.methodBuilder_ == null) {
                    ensureMethodIsMutable();
                    this.method_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.methodBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addMethod(int index, Builder builderForValue) {
                if (this.methodBuilder_ == null) {
                    ensureMethodIsMutable();
                    this.method_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.methodBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllMethod(Iterable<? extends MethodDescriptorProto> values) {
                if (this.methodBuilder_ == null) {
                    ensureMethodIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.method_);
                    onChanged();
                } else {
                    this.methodBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearMethod() {
                if (this.methodBuilder_ == null) {
                    this.method_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                    onChanged();
                } else {
                    this.methodBuilder_.clear();
                }
                return this;
            }

            public Builder removeMethod(int index) {
                if (this.methodBuilder_ == null) {
                    ensureMethodIsMutable();
                    this.method_.remove(index);
                    onChanged();
                } else {
                    this.methodBuilder_.remove(index);
                }
                return this;
            }

            public Builder getMethodBuilder(int index) {
                return (Builder) getMethodFieldBuilder().getBuilder(index);
            }

            public MethodDescriptorProtoOrBuilder getMethodOrBuilder(int index) {
                if (this.methodBuilder_ == null) {
                    return (MethodDescriptorProtoOrBuilder) this.method_.get(index);
                }
                return (MethodDescriptorProtoOrBuilder) this.methodBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends MethodDescriptorProtoOrBuilder> getMethodOrBuilderList() {
                if (this.methodBuilder_ != null) {
                    return this.methodBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.method_);
            }

            public Builder addMethodBuilder() {
                return (Builder) getMethodFieldBuilder().addBuilder(MethodDescriptorProto.getDefaultInstance());
            }

            public Builder addMethodBuilder(int index) {
                return (Builder) getMethodFieldBuilder().addBuilder(index, MethodDescriptorProto.getDefaultInstance());
            }

            public List<Builder> getMethodBuilderList() {
                return getMethodFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<MethodDescriptorProto, Builder, MethodDescriptorProtoOrBuilder> getMethodFieldBuilder() {
                if (this.methodBuilder_ == null) {
                    this.methodBuilder_ = new RepeatedFieldBuilder(this.method_, (this.bitField0_ & 2) == 2, getParentForChildren(), isClean());
                    this.method_ = null;
                }
                return this.methodBuilder_;
            }

            public boolean hasOptions() {
                return (this.bitField0_ & 4) == 4;
            }

            public ServiceOptions getOptions() {
                if (this.optionsBuilder_ == null) {
                    return this.options_;
                }
                return (ServiceOptions) this.optionsBuilder_.getMessage();
            }

            public Builder setOptions(ServiceOptions value) {
                if (this.optionsBuilder_ != null) {
                    this.optionsBuilder_.setMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    this.options_ = value;
                    onChanged();
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder setOptions(Builder builderForValue) {
                if (this.optionsBuilder_ == null) {
                    this.options_ = builderForValue.build();
                    onChanged();
                } else {
                    this.optionsBuilder_.setMessage(builderForValue.build());
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder mergeOptions(ServiceOptions value) {
                if (this.optionsBuilder_ == null) {
                    if ((this.bitField0_ & 4) != 4 || this.options_ == ServiceOptions.getDefaultInstance()) {
                        this.options_ = value;
                    } else {
                        this.options_ = ServiceOptions.newBuilder(this.options_).mergeFrom(value).buildPartial();
                    }
                    onChanged();
                } else {
                    this.optionsBuilder_.mergeFrom(value);
                }
                this.bitField0_ |= 4;
                return this;
            }

            public Builder clearOptions() {
                if (this.optionsBuilder_ == null) {
                    this.options_ = ServiceOptions.getDefaultInstance();
                    onChanged();
                } else {
                    this.optionsBuilder_.clear();
                }
                this.bitField0_ &= -5;
                return this;
            }

            public Builder getOptionsBuilder() {
                this.bitField0_ |= 4;
                onChanged();
                return (Builder) getOptionsFieldBuilder().getBuilder();
            }

            public ServiceOptionsOrBuilder getOptionsOrBuilder() {
                if (this.optionsBuilder_ != null) {
                    return (ServiceOptionsOrBuilder) this.optionsBuilder_.getMessageOrBuilder();
                }
                return this.options_;
            }

            private SingleFieldBuilder<ServiceOptions, Builder, ServiceOptionsOrBuilder> getOptionsFieldBuilder() {
                if (this.optionsBuilder_ == null) {
                    this.optionsBuilder_ = new SingleFieldBuilder(getOptions(), getParentForChildren(), isClean());
                    this.options_ = null;
                }
                return this.optionsBuilder_;
            }
        }

        private ServiceDescriptorProto(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private ServiceDescriptorProto(boolean noInit) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static ServiceDescriptorProto getDefaultInstance() {
            return defaultInstance;
        }

        public ServiceDescriptorProto getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ServiceDescriptorProto(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 10:
                            ByteString bs = input.readBytes();
                            this.bitField0_ |= 1;
                            this.name_ = bs;
                            break;
                        case 18:
                            if ((mutable_bitField0_ & 2) != 2) {
                                this.method_ = new ArrayList();
                                mutable_bitField0_ |= 2;
                            }
                            this.method_.add(input.readMessage(MethodDescriptorProto.PARSER, extensionRegistry));
                            break;
                        case 26:
                            Builder subBuilder = null;
                            if ((this.bitField0_ & 2) == 2) {
                                subBuilder = this.options_.toBuilder();
                            }
                            this.options_ = (ServiceOptions) input.readMessage(ServiceOptions.PARSER, extensionRegistry);
                            if (subBuilder != null) {
                                subBuilder.mergeFrom(this.options_);
                                this.options_ = subBuilder.buildPartial();
                            }
                            this.bitField0_ |= 2;
                            break;
                        default:
                            if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                                break;
                            }
                            break;
                    }
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                } catch (Throwable th) {
                    if ((mutable_bitField0_ & 2) == 2) {
                        this.method_ = Collections.unmodifiableList(this.method_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                }
            }
            if ((mutable_bitField0_ & 2) == 2) {
                this.method_ = Collections.unmodifiableList(this.method_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_ServiceDescriptorProto_descriptor;
        }

        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_ServiceDescriptorProto_fieldAccessorTable.ensureFieldAccessorsInitialized(ServiceDescriptorProto.class, Builder.class);
        }

        static {
            defaultInstance.initFields();
        }

        public Parser<ServiceDescriptorProto> getParserForType() {
            return PARSER;
        }

        public boolean hasName() {
            return (this.bitField0_ & 1) == 1;
        }

        public String getName() {
            ByteString ref = this.name_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.name_ = s;
            }
            return s;
        }

        public ByteString getNameBytes() {
            Object ref = this.name_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.name_ = b;
            return b;
        }

        public List<MethodDescriptorProto> getMethodList() {
            return this.method_;
        }

        public List<? extends MethodDescriptorProtoOrBuilder> getMethodOrBuilderList() {
            return this.method_;
        }

        public int getMethodCount() {
            return this.method_.size();
        }

        public MethodDescriptorProto getMethod(int index) {
            return (MethodDescriptorProto) this.method_.get(index);
        }

        public MethodDescriptorProtoOrBuilder getMethodOrBuilder(int index) {
            return (MethodDescriptorProtoOrBuilder) this.method_.get(index);
        }

        public boolean hasOptions() {
            return (this.bitField0_ & 2) == 2;
        }

        public ServiceOptions getOptions() {
            return this.options_;
        }

        public ServiceOptionsOrBuilder getOptionsOrBuilder() {
            return this.options_;
        }

        private void initFields() {
            this.name_ = "";
            this.method_ = Collections.emptyList();
            this.options_ = ServiceOptions.getDefaultInstance();
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == (byte) 1) {
                return true;
            }
            if (isInitialized == (byte) 0) {
                return false;
            }
            int i = 0;
            while (i < getMethodCount()) {
                if (getMethod(i).isInitialized()) {
                    i++;
                } else {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (!hasOptions() || getOptions().isInitialized()) {
                this.memoizedIsInitialized = (byte) 1;
                return true;
            }
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            getSerializedSize();
            if ((this.bitField0_ & 1) == 1) {
                output.writeBytes(1, getNameBytes());
            }
            for (int i = 0; i < this.method_.size(); i++) {
                output.writeMessage(2, (MessageLite) this.method_.get(i));
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeMessage(3, this.options_);
            }
            getUnknownFields().writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
                size = 0 + CodedOutputStream.computeBytesSize(1, getNameBytes());
            }
            for (int i = 0; i < this.method_.size(); i++) {
                size += CodedOutputStream.computeMessageSize(2, (MessageLite) this.method_.get(i));
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeMessageSize(3, this.options_);
            }
            size += getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = size;
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static ServiceDescriptorProto parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ServiceDescriptorProto) PARSER.parseFrom(data);
        }

        public static ServiceDescriptorProto parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ServiceDescriptorProto) PARSER.parseFrom(data, extensionRegistry);
        }

        public static ServiceDescriptorProto parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ServiceDescriptorProto) PARSER.parseFrom(data);
        }

        public static ServiceDescriptorProto parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ServiceDescriptorProto) PARSER.parseFrom(data, extensionRegistry);
        }

        public static ServiceDescriptorProto parseFrom(InputStream input) throws IOException {
            return (ServiceDescriptorProto) PARSER.parseFrom(input);
        }

        public static ServiceDescriptorProto parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServiceDescriptorProto) PARSER.parseFrom(input, extensionRegistry);
        }

        public static ServiceDescriptorProto parseDelimitedFrom(InputStream input) throws IOException {
            return (ServiceDescriptorProto) PARSER.parseDelimitedFrom(input);
        }

        public static ServiceDescriptorProto parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServiceDescriptorProto) PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static ServiceDescriptorProto parseFrom(CodedInputStream input) throws IOException {
            return (ServiceDescriptorProto) PARSER.parseFrom(input);
        }

        public static ServiceDescriptorProto parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServiceDescriptorProto) PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(ServiceDescriptorProto prototype) {
            return newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return newBuilder(this);
        }

        protected Builder newBuilderForType(BuilderParent parent) {
            return new Builder(parent);
        }
    }

    public interface ServiceOptionsOrBuilder extends ExtendableMessageOrBuilder<ServiceOptions> {
        boolean getDeprecated();

        UninterpretedOption getUninterpretedOption(int i);

        int getUninterpretedOptionCount();

        List<UninterpretedOption> getUninterpretedOptionList();

        UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int i);

        List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList();

        boolean hasDeprecated();
    }

    public static final class ServiceOptions extends ExtendableMessage<ServiceOptions> implements ServiceOptionsOrBuilder {
        public static final int DEPRECATED_FIELD_NUMBER = 33;
        public static Parser<ServiceOptions> PARSER = new AbstractParser<ServiceOptions>() {
            public ServiceOptions parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ServiceOptions(input, extensionRegistry);
            }
        };
        public static final int UNINTERPRETED_OPTION_FIELD_NUMBER = 999;
        private static final ServiceOptions defaultInstance = new ServiceOptions(true);
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private boolean deprecated_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private List<UninterpretedOption> uninterpretedOption_;
        private final UnknownFieldSet unknownFields;

        public static final class Builder extends ExtendableBuilder<ServiceOptions, Builder> implements ServiceOptionsOrBuilder {
            private int bitField0_;
            private boolean deprecated_;
            private RepeatedFieldBuilder<UninterpretedOption, Builder, UninterpretedOptionOrBuilder> uninterpretedOptionBuilder_;
            private List<UninterpretedOption> uninterpretedOption_;

            public static final Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_ServiceOptions_descriptor;
            }

            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_ServiceOptions_fieldAccessorTable.ensureFieldAccessorsInitialized(ServiceOptions.class, Builder.class);
            }

            private Builder() {
                this.uninterpretedOption_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.uninterpretedOption_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getUninterpretedOptionFieldBuilder();
                }
            }

            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                this.deprecated_ = false;
                this.bitField0_ &= -2;
                if (this.uninterpretedOptionBuilder_ == null) {
                    this.uninterpretedOption_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                } else {
                    this.uninterpretedOptionBuilder_.clear();
                }
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_ServiceOptions_descriptor;
            }

            public ServiceOptions getDefaultInstanceForType() {
                return ServiceOptions.getDefaultInstance();
            }

            public ServiceOptions build() {
                ServiceOptions result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw com.google.protobuf.AbstractMessage.Builder.newUninitializedMessageException(result);
            }

            public ServiceOptions buildPartial() {
                ServiceOptions result = new ServiceOptions((ExtendableBuilder) this);
                int to_bitField0_ = 0;
                if ((this.bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.deprecated_ = this.deprecated_;
                if (this.uninterpretedOptionBuilder_ == null) {
                    if ((this.bitField0_ & 2) == 2) {
                        this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
                        this.bitField0_ &= -3;
                    }
                    result.uninterpretedOption_ = this.uninterpretedOption_;
                } else {
                    result.uninterpretedOption_ = this.uninterpretedOptionBuilder_.build();
                }
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof ServiceOptions) {
                    return mergeFrom((ServiceOptions) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(ServiceOptions other) {
                RepeatedFieldBuilder repeatedFieldBuilder = null;
                if (other != ServiceOptions.getDefaultInstance()) {
                    if (other.hasDeprecated()) {
                        setDeprecated(other.getDeprecated());
                    }
                    if (this.uninterpretedOptionBuilder_ == null) {
                        if (!other.uninterpretedOption_.isEmpty()) {
                            if (this.uninterpretedOption_.isEmpty()) {
                                this.uninterpretedOption_ = other.uninterpretedOption_;
                                this.bitField0_ &= -3;
                            } else {
                                ensureUninterpretedOptionIsMutable();
                                this.uninterpretedOption_.addAll(other.uninterpretedOption_);
                            }
                            onChanged();
                        }
                    } else if (!other.uninterpretedOption_.isEmpty()) {
                        if (this.uninterpretedOptionBuilder_.isEmpty()) {
                            this.uninterpretedOptionBuilder_.dispose();
                            this.uninterpretedOptionBuilder_ = null;
                            this.uninterpretedOption_ = other.uninterpretedOption_;
                            this.bitField0_ &= -3;
                            if (GeneratedMessage.alwaysUseFieldBuilders) {
                                repeatedFieldBuilder = getUninterpretedOptionFieldBuilder();
                            }
                            this.uninterpretedOptionBuilder_ = repeatedFieldBuilder;
                        } else {
                            this.uninterpretedOptionBuilder_.addAllMessages(other.uninterpretedOption_);
                        }
                    }
                    mergeExtensionFields(other);
                    mergeUnknownFields(other.getUnknownFields());
                }
                return this;
            }

            public final boolean isInitialized() {
                for (int i = 0; i < getUninterpretedOptionCount(); i++) {
                    if (!getUninterpretedOption(i).isInitialized()) {
                        return false;
                    }
                }
                if (extensionsAreInitialized()) {
                    return true;
                }
                return false;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                ServiceOptions parsedMessage = null;
                try {
                    parsedMessage = (ServiceOptions) ServiceOptions.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    parsedMessage = (ServiceOptions) e.getUnfinishedMessage();
                    throw e;
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
            }

            public boolean hasDeprecated() {
                return (this.bitField0_ & 1) == 1;
            }

            public boolean getDeprecated() {
                return this.deprecated_;
            }

            public Builder setDeprecated(boolean value) {
                this.bitField0_ |= 1;
                this.deprecated_ = value;
                onChanged();
                return this;
            }

            public Builder clearDeprecated() {
                this.bitField0_ &= -2;
                this.deprecated_ = false;
                onChanged();
                return this;
            }

            private void ensureUninterpretedOptionIsMutable() {
                if ((this.bitField0_ & 2) != 2) {
                    this.uninterpretedOption_ = new ArrayList(this.uninterpretedOption_);
                    this.bitField0_ |= 2;
                }
            }

            public List<UninterpretedOption> getUninterpretedOptionList() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    return Collections.unmodifiableList(this.uninterpretedOption_);
                }
                return this.uninterpretedOptionBuilder_.getMessageList();
            }

            public int getUninterpretedOptionCount() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    return this.uninterpretedOption_.size();
                }
                return this.uninterpretedOptionBuilder_.getCount();
            }

            public UninterpretedOption getUninterpretedOption(int index) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    return (UninterpretedOption) this.uninterpretedOption_.get(index);
                }
                return (UninterpretedOption) this.uninterpretedOptionBuilder_.getMessage(index);
            }

            public Builder setUninterpretedOption(int index, UninterpretedOption value) {
                if (this.uninterpretedOptionBuilder_ != null) {
                    this.uninterpretedOptionBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setUninterpretedOption(int index, Builder builderForValue) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addUninterpretedOption(UninterpretedOption value) {
                if (this.uninterpretedOptionBuilder_ != null) {
                    this.uninterpretedOptionBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addUninterpretedOption(int index, UninterpretedOption value) {
                if (this.uninterpretedOptionBuilder_ != null) {
                    this.uninterpretedOptionBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addUninterpretedOption(Builder builderForValue) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addUninterpretedOption(int index, Builder builderForValue) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllUninterpretedOption(Iterable<? extends UninterpretedOption> values) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.uninterpretedOption_);
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearUninterpretedOption() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    this.uninterpretedOption_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.clear();
                }
                return this;
            }

            public Builder removeUninterpretedOption(int index) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    ensureUninterpretedOptionIsMutable();
                    this.uninterpretedOption_.remove(index);
                    onChanged();
                } else {
                    this.uninterpretedOptionBuilder_.remove(index);
                }
                return this;
            }

            public Builder getUninterpretedOptionBuilder(int index) {
                return (Builder) getUninterpretedOptionFieldBuilder().getBuilder(index);
            }

            public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
                if (this.uninterpretedOptionBuilder_ == null) {
                    return (UninterpretedOptionOrBuilder) this.uninterpretedOption_.get(index);
                }
                return (UninterpretedOptionOrBuilder) this.uninterpretedOptionBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
                if (this.uninterpretedOptionBuilder_ != null) {
                    return this.uninterpretedOptionBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.uninterpretedOption_);
            }

            public Builder addUninterpretedOptionBuilder() {
                return (Builder) getUninterpretedOptionFieldBuilder().addBuilder(UninterpretedOption.getDefaultInstance());
            }

            public Builder addUninterpretedOptionBuilder(int index) {
                return (Builder) getUninterpretedOptionFieldBuilder().addBuilder(index, UninterpretedOption.getDefaultInstance());
            }

            public List<Builder> getUninterpretedOptionBuilderList() {
                return getUninterpretedOptionFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<UninterpretedOption, Builder, UninterpretedOptionOrBuilder> getUninterpretedOptionFieldBuilder() {
                if (this.uninterpretedOptionBuilder_ == null) {
                    this.uninterpretedOptionBuilder_ = new RepeatedFieldBuilder(this.uninterpretedOption_, (this.bitField0_ & 2) == 2, getParentForChildren(), isClean());
                    this.uninterpretedOption_ = null;
                }
                return this.uninterpretedOptionBuilder_;
            }
        }

        private ServiceOptions(ExtendableBuilder<ServiceOptions, ?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private ServiceOptions(boolean noInit) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static ServiceOptions getDefaultInstance() {
            return defaultInstance;
        }

        public ServiceOptions getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ServiceOptions(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 264:
                            this.bitField0_ |= 1;
                            this.deprecated_ = input.readBool();
                            break;
                        case 7994:
                            if ((mutable_bitField0_ & 2) != 2) {
                                this.uninterpretedOption_ = new ArrayList();
                                mutable_bitField0_ |= 2;
                            }
                            this.uninterpretedOption_.add(input.readMessage(UninterpretedOption.PARSER, extensionRegistry));
                            break;
                        default:
                            if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                                break;
                            }
                            break;
                    }
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                } catch (Throwable th) {
                    if ((mutable_bitField0_ & 2) == 2) {
                        this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                }
            }
            if ((mutable_bitField0_ & 2) == 2) {
                this.uninterpretedOption_ = Collections.unmodifiableList(this.uninterpretedOption_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_ServiceOptions_descriptor;
        }

        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_ServiceOptions_fieldAccessorTable.ensureFieldAccessorsInitialized(ServiceOptions.class, Builder.class);
        }

        static {
            defaultInstance.initFields();
        }

        public Parser<ServiceOptions> getParserForType() {
            return PARSER;
        }

        public boolean hasDeprecated() {
            return (this.bitField0_ & 1) == 1;
        }

        public boolean getDeprecated() {
            return this.deprecated_;
        }

        public List<UninterpretedOption> getUninterpretedOptionList() {
            return this.uninterpretedOption_;
        }

        public List<? extends UninterpretedOptionOrBuilder> getUninterpretedOptionOrBuilderList() {
            return this.uninterpretedOption_;
        }

        public int getUninterpretedOptionCount() {
            return this.uninterpretedOption_.size();
        }

        public UninterpretedOption getUninterpretedOption(int index) {
            return (UninterpretedOption) this.uninterpretedOption_.get(index);
        }

        public UninterpretedOptionOrBuilder getUninterpretedOptionOrBuilder(int index) {
            return (UninterpretedOptionOrBuilder) this.uninterpretedOption_.get(index);
        }

        private void initFields() {
            this.deprecated_ = false;
            this.uninterpretedOption_ = Collections.emptyList();
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == (byte) 1) {
                return true;
            }
            if (isInitialized == (byte) 0) {
                return false;
            }
            int i = 0;
            while (i < getUninterpretedOptionCount()) {
                if (getUninterpretedOption(i).isInitialized()) {
                    i++;
                } else {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            if (extensionsAreInitialized()) {
                this.memoizedIsInitialized = (byte) 1;
                return true;
            }
            this.memoizedIsInitialized = (byte) 0;
            return false;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            getSerializedSize();
            ExtensionWriter extensionWriter = newExtensionWriter();
            if ((this.bitField0_ & 1) == 1) {
                output.writeBool(33, this.deprecated_);
            }
            for (int i = 0; i < this.uninterpretedOption_.size(); i++) {
                output.writeMessage(999, (MessageLite) this.uninterpretedOption_.get(i));
            }
            extensionWriter.writeUntil(536870912, output);
            getUnknownFields().writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            if ((this.bitField0_ & 1) == 1) {
                size = 0 + CodedOutputStream.computeBoolSize(33, this.deprecated_);
            }
            for (int i = 0; i < this.uninterpretedOption_.size(); i++) {
                size += CodedOutputStream.computeMessageSize(999, (MessageLite) this.uninterpretedOption_.get(i));
            }
            size = (size + extensionsSerializedSize()) + getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = size;
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static ServiceOptions parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ServiceOptions) PARSER.parseFrom(data);
        }

        public static ServiceOptions parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ServiceOptions) PARSER.parseFrom(data, extensionRegistry);
        }

        public static ServiceOptions parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ServiceOptions) PARSER.parseFrom(data);
        }

        public static ServiceOptions parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ServiceOptions) PARSER.parseFrom(data, extensionRegistry);
        }

        public static ServiceOptions parseFrom(InputStream input) throws IOException {
            return (ServiceOptions) PARSER.parseFrom(input);
        }

        public static ServiceOptions parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServiceOptions) PARSER.parseFrom(input, extensionRegistry);
        }

        public static ServiceOptions parseDelimitedFrom(InputStream input) throws IOException {
            return (ServiceOptions) PARSER.parseDelimitedFrom(input);
        }

        public static ServiceOptions parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServiceOptions) PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static ServiceOptions parseFrom(CodedInputStream input) throws IOException {
            return (ServiceOptions) PARSER.parseFrom(input);
        }

        public static ServiceOptions parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServiceOptions) PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(ServiceOptions prototype) {
            return newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return newBuilder(this);
        }

        protected Builder newBuilderForType(BuilderParent parent) {
            return new Builder(parent);
        }
    }

    public interface SourceCodeInfoOrBuilder extends MessageOrBuilder {
        Location getLocation(int i);

        int getLocationCount();

        List<Location> getLocationList();

        LocationOrBuilder getLocationOrBuilder(int i);

        List<? extends LocationOrBuilder> getLocationOrBuilderList();
    }

    public static final class SourceCodeInfo extends GeneratedMessage implements SourceCodeInfoOrBuilder {
        public static final int LOCATION_FIELD_NUMBER = 1;
        public static Parser<SourceCodeInfo> PARSER = new AbstractParser<SourceCodeInfo>() {
            public SourceCodeInfo parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new SourceCodeInfo(input, extensionRegistry);
            }
        };
        private static final SourceCodeInfo defaultInstance = new SourceCodeInfo(true);
        private static final long serialVersionUID = 0;
        private List<Location> location_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private final UnknownFieldSet unknownFields;

        public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> implements SourceCodeInfoOrBuilder {
            private int bitField0_;
            private RepeatedFieldBuilder<Location, Builder, LocationOrBuilder> locationBuilder_;
            private List<Location> location_;

            public static final Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_SourceCodeInfo_descriptor;
            }

            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_SourceCodeInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(SourceCodeInfo.class, Builder.class);
            }

            private Builder() {
                this.location_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.location_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getLocationFieldBuilder();
                }
            }

            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                if (this.locationBuilder_ == null) {
                    this.location_ = Collections.emptyList();
                    this.bitField0_ &= -2;
                } else {
                    this.locationBuilder_.clear();
                }
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_SourceCodeInfo_descriptor;
            }

            public SourceCodeInfo getDefaultInstanceForType() {
                return SourceCodeInfo.getDefaultInstance();
            }

            public SourceCodeInfo build() {
                SourceCodeInfo result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw com.google.protobuf.AbstractMessage.Builder.newUninitializedMessageException(result);
            }

            public SourceCodeInfo buildPartial() {
                SourceCodeInfo result = new SourceCodeInfo((com.google.protobuf.GeneratedMessage.Builder) this);
                int from_bitField0_ = this.bitField0_;
                if (this.locationBuilder_ == null) {
                    if ((this.bitField0_ & 1) == 1) {
                        this.location_ = Collections.unmodifiableList(this.location_);
                        this.bitField0_ &= -2;
                    }
                    result.location_ = this.location_;
                } else {
                    result.location_ = this.locationBuilder_.build();
                }
                onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof SourceCodeInfo) {
                    return mergeFrom((SourceCodeInfo) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(SourceCodeInfo other) {
                RepeatedFieldBuilder repeatedFieldBuilder = null;
                if (other != SourceCodeInfo.getDefaultInstance()) {
                    if (this.locationBuilder_ == null) {
                        if (!other.location_.isEmpty()) {
                            if (this.location_.isEmpty()) {
                                this.location_ = other.location_;
                                this.bitField0_ &= -2;
                            } else {
                                ensureLocationIsMutable();
                                this.location_.addAll(other.location_);
                            }
                            onChanged();
                        }
                    } else if (!other.location_.isEmpty()) {
                        if (this.locationBuilder_.isEmpty()) {
                            this.locationBuilder_.dispose();
                            this.locationBuilder_ = null;
                            this.location_ = other.location_;
                            this.bitField0_ &= -2;
                            if (GeneratedMessage.alwaysUseFieldBuilders) {
                                repeatedFieldBuilder = getLocationFieldBuilder();
                            }
                            this.locationBuilder_ = repeatedFieldBuilder;
                        } else {
                            this.locationBuilder_.addAllMessages(other.location_);
                        }
                    }
                    mergeUnknownFields(other.getUnknownFields());
                }
                return this;
            }

            public final boolean isInitialized() {
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                SourceCodeInfo parsedMessage = null;
                try {
                    parsedMessage = (SourceCodeInfo) SourceCodeInfo.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    parsedMessage = (SourceCodeInfo) e.getUnfinishedMessage();
                    throw e;
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
            }

            private void ensureLocationIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.location_ = new ArrayList(this.location_);
                    this.bitField0_ |= 1;
                }
            }

            public List<Location> getLocationList() {
                if (this.locationBuilder_ == null) {
                    return Collections.unmodifiableList(this.location_);
                }
                return this.locationBuilder_.getMessageList();
            }

            public int getLocationCount() {
                if (this.locationBuilder_ == null) {
                    return this.location_.size();
                }
                return this.locationBuilder_.getCount();
            }

            public Location getLocation(int index) {
                if (this.locationBuilder_ == null) {
                    return (Location) this.location_.get(index);
                }
                return (Location) this.locationBuilder_.getMessage(index);
            }

            public Builder setLocation(int index, Location value) {
                if (this.locationBuilder_ != null) {
                    this.locationBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureLocationIsMutable();
                    this.location_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setLocation(int index, Builder builderForValue) {
                if (this.locationBuilder_ == null) {
                    ensureLocationIsMutable();
                    this.location_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.locationBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addLocation(Location value) {
                if (this.locationBuilder_ != null) {
                    this.locationBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureLocationIsMutable();
                    this.location_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addLocation(int index, Location value) {
                if (this.locationBuilder_ != null) {
                    this.locationBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureLocationIsMutable();
                    this.location_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addLocation(Builder builderForValue) {
                if (this.locationBuilder_ == null) {
                    ensureLocationIsMutable();
                    this.location_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.locationBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addLocation(int index, Builder builderForValue) {
                if (this.locationBuilder_ == null) {
                    ensureLocationIsMutable();
                    this.location_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.locationBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllLocation(Iterable<? extends Location> values) {
                if (this.locationBuilder_ == null) {
                    ensureLocationIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.location_);
                    onChanged();
                } else {
                    this.locationBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearLocation() {
                if (this.locationBuilder_ == null) {
                    this.location_ = Collections.emptyList();
                    this.bitField0_ &= -2;
                    onChanged();
                } else {
                    this.locationBuilder_.clear();
                }
                return this;
            }

            public Builder removeLocation(int index) {
                if (this.locationBuilder_ == null) {
                    ensureLocationIsMutable();
                    this.location_.remove(index);
                    onChanged();
                } else {
                    this.locationBuilder_.remove(index);
                }
                return this;
            }

            public Builder getLocationBuilder(int index) {
                return (Builder) getLocationFieldBuilder().getBuilder(index);
            }

            public LocationOrBuilder getLocationOrBuilder(int index) {
                if (this.locationBuilder_ == null) {
                    return (LocationOrBuilder) this.location_.get(index);
                }
                return (LocationOrBuilder) this.locationBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends LocationOrBuilder> getLocationOrBuilderList() {
                if (this.locationBuilder_ != null) {
                    return this.locationBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.location_);
            }

            public Builder addLocationBuilder() {
                return (Builder) getLocationFieldBuilder().addBuilder(Location.getDefaultInstance());
            }

            public Builder addLocationBuilder(int index) {
                return (Builder) getLocationFieldBuilder().addBuilder(index, Location.getDefaultInstance());
            }

            public List<Builder> getLocationBuilderList() {
                return getLocationFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<Location, Builder, LocationOrBuilder> getLocationFieldBuilder() {
                boolean z = true;
                if (this.locationBuilder_ == null) {
                    List list = this.location_;
                    if ((this.bitField0_ & 1) != 1) {
                        z = false;
                    }
                    this.locationBuilder_ = new RepeatedFieldBuilder(list, z, getParentForChildren(), isClean());
                    this.location_ = null;
                }
                return this.locationBuilder_;
            }
        }

        public interface LocationOrBuilder extends MessageOrBuilder {
            String getLeadingComments();

            ByteString getLeadingCommentsBytes();

            int getPath(int i);

            int getPathCount();

            List<Integer> getPathList();

            int getSpan(int i);

            int getSpanCount();

            List<Integer> getSpanList();

            String getTrailingComments();

            ByteString getTrailingCommentsBytes();

            boolean hasLeadingComments();

            boolean hasTrailingComments();
        }

        public static final class Location extends GeneratedMessage implements LocationOrBuilder {
            public static final int LEADING_COMMENTS_FIELD_NUMBER = 3;
            public static Parser<Location> PARSER = new AbstractParser<Location>() {
                public Location parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new Location(input, extensionRegistry);
                }
            };
            public static final int PATH_FIELD_NUMBER = 1;
            public static final int SPAN_FIELD_NUMBER = 2;
            public static final int TRAILING_COMMENTS_FIELD_NUMBER = 4;
            private static final Location defaultInstance = new Location(true);
            private static final long serialVersionUID = 0;
            private int bitField0_;
            private Object leadingComments_;
            private byte memoizedIsInitialized;
            private int memoizedSerializedSize;
            private int pathMemoizedSerializedSize;
            private List<Integer> path_;
            private int spanMemoizedSerializedSize;
            private List<Integer> span_;
            private Object trailingComments_;
            private final UnknownFieldSet unknownFields;

            public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> implements LocationOrBuilder {
                private int bitField0_;
                private Object leadingComments_;
                private List<Integer> path_;
                private List<Integer> span_;
                private Object trailingComments_;

                public static final Descriptor getDescriptor() {
                    return DescriptorProtos.internal_static_google_protobuf_SourceCodeInfo_Location_descriptor;
                }

                protected FieldAccessorTable internalGetFieldAccessorTable() {
                    return DescriptorProtos.internal_static_google_protobuf_SourceCodeInfo_Location_fieldAccessorTable.ensureFieldAccessorsInitialized(Location.class, Builder.class);
                }

                private Builder() {
                    this.path_ = Collections.emptyList();
                    this.span_ = Collections.emptyList();
                    this.leadingComments_ = "";
                    this.trailingComments_ = "";
                    maybeForceBuilderInitialization();
                }

                private Builder(BuilderParent parent) {
                    super(parent);
                    this.path_ = Collections.emptyList();
                    this.span_ = Collections.emptyList();
                    this.leadingComments_ = "";
                    this.trailingComments_ = "";
                    maybeForceBuilderInitialization();
                }

                private void maybeForceBuilderInitialization() {
                    if (!GeneratedMessage.alwaysUseFieldBuilders) {
                    }
                }

                private static Builder create() {
                    return new Builder();
                }

                public Builder clear() {
                    super.clear();
                    this.path_ = Collections.emptyList();
                    this.bitField0_ &= -2;
                    this.span_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                    this.leadingComments_ = "";
                    this.bitField0_ &= -5;
                    this.trailingComments_ = "";
                    this.bitField0_ &= -9;
                    return this;
                }

                public Builder clone() {
                    return create().mergeFrom(buildPartial());
                }

                public Descriptor getDescriptorForType() {
                    return DescriptorProtos.internal_static_google_protobuf_SourceCodeInfo_Location_descriptor;
                }

                public Location getDefaultInstanceForType() {
                    return Location.getDefaultInstance();
                }

                public Location build() {
                    Location result = buildPartial();
                    if (result.isInitialized()) {
                        return result;
                    }
                    throw com.google.protobuf.AbstractMessage.Builder.newUninitializedMessageException(result);
                }

                public Location buildPartial() {
                    Location result = new Location((com.google.protobuf.GeneratedMessage.Builder) this);
                    int from_bitField0_ = this.bitField0_;
                    int to_bitField0_ = 0;
                    if ((this.bitField0_ & 1) == 1) {
                        this.path_ = Collections.unmodifiableList(this.path_);
                        this.bitField0_ &= -2;
                    }
                    result.path_ = this.path_;
                    if ((this.bitField0_ & 2) == 2) {
                        this.span_ = Collections.unmodifiableList(this.span_);
                        this.bitField0_ &= -3;
                    }
                    result.span_ = this.span_;
                    if ((from_bitField0_ & 4) == 4) {
                        to_bitField0_ = 0 | 1;
                    }
                    result.leadingComments_ = this.leadingComments_;
                    if ((from_bitField0_ & 8) == 8) {
                        to_bitField0_ |= 2;
                    }
                    result.trailingComments_ = this.trailingComments_;
                    result.bitField0_ = to_bitField0_;
                    onBuilt();
                    return result;
                }

                public Builder mergeFrom(Message other) {
                    if (other instanceof Location) {
                        return mergeFrom((Location) other);
                    }
                    super.mergeFrom(other);
                    return this;
                }

                public Builder mergeFrom(Location other) {
                    if (other != Location.getDefaultInstance()) {
                        if (!other.path_.isEmpty()) {
                            if (this.path_.isEmpty()) {
                                this.path_ = other.path_;
                                this.bitField0_ &= -2;
                            } else {
                                ensurePathIsMutable();
                                this.path_.addAll(other.path_);
                            }
                            onChanged();
                        }
                        if (!other.span_.isEmpty()) {
                            if (this.span_.isEmpty()) {
                                this.span_ = other.span_;
                                this.bitField0_ &= -3;
                            } else {
                                ensureSpanIsMutable();
                                this.span_.addAll(other.span_);
                            }
                            onChanged();
                        }
                        if (other.hasLeadingComments()) {
                            this.bitField0_ |= 4;
                            this.leadingComments_ = other.leadingComments_;
                            onChanged();
                        }
                        if (other.hasTrailingComments()) {
                            this.bitField0_ |= 8;
                            this.trailingComments_ = other.trailingComments_;
                            onChanged();
                        }
                        mergeUnknownFields(other.getUnknownFields());
                    }
                    return this;
                }

                public final boolean isInitialized() {
                    return true;
                }

                public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                    Location parsedMessage = null;
                    try {
                        parsedMessage = (Location) Location.PARSER.parsePartialFrom(input, extensionRegistry);
                        if (parsedMessage != null) {
                            mergeFrom(parsedMessage);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        parsedMessage = (Location) e.getUnfinishedMessage();
                        throw e;
                    } catch (Throwable th) {
                        if (parsedMessage != null) {
                            mergeFrom(parsedMessage);
                        }
                    }
                }

                private void ensurePathIsMutable() {
                    if ((this.bitField0_ & 1) != 1) {
                        this.path_ = new ArrayList(this.path_);
                        this.bitField0_ |= 1;
                    }
                }

                public List<Integer> getPathList() {
                    return Collections.unmodifiableList(this.path_);
                }

                public int getPathCount() {
                    return this.path_.size();
                }

                public int getPath(int index) {
                    return ((Integer) this.path_.get(index)).intValue();
                }

                public Builder setPath(int index, int value) {
                    ensurePathIsMutable();
                    this.path_.set(index, Integer.valueOf(value));
                    onChanged();
                    return this;
                }

                public Builder addPath(int value) {
                    ensurePathIsMutable();
                    this.path_.add(Integer.valueOf(value));
                    onChanged();
                    return this;
                }

                public Builder addAllPath(Iterable<? extends Integer> values) {
                    ensurePathIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.path_);
                    onChanged();
                    return this;
                }

                public Builder clearPath() {
                    this.path_ = Collections.emptyList();
                    this.bitField0_ &= -2;
                    onChanged();
                    return this;
                }

                private void ensureSpanIsMutable() {
                    if ((this.bitField0_ & 2) != 2) {
                        this.span_ = new ArrayList(this.span_);
                        this.bitField0_ |= 2;
                    }
                }

                public List<Integer> getSpanList() {
                    return Collections.unmodifiableList(this.span_);
                }

                public int getSpanCount() {
                    return this.span_.size();
                }

                public int getSpan(int index) {
                    return ((Integer) this.span_.get(index)).intValue();
                }

                public Builder setSpan(int index, int value) {
                    ensureSpanIsMutable();
                    this.span_.set(index, Integer.valueOf(value));
                    onChanged();
                    return this;
                }

                public Builder addSpan(int value) {
                    ensureSpanIsMutable();
                    this.span_.add(Integer.valueOf(value));
                    onChanged();
                    return this;
                }

                public Builder addAllSpan(Iterable<? extends Integer> values) {
                    ensureSpanIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.span_);
                    onChanged();
                    return this;
                }

                public Builder clearSpan() {
                    this.span_ = Collections.emptyList();
                    this.bitField0_ &= -3;
                    onChanged();
                    return this;
                }

                public boolean hasLeadingComments() {
                    return (this.bitField0_ & 4) == 4;
                }

                public String getLeadingComments() {
                    ByteString ref = this.leadingComments_;
                    if (ref instanceof String) {
                        return (String) ref;
                    }
                    ByteString bs = ref;
                    String s = bs.toStringUtf8();
                    if (!bs.isValidUtf8()) {
                        return s;
                    }
                    this.leadingComments_ = s;
                    return s;
                }

                public ByteString getLeadingCommentsBytes() {
                    Object ref = this.leadingComments_;
                    if (!(ref instanceof String)) {
                        return (ByteString) ref;
                    }
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    this.leadingComments_ = b;
                    return b;
                }

                public Builder setLeadingComments(String value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.bitField0_ |= 4;
                    this.leadingComments_ = value;
                    onChanged();
                    return this;
                }

                public Builder clearLeadingComments() {
                    this.bitField0_ &= -5;
                    this.leadingComments_ = Location.getDefaultInstance().getLeadingComments();
                    onChanged();
                    return this;
                }

                public Builder setLeadingCommentsBytes(ByteString value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.bitField0_ |= 4;
                    this.leadingComments_ = value;
                    onChanged();
                    return this;
                }

                public boolean hasTrailingComments() {
                    return (this.bitField0_ & 8) == 8;
                }

                public String getTrailingComments() {
                    ByteString ref = this.trailingComments_;
                    if (ref instanceof String) {
                        return (String) ref;
                    }
                    ByteString bs = ref;
                    String s = bs.toStringUtf8();
                    if (!bs.isValidUtf8()) {
                        return s;
                    }
                    this.trailingComments_ = s;
                    return s;
                }

                public ByteString getTrailingCommentsBytes() {
                    Object ref = this.trailingComments_;
                    if (!(ref instanceof String)) {
                        return (ByteString) ref;
                    }
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    this.trailingComments_ = b;
                    return b;
                }

                public Builder setTrailingComments(String value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.bitField0_ |= 8;
                    this.trailingComments_ = value;
                    onChanged();
                    return this;
                }

                public Builder clearTrailingComments() {
                    this.bitField0_ &= -9;
                    this.trailingComments_ = Location.getDefaultInstance().getTrailingComments();
                    onChanged();
                    return this;
                }

                public Builder setTrailingCommentsBytes(ByteString value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.bitField0_ |= 8;
                    this.trailingComments_ = value;
                    onChanged();
                    return this;
                }
            }

            private Location(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
                super(builder);
                this.pathMemoizedSerializedSize = -1;
                this.spanMemoizedSerializedSize = -1;
                this.memoizedIsInitialized = (byte) -1;
                this.memoizedSerializedSize = -1;
                this.unknownFields = builder.getUnknownFields();
            }

            private Location(boolean noInit) {
                this.pathMemoizedSerializedSize = -1;
                this.spanMemoizedSerializedSize = -1;
                this.memoizedIsInitialized = (byte) -1;
                this.memoizedSerializedSize = -1;
                this.unknownFields = UnknownFieldSet.getDefaultInstance();
            }

            public static Location getDefaultInstance() {
                return defaultInstance;
            }

            public Location getDefaultInstanceForType() {
                return defaultInstance;
            }

            public final UnknownFieldSet getUnknownFields() {
                return this.unknownFields;
            }

            private Location(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                this.pathMemoizedSerializedSize = -1;
                this.spanMemoizedSerializedSize = -1;
                this.memoizedIsInitialized = (byte) -1;
                this.memoizedSerializedSize = -1;
                initFields();
                int mutable_bitField0_ = 0;
                com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
                boolean done = false;
                while (!done) {
                    try {
                        int tag = input.readTag();
                        int limit;
                        ByteString bs;
                        switch (tag) {
                            case 0:
                                done = true;
                                break;
                            case 8:
                                if ((mutable_bitField0_ & 1) != 1) {
                                    this.path_ = new ArrayList();
                                    mutable_bitField0_ |= 1;
                                }
                                this.path_.add(Integer.valueOf(input.readInt32()));
                                break;
                            case 10:
                                limit = input.pushLimit(input.readRawVarint32());
                                if ((mutable_bitField0_ & 1) != 1 && input.getBytesUntilLimit() > 0) {
                                    this.path_ = new ArrayList();
                                    mutable_bitField0_ |= 1;
                                }
                                while (input.getBytesUntilLimit() > 0) {
                                    this.path_.add(Integer.valueOf(input.readInt32()));
                                }
                                input.popLimit(limit);
                                break;
                            case 16:
                                if ((mutable_bitField0_ & 2) != 2) {
                                    this.span_ = new ArrayList();
                                    mutable_bitField0_ |= 2;
                                }
                                this.span_.add(Integer.valueOf(input.readInt32()));
                                break;
                            case 18:
                                limit = input.pushLimit(input.readRawVarint32());
                                if ((mutable_bitField0_ & 2) != 2 && input.getBytesUntilLimit() > 0) {
                                    this.span_ = new ArrayList();
                                    mutable_bitField0_ |= 2;
                                }
                                while (input.getBytesUntilLimit() > 0) {
                                    this.span_.add(Integer.valueOf(input.readInt32()));
                                }
                                input.popLimit(limit);
                                break;
                            case 26:
                                bs = input.readBytes();
                                this.bitField0_ |= 1;
                                this.leadingComments_ = bs;
                                break;
                            case 34:
                                bs = input.readBytes();
                                this.bitField0_ |= 2;
                                this.trailingComments_ = bs;
                                break;
                            default:
                                if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                    done = true;
                                    break;
                                }
                                break;
                        }
                    } catch (InvalidProtocolBufferException e) {
                        throw e.setUnfinishedMessage(this);
                    } catch (IOException e2) {
                        throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                    } catch (Throwable th) {
                        if ((mutable_bitField0_ & 1) == 1) {
                            this.path_ = Collections.unmodifiableList(this.path_);
                        }
                        if ((mutable_bitField0_ & 2) == 2) {
                            this.span_ = Collections.unmodifiableList(this.span_);
                        }
                        this.unknownFields = unknownFields.build();
                        makeExtensionsImmutable();
                    }
                }
                if ((mutable_bitField0_ & 1) == 1) {
                    this.path_ = Collections.unmodifiableList(this.path_);
                }
                if ((mutable_bitField0_ & 2) == 2) {
                    this.span_ = Collections.unmodifiableList(this.span_);
                }
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
            }

            public static final Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_SourceCodeInfo_Location_descriptor;
            }

            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_SourceCodeInfo_Location_fieldAccessorTable.ensureFieldAccessorsInitialized(Location.class, Builder.class);
            }

            static {
                defaultInstance.initFields();
            }

            public Parser<Location> getParserForType() {
                return PARSER;
            }

            public List<Integer> getPathList() {
                return this.path_;
            }

            public int getPathCount() {
                return this.path_.size();
            }

            public int getPath(int index) {
                return ((Integer) this.path_.get(index)).intValue();
            }

            public List<Integer> getSpanList() {
                return this.span_;
            }

            public int getSpanCount() {
                return this.span_.size();
            }

            public int getSpan(int index) {
                return ((Integer) this.span_.get(index)).intValue();
            }

            public boolean hasLeadingComments() {
                return (this.bitField0_ & 1) == 1;
            }

            public String getLeadingComments() {
                ByteString ref = this.leadingComments_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.leadingComments_ = s;
                }
                return s;
            }

            public ByteString getLeadingCommentsBytes() {
                Object ref = this.leadingComments_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.leadingComments_ = b;
                return b;
            }

            public boolean hasTrailingComments() {
                return (this.bitField0_ & 2) == 2;
            }

            public String getTrailingComments() {
                ByteString ref = this.trailingComments_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.trailingComments_ = s;
                }
                return s;
            }

            public ByteString getTrailingCommentsBytes() {
                Object ref = this.trailingComments_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.trailingComments_ = b;
                return b;
            }

            private void initFields() {
                this.path_ = Collections.emptyList();
                this.span_ = Collections.emptyList();
                this.leadingComments_ = "";
                this.trailingComments_ = "";
            }

            public final boolean isInitialized() {
                byte isInitialized = this.memoizedIsInitialized;
                if (isInitialized == (byte) 1) {
                    return true;
                }
                if (isInitialized == (byte) 0) {
                    return false;
                }
                this.memoizedIsInitialized = (byte) 1;
                return true;
            }

            public void writeTo(CodedOutputStream output) throws IOException {
                int i;
                getSerializedSize();
                if (getPathList().size() > 0) {
                    output.writeRawVarint32(10);
                    output.writeRawVarint32(this.pathMemoizedSerializedSize);
                }
                for (i = 0; i < this.path_.size(); i++) {
                    output.writeInt32NoTag(((Integer) this.path_.get(i)).intValue());
                }
                if (getSpanList().size() > 0) {
                    output.writeRawVarint32(18);
                    output.writeRawVarint32(this.spanMemoizedSerializedSize);
                }
                for (i = 0; i < this.span_.size(); i++) {
                    output.writeInt32NoTag(((Integer) this.span_.get(i)).intValue());
                }
                if ((this.bitField0_ & 1) == 1) {
                    output.writeBytes(3, getLeadingCommentsBytes());
                }
                if ((this.bitField0_ & 2) == 2) {
                    output.writeBytes(4, getTrailingCommentsBytes());
                }
                getUnknownFields().writeTo(output);
            }

            public int getSerializedSize() {
                int size = this.memoizedSerializedSize;
                if (size != -1) {
                    return size;
                }
                int i;
                int dataSize = 0;
                for (i = 0; i < this.path_.size(); i++) {
                    dataSize += CodedOutputStream.computeInt32SizeNoTag(((Integer) this.path_.get(i)).intValue());
                }
                size = 0 + dataSize;
                if (!getPathList().isEmpty()) {
                    size = (size + 1) + CodedOutputStream.computeInt32SizeNoTag(dataSize);
                }
                this.pathMemoizedSerializedSize = dataSize;
                dataSize = 0;
                for (i = 0; i < this.span_.size(); i++) {
                    dataSize += CodedOutputStream.computeInt32SizeNoTag(((Integer) this.span_.get(i)).intValue());
                }
                size += dataSize;
                if (!getSpanList().isEmpty()) {
                    size = (size + 1) + CodedOutputStream.computeInt32SizeNoTag(dataSize);
                }
                this.spanMemoizedSerializedSize = dataSize;
                if ((this.bitField0_ & 1) == 1) {
                    size += CodedOutputStream.computeBytesSize(3, getLeadingCommentsBytes());
                }
                if ((this.bitField0_ & 2) == 2) {
                    size += CodedOutputStream.computeBytesSize(4, getTrailingCommentsBytes());
                }
                size += getUnknownFields().getSerializedSize();
                this.memoizedSerializedSize = size;
                return size;
            }

            protected Object writeReplace() throws ObjectStreamException {
                return super.writeReplace();
            }

            public static Location parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (Location) PARSER.parseFrom(data);
            }

            public static Location parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Location) PARSER.parseFrom(data, extensionRegistry);
            }

            public static Location parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (Location) PARSER.parseFrom(data);
            }

            public static Location parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (Location) PARSER.parseFrom(data, extensionRegistry);
            }

            public static Location parseFrom(InputStream input) throws IOException {
                return (Location) PARSER.parseFrom(input);
            }

            public static Location parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Location) PARSER.parseFrom(input, extensionRegistry);
            }

            public static Location parseDelimitedFrom(InputStream input) throws IOException {
                return (Location) PARSER.parseDelimitedFrom(input);
            }

            public static Location parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Location) PARSER.parseDelimitedFrom(input, extensionRegistry);
            }

            public static Location parseFrom(CodedInputStream input) throws IOException {
                return (Location) PARSER.parseFrom(input);
            }

            public static Location parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (Location) PARSER.parseFrom(input, extensionRegistry);
            }

            public static Builder newBuilder() {
                return Builder.create();
            }

            public Builder newBuilderForType() {
                return newBuilder();
            }

            public static Builder newBuilder(Location prototype) {
                return newBuilder().mergeFrom(prototype);
            }

            public Builder toBuilder() {
                return newBuilder(this);
            }

            protected Builder newBuilderForType(BuilderParent parent) {
                return new Builder(parent);
            }
        }

        private SourceCodeInfo(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private SourceCodeInfo(boolean noInit) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static SourceCodeInfo getDefaultInstance() {
            return defaultInstance;
        }

        public SourceCodeInfo getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private SourceCodeInfo(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 10:
                            if ((mutable_bitField0_ & 1) != 1) {
                                this.location_ = new ArrayList();
                                mutable_bitField0_ |= 1;
                            }
                            this.location_.add(input.readMessage(Location.PARSER, extensionRegistry));
                            break;
                        default:
                            if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                                break;
                            }
                            break;
                    }
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                } catch (Throwable th) {
                    if ((mutable_bitField0_ & 1) == 1) {
                        this.location_ = Collections.unmodifiableList(this.location_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                }
            }
            if ((mutable_bitField0_ & 1) == 1) {
                this.location_ = Collections.unmodifiableList(this.location_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_SourceCodeInfo_descriptor;
        }

        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_SourceCodeInfo_fieldAccessorTable.ensureFieldAccessorsInitialized(SourceCodeInfo.class, Builder.class);
        }

        static {
            defaultInstance.initFields();
        }

        public Parser<SourceCodeInfo> getParserForType() {
            return PARSER;
        }

        public List<Location> getLocationList() {
            return this.location_;
        }

        public List<? extends LocationOrBuilder> getLocationOrBuilderList() {
            return this.location_;
        }

        public int getLocationCount() {
            return this.location_.size();
        }

        public Location getLocation(int index) {
            return (Location) this.location_.get(index);
        }

        public LocationOrBuilder getLocationOrBuilder(int index) {
            return (LocationOrBuilder) this.location_.get(index);
        }

        private void initFields() {
            this.location_ = Collections.emptyList();
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == (byte) 1) {
                return true;
            }
            if (isInitialized == (byte) 0) {
                return false;
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            getSerializedSize();
            for (int i = 0; i < this.location_.size(); i++) {
                output.writeMessage(1, (MessageLite) this.location_.get(i));
            }
            getUnknownFields().writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            for (int i = 0; i < this.location_.size(); i++) {
                size += CodedOutputStream.computeMessageSize(1, (MessageLite) this.location_.get(i));
            }
            size += getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = size;
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static SourceCodeInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (SourceCodeInfo) PARSER.parseFrom(data);
        }

        public static SourceCodeInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SourceCodeInfo) PARSER.parseFrom(data, extensionRegistry);
        }

        public static SourceCodeInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (SourceCodeInfo) PARSER.parseFrom(data);
        }

        public static SourceCodeInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (SourceCodeInfo) PARSER.parseFrom(data, extensionRegistry);
        }

        public static SourceCodeInfo parseFrom(InputStream input) throws IOException {
            return (SourceCodeInfo) PARSER.parseFrom(input);
        }

        public static SourceCodeInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SourceCodeInfo) PARSER.parseFrom(input, extensionRegistry);
        }

        public static SourceCodeInfo parseDelimitedFrom(InputStream input) throws IOException {
            return (SourceCodeInfo) PARSER.parseDelimitedFrom(input);
        }

        public static SourceCodeInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SourceCodeInfo) PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static SourceCodeInfo parseFrom(CodedInputStream input) throws IOException {
            return (SourceCodeInfo) PARSER.parseFrom(input);
        }

        public static SourceCodeInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (SourceCodeInfo) PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(SourceCodeInfo prototype) {
            return newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return newBuilder(this);
        }

        protected Builder newBuilderForType(BuilderParent parent) {
            return new Builder(parent);
        }
    }

    public interface UninterpretedOptionOrBuilder extends MessageOrBuilder {
        String getAggregateValue();

        ByteString getAggregateValueBytes();

        double getDoubleValue();

        String getIdentifierValue();

        ByteString getIdentifierValueBytes();

        NamePart getName(int i);

        int getNameCount();

        List<NamePart> getNameList();

        NamePartOrBuilder getNameOrBuilder(int i);

        List<? extends NamePartOrBuilder> getNameOrBuilderList();

        long getNegativeIntValue();

        long getPositiveIntValue();

        ByteString getStringValue();

        boolean hasAggregateValue();

        boolean hasDoubleValue();

        boolean hasIdentifierValue();

        boolean hasNegativeIntValue();

        boolean hasPositiveIntValue();

        boolean hasStringValue();
    }

    public static final class UninterpretedOption extends GeneratedMessage implements UninterpretedOptionOrBuilder {
        public static final int AGGREGATE_VALUE_FIELD_NUMBER = 8;
        public static final int DOUBLE_VALUE_FIELD_NUMBER = 6;
        public static final int IDENTIFIER_VALUE_FIELD_NUMBER = 3;
        public static final int NAME_FIELD_NUMBER = 2;
        public static final int NEGATIVE_INT_VALUE_FIELD_NUMBER = 5;
        public static Parser<UninterpretedOption> PARSER = new AbstractParser<UninterpretedOption>() {
            public UninterpretedOption parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new UninterpretedOption(input, extensionRegistry);
            }
        };
        public static final int POSITIVE_INT_VALUE_FIELD_NUMBER = 4;
        public static final int STRING_VALUE_FIELD_NUMBER = 7;
        private static final UninterpretedOption defaultInstance = new UninterpretedOption(true);
        private static final long serialVersionUID = 0;
        private Object aggregateValue_;
        private int bitField0_;
        private double doubleValue_;
        private Object identifierValue_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private List<NamePart> name_;
        private long negativeIntValue_;
        private long positiveIntValue_;
        private ByteString stringValue_;
        private final UnknownFieldSet unknownFields;

        public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> implements UninterpretedOptionOrBuilder {
            private Object aggregateValue_;
            private int bitField0_;
            private double doubleValue_;
            private Object identifierValue_;
            private RepeatedFieldBuilder<NamePart, Builder, NamePartOrBuilder> nameBuilder_;
            private List<NamePart> name_;
            private long negativeIntValue_;
            private long positiveIntValue_;
            private ByteString stringValue_;

            public static final Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_UninterpretedOption_descriptor;
            }

            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_UninterpretedOption_fieldAccessorTable.ensureFieldAccessorsInitialized(UninterpretedOption.class, Builder.class);
            }

            private Builder() {
                this.name_ = Collections.emptyList();
                this.identifierValue_ = "";
                this.stringValue_ = ByteString.EMPTY;
                this.aggregateValue_ = "";
                maybeForceBuilderInitialization();
            }

            private Builder(BuilderParent parent) {
                super(parent);
                this.name_ = Collections.emptyList();
                this.identifierValue_ = "";
                this.stringValue_ = ByteString.EMPTY;
                this.aggregateValue_ = "";
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (GeneratedMessage.alwaysUseFieldBuilders) {
                    getNameFieldBuilder();
                }
            }

            private static Builder create() {
                return new Builder();
            }

            public Builder clear() {
                super.clear();
                if (this.nameBuilder_ == null) {
                    this.name_ = Collections.emptyList();
                    this.bitField0_ &= -2;
                } else {
                    this.nameBuilder_.clear();
                }
                this.identifierValue_ = "";
                this.bitField0_ &= -3;
                this.positiveIntValue_ = 0;
                this.bitField0_ &= -5;
                this.negativeIntValue_ = 0;
                this.bitField0_ &= -9;
                this.doubleValue_ = 0.0d;
                this.bitField0_ &= -17;
                this.stringValue_ = ByteString.EMPTY;
                this.bitField0_ &= -33;
                this.aggregateValue_ = "";
                this.bitField0_ &= -65;
                return this;
            }

            public Builder clone() {
                return create().mergeFrom(buildPartial());
            }

            public Descriptor getDescriptorForType() {
                return DescriptorProtos.internal_static_google_protobuf_UninterpretedOption_descriptor;
            }

            public UninterpretedOption getDefaultInstanceForType() {
                return UninterpretedOption.getDefaultInstance();
            }

            public UninterpretedOption build() {
                UninterpretedOption result = buildPartial();
                if (result.isInitialized()) {
                    return result;
                }
                throw com.google.protobuf.AbstractMessage.Builder.newUninitializedMessageException(result);
            }

            public UninterpretedOption buildPartial() {
                UninterpretedOption result = new UninterpretedOption((com.google.protobuf.GeneratedMessage.Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if (this.nameBuilder_ == null) {
                    if ((this.bitField0_ & 1) == 1) {
                        this.name_ = Collections.unmodifiableList(this.name_);
                        this.bitField0_ &= -2;
                    }
                    result.name_ = this.name_;
                } else {
                    result.name_ = this.nameBuilder_.build();
                }
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ = 0 | 1;
                }
                result.identifierValue_ = this.identifierValue_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 2;
                }
                result.positiveIntValue_ = this.positiveIntValue_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 4;
                }
                result.negativeIntValue_ = this.negativeIntValue_;
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 8;
                }
                result.doubleValue_ = this.doubleValue_;
                if ((from_bitField0_ & 32) == 32) {
                    to_bitField0_ |= 16;
                }
                result.stringValue_ = this.stringValue_;
                if ((from_bitField0_ & 64) == 64) {
                    to_bitField0_ |= 32;
                }
                result.aggregateValue_ = this.aggregateValue_;
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public Builder mergeFrom(Message other) {
                if (other instanceof UninterpretedOption) {
                    return mergeFrom((UninterpretedOption) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public Builder mergeFrom(UninterpretedOption other) {
                RepeatedFieldBuilder repeatedFieldBuilder = null;
                if (other != UninterpretedOption.getDefaultInstance()) {
                    if (this.nameBuilder_ == null) {
                        if (!other.name_.isEmpty()) {
                            if (this.name_.isEmpty()) {
                                this.name_ = other.name_;
                                this.bitField0_ &= -2;
                            } else {
                                ensureNameIsMutable();
                                this.name_.addAll(other.name_);
                            }
                            onChanged();
                        }
                    } else if (!other.name_.isEmpty()) {
                        if (this.nameBuilder_.isEmpty()) {
                            this.nameBuilder_.dispose();
                            this.nameBuilder_ = null;
                            this.name_ = other.name_;
                            this.bitField0_ &= -2;
                            if (GeneratedMessage.alwaysUseFieldBuilders) {
                                repeatedFieldBuilder = getNameFieldBuilder();
                            }
                            this.nameBuilder_ = repeatedFieldBuilder;
                        } else {
                            this.nameBuilder_.addAllMessages(other.name_);
                        }
                    }
                    if (other.hasIdentifierValue()) {
                        this.bitField0_ |= 2;
                        this.identifierValue_ = other.identifierValue_;
                        onChanged();
                    }
                    if (other.hasPositiveIntValue()) {
                        setPositiveIntValue(other.getPositiveIntValue());
                    }
                    if (other.hasNegativeIntValue()) {
                        setNegativeIntValue(other.getNegativeIntValue());
                    }
                    if (other.hasDoubleValue()) {
                        setDoubleValue(other.getDoubleValue());
                    }
                    if (other.hasStringValue()) {
                        setStringValue(other.getStringValue());
                    }
                    if (other.hasAggregateValue()) {
                        this.bitField0_ |= 64;
                        this.aggregateValue_ = other.aggregateValue_;
                        onChanged();
                    }
                    mergeUnknownFields(other.getUnknownFields());
                }
                return this;
            }

            public final boolean isInitialized() {
                for (int i = 0; i < getNameCount(); i++) {
                    if (!getName(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }

            public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                UninterpretedOption parsedMessage = null;
                try {
                    parsedMessage = (UninterpretedOption) UninterpretedOption.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    parsedMessage = (UninterpretedOption) e.getUnfinishedMessage();
                    throw e;
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        mergeFrom(parsedMessage);
                    }
                }
            }

            private void ensureNameIsMutable() {
                if ((this.bitField0_ & 1) != 1) {
                    this.name_ = new ArrayList(this.name_);
                    this.bitField0_ |= 1;
                }
            }

            public List<NamePart> getNameList() {
                if (this.nameBuilder_ == null) {
                    return Collections.unmodifiableList(this.name_);
                }
                return this.nameBuilder_.getMessageList();
            }

            public int getNameCount() {
                if (this.nameBuilder_ == null) {
                    return this.name_.size();
                }
                return this.nameBuilder_.getCount();
            }

            public NamePart getName(int index) {
                if (this.nameBuilder_ == null) {
                    return (NamePart) this.name_.get(index);
                }
                return (NamePart) this.nameBuilder_.getMessage(index);
            }

            public Builder setName(int index, NamePart value) {
                if (this.nameBuilder_ != null) {
                    this.nameBuilder_.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureNameIsMutable();
                    this.name_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder setName(int index, Builder builderForValue) {
                if (this.nameBuilder_ == null) {
                    ensureNameIsMutable();
                    this.name_.set(index, builderForValue.build());
                    onChanged();
                } else {
                    this.nameBuilder_.setMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addName(NamePart value) {
                if (this.nameBuilder_ != null) {
                    this.nameBuilder_.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureNameIsMutable();
                    this.name_.add(value);
                    onChanged();
                }
                return this;
            }

            public Builder addName(int index, NamePart value) {
                if (this.nameBuilder_ != null) {
                    this.nameBuilder_.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    ensureNameIsMutable();
                    this.name_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public Builder addName(Builder builderForValue) {
                if (this.nameBuilder_ == null) {
                    ensureNameIsMutable();
                    this.name_.add(builderForValue.build());
                    onChanged();
                } else {
                    this.nameBuilder_.addMessage(builderForValue.build());
                }
                return this;
            }

            public Builder addName(int index, Builder builderForValue) {
                if (this.nameBuilder_ == null) {
                    ensureNameIsMutable();
                    this.name_.add(index, builderForValue.build());
                    onChanged();
                } else {
                    this.nameBuilder_.addMessage(index, builderForValue.build());
                }
                return this;
            }

            public Builder addAllName(Iterable<? extends NamePart> values) {
                if (this.nameBuilder_ == null) {
                    ensureNameIsMutable();
                    com.google.protobuf.AbstractMessageLite.Builder.addAll(values, this.name_);
                    onChanged();
                } else {
                    this.nameBuilder_.addAllMessages(values);
                }
                return this;
            }

            public Builder clearName() {
                if (this.nameBuilder_ == null) {
                    this.name_ = Collections.emptyList();
                    this.bitField0_ &= -2;
                    onChanged();
                } else {
                    this.nameBuilder_.clear();
                }
                return this;
            }

            public Builder removeName(int index) {
                if (this.nameBuilder_ == null) {
                    ensureNameIsMutable();
                    this.name_.remove(index);
                    onChanged();
                } else {
                    this.nameBuilder_.remove(index);
                }
                return this;
            }

            public Builder getNameBuilder(int index) {
                return (Builder) getNameFieldBuilder().getBuilder(index);
            }

            public NamePartOrBuilder getNameOrBuilder(int index) {
                if (this.nameBuilder_ == null) {
                    return (NamePartOrBuilder) this.name_.get(index);
                }
                return (NamePartOrBuilder) this.nameBuilder_.getMessageOrBuilder(index);
            }

            public List<? extends NamePartOrBuilder> getNameOrBuilderList() {
                if (this.nameBuilder_ != null) {
                    return this.nameBuilder_.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.name_);
            }

            public Builder addNameBuilder() {
                return (Builder) getNameFieldBuilder().addBuilder(NamePart.getDefaultInstance());
            }

            public Builder addNameBuilder(int index) {
                return (Builder) getNameFieldBuilder().addBuilder(index, NamePart.getDefaultInstance());
            }

            public List<Builder> getNameBuilderList() {
                return getNameFieldBuilder().getBuilderList();
            }

            private RepeatedFieldBuilder<NamePart, Builder, NamePartOrBuilder> getNameFieldBuilder() {
                boolean z = true;
                if (this.nameBuilder_ == null) {
                    List list = this.name_;
                    if ((this.bitField0_ & 1) != 1) {
                        z = false;
                    }
                    this.nameBuilder_ = new RepeatedFieldBuilder(list, z, getParentForChildren(), isClean());
                    this.name_ = null;
                }
                return this.nameBuilder_;
            }

            public boolean hasIdentifierValue() {
                return (this.bitField0_ & 2) == 2;
            }

            public String getIdentifierValue() {
                ByteString ref = this.identifierValue_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.identifierValue_ = s;
                return s;
            }

            public ByteString getIdentifierValueBytes() {
                Object ref = this.identifierValue_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.identifierValue_ = b;
                return b;
            }

            public Builder setIdentifierValue(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.identifierValue_ = value;
                onChanged();
                return this;
            }

            public Builder clearIdentifierValue() {
                this.bitField0_ &= -3;
                this.identifierValue_ = UninterpretedOption.getDefaultInstance().getIdentifierValue();
                onChanged();
                return this;
            }

            public Builder setIdentifierValueBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.identifierValue_ = value;
                onChanged();
                return this;
            }

            public boolean hasPositiveIntValue() {
                return (this.bitField0_ & 4) == 4;
            }

            public long getPositiveIntValue() {
                return this.positiveIntValue_;
            }

            public Builder setPositiveIntValue(long value) {
                this.bitField0_ |= 4;
                this.positiveIntValue_ = value;
                onChanged();
                return this;
            }

            public Builder clearPositiveIntValue() {
                this.bitField0_ &= -5;
                this.positiveIntValue_ = 0;
                onChanged();
                return this;
            }

            public boolean hasNegativeIntValue() {
                return (this.bitField0_ & 8) == 8;
            }

            public long getNegativeIntValue() {
                return this.negativeIntValue_;
            }

            public Builder setNegativeIntValue(long value) {
                this.bitField0_ |= 8;
                this.negativeIntValue_ = value;
                onChanged();
                return this;
            }

            public Builder clearNegativeIntValue() {
                this.bitField0_ &= -9;
                this.negativeIntValue_ = 0;
                onChanged();
                return this;
            }

            public boolean hasDoubleValue() {
                return (this.bitField0_ & 16) == 16;
            }

            public double getDoubleValue() {
                return this.doubleValue_;
            }

            public Builder setDoubleValue(double value) {
                this.bitField0_ |= 16;
                this.doubleValue_ = value;
                onChanged();
                return this;
            }

            public Builder clearDoubleValue() {
                this.bitField0_ &= -17;
                this.doubleValue_ = 0.0d;
                onChanged();
                return this;
            }

            public boolean hasStringValue() {
                return (this.bitField0_ & 32) == 32;
            }

            public ByteString getStringValue() {
                return this.stringValue_;
            }

            public Builder setStringValue(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 32;
                this.stringValue_ = value;
                onChanged();
                return this;
            }

            public Builder clearStringValue() {
                this.bitField0_ &= -33;
                this.stringValue_ = UninterpretedOption.getDefaultInstance().getStringValue();
                onChanged();
                return this;
            }

            public boolean hasAggregateValue() {
                return (this.bitField0_ & 64) == 64;
            }

            public String getAggregateValue() {
                ByteString ref = this.aggregateValue_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.aggregateValue_ = s;
                return s;
            }

            public ByteString getAggregateValueBytes() {
                Object ref = this.aggregateValue_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.aggregateValue_ = b;
                return b;
            }

            public Builder setAggregateValue(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 64;
                this.aggregateValue_ = value;
                onChanged();
                return this;
            }

            public Builder clearAggregateValue() {
                this.bitField0_ &= -65;
                this.aggregateValue_ = UninterpretedOption.getDefaultInstance().getAggregateValue();
                onChanged();
                return this;
            }

            public Builder setAggregateValueBytes(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 64;
                this.aggregateValue_ = value;
                onChanged();
                return this;
            }
        }

        public interface NamePartOrBuilder extends MessageOrBuilder {
            boolean getIsExtension();

            String getNamePart();

            ByteString getNamePartBytes();

            boolean hasIsExtension();

            boolean hasNamePart();
        }

        public static final class NamePart extends GeneratedMessage implements NamePartOrBuilder {
            public static final int IS_EXTENSION_FIELD_NUMBER = 2;
            public static final int NAME_PART_FIELD_NUMBER = 1;
            public static Parser<NamePart> PARSER = new AbstractParser<NamePart>() {
                public NamePart parsePartialFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                    return new NamePart(input, extensionRegistry);
                }
            };
            private static final NamePart defaultInstance = new NamePart(true);
            private static final long serialVersionUID = 0;
            private int bitField0_;
            private boolean isExtension_;
            private byte memoizedIsInitialized;
            private int memoizedSerializedSize;
            private Object namePart_;
            private final UnknownFieldSet unknownFields;

            public static final class Builder extends com.google.protobuf.GeneratedMessage.Builder<Builder> implements NamePartOrBuilder {
                private int bitField0_;
                private boolean isExtension_;
                private Object namePart_;

                public static final Descriptor getDescriptor() {
                    return DescriptorProtos.internal_static_google_protobuf_UninterpretedOption_NamePart_descriptor;
                }

                protected FieldAccessorTable internalGetFieldAccessorTable() {
                    return DescriptorProtos.internal_static_google_protobuf_UninterpretedOption_NamePart_fieldAccessorTable.ensureFieldAccessorsInitialized(NamePart.class, Builder.class);
                }

                private Builder() {
                    this.namePart_ = "";
                    maybeForceBuilderInitialization();
                }

                private Builder(BuilderParent parent) {
                    super(parent);
                    this.namePart_ = "";
                    maybeForceBuilderInitialization();
                }

                private void maybeForceBuilderInitialization() {
                    if (!GeneratedMessage.alwaysUseFieldBuilders) {
                    }
                }

                private static Builder create() {
                    return new Builder();
                }

                public Builder clear() {
                    super.clear();
                    this.namePart_ = "";
                    this.bitField0_ &= -2;
                    this.isExtension_ = false;
                    this.bitField0_ &= -3;
                    return this;
                }

                public Builder clone() {
                    return create().mergeFrom(buildPartial());
                }

                public Descriptor getDescriptorForType() {
                    return DescriptorProtos.internal_static_google_protobuf_UninterpretedOption_NamePart_descriptor;
                }

                public NamePart getDefaultInstanceForType() {
                    return NamePart.getDefaultInstance();
                }

                public NamePart build() {
                    NamePart result = buildPartial();
                    if (result.isInitialized()) {
                        return result;
                    }
                    throw com.google.protobuf.AbstractMessage.Builder.newUninitializedMessageException(result);
                }

                public NamePart buildPartial() {
                    NamePart result = new NamePart((com.google.protobuf.GeneratedMessage.Builder) this);
                    int from_bitField0_ = this.bitField0_;
                    int to_bitField0_ = 0;
                    if ((from_bitField0_ & 1) == 1) {
                        to_bitField0_ = 0 | 1;
                    }
                    result.namePart_ = this.namePart_;
                    if ((from_bitField0_ & 2) == 2) {
                        to_bitField0_ |= 2;
                    }
                    result.isExtension_ = this.isExtension_;
                    result.bitField0_ = to_bitField0_;
                    onBuilt();
                    return result;
                }

                public Builder mergeFrom(Message other) {
                    if (other instanceof NamePart) {
                        return mergeFrom((NamePart) other);
                    }
                    super.mergeFrom(other);
                    return this;
                }

                public Builder mergeFrom(NamePart other) {
                    if (other != NamePart.getDefaultInstance()) {
                        if (other.hasNamePart()) {
                            this.bitField0_ |= 1;
                            this.namePart_ = other.namePart_;
                            onChanged();
                        }
                        if (other.hasIsExtension()) {
                            setIsExtension(other.getIsExtension());
                        }
                        mergeUnknownFields(other.getUnknownFields());
                    }
                    return this;
                }

                public final boolean isInitialized() {
                    if (hasNamePart() && hasIsExtension()) {
                        return true;
                    }
                    return false;
                }

                public Builder mergeFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                    NamePart parsedMessage = null;
                    try {
                        parsedMessage = (NamePart) NamePart.PARSER.parsePartialFrom(input, extensionRegistry);
                        if (parsedMessage != null) {
                            mergeFrom(parsedMessage);
                        }
                        return this;
                    } catch (InvalidProtocolBufferException e) {
                        parsedMessage = (NamePart) e.getUnfinishedMessage();
                        throw e;
                    } catch (Throwable th) {
                        if (parsedMessage != null) {
                            mergeFrom(parsedMessage);
                        }
                    }
                }

                public boolean hasNamePart() {
                    return (this.bitField0_ & 1) == 1;
                }

                public String getNamePart() {
                    ByteString ref = this.namePart_;
                    if (ref instanceof String) {
                        return (String) ref;
                    }
                    ByteString bs = ref;
                    String s = bs.toStringUtf8();
                    if (!bs.isValidUtf8()) {
                        return s;
                    }
                    this.namePart_ = s;
                    return s;
                }

                public ByteString getNamePartBytes() {
                    Object ref = this.namePart_;
                    if (!(ref instanceof String)) {
                        return (ByteString) ref;
                    }
                    ByteString b = ByteString.copyFromUtf8((String) ref);
                    this.namePart_ = b;
                    return b;
                }

                public Builder setNamePart(String value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.bitField0_ |= 1;
                    this.namePart_ = value;
                    onChanged();
                    return this;
                }

                public Builder clearNamePart() {
                    this.bitField0_ &= -2;
                    this.namePart_ = NamePart.getDefaultInstance().getNamePart();
                    onChanged();
                    return this;
                }

                public Builder setNamePartBytes(ByteString value) {
                    if (value == null) {
                        throw new NullPointerException();
                    }
                    this.bitField0_ |= 1;
                    this.namePart_ = value;
                    onChanged();
                    return this;
                }

                public boolean hasIsExtension() {
                    return (this.bitField0_ & 2) == 2;
                }

                public boolean getIsExtension() {
                    return this.isExtension_;
                }

                public Builder setIsExtension(boolean value) {
                    this.bitField0_ |= 2;
                    this.isExtension_ = value;
                    onChanged();
                    return this;
                }

                public Builder clearIsExtension() {
                    this.bitField0_ &= -3;
                    this.isExtension_ = false;
                    onChanged();
                    return this;
                }
            }

            private NamePart(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
                super(builder);
                this.memoizedIsInitialized = (byte) -1;
                this.memoizedSerializedSize = -1;
                this.unknownFields = builder.getUnknownFields();
            }

            private NamePart(boolean noInit) {
                this.memoizedIsInitialized = (byte) -1;
                this.memoizedSerializedSize = -1;
                this.unknownFields = UnknownFieldSet.getDefaultInstance();
            }

            public static NamePart getDefaultInstance() {
                return defaultInstance;
            }

            public NamePart getDefaultInstanceForType() {
                return defaultInstance;
            }

            public final UnknownFieldSet getUnknownFields() {
                return this.unknownFields;
            }

            private NamePart(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                this.memoizedIsInitialized = (byte) -1;
                this.memoizedSerializedSize = -1;
                initFields();
                com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
                boolean done = false;
                while (!done) {
                    try {
                        int tag = input.readTag();
                        switch (tag) {
                            case 0:
                                done = true;
                                break;
                            case 10:
                                ByteString bs = input.readBytes();
                                this.bitField0_ |= 1;
                                this.namePart_ = bs;
                                break;
                            case 16:
                                this.bitField0_ |= 2;
                                this.isExtension_ = input.readBool();
                                break;
                            default:
                                if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                    done = true;
                                    break;
                                }
                                break;
                        }
                    } catch (InvalidProtocolBufferException e) {
                        throw e.setUnfinishedMessage(this);
                    } catch (IOException e2) {
                        throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                    } catch (Throwable th) {
                        this.unknownFields = unknownFields.build();
                        makeExtensionsImmutable();
                    }
                }
                this.unknownFields = unknownFields.build();
                makeExtensionsImmutable();
            }

            public static final Descriptor getDescriptor() {
                return DescriptorProtos.internal_static_google_protobuf_UninterpretedOption_NamePart_descriptor;
            }

            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return DescriptorProtos.internal_static_google_protobuf_UninterpretedOption_NamePart_fieldAccessorTable.ensureFieldAccessorsInitialized(NamePart.class, Builder.class);
            }

            static {
                defaultInstance.initFields();
            }

            public Parser<NamePart> getParserForType() {
                return PARSER;
            }

            public boolean hasNamePart() {
                return (this.bitField0_ & 1) == 1;
            }

            public String getNamePart() {
                ByteString ref = this.namePart_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (bs.isValidUtf8()) {
                    this.namePart_ = s;
                }
                return s;
            }

            public ByteString getNamePartBytes() {
                Object ref = this.namePart_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.namePart_ = b;
                return b;
            }

            public boolean hasIsExtension() {
                return (this.bitField0_ & 2) == 2;
            }

            public boolean getIsExtension() {
                return this.isExtension_;
            }

            private void initFields() {
                this.namePart_ = "";
                this.isExtension_ = false;
            }

            public final boolean isInitialized() {
                byte isInitialized = this.memoizedIsInitialized;
                if (isInitialized == (byte) 1) {
                    return true;
                }
                if (isInitialized == (byte) 0) {
                    return false;
                }
                if (!hasNamePart()) {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                } else if (hasIsExtension()) {
                    this.memoizedIsInitialized = (byte) 1;
                    return true;
                } else {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }

            public void writeTo(CodedOutputStream output) throws IOException {
                getSerializedSize();
                if ((this.bitField0_ & 1) == 1) {
                    output.writeBytes(1, getNamePartBytes());
                }
                if ((this.bitField0_ & 2) == 2) {
                    output.writeBool(2, this.isExtension_);
                }
                getUnknownFields().writeTo(output);
            }

            public int getSerializedSize() {
                int size = this.memoizedSerializedSize;
                if (size != -1) {
                    return size;
                }
                size = 0;
                if ((this.bitField0_ & 1) == 1) {
                    size = 0 + CodedOutputStream.computeBytesSize(1, getNamePartBytes());
                }
                if ((this.bitField0_ & 2) == 2) {
                    size += CodedOutputStream.computeBoolSize(2, this.isExtension_);
                }
                size += getUnknownFields().getSerializedSize();
                this.memoizedSerializedSize = size;
                return size;
            }

            protected Object writeReplace() throws ObjectStreamException {
                return super.writeReplace();
            }

            public static NamePart parseFrom(ByteString data) throws InvalidProtocolBufferException {
                return (NamePart) PARSER.parseFrom(data);
            }

            public static NamePart parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (NamePart) PARSER.parseFrom(data, extensionRegistry);
            }

            public static NamePart parseFrom(byte[] data) throws InvalidProtocolBufferException {
                return (NamePart) PARSER.parseFrom(data);
            }

            public static NamePart parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return (NamePart) PARSER.parseFrom(data, extensionRegistry);
            }

            public static NamePart parseFrom(InputStream input) throws IOException {
                return (NamePart) PARSER.parseFrom(input);
            }

            public static NamePart parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (NamePart) PARSER.parseFrom(input, extensionRegistry);
            }

            public static NamePart parseDelimitedFrom(InputStream input) throws IOException {
                return (NamePart) PARSER.parseDelimitedFrom(input);
            }

            public static NamePart parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (NamePart) PARSER.parseDelimitedFrom(input, extensionRegistry);
            }

            public static NamePart parseFrom(CodedInputStream input) throws IOException {
                return (NamePart) PARSER.parseFrom(input);
            }

            public static NamePart parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                return (NamePart) PARSER.parseFrom(input, extensionRegistry);
            }

            public static Builder newBuilder() {
                return Builder.create();
            }

            public Builder newBuilderForType() {
                return newBuilder();
            }

            public static Builder newBuilder(NamePart prototype) {
                return newBuilder().mergeFrom(prototype);
            }

            public Builder toBuilder() {
                return newBuilder(this);
            }

            protected Builder newBuilderForType(BuilderParent parent) {
                return new Builder(parent);
            }
        }

        private UninterpretedOption(com.google.protobuf.GeneratedMessage.Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private UninterpretedOption(boolean noInit) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static UninterpretedOption getDefaultInstance() {
            return defaultInstance;
        }

        public UninterpretedOption getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private UninterpretedOption(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            int mutable_bitField0_ = 0;
            com.google.protobuf.UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
            boolean done = false;
            while (!done) {
                try {
                    int tag = input.readTag();
                    ByteString bs;
                    switch (tag) {
                        case 0:
                            done = true;
                            break;
                        case 18:
                            if ((mutable_bitField0_ & 1) != 1) {
                                this.name_ = new ArrayList();
                                mutable_bitField0_ |= 1;
                            }
                            this.name_.add(input.readMessage(NamePart.PARSER, extensionRegistry));
                            break;
                        case 26:
                            bs = input.readBytes();
                            this.bitField0_ |= 1;
                            this.identifierValue_ = bs;
                            break;
                        case 32:
                            this.bitField0_ |= 2;
                            this.positiveIntValue_ = input.readUInt64();
                            break;
                        case 40:
                            this.bitField0_ |= 4;
                            this.negativeIntValue_ = input.readInt64();
                            break;
                        case 49:
                            this.bitField0_ |= 8;
                            this.doubleValue_ = input.readDouble();
                            break;
                        case 58:
                            this.bitField0_ |= 16;
                            this.stringValue_ = input.readBytes();
                            break;
                        case 66:
                            bs = input.readBytes();
                            this.bitField0_ |= 32;
                            this.aggregateValue_ = bs;
                            break;
                        default:
                            if (!parseUnknownField(input, unknownFields, extensionRegistry, tag)) {
                                done = true;
                                break;
                            }
                            break;
                    }
                } catch (InvalidProtocolBufferException e) {
                    throw e.setUnfinishedMessage(this);
                } catch (IOException e2) {
                    throw new InvalidProtocolBufferException(e2.getMessage()).setUnfinishedMessage(this);
                } catch (Throwable th) {
                    if ((mutable_bitField0_ & 1) == 1) {
                        this.name_ = Collections.unmodifiableList(this.name_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                }
            }
            if ((mutable_bitField0_ & 1) == 1) {
                this.name_ = Collections.unmodifiableList(this.name_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return DescriptorProtos.internal_static_google_protobuf_UninterpretedOption_descriptor;
        }

        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return DescriptorProtos.internal_static_google_protobuf_UninterpretedOption_fieldAccessorTable.ensureFieldAccessorsInitialized(UninterpretedOption.class, Builder.class);
        }

        static {
            defaultInstance.initFields();
        }

        public Parser<UninterpretedOption> getParserForType() {
            return PARSER;
        }

        public List<NamePart> getNameList() {
            return this.name_;
        }

        public List<? extends NamePartOrBuilder> getNameOrBuilderList() {
            return this.name_;
        }

        public int getNameCount() {
            return this.name_.size();
        }

        public NamePart getName(int index) {
            return (NamePart) this.name_.get(index);
        }

        public NamePartOrBuilder getNameOrBuilder(int index) {
            return (NamePartOrBuilder) this.name_.get(index);
        }

        public boolean hasIdentifierValue() {
            return (this.bitField0_ & 1) == 1;
        }

        public String getIdentifierValue() {
            ByteString ref = this.identifierValue_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.identifierValue_ = s;
            }
            return s;
        }

        public ByteString getIdentifierValueBytes() {
            Object ref = this.identifierValue_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.identifierValue_ = b;
            return b;
        }

        public boolean hasPositiveIntValue() {
            return (this.bitField0_ & 2) == 2;
        }

        public long getPositiveIntValue() {
            return this.positiveIntValue_;
        }

        public boolean hasNegativeIntValue() {
            return (this.bitField0_ & 4) == 4;
        }

        public long getNegativeIntValue() {
            return this.negativeIntValue_;
        }

        public boolean hasDoubleValue() {
            return (this.bitField0_ & 8) == 8;
        }

        public double getDoubleValue() {
            return this.doubleValue_;
        }

        public boolean hasStringValue() {
            return (this.bitField0_ & 16) == 16;
        }

        public ByteString getStringValue() {
            return this.stringValue_;
        }

        public boolean hasAggregateValue() {
            return (this.bitField0_ & 32) == 32;
        }

        public String getAggregateValue() {
            ByteString ref = this.aggregateValue_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.aggregateValue_ = s;
            }
            return s;
        }

        public ByteString getAggregateValueBytes() {
            Object ref = this.aggregateValue_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.aggregateValue_ = b;
            return b;
        }

        private void initFields() {
            this.name_ = Collections.emptyList();
            this.identifierValue_ = "";
            this.positiveIntValue_ = 0;
            this.negativeIntValue_ = 0;
            this.doubleValue_ = 0.0d;
            this.stringValue_ = ByteString.EMPTY;
            this.aggregateValue_ = "";
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == (byte) 1) {
                return true;
            }
            if (isInitialized == (byte) 0) {
                return false;
            }
            int i = 0;
            while (i < getNameCount()) {
                if (getName(i).isInitialized()) {
                    i++;
                } else {
                    this.memoizedIsInitialized = (byte) 0;
                    return false;
                }
            }
            this.memoizedIsInitialized = (byte) 1;
            return true;
        }

        public void writeTo(CodedOutputStream output) throws IOException {
            getSerializedSize();
            for (int i = 0; i < this.name_.size(); i++) {
                output.writeMessage(2, (MessageLite) this.name_.get(i));
            }
            if ((this.bitField0_ & 1) == 1) {
                output.writeBytes(3, getIdentifierValueBytes());
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeUInt64(4, this.positiveIntValue_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeInt64(5, this.negativeIntValue_);
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeDouble(6, this.doubleValue_);
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeBytes(7, this.stringValue_);
            }
            if ((this.bitField0_ & 32) == 32) {
                output.writeBytes(8, getAggregateValueBytes());
            }
            getUnknownFields().writeTo(output);
        }

        public int getSerializedSize() {
            int size = this.memoizedSerializedSize;
            if (size != -1) {
                return size;
            }
            size = 0;
            for (int i = 0; i < this.name_.size(); i++) {
                size += CodedOutputStream.computeMessageSize(2, (MessageLite) this.name_.get(i));
            }
            if ((this.bitField0_ & 1) == 1) {
                size += CodedOutputStream.computeBytesSize(3, getIdentifierValueBytes());
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeUInt64Size(4, this.positiveIntValue_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size += CodedOutputStream.computeInt64Size(5, this.negativeIntValue_);
            }
            if ((this.bitField0_ & 8) == 8) {
                size += CodedOutputStream.computeDoubleSize(6, this.doubleValue_);
            }
            if ((this.bitField0_ & 16) == 16) {
                size += CodedOutputStream.computeBytesSize(7, this.stringValue_);
            }
            if ((this.bitField0_ & 32) == 32) {
                size += CodedOutputStream.computeBytesSize(8, getAggregateValueBytes());
            }
            size += getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = size;
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static UninterpretedOption parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (UninterpretedOption) PARSER.parseFrom(data);
        }

        public static UninterpretedOption parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (UninterpretedOption) PARSER.parseFrom(data, extensionRegistry);
        }

        public static UninterpretedOption parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (UninterpretedOption) PARSER.parseFrom(data);
        }

        public static UninterpretedOption parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (UninterpretedOption) PARSER.parseFrom(data, extensionRegistry);
        }

        public static UninterpretedOption parseFrom(InputStream input) throws IOException {
            return (UninterpretedOption) PARSER.parseFrom(input);
        }

        public static UninterpretedOption parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (UninterpretedOption) PARSER.parseFrom(input, extensionRegistry);
        }

        public static UninterpretedOption parseDelimitedFrom(InputStream input) throws IOException {
            return (UninterpretedOption) PARSER.parseDelimitedFrom(input);
        }

        public static UninterpretedOption parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (UninterpretedOption) PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static UninterpretedOption parseFrom(CodedInputStream input) throws IOException {
            return (UninterpretedOption) PARSER.parseFrom(input);
        }

        public static UninterpretedOption parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (UninterpretedOption) PARSER.parseFrom(input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return Builder.create();
        }

        public Builder newBuilderForType() {
            return newBuilder();
        }

        public static Builder newBuilder(UninterpretedOption prototype) {
            return newBuilder().mergeFrom(prototype);
        }

        public Builder toBuilder() {
            return newBuilder(this);
        }

        protected Builder newBuilderForType(BuilderParent parent) {
            return new Builder(parent);
        }
    }

    private DescriptorProtos() {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
    }

    public static FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n google/protobuf/descriptor.proto\u0012\u000fgoogle.protobuf\"G\n\u0011FileDescriptorSet\u00122\n\u0004file\u0018\u0001 \u0003(\u000b2$.google.protobuf.FileDescriptorProto\"Ë\u0003\n\u0013FileDescriptorProto\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\u0012\u000f\n\u0007package\u0018\u0002 \u0001(\t\u0012\u0012\n\ndependency\u0018\u0003 \u0003(\t\u0012\u0019\n\u0011public_dependency\u0018\n \u0003(\u0005\u0012\u0017\n\u000fweak_dependency\u0018\u000b \u0003(\u0005\u00126\n\fmessage_type\u0018\u0004 \u0003(\u000b2 .google.protobuf.DescriptorProto\u00127\n\tenum_type\u0018\u0005 \u0003(\u000b2$.google.protobuf.EnumDescriptorProto\u00128\n\u0007service\u0018\u0006 \u0003(\u000b2'.google.protobuf.", "ServiceDescriptorProto\u00128\n\textension\u0018\u0007 \u0003(\u000b2%.google.protobuf.FieldDescriptorProto\u0012-\n\u0007options\u0018\b \u0001(\u000b2\u001c.google.protobuf.FileOptions\u00129\n\u0010source_code_info\u0018\t \u0001(\u000b2\u001f.google.protobuf.SourceCodeInfo\"ä\u0003\n\u000fDescriptorProto\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\u00124\n\u0005field\u0018\u0002 \u0003(\u000b2%.google.protobuf.FieldDescriptorProto\u00128\n\textension\u0018\u0006 \u0003(\u000b2%.google.protobuf.FieldDescriptorProto\u00125\n\u000bnested_type\u0018\u0003 \u0003(\u000b2 .google.protobuf.DescriptorProto\u00127\n\tenum_type", "\u0018\u0004 \u0003(\u000b2$.google.protobuf.EnumDescriptorProto\u0012H\n\u000fextension_range\u0018\u0005 \u0003(\u000b2/.google.protobuf.DescriptorProto.ExtensionRange\u00129\n\noneof_decl\u0018\b \u0003(\u000b2%.google.protobuf.OneofDescriptorProto\u00120\n\u0007options\u0018\u0007 \u0001(\u000b2\u001f.google.protobuf.MessageOptions\u001a,\n\u000eExtensionRange\u0012\r\n\u0005start\u0018\u0001 \u0001(\u0005\u0012\u000b\n\u0003end\u0018\u0002 \u0001(\u0005\"©\u0005\n\u0014FieldDescriptorProto\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\u0012\u000e\n\u0006number\u0018\u0003 \u0001(\u0005\u0012:\n\u0005label\u0018\u0004 \u0001(\u000e2+.google.protobuf.FieldDescriptorProto.Label\u00128\n\u0004type\u0018\u0005 \u0001", "(\u000e2*.google.protobuf.FieldDescriptorProto.Type\u0012\u0011\n\ttype_name\u0018\u0006 \u0001(\t\u0012\u0010\n\bextendee\u0018\u0002 \u0001(\t\u0012\u0015\n\rdefault_value\u0018\u0007 \u0001(\t\u0012\u0013\n\u000boneof_index\u0018\t \u0001(\u0005\u0012.\n\u0007options\u0018\b \u0001(\u000b2\u001d.google.protobuf.FieldOptions\"¶\u0002\n\u0004Type\u0012\u000f\n\u000bTYPE_DOUBLE\u0010\u0001\u0012\u000e\n\nTYPE_FLOAT\u0010\u0002\u0012\u000e\n\nTYPE_INT64\u0010\u0003\u0012\u000f\n\u000bTYPE_UINT64\u0010\u0004\u0012\u000e\n\nTYPE_INT32\u0010\u0005\u0012\u0010\n\fTYPE_FIXED64\u0010\u0006\u0012\u0010\n\fTYPE_FIXED32\u0010\u0007\u0012\r\n\tTYPE_BOOL\u0010\b\u0012\u000f\n\u000bTYPE_STRING\u0010\t\u0012\u000e\n\nTYPE_GROUP\u0010\n\u0012\u0010\n\fTYPE_MESSAGE\u0010\u000b\u0012\u000e\n\nTYPE_BYTES\u0010\f\u0012\u000f\n\u000bTYPE_UINT32\u0010", "\r\u0012\r\n\tTYPE_ENUM\u0010\u000e\u0012\u0011\n\rTYPE_SFIXED32\u0010\u000f\u0012\u0011\n\rTYPE_SFIXED64\u0010\u0010\u0012\u000f\n\u000bTYPE_SINT32\u0010\u0011\u0012\u000f\n\u000bTYPE_SINT64\u0010\u0012\"C\n\u0005Label\u0012\u0012\n\u000eLABEL_OPTIONAL\u0010\u0001\u0012\u0012\n\u000eLABEL_REQUIRED\u0010\u0002\u0012\u0012\n\u000eLABEL_REPEATED\u0010\u0003\"$\n\u0014OneofDescriptorProto\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\"\u0001\n\u0013EnumDescriptorProto\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\u00128\n\u0005value\u0018\u0002 \u0003(\u000b2).google.protobuf.EnumValueDescriptorProto\u0012-\n\u0007options\u0018\u0003 \u0001(\u000b2\u001c.google.protobuf.EnumOptions\"l\n\u0018EnumValueDescriptorProto\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\u0012\u000e\n\u0006number\u0018\u0002 \u0001(\u0005\u00122\n\u0007", "options\u0018\u0003 \u0001(\u000b2!.google.protobuf.EnumValueOptions\"\u0001\n\u0016ServiceDescriptorProto\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\u00126\n\u0006method\u0018\u0002 \u0003(\u000b2&.google.protobuf.MethodDescriptorProto\u00120\n\u0007options\u0018\u0003 \u0001(\u000b2\u001f.google.protobuf.ServiceOptions\"\n\u0015MethodDescriptorProto\u0012\f\n\u0004name\u0018\u0001 \u0001(\t\u0012\u0012\n\ninput_type\u0018\u0002 \u0001(\t\u0012\u0013\n\u000boutput_type\u0018\u0003 \u0001(\t\u0012/\n\u0007options\u0018\u0004 \u0001(\u000b2\u001e.google.protobuf.MethodOptions\"«\u0004\n\u000bFileOptions\u0012\u0014\n\fjava_package\u0018\u0001 \u0001(\t\u0012\u001c\n\u0014java_outer_classname\u0018\b \u0001(\t\u0012\"\n\u0013java", "_multiple_files\u0018\n \u0001(\b:\u0005false\u0012,\n\u001djava_generate_equals_and_hash\u0018\u0014 \u0001(\b:\u0005false\u0012%\n\u0016java_string_check_utf8\u0018\u001b \u0001(\b:\u0005false\u0012F\n\foptimize_for\u0018\t \u0001(\u000e2).google.protobuf.FileOptions.OptimizeMode:\u0005SPEED\u0012\u0012\n\ngo_package\u0018\u000b \u0001(\t\u0012\"\n\u0013cc_generic_services\u0018\u0010 \u0001(\b:\u0005false\u0012$\n\u0015java_generic_services\u0018\u0011 \u0001(\b:\u0005false\u0012\"\n\u0013py_generic_services\u0018\u0012 \u0001(\b:\u0005false\u0012\u0019\n\ndeprecated\u0018\u0017 \u0001(\b:\u0005false\u0012C\n\u0014uninterpreted_option\u0018ç\u0007 \u0003(\u000b2$.google.protobuf.Uninterp", "retedOption\":\n\fOptimizeMode\u0012\t\n\u0005SPEED\u0010\u0001\u0012\r\n\tCODE_SIZE\u0010\u0002\u0012\u0010\n\fLITE_RUNTIME\u0010\u0003*\t\bè\u0007\u0010\u0002\"Ó\u0001\n\u000eMessageOptions\u0012&\n\u0017message_set_wire_format\u0018\u0001 \u0001(\b:\u0005false\u0012.\n\u001fno_standard_descriptor_accessor\u0018\u0002 \u0001(\b:\u0005false\u0012\u0019\n\ndeprecated\u0018\u0003 \u0001(\b:\u0005false\u0012C\n\u0014uninterpreted_option\u0018ç\u0007 \u0003(\u000b2$.google.protobuf.UninterpretedOption*\t\bè\u0007\u0010\u0002\"¾\u0002\n\fFieldOptions\u0012:\n\u0005ctype\u0018\u0001 \u0001(\u000e2#.google.protobuf.FieldOptions.CType:\u0006STRING\u0012\u000e\n\u0006packed\u0018\u0002 \u0001(\b\u0012\u0013\n\u0004lazy\u0018\u0005 ", "\u0001(\b:\u0005false\u0012\u0019\n\ndeprecated\u0018\u0003 \u0001(\b:\u0005false\u0012\u001c\n\u0014experimental_map_key\u0018\t \u0001(\t\u0012\u0013\n\u0004weak\u0018\n \u0001(\b:\u0005false\u0012C\n\u0014uninterpreted_option\u0018ç\u0007 \u0003(\u000b2$.google.protobuf.UninterpretedOption\"/\n\u0005CType\u0012\n\n\u0006STRING\u0010\u0000\u0012\b\n\u0004CORD\u0010\u0001\u0012\u0010\n\fSTRING_PIECE\u0010\u0002*\t\bè\u0007\u0010\u0002\"\u0001\n\u000bEnumOptions\u0012\u0013\n\u000ballow_alias\u0018\u0002 \u0001(\b\u0012\u0019\n\ndeprecated\u0018\u0003 \u0001(\b:\u0005false\u0012C\n\u0014uninterpreted_option\u0018ç\u0007 \u0003(\u000b2$.google.protobuf.UninterpretedOption*\t\bè\u0007\u0010\u0002\"}\n\u0010EnumValueOptions\u0012\u0019\n\ndeprecated\u0018\u0001 \u0001(", "\b:\u0005false\u0012C\n\u0014uninterpreted_option\u0018ç\u0007 \u0003(\u000b2$.google.protobuf.UninterpretedOption*\t\bè\u0007\u0010\u0002\"{\n\u000eServiceOptions\u0012\u0019\n\ndeprecated\u0018! \u0001(\b:\u0005false\u0012C\n\u0014uninterpreted_option\u0018ç\u0007 \u0003(\u000b2$.google.protobuf.UninterpretedOption*\t\bè\u0007\u0010\u0002\"z\n\rMethodOptions\u0012\u0019\n\ndeprecated\u0018! \u0001(\b:\u0005false\u0012C\n\u0014uninterpreted_option\u0018ç\u0007 \u0003(\u000b2$.google.protobuf.UninterpretedOption*\t\bè\u0007\u0010\u0002\"\u0002\n\u0013UninterpretedOption\u0012;\n\u0004name\u0018\u0002 \u0003(\u000b2-.google.protobuf.Uninte", "rpretedOption.NamePart\u0012\u0018\n\u0010identifier_value\u0018\u0003 \u0001(\t\u0012\u001a\n\u0012positive_int_value\u0018\u0004 \u0001(\u0004\u0012\u001a\n\u0012negative_int_value\u0018\u0005 \u0001(\u0003\u0012\u0014\n\fdouble_value\u0018\u0006 \u0001(\u0001\u0012\u0014\n\fstring_value\u0018\u0007 \u0001(\f\u0012\u0017\n\u000faggregate_value\u0018\b \u0001(\t\u001a3\n\bNamePart\u0012\u0011\n\tname_part\u0018\u0001 \u0002(\t\u0012\u0014\n\fis_extension\u0018\u0002 \u0002(\b\"±\u0001\n\u000eSourceCodeInfo\u0012:\n\blocation\u0018\u0001 \u0003(\u000b2(.google.protobuf.SourceCodeInfo.Location\u001ac\n\bLocation\u0012\u0010\n\u0004path\u0018\u0001 \u0003(\u0005B\u0002\u0010\u0001\u0012\u0010\n\u0004span\u0018\u0002 \u0003(\u0005B\u0002\u0010\u0001\u0012\u0018\n\u0010leading_comments\u0018\u0003 \u0001(\t\u0012\u0019\n\u0011trailing_comments", "\u0018\u0004 \u0001(\tB)\n\u0013com.google.protobufB\u0010DescriptorProtosH\u0001"}, new FileDescriptor[0], new InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(FileDescriptor root) {
                DescriptorProtos.descriptor = root;
                return null;
            }
        });
    }
}
