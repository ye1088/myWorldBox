package com.MCWorld.ui.picture;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.MCWorld.module.picture.b;

class PictureChooserFragment$2 implements OnItemClickListener {
    final /* synthetic */ PictureChooserFragment bea;

    PictureChooserFragment$2(PictureChooserFragment this$0) {
        this.bea = this$0;
    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0 && PictureChooserFragment.a(this.bea)) {
            PictureChooserFragment.b(this.bea);
        } else if (PictureChooserFragment.c(this.bea) == 1) {
            PictureChooserFragment.d(this.bea).kM(position);
        } else if (PictureChooserFragment.e(this.bea) != null) {
            PictureChooserFragment.e(this.bea).d((b) parent.getAdapter().getItem(position));
        }
    }
}
