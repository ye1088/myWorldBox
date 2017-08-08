package com.MCWorld.widget.textview;

import android.content.Context;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.method.Touch;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import com.MCWorld.t;
import com.MCWorld.widget.emoInput.d;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HyperlinkTextView extends TextView implements b {
    private ArrayList<a> bFr;
    b bFs;
    Pattern bFt;
    boolean bFu;
    boolean bFv;

    class a {
        CharSequence bFw;
        b bFx;
        final /* synthetic */ HyperlinkTextView bFy;
        int end;
        int start;

        a(HyperlinkTextView this$0) {
            this.bFy = this$0;
        }
    }

    public class b extends ClickableSpan {
        private String bFo;
        final /* synthetic */ HyperlinkTextView bFy;

        public b(HyperlinkTextView this$0, String clickedString) {
            this.bFy = this$0;
            this.bFo = clickedString;
        }

        public void onClick(View textView) {
            if (this.bFy.bFs != null) {
                this.bFy.bFs.c(textView, this.bFo);
            }
        }

        public void updateDrawState(TextPaint ds) {
            ds.setColor(ds.linkColor);
            ds.bgColor = 0;
            ds.setUnderlineText(false);
        }
    }

    public static class c extends LinkMovementMethod {
        static c bFz;

        public static c PX() {
            if (bFz == null) {
                bFz = new c();
            }
            return bFz;
        }

        public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
            int action = event.getAction();
            if (action != 1 && action != 0) {
                return Touch.onTouchEvent(widget, buffer, event);
            }
            int x = (((int) event.getX()) - widget.getTotalPaddingLeft()) + widget.getScrollX();
            int y = (((int) event.getY()) - widget.getTotalPaddingTop()) + widget.getScrollY();
            Layout layout = widget.getLayout();
            int off = layout.getOffsetForHorizontal(layout.getLineForVertical(y), (float) x);
            ClickableSpan[] link = (ClickableSpan[]) buffer.getSpans(off, off, ClickableSpan.class);
            if (link.length != 0) {
                if (action == 1) {
                    link[0].onClick(widget);
                } else if (action == 0) {
                    Selection.setSelection(buffer, buffer.getSpanStart(link[0]), buffer.getSpanEnd(link[0]));
                }
                if (!(widget instanceof HyperlinkTextView)) {
                    return true;
                }
                ((HyperlinkTextView) widget).bFv = true;
                return true;
            }
            Selection.removeSelection(buffer);
            Touch.onTouchEvent(widget, buffer, event);
            return false;
        }
    }

    public HyperlinkTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.bFt = Pattern.compile("(http://|ftp://|https://|www|bbs)[^一-龥\\s]*?[^一-龥\\s]*");
        this.bFu = true;
    }

    public HyperlinkTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.bFt = Pattern.compile("(http://|ftp://|https://|www|bbs)[^一-龥\\s]*?[^一-龥\\s]*");
        this.bFu = true;
        this.bFr = new ArrayList();
        setOnTextLinkClickListener(this);
    }

    public HyperlinkTextView(Context context) {
        super(context);
        this.bFt = Pattern.compile("(http://|ftp://|https://|www|bbs)[^一-龥\\s]*?[^一-龥\\s]*");
        this.bFu = true;
    }

    public void c(View view, String clickedString) {
        Log.i("HyperlinkTextView", clickedString);
        try {
            String url = URLEncoder.encode(clickedString, "UTF-8");
            t.f(view.getContext(), clickedString, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setText(String text) {
        if (text != null) {
            super.setText(a(d.Ou().e(getContext(), text + " ", (int) getTextSize())));
            if (!this.bFr.isEmpty()) {
                setLinkTextColor(-16743475);
                setHighlightColor(-13214303);
                setClickable(true);
                setMovementMethod(c.PX());
            }
        }
    }

    public boolean hasFocusable() {
        return false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        this.bFv = false;
        boolean res = super.onTouchEvent(event);
        if (this.bFu) {
            return this.bFv;
        }
        return res;
    }

    private Spannable a(Spannable linkableText) {
        a(this.bFr, linkableText, this.bFt);
        for (int i = 0; i < this.bFr.size(); i++) {
            a linkSpec = (a) this.bFr.get(i);
            Log.v("listOfLinks :: " + linkSpec.bFw, "'listOfLinks :: " + linkSpec.bFw);
            linkableText.setSpan(linkSpec.bFx, linkSpec.start, linkSpec.end, 33);
        }
        return linkableText;
    }

    public void setOnTextLinkClickListener(b newListener) {
        this.bFs = newListener;
    }

    private final void a(ArrayList<a> links, Spannable s, Pattern pattern) {
        links.clear();
        Matcher m = pattern.matcher(s);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            a spec = new a(this);
            spec.bFw = s.subSequence(start, end);
            spec.bFx = new b(this, spec.bFw.toString());
            spec.start = start;
            spec.end = end;
            links.add(spec);
        }
    }
}
