package io.netty.util;

import io.netty.util.internal.StringUtil;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicBoolean;

final class ResourceLeakDetector$DefaultResourceLeak extends PhantomReference<Object> implements ResourceLeak {
    private final String creationRecord;
    private final AtomicBoolean freed;
    private final Deque<String> lastRecords;
    private ResourceLeakDetector$DefaultResourceLeak next;
    private ResourceLeakDetector$DefaultResourceLeak prev;
    private int removedRecords;
    final /* synthetic */ ResourceLeakDetector this$0;

    ResourceLeakDetector$DefaultResourceLeak(ResourceLeakDetector resourceLeakDetector, Object referent) {
        ReferenceQueue access$200;
        this.this$0 = resourceLeakDetector;
        if (referent != null) {
            access$200 = ResourceLeakDetector.access$200(resourceLeakDetector);
        } else {
            access$200 = null;
        }
        super(referent, access$200);
        this.lastRecords = new ArrayDeque();
        if (referent != null) {
            if (ResourceLeakDetector.getLevel().ordinal() >= ResourceLeakDetector$Level.ADVANCED.ordinal()) {
                this.creationRecord = ResourceLeakDetector.newRecord(null, 3);
            } else {
                this.creationRecord = null;
            }
            synchronized (ResourceLeakDetector.access$300(resourceLeakDetector)) {
                this.prev = ResourceLeakDetector.access$300(resourceLeakDetector);
                this.next = ResourceLeakDetector.access$300(resourceLeakDetector).next;
                ResourceLeakDetector.access$300(resourceLeakDetector).next.prev = this;
                ResourceLeakDetector.access$300(resourceLeakDetector).next = this;
                ResourceLeakDetector.access$408(resourceLeakDetector);
            }
            this.freed = new AtomicBoolean();
            return;
        }
        this.creationRecord = null;
        this.freed = new AtomicBoolean(true);
    }

    public void record() {
        record0(null, 3);
    }

    public void record(Object hint) {
        record0(hint, 3);
    }

    private void record0(Object hint, int recordsToSkip) {
        if (this.creationRecord != null) {
            String value = ResourceLeakDetector.newRecord(hint, recordsToSkip);
            synchronized (this.lastRecords) {
                int size = this.lastRecords.size();
                if (size == 0 || !((String) this.lastRecords.getLast()).equals(value)) {
                    this.lastRecords.add(value);
                }
                if (size > ResourceLeakDetector.access$500()) {
                    this.lastRecords.removeFirst();
                    this.removedRecords++;
                }
            }
        }
    }

    public boolean close() {
        if (!this.freed.compareAndSet(false, true)) {
            return false;
        }
        synchronized (ResourceLeakDetector.access$300(this.this$0)) {
            ResourceLeakDetector.access$410(this.this$0);
            this.prev.next = this.next;
            this.next.prev = this.prev;
            this.prev = null;
            this.next = null;
        }
        return true;
    }

    public String toString() {
        if (this.creationRecord == null) {
            return "";
        }
        Object[] array;
        synchronized (this.lastRecords) {
            array = this.lastRecords.toArray();
            int removedRecords = this.removedRecords;
        }
        StringBuilder buf = new StringBuilder(16384).append(StringUtil.NEWLINE);
        if (removedRecords > 0) {
            buf.append("WARNING: ").append(removedRecords).append(" leak records were discarded because the leak record count is limited to ").append(ResourceLeakDetector.access$500()).append(". Use system property ").append("io.netty.leakDetection.maxRecords").append(" to increase the limit.").append(StringUtil.NEWLINE);
        }
        buf.append("Recent access records: ").append(array.length).append(StringUtil.NEWLINE);
        if (array.length > 0) {
            for (int i = array.length - 1; i >= 0; i--) {
                buf.append('#').append(i + 1).append(':').append(StringUtil.NEWLINE).append(array[i]);
            }
        }
        buf.append("Created at:").append(StringUtil.NEWLINE).append(this.creationRecord);
        buf.setLength(buf.length() - StringUtil.NEWLINE.length());
        return buf.toString();
    }
}
