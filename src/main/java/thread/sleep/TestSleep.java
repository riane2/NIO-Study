package thread.sleep;

public class TestSleep {

    public static void main(String[] args) throws Exception {
        /*Thread thread = new Thread() {

            @Override
            public void run() {
                System.out.println(System.currentTimeMillis() / 1000 + "," + Thread.currentThread().getName());
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis() / 1000);
            }
        };
        thread.start();
        System.out.println("main thread name:" + Thread.currentThread().getName());
        System.out.println("main:" + System.currentTimeMillis() / 1000);
        Thread.sleep(15000);
        System.out.println("main sleep:" + System.currentTimeMillis() / 1000);*/
        Thread thread = new Thread() {

            @Override
            public void run() {
                while (true) {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println(Thread.currentThread().getName() + " isInterrupted");
                        return;
                    }
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        System.out.println(Thread.currentThread().getName() + " isInterrupted while sleeping");
                        //设置中断标志（因为此时的异常处理会清除中断标志，为了让下次运行时进行中断监测生效，故此处要设置中断标志）
                        Thread.currentThread().interrupt();
                    }
                    System.out.println(System.currentTimeMillis() / 1000);
                    Thread.yield();
                }
            }
        };
        thread.start();
        Thread.sleep(15000);
        thread.interrupt();
        System.out.println("main sleep:" + System.currentTimeMillis() / 1000);
    }
}
