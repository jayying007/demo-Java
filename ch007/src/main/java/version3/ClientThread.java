package version3;

import version3.Client;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @author jayying
 * @date 2021/1/12
 */
public class ClientThread extends Thread {
    private final Client client;

    public ClientThread(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        System.out.println("输入整数个数，然后逐个输入");
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int[] array = new int[n];
        for(int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
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

        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        SocketChannel socketChannel = client.getSocketChannel();
        try {
            socketChannel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
