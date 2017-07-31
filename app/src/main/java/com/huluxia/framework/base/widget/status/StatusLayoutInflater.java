package com.huluxia.framework.base.widget.status;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.xmlpull.v1.XmlPullParser;

public class StatusLayoutInflater extends LayoutInflater {
    private LayoutInflater mInflater;

    protected StatusLayoutInflater(Context context) {
        super(context);
        this.mInflater = LayoutInflater.from(context);
    }

    public LayoutInflater cloneInContext(Context newContext) {
        return null;
    }

    public View inflate(XmlPullParser parser, ViewGroup root) {
        return getStatus(this.mInflater.inflate(parser, root));
    }

    public View inflate(XmlPullParser parser, ViewGroup root, boolean attachToRoot) {
        return getStatus(this.mInflater.inflate(parser, root, attachToRoot));
    }

    public View inflate(int resource, ViewGroup root) {
        return getStatus(this.mInflater.inflate(resource, root));
    }

    public View inflate(int resource, ViewGroup root, boolean attachToRoot) {
        return getStatus(this.mInflater.inflate(resource, root, attachToRoot));
    }

    private ViewGroup getStatus(View originRoot) {
        if (originRoot == null) {
            return null;
        }
        return StatusLayout.wrap(originRoot);
    }
}
