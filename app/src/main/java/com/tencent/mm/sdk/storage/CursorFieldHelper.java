package com.tencent.mm.sdk.storage;

import android.content.ContentValues;
import android.database.Cursor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

class CursorFieldHelper {
    private static final Map<Class<?>, ISetMethod> bP = new HashMap();
    private static final Map<Class<?>, IGetMethod> bQ = new HashMap();
    private static final Map<Class<?>, String> bR = new HashMap();

    public interface ISetMethod {
        void invoke(Field field, Object obj, Cursor cursor, int i);
    }

    public interface IGetMethod {
        void invoke(Field field, Object obj, ContentValues contentValues);
    }

    static {
        try {
            bP.put(byte[].class, new ISetMethod() {
                public final void invoke(Field field, Object obj, Cursor cursor, int i) {
                    CursorFieldHelper.keep_setBlob(field, obj, cursor, i);
                }
            });
            bP.put(Short.TYPE, new ISetMethod() {
                public final void invoke(Field field, Object obj, Cursor cursor, int i) {
                    CursorFieldHelper.keep_setShort(field, obj, cursor, i);
                }
            });
            bP.put(Short.class, new ISetMethod() {
                public final void invoke(Field field, Object obj, Cursor cursor, int i) {
                    CursorFieldHelper.keep_setShort(field, obj, cursor, i);
                }
            });
            bP.put(Boolean.TYPE, new ISetMethod() {
                public final void invoke(Field field, Object obj, Cursor cursor, int i) {
                    CursorFieldHelper.keep_setBoolean(field, obj, cursor, i);
                }
            });
            bP.put(Boolean.class, new ISetMethod() {
                public final void invoke(Field field, Object obj, Cursor cursor, int i) {
                    CursorFieldHelper.keep_setBoolean(field, obj, cursor, i);
                }
            });
            bP.put(Integer.TYPE, new ISetMethod() {
                public final void invoke(Field field, Object obj, Cursor cursor, int i) {
                    CursorFieldHelper.keep_setInt(field, obj, cursor, i);
                }
            });
            bP.put(Integer.class, new ISetMethod() {
                public final void invoke(Field field, Object obj, Cursor cursor, int i) {
                    CursorFieldHelper.keep_setInt(field, obj, cursor, i);
                }
            });
            bP.put(Float.TYPE, new ISetMethod() {
                public final void invoke(Field field, Object obj, Cursor cursor, int i) {
                    CursorFieldHelper.keep_setFloat(field, obj, cursor, i);
                }
            });
            bP.put(Float.class, new ISetMethod() {
                public final void invoke(Field field, Object obj, Cursor cursor, int i) {
                    CursorFieldHelper.keep_setFloat(field, obj, cursor, i);
                }
            });
            bP.put(Double.TYPE, new ISetMethod() {
                public final void invoke(Field field, Object obj, Cursor cursor, int i) {
                    CursorFieldHelper.keep_setDouble(field, obj, cursor, i);
                }
            });
            bP.put(Double.class, new ISetMethod() {
                public final void invoke(Field field, Object obj, Cursor cursor, int i) {
                    CursorFieldHelper.keep_setDouble(field, obj, cursor, i);
                }
            });
            bP.put(Long.TYPE, new ISetMethod() {
                public final void invoke(Field field, Object obj, Cursor cursor, int i) {
                    CursorFieldHelper.keep_setLong(field, obj, cursor, i);
                }
            });
            bP.put(Long.class, new ISetMethod() {
                public final void invoke(Field field, Object obj, Cursor cursor, int i) {
                    CursorFieldHelper.keep_setLong(field, obj, cursor, i);
                }
            });
            bP.put(String.class, new ISetMethod() {
                public final void invoke(Field field, Object obj, Cursor cursor, int i) {
                    CursorFieldHelper.keep_setString(field, obj, cursor, i);
                }
            });
            bQ.put(byte[].class, new IGetMethod() {
                public final void invoke(Field field, Object obj, ContentValues contentValues) {
                    CursorFieldHelper.keep_getBlob(field, obj, contentValues);
                }
            });
            bQ.put(Short.TYPE, new IGetMethod() {
                public final void invoke(Field field, Object obj, ContentValues contentValues) {
                    CursorFieldHelper.keep_getShort(field, obj, contentValues);
                }
            });
            bQ.put(Short.class, new IGetMethod() {
                public final void invoke(Field field, Object obj, ContentValues contentValues) {
                    CursorFieldHelper.keep_getShort(field, obj, contentValues);
                }
            });
            bQ.put(Boolean.TYPE, new IGetMethod() {
                public final void invoke(Field field, Object obj, ContentValues contentValues) {
                    CursorFieldHelper.keep_getBoolean(field, obj, contentValues);
                }
            });
            bQ.put(Boolean.class, new IGetMethod() {
                public final void invoke(Field field, Object obj, ContentValues contentValues) {
                    CursorFieldHelper.keep_getBoolean(field, obj, contentValues);
                }
            });
            bQ.put(Integer.TYPE, new IGetMethod() {
                public final void invoke(Field field, Object obj, ContentValues contentValues) {
                    CursorFieldHelper.keep_getInt(field, obj, contentValues);
                }
            });
            bQ.put(Integer.class, new IGetMethod() {
                public final void invoke(Field field, Object obj, ContentValues contentValues) {
                    CursorFieldHelper.keep_getInt(field, obj, contentValues);
                }
            });
            bQ.put(Float.TYPE, new IGetMethod() {
                public final void invoke(Field field, Object obj, ContentValues contentValues) {
                    CursorFieldHelper.keep_getFloat(field, obj, contentValues);
                }
            });
            bQ.put(Float.class, new IGetMethod() {
                public final void invoke(Field field, Object obj, ContentValues contentValues) {
                    CursorFieldHelper.keep_getFloat(field, obj, contentValues);
                }
            });
            bQ.put(Double.TYPE, new IGetMethod() {
                public final void invoke(Field field, Object obj, ContentValues contentValues) {
                    CursorFieldHelper.keep_getDouble(field, obj, contentValues);
                }
            });
            bQ.put(Double.class, new IGetMethod() {
                public final void invoke(Field field, Object obj, ContentValues contentValues) {
                    CursorFieldHelper.keep_getDouble(field, obj, contentValues);
                }
            });
            bQ.put(Long.TYPE, new IGetMethod() {
                public final void invoke(Field field, Object obj, ContentValues contentValues) {
                    CursorFieldHelper.keep_getLong(field, obj, contentValues);
                }
            });
            bQ.put(Long.class, new IGetMethod() {
                public final void invoke(Field field, Object obj, ContentValues contentValues) {
                    CursorFieldHelper.keep_getLong(field, obj, contentValues);
                }
            });
            bQ.put(String.class, new IGetMethod() {
                public final void invoke(Field field, Object obj, ContentValues contentValues) {
                    CursorFieldHelper.keep_getString(field, obj, contentValues);
                }
            });
            bR.put(byte[].class, "BLOB");
            bR.put(Short.TYPE, "SHORT");
            bR.put(Short.class, "SHORT");
            bR.put(Boolean.TYPE, "INTEGER");
            bR.put(Boolean.class, "INTEGER");
            bR.put(Integer.TYPE, "INTEGER");
            bR.put(Integer.class, "INTEGER");
            bR.put(Float.TYPE, "FLOAT");
            bR.put(Float.class, "FLOAT");
            bR.put(Double.TYPE, "DOUBLE");
            bR.put(Double.class, "DOUBLE");
            bR.put(Long.TYPE, "LONG");
            bR.put(Long.class, "LONG");
            bR.put(String.class, "TEXT");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    CursorFieldHelper() {
    }

    public static IGetMethod getter(Class<?> cls) {
        return (IGetMethod) bQ.get(cls);
    }

    public static void keep_getBlob(Field field, Object obj, ContentValues contentValues) {
        try {
            contentValues.put(IAutoDBItem.getColName(field), (byte[]) field.get(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void keep_getBoolean(Field field, Object obj, ContentValues contentValues) {
        try {
            contentValues.put(IAutoDBItem.getColName(field), Integer.valueOf(field.getBoolean(obj) ? 1 : 0));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void keep_getDouble(Field field, Object obj, ContentValues contentValues) {
        try {
            if (field.getType().equals(Double.TYPE)) {
                contentValues.put(IAutoDBItem.getColName(field), Double.valueOf(field.getDouble(obj)));
            } else {
                contentValues.put(IAutoDBItem.getColName(field), (Double) field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void keep_getFloat(Field field, Object obj, ContentValues contentValues) {
        try {
            if (field.getType().equals(Float.TYPE)) {
                contentValues.put(IAutoDBItem.getColName(field), Float.valueOf(field.getFloat(obj)));
            } else {
                contentValues.put(IAutoDBItem.getColName(field), (Float) field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void keep_getInt(Field field, Object obj, ContentValues contentValues) {
        try {
            if (field.getType().equals(Integer.TYPE)) {
                contentValues.put(IAutoDBItem.getColName(field), Integer.valueOf(field.getInt(obj)));
            } else {
                contentValues.put(IAutoDBItem.getColName(field), (Integer) field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void keep_getLong(Field field, Object obj, ContentValues contentValues) {
        try {
            if (field.getType().equals(Long.TYPE)) {
                contentValues.put(IAutoDBItem.getColName(field), Long.valueOf(field.getLong(obj)));
            } else {
                contentValues.put(IAutoDBItem.getColName(field), (Long) field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void keep_getShort(Field field, Object obj, ContentValues contentValues) {
        try {
            contentValues.put(IAutoDBItem.getColName(field), Short.valueOf(field.getShort(obj)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void keep_getString(Field field, Object obj, ContentValues contentValues) {
        try {
            contentValues.put(IAutoDBItem.getColName(field), (String) field.get(obj));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void keep_setBlob(Field field, Object obj, Cursor cursor, int i) {
        try {
            field.set(obj, cursor.getBlob(i));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void keep_setBoolean(Field field, Object obj, Cursor cursor, int i) {
        try {
            if (field.getType().equals(Boolean.TYPE)) {
                field.setBoolean(obj, cursor.getInt(i) != 0);
            } else {
                field.set(obj, Integer.valueOf(cursor.getInt(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void keep_setDouble(Field field, Object obj, Cursor cursor, int i) {
        try {
            if (field.getType().equals(Double.TYPE)) {
                field.setDouble(obj, cursor.getDouble(i));
            } else {
                field.set(obj, Double.valueOf(cursor.getDouble(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void keep_setFloat(Field field, Object obj, Cursor cursor, int i) {
        try {
            if (field.getType().equals(Float.TYPE)) {
                field.setFloat(obj, cursor.getFloat(i));
            } else {
                field.set(obj, Float.valueOf(cursor.getFloat(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void keep_setInt(Field field, Object obj, Cursor cursor, int i) {
        try {
            if (field.getType().equals(Integer.TYPE)) {
                field.setInt(obj, cursor.getInt(i));
            } else {
                field.set(obj, Integer.valueOf(cursor.getInt(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void keep_setLong(Field field, Object obj, Cursor cursor, int i) {
        try {
            if (field.getType().equals(Long.TYPE)) {
                field.setLong(obj, cursor.getLong(i));
            } else {
                field.set(obj, Long.valueOf(cursor.getLong(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void keep_setShort(Field field, Object obj, Cursor cursor, int i) {
        try {
            if (field.getType().equals(Short.TYPE)) {
                field.setShort(obj, cursor.getShort(i));
            } else {
                field.set(obj, Short.valueOf(cursor.getShort(i)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void keep_setString(Field field, Object obj, Cursor cursor, int i) {
        try {
            field.set(obj, cursor.getString(i));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ISetMethod setter(Class<?> cls) {
        return (ISetMethod) bP.get(cls);
    }

    public static String type(Class<?> cls) {
        return (String) bR.get(cls);
    }
}
