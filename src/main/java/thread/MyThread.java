package thread;

import thread.util.MyExcutors;

import java.util.concurrent.TimeUnit;

public class MyThread implements Runnable {

    private int age;

    private String name;


    MyThread(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public void run() {
        if (age == 10) {
            System.out.println(name.hashCode());
            age--;
            MyExcutors.getExecutorService().schedule(this, 10, TimeUnit.SECONDS);
            System.out.println(System.currentTimeMillis() / 1000 + ",running after 10 s");
        } else {
            System.out.println(name.hashCode());
            System.out.println(System.currentTimeMillis() / 1000 + ",running..." + age);
        }
    }
}
