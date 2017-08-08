package com.MCWorld.utils;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.MCWorld.framework.base.log.HLog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/* compiled from: SharedPref */
public abstract class n {
    private static final String DELIMITER = ",";
    protected final SharedPreferences mPref;

    public n(SharedPreferences pref) {
        this.mPref = pref;
    }

    public void putString(String key, String value) {
        put(key, value);
    }

    public String getString(String key) {
        return get(key);
    }

    public void putInt(String key, int value) {
        put(key, String.valueOf(value));
    }

    public void putBoolean(String key, boolean value) {
        put(key, String.valueOf(value));
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        String rawValue = get(key);
        if (!TextUtils.isEmpty(rawValue)) {
            try {
                defaultValue = Boolean.parseBoolean(rawValue);
            } catch (Exception e) {
                HLog.error(this, "failed to parse boolean value for key %s, %s", key, e);
            }
        }
        return defaultValue;
    }

    public int getInt(String key, int defaultValue) {
        String rawValue = get(key);
        return TextUtils.isEmpty(rawValue) ? defaultValue : parseInt(rawValue, defaultValue);
    }

    private int parseInt(String value, int defaultValue) {
        try {
            defaultValue = Integer.parseInt(value);
        } catch (NumberFormatException e) {
            HLog.error(this, "lcy failed to parse value for key %s, %s", value, e);
        }
        return defaultValue;
    }

    public int getInt(String key) {
        return getInt(key, -1);
    }

    public void putLong(String key, long value) {
        put(key, String.valueOf(value));
    }

    public long getLong(String key, long defaultValue) {
        String rawValue = get(key);
        if (!TextUtils.isEmpty(rawValue)) {
            try {
                defaultValue = Long.parseLong(rawValue);
            } catch (NumberFormatException e) {
                HLog.error(this, "lcy failed to parse %s as long, for key %s, ex : %s", rawValue, key, e);
            }
        }
        return defaultValue;
    }

    public long getLong(String key) {
        return getLong(key, -1);
    }

    public void putIntArray(String key, Integer[] values) {
        putIntList(key, Arrays.asList(values));
    }

    public int[] getIntArray(String key) {
        return getIntArray(key, null);
    }

    public int[] getIntArray(String key, int[] outValues) {
        List<Integer> list = getIntList(key);
        if (list == null || list.size() == 0) {
            return null;
        }
        int[] ret;
        if (list.size() <= outValues.length) {
            ret = outValues;
        } else {
            ret = new int[list.size()];
        }
        int i = 0;
        for (Integer e : list) {
            int i2 = i + 1;
            ret[i] = e.intValue();
            i = i2;
        }
        return ret;
    }

    public void putIntList(String key, List<Integer> values) {
        if (values != null && values.size() != 0) {
            put(key, TextUtils.join(",", values));
        }
    }

    public List<Integer> getIntList(String key) {
        List<Integer> list = null;
        String val = get(key);
        if (!TextUtils.isEmpty(val)) {
            String[] values = TextUtils.split(val, ",");
            if (!(values == null || values.length == 0)) {
                list = new ArrayList();
                for (String e : values) {
                    try {
                        list.add(Integer.valueOf(Integer.parseInt(e)));
                    } catch (NumberFormatException ex) {
                        HLog.error(this, "lcy failed to parse value for key: %s, value: %s, exception: %s", key, e, ex);
                    }
                }
            }
        }
        return list;
    }

    public final void put(String key, String value) {
        this.mPref.edit().putString(key, value).commit();
    }

    public final String get(String key) {
        return this.mPref.getString(key, null);
    }

    public void remove(String key) {
        this.mPref.edit().remove(key).commit();
    }

    public void clear() {
        this.mPref.edit().clear().commit();
    }

    public Map<String, ?> getAll() {
        return this.mPref.getAll();
    }

    public boolean contains(String key) {
        return this.mPref.contains(key);
    }
}
