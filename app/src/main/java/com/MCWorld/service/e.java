package com.MCWorld.service;

import com.MCWorld.data.map.MapItem;
import com.MCWorld.utils.UtilsDownloadFile;
import com.MCWorld.utils.j;
import com.MCWorld.widget.Constants.MapState;
import com.MCWorld.widget.h;

/* compiled from: DownSkinTask */
public class e {
    public String aCW;
    public String aCX;
    private com.MCWorld.utils.UtilsDownloadFile.a aCZ = new com.MCWorld.utils.UtilsDownloadFile.a(this) {
        final /* synthetic */ e aDl;

        {
            this.aDl = this$0;
        }

        public void au(int cur, int max) {
            this.aDl.aDk.cur = cur;
            this.aDl.aDk.max = max;
        }

        public void h(int state, String path) {
            if (state == 3) {
                this.aDl.aDk.aDb = MapState.DOWNLOADOK;
                this.aDl.Eq();
            }
        }
    };
    public a aDk;
    public String mPath;
    public String mTitle;
    public String mUrl;
    public long mapId;
    public String pN;
    public long postId;
    private String prefix = "skin";
    public String ver;

    /* compiled from: DownSkinTask */
    class a {
        MapState aDb = MapState.ORI;
        final /* synthetic */ e aDl;
        int cur = 0;
        int max = 100;

        a(e this$0) {
            this.aDl = this$0;
        }
    }

    public int En() {
        return this.aDk.cur;
    }

    public int getMax() {
        return this.aDk.max;
    }

    public MapState Eo() {
        return this.aDk.aDb;
    }

    public e(MapItem paraItem) {
        this.aCX = this.prefix + paraItem.id;
        this.mPath = j.Ku() + paraItem.name + ".zip";
        this.mUrl = paraItem.url;
        this.mTitle = paraItem.name;
        this.pN = paraItem.creatTime;
        this.aCW = paraItem.photo;
        this.mapId = paraItem.id;
        this.postId = paraItem.postid;
        this.ver = paraItem.version;
        this.aDk = new a(this);
    }

    public void Ep() {
        new UtilsDownloadFile(null, this.mPath, false, this.aCZ).execute(new String[]{this.mUrl});
        this.aDk.aDb = MapState.DOWNLOADING;
    }

    public void Eq() {
        this.aDk.aDb = MapState.UNZIPPING;
        h.NV().t(this.aCX, this.mPath, j.cU(true));
    }
}
