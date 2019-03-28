package thread.lock.condition;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TestCondition extends Thread {

    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    @Override
    public void run() {
        try {
            while (true) {
                lock.lock();
                System.out.println("wait: " + System.currentTimeMillis() / 1000);
                //condition.await();
                condition.await(6, TimeUnit.SECONDS);
                System.out.println(System.currentTimeMillis() / 1000);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        TestCondition testCondition = new TestCondition();
        testCondition.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        lock.lock();
        condition.signal();
        lock.unlock();

    }
}
