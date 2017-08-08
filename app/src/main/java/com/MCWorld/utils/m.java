package com.MCWorld.utils;

import android.content.Context;
import android.util.Log;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.utils.UtilsFile;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: PpssppIniResolver */
public class m {
    public static final String bkA = "2";
    public static final String bkB = "True";
    public static final String bkC = "True";
    public static final String bkD = "False";
    public static final String bkE = "True";
    public static final String bkF = "True";
    public static final String bkG = "True";
    public static final String bkH = "True";
    public static final String bkI = "False";
    public static final String bkJ = "True";
    public static final String bkK = "True";
    public static final String bkL = "True";
    public static final String bkM = "False";
    public static final String bkN = "False";
    public static final String bkO = "False";
    public static final String bkP = "False";
    public static final String bkQ = "False";
    public static final String bks = "PSP/SYSTEM";
    public static final String bkt = (bks + File.separator + "ppsspp_.ini");
    public static final String bku = "PSP/GAME";
    public static final String bkv = "False";
    public static final String bkw = "zh_CN";
    public static final String bkx = "True";
    public static final String bky = "1";
    public static final String bkz = "1";

    public static String bh(Context context) {
        return UtilsFile.getDiskCacheDir(context, bku).getAbsolutePath();
    }

    public static l bi(Context context) {
        FileNotFoundException localFileNotFoundException;
        IOException localIOException;
        Throwable th;
        l ppssppIni = new l();
        BufferedReader localBufferedReader = null;
        try {
            File dir = UtilsFile.getDiskCacheDir(context, bks);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File localFile = UtilsFile.getDiskCacheDir(context, bkt);
            String blockName = null;
            if (localFile.exists()) {
                BufferedReader localBufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(localFile)));
                int line = 0;
                while (true) {
                    try {
                        String str = localBufferedReader2.readLine();
                        if (str == null) {
                            break;
                        }
                        str = str.trim();
                        if (line == 0) {
                            byte[] strBytes = str.getBytes();
                            str = new String(strBytes, 3, strBytes.length - 3);
                        }
                        line++;
                        try {
                            if (str.startsWith("[") && str.endsWith("]")) {
                                blockName = str;
                                ppssppIni.KK().put(blockName, new HashMap());
                            } else {
                                a(str, ppssppIni, blockName);
                            }
                        } catch (Exception e) {
                            HLog.error("Options", "build options error, str = " + str, e, new Object[0]);
                        }
                    } catch (FileNotFoundException e2) {
                        localFileNotFoundException = e2;
                        localBufferedReader = localBufferedReader2;
                    } catch (IOException e3) {
                        localIOException = e3;
                        localBufferedReader = localBufferedReader2;
                    } catch (Throwable th2) {
                        th = th2;
                        localBufferedReader = localBufferedReader2;
                    }
                }
                localBufferedReader = localBufferedReader2;
            }
            if (localBufferedReader != null) {
                try {
                    localBufferedReader.close();
                } catch (IOException e4) {
                    Log.e("Options", "close bufferreader error", e4);
                }
            }
        } catch (FileNotFoundException e5) {
            localFileNotFoundException = e5;
            try {
                localFileNotFoundException.printStackTrace();
                if (localBufferedReader != null) {
                    try {
                        localBufferedReader.close();
                    } catch (IOException e42) {
                        Log.e("Options", "close bufferreader error", e42);
                    }
                }
                return ppssppIni;
            } catch (Throwable th3) {
                th = th3;
                if (localBufferedReader != null) {
                    try {
                        localBufferedReader.close();
                    } catch (IOException e422) {
                        Log.e("Options", "close bufferreader error", e422);
                    }
                }
                throw th;
            }
        } catch (IOException e6) {
            localIOException = e6;
            localIOException.printStackTrace();
            if (localBufferedReader != null) {
                try {
                    localBufferedReader.close();
                } catch (IOException e4222) {
                    Log.e("Options", "close bufferreader error", e4222);
                }
            }
            return ppssppIni;
        }
        return ppssppIni;
    }

    public static void a(String line, l ppssppIni, String blockName) {
        if (line != null && blockName != null) {
            Map<String, String> block = (Map) ppssppIni.KK().get(blockName);
            if (block != null && line.indexOf(SimpleComparison.EQUAL_TO_OPERATION) >= 0) {
                String[] strings = line.split(SimpleComparison.EQUAL_TO_OPERATION);
                if (strings.length <= 1) {
                    block.put(strings[0].trim(), null);
                } else {
                    block.put(strings[0].trim(), strings[1]);
                }
            }
        }
    }

    public static void a(Context context, l ppssppIni) {
        if (ppssppIni != null) {
            try {
                File dir = UtilsFile.getDiskCacheDir(context, bks);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File iniFile = UtilsFile.getDiskCacheDir(context, bkt);
                if (!iniFile.exists()) {
                    iniFile.createNewFile();
                }
                BufferedWriter localBufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(iniFile)));
                for (Entry localEntry : ppssppIni.KK().entrySet()) {
                    localBufferedWriter.write((String) localEntry.getKey());
                    localBufferedWriter.newLine();
                    Map<String, String> map = (Map) localEntry.getValue();
                    if (map == null) {
                        Log.e("", "map = " + map + " is null");
                    } else {
                        for (Entry itemEntry : map.entrySet()) {
                            String itemValue = (String) itemEntry.getValue();
                            localBufferedWriter.write(((String) itemEntry.getKey()) + " = " + itemValue);
                            localBufferedWriter.newLine();
                        }
                    }
                }
                localBufferedWriter.flush();
                localBufferedWriter.close();
            } catch (IOException localIOException) {
                localIOException.printStackTrace();
            }
        }
    }

    public static void B(Context ctx, String path) {
        l ini = bi(ctx);
        Map<String, String> block;
        if (ini != null) {
            block = (Map) ini.KK().get("[General]");
            if (block == null) {
                block = new HashMap();
                ini.KK().put("[General]", block);
            }
            block.put("CurrentDirectory", bh(ctx));
            block.put("Enable Logging", "False");
            block.put("Language", "zh_CN");
            block.put("DumpDecryptedEboots", "True");
            block = (Map) ini.KK().get("[Recent]");
            if (block == null) {
                block = new HashMap();
                ini.KK().put("[Recent]", block);
            }
            block.put("FileName0", path);
            block = (Map) ini.KK().get("[Graphics]");
            if (block == null) {
                block = new HashMap();
                ini.KK().put("[Graphics]", block);
            }
            block.put("ShowFPSCounter", "1");
            block.put("RenderingMode", "1");
            block.put("InternalResolution", "2");
            block.put("TextureBackoffCache", "True");
            block.put("TextureSecondaryCache", "True");
            block.put("MipMap", "False");
            block.put("DisableStencilTest", "True");
            block.put("AlwaysDepthWrite", "True");
            block.put("DepthRangeHack", "True");
            block.put("TimerHack", "True");
            block.put("MemBlockTransferGPU", "False");
            block.put("DisableSlowFramebufEffects", "True");
            block = (Map) ini.KK().get("[SpeedHacks]");
            if (block == null) {
                block = new HashMap();
                ini.KK().put("[SpeedHacks]", block);
            }
            block.put("PrescaleUV", "True");
            block.put("DisableAlphaTest", "True");
            block = (Map) ini.KK().get("[Control]");
            if (block == null) {
                block = new HashMap();
                ini.KK().put("[Control]", block);
            }
            block.put("ShowComboKey", "False");
            block.put("ShowComboKey1", "False");
            block.put("ShowComboKey2", "False");
            block.put("ShowComboKey3", "False");
            block.put("ShowComboKey4", "False");
        } else {
            ini = new l();
            block = new HashMap();
            ini.KK().put("[General]", block);
            block.put("CurrentDirectory", bh(ctx));
            block.put("Enable Logging", "False");
            block.put("Language", "zh_CN");
            block = new HashMap();
            ini.KK().put("[Recent]", block);
            block.put("FileName0", path);
            block = new HashMap();
            ini.KK().put("[Graphics]", block);
            block.put("ShowFPSCounter", "1");
            block.put("RenderingMode", "1");
            block.put("InternalResolution", "2");
            block.put("TextureBackoffCache", "True");
            block.put("TextureSecondaryCache", "True");
            block.put("MipMap", "False");
            block.put("DisableStencilTest", "True");
            block.put("AlwaysDepthWrite", "True");
            block.put("DepthRangeHack", "True");
            block.put("TimerHack", "True");
            block.put("MemBlockTransferGPU", "False");
            block.put("DisableSlowFramebufEffects", "True");
            block = new HashMap();
            ini.KK().put("[SpeedHacks]", block);
            block.put("PrescaleUV", "True");
            block.put("DisableAlphaTest", "True");
            block = new HashMap();
            ini.KK().put("[Control]", block);
            block.put("ShowComboKey", "False");
            block.put("ShowComboKey1", "False");
            block.put("ShowComboKey2", "False");
            block.put("ShowComboKey3", "False");
            block.put("ShowComboKey4", "False");
        }
        a(ctx, ini);
    }
}
