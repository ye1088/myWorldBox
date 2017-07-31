package com.huluxia.framework.base.widget.hlistview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.ExpandableListAdapter;
import android.widget.ListAdapter;
import com.huluxia.framework.R$attr;
import com.huluxia.framework.R$styleable;
import com.huluxia.framework.base.widget.hlistview.AdapterView.OnItemClickListener;
import com.huluxia.framework.base.widget.hlistview.ExpandableHListConnector.PositionMetadata;
import java.util.ArrayList;

public class ExpandableHListView extends HListView {
    public static final int CHILD_INDICATOR_INHERIT = -1;
    private static final int[] CHILD_LAST_STATE_SET = new int[]{16842918};
    private static final int[] EMPTY_STATE_SET = new int[0];
    private static final int[] GROUP_EMPTY_STATE_SET = new int[]{16842921};
    private static final int[] GROUP_EXPANDED_EMPTY_STATE_SET = new int[]{16842920, 16842921};
    private static final int[] GROUP_EXPANDED_STATE_SET = new int[]{16842920};
    private static final int[][] GROUP_STATE_SETS = new int[][]{EMPTY_STATE_SET, GROUP_EXPANDED_STATE_SET, GROUP_EMPTY_STATE_SET, GROUP_EXPANDED_EMPTY_STATE_SET};
    private static final int INDICATOR_UNDEFINED = -2;
    private static final long PACKED_POSITION_INT_MASK_CHILD = -1;
    private static final long PACKED_POSITION_INT_MASK_GROUP = 2147483647L;
    private static final long PACKED_POSITION_MASK_CHILD = 4294967295L;
    private static final long PACKED_POSITION_MASK_GROUP = 9223372032559808512L;
    private static final long PACKED_POSITION_MASK_TYPE = Long.MIN_VALUE;
    private static final long PACKED_POSITION_SHIFT_GROUP = 32;
    private static final long PACKED_POSITION_SHIFT_TYPE = 63;
    public static final int PACKED_POSITION_TYPE_CHILD = 1;
    public static final int PACKED_POSITION_TYPE_GROUP = 0;
    public static final int PACKED_POSITION_TYPE_NULL = 2;
    public static final long PACKED_POSITION_VALUE_NULL = 4294967295L;
    private ExpandableListAdapter mAdapter;
    private Drawable mChildDivider;
    private Drawable mChildIndicator;
    private int mChildIndicatorGravity;
    private int mChildIndicatorHeight;
    private int mChildIndicatorLeft;
    private int mChildIndicatorTop;
    private int mChildIndicatorWidth;
    private ExpandableHListConnector mConnector;
    private Drawable mGroupIndicator;
    private int mGroupIndicatorHeight;
    private int mGroupIndicatorWidth;
    private int mIndicatorGravity;
    private int mIndicatorLeft;
    private final Rect mIndicatorRect;
    private int mIndicatorTop;
    private OnChildClickListener mOnChildClickListener;
    private OnGroupClickListener mOnGroupClickListener;
    private OnGroupCollapseListener mOnGroupCollapseListener;
    private OnGroupExpandListener mOnGroupExpandListener;
    private final Rect mTempRect;

    public static class ExpandableListContextMenuInfo implements ContextMenuInfo {
        public long id;
        public long packedPosition;
        public View targetView;

        public ExpandableListContextMenuInfo(View targetView, long packedPosition, long id) {
            this.targetView = targetView;
            this.packedPosition = packedPosition;
            this.id = id;
        }
    }

    public interface OnChildClickListener {
        boolean onChildClick(ExpandableHListView expandableHListView, View view, int i, int i2, long j);
    }

    public interface OnGroupClickListener {
        boolean onGroupClick(ExpandableHListView expandableHListView, View view, int i, long j);
    }

    public interface OnGroupCollapseListener {
        void onGroupCollapse(int i);
    }

    public interface OnGroupExpandListener {
        void onGroupExpand(int i);
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
        ArrayList<GroupMetadata> expandedGroupMetadataList;

        SavedState(Parcelable superState, ArrayList<GroupMetadata> expandedGroupMetadataList) {
            super(superState);
            this.expandedGroupMetadataList = expandedGroupMetadataList;
        }

