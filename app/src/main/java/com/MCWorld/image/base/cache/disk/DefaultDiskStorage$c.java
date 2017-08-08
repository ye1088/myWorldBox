package com.MCWorld.image.base.cache.disk;

import com.MCWorld.image.base.cache.disk.DefaultDiskStorage.FileType;
import io.netty.handler.codec.http.multipart.DiskFileUpload;
import java.io.File;
import java.io.IOException;
import javax.annotation.Nullable;

class DefaultDiskStorage$c {
    public final FileType tS;
    public final String tT;

    private DefaultDiskStorage$c(FileType type, String resourceId) {
        this.tS = type;
        this.tT = resourceId;
    }

    public String toString() {
        return this.tS + "(" + this.tT + ")";
    }

    public String bg(String parentPath) {
        return parentPath + File.separator + this.tT + this.tS.extension;
    }

    public File n(File parent) throws IOException {
        return File.createTempFile(this.tT + ".", DiskFileUpload.postfix, parent);
    }

    @Nullable
    public static DefaultDiskStorage$c o(File file) {
        String name = file.getName();
        int pos = name.lastIndexOf(46);
        if (pos <= 0) {
            return null;
        }
        FileType type = FileType.fromExtension(name.substring(pos));
        if (type == null) {
            return null;
        }
        String resourceId = name.substring(0, pos);
        if (type.equals(FileType.TEMP)) {
            int numPos = resourceId.lastIndexOf(46);
            if (numPos <= 0) {
                return null;
            }
            resourceId = resourceId.substring(0, numPos);
        }
        return new DefaultDiskStorage$c(type, resourceId);
    }
}
