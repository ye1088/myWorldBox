package com.huluxia;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.huluxia.bbs.b.f;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.bbs.b.m;
import com.huluxia.data.TagInfo;
import com.huluxia.data.UserBaseInfo;
import com.huluxia.data.category.TopicCategory;
import com.huluxia.data.j;
import com.huluxia.data.message.MsgCounts;
import com.huluxia.data.profile.ExchangeRecord;
import com.huluxia.data.profile.GiftInfo;
import com.huluxia.data.profile.ProfileInfo;
import com.huluxia.data.profile.c;
import com.huluxia.data.topic.CommentItem;
import com.huluxia.data.topic.TopicItem;
import com.huluxia.framework.AppConfig;
import com.huluxia.framework.base.image.Config.NetFormat;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.framework.base.notification.EventNotifyCenter;
import com.huluxia.framework.base.utils.UtilUri;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.base.utils.UtilsVersion;
import com.huluxia.framework.base.widget.status.Statement.Size;
import com.huluxia.framework.base.widget.status.state.LoadingStatement;
import com.huluxia.framework.base.widget.status.state.ReloadStatement;
import com.huluxia.mctool.PlayerArchieveDetailActivity;
import com.huluxia.module.n;
import com.huluxia.module.picture.b;
import com.huluxia.module.profile.h;
import com.huluxia.ui.area.news.NewsCommentListActivity;
import com.huluxia.ui.area.news.NewsDetailActivity;
import com.huluxia.ui.base.WapActivity;
import com.huluxia.ui.base.a;
import com.huluxia.ui.bbs.CategoryDarenActivity;
import com.huluxia.ui.bbs.CategoryDetailActivity;
import com.huluxia.ui.bbs.CategoryVoteActivity;
import com.huluxia.ui.bbs.CommentTopicActivity;
import com.huluxia.ui.bbs.CreditListActivity;
import com.huluxia.ui.bbs.DarenRuleActivity;
import com.huluxia.ui.bbs.JsTopicDetailActivity;
import com.huluxia.ui.bbs.ModifyTopicActivity;
import com.huluxia.ui.bbs.MoveTopicActivity;
import com.huluxia.ui.bbs.PublishTopicActivity;
import com.huluxia.ui.bbs.PublishTopicWishActivity;
import com.huluxia.ui.bbs.ResTopicDetailActivity;
import com.huluxia.ui.bbs.SkinTopicDetailActivity;
import com.huluxia.ui.bbs.TopicDetailActivity;
import com.huluxia.ui.bbs.TopicListDrawerActivity;
import com.huluxia.ui.bbs.TopicSearchActivity;
import com.huluxia.ui.bbs.WoodTopicDetailActivity;
import com.huluxia.ui.bbs.addzone.ChooseZoneActivity;
import com.huluxia.ui.discovery.CardGameActivity;
import com.huluxia.ui.discovery.MsgSettingsActivity;
import com.huluxia.ui.discovery.SettingsActivity;
import com.huluxia.ui.loginAndRegister.ForgetPasswordActivity;
import com.huluxia.ui.loginAndRegister.LoginActivity;
import com.huluxia.ui.loginAndRegister.PolicyActivity;
import com.huluxia.ui.loginAndRegister.RegisterActivity;
import com.huluxia.ui.loginAndRegister.RegisterByMiActivity;
import com.huluxia.ui.other.FeedbackActivity;
import com.huluxia.ui.picture.PhotoViewerActivity;
import com.huluxia.ui.picture.PictureChooserActivity;
import com.huluxia.ui.picture.PictureCropActivity;
import com.huluxia.ui.profile.AchieveActivity;
import com.huluxia.ui.profile.FollowerListActivity;
import com.huluxia.ui.profile.FollowingListActivity;
import com.huluxia.ui.profile.FriendListActivity;
import com.huluxia.ui.profile.MessageHistoryActivity;
import com.huluxia.ui.profile.ProfileActivity;
import com.huluxia.ui.profile.ProfileCommentListActivity;
import com.huluxia.ui.profile.ProfileEditActivity;
import com.huluxia.ui.profile.ProfileExchangeCenterActivity;
import com.huluxia.ui.profile.ProfileExchangeDetailActivity;
import com.huluxia.ui.profile.ProfileExchangeRecordActivity;
import com.huluxia.ui.profile.ProfileExchangeSubmitActivity;
import com.huluxia.ui.profile.ProfileFavorActivity;
import com.huluxia.ui.profile.ProfileReadTopicListActivity;
import com.huluxia.ui.profile.ProfileScoreActivity;
import com.huluxia.ui.profile.ProfileTopicListActivity;
import com.huluxia.ui.profile.SpaceStyleActivity;
import com.huluxia.ui.profile.SpaceStyleDetailActivity;
import com.huluxia.ui.profile.StudioAuditActivity;
import com.huluxia.ui.profile.StudioEditActivity;
import com.huluxia.ui.profile.UploadPhotoActivity;
import com.huluxia.ui.profile.UserAlbumsActivity;
import com.huluxia.utils.UtilsEnumBiz;
import com.huluxia.utils.UtilsFile;
import com.huluxia.widget.Constants;
import com.huluxia.widget.x5web.BrowserActivity;
import com.simple.colorful.d;
import hlx.ui.userguide.UserGuideActivity;
import hlx.ui.userguide.UserGuideListActivity;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.apache.http.cookie.ClientCookie;
import org.bytedeco.javacpp.avformat;

