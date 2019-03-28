package same;

import same.thread.SameThread;

import java.util.HashMap;
import java.util.Map;

public class TestSameMain {
    public static void main(String[] args) {

        Map<String, String> map = new HashMap<String, String>();
        map.put("age", "20");

        SameThread sameThread = new SameThread(map);
        sameThread.run();

        System.out.println(map);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(map);

    }
}
