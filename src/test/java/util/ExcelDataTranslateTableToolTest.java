package util;

import junit.framework.TestCase;
import org.apache.ibatis.io.Resources;
import org.junit.Test;

import java.io.File;

public class ExcelDataTranslateTableToolTest extends TestCase {

    @Test
    public void testGetTable() throws Exception {
        File file = Resources.getResourceAsFile("table_template.xlsx").getAbsoluteFile();
        System.out.println(ExcelDataTranslateTableTool.getTable(ReadExcelTool.readExcel(file)));
    }
}