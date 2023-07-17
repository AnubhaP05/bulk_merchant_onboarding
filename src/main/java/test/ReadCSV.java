package main.java.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReadCSV {
    public static String[] main(String[] args) {
        String[] data = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(" src/main/test/data.csv"));
            String line = br.readLine();
            data = line.split(",");
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;

    }
}
