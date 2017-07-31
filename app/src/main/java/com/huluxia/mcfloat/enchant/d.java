package com.huluxia.mcfloat.enchant;

/* compiled from: EnchantLevel */
public class d {
    public long id;
    public String title;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public d(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public String toString() {
        return this.title;
    }
}
