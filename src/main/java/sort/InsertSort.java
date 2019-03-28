package sort;

import java.util.Arrays;

/**
 * 插入排序
 */
public class InsertSort {


    private static void inertSort(int[] arr) {
        int length = arr.length;
        for (int i = 1; i < length; i++) {
            int tmp = arr[i];
            int j = i;
            while (j > 0 && arr[j - 1] > tmp) {
                arr[j] = arr[j - 1];
                j--;
            }
            //存在比其小的数
            if (j != i) {
                arr[j] = tmp;
            }
        }
    }

    public static void main(String[] args) {

        int num = 100000;
        int arr[] = new int[num];
        for (int i = 0; i < num; i++) {
            int random = (int) (Math.random() * num);
            arr[i] = random;
        }
        //int arr[] = {3, 0, 9, 4, 6, 8, 2};
        System.out.println(System.currentTimeMillis() / 1000);
        inertSort(arr);
        System.out.println(System.currentTimeMillis() / 1000);
        System.out.println(Arrays.toString(arr));
    }
}
