package com.huluxia.image.base.binaryresource;

import com.huluxia.framework.base.utils.Files;
import com.huluxia.framework.base.utils.Preconditions;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/* compiled from: FileBinaryResource */
public class c implements a {
    private final File mFile;

    private c(File file) {
        this.mFile = (File) Preconditions.checkNotNull(file);
    }

    public File getFile() {
        return this.mFile;
    }

    public InputStream openStream() throws IOException {
        return new FileInputStream(this.mFile);
    }

    public long size() {
        return this.mFile.length();
    }

    public byte[] ga() throws IOException {
        return Files.toByteArray(this.mFile);
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof c)) {
            return false;
        }
        return this.mFile.equals(((c) obj).mFile);
    }

    public int hashCode() {
        return this.mFile.hashCode();
    }

    public static c h(File file) {
        return file != null ? new c(file) : null;
    }
}
