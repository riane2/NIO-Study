package nio.channel;

import org.junit.Test;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 分散(Scatter)和聚集(Gather)；
 * 分散读取(Scattering Reads)：将通道中的数据分散到多个缓冲区(依次按顺序填满各个缓冲区)
 * 聚集写入(Gathering Writes)：将多个缓冲区中的数据聚集到通道中(依次顺序写入)
 */
public class GatherAndScatterDemo {


    @Test
    public void test() throws Exception {
        //读写模式
        RandomAccessFile randomAccessFile = new RandomAccessFile("E:\\StudyCodes\\Java\\nio\\src\\main\\resources\\1.txt", "rw");
        FileChannel channel = randomAccessFile.getChannel();

        ByteBuffer buffer1 = ByteBuffer.allocateDirect(10);
        ByteBuffer buffer2 = ByteBuffer.allocateDirect(100);
        ByteBuffer buffer3 = ByteBuffer.allocateDirect(1024);

        ByteBuffer[] bfs = {buffer1, buffer2, buffer3};

        //分散读取
        channel.read(bfs);

        for (ByteBuffer buffer : bfs) {
            buffer.flip();
            //此处必须标记一下，否则后面的聚合写入就没数据了啊
            buffer.mark();
            byte[] bytes = new byte[buffer.limit()];
            buffer.get(bytes);
            buffer.reset();
            System.out.println(new String(bytes));
            System.out.println("**********************************************************");
        }

        RandomAccessFile randomAccessFile2 = new RandomAccessFile("E:\\StudyCodes\\Java\\nio\\src\\main\\resources\\1-4.txt", "rw");
        FileChannel channel1 = randomAccessFile2.getChannel();
        //聚合写入
        channel1.write(bfs);

        channel.close();
        channel1.close();

    }

}