/* compiled from: UIHelper */
public class t {
    private static Toast lL;

    public static boolean isTopActivity(Context c) {
        String packageName = c.getPackageName();
        List<RunningTaskInfo> tasksInfo = ((ActivityManager) c.getSystemService("activity")).getRunningTasks(1);
        if (tasksInfo.size() <= 0 || !packageName.equals(((RunningTaskInfo) tasksInfo.get(0)).topActivity.getPackageName())) {
            return false;
        }
        return true;
    }

    public static void l(Context context, String msg) {
        a(context, msg, 0, 17);
    }

    public static void m(Context context, String msg) {
        b(context, msg, 0, 150);
    }

    public static void n(Context context, String msg) {
        a(context, msg, f.g_icon_error, 17);
    }

    public static void o(Context context, String msg) {
        a(context, msg, f.g_icon_succes, 17);
    }

    public static void p(Context context, String msg) {
        a(context, msg, 0, 48);
    }

    public static void f(String msg, int color) {
        c(msg, 0, color);
    }

    private static void c(String msg, int iconResid, int color) {
        Context context = AppConfig.getInstance().getAppContext();
        View view = LayoutInflater.from(context).inflate(i.toast_view, null);
        TextView toast_msg = (TextView) view.findViewById(g.toast_msg);
        toast_msg.setTextColor(color);
        ImageView toast_icon = (ImageView) view.findViewById(g.toast_icon);
        if (iconResid > 0) {
            toast_icon.setVisibility(0);
            toast_icon.setImageResource(iconResid);
        }
        toast_msg.setText(msg);
        if (lL == null) {
            lL = new Toast(context);
        }
        lL.setGravity(17, 0, 0);
        lL.setDuration(0);
        lL.setView(view);
        lL.show();
    }

    private static void a(Context context, String msg, int iconResid, int pos) {
        if (context != null) {
            View view = LayoutInflater.from(context).inflate(i.toast_view, null);
            TextView toast_msg = (TextView) view.findViewById(g.toast_msg);
            ImageView toast_icon = (ImageView) view.findViewById(g.toast_icon);
            if (iconResid > 0) {
                toast_icon.setVisibility(0);
                toast_icon.setImageResource(iconResid);
            }
            toast_msg.setText(msg);
            if (lL == null) {
                lL = new Toast(context);
            }
            lL.setGravity(17, 0, 0);
            lL.setDuration(0);
            lL.setView(view);
            lL.show();
        }
    }

    private static void b(Context context, String msg, int iconResid, int pos) {
        if (context != null) {
            View view = LayoutInflater.from(context).inflate(i.toast_view, null);
            TextView toast_msg = (TextView) view.findViewById(g.toast_msg);
            ImageView toast_icon = (ImageView) view.findViewById(g.toast_icon);
            if (iconResid > 0) {
                toast_icon.setVisibility(0);
                toast_icon.setImageResource(iconResid);
            }
            toast_msg.setText(msg);
            if (lL == null) {
                lL = new Toast(context);
            }
            lL.setGravity(80, 0, 0);
            lL.setDuration(0);
            lL.setView(view);
            lL.show();
        }
    }

    public static void af(Context context) {
        a.Ft().bd(context);
    }

    public static void ag(Context context) {
        e(context, 0);
    }

    public static void e(Context context, int idx) {
        Intent in = new Intent();
        in.putExtra("currentIdx", idx);
        in.addFlags(avformat.AVFMT_SEEK_TO_PTS);
        in.setComponent(new ComponentName(context, "hlx.home.main.HomeActivity"));
        context.startActivity(in);
    }

    public static void ah(Context context) {
        Intent in = new Intent();
        in.putExtra("currentIdx", 0);
        in.addFlags(268435456);
        in.setComponent(new ComponentName(context, "hlx.home.main.HomeActivity"));
        context.startActivity(in);
    }

