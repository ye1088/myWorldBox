package hlx.gameoperator;

import android.content.Context;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.mojang.Mojang;
import com.MCWorld.t;
import com.MCWorld.utils.UtilsFile;
import com.MCWorld.utils.j;
import hlx.data.localstore.a;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/* compiled from: MapFileOperator */
public class c {
    private static final String TAG = "MapFileOperator";

    public static String gY(String mapFolderName) {
        String realleveldat = (UtilsFile.Kq() + mapFolderName) + File.separator + a.bKd;
        if (UtilsFile.isExist(realleveldat)) {
            try {
                BufferedReader bf = new BufferedReader(new FileReader(realleveldat));
                String fname = bf.readLine();
                bf.close();
                if (fname != null) {
                    mapFolderName = fname.trim();
                }
            } catch (IOException e) {
            }
        }
        return mapFolderName;
    }

    public static void aC(String levelName, String mapDirName) {
        BufferedWriter out;
        File file = new File(j.Kq() + mapDirName + File.separator + a.bKd);
        try {
            file.createNewFile();
            out = new BufferedWriter(new FileWriter(file));
            out.write(levelName);
            out.flush();
            out.close();
        } catch (IOException e) {
            HLog.info(TAG, "Complete map Level txt fail (IOException).", new Object[0]);
        } catch (Throwable th) {
            out.flush();
            out.close();
        }
    }

    public static boolean j(Context context, String oldMapFolderName, String newMapName) {
        if (newMapName.length() < 1 || oldMapFolderName.equals(newMapName)) {
            t.show_toast(context, "请输入存档的新名字！");
            return false;
        }
        try {
            HLog.verbose("TAG", "LSPrint" + Mojang.instance().getLevel().getLevelName(), new Object[0]);
            Mojang.instance().renameGame(newMapName);
            HLog.verbose("TAG", "LSPrint" + Mojang.instance().getLevel().getLevelName() + "\t" + newMapName, new Object[0]);
            if (UtilsFile.rename(UtilsFile.Kq() + Mojang.instance().getWorldItem().getFileName(), UtilsFile.Kq() + newMapName)) {
                aC(newMapName, newMapName);
                return true;
            }
            t.show_toast(context, "重命名失败，请检查是否已有该名字的地图");
            return false;
        } catch (Exception e) {
            t.show_toast(context, "重命名失败");
            return false;
        }
    }
}
