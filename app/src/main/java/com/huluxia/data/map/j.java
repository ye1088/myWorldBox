package com.huluxia.data.map;

import com.huluxia.module.picture.b;
import java.util.ArrayList;
import java.util.List;

/* compiled from: PublishResDraft */
public class j {
    public String author;
    public String detail;
    public List<b> images = new ArrayList();
    public long pW;
    public String source;

    public j(long userId, String author, String source, String detail, List<b> images) {
        this.pW = userId;
        this.author = author;
        this.source = source;
        this.detail = detail;
        this.images = images;
    }
}
