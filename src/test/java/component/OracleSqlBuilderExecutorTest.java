package component;

import core.DbType;
import core.Table;
import core.TableThread;
import main.SqlBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import util.ExcelDataTranslateTableTool;
import util.ReadExcelTool;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class OracleSqlBuilderExecutorTest {

    @Test
    public void getCreateTableSql() throws Exception {
        //连接到文件
        File tableTemplate = Resources.getResourceAsFile("table_template.xlsx").getAbsoluteFile();
        //获取表格数据
        List<String[]> data = ReadExcelTool.readExcel(tableTemplate);
        //获取自定义参数化配置
        Properties custom = Resources.getResourceAsProperties("setting.properties");

        List<Table> tables = ExcelDataTranslateTableTool.getTables(data);

        for (Table table : tables) {
            SqlBuilder sqlBuilder = new SqlBuilder(table, custom, DbType.oracle);

            System.out.println(sqlBuilder.getCreateTableSql());
        }

    }
}