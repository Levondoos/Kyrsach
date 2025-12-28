package main;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class Versus {
    static void start(int[] arr, String name, long maxCount) throws IOException, InterruptedException, ExecutionException{
        Random r = new Random();

        int rep = 10; //Число повторений
        long[][] mt = new long[arr.length][rep];

        int l = 1, t = 0;
        int k = 100; //Начальный интервал

        CSVtable2 table = new CSVtable2();
        boolean flag = true;
        String[] handle = new String[arr.length+1];
        handle[0] = "number";
        //Начало тестов
        while(l<(maxCount+1)){
            for(int j = 0; j<rep; j++){
                //Создаём случайный массив
                int[] nums = new int[l];
                for (int i = 0; i < nums.length; i++){
                    nums[i] = r.nextInt(l) + 1;
                }
                //Сама сортировка
                for(int s = 0; s<arr.length; s++){
                    long Time = sort(nums.clone(), handle, arr[s], s, flag);
                    mt[s][j] = Time;
                }
                flag = false;
            }
            System.out.print("\nЧисло элементов: " + l + " Среднее время: ");
            //Подсчет времени
            long sr[] = new long[arr.length + 1];
            sr[0] = l;
            for(int s = 1; s<sr.length; s++) {
                sr[s] = mean(mt[s-1]);
                System.out.print(sr[s] + " ");
            }
            table.add(sr);

            //Увеличение интервала
            if (t == 0) {
                l = k;
                t++;
            }
            else if (t == 10){
                k = k*10;
                l += k;
                t=2;
            }
            else {
                l += k;
                t++;
            }
        }
        table.save(name, handle);
    }
    public static long sort(int[] a, String[] hand, int n, int s, boolean flag) throws InterruptedException, ExecutionException {
        long startTime, endTime;
        switch(n) {
            case 1:
                startTime = System.nanoTime();
                new ParallelQuickInsertionSort().sort(a);
                endTime = System.nanoTime();
                if (flag)  hand[s+1] = "ParallelQuickInsertionSort";
                return endTime - startTime;
            case 2:
                startTime = System.nanoTime();
                new streamsort().parallelsort(a);
                endTime = System.nanoTime();
                if (flag)  hand[s+1] = "StreamParallelSort";
                return endTime - startTime;
            case 3:
                startTime = System.nanoTime();
                new streamsort().clasicsort(a);
                endTime = System.nanoTime();
                if (flag)  hand[s+1] = "StreamClasicSort";
                return endTime - startTime;
            case 4:
                startTime = System.nanoTime();
                new ParallelQuickSort().sort(a);
                endTime = System.nanoTime();
                if (flag)  hand[s+1] = "ParallelQuickSort";
                return endTime - startTime;
            case 5:
                startTime = System.nanoTime();
                int[] b = new ParallelMergeSort().sort(a);
                endTime = System.nanoTime();
                if (flag)  hand[s+1] = "ParallelMergeSort";
                return endTime - startTime;
            case 6:
                startTime = System.nanoTime();
                new QuickeInsertionSort().sort(a);
                endTime = System.nanoTime();
                if (flag)  hand[s+1] = "QuickeInsertionSort";
                return endTime - startTime;
            case 7:
                startTime = System.nanoTime();
                new QuickeSort().sort(a);
                endTime = System.nanoTime();
                if (flag)  hand[s+1] = "QuickeSort";
                return endTime - startTime;
            case 8:
                startTime = System.nanoTime();
                int[] c = new MergeSort().sort(a);
                endTime = System.nanoTime();
                if (flag)  hand[s+1] = "MergeSort";
                return endTime - startTime;
            case 9:
                startTime = System.nanoTime();
                int[] d = new SampleParSort().sort(a,  20);
                endTime = System.nanoTime();
                if (flag)  hand[s+1] = "ParallelSampleSort";
                return endTime - startTime;
            case 10:
                startTime = System.nanoTime();
                int[] e = new SampleSort().sort(a,  20);
                endTime = System.nanoTime();
                if (flag)  hand[s+1] = "SampleSort";
                return endTime - startTime;
            default:
                System.out.println("Не найдена сортировка");
                return 0;
        }
    }
    public static long mean(long[] t) {
        long sum =0;
        for(int i = 0; i < t.length; i++){
            sum += t[i];
        }
        return sum / (t.length);
    }
}
