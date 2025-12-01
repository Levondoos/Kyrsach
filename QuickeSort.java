public class QuickeSort {
    public void sort(int[] a) {
        SortAction(a, 0, a.length - 1);
    }
    private void SortAction(int[] a, int lo, int hi){
        if(hi <= lo) return;
        int j = partition(lo, hi, a);
        SortAction(a, lo, j - 1);
        SortAction(a, j + 1, hi);
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
