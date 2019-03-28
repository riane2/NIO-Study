package nio.channel;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 1、Channel：通道，用于源节点和目标节点的连接。在Java Nio中负责缓冲区数据的传输，Channel本身不存储数据，因此要配合buffer使用
 * 2、主要实现类：
 * {@linkplain Channel}接口
 * |--{@linkplain FileChannel} 完成本地文件传输的
 * |--{@linkplain java.nio.channels.SocketChannel} 网络IO->TCP IO
 * |--{@linkplain java.nio.channels.ServerSocketChannel} TCP->IO
 * |--{@linkplain java.nio.channels.DatagramChannel} UDP IO
 * <p>
 * <p>
 * 3、获取通道：
 * 3.1 Java针对支持通道的类提供了getChannel()方法
 * 3.1.1 本地IO操作：
 * FileInputStream/FileOutputStream
 * RandomAccessFile
 * 3.1.2 网络操作
 * Socket
 * ServerSocket
 * DatagramSocket
 * 3.2 在JDK1.7中的NIO2（jdk1.7之后的NIO叫IO2）针对各个通道提供了静态方法open()
 * 3.3 在JDK1.7中的NIO2（jdk1.7之后的NIO叫IO2）Files工具类的newByteChannel()
 * <p>
 * 4、通道之间直接的通信s
 * transferTo()
 * transferFrom()
 */
public class ChannelDemo {

    /**
     * 利用通道完成数据的复制;(非直接缓冲区)
     * 步骤：1、文件输入流和文件输出流
     * 2、根据流获取相应的通道
     * 3、创建缓冲区Buffer进行数据承载
     * 4、从输入通道拿到数据写入输出通道
     * 5、关闭流和通道
     */
    @Test
    public void test1() throws Exception {
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputStream = new FileInputStream("E:\\StudyCodes\\Java\\nio\\src\\main\\resources\\1.txt");
            outputStream = new FileOutputStream("E:\\StudyCodes\\Java\\nio\\src\\main\\resources\\1-1.txt");
            inputChannel = inputStream.getChannel();
            outputChannel = outputStream.getChannel();

            ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
            while (inputChannel.read(buffer) != -1) {
                buffer.flip();
                outputChannel.write(buffer);
                buffer.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputChannel != null) {
                inputChannel.close();
            }
            if (outputChannel != null) {
                outputChannel.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }

    }

    /**
     * 使用直接直接缓冲区测试文件复制（内存映射文件）
     */
    @Test
    public void testDirect() throws Exception {

        FileChannel inChannel = FileChannel.open(Paths.get("E:\\StudyCodes\\Java\\nio\\src\\main\\resources\\1.txt"), StandardOpenOption.READ);
        /**
         * StandardOpenOption.CREATE：不管文件存不存在，它都会创建
         * StandardOpenOption.CREATE_NEW: 如果给定位置的给定文件已经存在，就会报错
         */
        FileChannel outChannel = FileChannel.open(Paths.get("E:\\StudyCodes\\Java\\nio\\src\\main\\resources\\1-2.txt"),
                StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

        //内存映射文件
        //map()方法：将通道中的数据写入一个缓冲区，第一个参数表示模式，第二个参数表示开始位置，第三个位置表示读取的大小
        MappedByteBuffer inBuffer = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());

        //读写模式的内存映射文件->所以上面的outChannel也必须具有读写模式，否则会出现异常
        MappedByteBuffer outBuffer = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());
        //相较于之前的好处：原来要基于通道进行读写操作，现在直接在内存中
        outBuffer.put(inBuffer);

        inChannel.close();
        outChannel.close();
    }


    @Test
    public void testTransfer() throws Exception {
        FileChannel inChannel = FileChannel.open(Paths.get("E:\\StudyCodes\\Java\\nio\\src\\main\\resources\\1.txt"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("E:\\StudyCodes\\Java\\nio\\src\\main\\resources\\1-3.txt"),
                StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

        /**
         * 两个通道之间直接进行数据传输
         */
        inChannel.transferTo(0, inChannel.size(), outChannel);

        inChannel.close();
        outChannel.close();

    }

}
