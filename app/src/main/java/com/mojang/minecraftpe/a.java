package com.mojang.minecraftpe;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import com.huluxia.aa.mcspirit;
import com.huluxia.mcgame.af;
import com.huluxia.mcgame.e;
import com.huluxia.mcgame.g;
import com.huluxia.mcgame.k;
import com.huluxia.mcgame.v;
import com.huluxia.mcgame.z;
import com.huluxia.mcsdk.DTSDKManager;
import com.huluxia.mcsdk.DTSDKManagerEx;
import com.huluxia.mcsdk.crypto.c;
import com.huluxia.mcsdk.crypto.d;
import com.huluxia.mcsdk.dtlib.DTThread;
import com.huluxia.mcsdk.dtlib.h;
import com.huluxia.utils.ah;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import net.zhuoweizhang.mcpelauncher.ScriptManager;
import net.zhuoweizhang.mcpelauncher.texture.b;
import net.zhuoweizhang.mcpelauncher.texture.f;
import org.fmod.FMOD;

/* compiled from: DTMainActivity */
public class a {
    private static final boolean DEBUG = false;
    private static String MC_NATIVE_LIBRARY_LOCATION = "/data/data/com.mojang.minecraftpe/lib/libminecraftpe.so";
    private static final String TAG = "DTMainActivity";
    private static boolean bHR = false;
    public static boolean bHS = true;
    public static boolean bHT = true;
    private static final boolean bHU = false;
    private static boolean bHV = false;
    private static String bHW = null;
    private static Context bHX = null;
    public static e bHY = null;
    public static d bHZ = null;
    public static c bIa = null;
    private static Thread bIb = null;
    public static boolean bIc = false;
    public static final String bId = "http://bbs.huluxia.com/forum-63-0-0-0.html";
    private static String bIe;

    public static String QS() {
        return bIe;
    }

    public static void QT() {
        if (MainActivity.currentMainActivity != null) {
            MainActivity main = (MainActivity) MainActivity.currentMainActivity.get();
            if (main != null) {
                try {
                    main.nativeRegisterThis();
                } catch (Exception e) {
                }
            }
        }
    }

    public static void QU() {
        bHV = true;
    }

    public static void QV() {
        bHV = false;
    }

    public static boolean QW() {
        return bHV;
    }

    public static String QX() {
        return "net.zhuoweizhang.mcpelauncher";
    }

    private static void QY() {
        if (MainActivity.currentMainActivity != null) {
            MainActivity main = (MainActivity) MainActivity.currentMainActivity.get();
            if (main != null) {
                main.textureOverrides.clear();
                main.textureOverrides.add(new z());
                main.textureOverrides.add(new af(bHX));
            }
        }
    }

    public static byte[] getSoundBytes(String paramString) {
        if (MainActivity.currentMainActivity != null) {
            MainActivity localMainActivity = (MainActivity) MainActivity.currentMainActivity.get();
            if (localMainActivity != null) {
                return localMainActivity.getFileDataBytes(paramString.substring("file:///android_asset/".length()));
            }
        }
        return null;
    }

    public static InputStream getSoundInputStream(String paramString, long[] paramArrayOfLong) {
        if (MainActivity.currentMainActivity != null) {
            MainActivity localMainActivity = (MainActivity) MainActivity.currentMainActivity.get();
            if (localMainActivity != null) {
                return localMainActivity.getInputStreamForAsset(paramString.substring("file:///android_asset/".length()));
            }
        }
        return null;
    }

