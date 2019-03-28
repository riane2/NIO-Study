package sort;


import java.util.Arrays;

/**
 * 冒泡排序
 */
public class MaoPaoSort {

    public static void main(String[] args) {
        int arr[] = {1, 3, 9, 4, 6, 8, 2, 11, 5, 12, 10, 7};
        int length = arr.length;
        for (int i = 1; i < length; i++) {
            for (int j = 0; j < length - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(arr));


    }
}
