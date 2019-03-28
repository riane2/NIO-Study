package thread.volitiles;

public class ThreadVolatile implements Runnable {

    public void run() {
        for (int i = 0; i < 1000; i++) {
            VolitileUtils.getInstance().addNum();
        }
    }
}
