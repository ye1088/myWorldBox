package com.huluxia.data.topic;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.huluxia.module.a;
import java.util.ArrayList;
import java.util.List;

/* compiled from: TopicInfo */
public class e extends a {
    public static final Creator<e> CREATOR = new 1();
    public long categoryID;
    public List<CommentItem> comments = new ArrayList();
    public int currPageNo;
    public int pageSize;
    public TopicItem post;
    public long scoreUserCount;
    public int totalPage;

    public e(Parcel source) {
        super(source);
        this.post = (TopicItem) source.readParcelable(TopicItem.class.getClassLoader());
        source.readTypedList(this.comments, CommentItem.CREATOR);
        this.categoryID = source.readLong();
        this.scoreUserCount = source.readLong();
        this.currPageNo = source.readInt();
        this.pageSize = source.readInt();
        this.totalPage = source.readInt();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.post, flags);
        dest.writeTypedList(this.comments);
        dest.writeList(this.comments);
        dest.writeLong(this.categoryID);
        dest.writeLong(this.scoreUserCount);
        dest.writeInt(this.currPageNo);
        dest.writeInt(this.pageSize);
        dest.writeInt(this.totalPage);
    }
}
