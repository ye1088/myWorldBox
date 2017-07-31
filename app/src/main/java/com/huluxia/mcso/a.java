package com.huluxia.mcso;

import android.content.Context;
import com.huluxia.mcsdk.DTSDKManagerEx;
import com.tencent.smtt.sdk.TbsReaderView.ReaderCallback;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import net.lingala.zip4j.util.e;

/* compiled from: DTSpirit */
public class a {
    private static final boolean DEBUG = false;
    private static final int aoy = 32000;
    private static String apb;
    private static Context context;

    public static void load(String soPath) {
        System.loadLibrary("mcspirit");
    }

    public static void init(Context in_context) {
        context = in_context;
    }

    private static String Dg() {
        return apb;
    }

    private static void dX(String dstPath) {
        apb = dstPath;
    }

    private static void Dh() {
        try {
            File file = new File(Dg());
            InputStream in = context.getResources().getAssets().open("data/100009");
            FileOutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            while (true) {
                int length = in.read(buf);
                if (length != -1) {
                    out.write(buf, 0, length);
                } else {
                    out.flush();
                    out.flush();
                    in.close();
                    out.close();
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void n(byte[] inputByteArray, int inputDecodeLen) {
        for (int i = 0; i < inputDecodeLen; i++) {
            byte tmp = (byte) (inputByteArray[i] ^ (i + ReaderCallback.READER_PLUGIN_DOWNLOADING));
            if (i % 3 == 0) {
                if (i % 4 == 0) {
                    tmp = DTSDKManagerEx.h(tmp);
                } else {
                    tmp = DTSDKManagerEx.g(tmp);
                }
            }
            inputByteArray[i] = tmp;
        }
    }

    private static boolean Di() {
        try {
            RandomAccessFile raf = new RandomAccessFile(new File(Dg()), e.cle);
            int len = (int) raf.length();
            FileChannel channel = raf.getChannel();
            MappedByteBuffer buffer = channel.map(MapMode.READ_WRITE, 0, (long) len);
            for (int i = 0; i < len; i++) {
                byte tmp;
                byte rawByte = buffer.get(i);
                if (i < aoy) {
                    tmp = (byte) ((i + 9001) ^ rawByte);
                } else {
                    tmp = rawByte;
                }
                buffer.put(i, tmp);
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
