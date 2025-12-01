import java.util.concurrent.RecursiveAction;

public class QuickeInsertionSort {
    public void sort(int[] a) {
        SortAction(a, 0, a.length - 1);
    }
    private void SortAction(int[] a, int lo, int hi){
        int minBlock = 30;
        if(hi <= lo);
        else if ((hi - lo) > minBlock) {
            int j = partition(lo, hi, a);
            SortAction(a, lo, j - 1);
            SortAction(a, j + 1, hi);
        }else{
            insertionSort(a, lo, hi + 1);
        }
    }
    private void insertionSort(int[] a, int lo, int hi){
        int temp;
        for(int out=lo+1; out<hi; out++){
            temp = a[out];
            int j = out;
            while (j > lo && a[j-1] > temp) {
                a[j] = a[j-1];
                --j;
            }
            a[j] = temp;
        }
    }
    private int partition(int low, int high, int[] arr) {
        int pivot = arr[high];
        int i = (low - 1);

        for (int j = low; j < high; j++) {

            if (arr[j] <= pivot) {
                i++;

                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        return i + 1;
    }

}
