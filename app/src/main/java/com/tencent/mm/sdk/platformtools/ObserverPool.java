package com.tencent.mm.sdk.platformtools;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import junit.framework.Assert;

public final class ObserverPool {
    private final HashMap<String, LinkedList<Listener>> ay = new HashMap();

    public static class Event {
        public static final int FLAG_ORDER_EXE = 1;
        private final String aB;
        private int aC;
        public final Bundle data = new Bundle();

        public Event(String str) {
            Assert.assertNotNull(str);
            this.aB = str;
        }

        public int getFlag() {
            return this.aC;
        }

        public String getId() {
            return this.aB;
        }

        public void onComplete() {
        }

        public Event setFlag(int i) {
            this.aC = i;
            return this;
        }
    }

    public static abstract class Listener {
        private final int priority;

        public Listener() {
            this.priority = 0;
        }

        public Listener(int i) {
            this.priority = i;
        }

        public abstract boolean callback(Event event);

        public int getPriority() {
            return this.priority;
        }
    }

    public final synchronized boolean add(String str, Listener listener) {
        boolean z;
        Assert.assertNotNull(listener);
        LinkedList linkedList = (LinkedList) this.ay.get(str);
        if (linkedList == null) {
            linkedList = new LinkedList();
            this.ay.put(str, linkedList);
        }
        if (linkedList.contains(listener)) {
            Log.e("MicroMsg.ObserverPool", "Cannot add the same listener twice. EventId: %s, Stack: %s.", str, Util.getStack());
            z = false;
        } else {
            z = linkedList.add(listener);
        }
        return z;
    }

    public final void asyncPublish(Event event) {
        asyncPublish(event, Looper.myLooper());
    }

    public final void asyncPublish(final Event event, Looper looper) {
        Assert.assertNotNull(looper);
        new Handler(looper).post(new Runnable(this) {
            final /* synthetic */ ObserverPool az;

            public void run() {
                this.az.publish(event);
            }
        });
    }

    public final boolean publish(Event event) {
        int i = 0;
        Assert.assertNotNull(event);
        LinkedList linkedList = (LinkedList) this.ay.get(event.getId());
        if (linkedList == null) {
            Log.w("MicroMsg.ObserverPool", "No listener for this event %s, Stack: %s.", r3, Util.getStack());
            return false;
        }
        if ((event.getFlag() & 1) != 0) {
            i = 1;
        }
        if (i != 0) {
            Collections.sort(linkedList, new Comparator<Listener>(this) {
                final /* synthetic */ ObserverPool az;

                {
                    this.az = r1;
                }

                public int compare(Listener listener, Listener listener2) {
                    return listener2.priority - listener.priority;
                }
            });
        }
        Iterator it = linkedList.iterator();
        while (it.hasNext() && (!((Listener) it.next()).callback(event) || i == 0)) {
        }
        event.onComplete();
        return true;
    }

    public final synchronized void release() {
        this.ay.clear();
    }

    public final synchronized boolean remove(String str) {
        Assert.assertNotNull(str);
        return this.ay.remove(str) != null;
    }

    public final synchronized boolean remove(String str, Listener listener) {
        boolean remove;
        Assert.assertNotNull(listener);
        LinkedList linkedList = (LinkedList) this.ay.get(str);
        remove = linkedList != null ? linkedList.remove(listener) : false;
        if (!remove) {
            Log.e("MicroMsg.ObserverPool", "Listener isnot existed in the map. EventId: %s, Stack: %s.", str, Util.getStack());
        }
        return remove;
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ObserverPool profile:\n");
        stringBuilder.append("\tEvent number: ").append(this.ay.size()).append(SpecilApiUtil.LINE_SEP);
        stringBuilder.append("\tDetail:\n");
        for (String str : this.ay.keySet()) {
            stringBuilder.append("\t").append(str).append(" : ").append(((LinkedList) this.ay.get(str)).size()).append(SpecilApiUtil.LINE_SEP);
        }
        stringBuilder.append("End...");
        return stringBuilder.toString();
    }
}
