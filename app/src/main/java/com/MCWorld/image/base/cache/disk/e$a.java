package com.MCWorld.image.base.cache.disk;

import com.MCWorld.framework.base.utils.VisibleForTesting;
import java.io.File;
import javax.annotation.Nullable;

@VisibleForTesting
/* compiled from: DynamicDefaultDiskStorage */
class e$a {
    @Nullable
    public final File rootDirectory;
    @Nullable
    public final c uJ;

    @VisibleForTesting
    e$a(@Nullable File rootDirectory, @Nullable c delegate) {
        this.uJ = delegate;
        this.rootDirectory = rootDirectory;
    }
}
