package thread.join;

public class JoinAndYield {

    private static volatile int i = 0;

    private static TestThread t1 = new TestThread("t1");
    private static TestThread t2 = new TestThread("t2");

    static class TestThread extends Thread {

        String threadName;

        TestThread(String name) {
            this.threadName = name;
        }

        @Override
        public void run() {
            for (int j = 0; j < 10000; j++) {
                i++;
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(System.currentTimeMillis() / 1000);
        t1.start();
        t1.join();

        System.out.println(System.currentTimeMillis() / 1000);
        t2.start();
        t2.join();

        System.out.println(i);
        System.out.println(System.currentTimeMillis() / 1000);
    }

}
