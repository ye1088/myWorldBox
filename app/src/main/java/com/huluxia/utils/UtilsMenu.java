package com.huluxia.utils;

import android.content.Context;
import android.content.res.Resources;
import com.huluxia.bbs.b.d;
import com.huluxia.bbs.b.f;
import com.huluxia.bbs.b.m;
import com.huluxia.data.TagInfo;
import com.huluxia.data.j;
import com.huluxia.data.topic.CommentItem;
import com.huluxia.data.topic.TopicItem;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.CommonMenuDialogListener;
import com.huluxia.framework.base.widget.dialog.CommonMenuDialog.CommonMenuDialogAdapter.ResMenuItem;
import com.huluxia.widget.dialog.n;
import com.huluxia.widget.dialog.o;
import hlx.data.localstore.a;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class UtilsMenu {

    public enum MENU_VALUE {
        COMMENT,
        SHAREWIXIN,
        MOVETOPIC,
        EDITTOPIC,
        REMOVE_TOPIC,
        REPORT_TOPIC,
        LOCK_TOPIC,
        UNLOCK_TOPIC,
        REPLY,
        REPORT_COMMENT,
        REMOVE_COMMENT,
        VIEW_TOPIC,
        COPY_TEXT,
        SEND_HULU,
        AUTHENTICATE_TOPIC
    }

    public static n bk(Context context) {
        n dialogMenu = new n(context, "选择性别");
        List<o> menuManageList = new ArrayList();
        menuManageList.add(new o(Integer.valueOf(1), "女"));
        menuManageList.add(new o(Integer.valueOf(2), "男"));
        dialogMenu.setMenuItems(menuManageList);
        return dialogMenu;
    }

    public static n bl(Context context) {
        n dialogMenu = new n(context, a.bKA);
        List<o> menuManageList = new ArrayList();
        menuManageList.add(new o(MENU_VALUE.REPORT_TOPIC, "举报"));
        menuManageList.add(new o(MENU_VALUE.COPY_TEXT, "复制全文"));
        dialogMenu.setMenuItems(menuManageList);
        return dialogMenu;
    }

    public static n bm(Context context) {
        n dialogMenu = new n(context, a.bKA);
        List<o> menuManageList = new ArrayList();
        menuManageList.add(new o(MENU_VALUE.REPORT_COMMENT, "举报"));
        menuManageList.add(new o(MENU_VALUE.COPY_TEXT, "复制全文"));
        dialogMenu.setMenuItems(menuManageList);
        return dialogMenu;
    }

    public static n f(Context context, int totalPage, int curPage) {
        n dialogMenu = new n(context, "选择跳转分页");
        List<o> menuManageList = new ArrayList();
        for (int i = 0; i < totalPage; i++) {
            o item = new o(Integer.valueOf(i + 1), String.format(Locale.getDefault(), "第%d页", new Object[]{Integer.valueOf(i + 1)}));
            if (curPage == i + 1) {
                item.lV(f.ic_dropmenu_selector);
            }
            menuManageList.add(item);
        }
        dialogMenu.setMenuItems(menuManageList);
        dialogMenu.getListView().setSelection(curPage - 1);
        return dialogMenu;
    }

    public static n a(Context context, MENU_TOPIC_LIST curVal) {
        n dialogMenu = new n(context, "选择筛选条件");
        List<o> menuManageList = new ArrayList();
        menuManageList.add(new o(MENU_TOPIC_LIST.FILTER_MARROW, "只显示本版精华"));
        menuManageList.add(new o(MENU_TOPIC_LIST.FILTER_ACTIVE_TIME, "按回复时间排列"));
        menuManageList.add(new o(MENU_TOPIC_LIST.FILTER_CREATE_TIME, "按发布时间排列"));
        switch (curVal.ordinal()) {
            case 0:
                ((o) menuManageList.get(0)).lV(0);
                ((o) menuManageList.get(1)).lV(f.ic_dropmenu_selector);
                ((o) menuManageList.get(2)).lV(0);
                break;
            case 1:
                ((o) menuManageList.get(0)).lV(0);
                ((o) menuManageList.get(1)).lV(0);
                ((o) menuManageList.get(2)).lV(f.ic_dropmenu_selector);
                break;
            case 2:
                ((o) menuManageList.get(0)).lV(f.ic_dropmenu_selector);
                ((o) menuManageList.get(1)).lV(0);
                ((o) menuManageList.get(2)).lV(0);
                break;
        }
        dialogMenu.setMenuItems(menuManageList);
        return dialogMenu;
    }

    public static n c(Context context, boolean onlyPublic) {
        n dialogMenu = new n(context, "分享");
        List<o> menuManageList = new ArrayList();
        menuManageList.add(new o(MENU_SHARE_LIST.SHARE_QQZONE, "分享到QQ空间"));
        menuManageList.add(new o(MENU_SHARE_LIST.SHARE_QQ, "分享给QQ好友"));
        menuManageList.add(new o(MENU_SHARE_LIST.SHARE_WEIXIN, "分享给微信好友"));
        dialogMenu.setMenuItems(menuManageList);
        return dialogMenu;
    }

    public static n bn(Context context) {
        return c(context, false);
    }

    public static n a(Context context, ArrayList<String> urls) {
        n dialogMenu = new n(context, "下载");
        List<o> menuManageList = new ArrayList();
        Iterator it = urls.iterator();
        while (it.hasNext()) {
            String url = (String) it.next();
            menuManageList.add(new o(url, aw.W(url, 30)));
        }
        dialogMenu.setMenuItems(menuManageList);
        return dialogMenu;
    }

    public static n bo(Context context) {
        n dialogMenu = new n(context, "重试");
        List<o> menuManageList = new ArrayList();
        menuManageList.add(new o(MENU_DOWNLOAD_RETRY_LIST.RETRY_CHANGE, "更换下载地址"));
        menuManageList.add(new o(MENU_DOWNLOAD_RETRY_LIST.RETRY_DELETE, "删除下载任务"));
        menuManageList.add(new o(MENU_DOWNLOAD_RETRY_LIST.RETRY_CANCLE, a.bKB));
        dialogMenu.setMenuItems(menuManageList);
        return dialogMenu;
    }

    public static n bp(Context context) {
        n dialogMenu = new n(context, "选择");
        List<o> menuManageList = new ArrayList();
        menuManageList.add(new o(MENU_DOWNLOAD_TASK_LIST.TASK_VIEW, "查看详情"));
        menuManageList.add(new o(MENU_DOWNLOAD_TASK_LIST.TASK_DELETE, "删除任务"));
        dialogMenu.setMenuItems(menuManageList);
        return dialogMenu;
    }

    public static n b(Context context, TopicItem topicItem) {
        return a(context, topicItem, false);
    }

    public static n a(Context context, TopicItem topicItem, boolean isRes) {
        long topicUserId = topicItem.getUserInfo().getUserID();
        long sessionUserId = j.ep().getUserid();
        long sessionRole = (long) j.ep().getRole();
        boolean isModerator = false;
        n dialogMenu = new n(context, a.bKA);
        List<o> menuManageList = new ArrayList();
        if (!isRes) {
            menuManageList.add(new o(MENU_VALUE.COMMENT, "评论"));
        }
        if (topicUserId != sessionUserId) {
            menuManageList.add(new o(MENU_VALUE.SEND_HULU, "送葫芦"));
        }
        if (!(isRes || topicUserId == sessionUserId)) {
            menuManageList.add(new o(MENU_VALUE.REPORT_TOPIC, "举报"));
        }
        if (!isRes) {
            menuManageList.add(new o(MENU_VALUE.SHAREWIXIN, "分享到社交网络"));
        }
        if (topicItem.getCategory() != null) {
            isModerator = as.a(j.ep().getUserid(), topicItem.getCategory().getModerator());
        }
        if (!isRes && (sessionRole == 1 || isModerator)) {
            menuManageList.add(new o(MENU_VALUE.MOVETOPIC, "移动话题"));
            if (topicItem.getState() == 3) {
                menuManageList.add(new o(MENU_VALUE.UNLOCK_TOPIC, "解锁主题"));
            } else {
                menuManageList.add(new o(MENU_VALUE.LOCK_TOPIC, "锁定主题"));
            }
        }
        if (!isRes && (topicUserId == sessionUserId || sessionRole == 1 || isModerator)) {
            menuManageList.add(new o(MENU_VALUE.EDITTOPIC, "编辑话题"));
            menuManageList.add(new o(MENU_VALUE.REMOVE_TOPIC, "删除话题"));
        }
        menuManageList.add(new o(MENU_VALUE.COPY_TEXT, "复制全文"));
        dialogMenu.setMenuItems(menuManageList);
        return dialogMenu;
    }

    public static n a(Context context, TopicItem topicItem, CommentItem commentItem) {
        n dialogMenu = new n(context, a.bKA);
        List<o> menuManageList = new ArrayList();
        long topicUserId = topicItem.getUserInfo().getUserID();
        long commentUserId = commentItem.getUserInfo().getUserID();
        long sessionUserId = j.ep().getUserid();
        long sessionRole = (long) j.ep().getRole();
        boolean isModerator = false;
        if (topicItem.getCategory() != null) {
            isModerator = as.a(j.ep().getUserid(), topicItem.getCategory().getModerator());
        }
        if (commentUserId != sessionUserId) {
            menuManageList.add(new o(MENU_VALUE.REPLY, "回复"));
            menuManageList.add(new o(MENU_VALUE.REPORT_COMMENT, "举报"));
            menuManageList.add(new o(MENU_VALUE.SEND_HULU, "送葫芦"));
        }
        if (topicUserId == sessionUserId || commentUserId == sessionUserId || sessionRole == 1 || isModerator) {
            menuManageList.add(new o(MENU_VALUE.REMOVE_COMMENT, "删除"));
        }
        menuManageList.add(new o(MENU_VALUE.COPY_TEXT, "复制全文"));
        dialogMenu.setMenuItems(menuManageList);
        return dialogMenu;
    }

    public static n a(Context context, List<TagInfo> tags, long oriID) {
        n dialogMenu = new n(context, "标签");
        List<o> menuManageList = new ArrayList();
        for (TagInfo tag : tags) {
            o item = new o(Long.valueOf(tag.getID()), tag.getName());
            if (oriID == tag.getID()) {
                item.lV(f.ic_dropmenu_selector);
            } else {
                item.lV(0);
            }
            menuManageList.add(item);
        }
        dialogMenu.setMenuItems(menuManageList);
        return dialogMenu;
    }

    public static n c(Context context, List<TagInfo> tagList) {
        n dialogMenu = new n(context, "选择哪个标签?");
        List<o> menuManageList = new ArrayList();
        for (TagInfo tag : tagList) {
            if (0 != tag.getID()) {
                menuManageList.add(new o(Long.valueOf(tag.getID()), tag.getName()));
            }
        }
        dialogMenu.setMenuItems(menuManageList);
        return dialogMenu;
    }

    public static n a(Context context, CommentItem commentItem) {
        n dialogMenu = new n(context, a.bKA);
        List<o> menuManageList = new ArrayList();
        if (commentItem.getUserInfo().getUserID() != j.ep().getUserid()) {
            menuManageList.add(new o(MENU_VALUE.REPLY, "回复"));
            menuManageList.add(new o(MENU_VALUE.SEND_HULU, "回赠/赠送葫芦"));
            menuManageList.add(new o(MENU_VALUE.VIEW_TOPIC, "查看话题"));
        } else {
            menuManageList.add(new o(MENU_VALUE.VIEW_TOPIC, "查看话题"));
        }
        dialogMenu.setMenuItems(menuManageList);
        return dialogMenu;
    }

    public static n d(Context context, boolean isUserZone) {
        n dialogMenu = new n(context, "举报");
        List<o> menuManageList = new ArrayList();
        menuManageList.add(new o(COMPLAINT_VALUE.AD, "垃圾广告"));
        menuManageList.add(new o(COMPLAINT_VALUE.EROTIC, "色情消息"));
        if (!isUserZone) {
            menuManageList.add(new o(COMPLAINT_VALUE.NULLED, "无关内容"));
        }
        menuManageList.add(new o(COMPLAINT_VALUE.INSULT, "人身攻击"));
        dialogMenu.setMenuItems(menuManageList);
        return dialogMenu;
    }

    public static n bq(Context context) {
        n dialogMenu = new n(context, "请选择游戏版本");
        List<o> menuManageList = new ArrayList();
        o v105 = new o(MC_VER.VER105, "0.10.5 稳定版", 0);
        menuManageList.add(new o(MC_VER.VER110, "0.11.1 稳定版", 1));
        menuManageList.add(v105);
        dialogMenu.setMenuItems(menuManageList);
        dialogMenu.aL(d.green_tag, d.white);
        return dialogMenu;
    }

    public static n a(Context context, String name, boolean isOpen, boolean bShowDetail, int backupNum) {
        n dialogMenu = new n(context, name);
        List<o> menuManageList = new ArrayList();
        Resources res = context.getResources();
        if (isOpen) {
            if (bShowDetail) {
                menuManageList.add(new o(MENU_JS_LIST.JS_DETAIL, res.getString(m.MenuDetail)));
            }
            if (backupNum > 0) {
                menuManageList.add(new o(MENU_JS_LIST.JS_RECOVER, String.format("%s(有%d个备份)", new Object[]{res.getString(m.MenuRecover), Integer.valueOf(backupNum)})));
            }
            menuManageList.add(new o(MENU_JS_LIST.JS_RENAME, res.getString(m.MenuRename)));
            menuManageList.add(new o(MENU_JS_LIST.JS_BACKUP, res.getString(m.MenuBackupMap)));
            menuManageList.add(new o(MENU_JS_LIST.JS_EXPORT, res.getString(m.MenuExportMapToZip)));
        }
        menuManageList.add(new o(MENU_JS_LIST.JS_DELETE, res.getString(m.MenuDel)));
        dialogMenu.setMenuItems(menuManageList);
        return dialogMenu;
    }

    public static n a(Context context, String name, boolean isOpen, boolean bShowDetail) {
        n dialogMenu = new n(context, name);
        List<o> menuManageList = new ArrayList();
        Resources res = context.getResources();
        if (bShowDetail) {
            menuManageList.add(new o(MENU_JS_LIST.JS_DETAIL, res.getString(m.MenuDetail)));
        }
        if (isOpen) {
            menuManageList.add(new o(MENU_JS_LIST.JS_OPEN, res.getString(m.MenuOpen)));
        } else {
            menuManageList.add(new o(MENU_JS_LIST.JS_CLOSE, res.getString(m.MenuClose)));
        }
        menuManageList.add(new o(MENU_JS_LIST.JS_RENAME, res.getString(m.MenuRename)));
        menuManageList.add(new o(MENU_JS_LIST.JS_DELETE, res.getString(m.MenuDel)));
        dialogMenu.setMenuItems(menuManageList);
        return dialogMenu;
    }

    public static n b(Context context, String name, boolean isOpen, boolean bShowDetail) {
        return a(context, name, isOpen, bShowDetail);
    }

    public static n c(Context context, String name, boolean isOpen, boolean bShowDetail) {
        return a(context, name, isOpen, bShowDetail);
    }

    public static CommonMenuDialog a(Context context, TopicItem topicItem, boolean isRes, CommonMenuDialogListener menuListener) {
        long topicUserId = topicItem.getUserInfo().getUserID();
        long sessionUserId = j.ep().getUserid();
        long sessionRole = (long) j.ep().getRole();
        boolean isModerator = false;
        ArrayList<Object> mSortArrayList = new ArrayList();
        mSortArrayList.add(new ResMenuItem("评论", MENU_VALUE.COMMENT.ordinal(), d.locmgr_focus_btn_color));
        if (topicUserId != sessionUserId) {
            mSortArrayList.add(new ResMenuItem("送葫芦", MENU_VALUE.SEND_HULU.ordinal(), d.locmgr_focus_btn_color));
        }
        if (!(isRes || topicUserId == sessionUserId)) {
            mSortArrayList.add(new ResMenuItem("举报", MENU_VALUE.REPORT_TOPIC.ordinal(), d.locmgr_focus_btn_color));
        }
        if (!isRes) {
            mSortArrayList.add(new ResMenuItem("分享到社交网络", MENU_VALUE.SHAREWIXIN.ordinal(), d.locmgr_focus_btn_color));
        }
        if (topicItem.getCategory() != null) {
            isModerator = as.a(j.ep().getUserid(), topicItem.getCategory().getModerator());
        }
        if (!isRes && (sessionRole == 1 || isModerator)) {
            mSortArrayList.add(new ResMenuItem("移动话题", MENU_VALUE.MOVETOPIC.ordinal(), d.locmgr_focus_btn_color));
            if (topicItem.getState() == 3) {
                mSortArrayList.add(new ResMenuItem("解锁主题", MENU_VALUE.UNLOCK_TOPIC.ordinal(), d.locmgr_focus_btn_color));
            } else {
                mSortArrayList.add(new ResMenuItem("锁定主题", MENU_VALUE.LOCK_TOPIC.ordinal(), d.locmgr_focus_btn_color));
            }
        }
        if (!isRes && (topicUserId == sessionUserId || sessionRole == 1 || isModerator)) {
            mSortArrayList.add(new ResMenuItem("编辑话题", MENU_VALUE.EDITTOPIC.ordinal(), d.locmgr_focus_btn_color));
            mSortArrayList.add(new ResMenuItem("删除话题", MENU_VALUE.REMOVE_TOPIC.ordinal(), d.locmgr_focus_btn_color));
        }
        mSortArrayList.add(new ResMenuItem("复制全文", MENU_VALUE.COPY_TEXT.ordinal(), d.locmgr_focus_btn_color));
        if (!isRes && topicItem.getState() == 1 && (sessionRole == 1 || (isModerator && !topicItem.isAuthention()))) {
            mSortArrayList.add(new ResMenuItem(context.getResources().getString(topicItem.isAuthention() ? m.topic_unauth : m.topic_auth), MENU_VALUE.AUTHENTICATE_TOPIC.ordinal(), d.locmgr_focus_btn_color));
        }
        CommonMenuDialog dialogMenu = new CommonMenuDialog(context, menuListener, com.simple.colorful.d.RB(), mSortArrayList.size() >= 6 ? 2 : 1);
        dialogMenu.setMenuItems(mSortArrayList);
        return dialogMenu;
    }

    public static CommonMenuDialog a(Context context, TopicItem topicItem, CommentItem commentItem, CommonMenuDialogListener menuListener) {
        long topicUserId = topicItem.getUserInfo().getUserID();
        long commentUserId = commentItem.getUserInfo().getUserID();
        long sessionUserId = j.ep().getUserid();
        long sessionRole = (long) j.ep().getRole();
        boolean isModerator = false;
        if (topicItem.getCategory() != null) {
            isModerator = as.a(j.ep().getUserid(), topicItem.getCategory().getModerator());
        }
        ArrayList<Object> mSortArrayList = new ArrayList();
        if (commentUserId != sessionUserId) {
            mSortArrayList.add(new ResMenuItem("回复", MENU_VALUE.REPLY.ordinal(), d.locmgr_focus_btn_color));
            mSortArrayList.add(new ResMenuItem("举报", MENU_VALUE.REPORT_COMMENT.ordinal(), d.locmgr_focus_btn_color));
            mSortArrayList.add(new ResMenuItem("送葫芦", MENU_VALUE.SEND_HULU.ordinal(), d.locmgr_focus_btn_color));
        }
        if (topicUserId == sessionUserId || commentUserId == sessionUserId || sessionRole == 1 || isModerator) {
            mSortArrayList.add(new ResMenuItem("删除", MENU_VALUE.REMOVE_COMMENT.ordinal(), d.locmgr_focus_btn_color));
        }
        mSortArrayList.add(new ResMenuItem("复制全文", MENU_VALUE.COPY_TEXT.ordinal(), d.locmgr_focus_btn_color));
        CommonMenuDialog dialogMenu = new CommonMenuDialog(context, menuListener, com.simple.colorful.d.RB(), 1);
        dialogMenu.setMenuItems(mSortArrayList);
        return dialogMenu;
    }

    public static CommonMenuDialog a(Context context, boolean isUserZone, CommonMenuDialogListener menuListener) {
        ArrayList<Object> mSortArrayList = new ArrayList();
        mSortArrayList.add(new ResMenuItem("垃圾广告", COMPLAINT_VALUE.AD.Value(), d.locmgr_focus_btn_color));
        mSortArrayList.add(new ResMenuItem("色情消息", COMPLAINT_VALUE.EROTIC.Value(), d.locmgr_focus_btn_color));
        mSortArrayList.add(new ResMenuItem("人身攻击", COMPLAINT_VALUE.INSULT.Value(), d.locmgr_focus_btn_color));
        if (!isUserZone) {
            mSortArrayList.add(new ResMenuItem("无关内容", COMPLAINT_VALUE.NULLED.Value(), d.locmgr_focus_btn_color));
        }
        CommonMenuDialog dialogMenu = new CommonMenuDialog(context, menuListener, com.simple.colorful.d.RB(), 1);
        dialogMenu.setMenuItems(mSortArrayList);
        return dialogMenu;
    }

    public static CommonMenuDialog a(Context context, CommentItem commentItem, CommonMenuDialogListener menuListener) {
        long commentUserId = commentItem.getUserInfo().getUserID();
        long sessionUserId = j.ep().getUserid();
        ArrayList<Object> mSortArrayList = new ArrayList();
        if (commentUserId != sessionUserId) {
            mSortArrayList.add(new ResMenuItem("回复", MENU_VALUE.REPLY.ordinal(), d.locmgr_focus_btn_color));
            mSortArrayList.add(new ResMenuItem("回赠/赠送葫芦", MENU_VALUE.SEND_HULU.ordinal(), d.locmgr_focus_btn_color));
            mSortArrayList.add(new ResMenuItem("查看话题", MENU_VALUE.VIEW_TOPIC.ordinal(), d.locmgr_focus_btn_color));
        } else {
            mSortArrayList.add(new ResMenuItem("查看话题", MENU_VALUE.VIEW_TOPIC.ordinal(), d.locmgr_focus_btn_color));
        }
        CommonMenuDialog dialogMenu = new CommonMenuDialog(context, menuListener, com.simple.colorful.d.RB(), 1);
        dialogMenu.setMenuItems(mSortArrayList);
        return dialogMenu;
    }

    public static CommonMenuDialog a(Context context, CommonMenuDialogListener menuListener) {
        ArrayList<Object> mSortArrayList = new ArrayList();
        mSortArrayList.add(new ResMenuItem("分享到QQ空间", MENU_SHARE_LIST.SHARE_QQZONE.ordinal(), d.locmgr_focus_btn_color));
        mSortArrayList.add(new ResMenuItem("分享给QQ好友", MENU_SHARE_LIST.SHARE_QQ.ordinal(), d.locmgr_focus_btn_color));
        mSortArrayList.add(new ResMenuItem("分享给微信好友", MENU_SHARE_LIST.SHARE_WEIXIN.ordinal(), d.locmgr_focus_btn_color));
        CommonMenuDialog dialogMenu = new CommonMenuDialog(context, menuListener, com.simple.colorful.d.RB(), 1);
        dialogMenu.setMenuItems(mSortArrayList);
        return dialogMenu;
    }

    public static CommonMenuDialog b(Context context, CommonMenuDialogListener menuListener) {
        ArrayList<Object> mSortArrayList = new ArrayList();
        mSortArrayList.add(new ResMenuItem("保存图片", 0, d.download_normal_color));
        CommonMenuDialog menuDialog = new CommonMenuDialog(context, menuListener, com.simple.colorful.d.RB(), 1);
        menuDialog.setMenuItems(mSortArrayList);
        return menuDialog;
    }
}
