package version1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author jane
 * @date  2020/4/15
 * @description
 */
public class Server {
    private ServerSocket serverSocket;
    private BufferedInputStream bis;
    private BufferedOutputStream bos;

    public Server() {
        try {
            serverSocket = new ServerSocket(6666);
            while (true) {
                Socket socket = serverSocket.accept();//调用该方法后进程会阻塞，直到有主机连接
                System.out.println("收到新的连接");

                bis = new BufferedInputStream(socket.getInputStream());
                bos = new BufferedOutputStream(socket.getOutputStream());

                int[] arr = recv();
                int sum = 0;
                String s = "";
                for(int val : arr) {
                    sum += val;
                    s = s + val + " ";
                }
                System.out.println("收到数据:" + s);
                System.out.println("计算结果:" + sum);
                send(sum);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(int sum) {
        try {
            bos.write(sum);
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[] recv() {
        try {
            int n = bis.read();
            //客户端异常掉了
            if(n == -1) {
                return null;
            }
            int[] array = new int[n];
            byte[] bytes = new byte[4];
            for(int i = 0; i < n; i++) {
                bis.read(bytes);
                for(int j = 0; j < 4; j++) {
                    array[i] = (array[i] << 4) + bytes[j];
                }
            }
            return array;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        Server server = new Server();
        System.out.println("构造方法死循环时，会执行这条语句？");
    }
}
