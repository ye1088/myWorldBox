package com.MCWorld.ui.itemadapter.category;

import com.MCWorld.framework.base.widget.dialog.DialogManager.OkCancelDialogListener;
import com.MCWorld.t;
import com.MCWorld.ui.itemadapter.category.CategoryVoteItemAdapter.a;

class CategoryVoteItemAdapter$a$1 implements OkCancelDialogListener {
    final /* synthetic */ a aSE;

    CategoryVoteItemAdapter$a$1(a this$1) {
        this.aSE = this$1;
    }

    public void onCancel() {
    }

    public void onOk() {
        t.an(CategoryVoteItemAdapter.a(this.aSE.aSD));
    }
}
