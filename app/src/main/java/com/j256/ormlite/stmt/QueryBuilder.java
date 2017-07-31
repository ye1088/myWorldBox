package com.j256.ormlite.stmt;

import com.j256.ormlite.dao.CloseableIterator;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.stmt.StatementBuilder.StatementType;
import com.j256.ormlite.stmt.query.ColumnNameOrRawSql;
import com.j256.ormlite.stmt.query.OrderBy;
import com.j256.ormlite.table.TableInfo;
import io.netty.handler.codec.http.HttpConstants;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.util.internal.StringUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QueryBuilder<T, ID> extends StatementBuilder<T, ID> {
    private String alias;
    private String countOfQuery;
    private boolean distinct;
    private List<ColumnNameOrRawSql> groupByList;
    private String having;
    private final FieldType idField;
    private boolean isInnerQuery;
    private List<JoinInfo> joinList;
    private Long limit;
    private Long offset;
    private List<OrderBy> orderByList;
    private FieldType[] resultFieldTypes;
    private boolean selectIdColumn;
    private List<ColumnNameOrRawSql> selectList;

    public static class InternalQueryBuilderWrapper {
        private final QueryBuilder<?, ?> queryBuilder;

        InternalQueryBuilderWrapper(QueryBuilder<?, ?> queryBuilder) {
            this.queryBuilder = queryBuilder;
        }

        public void appendStatementString(StringBuilder sb, List<ArgumentHolder> argList) throws SQLException {
            this.queryBuilder.appendStatementString(sb, argList);
        }

        public FieldType[] getResultFieldTypes() {
            return this.queryBuilder.getResultFieldTypes();
        }
    }

    private class JoinInfo {
        FieldType localField;
        JoinWhereOperation operation;
        final QueryBuilder<?, ?> queryBuilder;
        FieldType remoteField;
        final JoinType type;

        public JoinInfo(JoinType type, QueryBuilder<?, ?> queryBuilder, JoinWhereOperation operation) {
            this.type = type;
            this.queryBuilder = queryBuilder;
            this.operation = operation;
        }
    }

    public enum JoinType {
        INNER("INNER"),
        LEFT("LEFT");
        
        private String sql;

        private JoinType(String sql) {
            this.sql = sql;
        }
    }

    public enum JoinWhereOperation {
        AND(WhereOperation.AND),
        OR(WhereOperation.OR);
        
        private WhereOperation whereOperation;

        private JoinWhereOperation(WhereOperation whereOperation) {
            this.whereOperation = whereOperation;
        }
    }

    public QueryBuilder(DatabaseType databaseType, TableInfo<T, ID> tableInfo, Dao<T, ID> dao) {
        super(databaseType, tableInfo, dao, StatementType.SELECT);
        this.idField = tableInfo.getIdField();
        this.selectIdColumn = this.idField != null;
    }

    void enableInnerQuery() {
        this.isInnerQuery = true;
    }

    int getSelectColumnCount() {
        if (this.countOfQuery != null) {
            return 1;
        }
        if (this.selectList == null) {
            return 0;
        }
        return this.selectList.size();
    }

    String getSelectColumnsAsString() {
        if (this.countOfQuery != null) {
            return "COUNT(" + this.countOfQuery + ")";
        }
        if (this.selectList == null) {
            return "";
        }
        return this.selectList.toString();
    }

    public PreparedQuery<T> prepare() throws SQLException {
        return super.prepareStatement(this.limit, this.selectList == null);
    }

    public QueryBuilder<T, ID> selectColumns(String... columns) {
        for (String column : columns) {
            addSelectColumnToList(column);
        }
        return this;
    }

    public QueryBuilder<T, ID> selectColumns(Iterable<String> columns) {
        for (String column : columns) {
            addSelectColumnToList(column);
        }
        return this;
    }

    public QueryBuilder<T, ID> selectRaw(String... columns) {
        for (String column : columns) {
            addSelectToList(ColumnNameOrRawSql.withRawSql(column));
        }
        return this;
    }

    public QueryBuilder<T, ID> groupBy(String columnName) {
        if (verifyColumnName(columnName).isForeignCollection()) {
            throw new IllegalArgumentException("Can't groupBy foreign colletion field: " + columnName);
        }
        addGroupBy(ColumnNameOrRawSql.withColumnName(columnName));
        return this;
    }

    public QueryBuilder<T, ID> groupByRaw(String rawSql) {
        addGroupBy(ColumnNameOrRawSql.withRawSql(rawSql));
        return this;
    }

    public QueryBuilder<T, ID> orderBy(String columnName, boolean ascending) {
        if (verifyColumnName(columnName).isForeignCollection()) {
            throw new IllegalArgumentException("Can't orderBy foreign colletion field: " + columnName);
        }
        addOrderBy(new OrderBy(columnName, ascending));
        return this;
    }

    public QueryBuilder<T, ID> orderByRaw(String rawSql) {
        addOrderBy(new OrderBy(rawSql, (ArgumentHolder[]) null));
        return this;
    }

    public QueryBuilder<T, ID> orderByRaw(String rawSql, ArgumentHolder... args) {
        addOrderBy(new OrderBy(rawSql, args));
        return this;
    }

    public QueryBuilder<T, ID> distinct() {
        this.distinct = true;
        this.selectIdColumn = false;
        return this;
    }

    public QueryBuilder<T, ID> limit(Long maxRows) {
        this.limit = maxRows;
        return this;
    }

    public QueryBuilder<T, ID> offset(Long startRow) throws SQLException {
        if (this.databaseType.isOffsetSqlSupported()) {
            this.offset = startRow;
            return this;
        }
        throw new SQLException("Offset is not supported by this database");
    }

    public QueryBuilder<T, ID> setCountOf(boolean countOf) {
        return setCountOf(WebSocketServerHandshaker.SUB_PROTOCOL_WILDCARD);
    }

    public QueryBuilder<T, ID> setCountOf(String countOfQuery) {
        this.countOfQuery = countOfQuery;
        return this;
    }

    public QueryBuilder<T, ID> having(String having) {
        this.having = having;
        return this;
    }

    public QueryBuilder<T, ID> join(QueryBuilder<?, ?> joinedQueryBuilder) throws SQLException {
        addJoinInfo(JoinType.INNER, null, null, joinedQueryBuilder, JoinWhereOperation.AND);
        return this;
    }

    public QueryBuilder<T, ID> join(QueryBuilder<?, ?> joinedQueryBuilder, JoinType type, JoinWhereOperation operation) throws SQLException {
        addJoinInfo(type, null, null, joinedQueryBuilder, operation);
        return this;
    }

    public QueryBuilder<T, ID> joinOr(QueryBuilder<?, ?> joinedQueryBuilder) throws SQLException {
        addJoinInfo(JoinType.INNER, null, null, joinedQueryBuilder, JoinWhereOperation.OR);
        return this;
    }

    public QueryBuilder<T, ID> leftJoin(QueryBuilder<?, ?> joinedQueryBuilder) throws SQLException {
        addJoinInfo(JoinType.LEFT, null, null, joinedQueryBuilder, JoinWhereOperation.AND);
        return this;
    }

    public QueryBuilder<T, ID> leftJoinOr(QueryBuilder<?, ?> joinedQueryBuilder) throws SQLException {
        addJoinInfo(JoinType.LEFT, null, null, joinedQueryBuilder, JoinWhereOperation.OR);
        return this;
    }

    public QueryBuilder<T, ID> join(String localColumnName, String joinedColumnName, QueryBuilder<?, ?> joinedQueryBuilder) throws SQLException {
        addJoinInfo(JoinType.INNER, localColumnName, joinedColumnName, joinedQueryBuilder, JoinWhereOperation.AND);
        return this;
    }

    public QueryBuilder<T, ID> join(String localColumnName, String joinedColumnName, QueryBuilder<?, ?> joinedQueryBuilder, JoinType type, JoinWhereOperation operation) throws SQLException {
        addJoinInfo(type, localColumnName, joinedColumnName, joinedQueryBuilder, operation);
        return this;
    }

    public List<T> query() throws SQLException {
        return this.dao.query(prepare());
    }

    public GenericRawResults<String[]> queryRaw() throws SQLException {
        return this.dao.queryRaw(prepareStatementString(), new String[0]);
    }

    public T queryForFirst() throws SQLException {
        return this.dao.queryForFirst(prepare());
    }

    public String[] queryRawFirst() throws SQLException {
        return (String[]) this.dao.queryRaw(prepareStatementString(), new String[0]).getFirstResult();
    }

    public CloseableIterator<T> iterator() throws SQLException {
        return this.dao.iterator(prepare());
    }

    public long countOf() throws SQLException {
        String countOfQuerySave = this.countOfQuery;
        try {
            setCountOf(true);
            long countOf = this.dao.countOf(prepare());
            return countOf;
        } finally {
            setCountOf(countOfQuerySave);
        }
    }

    public long countOf(String countOfQuery) throws SQLException {
        String countOfQuerySave = this.countOfQuery;
        try {
            setCountOf(countOfQuery);
            long countOf = this.dao.countOf(prepare());
            return countOf;
        } finally {
            setCountOf(countOfQuerySave);
        }
    }

    public QueryBuilder<T, ID> setAlias(String alias) {
        this.alias = alias;
        return this;
    }

    public void reset() {
        super.reset();
        this.distinct = false;
        this.selectIdColumn = this.idField != null;
        if (this.selectList != null) {
            this.selectList.clear();
            this.selectList = null;
        }
        if (this.orderByList != null) {
            this.orderByList.clear();
            this.orderByList = null;
        }
        if (this.groupByList != null) {
            this.groupByList.clear();
            this.groupByList = null;
        }
        this.isInnerQuery = false;
        this.countOfQuery = null;
        this.having = null;
        this.limit = null;
        this.offset = null;
        if (this.joinList != null) {
            this.joinList.clear();
            this.joinList = null;
        }
        this.addTableName = false;
        this.alias = null;
    }

    protected void appendStatementStart(StringBuilder sb, List<ArgumentHolder> list) {
        if (this.joinList == null) {
            setAddTableName(false);
        } else {
            setAddTableName(true);
        }
        sb.append("SELECT ");
        if (this.databaseType.isLimitAfterSelect()) {
            appendLimit(sb);
        }
        if (this.distinct) {
            sb.append("DISTINCT ");
        }
        if (this.countOfQuery == null) {
            appendSelects(sb);
        } else {
            this.type = StatementType.SELECT_LONG;
            sb.append("COUNT(").append(this.countOfQuery).append(") ");
        }
        sb.append("FROM ");
        this.databaseType.appendEscapedEntityName(sb, this.tableName);
        if (this.alias != null) {
            appendAlias(sb);
        }
        sb.append(HttpConstants.SP_CHAR);
        if (this.joinList != null) {
            appendJoinSql(sb);
        }
    }

    protected FieldType[] getResultFieldTypes() {
        return this.resultFieldTypes;
    }

    protected boolean appendWhereStatement(StringBuilder sb, List<ArgumentHolder> argList, WhereOperation operation) throws SQLException {
        boolean z = operation == WhereOperation.FIRST;
        if (this.where != null) {
            z = super.appendWhereStatement(sb, argList, operation);
        }
        if (this.joinList != null) {
            for (JoinInfo joinInfo : this.joinList) {
                if (z) {
                    operation = WhereOperation.FIRST;
                } else {
                    operation = joinInfo.operation.whereOperation;
                }
                z = joinInfo.queryBuilder.appendWhereStatement(sb, argList, operation);
            }
        }
        return z;
    }

    protected void appendStatementEnd(StringBuilder sb, List<ArgumentHolder> argList) throws SQLException {
        appendGroupBys(sb);
        appendHaving(sb);
        appendOrderBys(sb, argList);
        if (!this.databaseType.isLimitAfterSelect()) {
            appendLimit(sb);
        }
        appendOffset(sb);
        setAddTableName(false);
    }

    protected boolean shouldPrependTableNameToColumns() {
        return this.joinList != null;
    }

    protected void appendTableQualifier(StringBuilder sb) {
        this.databaseType.appendEscapedEntityName(sb, getTableName());
    }

    protected String getTableName() {
        return this.alias == null ? this.tableName : this.alias;
    }

    private void addOrderBy(OrderBy orderBy) {
        if (this.orderByList == null) {
            this.orderByList = new ArrayList();
        }
        this.orderByList.add(orderBy);
    }

    private void addGroupBy(ColumnNameOrRawSql groupBy) {
        if (this.groupByList == null) {
            this.groupByList = new ArrayList();
        }
        this.groupByList.add(groupBy);
        this.selectIdColumn = false;
    }

    private void setAddTableName(boolean addTableName) {
        this.addTableName = addTableName;
        if (this.joinList != null) {
            for (JoinInfo joinInfo : this.joinList) {
                joinInfo.queryBuilder.setAddTableName(addTableName);
            }
        }
    }

    private void addJoinInfo(JoinType type, String localColumnName, String joinedColumnName, QueryBuilder<?, ?> joinedQueryBuilder, JoinWhereOperation operation) throws SQLException {
        JoinInfo joinInfo = new JoinInfo(type, joinedQueryBuilder, operation);
        if (localColumnName == null) {
            matchJoinedFields(joinInfo, joinedQueryBuilder);
        } else {
            matchJoinedFieldsByName(joinInfo, localColumnName, joinedColumnName, joinedQueryBuilder);
        }
        if (this.joinList == null) {
            this.joinList = new ArrayList();
        }
        this.joinList.add(joinInfo);
    }

    private void matchJoinedFieldsByName(JoinInfo joinInfo, String localColumnName, String joinedColumnName, QueryBuilder<?, ?> joinedQueryBuilder) throws SQLException {
        joinInfo.localField = this.tableInfo.getFieldTypeByColumnName(localColumnName);
        if (joinInfo.localField == null) {
            throw new SQLException("Could not find field in " + this.tableInfo.getDataClass() + " that has column-name '" + localColumnName + "'");
        }
        joinInfo.remoteField = joinedQueryBuilder.tableInfo.getFieldTypeByColumnName(joinedColumnName);
        if (joinInfo.remoteField == null) {
            throw new SQLException("Could not find field in " + joinedQueryBuilder.tableInfo.getDataClass() + " that has column-name '" + joinedColumnName + "'");
        }
    }

    private void matchJoinedFields(JoinInfo joinInfo, QueryBuilder<?, ?> joinedQueryBuilder) throws SQLException {
        for (FieldType fieldType : this.tableInfo.getFieldTypes()) {
            FieldType foreignRefField = fieldType.getForeignRefField();
            if (fieldType.isForeign() && foreignRefField.equals(joinedQueryBuilder.tableInfo.getIdField())) {
                joinInfo.localField = fieldType;
                joinInfo.remoteField = foreignRefField;
                return;
            }
        }
        for (FieldType fieldType2 : joinedQueryBuilder.tableInfo.getFieldTypes()) {
            if (fieldType2.isForeign() && fieldType2.getForeignIdField().equals(this.idField)) {
                joinInfo.localField = this.idField;
                joinInfo.remoteField = fieldType2;
                return;
            }
        }
        throw new SQLException("Could not find a foreign " + this.tableInfo.getDataClass() + " field in " + joinedQueryBuilder.tableInfo.getDataClass() + " or vice versa");
    }

    private void addSelectColumnToList(String columnName) {
        verifyColumnName(columnName);
        addSelectToList(ColumnNameOrRawSql.withColumnName(columnName));
    }

    private void addSelectToList(ColumnNameOrRawSql select) {
        if (this.selectList == null) {
            this.selectList = new ArrayList();
        }
        this.selectList.add(select);
    }

    private void appendJoinSql(StringBuilder sb) {
        for (JoinInfo joinInfo : this.joinList) {
            sb.append(joinInfo.type.sql).append(" JOIN ");
            this.databaseType.appendEscapedEntityName(sb, joinInfo.queryBuilder.tableName);
            if (joinInfo.queryBuilder.alias != null) {
                joinInfo.queryBuilder.appendAlias(sb);
            }
            sb.append(" ON ");
            appendTableQualifier(sb);
            sb.append('.');
            this.databaseType.appendEscapedEntityName(sb, joinInfo.localField.getColumnName());
            sb.append(" = ");
            joinInfo.queryBuilder.appendTableQualifier(sb);
            sb.append('.');
            this.databaseType.appendEscapedEntityName(sb, joinInfo.remoteField.getColumnName());
            sb.append(HttpConstants.SP_CHAR);
            if (joinInfo.queryBuilder.joinList != null) {
                joinInfo.queryBuilder.appendJoinSql(sb);
            }
        }
    }

    private void appendSelects(StringBuilder sb) {
        this.type = StatementType.SELECT;
        if (this.selectList == null) {
            if (this.addTableName) {
                appendTableQualifier(sb);
                sb.append('.');
            }
            sb.append("* ");
            this.resultFieldTypes = this.tableInfo.getFieldTypes();
            return;
        }
        boolean hasId;
        boolean first = true;
        if (this.isInnerQuery) {
            hasId = true;
        } else {
            hasId = false;
        }
        List<FieldType> fieldTypeList = new ArrayList(this.selectList.size() + 1);
        for (ColumnNameOrRawSql select : this.selectList) {
            if (select.getRawSql() != null) {
                this.type = StatementType.SELECT_RAW;
                if (first) {
                    first = false;
                } else {
                    sb.append(", ");
                }
                sb.append(select.getRawSql());
            } else {
                FieldType fieldType = this.tableInfo.getFieldTypeByColumnName(select.getColumnName());
                if (fieldType.isForeignCollection()) {
                    fieldTypeList.add(fieldType);
                } else {
                    if (first) {
                        first = false;
                    } else {
                        sb.append(", ");
                    }
                    appendFieldColumnName(sb, fieldType, fieldTypeList);
                    if (fieldType == this.idField) {
                        hasId = true;
                    }
                }
            }
        }
        if (this.type != StatementType.SELECT_RAW) {
            if (!hasId && this.selectIdColumn) {
                if (!first) {
                    sb.append(StringUtil.COMMA);
                }
                appendFieldColumnName(sb, this.idField, fieldTypeList);
            }
            this.resultFieldTypes = (FieldType[]) fieldTypeList.toArray(new FieldType[fieldTypeList.size()]);
        }
        sb.append(HttpConstants.SP_CHAR);
    }

    private void appendFieldColumnName(StringBuilder sb, FieldType fieldType, List<FieldType> fieldTypeList) {
        appendColumnName(sb, fieldType.getColumnName());
        if (fieldTypeList != null) {
            fieldTypeList.add(fieldType);
        }
    }

    private void appendLimit(StringBuilder sb) {
        if (this.limit != null && this.databaseType.isLimitSqlSupported()) {
            this.databaseType.appendLimitValue(sb, this.limit.longValue(), this.offset);
        }
    }

    private void appendOffset(StringBuilder sb) throws SQLException {
        if (this.offset != null) {
            if (!this.databaseType.isOffsetLimitArgument()) {
                this.databaseType.appendOffsetValue(sb, this.offset.longValue());
            } else if (this.limit == null) {
                throw new SQLException("If the offset is specified, limit must also be specified with this database");
            }
        }
    }

    private void appendGroupBys(StringBuilder sb) {
        boolean first = true;
        if (hasGroupStuff()) {
            appendGroupBys(sb, true);
            first = false;
        }
        if (this.joinList != null) {
            for (JoinInfo joinInfo : this.joinList) {
                if (joinInfo.queryBuilder != null && joinInfo.queryBuilder.hasGroupStuff()) {
                    joinInfo.queryBuilder.appendGroupBys(sb, first);
                    first = false;
                }
            }
        }
    }

    private boolean hasGroupStuff() {
        return (this.groupByList == null || this.groupByList.isEmpty()) ? false : true;
    }

    private void appendGroupBys(StringBuilder sb, boolean first) {
        if (first) {
            sb.append("GROUP BY ");
        }
        for (ColumnNameOrRawSql groupBy : this.groupByList) {
            if (first) {
                first = false;
            } else {
                sb.append(StringUtil.COMMA);
            }
            if (groupBy.getRawSql() == null) {
                appendColumnName(sb, groupBy.getColumnName());
            } else {
                sb.append(groupBy.getRawSql());
            }
        }
        sb.append(HttpConstants.SP_CHAR);
    }

    private void appendOrderBys(StringBuilder sb, List<ArgumentHolder> argList) {
        boolean first = true;
        if (hasOrderStuff()) {
            appendOrderBys(sb, true, argList);
            first = false;
        }
        if (this.joinList != null) {
            for (JoinInfo joinInfo : this.joinList) {
                if (joinInfo.queryBuilder != null && joinInfo.queryBuilder.hasOrderStuff()) {
                    joinInfo.queryBuilder.appendOrderBys(sb, first, argList);
                    first = false;
                }
            }
        }
    }

    private boolean hasOrderStuff() {
        return (this.orderByList == null || this.orderByList.isEmpty()) ? false : true;
    }

    private void appendOrderBys(StringBuilder sb, boolean first, List<ArgumentHolder> argList) {
        if (first) {
            sb.append("ORDER BY ");
        }
        for (OrderBy orderBy : this.orderByList) {
            if (first) {
                first = false;
            } else {
                sb.append(StringUtil.COMMA);
            }
            if (orderBy.getRawSql() == null) {
                appendColumnName(sb, orderBy.getColumnName());
                if (!orderBy.isAscending()) {
                    sb.append(" DESC");
                }
            } else {
                sb.append(orderBy.getRawSql());
                if (orderBy.getOrderByArgs() != null) {
                    for (ArgumentHolder arg : orderBy.getOrderByArgs()) {
                        argList.add(arg);
                    }
                }
            }
        }
        sb.append(HttpConstants.SP_CHAR);
    }

    private void appendColumnName(StringBuilder sb, String columnName) {
        if (this.addTableName) {
            appendTableQualifier(sb);
            sb.append('.');
        }
        this.databaseType.appendEscapedEntityName(sb, columnName);
    }

    private void appendHaving(StringBuilder sb) {
        if (this.having != null) {
            sb.append("HAVING ").append(this.having).append(HttpConstants.SP_CHAR);
        }
    }

    private void appendAlias(StringBuilder sb) {
        sb.append(" AS ");
        this.databaseType.appendEscapedEntityName(sb, this.alias);
    }
}
