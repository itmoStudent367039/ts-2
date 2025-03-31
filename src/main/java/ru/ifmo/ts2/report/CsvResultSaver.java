package ru.ifmo.ts2.report;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CsvResultSaver {

    private final Map<String, Double> results = new ConcurrentHashMap<>();

    @Value("${csv.output.path:output.csv}")
    private String outputPath;

    @Value("${csv.delimiter:,}")
    private String delimiter;

    public void saveResult(String moduleName, double result) {
        results.put(moduleName, result);
    }

    public void writeResultsToCsv() {
        try (FileWriter writer = new FileWriter(outputPath)) {
            writer
                    .append("service_name")
                    .append(delimiter)
                    .append("result")
                    .append("\n");
            results.forEach((serviceName, result) -> {
                try {
                    writer
                            .append(serviceName)
                            .append(delimiter)
                            .append(String.valueOf(result))
                            .append("\n");
                } catch (IOException e) {
                    throw new RuntimeException("Error writing to CSV", e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException("Failed to write CSV file", e);
        }
    }
}
