package com.tencent.mm.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class LRUMap<K, O> {
    private Map<K, TimeVal<O>> c;
    private int d;
    private int e;
    private PreRemoveCallback<K, O> f;

    public interface OnClearListener<K, O> {
        void onClear(K k, O o);
    }

    public interface PreRemoveCallback<K, O> {
        void preRemoveCallback(K k, O o);
    }

    public class TimeVal<OO> {
        final /* synthetic */ LRUMap g;
        public OO obj;
        public Long t;

        public TimeVal(LRUMap lRUMap, OO oo) {
            this.g = lRUMap;
            this.obj = oo;
            UpTime();
        }

        public void UpTime() {
            this.t = Long.valueOf(System.currentTimeMillis());
        }
    }

    public LRUMap(int i) {
        this(i, null);
    }

    public LRUMap(int i, PreRemoveCallback<K, O> preRemoveCallback) {
        this.c = null;
        this.f = null;
        this.d = i;
        this.e = 0;
        this.f = preRemoveCallback;
        this.c = new HashMap();
    }

    public boolean check(K k) {
        return this.c.containsKey(k);
    }

    public boolean checkAndUpTime(K k) {
        if (!this.c.containsKey(k)) {
            return false;
        }
        ((TimeVal) this.c.get(k)).UpTime();
        return true;
    }

    public void clear() {
        this.c.clear();
    }

    public void clear(OnClearListener<K, O> onClearListener) {
        if (this.c != null) {
            if (onClearListener != null) {
                for (Entry entry : this.c.entrySet()) {
                    onClearListener.onClear(entry.getKey(), ((TimeVal) entry.getValue()).obj);
                }
            }
            this.c.clear();
        }
    }

    public O get(K k) {
        return getAndUptime(k);
    }

    public O getAndUptime(K k) {
        TimeVal timeVal = (TimeVal) this.c.get(k);
        if (timeVal == null) {
            return null;
        }
        timeVal.UpTime();
        return timeVal.obj;
    }

    public void remove(K k) {
        if (this.c.containsKey(k)) {
            if (this.f != null) {
                this.f.preRemoveCallback(k, ((TimeVal) this.c.get(k)).obj);
            }
            this.c.remove(k);
        }
    }

    public void setMaxSize(int i) {
        if (i > 0) {
            this.d = i;
        }
    }

    public void setPerDeleteSize(int i) {
        if (i > 0) {
            this.e = i;
        }
    }

    public int size() {
        return this.c.size();
    }

    public void update(K k, O o) {
        if (((TimeVal) this.c.get(k)) == null) {
            this.c.put(k, new TimeVal(this, o));
            if (this.c.size() > this.d) {
                int i;
                Object arrayList = new ArrayList(this.c.entrySet());
                Collections.sort(arrayList, new Comparator<Entry<K, TimeVal<O>>>(this) {
                    final /* synthetic */ LRUMap g;

                    {
                        this.g = r1;
                    }

                    public int compare(Entry<K, TimeVal<O>> entry, Entry<K, TimeVal<O>> entry2) {
                        return ((TimeVal) entry.getValue()).t.compareTo(((TimeVal) entry2.getValue()).t);
                    }
                });
                if (this.e <= 0) {
                    i = this.d / 10;
                    if (i <= 0) {
                        i = 1;
                    }
                } else {
                    i = this.e;
                }
                Iterator it = arrayList.iterator();
                int i2 = i;
                while (it.hasNext()) {
                    remove(((Entry) it.next()).getKey());
                    i = i2 - 1;
                    if (i > 0) {
                        i2 = i;
                    } else {
                        return;
                    }
                }
                return;
            }
            return;
        }
        ((TimeVal) this.c.get(k)).UpTime();
        ((TimeVal) this.c.get(k)).obj = o;
    }
}
