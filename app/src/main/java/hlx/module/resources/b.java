package hlx.module.resources;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.huluxia.module.a;
import java.util.ArrayList;

/* compiled from: ResourceCateInfo */
public class b extends a implements Parcelable {
    public static final Creator<b> CREATOR = new 1();
    public ArrayList<a> categorylist = new ArrayList();
    public ArrayList<a> jsCategoryList = new ArrayList();
    public ArrayList<a> mapCategoryList = new ArrayList();
    public ArrayList<a> skinCategoryList = new ArrayList();
    public ArrayList<a> woodCategoryList = new ArrayList();

    public b(Parcel source) {
        source.readTypedList(this.mapCategoryList, a.CREATOR);
        source.readTypedList(this.jsCategoryList, a.CREATOR);
        source.readTypedList(this.skinCategoryList, a.CREATOR);
        source.readTypedList(this.woodCategoryList, a.CREATOR);
        source.readTypedList(this.categorylist, a.CREATOR);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.mapCategoryList);
        dest.writeTypedList(this.jsCategoryList);
        dest.writeTypedList(this.skinCategoryList);
        dest.writeTypedList(this.woodCategoryList);
        dest.writeTypedList(this.categorylist);
    }
}
