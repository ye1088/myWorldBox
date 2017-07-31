package io.netty.handler.traffic;

import java.util.AbstractCollection;
import java.util.Iterator;

class GlobalChannelTrafficShapingHandler$1 extends AbstractCollection<TrafficCounter> {
    final /* synthetic */ GlobalChannelTrafficShapingHandler this$0;

    GlobalChannelTrafficShapingHandler$1(GlobalChannelTrafficShapingHandler globalChannelTrafficShapingHandler) {
        this.this$0 = globalChannelTrafficShapingHandler;
    }

    public Iterator<TrafficCounter> iterator() {
        return new Iterator<TrafficCounter>() {
            final Iterator<GlobalChannelTrafficShapingHandler$PerChannel> iter = GlobalChannelTrafficShapingHandler$1.this.this$0.channelQueues.values().iterator();

            public boolean hasNext() {
                return this.iter.hasNext();
            }

            public TrafficCounter next() {
                return ((GlobalChannelTrafficShapingHandler$PerChannel) this.iter.next()).channelTrafficCounter;
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public int size() {
        return this.this$0.channelQueues.size();
    }
}
