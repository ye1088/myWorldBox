package com.MCWorld.image.base.cache.disk;

import com.MCWorld.image.base.cache.common.b;
import com.MCWorld.image.base.cache.common.i;
import com.MCWorld.image.core.common.disk.a;
import java.io.IOException;

/* compiled from: FileCache */
public interface h extends a {
    long N(long j);

    com.MCWorld.image.base.binaryresource.a a(b bVar, i iVar) throws IOException;

    void clearAll();

    com.MCWorld.image.base.binaryresource.a d(b bVar);

    boolean e(b bVar);

    void f(b bVar);

    boolean g(b bVar);

    long getCount();

    long getSize();

    c.a gs() throws IOException;

    boolean h(b bVar);

    boolean isEnabled();
}
