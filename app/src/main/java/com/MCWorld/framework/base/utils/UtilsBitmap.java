package com.MCWorld.framework.base.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.support.annotation.y;
import android.support.annotation.z;
import android.support.v4.graphics.BitmapCompat;
import android.util.Log;
import com.MCWorld.module.n;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class UtilsBitmap {
    private static final String TAG = "UtilsBitmap";

    public static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
                inSampleSize *= 2;
            }
            for (long totalPixels = (long) ((width * height) / inSampleSize); totalPixels > ((long) ((reqWidth * reqHeight) * 2)); totalPixels /= 2) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    public static void saveBitmap(Bitmap bmp, String filePath) {
        Exception e;
        Throwable th;
        if (bmp != null && filePath != null) {
            FileOutputStream out = null;
            try {
                FileOutputStream out2 = new FileOutputStream(filePath);
                try {
                    bmp.compress(CompressFormat.PNG, 100, out2);
                    if (out2 != null) {
                        try {
                            out2.close();
                        } catch (IOException e2) {
                            Log.e(TAG, "Failed to save bitmap", e2);
                            out = out2;
                            return;
                        }
                    }
                    out = out2;
                } catch (Exception e3) {
                    e = e3;
                    out = out2;
                    try {
                        Log.e(TAG, "Failed to save bitmap", e);
                        if (out != null) {
                            try {
                                out.close();
                            } catch (IOException e22) {
                                Log.e(TAG, "Failed to save bitmap", e22);
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (out != null) {
                            try {
                                out.close();
                            } catch (IOException e222) {
                                Log.e(TAG, "Failed to save bitmap", e222);
                            }
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    out = out2;
                    if (out != null) {
                        out.close();
                    }
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
                Log.e(TAG, "Failed to save bitmap", e);
                if (out != null) {
                    out.close();
                }
            }
        }
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        try {
            Bitmap bitmap = Bitmap.createBitmap(Math.max(drawable.getIntrinsicWidth(), 1), Math.max(drawable.getIntrinsicHeight(), 1), Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            Log.w(TAG, "Failed to create bitmap from drawable!");
            return null;
        }
    }

    public static long sizeOf(@y Bitmap bitmap) {
        return (long) BitmapCompat.getAllocationByteCount(bitmap);
    }

    @z
    public static String compressFileBitmapToDisk(@y String src, @z String des, long maxDiskDize) {
        IOException e;
        BufferedOutputStream bos;
        FileNotFoundException e2;
        Throwable th;
        OutputStream outputStream;
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(src, options);
        options.inSampleSize = calculateInSampleSize(options, n.avC, n.avC);
        options.inJustDecodeBounds = false;
        options.inPreferredConfig = Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(src, options);
        if (bitmap == null) {
            return null;
        }
        int rotate = 0;
        try {
            rotate = getFileExifRotation(Uri.fromFile(new File(src)));
        } catch (IOException e3) {
            Log.e("compress", "get file rotate error " + e3);
        }
        if (rotate > 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate((float) rotate);
            Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            bitmap = rotatedBitmap;
        }
        long bitmapSize = sizeOf(bitmap);
        Log.d("compress", "bitmap size " + bitmapSize + ", max " + maxDiskDize + ", inSampleSize " + options.inSampleSize);
        String filePath;
        OutputStream fileOutputStream;
        if (bitmapSize / 4 < maxDiskDize) {
            BufferedOutputStream bos2;
            Log.d("compress", "bitmap smaller than max " + (bitmapSize / 4));
            bos = null;
            if (des == null) {
                try {
                    filePath = UtilsFile.getTempFileName();
                } catch (FileNotFoundException e4) {
                    e2 = e4;
                    try {
                        e2.printStackTrace();
                        if (bos != null) {
                            try {
                                bos.flush();
                                bos.close();
                            } catch (IOException e32) {
                                e32.printStackTrace();
                            }
                        }
                        bitmap.recycle();
                        return null;
                    } catch (Throwable th2) {
                        th = th2;
                        if (bos != null) {
                            try {
                                bos.flush();
                                bos.close();
                            } catch (IOException e322) {
                                e322.printStackTrace();
                            }
                        }
                        bitmap.recycle();
                        throw th;
                    }
                }
            }
            filePath = des;
            fileOutputStream = new FileOutputStream(filePath);
            try {
                bos2 = new BufferedOutputStream(fileOutputStream);
            } catch (FileNotFoundException e5) {
                e2 = e5;
                outputStream = fileOutputStream;
                e2.printStackTrace();
                if (bos != null) {
                    bos.flush();
                    bos.close();
                }
                bitmap.recycle();
                return null;
            } catch (Throwable th3) {
                th = th3;
                outputStream = fileOutputStream;
                if (bos != null) {
                    bos.flush();
                    bos.close();
                }
                bitmap.recycle();
                throw th;
            }
            try {
                bitmap.compress(CompressFormat.JPEG, 100, bos2);
                if (bos2 != null) {
                    try {
                        bos2.flush();
                        bos2.close();
                    } catch (IOException e3222) {
                        e3222.printStackTrace();
                    }
                }
                bitmap.recycle();
                return filePath;
            } catch (FileNotFoundException e6) {
                e2 = e6;
                bos = bos2;
                outputStream = fileOutputStream;
                e2.printStackTrace();
                if (bos != null) {
                    bos.flush();
                    bos.close();
                }
                bitmap.recycle();
                return null;
            } catch (Throwable th4) {
                th = th4;
                bos = bos2;
                outputStream = fileOutputStream;
                if (bos != null) {
                    bos.flush();
                    bos.close();
                }
                bitmap.recycle();
                throw th;
            }
        }
        bos = new ByteArrayOutputStream();
        int quality = 95;
        bitmap.compress(CompressFormat.JPEG, 95, bos);
        while (((long) bos.size()) > maxDiskDize && quality > 0) {
            bos.reset();
            quality -= 5;
            bitmap.compress(CompressFormat.JPEG, quality, bos);
        }
        Log.d("compress", "bitmap quality " + quality);
        BufferedOutputStream bufferOs = null;
        if (des == null) {
            try {
                filePath = UtilsFile.getTempFileName();
            } catch (IOException e7) {
                e3222 = e7;
                try {
                    e3222.printStackTrace();
                    if (bufferOs != null) {
                        try {
                            bufferOs.flush();
                            bufferOs.close();
                        } catch (IOException e32222) {
                            e32222.printStackTrace();
                        }
                    }
                    bitmap.recycle();
                    return null;
                } catch (Throwable th5) {
                    th = th5;
                    if (bufferOs != null) {
                        try {
                            bufferOs.flush();
                            bufferOs.close();
                        } catch (IOException e322222) {
                            e322222.printStackTrace();
                        }
                    }
                    bitmap.recycle();
                    throw th;
                }
            }
        }
        filePath = des;
        fileOutputStream = new FileOutputStream(filePath);
        try {
            BufferedOutputStream bufferOs2 = new BufferedOutputStream(fileOutputStream);
        } catch (IOException e8) {
            e322222 = e8;
            outputStream = fileOutputStream;
            e322222.printStackTrace();
            if (bufferOs != null) {
                bufferOs.flush();
                bufferOs.close();
            }
            bitmap.recycle();
            return null;
        } catch (Throwable th6) {
            th = th6;
            outputStream = fileOutputStream;
            if (bufferOs != null) {
                bufferOs.flush();
                bufferOs.close();
            }
            bitmap.recycle();
            throw th;
        }
        try {
            bufferOs2.write(bos.toByteArray());
            if (bufferOs2 != null) {
                try {
                    bufferOs2.flush();
                    bufferOs2.close();
                } catch (IOException e3222222) {
                    e3222222.printStackTrace();
                }
            }
            bitmap.recycle();
            return filePath;
        } catch (IOException e9) {
            e3222222 = e9;
            bufferOs = bufferOs2;
            outputStream = fileOutputStream;
            e3222222.printStackTrace();
            if (bufferOs != null) {
                bufferOs.flush();
                bufferOs.close();
            }
            bitmap.recycle();
            return null;
        } catch (Throwable th7) {
            th = th7;
            bufferOs = bufferOs2;
            outputStream = fileOutputStream;
            if (bufferOs != null) {
                bufferOs.flush();
                bufferOs.close();
            }
            bitmap.recycle();
            throw th;
        }
    }

    @z
    public static String compressBitmapToDisk(@y Bitmap bitmap, @z String des, long maxDiskDize) {
        BufferedOutputStream bos;
        FileNotFoundException e;
        IOException e2;
        Throwable th;
        FileOutputStream fos;
        FileOutputStream fileOutputStream;
        if (bitmap == null) {
            return null;
        }
        long bitmapSize = sizeOf(bitmap);
        Log.d("compress", "bitmap size " + bitmapSize + ", max " + maxDiskDize);
        String filePath;
        if (bitmapSize / 4 < maxDiskDize) {
            Log.d("compress", "bitmap smaller than max " + (bitmapSize / 4));
            bos = null;
            if (des == null) {
                try {
                    filePath = UtilsFile.getTempFileName();
                } catch (FileNotFoundException e3) {
                    e = e3;
                    try {
                        e.printStackTrace();
                        if (bos != null) {
                            return null;
                        }
                        try {
                            bos.flush();
                            bos.close();
                            return null;
                        } catch (IOException e22) {
                            e22.printStackTrace();
                            return null;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        if (bos != null) {
                            try {
                                bos.flush();
                                bos.close();
                            } catch (IOException e222) {
                                e222.printStackTrace();
                            }
                        }
                        throw th;
                    }
                }
            }
            filePath = des;
            fos = new FileOutputStream(filePath);
            try {
                BufferedOutputStream bos2 = new BufferedOutputStream(fos);
                try {
                    bitmap.compress(CompressFormat.JPEG, 100, bos2);
                    if (bos2 == null) {
                        return filePath;
                    }
                    try {
                        bos2.flush();
                        bos2.close();
                        return filePath;
                    } catch (IOException e2222) {
                        e2222.printStackTrace();
                        return filePath;
                    }
                } catch (FileNotFoundException e4) {
                    e = e4;
                    bos = bos2;
                    fileOutputStream = fos;
                    e.printStackTrace();
                    if (bos != null) {
                        return null;
                    }
                    bos.flush();
                    bos.close();
                    return null;
                } catch (Throwable th3) {
                    th = th3;
                    bos = bos2;
                    fileOutputStream = fos;
                    if (bos != null) {
                        bos.flush();
                        bos.close();
                    }
                    throw th;
                }
            } catch (FileNotFoundException e5) {
                e = e5;
                fileOutputStream = fos;
                e.printStackTrace();
                if (bos != null) {
                    return null;
                }
                bos.flush();
                bos.close();
                return null;
            } catch (Throwable th4) {
                th = th4;
                fileOutputStream = fos;
                if (bos != null) {
                    bos.flush();
                    bos.close();
                }
                throw th;
            }
        }
        bos = new ByteArrayOutputStream();
        int quality = 95;
        bitmap.compress(CompressFormat.JPEG, 95, bos);
        while (((long) bos.size()) > maxDiskDize && quality > 0) {
            bos.reset();
            quality -= 5;
            bitmap.compress(CompressFormat.JPEG, quality, bos);
        }
        Log.d("compress", "bitmap quality " + quality);
        BufferedOutputStream bufferOs = null;
        if (des == null) {
            try {
                filePath = UtilsFile.getTempFileName();
            } catch (IOException e6) {
                e2222 = e6;
                try {
                    e2222.printStackTrace();
                    if (bufferOs != null) {
                        return null;
                    }
                    try {
                        bufferOs.flush();
                        bufferOs.close();
                        return null;
                    } catch (IOException e22222) {
                        e22222.printStackTrace();
                        return null;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    if (bufferOs != null) {
                        try {
                            bufferOs.flush();
                            bufferOs.close();
                        } catch (IOException e222222) {
                            e222222.printStackTrace();
                        }
                    }
                    throw th;
                }
            }
        }
        filePath = des;
        fos = new FileOutputStream(filePath);
        try {
            BufferedOutputStream bufferOs2 = new BufferedOutputStream(fos);
            try {
                bufferOs2.write(bos.toByteArray());
                if (bufferOs2 == null) {
                    return filePath;
                }
                try {
                    bufferOs2.flush();
                    bufferOs2.close();
                    return filePath;
                } catch (IOException e2222222) {
                    e2222222.printStackTrace();
                    return filePath;
                }
            } catch (IOException e7) {
                e2222222 = e7;
                bufferOs = bufferOs2;
                fileOutputStream = fos;
                e2222222.printStackTrace();
                if (bufferOs != null) {
                    return null;
                }
                bufferOs.flush();
                bufferOs.close();
                return null;
            } catch (Throwable th6) {
                th = th6;
                bufferOs = bufferOs2;
                fileOutputStream = fos;
                if (bufferOs != null) {
                    bufferOs.flush();
                    bufferOs.close();
                }
                throw th;
            }
        } catch (IOException e8) {
            e2222222 = e8;
            fileOutputStream = fos;
            e2222222.printStackTrace();
            if (bufferOs != null) {
                return null;
            }
            bufferOs.flush();
            bufferOs.close();
            return null;
        } catch (Throwable th7) {
            th = th7;
            fileOutputStream = fos;
            if (bufferOs != null) {
                bufferOs.flush();
                bufferOs.close();
            }
            throw th;
        }
    }

    public static int getFileExifRotation(Uri uri) throws IOException {
        switch (new ExifInterface(uri.getPath()).getAttributeInt("Orientation", 1)) {
            case 3:
                return 180;
            case 6:
                return 90;
            case 8:
                return 270;
            default:
                return 0;
        }
    }
}
