package com.MCWorld.widget.banner;

/* compiled from: BannerInfo */
public abstract class a {
    public String url;

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        a that = (a) o;
        if (this.url != null) {
            return this.url.equals(that.url);
        }
        if (that.url != null) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.url != null ? this.url.hashCode() : 0;
    }
}
