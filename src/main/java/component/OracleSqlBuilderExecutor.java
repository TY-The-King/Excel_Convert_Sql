package component;

import core.Column;
import core.Table;
import core.TableThread;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import util.FieldType;
import util.WriteTxtTool;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author dxw
 * @className OracleSqlBuilderExecutor.java
 * @description Oracle建表语句生成器 oracle 语句后面不能有分号,否则执行时会报错.
 * @createTime 2021-05-25 10:25
 */
public class OracleSqlBuilderExecutor extends AbstractSqlBuilderExecutor {

    public OracleSqlBuilderExecutor(Table table, Properties properties) {
        super(table, properties);
    }

    Logger logger = LoggerFactory.getLogger(OracleSqlBuilderExecutor.class);

    @Override
    public List<String> getCreateTableSql() {
        final String commentTemplate = "COMMENT ON COLUMN %s.%s is '%s';";
        final String columnTemplate = "\"%s\" ";
        final String tableCommentTemplate = "COMMENT ON TABLE %S IS '%S';";
        int j = 1;
        StringBuilder createTableBuilder = new StringBuilder(1000);
        List<String> commentList = new ArrayList<>();
        String tableName = table.getTableName().toUpperCase();
        createTableBuilder.append("create table \"").append(tableName).append("\"");
        createTableBuilder.append(" ( ");
        for (Column column : table.getColumnList()) {
            createTableBuilder.append(String.format(columnTemplate, column.getColumnName()));
            //有的数据类型可以不设置长度
            if (betterCheckUnSetLengthType(column.getColumnType())) {
                createTableBuilder.append(column.getColumnType()).append(" ");
            } else {
                createTableBuilder.append(column.getColumnType()).append("(").append(column.getColumnLength()).append(")").append(" ");
            }
            createTableBuilder.append(",");
            // 设置comment
            if (!StringUtils.isEmpty(column.getColumnComment())) {
                //设置表comment
                if (j == 1) {
                    commentList.add(String.format(tableCommentTemplate, table.getTableName(), table.getTableComment()));
                    j++;
                }
                //设置字段comment
                commentList.add(String.format(commentTemplate, tableName, column.getColumnName().toUpperCase(), column.getColumnComment()));
            }
        }
        //去除最后一个逗号
        int i = createTableBuilder.lastIndexOf(",");
        createTableBuilder.delete(i, i + 1);
        createTableBuilder.append(" );");
        List<String> sqlList = new ArrayList<>();
        sqlList.add(createTableBuilder.toString());
        sqlList.addAll(commentList);
        //设置生成的SQL语句输出文本位置
        String filePath = properties.getProperty("sqlFilePath");
        String dateFormat = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHH:mm:ss"));
        String fileName = "createTable_" + dateFormat + ".sql";
        WriteTxtTool writeTxtTool = new WriteTxtTool();
        //将生成的sql语句，输出到文本中
        try {
            writeTxtTool.writeFile(sqlList, filePath + fileName);
            logger.info("{}-生成建表sql成功,查看文件位置:{}", table.getTableName(), filePath);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return sqlList;
    }

    @Override
    public boolean checkUnSetLengthType(String type) {

        // 默认为false,对于下面列出来的不能或不需要设置长度
        // 参考文档 https://docs.oracle.com/en/database/oracle/oracle-database/19/sqlrf/Data-Types.html#GUID-7B72E154-677A-4342-A1EA-C74C1EA928E6
        if (StringUtils.isEmpty(type)) {
            return false;
        }
        if (type.compareToIgnoreCase("NUMBER") == 0) {
            return true;
        }
        if (type.compareToIgnoreCase("LONG") == 0) {
            return true;
        }
        if (type.compareToIgnoreCase("INT") == 0) {
            return true;
        }
        if (type.compareToIgnoreCase("BINARY_FLOAT") == 0) {
            return true;
        }
        if (type.compareToIgnoreCase("BINARY_DOUBLE") == 0) {
            return true;
        }
        if (type.compareToIgnoreCase("TIMESTAMP") == 0) {
            return true;
        }
        if (type.compareToIgnoreCase("INTERVAL YEAR") == 0) {
            return true;
        }
        if (type.compareToIgnoreCase("INTERVAL DAY") == 0) {
            return true;
        }
        if (type.compareToIgnoreCase("LONG RAW") == 0) {
            return true;
        }
        if (type.compareToIgnoreCase("CHAR") == 0) {
            return true;
        }
        if (type.compareToIgnoreCase("NCHAR") == 0) {
            return true;
        }
        if (type.compareToIgnoreCase("DATE") == 0) {
            return true;
        }
        if (type.compareToIgnoreCase("CLOB") == 0) {
            return true;
        }
        if (type.compareToIgnoreCase("BLOB") == 0) {
            return true;
        }
        if (type.compareToIgnoreCase("BFILE") == 0) {
            return true;
        }
        if (type.compareToIgnoreCase("FLOAT") == 0) {
            String floatSetting = properties.getProperty("type_float_auto");
            return floatSetting != null && floatSetting.equals("1");
        }
        return false;
    }
}
