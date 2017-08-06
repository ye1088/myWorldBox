package io.netty.util.internal.chmv8;

import io.netty.handler.codec.http.HttpConstants;
import io.netty.util.internal.IntegerHolder;
import io.netty.util.internal.InternalThreadLocalMap;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import sun.misc.Unsafe;

public class ConcurrentHashMapV8<K, V> implements Serializable, ConcurrentMap<K, V> {
    private static final long ABASE;
    private static final int ASHIFT;
    private static final long BASECOUNT;
    private static final long CELLSBUSY;
    private static final long CELLVALUE;
    private static final int DEFAULT_CAPACITY = 16;
    private static final int DEFAULT_CONCURRENCY_LEVEL = 16;
    static final int HASH_BITS = Integer.MAX_VALUE;
    private static final float LOAD_FACTOR = 0.75f;
    private static final int MAXIMUM_CAPACITY = 1073741824;
    static final int MAX_ARRAY_SIZE = 2147483639;
    private static final int MIN_TRANSFER_STRIDE = 16;
    static final int MIN_TREEIFY_CAPACITY = 64;
    static final int MOVED = -1;
    static final int NCPU = Runtime.getRuntime().availableProcessors();
    static final int RESERVED = -3;
    static final int SEED_INCREMENT = 1640531527;
    private static final long SIZECTL;
    private static final long TRANSFERINDEX;
    private static final long TRANSFERORIGIN;
    static final int TREEBIN = -2;
    static final int TREEIFY_THRESHOLD = 8;
    private static final Unsafe U;
    static final int UNTREEIFY_THRESHOLD = 6;
    static final AtomicInteger counterHashCodeGenerator = new AtomicInteger();
    private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[]{new ObjectStreamField("segments", Segment[].class), new ObjectStreamField("segmentMask", Integer.TYPE), new ObjectStreamField("segmentShift", Integer.TYPE)};
    private static final long serialVersionUID = 7249069246763182397L;
    private volatile transient long baseCount;
    private volatile transient int cellsBusy;
    private volatile transient CounterCell[] counterCells;
    private transient EntrySetView<K, V> entrySet;
    private transient KeySetView<K, V> keySet;
    private volatile transient Node<K, V>[] nextTable;
    private volatile transient int sizeCtl;
    volatile transient Node<K, V>[] table;
    private volatile transient int transferIndex;
    private volatile transient int transferOrigin;
    private transient ValuesView<K, V> values;

