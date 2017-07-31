package com.google.protobuf;

import com.google.protobuf.GeneratedMessage.Builder;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class RepeatedFieldBuilder<MType extends GeneratedMessage, BType extends Builder, IType extends MessageOrBuilder> implements BuilderParent {
    private List<SingleFieldBuilder<MType, BType, IType>> builders;
    private BuilderExternalList<MType, BType, IType> externalBuilderList;
    private MessageExternalList<MType, BType, IType> externalMessageList;
    private MessageOrBuilderExternalList<MType, BType, IType> externalMessageOrBuilderList;
    private boolean isClean;
    private boolean isMessagesListMutable;
    private List<MType> messages;
    private BuilderParent parent;

    private static class BuilderExternalList<MType extends GeneratedMessage, BType extends Builder, IType extends MessageOrBuilder> extends AbstractList<BType> implements List<BType> {
        RepeatedFieldBuilder<MType, BType, IType> builder;

        BuilderExternalList(RepeatedFieldBuilder<MType, BType, IType> builder) {
            this.builder = builder;
        }

        public int size() {
            return this.builder.getCount();
        }

        public BType get(int index) {
            return this.builder.getBuilder(index);
        }

        void incrementModCount() {
            this.modCount++;
        }
    }

    private static class MessageExternalList<MType extends GeneratedMessage, BType extends Builder, IType extends MessageOrBuilder> extends AbstractList<MType> implements List<MType> {
        RepeatedFieldBuilder<MType, BType, IType> builder;

        MessageExternalList(RepeatedFieldBuilder<MType, BType, IType> builder) {
            this.builder = builder;
        }

        public int size() {
            return this.builder.getCount();
        }

        public MType get(int index) {
            return this.builder.getMessage(index);
        }

        void incrementModCount() {
            this.modCount++;
        }
    }

    private static class MessageOrBuilderExternalList<MType extends GeneratedMessage, BType extends Builder, IType extends MessageOrBuilder> extends AbstractList<IType> implements List<IType> {
        RepeatedFieldBuilder<MType, BType, IType> builder;

        MessageOrBuilderExternalList(RepeatedFieldBuilder<MType, BType, IType> builder) {
            this.builder = builder;
        }

        public int size() {
            return this.builder.getCount();
        }

        public IType get(int index) {
            return this.builder.getMessageOrBuilder(index);
        }

        void incrementModCount() {
            this.modCount++;
        }
    }

    public RepeatedFieldBuilder(List<MType> messages, boolean isMessagesListMutable, BuilderParent parent, boolean isClean) {
        this.messages = messages;
        this.isMessagesListMutable = isMessagesListMutable;
        this.parent = parent;
        this.isClean = isClean;
    }

    public void dispose() {
        this.parent = null;
    }

    private void ensureMutableMessageList() {
        if (!this.isMessagesListMutable) {
            this.messages = new ArrayList(this.messages);
            this.isMessagesListMutable = true;
        }
    }

    private void ensureBuilders() {
        if (this.builders == null) {
            this.builders = new ArrayList(this.messages.size());
            for (int i = 0; i < this.messages.size(); i++) {
                this.builders.add(null);
            }
        }
    }

    public int getCount() {
        return this.messages.size();
    }

    public boolean isEmpty() {
        return this.messages.isEmpty();
    }

    public MType getMessage(int index) {
        return getMessage(index, false);
    }

    private MType getMessage(int index, boolean forBuild) {
        if (this.builders == null) {
            return (GeneratedMessage) this.messages.get(index);
        }
        SingleFieldBuilder<MType, BType, IType> builder = (SingleFieldBuilder) this.builders.get(index);
        if (builder == null) {
            return (GeneratedMessage) this.messages.get(index);
        }
        return forBuild ? builder.build() : builder.getMessage();
    }

    public BType getBuilder(int index) {
        ensureBuilders();
        SingleFieldBuilder<MType, BType, IType> builder = (SingleFieldBuilder) this.builders.get(index);
        if (builder == null) {
            builder = new SingleFieldBuilder((GeneratedMessage) this.messages.get(index), this, this.isClean);
            this.builders.set(index, builder);
        }
        return builder.getBuilder();
    }

    public IType getMessageOrBuilder(int index) {
        if (this.builders == null) {
            return (MessageOrBuilder) this.messages.get(index);
        }
        SingleFieldBuilder<MType, BType, IType> builder = (SingleFieldBuilder) this.builders.get(index);
        if (builder == null) {
            return (MessageOrBuilder) this.messages.get(index);
        }
        return builder.getMessageOrBuilder();
    }

    public RepeatedFieldBuilder<MType, BType, IType> setMessage(int index, MType message) {
        if (message == null) {
            throw new NullPointerException();
        }
        ensureMutableMessageList();
        this.messages.set(index, message);
        if (this.builders != null) {
            SingleFieldBuilder<MType, BType, IType> entry = (SingleFieldBuilder) this.builders.set(index, null);
            if (entry != null) {
                entry.dispose();
            }
        }
        onChanged();
        incrementModCounts();
        return this;
    }

    public RepeatedFieldBuilder<MType, BType, IType> addMessage(MType message) {
        if (message == null) {
            throw new NullPointerException();
        }
        ensureMutableMessageList();
        this.messages.add(message);
        if (this.builders != null) {
            this.builders.add(null);
        }
        onChanged();
        incrementModCounts();
        return this;
    }

    public RepeatedFieldBuilder<MType, BType, IType> addMessage(int index, MType message) {
        if (message == null) {
            throw new NullPointerException();
        }
        ensureMutableMessageList();
        this.messages.add(index, message);
        if (this.builders != null) {
            this.builders.add(index, null);
        }
        onChanged();
        incrementModCounts();
        return this;
    }

    public RepeatedFieldBuilder<MType, BType, IType> addAllMessages(Iterable<? extends MType> values) {
        Iterator i$ = values.iterator();
        while (i$.hasNext()) {
            if (((GeneratedMessage) i$.next()) == null) {
                throw new NullPointerException();
            }
        }
        if (!(values instanceof Collection)) {
            ensureMutableMessageList();
            i$ = values.iterator();
            while (i$.hasNext()) {
                addMessage((GeneratedMessage) i$.next());
            }
            onChanged();
            incrementModCounts();
        } else if (((Collection) values).size() != 0) {
            ensureMutableMessageList();
            i$ = values.iterator();
            while (i$.hasNext()) {
                addMessage((GeneratedMessage) i$.next());
            }
            onChanged();
            incrementModCounts();
        }
        return this;
    }

    public BType addBuilder(MType message) {
        ensureMutableMessageList();
        ensureBuilders();
        SingleFieldBuilder<MType, BType, IType> builder = new SingleFieldBuilder(message, this, this.isClean);
        this.messages.add(null);
        this.builders.add(builder);
        onChanged();
        incrementModCounts();
        return builder.getBuilder();
    }

    public BType addBuilder(int index, MType message) {
        ensureMutableMessageList();
        ensureBuilders();
        SingleFieldBuilder<MType, BType, IType> builder = new SingleFieldBuilder(message, this, this.isClean);
        this.messages.add(index, null);
        this.builders.add(index, builder);
        onChanged();
        incrementModCounts();
        return builder.getBuilder();
    }

    public void remove(int index) {
        ensureMutableMessageList();
        this.messages.remove(index);
        if (this.builders != null) {
            SingleFieldBuilder<MType, BType, IType> entry = (SingleFieldBuilder) this.builders.remove(index);
            if (entry != null) {
                entry.dispose();
            }
        }
        onChanged();
        incrementModCounts();
    }

    public void clear() {
        this.messages = Collections.emptyList();
        this.isMessagesListMutable = false;
        if (this.builders != null) {
            for (SingleFieldBuilder<MType, BType, IType> entry : this.builders) {
                if (entry != null) {
                    entry.dispose();
                }
            }
            this.builders = null;
        }
        onChanged();
        incrementModCounts();
    }

    public List<MType> build() {
        this.isClean = true;
        if (!this.isMessagesListMutable && this.builders == null) {
            return this.messages;
        }
        int i;
        boolean allMessagesInSync = true;
        if (!this.isMessagesListMutable) {
            for (i = 0; i < this.messages.size(); i++) {
                Object message = (Message) this.messages.get(i);
                SingleFieldBuilder<MType, BType, IType> builder = (SingleFieldBuilder) this.builders.get(i);
                if (builder != null && builder.build() != message) {
                    allMessagesInSync = false;
                    break;
                }
            }
            if (allMessagesInSync) {
                return this.messages;
            }
        }
        ensureMutableMessageList();
        for (i = 0; i < this.messages.size(); i++) {
            this.messages.set(i, getMessage(i, true));
        }
        this.messages = Collections.unmodifiableList(this.messages);
        this.isMessagesListMutable = false;
        return this.messages;
    }

    public List<MType> getMessageList() {
        if (this.externalMessageList == null) {
            this.externalMessageList = new MessageExternalList(this);
        }
        return this.externalMessageList;
    }

    public List<BType> getBuilderList() {
        if (this.externalBuilderList == null) {
            this.externalBuilderList = new BuilderExternalList(this);
        }
        return this.externalBuilderList;
    }

    public List<IType> getMessageOrBuilderList() {
        if (this.externalMessageOrBuilderList == null) {
            this.externalMessageOrBuilderList = new MessageOrBuilderExternalList(this);
        }
        return this.externalMessageOrBuilderList;
    }

    private void onChanged() {
        if (this.isClean && this.parent != null) {
            this.parent.markDirty();
            this.isClean = false;
        }
    }

    public void markDirty() {
        onChanged();
    }

    private void incrementModCounts() {
        if (this.externalMessageList != null) {
            this.externalMessageList.incrementModCount();
        }
        if (this.externalBuilderList != null) {
            this.externalBuilderList.incrementModCount();
        }
        if (this.externalMessageOrBuilderList != null) {
            this.externalMessageOrBuilderList.incrementModCount();
        }
    }
}
