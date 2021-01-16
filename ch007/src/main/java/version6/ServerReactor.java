package version6;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;


class ServerReactor {
    ServerSocketChannel serverSocket;
    AtomicInteger next = new AtomicInteger(0);
    //selectors集合,引入多个selector选择器
    Selector[] selectors = new Selector[2];
    //引入多个子反应器
    SubReactor[] subReactors;


    ServerReactor() throws IOException {
        //初始化多个selector选择器
        selectors[0] = Selector.open();
        selectors[1] = Selector.open();
        serverSocket = ServerSocketChannel.open();

        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 6666);
        serverSocket.socket().bind(address);
        //非阻塞
        serverSocket.configureBlocking(false);

        //第一个selector,负责监控新连接事件
        SelectionKey sk = serverSocket.register(selectors[0], SelectionKey.OP_ACCEPT);
        //附加新连接处理handler处理器到SelectionKey（选择键）
        sk.attach(new AcceptorHandler());

        //第一个子反应器，一子反应器负责一个选择器
        SubReactor subReactor1 = new SubReactor(selectors[0], 0);
        //第二个子反应器，一子反应器负责一个选择器
        SubReactor subReactor2 = new SubReactor(selectors[1], 1);
        subReactors = new SubReactor[]{subReactor1, subReactor2};
    }

    private void startService() {
        // 一子反应器对应一条线程
        new Thread(subReactors[0]).start();
        new Thread(subReactors[1]).start();
    }

    //反应器
    class SubReactor implements Runnable {
        //每条线程负责一个选择器的查询
        final Selector selector;
        final int number;

        public SubReactor(Selector selector, int number) {
            this.selector = selector;
            this.number = number;
        }

        public void run() {
            try {
                while (!Thread.interrupted()) {
                    System.out.println("Selector" + number);
                    //Note: 如果不设置事件，会造成selector睡眠，导致后续无法建立新的连接
                    selector.select(10000);
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        //Reactor负责dispatch收到的事件
                        System.out.println("-------");
                        dispatch(iterator.next());
                        iterator.remove();
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }


        void dispatch(SelectionKey sk) {
            Runnable handler = (Runnable) sk.attachment();
            //调用之前attach绑定到选择键的handler处理器对象
            if (handler != null) {
                handler.run();
            }
        }
    }


    // Handler:新连接处理器
    class AcceptorHandler implements Runnable {
        public void run() {
            try {
                SocketChannel channel = serverSocket.accept();
                if (channel != null) {
                    System.out.println("new connection to" + next.get());
                    new CalculateHandler(selectors[next.get()], channel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (next.incrementAndGet() == selectors.length) {
                next.set(0);
            }
        }
    }


    public static void main(String[] args) throws IOException {
        ServerReactor server = new ServerReactor();
        server.startService();
    }
}
