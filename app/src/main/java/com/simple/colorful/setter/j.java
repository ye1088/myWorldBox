package com.simple.colorful.setter;

import android.content.res.Resources.Theme;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import com.simple.colorful.b;
import com.simple.colorful.setter.f.a;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/* compiled from: ViewGroupSetter */
public class j extends k {
    protected Set<k> bIQ = new HashSet();

    public j(ViewGroup targetView, int resId) {
        super((View) targetView, resId);
    }

    public j(ViewGroup targetView) {
        super((View) targetView, 0);
    }

    public j bf(int viewId, int colorId) {
        this.bIQ.add(new h(viewId, colorId));
        return this;
    }

    public j k(View view, int colorId) {
        this.bIQ.add(new h(view, colorId));
        return this;
    }

    public j bg(int viewId, int drawableId) {
        this.bIQ.add(new i(viewId, drawableId));
        return this;
    }

    public j bh(int viewId, int colorId) {
        this.bIQ.add(new e(viewId, colorId));
        return this;
    }

    public j ac(int viewId, int attrId, @a int position) {
        this.bIQ.add(new f(viewId, attrId, position));
        return this;
    }

    public j bi(int viewId, int attrId) {
        this.bIQ.add(new c(viewId, attrId));
        return this;
    }

    public j a(b setter) {
        setter.a(this);
        return this;
    }

    public j bj(int viewId, int attrId) {
        this.bIQ.add(new b(viewId, attrId));
        return this;
    }

    public void e(Theme newTheme, int themeId) {
        this.mView.setBackgroundColor(a(newTheme));
        I(this.mView);
        J(this.mView);
        a((ViewGroup) this.mView, newTheme, themeId);
    }

    private View l(View rootView, int viewId) {
        View targetView = rootView.findViewById(viewId);
        Log.d("", "### viewgroup find view : " + targetView);
        return targetView;
    }

    private void a(ViewGroup viewGroup, Theme newTheme, int themeId) {
        int childCount = viewGroup.getChildCount();
        a((View) viewGroup, newTheme, themeId);
        for (int i = 0; i < childCount; i++) {
            View childView = viewGroup.getChildAt(i);
            if (childView instanceof ViewGroup) {
                a((ViewGroup) childView, newTheme, themeId);
            }
            a(childView, newTheme, themeId);
        }
    }

    private void a(View childView, Theme newTheme, int themeId) {
        for (k setter : this.bIQ) {
            if (childView.getId() == setter.bIR) {
                setter.mView = childView;
                setter.e(newTheme, themeId);
            }
        }
    }

    private void I(View rootView) {
        if (rootView instanceof AbsListView) {
            try {
                Field localField = AbsListView.class.getDeclaredField("mRecycler");
                localField.setAccessible(true);
                Method localMethod = Class.forName("android.widget.AbsListView$RecycleBin").getDeclaredMethod("clear", new Class[0]);
                localMethod.setAccessible(true);
                localMethod.invoke(localField.get(rootView), new Object[0]);
            } catch (NoSuchFieldException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e2) {
                e2.printStackTrace();
            } catch (NoSuchMethodException e3) {
                e3.printStackTrace();
            } catch (IllegalAccessException e4) {
                e4.printStackTrace();
            } catch (InvocationTargetException e5) {
                e5.printStackTrace();
            }
        }
    }

    private void J(View rootView) {
    }
}
