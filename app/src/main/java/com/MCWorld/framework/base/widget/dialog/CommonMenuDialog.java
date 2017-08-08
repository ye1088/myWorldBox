package com.MCWorld.framework.base.widget.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.MCWorld.framework.R;
import java.util.ArrayList;
import java.util.List;

public class CommonMenuDialog {
    private boolean bShowMenuItem;
    private TextView mBtnCancel;
    private TextView mBtnSpecItem;
    private int mColumnNum;
    private CommonMenuDialogAdapter mCommonMenuDialogAdapter;
    private Context mContext;
    private View mLineSepcItem;
    private DialogManager mMenuDialog;
    protected GridView mMenuGirdView;
    private ArrayList<Object> mMenuItemArrayList;
    private TextView mMenuResName;
    private View mMenuResNameLine;
    private boolean mNightMode;
    private int mSpecItemColor;
    private OnClickListener mSpecItemListener;
    private String mSpecItemTxt;

    public static class CommonMenuDialogAdapter extends BaseAdapter {
        private CommonMenuDialogListener mCommonMenuDialogListener;
        private Context mCtx;
        private Object mCurItem;
        private int mCurOrderIndex = -1;
        private boolean mNightMode;
        private List<Object> mObjects;

        public interface CommonMenuDialogListener {
            void pressMenuById(int i, Object obj);
        }

        public static class ResMenuItem {
            public int color;
            public int index;
            public String name;

            public ResMenuItem(String inName, int inIndex, int inColor) {
                this.name = inName;
                this.index = inIndex;
                this.color = inColor;
            }
        }

        static class ViewHolder {
            RelativeLayout container;
            TextView name;

            ViewHolder() {
            }
        }

        public CommonMenuDialogAdapter(Context context, CommonMenuDialogListener listener, boolean nightmode) {
            this.mCtx = context;
            this.mNightMode = nightmode;
            this.mCommonMenuDialogListener = listener;
        }

        public void setData(List<Object> objects) {
            this.mObjects = objects;
        }

        public void setCurOrderIndex(int index) {
            this.mCurOrderIndex = index;
        }

        public void setCurItem(Object inItem) {
            this.mCurItem = inItem;
        }

        public int getCount() {
            return this.mObjects == null ? 0 : this.mObjects.size();
        }

        public Object getItem(int i) {
            return this.mObjects.get(i);
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(this.mCtx).inflate(R.layout.layout_common_menu_dialog_item, null);
                holder = new ViewHolder();
                holder.container = (RelativeLayout) convertView.findViewById(R.id.rlyComPopDlgItem);
                holder.name = (TextView) convertView.findViewById(R.id.tvComPopDlgItemItemName);
                holder.name.setBackgroundResource(this.mNightMode ? R.drawable.bg_btn_common_menu_night : R.drawable.bg_btn_common_menu_day);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            onBindViewHolder(convertView, holder, (ResMenuItem) getItem(position));
            return convertView;
        }

