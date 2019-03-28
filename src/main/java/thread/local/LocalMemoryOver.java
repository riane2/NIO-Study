package thread.local;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal的内存泄漏问题
 */
public class LocalMemoryOver {

    private static ThreadLocal<Integer> local = new ThreadLocal() {
        @Override
        protected Object initialValue() {
            return 100;
        }
    };


    public static void main(String[] args) {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 5, 0L,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        for (int i = 0; i < 10; i++) {
            final int num = i;
            poolExecutor.execute(() -> {
                local.set(num);
                System.out.println(local.get());
                //local.remove();
            });
        }
        //poolExecutor.shutdown();
    }


}
