package com.mojang.minecraftpe;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.NativeActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.provider.MediaStore.Images.Media;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.UtilsScreen;
import com.MCWorld.mcgame.g;
import com.MCWorld.mcgame.k;
import com.MCWorld.mclauncher010.b.i;
import com.MCWorld.mclauncher010.b.n;
import com.MCWorld.utils.ah;
import com.tencent.mm.sdk.plugin.MMPluginProviderConstants.OAuth;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;
import net.zhuoweizhang.mcpelauncher.ScriptManager;
import net.zhuoweizhang.mcpelauncher.h;
import org.bytedeco.javacpp.avutil;
import org.mozilla.javascript.RhinoException;

@SuppressLint({"SdCardPath"})
public class MainActivity extends NativeActivity {
    private static final boolean DEBUG = false;
    private static final boolean ENABLE_FLOAt_WINDOW = false;
    private static String MC_NATIVE_LIBRARY_DIR = "/data/data/com.mojang.minecraftpe/lib/";
    public static String MC_NATIVE_LIBRARY_LOCATION = "/data/data/com.mojang.minecraftpe/lib/libminecraftpe.so";
    private static final String TAG = "MainActivity";
    private static boolean USE_INSTALLED_MODE = false;
    private static boolean _isPowerVr = false;
    private static String bbc;
    private static int bbd;
    public static WeakReference<MainActivity> currentMainActivity = null;
    public static List<String> failedPatches;
    private static boolean globalRestart = false;
    public static boolean hasPrePatched = false;
    public static MainActivity mInstance = null;
    private static Context minecraftApkContext;
    public static ByteBuffer minecraftLibBuffer;
    private final DateFormat DateFormat = new SimpleDateFormat();
    private boolean _isTouchscreen = true;
    private int _userInputStatus = -1;
    private String[] _userInputText = null;
    public ArrayList<com.mojang.android.a> _userInputValues = new ArrayList();
    private int _viewDistance = 2;
    private int commandHistoryIndex = 0;
    public List<String> commandHistoryList = new ArrayList();
    private View commandHistoryView;
    private Bundle data;
    protected DisplayMetrics displayMetrics;
    public boolean forceFallback = false;
    private boolean hasResetSafeModeCounter = false;
    private boolean hiddenTextDismissAfterOneLine = false;
    private TextView hiddenTextView;
    private PopupWindow hiddenTextWindow;
    protected int inputStatus = -1;
    private Dialog loginDialog;
    private AlertDialog mDialog;
    private long mFileDialogCallback = 0;
    Dialog mLoginDialog;
    WebView mWebView;
    public ApplicationInfo mcAppInfo;
    private PackageInfo mcPkgInfo;
    private Button nextButton;
    private long pickImageCallbackAddress = 0;
    private Button prevButton;
    private SparseArray<a> requestMap = new SparseArray();
    TextInputProxyEditTextbox textInputWidget;
    public List<h> textureOverrides = new ArrayList();
    protected k texturePack;
    protected String[] userInputStrings = null;
    public int virtualKeyboardHeight = 0;

    class a implements Runnable {
        final /* synthetic */ MainActivity bIh;
        private HttpURLConnection bIs;
        private String bIt;
        private int bIu;
        private String bIv;
        private boolean bIw = true;
        private MainActivity bIx;
        private String method;
        private long timestamp;
        private URL url;

        public a(MainActivity this$0, MainActivity p1, int requestId, long timestamp, String url, String method, String cookies) {
            this.bIh = this$0;
            this.bIv = url;
            this.bIx = p1;
            synchronized (this$0.requestMap) {
                this$0.requestMap.put(requestId, this);
            }
        }

        public void run() {
            InputStream is = null;
            String content = null;
            int response = 0;
            try {
                this.url = new URL(this.bIv);
                this.bIs = (HttpURLConnection) this.url.openConnection();
                this.bIs.setRequestMethod(this.method);
                this.bIs.setRequestProperty("Cookie", this.bIt);
                this.bIs.setRequestProperty("User-Agent", "MCPE/Curl");
                this.bIs.setUseCaches(false);
                this.bIs.setDoInput(true);
                this.bIs.connect();
                try {
                    response = this.bIs.getResponseCode();
                    is = this.bIs.getInputStream();
                    if (is != null) {
                        int i;
                        if (this.bIs.getContentLength() < 0) {
                            i = 1024;
                        } else {
                            i = this.bIs.getContentLength();
                        }
                        content = MainActivity.stringFromInputStream(is, i);
                    }
                    if (is != null) {
                        try {
                            is.close();
                        } catch (Exception e) {
                        }
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                    if (is != null) {
                        try {
                            is.close();
                        } catch (Exception e3) {
                        }
                    }
                } catch (Throwable th) {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (Exception e4) {
                        }
                    }
                }
                if (this.bIw) {
                    this.bIx.nativeWebRequestCompleted(this.bIu, this.timestamp, response, content);
                }
                synchronized (this.bIh.requestMap) {
                    this.bIh.requestMap.remove(this.bIh.requestMap.indexOfValue(this));
                }
            } catch (Exception e22) {
                e22.printStackTrace();
            }
        }
    }

    class b extends WebViewClient {
        final /* synthetic */ MainActivity bIh;
        private MainActivity bIx;
        boolean bIy;

