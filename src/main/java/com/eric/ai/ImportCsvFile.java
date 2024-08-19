package com.eric.ai;

import com.eric.ai.domain.Category;
import com.eric.ai.dto.CategoryDto;
import com.eric.ai.dto.CategoryFileDto;
import com.eric.ai.dto.CsvDto;
import com.eric.ai.dto.ItemDto;
import com.eric.ai.exceptions.CsvFileException;
import com.eric.ai.mapper.DtoDomainMapper;
import com.eric.ai.repository.JsonFileRepository;
import com.eric.ai.service.CategoryDtoService;
import com.eric.ai.service.CategoryService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class ImportCsvFile {

    static final String CSV_FILENAME = "src/main/resources/ai-tech-dataset-files/azure/azure-services.txt";
    static final String FILE_FOLDER = "src/main/resources/ai-tech-dataset-files/azure/";
    static final String CHILD_FOLDER = "src/main/resources/ai-tech-dataset-files/azure/";

    public static void main(String[] args) {

        getNameList().forEach(name -> {
            List<ItemDto> itemDtoList =  getCsvDtoList(name).stream()
                                            .map(DtoDomainMapper::csvToItemDtoBuilder)
                                            .toList();
            CategoryDto categoryDto =  CategoryDtoService.categoryDtoFromItemDto(itemDtoList, name);
            String outputFileName = FILE_FOLDER + "1_" + name + ".json";
            JsonFileRepository.saveCategoryDtoFile(categoryDto, outputFileName);
            CategoryFileDto categoryFileDto = new CategoryFileDto(name, outputFileName, CHILD_FOLDER);
            Category category = CategoryService.recursiveCategoryBuilder(categoryFileDto);
            category.recursiveDisplay();
        });

    }

    private static Set<String> getNameList() {
        Set<String> nameList = new HashSet<>();
        try {
            Files.lines(Path.of(CSV_FILENAME))
                    .map(line -> line.split(";"))
                    .map(strArray -> new CsvDto(strArray[0], strArray[1], List.of(strArray[2])))
                    .forEach(csvDto -> nameList.add(csvDto.fileName()));
        } catch (IOException e) {
            throw new CsvFileException("Can not read file: " + CSV_FILENAME + ". Exception: " + e.getMessage());
        }
        return nameList;
    }

    private static List<CsvDto> getCsvDtoList(String name) {
        List<CsvDto> csvDtoList = new ArrayList<>();
        try {
            Files.lines(Path.of(CSV_FILENAME))
                    .map(line -> line.split(";"))
                    .filter(strArray -> strArray[0].equals(name))
                    .map(strArray -> new CsvDto(name, strArray[1], List.of(strArray[2])))
                    .forEach(csvDtoList::add);

        } catch (IOException e) {
            throw new CsvFileException("Can not read file: " + CSV_FILENAME + ". Exception: " + e.getMessage());
        }
        return csvDtoList;
    }

}
