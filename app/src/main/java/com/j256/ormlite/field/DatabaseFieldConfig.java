package com.j256.ormlite.field;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.types.VoidType;
import com.j256.ormlite.misc.JavaxPersistenceConfigurer;
import com.j256.ormlite.table.DatabaseTableConfig;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Locale;

public class DatabaseFieldConfig {
    public static final boolean DEFAULT_CAN_BE_NULL = true;
    public static final DataType DEFAULT_DATA_TYPE = DataType.UNKNOWN;
    public static final boolean DEFAULT_FOREIGN_COLLECTION_ORDER_ASCENDING = true;
    private static final int DEFAULT_MAX_EAGER_FOREIGN_COLLECTION_LEVEL = 1;
    public static final Class<? extends DataPersister> DEFAULT_PERSISTER_CLASS = VoidType.class;
    public static final int NO_MAX_FOREIGN_AUTO_REFRESH_LEVEL_SPECIFIED = -1;
    private static JavaxPersistenceConfigurer javaxPersistenceConfigurer;
    private boolean allowGeneratedIdInsert;
    private boolean canBeNull = true;
    private String columnDefinition;
    private String columnName;
    private DataPersister dataPersister;
    private DataType dataType = DEFAULT_DATA_TYPE;
    private String defaultValue;
    private String fieldName;
    private boolean foreign;
    private boolean foreignAutoCreate;
    private boolean foreignAutoRefresh;
    private boolean foreignCollection;
    private String foreignCollectionColumnName;
    private boolean foreignCollectionEager;
    private String foreignCollectionForeignFieldName;
    private int foreignCollectionMaxEagerLevel = 1;
    private boolean foreignCollectionOrderAscending = true;
    private String foreignCollectionOrderColumnName;
    private String foreignColumnName;
    private DatabaseTableConfig<?> foreignTableConfig;
    private String format;
    private boolean generatedId;
    private String generatedIdSequence;
    private boolean id;
    private boolean index;
    private String indexName;
    private int maxForeignAutoRefreshLevel = -1;
    private boolean persisted = true;
    private Class<? extends DataPersister> persisterClass = DEFAULT_PERSISTER_CLASS;
    private boolean readOnly;
    private boolean throwIfNull;
    private boolean unique;
    private boolean uniqueCombo;
    private boolean uniqueIndex;
    private String uniqueIndexName;
    private Enum<?> unknownEnumValue;
    private boolean useGetSet;
    private boolean version;
    private int width;

    static {
        try {
            Class.forName("javax.persistence.Entity");
            javaxPersistenceConfigurer = (JavaxPersistenceConfigurer) Class.forName("com.j256.ormlite.misc.JavaxPersistenceImpl").getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            javaxPersistenceConfigurer = null;
        }
    }

    public DatabaseFieldConfig(String fieldName) {
        this.fieldName = fieldName;
    }

