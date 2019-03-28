package sort;

import java.util.Arrays;

/**
 * 奇偶排序/砖排序（串行实现）
 * 算法步骤
 * 1、选取所有奇数列的元素与其右侧相邻的元素进行比较，将较小的元素排序在前面；
 * 2、选取所有偶数列的元素与其右侧相邻的元素进行比较，将较小的元素排序在前面；
 * 3、重复前面两步，直到所有序列有序为止。
 */
public class JiOuSort {

    /**
     * 奇偶排序的串行算法
     *
     * @param array
     */
    private static void core(int[] array) {

        //测试循环进行了几次
        int count = 0;

        int arrayLength = array.length;
        boolean oddSorted = false; //奇数排序
        boolean evenSorted = false; //偶数排序

        while (!oddSorted || !evenSorted) {
            count += 1;
            int base = 0;
            oddSorted = true;
            evenSorted = true;

            for (int i = base; i < arrayLength - 1; i += 2) {
                if (array[i] > array[i + 1]) {
                    swap(array, i, i + 1);
                    oddSorted = false;
                }
            }

            base = 1;
            for (int i = base; i < arrayLength - 1; i += 2) {
                if (array[i] > array[i + 1]) {
                    swap(array, i, i + 1);
                    evenSorted = false;
                }
            }
        }
        System.out.println(count);
    }

    private static void swap(int arr[], int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int num = 100000;
        int arr[] = new int[num];
        for (int i = 0; i < num; i++) {
            int random = (int) (Math.random() * num);
            arr[i] = random;
        }
        //int[] number = {11, 95, 45, 15, 78, 84, 51, 24, 12, 9, 8, 7, 6, 2, 4, 3, 1, 10, 20, 21, 34, 56, 77, 98, 99, 89, 88, 67, 76, 54, 34, 33, 22, 44, 100, 20};
        System.out.println(System.currentTimeMillis() / 1000);
        core(arr);
        System.out.println(System.currentTimeMillis() / 1000);
        System.out.println(Arrays.toString(arr));
    }

}