        private b(MainActivity this$0, MainActivity p1) {
            this.bIh = this$0;
            this.bIy = false;
            this.bIx = p1;
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Uri tempUri = Uri.parse(url);
            String endHost = this.bIx.getRealmsRedirectInfo().bIA;
            if (endHost == null) {
                endHost = "account.mojang.com";
            }
            if (!tempUri.getHost().equals(endHost)) {
                return false;
            }
            if (tempUri.getPath().equals("/m/launch")) {
                this.bIx.loginLaunchCallback(tempUri);
                this.bIy = true;
                return true;
            }
            view.loadUrl(url);
            return true;
        }

        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            if (this.bIx.isRedirectingRealms()) {
                handler.proceed();
            } else {
                super.onReceivedSslError(view, handler, error);
            }
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Uri tempUri = Uri.parse(url);
            String endHost = this.bIx.getRealmsRedirectInfo().bIA;
            if (endHost == null) {
                endHost = "account.mojang.com";
            }
            if (tempUri.getHost().equals(endHost) && tempUri.getPath().equals("/m/launch") && !this.bIy) {
                this.bIx.loginLaunchCallback(tempUri);
                this.bIy = true;
            }
        }
    }

    class c implements TextWatcher, OnEditorActionListener {
        final /* synthetic */ MainActivity bIh;

        private c(MainActivity this$0) {
            this.bIh = this$0;
        }

        public void afterTextChanged(Editable paramEditable) {
            this.bIh.nativeSetTextboxText(paramEditable.toString());
            if (this.bIh.isCommandHistoryEnabled() && this.bIh.commandHistoryIndex >= 0 && this.bIh.commandHistoryIndex < this.bIh.commandHistoryList.size()) {
                this.bIh.commandHistoryList.set(this.bIh.commandHistoryIndex, paramEditable.toString());
            }
        }

        public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (this.bIh.hiddenTextDismissAfterOneLine) {
                this.bIh.hiddenTextWindow.dismiss();
            } else {
                this.bIh.nativeReturnKeyPressed();
            }
            return true;
        }

        public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3) {
        }
    }

    private class d implements Runnable {
        final /* synthetic */ MainActivity bIh;

        private d(MainActivity mainActivity) {
            this.bIh = mainActivity;
        }

        public void run() {
            try {
                Thread.sleep(3000);
                System.exit(0);
            } catch (InterruptedException e) {
            }
        }
    }

    public static native void nativeOnPickImageCanceled(long j);

    public static native void nativeOnPickImageSuccess(long j, String str);

    public native void nativeBackPressed();

    public native void nativeBackSpacePressed();

    public native void nativeLoginData(String str, String str2, String str3, String str4);

    public native void nativeRegisterThis();

    public native void nativeReturnKeyPressed();

    public native void nativeSetTextboxText(String str);

    public native void nativeStopThis();

    public native void nativeSuspend();

    public native void nativeTypeCharacter(String str);

    public native void nativeUnregisterThis();

    public native void nativeWebRequestCompleted(int i, long j, int i2, String str);

    public long getPickImageCallbackAddress() {
        return this.pickImageCallbackAddress;
    }

    public void setPickImageCallbackAddress(long pickImageCallbackAddress) {
        this.pickImageCallbackAddress = pickImageCallbackAddress;
    }

    public static WeakReference<MainActivity> getMainActivity() {
        return currentMainActivity;
    }

    protected void onCreate(Bundle savedInstanceState) {
        if (a.Ri()) {
            currentMainActivity = new WeakReference(this);
            try {
                if (isUseInstalledMode()) {
                    minecraftApkContext = createPackageContext("com.mojang.minecraftpe", 2);
                } else {
                    minecraftApkContext = getApplicationContext();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Can't create package context for the original APK", 1).show();
                finish();
            }
            a.bO(getApplicationContext());
            a.QU();
            a();
            a.a(this);
            super.onCreate(savedInstanceState);
            a.QT();
            a.QV();
            a.Rb();
            ah.KZ().Q(g.adF, 1);
            mInstance = this;
        }
    }

    public void onDestroy2() {
    }

    protected void onDestroy() {
        ah.KZ().Q(g.adF, 3);
        mInstance = null;
        a.Ra();
        nativeUnregisterThis();
        super.onDestroy();
        com.MCWorld.mcsdk.dtlib.h.aY(getApplicationContext());
    }

    public static Context getAppContext() {
        return minecraftApkContext;
    }

    private boolean a() {
        try {
            bbd = 1;
            setVolumeControlStream(3);
            if (isUseInstalledMode()) {
                bbc = "/data/data/com.huluxia.mctool/app_patched";
            } else {
                bbc = com.MCWorld.mcgame.h.wo().ws();
            }
            bbb(bbc);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private File[] addToFileList(File[] paramArrayOfFile, File paramFile) {
        for (File equals : paramArrayOfFile) {
            if (equals.equals(paramFile)) {
                return paramArrayOfFile;
            }
        }
        File[] arrayOfFile = new File[(paramArrayOfFile.length + 1)];
        System.arraycopy(paramArrayOfFile, 0, arrayOfFile, 1, paramArrayOfFile.length);
        arrayOfFile[0] = paramFile;
        return arrayOfFile;
    }

    private void bbb(String paramString) {
        try {
            ClassLoader localClassLoader = getClassLoader();
            Field localField1 = d.b(localClassLoader.getClass(), "pathList");
            localField1.setAccessible(true);
            Object localObject = localField1.get(localClassLoader);
            Field localField2 = d.b(localObject.getClass(), "nativeLibraryDirectories");
            localField2.setAccessible(true);
            File[] arrayOfFile1 = (File[]) localField2.get(localObject);
            File[] arrayOfFile2 = addToFileList(arrayOfFile1, new File(paramString));
            if (arrayOfFile1 != arrayOfFile2) {
                localField2.set(localObject, arrayOfFile2);
            }
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
        return super.onKeyMultiple(keyCode, repeatCount, event);
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        return super.onKeyUp(keyCode, event);
    }

    public void onBackPressed() {
        try {
            if (com.MCWorld.mcsdk.dtlib.h.CW().CX() == 7) {
                nativeBackPressed();
            }
        } catch (Exception e) {
            com.MCWorld.mcsdk.log.a.verbose(com.MCWorld.mcsdk.log.a.aoP, "DTPrint is " + e, new Object[0]);
        }
    }

    protected void onPause() {
        nativeSuspend();
        super.onPause();
    }

    protected void onResume() {
        super.onResume();
    }

    @SuppressLint({"DefaultLocale"})
    protected void onStart() {
        super.onStart();
    }

    protected void onStop() {
        nativeStopThis();
        super.onStop();
        System.gc();
    }

    public void onNewIntent(Intent paramIntent) {
        HLog.verbose(TAG, "DTPrint onNewIntent 000 ", new Object[0]);
    }

    public void onWindowFocusChanged(boolean paramBoolean) {
        super.onWindowFocusChanged(paramBoolean);
    }

    public void prePatch() throws Exception {
        this.mcPkgInfo = getPackageManager().getPackageInfo("com.mojang.minecraftpe", 0);
        this.mcAppInfo = this.mcPkgInfo.applicationInfo;
        MC_NATIVE_LIBRARY_DIR = this.mcAppInfo.nativeLibraryDir;
        if (MC_NATIVE_LIBRARY_DIR == null) {
            MC_NATIVE_LIBRARY_DIR = "/data/data/com.mojang.minecraftpe/lib/";
        }
        MC_NATIVE_LIBRARY_LOCATION = MC_NATIVE_LIBRARY_DIR + "/libminecraftpe.so";
        File patched = getDir("patched", 0);
        File originalLibminecraft = new File(this.mcAppInfo.nativeLibraryDir + "/libminecraftpe.so");
        File newMinecraft = new File(patched, "libminecraftpe.so");
        boolean forcePrePatch = d.mB(1).getBoolean("force_prepatch", true);
        if ((!hasPrePatched && !newMinecraft.exists()) || forcePrePatch) {
            System.out.println("Forcing new prepatch");
            byte[] libBytes = new byte[((int) originalLibminecraft.length())];
            ByteBuffer libBuffer = ByteBuffer.wrap(libBytes);
            FileInputStream is = new FileInputStream(originalLibminecraft);
            is.read(libBytes);
            is.close();
            int patchedCount = 0;
            int maxPatchNum = getMaxNumPatches();
            for (String patchLoc : d.Ro()) {
                if (maxPatchNum < 0 || patchedCount > maxPatchNum) {
                    break;
                }
                File patchFile = new File(patchLoc);
                if (patchFile.exists()) {
                    try {
                        com.joshuahuelsman.patchtool.a patch = new com.joshuahuelsman.patchtool.a();
                        patch.z(patchFile);
                        if (patch.QL()) {
                            net.zhuoweizhang.mcpelauncher.patch.c.a(libBuffer, patch);
                            patchedCount++;
                        } else {
                            failedPatches.add(patchFile.getName());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        failedPatches.add(patchFile.getName());
                    }
                }
            }
            FileOutputStream os = new FileOutputStream(newMinecraft);
            os.write(libBytes);
            os.close();
            hasPrePatched = true;
            d.mB(1).edit().putBoolean("force_prepatch", false).putInt("prepatch_version", this.mcPkgInfo.versionCode).apply();
            MC_NATIVE_LIBRARY_DIR = patched.getCanonicalPath();
            MC_NATIVE_LIBRARY_LOCATION = newMinecraft.getCanonicalPath();
        }
    }

    void pickImage(long paramLong) {
        this.pickImageCallbackAddress = paramLong;
        startActivityForResult(new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI), bbd);
    }

    public int[] getImageData(String paramString, boolean paramBoolean) {
        if (!paramBoolean) {
            return a.gV(paramString);
        }
        try {
            InputStream localInputStream = getInputStreamForAsset(paramString);
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
        } catch (Exception localException) {
            localException.printStackTrace();
            return null;
        }
    }

    public String getDeviceId() {
        SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String str = localSharedPreferences.getString("snooperId", "");
        if (!str.isEmpty()) {
            return str;
        }
        str = UUID.randomUUID().toString().replaceAll("-", "");
        Editor localEditor = localSharedPreferences.edit();
        localEditor.putString("snooperId", str);
        localEditor.commit();
        return str;
    }

    public String createUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    boolean isTablet() {
        return (getResources().getConfiguration().screenLayout & 15) == 4;
    }

    public String getExternalStoragePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    public String getLocale() {
        Locale localLocale = getResources().getConfiguration().locale;
        return localLocale.getLanguage() + "_" + localLocale.getCountry();
    }

    public boolean isFirstSnooperStart() {
        return PreferenceManager.getDefaultSharedPreferences(this).getString("snooperId", "").isEmpty();
    }

    boolean hasHardwareChanged() {
        SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String str = localSharedPreferences.getString("lastAndroidVersion", "");
        if (str.isEmpty() || str != VERSION.RELEASE) {
        }
        if (1 != null) {
            Editor localEditor = localSharedPreferences.edit();
            localEditor.putString("lastAndroidVersion", VERSION.RELEASE);
            localEditor.commit();
        }
        return true;
    }

    private void disableTransparentSystemBar() {
        if (VERSION.SDK_INT >= 21) {
            getWindow().clearFlags(Integer.MIN_VALUE);
        }
    }

    private void fixMyEpicFail() {
        SharedPreferences prefs = d.mB(1);
        int lastVersion = prefs.getInt("last_bl_version", 0);
        int myVersion = 0;
        try {
            myVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
        }
        if (lastVersion < 69) {
            d.mB(0).edit().putBoolean("zz_load_native_addons", true).apply();
        }
        if (lastVersion != myVersion) {
            prefs.edit().putInt("last_bl_version", myVersion).apply();
        }
    }

    private boolean isCommandHistoryEnabled() {
        return d.mB(0).getBoolean("zz_command_history", true);
    }

    private void navigateCommandHistory(int paramInt) {
        int i = paramInt + this.commandHistoryIndex;
        if (i < 0) {
            i = 0;
        }
        if (i >= this.commandHistoryList.size()) {
            i = this.commandHistoryList.size() - 1;
        }
        setCommandHistoryIndex(i);
        String str = (String) this.commandHistoryList.get(i);
        this.hiddenTextView.setText(str);
        Selection.setSelection((Spannable) this.hiddenTextView.getText(), str.length());
    }

    private void reportError(Throwable paramThrowable) {
    }

    private void reportError(Throwable paramThrowable, int paramInt, String paramString) {
    }

    public static void saveScreenshot(String paramString, int paramInt1, int paramInt2, int[] paramArrayOfInt) {
    }

    private void setCommandHistoryIndex(int index) {
        boolean z;
        boolean z2 = true;
        this.commandHistoryIndex = index;
        Button button = this.prevButton;
        if (index != 0) {
            z = true;
        } else {
            z = false;
        }
        button.setEnabled(z);
        Button button2 = this.nextButton;
        if (index == this.commandHistoryList.size() - 1) {
            z2 = false;
        }
        button2.setEnabled(z2);
    }

    private void setImmersiveMode(boolean paramBoolean) {
        if (VERSION.SDK_INT >= 19) {
        }
    }

    private static String stringFromInputStream(InputStream in, int startingLength) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream(startingLength);
        try {
            byte[] buffer = new byte[1024];
            while (true) {
                int count = in.read(buffer);
                if (count == -1) {
                    break;
                }
                bytes.write(buffer, 0, count);
            }
            String byteArrayOutputStream = bytes.toString("UTF-8");
            return byteArrayOutputStream;
        } finally {
            bytes.close();
        }
    }

    private void turnOffSafeMode() {
        d.mB(0).edit().putBoolean("zz_safe_mode", false).commit();
        d.mB(1).edit().putBoolean("force_prepatch", true).commit();
        finish();
    }

    private boolean useLegacyKeyboardInput() {
        return d.mB(0).getBoolean("zz_legacy_keyboard_input", false);
    }

    public int abortWebRequest(int requestId) {
        a runner = (a) this.requestMap.get(requestId);
        if (runner != null) {
            runner.bIw = false;
        }
        return 0;
    }

    protected boolean allowScriptOverrideTextures() {
        return true;
    }

    protected void applyBuiltinPatches() {
    }

    public void buyGame() {
    }

    public int checkLicense() {
        return 0;
    }

    public void clearLoginInformation() {
        Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        localEditor.remove(OAuth.ACCESS_TOKEN);
        localEditor.remove("clientId");
        localEditor.remove("profileId");
        localEditor.remove("profileName");
        localEditor.commit();
    }

    protected Dialog createBackupsNotSupportedDialog() {
        return new Builder(this).setMessage("不支持").setPositiveButton(17039370, new OnClickListener(this) {
            final /* synthetic */ MainActivity bIh;

            {
                this.bIh = this$0;
            }

            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                this.bIh.finish();
            }
        }).setCancelable(false).create();
    }

    public void dismissHiddenTextbox() {
        if (this.hiddenTextWindow != null) {
            this.hiddenTextWindow.dismiss();
            hideKeyboardView();
        }
    }

    public boolean dispatchKeyEvent(KeyEvent paramKeyEvent) {
        if (paramKeyEvent.getAction() == 2 && paramKeyEvent.getKeyCode() == 0) {
            try {
                nativeTypeCharacter(paramKeyEvent.getCharacters());
                return true;
            } catch (UnsatisfiedLinkError e) {
            }
        }
        return super.dispatchKeyEvent(paramKeyEvent);
    }

    void setFileDialogCallback(long paramLong) {
        this.mFileDialogCallback = paramLong;
    }

    public void displayDialog(int dialogId) {
        this.inputStatus = 0;
        switch (dialogId) {
            case 1:
                this.inputStatus = -1;
                runOnUiThread(new Runnable(this) {
                    final /* synthetic */ MainActivity bIh;

                    {
                        this.bIh = this$0;
                    }

                    public void run() {
                        this.bIh.showDialog(1);
                    }
                });
                return;
            case 3:
                this.inputStatus = -1;
                return;
            case 4:
                this.inputStatus = -1;
                runOnUiThread(new Runnable(this) {
                    final /* synthetic */ MainActivity bIh;

                    {
                        this.bIh = this$0;
                    }

                    public void run() {
                        this.bIh.showDialog(4);
                    }
                });
                return;
            default:
                return;
        }
    }

    public boolean doesRequireGuiBlocksPatch() {
        return a.doesRequireGuiBlocksPatch();
    }

    protected String filterUrl(String paramString) {
        return paramString;
    }

    public void forceTextureReload() {
        try {
            if (com.MCWorld.mcsdk.dtlib.h.CW().CX() == 3 || com.MCWorld.mcsdk.dtlib.h.CW().CX() == 5 || com.MCWorld.mcsdk.dtlib.h.CW().CX() == 7) {
                ScriptManager.nativeOnGraphicsReset();
            }
        } catch (Exception e) {
            com.MCWorld.mcsdk.log.a.verbose(com.MCWorld.mcsdk.log.a.aoP, "DTPrint is " + e, new Object[0]);
        }
    }

    public String getAccessToken() {
        return d.mB(0).getString(OAuth.ACCESS_TOKEN, "");
    }

    public int getAndroidVersion() {
        return VERSION.SDK_INT;
    }

    public String[] getBroadcastAddresses() {
        ArrayList localArrayList = new ArrayList();
        try {
            System.setProperty("java.net.preferIPv4Stack", "true");
            Enumeration localEnumeration = NetworkInterface.getNetworkInterfaces();
            while (localEnumeration.hasMoreElements()) {
                NetworkInterface localNetworkInterface = (NetworkInterface) localEnumeration.nextElement();
                if (!localNetworkInterface.isLoopback()) {
                    for (InterfaceAddress localInterfaceAddress : localNetworkInterface.getInterfaceAddresses()) {
                        if (localInterfaceAddress.getBroadcast() != null) {
                            localArrayList.add(localInterfaceAddress.getBroadcast().toString().substring(1));
                        }
                    }
                }
            }
            return (String[]) localArrayList.toArray(new String[localArrayList.size()]);
        } catch (Exception e) {
            return new String[]{"255.255.255.255"};
        }
    }

    public String getClientId() {
        return d.mB(0).getString("clientId", "");
    }

    public String getDateString(int paramInt) {
        DateFormat dateFormat = this.DateFormat;
        return DateFormat.getDateInstance(3, Locale.US).format(new Date(1000 * ((long) paramInt)));
    }

    public String getDeviceModel() {
        String str1 = Build.MANUFACTURER;
        String str2 = Build.MODEL;
        if (str2.startsWith(str1)) {
            return str2.toUpperCase();
        }
        return str1.toUpperCase() + " " + str2;
    }

    public byte[] readFiletoByteArray(String filename) throws IOException {
        IOException e;
        Throwable th;
        byte[] bArr = null;
        File f = new File(filename);
        if (f.exists()) {
            ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());
            BufferedInputStream bufferedInputStream = null;
            try {
                BufferedInputStream in = new BufferedInputStream(new FileInputStream(f));
                try {
                    byte[] buffer = new byte[1024];
                    while (true) {
                        int len = in.read(buffer, 0, 1024);
                        if (-1 == len) {
                            break;
                        }
                        bos.write(buffer, 0, len);
                    }
                    bArr = bos.toByteArray();
                    try {
                        in.close();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    }
                    bos.close();
                } catch (IOException e3) {
                    e2 = e3;
                    bufferedInputStream = in;
                    try {
                        e2.printStackTrace();
                        try {
                            bufferedInputStream.close();
                        } catch (IOException e22) {
                            e22.printStackTrace();
                        }
                        bos.close();
                        return bArr;
                    } catch (Throwable th2) {
                        th = th2;
                        try {
                            bufferedInputStream.close();
                        } catch (IOException e222) {
                            e222.printStackTrace();
                        }
                        bos.close();
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    bufferedInputStream = in;
                    bufferedInputStream.close();
                    bos.close();
                    throw th;
                }
            } catch (IOException e4) {
                e222 = e4;
                e222.printStackTrace();
                bufferedInputStream.close();
                bos.close();
                return bArr;
            }
        }
        return bArr;
    }

    public byte[] getFileDataBytes(String paramString) {
        try {
            byte[] tmpLocRetData = readFiletoByteArray(paramString);
            if (tmpLocRetData != null) {
                return tmpLocRetData;
            }
        } catch (Exception e) {
        }
        byte[] fileDataBytes = getFileDataBytes(paramString, false);
        return getFileDataBytes(paramString, false);
    }

    public byte[] getFileDataBytes(String paramString, boolean forceInternal) {
        InputStream localInputStream;
        byte[] bArr = null;
        if (forceInternal) {
            try {
                localInputStream = getLocalInputStreamForAsset(paramString);
            } catch (Exception localException) {
                localException.printStackTrace();
            }
        } else {
            localInputStream = getInputStreamForAsset(paramString);
        }
        if (localInputStream != null) {
            try {
                ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
                byte[] arrayOfByte = new byte[1024];
                for (int i = localInputStream.read(arrayOfByte); i > 0; i = localInputStream.read(arrayOfByte)) {
                    localByteArrayOutputStream.write(arrayOfByte, 0, i);
                }
                bArr = localByteArrayOutputStream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bArr;
    }

    public int[] getImageDataWith15(String paramString) {
        Bitmap localObject = BitmapFactory.decodeFile(paramString);
        if (localObject == null) {
            return null;
        }
        int _tmpWidth = localObject.getWidth();
        int _tmpHeight = localObject.getHeight();
        int[] arrayOfInt = new int[((_tmpWidth * _tmpHeight) + 2)];
        arrayOfInt[0] = _tmpWidth;
        arrayOfInt[1] = _tmpHeight;
        localObject.getPixels(arrayOfInt, 2, _tmpWidth, 0, 0, _tmpWidth, _tmpHeight);
        return arrayOfInt;
    }

    public int[] getImageData(String paramString) {
        int[] tmpRetData = getImageDataWith15(paramString);
        if (tmpRetData != null) {
            return tmpRetData;
        }
        try {
            InputStream localInputStream = getInputStreamForAsset(paramString);
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
        } catch (Exception localException) {
            localException.printStackTrace();
            return null;
        }
    }

    public InputStream getInputStreamForAsset(String name) {
        InputStream is;
        int i = 0;
        while (i < this.textureOverrides.size()) {
            try {
                is = ((h) this.textureOverrides.get(i)).cN(name);
                if (is != null) {
                    return is;
                }
                i++;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        if (this.texturePack == null) {
            return getLocalInputStreamForAsset(name);
        }
        is = this.texturePack.cN(name);
        return is == null ? getLocalInputStreamForAsset(name) : is;
    }

    public int getKeyFromKeyCode(int paramInt1, int paramInt2, int paramInt3) {
        return KeyCharacterMap.load(paramInt3).get(paramInt1, paramInt2);
    }

    protected InputStream getLocalInputStreamForAsset(String paramString) {
        return a.getLocalInputStreamForAsset(paramString);
    }

    public int getMaxNumPatches() {
        return getResources().getInteger(com.MCWorld.mclauncher010.b.h.max_num_patches);
    }

    public String[] getOptionStrings() {
        System.err.println("OptionStrings");
        SharedPreferences sharedPref = d.mB(0);
        Set<Entry> prefsSet = sharedPref.getAll().entrySet();
        ArrayList<String> retval = new ArrayList();
        for (Entry<String, ?> e : prefsSet) {
            String key = (String) e.getKey();
            if (key.indexOf("zz_") != 0) {
                retval.add(key);
                if (key.equals("ctrl_sensitivity")) {
                    retval.add(Double.toString(((double) Integer.parseInt(e.getValue().toString())) / 100.0d));
                }
                retval.add(e.getValue().toString());
            }
        }
        retval.add("game_difficulty");
        if (sharedPref.getBoolean("game_difficultypeaceful", false)) {
            retval.add("0");
        } else {
            retval.add("2");
        }
        return (String[]) retval.toArray(new String[0]);
    }

    public PackageManager getPackageManager() {
        if (a.QW()) {
            return new c(super.getPackageManager(), bbc);
        }
        return super.getPackageManager();
    }

    public String getPackageName() {
        if (a.QW()) {
            return a.QX();
        }
        return super.getPackageName();
    }

    public float getPixelsPerMillimeter() {
        float f1 = ((float) this.displayMetrics.densityDpi) / 25.4f;
        String str = d.mB(0).getString("zz_custom_dpi", null);
        if (str != null && str.length() > 0) {
            try {
                return Float.parseFloat(str) / 25.4f;
            } catch (Exception localException) {
                localException.printStackTrace();
            }
        }
        return f1;
    }

    public String getPlatformStringVar(int paramInt) {
        if (paramInt == 0) {
            return Build.MODEL;
        }
        return "";
    }

    public String getProfileId() {
        return d.mB(0).getString("profileUuid", "");
    }

    public String getProfileName() {
        return d.mB(0).getString("profileName", "");
    }

    public b getRealmsRedirectInfo() {
        return (b) b.bIC.get("NONE");
    }

    public String getRefreshToken() {
        return d.mB(0).getString("refreshToken", "");
    }

    public int getScreenHeight() {
        return this.displayMetrics.heightPixels;
    }

    public int getScreenWidth() {
        return this.displayMetrics.widthPixels;
    }

    public String getSession() {
        return d.mB(0).getString("sessionId", "");
    }

    public long getTotalMemory() {
        return avutil.AV_CH_SURROUND_DIRECT_RIGHT;
    }

    public int getUserInputStatus() {
        return this.inputStatus;
    }

    public String[] getUserInputString() {
        return this.userInputStrings;
    }

    public String getWebRequestContent(int paramInt) {
        return "ThisIsSparta";
    }

    public int getWebRequestStatus(int paramInt) {
        return 0;
    }

    public boolean hasBuyButtonWhenInvalidLicense() {
        return true;
    }

    public void hideKeyboard() {
        if (useLegacyKeyboardInput()) {
            hideKeyboardView();
        } else {
            runOnUiThread(new Runnable(this) {
                final /* synthetic */ MainActivity bIh;

                {
                    this.bIh = this$0;
                }

                public void run() {
                    this.bIh.dismissHiddenTextbox();
                }
            });
        }
    }

    protected void hideKeyboardView() {
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    public void initiateUserInput(int paramInt) {
    }

    public boolean isDemo() {
        return false;
    }

    public boolean isNetworkEnabled(boolean paramBoolean) {
        return true;
    }

    public boolean isRedirectingRealms() {
        return false;
    }

    public boolean isTouchscreen() {
        return d.mB(0).getBoolean("ctrl_usetouchscreen", true);
    }

    public void leaveGameCallback() {
    }

    protected void loginLaunchCallback(Uri paramUri) {
        this.loginDialog.dismiss();
        if (paramUri.getQueryParameter("sessionId") != null) {
            String str = paramUri.getQueryParameter("profileName");
            paramUri.getQueryParameter("identity");
            nativeLoginData(paramUri.getQueryParameter(OAuth.ACCESS_TOKEN), paramUri.getQueryParameter("clientToken"), paramUri.getQueryParameter("profileUuid"), str);
        }
    }

    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent) {
        a.a(paramInt1, paramInt2, paramIntent, this);
    }

    public void openLoginWindow() {
        runOnUiThread(new Runnable(this) {
            final /* synthetic */ MainActivity bIh;

            {
                this.bIh = this$0;
            }

            public void run() {
                WebView loginWebView = new WebView(this.bIh);
                loginWebView.loadUrl(this.bIh.getRealmsRedirectInfo().bIz);
                loginWebView.setLayoutParams(new LayoutParams(-1, -1));
                loginWebView.setWebViewClient(new b(this.bIh));
                loginWebView.loadUrl(this.bIh.getRealmsRedirectInfo().bIz);
                loginWebView.getSettings().setJavaScriptEnabled(true);
                this.bIh.loginDialog = new Dialog(this.bIh);
                this.bIh.loginDialog.setCancelable(true);
                this.bIh.loginDialog.requestWindowFeature(1);
                this.bIh.loginDialog.setContentView(loginWebView);
                this.bIh.loginDialog.getWindow().setLayout(-1, -1);
                this.bIh.loginDialog.show();
            }
        });
    }

    public void postScreenshotToFacebook(String paramString, int paramInt1, int paramInt2, int[] paramArrayOfInt) {
    }

    public void quit() {
        runOnUiThread(new Runnable(this) {
            final /* synthetic */ MainActivity bIh;

            {
                this.bIh = this$0;
            }

            public void run() {
                this.bIh.finish();
            }
        });
    }

    protected void resetOrientation() {
    }

    public void screenshotCallback(final File paramFile) {
        runOnUiThread(new Runnable(this) {
            final /* synthetic */ MainActivity bIh;

            public void run() {
                Toast.makeText(this.bIh, this.bIh.getResources().getString(n.screenshot_saved_as) + " " + paramFile.getAbsolutePath(), 1).show();
                MediaScannerConnection.scanFile(this.bIh, new String[]{paramFile.getAbsolutePath()}, new String[]{"image/png"}, null);
            }
        });
    }

    public void scriptOverrideTexture(String paramString1, String paramString2) {
        forceTextureReload();
    }

    public void scriptResetImages() {
        forceTextureReload();
    }

    public void scriptTooManyErrorsCallback(final String paramString) {
        runOnUiThread(new Runnable(this) {
            final /* synthetic */ MainActivity bIh;

            public void run() {
                new Builder(this.bIh).setTitle(n.script_execution_error).setMessage(paramString + " " + this.bIh.getResources().getString(n.script_too_many_errors)).setPositiveButton(17039370, null).show();
            }
        });
    }

    public void setIsPowerVR(boolean paramBoolean) {
        _isPowerVr = paramBoolean;
    }

    public static boolean isPowerVR() {
        return _isPowerVr;
    }

    public void setLevelCallback(boolean paramBoolean) {
    }

    public void setLoginInformation(String paramString1, String paramString2, String paramString3, String paramString4) {
        d.mB(0).edit().putString(OAuth.ACCESS_TOKEN, paramString1).putString("clientId", paramString2).putString("profileUuid", paramString3).putString("profileName", paramString4).apply();
    }

    public void setRefreshToken(String paramString) {
        d.mB(0).edit().putString("refreshToken", paramString).apply();
    }

    public void setSession(String paramString) {
        Editor localEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        localEditor.putString("sessionID", paramString);
        localEditor.commit();
    }

    public void showHiddenTextbox(String text, int maxLength, boolean dismissAfterOneLine) {
        if (this.hiddenTextWindow == null) {
            this.hiddenTextDismissAfterOneLine = dismissAfterOneLine;
            this.commandHistoryView = getLayoutInflater().inflate(i.chat_history_popup, null);
            this.hiddenTextView = (TextView) this.commandHistoryView.findViewById(com.MCWorld.mclauncher010.b.g.hidden_text_view);
            c localPopupTextWatcher = new c();
            this.hiddenTextView.addTextChangedListener(localPopupTextWatcher);
            this.hiddenTextView.setOnEditorActionListener(localPopupTextWatcher);
            this.hiddenTextView.setSingleLine(true);
            this.hiddenTextView.setImeOptions(301989893);
            this.hiddenTextView.setInputType(1);
            this.hiddenTextView.setFocusable(true);
            this.hiddenTextView.setFocusableInTouchMode(true);
            ((LinearLayout.LayoutParams) this.hiddenTextView.getLayoutParams()).width = (int) (((double) UtilsScreen.getScreenWidth(getApplicationContext())) * 0.7d);
            this.hiddenTextWindow = new PopupWindow(this.commandHistoryView);
            this.hiddenTextWindow.setWindowLayoutMode(-2, -2);
            this.hiddenTextWindow.setFocusable(true);
            this.hiddenTextWindow.setInputMethodMode(1);
            this.hiddenTextWindow.setBackgroundDrawable(new ColorDrawable());
            this.hiddenTextWindow.setClippingEnabled(false);
            this.hiddenTextWindow.setTouchable(false);
            this.hiddenTextWindow.setOutsideTouchable(true);
            this.hiddenTextWindow.setOnDismissListener(new OnDismissListener(this) {
                final /* synthetic */ MainActivity bIh;

                {
                    this.bIh = this$0;
                }

                public void onDismiss() {
                    this.bIh.nativeBackPressed();
                }
            });
        }
        this.hiddenTextView.setText(text);
        Selection.setSelection((Spannable) this.hiddenTextView.getText(), text.length());
        this.hiddenTextDismissAfterOneLine = dismissAfterOneLine;
        this.hiddenTextWindow.showAtLocation(getWindow().getDecorView(), 51, 0, 0);
        this.hiddenTextView.requestFocus();
        showKeyboardView();
    }

    public void showKeyboard(String paramString, int paramInt, boolean paramBoolean) {
        showKeyboard(paramString, paramInt, paramBoolean, false);
    }

    public void showKeyboard(final String paramString, final int paramInt, final boolean paramBoolean1, boolean paramBoolean2) {
        if (useLegacyKeyboardInput()) {
            showKeyboardView();
        } else {
            runOnUiThread(new Runnable(this) {
                final /* synthetic */ MainActivity bIh;

                public void run() {
                    this.bIh.showHiddenTextbox(paramString, paramInt, paramBoolean1);
                }
            });
        }
    }

    public void showKeyboardView() {
        Log.i("BlockLauncher/MainActivity", "Show keyboard view");
        ((InputMethodManager) getSystemService("input_method")).showSoftInput(getWindow().getDecorView(), 2);
    }

    public void statsTrackEvent(String firstEvent, String secondEvent) {
    }

    public void statsUpdateUserData(String paramString1, String paramString2) {
    }

    public boolean supportsNonTouchscreen() {
        int i = 0;
        int j = 0;
        for (String str : new String[]{Build.MODEL.toLowerCase(Locale.ENGLISH), Build.DEVICE.toLowerCase(Locale.ENGLISH), Build.PRODUCT.toLowerCase(Locale.ENGLISH)}) {
            if (str.indexOf("xperia") >= 0) {
                i = 1;
            }
            if (str.indexOf("play") >= 0) {
                j = 1;
            }
        }
        if (i == 0 || j == 0) {
            return false;
        }
        return true;
    }

    public void tick() {
    }

    public void launchUri(String paramString) {
        startActivity(new Intent("android.intent.action.VIEW", Uri.parse(paramString)));
    }

    public int getKeyboardHeighs() {
        return this.virtualKeyboardHeight;
    }

    public float getKeyboardHeight() {
        return (float) this.virtualKeyboardHeight;
    }

    public float getKeyboardHoight() {
        return (float) this.virtualKeyboardHeight;
    }

    public void updateTextboxText(final String text) {
        try {
            if (this.hiddenTextView != null) {
                this.hiddenTextView.post(new Runnable(this) {
                    final /* synthetic */ MainActivity bIh;

                    public void run() {
                        if (this.bIh.isCommandHistoryEnabled()) {
                            if (this.bIh.commandHistoryList.size() < 1 || ((String) this.bIh.commandHistoryList.get(this.bIh.commandHistoryList.size() - 1)).length() > 0) {
                                this.bIh.commandHistoryList.add(text);
                            } else {
                                this.bIh.commandHistoryList.set(this.bIh.commandHistoryList.size() - 1, text);
                            }
                        }
                        this.bIh.hiddenTextView.setText(text);
                    }
                });
            }
        } catch (Exception e) {
        }
    }

    public void vibrate(int paramInt) {
        if (d.mB(0).getBoolean("zz_longvibration", false)) {
            paramInt *= 5;
        }
        ((Vibrator) getSystemService("vibrator")).vibrate((long) paramInt);
    }

    public void webRequest(int paramInt, long paramLong, String paramString1, String paramString2, String paramString3) {
        webRequest(paramInt, paramLong, paramString1, paramString2, paramString3, "");
    }

    public void webRequest(int requestId, long timestamp, String url, String method, String cookies, String extraParam) {
        new Thread(new a(this, this, requestId, timestamp, filterUrl(url), method, cookies)).start();
    }

    public k getTexturePack() {
        return this.texturePack;
    }

    public void scriptErrorCallback(final String paramString, final Throwable paramThrowable) {
        runOnUiThread(new Runnable(this) {
            final /* synthetic */ MainActivity bIh;

            public void run() {
                StringWriter localStringWriter = new StringWriter();
                PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
                localPrintWriter.println("Error occurred in script: " + paramString);
                if (paramThrowable instanceof RhinoException) {
                    String str = ((RhinoException) paramThrowable).lineSource();
                    if (str != null) {
                        localPrintWriter.println(str);
                    }
                }
                paramThrowable.printStackTrace(localPrintWriter);
                new Builder(this.bIh).setTitle(n.script_execution_error).setMessage(localStringWriter.toString()).setPositiveButton(17039370, null).setNeutralButton(17039361, new 1(this, localStringWriter)).show();
            }
        });
    }

    public void scriptPrintCallback(final String paramString1, final String paramString2) {
        runOnUiThread(new Runnable(this) {
            final /* synthetic */ MainActivity bIh;

            public void run() {
                Toast.makeText(this.bIh, "Script " + paramString2 + ": " + paramString1, 0).show();
            }
        });
    }

    public static boolean isUseInstalledMode() {
        return USE_INSTALLED_MODE;
    }

    public static void setUseInstalledMode(boolean inputUseInstalledMode) {
        USE_INSTALLED_MODE = inputUseInstalledMode;
    }

    public void HLXScriptPrintCallback(final String paramString1, final String paramString2) {
        runOnUiThread(new Runnable(this) {
            final /* synthetic */ MainActivity bIh;

            public void run() {
                View view = LayoutInflater.from(this.bIh).inflate(i.toast_view1, null);
                ((TextView) view.findViewById(com.MCWorld.mclauncher010.b.g.toast_msg)).setText(paramString2 + paramString1);
                Toast toast = new Toast(this.bIh);
                toast.setGravity(17, 0, (int) (((double) (this.bIh.getResources().getDisplayMetrics().widthPixels < this.bIh.getResources().getDisplayMetrics().heightPixels ? this.bIh.getResources().getDisplayMetrics().widthPixels : this.bIh.getResources().getDisplayMetrics().heightPixels)) * 0.32d));
                toast.setDuration(700);
                toast.setView(view);
                toast.show();
            }
        });
    }

    public void updateLocalization(String paramString1, String paramString2) {
        System.out.println("Update localization: " + paramString1 + ":" + paramString2);
    }

    public String[] getIPAddresses() {
        System.out.println("get IP addresses?");
        return new String[]{"127.0.0.1"};
    }

    public Intent createAndroidLaunchIntent() {
        System.out.println("create android launch intent");
        Context localContext = getApplicationContext();
        return localContext.getPackageManager().getLaunchIntentForPackage(localContext.getPackageName());
    }
}
