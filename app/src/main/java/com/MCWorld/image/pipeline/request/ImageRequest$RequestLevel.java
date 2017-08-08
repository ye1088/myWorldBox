package com.MCWorld.image.pipeline.request;

public enum ImageRequest$RequestLevel {
    FULL_FETCH(1),
    DISK_CACHE(2),
    ENCODED_MEMORY_CACHE(3),
    BITMAP_MEMORY_CACHE(4);
    
    private int mValue;

    private ImageRequest$RequestLevel(int value) {
        this.mValue = value;
    }

    public int getValue() {
        return this.mValue;
    }

    public static ImageRequest$RequestLevel getMax(ImageRequest$RequestLevel requestLevel1, ImageRequest$RequestLevel requestLevel2) {
        return requestLevel1.getValue() > requestLevel2.getValue() ? requestLevel1 : requestLevel2;
    }
}
