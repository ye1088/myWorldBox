package com.huluxia.module.news;

import com.huluxia.module.a;

/* compiled from: CheckNewsLikeInfo */
public class b extends a {
    public int favorited;

    public boolean isFavorite() {
        return isSucc() && this.favorited == 1;
    }
}
