package component;

import core.Table;

import java.util.Properties;

/**
 * @author dxw
 * @ClassName MySqlBuilderExecutor.java
 * @Description 不覆盖父方法,因为父方法是mysql为准
 * @createTime 2021-05-25 10:09
 */
public class MySqlBuilderExecutor extends AbstractSqlBuilderExecutor {

    public MySqlBuilderExecutor(Table table, Properties properties) {
        super(table, properties);
    }

}