        private SavedState(Parcel in) {
            super(in);
            this.expandedGroupMetadataList = new ArrayList();
            in.readList(this.expandedGroupMetadataList, ExpandableHListConnector.class.getClassLoader());
        }

        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeList(this.expandedGroupMetadataList);
        }
    }

    public ExpandableHListView(Context context) {
        this(context, null);
    }

    public ExpandableHListView(Context context, AttributeSet attrs) {
        this(context, attrs, R$attr.hlv_expandableListViewStyle);
    }

    public ExpandableHListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mIndicatorRect = new Rect();
        this.mTempRect = new Rect();
        TypedArray a = context.obtainStyledAttributes(attrs, R$styleable.ExpandableHListView, defStyle, 0);
        setGroupIndicator(a.getDrawable(R$styleable.ExpandableHListView_hlv_groupIndicator));
        setChildIndicator(a.getDrawable(R$styleable.ExpandableHListView_hlv_childIndicator));
        this.mIndicatorLeft = a.getDimensionPixelSize(R$styleable.ExpandableHListView_hlv_indicatorPaddingLeft, 0);
        this.mIndicatorTop = a.getDimensionPixelSize(R$styleable.ExpandableHListView_hlv_indicatorPaddingTop, 0);
        this.mIndicatorGravity = a.getInt(R$styleable.ExpandableHListView_hlv_indicatorGravity, 0);
        this.mChildIndicatorGravity = a.getInt(R$styleable.ExpandableHListView_hlv_childIndicatorGravity, 0);
        this.mChildIndicatorLeft = a.getDimensionPixelSize(R$styleable.ExpandableHListView_hlv_childIndicatorPaddingLeft, 0);
        this.mChildIndicatorTop = a.getDimensionPixelSize(R$styleable.ExpandableHListView_hlv_childIndicatorPaddingTop, 0);
        this.mChildDivider = a.getDrawable(R$styleable.ExpandableHListView_hlv_childDivider);
        a.recycle();
    }

    public void onRtlPropertiesChanged(int layoutDirection) {
        resolveIndicator();
        resolveChildIndicator();
    }

    private void resolveIndicator() {
        if (this.mGroupIndicator != null) {
            this.mGroupIndicatorWidth = this.mGroupIndicator.getIntrinsicWidth();
            this.mGroupIndicatorHeight = this.mGroupIndicator.getIntrinsicHeight();
            return;
        }
        this.mGroupIndicatorWidth = 0;
        this.mGroupIndicatorHeight = 0;
    }

    private void resolveChildIndicator() {
        if (this.mChildIndicator != null) {
            this.mChildIndicatorWidth = this.mChildIndicator.getIntrinsicWidth();
            this.mChildIndicatorHeight = this.mChildIndicator.getIntrinsicHeight();
            return;
        }
        this.mChildIndicatorWidth = 0;
        this.mChildIndicatorHeight = 0;
    }

    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (this.mChildIndicator != null || this.mGroupIndicator != null) {
            int headerViewsCount = getHeaderViewsCount();
            int lastChildFlPos = ((this.mItemCount - getFooterViewsCount()) - headerViewsCount) - 1;
            int myRight = getRight();
            int lastItemType = -4;
            Rect indicatorRect = this.mIndicatorRect;
            int childCount = getChildCount();
            int i = 0;
            int childFlPos = this.mFirstPosition - headerViewsCount;
            while (i < childCount) {
                if (childFlPos >= 0) {
                    if (childFlPos <= lastChildFlPos) {
                        View item = getChildAt(i);
                        int left = item.getLeft();
                        int right = item.getRight();
                        if (right >= 0 && left <= myRight) {
                            PositionMetadata pos = this.mConnector.getUnflattenedPos(childFlPos);
                            if (pos.position.type != lastItemType) {
                                if (pos.position.type == 1) {
                                    indicatorRect.top = item.getTop() + this.mChildIndicatorTop;
                                    indicatorRect.bottom = item.getBottom() + this.mChildIndicatorTop;
                                } else {
                                    indicatorRect.top = item.getTop() + this.mIndicatorTop;
                                    indicatorRect.bottom = item.getBottom() + this.mIndicatorTop;
                                }
                                lastItemType = pos.position.type;
                            }
                            if (indicatorRect.top != indicatorRect.bottom) {
                                if (pos.position.type == 1) {
                                    indicatorRect.left = this.mChildIndicatorLeft + left;
                                    indicatorRect.right = this.mChildIndicatorLeft + right;
                                } else {
                                    indicatorRect.left = this.mIndicatorLeft + left;
                                    indicatorRect.right = this.mIndicatorLeft + right;
                                }
                                Drawable indicator = getIndicator(pos);
                                if (indicator != null) {
                                    if (pos.position.type == 1) {
                                        Gravity.apply(this.mChildIndicatorGravity, this.mChildIndicatorWidth, this.mChildIndicatorHeight, indicatorRect, this.mTempRect);
                                    } else {
                                        Gravity.apply(this.mIndicatorGravity, this.mGroupIndicatorWidth, this.mGroupIndicatorHeight, indicatorRect, this.mTempRect);
                                    }
                                    indicator.setBounds(this.mTempRect);
                                    indicator.draw(canvas);
                                }
                            }
                            pos.recycle();
                        }
                    } else {
                        return;
                    }
                }
                i++;
                childFlPos++;
            }
        }
    }

    private Drawable getIndicator(PositionMetadata pos) {
        Drawable indicator;
        int i = 1;
        int i2 = 0;
        if (pos.position.type == 2) {
            indicator = this.mGroupIndicator;
            if (indicator != null && indicator.isStateful()) {
                boolean isEmpty = pos.groupMetadata == null || pos.groupMetadata.lastChildFlPos == pos.groupMetadata.flPos;
                if (!pos.isExpanded()) {
                    i = 0;
                }
                if (isEmpty) {
                    i2 = 2;
                }
                indicator.setState(GROUP_STATE_SETS[i | i2]);
            }
        } else {
            indicator = this.mChildIndicator;
            if (indicator != null && indicator.isStateful()) {
                indicator.setState(pos.position.flatListPos == pos.groupMetadata.lastChildFlPos ? CHILD_LAST_STATE_SET : EMPTY_STATE_SET);
            }
        }
        return indicator;
    }

    public void setChildDivider(Drawable childDivider) {
        this.mChildDivider = childDivider;
    }

    void drawDivider(Canvas canvas, Rect bounds, int childIndex) {
        int flatListPosition = childIndex + this.mFirstPosition;
        if (flatListPosition >= 0) {
            PositionMetadata pos = this.mConnector.getUnflattenedPos(getFlatPositionForConnector(flatListPosition));
            if (pos.position.type == 1 || (pos.isExpanded() && pos.groupMetadata.lastChildFlPos != pos.groupMetadata.flPos)) {
                Drawable divider = this.mChildDivider;
                divider.setBounds(bounds);
                divider.draw(canvas);
                pos.recycle();
                return;
            }
            pos.recycle();
        }
        super.drawDivider(canvas, bounds, flatListPosition);
    }

    public void setAdapter(ListAdapter adapter) {
        throw new RuntimeException("For ExpandableListView, use setAdapter(ExpandableListAdapter) instead of setAdapter(ListAdapter)");
    }

    public ListAdapter getAdapter() {
        return super.getAdapter();
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        super.setOnItemClickListener(l);
    }

    public void setAdapter(ExpandableListAdapter adapter) {
        this.mAdapter = adapter;
        if (adapter != null) {
            this.mConnector = new ExpandableHListConnector(adapter);
        } else {
            this.mConnector = null;
        }
        super.setAdapter(this.mConnector);
    }

    public ExpandableListAdapter getExpandableListAdapter() {
        return this.mAdapter;
    }

    private boolean isHeaderOrFooterPosition(int position) {
        return position < getHeaderViewsCount() || position >= this.mItemCount - getFooterViewsCount();
    }

    private int getFlatPositionForConnector(int flatListPosition) {
        return flatListPosition - getHeaderViewsCount();
    }

    private int getAbsoluteFlatPosition(int flatListPosition) {
        return getHeaderViewsCount() + flatListPosition;
    }

    public boolean performItemClick(View v, int position, long id) {
        if (isHeaderOrFooterPosition(position)) {
            return super.performItemClick(v, position, id);
        }
        return handleItemClick(v, getFlatPositionForConnector(position), id);
    }

    boolean handleItemClick(View v, int position, long id) {
        boolean returnValue;
        PositionMetadata posMetadata = this.mConnector.getUnflattenedPos(position);
        id = getChildOrGroupId(posMetadata.position);
        if (posMetadata.position.type == 2) {
            if (this.mOnGroupClickListener != null) {
                if (this.mOnGroupClickListener.onGroupClick(this, v, posMetadata.position.groupPos, id)) {
                    posMetadata.recycle();
                    return true;
                }
            }
            if (posMetadata.isExpanded()) {
                this.mConnector.collapseGroup(posMetadata);
                playSoundEffect(0);
                if (this.mOnGroupCollapseListener != null) {
                    this.mOnGroupCollapseListener.onGroupCollapse(posMetadata.position.groupPos);
                }
            } else {
                this.mConnector.expandGroup(posMetadata);
                playSoundEffect(0);
                if (this.mOnGroupExpandListener != null) {
                    this.mOnGroupExpandListener.onGroupExpand(posMetadata.position.groupPos);
                }
                int shiftedGroupPosition = posMetadata.position.flatListPos + getHeaderViewsCount();
                smoothScrollToPosition(this.mAdapter.getChildrenCount(posMetadata.position.groupPos) + shiftedGroupPosition, shiftedGroupPosition);
            }
            returnValue = true;
        } else if (this.mOnChildClickListener != null) {
            playSoundEffect(0);
            return this.mOnChildClickListener.onChildClick(this, v, posMetadata.position.groupPos, posMetadata.position.childPos, id);
        } else {
            returnValue = false;
        }
        posMetadata.recycle();
        return returnValue;
    }

    public boolean expandGroup(int groupPos) {
        return expandGroup(groupPos, false);
    }

    public boolean expandGroup(int groupPos, boolean animate) {
        ExpandableHListPosition elGroupPos = ExpandableHListPosition.obtain(2, groupPos, -1, -1);
        PositionMetadata pm = this.mConnector.getFlattenedPos(elGroupPos);
        elGroupPos.recycle();
        boolean retValue = this.mConnector.expandGroup(pm);
        if (this.mOnGroupExpandListener != null) {
            this.mOnGroupExpandListener.onGroupExpand(groupPos);
        }
        if (animate) {
            int shiftedGroupPosition = pm.position.flatListPos + getHeaderViewsCount();
            smoothScrollToPosition(this.mAdapter.getChildrenCount(groupPos) + shiftedGroupPosition, shiftedGroupPosition);
        }
        pm.recycle();
        return retValue;
    }

    public boolean collapseGroup(int groupPos) {
        boolean retValue = this.mConnector.collapseGroup(groupPos);
        if (this.mOnGroupCollapseListener != null) {
            this.mOnGroupCollapseListener.onGroupCollapse(groupPos);
        }
        return retValue;
    }

    public void setOnGroupCollapseListener(OnGroupCollapseListener onGroupCollapseListener) {
        this.mOnGroupCollapseListener = onGroupCollapseListener;
    }

    public void setOnGroupExpandListener(OnGroupExpandListener onGroupExpandListener) {
        this.mOnGroupExpandListener = onGroupExpandListener;
    }

    public void setOnGroupClickListener(OnGroupClickListener onGroupClickListener) {
        this.mOnGroupClickListener = onGroupClickListener;
    }

    public void setOnChildClickListener(OnChildClickListener onChildClickListener) {
        this.mOnChildClickListener = onChildClickListener;
    }

    public long getExpandableListPosition(int flatListPosition) {
        if (isHeaderOrFooterPosition(flatListPosition)) {
            return 4294967295L;
        }
        PositionMetadata pm = this.mConnector.getUnflattenedPos(getFlatPositionForConnector(flatListPosition));
        long packedPos = pm.position.getPackedPosition();
        pm.recycle();
        return packedPos;
    }

    public int getFlatListPosition(long packedPosition) {
        ExpandableHListPosition elPackedPos = ExpandableHListPosition.obtainPosition(packedPosition);
        PositionMetadata pm = this.mConnector.getFlattenedPos(elPackedPos);
        elPackedPos.recycle();
        int flatListPosition = pm.position.flatListPos;
        pm.recycle();
        return getAbsoluteFlatPosition(flatListPosition);
    }

    public long getSelectedPosition() {
        return getExpandableListPosition(getSelectedItemPosition());
    }

    public long getSelectedId() {
        long packedPos = getSelectedPosition();
        if (packedPos == 4294967295L) {
            return -1;
        }
        int groupPos = getPackedPositionGroup(packedPos);
        if (getPackedPositionType(packedPos) == 0) {
            return this.mAdapter.getGroupId(groupPos);
        }
        return this.mAdapter.getChildId(groupPos, getPackedPositionChild(packedPos));
    }

    public void setSelectedGroup(int groupPosition) {
        ExpandableHListPosition elGroupPos = ExpandableHListPosition.obtainGroupPosition(groupPosition);
        PositionMetadata pm = this.mConnector.getFlattenedPos(elGroupPos);
        elGroupPos.recycle();
        super.setSelection(getAbsoluteFlatPosition(pm.position.flatListPos));
        pm.recycle();
    }

    public boolean setSelectedChild(int groupPosition, int childPosition, boolean shouldExpandGroup) {
        ExpandableHListPosition elChildPos = ExpandableHListPosition.obtainChildPosition(groupPosition, childPosition);
        PositionMetadata flatChildPos = this.mConnector.getFlattenedPos(elChildPos);
        if (flatChildPos == null) {
            if (!shouldExpandGroup) {
                return false;
            }
            expandGroup(groupPosition);
            flatChildPos = this.mConnector.getFlattenedPos(elChildPos);
            if (flatChildPos == null) {
                throw new IllegalStateException("Could not find child");
            }
        }
        super.setSelection(getAbsoluteFlatPosition(flatChildPos.position.flatListPos));
        elChildPos.recycle();
        flatChildPos.recycle();
        return true;
    }

    public boolean isGroupExpanded(int groupPosition) {
        return this.mConnector.isGroupExpanded(groupPosition);
    }

    public static int getPackedPositionType(long packedPosition) {
        if (packedPosition == 4294967295L) {
            return 2;
        }
        return (packedPosition & Long.MIN_VALUE) == Long.MIN_VALUE ? 1 : 0;
    }

    public static int getPackedPositionGroup(long packedPosition) {
        if (packedPosition == 4294967295L) {
            return -1;
        }
        return (int) ((PACKED_POSITION_MASK_GROUP & packedPosition) >> 32);
    }

    public static int getPackedPositionChild(long packedPosition) {
        if (packedPosition != 4294967295L && (packedPosition & Long.MIN_VALUE) == Long.MIN_VALUE) {
            return (int) (packedPosition & 4294967295L);
        }
        return -1;
    }

    public static long getPackedPositionForChild(int groupPosition, int childPosition) {
        return (Long.MIN_VALUE | ((((long) groupPosition) & PACKED_POSITION_INT_MASK_GROUP) << 32)) | (((long) childPosition) & -1);
    }

    public static long getPackedPositionForGroup(int groupPosition) {
        return (((long) groupPosition) & PACKED_POSITION_INT_MASK_GROUP) << 32;
    }

    ContextMenuInfo createContextMenuInfo(View view, int flatListPosition, long id) {
        if (isHeaderOrFooterPosition(flatListPosition)) {
            return new AdapterView$AdapterContextMenuInfo(view, flatListPosition, id);
        }
        PositionMetadata pm = this.mConnector.getUnflattenedPos(getFlatPositionForConnector(flatListPosition));
        ExpandableHListPosition pos = pm.position;
        id = getChildOrGroupId(pos);
        long packedPosition = pos.getPackedPosition();
        pm.recycle();
        return new ExpandableListContextMenuInfo(view, packedPosition, id);
    }

    private long getChildOrGroupId(ExpandableHListPosition position) {
        if (position.type == 1) {
            return this.mAdapter.getChildId(position.groupPos, position.childPos);
        }
        return this.mAdapter.getGroupId(position.groupPos);
    }

    public void setChildIndicator(Drawable childIndicator) {
        this.mChildIndicator = childIndicator;
        resolveChildIndicator();
    }

    public void setGroupIndicator(Drawable groupIndicator) {
        this.mGroupIndicator = groupIndicator;
        resolveIndicator();
    }

    public Parcelable onSaveInstanceState() {
        return new SavedState(super.onSaveInstanceState(), this.mConnector != null ? this.mConnector.getExpandedGroupMetadataList() : null);
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof SavedState) {
            SavedState ss = (SavedState) state;
            super.onRestoreInstanceState(ss.getSuperState());
            if (this.mConnector != null && ss.expandedGroupMetadataList != null) {
                this.mConnector.setExpandedGroupMetadataList(ss.expandedGroupMetadataList);
                return;
            }
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
        super.onInitializeAccessibilityEvent(event);
        event.setClassName(ExpandableHListView.class.getName());
    }

    @TargetApi(14)
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info) {
        super.onInitializeAccessibilityNodeInfo(info);
        info.setClassName(ExpandableHListView.class.getName());
    }
}
