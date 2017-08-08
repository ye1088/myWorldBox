package com.MCWorld.framework.base.widget.hlistview.util.v11;

import android.annotation.TargetApi;
import android.view.ActionMode;
import android.view.ActionMode.Callback;

@TargetApi(11)
public interface MultiChoiceModeListener extends Callback {
    void onItemCheckedStateChanged(ActionMode actionMode, int i, long j, boolean z);
}
