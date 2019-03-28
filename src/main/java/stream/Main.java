package stream;


import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        List<Map<String, String>> list = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Map<String, String> map = new Hashtable<>();
            map.put("name", "riane" + i);
            map.put("sex", i % 2 == 0 ? "M" : "FM");
            list.add(map);
        }


        List<Student> students = new LinkedList<>();

        //students.add(null);
        students = list.stream().map(map -> {
            Student student = new Student();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (entry.getValue().equals("riane3")){
                    return null;
                }
                if (entry.getKey().equals("name")) {
                    student.setName(entry.getValue());
                } else {
                    student.setSex(entry.getValue());
                }
            }
            return student;
        }).collect(Collectors.toList());

        students.removeAll(Collections.singleton(null));
        for (Student student : students) {
            System.out.println(student);
        }

    }
}
