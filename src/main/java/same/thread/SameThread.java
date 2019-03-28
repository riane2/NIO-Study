package same.thread;

import java.util.HashMap;
import java.util.Map;

public class SameThread extends Thread {

    private Map<String, String> map;

    public SameThread(Map<String, String> map) {
        //this.map = map;
        Map<String, String> tmpMap = new HashMap<String, String>();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            tmpMap.put(entry.getKey(), entry.getValue());
        }
        this.map = tmpMap;
    }


    @Override
    public void run() {
        map.put("name", Thread.currentThread().getName());
        System.out.println(Thread.currentThread().getName() + ": " + map);
    }
}
