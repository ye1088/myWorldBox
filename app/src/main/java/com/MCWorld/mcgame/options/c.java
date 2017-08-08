package com.MCWorld.mcgame.options;

import android.os.Build.VERSION;
import android.os.Environment;
import com.MCWorld.mcgame.g;
import com.MCWorld.mcgame.z;
import com.MCWorld.mcsdk.dtlib.h;
import com.tencent.connect.common.Constants;
import com.tencent.mm.sdk.platformtools.SpecilApiUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/* compiled from: OptionsV0111 */
public class c {
    private static final boolean DEBUG = false;
    public static final String OPTIONS_FILE_PATH = "games/com.mojang/minecraftpe/options.txt";
    private static DTGameOptions agM = null;
    public static final String agN = "games/com.mojang/minecraftpe/custom.png";
    static String[] agP = new String[]{"mp_username", "game_difficulty", "game_thirdperson", "gfx_dpadscale", "mp_server_visible", "game_flatworldlayers", "game_limitworldsize", "game_language", "game_skintypefull", "game_lastcustomskinnew", "ctrl_sensitivity", "ctrl_invertmouse", "ctrl_islefthanded", "ctrl_usetouchscreen", "ctrl_usetouchjoypad", "feedback_vibration", "gfx_renderdistance_new", "gfx_fancygraphics", "gfx_fancyskies", "gfx_animatetextures", "gfx_hidegui", "gfx_gamma", "audio_sound", "dev_autoloadlevel", "dev_showchunkmap", "dev_disablefilesystem", "old_game_version_major", "old_game_version_minor", "old_game_version_patch", "old_game_version_beta"};

