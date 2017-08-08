package hlx.mcstorymode;

import android.support.v4.media.session.PlaybackStateCompat;
import com.MCWorld.utils.UtilsFile;
import com.MCWorld.utils.j;
import hlx.mcstorymode.storyfilecheck.a;
import hlx.mcstorymode.storyfilecheck.b;
import hlx.mcstorymode.storyfilecheck.c;
import hlx.mcstorymode.storyfilecheck.d;
import hlx.mcstorymode.storyfilecheck.f;
import hlx.mcstorymode.storyfilecheck.g;
import java.io.File;

/* compiled from: StoryOperator */
public class e {
    public static String TD() {
        return UtilsFile.get_mctool_path() + c.bUn + File.separator;
    }

    public static String TE() {
        return UtilsFile.get_mctool_path() + "mcstory" + File.separator;
    }

    public static String mY(int storyIndex) {
        String tmpPath = "";
        switch (storyIndex) {
            case 1:
                return UtilsFile.get_mctool_path() + c.bUB;
            case 2:
                return UtilsFile.get_mctool_path() + c.bUC;
            case 3:
                return UtilsFile.get_mctool_path() + c.bUD;
            case 4:
                return UtilsFile.get_mctool_path() + c.bUE;
            case 5:
                return UtilsFile.get_mctool_path() + c.bUF;
            case 6:
                return UtilsFile.get_mctool_path() + c.bUG;
            case 7:
                return UtilsFile.get_mctool_path() + c.bUH;
            default:
                return tmpPath;
        }
    }

    public static boolean mZ(int storyIndex) {
        switch (storyIndex) {
            case 1:
                return a.TF().hh(mY(storyIndex));
            case 2:
                return b.TG().hh(mY(storyIndex));
            case 3:
                return c.TH().hh(mY(storyIndex));
            case 4:
                return d.TI().hh(mY(storyIndex));
            case 5:
                return hlx.mcstorymode.storyfilecheck.e.TJ().hh(mY(storyIndex));
            case 6:
                return f.TK().hh(mY(storyIndex));
            case 7:
                return g.TL().hh(mY(storyIndex));
            default:
                return false;
        }
    }

    public static String na(int storyIndex) {
        String tmpStr = "";
        switch (storyIndex) {
            case 1:
                tmpStr = String.format(hlx.data.localstore.a.bKI, new Object[]{"5MB"});
                if (j.getSdcardAvailableSize(UtilsFile.getSdCardPath()) >= 5242880) {
                    return tmpStr;
                }
                return tmpStr + String.format(hlx.data.localstore.a.bKJ, new Object[]{"5MB"});
            case 2:
                tmpStr = String.format(hlx.data.localstore.a.bKI, new Object[]{"55KB"});
                if (j.getSdcardAvailableSize(UtilsFile.getSdCardPath()) >= 56320) {
                    return tmpStr;
                }
                return tmpStr + String.format(hlx.data.localstore.a.bKJ, new Object[]{"55KB"});
            case 3:
                tmpStr = String.format(hlx.data.localstore.a.bKI, new Object[]{"8MB"});
                if (j.getSdcardAvailableSize(UtilsFile.getSdCardPath()) >= 8388608) {
                    return tmpStr;
                }
                return tmpStr + String.format(hlx.data.localstore.a.bKJ, new Object[]{"8MB"});
            case 4:
                tmpStr = String.format(hlx.data.localstore.a.bKI, new Object[]{"2MB"});
                if (j.getSdcardAvailableSize(UtilsFile.getSdCardPath()) >= 2097152) {
                    return tmpStr;
                }
                return tmpStr + String.format(hlx.data.localstore.a.bKJ, new Object[]{"2MB"});
            case 5:
                tmpStr = String.format(hlx.data.localstore.a.bKI, new Object[]{"24.5MB"});
                if (j.getSdcardAvailableSize(UtilsFile.getSdCardPath()) >= 25690112) {
                    return tmpStr;
                }
                return tmpStr + String.format(hlx.data.localstore.a.bKJ, new Object[]{"24.5MB"});
            case 6:
                tmpStr = String.format(hlx.data.localstore.a.bKI, new Object[]{"1MB"});
                if (j.getSdcardAvailableSize(UtilsFile.getSdCardPath()) >= PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
                    return tmpStr;
                }
                return tmpStr + String.format(hlx.data.localstore.a.bKJ, new Object[]{"1MB"});
            case 7:
                tmpStr = String.format(hlx.data.localstore.a.bKI, new Object[]{"500KB"});
                if (j.getSdcardAvailableSize(UtilsFile.getSdCardPath()) >= 500) {
                    return tmpStr;
                }
                return tmpStr + String.format(hlx.data.localstore.a.bKJ, new Object[]{"500KB"});
            default:
                return tmpStr;
        }
    }

