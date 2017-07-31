package hlx.home.fragment;

import com.huluxia.framework.base.http.module.ProgressInfo;
import com.huluxia.framework.base.notification.CallbackHandler;
import com.huluxia.framework.base.notification.EventNotifyCenter.MessageHandler;

class MapJsWoodSkinFragment$3 extends CallbackHandler {
    final /* synthetic */ MapJsWoodSkinFragment bQy;

    MapJsWoodSkinFragment$3(MapJsWoodSkinFragment this$0) {
        this.bQy = this$0;
    }

    @MessageHandler(message = 258)
    public void onProgress(String url, String path, ProgressInfo progressInfo) {
        MapJsWoodSkinFragment.m(this.bQy);
        if (MapJsWoodSkinFragment.l(this.bQy) != null && MapJsWoodSkinFragment.a(this.bQy) == 7) {
            MapJsWoodSkinFragment.l(this.bQy).a(url, progressInfo);
        }
    }

    @MessageHandler(message = 256)
    public void onDownloadSucc(String url, String path) {
        switch (MapJsWoodSkinFragment.a(this.bQy)) {
            case 1:
            case 2:
            case 3:
            case 4:
                if (MapJsWoodSkinFragment.i(this.bQy) == null || true != MapJsWoodSkinFragment.n(this.bQy) || true != MapJsWoodSkinFragment.o(this.bQy)) {
                    if (MapJsWoodSkinFragment.i(this.bQy) != null) {
                        MapJsWoodSkinFragment.i(this.bQy).notifyDataSetChanged();
                        break;
                    }
                }
                MapJsWoodSkinFragment.i(this.bQy).eu(url);
                break;
                break;
        }
        if (MapJsWoodSkinFragment.l(this.bQy) != null && MapJsWoodSkinFragment.a(this.bQy) == 7) {
            MapJsWoodSkinFragment.l(this.bQy).eu(url);
        }
    }

    @MessageHandler(message = 259)
    public void onDownloadCancel(String url, String path) {
        MapJsWoodSkinFragment.m(this.bQy);
        if (MapJsWoodSkinFragment.l(this.bQy) != null && MapJsWoodSkinFragment.a(this.bQy) == 7) {
            MapJsWoodSkinFragment.l(this.bQy).ew(url);
        }
    }

    @MessageHandler(message = 257)
    public void onDownloadError(String url, String path, Object context) {
        MapJsWoodSkinFragment.m(this.bQy);
        if (MapJsWoodSkinFragment.l(this.bQy) != null && MapJsWoodSkinFragment.a(this.bQy) == 7) {
            MapJsWoodSkinFragment.l(this.bQy).ex(url);
        }
    }
}
