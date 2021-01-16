package version3;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author jayying
 * @date 2021/1/12
 */
public class Server {

    public void init() throws IOException {

        Selector selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        //与端口绑定
        serverSocketChannel.bind(inetSocketAddress);
        //设置为非阻塞模式
        serverSocketChannel.configureBlocking(false);
        //向selector注册，告诉它自己对accept事件感兴趣
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        System.out.println("服务器已启动，正在监听6666端口.");

        //select()会阻塞直到有事件发生才返回时
        while (selector.select() > 0) {
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                //有新的连接
                if (key.isAcceptable()) {
                    System.out.println("收到新的连接");
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }
                if(key.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel)key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(256);
                    socketChannel.read(byteBuffer);
                    //准备开始读取
                    byteBuffer.flip();
                    if(!byteBuffer.hasRemaining()) {
                        iterator.remove();
                        continue;
                    }
                    int n = byteBuffer.get();
                    int sum = 0;
                    StringBuilder s = new StringBuilder();
                    for(int i = 0; i < n; i++) {
                        int t = byteBuffer.getInt();
                        sum += t;
                        s.append(t).append(" ");
                    }
                    System.out.println("收到数据:" + s);
                    System.out.println("计算结果:" + sum);
                    byteBuffer.clear();
                    byteBuffer.putInt(sum);
                    byteBuffer.flip();
                    socketChannel.write(byteBuffer);
                    socketChannel.close();
                }
                iterator.remove();
            }
        }
    }
    public static void main(String[] args) throws IOException {
        new Server().init();
    }
}
