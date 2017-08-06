package io.netty.handler.codec.http2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http2.Http2Connection.Endpoint;
import io.netty.handler.codec.http2.Http2Connection.Listener;
import io.netty.handler.codec.http2.Http2Connection.PropertyKey;
import io.netty.handler.codec.http2.Http2Stream.State;
import io.netty.util.collection.IntCollections;
import io.netty.util.collection.IntObjectHashMap;
import io.netty.util.collection.IntObjectMap;
import io.netty.util.collection.IntObjectMap.PrimitiveEntry;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;
import io.netty.util.concurrent.UnaryPromiseNotifier;
import io.netty.util.internal.EmptyArrays;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SystemPropertyUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class DefaultHttp2Connection implements Http2Connection {
    private static final int INITIAL_CHILDREN_MAP_SIZE = Math.max(1, SystemPropertyUtil.getInt("io.netty.http2.childrenMapSize", 4));
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultHttp2Connection.class);
    final ActiveStreams activeStreams = new ActiveStreams(this.listeners);
    Promise<Void> closePromise;
    final ConnectionStream connectionStream = new ConnectionStream();
    final List<Listener> listeners = new ArrayList(4);
    final DefaultEndpoint<Http2LocalFlowController> localEndpoint;
    final PropertyKeyRegistry propertyKeyRegistry = new PropertyKeyRegistry();
    final DefaultEndpoint<Http2RemoteFlowController> remoteEndpoint;
    final IntObjectMap<Http2Stream> streamMap = new IntObjectHashMap();

    interface Event {
        void process();
    }

    private final class ActiveStreams {
        private final List<Listener> listeners;
        private final Queue<Event> pendingEvents = new ArrayDeque(4);
        private int pendingIterations;
        private final Set<Http2Stream> streams = new LinkedHashSet();

        public ActiveStreams(List<Listener> listeners) {
            this.listeners = listeners;
        }

        public int size() {
            return this.streams.size();
        }

        public void activate(final DefaultStream stream) {
            if (allowModifications()) {
                addToActiveStreams(stream);
            } else {
                this.pendingEvents.add(new Event() {
                    public void process() {
                        ActiveStreams.this.addToActiveStreams(stream);
                    }
                });
            }
        }

        public void deactivate(final DefaultStream stream, final Iterator<?> itr) {
            if (allowModifications() || itr != null) {
                removeFromActiveStreams(stream, itr);
            } else {
                this.pendingEvents.add(new Event() {
                    public void process() {
                        if (stream.parent() != null) {
                            ActiveStreams.this.removeFromActiveStreams(stream, itr);
                        }
                    }
                });
            }
        }

        public Http2Stream forEachActiveStream(Http2StreamVisitor visitor) throws Http2Exception {
            incrementPendingIterations();
            try {
                for (Http2Stream stream : this.streams) {
                    if (!visitor.visit(stream)) {
                        return stream;
                    }
                }
                decrementPendingIterations();
                return null;
            } finally {
                decrementPendingIterations();
            }
        }

        void addToActiveStreams(DefaultStream stream) {
            if (this.streams.add(stream)) {
                DefaultEndpoint createdBy = stream.createdBy();
                createdBy.numActiveStreams++;
                for (int i = 0; i < this.listeners.size(); i++) {
                    try {
                        ((Listener) this.listeners.get(i)).onStreamActive(stream);
                    } catch (Throwable cause) {
                        DefaultHttp2Connection.logger.error("Caught Throwable from listener onStreamActive.", cause);
                    }
                }
            }
        }

        void removeFromActiveStreams(DefaultStream stream, Iterator<?> itr) {
            if (this.streams.remove(stream)) {
                DefaultEndpoint createdBy = stream.createdBy();
                createdBy.numActiveStreams--;
                DefaultHttp2Connection.this.notifyClosed(stream);
            }
            DefaultHttp2Connection.this.removeStream(stream, itr);
        }

        boolean allowModifications() {
            return this.pendingIterations == 0;
        }

        void incrementPendingIterations() {
            this.pendingIterations++;
        }

        void decrementPendingIterations() {
            this.pendingIterations--;
            if (allowModifications()) {
                while (true) {
                    Event event = (Event) this.pendingEvents.poll();
                    if (event != null) {
                        try {
                            event.process();
                        } catch (Throwable cause) {
                            DefaultHttp2Connection.logger.error("Caught Throwable while processing pending ActiveStreams$Event.", cause);
                        }
                    } else {
                        return;
                    }
                }
            }
        }
    }

    private class DefaultStream implements Http2Stream {
        static final /* synthetic */ boolean $assertionsDisabled = (!DefaultHttp2Connection.class.desiredAssertionStatus());
        private IntObjectMap<DefaultStream> children = IntCollections.emptyMap();
        private final int id;
        private DefaultStream parent;
        private final PropertyMap properties = new PropertyMap();
        private boolean resetSent;
        private State state;
        private short weight = (short) 16;

        private class PropertyMap {
            Object[] values;

            private PropertyMap() {
                this.values = EmptyArrays.EMPTY_OBJECTS;
            }

            <V> V add(DefaultPropertyKey key, V value) {
                resizeIfNecessary(key.index);
                V prevValue = this.values[key.index];
                this.values[key.index] = value;
                return prevValue;
            }

            <V> V get(DefaultPropertyKey key) {
                if (key.index >= this.values.length) {
                    return null;
                }
                return this.values[key.index];
            }

            <V> V remove(DefaultPropertyKey key) {
                if (key.index >= this.values.length) {
                    return null;
                }
                V prevValue = this.values[key.index];
                this.values[key.index] = null;
                return prevValue;
            }

            void resizeIfNecessary(int index) {
                if (index >= this.values.length) {
                    this.values = Arrays.copyOf(this.values, DefaultHttp2Connection.this.propertyKeyRegistry.size());
                }
            }
        }

        DefaultStream(int id, State state) {
            this.id = id;
            this.state = state;
        }

        public final int id() {
            return this.id;
        }

        public final State state() {
            return this.state;
        }

        public boolean isResetSent() {
            return this.resetSent;
        }

        public Http2Stream resetSent() {
            this.resetSent = true;
            return this;
        }

        public final <V> V setProperty(PropertyKey key, V value) {
            return this.properties.add(DefaultHttp2Connection.this.verifyKey(key), value);
        }

        public final <V> V getProperty(PropertyKey key) {
            return this.properties.get(DefaultHttp2Connection.this.verifyKey(key));
        }

        public final <V> V removeProperty(PropertyKey key) {
            return this.properties.remove(DefaultHttp2Connection.this.verifyKey(key));
        }

        public final boolean isRoot() {
            return this.parent == null;
        }

        public final short weight() {
            return this.weight;
        }

        public final DefaultStream parent() {
            return this.parent;
        }

        public final boolean isDescendantOf(Http2Stream stream) {
            for (Http2Stream next = parent(); next != null; next = next.parent()) {
                if (next == stream) {
                    return true;
                }
            }
            return false;
        }

        public final boolean isLeaf() {
            return numChildren() == 0;
        }

        public final int numChildren() {
            return this.children.size();
        }

        public Http2Stream forEachChild(Http2StreamVisitor visitor) throws Http2Exception {
            for (DefaultStream stream : this.children.values()) {
                if (!visitor.visit(stream)) {
                    return stream;
                }
            }
            return null;
        }

        public Http2Stream setPriority(int parentStreamId, short weight, boolean exclusive) throws Http2Exception {
            int i = 0;
            if (weight < (short) 1 || weight > (short) 256) {
                throw new IllegalArgumentException(String.format("Invalid weight: %d.  Must be between %d and %d (inclusive).", new Object[]{Short.valueOf(weight), Short.valueOf((short) 1), Short.valueOf((short) 256)}));
            }
            DefaultStream newParent = (DefaultStream) DefaultHttp2Connection.this.stream(parentStreamId);
            if (newParent == null) {
                newParent = createdBy().createIdleStream(parentStreamId);
            } else if (this == newParent) {
                throw new IllegalArgumentException("A stream cannot depend on itself");
            }
            weight(weight);
            if (newParent != parent() || (exclusive && newParent.numChildren() != 1)) {
                List<ParentChangedEvent> events;
                if (newParent.isDescendantOf(this)) {
                    int numChildren;
                    if (exclusive) {
                        numChildren = newParent.numChildren();
                    } else {
                        numChildren = 0;
                    }
                    events = new ArrayList(numChildren + 2);
                    this.parent.takeChild(newParent, false, events);
                } else {
                    if (exclusive) {
                        i = newParent.numChildren();
                    }
                    events = new ArrayList(i + 1);
                }
                newParent.takeChild(this, exclusive, events);
                DefaultHttp2Connection.this.notifyParentChanged(events);
            }
            return this;
        }

        public Http2Stream open(boolean halfClosed) throws Http2Exception {
            this.state = DefaultHttp2Connection.activeState(this.id, this.state, isLocal(), halfClosed);
            if (createdBy().canOpenStream()) {
                activate();
                return this;
            }
            throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Maximum active streams violated for this endpoint.", new Object[0]);
        }

        void activate() {
            DefaultHttp2Connection.this.activeStreams.activate(this);
        }

        Http2Stream close(Iterator<?> itr) {
            if (this.state != State.CLOSED) {
                this.state = State.CLOSED;
                DefaultEndpoint createdBy = createdBy();
                createdBy.numStreams--;
                DefaultHttp2Connection.this.activeStreams.deactivate(this, itr);
            }
            return this;
        }

        public Http2Stream close() {
            return close(null);
        }

        public Http2Stream closeLocalSide() {
            switch (this.state) {
                case OPEN:
                    this.state = State.HALF_CLOSED_LOCAL;
                    DefaultHttp2Connection.this.notifyHalfClosed(this);
                    break;
                case HALF_CLOSED_LOCAL:
                    break;
                default:
                    close();
                    break;
            }
            return this;
        }

        public Http2Stream closeRemoteSide() {
            switch (this.state) {
                case OPEN:
                    this.state = State.HALF_CLOSED_REMOTE;
                    DefaultHttp2Connection.this.notifyHalfClosed(this);
                    break;
                case HALF_CLOSED_REMOTE:
                    break;
                default:
                    close();
                    break;
            }
            return this;
        }

        private void initChildrenIfEmpty() {
            if (this.children == IntCollections.emptyMap()) {
                initChildren();
            }
        }

        private void initChildren() {
            this.children = new IntObjectHashMap(DefaultHttp2Connection.INITIAL_CHILDREN_MAP_SIZE);
        }

        DefaultEndpoint<? extends Http2FlowController> createdBy() {
            return DefaultHttp2Connection.this.localEndpoint.isValidStreamId(this.id) ? DefaultHttp2Connection.this.localEndpoint : DefaultHttp2Connection.this.remoteEndpoint;
        }

        final boolean isLocal() {
            return DefaultHttp2Connection.this.localEndpoint.isValidStreamId(this.id);
        }

        final void weight(short weight) {
            if (weight != this.weight) {
                short oldWeight = this.weight;
                this.weight = weight;
                for (int i = 0; i < DefaultHttp2Connection.this.listeners.size(); i++) {
                    try {
                        ((Listener) DefaultHttp2Connection.this.listeners.get(i)).onWeightChanged(this, oldWeight);
                    } catch (Throwable cause) {
                        DefaultHttp2Connection.logger.error("Caught Throwable from listener onWeightChanged.", cause);
                    }
                }
            }
        }

        private IntObjectMap<DefaultStream> retain(DefaultStream streamToRetain) {
            streamToRetain = (DefaultStream) this.children.remove(streamToRetain.id());
            IntObjectMap<DefaultStream> prevChildren = this.children;
            initChildren();
            if (streamToRetain != null) {
                this.children.put(streamToRetain.id(), streamToRetain);
            }
            return prevChildren;
        }

        final void takeChild(DefaultStream child, boolean exclusive, List<ParentChangedEvent> events) {
            DefaultStream oldParent = child.parent();
            if (oldParent != this) {
                events.add(new ParentChangedEvent(child, oldParent));
                DefaultHttp2Connection.this.notifyParentChanging(child, this);
                child.parent = this;
                if (oldParent != null) {
                    oldParent.children.remove(child.id());
                }
                initChildrenIfEmpty();
                Http2Stream oldChild = (Http2Stream) this.children.put(child.id(), child);
                if (!($assertionsDisabled || oldChild == null)) {
                    throw new AssertionError("A stream with the same stream ID was already in the child map.");
                }
            }
            if (exclusive && !this.children.isEmpty()) {
                for (DefaultStream grandchild : retain(child).values()) {
                    child.takeChild(grandchild, false, events);
                }
            }
        }

        final boolean removeChild(DefaultStream child) {
            if (this.children.remove(child.id()) == null) {
                return false;
            }
            List<ParentChangedEvent> events = new ArrayList(child.numChildren() + 1);
            events.add(new ParentChangedEvent(child, child.parent()));
            DefaultHttp2Connection.this.notifyParentChanging(child, null);
            child.parent = null;
            for (DefaultStream grandchild : child.children.values()) {
                takeChild(grandchild, false, events);
            }
            DefaultHttp2Connection.this.notifyParentChanged(events);
            return true;
        }
    }

    private final class ConnectionStream extends DefaultStream {
        ConnectionStream() {
            super(0, State.IDLE);
        }

        public boolean isResetSent() {
            return false;
        }

        DefaultEndpoint<? extends Http2FlowController> createdBy() {
            return null;
        }

        public Http2Stream resetSent() {
            throw new UnsupportedOperationException();
        }

        public Http2Stream setPriority(int parentStreamId, short weight, boolean exclusive) {
            throw new UnsupportedOperationException();
        }

        public Http2Stream open(boolean halfClosed) {
            throw new UnsupportedOperationException();
        }

        public Http2Stream close() {
            throw new UnsupportedOperationException();
        }

        public Http2Stream closeLocalSide() {
            throw new UnsupportedOperationException();
        }

        public Http2Stream closeRemoteSide() {
            throw new UnsupportedOperationException();
        }
    }

    private final class DefaultEndpoint<F extends Http2FlowController> implements Endpoint<F> {
        private F flowController;
        private int lastStreamKnownByPeer = -1;
        private int maxActiveStreams;
        private int maxStreams;
        private int nextReservationStreamId;
        private int nextStreamIdToCreate;
        int numActiveStreams;
        int numStreams;
        private boolean pushToAllowed = true;
        private final boolean server;
        final /* synthetic */ DefaultHttp2Connection this$0;

        DefaultEndpoint(DefaultHttp2Connection defaultHttp2Connection, boolean server) {
            boolean z = true;
            this.this$0 = defaultHttp2Connection;
            this.server = server;
            if (server) {
                this.nextStreamIdToCreate = 2;
                this.nextReservationStreamId = 0;
            } else {
                this.nextStreamIdToCreate = 1;
                this.nextReservationStreamId = 1;
            }
            if (server) {
                z = false;
            }
            this.pushToAllowed = z;
            this.maxActiveStreams = Integer.MAX_VALUE;
            this.maxStreams = Integer.MAX_VALUE;
        }

        public int incrementAndGetNextStreamId() {
            if (this.nextReservationStreamId < 0) {
                return this.nextReservationStreamId;
            }
            int i = this.nextReservationStreamId + 2;
            this.nextReservationStreamId = i;
            return i;
        }

        private void incrementExpectedStreamId(int streamId) {
            if (streamId > this.nextReservationStreamId && this.nextReservationStreamId >= 0) {
                this.nextReservationStreamId = streamId;
            }
            this.nextStreamIdToCreate = streamId + 2;
            this.numStreams++;
        }

        public boolean isValidStreamId(int streamId) {
            if (streamId > 0) {
                if (this.server == ((streamId & 1) == 0)) {
                    return true;
                }
            }
            return false;
        }

        public boolean mayHaveCreatedStream(int streamId) {
            return isValidStreamId(streamId) && streamId <= lastStreamCreated();
        }

        public boolean canOpenStream() {
            return this.numActiveStreams < this.maxActiveStreams;
        }

        private DefaultStream createStream(int streamId, State state) throws Http2Exception {
            checkNewStreamAllowed(streamId, state);
            DefaultStream stream = new DefaultStream(streamId, state);
            incrementExpectedStreamId(streamId);
            addStream(stream);
            return stream;
        }

        public DefaultStream createIdleStream(int streamId) throws Http2Exception {
            return createStream(streamId, State.IDLE);
        }

        public DefaultStream createStream(int streamId, boolean halfClosed) throws Http2Exception {
            DefaultStream stream = createStream(streamId, DefaultHttp2Connection.activeState(streamId, State.IDLE, isLocal(), halfClosed));
            stream.activate();
            return stream;
        }

        public boolean created(Http2Stream stream) {
            return (stream instanceof DefaultStream) && ((DefaultStream) stream).createdBy() == this;
        }

        public boolean isServer() {
            return this.server;
        }

        public DefaultStream reservePushStream(int streamId, Http2Stream parent) throws Http2Exception {
            if (parent == null) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Parent stream missing", new Object[0]);
            } else if (isLocal() ? !parent.state().localSideOpen() : !parent.state().remoteSideOpen()) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Stream %d is not open for sending push promise", Integer.valueOf(parent.id()));
            } else if (opposite().allowPushTo()) {
                State state = isLocal() ? State.RESERVED_LOCAL : State.RESERVED_REMOTE;
                checkNewStreamAllowed(streamId, state);
                DefaultStream stream = new DefaultStream(streamId, state);
                incrementExpectedStreamId(streamId);
                addStream(stream);
                return stream;
            } else {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Server push not allowed to opposite endpoint.", new Object[0]);
            }
        }

        private void addStream(DefaultStream stream) {
            this.this$0.streamMap.put(stream.id(), stream);
            List<ParentChangedEvent> events = new ArrayList(1);
            this.this$0.connectionStream.takeChild(stream, false, events);
            for (int i = 0; i < this.this$0.listeners.size(); i++) {
                try {
                    ((Listener) this.this$0.listeners.get(i)).onStreamAdded(stream);
                } catch (Throwable cause) {
                    DefaultHttp2Connection.logger.error("Caught Throwable from listener onStreamAdded.", cause);
                }
            }
            this.this$0.notifyParentChanged(events);
        }

        public void allowPushTo(boolean allow) {
            if (allow && this.server) {
                throw new IllegalArgumentException("Servers do not allow push");
            }
            this.pushToAllowed = allow;
        }

        public boolean allowPushTo() {
            return this.pushToAllowed;
        }

        public int numActiveStreams() {
            return this.numActiveStreams;
        }

        public int maxActiveStreams() {
            return this.maxActiveStreams;
        }

        public int maxStreams() {
            return this.maxStreams;
        }

        public void maxStreams(int maxActiveStreams, int maxStreams) throws Http2Exception {
            if (maxStreams < maxActiveStreams) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "maxStream[%d] streams must be >= maxActiveStreams[%d]", Integer.valueOf(maxStreams), Integer.valueOf(maxActiveStreams));
            }
            this.maxStreams = maxStreams;
            this.maxActiveStreams = maxActiveStreams;
        }

        public int lastStreamCreated() {
            return this.nextStreamIdToCreate > 1 ? this.nextStreamIdToCreate - 2 : 0;
        }

        public int lastStreamKnownByPeer() {
            return this.lastStreamKnownByPeer;
        }

        private void lastStreamKnownByPeer(int lastKnownStream) {
            this.lastStreamKnownByPeer = lastKnownStream;
        }

        public F flowController() {
            return this.flowController;
        }

        public void flowController(F flowController) {
            this.flowController = (Http2FlowController) ObjectUtil.checkNotNull(flowController, "flowController");
        }

        public Endpoint<? extends Http2FlowController> opposite() {
            return isLocal() ? this.this$0.remoteEndpoint : this.this$0.localEndpoint;
        }

        private void checkNewStreamAllowed(int streamId, State state) throws Http2Exception {
            if (this.this$0.goAwayReceived() && streamId > this.this$0.localEndpoint.lastStreamKnownByPeer()) {
                throw Http2Exception.connectionError(Http2Error.PROTOCOL_ERROR, "Cannot create stream %d since this endpoint has received a_isRightVersion GOAWAY frame with last stream id %d.", Integer.valueOf(streamId), Integer.valueOf(this.this$0.localEndpoint.lastStreamKnownByPeer()));
            } else if (streamId < 0) {
                throw new Http2NoMoreStreamIdsException();
            } else if (!isValidStreamId(streamId)) {
                Http2Error http2Error = Http2Error.PROTOCOL_ERROR;
                String str = "Request stream %d is not correct for %s connection";
                Object[] objArr = new Object[2];
                objArr[0] = Integer.valueOf(streamId);
                objArr[1] = this.server ? "server" : "client";
                throw Http2Exception.connectionError(http2Error, str, objArr);
            } else if (streamId < this.nextStreamIdToCreate) {
                throw Http2Exception.closedStreamError(Http2Error.PROTOCOL_ERROR, "Request stream %d is behind the next expected stream %d", Integer.valueOf(streamId), Integer.valueOf(this.nextStreamIdToCreate));
            } else if (this.nextStreamIdToCreate <= 0) {
                throw Http2Exception.connectionError(Http2Error.REFUSED_STREAM, "Stream IDs are exhausted for this endpoint.", new Object[0]);
            } else {
                if (state.localSideOpen() || state.remoteSideOpen()) {
                    if (!canOpenStream()) {
                        throw Http2Exception.streamError(streamId, Http2Error.REFUSED_STREAM, "Maximum active streams violated for this endpoint.", new Object[0]);
                    }
                } else if (this.numStreams == this.maxStreams) {
                    throw Http2Exception.streamError(streamId, Http2Error.REFUSED_STREAM, "Maximum streams violated for this endpoint.", new Object[0]);
                }
                if (this.this$0.isClosed()) {
                    throw Http2Exception.connectionError(Http2Error.INTERNAL_ERROR, "Attempted to create stream id %d after connection was closed", Integer.valueOf(streamId));
                }
            }
        }

        private boolean isLocal() {
            return this == this.this$0.localEndpoint;
        }
    }

    final class DefaultPropertyKey implements PropertyKey {
        final int index;

        DefaultPropertyKey(int index) {
            this.index = index;
        }

        DefaultPropertyKey verifyConnection(Http2Connection connection) {
            if (connection == DefaultHttp2Connection.this) {
                return this;
            }
            throw new IllegalArgumentException("Using a_isRightVersion key that was not created by this connection");
        }
    }

    private static final class ParentChangedEvent {
        private final Http2Stream oldParent;
        private final Http2Stream stream;

        ParentChangedEvent(Http2Stream stream, Http2Stream oldParent) {
            this.stream = stream;
            this.oldParent = oldParent;
        }

        public void notifyListener(Listener l) {
            try {
                l.onPriorityTreeParentChanged(this.stream, this.oldParent);
            } catch (Throwable cause) {
                DefaultHttp2Connection.logger.error("Caught Throwable from listener onPriorityTreeParentChanged.", cause);
            }
        }
    }

    private final class PropertyKeyRegistry {
        final List<DefaultPropertyKey> keys;

        private PropertyKeyRegistry() {
            this.keys = new ArrayList(4);
        }

        DefaultPropertyKey newKey() {
            DefaultPropertyKey key = new DefaultPropertyKey(this.keys.size());
            this.keys.add(key);
            return key;
        }

        int size() {
            return this.keys.size();
        }
    }

    public DefaultHttp2Connection(boolean server) {
        this.localEndpoint = new DefaultEndpoint(this, server);
        this.remoteEndpoint = new DefaultEndpoint(this, !server);
        this.streamMap.put(this.connectionStream.id(), this.connectionStream);
    }

    final boolean isClosed() {
        return this.closePromise != null;
    }

    public Future<Void> close(Promise<Void> promise) {
        ObjectUtil.checkNotNull(promise, "promise");
        if (this.closePromise == null) {
            this.closePromise = promise;
        } else if (this.closePromise != promise) {
            if ((promise instanceof ChannelPromise) && ((ChannelPromise) this.closePromise).isVoid()) {
                this.closePromise = promise;
            } else {
                this.closePromise.addListener(new UnaryPromiseNotifier(promise));
            }
        }
        if (isStreamMapEmpty()) {
            promise.trySuccess(null);
            return promise;
        }
        Iterator<PrimitiveEntry<Http2Stream>> itr = this.streamMap.entries().iterator();
        if (this.activeStreams.allowModifications()) {
            this.activeStreams.incrementPendingIterations();
            while (itr.hasNext()) {
                try {
                    DefaultStream stream = (DefaultStream) ((PrimitiveEntry) itr.next()).value();
                    if (stream.id() != 0) {
                        stream.close(itr);
                    }
                } finally {
                    this.activeStreams.decrementPendingIterations();
                }
            }
        } else {
            while (itr.hasNext()) {
                Http2Stream stream2 = (Http2Stream) ((PrimitiveEntry) itr.next()).value();
                if (stream2.id() != 0) {
                    stream2.close();
                }
            }
        }
        return this.closePromise;
    }

    public void addListener(Listener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        this.listeners.remove(listener);
    }

    public boolean isServer() {
        return this.localEndpoint.isServer();
    }

    public Http2Stream connectionStream() {
        return this.connectionStream;
    }

    public Http2Stream stream(int streamId) {
        return (Http2Stream) this.streamMap.get(streamId);
    }

    public boolean streamMayHaveExisted(int streamId) {
        return this.remoteEndpoint.mayHaveCreatedStream(streamId) || this.localEndpoint.mayHaveCreatedStream(streamId);
    }

    public int numActiveStreams() {
        return this.activeStreams.size();
    }

    public Http2Stream forEachActiveStream(Http2StreamVisitor visitor) throws Http2Exception {
        return this.activeStreams.forEachActiveStream(visitor);
    }

    public Endpoint<Http2LocalFlowController> local() {
        return this.localEndpoint;
    }

    public Endpoint<Http2RemoteFlowController> remote() {
        return this.remoteEndpoint;
    }

    public boolean goAwayReceived() {
        return this.localEndpoint.lastStreamKnownByPeer >= 0;
    }

    public void goAwayReceived(final int lastKnownStream, long errorCode, ByteBuf debugData) {
        this.localEndpoint.lastStreamKnownByPeer(lastKnownStream);
        for (int i = 0; i < this.listeners.size(); i++) {
            try {
                ((Listener) this.listeners.get(i)).onGoAwayReceived(lastKnownStream, errorCode, debugData);
            } catch (Throwable cause) {
                logger.error("Caught Throwable from listener onGoAwayReceived.", cause);
            }
        }
        try {
            forEachActiveStream(new Http2StreamVisitor() {
                public boolean visit(Http2Stream stream) {
                    if (stream.id() > lastKnownStream && DefaultHttp2Connection.this.localEndpoint.isValidStreamId(stream.id())) {
                        stream.close();
                    }
                    return true;
                }
            });
        } catch (Http2Exception e) {
            PlatformDependent.throwException(e);
        }
    }

    public boolean goAwaySent() {
        return this.remoteEndpoint.lastStreamKnownByPeer >= 0;
    }

    public void goAwaySent(final int lastKnownStream, long errorCode, ByteBuf debugData) {
        this.remoteEndpoint.lastStreamKnownByPeer(lastKnownStream);
        for (int i = 0; i < this.listeners.size(); i++) {
            try {
                ((Listener) this.listeners.get(i)).onGoAwaySent(lastKnownStream, errorCode, debugData);
            } catch (Throwable cause) {
                logger.error("Caught Throwable from listener onGoAwaySent.", cause);
            }
        }
        try {
            forEachActiveStream(new Http2StreamVisitor() {
                public boolean visit(Http2Stream stream) {
                    if (stream.id() > lastKnownStream && DefaultHttp2Connection.this.remoteEndpoint.isValidStreamId(stream.id())) {
                        stream.close();
                    }
                    return true;
                }
            });
        } catch (Http2Exception e) {
            PlatformDependent.throwException(e);
        }
    }

    private boolean isStreamMapEmpty() {
        return this.streamMap.size() == 1;
    }

    void removeStream(DefaultStream stream, Iterator<?> itr) {
        if (stream.parent().removeChild(stream)) {
            if (itr == null) {
                this.streamMap.remove(stream.id());
            } else {
                itr.remove();
            }
            for (int i = 0; i < this.listeners.size(); i++) {
                try {
                    ((Listener) this.listeners.get(i)).onStreamRemoved(stream);
                } catch (Throwable cause) {
                    logger.error("Caught Throwable from listener onStreamRemoved.", cause);
                }
            }
            if (this.closePromise != null && isStreamMapEmpty()) {
                this.closePromise.trySuccess(null);
            }
        }
    }

    static State activeState(int streamId, State initialState, boolean isLocal, boolean halfClosed) throws Http2Exception {
        switch (initialState) {
            case IDLE:
                if (halfClosed) {
                    return isLocal ? State.HALF_CLOSED_LOCAL : State.HALF_CLOSED_REMOTE;
                } else {
                    return State.OPEN;
                }
            case RESERVED_LOCAL:
                return State.HALF_CLOSED_REMOTE;
            case RESERVED_REMOTE:
                return State.HALF_CLOSED_LOCAL;
            default:
                throw Http2Exception.streamError(streamId, Http2Error.PROTOCOL_ERROR, "Attempting to open a_isRightVersion stream in an invalid state: " + initialState, new Object[0]);
        }
    }

    void notifyHalfClosed(Http2Stream stream) {
        for (int i = 0; i < this.listeners.size(); i++) {
            try {
                ((Listener) this.listeners.get(i)).onStreamHalfClosed(stream);
            } catch (Throwable cause) {
                logger.error("Caught Throwable from listener onStreamHalfClosed.", cause);
            }
        }
    }

    void notifyClosed(Http2Stream stream) {
        for (int i = 0; i < this.listeners.size(); i++) {
            try {
                ((Listener) this.listeners.get(i)).onStreamClosed(stream);
            } catch (Throwable cause) {
                logger.error("Caught Throwable from listener onStreamClosed.", cause);
            }
        }
    }

    public PropertyKey newKey() {
        return this.propertyKeyRegistry.newKey();
    }

    final DefaultPropertyKey verifyKey(PropertyKey key) {
        return ((DefaultPropertyKey) ObjectUtil.checkNotNull((DefaultPropertyKey) key, "key")).verifyConnection(this);
    }

    private void notifyParentChanged(List<ParentChangedEvent> events) {
        for (int i = 0; i < events.size(); i++) {
            ParentChangedEvent event = (ParentChangedEvent) events.get(i);
            for (int j = 0; j < this.listeners.size(); j++) {
                event.notifyListener((Listener) this.listeners.get(j));
            }
        }
    }

    private void notifyParentChanging(Http2Stream stream, Http2Stream newParent) {
        for (int i = 0; i < this.listeners.size(); i++) {
            try {
                ((Listener) this.listeners.get(i)).onPriorityTreeParentChanging(stream, newParent);
            } catch (Throwable cause) {
                logger.error("Caught Throwable from listener onPriorityTreeParentChanging.", cause);
            }
        }
    }
}
