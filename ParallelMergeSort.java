import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelMergeSort {
    public int[] sort(int[] a) {
        SortAction sa = new SortAction(a);
        return ForkJoinPool.commonPool().invoke(new SortAction(a));
    }
    class SortAction extends RecursiveTask<int[]>{

        private static int[] merge(int[] numLeft, int[] numRight) {
        int[] mnum = new int[numLeft.length + numRight.length];
        int posLeft = 0, posRight = 0, counter = 0;

        while ( posLeft < numLeft.length && posRight < numRight.length ) {
            if (numLeft[posLeft] < numRight[posRight] ) {
                mnum[counter] = numLeft[posLeft];
                posLeft+=1;
            } else {
                mnum[counter] = numRight[posRight];
                posRight+=1;
            }
            counter++;
        }
        if ( posLeft < numLeft.length ) {
            System.arraycopy( numLeft, posLeft, mnum, counter, mnum.length - counter );
        }
        if ( posRight < numRight.length ) {
            System.arraycopy( numRight, posRight, mnum, counter, mnum.length - counter );
        }

        return mnum;
    }

        private int[] num;

    public SortAction(int[] num) {
        this.num = num;
    }

        @Override
        protected int[] compute() {
        if (num == null) {
            return null;
        }
        if (num.length < 2) {
            return num;
        }
        int[] num1 = new int[num.length / 2];
        System.arraycopy(num, 0, num1, 0, num.length / 2);

        int[] num2 = new int[num.length - num.length / 2];
        System.arraycopy(num, num.length / 2, num2, 0, num.length - num.length / 2);

        SortAction num1_sorted = new SortAction(num1);
        SortAction num2_sorted = new SortAction(num2);

        num1_sorted.fork();
        num2_sorted.fork();

        return merge(num1_sorted.join(), num2_sorted.join());
    }
    }
}
