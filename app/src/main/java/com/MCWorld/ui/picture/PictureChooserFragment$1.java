package com.MCWorld.ui.picture;

import android.view.View;
import android.view.View.OnClickListener;

class PictureChooserFragment$1 implements OnClickListener {
    final /* synthetic */ PictureChooserFragment bea;

    PictureChooserFragment$1(PictureChooserFragment this$0) {
        this.bea = this$0;
    }

    public void onClick(View v) {
        this.bea.getActivity().finish();
    }
}
