package version4;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author jayying
 * @date 2021/1/12
 */
public class ServerThread extends Thread {
    private BufferedInputStream bis;
    private BufferedOutputStream bos;

    public ServerThread(Socket socket) {
        try {
            bis = new BufferedInputStream(socket.getInputStream());
            bos = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        for(int i = 0; i < 5; i++) {
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
}
