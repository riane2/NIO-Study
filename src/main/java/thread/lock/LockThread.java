package thread.lock;

import java.util.concurrent.locks.ReentrantLock;

public class LockThread extends Thread {

    private static ReentrantLock lock1 = new ReentrantLock();

    private static ReentrantLock lock2 = new ReentrantLock();

    int num;

    public LockThread(int num) {
        this.num = num;
    }

    @Override
    public void run() {
        try {
            if (num == 1) {

                lock1.lockInterruptibly();

                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                lock2.lockInterruptibly();

            } else {
                lock2.lockInterruptibly();

                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    if (lock2.isHeldByCurrentThread()) {
                        lock2.unlock();
                    }
                }

                lock1.lockInterruptibly();

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (lock1.isHeldByCurrentThread()) {
                lock1.unlock();
            }
            if (lock2.isHeldByCurrentThread()) {
                lock2.unlock();
            }
            System.out.println(Thread.currentThread().getId() + "线程退出");
        }
    }

    public static void main(String[] args) throws Exception {
        LockThread lockThread1 = new LockThread(1);
        LockThread lockThread2 = new LockThread(2);
        lockThread1.start();
        lockThread2.start();
        Thread.sleep(5000);
        lockThread2.interrupt();

    }
}
