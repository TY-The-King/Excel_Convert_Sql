package main;

import component.MySqlBuilderExecutor;
import component.OracleSqlBuilderExecutor;
import component.SqlBuilderExecutor;
import core.DbType;
import core.Table;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Properties;

/**
 * @author dxw
 * @ClassName SqlBuilder.java
 * @Description SQL语句创建器
 * @createTime 2019-06-05 14:46
 */
public class SqlBuilder {

    private SqlBuilderExecutor sqlBuilderExecutor;

    public SqlBuilder(Table table, Properties properties, @NotNull DbType dbType) {
        switch (dbType) {
            case mysql:
                sqlBuilderExecutor = new MySqlBuilderExecutor(table, properties);
                break;
            case oracle:
                sqlBuilderExecutor = new OracleSqlBuilderExecutor(table, properties);
                break;
            default:
                System.err.println("暂不支持的数据库类型");
                System.exit(0);
                break;
        }
    }

    public List<String> getCreateTableSql() {
        return sqlBuilderExecutor.getCreateTableSql();
    }

}
