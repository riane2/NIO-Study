package pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FirstPoolDemo {

    public static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getId() + " start " + System.currentTimeMillis() / 1000);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getId() + " end " + System.currentTimeMillis() / 1000);
        }
    }

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        System.out.println(myThread.getId());

        System.out.println(Double.valueOf("1.234"));
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);

        Thread testThread = new Thread(() -> {
            try {
                System.out.println(System.currentTimeMillis() / 1000);
                Thread.sleep(5000);
            } catch (Exception ex) {

            }
        });

        Thread testThread2 = new Thread(() -> {
            try {
                System.out.println(System.currentTimeMillis() / 1000);
                Thread.sleep(5000);
            } catch (Exception ex) {

            }
        });
        executorService.scheduleWithFixedDelay(testThread, 0, 2, TimeUnit.SECONDS);
        //executorService.scheduleAtFixedRate(testThread2, 0, 3, TimeUnit.SECONDS);
        /*ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        System.out.println("main" + System.currentTimeMillis() / 1000);
        executorService.schedule(myThread, 2, TimeUnit.SECONDS);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.schedule(myThread, 5, TimeUnit.SECONDS);*/
    }

}
