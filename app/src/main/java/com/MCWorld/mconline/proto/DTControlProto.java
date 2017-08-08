package com.MCWorld.mconline.proto;

import com.google.protobuf.AbstractMessage;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.AbstractParser;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner;
import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.GeneratedMessage.FieldAccessorTable;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageOrBuilder;
import com.google.protobuf.Parser;
import com.google.protobuf.RepeatedFieldBuilder;
import com.google.protobuf.UnknownFieldSet;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class DTControlProto {
    private static final Descriptor amm = ((Descriptor) getDescriptor().getMessageTypes().get(0));
    private static FieldAccessorTable amn = new FieldAccessorTable(amm, new String[]{"ServerIp", "ServerPort", "ServerName", "AdminName", "AdminAvatar", "GameVersion", "ServerCurplayer", "ServerMaxplayer", "GameMode", "GameType", "GameSize", "AdminId"});
    private static final Descriptor amo = ((Descriptor) getDescriptor().getMessageTypes().get(1));
    private static FieldAccessorTable amp = new FieldAccessorTable(amo, new String[]{"ProtoType", "ResultMessage", "ServerInfoList"});
    private static FileDescriptor descriptor;

    public interface a extends MessageOrBuilder {
        int getProtoType();

        String getResultMessage();

        ByteString getResultMessageBytes();

        ServerInfo getServerInfoList(int i);

        int getServerInfoListCount();

        List<ServerInfo> getServerInfoListList();

        b getServerInfoListOrBuilder(int i);

        List<? extends b> getServerInfoListOrBuilderList();

        boolean hasProtoType();

        boolean hasResultMessage();
    }

    public static final class DTControlInfo extends GeneratedMessage implements a {
        public static Parser<DTControlInfo> PARSER = new AbstractParser<DTControlInfo>() {
            public /* synthetic */ Object parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return a(codedInputStream, extensionRegistryLite);
            }

            public DTControlInfo a(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new DTControlInfo(input, extensionRegistry);
            }
        };
        public static final int PROTO_TYPE_FIELD_NUMBER = 1;
        public static final int RESULT_MESSAGE_FIELD_NUMBER = 2;
        public static final int SERVER_INFO_LIST_FIELD_NUMBER = 3;
        private static final DTControlInfo defaultInstance = new DTControlInfo(true);
        private static final long serialVersionUID = 0;
        private int bitField0_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private int protoType_;
        private Object resultMessage_;
        private List<ServerInfo> serverInfoList_;
        private final UnknownFieldSet unknownFields;

        public static final class a extends Builder<a> implements a {
            private RepeatedFieldBuilder<ServerInfo, a, b> amq;
            private int bitField0_;
            private int protoType_;
            private Object resultMessage_;
            private List<ServerInfo> serverInfoList_;

            public /* synthetic */ Message build() {
                return Bs();
            }

            public /* synthetic */ MessageLite m0build() {
                return Bs();
            }

            public /* synthetic */ Message buildPartial() {
                return Bt();
            }

            public /* synthetic */ MessageLite m1buildPartial() {
                return Bt();
            }

            public /* synthetic */ AbstractMessage.Builder clear() {
                return Bq();
            }

            public /* synthetic */ Builder m2clear() {
                return Bq();
            }

            public /* synthetic */ Message.Builder m3clear() {
                return Bq();
            }

            public /* synthetic */ MessageLite.Builder m4clear() {
                return Bq();
            }

            public /* synthetic */ AbstractMessage.Builder clone() {
                return Br();
            }

            public /* synthetic */ AbstractMessageLite.Builder m5clone() {
                return Br();
            }

            public /* synthetic */ Builder m6clone() {
                return Br();
            }

            public /* synthetic */ Message.Builder m7clone() {
                return Br();
            }

            public /* synthetic */ MessageLite.Builder m8clone() {
                return Br();
            }

            public /* synthetic */ Object m9clone() throws CloneNotSupportedException {
                return Br();
            }

            public /* synthetic */ AbstractMessage.Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
                return b(codedInputStream, extensionRegistryLite);
            }

            public /* synthetic */ AbstractMessage.Builder mergeFrom(Message message) {
                return a(message);
            }

            public /* synthetic */ AbstractMessageLite.Builder m10mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
                return b(codedInputStream, extensionRegistryLite);
            }

            public /* synthetic */ Message.Builder m11mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
                return b(codedInputStream, extensionRegistryLite);
            }

            public /* synthetic */ Message.Builder m12mergeFrom(Message message) {
                return a(message);
            }

            public /* synthetic */ MessageLite.Builder m13mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
                return b(codedInputStream, extensionRegistryLite);
            }

            public static final Descriptor getDescriptor() {
                return DTControlProto.amo;
            }

            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return DTControlProto.amp.ensureFieldAccessorsInitialized(DTControlInfo.class, a.class);
            }

            private a() {
                this.resultMessage_ = "";
                this.serverInfoList_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private a(BuilderParent parent) {
                super(parent);
                this.resultMessage_ = "";
                this.serverInfoList_ = Collections.emptyList();
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (DTControlInfo.alwaysUseFieldBuilders) {
                    BA();
                }
            }

            private static a Bp() {
                return new a();
            }

            public a Bq() {
                super.clear();
                this.protoType_ = 0;
                this.bitField0_ &= -2;
                this.resultMessage_ = "";
                this.bitField0_ &= -3;
                if (this.amq == null) {
                    this.serverInfoList_ = Collections.emptyList();
                    this.bitField0_ &= -5;
                } else {
                    this.amq.clear();
                }
                return this;
            }

            public a Br() {
                return Bp().a(Bt());
            }

            public Descriptor getDescriptorForType() {
                return DTControlProto.amo;
            }

            public DTControlInfo getDefaultInstanceForType() {
                return DTControlInfo.getDefaultInstance();
            }

            public DTControlInfo Bs() {
                DTControlInfo result = Bt();
                if (result.isInitialized()) {
                    return result;
                }
                throw AbstractMessage.Builder.newUninitializedMessageException(result);
            }

            public DTControlInfo Bt() {
                DTControlInfo result = new DTControlInfo((Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.protoType_ = this.protoType_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.resultMessage_ = this.resultMessage_;
                if (this.amq == null) {
                    if ((this.bitField0_ & 4) == 4) {
                        this.serverInfoList_ = Collections.unmodifiableList(this.serverInfoList_);
                        this.bitField0_ &= -5;
                    }
                    result.serverInfoList_ = this.serverInfoList_;
                } else {
                    result.serverInfoList_ = this.amq.build();
                }
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public a a(Message other) {
                if (other instanceof DTControlInfo) {
                    return a((DTControlInfo) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public a a(DTControlInfo other) {
                RepeatedFieldBuilder repeatedFieldBuilder = null;
                if (other != DTControlInfo.getDefaultInstance()) {
                    if (other.hasProtoType()) {
                        hS(other.getProtoType());
                    }
                    if (other.hasResultMessage()) {
                        this.bitField0_ |= 2;
                        this.resultMessage_ = other.resultMessage_;
                        onChanged();
                    }
                    if (this.amq == null) {
                        if (!other.serverInfoList_.isEmpty()) {
                            if (this.serverInfoList_.isEmpty()) {
                                this.serverInfoList_ = other.serverInfoList_;
                                this.bitField0_ &= -5;
                            } else {
                                Bw();
                                this.serverInfoList_.addAll(other.serverInfoList_);
                            }
                            onChanged();
                        }
                    } else if (!other.serverInfoList_.isEmpty()) {
                        if (this.amq.isEmpty()) {
                            this.amq.dispose();
                            this.amq = null;
                            this.serverInfoList_ = other.serverInfoList_;
                            this.bitField0_ &= -5;
                            if (DTControlInfo.alwaysUseFieldBuilders) {
                                repeatedFieldBuilder = BA();
                            }
                            this.amq = repeatedFieldBuilder;
                        } else {
                            this.amq.addAllMessages(other.serverInfoList_);
                        }
                    }
                    mergeUnknownFields(other.getUnknownFields());
                }
                return this;
            }

            public final boolean isInitialized() {
                if (!hasProtoType() || !hasResultMessage()) {
                    return false;
                }
                for (int i = 0; i < getServerInfoListCount(); i++) {
                    if (!getServerInfoList(i).isInitialized()) {
                        return false;
                    }
                }
                return true;
            }

            public a b(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                DTControlInfo parsedMessage = null;
                try {
                    parsedMessage = (DTControlInfo) DTControlInfo.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage != null) {
                        a(parsedMessage);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    parsedMessage = (DTControlInfo) e.getUnfinishedMessage();
                    throw e;
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        a(parsedMessage);
                    }
                }
            }

            public boolean hasProtoType() {
                return (this.bitField0_ & 1) == 1;
            }

            public int getProtoType() {
                return this.protoType_;
            }

            public a hS(int value) {
                this.bitField0_ |= 1;
                this.protoType_ = value;
                onChanged();
                return this;
            }

            public a Bu() {
                this.bitField0_ &= -2;
                this.protoType_ = 0;
                onChanged();
                return this;
            }

            public boolean hasResultMessage() {
                return (this.bitField0_ & 2) == 2;
            }

            public String getResultMessage() {
                ByteString ref = this.resultMessage_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.resultMessage_ = s;
                return s;
            }

            public ByteString getResultMessageBytes() {
                Object ref = this.resultMessage_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.resultMessage_ = b;
                return b;
            }

            public a dk(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.resultMessage_ = value;
                onChanged();
                return this;
            }

            public a Bv() {
                this.bitField0_ &= -3;
                this.resultMessage_ = DTControlInfo.getDefaultInstance().getResultMessage();
                onChanged();
                return this;
            }

            public a a(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 2;
                this.resultMessage_ = value;
                onChanged();
                return this;
            }

            private void Bw() {
                if ((this.bitField0_ & 4) != 4) {
                    this.serverInfoList_ = new ArrayList(this.serverInfoList_);
                    this.bitField0_ |= 4;
                }
            }

            public List<ServerInfo> getServerInfoListList() {
                if (this.amq == null) {
                    return Collections.unmodifiableList(this.serverInfoList_);
                }
                return this.amq.getMessageList();
            }

            public int getServerInfoListCount() {
                if (this.amq == null) {
                    return this.serverInfoList_.size();
                }
                return this.amq.getCount();
            }

            public ServerInfo getServerInfoList(int index) {
                if (this.amq == null) {
                    return (ServerInfo) this.serverInfoList_.get(index);
                }
                return (ServerInfo) this.amq.getMessage(index);
            }

            public a a(int index, ServerInfo value) {
                if (this.amq != null) {
                    this.amq.setMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    Bw();
                    this.serverInfoList_.set(index, value);
                    onChanged();
                }
                return this;
            }

            public a a(int index, a builderForValue) {
                if (this.amq == null) {
                    Bw();
                    this.serverInfoList_.set(index, builderForValue.BF());
                    onChanged();
                } else {
                    this.amq.setMessage(index, builderForValue.BF());
                }
                return this;
            }

            public a a(ServerInfo value) {
                if (this.amq != null) {
                    this.amq.addMessage(value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    Bw();
                    this.serverInfoList_.add(value);
                    onChanged();
                }
                return this;
            }

            public a b(int index, ServerInfo value) {
                if (this.amq != null) {
                    this.amq.addMessage(index, value);
                } else if (value == null) {
                    throw new NullPointerException();
                } else {
                    Bw();
                    this.serverInfoList_.add(index, value);
                    onChanged();
                }
                return this;
            }

            public a a(a builderForValue) {
                if (this.amq == null) {
                    Bw();
                    this.serverInfoList_.add(builderForValue.BF());
                    onChanged();
                } else {
                    this.amq.addMessage(builderForValue.BF());
                }
                return this;
            }

            public a b(int index, a builderForValue) {
                if (this.amq == null) {
                    Bw();
                    this.serverInfoList_.add(index, builderForValue.BF());
                    onChanged();
                } else {
                    this.amq.addMessage(index, builderForValue.BF());
                }
                return this;
            }

            public a b(Iterable<? extends ServerInfo> values) {
                if (this.amq == null) {
                    Bw();
                    AbstractMessageLite.Builder.addAll(values, this.serverInfoList_);
                    onChanged();
                } else {
                    this.amq.addAllMessages(values);
                }
                return this;
            }

            public a Bx() {
                if (this.amq == null) {
                    this.serverInfoList_ = Collections.emptyList();
                    this.bitField0_ &= -5;
                    onChanged();
                } else {
                    this.amq.clear();
                }
                return this;
            }

            public a hT(int index) {
                if (this.amq == null) {
                    Bw();
                    this.serverInfoList_.remove(index);
                    onChanged();
                } else {
                    this.amq.remove(index);
                }
                return this;
            }

            public a hU(int index) {
                return (a) BA().getBuilder(index);
            }

            public b getServerInfoListOrBuilder(int index) {
                if (this.amq == null) {
                    return (b) this.serverInfoList_.get(index);
                }
                return (b) this.amq.getMessageOrBuilder(index);
            }

            public List<? extends b> getServerInfoListOrBuilderList() {
                if (this.amq != null) {
                    return this.amq.getMessageOrBuilderList();
                }
                return Collections.unmodifiableList(this.serverInfoList_);
            }

            public a By() {
                return (a) BA().addBuilder(ServerInfo.getDefaultInstance());
            }

            public a hV(int index) {
                return (a) BA().addBuilder(index, ServerInfo.getDefaultInstance());
            }

            public List<a> Bz() {
                return BA().getBuilderList();
            }

            private RepeatedFieldBuilder<ServerInfo, a, b> BA() {
                if (this.amq == null) {
                    this.amq = new RepeatedFieldBuilder(this.serverInfoList_, (this.bitField0_ & 4) == 4, getParentForChildren(), isClean());
                    this.serverInfoList_ = null;
                }
                return this.amq;
            }
        }

        private DTControlInfo(Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private DTControlInfo(boolean noInit) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static DTControlInfo getDefaultInstance() {
            return defaultInstance;
        }

        public DTControlInfo getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private DTControlInfo(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            int mutable_bitField0_ = 0;
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
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
                            this.protoType_ = input.readInt32();
                            break;
                        case 18:
                            ByteString bs = input.readBytes();
                            this.bitField0_ |= 2;
                            this.resultMessage_ = bs;
                            break;
                        case 26:
                            if ((mutable_bitField0_ & 4) != 4) {
                                this.serverInfoList_ = new ArrayList();
                                mutable_bitField0_ |= 4;
                            }
                            this.serverInfoList_.add(input.readMessage(ServerInfo.PARSER, extensionRegistry));
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
                        this.serverInfoList_ = Collections.unmodifiableList(this.serverInfoList_);
                    }
                    this.unknownFields = unknownFields.build();
                    makeExtensionsImmutable();
                }
            }
            if ((mutable_bitField0_ & 4) == 4) {
                this.serverInfoList_ = Collections.unmodifiableList(this.serverInfoList_);
            }
            this.unknownFields = unknownFields.build();
            makeExtensionsImmutable();
        }

        public static final Descriptor getDescriptor() {
            return DTControlProto.amo;
        }

        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return DTControlProto.amp.ensureFieldAccessorsInitialized(DTControlInfo.class, a.class);
        }

        static {
            defaultInstance.initFields();
        }

        public Parser<DTControlInfo> getParserForType() {
            return PARSER;
        }

        public boolean hasProtoType() {
            return (this.bitField0_ & 1) == 1;
        }

        public int getProtoType() {
            return this.protoType_;
        }

        public boolean hasResultMessage() {
            return (this.bitField0_ & 2) == 2;
        }

        public String getResultMessage() {
            ByteString ref = this.resultMessage_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.resultMessage_ = s;
            }
            return s;
        }

        public ByteString getResultMessageBytes() {
            Object ref = this.resultMessage_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.resultMessage_ = b;
            return b;
        }

        public List<ServerInfo> getServerInfoListList() {
            return this.serverInfoList_;
        }

        public List<? extends b> getServerInfoListOrBuilderList() {
            return this.serverInfoList_;
        }

        public int getServerInfoListCount() {
            return this.serverInfoList_.size();
        }

        public ServerInfo getServerInfoList(int index) {
            return (ServerInfo) this.serverInfoList_.get(index);
        }

        public b getServerInfoListOrBuilder(int index) {
            return (b) this.serverInfoList_.get(index);
        }

        private void initFields() {
            this.protoType_ = 0;
            this.resultMessage_ = "";
            this.serverInfoList_ = Collections.emptyList();
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == (byte) 1) {
                return true;
            }
            if (isInitialized == (byte) 0) {
                return false;
            }
            if (!hasProtoType()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            } else if (hasResultMessage()) {
                int i = 0;
                while (i < getServerInfoListCount()) {
                    if (getServerInfoList(i).isInitialized()) {
                        i++;
                    } else {
                        this.memoizedIsInitialized = (byte) 0;
                        return false;
                    }
                }
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
                output.writeInt32(1, this.protoType_);
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeBytes(2, getResultMessageBytes());
            }
            for (int i = 0; i < this.serverInfoList_.size(); i++) {
                output.writeMessage(3, (MessageLite) this.serverInfoList_.get(i));
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
                size = 0 + CodedOutputStream.computeInt32Size(1, this.protoType_);
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeBytesSize(2, getResultMessageBytes());
            }
            for (int i = 0; i < this.serverInfoList_.size(); i++) {
                size += CodedOutputStream.computeMessageSize(3, (MessageLite) this.serverInfoList_.get(i));
            }
            size += getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = size;
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static DTControlInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (DTControlInfo) PARSER.parseFrom(data);
        }

        public static DTControlInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DTControlInfo) PARSER.parseFrom(data, extensionRegistry);
        }

        public static DTControlInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (DTControlInfo) PARSER.parseFrom(data);
        }

        public static DTControlInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (DTControlInfo) PARSER.parseFrom(data, extensionRegistry);
        }

        public static DTControlInfo parseFrom(InputStream input) throws IOException {
            return (DTControlInfo) PARSER.parseFrom(input);
        }

        public static DTControlInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DTControlInfo) PARSER.parseFrom(input, extensionRegistry);
        }

        public static DTControlInfo parseDelimitedFrom(InputStream input) throws IOException {
            return (DTControlInfo) PARSER.parseDelimitedFrom(input);
        }

        public static DTControlInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DTControlInfo) PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static DTControlInfo parseFrom(CodedInputStream input) throws IOException {
            return (DTControlInfo) PARSER.parseFrom(input);
        }

        public static DTControlInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (DTControlInfo) PARSER.parseFrom(input, extensionRegistry);
        }

        public static a newBuilder() {
            return a.Bp();
        }

        public a newBuilderForType() {
            return newBuilder();
        }

        public static a newBuilder(DTControlInfo prototype) {
            return newBuilder().a(prototype);
        }

        public a toBuilder() {
            return newBuilder(this);
        }

        protected a newBuilderForType(BuilderParent parent) {
            return new a(parent);
        }
    }

    public interface b extends MessageOrBuilder {
        String getAdminAvatar();

        ByteString getAdminAvatarBytes();

        long getAdminId();

        String getAdminName();

        ByteString getAdminNameBytes();

        int getGameMode();

        int getGameSize();

        int getGameType();

        String getGameVersion();

        ByteString getGameVersionBytes();

        int getServerCurplayer();

        String getServerIp();

        ByteString getServerIpBytes();

        int getServerMaxplayer();

        String getServerName();

        ByteString getServerNameBytes();

        long getServerPort();

        boolean hasAdminAvatar();

        boolean hasAdminId();

        boolean hasAdminName();

        boolean hasGameMode();

        boolean hasGameSize();

        boolean hasGameType();

        boolean hasGameVersion();

        boolean hasServerCurplayer();

        boolean hasServerIp();

        boolean hasServerMaxplayer();

        boolean hasServerName();

        boolean hasServerPort();
    }

    public static final class ServerInfo extends GeneratedMessage implements b {
        public static final int ADMIN_AVATAR_FIELD_NUMBER = 5;
        public static final int ADMIN_ID_FIELD_NUMBER = 12;
        public static final int ADMIN_NAME_FIELD_NUMBER = 4;
        public static final int GAME_MODE_FIELD_NUMBER = 9;
        public static final int GAME_SIZE_FIELD_NUMBER = 11;
        public static final int GAME_TYPE_FIELD_NUMBER = 10;
        public static final int GAME_VERSION_FIELD_NUMBER = 6;
        public static Parser<ServerInfo> PARSER = new AbstractParser<ServerInfo>() {
            public /* synthetic */ Object parsePartialFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
                return c(codedInputStream, extensionRegistryLite);
            }

            public ServerInfo c(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
                return new ServerInfo(input, extensionRegistry);
            }
        };
        public static final int SERVER_CURPLAYER_FIELD_NUMBER = 7;
        public static final int SERVER_IP_FIELD_NUMBER = 1;
        public static final int SERVER_MAXPLAYER_FIELD_NUMBER = 8;
        public static final int SERVER_NAME_FIELD_NUMBER = 3;
        public static final int SERVER_PORT_FIELD_NUMBER = 2;
        private static final ServerInfo defaultInstance = new ServerInfo(true);
        private static final long serialVersionUID = 0;
        private Object adminAvatar_;
        private long adminId_;
        private Object adminName_;
        private int bitField0_;
        private int gameMode_;
        private int gameSize_;
        private int gameType_;
        private Object gameVersion_;
        private byte memoizedIsInitialized;
        private int memoizedSerializedSize;
        private int serverCurplayer_;
        private Object serverIp_;
        private int serverMaxplayer_;
        private Object serverName_;
        private long serverPort_;
        private final UnknownFieldSet unknownFields;

        public static final class a extends Builder<a> implements b {
            private Object adminAvatar_;
            private long adminId_;
            private Object adminName_;
            private int bitField0_;
            private int gameMode_;
            private int gameSize_;
            private int gameType_;
            private Object gameVersion_;
            private int serverCurplayer_;
            private Object serverIp_;
            private int serverMaxplayer_;
            private Object serverName_;
            private long serverPort_;

            public /* synthetic */ Message build() {
                return BF();
            }

            public /* synthetic */ MessageLite m14build() {
                return BF();
            }

            public /* synthetic */ Message buildPartial() {
                return BG();
            }

            public /* synthetic */ MessageLite m15buildPartial() {
                return BG();
            }

            public /* synthetic */ AbstractMessage.Builder clear() {
                return BD();
            }

            public /* synthetic */ Builder m16clear() {
                return BD();
            }

            public /* synthetic */ Message.Builder m17clear() {
                return BD();
            }

            public /* synthetic */ MessageLite.Builder m18clear() {
                return BD();
            }

            public /* synthetic */ AbstractMessage.Builder clone() {
                return BE();
            }

            public /* synthetic */ AbstractMessageLite.Builder m19clone() {
                return BE();
            }

            public /* synthetic */ Builder m20clone() {
                return BE();
            }

            public /* synthetic */ Message.Builder m21clone() {
                return BE();
            }

            public /* synthetic */ MessageLite.Builder m22clone() {
                return BE();
            }

            public /* synthetic */ Object m23clone() throws CloneNotSupportedException {
                return BE();
            }

            public /* synthetic */ AbstractMessage.Builder mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
                return d(codedInputStream, extensionRegistryLite);
            }

            public /* synthetic */ AbstractMessage.Builder mergeFrom(Message message) {
                return b(message);
            }

            public /* synthetic */ AbstractMessageLite.Builder m24mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
                return d(codedInputStream, extensionRegistryLite);
            }

            public /* synthetic */ Message.Builder m25mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
                return d(codedInputStream, extensionRegistryLite);
            }

            public /* synthetic */ Message.Builder m26mergeFrom(Message message) {
                return b(message);
            }

            public /* synthetic */ MessageLite.Builder m27mergeFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
                return d(codedInputStream, extensionRegistryLite);
            }

            public static final Descriptor getDescriptor() {
                return DTControlProto.amm;
            }

            protected FieldAccessorTable internalGetFieldAccessorTable() {
                return DTControlProto.amn.ensureFieldAccessorsInitialized(ServerInfo.class, a.class);
            }

            private a() {
                this.serverIp_ = "";
                this.serverName_ = "";
                this.adminName_ = "";
                this.adminAvatar_ = "";
                this.gameVersion_ = "";
                maybeForceBuilderInitialization();
            }

            private a(BuilderParent parent) {
                super(parent);
                this.serverIp_ = "";
                this.serverName_ = "";
                this.adminName_ = "";
                this.adminAvatar_ = "";
                this.gameVersion_ = "";
                maybeForceBuilderInitialization();
            }

            private void maybeForceBuilderInitialization() {
                if (!ServerInfo.alwaysUseFieldBuilders) {
                }
            }

            private static a BC() {
                return new a();
            }

            public a BD() {
                super.clear();
                this.serverIp_ = "";
                this.bitField0_ &= -2;
                this.serverPort_ = 0;
                this.bitField0_ &= -3;
                this.serverName_ = "";
                this.bitField0_ &= -5;
                this.adminName_ = "";
                this.bitField0_ &= -9;
                this.adminAvatar_ = "";
                this.bitField0_ &= -17;
                this.gameVersion_ = "";
                this.bitField0_ &= -33;
                this.serverCurplayer_ = 0;
                this.bitField0_ &= -65;
                this.serverMaxplayer_ = 0;
                this.bitField0_ &= -129;
                this.gameMode_ = 0;
                this.bitField0_ &= -257;
                this.gameType_ = 0;
                this.bitField0_ &= -513;
                this.gameSize_ = 0;
                this.bitField0_ &= -1025;
                this.adminId_ = 0;
                this.bitField0_ &= -2049;
                return this;
            }

            public a BE() {
                return BC().b(BG());
            }

            public Descriptor getDescriptorForType() {
                return DTControlProto.amm;
            }

            public ServerInfo getDefaultInstanceForType() {
                return ServerInfo.getDefaultInstance();
            }

            public ServerInfo BF() {
                ServerInfo result = BG();
                if (result.isInitialized()) {
                    return result;
                }
                throw AbstractMessage.Builder.newUninitializedMessageException(result);
            }

            public ServerInfo BG() {
                ServerInfo result = new ServerInfo((Builder) this);
                int from_bitField0_ = this.bitField0_;
                int to_bitField0_ = 0;
                if ((from_bitField0_ & 1) == 1) {
                    to_bitField0_ = 0 | 1;
                }
                result.serverIp_ = this.serverIp_;
                if ((from_bitField0_ & 2) == 2) {
                    to_bitField0_ |= 2;
                }
                result.serverPort_ = this.serverPort_;
                if ((from_bitField0_ & 4) == 4) {
                    to_bitField0_ |= 4;
                }
                result.serverName_ = this.serverName_;
                if ((from_bitField0_ & 8) == 8) {
                    to_bitField0_ |= 8;
                }
                result.adminName_ = this.adminName_;
                if ((from_bitField0_ & 16) == 16) {
                    to_bitField0_ |= 16;
                }
                result.adminAvatar_ = this.adminAvatar_;
                if ((from_bitField0_ & 32) == 32) {
                    to_bitField0_ |= 32;
                }
                result.gameVersion_ = this.gameVersion_;
                if ((from_bitField0_ & 64) == 64) {
                    to_bitField0_ |= 64;
                }
                result.serverCurplayer_ = this.serverCurplayer_;
                if ((from_bitField0_ & 128) == 128) {
                    to_bitField0_ |= 128;
                }
                result.serverMaxplayer_ = this.serverMaxplayer_;
                if ((from_bitField0_ & 256) == 256) {
                    to_bitField0_ |= 256;
                }
                result.gameMode_ = this.gameMode_;
                if ((from_bitField0_ & 512) == 512) {
                    to_bitField0_ |= 512;
                }
                result.gameType_ = this.gameType_;
                if ((from_bitField0_ & 1024) == 1024) {
                    to_bitField0_ |= 1024;
                }
                result.gameSize_ = this.gameSize_;
                if ((from_bitField0_ & 2048) == 2048) {
                    to_bitField0_ |= 2048;
                }
                result.adminId_ = this.adminId_;
                result.bitField0_ = to_bitField0_;
                onBuilt();
                return result;
            }

            public a b(Message other) {
                if (other instanceof ServerInfo) {
                    return b((ServerInfo) other);
                }
                super.mergeFrom(other);
                return this;
            }

            public a b(ServerInfo other) {
                if (other != ServerInfo.getDefaultInstance()) {
                    if (other.hasServerIp()) {
                        this.bitField0_ |= 1;
                        this.serverIp_ = other.serverIp_;
                        onChanged();
                    }
                    if (other.hasServerPort()) {
                        aa(other.getServerPort());
                    }
                    if (other.hasServerName()) {
                        this.bitField0_ |= 4;
                        this.serverName_ = other.serverName_;
                        onChanged();
                    }
                    if (other.hasAdminName()) {
                        this.bitField0_ |= 8;
                        this.adminName_ = other.adminName_;
                        onChanged();
                    }
                    if (other.hasAdminAvatar()) {
                        this.bitField0_ |= 16;
                        this.adminAvatar_ = other.adminAvatar_;
                        onChanged();
                    }
                    if (other.hasGameVersion()) {
                        this.bitField0_ |= 32;
                        this.gameVersion_ = other.gameVersion_;
                        onChanged();
                    }
                    if (other.hasServerCurplayer()) {
                        hW(other.getServerCurplayer());
                    }
                    if (other.hasServerMaxplayer()) {
                        hX(other.getServerMaxplayer());
                    }
                    if (other.hasGameMode()) {
                        hY(other.getGameMode());
                    }
                    if (other.hasGameType()) {
                        hZ(other.getGameType());
                    }
                    if (other.hasGameSize()) {
                        ia(other.getGameSize());
                    }
                    if (other.hasAdminId()) {
                        ab(other.getAdminId());
                    }
                    mergeUnknownFields(other.getUnknownFields());
                }
                return this;
            }

            public final boolean isInitialized() {
                if (hasServerIp() && hasServerPort() && hasServerName() && hasAdminName() && hasAdminAvatar() && hasGameVersion() && hasServerCurplayer() && hasServerMaxplayer() && hasGameMode() && hasGameType() && hasGameSize() && hasAdminId()) {
                    return true;
                }
                return false;
            }

            public a d(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
                ServerInfo parsedMessage = null;
                try {
                    parsedMessage = (ServerInfo) ServerInfo.PARSER.parsePartialFrom(input, extensionRegistry);
                    if (parsedMessage != null) {
                        b(parsedMessage);
                    }
                    return this;
                } catch (InvalidProtocolBufferException e) {
                    parsedMessage = (ServerInfo) e.getUnfinishedMessage();
                    throw e;
                } catch (Throwable th) {
                    if (parsedMessage != null) {
                        b(parsedMessage);
                    }
                }
            }

            public boolean hasServerIp() {
                return (this.bitField0_ & 1) == 1;
            }

            public String getServerIp() {
                ByteString ref = this.serverIp_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.serverIp_ = s;
                return s;
            }

            public ByteString getServerIpBytes() {
                Object ref = this.serverIp_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.serverIp_ = b;
                return b;
            }

            public a dl(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.serverIp_ = value;
                onChanged();
                return this;
            }

            public a BH() {
                this.bitField0_ &= -2;
                this.serverIp_ = ServerInfo.getDefaultInstance().getServerIp();
                onChanged();
                return this;
            }

            public a b(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 1;
                this.serverIp_ = value;
                onChanged();
                return this;
            }

            public boolean hasServerPort() {
                return (this.bitField0_ & 2) == 2;
            }

            public long getServerPort() {
                return this.serverPort_;
            }

            public a aa(long value) {
                this.bitField0_ |= 2;
                this.serverPort_ = value;
                onChanged();
                return this;
            }

            public a BI() {
                this.bitField0_ &= -3;
                this.serverPort_ = 0;
                onChanged();
                return this;
            }

            public boolean hasServerName() {
                return (this.bitField0_ & 4) == 4;
            }

            public String getServerName() {
                ByteString ref = this.serverName_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.serverName_ = s;
                return s;
            }

            public ByteString getServerNameBytes() {
                Object ref = this.serverName_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.serverName_ = b;
                return b;
            }

            public a dm(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 4;
                this.serverName_ = value;
                onChanged();
                return this;
            }

            public a BJ() {
                this.bitField0_ &= -5;
                this.serverName_ = ServerInfo.getDefaultInstance().getServerName();
                onChanged();
                return this;
            }

            public a c(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 4;
                this.serverName_ = value;
                onChanged();
                return this;
            }

            public boolean hasAdminName() {
                return (this.bitField0_ & 8) == 8;
            }

            public String getAdminName() {
                ByteString ref = this.adminName_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.adminName_ = s;
                return s;
            }

            public ByteString getAdminNameBytes() {
                Object ref = this.adminName_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.adminName_ = b;
                return b;
            }

            public a dn(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 8;
                this.adminName_ = value;
                onChanged();
                return this;
            }

            public a BK() {
                this.bitField0_ &= -9;
                this.adminName_ = ServerInfo.getDefaultInstance().getAdminName();
                onChanged();
                return this;
            }

            public a d(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 8;
                this.adminName_ = value;
                onChanged();
                return this;
            }

            public boolean hasAdminAvatar() {
                return (this.bitField0_ & 16) == 16;
            }

            public String getAdminAvatar() {
                ByteString ref = this.adminAvatar_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.adminAvatar_ = s;
                return s;
            }

            public ByteString getAdminAvatarBytes() {
                Object ref = this.adminAvatar_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.adminAvatar_ = b;
                return b;
            }

            public a do(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 16;
                this.adminAvatar_ = value;
                onChanged();
                return this;
            }

            public a BL() {
                this.bitField0_ &= -17;
                this.adminAvatar_ = ServerInfo.getDefaultInstance().getAdminAvatar();
                onChanged();
                return this;
            }

            public a e(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 16;
                this.adminAvatar_ = value;
                onChanged();
                return this;
            }

            public boolean hasGameVersion() {
                return (this.bitField0_ & 32) == 32;
            }

            public String getGameVersion() {
                ByteString ref = this.gameVersion_;
                if (ref instanceof String) {
                    return (String) ref;
                }
                ByteString bs = ref;
                String s = bs.toStringUtf8();
                if (!bs.isValidUtf8()) {
                    return s;
                }
                this.gameVersion_ = s;
                return s;
            }

            public ByteString getGameVersionBytes() {
                Object ref = this.gameVersion_;
                if (!(ref instanceof String)) {
                    return (ByteString) ref;
                }
                ByteString b = ByteString.copyFromUtf8((String) ref);
                this.gameVersion_ = b;
                return b;
            }

            public a dp(String value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 32;
                this.gameVersion_ = value;
                onChanged();
                return this;
            }

            public a BM() {
                this.bitField0_ &= -33;
                this.gameVersion_ = ServerInfo.getDefaultInstance().getGameVersion();
                onChanged();
                return this;
            }

            public a f(ByteString value) {
                if (value == null) {
                    throw new NullPointerException();
                }
                this.bitField0_ |= 32;
                this.gameVersion_ = value;
                onChanged();
                return this;
            }

            public boolean hasServerCurplayer() {
                return (this.bitField0_ & 64) == 64;
            }

            public int getServerCurplayer() {
                return this.serverCurplayer_;
            }

            public a hW(int value) {
                this.bitField0_ |= 64;
                this.serverCurplayer_ = value;
                onChanged();
                return this;
            }

            public a BN() {
                this.bitField0_ &= -65;
                this.serverCurplayer_ = 0;
                onChanged();
                return this;
            }

            public boolean hasServerMaxplayer() {
                return (this.bitField0_ & 128) == 128;
            }

            public int getServerMaxplayer() {
                return this.serverMaxplayer_;
            }

            public a hX(int value) {
                this.bitField0_ |= 128;
                this.serverMaxplayer_ = value;
                onChanged();
                return this;
            }

            public a BO() {
                this.bitField0_ &= -129;
                this.serverMaxplayer_ = 0;
                onChanged();
                return this;
            }

            public boolean hasGameMode() {
                return (this.bitField0_ & 256) == 256;
            }

            public int getGameMode() {
                return this.gameMode_;
            }

            public a hY(int value) {
                this.bitField0_ |= 256;
                this.gameMode_ = value;
                onChanged();
                return this;
            }

            public a BP() {
                this.bitField0_ &= -257;
                this.gameMode_ = 0;
                onChanged();
                return this;
            }

            public boolean hasGameType() {
                return (this.bitField0_ & 512) == 512;
            }

            public int getGameType() {
                return this.gameType_;
            }

            public a hZ(int value) {
                this.bitField0_ |= 512;
                this.gameType_ = value;
                onChanged();
                return this;
            }

            public a BQ() {
                this.bitField0_ &= -513;
                this.gameType_ = 0;
                onChanged();
                return this;
            }

            public boolean hasGameSize() {
                return (this.bitField0_ & 1024) == 1024;
            }

            public int getGameSize() {
                return this.gameSize_;
            }

            public a ia(int value) {
                this.bitField0_ |= 1024;
                this.gameSize_ = value;
                onChanged();
                return this;
            }

            public a BR() {
                this.bitField0_ &= -1025;
                this.gameSize_ = 0;
                onChanged();
                return this;
            }

            public boolean hasAdminId() {
                return (this.bitField0_ & 2048) == 2048;
            }

            public long getAdminId() {
                return this.adminId_;
            }

            public a ab(long value) {
                this.bitField0_ |= 2048;
                this.adminId_ = value;
                onChanged();
                return this;
            }

            public a BS() {
                this.bitField0_ &= -2049;
                this.adminId_ = 0;
                onChanged();
                return this;
            }
        }

        private ServerInfo(Builder<?> builder) {
            super(builder);
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = builder.getUnknownFields();
        }

        private ServerInfo(boolean noInit) {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            this.unknownFields = UnknownFieldSet.getDefaultInstance();
        }

        public static ServerInfo getDefaultInstance() {
            return defaultInstance;
        }

        public ServerInfo getDefaultInstanceForType() {
            return defaultInstance;
        }

        public final UnknownFieldSet getUnknownFields() {
            return this.unknownFields;
        }

        private ServerInfo(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            this.memoizedIsInitialized = (byte) -1;
            this.memoizedSerializedSize = -1;
            initFields();
            UnknownFieldSet.Builder unknownFields = UnknownFieldSet.newBuilder();
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
                            this.serverIp_ = bs;
                            break;
                        case 16:
                            this.bitField0_ |= 2;
                            this.serverPort_ = input.readInt64();
                            break;
                        case 26:
                            bs = input.readBytes();
                            this.bitField0_ |= 4;
                            this.serverName_ = bs;
                            break;
                        case 34:
                            bs = input.readBytes();
                            this.bitField0_ |= 8;
                            this.adminName_ = bs;
                            break;
                        case 42:
                            bs = input.readBytes();
                            this.bitField0_ |= 16;
                            this.adminAvatar_ = bs;
                            break;
                        case 50:
                            bs = input.readBytes();
                            this.bitField0_ |= 32;
                            this.gameVersion_ = bs;
                            break;
                        case 56:
                            this.bitField0_ |= 64;
                            this.serverCurplayer_ = input.readInt32();
                            break;
                        case 64:
                            this.bitField0_ |= 128;
                            this.serverMaxplayer_ = input.readInt32();
                            break;
                        case 72:
                            this.bitField0_ |= 256;
                            this.gameMode_ = input.readInt32();
                            break;
                        case 80:
                            this.bitField0_ |= 512;
                            this.gameType_ = input.readInt32();
                            break;
                        case 88:
                            this.bitField0_ |= 1024;
                            this.gameSize_ = input.readInt32();
                            break;
                        case 96:
                            this.bitField0_ |= 2048;
                            this.adminId_ = input.readInt64();
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
            return DTControlProto.amm;
        }

        protected FieldAccessorTable internalGetFieldAccessorTable() {
            return DTControlProto.amn.ensureFieldAccessorsInitialized(ServerInfo.class, a.class);
        }

        static {
            defaultInstance.initFields();
        }

        public Parser<ServerInfo> getParserForType() {
            return PARSER;
        }

        public boolean hasServerIp() {
            return (this.bitField0_ & 1) == 1;
        }

        public String getServerIp() {
            ByteString ref = this.serverIp_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.serverIp_ = s;
            }
            return s;
        }

        public ByteString getServerIpBytes() {
            Object ref = this.serverIp_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.serverIp_ = b;
            return b;
        }

        public boolean hasServerPort() {
            return (this.bitField0_ & 2) == 2;
        }

        public long getServerPort() {
            return this.serverPort_;
        }

        public boolean hasServerName() {
            return (this.bitField0_ & 4) == 4;
        }

        public String getServerName() {
            ByteString ref = this.serverName_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.serverName_ = s;
            }
            return s;
        }

        public ByteString getServerNameBytes() {
            Object ref = this.serverName_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.serverName_ = b;
            return b;
        }

        public boolean hasAdminName() {
            return (this.bitField0_ & 8) == 8;
        }

        public String getAdminName() {
            ByteString ref = this.adminName_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.adminName_ = s;
            }
            return s;
        }

        public ByteString getAdminNameBytes() {
            Object ref = this.adminName_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.adminName_ = b;
            return b;
        }

        public boolean hasAdminAvatar() {
            return (this.bitField0_ & 16) == 16;
        }

        public String getAdminAvatar() {
            ByteString ref = this.adminAvatar_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.adminAvatar_ = s;
            }
            return s;
        }

        public ByteString getAdminAvatarBytes() {
            Object ref = this.adminAvatar_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.adminAvatar_ = b;
            return b;
        }

        public boolean hasGameVersion() {
            return (this.bitField0_ & 32) == 32;
        }

        public String getGameVersion() {
            ByteString ref = this.gameVersion_;
            if (ref instanceof String) {
                return (String) ref;
            }
            ByteString bs = ref;
            String s = bs.toStringUtf8();
            if (bs.isValidUtf8()) {
                this.gameVersion_ = s;
            }
            return s;
        }

        public ByteString getGameVersionBytes() {
            Object ref = this.gameVersion_;
            if (!(ref instanceof String)) {
                return (ByteString) ref;
            }
            ByteString b = ByteString.copyFromUtf8((String) ref);
            this.gameVersion_ = b;
            return b;
        }

        public boolean hasServerCurplayer() {
            return (this.bitField0_ & 64) == 64;
        }

        public int getServerCurplayer() {
            return this.serverCurplayer_;
        }

        public boolean hasServerMaxplayer() {
            return (this.bitField0_ & 128) == 128;
        }

        public int getServerMaxplayer() {
            return this.serverMaxplayer_;
        }

        public boolean hasGameMode() {
            return (this.bitField0_ & 256) == 256;
        }

        public int getGameMode() {
            return this.gameMode_;
        }

        public boolean hasGameType() {
            return (this.bitField0_ & 512) == 512;
        }

        public int getGameType() {
            return this.gameType_;
        }

        public boolean hasGameSize() {
            return (this.bitField0_ & 1024) == 1024;
        }

        public int getGameSize() {
            return this.gameSize_;
        }

        public boolean hasAdminId() {
            return (this.bitField0_ & 2048) == 2048;
        }

        public long getAdminId() {
            return this.adminId_;
        }

        private void initFields() {
            this.serverIp_ = "";
            this.serverPort_ = 0;
            this.serverName_ = "";
            this.adminName_ = "";
            this.adminAvatar_ = "";
            this.gameVersion_ = "";
            this.serverCurplayer_ = 0;
            this.serverMaxplayer_ = 0;
            this.gameMode_ = 0;
            this.gameType_ = 0;
            this.gameSize_ = 0;
            this.adminId_ = 0;
        }

        public final boolean isInitialized() {
            byte isInitialized = this.memoizedIsInitialized;
            if (isInitialized == (byte) 1) {
                return true;
            }
            if (isInitialized == (byte) 0) {
                return false;
            }
            if (!hasServerIp()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            } else if (!hasServerPort()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            } else if (!hasServerName()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            } else if (!hasAdminName()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            } else if (!hasAdminAvatar()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            } else if (!hasGameVersion()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            } else if (!hasServerCurplayer()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            } else if (!hasServerMaxplayer()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            } else if (!hasGameMode()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            } else if (!hasGameType()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            } else if (!hasGameSize()) {
                this.memoizedIsInitialized = (byte) 0;
                return false;
            } else if (hasAdminId()) {
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
                output.writeBytes(1, getServerIpBytes());
            }
            if ((this.bitField0_ & 2) == 2) {
                output.writeInt64(2, this.serverPort_);
            }
            if ((this.bitField0_ & 4) == 4) {
                output.writeBytes(3, getServerNameBytes());
            }
            if ((this.bitField0_ & 8) == 8) {
                output.writeBytes(4, getAdminNameBytes());
            }
            if ((this.bitField0_ & 16) == 16) {
                output.writeBytes(5, getAdminAvatarBytes());
            }
            if ((this.bitField0_ & 32) == 32) {
                output.writeBytes(6, getGameVersionBytes());
            }
            if ((this.bitField0_ & 64) == 64) {
                output.writeInt32(7, this.serverCurplayer_);
            }
            if ((this.bitField0_ & 128) == 128) {
                output.writeInt32(8, this.serverMaxplayer_);
            }
            if ((this.bitField0_ & 256) == 256) {
                output.writeInt32(9, this.gameMode_);
            }
            if ((this.bitField0_ & 512) == 512) {
                output.writeInt32(10, this.gameType_);
            }
            if ((this.bitField0_ & 1024) == 1024) {
                output.writeInt32(11, this.gameSize_);
            }
            if ((this.bitField0_ & 2048) == 2048) {
                output.writeInt64(12, this.adminId_);
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
                size = 0 + CodedOutputStream.computeBytesSize(1, getServerIpBytes());
            }
            if ((this.bitField0_ & 2) == 2) {
                size += CodedOutputStream.computeInt64Size(2, this.serverPort_);
            }
            if ((this.bitField0_ & 4) == 4) {
                size += CodedOutputStream.computeBytesSize(3, getServerNameBytes());
            }
            if ((this.bitField0_ & 8) == 8) {
                size += CodedOutputStream.computeBytesSize(4, getAdminNameBytes());
            }
            if ((this.bitField0_ & 16) == 16) {
                size += CodedOutputStream.computeBytesSize(5, getAdminAvatarBytes());
            }
            if ((this.bitField0_ & 32) == 32) {
                size += CodedOutputStream.computeBytesSize(6, getGameVersionBytes());
            }
            if ((this.bitField0_ & 64) == 64) {
                size += CodedOutputStream.computeInt32Size(7, this.serverCurplayer_);
            }
            if ((this.bitField0_ & 128) == 128) {
                size += CodedOutputStream.computeInt32Size(8, this.serverMaxplayer_);
            }
            if ((this.bitField0_ & 256) == 256) {
                size += CodedOutputStream.computeInt32Size(9, this.gameMode_);
            }
            if ((this.bitField0_ & 512) == 512) {
                size += CodedOutputStream.computeInt32Size(10, this.gameType_);
            }
            if ((this.bitField0_ & 1024) == 1024) {
                size += CodedOutputStream.computeInt32Size(11, this.gameSize_);
            }
            if ((this.bitField0_ & 2048) == 2048) {
                size += CodedOutputStream.computeInt64Size(12, this.adminId_);
            }
            size += getUnknownFields().getSerializedSize();
            this.memoizedSerializedSize = size;
            return size;
        }

        protected Object writeReplace() throws ObjectStreamException {
            return super.writeReplace();
        }

        public static ServerInfo parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ServerInfo) PARSER.parseFrom(data);
        }

        public static ServerInfo parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ServerInfo) PARSER.parseFrom(data, extensionRegistry);
        }

        public static ServerInfo parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ServerInfo) PARSER.parseFrom(data);
        }

        public static ServerInfo parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ServerInfo) PARSER.parseFrom(data, extensionRegistry);
        }

        public static ServerInfo parseFrom(InputStream input) throws IOException {
            return (ServerInfo) PARSER.parseFrom(input);
        }

        public static ServerInfo parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServerInfo) PARSER.parseFrom(input, extensionRegistry);
        }

        public static ServerInfo parseDelimitedFrom(InputStream input) throws IOException {
            return (ServerInfo) PARSER.parseDelimitedFrom(input);
        }

        public static ServerInfo parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServerInfo) PARSER.parseDelimitedFrom(input, extensionRegistry);
        }

        public static ServerInfo parseFrom(CodedInputStream input) throws IOException {
            return (ServerInfo) PARSER.parseFrom(input);
        }

        public static ServerInfo parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ServerInfo) PARSER.parseFrom(input, extensionRegistry);
        }

        public static a newBuilder() {
            return a.BC();
        }

        public a newBuilderForType() {
            return newBuilder();
        }

        public static a newBuilder(ServerInfo prototype) {
            return newBuilder().b(prototype);
        }

        public a toBuilder() {
            return newBuilder(this);
        }

        protected a newBuilderForType(BuilderParent parent) {
            return new a(parent);
        }
    }

    private DTControlProto() {
    }

    public static void registerAllExtensions(ExtensionRegistry registry) {
    }

    public static FileDescriptor getDescriptor() {
        return descriptor;
    }

    static {
        FileDescriptor.internalBuildGeneratedFileFrom(new String[]{"\n\u0014DTControlProto.proto\u0012\rDTControlInfo\"\u0002\n\nServerInfo\u0012\u0011\n\tserver_ip\u0018\u0001 \u0002(\t\u0012\u0013\n\u000bserver_port\u0018\u0002 \u0002(\u0003\u0012\u0013\n\u000bserver_name\u0018\u0003 \u0002(\t\u0012\u0012\n\nadmin_name\u0018\u0004 \u0002(\t\u0012\u0014\n\fadmin_avatar\u0018\u0005 \u0002(\t\u0012\u0014\n\fgame_version\u0018\u0006 \u0002(\t\u0012\u0018\n\u0010server_curplayer\u0018\u0007 \u0002(\u0005\u0012\u0018\n\u0010server_maxplayer\u0018\b \u0002(\u0005\u0012\u0011\n\tgame_mode\u0018\t \u0002(\u0005\u0012\u0011\n\tgame_type\u0018\n \u0002(\u0005\u0012\u0011\n\tgame_size\u0018\u000b \u0002(\u0005\u0012\u0010\n\badmin_id\u0018\f \u0002(\u0003\"p\n\rDTControlInfo\u0012\u0012\n\nproto_type\u0018\u0001 \u0002(\u0005\u0012\u0016\n\u000eresult_message\u0018\u0002 \u0002(\t\u00123\n\u0010server_info_list\u0018\u0003 \u0003(\u000b2\u0019.DTCont", "rolInfo.ServerInfoB\u001c\n\u001acom.huluxia.mconline.proto"}, new FileDescriptor[0], new InternalDescriptorAssigner() {
            public ExtensionRegistry assignDescriptors(FileDescriptor root) {
                DTControlProto.descriptor = root;
                return null;
            }
        });
    }
}
