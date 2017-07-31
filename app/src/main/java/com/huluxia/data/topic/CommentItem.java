package com.huluxia.data.topic;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.huluxia.data.UserBaseInfo;
import com.huluxia.data.category.TopicCategory;
import com.huluxia.data.profile.a;
import com.huluxia.framework.base.http.toolbox.download.DownloadRecord;
import io.netty.handler.codec.rtsp.RtspHeaders.Values;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.tools.ant.types.selectors.ContainsSelector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CommentItem implements Parcelable, Serializable {
    public static final Creator<CommentItem> CREATOR = new Creator<CommentItem>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return ac(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return aM(i);
        }

        public CommentItem ac(Parcel in) {
            return new CommentItem(in);
        }

        public CommentItem[] aM(int size) {
            return new CommentItem[size];
        }
    };
    private static final long serialVersionUID = 928157839121865268L;
    public String address;
    public TopicCategory category;
    public long commentID;
    public long createTime;
    public List<String> images = new ArrayList();
    public TopicItem post;
    public RefCommentItem refComment;
    public int score;
    public String scoreTxt;
    public long scoreUserCount;
    public long scorecount;
    public List<ScoreItem> scorelist = new ArrayList();
    public long seq;
    public int state;
    public String text;
    public UserBaseInfo user;
    public String voice;
    public int voiceTime;

    public CommentItem(JSONObject obj) throws JSONException {
        int i;
        this.commentID = obj.optLong("commentID");
        this.text = obj.optString(ContainsSelector.CONTAINS_KEY);
        JSONArray arr = obj.optJSONArray("images");
        if (arr != null) {
            for (i = 0; i < arr.length(); i++) {
                this.images.add(arr.optString(i));
            }
        }
        this.voice = obj.optString("voice");
        this.voiceTime = obj.optInt("voiceTime");
        this.score = obj.optInt("score");
        this.scoreTxt = obj.optString("scoreTxt", "");
        this.seq = obj.optLong(Values.SEQ);
        this.createTime = obj.optLong("createTime");
        this.state = obj.optInt(DownloadRecord.COLUMN_STATE);
        if (!obj.isNull("user")) {
            this.user = new UserBaseInfo(obj.optJSONObject("user"));
        }
        if (!obj.isNull("post")) {
            this.post = new TopicItem(obj.optJSONObject("post"));
        }
        if (!obj.isNull("category")) {
            this.category = new TopicCategory(obj.optJSONObject("category"));
        }
        if (!obj.isNull("refComment")) {
            this.refComment = new RefCommentItem(obj.optJSONObject("refComment"));
        }
        JSONArray arrScore = obj.optJSONArray("scorelist");
        if (arrScore != null) {
            for (i = 0; i < arrScore.length(); i++) {
                this.scorelist.add(new ScoreItem(arrScore.optJSONObject(i)));
            }
        }
        this.scoreUserCount = obj.optLong("scoreUserCount");
        this.scorecount = obj.optLong("scorecount");
        this.address = obj.optString(a.qf, null);
    }

    public CommentItem(Parcel source) {
        this.commentID = source.readLong();
        this.text = source.readString();
        source.readStringList(this.images);
        this.voice = source.readString();
        this.voiceTime = source.readInt();
        this.score = source.readInt();
        this.scoreTxt = source.readString();
        this.seq = source.readLong();
        this.createTime = source.readLong();
        this.state = source.readInt();
        this.user = (UserBaseInfo) source.readParcelable(UserBaseInfo.class.getClassLoader());
        this.post = (TopicItem) source.readParcelable(TopicItem.class.getClassLoader());
        this.category = (TopicCategory) source.readParcelable(TopicCategory.class.getClassLoader());
        this.refComment = (RefCommentItem) source.readParcelable(RefCommentItem.class.getClassLoader());
        source.readTypedList(this.scorelist, ScoreItem.CREATOR);
        this.scoreUserCount = source.readLong();
        this.scorecount = source.readLong();
        this.address = source.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.commentID);
        dest.writeString(this.text);
        dest.writeStringList(this.images);
        dest.writeString(this.voice);
        dest.writeInt(this.voiceTime);
        dest.writeInt(this.score);
        dest.writeString(this.scoreTxt);
        dest.writeLong(this.seq);
        dest.writeLong(this.createTime);
        dest.writeInt(this.state);
        dest.writeParcelable(this.user, flags);
        dest.writeParcelable(this.post, flags);
        dest.writeParcelable(this.category, flags);
        dest.writeParcelable(this.refComment, flags);
        dest.writeTypedList(this.scorelist);
        dest.writeLong(this.scoreUserCount);
        dest.writeLong(this.scorecount);
        dest.writeString(this.address);
    }

    public long getCommentID() {
        return this.commentID;
    }

    public String getText() {
        return this.text;
    }

    public List<String> getImages() {
        return this.images;
    }

    public String getVoice() {
        return this.voice;
    }

    public long getVoiceTime() {
        return (long) this.voiceTime;
    }

    public int getScore() {
        return this.score;
    }

    public String getScoreTxt() {
        return this.scoreTxt;
    }

    public long getSeq() {
        return this.seq;
    }

    public long getCreateTime() {
        return this.createTime;
    }

    public int getState() {
        return this.state;
    }

    public UserBaseInfo getUserInfo() {
        return this.user;
    }

    public TopicItem getTopicItem() {
        return this.post;
    }

    public TopicCategory getTopicCategory() {
        return this.category;
    }

    public RefCommentItem getRefComment() {
        return this.refComment;
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

    public String getAddress() {
        return this.address;
    }
}
