package com.eric.ai;

import com.eric.ai.repository.JsonFileRepository;


public class DisplayAllData {

    static final String JSON_FILENAME = "src/main/resources/ai-tech-dataset-files/all/all.json";

    public static void main(String[] args) {

        final String KEYWORD = "opensource";

        JsonFileRepository.getItemDataStreamFromJsonFile(JSON_FILENAME, ";")
                .forEach(System.out::println);
    }
}
