package com.MCWorld.module.topic;

import android.os.Parcel;
import android.os.Parcelable;

import com.MCWorld.data.category.TopicCategory;
import com.MCWorld.data.topic.TopicItem;
import com.MCWorld.module.a;
import java.util.ArrayList;
import java.util.List;

/* compiled from: BbsTopic */
public class b extends a implements Parcelable {
    public static final Creator<b> CREATOR = new Creator<b>() {
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return bi(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return jW(i);
        }

        public b bi(Parcel source) {
            return new b(source);
        }

        public b[] jW(int size) {
            return new b[size];
        }
    };
    public TopicCategory category;
    public int more;
    public List<TopicItem> posts = new ArrayList();
    public String start;

    public b(Parcel source) {
        super(source);
        source.readTypedList(this.posts, TopicItem.CREATOR);
        this.more = source.readInt();
        this.start = source.readString();
        this.category = (TopicCategory) source.readParcelable(TopicCategory.class.getClassLoader());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.posts);
        dest.writeInt(this.more);
        dest.writeString(this.start);
        dest.writeParcelable(this.category, 0);
    }
}
