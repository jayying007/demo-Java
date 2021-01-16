import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class DownloadUtil {
    //下载地址
    private final String path;
    //保存路径
    private final String targetFile;
    //多线程下载
    private final int threadNum;
    private final DownloadThread[] threads;
    //文件总大小
    private int fileSize;
    //累计已下载大小
    private int lastSize;

    public DownloadUtil(String path, String targetFile, int threadNum) {
        this.path = path;
        this.targetFile = targetFile;
        this.threadNum = threadNum;
        threads = new DownloadThread[threadNum];
    }

    public void download() throws IOException, InterruptedException {
        connAndSetFileSize();

        //计算每个线程需要下载的大小
        int currentPartSize = fileSize / threadNum + 1;
        for(int i = 0; i < threadNum; i++) {
            //每个线程开始的位置
            int startPos = i * currentPartSize;
            //
            RandomAccessFile currentPart = new RandomAccessFile(targetFile, "rw");
            //移动文件指针
            currentPart.seek(startPos);
            //
            threads[i] = new DownloadThread(startPos, currentPartSize, currentPart, path);
            threads[i].setName("DownloadThread-" + i);
            threads[i].start();
        }
        //打印下载速度
        while (lastSize < fileSize) {
            showDownloadRate();
            //休眠1s，便于计算每秒下载速度
            TimeUnit.SECONDS.sleep(1);
        }
    }

    /**
     * 请求资源，创建相应空间的文件大小
     * @throws IOException IO异常
     */
    public void connAndSetFileSize() throws IOException {
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(1000);
        conn.setRequestMethod("GET");

        conn.setRequestProperty("Charset", "utf-8");
        conn.setRequestProperty("Connection", "Keep-Alive");
        //
        fileSize = conn.getContentLength();
        if(fileSize == -1) {
            System.out.println("不支持的资源下载类型");
            return;
        }
        conn.disconnect();
        RandomAccessFile file = new RandomAccessFile(targetFile, "rw");
        //先创建对应大小的文件
        file.setLength(fileSize);
        file.close();
    }

    public void showDownloadRate() {
        //统计所有线程下载大小
        int size = 0;
        for(int i = 0; i < threadNum; i++) {
            size += threads[i].length;
        }
        //控制台实时输出进度
        System.out.printf("下载进度：%.3f%%   速度：%.1f KB/S\r", size * 100.0 / fileSize, (size - lastSize) / 1024.0);
        lastSize = size;
    }

}
