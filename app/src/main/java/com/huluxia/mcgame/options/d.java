package com.huluxia.mcgame.options;

import android.os.Build.VERSION;
import android.os.Environment;
import com.huluxia.mcgame.g;
import com.huluxia.mcgame.z;
import com.huluxia.mcsdk.dtlib.c;
import com.huluxia.mcsdk.dtlib.h;
import com.tencent.connect.common.Constants;
import com.tencent.mm.sdk.platformtools.SpecilApiUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/* compiled from: OptionsV0121 */
public class d {
    private static final boolean DEBUG = false;
    public static final String OPTIONS_FILE_PATH = "games/com.mojang/minecraftpe/options.txt";
    private static DTGameOptions agM = null;
    public static final String agN = "games/com.mojang/minecraftpe/custom.png";
    private static final int agQ = 38;
    static String[] agR = new String[]{"mp_username", "game_difficulty_new", "game_thirdperson", "gfx_dpadscale", "mp_server_visible", "game_flatworldlayers", "game_limitworldsize", "game_language", "game_skintypefull", "game_lastcustomskinnew", "ctrl_sensitivity", "ctrl_invertmouse", "ctrl_islefthanded", "ctrl_usetouchscreen", "ctrl_usetouchjoypad", "ctrl_swapjumpandsneak", "feedback_vibration", "ctrl_autojump", "ctrl_keyboardlayout", "ctrl_gamePadMap", "gfx_renderdistance_new", "gfx_viewbobbing", "gfx_fancygraphics", "gfx_fancyskies", "gfx_animatetextures", "gfx_hidegui", "gfx_field_of_view", "gfx_gamma", "gfx_fullscreen", "audio_sound", "audio_music", "dev_autoloadlevel", "dev_showchunkmap", "dev_disablefilesystem", "old_game_version_major", "old_game_version_minor", "old_game_version_patch", "old_game_version_beta"};

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

