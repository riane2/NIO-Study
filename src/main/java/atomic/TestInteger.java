package atomic;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class TestInteger {

    static ConcurrentHashMap<Integer, AtomicInteger> map = new ConcurrentHashMap<Integer, AtomicInteger>();


    public static void main(String[] args) {

        AtomicInteger atomicInteger = new AtomicInteger(0);

        TestInteger testInteger = new TestInteger();
        testInteger.add(0);

        System.out.println(map);

        /*map.put(1,new AtomicInteger(1));
        System.out.println(map.hashCode());
        ConcurrentHashMap<Integer, AtomicInteger> mapCopy = map;
        System.out.println(mapCopy.hashCode());
        mapCopy.put(1,new AtomicInteger(2));
        System.out.println(mapCopy);
        System.out.println(map);
        System.out.println(mapCopy == map);

        System.out.println(mapCopy.hashCode());
        System.out.println(map.hashCode());*/

        ConcurrentHashMap<Integer, AtomicInteger> mapCopy2 = new ConcurrentHashMap<Integer, AtomicInteger>();
        mapCopy2.putAll(map);

        System.out.println(mapCopy2 == map);

        System.out.println(mapCopy2);

        System.out.println(mapCopy2.hashCode());
        System.out.println(map.hashCode());


//        System.out.println(atomicInteger.get());
//        int increment = atomicInteger.getAndIncrement();
//        System.out.println(increment);
//        System.out.println(atomicInteger.get());
//
//        int increment1 = atomicInteger.getAndIncrement();
//        System.out.println(increment1);
//
//        System.out.println(atomicInteger.get());
//
//        System.out.println("******************");
//        int andGet = atomicInteger.incrementAndGet();
//
//        System.out.println(andGet);
//        System.out.println(atomicInteger.get());


    }


    private void add(Integer key) {
        AtomicInteger integer = map.get(key);
        if (integer == null) {
            integer = new AtomicInteger(0);
        }
        integer.incrementAndGet();
        map.put(key, integer);
    }

}
