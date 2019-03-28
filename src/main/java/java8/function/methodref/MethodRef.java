package java8.function.methodref;

import java.util.ArrayList;
import java.util.List;

/**
 * 方法引用
 */
public class MethodRef {

    static class User {
        private String name;
        private String sex;

        User() {

        }

        User(String name, String sex) {
            this.name = name;
            this.sex = sex;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", sex='" + sex + '\'' +
                    '}';
        }
    }

    @FunctionalInterface
    interface UserFactory<u extends User> {

        u create(String name, String sex);

    }

    /**
     * 实例方法引用
     */
    private static void instanceMethodRef() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setName("riane" + i);
            user.setSex(i % 2 == 0 ? "M" : "FM");
            users.add(user);
        }
        users.stream().map(User::toString).forEach(System.out::println);
    }

    //构造函数引用
    private static void constructorMethRef() {

        //UserFactory必须是一个FunctionalInterface
        //也就是说User::new对应的必须是一个函数式接口
        UserFactory<User> factory = User::new;

        List<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            //UserFactory会根据参数调用合适的User构造函数
            User user = factory.create("riane" + i, i % 2 == 0 ? "M" : "FM");
            users.add(user);
        }
        users.stream().map(User::toString).forEach(System.out::println);
    }

    public static void main(String[] args) {
        instanceMethodRef();
        System.out.println();
        constructorMethRef();
    }

}
