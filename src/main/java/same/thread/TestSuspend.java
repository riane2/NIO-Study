package same.thread;

public class TestSuspend {


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
                Thread.currentThread().suspend();
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

        t1.resume();
        /**
         * 此处如果不休眠，t2线程可能会一直等待下去，因为resume可能在suspend之前执行，导致t2无法resume而一直等到
         */
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.resume();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
