package com.huluxia.ui.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: LoadingLayoutInflater */
public abstract class b extends LayoutInflater {
    private LayoutInflater mInflater;

    abstract ViewGroup FE();

    abstract ViewGroup getContainer();

    protected b(Context context) {
        super(context);
        this.mInflater = LayoutInflater.from(context);
    }

    public LayoutInflater cloneInContext(Context newContext) {
        return null;
    }

    public View inflate(XmlPullParser parser, ViewGroup root) {
        return h(this.mInflater.inflate(parser, root));
    }

    public View inflate(XmlPullParser parser, ViewGroup root, boolean attachToRoot) {
        return h(this.mInflater.inflate(parser, root, attachToRoot));
    }

    public View inflate(int resource, ViewGroup root) {
        return h(this.mInflater.inflate(resource, root));
    }

    public View inflate(int resource, ViewGroup root, boolean attachToRoot) {
        return h(this.mInflater.inflate(resource, root, attachToRoot));
    }

    private ViewGroup h(View originRoot) {
        if (originRoot == null) {
            return null;
        }
        ViewGroup root = FE();
        getContainer().addView(originRoot);
        return root;
    }
}
