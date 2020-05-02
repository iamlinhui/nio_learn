package cn.promptness.io;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

public class WordTest {

    public List<String> readWord(File file) throws IOException {

        FileInputStream fileInputStream = new FileInputStream(file);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        LinkedList<String> lineList = new LinkedList<>();

        String line;
        while ((line = bufferedReader.readLine()) != null) {
            lineList.add(line);
        }

        bufferedReader.close();
        inputStreamReader.close();
        fileInputStream.close();

        return lineList;
    }


    public void outWord(File file, List<String> lineList) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

        for (String line : lineList) {
            bufferedWriter.write(line);
            //换行
            bufferedWriter.newLine();
        }
        //强制输出缓冲区的内容
        bufferedWriter.flush();

        bufferedWriter.close();
        outputStreamWriter.close();
        fileOutputStream.close();
    }

}
