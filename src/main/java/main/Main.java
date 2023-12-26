package main;

import core.DbType;
import core.Table;
import core.TableThread;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import util.ExcelDataTranslateTableTool;
import util.ReadExcelTool;
import util.WriteTxtTool;

import java.io.File;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author dxw
 * @ClassName Main.java
 * @Description 系统运行入口
 * @createTime 2019-06-05 14:11
 */
public class Main {

    public static void main(String[] args) throws Exception {
        //获取数据库配置文件
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        //获取自定义参数化配置
        Properties custom = Resources.getResourceAsProperties("setting.properties");
        //连接到文件
        File tableTemplate = Resources.getResourceAsFile(custom.getProperty("table_template_name")).getAbsoluteFile();
        //获取表格数据
        List<String[]> data = ReadExcelTool.readExcel(tableTemplate);
        //获取数据库类型
        String driverClass = sqlSessionFactory.getConfiguration().getVariables().getProperty("jdbc.driver");
        DbType dbType = DbType.getByDriverClass(driverClass);
        boolean isCommitSql = Boolean.getBoolean(custom.getProperty("is_commit_sql"));
        if (isCommitSql) {
            System.out.println("执行sql文件");
        }else {
            System.out.println("只生成建表sql文件，不执行sql");
        }

        if (dbType == null) {
            System.err.println("暂不支持的数据库类型");
            System.exit(0);
        }
        //判定数据库类型，1为Oracle数据库
        String createTableMany = custom.getProperty("create_table_many");
        if (createTableMany.equals("1")) {
            List<Table> tables = ExcelDataTranslateTableTool.getTables(data);
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 10, 60, TimeUnit.SECONDS, new LinkedBlockingQueue());
            threadPoolExecutor.prestartCoreThread();
            for (Table table : tables) {
                SqlBuilder sqlBuilder = new SqlBuilder(table, custom, dbType);
                TableThread tableThread = new TableThread(sqlBuilder, sqlSessionFactory);
                threadPoolExecutor.execute(tableThread);
            }
            threadPoolExecutor.shutdown();
        } else {
            //解析数据
            Table table = ExcelDataTranslateTableTool.getTable(data);
            //获取连接
            //创建sql
            SqlBuilder sqlBuilder = new SqlBuilder(table, custom, dbType);
            List<String> sqlList = sqlBuilder.getCreateTableSql();
            for (String sql : sqlList) {
                SqlSession sqlSession = sqlSessionFactory.openSession();
                System.out.println(sql);
                //执行操作
                PreparedStatement preparedStatement = sqlSession.getConnection().prepareStatement(sql);
                preparedStatement.executeUpdate();
                //关闭
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (sqlSession != null) {
                    sqlSession.close();
                }
            }
        }
    }
}
