package com.eric.ai;

import com.eric.ai.domain.Item;
import com.eric.ai.dto.ItemContainerDto;
import com.eric.ai.dto.MastersRecordDto;
import com.eric.ai.exceptions.JsonFileException;
import com.eric.ai.repository.JsonFileRepository;
import com.eric.ai.service.ItemService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;


public class BuildJsonAll {

    static final String MASTERS_RECORD_FOLDER = "src/main/resources/ai-tech-dataset-files/master/";
    static final String FILE_FOLDER = "src/main/resources/ai-tech-dataset-files/";
    static final String JSON_FILENAME = "src/main/resources/ai-tech-dataset-files/all/all.json";

    public static void main(String[] args) {

        List<ItemContainerDto> ItemContainerDtoList = getItemContainerDtoList();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            objectMapper.writeValue(new File(JSON_FILENAME), ItemContainerDtoList);
        } catch (IOException e) {
            throw new JsonFileException("Cannot write to file: " + JSON_FILENAME + ". Exception: " + e.getMessage());
        }

    }

    private static List<ItemContainerDto> getItemContainerDtoList() {

        final List<String> providers = Arrays.asList("aws", "azure");

        Map<String, List<Item>> itemMap = new HashMap<>();

        List<ItemContainerDto> ItemContainerDtoList = new ArrayList<>();

        providers.forEach(provider -> {
            String masterFileName = MASTERS_RECORD_FOLDER + provider + "_masters-record.json";
            String fileFolder = FILE_FOLDER + provider + "/";
            MastersRecordDto mastersRecordDto = JsonFileRepository.getMastersRecordDto(masterFileName);
            ItemService.getItemStream(mastersRecordDto, fileFolder, fileFolder)
                    .forEach(item -> {
                        String categoryName = item.getCategory();
                        if(!itemMap.containsKey(categoryName)) {
                            itemMap.put(categoryName, new ArrayList<>());
                        }
                        itemMap.get(categoryName).add(item);
                    });
        });

        itemMap.forEach((categoryName, itemList) -> {
            ItemContainerDto itemContainerDto = new ItemContainerDto(categoryName, itemList);
            ItemContainerDtoList.add(itemContainerDto);
        });

        return ItemContainerDtoList;
    }
}