package io.netty.buffer;

import io.netty.util.concurrent.FastThreadLocal;

final class PooledByteBufAllocator$PoolThreadLocalCache extends FastThreadLocal<PoolThreadCache> {
    final /* synthetic */ PooledByteBufAllocator this$0;

    PooledByteBufAllocator$PoolThreadLocalCache(PooledByteBufAllocator pooledByteBufAllocator) {
        this.this$0 = pooledByteBufAllocator;
    }

    protected synchronized PoolThreadCache initialValue() {
        return new PoolThreadCache(leastUsedArena(PooledByteBufAllocator.access$000(this.this$0)), leastUsedArena(PooledByteBufAllocator.access$100(this.this$0)), PooledByteBufAllocator.access$200(this.this$0), PooledByteBufAllocator.access$300(this.this$0), PooledByteBufAllocator.access$400(this.this$0), PooledByteBufAllocator.access$500(), PooledByteBufAllocator.access$600());
    }

    protected void onRemoval(PoolThreadCache threadCache) {
        threadCache.free();
    }

    private <T> PoolArena<T> leastUsedArena(PoolArena<T>[] arenas) {
        if (arenas == null || arenas.length == 0) {
            return null;
        }
        PoolArena<T> minArena = arenas[0];
        for (int i = 1; i < arenas.length; i++) {
            PoolArena<T> arena = arenas[i];
            if (arena.numThreadCaches.get() < minArena.numThreadCaches.get()) {
                minArena = arena;
            }
        }
        return minArena;
    }
}
