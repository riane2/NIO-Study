package finalclass;

import finalclass.bean.Student;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("1", "1");
        map.put("2", "2");
        Student student = new Student(10, map);
        System.out.println(10 == student.getAge());
        System.out.println(map == student.getHashMap());
        System.out.println(student.getHashMap());

        map.put("3", "3");

        //观察结果，这就是浅度copy引起的问题
        System.out.println(student.getHashMap());
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.err.println("**************************************");

        HashMap<String, String> deepMap = new HashMap<String, String>();
        deepMap.put("1", "1");
        deepMap.put("2", "2");

        Student studentDeep = new Student(20, deepMap, true);
        System.out.println(studentDeep.getAge() == 20);
        System.out.println(deepMap == studentDeep.getHashMap());

        deepMap.put("3", "3");
        System.out.println(studentDeep.getHashMap());
        System.out.println(deepMap);


    }
}