    private static void u(String fileName, String write_str) throws IOException {
        try {
            FileOutputStream fout = new FileOutputStream(fileName);
            fout.write(write_str.getBytes());
            fout.flush();
            fout.flush();
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void y(String userName, String userSkin) {
        int i;
        FileNotFoundException localFileNotFoundException;
        IOException localIOException;
        Throwable th;
        File tmpLocalFile;
        String fileContext;
        String mcSetName;
        BufferedReader localBufferedReader = null;
        String[] stringValArray = new String[30];
        try {
            File localFile = new File(Environment.getExternalStorageDirectory(), "games/com.mojang/minecraftpe/options.txt");
            if (localFile.exists()) {
                BufferedReader localBufferedReader2 = new BufferedReader(new InputStreamReader(new FileInputStream(localFile)));
                while (true) {
                    try {
                        String str = localBufferedReader2.readLine();
                        if (str == null) {
                            break;
                        }
                        try {
                            String[] arrayOfString = str.split(":");
                            String str1 = arrayOfString[0];
                            String str2 = arrayOfString[1];
                            if (str1 != null && str2 != null) {
                                i = 0;
                                while (i < 30) {
                                    if (agP[i] != null && str1.equalsIgnoreCase(agP[i])) {
                                        stringValArray[i] = str2;
                                        break;
                                    }
                                    i++;
                                }
                            }
                        } catch (Exception e) {
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
                }
            }
        } catch (FileNotFoundException e5) {
            localFileNotFoundException = e5;
            try {
                localFileNotFoundException.printStackTrace();
                if (localBufferedReader != null) {
                    try {
                        localBufferedReader.close();
                    } catch (IOException e6) {
                    }
                }
                tmpLocalFile = new File(Environment.getExternalStorageDirectory(), "games/com.mojang/minecraftpe/options.txt");
                tmpLocalFile.setReadable(true);
                tmpLocalFile.setWritable(true);
                fileContext = "";
                while (i < 30) {
                    if (agP[i] != null) {
                        if (!agP[i].equalsIgnoreCase("mp_username")) {
                            mcSetName = g.wj();
                            if (mcSetName == null) {
                                if (!stringValArray[i].equalsIgnoreCase("Steve")) {
                                }
                                fileContext = agP[i] + ":" + mcSetName;
                            } else {
                                fileContext = agP[i] + ":" + stringValArray[i];
                            }
                        } else if (!agP[i].equalsIgnoreCase("game_skintypefull")) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + userSkin;
                        } else if (!agP[i].equalsIgnoreCase("game_lastcustomskinnew")) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + userSkin;
                        } else if (!agP[i].equalsIgnoreCase("old_game_version_major")) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + "0";
                        } else if (!agP[i].equalsIgnoreCase("old_game_version_minor")) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE;
                        } else if (!agP[i].equalsIgnoreCase("old_game_version_patch")) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + "1";
                        } else if (!agP[i].equalsIgnoreCase("old_game_version_beta")) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + "0";
                        } else if (agP[i].equalsIgnoreCase("game_language")) {
                            if (!agP[i].equalsIgnoreCase("dev_autoloadlevel")) {
                                fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + "0";
                            } else if (stringValArray[i] == null) {
                                fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + stringValArray[i];
                            } else if (!agP[i].equalsIgnoreCase("gfx_dpadscale")) {
                                fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + "0.7";
                            }
                        } else if (g.wi() == 1) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + "zh_CN";
                        } else {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + "en_US";
                        }
                    }
                }
                u(tmpLocalFile.getPath(), fileContext);
            } catch (Throwable th3) {
                th = th3;
                if (localBufferedReader != null) {
                    try {
                        localBufferedReader.close();
                    } catch (IOException e7) {
                    }
                }
                throw th;
            }
        } catch (IOException e8) {
            localIOException = e8;
            localIOException.printStackTrace();
            if (localBufferedReader != null) {
                try {
                    localBufferedReader.close();
                } catch (IOException e9) {
                }
            }
            tmpLocalFile = new File(Environment.getExternalStorageDirectory(), "games/com.mojang/minecraftpe/options.txt");
            tmpLocalFile.setReadable(true);
            tmpLocalFile.setWritable(true);
            fileContext = "";
            while (i < 30) {
                if (agP[i] != null) {
                    if (!agP[i].equalsIgnoreCase("mp_username")) {
                        mcSetName = g.wj();
                        if (mcSetName == null) {
                            fileContext = agP[i] + ":" + stringValArray[i];
                        } else {
                            if (stringValArray[i].equalsIgnoreCase("Steve")) {
                            }
                            fileContext = agP[i] + ":" + mcSetName;
                        }
                    } else if (!agP[i].equalsIgnoreCase("game_skintypefull")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + userSkin;
                    } else if (!agP[i].equalsIgnoreCase("game_lastcustomskinnew")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + userSkin;
                    } else if (!agP[i].equalsIgnoreCase("old_game_version_major")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + "0";
                    } else if (!agP[i].equalsIgnoreCase("old_game_version_minor")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE;
                    } else if (!agP[i].equalsIgnoreCase("old_game_version_patch")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + "1";
                    } else if (!agP[i].equalsIgnoreCase("old_game_version_beta")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + "0";
                    } else if (agP[i].equalsIgnoreCase("game_language")) {
                        if (g.wi() == 1) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + "en_US";
                        } else {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + "zh_CN";
                        }
                    } else if (!agP[i].equalsIgnoreCase("dev_autoloadlevel")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + "0";
                    } else if (stringValArray[i] == null) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + stringValArray[i];
                    } else if (!agP[i].equalsIgnoreCase("gfx_dpadscale")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + "0.7";
                    }
                }
            }
            u(tmpLocalFile.getPath(), fileContext);
        }
        try {
            tmpLocalFile = new File(Environment.getExternalStorageDirectory(), "games/com.mojang/minecraftpe/options.txt");
            if (!tmpLocalFile.exists() && tmpLocalFile.createNewFile()) {
                tmpLocalFile.setReadable(true);
                tmpLocalFile.setWritable(true);
            }
            fileContext = "";
            for (i = 0; i < 30; i++) {
                if (agP[i] != null) {
                    if (!agP[i].equalsIgnoreCase("mp_username")) {
                        mcSetName = g.wj();
                        if (mcSetName == null) {
                            fileContext = agP[i] + ":" + stringValArray[i];
                        } else if (stringValArray[i].equalsIgnoreCase("Steve") || VERSION.SDK_INT < 11) {
                            fileContext = agP[i] + ":" + mcSetName;
                        } else {
                            fileContext = agP[i] + ":" + stringValArray[i];
                        }
                    } else if (!agP[i].equalsIgnoreCase("game_skintypefull")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + userSkin;
                    } else if (!agP[i].equalsIgnoreCase("game_lastcustomskinnew")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + userSkin;
                    } else if (!agP[i].equalsIgnoreCase("old_game_version_major")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + "0";
                    } else if (!agP[i].equalsIgnoreCase("old_game_version_minor")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + Constants.VIA_REPORT_TYPE_SHARE_TO_QZONE;
                    } else if (!agP[i].equalsIgnoreCase("old_game_version_patch")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + "1";
                    } else if (!agP[i].equalsIgnoreCase("old_game_version_beta")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + "0";
                    } else if (agP[i].equalsIgnoreCase("game_language")) {
                        if (g.wi() == 1) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + "en_US";
                        } else {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + "zh_CN";
                        }
                    } else if (!agP[i].equalsIgnoreCase("dev_autoloadlevel")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + "0";
                    } else if (stringValArray[i] == null) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + stringValArray[i];
                    } else if (!agP[i].equalsIgnoreCase("gfx_dpadscale")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agP[i] + ":" + "0.7";
                    }
                }
            }
            u(tmpLocalFile.getPath(), fileContext);
        } catch (Exception e10) {
        }
    }

    public static DTGameOptions za() {
        if (agM == null) {
            agM = new DTGameOptions();
        }
        return agM;
    }

    private static void zb() {
        File localSkinFile = new File(Environment.getExternalStorageDirectory(), "games/com.mojang/minecraftpe/custom.png");
        if (localSkinFile.exists()) {
            com.MCWorld.mcsdk.dtlib.c.copyFile(z.yr(), localSkinFile.getPath());
        } else {
            com.MCWorld.mcsdk.dtlib.c.copyFile(z.yr(), localSkinFile.getPath());
        }
    }

    public static void init() {
        if (h.CW().CX() != 1) {
            return;
        }
        if (z.yr() != null) {
            y("heel23", DTGameOptions.SKIN_TYPE_Custom);
            z.bF(true);
            return;
        }
        y("heel0o", DTGameOptions.SKIN_TYPE_Steve);
    }
}
