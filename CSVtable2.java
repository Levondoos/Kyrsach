import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVtable2 {
    public List<String[]> measure = new ArrayList<>();
    void add(long[] arr) {
        String[] stringArray = Arrays.stream(arr)
                .mapToObj(String::valueOf)
                .toArray(String[]::new);
        measure.add(stringArray);
    }
    void save(String name, String[] handle) throws IOException{
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(name + ".csv", StandardCharsets.UTF_8),
                    CSVWriter.DEFAULT_SEPARATOR,
                    CSVWriter.DEFAULT_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);
            writer.writeNext(handle);
            for (int i = 0; i < measure.size(); i++) {
                writer.writeNext(measure.get(i));
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing to CSV file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}