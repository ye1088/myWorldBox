package com.MCWorld.ui.itemadapter.topic;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.MCWorld.bbs.b.f;
import com.MCWorld.bbs.b.g;
import com.MCWorld.bbs.b.i;
import com.MCWorld.bbs.b.m;
import com.MCWorld.data.Medal;
import com.MCWorld.data.PageList;
import com.MCWorld.data.UserBaseInfo;
import com.MCWorld.data.category.TopicCategory;
import com.MCWorld.data.map.f.a;
import com.MCWorld.data.topic.CommentItem;
import com.MCWorld.data.topic.ScoreItem;
import com.MCWorld.data.topic.TopicItem;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.utils.UtilUri;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.base.widget.hlistview.HListView;
import com.MCWorld.l;
import com.MCWorld.t;
import com.MCWorld.utils.ad;
import com.MCWorld.utils.as;
import com.MCWorld.utils.at;
import com.MCWorld.utils.aw;
import com.MCWorld.utils.az;
import com.MCWorld.widget.photowall.PhotoWall;
import com.MCWorld.widget.textview.EmojiTextView;
import com.MCWorld.widget.textview.HyperlinkEmojiTextView;
import com.MCWorld.widget.textview.HyperlinkTextView;
import com.simple.colorful.b;
import com.simple.colorful.setter.j;
import com.tencent.mm.sdk.platformtools.SpecilApiUtil;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResTopicDetailItemAdapter extends BaseAdapter implements b {
    private static final String TAG = "ResTopicDetailItemAdapter";
    private static final int aWC = 1;
    private static final int aWD = 2;
    private static final int aWE = 3;
    private TopicCategory aJO;
    private int aKj;
    private ProgressBar aMO;
    private Button aOx;
    private Activity aTg;
    private OnClickListener aUo;
    private a aWF;
    private PageList aWG;
    private Map<Long, Boolean> aWH;
    private c aWI;
    private d aWJ;
    private OnClickListener aWK;
    private LayoutInflater mInflater;

    public interface c {
        void a(boolean z, CommentItem commentItem);
    }

    public interface d {
        void FZ();
    }

    public ResTopicDetailItemAdapter(Activity context) {
        this.mInflater = null;
        this.aWG = null;
        this.aJO = null;
        this.aKj = 0;
        this.aUo = new 1(this);
        this.aWK = new 8(this);
        this.aWG = new PageList();
        this.mInflater = LayoutInflater.from(context);
        this.aTg = context;
        this.aWH = new HashMap();
        this.aKj = at.dipToPx(context, 5);
    }

    public int getItemViewType(int position) {
        if (!(getItem(position) instanceof TopicItem)) {
            return 3;
        }
        if (this.aWG.getIsTmp() == 1) {
            return 2;
        }
        return 1;
    }

    public int getViewTypeCount() {
        return 4;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        if (convertView == null) {
            if (type == 1) {
                convertView = this.mInflater.inflate(i.item_topicdetail_one, parent, false);
            } else if (type == 2) {
                convertView = this.mInflater.inflate(i.item_topicdetail_cuz_one, parent, false);
            } else {
                convertView = this.mInflater.inflate(i.item_topicdetail_other, parent, false);
            }
        }
        if (type == 1) {
            a(convertView, (TopicItem) getItem(position));
        } else if (type == 2) {
            b(convertView, (TopicItem) getItem(position));
        } else {
            a(convertView, (CommentItem) getItem(position));
        }
        return convertView;
    }

    private void a(View v, UserBaseInfo user) {
        EmojiTextView nick = (EmojiTextView) v.findViewById(g.nick);
        nick.setText(aw.W(user.getNick(), 8));
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
                    medal0.setUri(UtilUri.getUriOrNull(((Medal) user.getMedalList().get(i)).getUrl())).placeHolder(com.simple.colorful.d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal).tag(this.aTg).setImageLoader(l.cb().getImageLoader());
                    break;
                case 1:
                    medal1.setVisibility(0);
                    medal1.setUri(UtilUri.getUriOrNull(((Medal) user.getMedalList().get(i)).getUrl())).placeHolder(com.simple.colorful.d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal).tag(this.aTg).setImageLoader(l.cb().getImageLoader());
                    break;
                case 2:
                    medal2.setVisibility(0);
                    medal2.setUri(UtilUri.getUriOrNull(((Medal) user.getMedalList().get(i)).getUrl())).placeHolder(com.simple.colorful.d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal).tag(this.aTg).setImageLoader(l.cb().getImageLoader());
                    break;
                case 3:
                    medal3.setVisibility(0);
                    medal3.setUri(UtilUri.getUriOrNull(((Medal) user.getMedalList().get(i)).getUrl())).placeHolder(com.simple.colorful.d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal).tag(this.aTg).setImageLoader(l.cb().getImageLoader());
                    break;
                case 4:
                    medal4.setVisibility(0);
                    medal4.setUri(UtilUri.getUriOrNull(((Medal) user.getMedalList().get(i)).getUrl())).placeHolder(com.simple.colorful.d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal).tag(this.aTg).setImageLoader(l.cb().getImageLoader());
                    break;
                case 5:
                    medal5.setVisibility(0);
                    medal5.setUri(UtilUri.getUriOrNull(((Medal) user.getMedalList().get(i)).getUrl())).placeHolder(com.simple.colorful.d.isDayMode() ? f.place_holder_normal : f.place_holder_night_normal).tag(this.aTg).setImageLoader(l.cb().getImageLoader());
                    break;
                default:
                    break;
            }
        }
        v.findViewById(g.ly_medal).setOnClickListener(this.aUo);
    }

    private void a(View v, CommentItem data) {
        ((LinearLayout) v.findViewById(g.topic_other)).setOnClickListener(new 2(this, data));
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
        v.findViewById(g.topic_one).setOnClickListener(new 3(this));
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
        if (ad.empty(data.getVoice())) {
            v.findViewById(g.topic_video).setVisibility(8);
            a((PhotoWall) v.findViewById(g.photoWall), data.getImages(), true);
            v.findViewById(g.photoWall).setVisibility(8);
        } else {
            v.findViewById(g.photoWall).setVisibility(8);
            v.findViewById(g.topic_video).setVisibility(0);
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

    private void b(View v, TopicItem data) {
        v.findViewById(g.topic_cuz_one).setOnClickListener(new 4(this));
        t.a((PaintView) v.findViewById(g.avatar), data.getUserInfo().getAvatar(), (float) this.aKj);
        v.findViewById(g.layout_header).setOnClickListener(new a(this, this.aTg, data.getUserInfo().getUserID(), data.getUserInfo()));
        ((HyperlinkTextView) v.findViewById(g.map_introduce)).setText(data.getDetail());
        ((TextView) v.findViewById(g.publish_time)).setText(az.bD(data.getCreateTime()));
        a(v, data.getUserInfo());
        b(v, data.getUserInfo());
        c(v, data.getUserInfo());
        d(v, data.getUserInfo());
        as.a(this.aTg, (ImageView) v.findViewById(g.iv_role), data.getUserInfo());
        f(v, data.getUserInfo());
        if (ad.empty(data.getScoreList())) {
            v.findViewById(g.ll_credit).setVisibility(8);
        } else {
            v.findViewById(g.ll_credit).setVisibility(0);
            a(v, data.getScoreCount(), data.getScoreList(), data.getPostID(), true);
        }
        this.aMO = (ProgressBar) v.findViewById(g.progress_bar_download);
        this.aOx = (Button) v.findViewById(g.btn_download);
        this.aWJ.FZ();
        l(v);
        a(v, this.aWF);
        HE();
    }

    private void HE() {
        this.aOx.getViewTreeObserver().addOnGlobalLayoutListener(new 5(this));
    }

    private void l(View v) {
        com.MCWorld.data.topic.a studioInfo = this.aWG.getStudioInfo();
        ((LinearLayout) v.findViewById(g.ll_studio)).setVisibility(studioInfo != null ? 0 : 8);
        if (studioInfo != null) {
            ((TextView) v.findViewById(g.map_studio)).setText(studioInfo.name);
            HListView listView = (HListView) v.findViewById(g.studio_list);
            ListAdapter adapter = new e(this.aTg);
            listView.setAdapter(adapter);
            adapter.f(studioInfo.studioUserInfos);
            listView.setOnItemClickListener(null);
            v.findViewById(g.tv_more_resource).setOnClickListener(new 6(this, studioInfo));
        }
    }

    public void a(View v, a data) {
        if (data != null) {
            TextView mapVersion = (TextView) v.findViewById(g.map_version);
            TextView mapSize = (TextView) v.findViewById(g.map_size);
            TextView mapDate = (TextView) v.findViewById(g.map_date);
            TextView mapDownload = (TextView) v.findViewById(g.map_download);
            EmojiTextView mapAuthor = (EmojiTextView) v.findViewById(g.map_author);
            ((TextView) v.findViewById(g.map_type)).setText(data.cateName);
            mapVersion.setText(data.version);
            if (data.mapSize == null || !aw.validNumber(data.mapSize)) {
                mapSize.setText("未知");
            } else {
                mapSize.setText(aw.bA(Long.valueOf(data.mapSize).longValue()));
            }
            mapDate.setText(az.bF(Long.valueOf(data.createTime).longValue()));
            mapDownload.setText("" + data.downCount);
            mapAuthor.setText(data.author);
        }
    }

    public void e(a data) {
        this.aWF = data;
        notifyDataSetChanged();
    }

    public Button getDownButton() {
        return this.aOx;
    }

    public ProgressBar HF() {
        return this.aMO;
    }

    private void m(View v) {
        Collection userList = this.aWG.getRemindUsers();
        LinearLayout ll_alt = (LinearLayout) v.findViewById(g.ll_alt);
        if (ad.empty(userList)) {
            ll_alt.setVisibility(8);
            return;
        }
        ll_alt.setVisibility(0);
        ((HyperlinkEmojiTextView) v.findViewById(g.tv_userlist)).setText(userList);
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
        more_tv.setOnClickListener(new 7(this, isSpread, seq, content_short, content_long, more_tv));
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
            tv.setTextColor(com.simple.colorful.d.getColor(this.aTg, com.MCWorld.bbs.b.c.textColorTopicHuluRed));
        } else {
            tv.setTextColor(com.simple.colorful.d.getColor(this.aTg, com.MCWorld.bbs.b.c.textColorGreenTopic));
        }
    }

    private void a(ScoreItem scoreItem, PaintView iv) {
        t.a(iv, scoreItem.getAvatar(), (float) at.dipToPx(this.aTg, 5));
    }

    public void a(c clickItemListener) {
        this.aWI = clickItemListener;
    }

    public void a(d prepareListener) {
        this.aWJ = prepareListener;
    }

    public void a(j setter) {
        setter.bg(g.topic_other, com.MCWorld.bbs.b.c.listSelector).bg(g.topic_cuz_one, com.MCWorld.bbs.b.c.listSelector).bg(g.topic_one, com.MCWorld.bbs.b.c.listSelector).bh(g.map_introduce, 16843282).bh(g.content, 16842808).bh(g.floor, 16842808).bh(g.publish_time, 16842808).bh(g.content_short, 16842808).bh(g.content_long, 16842808).bg(g.delcontent, com.MCWorld.bbs.b.c.backgroundTopicReply).bg(g.retcontent, com.MCWorld.bbs.b.c.backgroundTopicReply).bh(g.delcontent, 16843282).bh(g.retcontent, 16843282).bh(g.more, com.MCWorld.bbs.b.c.textColorGreen).bf(g.split_item_alt, com.MCWorld.bbs.b.c.splitColor).bf(g.split_item, com.MCWorld.bbs.b.c.splitColor).bi(g.avatar, com.MCWorld.bbs.b.c.valBrightness).bj(g.iv_moderator_symbol, com.MCWorld.bbs.b.c.drawableModeratorAuth).bh(g.tv_more_resource, com.MCWorld.bbs.b.c.textColorQuaternary).ac(g.tv_more_resource, com.MCWorld.bbs.b.c.ic_more_resource_arrow, 2);
    }
}
