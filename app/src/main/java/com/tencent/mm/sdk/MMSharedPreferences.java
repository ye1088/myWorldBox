package com.tencent.mm.sdk;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.database.Cursor;
import com.j256.ormlite.field.FieldType;
import com.tencent.mm.sdk.plugin.MMPluginProviderConstants.Resolver;
import com.tencent.mm.sdk.plugin.MMPluginProviderConstants.SharedPref;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class MMSharedPreferences implements SharedPreferences {
    private final String[] columns = new String[]{FieldType.FOREIGN_ID_FIELD_SUFFIX, "key", "type", "value"};
    private final ContentResolver i;
    private final HashMap<String, Object> j = new HashMap();
    private REditor k = null;

    private static class REditor implements Editor {
        private ContentResolver i;
        private Map<String, Object> l = new HashMap();
        private Set<String> m = new HashSet();
        private boolean n = false;

        public REditor(ContentResolver contentResolver) {
            this.i = contentResolver;
        }

        public void apply() {
        }

        public Editor clear() {
            this.n = true;
            return this;
        }

        public boolean commit() {
            ContentValues contentValues = new ContentValues();
            if (this.n) {
                this.i.delete(SharedPref.CONTENT_URI, null, null);
                this.n = false;
            }
            for (String str : this.m) {
                this.i.delete(SharedPref.CONTENT_URI, "key = ?", new String[]{str});
            }
            for (Entry value : this.l.entrySet()) {
                if (Resolver.unresolveObj(contentValues, value.getValue())) {
                    this.i.update(SharedPref.CONTENT_URI, contentValues, "key = ?", new String[]{(String) ((Entry) r2.next()).getKey()});
                }
            }
            return true;
        }

        public Editor putBoolean(String str, boolean z) {
            this.l.put(str, Boolean.valueOf(z));
            this.m.remove(str);
            return this;
        }

        public Editor putFloat(String str, float f) {
            this.l.put(str, Float.valueOf(f));
            this.m.remove(str);
            return this;
        }

        public Editor putInt(String str, int i) {
            this.l.put(str, Integer.valueOf(i));
            this.m.remove(str);
            return this;
        }

        public Editor putLong(String str, long j) {
            this.l.put(str, Long.valueOf(j));
            this.m.remove(str);
            return this;
        }

        public Editor putString(String str, String str2) {
            this.l.put(str, str2);
            this.m.remove(str);
            return this;
        }

        public Editor putStringSet(String str, Set<String> set) {
            return null;
        }

        public Editor remove(String str) {
            this.m.add(str);
            return this;
        }
    }

    public MMSharedPreferences(Context context) {
        this.i = context.getContentResolver();
    }

    private Object getValue(String str) {
        try {
            Cursor query = this.i.query(SharedPref.CONTENT_URI, this.columns, "key = ?", new String[]{str}, null);
            if (query == null) {
                return null;
            }
            Object resolveObj = query.moveToFirst() ? Resolver.resolveObj(query.getInt(query.getColumnIndex("type")), query.getString(query.getColumnIndex("value"))) : null;
            query.close();
            return resolveObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean contains(String str) {
        return getValue(str) != null;
    }

    public Editor edit() {
        if (this.k == null) {
            this.k = new REditor(this.i);
        }
        return this.k;
    }

    public Map<String, ?> getAll() {
        try {
            Cursor query = this.i.query(SharedPref.CONTENT_URI, this.columns, null, null, null);
            if (query == null) {
                return null;
            }
            int columnIndex = query.getColumnIndex("key");
            int columnIndex2 = query.getColumnIndex("type");
            int columnIndex3 = query.getColumnIndex("value");
            while (query.moveToNext()) {
                this.j.put(query.getString(columnIndex), Resolver.resolveObj(query.getInt(columnIndex2), query.getString(columnIndex3)));
            }
            query.close();
            return this.j;
        } catch (Exception e) {
            e.printStackTrace();
            return this.j;
        }
    }

    public boolean getBoolean(String str, boolean z) {
        Object value = getValue(str);
        return (value == null || !(value instanceof Boolean)) ? z : ((Boolean) value).booleanValue();
    }

    public float getFloat(String str, float f) {
        Object value = getValue(str);
        return (value == null || !(value instanceof Float)) ? f : ((Float) value).floatValue();
    }

    public int getInt(String str, int i) {
        Object value = getValue(str);
        return (value == null || !(value instanceof Integer)) ? i : ((Integer) value).intValue();
    }

    public long getLong(String str, long j) {
        Object value = getValue(str);
        return (value == null || !(value instanceof Long)) ? j : ((Long) value).longValue();
    }

    public String getString(String str, String str2) {
        Object value = getValue(str);
        return (value == null || !(value instanceof String)) ? str2 : (String) value;
    }

    public Set<String> getStringSet(String str, Set<String> set) {
        return null;
    }

    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
    }

    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
    }
}
