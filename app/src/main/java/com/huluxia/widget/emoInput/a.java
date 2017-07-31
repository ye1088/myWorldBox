package com.huluxia.widget.emoInput;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.style.ImageSpan;
import com.huluxia.bbs.b.c;
import com.huluxia.utils.z;
import com.simple.colorful.d;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.DecoderException;

/* compiled from: EmojiCodeParser */
public class a {
    private static final Long[] bxZ = new Long[]{Long.valueOf(4036991108L), Long.valueOf(4036991114L), Long.valueOf(4036991107L), Long.valueOf(14850234), Long.valueOf(4036991113L), Long.valueOf(4036991117L), Long.valueOf(4036991128L), Long.valueOf(4036991130L), Long.valueOf(4036991155L), Long.valueOf(4036991124L), Long.valueOf(4036991105L), Long.valueOf(4036991132L), Long.valueOf(4036991133L), Long.valueOf(4036991122L), Long.valueOf(4036991119L), Long.valueOf(4036991123L), Long.valueOf(4036991124L), Long.valueOf(4036991134L), Long.valueOf(4036991126L), Long.valueOf(4036991141L), Long.valueOf(4036991152L), Long.valueOf(4036991144L), Long.valueOf(4036991139L), Long.valueOf(4036991138L), Long.valueOf(4036991149L), Long.valueOf(4036991106L), Long.valueOf(4036991154L), Long.valueOf(4036991153L), Long.valueOf(4036991136L), Long.valueOf(4036991137L), Long.valueOf(4036991146L), Long.valueOf(4036991159L), Long.valueOf(4036989375L), Long.valueOf(4036989373L), Long.valueOf(4036989595L), Long.valueOf(4036989593L), Long.valueOf(4036989596L), Long.valueOf(4036989591L), Long.valueOf(4036989594L), Long.valueOf(14851492), Long.valueOf(4036989588L), Long.valueOf(4036989587L), Long.valueOf(4036989592L), Long.valueOf(14851240), Long.valueOf(4036988063L), Long.valueOf(4036989602L), Long.valueOf(14851477), Long.valueOf(14851476), Long.valueOf(4036989604L), Long.valueOf(4036989608L), Long.valueOf(4036989606L), Long.valueOf(4036988598L), Long.valueOf(4036988597L), Long.valueOf(4036990117L), Long.valueOf(4036989609L), Long.valueOf(4036989325L), Long.valueOf(4036989326L), Long.valueOf(4036989324L), Long.valueOf(4036989322L), Long.valueOf(14851210), Long.valueOf(14851212), Long.valueOf(4036989323L), Long.valueOf(14851211)};
    private static final Map<Long, Integer> bya = new EmojiCodeParser$1();
    private List<Long> bxV;
    private Map<Long, String> bxW = new HashMap();
    private String bxX = "(";
    private Pattern bxY = null;

    public a() {
        DecoderException e;
        String s = null;
        for (Entry<Long, Integer> entry : bya.entrySet()) {
            try {
                String s2 = new String(z.decodeHex(String.format(Locale.getDefault(), "%x", new Object[]{Long.valueOf(((Long) entry.getKey()).longValue())}).toCharArray()));
                try {
                    this.bxW.put(Long.valueOf(((Long) entry.getKey()).longValue()), s2);
                    this.bxX += s2 + "|";
                    s = s2;
                } catch (DecoderException e2) {
                    e = e2;
                    s = s2;
                    e.printStackTrace();
                }
            } catch (DecoderException e3) {
                e = e3;
                e.printStackTrace();
            }
        }
        this.bxX += s + ")";
        this.bxY = Pattern.compile(this.bxX);
        this.bxV = Arrays.asList(bxZ);
    }

    public String bJ(long code) {
        if (this.bxW.containsKey(Long.valueOf(code))) {
            return (String) this.bxW.get(Long.valueOf(code));
        }
        return "";
    }

    public int bK(long code) {
        return ((Integer) bya.get(Long.valueOf(code))).intValue();
    }

    public List<Long> getTags() {
        return this.bxV;
    }

    public int gN(String text) {
        int result = 0;
        while (Pattern.compile(this.bxX).matcher(text).find()) {
            result++;
        }
        return result;
    }

    public void a(Context context, Spannable sp, String text, int faceWidth, int verticalAlignment) {
        Matcher m = this.bxY.matcher(text);
        while (m.find()) {
            try {
                Drawable drw = context.getResources().getDrawable(bK(Long.parseLong(z.encodeHexString(m.group(1).getBytes("utf-8")), 16)));
                if (drw != null) {
                    drw.setBounds(0, 0, faceWidth, faceWidth);
                    a(context, drw);
                    sp.setSpan(new ImageSpan(drw, verticalAlignment), m.start(), m.end(), 33);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void a(Context context, Drawable drawable) {
        int brightness = d.s(context, c.valBrightness);
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(new float[]{1.0f, 0.0f, 0.0f, 0.0f, (float) brightness, 0.0f, 1.0f, 0.0f, 0.0f, (float) brightness, 0.0f, 0.0f, 1.0f, 0.0f, (float) brightness, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
        if (drawable != null) {
            drawable.setColorFilter(new ColorMatrixColorFilter(cMatrix));
        }
    }
}
