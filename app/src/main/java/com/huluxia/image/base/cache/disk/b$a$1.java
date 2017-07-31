package com.huluxia.image.base.cache.disk;

import com.huluxia.framework.base.utils.Supplier;
import com.huluxia.image.base.cache.disk.b.a;
import java.io.File;

/* compiled from: DiskCacheConfig */
class b$a$1 implements Supplier<File> {
    final /* synthetic */ a ul;

    b$a$1(a this$0) {
        this.ul = this$0;
    }

    public /* synthetic */ Object get() {
        return gL();
    }

    public File gL() {
        return a.k(this.ul).getApplicationContext().getCacheDir();
    }
}
