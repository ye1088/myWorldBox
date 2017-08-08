package com.MCWorld.ui.bbs;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.MCWorld.bbs.b.f;
import com.MCWorld.bbs.b.g;
import com.MCWorld.data.Medal;
import com.MCWorld.data.UserBaseInfo;
import com.MCWorld.data.category.TopicCategory;
import com.MCWorld.data.topic.CommentItem;
import com.MCWorld.data.topic.TopicItem;
import com.MCWorld.data.topic.c;
import com.MCWorld.framework.base.image.PaintView;
import com.MCWorld.framework.base.log.HLog;
import com.MCWorld.framework.base.notification.CallbackHandler;
import com.MCWorld.framework.base.notification.EventNotifyCenter;
import com.MCWorld.framework.base.notification.EventNotifyCenter.MessageHandler;
import com.MCWorld.framework.base.utils.UtilsFunction;
import com.MCWorld.framework.base.utils.UtilsScreen;
import com.MCWorld.http.base.d;
import com.MCWorld.module.h;
import com.MCWorld.module.picture.b;
import com.MCWorld.module.topic.k;
import com.MCWorld.module.w;
import com.MCWorld.r;
import com.MCWorld.t;
import com.MCWorld.utils.ab;
import com.MCWorld.utils.ad;
import com.MCWorld.utils.as;
import com.MCWorld.utils.at;
import com.MCWorld.utils.aw;
import com.MCWorld.widget.dialog.i;
import com.MCWorld.widget.textview.EmojiTextView;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.cookie.ClientCookie;

public class CommentTopicActivity extends PublishTopicBaseActivity {
    private CommentTopicActivity aKd;
    private CommentItem aKe;
    private UserBaseInfo aKf;
    private EmojiTextView aKg;
    private TextView aKh;
    private View aKi;
    private int aKj = 0;
    private boolean aKk;
    private CallbackHandler mCallback = new CallbackHandler(this) {
        final /* synthetic */ CommentTopicActivity aKl;

        {
            this.aKl = this$0;
        }

        @MessageHandler(message = 659)
        public void onComment(boolean succ, w info) {
            this.aKl.cs(false);
            this.aKl.aIT.setEnabled(true);
            if (info == null) {
                t.n(this.aKl.aKd, "请求失败, 网络问题");
            } else if (info.status == 1) {
                this.aKl.aKd.cv(true);
                this.aKl.aKd.setResult(-1);
                if (info.code == 201) {
                    this.aKl.f(info.msg, true);
                    return;
                }
                t.o(this.aKl.aKd, info.msg);
                this.aKl.aKd.finish();
            } else if (info.code == 104) {
                String title = "启禀陛下";
                if (!(info.title == null || info.title.equals("null"))) {
                    title = info.title;
                }
                String msg = ab.n(info.code, info.msg);
                i dia = new i(this.aKl.aKd, new a());
                dia.az(title, msg);
                dia.gK("朕知道了");
                dia.showDialog();
            } else {
                this.aKl.g(ab.n(info.code, info.msg), false);
                if (info.code == 106) {
                    this.aKl.Gk();
                }
            }
        }
    };
    private TopicItem sK;

    private class a implements com.MCWorld.widget.dialog.i.a {
        final /* synthetic */ CommentTopicActivity aKl;

        private a(CommentTopicActivity commentTopicActivity) {
            this.aKl = commentTopicActivity;
        }

        public void ra() {
        }

        public void rd() {
            this.aKl.aKd.finish();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(2);
        this.aKk = true;
        this.aKd = this;
        this.sK = (TopicItem) getIntent().getSerializableExtra(r.gO);
        this.aKe = (CommentItem) getIntent().getParcelableExtra(ClientCookie.COMMENT_ATTR);
        this.aKf = (UserBaseInfo) getIntent().getParcelableExtra("user");
        FQ();
        a(findViewById(g.root_view), this.sK, this.aKe);
        EventNotifyCenter.add(h.class, this.mCallback);
    }

    protected void onDestroy() {
        super.onDestroy();
        EventNotifyCenter.remove(this.mCallback);
    }

