package com.huluxia.service;

import com.huluxia.data.map.MapItem;
import com.huluxia.utils.UtilsDownloadFile;
import com.huluxia.utils.j;
import com.huluxia.widget.Constants.MapState;
import com.huluxia.widget.h;

/* compiled from: DownMapTask */
public class c {
    public String aCW;
    private com.huluxia.utils.UtilsDownloadFile.a aCZ = new com.huluxia.utils.UtilsDownloadFile.a(this) {
        final /* synthetic */ c aDh;

        {
            this.aDh = this$0;
        }

        public void au(int cur, int max) {
            this.aDh.aDg.cur = cur;
            this.aDh.aDg.max = max;
        }

        public void h(int state, String path) {
            if (state == 3) {
                this.aDh.aDg.aDb = MapState.DOWNLOADOK;
                this.aDh.Eq();
            }
        }
    };
    public a aDg;
    public String id;
    public String mPath;
    public String mTitle;
    public String mUrl;
    public long mapId;
    public String pN;
    public long postId;
    private String prefix = "map";

    /* compiled from: DownMapTask */
    class a {
        MapState aDb = MapState.ORI;
        final /* synthetic */ c aDh;
        int cur = 0;
        int max = 100;

        a(c this$0) {
            this.aDh = this$0;
        }
    }

    public int En() {
        return this.aDg.cur;
    }

    public int getMax() {
        return this.aDg.max;
    }

    public MapState Eo() {
        return this.aDg.aDb;
    }

    public c(MapItem paraItem) {
        this.id = this.prefix + paraItem.id;
        this.mPath = j.cV(true) + paraItem.name + ".zip";
        this.mTitle = paraItem.name;
        this.aCW = paraItem.photo;
        this.mUrl = paraItem.url;
        this.mapId = paraItem.id;
        this.postId = paraItem.postid;
        this.pN = String.valueOf(paraItem.creatTime);
        this.aDg = new a(this);
    }

    public void Ep() {
        new UtilsDownloadFile(null, this.mPath, false, this.aCZ).execute(new String[]{this.mUrl});
        this.aDg.aDb = MapState.DOWNLOADING;
    }

    public void Eq() {
        this.aDg.aDb = MapState.UNZIPPING;
        h.NV().t(String.valueOf(this.id), this.mPath, j.Kq());
    }
}
