package com.huluxia.mcgame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import net.zhuoweizhang.mcpelauncher.h;

/* compiled from: DTPlayerSkin */
public class z implements h {
    private static String afW = null;
    private static volatile boolean afX = false;
    private static int afY = 0;

    public static String yr() {
        return afW;
    }

    public static void cP(String skinPath) {
        afW = skinPath;
    }

    public static boolean ys() {
        return afX;
    }

    public static void bF(boolean usehlxskin) {
        afX = usehlxskin;
    }

    public static int yt() {
        return afY;
    }

    public static void gW(int loadSkinCnt) {
        afY = loadSkinCnt;
    }

    public static void yu() {
        afY++;
    }

    public static InputStream cQ(String fileName) {
        try {
            String tmpSkinPath = yr();
            if (com.huluxia.mcsdk.dtlib.h.CW().CX() != 1 && com.huluxia.mcsdk.dtlib.h.CW().CX() != 2 && com.huluxia.mcsdk.dtlib.h.CW().CX() != 3 && com.huluxia.mcsdk.dtlib.h.CW().CX() != 5) {
                return null;
            }
            File file;
            if (com.huluxia.mcsdk.dtlib.h.CW().CX() == 3 || com.huluxia.mcsdk.dtlib.h.CW().CX() == 5 || com.huluxia.mcsdk.dtlib.h.CW().CX() == 7) {
                if (!ys() || !fileName.endsWith("custom.png")) {
                    return null;
                }
                file = new File(tmpSkinPath);
                if (file.exists()) {
                    return new FileInputStream(file);
                }
                return null;
            } else if (!ys() || !fileName.endsWith("games/com.mojang/minecraftpe/custom.png")) {
                return null;
            } else {
                file = new File(tmpSkinPath);
                if (file.exists()) {
                    return new FileInputStream(file);
                }
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static int[] cR(String paramString) {
        try {
            InputStream localInputStream = cQ(paramString);
            if (localInputStream == null) {
                return null;
            }
            Bitmap localBitmap = BitmapFactory.decodeStream(localInputStream);
            int[] arrayOfInt = new int[((localBitmap.getWidth() * localBitmap.getHeight()) + 2)];
            arrayOfInt[0] = localBitmap.getWidth();
            arrayOfInt[1] = localBitmap.getHeight();
            localBitmap.getPixels(arrayOfInt, 2, localBitmap.getWidth(), 0, 0, localBitmap.getWidth(), localBitmap.getHeight());
            localInputStream.close();
            localBitmap.recycle();
            return arrayOfInt;
        } catch (Exception e) {
            return null;
        }
    }

    public InputStream cN(String fileName) {
        try {
            String tmpSkinPath = yr();
            if (com.huluxia.mcsdk.dtlib.h.CW().CX() != 0 || tmpSkinPath == null || !fileName.equals("images/mob/char.png")) {
                return null;
            }
            File file = new File(tmpSkinPath);
            if (file.exists()) {
                return new FileInputStream(file);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    public long getSize(String paramString) throws IOException {
        return 0;
    }

    public void close() throws IOException {
    }

    public List<String> ww() throws IOException {
        return null;
    }
}
