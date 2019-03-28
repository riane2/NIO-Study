package atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class UpdaterDemo {

    public static class Student {
        int id;
        //此字段：1，不能static 2，必须用volatile修饰保证可读性 3，必须是public否则反射无法访问
        volatile int score;
    }

    static AtomicIntegerFieldUpdater integerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(Student.class, "score");

    static AtomicInteger allScore = new AtomicInteger(0);


    public static void main(String[] args) {

        final Student student = new Student();
        Thread[] threads = new Thread[10000];
        for (int i = 0; i < 10000; i++) {
            threads[i] = new Thread(() -> {
                if (Math.random() > 0.4) {
                    integerFieldUpdater.getAndIncrement(student);
                    allScore.getAndIncrement();
                }
            });
            threads[i].start();
        }
        for (int i = 0; i < 10000; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(integerFieldUpdater.get(student));
        System.out.println(allScore.get());

    }

}
