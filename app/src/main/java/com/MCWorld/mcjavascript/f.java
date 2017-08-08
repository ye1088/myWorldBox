package com.MCWorld.mcjavascript;

import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.mcgame.g;
import com.MCWorld.mcjsmanager.b;
import com.MCWorld.mcsdk.DTSDKManagerEx;
import com.MCWorld.mcsdk.dtlib.h;
import com.mojang.minecraftpe.MainActivity;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.zhuoweizhang.mcpelauncher.Scrambler;
import net.zhuoweizhang.mcpelauncher.ScriptManager;
import net.zhuoweizhang.mcpelauncher.api.modpe.ArmorType;
import net.zhuoweizhang.mcpelauncher.api.modpe.MobEffect;
import net.zhuoweizhang.mcpelauncher.api.modpe.RendererManager;
import net.zhuoweizhang.mcpelauncher.c;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.NativeObject;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSFunction;

/* compiled from: DTJSManager */
public class f {
    private static final boolean DEBUG = false;
    private static final String TAG = "mcjavascript/DTJSManager";
    public static List<g> ajG = new ArrayList();
    static boolean ajH = false;
    private static final String[] ajI = new String[0];
    private static String currentScript;
    private static boolean scriptingEnabled = true;
    private static boolean scriptingInitialized = false;

    /* compiled from: DTJSManager */
    private static class a implements Runnable {
        public Throwable ajJ = null;
        private Reader in;
        private String sourceName;

        public a(Reader paramReader, String paramString) {
            this.in = paramReader;
            this.sourceName = paramString;
        }

        public void run() {
            try {
                Context localContext = Context.enter();
                f.setupContext(localContext);
                f.initJustLoadedScript(localContext, localContext.compileReader(this.in, this.sourceName, 0, null), this.sourceName);
                Context.exit();
            } catch (Throwable throwable) {
                HLog.error(f.TAG, "js parse thread run throw " + throwable, new Object[0]);
                this.ajJ = throwable;
            }
        }
    }

    public static boolean Al() {
        return scriptingInitialized;
    }

    public static void bX(boolean scriptingInitialized) {
        scriptingInitialized = scriptingInitialized;
    }

    public static boolean Am() {
        return scriptingEnabled;
    }

    public static void bY(boolean scriptingEnabled) {
        scriptingEnabled = scriptingEnabled;
    }

    public static void quit() {
        bY(true);
        bX(false);
        ajG.clear();
    }

    public static void init() {
        bZ(false);
    }

    public static boolean An() {
        return ajH;
    }

    public static void bZ(boolean loadMobEffectFlag) {
        ajH = loadMobEffectFlag;
    }

    public static void Ao() {
        if (!g.vY()) {
            g.bd(true);
            ScriptManager.nativeDefinePlaceholderBlocks();
        }
        if ((h.CW().CX() == 1 || h.CW().CX() == 2 || h.CW().CX() == 3 || h.CW().CX() == 5 || h.CW().CZ() == 1) && !An()) {
            MobEffect.initIds();
            bZ(true);
        }
        try {
            Aq();
        } catch (Exception localException) {
            localException.printStackTrace();
            a(null, localException);
        }
    }

    public static void Ap() {
        for (int i = 0; i < d.ajz.size(); i++) {
            e pTmpDTJSItem = (e) d.ajz.get(i);
            if (true == pTmpDTJSItem.isValid() && 1 == pTmpDTJSItem.Ai()) {
                DTSDKManagerEx.Ct();
                return;
            }
        }
    }

    private static void B(String paramString, String paramString2) {
        if (MainActivity.currentMainActivity != null) {
            MainActivity localMainActivity = (MainActivity) MainActivity.currentMainActivity.get();
            if (localMainActivity != null) {
                localMainActivity.HLXScriptPrintCallback(paramString, paramString2);
            }
        }
    }

    private static void Aq() throws IOException {
        for (int i = 0; i < d.ajz.size(); i++) {
            e pTmpDTJSItem = (e) d.ajz.get(i);
            if (true == pTmpDTJSItem.isValid() && 1 == pTmpDTJSItem.Ai()) {
                pTmpDTJSItem.hA(2);
                if (g.vR()) {
                    int nEncodeMode = pTmpDTJSItem.Ah();
                    if (nEncodeMode == 0) {
                        if (h.CW().Da() == 0) {
                            String path = pTmpDTJSItem.getPath();
                            B(pTmpDTJSItem.getName(), "葫芦侠提醒您: 正在加载--");
                            dd(path);
                            b.AA();
                        } else {
                            B("您的手机型号暂时不支持JS加载", "葫芦侠提醒您: ");
                        }
                    } else if (1 == nEncodeMode) {
                        h.C("data/", pTmpDTJSItem.Aj());
                    }
                }
            }
        }
    }

