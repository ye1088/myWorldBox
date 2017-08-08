package com.MCWorld.widget.emoInput;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import java.util.List;

/* compiled from: FaceManager */
public class d {
    private static d byq;
    private c byr = new c();
    private a bys = new a();

    public static d Ou() {
        if (byq == null) {
            byq = new d();
        }
        return byq;
    }

    private d() {
    }

    public int gO(String code) {
        return this.byr.gO(code);
    }

    public List<String> getTags() {
        return this.byr.getTags();
    }

    public Spannable e(Context context, String text, int faceWidth) {
        return c(context, text, faceWidth, 1);
    }

    public int gN(String text) {
        return this.bys.gN(text) + this.byr.gN(text);
    }

    public Spannable c(Context context, String text, int faceWidth, int verticalAlignment) {
        Spannable sp = new SpannableString(text);
        this.bys.a(context, sp, text, faceWidth, verticalAlignment);
        this.byr.a(context, sp, text, faceWidth, verticalAlignment);
        return sp;
    }
}