    public static String nb(int storyIndex) {
        String tmpStr = "";
        switch (storyIndex) {
            case 1:
                return c.bUP;
            case 2:
                return c.bUQ;
            case 3:
                return c.bUR;
            case 4:
                return c.bUS;
            case 5:
                return c.bUT;
            case 6:
                return c.bUU;
            case 7:
                return c.bUV;
            default:
                return tmpStr;
        }
    }

    public static String nc(int storyIndex) {
        String tmpStr = "";
        switch (storyIndex) {
            case 1:
                return c.bUW;
            case 2:
                return c.bUX;
            case 3:
                return c.bUY;
            case 4:
                return c.bUZ;
            case 5:
                return c.bVa;
            case 6:
                return c.bVb;
            case 7:
                return c.bVc;
            default:
                return tmpStr;
        }
    }

    public static String nd(int storyIndex) {
        String tmpStr = "";
        switch (storyIndex) {
            case 1:
                return c.bVd;
            case 2:
                return c.bVe;
            case 3:
                return c.bVf;
            case 4:
                return c.bVg;
            case 5:
                return c.bVh;
            case 6:
                return c.bVi;
            case 7:
                return c.bVj;
            default:
                return tmpStr;
        }
    }

    public static String ne(int storyIndex) {
        String tmpStr = "";
        switch (storyIndex) {
            case 1:
                return UtilsFile.get_mctool_path() + "mcstory";
            case 2:
                return UtilsFile.get_mctool_path() + c.bVl;
            case 3:
                return UtilsFile.get_mctool_path() + c.bVm;
            case 4:
                return UtilsFile.get_mctool_path() + c.bVn;
            case 5:
                return UtilsFile.get_mctool_path() + c.bVo;
            case 6:
                return UtilsFile.get_mctool_path() + c.bVp;
            case 7:
                return UtilsFile.get_mctool_path() + c.bVq;
            default:
                return tmpStr;
        }
    }

    public static boolean nf(int storyIndex) {
        switch (storyIndex) {
            case 1:
                return a.TF().hi(ne(storyIndex));
            case 2:
                return b.TG().hi(ne(storyIndex));
            case 3:
                return c.TH().hi(ne(storyIndex));
            case 4:
                return d.TI().hi(ne(storyIndex));
            case 5:
                return hlx.mcstorymode.storyfilecheck.e.TJ().hi(ne(storyIndex));
            case 6:
                return f.TK().hi(ne(storyIndex));
            case 7:
                return g.TL().hi(ne(storyIndex));
            default:
                return false;
        }
    }

    public static String ng(int storyIndex) {
        String tmpStr = ne(storyIndex);
        switch (storyIndex) {
            case 1:
                return tmpStr + File.separator + c.bVr;
            case 2:
                return tmpStr + File.separator + c.bVs;
            case 3:
                return tmpStr + File.separator + c.bVt;
            case 4:
                return tmpStr + File.separator + c.bVu;
            case 5:
                return tmpStr + File.separator + c.bVv;
            case 6:
                return tmpStr + File.separator + c.bVw;
            case 7:
                return tmpStr + File.separator + c.bVx;
            default:
                return tmpStr;
        }
    }
}
