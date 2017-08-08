package com.MCWorld.framework.base.widget.hlistview;

import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;

public class AdapterView$AdapterContextMenuInfo implements ContextMenuInfo {
    public long id;
    public int position;
    public View targetView;

    public AdapterView$AdapterContextMenuInfo(View targetView, int position, long id) {
        this.targetView = targetView;
        this.position = position;
        this.id = id;
    }
}
