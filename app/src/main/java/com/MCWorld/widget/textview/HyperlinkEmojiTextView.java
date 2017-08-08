package com.MCWorld.widget.textview;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.View;
import com.MCWorld.bbs.b;
import com.MCWorld.data.UserBaseInfo;
import com.MCWorld.t;
import com.MCWorld.utils.ba$a;
import com.MCWorld.widget.textview.HyperlinkTextView.c;
import com.simple.colorful.d;
import java.util.List;

public class HyperlinkEmojiTextView extends EmojiTextView implements a {
    private a bFn;
    private Context mContext;

    private class a extends ClickableSpan {
        private String bFo;
        private UserBaseInfo bFp;
        final /* synthetic */ HyperlinkEmojiTextView bFq;

        public a(HyperlinkEmojiTextView hyperlinkEmojiTextView, String clickedString, UserBaseInfo info) {
            this.bFq = hyperlinkEmojiTextView;
            this.bFo = clickedString;
            this.bFp = info;
        }

        public void onClick(View textView) {
            if (this.bFq.bFn != null) {
                this.bFq.bFn.a(textView, this.bFo, this.bFp);
            }
        }

        public void updateDrawState(TextPaint ds) {
            ds.bgColor = 0;
            ds.setUnderlineText(false);
        }
    }

    public HyperlinkEmojiTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        PW();
        setNickLinkClickListener(this);
    }

    public HyperlinkEmojiTextView(Context context) {
        super(context);
        PW();
    }

    private void PW() {
        setClickable(true);
        setMovementMethod(c.PX());
    }

    public void setText(List<UserBaseInfo> userList) {
        super.setText(N(userList));
    }

    private Spannable N(List<UserBaseInfo> userList) {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        int start = 0;
        for (int i = 0; i < userList.size(); i++) {
            String nick = ((UserBaseInfo) userList.get(i)).getNick();
            if (i == 0) {
                nick = " " + nick;
            }
            int color = d.getColor(this.mContext, b.c.textColorTopicAltUser);
            ForegroundColorSpan fSpan = new ForegroundColorSpan(color);
            a urlSpan = new a(this, nick, (UserBaseInfo) userList.get(i));
            ssb.insert(start, nick);
            ssb.setSpan(fSpan, start, nick.length() + start, 17);
            ssb.setSpan(urlSpan, start, nick.length() + start, 17);
            start += nick.length();
            if (i != userList.size() - 1) {
                ForegroundColorSpan fSpan1 = new ForegroundColorSpan(color);
                ssb.insert(start, "、");
                ssb.setSpan(fSpan1, start, start + 1, 17);
            }
            start++;
        }
        ba$a span = new ba$a(this.mContext, d.r(this.mContext, b.c.drawableTopicAltUser));
        ssb.insert(0, "1");
        ssb.setSpan(span, 0, 1, 33);
        return ssb;
    }

    public void a(View view, String clickedString, UserBaseInfo info) {
        t.a(this.mContext, info.getUserID(), info);
    }

    private void setNickLinkClickListener(a listener) {
        this.bFn = listener;
    }
}
