package sort;

import finalclass.bean.Student;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 希尔排序
 */
public class ShellSort {

    private static void shellSort(int[] arr) {

        int length = arr.length;
        int gap = 1;
        if (gap < length / 3) {
            gap = gap * 3 + 1;
        }
        while (gap > 0) {
            for (int i = gap; i < length; i++) {
                int tmp = arr[i];
                int j = i - gap;
                while (j >= 0 && arr[j] > tmp) {
                    arr[j + gap] = arr[j];
                    j -= gap;
                }
                arr[j + gap] = tmp;
            }
            gap = (int) Math.floor(gap / 2);
        }
    }

    public static void main(String[] args) {
        /*int num = 100000;
        int arr[] = new int[num];
        for (int i = 0; i < num; i++) {
            int random = (int) (Math.random() * num);
            arr[i] = random;
        }*/
        /*int arr[] = {3, 0, 9, 4, 6, 8, 2};
        System.out.println(System.currentTimeMillis()/1000);
        shellSort(arr);
        System.out.println(System.currentTimeMillis()/1000);
        System.out.println(Arrays.toString(arr));*/

        System.out.println(String.format("%03d", 111));



        /*String name = "6幢";

        System.out.println(name.substring(0,name.lastIndexOf("3")));*/





    }

}
