package com.eric.ai.repository;

import com.eric.ai.dto.*;
import com.eric.ai.exceptions.FileNotFoundException;
import com.eric.ai.exceptions.JsonFileException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class JsonFileRepository {

    public static MastersRecordDto getMastersRecordDto(String mastersRecordFileName) {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(mastersRecordFileName));
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonData, MastersRecordDto.class);
        } catch (IOException e) {
            throw new JsonFileException("Cannot get file content: " + mastersRecordFileName + ". Exception: " + e.getMessage());
        }
    }

    public static void mergeCategoryDtoFile(CategoryDto categoryDto, String origFileName, String outputFileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Path path = Paths.get(origFileName);
        if(!Files.exists(path)) {
            try {
                objectMapper.writeValue(new File(outputFileName), categoryDto);
            } catch (IOException e) {
                throw new JsonFileException("Cannot write to file: " + outputFileName + ". Exception: " + e.getMessage());
            }
        } else {
            CategoryDto categoryDtoOrig = getCategoryDtoFromFile(origFileName);
            categoryDto.items().addAll(categoryDtoOrig.items());
            // create new dto with metadata values from origin
            CategoryDto newCategoryDto = new CategoryDto(categoryDtoOrig.name(), categoryDtoOrig.parents(),
                    categoryDtoOrig.acronym(), categoryDtoOrig.level(), categoryDtoOrig.childNames(), categoryDto.items());
            try {
                objectMapper.writeValue(new File(outputFileName), newCategoryDto);
            } catch (IOException e) {
                throw new JsonFileException("Cannot write to file: " + outputFileName + ". Exception: " + e.getMessage());
            }
        }
    }

    public static void saveCategoryDtoFile(CategoryDto categoryDto, String outputFileName) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            objectMapper.writeValue(new File(outputFileName), categoryDto);
        } catch (IOException e) {
            throw new JsonFileException("Cannot write to file: " + outputFileName + ". Exception: " + e.getMessage());
        }
    }

    public static CategoryDto getCategoryDtoFromFile(String fileName) {
        Path path = Paths.get(fileName);
        if(!Files.exists(path)) {
            throw new FileNotFoundException("File does not exist: " + path);
        }
        byte[] jsonData = null;
        try {
            jsonData = Files.readAllBytes(path);
        } catch (IOException e) {
            throw new JsonFileException("Cannot read file content: " + fileName + ". Exception: " + e.getMessage());
        }
        CategoryDto categoryDto = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            categoryDto = objectMapper.readValue(jsonData, CategoryDto.class);
        } catch (IOException e) {
            throw new JsonFileException("Cannot map json data to CategoryDto.class. Exception: " + e.getMessage());
        }
        return categoryDto;
    }

    public static List<ItemDto> getItemDtoListFromFile(String fileName) {
        Path path = Paths.get(fileName);
        if(!Files.exists(path)) {
            throw new FileNotFoundException("File does not exist: " + path);
        }
        List<ItemDto> itemDtoList;
        try {
            itemDtoList = Files.lines(path)
                    .map(JsonFileRepository::createItemDtoFromLine)
                    .toList();
        } catch (IOException e) {
            throw new JsonFileException("Cannot read line from file: " + fileName + ". Exception: " + e.getMessage());
        }
        return itemDtoList;
    }

    public static List<ItemContainerDto> getItemContainerDtoListFromFile(String fileName) {
        Path path = Paths.get(fileName);
        if(!Files.exists(path)) {
            throw new FileNotFoundException("File does not exist: " + path);
        }
        byte[] jsonData = null;
        try {
            jsonData = Files.readAllBytes(path);
        } catch (IOException e) {
            throw new JsonFileException("Cannot read file content: " + fileName + ". Exception: " + e.getMessage());
        }
        List<ItemContainerDto> containerDtoList;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            containerDtoList = objectMapper.readValue(jsonData, new TypeReference<List<ItemContainerDto>>() {});
        } catch (IOException e) {
            throw new JsonFileException("Cannot map json data to ItemContainerDto.class. Exception: " + e.getMessage());
        }
        return containerDtoList;
    }

    public static Stream<String> getItemDataStreamFromJsonFile(String fileName, String separator) {
        List<ItemContainerDto> itemContainerDtoList = JsonFileRepository.getItemContainerDtoListFromFile(fileName);
        return itemContainerDtoList.stream()
                .map(ItemContainerDto::items)
                .flatMap(Collection::stream)
                .flatMap(item -> item.getDataStream(separator, null));
    }

    private static ItemDto createItemDtoFromLine(String line) {
        ItemDto itemDto;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            itemDto = objectMapper.readValue(line, ItemDto.class);
        } catch (IOException e) {
            throw new JsonFileException("Cannot map json data: " + line + " to ItemDto.class. Exception: " + e.getMessage());
        }
        return itemDto;
    }

}
