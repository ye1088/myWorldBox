package com.MCWorld.mcsdk.crypto;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

/* compiled from: DTEnFileNormal */
public class e {
    private static e aox = null;
    private static final int aoy = 56;

    private e() {
    }

    public static e CS() {
        synchronized (e.class) {
            if (aox == null) {
                aox = new e();
            }
        }
        return aox;
    }

    public static boolean dM(String fileUrl) {
        dN(fileUrl);
        return true;
    }

    private static boolean dN(String strFile) {
        try {
            RandomAccessFile raf = new RandomAccessFile(new File(strFile), net.lingala.zip4j.util.e.cle);
            int len = (int) raf.length();
            FileChannel channel = raf.getChannel();
            MappedByteBuffer buffer = channel.map(MapMode.READ_WRITE, 0, (long) len);
            for (int i = 0; i < len; i++) {
                buffer.put(i, (byte) ((i + 9031) ^ buffer.get(i)));
            }
            buffer.force();
            buffer.clear();
            channel.close();
            raf.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
