package util;

import junit.framework.TestCase;
import org.apache.ibatis.io.Resources;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class ReadExcelToolTest extends TestCase {

    @Test
    public void testReadExcel() throws IOException {
        File tableTemplate = Resources.getResourceAsFile("table_template.xlsx").getAbsoluteFile();
        System.out.println(ReadExcelTool.readExcel(tableTemplate));
    }
}