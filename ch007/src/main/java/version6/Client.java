package version6;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * @author jayying
 * @date 2021/1/12
 */
public class Client {
    private SocketChannel socketChannel;

    public void init() throws IOException {
        Selector selector = Selector.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        socketChannel = SocketChannel.open(inetSocketAddress);
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);

        //只进行一次读写请求
        selector.select();
        Set<SelectionKey> keys = selector.selectedKeys();
        for(SelectionKey key : keys) {
            if(key.isReadable()) {
                SocketChannel sc = (SocketChannel)key.channel();

                ByteBuffer byteBuffer = ByteBuffer.allocate(256);
                sc.read(byteBuffer);
                byteBuffer.flip();

                int ans = byteBuffer.getInt();
                System.out.println("计算结果：" + ans);

                sc.close();
            }
        }
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        new ClientThread(client).start();
        client.init();
    }
}