    private static void QZ() {
        try {
            if (MainActivity.currentMainActivity != null) {
                MainActivity main = (MainActivity) MainActivity.currentMainActivity.get();
                if (main != null) {
                    b localAtlasProvider1 = new b("images/terrain.meta", "images/terrain-atlas.tga", "images/terrain-atlas/", new f(), 1, 4);
                    b localAtlasProvider2 = new b("images/items.meta", "images/items-opaque.png", "images/items-opaque/", new net.zhuoweizhang.mcpelauncher.texture.e(), 2, 0);
                    localAtlasProvider1.d((MainActivity) MainActivity.currentMainActivity.get());
                    localAtlasProvider2.d((MainActivity) MainActivity.currentMainActivity.get());
                    main.textureOverrides.add(0, localAtlasProvider1);
                    main.textureOverrides.add(1, localAtlasProvider2);
                    DTSDKManagerEx.anN = localAtlasProvider1.cmR;
                    DTSDKManagerEx.ig(22);
                    DTSDKManagerEx.ch(true);
                    DTSDKManagerEx.anO = localAtlasProvider2.cmR;
                }
            }
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    public static void Ra() {
        if (h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CX() == 7) {
            FMOD.close();
        }
    }

    public static void dC() {
        if (MainActivity.currentMainActivity != null && ((MainActivity) MainActivity.currentMainActivity.get()) == null) {
        }
    }

    public static boolean Rb() {
        try {
            if (bHR) {
                if (h.CW().CX() == 0) {
                    DTSDKManager.init(bHX);
                } else if (h.CW().CX() == 1) {
                    DTSDKManager.init(bHX);
                } else if (h.CW().CX() == 2) {
                    FMOD.init(bHX);
                    if (bHR) {
                        DTSDKManager.init(bHX);
                    }
                } else if (h.CW().CX() == 3) {
                    FMOD.init(bHX);
                    if (bHR) {
                        if (VERSION.SDK_INT < 11) {
                            g.gj(512);
                            ScriptManager.ITEM_ID_COUNT = g.vU();
                            mcspirit.a(bHX.getApplicationContext(), 130, 23, "1", 0);
                        } else {
                            DTSDKManager.init(bHX);
                        }
                    }
                } else if (h.CW().CX() == 5 || h.CW().CX() == 7) {
                    FMOD.init(bHX);
                    if (bHR && bHS) {
                        DTSDKManager.init(bHX);
                    }
                }
            }
            bIe = MainActivity.getAppContext().getApplicationContext().getFilesDir().getAbsolutePath();
            if (h.CW().CX() == 0) {
                g.gl(0);
                g.bf(true);
                bIb = new DTThread();
                bIb.start();
            }
            CookieHandler.setDefault(new CookieManager());
            System.gc();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static void Rc() {
        if (MainActivity.currentMainActivity != null) {
            MainActivity main = (MainActivity) MainActivity.currentMainActivity.get();
            if (main != null) {
                try {
                    String str = k.wu();
                    if (true && str != null) {
                        File localFile = new File(str);
                        if (localFile.exists()) {
                            main.textureOverrides.add(new k(localFile));
                            return;
                        } else {
                            main.texturePack = null;
                            return;
                        }
                    }
                } catch (Exception localException) {
                    localException.printStackTrace();
                }
                main.texturePack = null;
            }
        }
    }

    private static void Rd() {
        if (MainActivity.currentMainActivity != null) {
            MainActivity main = (MainActivity) MainActivity.currentMainActivity.get();
            if (main != null) {
                try {
                    String str = k.wu();
                    if (true && str != null) {
                        File localFile = new File(str);
                        if (localFile.exists()) {
                            main.textureOverrides.add(new k(localFile));
                            return;
                        } else {
                            main.texturePack = null;
                            return;
                        }
                    }
                } catch (Exception localException) {
                    localException.printStackTrace();
                }
                main.texturePack = null;
            }
        }
    }

    public static boolean Re() {
        if (h.CW().CX() == 1) {
            return true;
        }
        return false;
    }

    private static File z(Uri paramUri) {
        try {
            File localFile = new File(bHX.getExternalFilesDir(null), "skintemp.png");
            localFile.getParentFile().mkdirs();
            InputStream localInputStream = bHX.getContentResolver().openInputStream(paramUri);
            FileOutputStream localFileOutputStream = new FileOutputStream(localFile);
            byte[] arrayOfByte = new byte[4096];
            while (true) {
                int i = localInputStream.read(arrayOfByte);
                if (i == -1) {
                    localInputStream.close();
                    localFileOutputStream.close();
                    return localFile;
                }
                localFileOutputStream.write(arrayOfByte, 0, i);
            }
        } catch (IOException localIOException) {
            localIOException.printStackTrace();
            return new File("/sdcard/totally/fake");
        }
    }

    public static void a(int paramInt1, int paramInt2, Intent paramIntent, MainActivity inputMainActivity) {
        b(paramInt1, paramInt2, paramIntent, inputMainActivity);
    }

    public static int[] gV(String paramString) {
        try {
            int[] ret_val = z.cR(paramString);
            if (ret_val != null) {
                return ret_val;
            }
            Bitmap localBitmap = BitmapFactory.decodeFile(paramString);
            if (localBitmap == null) {
                return null;
            }
            int[] arrayOfInt = new int[((localBitmap.getWidth() * localBitmap.getHeight()) + 2)];
            arrayOfInt[0] = localBitmap.getWidth();
            arrayOfInt[1] = localBitmap.getHeight();
            localBitmap.getPixels(arrayOfInt, 2, localBitmap.getWidth(), 0, 0, localBitmap.getWidth(), localBitmap.getHeight());
            localBitmap.recycle();
            return arrayOfInt;
        } catch (Exception localException) {
            localException.printStackTrace();
            return null;
        }
    }

    private static void b(int paramInt1, int paramInt2, Intent paramIntent, MainActivity inputMainActivity) {
        z.bF(false);
        if (paramInt1 == 1234) {
            inputMainActivity.inputStatus = 1;
            if (!d.isSafeMode()) {
                inputMainActivity.applyBuiltinPatches();
            }
        } else if (paramInt1 == -1) {
            MainActivity.nativeOnPickImageSuccess(inputMainActivity.getPickImageCallbackAddress(), z(paramIntent.getData()).getAbsolutePath());
            return;
        } else if (paramInt1 == 1) {
            if (paramInt2 != -1 || paramIntent == null) {
                MainActivity.nativeOnPickImageCanceled(inputMainActivity.getPickImageCallbackAddress());
                return;
            }
            Uri localUri = paramIntent.getData();
            if (localUri != null) {
                String[] arrayOfString = new String[]{"_data"};
                Cursor localCursor = inputMainActivity.getContentResolver().query(localUri, arrayOfString, null, null, null);
                if (localCursor != null) {
                    localCursor.moveToFirst();
                    MainActivity.nativeOnPickImageSuccess(inputMainActivity.getPickImageCallbackAddress(), localCursor.getString(localCursor.getColumnIndex(arrayOfString[0])));
                    inputMainActivity.setPickImageCallbackAddress(0);
                    localCursor.close();
                    return;
                }
                return;
            }
            return;
        }
        MainActivity.nativeOnPickImageCanceled(inputMainActivity.getPickImageCallbackAddress());
    }

    public static void Rf() {
        if (h.CW().CX() != 2) {
        }
    }

    public static void Rg() {
        System.exit(0);
    }

    public static void quit() {
        g.bf(false);
        bIb = null;
        if (MainActivity.currentMainActivity != null) {
            MainActivity main = (MainActivity) MainActivity.currentMainActivity.get();
            if (main != null) {
                main._userInputValues = null;
                main.commandHistoryList = null;
                main.textureOverrides.clear();
                main.textureOverrides = null;
                main.texturePack = null;
            }
        }
        bHY = null;
        bHZ = null;
        bIa = null;
        MainActivity.currentMainActivity = null;
        z.cP(null);
        if (!bHR) {
            return;
        }
        if (h.CW().CX() == 1 || h.CW().CX() == 2) {
            DTSDKManagerEx.a(0, null, 0, null, 0);
        }
    }

    public static boolean bO(Context in_context) {
        try {
            com.huluxia.mcgame.options.a.yZ();
            com.huluxia.mcjavascript.f.init();
            DTSDKManagerEx.ch(false);
            g.bd(false);
            bHX = in_context;
            com.huluxia.mcmod.a.init(bHX);
            com.huluxia.mcmod.a.aT(bHX);
            com.huluxia.mcinterface.h.bQ(false);
            com.huluxia.mcso.a.init(bHX);
            v.init();
            if (h.CW().CX() == 0) {
                com.huluxia.mcgame.options.b.init();
            } else if (h.CW().CX() == 1) {
                com.huluxia.mcgame.options.c.init();
            } else if (h.CW().CX() == 2) {
                com.huluxia.mcgame.options.d.init();
            } else if (h.CW().CX() == 3) {
                com.huluxia.mcgame.options.d.init();
            } else if (h.CW().CX() == 5) {
                com.huluxia.mcgame.options.d.init();
            } else if (h.CW().CX() == 7) {
                com.huluxia.mcgame.options.e.init();
            }
            bHZ = new d("sg5.w36yhews");
            bHZ.aX(in_context);
            bIa = new c();
            bIa.aX(in_context);
            bHY = new e();
            com.huluxia.mcjsmanager.b.hD(0);
            com.huluxia.mcjavascript.h.init();
            com.huluxia.mcgame.d.vi();
            com.huluxia.mcjsmanager.b.init(in_context, com.huluxia.mcgame.h.wo().wp());
            g.gk(0);
            g.gm(0);
            d.setContext(in_context);
            h.CW().Dc();
            QY();
            Rd();
            if (k.wu() == null) {
                return true;
            }
            if (h.CW().CX() != 3 && h.CW().CX() != 2) {
                return true;
            }
            QZ();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean Rh() {
        com.huluxia.mcjavascript.f.bX(true);
        com.huluxia.mcjavascript.f.bY(true);
        return true;
    }

    public static boolean Ri() {
        boolean bCheckResult = true;
        if (1 == null) {
            h.Dd();
        }
        if (h.CW().CX() == 10) {
            Rh();
            bCheckResult = false;
        }
        if (h.CW().CX() == 0 || h.CW().CX() == 1 || h.CW().CX() == 2) {
            bHR = true;
        } else if (h.CW().CX() == 3) {
            if (VERSION.SDK_INT < 11) {
                bHR = true;
                g.aZ(false);
            } else {
                bHR = true;
            }
        } else if (h.CW().CX() == 5) {
            bHR = true;
            MainActivity.setUseInstalledMode(true);
        } else if (h.CW().CX() == 7) {
            bHR = true;
            MainActivity.setUseInstalledMode(true);
        }
        if (!g.vT()) {
            z.cP(null);
        }
        if (!g.vS()) {
            k.cM(null);
        }
        return bCheckResult;
    }

    public static long Rj() throws Exception {
        return new File(bHW).length();
    }

    private static void Rk() {
        if (Rm()) {
            ah.KZ().Q(hlx.data.localstore.a.bKr, 0);
            h.aY(bHX);
        }
    }

    public static void Rl() throws Exception {
        if (!com.huluxia.mcsdk.g.aC(Rj())) {
            Rk();
        }
    }

    private static void gW(String gameSoPath) {
    }

    public static boolean a(MainActivity paramMainActivity) {
        try {
            String soPath = com.huluxia.mcgame.h.wo().ws();
            String soFullPath;
            if (h.CW().CX() == 0) {
                System.loadLibrary("gnustl_shared");
                soFullPath = soPath + File.separator + "libminecraftpe.so";
                bHW = soFullPath;
                System.load(soFullPath);
                if (bHR) {
                    Rl();
                    DTSDKManagerEx.Co();
                }
            } else if (h.CW().CX() == 1) {
                System.loadLibrary("gnustl_shared");
                soFullPath = soPath + File.separator + com.huluxia.mcinterface.e.aiU;
                bHW = soFullPath;
                System.load(soFullPath);
                if (bHR) {
                    Rl();
                    DTSDKManagerEx.a(true, paramMainActivity);
                }
            } else if (h.CW().CX() == 2) {
                System.loadLibrary("gnustl_shared");
                System.load(soPath + File.separator + "libfmod.so");
                soFullPath = soPath + File.separator + "libminecraftpo.so";
                bHW = soFullPath;
                System.load(soFullPath);
                if (bHR) {
                    Rl();
                    DTSDKManagerEx.a(true, paramMainActivity, false);
                }
            } else if (h.CW().CX() == 3) {
                if (MainActivity.isUseInstalledMode()) {
                    System.loadLibrary("gnustl_shared");
                    System.loadLibrary("fmod");
                    ((MainActivity) MainActivity.currentMainActivity.get()).prePatch();
                    r4 = (MainActivity) MainActivity.currentMainActivity.get();
                    MC_NATIVE_LIBRARY_LOCATION = MainActivity.MC_NATIVE_LIBRARY_LOCATION;
                    soFullPath = hlx.data.localstore.a.bJP;
                    bHW = soFullPath;
                    System.load(soFullPath);
                    if (bHR) {
                        Rl();
                        DTSDKManagerEx.a(true, paramMainActivity, true, true, true);
                    }
                } else {
                    System.loadLibrary("gnustl_shared");
                    System.load(soPath + File.separator + "libfmod.so");
                    soFullPath = soPath + File.separator + "libminecraftpo.so";
                    bHW = soFullPath;
                    System.load(soFullPath);
                    if (bHR) {
                        Rl();
                        DTSDKManagerEx.a(true, paramMainActivity, true, true, true);
                    }
                }
            } else if ((h.CW().CX() == 5 || h.CW().CX() == 7) && MainActivity.isUseInstalledMode()) {
                System.loadLibrary("gnustl_shared");
                System.loadLibrary("fmod");
                ((MainActivity) MainActivity.currentMainActivity.get()).prePatch();
                r4 = (MainActivity) MainActivity.currentMainActivity.get();
                MC_NATIVE_LIBRARY_LOCATION = MainActivity.MC_NATIVE_LIBRARY_LOCATION;
                soFullPath = hlx.data.localstore.a.bJP;
                bHW = soFullPath;
                System.load(soFullPath);
                if (bHR) {
                    Rl();
                    if (bHT) {
                        ScriptManager.nativePrePatch(true, paramMainActivity, true, true, true);
                    }
                }
            }
            if (MainActivity.currentMainActivity != null) {
                MainActivity main = (MainActivity) MainActivity.currentMainActivity.get();
                if (main != null) {
                    main.displayMetrics = new DisplayMetrics();
                    main.getWindowManager().getDefaultDisplay().getMetrics(main.displayMetrics);
                    g.gh(main.displayMetrics.widthPixels);
                    g.gi(main.displayMetrics.heightPixels);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Rk();
            return false;
        }
    }

    public static boolean doesRequireGuiBlocksPatch() {
        boolean z = true;
        MainActivity main = null;
        if (MainActivity.currentMainActivity != null) {
            main = (MainActivity) MainActivity.currentMainActivity.get();
            if (main == null) {
                return false;
            }
        }
        if (main.forceFallback) {
            return true;
        }
        if (main.texturePack != null) {
            try {
                InputStream instr = main.texturePack.cN("images/gui/gui.png");
                if (instr != null) {
                    instr.close();
                }
                if (instr != null) {
                    z = false;
                }
                return z;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        try {
            if (MainActivity.isUseInstalledMode()) {
                MainActivity.getAppContext().getResources().getAssets().open("images/gui/gui.png").close();
                return false;
            }
            com.huluxia.mcgame.h.wo().cK("images/gui/gui.png").close();
            return false;
        } catch (Exception e2) {
            e2.printStackTrace();
            return true;
        }
    }

    public static InputStream getLocalInputStreamForAsset(String paramString) {
        MainActivity main = null;
        if (MainActivity.currentMainActivity != null) {
            main = (MainActivity) MainActivity.currentMainActivity.get();
            if (main == null) {
                return null;
            }
        }
        try {
            if (main.forceFallback) {
                return main.getAssets().open(paramString);
            }
            InputStream localObject;
            try {
                if (MainActivity.isUseInstalledMode()) {
                    localObject = MainActivity.getAppContext().getResources().getAssets().open(paramString);
                } else {
                    localObject = com.huluxia.mcgame.h.wo().cK(paramString);
                }
            } catch (Exception e) {
                if (MainActivity.isUseInstalledMode()) {
                    localObject = null;
                } else {
                    localObject = main.getAssets().open(paramString);
                }
            }
            if (localObject == null) {
                localObject = main.getAssets().open(paramString);
            }
            return localObject;
        } catch (Exception e2) {
            return null;
        }
    }

    public static boolean Rm() {
        return bIc;
    }

    public static void ds(boolean crashWithSo) {
        bIc = crashWithSo;
    }
}
