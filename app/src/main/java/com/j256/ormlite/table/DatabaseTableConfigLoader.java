package com.j256.ormlite.table;

import com.j256.ormlite.field.DatabaseFieldConfig;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.j256.ormlite.misc.SqlExceptionUtil;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseTableConfigLoader {
    private static final String CONFIG_FILE_END_MARKER = "# --table-end--";
    private static final String CONFIG_FILE_FIELDS_END = "# --table-fields-end--";
    private static final String CONFIG_FILE_FIELDS_START = "# --table-fields-start--";
    private static final String CONFIG_FILE_START_MARKER = "# --table-start--";
    private static final String FIELD_NAME_DATA_CLASS = "dataClass";
    private static final String FIELD_NAME_TABLE_NAME = "tableName";

    public static List<DatabaseTableConfig<?>> loadDatabaseConfigFromReader(BufferedReader reader) throws SQLException {
        List<DatabaseTableConfig<?>> list = new ArrayList();
        while (true) {
            DatabaseTableConfig<?> config = fromReader(reader);
            if (config == null) {
                return list;
            }
            list.add(config);
        }
    }

    public static <T> DatabaseTableConfig<T> fromReader(BufferedReader reader) throws SQLException {
        DatabaseTableConfig<T> config = new DatabaseTableConfig();
        boolean anything = false;
        while (true) {
            try {
                String line = reader.readLine();
                if (!(line == null || line.equals(CONFIG_FILE_END_MARKER))) {
                    if (line.equals(CONFIG_FILE_FIELDS_START)) {
                        readFields(reader, config);
                    } else if (!(line.length() == 0 || line.startsWith("#") || line.equals(CONFIG_FILE_START_MARKER))) {
                        String[] parts = line.split(SimpleComparison.EQUAL_TO_OPERATION, -2);
                        if (parts.length != 2) {
                            throw new SQLException("DatabaseTableConfig reading from stream cannot parse line: " + line);
                        }
                        readTableField(config, parts[0], parts[1]);
                        anything = true;
                    }
                }
            } catch (IOException e) {
                throw SqlExceptionUtil.create("Could not read DatabaseTableConfig from stream", e);
            }
        }
        return anything ? config : null;
    }

    public static <T> void write(BufferedWriter writer, DatabaseTableConfig<T> config) throws SQLException {
        try {
            writeConfig(writer, config);
        } catch (IOException e) {
            throw SqlExceptionUtil.create("Could not write config to writer", e);
        }
    }

    private static <T> void writeConfig(BufferedWriter writer, DatabaseTableConfig<T> config) throws IOException, SQLException {
        writer.append(CONFIG_FILE_START_MARKER);
        writer.newLine();
        if (config.getDataClass() != null) {
            writer.append(FIELD_NAME_DATA_CLASS).append('=').append(config.getDataClass().getName());
            writer.newLine();
        }
        if (config.getTableName() != null) {
            writer.append(FIELD_NAME_TABLE_NAME).append('=').append(config.getTableName());
            writer.newLine();
        }
        writer.append(CONFIG_FILE_FIELDS_START);
        writer.newLine();
        if (config.getFieldConfigs() != null) {
            for (DatabaseFieldConfig field : config.getFieldConfigs()) {
                DatabaseFieldConfigLoader.write(writer, field, config.getTableName());
            }
        }
        writer.append(CONFIG_FILE_FIELDS_END);
        writer.newLine();
        writer.append(CONFIG_FILE_END_MARKER);
        writer.newLine();
    }

    private static <T> void readTableField(DatabaseTableConfig<T> config, String field, String value) {
        if (field.equals(FIELD_NAME_DATA_CLASS)) {
            try {
                config.setDataClass(Class.forName(value));
            } catch (ClassNotFoundException e) {
                throw new IllegalArgumentException("Unknown class specified for dataClass: " + value);
            }
        } else if (field.equals(FIELD_NAME_TABLE_NAME)) {
            config.setTableName(value);
        }
    }

    private static <T> void readFields(BufferedReader reader, DatabaseTableConfig<T> config) throws SQLException {
        List<DatabaseFieldConfig> fields = new ArrayList();
        while (true) {
            try {
                String line = reader.readLine();
                if (line != null && !line.equals(CONFIG_FILE_FIELDS_END)) {
                    DatabaseFieldConfig fieldConfig = DatabaseFieldConfigLoader.fromReader(reader);
                    if (fieldConfig == null) {
                        break;
                    }
                    fields.add(fieldConfig);
                } else {
                    break;
                }
            } catch (IOException e) {
                throw SqlExceptionUtil.create("Could not read next field from config file", e);
            }
        }
        config.setFieldConfigs(fields);
    }
}
