package com.MCWorld.mcgame.options;

import android.os.Build.VERSION;
import android.os.Environment;
import com.MCWorld.mcgame.g;
import com.MCWorld.mcgame.z;
import com.MCWorld.mcsdk.dtlib.c;
import com.tencent.connect.common.Constants;
import com.tencent.mm.sdk.platformtools.SpecilApiUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/* compiled from: OptionsV0154 */
public class e {
    private static final boolean DEBUG = false;
    public static final String OPTIONS_FILE_PATH = "games/com.mojang/minecraftpe/options.txt";
    private static final String TAG = "OptionsV0154";
    private static DTGameOptions agM = null;
    public static final String agN = "games/com.mojang/minecraftpe/custom.png";
    private static final int agS = 78;
    static String[] agT = new String[]{"mp_username", "game_difficulty_new", "game_thirdperson", "gfx_dpadscale", "mp_server_visible", "mp_xboxlive_visible", "game_flatworldlayers", "game_limitworldsize", "game_language", "game_skintypefull", "game_lastcustomskinnew", "game_recentskin1", "game_recentskin2", "game_recentskin3", "game_automationserverretrytime", "game_haseverloggedintoxbl", "game_haschosennottosignintoxbl", "game_hasBeenShownXbLiveSignInSurvey", "ctrl_sensitivity", "ctrl_invertmouse", "ctrl_islefthanded", "ctrl_usetouchscreen", "ctrl_usetouchjoypad", "ctrl_swapjumpandsneak", "feedback_vibration", "ctrl_autojump", "ctrl_keyboardlayout", "ctrl_gamePadMap", "gfx_renderdistance_new", "gfx_particleviewdistance", "gfx_viewbobbing", "gfx_fancygraphics", "gfx_transparentleaves", "gfx_fancyskies", "gfx_hidegui", "gfx_field_of_view", "gfx_msaa", "gfx_texel_aa", "gfx_gamma", "gfx_fullscreen", "gfx_guiscale", "audio_sound", "audio_music", "vr_sensitivity", "vr_gamma", "vr_stutter_turn", "vr_stutter_turn_sound", "vr_hmd_displacement", "vr_renderdistance", "vr_autojump", "vr_head_steering", "vr_stutter_turn_constant_time", "vr_stereo", "vr_hud_at_top", "vr_use_normal_hit", "vr_use_red_flash", "vr_rstick_pitch_assist", "vr_rstick_gazeadjust", "vr_gaze_pitch_boost", "vr_hud_drift", "vr_linear_jump", "vr_linear_motion", "vr_sticky_mining", "vr_use_comfort_controls", "vr_show_comfort_select_screen", "vr_livingroom_hint_time", "dev_autoloadlevel", "dev_showchunkmap", "dev_disablefilesystem", "dev_enable_profiler", "dev_uselocalserver", "dev_connection_quality", "old_game_version_major", "old_game_version_minor", "old_game_version_patch", "old_game_version_beta", "realms_show_friend_invites_only", "allow_cellular_data"};

