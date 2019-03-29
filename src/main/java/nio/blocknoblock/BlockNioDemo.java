package nio.blocknoblock;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 一、使用NIO完成网络通信的三个核心：
 * <p>
 * 1、通道：负责连接即数据传送
 * {@linkplain Channel}
 * |-- {@linkplain SelectableChannel}
 * |------ {@linkplain SocketChannel}
 * |------ {@linkplain ServerSocketChannel}
 * |------ {@linkplain DatagramChannel}
 * <p>
 * |------ {@linkplain Pipe.SourceChannel}
 * |------ {@linkplain Pipe.SinkChannel}
 * <p>
 * 2、缓冲区：负责数据的存取
 * <p>
 * 3、选择器：是SelectableChannel的多路复用器，用于监控SelectableChannel的IO状况
 */
//@SuppressWarnings("all")
public class BlockNioDemo {

    /**
     * 客户端
     */
    @Test
    public void client() throws Exception {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 8088));

        //读取本地文件发送服务端
        FileChannel fileChannel = FileChannel.open(Paths.get("E:\\StudyCodes\\Java\\nio\\src\\main\\resources\\1.txt"), StandardOpenOption.READ);
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (fileChannel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            byteBuffer.clear();
        }

        fileChannel.close();
        socketChannel.close();

    }

    /**
     * 服务端
     */
    @Test
    public void server() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //绑定连接
        //TODO 这是最新的绑定方式
        serverSocketChannel.socket().bind(new InetSocketAddress(8088));
        //TODO 这是1.7之前的绑定方式
        //serverSocketChannel.bind(new InetSocketAddress(8088));
        //获取客户端的连接的通道
        SocketChannel socketChannel = serverSocketChannel.accept();

        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byte[] bytes = new byte[1024];
        while (socketChannel.read(byteBuffer) != -1) {
            byteBuffer.flip();
            bytes = new byte[byteBuffer.limit()];
            byteBuffer.get(bytes);
            System.out.println(new String(bytes));
            byteBuffer.clear();
        }
        socketChannel.close();
        serverSocketChannel.close();
    }
}
