package com.huluxia.ui.itemadapter.topic;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.huluxia.bbs.b.f;
import com.huluxia.bbs.b.g;
import com.huluxia.bbs.b.i;
import com.huluxia.bbs.b.m;
import com.huluxia.data.Medal;
import com.huluxia.data.PageList;
import com.huluxia.data.UserBaseInfo;
import com.huluxia.data.category.TopicCategory;
import com.huluxia.data.topic.CommentItem;
import com.huluxia.data.topic.ScoreItem;
import com.huluxia.data.topic.TopicItem;
import com.huluxia.data.topic.VideoInfo;
import com.huluxia.framework.base.async.AsyncTaskCenter;
import com.huluxia.framework.base.http.io.impl.request.DownloadRequestBuilder;
import com.huluxia.framework.base.image.PaintView;
import com.huluxia.framework.base.log.HLog;
import com.huluxia.framework.base.utils.UtilUri;
import com.huluxia.framework.base.utils.UtilsFunction;
import com.huluxia.framework.base.utils.UtilsMD5;
import com.huluxia.framework.base.utils.UtilsScreen;
import com.huluxia.framework.base.utils.UtilsText;
import com.huluxia.framework.base.widget.dialog.DialogManager;
import com.huluxia.framework.http.HttpMgr;
import com.huluxia.l;
import com.huluxia.t;
import com.huluxia.utils.ak;
import com.huluxia.utils.as;
import com.huluxia.utils.at;
import com.huluxia.utils.aw;
import com.huluxia.utils.az;
import com.huluxia.video.recorder.a;
import com.huluxia.video.views.VideoView;
import com.huluxia.widget.photowall.PhotoWall;
import com.huluxia.widget.textview.EmojiTextView;
import com.huluxia.widget.textview.HyperlinkEmojiTextView;
import com.huluxia.widget.textview.HyperlinkTextView;
import com.simple.colorful.b;
import com.simple.colorful.d;
import com.simple.colorful.setter.j;
import com.tencent.mm.sdk.platformtools.SpecilApiUtil;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TopicDetailItemAdapter extends BaseAdapter implements b {
    private static final String TAG = "TopicDetailItemAdapter";
    private static final int aWC = 1;
    private static final int aWE = 2;
    private TopicCategory aJO;
    private int aKj;
    private DialogManager aSB;
    private Activity aTg;
    private OnClickListener aUo;
    private PageList aWG;
    private Map<Long, Boolean> aWH;
    private OnClickListener aWK;
    private c aXl;
    private String aXm;
    private View aXn;
    private VideoView aXo;
    private ImageView aXp;
    private ProgressBar aXq;
    private PaintView aXr;
    private RelativeLayout aXs;
    private TextView aXt;
    private TextView aXu;
    private com.huluxia.video.views.scalable.b aXv;
    private long aXw;
    private Bitmap aXx;
    private VideoInfo aXy;
    private DownloadRequestBuilder aXz;
    private LayoutInflater mInflater;
    private TopicItem sK;

    public interface c {
        void a(boolean z, CommentItem commentItem);
    }

    public TopicDetailItemAdapter(Activity context) {
        this.mInflater = null;
        this.aWG = null;
        this.aJO = null;
        this.aKj = 0;
        this.aUo = new 1(this);
        this.aWK = new 6(this);
        this.aWG = new PageList();
        this.mInflater = LayoutInflater.from(context);
        this.aTg = context;
        this.aWH = new HashMap();
        this.aKj = at.dipToPx(context, 5);
        this.aSB = new DialogManager(context);
    }

    public int getItemViewType(int position) {
        if (getItem(position) instanceof TopicItem) {
            return 1;
        }
        return 2;
    }

    public int getViewTypeCount() {
        return 3;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        if (convertView == null) {
            if (type == 1) {
                convertView = this.mInflater.inflate(i.item_topicdetail_one, parent, false);
            } else {
                convertView = this.mInflater.inflate(i.item_topicdetail_other, parent, false);
            }
        }
        if (type == 1) {
            a(convertView, (TopicItem) getItem(position));
        } else {
            a(convertView, (CommentItem) getItem(position));
        }
        return convertView;
    }

    private void a(View v, UserBaseInfo user) {
        EmojiTextView nick = (EmojiTextView) v.findViewById(g.nick);
        String nickname = "";
        if (user.getMedalList() == null || user.getMedalList().size() <= 0) {
            nickname = aw.W(user.getNick(), 8);
        } else {
            nickname = aw.W(user.getNick(), 8);
        }
        nick.setText(nickname);
        nick.setTextColor(as.a(v.getContext(), user));
    }

    private void f(View v, UserBaseInfo user) {
        View flag = v.findViewById(g.moderator_flag);
        ((GradientDrawable) flag.getBackground()).setColor(Color.argb(255, 51, 181, 229));
        flag.setVisibility(8);
        if (this.aJO != null && as.a(user.getUserID(), this.aJO.getModerator())) {
            flag.setVisibility(0);
        }
    }

    private void b(View view, UserBaseInfo user) {
        TextView user_age = (TextView) view.findViewById(g.user_age);
        user_age.setText(Integer.toString(user.getAge()));
        if (user.getGender() == 1) {
            user_age.setBackgroundResource(f.bg_gender_female);
            user_age.setCompoundDrawablesWithIntrinsicBounds(this.aTg.getResources().getDrawable(f.user_female), null, null, null);
            return;
        }
        user_age.setBackgroundResource(f.bg_gender_male);
        user_age.setCompoundDrawablesWithIntrinsicBounds(this.aTg.getResources().getDrawable(f.user_male), null, null, null);
    }

    @TargetApi(16)
    private void c(View v, UserBaseInfo user) {
        TextView tv_honor = (TextView) v.findViewById(g.tv_honor);
        if (user.getIdentityColor() != 0) {
            tv_honor.setText(user.getIdentityTitle());
            tv_honor.setVisibility(0);
            ((GradientDrawable) tv_honor.getBackground()).setColor(user.getIdentityColor());
            return;
        }
        tv_honor.setVisibility(8);
    }

    private void d(View v, UserBaseInfo user) {
        if (user.getMedalList() == null || user.getMedalList().size() <= 0) {
            v.findViewById(g.ly_medal).setVisibility(8);
            return;
        }
        v.findViewById(g.ly_medal).setVisibility(0);
        PaintView medal0 = (PaintView) v.findViewById(g.iv_medal0);
        medal0.setVisibility(8);
        PaintView medal1 = (PaintView) v.findViewById(g.iv_medal1);
        medal1.setVisibility(8);
        PaintView medal2 = (PaintView) v.findViewById(g.iv_medal2);
        medal2.setVisibility(8);
        PaintView medal3 = (PaintView) v.findViewById(g.iv_medal3);
        medal3.setVisibility(8);
        PaintView medal4 = (PaintView) v.findViewById(g.iv_medal4);
        medal4.setVisibility(8);
        PaintView medal5 = (PaintView) v.findViewById(g.iv_medal5);
        medal5.setVisibility(8);
        for (int i = 0; i < user.getMedalList().size(); i++) {
            switch (i) {
                case 0:
                    medal0.setVisibility(0);
                    medal0.setUri(UtilUri.getUriOrNull(((Medal) user.getMedalList().get(i)).getUrl())).placeHolder(d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal).tag(this.aTg).setImageLoader(l.cb().getImageLoader());
                    break;
                case 1:
                    medal1.setVisibility(0);
                    medal1.setUri(UtilUri.getUriOrNull(((Medal) user.getMedalList().get(i)).getUrl())).placeHolder(d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal).tag(this.aTg).setImageLoader(l.cb().getImageLoader());
                    break;
                case 2:
                    medal2.setVisibility(0);
                    medal2.setUri(UtilUri.getUriOrNull(((Medal) user.getMedalList().get(i)).getUrl())).placeHolder(d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal).tag(this.aTg).setImageLoader(l.cb().getImageLoader());
                    break;
                case 3:
                    medal3.setVisibility(0);
                    medal3.setUri(UtilUri.getUriOrNull(((Medal) user.getMedalList().get(i)).getUrl())).placeHolder(d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal).tag(this.aTg).setImageLoader(l.cb().getImageLoader());
                    break;
                case 4:
                    medal4.setVisibility(0);
                    medal4.setUri(UtilUri.getUriOrNull(((Medal) user.getMedalList().get(i)).getUrl())).placeHolder(d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal).tag(this.aTg).setImageLoader(l.cb().getImageLoader());
                    break;
                case 5:
                    medal5.setVisibility(0);
                    medal5.setUri(UtilUri.getUriOrNull(((Medal) user.getMedalList().get(i)).getUrl())).placeHolder(d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal).tag(this.aTg).setImageLoader(l.cb().getImageLoader());
                    break;
                default:
                    break;
            }
        }
        v.findViewById(g.ly_medal).setOnClickListener(this.aUo);
    }

    private void a(View v, CommentItem data) {
        ((LinearLayout) v.findViewById(g.topic_other)).setOnClickListener(new 7(this, data));
        t.a((PaintView) v.findViewById(g.avatar), data.getUserInfo().getAvatar(), (float) this.aKj);
        v.findViewById(g.layout_header).setOnClickListener(new a(this, this.aTg, data.getUserInfo().getUserID(), data.getUserInfo()));
        ((TextView) v.findViewById(g.floor)).setText(Long.toString(data.getSeq()) + "楼");
        ((TextView) v.findViewById(g.publish_time)).setText(az.bD(data.getCreateTime()));
        a(v, data.getUserInfo());
        b(v, data.getUserInfo());
        c(v, data.getUserInfo());
        d(v, data.getUserInfo());
        as.a(this.aTg, (ImageView) v.findViewById(g.iv_role), data.getUserInfo());
        f(v, data.getUserInfo());
        HyperlinkTextView content_short = (HyperlinkTextView) v.findViewById(g.content_short);
        HyperlinkTextView content_long = (HyperlinkTextView) v.findViewById(g.content_long);
        TextView more_tv = (TextView) v.findViewById(g.more);
        HyperlinkTextView retcontent = (HyperlinkTextView) v.findViewById(g.retcontent);
        TextView delcontent = (TextView) v.findViewById(g.delcontent);
        PhotoWall photoWall = (PhotoWall) v.findViewById(g.photoWall);
        TextView tv_score = (TextView) v.findViewById(g.tv_score);
        tv_score.setVisibility(8);
        if (data.getState() == 2) {
            delcontent.setVisibility(0);
            content_short.setVisibility(8);
            content_long.setVisibility(8);
            more_tv.setVisibility(8);
            retcontent.setVisibility(8);
            photoWall.setVisibility(8);
            return;
        }
        delcontent.setVisibility(8);
        retcontent.setVisibility(8);
        content_short.setText(data.getText());
        content_long.setText(data.getText());
        a(content_short, content_long, more_tv, data.getSeq());
        if (data.getRefComment() != null) {
            String szRetText = data.getRefComment().getText();
            if (data.getRefComment().getState() == 2) {
                szRetText = "此评论已经删除";
            }
            retcontent.setText(aw.W("回复 " + aw.W(data.getRefComment().getNick(), 10) + SpecilApiUtil.LINE_SEP + szRetText, 100));
            retcontent.setVisibility(0);
        }
        a(photoWall, (List) data.getImages(), false);
        if (data.getScore() == 0 && data.getScoreTxt().trim().length() > 0) {
            tv_score.setVisibility(0);
            tv_score.setText(data.getScoreTxt());
        }
        a(v, data.getScoreCount(), data.getScoreList(), data.getCommentID(), false);
    }

    protected void a(View v, TopicItem data) {
        v.findViewById(g.topic_one).setOnClickListener(new 8(this));
        t.a((PaintView) v.findViewById(g.avatar), data.getUserInfo().getAvatar(), (float) this.aKj);
        v.findViewById(g.layout_header).setOnClickListener(new a(this, this.aTg, data.getUserInfo().getUserID(), data.getUserInfo()));
        ((HyperlinkTextView) v.findViewById(g.content)).setText(data.getDetail());
        ((TextView) v.findViewById(g.publish_time)).setText(az.bD(data.getCreateTime()));
        a(v, data.getUserInfo());
        b(v, data.getUserInfo());
        c(v, data.getUserInfo());
        d(v, data.getUserInfo());
        as.a(this.aTg, (ImageView) v.findViewById(g.iv_role), data.getUserInfo());
        f(v, data.getUserInfo());
        if (UtilsFunction.empty(data.getVoice())) {
            v.findViewById(g.topic_video).setVisibility(8);
            v.findViewById(g.photoWall).setVisibility(0);
            a((PhotoWall) v.findViewById(g.photoWall), data.getImages(), true);
        } else {
            v.findViewById(g.photoWall).setVisibility(8);
            v.findViewById(g.topic_video).setVisibility(0);
            c(v, data);
        }
        m(v);
        TextView tv_score = (TextView) v.findViewById(g.tv_score);
        tv_score.setVisibility(8);
        if (data.getScore() == 0 && data.getScoreTxt().trim().length() > 0) {
            tv_score.setVisibility(0);
            tv_score.setText(data.getScoreTxt());
        }
        a(v, data.getScoreCount(), data.getScoreList(), data.getPostID(), true);
        if (data.isAuthention() && (data.getState() == 1 || data.getState() == 3)) {
            v.findViewById(g.iv_moderator_symbol).setVisibility(0);
        } else {
            v.findViewById(g.iv_moderator_symbol).setVisibility(8);
        }
    }

    private void m(View v) {
        Collection userList = this.aWG.getRemindUsers();
        LinearLayout ll_alt = (LinearLayout) v.findViewById(g.ll_alt);
        if (UtilsFunction.empty(userList)) {
            ll_alt.setVisibility(8);
            return;
        }
        ll_alt.setVisibility(0);
        ((HyperlinkEmojiTextView) v.findViewById(g.tv_userlist)).setText(userList);
    }

    public View HH() {
        return this.aXn;
    }

    private void c(View v, TopicItem data) {
        v.findViewById(g.topic_video).setVisibility(data.getState() == 2 ? 8 : 0);
        this.sK = data;
        this.aXy = VideoInfo.convertFromString(data.getVoice());
        this.aXn = v.findViewById(g.video_container);
        ViewCompat.setLayerType(this.aXn, 2, null);
        this.aXn.getLayoutParams().width = UtilsScreen.getScreenWidth(this.aTg);
        this.aXn.getLayoutParams().height = (UtilsScreen.getScreenWidth(this.aTg) * 3) / 4;
        this.aXo = (VideoView) v.findViewById(g.video_view);
        this.aXr = (PaintView) v.findViewById(g.thumbnail);
        this.aXr.setDefaultImageResId(com.huluxia.bbs.b.d.black);
        try {
            this.aXo.setDataSource("");
        } catch (IOException e) {
            HLog.debug(TAG, "try set data source empty!", new Object[0]);
        }
        this.aXp = (ImageView) v.findViewById(g.play_btn);
        this.aXq = (ProgressBar) v.findViewById(g.loading_view);
        this.aXs = (RelativeLayout) v.findViewById(g.rly_play_end);
        this.aXt = (TextView) v.findViewById(g.tv_replay);
        this.aXt.setOnClickListener(new 9(this));
        this.aXo.setOnClickListener(new 10(this));
        this.aXo.setSurfaceTextureUpdatedListener(new 11(this));
        this.aXp.setOnClickListener(new 12(this));
        this.aXo.setCompletionListener(new 13(this));
        this.aXu = (TextView) v.findViewById(g.tv_progress_time);
        HI();
    }

    private void HI() {
        if (this.aXy != null && !UtilsFunction.empty(this.aXy.imgurl)) {
            this.aXr.setVisibility(0);
            this.aXr.setImageUrl(this.aXy.imgurl, l.cb().getImageLoader());
        }
    }

    private void HJ() {
        if (UtilsFunction.empty(this.aXm)) {
            this.aXp.setVisibility(8);
            this.aXq.setVisibility(0);
            d(this.sK);
            return;
        }
        HK();
    }

    public void HK() {
        try {
            if (this.aXo.Nw()) {
                this.aXo.start();
                HLog.debug(TAG, "video view position = %d so resume", Integer.valueOf(this.aXo.getCurrentPosition()));
            } else if (UtilsFunction.empty(this.aXm)) {
                HLog.error(TAG, "inner play video, but video path is NULL", new Object[0]);
                return;
            } else {
                this.aXo.setDataSource(this.aXm);
                this.aXo.setLooping(false);
                this.aXo.b(new 14(this));
            }
            this.aXp.setVisibility(8);
            this.aXq.setVisibility(8);
            this.aXs.setVisibility(8);
            this.aXr.setVisibility(8);
        } catch (IOException e) {
            t.download_toast(this.aTg, "暂时不支持播放该格式的视频");
        }
    }

    private void d(TopicItem data) {
        if (!ak.isNetworkConnected(this.aTg)) {
            HO();
            t.n(this.aTg, "网络连接不可用，请稍后再试");
        } else if (this.aXy == null) {
            t.download_toast(this.aTg, "视频地址解析出错");
            this.aXq.setVisibility(8);
            this.aXp.setVisibility(0);
        } else {
            this.aXz = HttpMgr.getInstance().getDownloadReqBuilder(this.aXy.videourl, Environment.getExternalStorageDirectory() + com.huluxia.utils.d.bjY + File.separator + "downloads", UtilsMD5.getMD5String(String.valueOf(SystemClock.elapsedRealtime())) + a.boV);
            this.aXz.setSuccListener(new 3(this)).setErrListener(new 2(this)).execute();
        }
    }

    public void HL() {
        if (this.aXz != null) {
            this.aXz.cancel();
        }
    }

    private void HM() {
        if (!UtilsFunction.empty(this.aXm)) {
            AsyncTaskCenter.getInstance().executeSingleThread(new 4(this));
        }
    }

    private void HN() {
        if (this.aXv != null) {
            int videoHeight = this.aXv.getHeight();
            int videoWidth = this.aXv.getWidth();
            if (videoHeight <= videoWidth) {
                this.aXn.getLayoutParams().width = UtilsScreen.getScreenWidth(this.aTg);
                this.aXn.getLayoutParams().height = (UtilsScreen.getScreenWidth(this.aTg) * videoHeight) / videoWidth;
            }
        }
    }

    private void cM(boolean show) {
        long left;
        this.aXu.setVisibility(show ? 0 : 8);
        if (this.aXy == null || this.aXy.getLength() == 0) {
            left = (this.aXw / 1000) / 1000;
        } else {
            left = this.aXy.getLength() - ((this.aXw / 1000) / 1000);
        }
        this.aXu.setText(UtilsText.getTimeLength(left));
    }

    public void HO() {
        this.aXp.setVisibility(0);
        this.aXq.setVisibility(8);
        this.aXu.setVisibility(8);
        t.download_toast(this.aTg, "视频加载失败,请稍后重试");
    }

    public void HP() {
        if ((this.sK == null || !UtilsFunction.empty(this.sK.getVoice())) && this.aXo != null && this.aXo.isPlaying()) {
            this.aXo.pause();
            this.aXp.setVisibility(0);
            this.aXu.setVisibility(0);
            HI();
        }
    }

    public void HQ() {
        if ((this.sK != null && UtilsFunction.empty(this.sK.getVoice())) || this.aXo == null) {
            return;
        }
        if (this.aXo.isPlaying() || this.aXo.Nw()) {
            HLog.debug(TAG, "<---stop video--->", new Object[0]);
            this.aXo.stop();
            this.aXp.setVisibility(0);
            this.aXu.setVisibility(8);
            HI();
        }
    }

    public boolean HR() {
        if (this.aXo == null || !this.aXo.Nw()) {
            return false;
        }
        return true;
    }

    public boolean HS() {
        if (this.aXo == null || !this.aXo.isPlaying()) {
            return false;
        }
        return true;
    }

    private void a(TextView content_short, TextView content_long, TextView more_tv, long seq) {
        boolean isSpread = false;
        if (this.aWH.containsKey(Long.valueOf(seq))) {
            isSpread = ((Boolean) this.aWH.get(Long.valueOf(seq))).booleanValue();
        } else {
            this.aWH.put(Long.valueOf(seq), Boolean.valueOf(false));
        }
        if (((int) content_long.getPaint().measureText(content_long.getText().toString())) <= (at.getScreenPixWidth(this.aTg) - at.dipToPx(this.aTg, 60)) * 6) {
            content_short.setVisibility(8);
            more_tv.setVisibility(8);
            content_long.setVisibility(0);
            return;
        }
        more_tv.setOnClickListener(new 5(this, isSpread, seq, content_short, content_long, more_tv));
        more_tv.setVisibility(0);
        if (isSpread) {
            content_short.setVisibility(8);
            content_long.setVisibility(0);
            more_tv.setText(m.content_shrinkup);
            return;
        }
        content_short.setVisibility(0);
        content_long.setVisibility(8);
        more_tv.setText(m.content_spread);
    }

    private void a(PhotoWall photoWall, List<String> images, boolean isTop) {
        photoWall.setReadOnly(true);
        if (UtilsFunction.empty((Collection) images)) {
            photoWall.setVisibility(8);
            return;
        }
        photoWall.PD();
        photoWall.setVisibility(0);
        a(photoWall, images.size(), isTop);
        for (String url : images) {
            PhotoWall.b un = new PhotoWall.b();
            un.setUrl(url);
            photoWall.a(un);
        }
    }

    private void a(PhotoWall photoWall, int size, boolean isTop) {
        int nitemW;
        if (isTop) {
            nitemW = (at.getScreenPixWidth(photoWall.getContext()) - at.dipToPx(this.aTg, 16)) / 3;
        } else {
            nitemW = at.getScreenPixWidth(photoWall.getContext()) / 4;
        }
        if (size < 4) {
            photoWall.getLayoutParams().width = nitemW * size;
            photoWall.setMaxPhotoNum(size);
            photoWall.setNumColumns(size);
        } else if (size == 4) {
            photoWall.getLayoutParams().width = nitemW * 2;
            photoWall.setMaxPhotoNum(size);
            photoWall.setNumColumns(2);
        } else {
            photoWall.getLayoutParams().width = nitemW * 3;
            photoWall.setMaxPhotoNum(size);
            photoWall.setNumColumns(3);
        }
    }

    public int getCount() {
        return this.aWG.size();
    }

    public Object getItem(int position) {
        return this.aWG.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    public PageList getPageList() {
        return this.aWG;
    }

    public void a(PageList pageList) {
        this.aWG = pageList;
    }

    public TopicCategory getTopicCategory() {
        return this.aJO;
    }

    public void setTopicCategory(TopicCategory topicCategory) {
        this.aJO = topicCategory;
    }

    public void HG() {
        this.aWH.clear();
    }

    private void a(View v, long scoreCount, List<ScoreItem> scoreList, long post_id, boolean isTopic) {
        LinearLayout ly_score = (LinearLayout) v.findViewById(g.ly_score);
        if (scoreList == null || scoreList.isEmpty()) {
            ly_score.setVisibility(8);
            return;
        }
        b clickInfo = new b(post_id, isTopic);
        ly_score.setVisibility(0);
        ImageView iv_more = (ImageView) v.findViewById(g.iv_more);
        iv_more.setVisibility(0);
        iv_more.setTag(clickInfo);
        iv_more.setOnClickListener(this.aWK);
        PaintView iv_total = (PaintView) v.findViewById(g.iv_total);
        iv_total.radius(2.0f);
        iv_total.setTag(clickInfo);
        iv_total.setOnClickListener(this.aWK);
        TextView tv_score_count = (TextView) v.findViewById(g.tv_total);
        if (scoreCount > 0) {
            tv_score_count.setText(String.format("+%d", new Object[]{Long.valueOf(scoreCount)}));
        } else {
            tv_score_count.setText(String.valueOf(scoreCount));
        }
        v.findViewById(g.rly_user2).setVisibility(8);
        v.findViewById(g.rly_user3).setVisibility(8);
        v.findViewById(g.rly_user4).setVisibility(8);
        v.findViewById(g.rly_user5).setVisibility(8);
        PaintView iv_user1 = (PaintView) v.findViewById(g.iv_userl);
        PaintView iv_user2 = (PaintView) v.findViewById(g.iv_user2);
        PaintView iv_user3 = (PaintView) v.findViewById(g.iv_user3);
        PaintView iv_user4 = (PaintView) v.findViewById(g.iv_user4);
        PaintView iv_user5 = (PaintView) v.findViewById(g.iv_user5);
        TextView tv_count1 = (TextView) v.findViewById(g.tv_countl);
        TextView tv_count2 = (TextView) v.findViewById(g.tv_count2);
        TextView tv_count3 = (TextView) v.findViewById(g.tv_count3);
        TextView tv_count4 = (TextView) v.findViewById(g.tv_count4);
        TextView tv_count5 = (TextView) v.findViewById(g.tv_count5);
        for (int i = 0; i < scoreList.size(); i++) {
            switch (i) {
                case 0:
                    a(v.findViewById(g.rly_user1), clickInfo);
                    a((ScoreItem) scoreList.get(0), iv_user1);
                    a((ScoreItem) scoreList.get(0), tv_count1);
                    break;
                case 1:
                    a(v.findViewById(g.rly_user2), clickInfo);
                    a((ScoreItem) scoreList.get(1), iv_user2);
                    a((ScoreItem) scoreList.get(1), tv_count2);
                    break;
                case 2:
                    a(v.findViewById(g.rly_user3), clickInfo);
                    a((ScoreItem) scoreList.get(2), iv_user3);
                    a((ScoreItem) scoreList.get(2), tv_count3);
                    break;
                case 3:
                    a(v.findViewById(g.rly_user4), clickInfo);
                    a((ScoreItem) scoreList.get(3), iv_user4);
                    a((ScoreItem) scoreList.get(3), tv_count4);
                    break;
                case 4:
                    a(v.findViewById(g.rly_user5), clickInfo);
                    a((ScoreItem) scoreList.get(4), iv_user5);
                    a((ScoreItem) scoreList.get(4), tv_count5);
                    break;
                default:
                    break;
            }
        }
    }

    private void a(View view, b clickInfo) {
        view.setVisibility(0);
        view.setTag(clickInfo);
        view.setOnClickListener(this.aWK);
    }

    private void a(ScoreItem scoreItem, TextView tv) {
        tv.setText(String.valueOf(scoreItem.getScore()));
        if (scoreItem.isIsadmin()) {
            tv.setTextColor(d.getColor(this.aTg, com.huluxia.bbs.b.c.textColorTopicHuluRed));
        } else {
            tv.setTextColor(d.getColor(this.aTg, com.huluxia.bbs.b.c.textColorGreenTopic));
        }
    }

    private void a(ScoreItem scoreItem, PaintView iv) {
        t.a(iv, scoreItem.getAvatar(), (float) at.dipToPx(this.aTg, 5));
    }

    public c HT() {
        return this.aXl;
    }

    public void a(c clickItemListener) {
        this.aXl = clickItemListener;
    }

    public void a(j setter) {
        setter.bg(g.topic_other, com.huluxia.bbs.b.c.listSelector).bg(g.topic_one, com.huluxia.bbs.b.c.listSelector).bh(g.content, 16842808).bh(g.floor, 16842808).bh(g.publish_time, 16842808).bh(g.content_short, 16842808).bh(g.content_long, 16842808).bg(g.delcontent, com.huluxia.bbs.b.c.backgroundTopicReply).bg(g.retcontent, com.huluxia.bbs.b.c.backgroundTopicReply).bh(g.delcontent, 16843282).bh(g.retcontent, 16843282).bh(g.more, com.huluxia.bbs.b.c.textColorGreen).bf(g.split_item_alt, com.huluxia.bbs.b.c.splitColor).bf(g.split_item, com.huluxia.bbs.b.c.splitColor).bi(g.avatar, com.huluxia.bbs.b.c.valBrightness).bj(g.iv_moderator_symbol, com.huluxia.bbs.b.c.drawableModeratorAuth);
    }
}
