package disruptor;


import com.lmax.disruptor.WorkHandler;

public class Consumer implements WorkHandler<PcData> {

    public void onEvent(PcData event) throws Exception {
        System.out.println("consumer " + Thread.currentThread().getId() + ":" + event);
    }
}
