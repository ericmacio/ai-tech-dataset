package com.eric.ai;

import com.eric.ai.exceptions.CsvFileException;
import com.eric.ai.repository.JsonFileRepository;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;


public class BuildCsvAll {

    static final String JSON_FILENAME = "src/main/resources/ai-tech-dataset-files/all/all.json";
    static final String CSV_FILENAME = "src/main/resources/ai-tech-dataset-files/all/all.csv";

    public static void main(String[] args) {

        String headers = "category;sub category;domain;description;provider;product";

        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(CSV_FILENAME), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            try {
                writer.write(headers);
                writer.newLine();
            } catch (IOException e) {
                throw new CsvFileException("Cannot write headers to file: " + CSV_FILENAME + ". Exception: " + e.getMessage());
            }
            JsonFileRepository.getItemDataStreamFromJsonFile(JSON_FILENAME, ";")
                    .forEach(str -> {
                        try {
                            writer.write(str);
                            writer.newLine();
                        } catch (IOException e) {
                            throw new CsvFileException("Cannot write to file: " + CSV_FILENAME + ". Exception: " + e.getMessage());
                        }
                    });
            writer.flush();
        } catch (IOException e) {
            throw new CsvFileException("Cannot create writer for file: " + CSV_FILENAME + ". Exception: " + e.getMessage());
        }

    }
}
