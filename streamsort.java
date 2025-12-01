import java.util.*;

class streamsort {
    void parallelsort(int[] a) {
        int i = 0;
        for(int number : Arrays.stream(a)
                .parallel()
                .sorted()
                .toArray()){
            a[i] = number;
            i++;
        }
    }
    void clasicsort(int[] a) {
        int i = 0;
        for(int number : Arrays.stream(a)
                .sorted()
                .toArray()){
            a[i] = number;
            i++;
        }
    }
    void sort2(int[] a) {
        Arrays.stream(a)
                .parallel()
                .sorted()
                .toArray();

    }
}
