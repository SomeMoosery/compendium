package SortingAlgorithms;

public class Quicksort {
    public static void quicksort(int[] arr) {
        quicksort(arr, 0, arr.length-1);
    }

    private static void quicksort(int[] arr, int l, int r) {
        if (l < r) {
            int pivot = partition(arr, l, r);
            quicksort(arr, l, pivot-1);
            quicksort(arr, pivot+1, r);
        }
    }

    private static int partition(int[] arr, int l, int r) {
        int pivot = arr[r];
        int i = l-1;
        for (int j = l; j < r; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i+1, r);
        return i+1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] list = new int[]{3,7,8,2,1};
        System.out.print("List prior to sorting: ");
        for (int i : list) {
            System.out.print(i + ", ");
        }
        System.out.println();
        quicksort(list);
        System.out.print("List after sorting: ");
        for (int i : list) {
            System.out.print(i + ", ");
        }
        System.out.println();
    }
}