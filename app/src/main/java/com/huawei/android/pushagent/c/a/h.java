package com.huawei.android.pushagent.c.a;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class h {
    protected SharedPreferences a;

    public h(Context context, String str) {
        if (context == null) {
            throw new NullPointerException("context is null!");
        }
        this.a = context.getSharedPreferences(str, 4);
    }

    public ContentValues a() {
        if (this.a == null) {
            return null;
        }
        Map all = this.a.getAll();
        if (all == null) {
            return null;
        }
        ContentValues contentValues = new ContentValues();
        for (Entry entry : all.entrySet()) {
            String str = (String) entry.getKey();
            Object value = entry.getValue();
            if (value instanceof String) {
                contentValues.put(str, String.valueOf(value));
            } else if ((value instanceof Integer) || (value instanceof Short) || (value instanceof Byte)) {
                contentValues.put(str, (Integer) value);
            } else if (value instanceof Long) {
                contentValues.put(str, (Long) value);
            } else if (value instanceof Float) {
                contentValues.put(str, (Float) value);
            } else if (value instanceof Double) {
                contentValues.put(str, Float.valueOf((float) ((Double) value).doubleValue()));
            } else if (value instanceof Boolean) {
                contentValues.put(str, (Boolean) value);
            }
        }
        return contentValues;
    }

    public void a(String str, Integer num) {
        if (this.a != null) {
            Editor edit = this.a.edit();
            if (edit != null) {
                edit.putInt(str, num.intValue()).commit();
            }
        }
    }

    public void a(String str, Long l) {
        if (this.a != null) {
            Editor edit = this.a.edit();
            if (edit != null) {
                edit.putLong(str, l.longValue()).commit();
            }
        }
    }

    public void a(String str, boolean z) {
        if (this.a != null) {
            Editor edit = this.a.edit();
            if (edit != null) {
                edit.putBoolean(str, z).commit();
            }
        }
    }

    public void a(Map map) {
        for (Entry entry : map.entrySet()) {
            a((String) entry.getKey(), entry.getValue());
        }
    }

    public boolean a(ContentValues contentValues) {
        if (this.a == null || contentValues == null) {
            return false;
        }
        boolean z = true;
        for (Entry entry : contentValues.valueSet()) {
            z = !a((String) entry.getKey(), entry.getValue()) ? false : z;
        }
        return z;
    }

    public boolean a(String str) {
        return this.a != null ? this.a.getBoolean(str, false) : false;
    }

    public boolean a(String str, Object obj) {
        Editor edit = this.a.edit();
        if (obj instanceof String) {
            edit.putString(str, String.valueOf(obj));
        } else if (obj instanceof Integer) {
            edit.putInt(str, ((Integer) obj).intValue());
        } else if (obj instanceof Short) {
            edit.putInt(str, ((Short) obj).shortValue());
        } else if (obj instanceof Byte) {
            edit.putInt(str, ((Byte) obj).byteValue());
        } else if (obj instanceof Long) {
            edit.putLong(str, ((Long) obj).longValue());
        } else if (obj instanceof Float) {
            edit.putFloat(str, ((Float) obj).floatValue());
        } else if (obj instanceof Double) {
            edit.putFloat(str, (float) ((Double) obj).doubleValue());
        } else if (obj instanceof Boolean) {
            edit.putBoolean(str, ((Boolean) obj).booleanValue());
        }
        return edit.commit();
    }

    public boolean a(String str, String str2) {
        if (this.a == null) {
            return false;
        }
        Editor edit = this.a.edit();
        return edit != null ? edit.putString(str, str2).commit() : false;
    }

    public String b(String str) {
        return this.a != null ? this.a.getString(str, "") : "";
    }

    public Map b() {
        return this.a != null ? this.a.getAll() : new HashMap();
    }

    public int c(String str) {
        return this.a != null ? this.a.getInt(str, 0) : 0;
    }

    public boolean c() {
        return this.a != null ? this.a.edit().clear().commit() : false;
    }

    public long d(String str) {
        return this.a != null ? this.a.getLong(str, 0) : 0;
    }

    public boolean e(String str) {
        return this.a != null && this.a.contains(str);
    }

    public boolean f(String str) {
        if (this.a == null || !this.a.contains(str)) {
            return false;
        }
        Editor remove = this.a.edit().remove(str);
        remove.commit();
        return remove.commit();
    }
}
