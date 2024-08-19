package com.eric.ai;

import com.eric.ai.repository.JsonFileRepository;

public class FindAny {

    static final String JSON_FILENAME = "src/main/resources/ai-tech-dataset-files/all/all.json";

    public static void main(String[] args) {

        final String KEYWORD = "fabric";

        JsonFileRepository.getItemDataStreamFromJsonFile(JSON_FILENAME, ";")
                .filter(str -> str.toLowerCase().contains(KEYWORD.toLowerCase()))
                .forEach(System.out::println);
    }

}
