package es.upm.fi.cloud.YellowTaxiTrip.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

/**
 * @deprecated
 * @author Wenqi Jiang & Zhuo Cheng
 */
public class SampleGenerator {

    public static void main(String[] args) throws Exception {
        Path filePath = Path.of("src/main/resources/yellow_tripdata_2022-03_sample.csv");
        Files.deleteIfExists(filePath);
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/yellow_tripdata_2022-03.csv"))) {
            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                if (count % 250 == 0) {
                    Files.writeString(filePath, line + "\n", StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                }
                count++;
            }
        }
    }
}
