package com.MCWorld.widget;

import com.MCWorld.HTApplication;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class Constants {
    public static final int brJ = 0;
    public static final int brK = 1;
    public static final String brL = "id";
    public static final String brM = "title";
    public static final String brN = "EXTRA_HOME";
    public static final String brO = "PUSH_MODEL";
    public static final String brP = "FROM_NOTIFICATION";
    public static final String brQ = "com.mojang.minecraftpe";
    public static final String brR = "http://mc.huluxia.com/tohulusi.html";
    public static final int brS = 740110001;
    public static final String brT = "app.apk";
    public static final String brU = "info.txt";
    public static final String brV = "Meizu";
    public static final String brW = "IamSteveNetSteveamI";
    public static final String brX = "default";
    public static final long brY = 1001;
    public static final String brZ = "com.vectorunit.green";
    public static String bsA = null;
    public static final int bsB = 90000;
    public static final int bsC = 90001;
    public static final int bsD = 90002;
    public static final int bsE = 90003;
    public static final int bsF = 90004;
    public static final int bsG = 90005;
    public static final int bsH = 2300;
    public static final int bsI = 2304;
    public static final int bsJ = 2317;
    public static final int bsK = 2306;
    public static final String bsL = "2300";
    public static final String bsM = "2304";
    public static final String bsN = "2317";
    public static final String bsO = "2306";
    public static final String bsP = "http://tieba.baidu.com/p/3219122263";
    public static final long bsQ = 55;
    public static final long bsR = 68;
    public static final long bsS = 69;
    public static final long bsT = (HTApplication.DEBUG ? 70 : 74);
    public static final int bsU = 2;
    public static final int bsV = 203;
    public static final int bsW = 10;
    public static final int bsX = 11;
    public static final int bsY = 20;
    public static final int bsZ = 21;
    public static final String bsa = "Save1.zip";
    public static final String bsb = "Save1.ht";
    public static final String bsc = "Save2.zip";
    public static final String bsd = "Save2.ht";
    public static final String bse = "Save3.zip";
    public static final String bsf = "Save3.ht";
    public static final String bsg = "Save4.zip";
    public static final String bsh = "Save4.ht";
    public static final String bsi = "SaveAuto.zip";
    public static final String bsj = "SaveAuto.ht";
    public static final String bsk = "bak";
    public static String bsl = null;
    public static String bsm = null;
    public static String bsn = null;
    public static String bso = null;
    public static String bsp = null;
    public static String bsq = null;
    public static String bsr = null;
    public static String bss = null;
    public static String bst = null;
    public static final String bsu = "game_pack0105";
    public static final String bsv = "game_pack0110";
    public static final String bsw = "game_pack0121";
    public static final String bsx = "game_pack0130";
    public static final String bsy = "game_pack0131";
    public static final String bsz = "http://mc.huluxia.com/tool/";
    public static final int btA = 5;
    public static final int bta = 31;
    public static final String btb = "tool_xiaomi";
    public static final String btc = "FloatChkGameMode";
    public static final String btd = "FloatChkGameTime";
    public static final String bte = "FloatChkRunSpeed";
    public static final String btf = "FloatChkShowBlood";
    public static final String btg = "FloatChkDeadLost";
    public static final String bth = "FloatChkSmallMap";
    public static final String bti = "FloatFullBlood";
    public static final String btj = "FloatChkFly";
    public static final int btk = 5;
    public static final int btl = 6;
    public static final int btm = 7;
    public static final int btn = 1;
    public static final int bto = 2;
    public static final int btp = 3;
    public static final int btq = 4;
    public static final long btr = 6801;
    public static final long bts = 6803;
    public static final long btt = 6804;
    public static final long btu = 6805;
    public static final int btv = 0;
    public static final int btw = 1;
    public static final int btx = 2;
    public static final int bty = 3;
    public static final int btz = 4;

    public enum AppType {
        FLOOR(1),
        TOOL(2),
        MCTOOL(3);
        
        private int m_val;

        private AppType(int val) {
            this.m_val = val;
        }

        public int Value() {
            return this.m_val;
        }
    }

    public enum DownFileType {
        defaultType(0),
        Js(1),
        Wood(2),
        Skin(3);
        
        private int m_val;

        private DownFileType(int val) {
            this.m_val = val;
        }

        public int Value() {
            return this.m_val;
        }
    }

    public enum FeedBackType {
        BUG(0),
        SUGGESTION(1),
        GAMEBUG(2),
        MCBUG(3);
        
        private int m_val;

        private FeedBackType(int val) {
            this.m_val = val;
        }

        public int Value() {
            return this.m_val;
        }
    }

    public enum FileType {
        UNKNOWN(0),
        ROOTDIR(1),
        PARENTDIR(2),
        SUBDIR(3),
        ZIP(4),
        JS(5),
        PNG(6);
        
        private int m_val;

        private FileType(int val) {
            this.m_val = val;
        }

        public int Value() {
            return this.m_val;
        }
    }

    public enum MapState {
        ORI(0),
        DOWNLOADING(1),
        DOWNLOADOK(2),
        UNZIPPING(3),
        UNZIPOK(4);
        
        private int m_val;

        private MapState(int val) {
            this.m_val = val;
        }

        public int Value() {
            return this.m_val;
        }
    }

    public enum MiVer {
        nomi,
        miv5,
        miv6
    }

    public enum Model {
        BAIDU(1),
        XIAOMI(2),
        HUAWEI(3);
        
        private int m_val;

        private Model(int val) {
            this.m_val = val;
        }

        public int Value() {
            return this.m_val;
        }
    }

    public enum PushMsgType {
        INVALID(-1),
        COUNTS(0),
        KICK(1),
        MODE(2),
        GAME(3),
        TOPIC(4),
        UPDATE(5),
        DEPRECIATE(6),
        MCRES(7);
        
        private int m_val;

        private PushMsgType(int val) {
            this.m_val = val;
        }

        public int Value() {
            return this.m_val;
        }
    }

    public enum PushWay {
        BAIDU(1),
        XIAOMI(2),
        HUAWEI(3),
        TENCENT(4);
        
        private int m_val;

        private PushWay(int val) {
            this.m_val = val;
        }

        public int Value() {
            return this.m_val;
        }
    }

    public enum ReStartSoftFlag {
        MC_RESTART_NORMAL(0),
        MC_RESTART_V0105(1),
        MC_RESTART_V0111(2),
        MC_RESTART_V0121(3),
        MC_RESTART_BLACK(4),
        MC_RESTART_V0130(5),
        MC_RESTART_V0131(6),
        MC_RESTART_V0140(7),
        MC_RESTART_V0150(8);
        
        private int m_val;

        private ReStartSoftFlag(int val) {
            this.m_val = val;
        }

        public int Value() {
            return this.m_val;
        }
    }

    public enum SdType {
        Internal,
        External
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface a {
    }

    public static void NR() {
        bsl = HTApplication.fn + "/game/2015/06/08/game_pack0105.zip";
        bsm = HTApplication.fn + "/game/2015/07/30/game_pack0110.zip";
        bsn = HTApplication.fn + "/file/mc/resource/2016/01/29/game_pack0121.zip";
        bso = HTApplication.fn + "/file/mc/resource/2015/11/20/game_pack0130.zip";
        bsp = HTApplication.fn + "/file/mc/resource/2015/12/17a/game_pack0131.zip";
        bsq = HTApplication.fn + "/file/mc/resource/2016/01/29a/game_pack0121.zip";
        bsr = HTApplication.fn + "/file/mc/resource/2015/11/10a/game_pack0130.zip";
        bss = HTApplication.fn + "/file/mc/resource/2015/11/10a/game_pack0130.zip";
        bst = HTApplication.fn + "/file/mc/resource/2015/11/10a/game_pack0130.zip";
        bsA = HTApplication.fn + "/game/2015/04/21/app_icon.png";
    }
}
