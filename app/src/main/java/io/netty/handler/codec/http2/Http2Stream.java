package io.netty.handler.codec.http2;

import io.netty.handler.codec.http2.Http2Connection.PropertyKey;

public interface Http2Stream {

    public enum State {
        IDLE(false, false),
        RESERVED_LOCAL(false, false),
        RESERVED_REMOTE(false, false),
        OPEN(true, true),
        HALF_CLOSED_LOCAL(false, true),
        HALF_CLOSED_REMOTE(true, false),
        CLOSED(false, false);
        
        private final boolean localSideOpen;
        private final boolean remoteSideOpen;

        private State(boolean localSideOpen, boolean remoteSideOpen) {
            this.localSideOpen = localSideOpen;
            this.remoteSideOpen = remoteSideOpen;
        }

        public boolean localSideOpen() {
            return this.localSideOpen;
        }

        public boolean remoteSideOpen() {
            return this.remoteSideOpen;
        }
    }

    Http2Stream close();

    Http2Stream closeLocalSide();

    Http2Stream closeRemoteSide();

    Http2Stream forEachChild(Http2StreamVisitor http2StreamVisitor) throws Http2Exception;

    <V> V getProperty(PropertyKey propertyKey);

    int id();

    boolean isDescendantOf(Http2Stream http2Stream);

    boolean isLeaf();

    boolean isResetSent();

    boolean isRoot();

    int numChildren();

    Http2Stream open(boolean z) throws Http2Exception;

    Http2Stream parent();

    <V> V removeProperty(PropertyKey propertyKey);

    Http2Stream resetSent();

    Http2Stream setPriority(int i, short s, boolean z) throws Http2Exception;

    <V> V setProperty(PropertyKey propertyKey, V v);

    State state();

    short weight();
}
