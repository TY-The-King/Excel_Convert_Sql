package main;

import core.Column;
import core.DbType;
import core.Table;
import junit.framework.TestCase;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class SqlBuilderTest extends TestCase {

    @Test
    public void testGetCreateTableSql() {

        Table table = new Table();
        table.setTableName("1");
        table.setTableComment("111");
        ArrayList<Column> columnList = new ArrayList<>();
        Column e1 = new Column();
        e1.setColumnName("1");
        e1.setColumnType("int");
        e1.setColumnLength("2");
        e1.setColumnComment("1");
        columnList.add(e1);
        Column e2 = new Column();
        e2.setColumnName("2");
        e2.setColumnType("varchar");
        e2.setColumnLength("2");
        e2.setColumnComment("1");
        columnList.add(e2);
        table.setColumnList(columnList);
        SqlBuilder sqlBuilder = new SqlBuilder(table, new Properties(), DbType.mysql);
        System.out.println(sqlBuilder.getCreateTableSql());
    }

    @Test
    public void testOracle() throws IOException, SQLException {


        String sql = "CREATE TABLE \"DRUG_PAIR\" (\n" +
                "\"MEDI_INST_CODE\" VARCHAR ( 16 ),\n" +
                "\"RECORD_NO\" VARCHAR ( 32 ),\n" +
                "\"DRUG_CATE_CODE\" VARCHAR ( 32 ),\n" +
                "\"DRUG_CODE\" VARCHAR ( 32 ),\n" +
                "\"DRUG_NAME\" VARCHAR ( 64 ),\n" +
                "\"DRUG_MEDI_CODE\" VARCHAR ( 32 ),\n" +
                "\"DRUG_MEDI_NAME\" VARCHAR ( 32 ),\n" +
                "\"JOIN_INSU_CODE\" VARCHAR ( 32 ),\n" +
                "\"INSERT_TIME\" DATE,\n" +
                "\"UPDATE_TIME\" DATE,\n" +
                "\"PKID\" VARCHAR ( 128 ),\n" +
                "\"STANDARD_VERSION\" INT \n" +
                ")";
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));

        SqlSession sqlSession = sqlSessionFactory.openSession();

        sqlSession.getConnection().prepareStatement(sql).execute();
        sqlSession.close();
    }
}