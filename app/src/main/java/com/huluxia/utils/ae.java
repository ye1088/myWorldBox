package com.huluxia.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.util.Log;
import com.huluxia.HTApplication;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/* compiled from: UtilsImage */
public class ae {
    private static int sc_height;
    private static int sc_width;

    static {
        sc_width = 0;
        sc_height = 0;
        sc_width = HTApplication.getAppContext().getResources().getDisplayMetrics().widthPixels;
        sc_height = HTApplication.getAppContext().getResources().getDisplayMetrics().heightPixels;
    }

    public static boolean setLocalBitmap(Bitmap bitmap, String path) {
        try {
            File file = new File(path);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(CompressFormat.PNG, 100, fos);
            fos.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static Bitmap getLocalBitMap(String path) {
        Bitmap bitmap = null;
        if (UtilsFile.isExist(path)) {
            try {
                bitmap = BitmapFactory.decodeStream(new FileInputStream(path));
            } catch (FileNotFoundException e) {
            }
        }
        return bitmap;
    }

    public static Bitmap ScaleBitmap(Bitmap bm, int outputX, int outputY) {
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

    private static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        if (height <= reqHeight && width <= reqWidth) {
            return 1;
        }
        int heightRatio = Math.round(((float) height) / ((float) reqHeight));
        int widthRatio = Math.round(((float) width) / ((float) reqWidth));
        int inSampleSize = widthRatio;
        if (heightRatio > widthRatio) {
            return heightRatio;
        }
        return inSampleSize;
    }

    public static Options getOptions(byte[] data) {
        Options opts = new Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, opts);
        if (opts.outMimeType == null || opts.outMimeType.equals("image/jpeg")) {
            opts.inPreferredConfig = Config.RGB_565;
        }
        opts.inSampleSize = calculateInSampleSize(opts, (int) (((float) sc_width) * 1.2f), (int) (((float) sc_height) * 1.2f));
        opts.inJustDecodeBounds = false;
        return opts;
    }

    public static Bitmap decodeByteArray(byte[] data) {
        Options opts = getOptions(data);
        Bitmap bmp = null;
        int i = 0;
        while (i < 2) {
            try {
                bmp = BitmapFactory.decodeByteArray(data, 0, data.length, opts);
                break;
            } catch (OutOfMemoryError e) {
                Log.i("OutOfMemoryError", "out of memory, clearing mem cache");
                bmp = null;
                i++;
            }
        }
        return bmp;
    }

    public static byte[] bmpToByteArray(Bitmap bmp, boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    private static int getRotate(String path) {
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (exif == null) {
            return -1;
        }
        switch (exif.getAttributeInt("Orientation", 1)) {
            case 3:
                return 180;
            case 6:
                return 90;
            case 8:
                return 270;
            default:
                return -1;
        }
    }

    public static String ScaleBitmapFile(String path, int ScaleWidth, int ScaleHeight) {
        Options opts = new Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, opts);
        opts.inPreferredConfig = Config.RGB_565;
        opts.inJustDecodeBounds = false;
        opts.inSampleSize = calculateInSampleSize(opts, ScaleWidth, ScaleHeight);
        Bitmap bmp = BitmapFactory.decodeFile(path, opts);
        if (bmp == null) {
            return path;
        }
        int rotate = getRotate(path);
        if (rotate != -1) {
            Matrix matrix = new Matrix();
            matrix.postRotate((float) rotate);
            Bitmap rotatedBitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
            bmp.recycle();
            bmp = rotatedBitmap;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmp.compress(CompressFormat.JPEG, 85, bos);
        byte[] bitmapdata = bos.toByteArray();
        String filePath = UtilsFile.getTempFileName();
        UtilsFile.saveBytesToSD(filePath, bitmapdata);
        bmp.recycle();
        return filePath;
    }
}
