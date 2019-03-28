package thread.volitiles;

import java.util.Collections;

public class TestVolitile {

    public static void main(String[] args) throws Exception {
        Thread[] threads = new Thread[10];

        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(new ThreadVolatile());
            threads[i].start();
            //threads[i].join();
        }
        Thread.sleep(5000);
        System.out.println(VolitileUtils.getInstance().getNum());

        //Collections.synchronizedMap();

    }


}
