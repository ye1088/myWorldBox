package com.huluxia.mcsdk.log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.DateFormatSymbols;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DTFastDateFormat extends Format {
    public static final int FULL = 0;
    public static final int LONG = 1;
    public static final int MEDIUM = 2;
    public static final int SHORT = 3;
    private static ConcurrentMap<g, String> cTimeZoneDisplayCache = new ConcurrentHashMap(7);
    private static final b<DTFastDateFormat> cache = new b<DTFastDateFormat>() {
        protected /* synthetic */ Format createInstance(String str, TimeZone timeZone, Locale locale) {
            return a(str, timeZone, locale);
        }

        protected DTFastDateFormat a(String pattern, TimeZone timeZone, Locale locale) {
            return new DTFastDateFormat(pattern, timeZone, locale);
        }
    };
    private static final long serialVersionUID = 1;
    private final Locale mLocale;
    private transient int mMaxLengthEstimate;
    private final String mPattern;
    private transient d[] mRules;
    private final TimeZone mTimeZone;

    private interface d {
        void appendTo(StringBuffer stringBuffer, Calendar calendar);

        int estimateLength();
    }

    private static class a implements d {
        private final char mValue;

        a(char value) {
            this.mValue = value;
        }

        public int estimateLength() {
            return 1;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            buffer.append(this.mValue);
        }
    }

    private interface b extends d {
        void appendTo(StringBuffer stringBuffer, int i);
    }

    private static class c implements b {
        private final int mField;
        private final int mSize;

        c(int field, int size) {
            if (size < 3) {
                throw new IllegalArgumentException();
            }
            this.mField = field;
            this.mSize = size;
        }

        public int estimateLength() {
            return 4;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            appendTo(buffer, calendar.get(this.mField));
        }

        public final void appendTo(StringBuffer buffer, int value) {
            int i;
            if (value < 100) {
                i = this.mSize;
                while (true) {
                    i--;
                    if (i >= 2) {
                        buffer.append('0');
                    } else {
                        buffer.append((char) ((value / 10) + 48));
                        buffer.append((char) ((value % 10) + 48));
                        return;
                    }
                }
            }
            int digits;
            if (value < 1000) {
                digits = 3;
            } else {
                digits = Integer.toString(value).length();
            }
            i = this.mSize;
            while (true) {
                i--;
                if (i >= digits) {
                    buffer.append('0');
                } else {
                    buffer.append(Integer.toString(value));
                    return;
                }
            }
        }
    }

    private static class e implements d {
        private final String mValue;

        e(String value) {
            this.mValue = value;
        }

        public int estimateLength() {
            return this.mValue.length();
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            buffer.append(this.mValue);
        }
    }

    private static class f implements d {
        private final int mField;
        private final String[] mValues;

        f(int field, String[] values) {
            this.mField = field;
            this.mValues = values;
        }

        public int estimateLength() {
            int max = 0;
            int i = this.mValues.length;
            while (true) {
                i--;
                if (i < 0) {
                    return max;
                }
                int len = this.mValues[i].length();
                if (len > max) {
                    max = len;
                }
            }
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            buffer.append(this.mValues[calendar.get(this.mField)]);
        }
    }

    private static class g {
        private final Locale mLocale;
        private final int mStyle;
        private final TimeZone mTimeZone;

        g(TimeZone timeZone, boolean daylight, int style, Locale locale) {
            this.mTimeZone = timeZone;
            if (daylight) {
                style |= Integer.MIN_VALUE;
            }
            this.mStyle = style;
            this.mLocale = locale;
        }

        public int hashCode() {
            return (((this.mStyle * 31) + this.mLocale.hashCode()) * 31) + this.mTimeZone.hashCode();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof g)) {
                return false;
            }
            g other = (g) obj;
            if (this.mTimeZone.equals(other.mTimeZone) && this.mStyle == other.mStyle && this.mLocale.equals(other.mLocale)) {
                return true;
            }
            return false;
        }
    }

    private static class h implements d {
        private final String mDaylight;
        private final String mStandard;
        private final TimeZone mTimeZone;

        h(TimeZone timeZone, Locale locale, int style) {
            this.mTimeZone = timeZone;
            this.mStandard = DTFastDateFormat.getTimeZoneDisplay(timeZone, false, style, locale);
            this.mDaylight = DTFastDateFormat.getTimeZoneDisplay(timeZone, true, style, locale);
        }

        public int estimateLength() {
            return Math.max(this.mStandard.length(), this.mDaylight.length());
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            if (!this.mTimeZone.useDaylightTime() || calendar.get(16) == 0) {
                buffer.append(this.mStandard);
            } else {
                buffer.append(this.mDaylight);
            }
        }
    }

    private static class i implements d {
        static final i aoT = new i(true);
        static final i aoU = new i(false);
        final boolean mColon;

        i(boolean colon) {
            this.mColon = colon;
        }

        public int estimateLength() {
            return 5;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            int offset = calendar.get(15) + calendar.get(16);
            if (offset < 0) {
                buffer.append('-');
                offset = -offset;
            } else {
                buffer.append('+');
            }
            int hours = offset / 3600000;
            buffer.append((char) ((hours / 10) + 48));
            buffer.append((char) ((hours % 10) + 48));
            if (this.mColon) {
                buffer.append(':');
            }
            int minutes = (offset / com.huluxia.image.core.common.util.e.yQ) - (hours * 60);
            buffer.append((char) ((minutes / 10) + 48));
            buffer.append((char) ((minutes % 10) + 48));
        }
    }

    private static class j implements b {
        private final b aoV;

        j(b rule) {
            this.aoV = rule;
        }

        public int estimateLength() {
            return this.aoV.estimateLength();
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            int value = calendar.get(10);
            if (value == 0) {
                value = calendar.getLeastMaximum(10) + 1;
            }
            this.aoV.appendTo(buffer, value);
        }

        public void appendTo(StringBuffer buffer, int value) {
            this.aoV.appendTo(buffer, value);
        }
    }

    private static class k implements b {
        private final b aoV;

        k(b rule) {
            this.aoV = rule;
        }

        public int estimateLength() {
            return this.aoV.estimateLength();
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            int value = calendar.get(11);
            if (value == 0) {
                value = calendar.getMaximum(11) + 1;
            }
            this.aoV.appendTo(buffer, value);
        }

        public void appendTo(StringBuffer buffer, int value) {
            this.aoV.appendTo(buffer, value);
        }
    }

    private static class l implements b {
        static final l aoW = new l();

        l() {
        }

        public int estimateLength() {
            return 2;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            appendTo(buffer, calendar.get(2) + 1);
        }

        public final void appendTo(StringBuffer buffer, int value) {
            buffer.append((char) ((value / 10) + 48));
            buffer.append((char) ((value % 10) + 48));
        }
    }

    private static class m implements b {
        private final int mField;

        m(int field) {
            this.mField = field;
        }

        public int estimateLength() {
            return 2;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            appendTo(buffer, calendar.get(this.mField));
        }

        public final void appendTo(StringBuffer buffer, int value) {
            if (value < 100) {
                buffer.append((char) ((value / 10) + 48));
                buffer.append((char) ((value % 10) + 48));
                return;
            }
            buffer.append(Integer.toString(value));
        }
    }

    private static class n implements b {
        static final n aoX = new n();

        n() {
        }

        public int estimateLength() {
            return 2;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            appendTo(buffer, calendar.get(1) % 100);
        }

        public final void appendTo(StringBuffer buffer, int value) {
            buffer.append((char) ((value / 10) + 48));
            buffer.append((char) ((value % 10) + 48));
        }
    }

    private static class o implements b {
        static final o aoY = new o();

        o() {
        }

        public int estimateLength() {
            return 2;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            appendTo(buffer, calendar.get(2) + 1);
        }

        public final void appendTo(StringBuffer buffer, int value) {
            if (value < 10) {
                buffer.append((char) (value + 48));
                return;
            }
            buffer.append((char) ((value / 10) + 48));
            buffer.append((char) ((value % 10) + 48));
        }
    }

    private static class p implements b {
        private final int mField;

        p(int field) {
            this.mField = field;
        }

        public int estimateLength() {
            return 4;
        }

        public void appendTo(StringBuffer buffer, Calendar calendar) {
            appendTo(buffer, calendar.get(this.mField));
        }

        public final void appendTo(StringBuffer buffer, int value) {
            if (value < 10) {
                buffer.append((char) (value + 48));
            } else if (value < 100) {
                buffer.append((char) ((value / 10) + 48));
                buffer.append((char) ((value % 10) + 48));
            } else {
                buffer.append(Integer.toString(value));
            }
        }
    }

    public static DTFastDateFormat getInstance() {
        return (DTFastDateFormat) cache.getDateTimeInstance(Integer.valueOf(3), Integer.valueOf(3), null, null);
    }

    public static DTFastDateFormat getInstance(String pattern) {
        return (DTFastDateFormat) cache.getInstance(pattern, null, null);
    }

    public static DTFastDateFormat getInstance(String pattern, TimeZone timeZone) {
        return (DTFastDateFormat) cache.getInstance(pattern, timeZone, null);
    }

    public static DTFastDateFormat getInstance(String pattern, Locale locale) {
        return (DTFastDateFormat) cache.getInstance(pattern, null, locale);
    }

    public static DTFastDateFormat getInstance(String pattern, TimeZone timeZone, Locale locale) {
        return (DTFastDateFormat) cache.getInstance(pattern, timeZone, locale);
    }

    public static DTFastDateFormat getDateInstance(int style) {
        return (DTFastDateFormat) cache.getDateTimeInstance(Integer.valueOf(style), null, null, null);
    }

    public static DTFastDateFormat getDateInstance(int style, Locale locale) {
        return (DTFastDateFormat) cache.getDateTimeInstance(Integer.valueOf(style), null, null, locale);
    }

    public static DTFastDateFormat getDateInstance(int style, TimeZone timeZone) {
        return (DTFastDateFormat) cache.getDateTimeInstance(Integer.valueOf(style), null, timeZone, null);
    }

    public static DTFastDateFormat getDateInstance(int style, TimeZone timeZone, Locale locale) {
        return (DTFastDateFormat) cache.getDateTimeInstance(Integer.valueOf(style), null, timeZone, locale);
    }

    public static DTFastDateFormat getTimeInstance(int style) {
        return (DTFastDateFormat) cache.getDateTimeInstance(null, Integer.valueOf(style), null, null);
    }

    public static DTFastDateFormat getTimeInstance(int style, Locale locale) {
        return (DTFastDateFormat) cache.getDateTimeInstance(null, Integer.valueOf(style), null, locale);
    }

    public static DTFastDateFormat getTimeInstance(int style, TimeZone timeZone) {
        return (DTFastDateFormat) cache.getDateTimeInstance(null, Integer.valueOf(style), timeZone, null);
    }

    public static DTFastDateFormat getTimeInstance(int style, TimeZone timeZone, Locale locale) {
        return (DTFastDateFormat) cache.getDateTimeInstance(null, Integer.valueOf(style), timeZone, locale);
    }

    public static DTFastDateFormat getDateTimeInstance(int dateStyle, int timeStyle) {
        return (DTFastDateFormat) cache.getDateTimeInstance(Integer.valueOf(dateStyle), Integer.valueOf(timeStyle), null, null);
    }

    public static DTFastDateFormat getDateTimeInstance(int dateStyle, int timeStyle, Locale locale) {
        return (DTFastDateFormat) cache.getDateTimeInstance(Integer.valueOf(dateStyle), Integer.valueOf(timeStyle), null, locale);
    }

    public static DTFastDateFormat getDateTimeInstance(int dateStyle, int timeStyle, TimeZone timeZone) {
        return getDateTimeInstance(dateStyle, timeStyle, timeZone, null);
    }

    public static DTFastDateFormat getDateTimeInstance(int dateStyle, int timeStyle, TimeZone timeZone, Locale locale) {
        return (DTFastDateFormat) cache.getDateTimeInstance(Integer.valueOf(dateStyle), Integer.valueOf(timeStyle), timeZone, locale);
    }

    static String getTimeZoneDisplay(TimeZone tz, boolean daylight, int style, Locale locale) {
        g key = new g(tz, daylight, style, locale);
        String value = (String) cTimeZoneDisplayCache.get(key);
        if (value != null) {
            return value;
        }
        value = tz.getDisplayName(daylight, style, locale);
        String prior = (String) cTimeZoneDisplayCache.putIfAbsent(key, value);
        if (prior != null) {
            return prior;
        }
        return value;
    }

    protected DTFastDateFormat(String pattern, TimeZone timeZone, Locale locale) {
        this.mPattern = pattern;
        this.mTimeZone = timeZone;
        this.mLocale = locale;
        init();
    }

    private void init() {
        List<d> rulesList = parsePattern();
        this.mRules = (d[]) rulesList.toArray(new d[rulesList.size()]);
        int len = 0;
        int i = this.mRules.length;
        while (true) {
            i--;
            if (i >= 0) {
                len += this.mRules[i].estimateLength();
            } else {
                this.mMaxLengthEstimate = len;
                return;
            }
        }
    }

    protected List<d> parsePattern() {
        DateFormatSymbols symbols = new DateFormatSymbols(this.mLocale);
        List<d> rules = new ArrayList();
        String[] ERAs = symbols.getEras();
        String[] months = symbols.getMonths();
        String[] shortMonths = symbols.getShortMonths();
        String[] weekdays = symbols.getWeekdays();
        String[] shortWeekdays = symbols.getShortWeekdays();
        String[] AmPmStrings = symbols.getAmPmStrings();
        int length = this.mPattern.length();
        int[] indexRef = new int[1];
        int i = 0;
        while (i < length) {
            indexRef[0] = i;
            String token = parseToken(this.mPattern, indexRef);
            i = indexRef[0];
            int tokenLen = token.length();
            if (tokenLen == 0) {
                return rules;
            }
            d rule;
            switch (token.charAt(0)) {
                case '\'':
                    String sub = token.substring(1);
                    if (sub.length() != 1) {
                        rule = new e(sub);
                        break;
                    }
                    rule = new a(sub.charAt(0));
                    break;
                case 'D':
                    rule = selectNumberRule(6, tokenLen);
                    break;
                case 'E':
                    String[] strArr;
                    if (tokenLen < 4) {
                        strArr = shortWeekdays;
                    } else {
                        strArr = weekdays;
                    }
                    rule = new f(7, strArr);
                    break;
                case 'F':
                    rule = selectNumberRule(8, tokenLen);
                    break;
                case 'G':
                    rule = new f(0, ERAs);
                    break;
                case 'H':
                    rule = selectNumberRule(11, tokenLen);
                    break;
                case 'K':
                    rule = selectNumberRule(10, tokenLen);
                    break;
                case 'M':
                    if (tokenLen < 4) {
                        if (tokenLen != 3) {
                            if (tokenLen != 2) {
                                rule = o.aoY;
                                break;
                            }
                            rule = l.aoW;
                            break;
                        }
                        rule = new f(2, shortMonths);
                        break;
                    }
                    rule = new f(2, months);
                    break;
                case 'S':
                    rule = selectNumberRule(14, tokenLen);
                    break;
                case 'W':
                    rule = selectNumberRule(4, tokenLen);
                    break;
                case 'Z':
                    if (tokenLen != 1) {
                        rule = i.aoT;
                        break;
                    }
                    rule = i.aoU;
                    break;
                case 'a':
                    rule = new f(9, AmPmStrings);
                    break;
                case 'd':
                    rule = selectNumberRule(5, tokenLen);
                    break;
                case 'h':
                    rule = new j(selectNumberRule(10, tokenLen));
                    break;
                case 'k':
                    rule = new k(selectNumberRule(11, tokenLen));
                    break;
                case 'm':
                    rule = selectNumberRule(12, tokenLen);
                    break;
                case 's':
                    rule = selectNumberRule(13, tokenLen);
                    break;
                case 'w':
                    rule = selectNumberRule(3, tokenLen);
                    break;
                case 'y':
                    if (tokenLen != 2) {
                        if (tokenLen < 4) {
                            tokenLen = 4;
                        }
                        rule = selectNumberRule(1, tokenLen);
                        break;
                    }
                    rule = n.aoX;
                    break;
                case 'z':
                    if (tokenLen < 4) {
                        rule = new h(this.mTimeZone, this.mLocale, 0);
                        break;
                    }
                    rule = new h(this.mTimeZone, this.mLocale, 1);
                    break;
                default:
                    throw new IllegalArgumentException("Illegal pattern component: " + token);
            }
            rules.add(rule);
            i++;
        }
        return rules;
    }

    protected String parseToken(String pattern, int[] indexRef) {
        StringBuilder buf = new StringBuilder();
        int i = indexRef[0];
        int length = pattern.length();
        char c = pattern.charAt(i);
        if ((c < 'A' || c > 'Z') && (c < 'a' || c > 'z')) {
            buf.append('\'');
            boolean inLiteral = false;
            while (i < length) {
                c = pattern.charAt(i);
                if (c != '\'') {
                    if (!inLiteral && ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))) {
                        i--;
                        break;
                    }
                    buf.append(c);
                } else if (i + 1 >= length || pattern.charAt(i + 1) != '\'') {
                    inLiteral = !inLiteral;
                } else {
                    i++;
                    buf.append(c);
                }
                i++;
            }
        } else {
            buf.append(c);
            while (i + 1 < length && pattern.charAt(i + 1) == c) {
                buf.append(c);
                i++;
            }
        }
        indexRef[0] = i;
        return buf.toString();
    }

    protected b selectNumberRule(int field, int padding) {
        switch (padding) {
            case 1:
                return new p(field);
            case 2:
                return new m(field);
            default:
                return new c(field, padding);
        }
    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        if (obj instanceof Date) {
            return format((Date) obj, toAppendTo);
        }
        if (obj instanceof Calendar) {
            return format((Calendar) obj, toAppendTo);
        }
        if (obj instanceof Long) {
            return format(((Long) obj).longValue(), toAppendTo);
        }
        String str;
        StringBuilder append = new StringBuilder().append("Unknown class: ");
        if (obj == null) {
            str = "<null>";
        } else {
            str = obj.getClass().getName();
        }
        throw new IllegalArgumentException(append.append(str).toString());
    }

    public String format(long millis) {
        return format(new Date(millis));
    }

    public String format(Date date) {
        Calendar c = new GregorianCalendar(this.mTimeZone, this.mLocale);
        c.setTime(date);
        return applyRules(c, new StringBuffer(this.mMaxLengthEstimate)).toString();
    }

    public String format(Calendar calendar) {
        return format(calendar, new StringBuffer(this.mMaxLengthEstimate)).toString();
    }

    public StringBuffer format(long millis, StringBuffer buf) {
        return format(new Date(millis), buf);
    }

    public StringBuffer format(Date date, StringBuffer buf) {
        Calendar c = new GregorianCalendar(this.mTimeZone, this.mLocale);
        c.setTime(date);
        return applyRules(c, buf);
    }

    public StringBuffer format(Calendar calendar, StringBuffer buf) {
        return applyRules(calendar, buf);
    }

    protected StringBuffer applyRules(Calendar calendar, StringBuffer buf) {
        for (d rule : this.mRules) {
            rule.appendTo(buf, calendar);
        }
        return buf;
    }

    public Object parseObject(String source, ParsePosition pos) {
        pos.setIndex(0);
        pos.setErrorIndex(0);
        return null;
    }

    public String getPattern() {
        return this.mPattern;
    }

    public TimeZone getTimeZone() {
        return this.mTimeZone;
    }

    public Locale getLocale() {
        return this.mLocale;
    }

    public int getMaxLengthEstimate() {
        return this.mMaxLengthEstimate;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DTFastDateFormat)) {
            return false;
        }
        DTFastDateFormat other = (DTFastDateFormat) obj;
        if (this.mPattern.equals(other.mPattern) && this.mTimeZone.equals(other.mTimeZone) && this.mLocale.equals(other.mLocale)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.mPattern.hashCode() + ((this.mTimeZone.hashCode() + (this.mLocale.hashCode() * 13)) * 13);
    }

    public String toString() {
        return "FastDateFormat[" + this.mPattern + "]";
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        init();
    }
}
