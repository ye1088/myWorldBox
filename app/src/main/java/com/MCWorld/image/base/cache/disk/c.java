package com.MCWorld.image.base.cache.disk;

import com.MCWorld.image.base.cache.common.i;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: DiskStorage */
public interface c {

    /* compiled from: DiskStorage */
    public interface c {
        String getId();

        long getSize();

        long getTimestamp();

        com.MCWorld.image.base.binaryresource.a gx();
    }

    /* compiled from: DiskStorage */
    public interface d {
        void a(i iVar, Object obj) throws IOException;

        boolean gy();

        com.MCWorld.image.base.binaryresource.a h(Object obj) throws IOException;
    }

    /* compiled from: DiskStorage */
    public static class a {
        public List<b> entries = new ArrayList();
        public Map<String, Integer> um = new HashMap();
    }

    /* compiled from: DiskStorage */
    public static class b {
        public final String path;
        public final float size;
        public final String type;
        public final String un;

        protected b(String path, String type, float size, String firstBits) {
            this.path = path;
            this.type = type;
            this.size = size;
            this.un = firstBits;
        }
    }

    long a(c cVar) throws IOException;

    long bf(String str) throws IOException;

    d c(String str, Object obj) throws IOException;

    void clearAll() throws IOException;

    com.MCWorld.image.base.binaryresource.a d(String str, Object obj) throws IOException;

    boolean e(String str, Object obj) throws IOException;

    boolean f(String str, Object obj) throws IOException;

    boolean go();

    String gp();

    void gr();

    a gs() throws IOException;

    Collection<c> gu() throws IOException;

    boolean isEnabled();
}
