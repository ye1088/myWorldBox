package com.MCWorld.utils;

import com.MCWorld.data.topic.SimpleTopicItem;
import java.util.Comparator;

/* compiled from: UtilsReadingHistory */
public class ao$a implements Comparator<SimpleTopicItem> {
    public /* synthetic */ int compare(Object obj, Object obj2) {
        return a((SimpleTopicItem) obj, (SimpleTopicItem) obj2);
    }

    public int a(SimpleTopicItem object1, SimpleTopicItem object2) {
        return Double.compare((double) object2.activeTime, (double) object1.activeTime);
    }
}