    protected void onResume() {
        super.onResume();
        if (this.aKk) {
            UtilsScreen.showInputMethod(this.aLw, 1000);
            this.aKk = false;
        }
    }

    private void FQ() {
        findViewById(g.title_Text).setVisibility(8);
        this.aKg = (EmojiTextView) findViewById(g.quote_text);
        this.aKg.setVisibility(0);
        this.aKh = (TextView) findViewById(g.tip_media);
        this.aKi = findViewById(g.user_info);
        this.aKi.setVisibility(0);
        this.aLO.setVisibility(8);
        if (this.aKe == null) {
            ej("评论话题");
            if (this.sK != null && this.sK.getDetail() != null) {
                this.aKg.setText(aw.W(this.sK.getDetail(), 100));
                if (this.sK.getVoice() != null && this.sK.getVoice().length() > 0) {
                    this.aKh.setVisibility(0);
                    this.aKh.setText("【视频】");
                    return;
                } else if (UtilsFunction.empty(this.sK.getImages())) {
                    this.aKh.setVisibility(8);
                    return;
                } else {
                    this.aKh.setVisibility(0);
                    this.aKh.setText("【图片】");
                    return;
                }
            }
            return;
        }
        ej("回复评论");
        this.aKg.setText(aw.W(this.aKe.getText(), 100));
    }

    public void FR() {
        String detail = this.aLw.getText().toString();
        String patcha = this.aLB.getText().toString();
        if (this.sK == null) {
            HLog.error("CommentTopicActivity", "topicItem is null", new Object[0]);
            t.n(this, "帖子信息异常");
            return;
        }
        this.aIT.setEnabled(false);
        List images = new ArrayList();
        for (b photo : this.aLY.getExistPhotos()) {
            if (photo.fid != null) {
                images.add(photo.fid);
                HLog.verbose("CommentTopicActivity", "fid(%s)", photo.fid);
            }
        }
        long comment_id = 0;
        if (this.aKe != null) {
            comment_id = this.aKe.getCommentID();
        }
        cs(true);
        k.Ej().a(this.sK.getPostID(), comment_id, detail, patcha, images);
    }

    public void c(d response) {
        super.c(response);
        if (response.fe() == 2) {
            cs(false);
            this.aIT.setEnabled(true);
            if (response.getStatus() == 1) {
                cv(true);
                setResult(-1);
                if (response.getCode() == 201) {
                    f((String) response.getData(), true);
                    return;
                }
                t.o(this, (String) response.getData());
                finish();
            } else if (response.fg() == 104) {
                String title = "启禀陛下";
                if (!(response.fj() == null || response.fj().equals("null"))) {
                    title = response.fj();
                }
                String msg = ab.n(response.fg(), response.fh());
                i dia = new i(this, new a());
                dia.az(title, msg);
                dia.gK("朕知道了");
                dia.showDialog();
            } else {
                g(ab.n(response.fg(), response.fh()), false);
                if (response.fg() == 106) {
                    Gk();
                }
            }
        }
    }

    protected String FS() {
        return "CommentTopic";
    }

    protected void a(c draft) {
        if (!(this.aLw == null || this.aLw.getText() == null)) {
            draft.aC(this.aLw.getText().toString());
        }
        if (this.sK != null) {
            draft.m(this.sK.getPostID());
        }
        if (this.aLY != null) {
            draft.f(this.aLY.getPhotos());
        }
    }

    protected void b(c draft) {
        if (this.sK != null && draft != null && draft.eF() == this.sK.getPostID()) {
            String text = draft.eE();
            if (text != null) {
                this.aLw.setText(com.MCWorld.widget.emoInput.d.Ou().c(this.aLw.getContext(), text, at.dipToPx(this, 22), 0));
                this.aLw.setSelection(text.length());
                if (!UtilsFunction.empty(draft.getPhotos())) {
                    this.aLY.e(draft.getPhotos(), true);
                }
            }
        }
    }

    protected void a(com.simple.colorful.a.a builder) {
        super.a(builder);
        builder.a(this.aKg, 16842808);
    }

