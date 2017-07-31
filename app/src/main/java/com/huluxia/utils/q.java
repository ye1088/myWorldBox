package com.huluxia.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import com.huluxia.HTApplication;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.module.h;
import com.huluxia.t;
import com.huluxia.widget.cropimage.CropImageActivity;
import java.io.File;
import java.util.List;

/* compiled from: UtilsCamera */
public class q {
    public static final int CROP_FROM_CAMERA = 258;
    public static final int PICK_FROM_CAMERA = 256;
    public static final int PICK_FROM_FILE = 257;
    private static String cropPath = "";
    private static String savePath = "";

    private static boolean clearOutput(Uri uri) {
        File f = new File(uri.getPath());
        if (f.exists()) {
            return f.delete();
        }
        return false;
    }

    public static void fromCamera(Activity act) {
        if (UtilsFile.CT()) {
            savePath = UtilsFile.getTempFileName();
            Uri saveUri = Uri.fromFile(new File(savePath));
            clearOutput(saveUri);
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            intent.putExtra("return-data", false);
            intent.putExtra("output", saveUri);
            intent.putExtra("android.intent.extra.videoQuality", 1);
            act.startActivityForResult(intent, 256);
            return;
        }
        t.n(HTApplication.getAppContext(), "没有安装SD卡，不能使用相机");
    }

    public static void fromAlbums(Activity act) {
        Intent intent = new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI);
        intent.setFlags(524288);
        intent.setType("image/*");
        act.startActivityForResult(intent, 257);
    }

    public static void a(Activity act, Uri fromUri, Uri cropUri, int width, int height, boolean fixedAspectRatio) {
        String fromPath = UtilsFile.uriToPath(act, fromUri);
        String outputPath = cropUri.getPath();
        Intent cropIntent = new Intent();
        cropIntent.setClass(HTApplication.getAppContext(), CropImageActivity.class);
        cropIntent.putExtra("fromPath", fromPath);
        cropIntent.putExtra("outputPath", outputPath);
        cropIntent.putExtra("outputX", width);
        cropIntent.putExtra("outputY", height);
        cropIntent.putExtra("fixedAspectRatio", fixedAspectRatio);
        act.startActivityForResult(cropIntent, 258);
    }

    public static void cropPhoto(Activity act, Uri fromUri, Uri cropUri, int width, int height, boolean fromAlbums) {
        if (!fromAlbums || new File(fromUri.getPath()).exists()) {
            Log.e("crop", "exists");
            if (UtilsFile.CT()) {
                UtilsFile.deleteFile(Environment.getExternalStorageDirectory() + "/android/data/com.cooliris.media/cache/hires-image-cache");
            }
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(fromUri, "image/*");
            List<ResolveInfo> list = act.getPackageManager().queryIntentActivities(cropIntent, 0);
            if (list.size() > 0) {
                cropIntent.putExtra("output", cropUri);
                cropIntent.putExtra("crop", "true");
                cropIntent.putExtra("aspectX", 1);
                cropIntent.putExtra("aspectY", 1);
                cropIntent.putExtra("scale", true);
                cropIntent.putExtra("scaleUpIfNeeded", true);
                cropIntent.putExtra("outputX", width);
                cropIntent.putExtra("outputY", height);
                cropIntent.putExtra("return-data", false);
                cropIntent.putExtra("outputFormat", CompressFormat.JPEG.toString());
                Intent i = new Intent(cropIntent);
                ResolveInfo res = (ResolveInfo) list.get(0);
                i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                act.startActivityForResult(i, 258);
                return;
            }
            t.n(act, "Can not find image crop app");
            Log.d("[doCrop] ", "size = 0");
            return;
        }
        Log.e("crop", "not exists");
    }

    public static String a(int resultCode, int requestCode, Intent data, Activity act, ImageView iv, boolean fixedAspectRatio) {
        if (resultCode == -1) {
            Uri cropUri;
            switch (requestCode) {
                case 256:
                    cropPath = UtilsFile.getTempFileName();
                    cropUri = Uri.fromFile(new File(cropPath));
                    a(act, Uri.fromFile(new File(savePath)), cropUri, (int) h.arp, h.arp, fixedAspectRatio);
                    break;
                case 257:
                    if (!(data == null || data.getData() == null)) {
                        cropPath = UtilsFile.getTempFileName();
                        cropUri = Uri.fromFile(new File(cropPath));
                        a(act, data.getData(), cropUri, (int) h.arp, h.arp, fixedAspectRatio);
                        break;
                    }
                case 258:
                    if (!UtilsFile.isExist(cropPath)) {
                        if (iv != null) {
                            iv.setImageResource(0);
                            break;
                        }
                    }
                    if (iv != null) {
                        iv.setImageBitmap(ae.decodeByteArray(UtilsFile.getBytesFromSD(cropPath)));
                    }
                    return cropPath;
                    break;
            }
        }
        return "";
    }

    public static String onPickResult(int resultCode, int requestCode, Intent data, Activity act) {
        String path = "";
        if (resultCode != -1) {
            return path;
        }
        switch (requestCode) {
            case 256:
                if (data == null || data.getData() == null) {
                    return savePath;
                }
                return UtilsFile.uriToPath(act, data.getData());
            case 257:
                if (data == null || data.getData() == null) {
                    return path;
                }
                return UtilsFile.uriToPath(act, data.getData());
            default:
                return path;
        }
    }

    public static void showPhotoMenu(Activity activity) {
        Builder builder = new Builder(activity);
        builder.setInverseBackgroundForced(true);
        View layout = LayoutInflater.from(activity).inflate(i.include_dialog_camera, null);
        builder.setView(layout);
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.setView(layout, 0, 0, 0, 0);
        dialog.show();
        layout.findViewById(g.tv_shot).setOnClickListener(new 1(dialog, activity));
        layout.findViewById(g.tv_album).setOnClickListener(new 2(dialog, activity));
    }
}
