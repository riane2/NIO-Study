package nio;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NioService {

    private Selector selector;
    private ExecutorService executorService = Executors.newCachedThreadPool();
    private static Map<Socket, Long> time_stat = new ConcurrentHashMap<>(10240);


    public static void main(String[] args) {
        NioService nioService = new NioService();
        try {
            nioService.startServer();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void startServer() throws Exception {
        selector = SelectorProvider.provider().openSelector();

        //服务端的SocketChannel实例对象
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        //设置为非阻塞模式
        socketChannel.configureBlocking(false);

        InetSocketAddress isa = new InetSocketAddress(InetAddress.getLocalHost(), 8000);
        InetSocketAddress isa1 = new InetSocketAddress(8000);

        socketChannel.socket().bind(isa);

        //socketChannel绑定到选择器上，并注册它感兴趣的时间为accept
        //此后，selector发现当有新的客户端连接进来时，socketChannel就可以进行处理了
        //返回值表示selector与channel的关系，相当于契约，当有一方关闭时，契约就失效了
        SelectionKey acceptKey = socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //死循环等待分发网络消息
        for (; ; ) {
            //阻塞方法，如果当前没有任何数据准备好，就会等待，一旦有数据可读，它就会返回，返回值是已经准备就绪的SelectionKey的数量
            selector.select();
            //获取所有准备好的SelectionKey
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();

            long e = 0;
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();
                if (selectionKey.isAcceptable()) {
                    doAccept(selectionKey);
                } else if (selectionKey.isValid() && selectionKey.isReadable()) {
                    Socket socket = ((SocketChannel) selectionKey.channel()).socket();
                    if (!time_stat.containsKey(socket)) {
                        time_stat.put(socket, System.currentTimeMillis());
                    }
                    doRead(selectionKey);
                } else if (selectionKey.isValid() && selectionKey.isWritable()) {
                    doWrite(selectionKey);
                    Socket socket = ((SocketChannel) selectionKey.channel()).socket();
                    e = System.currentTimeMillis();
                    long b = time_stat.remove(socket);
                    System.out.println("spend:" + (e - b) + "ms");
                }
            }
        }
    }

    /***
     * 当有一个新的客户端连入时，就产生一个新的channel表示这个连接
     * @param selectionKey
     */
    private void doAccept(SelectionKey selectionKey) {
        ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
        SocketChannel clientChannel;
        try {
            //表示和客户端通信的通道
            clientChannel = server.accept();
            clientChannel.configureBlocking(false);
            //注册这个通道可读，即只对读感兴趣，此后当selector发现这个channel已经住备好时，就给线程一个通知
            SelectionKey clientKey = clientChannel.register(selector, SelectionKey.OP_READ);
            EchoClient echoClient = new EchoClient();
            //我们将这个客户端实例作为附件附加到这个连接的SelectionKey上。这样在整个连接的处理的过程中能共享这个实例对象
            clientKey.attach(echoClient);

            InetAddress clientAddress = clientChannel.socket().getInetAddress();
            System.out.println("Accept connection from " + clientAddress.getHostAddress() + ".");

        } catch (Exception ex) {
            System.out.println("Failed to accept new client");
            ex.printStackTrace();
        }
    }

    private void doRead(SelectionKey selectionKey) {
        System.out.println("doReading...");
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(8192); //8K的缓存区读取数据
        int len;
        try {
            len = socketChannel.read(byteBuffer);
            if (len < 0) {
                disconnect(selectionKey);
                return;
            }
        } catch (Exception ex) {
            System.out.println("Failed to read from client");
            ex.printStackTrace();
            disconnect(selectionKey);
            return;
        }
        byteBuffer.flip(); //重置缓冲区
        executorService.execute(new HandleMsg(selectionKey, byteBuffer));
    }

    private void doWrite(SelectionKey selectionKey) {
        System.out.println("doWriting...");
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        EchoClient echoClient = (EchoClient) selectionKey.attachment();
        LinkedList<ByteBuffer> outq = echoClient.getOutq();
        //获取列表顶端元素，准备写回客户端
        ByteBuffer bb = outq.getLast();
        try {
            //写回客户端
            int len = socketChannel.write(bb);
            if (len == -1) {
                disconnect(selectionKey);
                return;
            }
            //写完后删除此元素
            if (bb.remaining() == 0) {
                outq.removeLast();
            }
        } catch (Exception ex) {
            System.err.println("failed to write client");
            disconnect(selectionKey);
            ex.printStackTrace();
        }
        //在将全部数据发送完成之后，需要将写事件从感兴趣的操作中移除，如果不这么做，再次channel准备好写时，
        //都回来执行doWirte()方法。而实际上你又无数据可写。
        if (outq.size() == 0) {
            selectionKey.interestOps(SelectionKey.OP_READ);
        }

    }

    private void disconnect(SelectionKey selectionKey) {
        selectionKey.cancel();
    }


    public class HandleMsg implements Runnable {

        SelectionKey selectionKey;
        ByteBuffer byteBuffer;

        HandleMsg(SelectionKey selectionKey,
                  ByteBuffer byteBuffer) {
            this.selectionKey = selectionKey;
            this.byteBuffer = byteBuffer;
        }


        @Override
        public void run() {
            EchoClient echoClient = (EchoClient) selectionKey.attachment();
            echoClient.enqueue(byteBuffer);
            //重新注册感兴趣的事件
            selectionKey.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
            //强制selector立即返回
            selector.wakeup();
        }
    }
}
