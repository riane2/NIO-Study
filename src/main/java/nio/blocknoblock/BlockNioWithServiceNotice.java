package nio.blocknoblock;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 客户端发送数据，服务器接受数据之后给个通知到客户端
 */
public class BlockNioWithServiceNotice {


    @Test
    public void client() throws Exception {
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 8888));


        FileChannel fileChannel = FileChannel.open(Paths.get("/Users/lixiongxiong-riane/Documents/Code/Git/NIO-Study/src/main/resources/1.txt"), StandardOpenOption.READ);

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (fileChannel.read(buffer) != -1) {
            buffer.flip();
            socketChannel.write(buffer);
            buffer.clear();
        }

        /**
         * 服务端不知道客户端是不是发送完数据，所以服务器处于阻塞状态
         * 因为客户端必须明确告诉服务端"我发送完数据了"。
         */
        socketChannel.shutdownOutput();
        /**
         * 接受客户端的回执信息
         */
        int len;
        while ((len = socketChannel.read(buffer)) != -1) {
            buffer.flip();
            System.out.println(new String(buffer.array(), 0, len));
            buffer.clear();
        }

        fileChannel.close();
        socketChannel.close();
    }

    @Test
    public void service() throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8888));

        SocketChannel socketChannel = serverSocketChannel.accept();
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (socketChannel.read(buffer) != -1) {
            buffer.flip();
            System.out.println(new String(buffer.array(), 0, buffer.limit()));
            buffer.clear();
        }

        buffer.put("收到客户端发过来的消息".getBytes());

        buffer.flip();
        socketChannel.write(buffer);

        socketChannel.close();
        serverSocketChannel.close();


    }


}
