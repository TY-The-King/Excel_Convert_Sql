package core;

/**
 * @author dxw
 * @ClassName Column.java
 * @Description 列
 * @createTime 2019-06-05 12:07
 */
public class Column {

    /**
     * 列名
     */
    private String columnName;

    /**
     * 注释
     */
    private String columnComment;

    /**
     * 字段类型
     */
    private String columnType;

    /**
     * 字段长度
     */
    private String columnLength;

    private String columnNull;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnComment() {
        return columnComment;
    }

    public void setColumnComment(String columnComment) {
        this.columnComment = columnComment;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnLength() {
        return columnLength;
    }

    public void setColumnLength(String columnLength) {
        this.columnLength = columnLength;
    }

    public String getColumnNull() {
        return columnNull;
    }

    public void setColumnNull(String columnNull) {
        this.columnNull = columnNull;
    }

    @Override
    public String toString() {
        return "Column{" +
                "columnName='" + columnName + '\'' +
                ", columnComment='" + columnComment + '\'' +
                ", columnType='" + columnType + '\'' +
                ", columnLength='" + columnLength + '\'' +
                ", columnNull='" + columnNull + '\'' +
                '}';
    }
}
