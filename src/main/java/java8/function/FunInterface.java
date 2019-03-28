package java8.function;

/**
 * 函数式接口：只定义了单一抽象方法的接口；
 * 如果一个接口满足上面的条件，即使没有@FunctionalInterface注解，编译器依然认定是一个函数式接口
 */
@FunctionalInterface
public interface FunInterface {

    /**
     * 需要强调的函数式接口意味着只能有一个抽象的方法，而不是只能有一个方法。
     * 1、接口运行存在实例方法（“接口默认方法”）
     * 2、任何被java.lang.Object实现的方法，都不能视为抽象方法，例如方法hashCode(),equals(),toString()等
     *
     * @param object
     */
    void print(Object object);

    boolean equals(Object object); //只能跟上面的方法同时存在，不能单独存在

    //如果只有此方法，编译器会提示错误：没有抽象方法
    //String toString();

    //接口默认方法，即实例方法，并不是抽象方法，而是拥有特定逻辑的具体实例方法
    default int size() {
        return 0;
    }
}
