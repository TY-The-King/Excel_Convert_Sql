package core;
/**
 * @author cjs
 */
public enum DbType {

    //mysql配置
    mysql("com.mysql.cj.jdbc.Driver"),
    //oracle配置
    oracle("oracle.jdbc.OracleDriver");

    private String driver;

    DbType(String driver) {
        this.driver = driver;
    }

    public static DbType getByDriverClass(String driverClass){
        for (DbType dbType : DbType.values()) {
            if(dbType.driver.equals(driverClass)){
                return dbType;
            }
        }
        return null;
    }

}
