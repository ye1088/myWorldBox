package com.huluxia.framework.base.http.toolbox.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileAdapter extends RandomAccessFile implements IAdapterToStreamAndRaf {
    public RandomAccessFileAdapter(File file, String mode) throws FileNotFoundException {
        super(file, mode);
    }

    public RandomAccessFileAdapter(String fileName, String mode) throws FileNotFoundException {
        super(fileName, mode);
    }

    public void flush() throws IOException {
    }

    public void close() throws IOException {
        super.close();
    }
}
