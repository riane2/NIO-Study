package atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class IntDemo {

    static AtomicInteger integer = new AtomicInteger();

    public static void main(String[] args) {
        integer.incrementAndGet();
        System.out.println(integer.get());
    }


}