    static {
        try {
            U = getUnsafe();
            Class<?> k = ConcurrentHashMapV8.class;
            SIZECTL = U.objectFieldOffset(k.getDeclaredField("sizeCtl"));
            TRANSFERINDEX = U.objectFieldOffset(k.getDeclaredField("transferIndex"));
            TRANSFERORIGIN = U.objectFieldOffset(k.getDeclaredField("transferOrigin"));
            BASECOUNT = U.objectFieldOffset(k.getDeclaredField("baseCount"));
            CELLSBUSY = U.objectFieldOffset(k.getDeclaredField("cellsBusy"));
            CELLVALUE = U.objectFieldOffset(CounterCell.class.getDeclaredField("value"));
            Class<?> ak = Node[].class;
            ABASE = (long) U.arrayBaseOffset(ak);
            int scale = U.arrayIndexScale(ak);
            if (((scale - 1) & scale) != 0) {
                throw new Error("data type scale not a_isRightVersion power of two");
            }
            ASHIFT = 31 - Integer.numberOfLeadingZeros(scale);
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    static final int spread(int h) {
        return ((h >>> 16) ^ h) & Integer.MAX_VALUE;
    }

    private static final int tableSizeFor(int c) {
        int n = c - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        if (n < 0) {
            return 1;
        }
        if (n < 1073741824) {
            return n + 1;
        }
        return 1073741824;
    }

    static Class<?> comparableClassFor(Object x) {
        if (x instanceof Comparable) {
            Type c = x.getClass();
            if (c == String.class) {
                return c;
            }
            Type[] ts = c.getGenericInterfaces();
            if (ts != null) {
                for (Type t : ts) {
                    if (t instanceof ParameterizedType) {
                        ParameterizedType p = (ParameterizedType) t;
                        if (p.getRawType() == Comparable.class) {
                            Type[] as = p.getActualTypeArguments();
                            if (as != null && as.length == 1 && as[0] == c) {
                                return c;
                            }
                        }
                        continue;
                    }
                }
            }
        }
        return null;
    }

    static int compareComparables(Class<?> kc, Object k, Object x) {
        return (x == null || x.getClass() != kc) ? 0 : ((Comparable) k).compareTo(x);
    }

    static final <K, V> Node<K, V> tabAt(Node<K, V>[] tab, int i) {
        return (Node) U.getObjectVolatile(tab, (((long) i) << ASHIFT) + ABASE);
    }

    static final <K, V> boolean casTabAt(Node<K, V>[] tab, int i, Node<K, V> c, Node<K, V> v) {
        return U.compareAndSwapObject(tab, (((long) i) << ASHIFT) + ABASE, c, v);
    }

    static final <K, V> void setTabAt(Node<K, V>[] tab, int i, Node<K, V> v) {
        U.putObjectVolatile(tab, (((long) i) << ASHIFT) + ABASE, v);
    }

    public ConcurrentHashMapV8(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException();
        }
        this.sizeCtl = initialCapacity >= 536870912 ? 1073741824 : tableSizeFor(((initialCapacity >>> 1) + initialCapacity) + 1);
    }

    public ConcurrentHashMapV8(Map<? extends K, ? extends V> m) {
        this.sizeCtl = 16;
        putAll(m);
    }

    public ConcurrentHashMapV8(int initialCapacity, float loadFactor) {
        this(initialCapacity, loadFactor, 1);
    }

    public ConcurrentHashMapV8(int initialCapacity, float loadFactor, int concurrencyLevel) {
        if (loadFactor <= 0.0f || initialCapacity < 0 || concurrencyLevel <= 0) {
            throw new IllegalArgumentException();
        }
        if (initialCapacity < concurrencyLevel) {
            initialCapacity = concurrencyLevel;
        }
        long size = (long) (1.0d + ((double) (((float) ((long) initialCapacity)) / loadFactor)));
        this.sizeCtl = size >= 1073741824 ? 1073741824 : tableSizeFor((int) size);
    }

    public int size() {
        long n = sumCount();
        if (n < 0) {
            return 0;
        }
        return n > 2147483647L ? Integer.MAX_VALUE : (int) n;
    }

    public boolean isEmpty() {
        return sumCount() <= 0;
    }

    public V get(Object key) {
        int h = spread(key.hashCode());
        Node<K, V>[] tab = this.table;
        if (tab == null) {
            return null;
        }
        int n = tab.length;
        if (n <= 0) {
            return null;
        }
        Node<K, V> e = tabAt(tab, (n - 1) & h);
        if (e == null) {
            return null;
        }
        K ek;
        int eh = e.hash;
        if (eh == h) {
            ek = e.key;
            if (ek == key || (ek != null && key.equals(ek))) {
                return e.val;
            }
        } else if (eh < 0) {
            Node<K, V> p = e.find(h, key);
            if (p != null) {
                return p.val;
            }
            return null;
        }
        while (true) {
            e = e.next;
            if (e == null) {
                return null;
            }
            if (e.hash == h) {
                ek = e.key;
                if (ek == key || (ek != null && key.equals(ek))) {
                }
            }
        }
        return e.val;
    }

    public boolean containsKey(Object key) {
        return get(key) != null;
    }

    public boolean containsValue(Object value) {
        if (value == null) {
            throw new NullPointerException();
        }
        Node<K, V>[] t = this.table;
        if (t == null) {
            return false;
        }
        Traverser<K, V> it = new Traverser(t, t.length, 0, t.length);
        while (true) {
            Node<K, V> p = it.advance();
            if (p == null) {
                return false;
            }
            V v = p.val;
            if (v == value || (v != null && value.equals(v))) {
            }
        }
        return true;
    }

    public V put(K key, V value) {
        return putVal(key, value, false);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    final V putVal(K r20, V r21, boolean r22) {
        /*
        r19 = this;
        if (r20 == 0) goto L_0x0004;
    L_0x0002:
        if (r21 != 0) goto L_0x000a;
    L_0x0004:
        r16 = new java.lang.NullPointerException;
        r16.<init>();
        throw r16;
    L_0x000a:
        r16 = r20.hashCode();
        r9 = spread(r16);
        r4 = 0;
        r0 = r19;
        r15 = r0.table;
    L_0x0017:
        if (r15 == 0) goto L_0x001c;
    L_0x0019:
        r11 = r15.length;
        if (r11 != 0) goto L_0x0021;
    L_0x001c:
        r15 = r19.initTable();
        goto L_0x0017;
    L_0x0021:
        r16 = r11 + -1;
        r10 = r16 & r9;
        r7 = tabAt(r15, r10);
        if (r7 != 0) goto L_0x0052;
    L_0x002b:
        r16 = 0;
        r17 = new io.netty.util.internal.chmv8.ConcurrentHashMapV8$Node;
        r18 = 0;
        r0 = r17;
        r1 = r20;
        r2 = r21;
        r3 = r18;
        r0.<init>(r9, r1, r2, r3);
        r0 = r16;
        r1 = r17;
        r16 = casTabAt(r15, r10, r0, r1);
        if (r16 == 0) goto L_0x0017;
    L_0x0046:
        r16 = 1;
        r0 = r19;
        r1 = r16;
        r0.addCount(r1, r4);
        r16 = 0;
    L_0x0051:
        return r16;
    L_0x0052:
        r8 = r7.hash;
        r16 = -1;
        r0 = r16;
        if (r8 != r0) goto L_0x0061;
    L_0x005a:
        r0 = r19;
        r15 = r0.helpTransfer(r15, r7);
        goto L_0x0017;
    L_0x0061:
        r12 = 0;
        monitor-enter(r7);
        r16 = tabAt(r15, r10);	 Catch:{ all -> 0x00bb }
        r0 = r16;
        if (r0 != r7) goto L_0x00e4;
    L_0x006b:
        if (r8 < 0) goto L_0x00c1;
    L_0x006d:
        r4 = 1;
        r5 = r7;
    L_0x006f:
        r0 = r5.hash;	 Catch:{ all -> 0x00bb }
        r16 = r0;
        r0 = r16;
        if (r0 != r9) goto L_0x00a2;
    L_0x0077:
        r6 = r5.key;	 Catch:{ all -> 0x00bb }
        r0 = r20;
        if (r6 == r0) goto L_0x0087;
    L_0x007d:
        if (r6 == 0) goto L_0x00a2;
    L_0x007f:
        r0 = r20;
        r16 = r0.equals(r6);	 Catch:{ all -> 0x00bb }
        if (r16 == 0) goto L_0x00a2;
    L_0x0087:
        r12 = r5.val;	 Catch:{ all -> 0x00bb }
        if (r22 != 0) goto L_0x008f;
    L_0x008b:
        r0 = r21;
        r5.val = r0;	 Catch:{ all -> 0x00bb }
    L_0x008f:
        r16 = r12;
    L_0x0091:
        monitor-exit(r7);	 Catch:{ all -> 0x00bb }
        if (r4 == 0) goto L_0x0017;
    L_0x0094:
        r17 = 8;
        r0 = r17;
        if (r4 < r0) goto L_0x009f;
    L_0x009a:
        r0 = r19;
        r0.treeifyBin(r15, r10);
    L_0x009f:
        if (r16 == 0) goto L_0x0046;
    L_0x00a1:
        goto L_0x0051;
    L_0x00a2:
        r14 = r5;
        r5 = r5.next;	 Catch:{ all -> 0x00bb }
        if (r5 != 0) goto L_0x00be;
    L_0x00a7:
        r16 = new io.netty.util.internal.chmv8.ConcurrentHashMapV8$Node;	 Catch:{ all -> 0x00bb }
        r17 = 0;
        r0 = r16;
        r1 = r20;
        r2 = r21;
        r3 = r17;
        r0.<init>(r9, r1, r2, r3);	 Catch:{ all -> 0x00bb }
        r0 = r16;
        r14.next = r0;	 Catch:{ all -> 0x00bb }
        goto L_0x008f;
    L_0x00bb:
        r16 = move-exception;
        monitor-exit(r7);	 Catch:{ all -> 0x00bb }
        throw r16;
    L_0x00be:
        r4 = r4 + 1;
        goto L_0x006f;
    L_0x00c1:
        r0 = r7 instanceof io.netty.util.internal.chmv8.ConcurrentHashMapV8.TreeBin;	 Catch:{ all -> 0x00bb }
        r16 = r0;
        if (r16 == 0) goto L_0x00e4;
    L_0x00c7:
        r4 = 2;
        r0 = r7;
        r0 = (io.netty.util.internal.chmv8.ConcurrentHashMapV8.TreeBin) r0;	 Catch:{ all -> 0x00bb }
        r16 = r0;
        r0 = r16;
        r1 = r20;
        r2 = r21;
        r13 = r0.putTreeVal(r9, r1, r2);	 Catch:{ all -> 0x00bb }
        if (r13 == 0) goto L_0x00e4;
    L_0x00d9:
        r12 = r13.val;	 Catch:{ all -> 0x00bb }
        if (r22 != 0) goto L_0x00e1;
    L_0x00dd:
        r0 = r21;
        r13.val = r0;	 Catch:{ all -> 0x00bb }
    L_0x00e1:
        r16 = r12;
        goto L_0x0091;
    L_0x00e4:
        r16 = r12;
        goto L_0x0091;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.chmv8.ConcurrentHashMapV8.putVal(java.lang.Object, java.lang.Object, boolean):V");
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        tryPresize(m.size());
        for (Entry<? extends K, ? extends V> e : m.entrySet()) {
            putVal(e.getKey(), e.getValue(), false);
        }
    }

    public V remove(Object key) {
        return replaceNode(key, null, null);
    }

    final V replaceNode(Object key, V value, Object cv) {
        int hash = spread(key.hashCode());
        Node<K, V>[] tab = this.table;
        while (tab != null) {
            int n = tab.length;
            if (n == 0) {
                break;
            }
            int i = (n - 1) & hash;
            Node<K, V> f = tabAt(tab, i);
            if (f == null) {
                break;
            }
            int fh = f.hash;
            if (fh == -1) {
                tab = helpTransfer(tab, f);
            } else {
                V v;
                V oldVal = null;
                boolean validated = false;
                synchronized (f) {
                    if (tabAt(tab, i) == f) {
                        if (fh >= 0) {
                            validated = true;
                            Node<K, V> e = f;
                            Node<K, V> pred = null;
                            do {
                                if (e.hash == hash) {
                                    K ek = e.key;
                                    if (ek == key || (ek != null && key.equals(ek))) {
                                        V ev = e.val;
                                        if (cv == null || cv == ev || (ev != null && cv.equals(ev))) {
                                            oldVal = ev;
                                            if (value != null) {
                                                e.val = value;
                                            } else if (pred != null) {
                                                pred.next = e.next;
                                            } else {
                                                setTabAt(tab, i, e.next);
                                            }
                                        }
                                        v = oldVal;
                                    }
                                }
                                pred = e;
                                e = e.next;
                            } while (e != null);
                            v = oldVal;
                        } else if (f instanceof TreeBin) {
                            validated = true;
                            TreeBin<K, V> t = (TreeBin) f;
                            TreeNode<K, V> r = t.root;
                            if (r != null) {
                                TreeNode<K, V> p = r.findTreeNode(hash, key, null);
                                if (p != null) {
                                    V pv = p.val;
                                    if (cv == null || cv == pv || (pv != null && cv.equals(pv))) {
                                        oldVal = pv;
                                        if (value != null) {
                                            p.val = value;
                                            v = oldVal;
                                        } else {
                                            if (t.removeTreeNode(p)) {
                                                setTabAt(tab, i, untreeify(t.first));
                                            }
                                            v = oldVal;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    v = null;
                }
                if (validated) {
                    if (v != null) {
                        if (value != null) {
                            return v;
                        }
                        addCount(-1, -1);
                        return v;
                    }
                }
            }
        }
        return null;
    }

    public void clear() {
        Throwable th;
        long delta = 0;
        Node<K, V>[] tab = this.table;
        int i = 0;
        while (tab != null && i < tab.length) {
            int i2;
            Node<K, V> f = tabAt(tab, i);
            if (f == null) {
                i2 = i + 1;
            } else {
                int fh = f.hash;
                if (fh == -1) {
                    tab = helpTransfer(tab, f);
                    i2 = 0;
                } else {
                    synchronized (f) {
                        try {
                            if (tabAt(tab, i) == f) {
                                Node<K, V> p = fh >= 0 ? f : f instanceof TreeBin ? ((TreeBin) f).first : null;
                                while (p != null) {
                                    delta--;
                                    p = p.next;
                                }
                                i2 = i + 1;
                                try {
                                    setTabAt(tab, i, null);
                                } catch (Throwable th2) {
                                    th = th2;
                                }
                            } else {
                                i2 = i;
                            }
                        } catch (Throwable th3) {
                            th = th3;
                            i2 = i;
                        }
                    }
                }
            }
            i = i2;
        }
        if (delta != 0) {
            addCount(delta, -1);
            return;
        }
        return;
        throw th;
    }

    public KeySetView<K, V> keySet() {
        KeySetView<K, V> ks = this.keySet;
        if (ks != null) {
            return ks;
        }
        ks = new KeySetView(this, null);
        this.keySet = ks;
        return ks;
    }

    public Collection<V> values() {
        ValuesView<K, V> vs = this.values;
        if (vs != null) {
            return vs;
        }
        vs = new ValuesView(this);
        this.values = vs;
        return vs;
    }

    public Set<Entry<K, V>> entrySet() {
        EntrySetView<K, V> es = this.entrySet;
        if (es != null) {
            return es;
        }
        es = new EntrySetView(this);
        this.entrySet = es;
        return es;
    }

    public int hashCode() {
        int h = 0;
        Node<K, V>[] t = this.table;
        if (t != null) {
            Traverser<K, V> it = new Traverser(t, t.length, 0, t.length);
            while (true) {
                Node<K, V> p = it.advance();
                if (p == null) {
                    break;
                }
                h += p.key.hashCode() ^ p.val.hashCode();
            }
        }
        return h;
    }

    public String toString() {
        Node<K, V>[] t = this.table;
        int f = t == null ? 0 : t.length;
        Traverser<K, V> it = new Traverser(t, f, 0, f);
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        Node<K, V> p = it.advance();
        if (p != null) {
            while (true) {
                K k = p.key;
                V v = p.val;
                if (k == this) {
                    k = "(this Map)";
                }
                sb.append(k);
                sb.append('=');
                if (v == this) {
                    v = "(this Map)";
                }
                sb.append(v);
                p = it.advance();
                if (p == null) {
                    break;
                }
                sb.append(StringUtil.COMMA).append(HttpConstants.SP_CHAR);
            }
        }
        return sb.append('}').toString();
    }

    public boolean equals(Object o) {
        if (o != this) {
            if (!(o instanceof Map)) {
                return false;
            }
            Map<?, ?> m = (Map) o;
            Node<K, V>[] t = this.table;
            int f = t == null ? 0 : t.length;
            Traverser<K, V> it = new Traverser(t, f, 0, f);
            while (true) {
                Node<K, V> p = it.advance();
                if (p == null) {
                    break;
                }
                V val = p.val;
                V v = m.get(p.key);
                if (v == null) {
                    return false;
                }
                if (v != val && !v.equals(val)) {
                    return false;
                }
            }
            for (Entry<?, ?> e : m.entrySet()) {
                Object mk = e.getKey();
                if (mk == null) {
                    return false;
                }
                Object mv = e.getValue();
                if (mv == null) {
                    return false;
                }
                Object v2 = get(mk);
                if (v2 == null) {
                    return false;
                }
                if (mv != v2 && !mv.equals(v2)) {
                    return false;
                }
            }
        }
        return true;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        int sshift = 0;
        int ssize = 1;
        while (ssize < 16) {
            sshift++;
            ssize <<= 1;
        }
        int segmentShift = 32 - sshift;
        int segmentMask = ssize - 1;
        Segment[] segments = (Segment[]) new Segment[16];
        for (int i = 0; i < segments.length; i++) {
            segments[i] = new Segment(LOAD_FACTOR);
        }
        s.putFields().put("segments", segments);
        s.putFields().put("segmentShift", segmentShift);
        s.putFields().put("segmentMask", segmentMask);
        s.writeFields();
        Node<K, V>[] t = this.table;
        if (t != null) {
            Traverser<K, V> it = new Traverser(t, t.length, 0, t.length);
            while (true) {
                Node<K, V> p = it.advance();
                if (p == null) {
                    break;
                }
                s.writeObject(p.key);
                s.writeObject(p.val);
            }
        }
        s.writeObject(null);
        s.writeObject(null);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        this.sizeCtl = -1;
        s.defaultReadObject();
        long size = 0;
        Node<K, V> p = null;
        while (true) {
            K k = s.readObject();
            V v = s.readObject();
            if (k != null && v != null) {
                size++;
                p = new Node(spread(k.hashCode()), k, v, p);
            }
        }
        if (size == 0) {
            this.sizeCtl = 0;
            return;
        }
        int n;
        if (size >= 536870912) {
            n = 1073741824;
        } else {
            int sz = (int) size;
            n = tableSizeFor(((sz >>> 1) + sz) + 1);
        }
        Node[] tab = (Node[]) new Node[n];
        int mask = n - 1;
        long added = 0;
        while (p != null) {
            boolean insertAtFront;
            Node<K, V> next = p.next;
            int h = p.hash;
            int j = h & mask;
            Node<K, V> first = tabAt(tab, j);
            if (first == null) {
                insertAtFront = true;
            } else {
                k = p.key;
                if (first.hash < 0) {
                    if (((TreeBin) first).putTreeVal(h, k, p.val) == null) {
                        added++;
                    }
                    insertAtFront = false;
                } else {
                    Node<K, V> q;
                    int binCount = 0;
                    insertAtFront = true;
                    for (q = first; q != null; q = q.next) {
                        if (q.hash == h) {
                            K qk = q.key;
                            if (qk == k || (qk != null && k.equals(qk))) {
                                insertAtFront = false;
                                break;
                            }
                        }
                        binCount++;
                    }
                    if (insertAtFront && binCount >= 8) {
                        insertAtFront = false;
                        added++;
                        p.next = first;
                        TreeNode<K, V> hd = null;
                        TreeNode<K, V> tl = null;
                        for (q = p; q != null; q = q.next) {
                            TreeNode<K, V> t = new TreeNode(q.hash, q.key, q.val, null, null);
                            t.prev = tl;
                            if (tl == null) {
                                hd = t;
                            } else {
                                tl.next = t;
                            }
                            tl = t;
                        }
                        setTabAt(tab, j, new TreeBin(hd));
                    }
                }
            }
            if (insertAtFront) {
                added++;
                p.next = first;
                setTabAt(tab, j, p);
            }
            p = next;
        }
        this.table = tab;
        this.sizeCtl = n - (n >>> 2);
        this.baseCount = added;
    }

    public V putIfAbsent(K key, V value) {
        return putVal(key, value, true);
    }

    public boolean remove(Object key, Object value) {
        if (key != null) {
            return (value == null || replaceNode(key, null, value) == null) ? false : true;
        } else {
            throw new NullPointerException();
        }
    }

    public boolean replace(K key, V oldValue, V newValue) {
        if (key != null && oldValue != null && newValue != null) {
            return replaceNode(key, newValue, oldValue) != null;
        } else {
            throw new NullPointerException();
        }
    }

    public V replace(K key, V value) {
        if (key != null && value != null) {
            return replaceNode(key, value, null);
        }
        throw new NullPointerException();
    }

    public V getOrDefault(Object key, V defaultValue) {
        V v = get(key);
        return v == null ? defaultValue : v;
    }

    public void forEach(BiAction<? super K, ? super V> action) {
        if (action == null) {
            throw new NullPointerException();
        }
        Node<K, V>[] t = this.table;
        if (t != null) {
            Traverser<K, V> it = new Traverser(t, t.length, 0, t.length);
            while (true) {
                Node<K, V> p = it.advance();
                if (p != null) {
                    action.apply(p.key, p.val);
                } else {
                    return;
                }
            }
        }
    }

    public void replaceAll(BiFun<? super K, ? super V, ? extends V> function) {
        if (function == null) {
            throw new NullPointerException();
        }
        Node<K, V>[] t = this.table;
        if (t != null) {
            Traverser<K, V> it = new Traverser(t, t.length, 0, t.length);
            while (true) {
                Node<K, V> p = it.advance();
                if (p != null) {
                    V oldValue = p.val;
                    K key = p.key;
                    do {
                        V newValue = function.apply(key, oldValue);
                        if (newValue != null) {
                            if (replaceNode(key, newValue, oldValue) != null) {
                                break;
                            }
                            oldValue = get(key);
                        } else {
                            throw new NullPointerException();
                        }
                    } while (oldValue != null);
                }
                return;
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public V computeIfAbsent(K r27, io.netty.util.internal.chmv8.ConcurrentHashMapV8.Fun<? super K, ? extends V> r28) {
        /*
        r26 = this;
        if (r27 == 0) goto L_0x0004;
    L_0x0002:
        if (r28 != 0) goto L_0x000a;
    L_0x0004:
        r22 = new java.lang.NullPointerException;
        r22.<init>();
        throw r22;
    L_0x000a:
        r22 = r27.hashCode();
        r10 = spread(r22);
        r21 = 0;
        r5 = 0;
        r0 = r26;
        r0 = r0.table;
        r20 = r0;
        r22 = r21;
    L_0x001d:
        if (r20 == 0) goto L_0x0024;
    L_0x001f:
        r0 = r20;
        r12 = r0.length;
        if (r12 != 0) goto L_0x0029;
    L_0x0024:
        r20 = r26.initTable();
        goto L_0x001d;
    L_0x0029:
        r23 = r12 + -1;
        r11 = r23 & r10;
        r0 = r20;
        r8 = tabAt(r0, r11);
        if (r8 != 0) goto L_0x0082;
    L_0x0035:
        r17 = new io.netty.util.internal.chmv8.ConcurrentHashMapV8$ReservationNode;
        r17.<init>();
        monitor-enter(r17);
        r23 = 0;
        r0 = r20;
        r1 = r23;
        r2 = r17;
        r23 = casTabAt(r0, r11, r1, r2);	 Catch:{ all -> 0x007f }
        if (r23 == 0) goto L_0x0069;
    L_0x0049:
        r5 = 1;
        r14 = 0;
        r0 = r28;
        r1 = r27;
        r21 = r0.apply(r1);	 Catch:{ all -> 0x0078 }
        if (r21 == 0) goto L_0x0141;
    L_0x0055:
        r13 = new io.netty.util.internal.chmv8.ConcurrentHashMapV8$Node;	 Catch:{ all -> 0x0078 }
        r22 = 0;
        r0 = r27;
        r1 = r21;
        r2 = r22;
        r13.<init>(r10, r0, r1, r2);	 Catch:{ all -> 0x0078 }
    L_0x0062:
        r0 = r20;
        setTabAt(r0, r11, r13);	 Catch:{ all -> 0x007f }
        r22 = r21;
    L_0x0069:
        monitor-exit(r17);	 Catch:{ all -> 0x007f }
        if (r5 == 0) goto L_0x001d;
    L_0x006c:
        if (r22 == 0) goto L_0x0077;
    L_0x006e:
        r24 = 1;
        r0 = r26;
        r1 = r24;
        r0.addCount(r1, r5);
    L_0x0077:
        return r22;
    L_0x0078:
        r22 = move-exception;
        r0 = r20;
        setTabAt(r0, r11, r14);	 Catch:{ all -> 0x007f }
        throw r22;	 Catch:{ all -> 0x007f }
    L_0x007f:
        r22 = move-exception;
        monitor-exit(r17);	 Catch:{ all -> 0x007f }
        throw r22;
    L_0x0082:
        r9 = r8.hash;
        r23 = -1;
        r0 = r23;
        if (r9 != r0) goto L_0x0093;
    L_0x008a:
        r0 = r26;
        r1 = r20;
        r20 = r0.helpTransfer(r1, r8);
        goto L_0x001d;
    L_0x0093:
        r4 = 0;
        monitor-enter(r8);
        r0 = r20;
        r23 = tabAt(r0, r11);	 Catch:{ all -> 0x00fb }
        r0 = r23;
        if (r0 != r8) goto L_0x00c1;
    L_0x009f:
        if (r9 < 0) goto L_0x0101;
    L_0x00a1:
        r5 = 1;
        r6 = r8;
    L_0x00a3:
        r0 = r6.hash;	 Catch:{ all -> 0x00fb }
        r22 = r0;
        r0 = r22;
        if (r0 != r10) goto L_0x00d4;
    L_0x00ab:
        r7 = r6.key;	 Catch:{ all -> 0x00fb }
        r0 = r27;
        if (r7 == r0) goto L_0x00bb;
    L_0x00b1:
        if (r7 == 0) goto L_0x00d4;
    L_0x00b3:
        r0 = r27;
        r22 = r0.equals(r7);	 Catch:{ all -> 0x00fb }
        if (r22 == 0) goto L_0x00d4;
    L_0x00bb:
        r0 = r6.val;	 Catch:{ all -> 0x00fb }
        r21 = r0;
    L_0x00bf:
        r22 = r21;
    L_0x00c1:
        monitor-exit(r8);	 Catch:{ all -> 0x00fb }
        if (r5 == 0) goto L_0x001d;
    L_0x00c4:
        r23 = 8;
        r0 = r23;
        if (r5 < r0) goto L_0x00d1;
    L_0x00ca:
        r0 = r26;
        r1 = r20;
        r0.treeifyBin(r1, r11);
    L_0x00d1:
        if (r4 != 0) goto L_0x006c;
    L_0x00d3:
        goto L_0x0077;
    L_0x00d4:
        r16 = r6;
        r6 = r6.next;	 Catch:{ all -> 0x00fb }
        if (r6 != 0) goto L_0x00fe;
    L_0x00da:
        r0 = r28;
        r1 = r27;
        r21 = r0.apply(r1);	 Catch:{ all -> 0x00fb }
        if (r21 == 0) goto L_0x00bf;
    L_0x00e4:
        r4 = 1;
        r22 = new io.netty.util.internal.chmv8.ConcurrentHashMapV8$Node;	 Catch:{ all -> 0x00fb }
        r23 = 0;
        r0 = r22;
        r1 = r27;
        r2 = r21;
        r3 = r23;
        r0.<init>(r10, r1, r2, r3);	 Catch:{ all -> 0x00fb }
        r0 = r22;
        r1 = r16;
        r1.next = r0;	 Catch:{ all -> 0x00fb }
        goto L_0x00bf;
    L_0x00fb:
        r22 = move-exception;
        monitor-exit(r8);	 Catch:{ all -> 0x00fb }
        throw r22;
    L_0x00fe:
        r5 = r5 + 1;
        goto L_0x00a3;
    L_0x0101:
        r0 = r8 instanceof io.netty.util.internal.chmv8.ConcurrentHashMapV8.TreeBin;	 Catch:{ all -> 0x00fb }
        r23 = r0;
        if (r23 == 0) goto L_0x00c1;
    L_0x0107:
        r5 = 2;
        r0 = r8;
        r0 = (io.netty.util.internal.chmv8.ConcurrentHashMapV8.TreeBin) r0;	 Catch:{ all -> 0x00fb }
        r19 = r0;
        r0 = r19;
        r0 = r0.root;	 Catch:{ all -> 0x00fb }
        r18 = r0;
        if (r18 == 0) goto L_0x012a;
    L_0x0115:
        r22 = 0;
        r0 = r18;
        r1 = r27;
        r2 = r22;
        r15 = r0.findTreeNode(r10, r1, r2);	 Catch:{ all -> 0x00fb }
        if (r15 == 0) goto L_0x012a;
    L_0x0123:
        r0 = r15.val;	 Catch:{ all -> 0x00fb }
        r21 = r0;
        r22 = r21;
        goto L_0x00c1;
    L_0x012a:
        r0 = r28;
        r1 = r27;
        r21 = r0.apply(r1);	 Catch:{ all -> 0x00fb }
        if (r21 == 0) goto L_0x013e;
    L_0x0134:
        r4 = 1;
        r0 = r19;
        r1 = r27;
        r2 = r21;
        r0.putTreeVal(r10, r1, r2);	 Catch:{ all -> 0x00fb }
    L_0x013e:
        r22 = r21;
        goto L_0x00c1;
    L_0x0141:
        r13 = r14;
        goto L_0x0062;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.chmv8.ConcurrentHashMapV8.computeIfAbsent(java.lang.Object, io.netty.util.internal.chmv8.ConcurrentHashMapV8$Fun):V");
    }

    public V computeIfPresent(K key, BiFun<? super K, ? super V, ? extends V> remappingFunction) {
        if (key == null || remappingFunction == null) {
            throw new NullPointerException();
        }
        int h = spread(key.hashCode());
        V val = null;
        int delta = 0;
        int binCount = 0;
        Node<K, V>[] tab = this.table;
        while (true) {
            if (tab != null) {
                int n = tab.length;
                if (n != 0) {
                    int i = (n - 1) & h;
                    Node<K, V> f = tabAt(tab, i);
                    if (f == null) {
                        break;
                    }
                    int fh = f.hash;
                    if (fh == -1) {
                        tab = helpTransfer(tab, f);
                    } else {
                        synchronized (f) {
                            if (tabAt(tab, i) == f) {
                                if (fh >= 0) {
                                    binCount = 1;
                                    Node<K, V> e = f;
                                    Node<K, V> pred = null;
                                    while (true) {
                                        if (e.hash == h) {
                                            K ek = e.key;
                                            if (ek == key || (ek != null && key.equals(ek))) {
                                                val = remappingFunction.apply(key, e.val);
                                            }
                                        }
                                        pred = e;
                                        e = e.next;
                                        if (e == null) {
                                            break;
                                        }
                                        binCount++;
                                    }
                                    val = remappingFunction.apply(key, e.val);
                                    if (val != null) {
                                        e.val = val;
                                    } else {
                                        delta = -1;
                                        Node<K, V> en = e.next;
                                        if (pred != null) {
                                            pred.next = en;
                                        } else {
                                            setTabAt(tab, i, en);
                                        }
                                    }
                                } else if (f instanceof TreeBin) {
                                    binCount = 2;
                                    TreeBin<K, V> t = (TreeBin) f;
                                    TreeNode<K, V> r = t.root;
                                    if (r != null) {
                                        TreeNode<K, V> p = r.findTreeNode(h, key, null);
                                        if (p != null) {
                                            val = remappingFunction.apply(key, p.val);
                                            if (val != null) {
                                                p.val = val;
                                            } else {
                                                delta = -1;
                                                if (t.removeTreeNode(p)) {
                                                    setTabAt(tab, i, untreeify(t.first));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (binCount != 0) {
                            break;
                        }
                    }
                }
            }
            tab = initTable();
        }
        if (delta != 0) {
            addCount((long) delta, binCount);
        }
        return val;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public V compute(K r29, io.netty.util.internal.chmv8.ConcurrentHashMapV8.BiFun<? super K, ? super V, ? extends V> r30) {
        /*
        r28 = this;
        if (r29 == 0) goto L_0x0004;
    L_0x0002:
        if (r30 != 0) goto L_0x000a;
    L_0x0004:
        r24 = new java.lang.NullPointerException;
        r24.<init>();
        throw r24;
    L_0x000a:
        r24 = r29.hashCode();
        r11 = spread(r24);
        r23 = 0;
        r5 = 0;
        r4 = 0;
        r0 = r28;
        r0 = r0.table;
        r22 = r0;
        r24 = r23;
    L_0x001e:
        if (r22 == 0) goto L_0x0025;
    L_0x0020:
        r0 = r22;
        r13 = r0.length;
        if (r13 != 0) goto L_0x002a;
    L_0x0025:
        r22 = r28.initTable();
        goto L_0x001e;
    L_0x002a:
        r25 = r13 + -1;
        r12 = r25 & r11;
        r0 = r22;
        r9 = tabAt(r0, r12);
        if (r9 != 0) goto L_0x0089;
    L_0x0036:
        r19 = new io.netty.util.internal.chmv8.ConcurrentHashMapV8$ReservationNode;
        r19.<init>();
        monitor-enter(r19);
        r25 = 0;
        r0 = r22;
        r1 = r25;
        r2 = r19;
        r25 = casTabAt(r0, r12, r1, r2);	 Catch:{ all -> 0x0086 }
        if (r25 == 0) goto L_0x006f;
    L_0x004a:
        r4 = 1;
        r15 = 0;
        r24 = 0;
        r0 = r30;
        r1 = r29;
        r2 = r24;
        r23 = r0.apply(r1, r2);	 Catch:{ all -> 0x007f }
        if (r23 == 0) goto L_0x01a1;
    L_0x005a:
        r5 = 1;
        r14 = new io.netty.util.internal.chmv8.ConcurrentHashMapV8$Node;	 Catch:{ all -> 0x007f }
        r24 = 0;
        r0 = r29;
        r1 = r23;
        r2 = r24;
        r14.<init>(r11, r0, r1, r2);	 Catch:{ all -> 0x007f }
    L_0x0068:
        r0 = r22;
        setTabAt(r0, r12, r14);	 Catch:{ all -> 0x0086 }
        r24 = r23;
    L_0x006f:
        monitor-exit(r19);	 Catch:{ all -> 0x0086 }
        if (r4 == 0) goto L_0x001e;
    L_0x0072:
        if (r5 == 0) goto L_0x007e;
    L_0x0074:
        r0 = (long) r5;
        r26 = r0;
        r0 = r28;
        r1 = r26;
        r0.addCount(r1, r4);
    L_0x007e:
        return r24;
    L_0x007f:
        r24 = move-exception;
        r0 = r22;
        setTabAt(r0, r12, r15);	 Catch:{ all -> 0x0086 }
        throw r24;	 Catch:{ all -> 0x0086 }
    L_0x0086:
        r24 = move-exception;
        monitor-exit(r19);	 Catch:{ all -> 0x0086 }
        throw r24;
    L_0x0089:
        r10 = r9.hash;
        r25 = -1;
        r0 = r25;
        if (r10 != r0) goto L_0x009a;
    L_0x0091:
        r0 = r28;
        r1 = r22;
        r22 = r0.helpTransfer(r1, r9);
        goto L_0x001e;
    L_0x009a:
        monitor-enter(r9);
        r0 = r22;
        r25 = tabAt(r0, r12);	 Catch:{ all -> 0x00f4 }
        r0 = r25;
        if (r0 != r9) goto L_0x00d9;
    L_0x00a5:
        if (r10 < 0) goto L_0x012b;
    L_0x00a7:
        r4 = 1;
        r6 = r9;
        r17 = 0;
    L_0x00ab:
        r0 = r6.hash;	 Catch:{ all -> 0x00f4 }
        r24 = r0;
        r0 = r24;
        if (r0 != r11) goto L_0x00fd;
    L_0x00b3:
        r7 = r6.key;	 Catch:{ all -> 0x00f4 }
        r0 = r29;
        if (r7 == r0) goto L_0x00c3;
    L_0x00b9:
        if (r7 == 0) goto L_0x00fd;
    L_0x00bb:
        r0 = r29;
        r24 = r0.equals(r7);	 Catch:{ all -> 0x00f4 }
        if (r24 == 0) goto L_0x00fd;
    L_0x00c3:
        r0 = r6.val;	 Catch:{ all -> 0x00f4 }
        r24 = r0;
        r0 = r30;
        r1 = r29;
        r2 = r24;
        r23 = r0.apply(r1, r2);	 Catch:{ all -> 0x00f4 }
        if (r23 == 0) goto L_0x00ea;
    L_0x00d3:
        r0 = r23;
        r6.val = r0;	 Catch:{ all -> 0x00f4 }
    L_0x00d7:
        r24 = r23;
    L_0x00d9:
        monitor-exit(r9);	 Catch:{ all -> 0x00f4 }
        if (r4 == 0) goto L_0x001e;
    L_0x00dc:
        r25 = 8;
        r0 = r25;
        if (r4 < r0) goto L_0x0072;
    L_0x00e2:
        r0 = r28;
        r1 = r22;
        r0.treeifyBin(r1, r12);
        goto L_0x0072;
    L_0x00ea:
        r5 = -1;
        r8 = r6.next;	 Catch:{ all -> 0x00f4 }
        if (r17 == 0) goto L_0x00f7;
    L_0x00ef:
        r0 = r17;
        r0.next = r8;	 Catch:{ all -> 0x00f4 }
        goto L_0x00d7;
    L_0x00f4:
        r24 = move-exception;
        monitor-exit(r9);	 Catch:{ all -> 0x00f4 }
        throw r24;
    L_0x00f7:
        r0 = r22;
        setTabAt(r0, r12, r8);	 Catch:{ all -> 0x00f4 }
        goto L_0x00d7;
    L_0x00fd:
        r17 = r6;
        r6 = r6.next;	 Catch:{ all -> 0x00f4 }
        if (r6 != 0) goto L_0x0128;
    L_0x0103:
        r24 = 0;
        r0 = r30;
        r1 = r29;
        r2 = r24;
        r23 = r0.apply(r1, r2);	 Catch:{ all -> 0x00f4 }
        if (r23 == 0) goto L_0x00d7;
    L_0x0111:
        r5 = 1;
        r24 = new io.netty.util.internal.chmv8.ConcurrentHashMapV8$Node;	 Catch:{ all -> 0x00f4 }
        r25 = 0;
        r0 = r24;
        r1 = r29;
        r2 = r23;
        r3 = r25;
        r0.<init>(r11, r1, r2, r3);	 Catch:{ all -> 0x00f4 }
        r0 = r24;
        r1 = r17;
        r1.next = r0;	 Catch:{ all -> 0x00f4 }
        goto L_0x00d7;
    L_0x0128:
        r4 = r4 + 1;
        goto L_0x00ab;
    L_0x012b:
        r0 = r9 instanceof io.netty.util.internal.chmv8.ConcurrentHashMapV8.TreeBin;	 Catch:{ all -> 0x00f4 }
        r25 = r0;
        if (r25 == 0) goto L_0x00d9;
    L_0x0131:
        r4 = 1;
        r0 = r9;
        r0 = (io.netty.util.internal.chmv8.ConcurrentHashMapV8.TreeBin) r0;	 Catch:{ all -> 0x00f4 }
        r21 = r0;
        r0 = r21;
        r0 = r0.root;	 Catch:{ all -> 0x00f4 }
        r20 = r0;
        if (r20 == 0) goto L_0x0167;
    L_0x013f:
        r24 = 0;
        r0 = r20;
        r1 = r29;
        r2 = r24;
        r16 = r0.findTreeNode(r11, r1, r2);	 Catch:{ all -> 0x00f4 }
    L_0x014b:
        if (r16 != 0) goto L_0x016a;
    L_0x014d:
        r18 = 0;
    L_0x014f:
        r0 = r30;
        r1 = r29;
        r2 = r18;
        r23 = r0.apply(r1, r2);	 Catch:{ all -> 0x00f4 }
        if (r23 == 0) goto L_0x017f;
    L_0x015b:
        if (r16 == 0) goto L_0x0171;
    L_0x015d:
        r0 = r23;
        r1 = r16;
        r1.val = r0;	 Catch:{ all -> 0x00f4 }
        r24 = r23;
        goto L_0x00d9;
    L_0x0167:
        r16 = 0;
        goto L_0x014b;
    L_0x016a:
        r0 = r16;
        r0 = r0.val;	 Catch:{ all -> 0x00f4 }
        r18 = r0;
        goto L_0x014f;
    L_0x0171:
        r5 = 1;
        r0 = r21;
        r1 = r29;
        r2 = r23;
        r0.putTreeVal(r11, r1, r2);	 Catch:{ all -> 0x00f4 }
        r24 = r23;
        goto L_0x00d9;
    L_0x017f:
        if (r16 == 0) goto L_0x019d;
    L_0x0181:
        r5 = -1;
        r0 = r21;
        r1 = r16;
        r24 = r0.removeTreeNode(r1);	 Catch:{ all -> 0x00f4 }
        if (r24 == 0) goto L_0x019d;
    L_0x018c:
        r0 = r21;
        r0 = r0.first;	 Catch:{ all -> 0x00f4 }
        r24 = r0;
        r24 = untreeify(r24);	 Catch:{ all -> 0x00f4 }
        r0 = r22;
        r1 = r24;
        setTabAt(r0, r12, r1);	 Catch:{ all -> 0x00f4 }
    L_0x019d:
        r24 = r23;
        goto L_0x00d9;
    L_0x01a1:
        r14 = r15;
        goto L_0x0068;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.chmv8.ConcurrentHashMapV8.compute(java.lang.Object, io.netty.util.internal.chmv8.ConcurrentHashMapV8$BiFun):V");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public V merge(K r25, V r26, io.netty.util.internal.chmv8.ConcurrentHashMapV8.BiFun<? super V, ? super V, ? extends V> r27) {
        /*
        r24 = this;
        if (r25 == 0) goto L_0x0006;
    L_0x0002:
        if (r26 == 0) goto L_0x0006;
    L_0x0004:
        if (r27 != 0) goto L_0x000c;
    L_0x0006:
        r20 = new java.lang.NullPointerException;
        r20.<init>();
        throw r20;
    L_0x000c:
        r20 = r25.hashCode();
        r11 = spread(r20);
        r19 = 0;
        r5 = 0;
        r4 = 0;
        r0 = r24;
        r0 = r0.table;
        r18 = r0;
        r20 = r19;
    L_0x0020:
        if (r18 == 0) goto L_0x0027;
    L_0x0022:
        r0 = r18;
        r13 = r0.length;
        if (r13 != 0) goto L_0x002c;
    L_0x0027:
        r18 = r24.initTable();
        goto L_0x0020;
    L_0x002c:
        r21 = r13 + -1;
        r12 = r21 & r11;
        r0 = r18;
        r9 = tabAt(r0, r12);
        if (r9 != 0) goto L_0x0067;
    L_0x0038:
        r21 = 0;
        r22 = new io.netty.util.internal.chmv8.ConcurrentHashMapV8$Node;
        r23 = 0;
        r0 = r22;
        r1 = r25;
        r2 = r26;
        r3 = r23;
        r0.<init>(r11, r1, r2, r3);
        r0 = r18;
        r1 = r21;
        r2 = r22;
        r21 = casTabAt(r0, r12, r1, r2);
        if (r21 == 0) goto L_0x0020;
    L_0x0055:
        r5 = 1;
        r19 = r26;
        r20 = r19;
    L_0x005a:
        if (r5 == 0) goto L_0x0066;
    L_0x005c:
        r0 = (long) r5;
        r22 = r0;
        r0 = r24;
        r1 = r22;
        r0.addCount(r1, r4);
    L_0x0066:
        return r20;
    L_0x0067:
        r10 = r9.hash;
        r21 = -1;
        r0 = r21;
        if (r10 != r0) goto L_0x0078;
    L_0x006f:
        r0 = r24;
        r1 = r18;
        r18 = r0.helpTransfer(r1, r9);
        goto L_0x0020;
    L_0x0078:
        monitor-enter(r9);
        r0 = r18;
        r21 = tabAt(r0, r12);	 Catch:{ all -> 0x00cf }
        r0 = r21;
        if (r0 != r9) goto L_0x00b6;
    L_0x0083:
        if (r10 < 0) goto L_0x00f7;
    L_0x0085:
        r4 = 1;
        r6 = r9;
        r15 = 0;
    L_0x0088:
        r0 = r6.hash;	 Catch:{ all -> 0x00cf }
        r20 = r0;
        r0 = r20;
        if (r0 != r11) goto L_0x00d8;
    L_0x0090:
        r7 = r6.key;	 Catch:{ all -> 0x00cf }
        r0 = r25;
        if (r7 == r0) goto L_0x00a0;
    L_0x0096:
        if (r7 == 0) goto L_0x00d8;
    L_0x0098:
        r0 = r25;
        r20 = r0.equals(r7);	 Catch:{ all -> 0x00cf }
        if (r20 == 0) goto L_0x00d8;
    L_0x00a0:
        r0 = r6.val;	 Catch:{ all -> 0x00cf }
        r20 = r0;
        r0 = r27;
        r1 = r20;
        r2 = r26;
        r19 = r0.apply(r1, r2);	 Catch:{ all -> 0x00cf }
        if (r19 == 0) goto L_0x00c7;
    L_0x00b0:
        r0 = r19;
        r6.val = r0;	 Catch:{ all -> 0x00cf }
    L_0x00b4:
        r20 = r19;
    L_0x00b6:
        monitor-exit(r9);	 Catch:{ all -> 0x00cf }
        if (r4 == 0) goto L_0x0020;
    L_0x00b9:
        r21 = 8;
        r0 = r21;
        if (r4 < r0) goto L_0x005a;
    L_0x00bf:
        r0 = r24;
        r1 = r18;
        r0.treeifyBin(r1, r12);
        goto L_0x005a;
    L_0x00c7:
        r5 = -1;
        r8 = r6.next;	 Catch:{ all -> 0x00cf }
        if (r15 == 0) goto L_0x00d2;
    L_0x00cc:
        r15.next = r8;	 Catch:{ all -> 0x00cf }
        goto L_0x00b4;
    L_0x00cf:
        r20 = move-exception;
        monitor-exit(r9);	 Catch:{ all -> 0x00cf }
        throw r20;
    L_0x00d2:
        r0 = r18;
        setTabAt(r0, r12, r8);	 Catch:{ all -> 0x00cf }
        goto L_0x00b4;
    L_0x00d8:
        r15 = r6;
        r6 = r6.next;	 Catch:{ all -> 0x00cf }
        if (r6 != 0) goto L_0x00f4;
    L_0x00dd:
        r5 = 1;
        r19 = r26;
        r20 = new io.netty.util.internal.chmv8.ConcurrentHashMapV8$Node;	 Catch:{ all -> 0x00cf }
        r21 = 0;
        r0 = r20;
        r1 = r25;
        r2 = r19;
        r3 = r21;
        r0.<init>(r11, r1, r2, r3);	 Catch:{ all -> 0x00cf }
        r0 = r20;
        r15.next = r0;	 Catch:{ all -> 0x00cf }
        goto L_0x00b4;
    L_0x00f4:
        r4 = r4 + 1;
        goto L_0x0088;
    L_0x00f7:
        r0 = r9 instanceof io.netty.util.internal.chmv8.ConcurrentHashMapV8.TreeBin;	 Catch:{ all -> 0x00cf }
        r21 = r0;
        if (r21 == 0) goto L_0x00b6;
    L_0x00fd:
        r4 = 2;
        r0 = r9;
        r0 = (io.netty.util.internal.chmv8.ConcurrentHashMapV8.TreeBin) r0;	 Catch:{ all -> 0x00cf }
        r17 = r0;
        r0 = r17;
        r0 = r0.root;	 Catch:{ all -> 0x00cf }
        r16 = r0;
        if (r16 != 0) goto L_0x011b;
    L_0x010b:
        r14 = 0;
    L_0x010c:
        if (r14 != 0) goto L_0x0128;
    L_0x010e:
        r19 = r26;
    L_0x0110:
        if (r19 == 0) goto L_0x0145;
    L_0x0112:
        if (r14 == 0) goto L_0x0137;
    L_0x0114:
        r0 = r19;
        r14.val = r0;	 Catch:{ all -> 0x00cf }
        r20 = r19;
        goto L_0x00b6;
    L_0x011b:
        r20 = 0;
        r0 = r16;
        r1 = r25;
        r2 = r20;
        r14 = r0.findTreeNode(r11, r1, r2);	 Catch:{ all -> 0x00cf }
        goto L_0x010c;
    L_0x0128:
        r0 = r14.val;	 Catch:{ all -> 0x00cf }
        r20 = r0;
        r0 = r27;
        r1 = r20;
        r2 = r26;
        r19 = r0.apply(r1, r2);	 Catch:{ all -> 0x00cf }
        goto L_0x0110;
    L_0x0137:
        r5 = 1;
        r0 = r17;
        r1 = r25;
        r2 = r19;
        r0.putTreeVal(r11, r1, r2);	 Catch:{ all -> 0x00cf }
        r20 = r19;
        goto L_0x00b6;
    L_0x0145:
        if (r14 == 0) goto L_0x0161;
    L_0x0147:
        r5 = -1;
        r0 = r17;
        r20 = r0.removeTreeNode(r14);	 Catch:{ all -> 0x00cf }
        if (r20 == 0) goto L_0x0161;
    L_0x0150:
        r0 = r17;
        r0 = r0.first;	 Catch:{ all -> 0x00cf }
        r20 = r0;
        r20 = untreeify(r20);	 Catch:{ all -> 0x00cf }
        r0 = r18;
        r1 = r20;
        setTabAt(r0, r12, r1);	 Catch:{ all -> 0x00cf }
    L_0x0161:
        r20 = r19;
        goto L_0x00b6;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.chmv8.ConcurrentHashMapV8.merge(java.lang.Object, java.lang.Object, io.netty.util.internal.chmv8.ConcurrentHashMapV8$BiFun):V");
    }

    @Deprecated
    public boolean contains(Object value) {
        return containsValue(value);
    }

    public Enumeration<K> keys() {
        Node<K, V>[] t = this.table;
        int f = t == null ? 0 : t.length;
        return new KeyIterator(t, f, 0, f, this);
    }

    public Enumeration<V> elements() {
        Node<K, V>[] t = this.table;
        int f = t == null ? 0 : t.length;
        return new ValueIterator(t, f, 0, f, this);
    }

    public long mappingCount() {
        long n = sumCount();
        return n < 0 ? 0 : n;
    }

    public static <K> KeySetView<K, Boolean> newKeySet() {
        return new KeySetView(new ConcurrentHashMapV8(), Boolean.TRUE);
    }

    public static <K> KeySetView<K, Boolean> newKeySet(int initialCapacity) {
        return new KeySetView(new ConcurrentHashMapV8(initialCapacity), Boolean.TRUE);
    }

    public KeySetView<K, V> keySet(V mappedValue) {
        if (mappedValue != null) {
            return new KeySetView(this, mappedValue);
        }
        throw new NullPointerException();
    }

    private final Node<K, V>[] initTable() {
        Node<K, V>[] tab;
        int sc;
        while (true) {
            tab = this.table;
            if (tab != null && tab.length != 0) {
                break;
            }
            sc = this.sizeCtl;
            if (sc < 0) {
                Thread.yield();
            } else if (U.compareAndSwapInt(this, SIZECTL, sc, -1)) {
                try {
                    break;
                } catch (Throwable th) {
                    this.sizeCtl = sc;
                }
            }
        }
        tab = this.table;
        if (tab == null || tab.length == 0) {
            int n = sc > 0 ? sc : 16;
            Node<K, V>[] nt = (Node[]) new Node[n];
            tab = nt;
            this.table = nt;
            sc = n - (n >>> 2);
        }
        this.sizeCtl = sc;
        return tab;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void addCount(long r36, int r38) {
        /*
        r35 = this;
        r0 = r35;
        r0 = r0.counterCells;
        r30 = r0;
        if (r30 != 0) goto L_0x001a;
    L_0x0008:
        r4 = U;
        r6 = BASECOUNT;
        r0 = r35;
        r8 = r0.baseCount;
        r10 = r8 + r36;
        r5 = r35;
        r4 = r4.compareAndSwapLong(r5, r6, r8, r10);
        if (r4 != 0) goto L_0x005c;
    L_0x001a:
        r23 = 1;
        r34 = io.netty.util.internal.InternalThreadLocalMap.get();
        r22 = r34.counterHashCode();
        if (r22 == 0) goto L_0x0049;
    L_0x0026:
        if (r30 == 0) goto L_0x0049;
    L_0x0028:
        r0 = r30;
        r4 = r0.length;
        r31 = r4 + -1;
        if (r31 < 0) goto L_0x0049;
    L_0x002f:
        r0 = r22;
        r4 = r0.value;
        r4 = r4 & r31;
        r13 = r30[r4];
        if (r13 == 0) goto L_0x0049;
    L_0x0039:
        r12 = U;
        r14 = CELLVALUE;
        r0 = r13.value;
        r16 = r0;
        r18 = r16 + r36;
        r23 = r12.compareAndSwapLong(r13, r14, r16, r18);
        if (r23 != 0) goto L_0x0053;
    L_0x0049:
        r18 = r35;
        r19 = r34;
        r20 = r36;
        r18.fullAddCount(r19, r20, r22, r23);
    L_0x0052:
        return;
    L_0x0053:
        r4 = 1;
        r0 = r38;
        if (r0 <= r4) goto L_0x0052;
    L_0x0058:
        r10 = r35.sumCount();
    L_0x005c:
        if (r38 < 0) goto L_0x0052;
    L_0x005e:
        r0 = r35;
        r0 = r0.sizeCtl;
        r28 = r0;
        r0 = r28;
        r4 = (long) r0;
        r4 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1));
        if (r4 < 0) goto L_0x0052;
    L_0x006b:
        r0 = r35;
        r0 = r0.table;
        r33 = r0;
        if (r33 == 0) goto L_0x0052;
    L_0x0073:
        r0 = r33;
        r4 = r0.length;
        r5 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        if (r4 >= r5) goto L_0x0052;
    L_0x007a:
        if (r28 >= 0) goto L_0x00af;
    L_0x007c:
        r4 = -1;
        r0 = r28;
        if (r0 == r4) goto L_0x0052;
    L_0x0081:
        r0 = r35;
        r4 = r0.transferIndex;
        r0 = r35;
        r5 = r0.transferOrigin;
        if (r4 <= r5) goto L_0x0052;
    L_0x008b:
        r0 = r35;
        r0 = r0.nextTable;
        r32 = r0;
        if (r32 == 0) goto L_0x0052;
    L_0x0093:
        r24 = U;
        r26 = SIZECTL;
        r29 = r28 + -1;
        r25 = r35;
        r4 = r24.compareAndSwapInt(r25, r26, r28, r29);
        if (r4 == 0) goto L_0x00aa;
    L_0x00a1:
        r0 = r35;
        r1 = r33;
        r2 = r32;
        r0.transfer(r1, r2);
    L_0x00aa:
        r10 = r35.sumCount();
        goto L_0x005e;
    L_0x00af:
        r24 = U;
        r26 = SIZECTL;
        r29 = -2;
        r25 = r35;
        r4 = r24.compareAndSwapInt(r25, r26, r28, r29);
        if (r4 == 0) goto L_0x00aa;
    L_0x00bd:
        r4 = 0;
        r0 = r35;
        r1 = r33;
        r0.transfer(r1, r4);
        goto L_0x00aa;
        */
        throw new UnsupportedOperationException("Method not decompiled: io.netty.util.internal.chmv8.ConcurrentHashMapV8.addCount(long, int):void");
    }

    final Node<K, V>[] helpTransfer(Node<K, V>[] tab, Node<K, V> f) {
        if (f instanceof ForwardingNode) {
            Node<K, V>[] nextTab = ((ForwardingNode) f).nextTable;
            if (nextTab != null) {
                if (nextTab != this.nextTable || tab != this.table || this.transferIndex <= this.transferOrigin) {
                    return nextTab;
                }
                int sc = this.sizeCtl;
                if (sc >= -1 || !U.compareAndSwapInt(this, SIZECTL, sc, sc - 1)) {
                    return nextTab;
                }
                transfer(tab, nextTab);
                return nextTab;
            }
        }
        return this.table;
    }

    private final void tryPresize(int size) {
        int c = size >= 536870912 ? 1073741824 : tableSizeFor(((size >>> 1) + size) + 1);
        while (true) {
            int sc = this.sizeCtl;
            if (sc >= 0) {
                int n;
                Node<K, V>[] tab = this.table;
                if (tab != null) {
                    n = tab.length;
                    if (n != 0) {
                        if (c > sc && n < 1073741824) {
                            if (tab == this.table && U.compareAndSwapInt(this, SIZECTL, sc, -2)) {
                                transfer(tab, null);
                            }
                        } else {
                            return;
                        }
                    }
                }
                if (sc > c) {
                    n = sc;
                } else {
                    n = c;
                }
                if (U.compareAndSwapInt(this, SIZECTL, sc, -1)) {
                    try {
                        if (this.table == tab) {
                            this.table = (Node[]) new Node[n];
                            sc = n - (n >>> 2);
                        }
                        this.sizeCtl = sc;
                    } catch (Throwable th) {
                        this.sizeCtl = sc;
                    }
                } else {
                    continue;
                }
            } else {
                return;
            }
        }
    }

    private final void transfer(Node<K, V>[] tab, Node<K, V>[] nextTab) {
        int stride;
        int n = tab.length;
        if (NCPU > 1) {
            stride = (n >>> 3) / NCPU;
        } else {
            stride = n;
        }
        if (stride < 16) {
            stride = 16;
        }
        if (nextTab == null) {
            try {
                nextTab = (Node[]) new Node[(n << 1)];
                this.nextTable = nextTab;
                this.transferOrigin = n;
                this.transferIndex = n;
                ForwardingNode<K, V> forwardingNode = new ForwardingNode(tab);
                int k = n;
                while (k > 0) {
                    int m;
                    int nextk = k > stride ? k - stride : 0;
                    for (m = nextk; m < k; m++) {
                        nextTab[m] = forwardingNode;
                    }
                    for (m = n + nextk; m < n + k; m++) {
                        nextTab[m] = forwardingNode;
                    }
                    k = nextk;
                    U.putOrderedInt(this, TRANSFERORIGIN, nextk);
                }
            } catch (Throwable th) {
                this.sizeCtl = Integer.MAX_VALUE;
                return;
            }
        }
        int nextn = nextTab.length;
        Node forwardingNode2 = new ForwardingNode(nextTab);
        boolean advance = true;
        boolean finishing = false;
        int i = 0;
        int bound = 0;
        while (true) {
            if (advance) {
                i--;
                if (i >= bound || finishing) {
                    advance = false;
                } else {
                    int nextIndex = this.transferIndex;
                    if (nextIndex <= this.transferOrigin) {
                        i = -1;
                        advance = false;
                    } else {
                        Unsafe unsafe = U;
                        long j = TRANSFERINDEX;
                        int nextBound = nextIndex > stride ? nextIndex - stride : 0;
                        if (unsafe.compareAndSwapInt(this, j, nextIndex, nextBound)) {
                            bound = nextBound;
                            i = nextIndex - 1;
                            advance = false;
                        }
                    }
                }
            } else if (i >= 0 && i < n && i + n < nextn) {
                Node<K, V> f = tabAt(tab, i);
                if (f != null) {
                    int fh = f.hash;
                    if (fh == -1) {
                        advance = true;
                    } else {
                        synchronized (f) {
                            if (tabAt(tab, i) == f) {
                                Node<K, V> ln;
                                Node<K, V> hn;
                                Node<K, V> node;
                                if (fh >= 0) {
                                    Node<K, V> p;
                                    int runBit = fh & n;
                                    Node<K, V> lastRun = f;
                                    for (p = f.next; p != null; p = p.next) {
                                        int b = p.hash & n;
                                        if (b != runBit) {
                                            runBit = b;
                                            lastRun = p;
                                        }
                                    }
                                    if (runBit == 0) {
                                        ln = lastRun;
                                        hn = null;
                                    } else {
                                        hn = lastRun;
                                        ln = null;
                                    }
                                    p = f;
                                    Node<K, V> hn2 = hn;
                                    Node<K, V> ln2 = ln;
                                    while (p != lastRun) {
                                        int ph = p.hash;
                                        K pk = p.key;
                                        V pv = p.val;
                                        if ((ph & n) == 0) {
                                            node = new Node(ph, pk, pv, ln2);
                                            hn = hn2;
                                        } else {
                                            node = new Node(ph, pk, pv, hn2);
                                            ln = ln2;
                                        }
                                        p = p.next;
                                        hn2 = hn;
                                        ln2 = ln;
                                    }
                                    setTabAt(nextTab, i, ln2);
                                    setTabAt(nextTab, i + n, hn2);
                                    setTabAt(tab, i, forwardingNode2);
                                    advance = true;
                                } else if (f instanceof TreeBin) {
                                    Node<K, V> t = (TreeBin) f;
                                    TreeNode<K, V> lo = null;
                                    TreeNode<K, V> loTail = null;
                                    TreeNode<K, V> hi = null;
                                    TreeNode<K, V> hiTail = null;
                                    int lc = 0;
                                    int hc = 0;
                                    for (Node<K, V> e = t.first; e != null; e = e.next) {
                                        int h = e.hash;
                                        Node p2 = new TreeNode(h, e.key, e.val, null, null);
                                        if ((h & n) == 0) {
                                            p2.prev = loTail;
                                            if (loTail == null) {
                                                lo = p2;
                                            } else {
                                                loTail.next = p2;
                                            }
                                            loTail = p2;
                                            lc++;
                                        } else {
                                            p2.prev = hiTail;
                                            if (hiTail == null) {
                                                hi = p2;
                                            } else {
                                                hiTail.next = p2;
                                            }
                                            Node hiTail2 = p2;
                                            hc++;
                                        }
                                    }
                                    if (lc <= 6) {
                                        ln = untreeify(lo);
                                    } else if (hc != 0) {
                                        node = new TreeBin(lo);
                                    } else {
                                        ln = t;
                                    }
                                    if (hc <= 6) {
                                        hn = untreeify(hi);
                                    } else if (lc != 0) {
                                        node = new TreeBin(hi);
                                    } else {
                                        hn = t;
                                    }
                                    setTabAt(nextTab, i, ln);
                                    setTabAt(nextTab, i + n, hn);
                                    setTabAt(tab, i, forwardingNode2);
                                    advance = true;
                                }
                            }
                        }
                    }
                } else if (casTabAt(tab, i, null, forwardingNode2)) {
                    setTabAt(nextTab, i, null);
                    setTabAt(nextTab, i + n, null);
                    advance = true;
                }
            } else if (finishing) {
                this.nextTable = null;
                this.table = nextTab;
                this.sizeCtl = (n << 1) - (n >>> 1);
                return;
            } else {
                int sc;
                Unsafe unsafe2;
                long j2;
                int i2;
                do {
                    unsafe2 = U;
                    j2 = SIZECTL;
                    i2 = this.sizeCtl;
                    sc = i2 + 1;
                } while (!unsafe2.compareAndSwapInt(this, j2, i2, sc));
                if (sc == -1) {
                    advance = true;
                    finishing = true;
                    i = n;
                } else {
                    return;
                }
            }
        }
    }

    private final void treeifyBin(Node<K, V>[] tab, int index) {
        if (tab == null) {
            return;
        }
        if (tab.length >= 64) {
            Node<K, V> b = tabAt(tab, index);
            if (b != null && b.hash >= 0) {
                synchronized (b) {
                    if (tabAt(tab, index) == b) {
                        TreeNode<K, V> hd = null;
                        TreeNode<K, V> tl = null;
                        for (Node<K, V> e = b; e != null; e = e.next) {
                            TreeNode<K, V> p = new TreeNode(e.hash, e.key, e.val, null, null);
                            p.prev = tl;
                            if (tl == null) {
                                hd = p;
                            } else {
                                tl.next = p;
                            }
                            tl = p;
                        }
                        setTabAt(tab, index, new TreeBin(hd));
                    }
                }
            }
        } else if (tab == this.table) {
            int sc = this.sizeCtl;
            if (sc >= 0 && U.compareAndSwapInt(this, SIZECTL, sc, -2)) {
                transfer(tab, null);
            }
        }
    }

    static <K, V> Node<K, V> untreeify(Node<K, V> b) {
        Node<K, V> hd = null;
        Node<K, V> tl = null;
        for (Node<K, V> q = b; q != null; q = q.next) {
            Node<K, V> p = new Node(q.hash, q.key, q.val, null);
            if (tl == null) {
                hd = p;
            } else {
                tl.next = p;
            }
            tl = p;
        }
        return hd;
    }

    final int batchFor(long b) {
        if (b != Long.MAX_VALUE) {
            long n = sumCount();
            if (n > 1 && n >= b) {
                int sp = ForkJoinPool.getCommonPoolParallelism() << 2;
                if (b <= 0) {
                    return sp;
                }
                n /= b;
                return n < ((long) sp) ? (int) n : sp;
            }
        }
        return 0;
    }

    public void forEach(long parallelismThreshold, BiAction<? super K, ? super V> action) {
        if (action == null) {
            throw new NullPointerException();
        }
        new ForEachMappingTask(null, batchFor(parallelismThreshold), 0, 0, this.table, action).invoke();
    }

    public <U> void forEach(long parallelismThreshold, BiFun<? super K, ? super V, ? extends U> transformer, Action<? super U> action) {
        if (transformer == null || action == null) {
            throw new NullPointerException();
        }
        new ForEachTransformedMappingTask(null, batchFor(parallelismThreshold), 0, 0, this.table, transformer, action).invoke();
    }

    public <U> U search(long parallelismThreshold, BiFun<? super K, ? super V, ? extends U> searchFunction) {
        if (searchFunction == null) {
            throw new NullPointerException();
        }
        return new SearchMappingsTask(null, batchFor(parallelismThreshold), 0, 0, this.table, searchFunction, new AtomicReference()).invoke();
    }

    public <U> U reduce(long parallelismThreshold, BiFun<? super K, ? super V, ? extends U> transformer, BiFun<? super U, ? super U, ? extends U> reducer) {
        if (transformer == null || reducer == null) {
            throw new NullPointerException();
        }
        return new MapReduceMappingsTask(null, batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, reducer).invoke();
    }

    public double reduceToDouble(long parallelismThreshold, ObjectByObjectToDouble<? super K, ? super V> transformer, double basis, DoubleByDoubleToDouble reducer) {
        if (transformer != null && reducer != null) {
            return ((Double) new MapReduceMappingsToDoubleTask(null, batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer).invoke()).doubleValue();
        }
        throw new NullPointerException();
    }

    public long reduceToLong(long parallelismThreshold, ObjectByObjectToLong<? super K, ? super V> transformer, long basis, LongByLongToLong reducer) {
        if (transformer != null && reducer != null) {
            return ((Long) new MapReduceMappingsToLongTask(null, batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer).invoke()).longValue();
        }
        throw new NullPointerException();
    }

    public int reduceToInt(long parallelismThreshold, ObjectByObjectToInt<? super K, ? super V> transformer, int basis, IntByIntToInt reducer) {
        if (transformer == null || reducer == null) {
            throw new NullPointerException();
        }
        return ((Integer) new MapReduceMappingsToIntTask(null, batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer).invoke()).intValue();
    }

    public void forEachKey(long parallelismThreshold, Action<? super K> action) {
        if (action == null) {
            throw new NullPointerException();
        }
        new ForEachKeyTask(null, batchFor(parallelismThreshold), 0, 0, this.table, action).invoke();
    }

    public <U> void forEachKey(long parallelismThreshold, Fun<? super K, ? extends U> transformer, Action<? super U> action) {
        if (transformer == null || action == null) {
            throw new NullPointerException();
        }
        new ForEachTransformedKeyTask(null, batchFor(parallelismThreshold), 0, 0, this.table, transformer, action).invoke();
    }

    public <U> U searchKeys(long parallelismThreshold, Fun<? super K, ? extends U> searchFunction) {
        if (searchFunction == null) {
            throw new NullPointerException();
        }
        return new SearchKeysTask(null, batchFor(parallelismThreshold), 0, 0, this.table, searchFunction, new AtomicReference()).invoke();
    }

    public K reduceKeys(long parallelismThreshold, BiFun<? super K, ? super K, ? extends K> reducer) {
        if (reducer == null) {
            throw new NullPointerException();
        }
        return new ReduceKeysTask(null, batchFor(parallelismThreshold), 0, 0, this.table, null, reducer).invoke();
    }

    public <U> U reduceKeys(long parallelismThreshold, Fun<? super K, ? extends U> transformer, BiFun<? super U, ? super U, ? extends U> reducer) {
        if (transformer == null || reducer == null) {
            throw new NullPointerException();
        }
        return new MapReduceKeysTask(null, batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, reducer).invoke();
    }

    public double reduceKeysToDouble(long parallelismThreshold, ObjectToDouble<? super K> transformer, double basis, DoubleByDoubleToDouble reducer) {
        if (transformer != null && reducer != null) {
            return ((Double) new MapReduceKeysToDoubleTask(null, batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer).invoke()).doubleValue();
        }
        throw new NullPointerException();
    }

    public long reduceKeysToLong(long parallelismThreshold, ObjectToLong<? super K> transformer, long basis, LongByLongToLong reducer) {
        if (transformer != null && reducer != null) {
            return ((Long) new MapReduceKeysToLongTask(null, batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer).invoke()).longValue();
        }
        throw new NullPointerException();
    }

    public int reduceKeysToInt(long parallelismThreshold, ObjectToInt<? super K> transformer, int basis, IntByIntToInt reducer) {
        if (transformer == null || reducer == null) {
            throw new NullPointerException();
        }
        return ((Integer) new MapReduceKeysToIntTask(null, batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer).invoke()).intValue();
    }

    public void forEachValue(long parallelismThreshold, Action<? super V> action) {
        if (action == null) {
            throw new NullPointerException();
        }
        new ForEachValueTask(null, batchFor(parallelismThreshold), 0, 0, this.table, action).invoke();
    }

    public <U> void forEachValue(long parallelismThreshold, Fun<? super V, ? extends U> transformer, Action<? super U> action) {
        if (transformer == null || action == null) {
            throw new NullPointerException();
        }
        new ForEachTransformedValueTask(null, batchFor(parallelismThreshold), 0, 0, this.table, transformer, action).invoke();
    }

    public <U> U searchValues(long parallelismThreshold, Fun<? super V, ? extends U> searchFunction) {
        if (searchFunction == null) {
            throw new NullPointerException();
        }
        return new SearchValuesTask(null, batchFor(parallelismThreshold), 0, 0, this.table, searchFunction, new AtomicReference()).invoke();
    }

    public V reduceValues(long parallelismThreshold, BiFun<? super V, ? super V, ? extends V> reducer) {
        if (reducer == null) {
            throw new NullPointerException();
        }
        return new ReduceValuesTask(null, batchFor(parallelismThreshold), 0, 0, this.table, null, reducer).invoke();
    }

    public <U> U reduceValues(long parallelismThreshold, Fun<? super V, ? extends U> transformer, BiFun<? super U, ? super U, ? extends U> reducer) {
        if (transformer == null || reducer == null) {
            throw new NullPointerException();
        }
        return new MapReduceValuesTask(null, batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, reducer).invoke();
    }

    public double reduceValuesToDouble(long parallelismThreshold, ObjectToDouble<? super V> transformer, double basis, DoubleByDoubleToDouble reducer) {
        if (transformer != null && reducer != null) {
            return ((Double) new MapReduceValuesToDoubleTask(null, batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer).invoke()).doubleValue();
        }
        throw new NullPointerException();
    }

    public long reduceValuesToLong(long parallelismThreshold, ObjectToLong<? super V> transformer, long basis, LongByLongToLong reducer) {
        if (transformer != null && reducer != null) {
            return ((Long) new MapReduceValuesToLongTask(null, batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer).invoke()).longValue();
        }
        throw new NullPointerException();
    }

    public int reduceValuesToInt(long parallelismThreshold, ObjectToInt<? super V> transformer, int basis, IntByIntToInt reducer) {
        if (transformer == null || reducer == null) {
            throw new NullPointerException();
        }
        return ((Integer) new MapReduceValuesToIntTask(null, batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer).invoke()).intValue();
    }

    public void forEachEntry(long parallelismThreshold, Action<? super Entry<K, V>> action) {
        if (action == null) {
            throw new NullPointerException();
        }
        new ForEachEntryTask(null, batchFor(parallelismThreshold), 0, 0, this.table, action).invoke();
    }

    public <U> void forEachEntry(long parallelismThreshold, Fun<Entry<K, V>, ? extends U> transformer, Action<? super U> action) {
        if (transformer == null || action == null) {
            throw new NullPointerException();
        }
        new ForEachTransformedEntryTask(null, batchFor(parallelismThreshold), 0, 0, this.table, transformer, action).invoke();
    }

    public <U> U searchEntries(long parallelismThreshold, Fun<Entry<K, V>, ? extends U> searchFunction) {
        if (searchFunction == null) {
            throw new NullPointerException();
        }
        return new SearchEntriesTask(null, batchFor(parallelismThreshold), 0, 0, this.table, searchFunction, new AtomicReference()).invoke();
    }

    public Entry<K, V> reduceEntries(long parallelismThreshold, BiFun<Entry<K, V>, Entry<K, V>, ? extends Entry<K, V>> reducer) {
        if (reducer == null) {
            throw new NullPointerException();
        }
        return (Entry) new ReduceEntriesTask(null, batchFor(parallelismThreshold), 0, 0, this.table, null, reducer).invoke();
    }

    public <U> U reduceEntries(long parallelismThreshold, Fun<Entry<K, V>, ? extends U> transformer, BiFun<? super U, ? super U, ? extends U> reducer) {
        if (transformer == null || reducer == null) {
            throw new NullPointerException();
        }
        return new MapReduceEntriesTask(null, batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, reducer).invoke();
    }

    public double reduceEntriesToDouble(long parallelismThreshold, ObjectToDouble<Entry<K, V>> transformer, double basis, DoubleByDoubleToDouble reducer) {
        if (transformer != null && reducer != null) {
            return ((Double) new MapReduceEntriesToDoubleTask(null, batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer).invoke()).doubleValue();
        }
        throw new NullPointerException();
    }

    public long reduceEntriesToLong(long parallelismThreshold, ObjectToLong<Entry<K, V>> transformer, long basis, LongByLongToLong reducer) {
        if (transformer != null && reducer != null) {
            return ((Long) new MapReduceEntriesToLongTask(null, batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer).invoke()).longValue();
        }
        throw new NullPointerException();
    }

    public int reduceEntriesToInt(long parallelismThreshold, ObjectToInt<Entry<K, V>> transformer, int basis, IntByIntToInt reducer) {
        if (transformer == null || reducer == null) {
            throw new NullPointerException();
        }
        return ((Integer) new MapReduceEntriesToIntTask(null, batchFor(parallelismThreshold), 0, 0, this.table, null, transformer, basis, reducer).invoke()).intValue();
    }

    final long sumCount() {
        CounterCell[] as = this.counterCells;
        long sum = this.baseCount;
        if (as != null) {
            for (CounterCell a : as) {
                if (a != null) {
                    sum += a.value;
                }
            }
        }
        return sum;
    }

    private final void fullAddCount(InternalThreadLocalMap threadLocals, long x, IntegerHolder hc, boolean wasUncontended) {
        int h;
        if (hc == null) {
            hc = new IntegerHolder();
            int s = counterHashCodeGenerator.addAndGet(SEED_INCREMENT);
            if (s == 0) {
                h = 1;
            } else {
                h = s;
            }
            hc.value = h;
            threadLocals.setCounterHashCode(hc);
        } else {
            h = hc.value;
        }
        boolean collide = false;
        while (true) {
            long v;
            CounterCell[] rs;
            CounterCell[] as = this.counterCells;
            if (as != null) {
                int n = as.length;
                if (n > 0) {
                    CounterCell a = as[(n - 1) & h];
                    if (a != null) {
                        if (wasUncontended) {
                            Unsafe unsafe = U;
                            long j = CELLVALUE;
                            v = a.value;
                            if (unsafe.compareAndSwapLong(a, j, v, v + x)) {
                                break;
                            } else if (this.counterCells != as || n >= NCPU) {
                                collide = false;
                            } else if (!collide) {
                                collide = true;
                            } else if (this.cellsBusy == 0 && U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
                                try {
                                    if (this.counterCells == as) {
                                        rs = new CounterCell[(n << 1)];
                                        for (int i = 0; i < n; i++) {
                                            rs[i] = as[i];
                                        }
                                        this.counterCells = rs;
                                    }
                                    this.cellsBusy = 0;
                                    collide = false;
                                } catch (Throwable th) {
                                    this.cellsBusy = 0;
                                }
                            }
                        } else {
                            wasUncontended = true;
                        }
                    } else {
                        if (this.cellsBusy == 0) {
                            CounterCell counterCell = new CounterCell(x);
                            if (this.cellsBusy == 0 && U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
                                boolean created = false;
                                try {
                                    rs = this.counterCells;
                                    if (rs != null) {
                                        int m = rs.length;
                                        if (m > 0) {
                                            int j2 = (m - 1) & h;
                                            if (rs[j2] == null) {
                                                rs[j2] = counterCell;
                                                created = true;
                                            }
                                        }
                                    }
                                    this.cellsBusy = 0;
                                    if (created) {
                                        break;
                                    }
                                } catch (Throwable th2) {
                                    this.cellsBusy = 0;
                                }
                            }
                        }
                        collide = false;
                    }
                    h ^= h << 13;
                    h ^= h >>> 17;
                    h ^= h << 5;
                }
            }
            if (this.cellsBusy == 0 && this.counterCells == as && U.compareAndSwapInt(this, CELLSBUSY, 0, 1)) {
                boolean init = false;
                try {
                    if (this.counterCells == as) {
                        rs = new CounterCell[2];
                        rs[h & 1] = new CounterCell(x);
                        this.counterCells = rs;
                        init = true;
                    }
                    this.cellsBusy = 0;
                    if (init) {
                        break;
                    }
                } catch (Throwable th3) {
                    this.cellsBusy = 0;
                }
            } else {
                Unsafe unsafe2 = U;
                long j3 = BASECOUNT;
                v = this.baseCount;
                if (unsafe2.compareAndSwapLong(this, j3, v, v + x)) {
                    break;
                }
            }
        }
        hc.value = h;
    }

    private static Unsafe getUnsafe() {
        Unsafe unsafe;
        try {
            unsafe = Unsafe.getUnsafe();
        } catch (SecurityException e) {
            try {
                unsafe = (Unsafe) AccessController.doPrivileged(new 1());
            } catch (PrivilegedActionException e2) {
                throw new RuntimeException("Could not initialize intrinsics", e2.getCause());
            }
        }
        return unsafe;
    }
}
