import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

class ParallelQuickSort {
    public void sort(int[] a) {
        ForkJoinPool.commonPool().invoke(new SortAction(a, 0, a.length - 1));
    }
    private class SortAction extends RecursiveAction {
        private final int bubbleBlock = 16;

        int[] a;
        int lo;
        int hi;

        SortAction(int[] a, int lo, int hi) {
            this.a = a;
            this.lo = lo;
            this.hi = hi;
        }
        @Override
        protected void compute() {
            if(hi <= lo) return;
            int j = partition(lo, hi,a);
            invokeAll(new SortAction(a, lo, j - 1), new SortAction(a, j + 1, hi));
        }
        private int partition(int low, int high, int[] arr) {
            int pivot = arr[high];
            int i = (low - 1); // Index of smaller element

            for (int j = low; j < high; j++) {
                // If current element is smaller than or equal to pivot
                if (arr[j] <= pivot) {
                    i++;

                    // Swap arr[i] and arr[j]
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }

            // Swap arr[i+1] and arr[high] (pivot)
            int temp = arr[i + 1];
            arr[i + 1] = arr[high];
            arr[high] = temp;

            return i + 1;
        }
    }
}