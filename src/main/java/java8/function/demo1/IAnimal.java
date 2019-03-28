package java8.function.demo1;

public interface IAnimal {

    default void breath(){
        System.out.println("Animal is breathing...");
    }
}
