package com.huluxia;

import android.content.Context;
import com.huluxia.framework.base.utils.Preconditions;

/* compiled from: PainterConfig */
public class m {
    private final String gf;
    private final Context mContext;

    /* compiled from: PainterConfig */
    public static class a {
        private String gf;
        private final Context mContext;

        private a(Context context) {
            this.mContext = (Context) Preconditions.checkNotNull(context);
        }

        public a D(String diskCachePath) {
            this.gf = diskCachePath;
            return this;
        }

        public m cf() {
            return new m();
        }
    }

    private m(a builder) {
        this.mContext = (Context) Preconditions.checkNotNull(builder.mContext);
        this.gf = builder.gf;
    }

    public String ce() {
        return this.gf;
    }

    public Context getContext() {
        return this.mContext;
    }

    public static a ac(Context context) {
        return new a(context);
    }
}
