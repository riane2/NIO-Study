package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;

/**
 * NIO客户端
 */
public class NioClient {

    private Selector selector;


    public static void main(String[] args) {
        NioClient client = new NioClient();
        try {
            client.init();
            client.working();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void init() throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);

        selector = SelectorProvider.provider().openSelector();

        //由于是非阻塞的，此方法返回时，连接不一定已经建立完成，
        //下次使用时，需要通过channel.finishConnect()确认是否连接成功
        channel.connect(new InetSocketAddress(8000));
        channel.register(selector, SelectionKey.OP_CONNECT);
    }

    private void working() throws IOException {
        while (true) {
            if (!selector.isOpen()) {
                break;
            }
            selector.select(); //得到已经准备好的事件，如果没有任何事件准备就绪，就阻塞
            Iterator<SelectionKey> ite = selector.selectedKeys().iterator();
            while (ite.hasNext()) {
                SelectionKey key = ite.next();
                ite.remove();
                if (key.isConnectable()) {
                    connect(key);
                } else if (key.isReadable()) {
                    read(key);
                }
            }
        }
    }


    private void connect(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        //如果正在连接，则完成连接操作
        if (channel.isConnectionPending()) {
            channel.finishConnect();
        }
        channel.configureBlocking(false);

        channel.write(ByteBuffer.wrap(new String("Hello world").getBytes()));
        channel.register(selector, SelectionKey.OP_READ);

    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        channel.read(byteBuffer);
        byteBuffer.flip();
        String msg = new String(byteBuffer.array()).trim();
        System.out.println("客户端收到消息：" + msg);
        //channel.close();
        //key.selector().close();
    }
}
