package com.MCWorld.mcsdk.log;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* compiled from: DTFormatCache */
abstract class b<F extends Format> {
    static final int NONE = -1;
    private final ConcurrentMap<a, String> cDateTimeInstanceCache = new ConcurrentHashMap(7);
    private final ConcurrentMap<a, F> cInstanceCache = new ConcurrentHashMap(7);

    /* compiled from: DTFormatCache */
    private static class a {
        private int hashCode;
        private final Object[] keys;

        public a(Object... keys) {
            this.keys = keys;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof a) {
                return Arrays.equals(this.keys, ((a) obj).keys);
            }
            return false;
        }

        public int hashCode() {
            if (this.hashCode == 0) {
                int rc = 0;
                for (Object key : this.keys) {
                    if (key != null) {
                        rc = (rc * 7) + key.hashCode();
                    }
                }
                this.hashCode = rc;
            }
            return this.hashCode;
        }
    }

    protected abstract F createInstance(String str, TimeZone timeZone, Locale locale);

    b() {
    }

    public F getInstance() {
        return getDateTimeInstance(Integer.valueOf(3), Integer.valueOf(3), TimeZone.getDefault(), Locale.getDefault());
    }

    public F getInstance(String pattern, TimeZone timeZone, Locale locale) {
        if (pattern == null) {
            throw new NullPointerException("pattern must not be null");
        }
        if (timeZone == null) {
            timeZone = TimeZone.getDefault();
        }
        if (locale == null) {
            locale = Locale.getDefault();
        }
        a key = new a(pattern, timeZone, locale);
        Format format = (Format) this.cInstanceCache.get(key);
        if (format != null) {
            return format;
        }
        F format2 = createInstance(pattern, timeZone, locale);
        F previousValue = (Format) this.cInstanceCache.putIfAbsent(key, format2);
        if (previousValue != null) {
            return previousValue;
        }
        return format2;
    }

    public F getDateTimeInstance(Integer dateStyle, Integer timeStyle, TimeZone timeZone, Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        a key = new a(dateStyle, timeStyle, locale);
        String pattern = (String) this.cDateTimeInstanceCache.get(key);
        if (pattern == null) {
            DateFormat formatter;
            if (dateStyle == null) {
                try {
                    formatter = DateFormat.getTimeInstance(timeStyle.intValue(), locale);
                } catch (ClassCastException e) {
                    throw new IllegalArgumentException("No date time pattern for locale: " + locale);
                }
            } else if (timeStyle == null) {
                formatter = DateFormat.getDateInstance(dateStyle.intValue(), locale);
            } else {
                formatter = DateFormat.getDateTimeInstance(dateStyle.intValue(), timeStyle.intValue(), locale);
            }
            pattern = ((SimpleDateFormat) formatter).toPattern();
            String previous = (String) this.cDateTimeInstanceCache.putIfAbsent(key, pattern);
            if (previous != null) {
                pattern = previous;
            }
        }
        return getInstance(pattern, timeZone, locale);
    }
}
