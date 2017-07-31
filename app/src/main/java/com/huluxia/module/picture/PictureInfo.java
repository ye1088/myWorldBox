package com.huluxia.module.picture;

import com.huluxia.framework.base.utils.UtilsFunction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PictureInfo implements Serializable {
    private static PictureInfo sInstance;
    public ArrayList<b> allPictures = new ArrayList();
    private List<a> buckets;
    public HashMap<Long, a> pictureBucket = new HashMap();

    private PictureInfo() {
    }

    public static synchronized PictureInfo getInstance() {
        PictureInfo pictureInfo;
        synchronized (PictureInfo.class) {
            if (sInstance == null) {
                sInstance = new PictureInfo();
            }
            pictureInfo = sInstance;
        }
        return pictureInfo;
    }

    public synchronized boolean isEmpty() {
        return UtilsFunction.empty(this.allPictures);
    }

    public synchronized int getSize() {
        return this.allPictures.size();
    }

    public synchronized a getBucket(long bucketId) {
        return (a) this.pictureBucket.get(Long.valueOf(bucketId));
    }

    public synchronized b getPicture(int position) {
        b bVar;
        if (position >= 0) {
            if (position < this.allPictures.size()) {
                bVar = (b) this.allPictures.get(position);
            }
        }
        bVar = null;
        return bVar;
    }

    public synchronized int getBucketCount() {
        return this.pictureBucket.size();
    }

    public synchronized a getBucketAt(int position) {
        a aVar;
        if (this.buckets == null) {
            this.buckets = new ArrayList();
            for (a bucket : this.pictureBucket.values()) {
                this.buckets.add(bucket);
            }
        }
        if (position < getBucketCount()) {
            aVar = (a) this.buckets.get(position);
        } else {
            aVar = null;
        }
        return aVar;
    }

    public void clear() {
        this.allPictures.clear();
        this.pictureBucket.clear();
        sInstance = null;
    }
}