        public void onBindViewHolder(View view, ViewHolder holder, ResMenuItem item) {
            holder.name.setText(item.name);
            holder.name.setTag(item);
            if (this.mCurOrderIndex != item.index) {
                if (this.mNightMode) {
                    holder.name.setTextColor(view.getResources().getColor(R.color.common_menu_dialog_unfocus_text_color_night));
                } else {
                    holder.name.setTextColor(view.getResources().getColor(R.color.common_menu_dialog_unfocus_text_color_day));
                }
                if (item.color != 0) {
                    holder.name.setTextColor(view.getResources().getColor(item.color));
                }
            } else if (this.mNightMode) {
                holder.name.setTextColor(view.getResources().getColor(R.color.common_menu_dialog_focus_text_color_night));
            } else {
                holder.name.setTextColor(view.getResources().getColor(R.color.common_menu_dialog_focus_text_color_day));
            }
            holder.name.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    ResMenuItem _tmpItem = (ResMenuItem) v.getTag();
                    if (CommonMenuDialogAdapter.this.mCommonMenuDialogListener != null) {
                        CommonMenuDialogAdapter.this.mCommonMenuDialogListener.pressMenuById(_tmpItem.index, CommonMenuDialogAdapter.this.mCurItem);
                    }
                }
            });
        }
    }

    public CommonMenuDialog(Context context, ArrayList<Object> menuItemList, CommonMenuDialogListener listener, boolean nightmode) {
        this(context, menuItemList, listener, nightmode, 1);
    }

    public CommonMenuDialog(Context context, ArrayList<Object> menuItemList, CommonMenuDialogListener listener, boolean nightmode, int columnNum) {
        this.mCommonMenuDialogAdapter = null;
        this.mMenuItemArrayList = new ArrayList();
        this.mSpecItemTxt = null;
        this.bShowMenuItem = true;
        this.mContext = context;
        this.mColumnNum = columnNum;
        this.mNightMode = nightmode;
        this.mMenuDialog = new DialogManager(this.mContext);
        this.mCommonMenuDialogAdapter = new CommonMenuDialogAdapter(this.mContext, listener, this.mNightMode);
        if (menuItemList == null) {
            this.bShowMenuItem = false;
            return;
        }
        this.mMenuItemArrayList.addAll(menuItemList);
        this.mCommonMenuDialogAdapter.setData(this.mMenuItemArrayList);
    }

    public CommonMenuDialog(Context context, CommonMenuDialogListener listener, boolean nightmode, int columnNum) {
        this(context, null, listener, nightmode, columnNum);
    }

    public void setMenuItems(List<Object> menuItemList) {
        if (menuItemList != null) {
            this.bShowMenuItem = true;
        } else {
            this.bShowMenuItem = false;
        }
        this.mMenuItemArrayList.addAll(menuItemList);
        this.mCommonMenuDialogAdapter.setData(this.mMenuItemArrayList);
    }

    public void setSpecialItem(String inName, int inColor, OnClickListener listener) {
        this.mSpecItemTxt = inName;
        this.mSpecItemColor = inColor;
        this.mSpecItemListener = listener;
    }

    public void updateCurFocusIndex(int index) {
        this.mCommonMenuDialogAdapter.setCurOrderIndex(index);
    }

    public void showMenu(Object item, String title) {
        View customView = LayoutInflater.from(this.mContext).inflate(R.layout.layout_common_menu_dialog, null, false);
        this.mMenuGirdView = (GridView) customView.findViewById(R.id.lvComPopDlgItemList);
        this.mLineSepcItem = customView.findViewById(R.id.tvComPopDlgBtnSpecItemLine);
        this.mBtnSpecItem = (TextView) customView.findViewById(R.id.tvComPopDlgBtnSpecItem);
        this.mMenuResName = (TextView) customView.findViewById(R.id.tvComPopDlgResName);
        this.mMenuResNameLine = customView.findViewById(R.id.tvComPopDlgResNameLine);
        this.mBtnCancel = (TextView) customView.findViewById(R.id.tvComPopDlgBtnCancel);
        if (this.bShowMenuItem) {
            this.mCommonMenuDialogAdapter.setCurItem(item);
            this.mMenuGirdView.setNumColumns(this.mColumnNum);
            this.mMenuGirdView.setAdapter(this.mCommonMenuDialogAdapter);
        } else {
            this.mMenuGirdView.setVisibility(8);
        }
        if (this.mNightMode) {
            this.mMenuGirdView.setBackgroundColor(this.mContext.getResources().getColor(R.color.common_menu_dialog_divide_line_color_night01));
            this.mBtnCancel.setBackgroundResource(R.drawable.bg_btn_common_menu_night);
            this.mBtnCancel.setTextColor(this.mContext.getResources().getColor(R.color.common_menu_dialog_unfocus_text_color_night));
            this.mMenuResName.setBackgroundResource(R.drawable.bg_btn_common_menu_night);
            this.mMenuResName.setTextColor(this.mContext.getResources().getColor(R.color.common_menu_dialog_focus_text_color_night));
            customView.findViewById(R.id.tvComPopDlgResNameLine).setBackgroundResource(R.color.common_menu_dialog_divide_line_color_night02);
            customView.findViewById(R.id.tvComPopDlgBtnCancelLine).setBackgroundResource(R.color.common_menu_dialog_divide_line_color_night02);
        } else {
            this.mMenuGirdView.setBackgroundColor(this.mContext.getResources().getColor(R.color.common_menu_dialog_divide_line_color_day01));
            this.mBtnCancel.setBackgroundResource(R.drawable.bg_btn_common_menu_day);
            this.mBtnCancel.setTextColor(this.mContext.getResources().getColor(R.color.common_menu_dialog_unfocus_text_color_day));
            this.mMenuResName.setBackgroundResource(R.drawable.bg_btn_common_menu_day);
            this.mMenuResName.setTextColor(this.mContext.getResources().getColor(R.color.common_menu_dialog_name_color));
            customView.findViewById(R.id.tvComPopDlgResNameLine).setBackgroundResource(R.color.common_menu_dialog_divide_line_color);
            customView.findViewById(R.id.tvComPopDlgBtnCancelLine).setBackgroundResource(R.color.common_menu_dialog_divide_line_color);
        }
        if (title == null) {
            this.mMenuResName.setVisibility(8);
            this.mMenuResNameLine.setVisibility(8);
        } else {
            this.mMenuResName.setText(title);
        }
        if (this.mSpecItemTxt != null) {
            if (this.bShowMenuItem) {
                this.mLineSepcItem.setVisibility(0);
            }
            this.mBtnSpecItem.setVisibility(0);
            this.mBtnSpecItem.setOnClickListener(this.mSpecItemListener);
            this.mBtnSpecItem.setText(this.mSpecItemTxt);
            this.mBtnSpecItem.setTextColor(this.mContext.getResources().getColor(this.mSpecItemColor));
            this.mBtnSpecItem.setBackgroundResource(this.mNightMode ? R.drawable.bg_btn_common_menu_night : R.drawable.bg_btn_common_menu_day);
            this.mLineSepcItem.setBackgroundColor(this.mContext.getResources().getColor(this.mNightMode ? R.color.common_menu_dialog_divide_line_color_night01 : R.color.common_menu_dialog_divide_line_color_day01));
        }
        this.mBtnCancel.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CommonMenuDialog.this.mMenuDialog.dismissDialog();
            }
        });
        this.mMenuDialog.showCustomViewPopupDialog(customView);
    }

    public void setTitleStyle(int size, int color) {
        this.mMenuResName.setTextSize((float) size);
        if (color != 0) {
            this.mMenuResName.setTextColor(color);
        }
    }

    public void dismissDialog() {
        this.mMenuDialog.dismissDialog();
    }

    public boolean isDialogShowing() {
        if (this.mMenuDialog != null) {
            return this.mMenuDialog.isDialogShowing();
        }
        return false;
    }
}