    public static void z(String userName, String userSkin) {
        int i;
        FileNotFoundException localFileNotFoundException;
        IOException localIOException;
        Throwable th;
        File tmpLocalFile;
        String fileContext;
        String mcSetName;
        BufferedReader localBufferedReader = null;
        String[] stringValArray = new String[38];
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
                                while (i < 38) {
                                    if (agR[i] != null && str1.equalsIgnoreCase(agR[i])) {
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
                while (i < 38) {
                    if (agR[i] != null) {
                        if (!agR[i].equalsIgnoreCase("mp_username")) {
                            mcSetName = g.wj();
                            if (mcSetName == null) {
                                if (!stringValArray[i].equalsIgnoreCase("Steve")) {
                                }
                                fileContext = agR[i] + ":" + mcSetName;
                            } else {
                                fileContext = agR[i] + ":" + stringValArray[i];
                            }
                        } else if (!agR[i].equalsIgnoreCase("game_difficulty_new")) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "1";
                        } else if (!agR[i].equalsIgnoreCase("game_skintypefull")) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + userSkin;
                        } else if (!agR[i].equalsIgnoreCase("game_lastcustomskinnew")) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + userSkin;
                        } else if (!agR[i].equalsIgnoreCase("old_game_version_major")) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "0";
                        } else if (agR[i].equalsIgnoreCase("old_game_version_minor")) {
                            if (agR[i].equalsIgnoreCase("old_game_version_patch")) {
                                if (!agR[i].equalsIgnoreCase("old_game_version_beta")) {
                                    fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "0";
                                } else if (agR[i].equalsIgnoreCase("game_language")) {
                                    if (!agR[i].equalsIgnoreCase("dev_autoloadlevel")) {
                                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "0";
                                    } else if (stringValArray[i] == null) {
                                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + stringValArray[i];
                                    } else if (!agR[i].equalsIgnoreCase("gfx_dpadscale")) {
                                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "0.7";
                                    }
                                } else if (h.CW().CX() == 2) {
                                    if (h.CW().CX() != 3) {
                                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "zh_CN";
                                    } else if (h.CW().CX() != 5) {
                                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "zh_CN";
                                    } else if (h.CW().CX() != 7) {
                                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "zh_CN";
                                    }
                                } else if (g.wi() == 1) {
                                    fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "zh_CN";
                                } else {
                                    fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "en_US";
                                }
                            } else if (h.CW().CX() != 2) {
                                fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "3";
                            } else if (h.CW().CX() == 3) {
                                if (h.CW().CX() == 5) {
                                    if (h.CW().CY() == 0) {
                                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "3";
                                    } else {
                                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "0";
                                    }
                                }
                            } else if (h.CW().CY() == 0) {
                                fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "1";
                            } else {
                                fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "0";
                            }
                        } else if (h.CW().CX() != 2) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + Constants.VIA_REPORT_TYPE_SET_AVATAR;
                        } else if (h.CW().CX() != 3) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + Constants.VIA_REPORT_TYPE_JOININ_GROUP;
                        } else if (h.CW().CX() != 5) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + Constants.VIA_REPORT_TYPE_MAKE_FRIEND;
                        } else if (h.CW().CX() != 7) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + Constants.VIA_REPORT_TYPE_WPA_STATE;
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
            while (i < 38) {
                if (agR[i] != null) {
                    if (!agR[i].equalsIgnoreCase("mp_username")) {
                        mcSetName = g.wj();
                        if (mcSetName == null) {
                            fileContext = agR[i] + ":" + stringValArray[i];
                        } else {
                            if (stringValArray[i].equalsIgnoreCase("Steve")) {
                            }
                            fileContext = agR[i] + ":" + mcSetName;
                        }
                    } else if (!agR[i].equalsIgnoreCase("game_difficulty_new")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "1";
                    } else if (!agR[i].equalsIgnoreCase("game_skintypefull")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + userSkin;
                    } else if (!agR[i].equalsIgnoreCase("game_lastcustomskinnew")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + userSkin;
                    } else if (!agR[i].equalsIgnoreCase("old_game_version_major")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "0";
                    } else if (agR[i].equalsIgnoreCase("old_game_version_minor")) {
                        if (h.CW().CX() != 2) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + Constants.VIA_REPORT_TYPE_SET_AVATAR;
                        } else if (h.CW().CX() != 3) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + Constants.VIA_REPORT_TYPE_JOININ_GROUP;
                        } else if (h.CW().CX() != 5) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + Constants.VIA_REPORT_TYPE_MAKE_FRIEND;
                        } else if (h.CW().CX() != 7) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + Constants.VIA_REPORT_TYPE_WPA_STATE;
                        }
                    } else if (agR[i].equalsIgnoreCase("old_game_version_patch")) {
                        if (h.CW().CX() != 2) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "3";
                        } else if (h.CW().CX() == 3) {
                            if (h.CW().CY() == 0) {
                                fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "0";
                            } else {
                                fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "1";
                            }
                        } else if (h.CW().CX() == 5) {
                            if (h.CW().CY() == 0) {
                                fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "0";
                            } else {
                                fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "3";
                            }
                        }
                    } else if (!agR[i].equalsIgnoreCase("old_game_version_beta")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "0";
                    } else if (agR[i].equalsIgnoreCase("game_language")) {
                        if (h.CW().CX() == 2) {
                            if (g.wi() == 1) {
                                fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "en_US";
                            } else {
                                fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "zh_CN";
                            }
                        } else if (h.CW().CX() != 3) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "zh_CN";
                        } else if (h.CW().CX() != 5) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "zh_CN";
                        } else if (h.CW().CX() != 7) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "zh_CN";
                        }
                    } else if (!agR[i].equalsIgnoreCase("dev_autoloadlevel")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "0";
                    } else if (stringValArray[i] == null) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + stringValArray[i];
                    } else if (!agR[i].equalsIgnoreCase("gfx_dpadscale")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "0.7";
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
            for (i = 0; i < 38; i++) {
                if (agR[i] != null) {
                    if (!agR[i].equalsIgnoreCase("mp_username")) {
                        mcSetName = g.wj();
                        if (mcSetName == null) {
                            fileContext = agR[i] + ":" + stringValArray[i];
                        } else if (stringValArray[i].equalsIgnoreCase("Steve") || VERSION.SDK_INT < 11) {
                            fileContext = agR[i] + ":" + mcSetName;
                        } else {
                            fileContext = agR[i] + ":" + stringValArray[i];
                        }
                    } else if (!agR[i].equalsIgnoreCase("game_difficulty_new")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "1";
                    } else if (!agR[i].equalsIgnoreCase("game_skintypefull")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + userSkin;
                    } else if (!agR[i].equalsIgnoreCase("game_lastcustomskinnew")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + userSkin;
                    } else if (!agR[i].equalsIgnoreCase("old_game_version_major")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "0";
                    } else if (agR[i].equalsIgnoreCase("old_game_version_minor")) {
                        if (h.CW().CX() != 2) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + Constants.VIA_REPORT_TYPE_SET_AVATAR;
                        } else if (h.CW().CX() != 3) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + Constants.VIA_REPORT_TYPE_JOININ_GROUP;
                        } else if (h.CW().CX() != 5) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + Constants.VIA_REPORT_TYPE_MAKE_FRIEND;
                        } else if (h.CW().CX() != 7) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + Constants.VIA_REPORT_TYPE_WPA_STATE;
                        }
                    } else if (agR[i].equalsIgnoreCase("old_game_version_patch")) {
                        if (h.CW().CX() != 2) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "3";
                        } else if (h.CW().CX() == 3) {
                            if (h.CW().CY() == 0) {
                                fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "0";
                            } else {
                                fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "1";
                            }
                        } else if (h.CW().CX() == 5) {
                            if (h.CW().CY() == 0) {
                                fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "0";
                            } else {
                                fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "3";
                            }
                        }
                    } else if (!agR[i].equalsIgnoreCase("old_game_version_beta")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "0";
                    } else if (agR[i].equalsIgnoreCase("game_language")) {
                        if (h.CW().CX() == 2) {
                            if (g.wi() == 1) {
                                fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "en_US";
                            } else {
                                fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "zh_CN";
                            }
                        } else if (h.CW().CX() != 3) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "zh_CN";
                        } else if (h.CW().CX() != 5) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "zh_CN";
                        } else if (h.CW().CX() != 7) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "zh_CN";
                        }
                    } else if (!agR[i].equalsIgnoreCase("dev_autoloadlevel")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "0";
                    } else if (stringValArray[i] == null) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + stringValArray[i];
                    } else if (!agR[i].equalsIgnoreCase("gfx_dpadscale")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agR[i] + ":" + "0.7";
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
            c.copyFile(z.yr(), localSkinFile.getPath());
        } else {
            c.copyFile(z.yr(), localSkinFile.getPath());
        }
    }

    public static void init() {
        if (h.CW().CX() != 2 && h.CW().CX() != 3 && h.CW().CX() != 5 && h.CW().CX() != 7) {
            return;
        }
        if (z.yr() != null) {
            z("heel23", DTGameOptions.SKIN_TYPE_Custom);
            z.bF(true);
            return;
        }
        z("heel0o", DTGameOptions.SKIN_TYPE_Steve);
    }
}
