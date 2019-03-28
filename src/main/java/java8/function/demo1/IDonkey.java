package java8.function.demo1;

public interface IDonkey {
    void eat();

    default void run() {
        System.out.println("Donkey is running...");
    }
}
