package com.huluxia.image.pipeline.producers;

import android.util.Pair;
import com.huluxia.framework.base.utils.Preconditions;
import com.huluxia.framework.base.utils.Sets;
import com.huluxia.framework.base.utils.VisibleForTesting;
import com.huluxia.image.base.imagepipeline.common.Priority;
import com.huluxia.image.pipeline.producers.af$a.a;
import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

@VisibleForTesting
/* compiled from: MultiplexProducer */
class af$a {
    private final K KM;
    private final CopyOnWriteArraySet<Pair<j<T>, ao>> KN = Sets.newCopyOnWriteArraySet();
    @GuardedBy("Multiplexer.this")
    @Nullable
    private T KO;
    @GuardedBy("Multiplexer.this")
    private float KP;
    @GuardedBy("Multiplexer.this")
    @Nullable
    private d KQ;
    @GuardedBy("Multiplexer.this")
    @Nullable
    private a KR;
    final /* synthetic */ af KS;

    public af$a(af this$0, K key) {
        this.KS = this$0;
        this.KM = key;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean f(com.huluxia.image.pipeline.producers.j<T> r10, com.huluxia.image.pipeline.producers.ao r11) {
        /*
        r9 = this;
        r6 = 0;
        r0 = android.util.Pair.create(r10, r11);
        monitor-enter(r9);
        r7 = r9.KS;	 Catch:{ all -> 0x0050 }
        r8 = r9.KM;	 Catch:{ all -> 0x0050 }
        r7 = com.huluxia.image.pipeline.producers.af.a(r7, r8);	 Catch:{ all -> 0x0050 }
        if (r7 == r9) goto L_0x0012;
    L_0x0010:
        monitor-exit(r9);	 Catch:{ all -> 0x0050 }
    L_0x0011:
        return r6;
    L_0x0012:
        r6 = r9.KN;	 Catch:{ all -> 0x0050 }
        r6.add(r0);	 Catch:{ all -> 0x0050 }
        r4 = r9.pa();	 Catch:{ all -> 0x0050 }
        r5 = r9.pf();	 Catch:{ all -> 0x0050 }
        r1 = r9.pd();	 Catch:{ all -> 0x0050 }
        r2 = r9.KO;	 Catch:{ all -> 0x0050 }
        r3 = r9.KP;	 Catch:{ all -> 0x0050 }
        monitor-exit(r9);	 Catch:{ all -> 0x0050 }
        com.huluxia.image.pipeline.producers.d.r(r4);
        com.huluxia.image.pipeline.producers.d.t(r5);
        com.huluxia.image.pipeline.producers.d.s(r1);
        monitor-enter(r0);
        monitor-enter(r9);	 Catch:{ all -> 0x005f }
        r6 = r9.KO;	 Catch:{ all -> 0x005c }
        if (r2 == r6) goto L_0x0053;
    L_0x0037:
        r2 = 0;
    L_0x0038:
        monitor-exit(r9);	 Catch:{ all -> 0x005c }
        if (r2 == 0) goto L_0x004a;
    L_0x003b:
        r6 = 0;
        r6 = (r3 > r6 ? 1 : (r3 == r6 ? 0 : -1));
        if (r6 <= 0) goto L_0x0043;
    L_0x0040:
        r10.onProgressUpdate(r3);	 Catch:{ all -> 0x005f }
    L_0x0043:
        r6 = 0;
        r10.e(r2, r6);	 Catch:{ all -> 0x005f }
        r9.e(r2);	 Catch:{ all -> 0x005f }
    L_0x004a:
        monitor-exit(r0);	 Catch:{ all -> 0x005f }
        r9.a(r0, r11);
        r6 = 1;
        goto L_0x0011;
    L_0x0050:
        r6 = move-exception;
        monitor-exit(r9);	 Catch:{ all -> 0x0050 }
        throw r6;
    L_0x0053:
        if (r2 == 0) goto L_0x0038;
    L_0x0055:
        r6 = r9.KS;	 Catch:{ all -> 0x005c }
        r2 = r6.d(r2);	 Catch:{ all -> 0x005c }
        goto L_0x0038;
    L_0x005c:
        r6 = move-exception;
        monitor-exit(r9);	 Catch:{ all -> 0x005c }
        throw r6;	 Catch:{ all -> 0x005f }
    L_0x005f:
        r6 = move-exception;
        monitor-exit(r0);	 Catch:{ all -> 0x005f }
        throw r6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huluxia.image.pipeline.producers.af$a.f(com.huluxia.image.pipeline.producers.j, com.huluxia.image.pipeline.producers.ao):boolean");
    }

    private void a(Pair<j<T>, ao> consumerContextPair, ao producerContext) {
        producerContext.a(new 1(this, consumerContextPair));
    }

    private void oZ() {
        boolean z = true;
        synchronized (this) {
            Preconditions.checkArgument(this.KQ == null);
            if (this.KR != null) {
                z = false;
            }
            Preconditions.checkArgument(z);
            if (this.KN.isEmpty()) {
                af.a(this.KS, this.KM, this);
                return;
            }
            ao producerContext = ((Pair) this.KN.iterator().next()).second;
            this.KQ = new d(producerContext.oA(), producerContext.getId(), producerContext.oB(), producerContext.gk(), producerContext.oC(), pc(), pe(), pg());
            this.KR = new a(this, null);
            d multiplexProducerContext = this.KQ;
            a forwardingConsumer = this.KR;
            af.a(this.KS).b(forwardingConsumer, multiplexProducerContext);
        }
    }

    @Nullable
    private synchronized List<ap> pa() {
        List<ap> list;
        if (this.KQ == null) {
            list = null;
        } else {
            list = this.KQ.ao(pc());
        }
        return list;
    }

    private synchronized boolean pc() {
        boolean z;
        Iterator it = this.KN.iterator();
        while (it.hasNext()) {
            if (!((ao) ((Pair) it.next()).second).oD()) {
                z = false;
                break;
            }
        }
        z = true;
        return z;
    }

    @Nullable
    private synchronized List<ap> pd() {
        List<ap> list;
        if (this.KQ == null) {
            list = null;
        } else {
            list = this.KQ.ap(pe());
        }
        return list;
    }

    private synchronized boolean pe() {
        boolean z;
        Iterator it = this.KN.iterator();
        while (it.hasNext()) {
            if (((ao) ((Pair) it.next()).second).oF()) {
                z = true;
                break;
            }
        }
        z = false;
        return z;
    }

    @Nullable
    private synchronized List<ap> pf() {
        List<ap> list;
        if (this.KQ == null) {
            list = null;
        } else {
            list = this.KQ.a(pg());
        }
        return list;
    }

    private synchronized Priority pg() {
        Priority priority;
        priority = Priority.LOW;
        Iterator it = this.KN.iterator();
        while (it.hasNext()) {
            priority = Priority.getHigherPriority(priority, ((ao) ((Pair) it.next()).second).oE());
        }
        return priority;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(com.huluxia.image.pipeline.producers.af$a.a r5, java.lang.Throwable r6) {
        /*
        r4 = this;
        monitor-enter(r4);
        r2 = r4.KR;	 Catch:{ all -> 0x003b }
        if (r2 == r5) goto L_0x0007;
    L_0x0005:
        monitor-exit(r4);	 Catch:{ all -> 0x003b }
    L_0x0006:
        return;
    L_0x0007:
        r2 = r4.KN;	 Catch:{ all -> 0x003b }
        r0 = r2.iterator();	 Catch:{ all -> 0x003b }
        r2 = r4.KN;	 Catch:{ all -> 0x003b }
        r2.clear();	 Catch:{ all -> 0x003b }
        r2 = r4.KS;	 Catch:{ all -> 0x003b }
        r3 = r4.KM;	 Catch:{ all -> 0x003b }
        com.huluxia.image.pipeline.producers.af.a(r2, r3, r4);	 Catch:{ all -> 0x003b }
        r2 = r4.KO;	 Catch:{ all -> 0x003b }
        r4.e(r2);	 Catch:{ all -> 0x003b }
        r2 = 0;
        r4.KO = r2;	 Catch:{ all -> 0x003b }
        monitor-exit(r4);	 Catch:{ all -> 0x003b }
    L_0x0022:
        r2 = r0.hasNext();
        if (r2 == 0) goto L_0x0006;
    L_0x0028:
        r1 = r0.next();
        r1 = (android.util.Pair) r1;
        monitor-enter(r1);
        r2 = r1.first;	 Catch:{ all -> 0x0038 }
        r2 = (com.huluxia.image.pipeline.producers.j) r2;	 Catch:{ all -> 0x0038 }
        r2.j(r6);	 Catch:{ all -> 0x0038 }
        monitor-exit(r1);	 Catch:{ all -> 0x0038 }
        goto L_0x0022;
    L_0x0038:
        r2 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0038 }
        throw r2;
    L_0x003b:
        r2 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x003b }
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huluxia.image.pipeline.producers.af$a.a(com.huluxia.image.pipeline.producers.af$a$a, java.lang.Throwable):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(com.huluxia.image.pipeline.producers.af$a.a r5, T r6, boolean r7) {
        /*
        r4 = this;
        monitor-enter(r4);
        r2 = r4.KR;	 Catch:{ all -> 0x0046 }
        if (r2 == r5) goto L_0x0007;
    L_0x0005:
        monitor-exit(r4);	 Catch:{ all -> 0x0046 }
    L_0x0006:
        return;
    L_0x0007:
        r2 = r4.KO;	 Catch:{ all -> 0x0046 }
        r4.e(r2);	 Catch:{ all -> 0x0046 }
        r2 = 0;
        r4.KO = r2;	 Catch:{ all -> 0x0046 }
        r2 = r4.KN;	 Catch:{ all -> 0x0046 }
        r0 = r2.iterator();	 Catch:{ all -> 0x0046 }
        if (r7 != 0) goto L_0x0039;
    L_0x0017:
        r2 = r4.KS;	 Catch:{ all -> 0x0046 }
        r2 = r2.d(r6);	 Catch:{ all -> 0x0046 }
        r4.KO = r2;	 Catch:{ all -> 0x0046 }
    L_0x001f:
        monitor-exit(r4);	 Catch:{ all -> 0x0046 }
    L_0x0020:
        r2 = r0.hasNext();
        if (r2 == 0) goto L_0x0006;
    L_0x0026:
        r1 = r0.next();
        r1 = (android.util.Pair) r1;
        monitor-enter(r1);
        r2 = r1.first;	 Catch:{ all -> 0x0036 }
        r2 = (com.huluxia.image.pipeline.producers.j) r2;	 Catch:{ all -> 0x0036 }
        r2.e(r6, r7);	 Catch:{ all -> 0x0036 }
        monitor-exit(r1);	 Catch:{ all -> 0x0036 }
        goto L_0x0020;
    L_0x0036:
        r2 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0036 }
        throw r2;
    L_0x0039:
        r2 = r4.KN;	 Catch:{ all -> 0x0046 }
        r2.clear();	 Catch:{ all -> 0x0046 }
        r2 = r4.KS;	 Catch:{ all -> 0x0046 }
        r3 = r4.KM;	 Catch:{ all -> 0x0046 }
        com.huluxia.image.pipeline.producers.af.a(r2, r3, r4);	 Catch:{ all -> 0x0046 }
        goto L_0x001f;
    L_0x0046:
        r2 = move-exception;
        monitor-exit(r4);	 Catch:{ all -> 0x0046 }
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huluxia.image.pipeline.producers.af$a.a(com.huluxia.image.pipeline.producers.af$a$a, java.io.Closeable, boolean):void");
    }

    public void a(a forwardingConsumer) {
        synchronized (this) {
            if (this.KR != forwardingConsumer) {
                return;
            }
            this.KR = null;
            this.KQ = null;
            e(this.KO);
            this.KO = null;
            oZ();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(com.huluxia.image.pipeline.producers.af$a.a r4, float r5) {
        /*
        r3 = this;
        monitor-enter(r3);
        r2 = r3.KR;	 Catch:{ all -> 0x0029 }
        if (r2 == r4) goto L_0x0007;
    L_0x0005:
        monitor-exit(r3);	 Catch:{ all -> 0x0029 }
    L_0x0006:
        return;
    L_0x0007:
        r3.KP = r5;	 Catch:{ all -> 0x0029 }
        r2 = r3.KN;	 Catch:{ all -> 0x0029 }
        r0 = r2.iterator();	 Catch:{ all -> 0x0029 }
        monitor-exit(r3);	 Catch:{ all -> 0x0029 }
    L_0x0010:
        r2 = r0.hasNext();
        if (r2 == 0) goto L_0x0006;
    L_0x0016:
        r1 = r0.next();
        r1 = (android.util.Pair) r1;
        monitor-enter(r1);
        r2 = r1.first;	 Catch:{ all -> 0x0026 }
        r2 = (com.huluxia.image.pipeline.producers.j) r2;	 Catch:{ all -> 0x0026 }
        r2.onProgressUpdate(r5);	 Catch:{ all -> 0x0026 }
        monitor-exit(r1);	 Catch:{ all -> 0x0026 }
        goto L_0x0010;
    L_0x0026:
        r2 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0026 }
        throw r2;
    L_0x0029:
        r2 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0029 }
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huluxia.image.pipeline.producers.af$a.a(com.huluxia.image.pipeline.producers.af$a$a, float):void");
    }

    private void e(Closeable obj) {
        if (obj != null) {
            try {
                obj.close();
            } catch (IOException ioe) {
                throw new RuntimeException(ioe);
            }
        }
    }
}
