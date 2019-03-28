package java8.function;

import java.util.*;
import java.util.function.Function;

/**
 * 函数式编程
 * 1、无副作用（没法做到完全无副作用）
 * 2、不变性
 * 3、并行性
 * 4、简洁性（更少的代码）
 * 5、申明式的
 */
public class Demo01 {
    public static void main(String[] args) {
        int[] a = {1, 2, 3, 4, 5, 6};
        //不变性
        Arrays.stream(a).map(x -> x += 1).forEach(System.out::print);
        System.out.println();
        //System.out::print这种写法叫方法引用
        Arrays.stream(a).forEach(System.out::print);

        System.out.println();
        int[] tmp = Arrays.copyOf(a, a.length);
        //简洁性
        for (int i = 0; i < a.length; i++) {
            if (a[i] % 2 == 0) {
                a[i] += 3;
            }
        }
        System.out.println(Arrays.toString(a));
        //函数式写法，体现简洁性
        System.out.println(Arrays.toString(Arrays.stream(tmp).map(x -> (x % 2 == 0) ? x += 3 : x).toArray()));

        //申明式：如果要输出数组，一般写法是：
        for (int i = 0; i < tmp.length; i++) {
            System.out.print(tmp[i] + ",");
        }
        System.out.println();
        //函数式写法是：
        Arrays.stream(tmp).forEach((value) -> System.out.print(value + ","));

        System.out.println();
        int num = 3;
        Function<Integer, String> function = (from) -> from * num + ",";
        System.out.println(function.apply(5));

        Function<Map<String, Integer>, List<Integer>> function1 = (input) -> {
            List<Integer> result = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : input.entrySet()) {
                result.add(entry.getValue());
            }
            return result;
        };

        Map<String, Integer> inputMap = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            inputMap.put(i + "", i);
        }

        List<Integer> resultList = function1.apply(inputMap);
        System.out.println(resultList.toString());

    }
}
