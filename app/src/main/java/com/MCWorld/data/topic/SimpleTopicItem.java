package com.MCWorld.data.topic;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Date;

public class SimpleTopicItem implements Parcelable, Serializable {
    public static final Creator<SimpleTopicItem> CREATOR = new 1();
    public long activeTime;
    public String categoryName;
    public long postID;
    public int state;
    public String title;

    public SimpleTopicItem(long postID, String title, String categoryName, long activeTime, int state) {
        this.postID = postID;
        this.title = title;
        this.categoryName = categoryName;
        this.activeTime = activeTime;
        this.state = state;
    }

    public SimpleTopicItem(Parcel source) {
        this.postID = source.readLong();
        this.title = source.readString();
        this.categoryName = source.readString();
        this.activeTime = source.readLong();
        this.state = source.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.postID);
        dest.writeString(this.title);
        dest.writeString(this.categoryName);
        dest.writeLong(this.activeTime);
        dest.writeInt(this.state);
    }

    public static SimpleTopicItem convertToSimpleTopic(TopicItem item, String categoryName) {
        long time = new Date().getTime();
        if (item.getCategory() != null) {
            long catId = item.getCategory().categoryID;
        }
        return new SimpleTopicItem(item.getPostID(), item.getTitle(), categoryName, time, item.getState());
    }

    public boolean isDel() {
        return this.state == 2;
    }
}
