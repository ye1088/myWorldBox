package com.huluxia.ui.picture;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import com.huluxia.bbs.b.m;
import com.huluxia.module.picture.a;

class PictureChooserFragment$6 implements OnItemClickListener {
    final /* synthetic */ PictureChooserFragment bea;

    PictureChooserFragment$6(PictureChooserFragment this$0) {
        this.bea = this$0;
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (position == 0) {
            this.bea.Jb();
            PictureChooserFragment.i(this.bea).setText(m.all);
        } else {
            a bucket = (a) PictureChooserFragment.j(this.bea).getItem(position);
            PictureChooserFragment.i(this.bea).setText(bucket.bucketName);
            this.bea.a(bucket);
        }
        PictureChooserFragment.h(this.bea).dismiss();
    }
}
