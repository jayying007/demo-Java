import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author jayying
 * @since 2021/1/16
 */
public class FileCopy {
    /**
     * 通过复制文件的例子展示各种用法
     * @param sourcePath 源文件路径
     * @param targetPath 目标文件路径
     * @return 拷贝耗时 ms
     */
    public long copy1(String sourcePath, String targetPath) {
        long startTime = System.currentTimeMillis();

        File sourceFile = new File(sourcePath);
        File targetFile = new File(targetPath);

        try {
            InputStream inputStream = new FileInputStream(sourceFile);
            OutputStream outputStream = new FileOutputStream(targetFile);

            byte[] bytes = new byte[512];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            //刷盘
            //outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
    public long copy2(String sourcePath, String targetPath) {
        long startTime = System.currentTimeMillis();

        File sourceFile = new File(sourcePath);
        File targetFile = new File(targetPath);

        try {
            FileChannel inChannel = new FileInputStream(sourceFile).getChannel();
            FileChannel outChannel = new FileOutputStream(targetFile).getChannel();

            ByteBuffer byteBuffer = ByteBuffer.allocate(512);
            while (inChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                //是否必要？
                while (outChannel.write(byteBuffer) != 0) ;
                byteBuffer.clear();
            }
            //强制刷盘
            //outChannel.force(true);

            inChannel.close();
            outChannel.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }
    public long copy3(String sourcePath, String targetPath) {
        long startTime = System.currentTimeMillis();

        File sourceFile = new File(sourcePath);
        File targetFile = new File(targetPath);

        try {
            FileChannel inChannel = new FileInputStream(sourceFile).getChannel();
            FileChannel outChannel = new FileOutputStream(targetFile).getChannel();

            long size = inChannel.size();
            long pos = 0;
            long count;
            while (pos < size) {
                count = pos + 512 > size ? size - pos : 512;
                pos += outChannel.transferFrom(inChannel, pos, count);
            }
            //outChannel.force(true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

    public static void main(String[] args) {
        FileCopy copy = new FileCopy();
        //看起来，不过有没有开启刷盘，或者文件是几M和几百M，速度差不多，甚至原先的还更快！
        System.out.println("method1:" + copy.copy1("ch013/src/main/resources/bg.png",
                "ch013/src/main/resources/bg1.png") + "ms");
        System.out.println("method2:" + copy.copy2("ch013/src/main/resources/bg.png",
                "ch013/src/main/resources/bg2.png") + "ms");
        System.out.println("method3:" + copy.copy2("ch013/src/main/resources/bg.png",
                "ch013/src/main/resources/bg3.png") + "ms");
    }
}
