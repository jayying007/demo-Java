package version6;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class CalculateHandler implements Runnable {
    final SocketChannel channel;
    final SelectionKey sk;
    final ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

    //引入线程池
    static ExecutorService pool = Executors.newFixedThreadPool(4);

    CalculateHandler(Selector selector, SocketChannel c) throws IOException {
        channel = c;
        c.configureBlocking(false);
        //仅仅取得选择键，后设置感兴趣的IO事件
        sk = channel.register(selector, 0);
        //将本Handler作为sk选择键的附件，方便事件dispatch
        sk.attach(this);
        //向sk选择键注册Read就绪事件
        sk.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    public void run() {
        System.out.println("*");
        //异步任务，在独立的线程池中执行
        pool.execute(new AsyncTask());
    }

    //异步任务，不在Reactor线程中执行
    public synchronized void asyncRun() {
        //TODO 异步线程执行，导致还没执行时，selector还是能发现这个key有事件，然后反复处理
        if(!sk.isValid()) return;
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

    //异步任务的内部类
    class AsyncTask implements Runnable {
        public void run() {
            CalculateHandler.this.asyncRun();
        }
    }
}