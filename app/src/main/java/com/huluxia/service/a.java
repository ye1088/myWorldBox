package com.huluxia.service;

import com.huluxia.data.map.MapItem;
import com.huluxia.utils.UtilsDownloadFile;
import com.huluxia.utils.j;
import com.huluxia.widget.Constants.MapState;
import com.huluxia.widget.h;

/* compiled from: DownJsTask */
public class a {
    public String aCW = "";
    public String aCX;
    public a aCY;
    private com.huluxia.utils.UtilsDownloadFile.a aCZ = new com.huluxia.utils.UtilsDownloadFile.a(this) {
        final /* synthetic */ a aDa;

        {
            this.aDa = this$0;
        }

        public void au(int cur, int max) {
            this.aDa.aCY.cur = cur;
            this.aDa.aCY.max = max;
        }

        public void h(int state, String path) {
            if (state == 3) {
                this.aDa.aCY.aDb = MapState.DOWNLOADOK;
                this.aDa.Eq();
            }
        }
    };
    public String mPath;
    public String mTitle;
    public String mUrl;
    public long mapId = -1;
    public String pN;
    public long postId = -1;
    private String prefix = "js";
    public String ver;

    /* compiled from: DownJsTask */
    class a {
        final /* synthetic */ a aDa;
        MapState aDb = MapState.ORI;
        int cur = 0;
        int max = 100;

        a(a this$0) {
            this.aDa = this$0;
        }
    }

    public int En() {
        return this.aCY.cur;
    }

    public int getMax() {
        return this.aCY.max;
    }

    public MapState Eo() {
        return this.aCY.aDb;
    }

    public a(MapItem paraItem) {
        this.aCX = this.prefix + paraItem.id;
        this.mPath = j.Kt() + paraItem.name + ".zip";
        this.mUrl = paraItem.url;
        this.mTitle = paraItem.name;
        this.pN = paraItem.creatTime;
        this.ver = paraItem.version;
        this.mapId = paraItem.id;
        this.postId = paraItem.postid;
        this.aCW = paraItem.photo;
        this.aCY = new a(this);
    }

    public void Ep() {
        new UtilsDownloadFile(null, this.mPath, false, this.aCZ).execute(new String[]{this.mUrl});
        this.aCY.aDb = MapState.DOWNLOADING;
    }

    public void Eq() {
        this.aCY.aDb = MapState.UNZIPPING;
        h.NV().t(this.aCX, this.mPath, j.cT(true));
    }
}
