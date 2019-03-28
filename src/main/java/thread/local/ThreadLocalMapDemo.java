package thread.local;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.*;

public class ThreadLocalMapDemo {

    private volatile static CountDownLatch countDownLatch = new CountDownLatch(1000);
    private volatile static ThreadLocal<SimpleDateFormat> simpleDateFormat = new ThreadLocal() {
        @Override
        protected void finalize() throws Throwable {
            System.out.println(this.toString() + " is gc ......");
        }
    };

    static class ParseDateRunnable implements Runnable {


        int i;

        ParseDateRunnable(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            if (simpleDateFormat.get() == null) {
                System.out.println("设置new sDF");
                simpleDateFormat.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") {
                    @Override
                    protected void finalize() throws Throwable {
                        System.out.println(this.toString() + " is gc...");
                    }
                });
            }

            try {
                simpleDateFormat.get().parse("2019-01-25 11:20:" + 1 % 60);
                //simpleDateFormat.remove();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(10, 10, 0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>()){
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                super.beforeExecute(t, r);
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {

            }
        };
        for (int i = 0; i < 1000; i++) {
            ParseDateRunnable dateRunnable = new ParseDateRunnable(i);
            poolExecutor.execute(dateRunnable);
        }
        countDownLatch.await();
        //System.out.println(System.currentTimeMillis() / 1000);
        System.out.println("mission completed");

        //countDownLatch = null;
        simpleDateFormat = null;
        System.gc();
        System.out.println("first gc completed");

        //此处再设置ThreadLocal时会清除ThreadLocalMap中的无效对象

        simpleDateFormat = new ThreadLocal();

        countDownLatch = new CountDownLatch(10000);
        for (int i = 0; i < 10000; i++) {
            ParseDateRunnable dateRunnable = new ParseDateRunnable(i);
            poolExecutor.execute(dateRunnable);
        }
        countDownLatch.await();
        // System.out.println(System.currentTimeMillis() / 1000);
        Thread.sleep(1000);
        System.gc();
        System.out.println("seconf gc completed");
        poolExecutor.shutdown();

    }

}
