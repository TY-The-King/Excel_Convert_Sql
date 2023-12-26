package component;

import java.util.List;

public interface SqlBuilderExecutor {

    /**
     * 获得建表语句,由于oracle表的注释不能写在建表语句内,故会形成多条sql
     *
     * @return
     */
    List<String> getCreateTableSql();

    /**
     * 检测字段是否需要填写长度
     * @author TheKing
     * @date 2021/11/3 16:59
     * @param  type-字段类型
     * @return boolean
     */
    boolean checkUnSetLengthType(String type);

    /**
     * 优化checkUnSetLengthType方法
     * @author TheKing
     * @date 2021/11/4 17:35
     * @param type
     * @return boolean
     */
    boolean betterCheckUnSetLengthType(String type);
}
