package util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

/**
 * @author TheKing
 * @createTime 2021/10/11 15:29
 */
public class WriteTxtTool {

    /**
     * 将生成的Oracle建表语句输出到文件中
     *
     * @param list SQL语句集合
     * @param path 文件路径
     * @author TheKing
     * @createTime 2021/10/11 17:31
     */
    public void writeFile(List<String> list, String path) throws Exception {
        File file = new File(path);
        if (file.createNewFile()) {
            System.out.println("重新创建文件");
        }

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path, true));
        for (String text : list) {
            bufferedWriter.write(text + "\r\n");
        }

        //建表sql增加换行
        bufferedWriter.write("\r\n");
        bufferedWriter.close();
    }

}
