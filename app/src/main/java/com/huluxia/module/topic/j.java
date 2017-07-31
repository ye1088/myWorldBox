package com.huluxia.module.topic;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.huluxia.data.PageList;
import com.huluxia.data.UserBaseInfo;
import com.huluxia.data.topic.CommentItem;
import com.huluxia.data.topic.TopicItem;
import com.huluxia.module.a;
import java.util.List;

/* compiled from: TopicDetailInfo */
public class j extends a implements Parcelable {
    public static final Creator<j> CREATOR = new 1();
    public int categoryID;
    public List<CommentItem> comments;
    public int currPageNo;
    public int isTmp;
    public int pageSize;
    public TopicItem post;
    public List<UserBaseInfo> remindUsers;
    public int scoreUserCount;
    public com.huluxia.data.topic.a studioInfo;
    public int totalPage;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.post, flags);
        dest.writeTypedList(this.comments);
        dest.writeInt(this.categoryID);
        dest.writeInt(this.scoreUserCount);
        dest.writeInt(this.currPageNo);
        dest.writeInt(this.pageSize);
        dest.writeInt(this.totalPage);
        dest.writeInt(this.isTmp);
        dest.writeParcelable(this.studioInfo, flags);
        dest.writeTypedList(this.remindUsers);
    }

    protected j(Parcel in) {
        super(in);
        this.post = (TopicItem) in.readParcelable(TopicItem.class.getClassLoader());
        this.comments = in.createTypedArrayList(CommentItem.CREATOR);
        this.categoryID = in.readInt();
        this.scoreUserCount = in.readInt();
        this.currPageNo = in.readInt();
        this.pageSize = in.readInt();
        this.totalPage = in.readInt();
        this.isTmp = in.readInt();
        this.studioInfo = (com.huluxia.data.topic.a) in.readParcelable(com.huluxia.data.topic.a.class.getClassLoader());
        this.remindUsers = in.createTypedArrayList(UserBaseInfo.CREATOR);
    }

    public PageList getPageList() {
        PageList pageList = new PageList();
        if (this.post != null) {
            pageList.add(this.post);
        }
        if (this.comments != null) {
            for (CommentItem comment : this.comments) {
                pageList.add(comment);
            }
        }
        pageList.setCurrPageNo(this.currPageNo);
        pageList.setPageSize(this.pageSize);
        pageList.setTotalPage(this.totalPage);
        pageList.setIsTmp(this.isTmp);
        pageList.setStudioInfo(this.studioInfo);
        pageList.setRemindUsers(this.remindUsers);
        return pageList;
    }
}
