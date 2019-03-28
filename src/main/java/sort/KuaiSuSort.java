package sort;

import java.util.Arrays;

/**
 * 快速排序
 */
public class KuaiSuSort {

    private static int partition(int arr[], int left, int right) {
        int pivot = left; //基准
        int index = pivot + 1;
        for (int i = index; i <= right; i++) {
            if (arr[i] < arr[pivot]) {
                swap(arr, i, index);
                index++;
            }
        }
        swap(arr, pivot, index - 1);
        return index - 1;
    }

    private static void swap(int arr[], int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static int[] quickSort(int arr[], int left, int right) {
        if (left < right) {
            int partition = partition(arr, left, right);
            quickSort(arr, left, partition - 1);
            quickSort(arr, partition + 1, right);
        }
        return arr;
    }

    public static void main(String[] args) {
        int arr[] = {1, 3, 9, 4, 6, 8, 2};
        int length = arr.length;
        int[] sort = quickSort(arr, 0, length - 1);
        System.out.println(Arrays.toString(sort));
    }


}
