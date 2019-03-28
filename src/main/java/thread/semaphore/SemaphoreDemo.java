package thread.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreDemo extends Thread {

    private static Semaphore semaphore = new Semaphore(5);

    @Override
    public void run() {
        try {
            semaphore.acquire();
            Thread.sleep(2000);
            System.out.println("time: " + System.currentTimeMillis() / 1000);
        } catch (Exception ex) {

        } finally {
            semaphore.release();
        }
    }

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        SemaphoreDemo semaphoreDemo = new SemaphoreDemo();
        for (int i = 0; i < 20; i++) {
            executorService.execute(semaphoreDemo);
        }
        executorService.shutdown();
        if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
            executorService.shutdown();
        }
    }
}