    public static int dipToPx(Context context, int dip) {
        return (int) ((((float) dip) * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static void a(PaintView imageView, String url, float radius) {
        imageView.setUri(UtilUri.getUriOrNull(url), NetFormat.FORMAT_160).radius(radius).placeHolder(d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal).setImageLoader(l.cb().getImageLoader());
    }

    public static void b(PaintView imageView, String url, float radius) {
        imageView.setUri(UtilUri.getUriOrNull(url)).radius(radius).placeHolder(d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal).setImageLoader(l.cb().getImageLoader());
    }

    public static void a(PaintView imageView, String url, int width, int height) {
        imageView.setUri(UtilUri.getUriOrNull(String.format("%s_%dx%d.jpeg", new Object[]{url, Integer.valueOf(width), Integer.valueOf(height)}))).placeHolder(d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal).setImageLoader(l.cb().getImageLoader());
    }

    public static void q(Context context, String url) {
        try {
            context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
        } catch (ActivityNotFoundException e) {
        }
    }

    public static void ai(Context context) {
        Intent in = new Intent();
        in.setClass(context, CategoryVoteActivity.class);
        context.startActivity(in);
    }

    public static void aj(Context context) {
        Intent in = new Intent();
        in.setClass(context, FeedbackActivity.class);
        context.startActivity(in);
    }

    public static void U(String strApkFile) {
        if (strApkFile != null && strApkFile.length() != 0 && UtilsFile.isExist(strApkFile)) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setFlags(268435456);
            intent.setDataAndType(Uri.fromFile(new File(strApkFile)), "application/vnd.android.package-archive");
            HTApplication.getAppContext().startActivity(intent);
        }
    }

    public static void V(String packageName) {
        HTApplication.getAppContext().startActivity(new Intent("android.intent.action.DELETE", Uri.parse("package:" + packageName)));
    }

    public static void a(String strName, UtilsEnumBiz biz) {
        if (UtilsEnumBiz.isTool(biz) || strName.equals("com.huluxia.dota") || strName.equals("com.huluxia.bb") || strName.equals("com.huluxia.pao") || strName.equals("com.huluxia.hlxtoola") || strName.equals("com.huluxia.hlxtoolb") || strName.equals("com.huluxia.hlxtoolc")) {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(strName, strName + ".ui.MainActivity.AppStart"));
            intent.putExtra("activityID", 1);
            intent.setFlags(268435456);
            HTApplication.getAppContext().startActivity(intent);
            return;
        }
        try {
            intent = HTApplication.getAppContext().getPackageManager().getLaunchIntentForPackage(strName);
            intent.setFlags(268435456);
            HTApplication.getAppContext().startActivity(intent);
        } catch (Exception e) {
        }
    }

    public static void f(Context context, String url, String title) {
        a(context, url, title, true);
    }

    public static void a(Context context, String url, String title, boolean useWideView) {
        Intent intent;
        if (UtilsVersion.hasNouga()) {
            intent = new Intent(context, WapActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("url", url);
        } else {
            intent = new Intent(context, BrowserActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("url", url);
        }
        context.startActivity(intent);
    }

    public static void a(Context context, TopicCategory obj) {
        Intent in = new Intent();
        in.putExtra("category", obj);
        in.setClass(context, TopicListDrawerActivity.class);
        context.startActivity(in);
    }

    public static void a(Activity context, long postId, boolean isTopic) {
        Intent in = new Intent();
        in.putExtra("post_id", postId);
        in.putExtra("isTopic", isTopic);
        in.setClass(context, CreditListActivity.class);
        context.startActivity(in);
    }

    public static void c(Activity context, int flag) {
        Intent in = new Intent();
        in.setClass(context, RegisterActivity.class);
        in.putExtra("flag", flag);
        context.startActivityForResult(in, 1);
    }

    public static void ak(Context context) {
        Intent in = new Intent();
        in.setClass(context, ForgetPasswordActivity.class);
        context.startActivity(in);
    }

    public static void a(Activity context, long cat_id, List<TagInfo> tagList, int video_power) {
        if (j.ep().ey()) {
            Intent in = new Intent();
            in.putExtra("cat_id", cat_id);
            in.putExtra("video_power", video_power);
            Bundle bu = new Bundle();
            bu.putParcelableArrayList("taglist", (ArrayList) tagList);
            in.putExtras(bu);
            in.setClass(context, PublishTopicActivity.class);
            context.startActivityForResult(in, 1);
            return;
        }
        an(context);
    }

    public static void a(Activity context, long cat_id, long tag_id) {
        if (j.ep().ey()) {
            Intent in = new Intent();
            in.putExtra("cat_id", cat_id);
            in.putExtra("tag_id", tag_id);
            in.setClass(context, PublishTopicWishActivity.class);
            context.startActivityForResult(in, 1);
            return;
        }
        an(context);
    }

    public static void a(Activity context, long postID, int requestCode) {
        Intent in = new Intent();
        in.putExtra(TopicDetailActivity.aOM, postID);
        in.setClass(context, TopicDetailActivity.class);
        context.startActivityForResult(in, requestCode);
    }

    public static void a(Context context, TopicItem obj) {
        a(context, obj, 0);
    }

    public static void a(Context context, TopicItem obj, long catId) {
        if (obj != null) {
            if (obj.ext == null || obj.ext.biz == null || obj.ext.subBiz == null || !obj.ext.biz.equals("mc")) {
                Intent in = new Intent();
                in.putExtra(r.gO, obj);
                in.putExtra("cat_id", catId);
                in.setClass(context, TopicDetailActivity.class);
                context.startActivity(in);
                if (obj == null || obj.getCategory() == null) {
                    r.ck().k(0);
                    return;
                } else {
                    r.ck().k(obj.getCategory().getCategoryID());
                    return;
                }
            }
            long mapId = obj.ext.id;
            if (obj.ext.subBiz.equalsIgnoreCase("pam")) {
                EventNotifyCenter.notifyEvent(n.class, n.axn, context, Long.valueOf(mapId), Long.valueOf(obj.postID));
            } else if (obj.ext.subBiz.equalsIgnoreCase("js")) {
                a(context, mapId, obj.postID, obj.tagid);
            } else if (obj.ext.subBiz.equalsIgnoreCase("skin")) {
                b(context, mapId, obj.postID, obj.tagid);
            } else if (obj.ext.subBiz.equalsIgnoreCase("wood")) {
                c(context, mapId, obj.postID, obj.tagid);
            } else {
                a(context, mapId, obj.postID, obj.tagid, catId);
            }
        }
    }

    public static void a(Context context, long postID, boolean isFromNotication) {
        Intent in = new Intent();
        in.putExtra(TopicDetailActivity.aOM, postID);
        if (isFromNotication) {
            in.putExtra(Constants.brP, true);
        }
        in.setFlags(268435456);
        in.setClass(context, TopicDetailActivity.class);
        context.startActivity(in);
        r.ck().k(0);
    }

    public static void a(Context context, com.huluxia.data.map.f.a obj) {
        Intent in = new Intent();
        in.putExtra(r.gO, obj);
        in.setClass(context, ResTopicDetailActivity.class);
        context.startActivity(in);
    }

    public static void b(Context context, long mapId, long postId) {
        a(context, mapId, postId, "default");
    }

    public static void a(Context context, long mapId, long postId, boolean isFromNotication) {
        Intent in = new Intent();
        if (isFromNotication) {
            in.setFlags(268435456);
        }
        in.putExtra("mapId", mapId);
        in.putExtra("postId", postId);
        in.putExtra("tagId", 0);
        in.putExtra("src", "default");
        in.putExtra("catId", 0);
        in.setClass(context, ResTopicDetailActivity.class);
        context.startActivity(in);
    }

    public static void a(Context context, long mapId, long postId, String src) {
        a(context, mapId, postId, 0, src, 0);
    }

    public static void a(Context context, long mapId, long postId, long tagId, long catId) {
        a(context, mapId, postId, tagId, "default", catId);
    }

    public static void a(Context context, long mapId, long postId, long tagId, String src, long catId) {
        Intent in = new Intent();
        in.putExtra("mapId", mapId);
        in.putExtra("postId", postId);
        in.putExtra("tagId", tagId);
        in.putExtra("src", src);
        in.putExtra("catId", catId);
        in.setClass(context, ResTopicDetailActivity.class);
        context.startActivity(in);
    }

    public static void b(Context context, com.huluxia.data.map.f.a obj) {
        Intent in = new Intent();
        in.putExtra(r.gO, obj);
        in.setClass(context, JsTopicDetailActivity.class);
        context.startActivity(in);
    }

    public static void c(Context context, long mapId, long postId) {
        a(context, mapId, postId, 0);
    }

    public static void a(Context context, long mapId, long postId, long tagId) {
        Intent in = new Intent();
        in.putExtra("mapId", mapId);
        in.putExtra("postId", postId);
        in.putExtra("tagId", tagId);
        in.setClass(context, JsTopicDetailActivity.class);
        context.startActivity(in);
    }

    public static void c(Context context, com.huluxia.data.map.f.a obj) {
        Intent in = new Intent();
        in.putExtra(r.gO, obj);
        in.setClass(context, SkinTopicDetailActivity.class);
        context.startActivity(in);
    }

    public static void d(Context context, long mapId, long postId) {
        b(context, mapId, postId, 0);
    }

    public static void b(Context context, long mapId, long postId, long tagId) {
        Intent in = new Intent();
        in.putExtra("mapId", mapId);
        in.putExtra("postId", postId);
        in.putExtra("tagId", tagId);
        in.setClass(context, SkinTopicDetailActivity.class);
        context.startActivity(in);
    }

    public static void d(Context context, com.huluxia.data.map.f.a obj) {
        Intent in = new Intent();
        in.putExtra(r.gO, obj);
        in.setClass(context, WoodTopicDetailActivity.class);
        context.startActivity(in);
    }

    public static void e(Context context, long mapId, long postId) {
        c(context, mapId, postId, 0);
    }

    public static void c(Context context, long mapId, long postId, long tagId) {
        Intent in = new Intent();
        in.putExtra("mapId", mapId);
        in.putExtra("postId", postId);
        in.putExtra("tagId", tagId);
        in.setClass(context, WoodTopicDetailActivity.class);
        context.startActivity(in);
    }

    public static void c(Context context, long cat_id) {
        Intent in = new Intent();
        in.putExtra("cat_id", cat_id);
        in.setClass(context, CategoryDetailActivity.class);
        context.startActivity(in);
    }

    public static void d(Context context, long cat_id) {
        Intent in = new Intent();
        in.putExtra("cat_id", cat_id);
        in.setClass(context, CategoryDarenActivity.class);
        context.startActivity(in);
    }

    public static void al(Context context) {
        context.startActivity(new Intent(context, DarenRuleActivity.class));
    }

    public static void d(Activity context, int flag) {
        Intent in = new Intent();
        in.setClass(context, LoginActivity.class);
        in.putExtra("flag", flag);
        context.startActivityForResult(in, 1);
    }

    public static void b(Activity context, int reqCode, int rspCode) {
        Intent in = new Intent();
        in.setClass(context, LoginActivity.class);
        in.putExtra("flag", rspCode);
        context.startActivityForResult(in, reqCode);
    }

    public static void e(Context context, long userid) {
    }

    public static void a(Context context, long userid, UserBaseInfo info) {
        if (j.ep().ey()) {
            Intent in = new Intent();
            in.putExtra(ProfileActivity.EXTRA_USER_ID, userid);
            in.putExtra(ProfileActivity.bfF, info);
            in.setClass(context, ProfileActivity.class);
            context.startActivity(in);
            return;
        }
        an(context);
    }

    public static void am(Context context) {
        Intent in = new Intent();
        in.setClass(context, LoginActivity.class);
        context.startActivity(in);
    }

    public static void an(Context context) {
        Intent in = new Intent();
        in.setClass(context, LoginActivity.class);
        context.startActivity(in);
    }

    public static void a(Activity context, TopicItem obj) {
        if (j.ep().ey()) {
            Intent in = new Intent();
            in.putExtra(r.gO, obj);
            in.setClass(context, MoveTopicActivity.class);
            context.startActivityForResult(in, 1);
            return;
        }
        an(context);
    }

    public static void b(Activity context, TopicItem obj) {
        if (j.ep().ey()) {
            Intent in = new Intent();
            in.putExtra(r.gO, obj);
            in.setClass(context, ModifyTopicActivity.class);
            context.startActivityForResult(in, 1);
            return;
        }
        an(context);
    }

    public static void a(Activity context, TopicItem obj, long cat_id) {
        if (j.ep().ey()) {
            Intent in = new Intent();
            in.putExtra("cat_id", cat_id);
            in.putExtra(r.gO, obj);
            in.setClass(context, ModifyTopicActivity.class);
            context.startActivityForResult(in, 1);
            return;
        }
        an(context);
    }

    public static void a(Activity context, TopicItem topicItem, ArrayList<UserBaseInfo> userBaseInfos) {
        if (j.ep().ey()) {
            Intent in = new Intent();
            in.putExtra(r.gO, topicItem);
            in.putExtra("EXTRA_CURRENT_SELECTED", userBaseInfos);
            in.putExtra(FriendListActivity.aLs, userBaseInfos);
            if (topicItem != null) {
                in.putExtra(ModifyTopicActivity.aKv, topicItem.getUserInfo());
            }
            in.setClass(context, ModifyTopicActivity.class);
            context.startActivityForResult(in, 1);
            return;
        }
        an(context);
    }

    public static void a(Activity context, TopicItem obj, UserBaseInfo userInfo) {
        if (j.ep().ey()) {
            Intent in = new Intent();
            in.putExtra(r.gO, obj);
            in.putExtra("user", userInfo);
            in.setClass(context, CommentTopicActivity.class);
            context.startActivityForResult(in, 1);
            return;
        }
        an(context);
    }

    public static void a(Activity context, TopicItem obj, CommentItem commentObj, boolean needResult) {
        Intent in = new Intent();
        in.putExtra(r.gO, obj);
        in.putExtra(ClientCookie.COMMENT_ATTR, commentObj);
        in.setClass(context, CommentTopicActivity.class);
        if (needResult) {
            context.startActivityForResult(in, 1);
        } else {
            context.startActivity(in);
        }
    }

    public static void a(Activity context, long userId, ArrayList<b> photos, boolean isOther, int requestCode) {
        if (j.ep().ey()) {
            Intent in = new Intent();
            in.putParcelableArrayListExtra("photos", photos);
            in.putExtra("userId", userId);
            in.putExtra("isOther", isOther);
            in.setClass(context, UploadPhotoActivity.class);
            context.startActivityForResult(in, requestCode);
            return;
        }
        an(context);
    }

    public static void a(Context context, ProfileInfo profileInfo) {
        if (j.ep().ey()) {
            Intent in = new Intent();
            in.putExtra("profileInfo", profileInfo);
            in.setClass(context, ProfileEditActivity.class);
            context.startActivity(in);
            return;
        }
        an(context);
    }

    public static void f(Context context, long userID) {
        if (j.ep().ey()) {
            Intent in = new Intent();
            in.putExtra(FriendListActivity.EXTRA_USER_ID, userID);
            in.setClass(context, ProfileTopicListActivity.class);
            context.startActivity(in);
            return;
        }
        an(context);
    }

    public static void g(Context context, long userID) {
        if (j.ep().ey()) {
            Intent in = new Intent();
            in.putExtra(FriendListActivity.EXTRA_USER_ID, userID);
            in.setClass(context, ProfileCommentListActivity.class);
            context.startActivity(in);
            return;
        }
        an(context);
    }

    public static void h(Context context, long userID) {
        if (j.ep().ey()) {
            Intent in = new Intent();
            in.putExtra(FriendListActivity.EXTRA_USER_ID, userID);
            in.setClass(context, ProfileFavorActivity.class);
            context.startActivity(in);
            return;
        }
        an(context);
    }

    public static void i(Context context, long userID) {
        if (j.ep().ey()) {
            Intent in = new Intent();
            in.putExtra(FriendListActivity.EXTRA_USER_ID, userID);
            in.setClass(context, FollowingListActivity.class);
            context.startActivity(in);
            return;
        }
        an(context);
    }

    public static void j(Context context, long userID) {
        if (j.ep().ey()) {
            Intent in = new Intent();
            in.putExtra(FriendListActivity.EXTRA_USER_ID, userID);
            in.setClass(context, FollowerListActivity.class);
            context.startActivity(in);
            return;
        }
        an(context);
    }

    public static void a(Activity context, long userID, ArrayList<UserBaseInfo> remindUsers, ArrayList<UserBaseInfo> oriRemindUsers) {
        if (j.ep().ey()) {
            Intent in = new Intent();
            in.putExtra(FriendListActivity.EXTRA_USER_ID, userID);
            in.putExtra("EXTRA_CURRENT_SELECTED", remindUsers);
            in.putExtra(FriendListActivity.aLs, oriRemindUsers);
            in.setClass(context, FriendListActivity.class);
            context.startActivityForResult(in, 532);
            return;
        }
        an(context);
    }

    public static void ao(Context context) {
        Intent in = new Intent();
        in.setClass(context, SettingsActivity.class);
        context.startActivity(in);
    }

    public static void f(Context context, int curIdx) {
        Intent in = new Intent();
        in.putExtra("curIdx", curIdx);
        in.setClass(context, ProfileExchangeCenterActivity.class);
        context.startActivity(in);
    }

    public static void ap(Context context) {
        context.startActivity(new Intent(context, ProfileExchangeRecordActivity.class));
    }

    public static void a(Context context, ExchangeRecord record) {
        Intent intent = new Intent(context, ProfileExchangeDetailActivity.class);
        intent.putExtra(ProfileExchangeDetailActivity.bgh, record);
        context.startActivity(intent);
    }

    public static void a(Context context, GiftInfo info, long userCredits) {
        Intent intent = new Intent(context, ProfileExchangeSubmitActivity.class);
        intent.putExtra(ProfileExchangeSubmitActivity.bgp, info);
        intent.putExtra(ProfileExchangeSubmitActivity.bgq, userCredits);
        context.startActivity(intent);
    }

    public static void aq(Context context) {
        Intent in = new Intent();
        in.setClass(context, PolicyActivity.class);
        context.startActivity(in);
    }

    public static void a(Context context, MsgCounts msgCounts) {
        a(context, msgCounts, false);
    }

    public static void a(Context context, MsgCounts msgCounts, boolean isNewTask) {
        if (j.ep().ey()) {
            Intent in = new Intent();
            in.putExtra("msgCounts", msgCounts);
            if (isNewTask) {
                in.setFlags(268435456);
            }
            in.setClass(context, MessageHistoryActivity.class);
            context.startActivity(in);
            return;
        }
        an(context);
    }

    public static void b(Context context, TopicCategory obj) {
        if (j.ep().ey()) {
            Intent in = new Intent();
            in.putExtra("category", obj);
            in.setClass(context, TopicSearchActivity.class);
            context.startActivity(in);
            return;
        }
        an(context);
    }

    public static void a(Context context, ProfileInfo info, int flag) {
        if (!j.ep().ey()) {
            an(context);
        } else if (info != null) {
            Intent in = new Intent(context, ProfileScoreActivity.class);
            in.putExtra(ProfileScoreActivity.bhf, info);
            in.putExtra("flag", flag);
            context.startActivity(in);
        }
    }

    public static void a(Context c, Class<?> cls) {
        Intent intent = new Intent();
        intent.setFlags(268435456);
        intent.setClass(c, cls);
        c.startActivity(intent);
    }

    public static void a(Activity context, long uid, String nick, int flag) {
        Intent in = new Intent();
        in.setClass(context, RegisterByMiActivity.class);
        in.putExtra("miUid", uid);
        in.putExtra("miNick", nick);
        in.putExtra("flag", flag);
        context.startActivityForResult(in, 1);
    }

    public static void a(Activity context, String urlText) {
        context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(urlText)));
    }

    public static void ar(Context context) {
        context.startActivity(new Intent(context, CardGameActivity.class));
    }

    public static void a(Context context, int detailIndex, int value, ProfileInfo info) {
        Intent in = new Intent(context, PlayerArchieveDetailActivity.class);
        in.putExtra("detailIndex", detailIndex);
        in.putExtra("ext_info", info);
        in.putExtra("value", value);
        context.startActivity(in);
    }

    public static void a(Activity context, int reqCode, int rspCode, String openId, String qqToken) {
        Intent in = new Intent();
        in.setClass(context, RegisterActivity.class);
        in.putExtra("flag", rspCode);
        in.putExtra("open_id", openId);
        in.putExtra("qq_token", qqToken);
        context.startActivityForResult(in, reqCode);
    }

    public static void a(Context context, int index, ProfileInfo profileInfo, boolean fromHome) {
        if (profileInfo != null) {
            Intent intent = new Intent(context, SpaceStyleActivity.class);
            intent.putExtra(SpaceStyleActivity.bhA, index);
            intent.putExtra(SpaceStyleActivity.bhB, profileInfo);
            intent.putExtra("EXTRA_FROM_HOME", fromHome);
            context.startActivity(intent);
        }
    }

    public static void a(Context context, ArrayList<String> urls, int index) {
        ArrayList thumnails = new ArrayList();
        if (UtilsFunction.notEmpty((Collection) urls)) {
            Iterator it = urls.iterator();
            while (it.hasNext()) {
                if (UtilsFunction.notEmpty((String) it.next())) {
                    thumnails.add(String.format("%s_160x160.jpeg", new Object[]{(String) it.next()}));
                } else {
                    thumnails.add("");
                }
            }
        }
        a(context, (ArrayList) urls, thumnails, index, 1);
    }

    public static void a(Context context, ArrayList<String> urls, ArrayList<String> thumbnails, int index, int orientation) {
        Intent intent = new Intent(context, PhotoViewerActivity.class);
        intent.putStringArrayListExtra(PhotoViewerActivity.bdr, urls);
        intent.putStringArrayListExtra(PhotoViewerActivity.bds, thumbnails);
        intent.putExtra(PhotoViewerActivity.bdt, orientation);
        intent.putExtra(PhotoViewerActivity.bdu, index);
        context.startActivity(intent);
    }

    public static void a(Context context, ProfileInfo info, boolean isOther) {
        if (j.ep().ey()) {
            Intent in = new Intent();
            in.putParcelableArrayListExtra("photos", (ArrayList) info.getPhotos());
            in.putExtra("userId", info.getUserID());
            in.putExtra("isOther", isOther);
            in.setClass(context, UserAlbumsActivity.class);
            context.startActivity(in);
            return;
        }
        an(context);
    }

    public static void as(Context context) {
        Intent in = new Intent();
        in.setClass(context, ProfileReadTopicListActivity.class);
        context.startActivity(in);
    }

    public static void b(Context context, int currentIdx, boolean fromFloat) {
    }

    public static void b(Context context, ProfileInfo profileInfo) {
        if (!j.ep().ey()) {
            an(context);
        } else if (profileInfo != null) {
            Intent in = new Intent();
            Bundle bundle = new Bundle();
            bundle.putParcelable("profileInfo", profileInfo);
            in.putExtras(bundle);
            in.setClass(context, AchieveActivity.class);
            context.startActivity(in);
        }
    }

    public static void i(Activity activity) {
        activity.startActivity(new Intent(activity, ChooseZoneActivity.class));
    }

    public static LoadingStatement dz() {
        LoadingStatement statement = new LoadingStatement();
        statement.gerneralImgSize = new Size(30, 30);
        if (d.isDayMode()) {
            statement.generalImg = f.ic_loading_default;
            statement.background = com.huluxia.bbs.b.d.background_normal;
        } else {
            statement.generalImg = f.ic_loading_default;
            statement.background = com.huluxia.bbs.b.d.color_split_dim_night;
        }
        return statement;
    }

    public static ReloadStatement dA() {
        ReloadStatement statement = new ReloadStatement();
        statement.generalSubtitle = m.loading_failed;
        statement.generalSubtitleSize = 16;
        if (d.isDayMode()) {
            statement.generalImg = f.ic_loading_failed;
            statement.background = com.huluxia.bbs.b.d.background_normal;
            statement.generalSubtitleColor = com.huluxia.bbs.b.d.text_color_secondary;
        } else {
            statement.generalImg = f.ic_loading_failed_night;
            statement.background = com.huluxia.bbs.b.d.color_split_dim_night;
            statement.generalSubtitleColor = com.huluxia.bbs.b.d.text_color_secondary_night;
        }
        return statement;
    }

    public static void a(Fragment fragment, int requestCode, int maxSelected, ArrayList<b> mData) {
        Intent in = new Intent();
        in.setClass(fragment.getActivity(), PictureChooserActivity.class);
        in.setAction(PictureChooserActivity.bdF);
        in.putExtra(PictureChooserActivity.bdE, true);
        in.putExtra(PictureChooserActivity.bdD, maxSelected);
        in.putExtra("EXTRA_CURRENT_SELECTED", mData);
        fragment.startActivityForResult(in, requestCode);
    }

    public static void a(Activity context, int requestCode, ArrayList<b> photos, int outputX, int outputY, int position) {
        Intent intent = new Intent(context, PictureCropActivity.class);
        intent.putExtra("EXTRA_PHOTOS", photos);
        intent.putExtra(PictureCropActivity.bej, outputX);
        intent.putExtra(PictureCropActivity.bek, outputY);
        intent.putExtra(PictureCropActivity.ben, position);
        context.startActivityForResult(intent, requestCode);
    }

    public static void a(Activity context, int requestCode, int maxSelected, ArrayList<b> mData) {
        Intent in = new Intent();
        in.setClass(context, PictureChooserActivity.class);
        in.setAction(PictureChooserActivity.bdF);
        in.putExtra(PictureChooserActivity.bdE, true);
        in.putExtra(PictureChooserActivity.bdD, maxSelected);
        in.putExtra("EXTRA_CURRENT_SELECTED", mData);
        context.startActivityForResult(in, requestCode);
    }

    public static void e(Activity context, int requestCode) {
        Intent in = new Intent();
        in.setClass(context, PictureChooserActivity.class);
        in.setAction(PictureChooserActivity.ACTION_PICK);
        in.putExtra(PictureChooserActivity.bdE, true);
        context.startActivityForResult(in, requestCode);
    }

    public static void a(Context context, int position, boolean fromHome, ArrayList<h> spaceStyles) {
        Intent intent = new Intent(context, SpaceStyleDetailActivity.class);
        intent.putExtra("EXTRA_FROM_HOME", fromHome);
        intent.putExtra(SpaceStyleDetailActivity.bhH, spaceStyles);
        intent.putExtra(SpaceStyleDetailActivity.bhG, position);
        context.startActivity(intent);
    }

    public static void a(Context context, c.a info, boolean isCreateStudio) {
        Intent intent = new Intent(context, StudioEditActivity.class);
        intent.putExtra(StudioEditActivity.bie, info);
        intent.putExtra(StudioEditActivity.bif, isCreateStudio);
        context.startActivity(intent);
    }

    public static void a(Activity context, c info, boolean isCreateStudio, int requestCode) {
        Intent intent = new Intent(context, StudioEditActivity.class);
        intent.putExtra(StudioEditActivity.bie, info);
        intent.putExtra(StudioEditActivity.bif, isCreateStudio);
        context.startActivityForResult(intent, requestCode);
    }

    public static void g(Context context, int studioId) {
        Intent intent = new Intent(context, StudioAuditActivity.class);
        intent.putExtra("STUDIO_ID", studioId);
        context.startActivity(intent);
    }

    public static void at(Context context) {
        Intent intent = new Intent();
        intent.setClassName(context, "hlx.home.main.HomeActivity");
        intent.addFlags(603979776);
        context.startActivity(intent);
    }

    public static void au(Context context) {
        Intent intent = new Intent(context, ProfileActivity.class);
        intent.addFlags(603979776);
        context.startActivity(intent);
    }

    public static void av(Context context) {
        Intent in = new Intent();
        in.setClass(context, MsgSettingsActivity.class);
        context.startActivity(in);
    }

    public static void h(Context context, @UserGuideActivity.a int type) {
        Intent intent = new Intent(context, UserGuideActivity.class);
        intent.putExtra(UserGuideActivity.cgv, type);
        context.startActivity(intent);
    }

    public static void aw(Context context) {
        context.startActivity(new Intent(context, UserGuideListActivity.class));
    }

    public static void a(Context context, com.huluxia.module.news.c item) {
        Intent in = new Intent();
        in.putExtra("NEWS_ITEM", item);
        in.setClass(context, NewsDetailActivity.class);
        context.startActivity(in);
    }

    public static void b(Context context, com.huluxia.module.news.c item) {
        Intent in = new Intent();
        in.putExtra("NEWS_ITEM", item);
        in.setClass(context, NewsCommentListActivity.class);
        context.startActivity(in);
    }
}
