package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import java.lang.reflect.Field;
import java.sql.Date;
import java.sql.Timestamp;
import org.apache.tools.ant.util.DateUtils;

public class SqlDateType extends DateType {
    private static final SqlDateType singleTon = new SqlDateType();
    private static final DateStringFormatConfig sqlDateFormatConfig = new DateStringFormatConfig(DateUtils.ISO8601_DATE_PATTERN);

    public static SqlDateType getSingleton() {
        return singleTon;
    }

    private SqlDateType() {
        super(SqlType.DATE, new Class[]{Date.class});
    }

    protected SqlDateType(SqlType sqlType, Class<?>[] classes) {
        super(sqlType, classes);
    }

    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        return new Date(((Timestamp) sqlArg).getTime());
    }

    public Object javaToSqlArg(FieldType fieldType, Object javaObject) {
        return new Timestamp(((Date) javaObject).getTime());
    }

    protected DateStringFormatConfig getDefaultDateFormatConfig() {
        return sqlDateFormatConfig;
    }

    public boolean isValidForField(Field field) {
        return field.getType() == Date.class;
    }
}
