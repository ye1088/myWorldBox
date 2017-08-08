package com.MCWorld.widget.menudrawer;

import android.view.ViewTreeObserver.OnScrollChangedListener;

class MenuDrawer$2 implements OnScrollChangedListener {
    final /* synthetic */ MenuDrawer bBM;

    MenuDrawer$2(MenuDrawer this$0) {
        this.bBM = this$0;
    }

    public void onScrollChanged() {
        if (this.bBM.bBg != null && this.bBM.z(this.bBM.bBg)) {
            this.bBM.bBg.getDrawingRect(MenuDrawer.b(this.bBM));
            this.bBM.offsetDescendantRectToMyCoords(this.bBM.bBg, MenuDrawer.b(this.bBM));
            if (MenuDrawer.b(this.bBM).left != this.bBM.bBj.left || MenuDrawer.b(this.bBM).top != this.bBM.bBj.top || MenuDrawer.b(this.bBM).right != this.bBM.bBj.right || MenuDrawer.b(this.bBM).bottom != this.bBM.bBj.bottom) {
                this.bBM.invalidate();
            }
        }
    }
}
