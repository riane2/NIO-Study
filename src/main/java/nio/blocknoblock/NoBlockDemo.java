package nio.blocknoblock;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.time.LocalDateTime;
import java.util.Iterator;

/**
 * @author lixiongxiong
 * @date 2019/3/28
 * @description no block nio
 */
public class NoBlockDemo {

    @Test
    public void client() throws IOException {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 8888));
        socketChannel.configureBlocking(false);

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put(LocalDateTime.now().toString().getBytes());

        buffer.flip();
        socketChannel.write(buffer);
        buffer.clear();

        socketChannel.close();
    }

    @Test
    public void service() throws IOException {

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.socket().bind(new InetSocketAddress(8888));

        //获取selector的方法
        //1 、Selector.open()
        Selector selector = Selector.open();

        //2、第二种方式：
        Selector selector1 = SelectorProvider.provider().openSelector();

        //将通道注册到选择器上，并注册其感兴趣的事件
        //因为有四种感兴趣的事件，所以服务器必须分别对每一种感兴趣的事件进行不同的逻辑处理
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        /**
         * (selector.select() >0 表示至少有一个准备就绪
         */
        while (selector.select() > 0) {

            //获取选择器上所有的SelectionKey(已经就绪的监听事件)
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            // 轮询式的获取选择器上已经准备就绪的的事件
            while (iterator.hasNext()) {

                //获取选择器上已经准备就绪的的事件
                SelectionKey selectionKey = iterator.next();

                //之后需要判断是什么事件准备就绪了
                /**
                 * 服务器接收到客户端的接受请求后，需要进行的处理。即通道数据准备就绪了，然后才进行操作
                 */
                if (selectionKey.isValid() && selectionKey.isAcceptable()) {
                    //若接收就绪，则获取客户端连接
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    //将通道注册到选择器
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) {
                    //若是读就绪，则获取当前通道
                    SocketChannel sChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    while (sChannel.read(buffer) != -1) {
                        buffer.flip();
                        System.out.println(new String(buffer.array(), 0, buffer.limit()));
                        buffer.clear();
                    }
                }
                /**
                 * 必须取消选择键，否则一直有效，影响下次的结果
                 */
                iterator.remove();
            }
        }
    }

}
