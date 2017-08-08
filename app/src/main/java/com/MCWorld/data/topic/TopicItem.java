package com.MCWorld.data.topic;

import android.os.Parcel;
import android.os.Parcelable;

import com.MCWorld.data.UserBaseInfo;
import com.MCWorld.data.category.TopicCategory;
import com.MCWorld.data.profile.a;
import com.MCWorld.framework.base.http.toolbox.download.DownloadRecord;
import com.MCWorld.r;
import com.MCWorld.ui.bbs.TopicDetailActivity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TopicItem implements Parcelable, Serializable {
    public static final Creator<TopicItem> CREATOR = new Creator<TopicItem>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return am(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return aW(i);
        }

        public TopicItem am(Parcel source) {
            return new TopicItem(source);
        }

        public TopicItem[] aW(int size) {
            return new TopicItem[size];
        }
    };
    private static final long serialVersionUID = 2491851872988938823L;
    public long activeTime;
    public String address;
    public TopicCategory category = null;
    public String categoryName;
    public long commentCount;
    public long createTime;
    public String detail;
    public ExtJson ext;
    public long hit;
    public List<String> images = new ArrayList();
    public int isAuthention;
    public int isGood;
    public long isPraised;
    public int line;
    public int notice;
    public long postID;
    public long praise;
    public int score;
    public String scoreTxt;
    public long scoreUserCount;
    public long scorecount;
    public List<ScoreItem> scorelist = new ArrayList();
    public int state;
    public int status;
    public long tagid;
    public String title;
    public UserBaseInfo user = null;
    public String voice;
    public int voiceTime;
    public int weight;

    public TopicItem(JSONObject obj) throws JSONException {
        if (obj != null) {
            int i;
            this.postID = obj.optLong(TopicDetailActivity.aOM);
            this.tagid = obj.optLong("tagid");
            this.title = obj.optString("title");
            this.detail = obj.optString(r.gQ);
            this.commentCount = obj.optLong("commentCount");
            this.createTime = obj.optLong("createTime");
            this.activeTime = obj.optLong("activeTime");
            this.isGood = obj.optInt("isGood");
            this.weight = obj.optInt("weight");
            this.voice = obj.optString("voice");
            this.voiceTime = obj.optInt("voiceTime");
            this.score = obj.optInt("score");
            this.scoreTxt = obj.optString("scoreTxt", "");
            this.notice = obj.optInt("notice");
            this.state = obj.optInt(DownloadRecord.COLUMN_STATE, 1);
            this.line = obj.optInt("line", 0);
            this.address = obj.optString(a.qf, null);
            this.hit = obj.optLong("hit", 0);
            this.praise = obj.optLong("praise", 0);
            this.scoreUserCount = obj.optLong("scoreUserCount");
            this.scorecount = obj.optLong("scorecount");
            this.status = obj.optInt("status");
            JSONArray arr = obj.optJSONArray("images");
            if (arr != null) {
                for (i = 0; i < arr.length(); i++) {
                    this.images.add(arr.optString(i));
                }
            }
            if (!obj.isNull("user")) {
                this.user = new UserBaseInfo(obj.optJSONObject("user"));
            }
            if (!obj.isNull("category")) {
                this.category = new TopicCategory(obj.optJSONObject("category"));
            }
            JSONArray arrScore = obj.optJSONArray("scorelist");
            if (arrScore != null) {
                for (i = 0; i < arrScore.length(); i++) {
                    this.scorelist.add(new ScoreItem(arrScore.optJSONObject(i)));
                }
            }
            this.isAuthention = obj.optInt("isAuthention");
            if (!obj.isNull("ext")) {
                this.ext = new ExtJson(obj.optJSONObject("ext"));
            }
        }
    }

    public TopicItem(long postID, String title, String categoryName) {
        this.postID = postID;
        this.title = title;
        this.categoryName = categoryName;
    }

    public long getPostID() {
        return this.postID;
    }

    public void setPostID(long postID) {
        this.postID = postID;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return this.detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public long getCommentCount() {
        return this.commentCount;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCommentCount(long commentCount) {
        this.commentCount = commentCount;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getActiveTime() {
        return this.activeTime;
    }

    public void setActiveTime(long activeTime) {
        this.activeTime = activeTime;
    }

    public boolean isGood() {
        return this.isGood == 1;
    }

    public boolean isWeight() {
        return this.weight == 1;
    }

    public List<String> getImages() {
        return this.images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public UserBaseInfo getUserInfo() {
        return this.user;
    }

    public void setUserInfo(UserBaseInfo userInfo) {
        this.user = userInfo;
    }

    public String getVoice() {
        return this.voice;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public int getVoiceTime() {
        return this.voiceTime;
    }

    public void setVoiceTime(int voiceTime) {
        this.voiceTime = voiceTime;
    }

    public TopicCategory getCategory() {
        return this.category;
    }

    public void setCategory(TopicCategory category) {
        this.category = category;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getScoreTxt() {
        return this.scoreTxt;
    }

    public void setScoreTxt(String scoreTxt) {
        this.scoreTxt = scoreTxt;
    }

    public boolean isNotice() {
        return this.notice == 1;
    }

    public void setNotice(boolean b) {
        this.notice = b ? 1 : 0;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public long getTagid() {
        return this.tagid;
    }

    public void setTagid(long tagid) {
        this.tagid = tagid;
    }

    public List<ScoreItem> getScoreList() {
        return this.scorelist;
    }

    public long getScoreUserCount() {
        return this.scoreUserCount;
    }

    public long getScoreCount() {
        return this.scorecount;
    }

    public int getLine() {
        return this.line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getHit() {
        return this.hit;
    }

    public void setHit(long hit) {
        this.hit = hit;
    }

    public long getPraise() {
        return this.praise;
    }

    public void setPraise(long praise) {
        this.praise = praise;
    }

    public long getIsPraised() {
        return this.isPraised;
    }

    public void setIsPraised(long isPraised) {
        this.isPraised = isPraised;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isAuthention() {
        return this.isAuthention == 1;
    }

    public void setAuthentication(boolean authentication) {
        this.isAuthention = authentication ? 1 : 0;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.postID);
        dest.writeLong(this.tagid);
        dest.writeString(this.title);
        dest.writeString(this.detail);
        dest.writeString(this.categoryName);
        dest.writeLong(this.commentCount);
        dest.writeInt(this.isGood);
        dest.writeInt(this.weight);
        dest.writeInt(this.notice);
        dest.writeLong(this.createTime);
        dest.writeLong(this.activeTime);
        dest.writeString(this.voice);
        dest.writeInt(this.voiceTime);
        dest.writeInt(this.score);
        dest.writeString(this.scoreTxt);
        dest.writeInt(this.state);
        dest.writeInt(this.line);
        dest.writeString(this.address);
        dest.writeLong(this.hit);
        dest.writeLong(this.praise);
        dest.writeLong(this.isPraised);
        dest.writeLong(this.scoreUserCount);
        dest.writeLong(this.scorecount);
        dest.writeStringList(this.images);
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.category, flags);
        dest.writeTypedList(this.scorelist);
        dest.writeInt(this.status);
        dest.writeInt(this.isAuthention);
        dest.writeParcelable(this.ext, flags);
    }

    protected TopicItem(Parcel in) {
        this.postID = in.readLong();
        this.tagid = in.readLong();
        this.title = in.readString();
        this.detail = in.readString();
        this.categoryName = in.readString();
        this.commentCount = in.readLong();
        this.isGood = in.readInt();
        this.weight = in.readInt();
        this.notice = in.readInt();
        this.createTime = in.readLong();
        this.activeTime = in.readLong();
        this.voice = in.readString();
        this.voiceTime = in.readInt();
        this.score = in.readInt();
        this.scoreTxt = in.readString();
        this.state = in.readInt();
        this.line = in.readInt();
        this.address = in.readString();
        this.hit = in.readLong();
        this.praise = in.readLong();
        this.isPraised = in.readLong();
        this.scoreUserCount = in.readLong();
        this.scorecount = in.readLong();
        this.images = in.createStringArrayList();
        this.user = (UserBaseInfo) in.readParcelable(UserBaseInfo.class.getClassLoader());
        this.category = (TopicCategory) in.readParcelable(TopicCategory.class.getClassLoader());
        this.scorelist = in.createTypedArrayList(ScoreItem.CREATOR);
        this.status = in.readInt();
        this.isAuthention = in.readInt();
        this.ext = (ExtJson) in.readParcelable(ExtJson.class.getClassLoader());
    }
}
