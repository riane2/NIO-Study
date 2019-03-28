package thread.countdowm;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestCountDownLatch implements Runnable {


    private static CountDownLatch countDownLatch = new CountDownLatch(10);
    private static TestCountDownLatch testCountDownLatch = new TestCountDownLatch();

    public void run() {
        try {
            System.out.println(System.currentTimeMillis() / 1000);
            Thread.sleep(5000);
            countDownLatch.countDown();
        } catch (Exception ex) {

        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        System.out.println("main start: "
                + System.currentTimeMillis() / 1000);
        for (int i = 0; i < 15; i++) {
            executorService.execute(testCountDownLatch);
        }
        try {
            countDownLatch.await();
            //countDownLatch.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main end: "
                + System.currentTimeMillis() / 1000);
        executorService.shutdown();
    }
}
