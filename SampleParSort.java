package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class SampleParSort {
    public int[] sort(int[] a, int treads) throws InterruptedException, ExecutionException{
        final ExecutorService executor = Executors
                .newFixedThreadPool(treads);
        int[] arr = SortAction(a, executor, treads);
        executor.shutdown();
        return arr;
    }
    public int[] SortAction(int[] arr, ExecutorService executor, int treads) throws InterruptedException, ExecutionException{
        // Шаг 1: Выбираем граничные значения для диапозонов и сортируем их
        int[] splitters = selectSamples(arr, treads);
        // Шаг 2: Создаём диапазоны
        int[][] bucket = creatBucket(splitters);
        // Шаг 3: Параллельная перемещение элементов в диапазоны и их сортировка
        List<Future<int[]>> sortingPart = sortBucketsParallel(arr, bucket, executor);
        // Шаг 4: Объединение результатов
        return margePart(sortingPart, arr.length);
    }

    private int[] selectSamples(int[] arr, int treads) {
        int sampleSize = arr.length;
        if (arr.length > treads * 5) {
            sampleSize = treads * 5;
        }
        int[] samples = new int[sampleSize];

        Random random = new Random();
        for (int i = 0; i < sampleSize; i++) {
            int randomIndex = random.nextInt(arr.length);
            samples[i] = arr[randomIndex];
        }
        // Сортируем
        insertionSort(samples, 0, sampleSize);

        int[] splitters = new int[treads - 1];
        int step = sampleSize / treads;

        for (int i = 1; i < treads; i++) {
            splitters[i-1] = samples[i * step];
        }
        return splitters;
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
    private int[][] creatBucket(int[] splitters) {
        int [][] bucket = new int [splitters.length+1][2];
        bucket[0][0] = 0; bucket[0][1] = splitters[0];
        for(int s = 1; s<splitters.length; s++){
            bucket[s][0] = splitters[s-1]; bucket[s][1] = splitters[s];
        }
        bucket[splitters.length][0] = splitters[splitters.length-1]; bucket[splitters.length][1] = -1;
        return bucket;
    }

    public List<Future<int[]>> sortBucketsParallel(int[] arr, int[][] bucket, ExecutorService executor) throws InterruptedException {
        List<Sorting> Buckets = new ArrayList<>();
        for (int i = 0; i < bucket.length; i++) {
            Buckets.add( new Sorting(arr, bucket[i][0], bucket[i][1]));
        }
        return executor.invokeAll(Buckets);
    }

    public class Sorting implements Callable<int[]> {

        private int[] arr;
        private int lo, hi;

        public  Sorting (int[] arr, int lo, int hi){
            this.arr = arr;
            this.lo = lo;
            this.hi = hi;
        }

        @Override
        public int[] call() {
            ArrayList<Integer> sortbudy = new ArrayList<>();
            if (hi == -1) {
                for (int i = 0; i< arr.length; i++) {
                    if (arr[i] >= lo) {
                        sortbudy.add(arr[i]);
                    }
                }
            } else {
                for (int i = 0; i< arr.length; i++) {
                    if ((arr[i] >= lo) && (arr[i] < hi)) {
                        sortbudy.add(arr[i]);
                    }
                }
            }
            int[] sortm = sortbudy.stream().mapToInt(i->i).toArray();
            new QuickeInsertionSort().sort(sortm);
            return sortm;
        }
    }
    private int[] margePart(List<Future<int[]>> farr, int len) throws InterruptedException, ExecutionException {
        int[] enArray = new int[len];
        int[] part;
        int s = 0;
        for (Future<int[]> i: farr) {
            part = i.get();
            System.arraycopy(part, 0, enArray, s, part.length);
            s = s + part.length;
        }
        return enArray;
    }
}
