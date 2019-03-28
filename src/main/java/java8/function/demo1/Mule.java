package java8.function.demo1;

/**
 * 由于马和驴的接口中的方法一样，实现其方法时需要指定实现的是哪个接口的
 */
public class Mule implements IAnimal, IDonkey, IHorse {
    @Override
    public void eat() {
        System.out.println("Mule is eating...");
    }

    @Override
    public void run() {
        //指定实现的run方法用的是IHorse中的默认实现方法
        IHorse.super.run();
    }

    public static void main(String[] args) {
        Mule mule = new Mule();
        mule.breath();
        mule.eat();
        mule.run();
    }

}
