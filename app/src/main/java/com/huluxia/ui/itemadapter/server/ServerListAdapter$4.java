package com.huluxia.ui.itemadapter.server;

import com.huluxia.data.server.a.a;
import java.util.Comparator;

class ServerListAdapter$4 implements Comparator<a> {
    final /* synthetic */ ServerListAdapter aWq;

    ServerListAdapter$4(ServerListAdapter this$0) {
        this.aWq = this$0;
    }

    public /* synthetic */ int compare(Object obj, Object obj2) {
        return a((a) obj, (a) obj2);
    }

    public int a(a b1, a b2) {
        return new Integer(b2.seq).compareTo(new Integer(b1.seq));
    }
}
