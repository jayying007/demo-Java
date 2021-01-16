package version5;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

class CalculateHandler implements Runnable {
    final SocketChannel channel;
    final SelectionKey sk;
    final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    CalculateHandler(Selector selector, SocketChannel c) throws IOException {
        channel = c;
        c.configureBlocking(false);
        //仅仅取得选择键，后设置感兴趣的IO事件
        sk = channel.register(selector, 0);

        //将Handler作为选择键的附件
        sk.attach(this);

        //第二步,注册Read就绪事件
        sk.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    public void run() {
        if(sk.isReadable()) {
            try {
                channel.read(byteBuffer);
                //准备开始读取
                byteBuffer.flip();
                if(!byteBuffer.hasRemaining()) {
                    return;
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
                channel.write(byteBuffer);
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

