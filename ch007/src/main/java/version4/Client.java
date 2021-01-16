package version4;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author jane
 * @date  2020/4/15
 * @description
 */
public class Client {
    private Socket socket;
    private BufferedInputStream bis;
    private BufferedOutputStream bos;

    public Client() {
        try {
            socket = new Socket("127.0.0.1", 6666);
            bis = new BufferedInputStream(socket.getInputStream());
            bos = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("连接Socket失败!");
            System.exit(0);
        }
    }

    public void send() {
        System.out.println("输入整数个数，然后逐个输入");
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int[] array = new int[n];
        for(int i = 0; i < n; i++) {
            array[i] = sc.nextInt();
        }
        //协议格式：整数个数(1 Byte) + 整数1(4 Bytes) + 整数2(4 Bytes) + ...
        byte[] bytes = new byte[1 + 4 * n];
        int offset = 0;
        bytes[offset++] = (byte)n;
        for(int i = 0; i < n; i++) {
            bytes[offset++] = (byte) (255 & (array[i] >>> 24));
            bytes[offset++] = (byte) (255 & (array[i] >>> 16));
            bytes[offset++] = (byte) (255 & (array[i] >>> 8));
            bytes[offset++] = (byte) (255 & array[i]);
        }

        try {
            bos.write(bytes);
            //调用flush或close都可让服务端接收数据,到达read()=-1的地方
            bos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void recv() {
        try {
            int ans = bis.read();
            System.out.println("结果：" + ans);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        //同一个socket，复用5次
        for(int i = 0; i < 5; i++) {
            client.send();
            client.recv();
        }
    }
}
