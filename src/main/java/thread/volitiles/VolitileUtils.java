package thread.volitiles;

import java.util.concurrent.atomic.AtomicInteger;

public class VolitileUtils {

    private volatile AtomicInteger a = new AtomicInteger(0);

    private static final class VolitileUtilsHolder {
        private static final VolitileUtils instance = new VolitileUtils();
    }

    public static VolitileUtils getInstance() {
        return VolitileUtilsHolder.instance;
    }


    public void addNum() {
        //synchronized (this) {
        a.incrementAndGet();
        //}
    }

    public int getNum() {
        //synchronized (this){
        return this.a.get();
        //}
    }
}
