package component;

import core.Column;
import core.Table;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import util.WriteTxtTool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @author dxw
 * @ClassName AbstractSqlBuilderExecutor.java
 * @Description 抽象的sql建表语句构建器, 其实现方法为mysql, 如果不为mysql, 需要重写相关方法
 * @date 2021-05-25 10:16
 */
public abstract class AbstractSqlBuilderExecutor implements SqlBuilderExecutor {

    /**
     * 表信息
     */
    protected Table table;

    /**
     * 影响语句创建的配置项
     */
    protected Properties properties;
    Logger logger = LoggerFactory.getLogger(AbstractSqlBuilderExecutor.class);

    public AbstractSqlBuilderExecutor(Table table, Properties properties) {
        this.table = table;
        this.properties = properties;
    }

    @Override
    public List<String> getCreateTableSql() {
        StringBuilder builder = new StringBuilder(1000);
        builder.append("create table `" + table.getTableName() + "`");
        builder.append(" ( ");
        for (Column column : table.getColumnList()) {
            builder.append(column.getColumnName() + " ");
            //部分数据类型特殊处理
            if (checkUnSetLengthType(column.getColumnType())) {
                builder.append(column.getColumnType() + " ");
            } else {
                builder.append(column.getColumnType() + "(" + column.getColumnLength() + ")" + " ");
            }
            builder.append("comment '" + column.getColumnComment() + "'" + " ");
            builder.append(",");
        }
        //去除最后一个逗号
        int i = builder.lastIndexOf(",");
        builder.delete(i, i + 1);
        builder.append(" ) ");
        builder.append(" comment = '" + table.getTableComment() + "' ");
        ArrayList<String> sqlList = new ArrayList<>();
        sqlList.add(builder.toString());
        //设置生成的SQL语句输出文本位置
        String path = "D:/mysqlSQL.txt";
        WriteTxtTool writeTxtTool = new WriteTxtTool();
        //将生成的sql语句，输出到文本中
        try {
            writeTxtTool.writeFile(sqlList,path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("mysql:"+table.getTableName()+"表，暂不执行数据库建表语句，只生成SQL文本文件！");
        return sqlList;
    }

    /**
     * 当数据类型不需要设置长度时返回true，否则返回false
     *
     * @param type
     * @return
     */
    @Override
    public boolean checkUnSetLengthType(String type) {
        if (StringUtils.isEmpty(type)) {
            return false;
        }
        if (type.compareToIgnoreCase("DATETIME") == 0) {
            return true;
        }
        if (type.compareToIgnoreCase("DATE") == 0) {
            return true;
        }
        if (type.compareToIgnoreCase("TIME") == 0) {
            return true;
        }
        if (type.compareToIgnoreCase("YEAR") == 0) {
            return true;
        }
        if (type.compareToIgnoreCase("TIMESTAMP") == 0) {
            return true;
        }
        if (type.compareToIgnoreCase("TEXT") == 0) {
            return true;
        }
        if (type.compareToIgnoreCase("DOUBLE") == 0) {
            String doubleSetting = properties.getProperty("type_double_auto");
            if (doubleSetting != null && doubleSetting.equals("1")) {
                return true;
            }
            return false;
        }
        if (type.compareToIgnoreCase("FLOAT") == 0) {
            String floatSetting = properties.getProperty("type_float_auto");
            if (floatSetting != null && floatSetting.equals("1")) {
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * 优化CheckUnSetLengthType方法
     * @author TheKing
     * @date 2021/11/4 17:19
     * @param type-字段类型
     * @return boolean
     */
    @Override
    public boolean betterCheckUnSetLengthType(@NotNull String type) {
        //不需要设置长度的字段类型数组
        String columnArray = properties.getProperty("no_set_length_column_type");
        List<String> columnList = Arrays.asList(columnArray.split(","));
        //MySQL数据库中的字段类型FLOAT
        String mysqlFloat = "FLOAT";
        String mysqlDouble = "DOUBLE";
        final String i="1";
        if (type.compareToIgnoreCase(mysqlFloat) == 0) {
            String floatSetting = properties.getProperty("type_float_auto");
            return i.equals(floatSetting);
        }
        else if (type.compareToIgnoreCase(mysqlDouble) == 0){
            String doubleSetting = properties.getProperty("type_double_auto");
            return i.equals(doubleSetting);
        }
        else {
            return columnList.contains(type);
        }
    }
}
