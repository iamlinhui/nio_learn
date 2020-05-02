package cn.promptness.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class FileTest {


    public void outFile(FileInputStream fileInputStream, FileOutputStream fileOutputStream) throws IOException {
        FileChannel fileInputStreamChannel = fileInputStream.getChannel();
        FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true) {
            //将postion置0，limit变成capacity，迎接下一次读取
            buffer.clear();
            //将字节读到buffer里，返回所读字节，如果为-1，则表示读到文件的结尾了
            int read = fileInputStreamChannel.read(buffer);
            if (-1 == read) {
                //如果read为-1，跳出循环
                break;
            }
            //反转，将buffer写出到通道中，持久化到硬盘中
            buffer.flip();
            fileOutputStreamChannel.write(buffer);
        }
    }

    public String readWord(File file) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        FileInputStream fileInputStream = new FileInputStream(file);

        FileChannel channel = fileInputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);


        int length = -1;
        while ((length = channel.read(byteBuffer)) != -1) {

            //读取后，将位置置为0，将limit置为容量, 以备下次读入到字节缓冲中，从0开始存储

            byteBuffer.clear();
            byte[] bytes = byteBuffer.array();

            String str = new String(bytes, 0, length);
            stringBuilder.append(str);
        }

        fileInputStream.close();

        return stringBuilder.toString();
    }

    public void writeWord(String wordStr, File file) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        FileChannel channel = fileOutputStream.getChannel();
        ByteBuffer src = StandardCharsets.UTF_8.encode(wordStr);
        int length = 0;
        while ((length = channel.write(src)) != 0) {
            // 注意，这里不需要clear，将缓冲中的数据写入到通道中后 第二次接着上一次的顺序往下读
        }
        fileOutputStream.close();
    }


    public static void testReadAndWriteNIO(File inFile, File outFile) throws IOException {


        FileInputStream fileInputStream = new FileInputStream(inFile);
        FileChannel fileInputStreamChannel = fileInputStream.getChannel();

        ByteBuffer bf = ByteBuffer.allocate(1024);
        int intLength = -1;

        FileOutputStream fileOutputStream = new FileOutputStream(outFile);
        FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();


        while ((intLength = fileInputStreamChannel.read(bf)) != -1) {

            //将当前位置置为limit，然后设置当前位置为0，也就是从0到limit这块，都写入到同道中
            bf.flip();

            int outLength = 0;
            while ((outLength = fileOutputStreamChannel.write(bf)) != 0) {
            }
            //将当前位置置为0，然后设置limit为容量，也就是从0到limit（容量）这块，
            //都可以利用，通道读取的数据存储到
            //0到limit这块
            bf.clear();

        }

    }
}
