package com.huluxia.framework.base.widget.hlistview;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.SparseArrayCompat;
import android.view.View.BaseSavedState;

class AbsHListView$SavedState extends BaseSavedState {
    public static final Creator<AbsHListView$SavedState> CREATOR = new Creator<AbsHListView$SavedState>() {
        public AbsHListView$SavedState createFromParcel(Parcel in) {
            return new AbsHListView$SavedState(in);
        }

        public AbsHListView$SavedState[] newArray(int size) {
            return new AbsHListView$SavedState[size];
        }
    };
    LongSparseArray<Integer> checkIdState;
    SparseArrayCompat<Boolean> checkState;
    int checkedItemCount;
    String filter;
    long firstId;
    boolean inActionMode;
    int position;
    long selectedId;
    int viewLeft;
    int width;

    AbsHListView$SavedState(Parcelable superState) {
        super(superState);
    }

    private AbsHListView$SavedState(Parcel in) {
        super(in);
        this.selectedId = in.readLong();
        this.firstId = in.readLong();
        this.viewLeft = in.readInt();
        this.position = in.readInt();
        this.width = in.readInt();
        this.filter = in.readString();
        this.inActionMode = in.readByte() != (byte) 0;
        this.checkedItemCount = in.readInt();
        this.checkState = readSparseBooleanArray(in);
        this.checkIdState = readSparseLongArray(in);
    }

    private LongSparseArray<Integer> readSparseLongArray(Parcel in) {
        int N = in.readInt();
        if (N <= 0) {
            return null;
        }
        LongSparseArray<Integer> array = new LongSparseArray(N);
        readSparseLongArrayInternal(array, in, N);
        return array;
    }

    private SparseArrayCompat<Boolean> readSparseBooleanArray(Parcel in) {
        int N = in.readInt();
        if (N < 0) {
            return null;
        }
        SparseArrayCompat<Boolean> sa = new SparseArrayCompat(N);
        readSparseBooleanArrayInternal(sa, in, N);
        return sa;
    }

    private void readSparseLongArrayInternal(LongSparseArray<Integer> outVal, Parcel in, int N) {
        while (N > 0) {
            outVal.put(in.readLong(), Integer.valueOf(in.readInt()));
            N--;
        }
    }

    private void readSparseBooleanArrayInternal(SparseArrayCompat<Boolean> outVal, Parcel in, int N) {
        while (N > 0) {
            outVal.append(in.readInt(), Boolean.valueOf(in.readByte() == (byte) 1));
            N--;
        }
    }

    private void writeSparseLongArray(LongSparseArray<Integer> array, Parcel out) {
        int N = array != null ? array.size() : 0;
        out.writeInt(N);
        for (int i = 0; i < N; i++) {
            out.writeLong(array.keyAt(i));
            out.writeInt(((Integer) array.valueAt(i)).intValue());
        }
    }

    private void writeSparseBooleanArray(SparseArrayCompat<Boolean> val, Parcel out) {
        if (val == null) {
            out.writeInt(-1);
            return;
        }
        int N = val.size();
        out.writeInt(N);
        for (int i = 0; i < N; i++) {
            out.writeInt(val.keyAt(i));
            out.writeByte((byte) (((Boolean) val.valueAt(i)).booleanValue() ? 1 : 0));
        }
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeLong(this.selectedId);
        out.writeLong(this.firstId);
        out.writeInt(this.viewLeft);
        out.writeInt(this.position);
        out.writeInt(this.width);
        out.writeString(this.filter);
        out.writeByte((byte) (this.inActionMode ? 1 : 0));
        out.writeInt(this.checkedItemCount);
        writeSparseBooleanArray(this.checkState, out);
        writeSparseLongArray(this.checkIdState, out);
    }

    public String toString() {
        return "AbsListView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " selectedId=" + this.selectedId + " firstId=" + this.firstId + " viewLeft=" + this.viewLeft + " position=" + this.position + " width=" + this.width + " filter=" + this.filter + " checkState=" + this.checkState + "}";
    }
}
