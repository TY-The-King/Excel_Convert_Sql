package core;

import main.SqlBuilder;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

/**
 * @author dxw
 * @ClassName TableThread.java
 * @Description 表创建线程，一次性创建多个表时使用
 * @createTime 2019-06-21 10:26
 */
public class TableThread extends Thread {

    private Properties properties = Resources.getResourceAsProperties("setting.properties");

    private SqlBuilder sqlBuilder;

    private SqlSessionFactory sqlSessionFactory;

    Logger logger = LoggerFactory.getLogger(TableThread.class);

    public TableThread(SqlBuilder sqlBuilder, SqlSessionFactory sqlSessionFactory) throws IOException {
        this.sqlBuilder = sqlBuilder;
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public void run() {
        //创建sql
        List<String> sqlList = sqlBuilder.getCreateTableSql();
        //读取配置是否执行SQL语句
        boolean isCommitSql = Boolean.getBoolean(properties.getProperty("is_commit_sql"));
        if (isCommitSql) {
            for (String sql : sqlList) {
                executeSql(sql);
            }
        }
    }

    private void executeSql(String sql) {
        //获取连接
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PreparedStatement preparedStatement = null;
        try {
            logger.info(sql);
            preparedStatement = sqlSession.getConnection().prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //关闭
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }
}
