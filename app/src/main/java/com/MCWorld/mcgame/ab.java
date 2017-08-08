package com.MCWorld.mcgame;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.opengl.GLES10;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

/* compiled from: DTScreenShot */
public class ab {
    private static Bitmap abr;
    private static boolean agd = true;
    private static volatile int agf;
    private static volatile int agg;
    private static boolean agh = false;
    private static String agi;
    private static volatile boolean agj = false;
    private static Object agk = new Object();
    private static boolean agl = false;
    private static int agm = 0;
    private static String agn;

    public static boolean yA() {
        return agh;
    }

    public static void c(boolean screenshotOverFlag, String in_ScreenshotPath) {
        agh = screenshotOverFlag;
        agi = in_ScreenshotPath;
    }

    private static boolean yB() {
        return agj;
    }

    public static boolean a(boolean startScreenshotFlag, int in_icon_x, int in_icon_y) {
        if (g.wc() != 1) {
            return false;
        }
        agj = startScreenshotFlag;
        agf = in_icon_x;
        agg = in_icon_y;
        return true;
    }

    public static void yC() {
        yD();
    }

    public static Bitmap v(String fullPathName, int inputFlag) {
        synchronized (agk) {
            agn = fullPathName;
            agm = inputFlag;
            agl = true;
            try {
                agk.wait(100);
            } catch (InterruptedException e) {
                return null;
            }
        }
        return abr;
    }

    public static void w(String inputFullPathName, int inputFlag) {
        try {
            x(inputFullPathName, inputFlag);
        } catch (Exception e) {
        }
    }

    public static void yD() {
        if (agl) {
            synchronized (agk) {
                abr = null;
                w(agn, agm);
                agl = false;
                agk.notify();
            }
        }
    }

    private static boolean x(String inputFullPathName, int inputFlag) {
        IOException e;
        Throwable th;
        int[] screenDim = new int[4];
        GLES10.glGetIntegerv(2978, screenDim, 0);
        ByteBuffer buf = ByteBuffer.allocateDirect((screenDim[2] * screenDim[3]) * 4);
        GLES10.glReadPixels(screenDim[0], screenDim[1], screenDim[2], screenDim[3], 6408, 5121, buf);
        int width = screenDim[2];
        int height = screenDim[3];
        Bitmap bmp = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        buf.rewind();
        byte[] rowBuffer = new byte[((width * 4) * 2)];
        int stride = width * 4;
        for (int y = 0; y < height / 2; y++) {
            buf.position(y * stride);
            buf.get(rowBuffer, 0, stride);
            buf.position(((height - y) - 1) * stride);
            buf.get(rowBuffer, stride, stride);
            buf.position(((height - y) - 1) * stride);
            buf.put(rowBuffer, 0, stride);
            buf.position(y * stride);
            buf.put(rowBuffer, stride, stride);
        }
        buf.rewind();
        bmp.copyPixelsFromBuffer(buf);
        if (inputFullPathName == null) {
            try {
                abr = b(bmp, bmp.getWidth(), bmp.getHeight());
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            return true;
        }
        File file = new File(inputFullPathName);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    file.setReadable(true);
                    file.setWritable(true);
                }
            } catch (IOException e3) {
                e3.printStackTrace();
                return false;
            }
        }
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fos = new FileOutputStream(file);
            if (inputFlag == 0) {
                try {
                    abr = ScaleBitmap(bmp, 300, 180);
                } catch (IOException e4) {
                    e3 = e4;
                    fileOutputStream = fos;
                    try {
                        e3.printStackTrace();
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e5) {
                            }
                        }
                        return true;
                    } catch (Throwable th2) {
                        th = th2;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e6) {
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    fileOutputStream = fos;
                    if (fileOutputStream != null) {
                        fileOutputStream.close();
                    }
                    throw th;
                }
            }
            abr = b(bmp, bmp.getWidth(), bmp.getHeight());
            abr.compress(CompressFormat.PNG, 70, fos);
            fos.flush();
            fos.close();
            if (fos != null) {
                try {
                    fos.close();
                    fileOutputStream = fos;
                } catch (IOException e7) {
                    fileOutputStream = fos;
                }
            }
        } catch (IOException e8) {
            e3 = e8;
            e3.printStackTrace();
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            return true;
        }
        return true;
    }

    public static Bitmap b(Bitmap inputMap, int draw_width, int draw_height) {
        Bitmap bitmap = Bitmap.createBitmap(draw_width, draw_height, inputMap.getConfig());
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAlpha(255);
        canvas.drawBitmap(inputMap, new Matrix(), paint);
        return bitmap;
    }

    private static Bitmap ScaleBitmap(Bitmap bm, int outputX, int outputY) {
        float oldWidth = (float) bm.getWidth();
        float oldHeight = (float) bm.getHeight();
        float scale = oldWidth / oldHeight;
        float newWidth = oldWidth;
        float newHeight = oldHeight;
        boolean isScale = false;
        if (scale >= 1.0f) {
            if (oldWidth > ((float) outputX)) {
                newWidth = (float) outputX;
                newHeight = newWidth / scale;
                isScale = true;
            }
        } else if (oldHeight > ((float) outputY)) {
            newHeight = (float) outputY;
            newWidth = newHeight * scale;
            isScale = true;
        }
        if (!isScale) {
            return bm;
        }
        Matrix matrix = new Matrix();
        matrix.setScale(newWidth / oldWidth, newHeight / oldHeight);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bm, 0, 0, (int) oldWidth, (int) oldHeight, matrix, true);
        bm.recycle();
        return rotatedBitmap;
    }

    private static String y(String pathandname, int flag) {
        if (pathandname == null) {
            return null;
        }
        int start = pathandname.lastIndexOf("/");
        int end = pathandname.lastIndexOf(".");
        if (flag == 1) {
            end = pathandname.length();
        }
        if (start == -1 || end == -1) {
            return null;
        }
        return pathandname.substring(start + 1, end);
    }
}
