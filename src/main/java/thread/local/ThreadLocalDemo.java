package thread.local;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadLocalDemo {

    private static final ThreadLocal<SimpleDateFormat> LOCAL = new ThreadLocal<>();

    static {
        LOCAL.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    static class MyRunnable implements Runnable {

        private int i;

        MyRunnable(int i) {
            this.i = i;
        }

        public void run() {
            try {

                //Date date = sdf.parse("2019-01-24 11:00:" + i % 60); //线程不安全的
                if (LOCAL.get() == null) {
                    LOCAL.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
                    System.err.println("设置参数");
                }
                Date date = LOCAL.get().parse("2019-01-24 11:00:" + i % 60);
                System.out.println(i + " : " + date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
       /* ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(10, 10,
                0L, TimeUnit.SECONDS, new LinkedBlockingQueue());
        for (int i = 0; i < 100; i++) {
            MyRunnable myRunnable = new MyRunnable(i);
            poolExecutor.execute(myRunnable);
        }
        poolExecutor.shutdown();*/

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddHHmmss");
        System.out.println(simpleDateFormat.format(new Date()));

    }
}
