package same.thread;

import java.util.concurrent.locks.LockSupport;

public class TestLockSupport {


    private static String str = "name";

    private static TestThread t1 = new TestThread("t1");

    private static TestThread t2 = new TestThread("t2");

    static class TestThread extends Thread {

        String threadName;

        TestThread(String name) {
            this.threadName = name;
        }

        @Override
        public void run() {
            synchronized (str) {
                System.out.println(System.currentTimeMillis() / 1000 + " ,  " + threadName);
                LockSupport.park();
                System.out.println(threadName + " end...");
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(System.currentTimeMillis() / 1000);
        t1.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
        /**
         * 体会其与suspend的区别：弥补了resume执行在前，suspend执行在后而引起的线程一直阻塞的问题
         */
        LockSupport.unpark(t1);
        LockSupport.unpark(t2);
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
