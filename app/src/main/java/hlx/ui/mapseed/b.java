package hlx.ui.mapseed;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/* compiled from: SeedMoreInfo */
public class b extends com.MCWorld.module.b {
    public static final Creator<b> CREATOR = new 1();
    public List<a> seedList;

    /* compiled from: SeedMoreInfo */
    public static class a implements Parcelable {
        public static final Creator<a> CREATOR = new 1();
        public int SpawnX;
        public int SpawnY;
        public int SpawnZ;
        public String author;
        public String cateName;
        public String createTime;
        public long downCount;
        public String icon;
        public int id;
        public String language;
        public String mapDesc;
        public String name;
        public long postID;
        public List<String> resourceList;
        public long seedValue;
        public String source;
        public String version;

        public a() {
            this.resourceList = new ArrayList();
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.id);
            dest.writeString(this.name);
            dest.writeString(this.icon);
            dest.writeString(this.mapDesc);
            dest.writeString(this.createTime);
            dest.writeString(this.cateName);
            dest.writeString(this.language);
            dest.writeString(this.author);
            dest.writeLong(this.downCount);
            dest.writeString(this.source);
            dest.writeString(this.version);
            dest.writeLong(this.postID);
            dest.writeStringList(this.resourceList);
            dest.writeLong(this.seedValue);
            dest.writeInt(this.SpawnX);
            dest.writeInt(this.SpawnY);
            dest.writeInt(this.SpawnZ);
        }

        protected a(Parcel in) {
            this.id = in.readInt();
            this.name = in.readString();
            this.icon = in.readString();
            this.mapDesc = in.readString();
            this.createTime = in.readString();
            this.cateName = in.readString();
            this.language = in.readString();
            this.author = in.readString();
            this.downCount = in.readLong();
            this.source = in.readString();
            this.version = in.readString();
            this.postID = in.readLong();
            this.resourceList = in.createStringArrayList();
            this.seedValue = in.readLong();
            this.SpawnX = in.readInt();
            this.SpawnY = in.readInt();
            this.SpawnZ = in.readInt();
        }
    }

    public b() {
        this.seedList = new ArrayList();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.seedList);
    }

    protected b(Parcel in) {
        super(in);
        this.seedList = in.createTypedArrayList(a.CREATOR);
    }
}
