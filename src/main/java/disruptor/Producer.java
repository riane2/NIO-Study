package disruptor;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

public class Producer {

    //环形缓冲区
    private final RingBuffer<PcData> ringBuffer;


    public Producer(RingBuffer<PcData> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /**
     * 将传入的数据取出来，放入环形缓冲区
     *
     * @param bb 用来包装任何数据类型
     */
    public void pushData(ByteBuffer bb) {
        long next = ringBuffer.next(); //抓取下一个可用的序列号
        try {
            PcData data = ringBuffer.get(next); //下一个可用序列号的实例
            data.setValue(bb.getLong(0)); //fill with data
        } catch (Exception ex) {

        } finally {
            //将数据推入缓冲区，等待消费
            ringBuffer.publish(next);
        }
    }


}
