package codeblock;

public class ConstructorCodeBlock {
    //构造代码块：直接在类中定义且没有加static关键字的代码块称为{}构造代码块。
    //构造代码块在创建对象时被调用，每次创建对象都会被调用，并且构造代码块的执行次序优先于类构造函数
    {
        System.out.println("构造代码块1");
    }

    ConstructorCodeBlock() {
        System.out.println("构造函数");
    }

    {
        System.out.println("构造代码块2");
    }

    public static void main(String[] args) {
        //普通代码块：在方法或语句中出现的{}就称为普通代码块。
        //普通代码块和一般的语句执行顺序由他们在代码中出现的次序决定--“先出现先执行
        {
            System.out.println("普通代码块");
        }
        new ConstructorCodeBlock();
        System.out.println("another");
        new ConstructorCodeBlock();
    }
}
