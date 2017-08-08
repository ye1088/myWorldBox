package com.MCWorld.video.camera;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.media.CamcorderProfile;
import android.os.Build.VERSION;
import android.util.Log;
import com.MCWorld.mcfloat.InstanceZones.e;
import java.util.List;

/* compiled from: CameraHelper */
public class a {
    private static final String TAG = "CameraHelper";

    public static int MW() {
        return Camera.getNumberOfCameras();
    }

    public static int MX() {
        int camerasCnt = MW();
        int defaultCameraID = -1;
        CameraInfo cameraInfo = new CameraInfo();
        for (int i = 0; i < camerasCnt; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == 0) {
                defaultCameraID = i;
            }
        }
        return defaultCameraID;
    }

    public static void a(Camera camera, boolean open) {
        if (camera != null) {
            Parameters p = camera.getParameters();
            p.setFlashMode(open ? "torch" : "off");
            camera.setParameters(p);
        }
    }

    public static int MY() {
        int camerasCnt = MW();
        int defaultCameraID = -1;
        CameraInfo cameraInfo = new CameraInfo();
        for (int i = 0; i < camerasCnt; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == 1) {
                defaultCameraID = i;
            }
        }
        return defaultCameraID;
    }

    public static boolean lB(int cameraID) {
        CameraInfo cameraInfo = new CameraInfo();
        Camera.getCameraInfo(cameraID, cameraInfo);
        return cameraInfo.facing == 0;
    }

    @TargetApi(11)
    public static List<Size> a(Camera camera) {
        if (camera == null) {
            return null;
        }
        if (VERSION.SDK_INT >= 11) {
            List<Size> sizes = camera.getParameters().getSupportedVideoSizes();
            if (sizes != null) {
                return sizes;
            }
        }
        return camera.getParameters().getSupportedPreviewSizes();
    }

    public static Camera lC(int cameraId) {
        Camera c = null;
        try {
            c = Camera.open(cameraId);
        } catch (Exception e) {
            Log.e(TAG, "open camera failed: " + e.getMessage());
        }
        return c;
    }

    public static boolean bz(Context context) {
        if (context.getPackageManager().hasSystemFeature("android.hardware.camera")) {
            return true;
        }
        return false;
    }

    public static int a(Activity activity, int cameraId, Camera camera) {
        int result;
        CameraInfo info = new CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int degrees = 0;
        switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
            case 0:
                degrees = 0;
                break;
            case 1:
                degrees = 90;
                break;
            case 2:
                degrees = 180;
                break;
            case 3:
                degrees = 270;
                break;
        }
        if (info.facing == 1) {
            result = (360 - ((info.orientation + degrees) % e.Wx)) % e.Wx;
        } else {
            result = ((info.orientation - degrees) + e.Wx) % e.Wx;
        }
        Log.d(TAG, "camera display orientation: " + result);
        camera.setDisplayOrientation(result);
        return result;
    }

    public static Size a(List<Size> sizes, int targetHeight) {
        Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;
        for (Size size : sizes) {
            double ratio = ((double) size.width) / ((double) size.height);
            if (ratio > 1.0d && ratio <= 1.5d && ((double) Math.abs(size.height - targetHeight)) < minDiff) {
                optimalSize = size;
                minDiff = (double) Math.abs(size.height - targetHeight);
            }
        }
        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Size size2 : sizes) {
                if (((double) Math.abs(size2.height - targetHeight)) < minDiff) {
                    optimalSize = size2;
                    minDiff = (double) Math.abs(size2.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }

    public static Size a(int cameraId, Camera camera) {
        CamcorderProfile cameraProfile = CamcorderProfile.get(cameraId, 1);
        camera.getClass();
        return new Size(camera, cameraProfile.videoFrameWidth, cameraProfile.videoFrameHeight);
    }

    public static void a(String focusMode, Camera camera) {
        Parameters parameters = camera.getParameters();
        if (parameters.getSupportedFocusModes().contains(focusMode)) {
            parameters.setFocusMode(focusMode);
        }
        camera.setParameters(parameters);
    }
}
