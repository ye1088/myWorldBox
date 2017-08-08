package com.MCWorld.ui.base;

import android.app.Activity;
import android.content.Context;
import java.util.Iterator;
import java.util.Stack;

/* compiled from: AppManager */
public class a {
    private static Stack<Activity> aIj;
    private static a aIk;

    private a() {
        aIj = new Stack();
    }

    public static a Ft() {
        if (aIk == null) {
            aIk = new a();
        }
        return aIk;
    }

    public void n(Activity activity) {
        aIj.add(activity);
    }

    public Activity Fu() {
        return (Activity) aIj.lastElement();
    }

    public void Fv() {
        if (aIj != null && !aIj.isEmpty()) {
            p((Activity) aIj.lastElement());
        }
    }

    public void o(Activity activity) {
        if (activity != null) {
            aIj.remove(activity);
        }
    }

    public void p(Activity activity) {
        if (activity != null) {
            aIj.remove(activity);
            activity.finish();
        }
    }

    public void d(Class<?> cls) {
        Iterator it = aIj.iterator();
        while (it.hasNext()) {
            Activity activity = (Activity) it.next();
            if (activity.getClass().equals(cls)) {
                p(activity);
            }
        }
    }

    public void Fw() {
        int size = aIj.size();
        for (int i = 0; i < size; i++) {
            if (aIj.get(i) != null) {
                ((Activity) aIj.get(i)).finish();
            }
        }
        aIj.clear();
    }

    public void bd(Context context) {
        try {
            Fw();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