    private void a(View v, TopicItem topicItem, CommentItem commentItem) {
        UserBaseInfo info = null;
        TopicCategory category = null;
        if (commentItem != null) {
            info = this.aKf != null ? this.aKf : commentItem.getUserInfo();
            category = commentItem.getTopicCategory();
        } else if (topicItem != null) {
            info = this.aKf != null ? this.aKf : topicItem.getUserInfo();
            category = topicItem.getCategory();
        }
        if (info != null) {
            t.a((PaintView) v.findViewById(g.avatar), info.getAvatar(), (float) t.dipToPx(this.aKd, 5));
            a(v, info);
            b(v, info);
            c(v, info);
            d(v, info);
            as.a(this.aKd, (ImageView) v.findViewById(g.iv_role), info);
            a(v, info, category);
            v.findViewById(g.floor).setVisibility(8);
            v.findViewById(g.publish_time).setVisibility(8);
        }
    }

    private void a(View v, UserBaseInfo user) {
        String nickname;
        EmojiTextView nick = (EmojiTextView) v.findViewById(g.nick);
        if (user.getMedalList() == null || user.getMedalList().size() <= 0) {
            nickname = aw.go(user.getNick());
        } else {
            nickname = aw.W(user.getNick(), 10);
        }
        nick.setText(nickname);
        nick.setTextColor(as.a(v.getContext(), user));
    }

    private void a(View v, UserBaseInfo user, TopicCategory topicCategory) {
        View flag = v.findViewById(g.moderator_flag);
        ((GradientDrawable) flag.getBackground()).setColor(Color.argb(255, 51, 181, 229));
        flag.setVisibility(8);
        if (topicCategory != null && as.a(user.getUserID(), topicCategory.getModerator())) {
            flag.setVisibility(0);
        }
    }

    private void b(View view, UserBaseInfo user) {
        TextView user_age = (TextView) view.findViewById(g.user_age);
        user_age.setText(Integer.toString(user.getAge()));
        if (user.getGender() == 1) {
            user_age.setBackgroundResource(f.bg_gender_female);
            user_age.setCompoundDrawablesWithIntrinsicBounds(this.aKd.getResources().getDrawable(f.user_female), null, null, null);
            return;
        }
        user_age.setBackgroundResource(f.bg_gender_male);
        user_age.setCompoundDrawablesWithIntrinsicBounds(this.aKd.getResources().getDrawable(f.user_male), null, null, null);
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
                    t.b(medal0, ((Medal) user.getMedalList().get(i)).getUrl(), 0.0f);
                    break;
                case 1:
                    medal1.setVisibility(0);
                    t.b(medal1, ((Medal) user.getMedalList().get(i)).getUrl(), 0.0f);
                    break;
                case 2:
                    medal2.setVisibility(0);
                    t.b(medal2, ((Medal) user.getMedalList().get(i)).getUrl(), 0.0f);
                    break;
                case 3:
                    medal3.setVisibility(0);
                    t.b(medal3, ((Medal) user.getMedalList().get(i)).getUrl(), 0.0f);
                    break;
                case 4:
                    medal4.setVisibility(0);
                    t.b(medal4, ((Medal) user.getMedalList().get(i)).getUrl(), 0.0f);
                    break;
                case 5:
                    medal5.setVisibility(0);
                    t.b(medal5, ((Medal) user.getMedalList().get(i)).getUrl(), 0.0f);
                    break;
                default:
                    break;
            }
        }
    }

    private void e(View v, UserBaseInfo user) {
        TextView integral_title = (TextView) v.findViewById(g.integral_title);
        String integralNick = user.getIntegralNick();
        if (integralNick == null || integralNick.equals("") || integralNick.equals("null")) {
            integral_title.setVisibility(8);
            return;
        }
        integral_title.setVisibility(0);
        integral_title.setText(integralNick);
    }

    private void b(View v, String voice) {
        if (ad.empty((CharSequence) voice)) {
            v.findViewById(g.iv_video_tag).setVisibility(8);
        } else {
            v.findViewById(g.iv_video_tag).setVisibility(0);
        }
    }
}
