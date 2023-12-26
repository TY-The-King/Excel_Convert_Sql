package util;

import java.util.List;

/**数据库字段枚举类*/
public enum FieldType {

    //Oracle数据库字段类型
    NUMBER(1,"Number"),
    LONG(2,"LONG"),
    INT(3,"INT"),
    BINARY_FLOAT(4,"BINARY_FLOAT"),
    BINARY_DOUBLE(5,"BINARY_DOUBLE"),
    TIMESTAMP(6,"TIMESTAMP"),
    INTERVAL(7,"INTERVAL"),
    YEAR(8,"YEAR"),
    INTERVAL_DAY(9,"INTERVAL DAY"),
    LONG_RAW(10,"LONG RAW"),
    CHAR(11,"CHAR"),
    NCHAR(12,"NCHAR"),
    DATE(13,"DATE"),
    CLOB(14,"CLOB"),
    BFILE(15,"BFILE"),
    FLOAT(16,"FLOAT");


    //自定义编码
    private final int code;

    //编码对应字段类型名称
    private final String fieldTypeName;


    FieldType(int code,String fieldTypeName){
        this.code = code;
        this.fieldTypeName = fieldTypeName;

    }

    public int getCode() {
        return code;
    }

    public String getFieldTypeName(int code) {
        return fieldTypeName;
    }
}
