package com.j256.ormlite.android;

import android.os.Build.VERSION;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.DatabaseFieldConfig;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseTableConfigUtil {
    private static final int ALLOW_GENERATED_ID_INSERT = 24;
    private static final int CAN_BE_NULL = 5;
    private static final int COLUMN_DEFINITON = 25;
    private static final int COLUMN_NAME = 1;
    private static final int DATA_TYPE = 2;
    private static final int DEFAULT_VALUE = 3;
    public static final String DISABLE_ANNOTATION_HACK_SYSTEM_PROPERTY = "ormlite.annotation.hack.disable";
    private static final int FOREIGN = 9;
    private static final int FOREIGN_AUTO_CREATE = 26;
    private static final int FOREIGN_AUTO_REFRESH = 21;
    private static final int FOREIGN_COLUMN_NAME = 28;
    private static final int FORMAT = 14;
    private static final int GENERATED_ID = 7;
    private static final int GENERATED_ID_SEQUENCE = 8;
    private static final int ID = 6;
    private static final int INDEX = 17;
    private static final int INDEX_NAME = 19;
    private static final int MAX_FOREIGN_AUTO_REFRESH_LEVEL = 22;
    private static final int PERSISTED = 13;
    private static final int PERSISTER_CLASS = 23;
    private static final int READ_ONLY = 29;
    private static final int THROW_IF_NULL = 12;
    private static final int UNIQUE = 15;
    private static final int UNIQUE_COMBO = 16;
    private static final int UNIQUE_INDEX = 18;
    private static final int UNIQUE_INDEX_NAME = 20;
    private static final int UNKNOWN_ENUM_NAME = 11;
    private static final int USE_GET_SET = 10;
    private static final int VERSION = 27;
    private static final int WIDTH = 4;
    private static Class<?> annotationFactoryClazz;
    private static Class<?> annotationMemberClazz;
    private static final int[] configFieldNums;
    private static Field elementsField;
    private static Field nameField;
    private static Field valueField;
    private static int workedC = 0;

    static {
        if (VERSION.SDK_INT >= 14 || System.getProperty(DISABLE_ANNOTATION_HACK_SYSTEM_PROPERTY) != null) {
            configFieldNums = null;
        } else {
            configFieldNums = lookupClasses();
        }
    }

    public static <T> DatabaseTableConfig<T> fromClass(ConnectionSource connectionSource, Class<T> clazz) throws SQLException {
        DatabaseType databaseType = connectionSource.getDatabaseType();
        String tableName = DatabaseTableConfig.extractTableName(clazz);
        List fieldConfigs = new ArrayList();
        for (Class<?> classWalk = clazz; classWalk != null; classWalk = classWalk.getSuperclass()) {
            for (Field field : classWalk.getDeclaredFields()) {
                DatabaseFieldConfig config = configFromField(databaseType, tableName, field);
                if (config != null && config.isPersisted()) {
                    fieldConfigs.add(config);
                }
            }
        }
        if (fieldConfigs.size() == 0) {
            return null;
        }
        return new DatabaseTableConfig((Class) clazz, tableName, fieldConfigs);
    }

    public static int getWorkedC() {
        return workedC;
    }

    private static int[] lookupClasses() {
        try {
            annotationFactoryClazz = Class.forName("org.apache.harmony.lang.annotation.AnnotationFactory");
            annotationMemberClazz = Class.forName("org.apache.harmony.lang.annotation.AnnotationMember");
            Class<?> annotationMemberArrayClazz = Class.forName("[Lorg.apache.harmony.lang.annotation.AnnotationMember;");
            try {
                elementsField = annotationFactoryClazz.getDeclaredField("elements");
                elementsField.setAccessible(true);
                nameField = annotationMemberClazz.getDeclaredField("name");
                nameField.setAccessible(true);
                valueField = annotationMemberClazz.getDeclaredField("value");
                valueField.setAccessible(true);
                InvocationHandler proxy = Proxy.getInvocationHandler((DatabaseField) DatabaseFieldSample.class.getDeclaredField("field").getAnnotation(DatabaseField.class));
                if (proxy.getClass() != annotationFactoryClazz) {
                    return null;
                }
                try {
                    Object elements = elementsField.get(proxy);
                    if (elements == null || elements.getClass() != annotationMemberArrayClazz) {
                        return null;
                    }
                    Object[] elementArray = (Object[]) elements;
                    int[] configNums = new int[elementArray.length];
                    for (int i = 0; i < elementArray.length; i++) {
                        configNums[i] = configFieldNameToNum((String) nameField.get(elementArray[i]));
                    }
                    return configNums;
                } catch (IllegalAccessException e) {
                    return null;
                }
            } catch (SecurityException e2) {
                return null;
            } catch (NoSuchFieldException e3) {
                return null;
            }
        } catch (ClassNotFoundException e4) {
            return null;
        }
    }

    private static int configFieldNameToNum(String configName) {
        if (configName.equals("columnName")) {
            return 1;
        }
        if (configName.equals("dataType")) {
            return 2;
        }
        if (configName.equals("defaultValue")) {
            return 3;
        }
        if (configName.equals(b.KG)) {
            return 4;
        }
        if (configName.equals("canBeNull")) {
            return 5;
        }
        if (configName.equals("id")) {
            return 6;
        }
        if (configName.equals("generatedId")) {
            return 7;
        }
        if (configName.equals("generatedIdSequence")) {
            return 8;
        }
        if (configName.equals("foreign")) {
            return 9;
        }
        if (configName.equals("useGetSet")) {
            return 10;
        }
        if (configName.equals("unknownEnumName")) {
            return 11;
        }
        if (configName.equals("throwIfNull")) {
            return 12;
        }
        if (configName.equals("persisted")) {
            return 13;
        }
        if (configName.equals("format")) {
            return 14;
        }
        if (configName.equals("unique")) {
            return 15;
        }
        if (configName.equals("uniqueCombo")) {
            return 16;
        }
        if (configName.equals("index")) {
            return 17;
        }
        if (configName.equals("uniqueIndex")) {
            return 18;
        }
        if (configName.equals("indexName")) {
            return 19;
        }
        if (configName.equals("uniqueIndexName")) {
            return 20;
        }
        if (configName.equals("foreignAutoRefresh")) {
            return 21;
        }
        if (configName.equals("maxForeignAutoRefreshLevel")) {
            return 22;
        }
        if (configName.equals("persisterClass")) {
            return 23;
        }
        if (configName.equals("allowGeneratedIdInsert")) {
            return 24;
        }
        if (configName.equals("columnDefinition")) {
            return 25;
        }
        if (configName.equals("foreignAutoCreate")) {
            return 26;
        }
        if (configName.equals("version")) {
            return 27;
        }
        if (configName.equals("foreignColumnName")) {
            return 28;
        }
        if (configName.equals("readOnly")) {
            return 29;
        }
        throw new IllegalStateException("Could not find support for DatabaseField " + configName);
    }

    private static DatabaseFieldConfig configFromField(DatabaseType databaseType, String tableName, Field field) throws SQLException {
        if (configFieldNums == null) {
            return DatabaseFieldConfig.fromField(databaseType, tableName, field);
        }
        DatabaseField databaseField = (DatabaseField) field.getAnnotation(DatabaseField.class);
        DatabaseFieldConfig config = null;
        if (databaseField != null) {
            try {
                config = buildConfig(databaseField, tableName, field);
            } catch (Exception e) {
            }
        }
        if (config == null) {
            return DatabaseFieldConfig.fromField(databaseType, tableName, field);
        }
        workedC++;
        return config;
    }

    private static DatabaseFieldConfig buildConfig(DatabaseField databaseField, String tableName, Field field) throws Exception {
        DatabaseFieldConfig databaseFieldConfig = null;
        InvocationHandler proxy = Proxy.getInvocationHandler(databaseField);
        if (proxy.getClass() == annotationFactoryClazz) {
            Object elementsObject = elementsField.get(proxy);
            if (elementsObject != null) {
                databaseFieldConfig = new DatabaseFieldConfig(field.getName());
                Object[] objs = (Object[]) elementsObject;
                for (int i = 0; i < configFieldNums.length; i++) {
                    Object value = valueField.get(objs[i]);
                    if (value != null) {
                        assignConfigField(configFieldNums[i], databaseFieldConfig, field, value);
                    }
                }
            }
        }
        return databaseFieldConfig;
    }

    private static void assignConfigField(int configNum, DatabaseFieldConfig config, Field field, Object value) {
        switch (configNum) {
            case 1:
                config.setColumnName(valueIfNotBlank((String) value));
                return;
            case 2:
                config.setDataType((DataType) value);
                return;
            case 3:
                String defaultValue = (String) value;
                if (defaultValue != null && !defaultValue.equals(DatabaseField.DEFAULT_STRING)) {
                    config.setDefaultValue(defaultValue);
                    return;
                }
                return;
            case 4:
                config.setWidth(((Integer) value).intValue());
                return;
            case 5:
                config.setCanBeNull(((Boolean) value).booleanValue());
                return;
            case 6:
                config.setId(((Boolean) value).booleanValue());
                return;
            case 7:
                config.setGeneratedId(((Boolean) value).booleanValue());
                return;
            case 8:
                config.setGeneratedIdSequence(valueIfNotBlank((String) value));
                return;
            case 9:
                config.setForeign(((Boolean) value).booleanValue());
                return;
            case 10:
                config.setUseGetSet(((Boolean) value).booleanValue());
                return;
            case 11:
                config.setUnknownEnumValue(DatabaseFieldConfig.findMatchingEnumVal(field, (String) value));
                return;
            case 12:
                config.setThrowIfNull(((Boolean) value).booleanValue());
                return;
            case 13:
                config.setPersisted(((Boolean) value).booleanValue());
                return;
            case 14:
                config.setFormat(valueIfNotBlank((String) value));
                return;
            case 15:
                config.setUnique(((Boolean) value).booleanValue());
                return;
            case 16:
                config.setUniqueCombo(((Boolean) value).booleanValue());
                return;
            case 17:
                config.setIndex(((Boolean) value).booleanValue());
                return;
            case 18:
                config.setUniqueIndex(((Boolean) value).booleanValue());
                return;
            case 19:
                config.setIndexName(valueIfNotBlank((String) value));
                return;
            case 20:
                config.setUniqueIndexName(valueIfNotBlank((String) value));
                return;
            case 21:
                config.setForeignAutoRefresh(((Boolean) value).booleanValue());
                return;
            case 22:
                config.setMaxForeignAutoRefreshLevel(((Integer) value).intValue());
                return;
            case 23:
                config.setPersisterClass((Class) value);
                return;
            case 24:
                config.setAllowGeneratedIdInsert(((Boolean) value).booleanValue());
                return;
            case 25:
                config.setColumnDefinition(valueIfNotBlank((String) value));
                return;
            case 26:
                config.setForeignAutoCreate(((Boolean) value).booleanValue());
                return;
            case 27:
                config.setVersion(((Boolean) value).booleanValue());
                return;
            case 28:
                config.setForeignColumnName(valueIfNotBlank((String) value));
                return;
            case 29:
                config.setReadOnly(((Boolean) value).booleanValue());
                return;
            default:
                throw new IllegalStateException("Could not find support for DatabaseField number " + configNum);
        }
    }

    private static String valueIfNotBlank(String value) {
        if (value == null || value.length() == 0) {
            return null;
        }
        return value;
    }
}