    private static void b(File file, boolean firstLoad) throws IOException {
        if (!firstLoad) {
            ScriptManager.modPkgTexturePack.P(file);
        }
        ZipFile v10_ZipFile = null;
        try {
            ZipFile v11 = new ZipFile(file);
            c v2 = c.a(v11);
            InputStream v3;
            byte[] v7;
            if (v2 != null) {
                if (v2.cmy.length() <= 0) {
                    v3 = v11.getInputStream((ZipEntry) null);
                    v7 = new byte[((int) ((ZipEntry) null).getSize())];
                    v3.read(v7);
                    v3.close();
                    loadScript(Scrambler.a(v7, v2), file.getName());
                } else {
                    loadScript(new InputStreamReader(v11.getInputStream((ZipEntry) null)), file.getName());
                }
                if (v10_ZipFile != null) {
                    v10_ZipFile.close();
                }
                if (!firstLoad) {
                    ScriptManager.modPkgTexturePack.iA(file.getName());
                }
            }
            Object v1;
            Enumeration v0 = v11.entries();
            while (true) {
                if (v0.hasMoreElements()) {
                    v1 = v0.nextElement();
                    String v4 = ((ZipEntry) v1).getName();
                    if (v4.startsWith("script/") && v4.toLowerCase().endsWith(hlx.data.localstore.a.bJY)) {
                        break;
                    }
                }
            }
            v3 = v11.getInputStream((ZipEntry) v1);
            v7 = new byte[((int) ((ZipEntry) v1).getSize())];
            v3.read(v7);
            v3.close();
            loadScript(Scrambler.a(v7, v2), file.getName());
            if (v10_ZipFile != null) {
                v10_ZipFile.close();
            }
            if (!firstLoad) {
                ScriptManager.modPkgTexturePack.iA(file.getName());
            }
        } catch (Exception e) {
            HLog.error("TAG", "DTPrint loadPackagedScript error " + e, new Object[0]);
        }
    }

    public static void dd(String path) throws IOException {
        File file = new File(path);
        Reader in = new FileReader(file);
        loadScript(in, file.getName());
        if (in != null) {
            in.close();
        }
    }

