package com.MCWorld.widget.dialog;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

class MenuItemArrayAdapter$1 implements OnCheckedChangeListener {
    final /* synthetic */ MenuItemArrayAdapter bxC;

    MenuItemArrayAdapter$1(MenuItemArrayAdapter this$0) {
        this.bxC = this$0;
    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        this.bxC.checked = isChecked ? 1 : 0;
    }
}
