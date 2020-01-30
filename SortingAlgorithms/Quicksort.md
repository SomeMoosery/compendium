## Quicksort: 

### Steps:
1. Split function
    1. Split input
    2. Call partition step
    3. Based on pivot, split data and run trees
2. Partitioning subroutine
    1. Choose a pivot lmao

**pivot:** value within the partitioning space that you want to find a position for

```java
public void quicksort(int[] arr) {
    quicksort(arr, 0, arr.length-1);
}

private void quicksort(int[] arr, int l, int r) {
    if (l < r) {
        int pivot = partition(arr, l, r);
        quicksort(arr, l, pivot-1);
        quicksort(arr, pivot+1, r);
    }
}

private int partition(int[] arr, int l, int r) {
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

private void swap(int[] arr, int i, int j) {
    int temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
}
```

**Worst Case** is when the pivot is the greatest or smallest item, which leads to O(n^2)