    public DatabaseFieldConfig(String fieldName, String columnName, DataType dataType, String defaultValue, int width, boolean canBeNull, boolean id, boolean generatedId, String generatedIdSequence, boolean foreign, DatabaseTableConfig<?> foreignTableConfig, boolean useGetSet, Enum<?> unknownEnumValue, boolean throwIfNull, String format, boolean unique, String indexName, String uniqueIndexName, boolean autoRefresh, int maxForeignAutoRefreshLevel, int maxForeignCollectionLevel) {
        this.fieldName = fieldName;
        this.columnName = columnName;
        this.dataType = dataType;
        this.defaultValue = defaultValue;
        this.width = width;
        this.canBeNull = canBeNull;
        this.id = id;
        this.generatedId = generatedId;
        this.generatedIdSequence = generatedIdSequence;
        this.foreign = foreign;
        this.foreignTableConfig = foreignTableConfig;
        this.useGetSet = useGetSet;
        this.unknownEnumValue = unknownEnumValue;
        this.throwIfNull = throwIfNull;
        this.format = format;
        this.unique = unique;
        this.indexName = indexName;
        this.uniqueIndexName = uniqueIndexName;
        this.foreignAutoRefresh = autoRefresh;
        this.maxForeignAutoRefreshLevel = maxForeignAutoRefreshLevel;
        this.foreignCollectionMaxEagerLevel = maxForeignCollectionLevel;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getColumnName() {
        return this.columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public DataType getDataType() {
        return this.dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public DataPersister getDataPersister() {
        if (this.dataPersister == null) {
            return this.dataType.getDataPersister();
        }
        return this.dataPersister;
    }

    public void setDataPersister(DataPersister dataPersister) {
        this.dataPersister = dataPersister;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public boolean isCanBeNull() {
        return this.canBeNull;
    }

    public void setCanBeNull(boolean canBeNull) {
        this.canBeNull = canBeNull;
    }

    public boolean isId() {
        return this.id;
    }

    public void setId(boolean id) {
        this.id = id;
    }

    public boolean isGeneratedId() {
        return this.generatedId;
    }

    public void setGeneratedId(boolean generatedId) {
        this.generatedId = generatedId;
    }

    public String getGeneratedIdSequence() {
        return this.generatedIdSequence;
    }

    public void setGeneratedIdSequence(String generatedIdSequence) {
        this.generatedIdSequence = generatedIdSequence;
    }

    public boolean isForeign() {
        return this.foreign;
    }

    public void setForeign(boolean foreign) {
        this.foreign = foreign;
    }

    public DatabaseTableConfig<?> getForeignTableConfig() {
        return this.foreignTableConfig;
    }

    public void setForeignTableConfig(DatabaseTableConfig<?> foreignTableConfig) {
        this.foreignTableConfig = foreignTableConfig;
    }

    public boolean isUseGetSet() {
        return this.useGetSet;
    }

    public void setUseGetSet(boolean useGetSet) {
        this.useGetSet = useGetSet;
    }

    public Enum<?> getUnknownEnumValue() {
        return this.unknownEnumValue;
    }

    public void setUnknownEnumValue(Enum<?> unknownEnumValue) {
        this.unknownEnumValue = unknownEnumValue;
    }

    public boolean isThrowIfNull() {
        return this.throwIfNull;
    }

    public void setThrowIfNull(boolean throwIfNull) {
        this.throwIfNull = throwIfNull;
    }

    public boolean isPersisted() {
        return this.persisted;
    }

    public void setPersisted(boolean persisted) {
        this.persisted = persisted;
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public boolean isUnique() {
        return this.unique;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public boolean isUniqueCombo() {
        return this.uniqueCombo;
    }

    public void setUniqueCombo(boolean uniqueCombo) {
        this.uniqueCombo = uniqueCombo;
    }

    public boolean isIndex() {
        return this.index;
    }

    public void setIndex(boolean index) {
        this.index = index;
    }

    public String getIndexName(String tableName) {
        if (this.index && this.indexName == null) {
            this.indexName = findIndexName(tableName);
        }
        return this.indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public boolean isUniqueIndex() {
        return this.uniqueIndex;
    }

    public void setUniqueIndex(boolean uniqueIndex) {
        this.uniqueIndex = uniqueIndex;
    }

    public String getUniqueIndexName(String tableName) {
        if (this.uniqueIndex && this.uniqueIndexName == null) {
            this.uniqueIndexName = findIndexName(tableName);
        }
        return this.uniqueIndexName;
    }

    public void setUniqueIndexName(String uniqueIndexName) {
        this.uniqueIndexName = uniqueIndexName;
    }

    public void setForeignAutoRefresh(boolean foreignAutoRefresh) {
        this.foreignAutoRefresh = foreignAutoRefresh;
    }

    public boolean isForeignAutoRefresh() {
        return this.foreignAutoRefresh;
    }

    public int getMaxForeignAutoRefreshLevel() {
        if (this.foreignAutoRefresh) {
            return this.maxForeignAutoRefreshLevel;
        }
        return -1;
    }

    public void setMaxForeignAutoRefreshLevel(int maxForeignLevel) {
        this.maxForeignAutoRefreshLevel = maxForeignLevel;
    }

    public boolean isForeignCollection() {
        return this.foreignCollection;
    }

    public void setForeignCollection(boolean foreignCollection) {
        this.foreignCollection = foreignCollection;
    }

    public boolean isForeignCollectionEager() {
        return this.foreignCollectionEager;
    }

    public void setForeignCollectionEager(boolean foreignCollectionEager) {
        this.foreignCollectionEager = foreignCollectionEager;
    }

    public int getForeignCollectionMaxEagerLevel() {
        return this.foreignCollectionMaxEagerLevel;
    }

    public void setForeignCollectionMaxEagerLevel(int foreignCollectionMaxEagerLevel) {
        this.foreignCollectionMaxEagerLevel = foreignCollectionMaxEagerLevel;
    }

    public String getForeignCollectionColumnName() {
        return this.foreignCollectionColumnName;
    }

    public void setForeignCollectionColumnName(String foreignCollectionColumn) {
        this.foreignCollectionColumnName = foreignCollectionColumn;
    }

    public String getForeignCollectionOrderColumnName() {
        return this.foreignCollectionOrderColumnName;
    }

    public void setForeignCollectionOrderColumnName(String foreignCollectionOrderColumn) {
        this.foreignCollectionOrderColumnName = foreignCollectionOrderColumn;
    }

    public boolean isForeignCollectionOrderAscending() {
        return this.foreignCollectionOrderAscending;
    }

    public void setForeignCollectionOrderAscending(boolean foreignCollectionOrderAscending) {
        this.foreignCollectionOrderAscending = foreignCollectionOrderAscending;
    }

    public String getForeignCollectionForeignFieldName() {
        return this.foreignCollectionForeignFieldName;
    }

    public void setForeignCollectionForeignFieldName(String foreignCollectionForeignFieldName) {
        this.foreignCollectionForeignFieldName = foreignCollectionForeignFieldName;
    }

    public Class<? extends DataPersister> getPersisterClass() {
        return this.persisterClass;
    }

    public void setPersisterClass(Class<? extends DataPersister> persisterClass) {
        this.persisterClass = persisterClass;
    }

    public boolean isAllowGeneratedIdInsert() {
        return this.allowGeneratedIdInsert;
    }

    public void setAllowGeneratedIdInsert(boolean allowGeneratedIdInsert) {
        this.allowGeneratedIdInsert = allowGeneratedIdInsert;
    }

    public String getColumnDefinition() {
        return this.columnDefinition;
    }

    public void setColumnDefinition(String columnDefinition) {
        this.columnDefinition = columnDefinition;
    }

    public boolean isForeignAutoCreate() {
        return this.foreignAutoCreate;
    }

    public void setForeignAutoCreate(boolean foreignAutoCreate) {
        this.foreignAutoCreate = foreignAutoCreate;
    }

    public boolean isVersion() {
        return this.version;
    }

    public void setVersion(boolean version) {
        this.version = version;
    }

    public String getForeignColumnName() {
        return this.foreignColumnName;
    }

    public void setForeignColumnName(String foreignColumnName) {
        this.foreignColumnName = foreignColumnName;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public static DatabaseFieldConfig fromField(DatabaseType databaseType, String tableName, Field field) throws SQLException {
        DatabaseField databaseField = (DatabaseField) field.getAnnotation(DatabaseField.class);
        if (databaseField == null) {
            ForeignCollectionField foreignCollection = (ForeignCollectionField) field.getAnnotation(ForeignCollectionField.class);
            if (foreignCollection != null) {
                return fromForeignCollection(databaseType, field, foreignCollection);
            }
            if (javaxPersistenceConfigurer != null) {
                return javaxPersistenceConfigurer.createFieldConfig(databaseType, field);
            }
            return null;
        } else if (databaseField.persisted()) {
            return fromDatabaseField(databaseType, tableName, field, databaseField);
        } else {
            return null;
        }
    }

    public static Method findGetMethod(Field field, boolean throwExceptions) throws IllegalArgumentException {
        Method fieldGetMethod;
        if (Locale.ENGLISH.equals(Locale.getDefault())) {
            fieldGetMethod = findMethodFromNames(field, true, throwExceptions, methodFromField(field, "get", null), methodFromField(field, "is", null));
        } else {
            fieldGetMethod = findMethodFromNames(field, true, throwExceptions, methodFromField(field, "get", null), methodFromField(field, "get", Locale.ENGLISH), methodFromField(field, "is", null), methodFromField(field, "is", Locale.ENGLISH));
        }
        if (fieldGetMethod == null) {
            return null;
        }
        if (fieldGetMethod.getReturnType() == field.getType()) {
            return fieldGetMethod;
        }
        if (!throwExceptions) {
            return null;
        }
        throw new IllegalArgumentException("Return type of get method " + fieldGetMethod.getName() + " does not return " + field.getType());
    }

    public static Method findSetMethod(Field field, boolean throwExceptions) throws IllegalArgumentException {
        Method fieldSetMethod;
        if (Locale.ENGLISH.equals(Locale.getDefault())) {
            fieldSetMethod = findMethodFromNames(field, false, throwExceptions, methodFromField(field, "set", null));
        } else {
            fieldSetMethod = findMethodFromNames(field, false, throwExceptions, methodFromField(field, "set", null), methodFromField(field, "set", Locale.ENGLISH));
        }
        if (fieldSetMethod == null) {
            return null;
        }
        if (fieldSetMethod.getReturnType() == Void.TYPE) {
            return fieldSetMethod;
        }
        if (!throwExceptions) {
            return null;
        }
        throw new IllegalArgumentException("Return type of set method " + fieldSetMethod.getName() + " returns " + fieldSetMethod.getReturnType() + " instead of void");
    }

    public static DatabaseFieldConfig fromDatabaseField(DatabaseType databaseType, String tableName, Field field, DatabaseField databaseField) {
        DatabaseFieldConfig config = new DatabaseFieldConfig();
        config.fieldName = field.getName();
        if (databaseType.isEntityNamesMustBeUpCase()) {
            config.fieldName = databaseType.upCaseEntityName(config.fieldName);
        }
        config.columnName = valueIfNotBlank(databaseField.columnName());
        config.dataType = databaseField.dataType();
        String defaultValue = databaseField.defaultValue();
        if (!defaultValue.equals(DatabaseField.DEFAULT_STRING)) {
            config.defaultValue = defaultValue;
        }
        config.width = databaseField.width();
        config.canBeNull = databaseField.canBeNull();
        config.id = databaseField.id();
        config.generatedId = databaseField.generatedId();
        config.generatedIdSequence = valueIfNotBlank(databaseField.generatedIdSequence());
        config.foreign = databaseField.foreign();
        config.useGetSet = databaseField.useGetSet();
        config.unknownEnumValue = findMatchingEnumVal(field, databaseField.unknownEnumName());
        config.throwIfNull = databaseField.throwIfNull();
        config.format = valueIfNotBlank(databaseField.format());
        config.unique = databaseField.unique();
        config.uniqueCombo = databaseField.uniqueCombo();
        config.index = databaseField.index();
        config.indexName = valueIfNotBlank(databaseField.indexName());
        config.uniqueIndex = databaseField.uniqueIndex();
        config.uniqueIndexName = valueIfNotBlank(databaseField.uniqueIndexName());
        config.foreignAutoRefresh = databaseField.foreignAutoRefresh();
        if (config.foreignAutoRefresh || databaseField.maxForeignAutoRefreshLevel() != 2) {
            config.maxForeignAutoRefreshLevel = databaseField.maxForeignAutoRefreshLevel();
        } else {
            config.maxForeignAutoRefreshLevel = -1;
        }
        config.persisterClass = databaseField.persisterClass();
        config.allowGeneratedIdInsert = databaseField.allowGeneratedIdInsert();
        config.columnDefinition = valueIfNotBlank(databaseField.columnDefinition());
        config.foreignAutoCreate = databaseField.foreignAutoCreate();
        config.version = databaseField.version();
        config.foreignColumnName = valueIfNotBlank(databaseField.foreignColumnName());
        config.readOnly = databaseField.readOnly();
        return config;
    }

    public void postProcess() {
        if (this.foreignColumnName != null) {
            this.foreignAutoRefresh = true;
        }
        if (this.foreignAutoRefresh && this.maxForeignAutoRefreshLevel == -1) {
            this.maxForeignAutoRefreshLevel = 2;
        }
    }

    public static Enum<?> findMatchingEnumVal(Field field, String unknownEnumName) {
        if (unknownEnumName == null || unknownEnumName.length() == 0) {
            return null;
        }
        for (Enum<?> enumVal : (Enum[]) field.getType().getEnumConstants()) {
            if (enumVal.name().equals(unknownEnumName)) {
                return enumVal;
            }
        }
        throw new IllegalArgumentException("Unknwown enum unknown name " + unknownEnumName + " for field " + field);
    }

    private static DatabaseFieldConfig fromForeignCollection(DatabaseType databaseType, Field field, ForeignCollectionField foreignCollection) {
        DatabaseFieldConfig config = new DatabaseFieldConfig();
        config.fieldName = field.getName();
        if (foreignCollection.columnName().length() > 0) {
            config.columnName = foreignCollection.columnName();
        }
        config.foreignCollection = true;
        config.foreignCollectionEager = foreignCollection.eager();
        config.foreignCollectionMaxEagerLevel = foreignCollection.maxEagerLevel();
        config.foreignCollectionOrderColumnName = valueIfNotBlank(foreignCollection.orderColumnName());
        config.foreignCollectionOrderAscending = foreignCollection.orderAscending();
        config.foreignCollectionColumnName = valueIfNotBlank(foreignCollection.columnName());
        config.foreignCollectionForeignFieldName = valueIfNotBlank(foreignCollection.foreignFieldName());
        return config;
    }

    private String findIndexName(String tableName) {
        if (this.columnName == null) {
            return tableName + "_" + this.fieldName + "_idx";
        }
        return tableName + "_" + this.columnName + "_idx";
    }

    private static String valueIfNotBlank(String newValue) {
        if (newValue == null || newValue.length() == 0) {
            return null;
        }
        return newValue;
    }

    private static Method findMethodFromNames(Field field, boolean isGetMethod, boolean throwExceptions, String... methodNames) {
        NoSuchMethodException firstException = null;
        String[] arr$ = methodNames;
        int len$ = arr$.length;
        int i$ = 0;
        while (i$ < len$) {
            String methodName = arr$[i$];
            if (isGetMethod) {
                try {
                    return field.getDeclaringClass().getMethod(methodName, new Class[0]);
                } catch (NoSuchMethodException nsme) {
                    if (firstException == null) {
                        firstException = nsme;
                    }
                    i$++;
                }
            } else {
                return field.getDeclaringClass().getMethod(methodName, new Class[]{field.getType()});
            }
        }
        if (!throwExceptions) {
            return null;
        }
        throw new IllegalArgumentException("Could not find appropriate " + (isGetMethod ? "get" : "set") + " method for " + field, firstException);
    }

    private static String methodFromField(Field field, String prefix, Locale locale) {
        String name = field.getName();
        String start = name.substring(0, 1);
        if (locale == null) {
            start = start.toUpperCase();
        } else {
            start = start.toUpperCase(locale);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        sb.append(start);
        sb.append(name, 1, name.length());
        return sb.toString();
    }
}
