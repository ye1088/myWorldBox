package com.j256.ormlite.field.types;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.support.DatabaseResults;
import java.sql.SQLException;

public class BooleanCharType extends BooleanType {
    private static final String DEFAULT_TRUE_FALSE_FORMAT = "10";
    private static final BooleanCharType singleTon = new BooleanCharType();

    public static BooleanCharType getSingleton() {
        return singleTon;
    }

    public BooleanCharType() {
        super(SqlType.STRING);
    }

    public Object parseDefaultString(FieldType fieldType, String defaultStr) {
        return javaToSqlArg(fieldType, Boolean.valueOf(Boolean.parseBoolean(defaultStr)));
    }

    public Object javaToSqlArg(FieldType fieldType, Object obj) {
        String format = (String) fieldType.getDataTypeConfigObj();
        return Character.valueOf(((Boolean) obj).booleanValue() ? format.charAt(0) : format.charAt(1));
    }

    public Object resultToSqlArg(FieldType fieldType, DatabaseResults results, int columnPos) throws SQLException {
        return Character.valueOf(results.getChar(columnPos));
    }

    public Object sqlArgToJava(FieldType fieldType, Object sqlArg, int columnPos) {
        return ((Character) sqlArg).charValue() == ((String) fieldType.getDataTypeConfigObj()).charAt(0) ? Boolean.TRUE : Boolean.FALSE;
    }

    public Object resultStringToJava(FieldType fieldType, String stringValue, int columnPos) {
        if (stringValue.length() == 0) {
            return Boolean.FALSE;
        }
        return sqlArgToJava(fieldType, Character.valueOf(stringValue.charAt(0)), columnPos);
    }

    public Object makeConfigObject(FieldType fieldType) throws SQLException {
        String format = fieldType.getFormat();
        if (format == null) {
            return "10";
        }
        if (format.length() == 2 && format.charAt(0) != format.charAt(1)) {
            return format;
        }
        throw new SQLException("Invalid boolean format must have 2 different characters that represent true/false like \"10\" or \"tf\": " + format);
    }
}
