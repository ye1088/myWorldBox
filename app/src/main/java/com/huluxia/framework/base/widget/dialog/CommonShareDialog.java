package com.huluxia.framework.base.widget.dialog;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.huluxia.framework.R;
import java.util.ArrayList;
import java.util.List;

public class CommonShareDialog {
    private TextView mBtnCancel;
    private int mColumnNum;
    private CommonShareDialogAdapter mCommonShareDialogAdapter = null;
    private Context mContext;
    private ArrayList<Object> mMenuItemArrayList = new ArrayList();
    private boolean mNightMode;
    private DialogManager mShareDialog;
    protected GridView mShareGirdView;

    public static class CommonShareDialogAdapter extends BaseAdapter {
        private Context mAdaptContext;
        private CommonShareDialogListener mCommonShareDialogListener;
        private Object mCurItem;
        private Object mCurParam;
        private boolean mNightMode;
        private List<Object> mObjects;

        public interface CommonShareDialogListener {
            void pressMenuById(int i, Object obj, Object obj2);
        }

        static class ViewHolder {
            LinearLayout container;
            ImageView image;
            TextView name;

            ViewHolder() {
            }
        }

        public static class commonShareItem {
            public Drawable background;
            public int index;
            public String name;
            public int textColor;

            public commonShareItem(String inName, int inIndex, Drawable inBackground, int inTextColor) {
                this.name = inName;
                this.index = inIndex;
                this.textColor = inTextColor;
                this.background = inBackground;
            }
        }

        public CommonShareDialogAdapter(Context context, CommonShareDialogListener listener, boolean nightmode) {
            this.mAdaptContext = context;
            this.mNightMode = nightmode;
            this.mCommonShareDialogListener = listener;
        }

        public void setData(List<Object> objects) {
            this.mObjects = objects;
        }

        public void setCurItem(Object inItem) {
            this.mCurItem = inItem;
        }

        public void setCurParam(Object inParam) {
            this.mCurParam = inParam;
        }

        public int getCount() {
            return this.mObjects == null ? 0 : this.mObjects.size();
        }

        public Object getItem(int position) {
            return this.mObjects.get(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(this.mAdaptContext).inflate(R.layout.layout_common_share_dialog_item, null);
                holder = new ViewHolder();
                holder.container = (LinearLayout) convertView.findViewById(R.id.rlySharePopDlgItem);
                holder.image = (ImageView) convertView.findViewById(R.id.ivSharePopDlgItemImage);
                holder.name = (TextView) convertView.findViewById(R.id.tvSharePopDlgItemName);
                holder.container.setBackgroundResource(this.mNightMode ? R.drawable.bg_btn_common_menu_night : R.drawable.bg_btn_common_menu_day);
                holder.name.setBackgroundResource(this.mNightMode ? R.drawable.bg_btn_common_menu_night : R.drawable.bg_btn_common_menu_day);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            onBindViewHolder(convertView, holder, (commonShareItem) getItem(position));
            return convertView;
        }

        public void onBindViewHolder(View view, ViewHolder holder, commonShareItem item) {
            holder.name.setText(item.name);
            holder.image.setTag(item);
            if (this.mNightMode) {
                holder.name.setTextColor(view.getResources().getColor(R.color.common_menu_dialog_unfocus_text_color_night));
            } else {
                holder.name.setTextColor(view.getResources().getColor(R.color.common_menu_dialog_unfocus_text_color_day));
            }
            if (item.textColor != 0) {
                holder.name.setTextColor(view.getResources().getColor(item.textColor));
            }
            holder.image.setImageDrawable(item.background);
            holder.image.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    commonShareItem _tmpItem = (commonShareItem) v.getTag();
                    if (CommonShareDialogAdapter.this.mCommonShareDialogListener != null) {
                        CommonShareDialogAdapter.this.mCommonShareDialogListener.pressMenuById(_tmpItem.index, CommonShareDialogAdapter.this.mCurItem, CommonShareDialogAdapter.this.mCurParam);
                    }
                }
            });
        }
    }

    public CommonShareDialog(Context context, ArrayList<Object> menuItemList, CommonShareDialogListener listener, boolean nightmode, int columnNum) {
        this.mContext = context;
        this.mColumnNum = columnNum;
        this.mNightMode = nightmode;
        this.mShareDialog = new DialogManager(this.mContext);
        this.mCommonShareDialogAdapter = new CommonShareDialogAdapter(this.mContext, listener, this.mNightMode);
        this.mMenuItemArrayList.addAll(menuItemList);
        this.mCommonShareDialogAdapter.setData(this.mMenuItemArrayList);
    }

    public void showMenu(Object item, String param) {
        View customView = LayoutInflater.from(this.mContext).inflate(R.layout.layout_common_share_dialog, null, false);
        this.mShareGirdView = (GridView) customView.findViewById(R.id.lvSharePopDlgItemList);
        this.mCommonShareDialogAdapter.setCurItem(item);
        this.mCommonShareDialogAdapter.setCurParam(param);
        this.mShareGirdView.setNumColumns(this.mColumnNum);
        this.mShareGirdView.setAdapter(this.mCommonShareDialogAdapter);
        this.mBtnCancel = (TextView) customView.findViewById(R.id.tvSharePopDlgBtnCancel);
        if (this.mNightMode) {
            this.mShareGirdView.setBackgroundColor(this.mContext.getResources().getColor(R.color.common_menu_dialog_divide_line_color_night01));
            this.mBtnCancel.setBackgroundResource(R.drawable.bg_btn_common_menu_night);
            this.mBtnCancel.setTextColor(this.mContext.getResources().getColor(R.color.common_menu_dialog_unfocus_text_color_night));
            customView.findViewById(R.id.tvSharePopDlgBtnCancelLine).setBackgroundResource(R.color.common_menu_dialog_divide_line_color_night02);
        } else {
            this.mShareGirdView.setBackgroundColor(this.mContext.getResources().getColor(R.color.common_menu_dialog_divide_line_color_day01));
            this.mBtnCancel.setBackgroundResource(R.drawable.bg_btn_common_menu_day);
            this.mBtnCancel.setTextColor(this.mContext.getResources().getColor(R.color.common_menu_dialog_unfocus_text_color_day));
            customView.findViewById(R.id.tvSharePopDlgBtnCancelLine).setBackgroundResource(R.color.common_menu_dialog_divide_line_color);
        }
        this.mBtnCancel.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CommonShareDialog.this.mShareDialog.dismissDialog();
            }
        });
        this.mShareDialog.showCustomViewPopupDialog(customView);
    }

    public void dismissDialog() {
        this.mShareDialog.dismissDialog();
    }
}
