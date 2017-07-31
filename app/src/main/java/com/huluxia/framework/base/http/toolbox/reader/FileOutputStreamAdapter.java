package com.huluxia.framework.base.http.toolbox.reader;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileOutputStreamAdapter extends FileOutputStream implements IAdapterToStreamAndRaf {
    public FileOutputStreamAdapter(File file) throws FileNotFoundException {
        super(file);
    }

    public FileOutputStreamAdapter(File file, boolean append) throws FileNotFoundException {
        super(file, append);
    }

    public FileOutputStreamAdapter(FileDescriptor fd) {
        super(fd);
    }

    public FileOutputStreamAdapter(String path) throws FileNotFoundException {
        super(path);
    }

    public FileOutputStreamAdapter(String path, boolean append) throws FileNotFoundException {
        super(path, append);
    }

    public void flush() throws IOException {
        super.flush();
    }
}
