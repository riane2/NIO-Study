package java8.function.demo1;

public interface IHorse {

    void eat();

    default void run() {
        System.out.println("Horse is running...");
    }

}
