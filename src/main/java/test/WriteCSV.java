package main.java.test;

import java.io.FileWriter;
import java.io.IOException;

public class WriteCSV {
    public static void main(String[] args) {
        String csvFile = "output.csv";
        String csvHeader = "Name,Age,City";
        String[] data1 = {"John", "25", "New York"};
        String[] data2 = {"Alice", "30", "London"};

        try (FileWriter writer = new FileWriter(csvFile)) {
            writer.append(csvHeader);
            writer.append("\n");
            writer.append(String.join(",", data1));
            writer.append("\n");
            writer.append(String.join(",", data2));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
