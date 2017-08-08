package com.MCWorld.widget.emoInput;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.style.ImageSpan;
import com.MCWorld.framework.base.log.HLog;
import com.simple.colorful.d;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: ExpressionParser */
public class c {
    public static final String byf = "[删除]";
    private static final String[] byg = new String[]{"[呵呵]", "[哈哈]", "[吐舌]", "[啊]", "[酷]", "[怒]", "[开心]", "[汗]", "[泪]", "[黑线]", "[鄙视]", "[不高兴]", "[真棒]", "[钱]", "[疑问]", "[阴险]", "[吐]", "[咦]", "[委屈]", "[花心]", "[呼~]", "[笑眼]", "[冷]", "[太开心]", "[滑稽]", "[勉强]", "[狂汗]", "[乖]", "[睡觉]", "[惊哭]", "[升起]", "[惊讶]", "[喷]", "[爱心]", "[心碎]", "[玫瑰]", "[礼物]", "[彩虹]", "[星星月亮]", "[太阳]", "[铅笔]", "[灯泡]", "[茶杯]", "[蛋糕]", "[音乐]", "[haha]", "[胜利]", "[大拇指]", "[弱]", "[OK]", "[赖皮]", "[感动]", "[十分惊讶]", "[怒气]", "[哭泣]", "[吃惊]", "[嘲弄]", "[飘过]", "[转圈哭]", "[神经病]", "[揪耳朵]", "[惊汗]", "[隐身]", "[不要嘛]", "[遁]", "[不公平]", "[爬来了]", "[蛋花哭]", "[温柔]", "[点头]", "[撒钱]", "[献花]", "[寒]", "[傻笑]", "[扭扭]", "[疯]", "[抓狂]", "[抓]", "[蜷]", "[挠墙]", "[狂笑]", "[抱枕]", "[吼叫]", "[嚷]", "[唠叨]", "[捏脸]", "[爆笑]", "[郁闷]", "[潜水]", "[十分开心]", "[冷笑话]", "[顶!]", "[潜]", "[画圈圈]", "[玩电脑]", "[狂吐]", "[哭着跑]", "[阿狸侠]", "[冷死了]", "[惆怅]", "[摸头]", "[蹭]", "[打滚]", "[叩拜]", "[摸]", "[数钱]", "[拖走]", "[热]", "[加1]", "[压力]", "[表逼我]", "[人呢]", "[摇晃]", "[打地鼠]", "[这个屌]", "[恐慌]", "[晕乎乎]", "[浮云]", "[给力]", "[杯具了]"};
    private static final Map<String, Integer> byh = new ExpressionParser$1();
    private List<String> bxV;
    private String bxX = "(";
    private Pattern bxY = null;

    public c() {
        String strCode = null;
        for (Entry<String, Integer> entry : byh.entrySet()) {
            strCode = ((String) entry.getKey()).replace("[", "\\[").replace("]", "\\]");
            this.bxX += strCode + "|";
        }
        this.bxX += strCode + ")";
        this.bxY = Pattern.compile(this.bxX);
        this.bxV = Arrays.asList(byg);
    }

    public List<String> getTags() {
        return this.bxV;
    }

    public int gO(String code) {
        if (byh.containsKey(code)) {
            return ((Integer) byh.get(code)).intValue();
        }
        return 0;
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
                Drawable drw = context.getResources().getDrawable(gO(m.group(1)));
                if (drw != null) {
                    drw.setBounds(0, 0, faceWidth, faceWidth);
                    a(context, drw);
                    sp.setSpan(new ImageSpan(drw, verticalAlignment), m.start(), m.end(), 33);
                }
            } catch (Exception e) {
                HLog.error("", "parsetext error text = " + text, new Object[0]);
            }
        }
    }

    private void a(Context context, Drawable drawable) {
        int brightness = d.s(context, com.MCWorld.bbs.b.c.valBrightness);
        ColorMatrix cMatrix = new ColorMatrix();
        cMatrix.set(new float[]{1.0f, 0.0f, 0.0f, 0.0f, (float) brightness, 0.0f, 1.0f, 0.0f, 0.0f, (float) brightness, 0.0f, 0.0f, 1.0f, 0.0f, (float) brightness, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f});
        if (drawable != null) {
            drawable.setColorFilter(new ColorMatrixColorFilter(cMatrix));
        }
    }
}
