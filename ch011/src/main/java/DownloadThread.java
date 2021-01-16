import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

/**
 * @author jayying
 * @date 2021/1/13
 */
public class DownloadThread extends Thread {
    private final int startPos;
    private final int currentPartSize;
    private final RandomAccessFile currentPart;
    private final String path;
    //已下载大小
    public int length = 0;

    public DownloadThread(int startPos, int currentPartSize, RandomAccessFile currentPart, String path) {
        this.startPos = startPos;
        this.currentPartSize = currentPartSize;
        this.currentPart = currentPart;
        this.path = path;
    }

    @Override
    public void run() {
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(1000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "image/gif, image/jpeg, image/pjpeg, image/pjpge,"
                    + "application/x-shockwave-flash, application/xaml+xml" +
                    "application/vnd.ms-xpsdocument, application/x-ms-xbap" +
                    "application/x-ms-application, application/vnd.ms-excel" +
                    "application/vnd.ms-powerpoint, application/msword, */*");
            conn.setRequestProperty("Charset", "utf-8");
            //
            InputStream inputStream = conn.getInputStream();
            inputStream.skip(this.startPos + length);
            //获取输入输出的两条channel
            ReadableByteChannel inputChannel = Channels.newChannel(inputStream);
            FileChannel outputChannel = currentPart.getChannel();
            //缓存
            ByteBuffer byteBuffer = ByteBuffer.allocate(2048);
            int hasRead;
            while (length < currentPartSize && (hasRead = inputChannel.read(byteBuffer)) != -1) {
                byteBuffer.flip();
                outputChannel.write(byteBuffer);
                length += hasRead;
                byteBuffer.clear();
            }
            //关闭流
            currentPart.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
