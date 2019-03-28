package disruptor;

import com.lmax.disruptor.EventFactory;

/**
 * PcData 的工厂类，它会在Disruptor系统初始化的构造所有的缓冲区中的对象实例，即预先分配空间
 */
public class PcDataFactory implements EventFactory<PcData> {
    public PcData newInstance() {
        return new PcData();
    }
}
