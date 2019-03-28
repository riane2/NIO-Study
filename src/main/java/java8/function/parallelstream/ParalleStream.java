package java8.function.parallelstream;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * 并行流处理
 */
public class ParalleStream {

    private static boolean isPrime(int num) {
        int tmp = num;
        if (tmp < 2) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(tmp); i++) {
            if (tmp % i == 0) {
                return false;
            }
        }
        return true;
    }


    /**
     * 串行流处理
     */
    private static void serialStream() {
        long start = System.currentTimeMillis();
        long count = IntStream.range(1, 100000).filter(ParalleStream::isPrime).count();
        System.out.println(count);
        System.out.println(System.currentTimeMillis() - start);
    }

    /**
     * 并行流处理
     */
    private static void parallelStream() {
        long start = System.currentTimeMillis();
        long count = IntStream.range(1, 100000).parallel().filter(ParalleStream::isPrime).count();
        System.out.println(count);
        System.out.println(System.currentTimeMillis() - start);
    }

    private static void parallelSort() {
        int[] a = new int[1000];

        //给数组中的每一个元素付一个随机值
        Random random = new Random();
        //串行赋值
        //Arrays.setAll(a, (i) -> random.nextInt());
        //并行赋值
        Arrays.parallelSetAll(a, (i) -> random.nextInt());

        Arrays.sort(a);//串行排序

        Arrays.parallelSort(a);//并行排序

        System.out.println(Arrays.toString(a));
    }

    public static void main(String[] args) {
        serialStream();
        parallelStream();
        parallelSort();
    }


}
