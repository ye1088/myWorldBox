package com.j256.ormlite.misc;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.DataPersisterManager;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseFieldConfig;
import java.lang.reflect.Field;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

public class JavaxPersistenceImpl implements JavaxPersistenceConfigurer {
    public DatabaseFieldConfig createFieldConfig(DatabaseType databaseType, Field field) {
        Column columnAnnotation = (Column) field.getAnnotation(Column.class);
        Basic basicAnnotation = (Basic) field.getAnnotation(Basic.class);
        Id idAnnotation = (Id) field.getAnnotation(Id.class);
        GeneratedValue generatedValueAnnotation = (GeneratedValue) field.getAnnotation(GeneratedValue.class);
        OneToOne oneToOneAnnotation = (OneToOne) field.getAnnotation(OneToOne.class);
        ManyToOne manyToOneAnnotation = (ManyToOne) field.getAnnotation(ManyToOne.class);
        JoinColumn joinColumnAnnotation = (JoinColumn) field.getAnnotation(JoinColumn.class);
        Enumerated enumeratedAnnotation = (Enumerated) field.getAnnotation(Enumerated.class);
        Version versionAnnotation = (Version) field.getAnnotation(Version.class);
        if (columnAnnotation == null && basicAnnotation == null && idAnnotation == null && oneToOneAnnotation == null && manyToOneAnnotation == null && enumeratedAnnotation == null && versionAnnotation == null) {
            return null;
        }
        boolean z;
        DatabaseFieldConfig config = new DatabaseFieldConfig();
        String fieldName = field.getName();
        if (databaseType.isEntityNamesMustBeUpCase()) {
            fieldName = databaseType.upCaseEntityName(fieldName);
        }
        config.setFieldName(fieldName);
        if (columnAnnotation != null) {
            if (stringNotEmpty(columnAnnotation.name())) {
                config.setColumnName(columnAnnotation.name());
            }
            if (stringNotEmpty(columnAnnotation.columnDefinition())) {
                config.setColumnDefinition(columnAnnotation.columnDefinition());
            }
            config.setWidth(columnAnnotation.length());
            config.setCanBeNull(columnAnnotation.nullable());
            config.setUnique(columnAnnotation.unique());
        }
        if (basicAnnotation != null) {
            config.setCanBeNull(basicAnnotation.optional());
        }
        if (idAnnotation != null) {
            if (generatedValueAnnotation == null) {
                config.setId(true);
            } else {
                config.setGeneratedId(true);
            }
        }
        if (!(oneToOneAnnotation == null && manyToOneAnnotation == null)) {
            if (Collection.class.isAssignableFrom(field.getType()) || ForeignCollection.class.isAssignableFrom(field.getType())) {
                config.setForeignCollection(true);
                if (joinColumnAnnotation != null && stringNotEmpty(joinColumnAnnotation.name())) {
                    config.setForeignCollectionColumnName(joinColumnAnnotation.name());
                }
                if (manyToOneAnnotation != null) {
                    FetchType fetchType = manyToOneAnnotation.fetch();
                    if (fetchType != null && fetchType == FetchType.EAGER) {
                        config.setForeignCollectionEager(true);
                    }
                }
            } else {
                config.setForeign(true);
                if (joinColumnAnnotation != null) {
                    if (stringNotEmpty(joinColumnAnnotation.name())) {
                        config.setColumnName(joinColumnAnnotation.name());
                    }
                    config.setCanBeNull(joinColumnAnnotation.nullable());
                    config.setUnique(joinColumnAnnotation.unique());
                }
            }
        }
        if (enumeratedAnnotation != null) {
            EnumType enumType = enumeratedAnnotation.value();
            if (enumType == null || enumType != EnumType.STRING) {
                config.setDataType(DataType.ENUM_INTEGER);
            } else {
                config.setDataType(DataType.ENUM_STRING);
            }
        }
        if (versionAnnotation != null) {
            config.setVersion(true);
        }
        if (config.getDataPersister() == null) {
            config.setDataPersister(DataPersisterManager.lookupForField(field));
        }
        if (DatabaseFieldConfig.findGetMethod(field, false) == null || DatabaseFieldConfig.findSetMethod(field, false) == null) {
            z = false;
        } else {
            z = true;
        }
        config.setUseGetSet(z);
        return config;
    }

    public String getEntityName(Class<?> clazz) {
        Entity entityAnnotation = (Entity) clazz.getAnnotation(Entity.class);
        Table tableAnnotation = (Table) clazz.getAnnotation(Table.class);
        if (entityAnnotation != null && stringNotEmpty(entityAnnotation.name())) {
            return entityAnnotation.name();
        }
        if (tableAnnotation == null || !stringNotEmpty(tableAnnotation.name())) {
            return null;
        }
        return tableAnnotation.name();
    }

    private boolean stringNotEmpty(String value) {
        return value != null && value.length() > 0;
    }
}
