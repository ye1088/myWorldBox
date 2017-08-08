package com.MCWorld.module.news;

import com.MCWorld.module.a;

/* compiled from: CheckNewsLikeInfo */
public class b extends a {
    public int favorited;

    public boolean isFavorite() {
        return isSucc() && this.favorited == 1;
    }
}
