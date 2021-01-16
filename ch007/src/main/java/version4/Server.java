package version4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author jane
 * @date  2020/4/15
 * @description
 */
public class Server {
    private ServerSocket serverSocket;
    private ThreadPoolExecutor threadPool = new ThreadPoolExecutor(50,
            200, 30,
            TimeUnit.SECONDS, new ArrayBlockingQueue<>(1000));

    public Server() {
        try {
            serverSocket = new ServerSocket(6666);
            while (true) {
                Socket socket = serverSocket.accept();//调用该方法后进程会阻塞，直到有主机连接
                System.out.println("收到新的连接");

                threadPool.execute(new ServerThread(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        System.out.println("构造方法死循环时，会执行这条语句？");
    }
}
