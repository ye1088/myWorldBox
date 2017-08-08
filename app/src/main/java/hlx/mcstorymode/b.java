package hlx.mcstorymode;

import com.MCWorld.data.storymode.a;
import com.MCWorld.data.storymode.d;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.utils.ah;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: MCStoryArchiveManage */
public class b {
    public static void a(String storyName, String gameChapterName, List<a> mStoryData, Map<String, String> keyMap) {
        if (mStoryData != null && mStoryData.size() != 0) {
            d _tempArchive = new d();
            _tempArchive.qL = gameChapterName;
            _tempArchive.data = mStoryData;
            _tempArchive.qM = keyMap;
            ah.KZ().a(_tempArchive, storyName);
        }
    }

    public static d hj(String storyName) {
        d _tmpArchive = ah.KZ().gd(storyName);
        if (!(_tmpArchive == null || UtilsFunction.empty(_tmpArchive.data))) {
            List<a> itemStructures = new ArrayList();
            for (int i = _tmpArchive.data.size() - 1; i >= 0; i--) {
                a item = (a) _tmpArchive.data.get(i);
                if (item.mId == null || item.qi == null) {
                    itemStructures.add(item);
                }
            }
            _tmpArchive.data.removeAll(itemStructures);
        }
        return _tmpArchive;
    }

    public static void Tr() {
        ah.KZ().ge("");
    }
}