    private static void u(String fileName, String write_str) throws IOException {
        try {
            FileOutputStream fout = new FileOutputStream(fileName);
            fout.write(write_str.getBytes());
            fout.flush();
            fout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void a(String userName, String userSkin, int input_dev_autoloadlevel, boolean updateAutoLoadLevel) {
        int i;
        FileNotFoundException localFileNotFoundException;
        IOException localIOException;
        Throwable th;
        File tmpLocalFile;
        String fileContext;
        String mcSetName;
        BufferedReader localBufferedReader = null;
        String[] stringValArray = new String[78];
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
                                while (i < 78) {
                                    if (agT[i] != null && str1.equalsIgnoreCase(agT[i])) {
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
                while (i < 78) {
                    if (agT[i] != null) {
                        if (!agT[i].equalsIgnoreCase("mp_username")) {
                            mcSetName = g.wj();
                            if (mcSetName == null) {
                                if (!stringValArray[i].equalsIgnoreCase("Steve")) {
                                }
                                fileContext = agT[i] + ":" + mcSetName;
                            } else {
                                fileContext = agT[i] + ":" + stringValArray[i];
                            }
                        } else if (!agT[i].equalsIgnoreCase("game_difficulty_new")) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + "1";
                        } else if (!agT[i].equalsIgnoreCase("game_skintypefull")) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + userSkin;
                        } else if (!agT[i].equalsIgnoreCase("game_lastcustomskinnew")) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + userSkin;
                        } else if (!agT[i].equalsIgnoreCase("old_game_version_major")) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + "0";
                        } else if (!agT[i].equalsIgnoreCase("old_game_version_minor")) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + Constants.VIA_REPORT_TYPE_WPA_STATE;
                        } else if (!agT[i].equalsIgnoreCase("old_game_version_patch")) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + "4";
                        } else if (!agT[i].equalsIgnoreCase("old_game_version_beta")) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + "0";
                        } else if (!agT[i].equalsIgnoreCase("game_language")) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + "zh_CN";
                        } else if (agT[i].equalsIgnoreCase("dev_autoloadlevel")) {
                            if (stringValArray[i] == null) {
                                fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + stringValArray[i];
                            } else if (!agT[i].equalsIgnoreCase("gfx_dpadscale")) {
                                fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + "0.7";
                            }
                        } else if (updateAutoLoadLevel) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + stringValArray[i];
                        } else {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + String.valueOf(input_dev_autoloadlevel);
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
            while (i < 78) {
                if (agT[i] != null) {
                    if (!agT[i].equalsIgnoreCase("mp_username")) {
                        mcSetName = g.wj();
                        if (mcSetName == null) {
                            fileContext = agT[i] + ":" + stringValArray[i];
                        } else {
                            if (stringValArray[i].equalsIgnoreCase("Steve")) {
                            }
                            fileContext = agT[i] + ":" + mcSetName;
                        }
                    } else if (!agT[i].equalsIgnoreCase("game_difficulty_new")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + "1";
                    } else if (!agT[i].equalsIgnoreCase("game_skintypefull")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + userSkin;
                    } else if (!agT[i].equalsIgnoreCase("game_lastcustomskinnew")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + userSkin;
                    } else if (!agT[i].equalsIgnoreCase("old_game_version_major")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + "0";
                    } else if (!agT[i].equalsIgnoreCase("old_game_version_minor")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + Constants.VIA_REPORT_TYPE_WPA_STATE;
                    } else if (!agT[i].equalsIgnoreCase("old_game_version_patch")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + "4";
                    } else if (!agT[i].equalsIgnoreCase("old_game_version_beta")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + "0";
                    } else if (!agT[i].equalsIgnoreCase("game_language")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + "zh_CN";
                    } else if (agT[i].equalsIgnoreCase("dev_autoloadlevel")) {
                        if (updateAutoLoadLevel) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + String.valueOf(input_dev_autoloadlevel);
                        } else {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + stringValArray[i];
                        }
                    } else if (stringValArray[i] == null) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + stringValArray[i];
                    } else if (!agT[i].equalsIgnoreCase("gfx_dpadscale")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + "0.7";
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
            for (i = 0; i < 78; i++) {
                if (agT[i] != null) {
                    if (!agT[i].equalsIgnoreCase("mp_username")) {
                        mcSetName = g.wj();
                        if (mcSetName == null) {
                            fileContext = agT[i] + ":" + stringValArray[i];
                        } else if (stringValArray[i].equalsIgnoreCase("Steve") || VERSION.SDK_INT < 11) {
                            fileContext = agT[i] + ":" + mcSetName;
                        } else {
                            fileContext = agT[i] + ":" + stringValArray[i];
                        }
                    } else if (!agT[i].equalsIgnoreCase("game_difficulty_new")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + "1";
                    } else if (!agT[i].equalsIgnoreCase("game_skintypefull")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + userSkin;
                    } else if (!agT[i].equalsIgnoreCase("game_lastcustomskinnew")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + userSkin;
                    } else if (!agT[i].equalsIgnoreCase("old_game_version_major")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + "0";
                    } else if (!agT[i].equalsIgnoreCase("old_game_version_minor")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + Constants.VIA_REPORT_TYPE_WPA_STATE;
                    } else if (!agT[i].equalsIgnoreCase("old_game_version_patch")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + "4";
                    } else if (!agT[i].equalsIgnoreCase("old_game_version_beta")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + "0";
                    } else if (!agT[i].equalsIgnoreCase("game_language")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + "zh_CN";
                    } else if (agT[i].equalsIgnoreCase("dev_autoloadlevel")) {
                        if (updateAutoLoadLevel) {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + String.valueOf(input_dev_autoloadlevel);
                        } else {
                            fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + stringValArray[i];
                        }
                    } else if (stringValArray[i] == null) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + stringValArray[i];
                    } else if (!agT[i].equalsIgnoreCase("gfx_dpadscale")) {
                        fileContext = fileContext + SpecilApiUtil.LINE_SEP + agT[i] + ":" + "0.7";
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
        if (z.yr() != null) {
            a("heel23", DTGameOptions.SKIN_TYPE_Custom, 0, true);
            zb();
            z.bF(true);
            return;
        }
        a("heel0o", DTGameOptions.SKIN_TYPE_Steve, 0, true);
    }

    public static void bJ(boolean bCreateRoomMode) {
        if (bCreateRoomMode) {
            a("heel0o", DTGameOptions.SKIN_TYPE_Steve, 1, true);
        } else {
            a("heel0o", DTGameOptions.SKIN_TYPE_Steve, 0, true);
        }
    }
}
