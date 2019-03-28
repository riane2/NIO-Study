package sort;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 奇偶排序/砖排序（并行实现）
 */
public class BingXingJiOuSort {

    static ExecutorService pool = Executors.newCachedThreadPool();

    static int exchFlag = 1;    //记录当前迭代是否发生了数据交换


    public static void main(String[] args) {
        // private static
        //int[] arr = {11, 95, 45, 15, 78, 84, 51, 24, 12};
        int num = 10000;
        int arr[] = new int[num];
        for (int i = 0; i < num; i++) {
            int random = (int) (Math.random() * num);
            arr[i] = random;
        }
        try {
            System.out.println(System.currentTimeMillis() / 1000);
            PoddEvenSort(arr);
            System.out.println(System.currentTimeMillis() / 1000);
            pool.shutdown();
            System.out.println(Arrays.toString(arr));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static synchronized int getExchFlag() {
        return exchFlag;
    }

    public static synchronized void setExchFlag(int exchFlag) {
        BingXingJiOuSort.exchFlag = exchFlag;
    }

    static class OddSort implements Runnable {

        int i;
        int arr[];

        CountDownLatch countDownLatch;

        public OddSort(int arr[], int i, CountDownLatch countDownLatch) {
            this.i = i;
            this.countDownLatch = countDownLatch;
            this.arr = arr;
        }

        @Override
        public void run() {
            if (arr[i] > arr[i + 1]) {
                int tmp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = tmp;
                setExchFlag(1);
            }
            countDownLatch.countDown();
        }
    }

    static void PoddEvenSort(int[] arr) throws Exception {
        int start = 0;
        while (getExchFlag() == 1 || start == 1) {
            setExchFlag(0);
            //偶数的数组长度，当start为1时，只有len/2-1个线程
            CountDownLatch countDownLatch = new CountDownLatch(arr.length / 2 - (arr.length % 2 == 0 ? start : 0));
            for (int i = start; i < arr.length - 1; i += 2) {
                pool.submit(new OddSort(arr, i, countDownLatch));
            }
            //等待所有线程完成操作
            countDownLatch.await();
            start = start == 0 ? 1 : 0;
        }
    }


}
