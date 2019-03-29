package nio.pipe;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * Pipe：管道，2个线程之间单向的数据连接，
 *
 * Sink通道：写线程即数据会被写入Sink通道
 * Source通道：读线程，即数据从source通道被拿走
 *
 */
public class PipeDemo {

    @Test
    public void testPipe() throws IOException {
        Pipe pipe = Pipe.open();
        Pipe.SinkChannel sinkChannel = pipe.sink();

        ByteBuffer buffer = ByteBuffer.allocate(100);
        buffer.put("sink单项管道数据传输".getBytes());

        buffer.flip();
        sinkChannel.write(buffer);

        Pipe.SourceChannel sourceChannel = pipe.source();
        buffer.clear();
        while (sourceChannel.read(buffer)!=-1){
            buffer.flip();
            System.out.println(new String(buffer.array(),0,buffer.limit()));
            buffer.clear();
        }

        sinkChannel.close();
        sourceChannel.close();
    }
}
