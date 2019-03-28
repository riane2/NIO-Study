package codeblock;

/**
 * 执行顺序：（优先级从高到低。）静态代码块 > mian方法 > 构造代码块 > 构造方法。
 * 其中静态代码块只执行一次。构造代码块在每次创建对象是都会执行
 */
public class StaticCodeBlock {
    // 静态代码块:在java中使用static关键字声明的代码块。静态块用于初始化类，为类的属性初始化。
    // 每个静态代码块只会执行一次。由于JVM在加载类时会执行静态代码块，所以静态代码块先于主方法执行。
    // 如果类中包含多个静态代码块，那么将按照"先定义的代码先执行，后定义的代码后执行"。
    // 注意：1 静态代码块不能存在于任何方法体内。2 静态代码块不能直接访问实例变量和实例方法，需要通过类的实例对象来访问
    static {
        System.out.println("这是静态代码块");
    }

    // 静态代码块先于主方法执行
    public static void main(String[] args) {
        {
            System.out.println("这是普通代码块");
        }
        System.out.println("main code block");

        new StaticCodeBlock();
    }


}
