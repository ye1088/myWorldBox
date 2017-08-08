package com.MCWorld.ui.picture;

import com.MCWorld.framework.base.async.AsyncTaskCenter.RunnableCallback;

class PictureChooserFragment$7 implements RunnableCallback {
    final /* synthetic */ PictureChooserFragment bea;

    PictureChooserFragment$7(PictureChooserFragment this$0) {
        this.bea = this$0;
    }

    public void onCallback() {
        this.bea.getActivity().runOnUiThread(new Runnable(this) {
            final /* synthetic */ PictureChooserFragment$7 beb;

            {
                this.beb = this$1;
            }

            public void run() {
                PictureChooserFragment.k(this.beb.bea);
            }
        });
    }
}