    private static void loadScript(Reader in, String sourceName) throws IOException {
        if (!Al()) {
            return;
        }
        if (scriptingEnabled) {
            a parseRunner = new a(in, sourceName);
            Thread t = new Thread(Thread.currentThread().getThreadGroup(), parseRunner, "mctools parse thread", 262144);
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
            }
            if (parseRunner.ajJ != null) {
                IOException back;
                if (parseRunner.ajJ instanceof IOException) {
                    back = parseRunner.ajJ;
                } else {
                    back = new IOException(parseRunner.ajJ);
                }
                throw back;
            }
            return;
        }
        throw new RuntimeException("Not available in multiplayer");
    }

    public static void setupContext(Context paramContext) {
        paramContext.setOptimizationLevel(-1);
    }

    private static String[] c(Class<? extends ScriptableObject> paramClass) {
        ArrayList localArrayList = new ArrayList();
        for (Method localMethod : paramClass.getMethods()) {
            if (localMethod.getAnnotation(JSFunction.class) != null) {
                localArrayList.add(localMethod.getName());
            }
        }
        return (String[]) localArrayList.toArray(ajI);
    }

    public static ScriptableObject classConstantsToJSObject(Class<?> paramClass) {
        NativeObject localNativeObject = new NativeObject();
        for (Field localField : paramClass.getFields()) {
            int k = localField.getModifiers();
            if (!Modifier.isStatic(k) || !Modifier.isPublic(k)) {
                try {
                } catch (Exception localException) {
                    localException.printStackTrace();
                }
            }
            localNativeObject.putConst(localField.getName(), localNativeObject, localField.get(null));
        }
        return localNativeObject;
    }

    public static void initJustLoadedScript(Context paramContext, Script paramScript, String paramString) {
        ScriptableObject localScriptableObject = paramContext.initStandardObjects(new DTBlockHostObject(), false);
        g localScriptState = new g(paramScript, localScriptableObject, paramString);
        localScriptableObject.defineFunctionProperties(c(DTBlockHostObject.class), DTBlockHostObject.class, 2);
        try {
            ScriptableObject.defineClass(localScriptableObject, DTNativePlayerApi.class);
            ScriptableObject.defineClass(localScriptableObject, DTNativeLevelApi.class);
            ScriptableObject.defineClass(localScriptableObject, DTNativeEntityApi.class);
            ScriptableObject.defineClass(localScriptableObject, DTNativeModPEApi.class);
            ScriptableObject.defineClass(localScriptableObject, DTNativeItemApi.class);
            ScriptableObject.putProperty(localScriptableObject, "ChatColor", classConstantsToJSObject(ChatColor.class));
            ScriptableObject.putProperty(localScriptableObject, "ItemCategory", classConstantsToJSObject(ItemCategory.class));
            ScriptableObject.defineClass(localScriptableObject, DTNativeBlockApi.class);
            ScriptableObject.defineClass(localScriptableObject, DTNativeServerApi.class);
            RendererManager.defineClasses(localScriptableObject);
            ScriptableObject.putProperty(localScriptableObject, "ParticleType", classConstantsToJSObject(ParticleType.class));
            ScriptableObject.putProperty(localScriptableObject, "EntityType", classConstantsToJSObject(EntityType.class));
            ScriptableObject.putProperty(localScriptableObject, "EntityRenderType", classConstantsToJSObject(EntityRenderType.class));
            ScriptableObject.putProperty(localScriptableObject, "ArmorType", classConstantsToJSObject(ArmorType.class));
            ScriptableObject.putProperty(localScriptableObject, "MobEffect", classConstantsToJSObject(MobEffect.class));
            paramScript.exec(paramContext, localScriptableObject);
            ajG.add(localScriptState);
        } catch (Exception localException) {
            localException.printStackTrace();
            a(localScriptState, localException);
        }
    }

    public static void scriptPrint(String paramString) {
        if (MainActivity.currentMainActivity != null) {
            MainActivity localMainActivity = (MainActivity) MainActivity.currentMainActivity.get();
            if (localMainActivity != null) {
                localMainActivity.scriptPrintCallback(paramString, currentScript);
            }
        }
    }

    public static void a(g state, Throwable t) {
        if (state != null) {
            state.errors++;
        }
        if (MainActivity.currentMainActivity != null) {
            MainActivity main = (MainActivity) MainActivity.currentMainActivity.get();
            if (main == null) {
                return;
            }
            if (state == null) {
                main.scriptErrorCallback("JS错误", t);
                return;
            }
            main.scriptErrorCallback(state.name, t);
            if (state != null && state.errors >= 5) {
                main.scriptTooManyErrorsCallback(state.name);
            }
        }
    }

    public static void removeScript(String paramString) {
        for (int i = ajG.size() - 1; i >= 0; i--) {
            if (((g) ajG.get(i)).name.equals(paramString)) {
                ajG.remove(i);
            }
        }
    }

    public static void d(String paramString, boolean bEnabled) {
        for (int i = ajG.size() - 1; i >= 0; i--) {
            if (((g) ajG.get(i)).name.equals(paramString)) {
                ((g) ajG.get(i)).bW(bEnabled);
            }
        }
    }

    public static void c(String scriptName, String functionName, Object[] args) {
        if (Am()) {
            Context ctx = Context.enter();
            setupContext(ctx);
            for (g state : ajG) {
                if (state.errors < 5) {
                    currentScript = state.name;
                    Scriptable scope = state.scope;
                    if (currentScript.equals(scriptName)) {
                        Object obj = scope.get(functionName, scope);
                        if (obj != null && (obj instanceof Function)) {
                            try {
                                ((Function) obj).call(ctx, scope, scope, args);
                                return;
                            } catch (Exception e) {
                                e.printStackTrace();
                                a(state, e);
                                return;
                            }
                        }
                        return;
                    }
                }
            }
        }
    }

    public static void callScriptMethod(String functionName, Object... args) {
        if (scriptingEnabled) {
            Context ctx = Context.enter();
            setupContext(ctx);
            for (g state : ajG) {
                if (state.errors < 5) {
                    currentScript = state.name;
                    Scriptable scope = state.scope;
                    if (state.Ak()) {
                        Object obj = scope.get(functionName, scope);
                        if (obj != null && (obj instanceof Function)) {
                            try {
                                ((Function) obj).call(ctx, scope, scope, args);
                            } catch (Exception e) {
                                e.printStackTrace();
                                a(state, e);
                            }
                        }
                    }
                }
            }
        }
    }

    private static boolean v(File file) {
        return de(file.getName());
    }

    private static boolean de(String str) {
        return str.toLowerCase().endsWith(hlx.data.localstore.a.bJY);
    }
}
