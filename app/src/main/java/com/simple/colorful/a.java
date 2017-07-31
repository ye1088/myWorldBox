package com.simple.colorful;

import android.app.Activity;
import android.content.res.Resources.Theme;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.simple.colorful.setter.b;
import com.simple.colorful.setter.c;
import com.simple.colorful.setter.d;
import com.simple.colorful.setter.e;
import com.simple.colorful.setter.f;
import com.simple.colorful.setter.g;
import com.simple.colorful.setter.h;
import com.simple.colorful.setter.i;
import com.simple.colorful.setter.k;
import java.util.HashSet;
import java.util.Set;

/* compiled from: Colorful */
public final class a {
    a bIJ;

    /* compiled from: Colorful */
    public static class a {
        Set<k> bIK = new HashSet();
        Activity mActivity;

        public a(Activity activity) {
            this.mActivity = activity;
        }

        public a(Fragment fragment) {
            this.mActivity = fragment.getActivity();
        }

        private View findViewById(int viewId) {
            return this.mActivity.findViewById(viewId);
        }

        public a aY(int viewId, int colorId) {
            return i(findViewById(viewId), colorId);
        }

        public a i(View view, int colorId) {
            this.bIK.add(new h(view, colorId));
            return this;
        }

        public a aZ(int viewId, int drawableId) {
            j(findViewById(viewId), drawableId);
            return this;
        }

        public a j(View view, int drawableId) {
            this.bIK.add(new i(view, drawableId));
            return this;
        }

        public a ba(int viewId, int colorId) {
            return a((TextView) findViewById(viewId), colorId);
        }

        public a a(TextView textView, int colorId) {
            this.bIK.add(new e(textView, colorId));
            return this;
        }

        public a ab(int viewId, int attrId, @com.simple.colorful.setter.f.a int position) {
            return a((TextView) findViewById(viewId), attrId, position);
        }

        public a a(TextView textView, int attrId, @com.simple.colorful.setter.f.a int position) {
            this.bIK.add(new f(textView, attrId, position));
            return this;
        }

        public a bb(int viewId, int attrId) {
            return b((CheckBox) findViewById(viewId), attrId);
        }

        public a b(CheckBox checkBox, int attrId) {
            this.bIK.add(new com.simple.colorful.setter.a(checkBox, attrId));
            return this;
        }

        public a bc(int viewId, int attrId) {
            return c((ImageView) findViewById(viewId), attrId);
        }

        public a c(ImageView imageView, int attrId) {
            this.bIK.add(new b(imageView, attrId));
            return this;
        }

        public a bd(int viewId, int attrId) {
            return b((TextView) findViewById(viewId), attrId);
        }

        public a b(TextView textView, int attrId) {
            this.bIK.add(new g(textView, attrId));
            return this;
        }

        public a be(int iv_pic, int attrId) {
            return d((ImageView) findViewById(iv_pic), attrId);
        }

        public a d(ImageView imageView, int attrId) {
            this.bIK.add(new c(imageView, attrId));
            return this;
        }

        public a a(ProgressBar progressBar, int attrId, int width, int height) {
            this.bIK.add(new d(progressBar, attrId, width, height));
            return this;
        }

        public a a(k setter) {
            this.bIK.add(setter);
            return this;
        }

        public a a(c setter) {
            if (setter != null) {
                setter.b(this);
            }
            return this;
        }

        protected void setTheme(int newTheme) {
            this.mActivity.setTheme(newTheme);
            mC(newTheme);
        }

        private void mC(int themeId) {
            Theme curTheme = this.mActivity.getTheme();
            for (k setter : this.bIK) {
                setter.e(curTheme, themeId);
            }
        }

        public a Rz() {
            return new a();
        }
    }

    private a(a builder) {
        this.bIJ = builder;
    }

    public void setTheme(int newTheme) {
        this.bIJ.setTheme(newTheme);
    }
}
