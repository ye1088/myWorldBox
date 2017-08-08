package com.MCWorld.widget.caseview;

import android.content.Context;
import android.graphics.RectF;
import android.view.View;
import com.MCWorld.widget.caseview.highlight.c;
import com.MCWorld.widget.caseview.target.b;

/* compiled from: Case */
public class a {
    private int bvA;
    private c bvB;
    private b bvz;

    /* compiled from: Case */
    public static class a {
        private a bvC = new a();

        public a s(View target) {
            this.bvC.a(new com.MCWorld.widget.caseview.target.c(target));
            return this;
        }

        public a a(RectF rectF, Context context) {
            this.bvC.a(new com.MCWorld.widget.caseview.target.a(rectF, context));
            return this;
        }

        public a lP(int imgRes) {
            this.bvC.lO(imgRes);
            return this;
        }

        public a b(c highLight) {
            this.bvC.a(highLight);
            return this;
        }

        public a Oe() {
            if (this.bvC.Ob() == null || this.bvC.Oc() == 0 || this.bvC.Od() == null) {
                return null;
            }
            return this.bvC;
        }
    }

    private a() {
    }

    public void a(c highLight) {
        this.bvB = highLight;
    }

    public void lO(int imageRes) {
        this.bvA = imageRes;
    }

    public void a(b target) {
        this.bvz = target;
    }

    public a(RectF rectF, int imgRes, boolean circle) {
    }

    public b Ob() {
        return this.bvz;
    }

    public int Oc() {
        return this.bvA;
    }

    public c Od() {
        return this.bvB;
    }
}
