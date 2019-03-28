package disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        PcDataFactory dataFactory = new PcDataFactory();
        //环形缓冲区的大小，必须是2的整数次幂
        int bufferSize = 1024;

        Disruptor<PcData> disruptor = new Disruptor<PcData>(dataFactory, bufferSize, executorService,
                ProducerType.MULTI, new BlockingWaitStrategy());

        //定义4个消费者，系统会自动分配4个线程
        disruptor.handleEventsWithWorkerPool(new Consumer(), new Consumer(), new Consumer(), new Consumer());
        disruptor.start();

        RingBuffer<PcData> ringBuffer = disruptor.getRingBuffer();

        for (long l = 1; l < 5; l++) {
            Producer producer = new Producer(ringBuffer);
            ByteBuffer byteBuffer = ByteBuffer.allocate(8);
            byteBuffer.putLong(0, l);
            System.out.println("add data " + l);
            producer.pushData(byteBuffer);
            Thread.sleep(1000);
        }

    }
}
