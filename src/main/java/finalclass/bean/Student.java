package finalclass.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class Student {

    private final int age;

    private final HashMap<String, String> hashMap;

    /**
     * 浅度copy
     *
     * @param age
     * @param hashMap
     */
    public Student(int age, HashMap<String, String> hashMap) {
        this.age = age;
        this.hashMap = hashMap;
    }

    /**
     * 深度Copy
     *
     * @param age
     * @param hashMap
     * @param isDeep
     */
    public Student(int age, HashMap<String, String> hashMap, Boolean isDeep) {
        this.age = age;
        HashMap<String, String> tmpMap = new HashMap<String, String>();
        Set<Map.Entry<String, String>> entries = hashMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            tmpMap.put(entry.getKey(), entry.getValue());
        }
        this.hashMap = tmpMap;
    }

    public int getAge() {
        return age;
    }

    public HashMap getHashMap() {
        //deep-copy
        return (HashMap) hashMap.clone();
        //shallow-copy
        //return hashMap;
    }
}
