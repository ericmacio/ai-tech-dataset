package com.eric.ai;

import com.eric.ai.domain.Category;
import com.eric.ai.dto.CategoryDto;
import com.eric.ai.dto.CategoryFileDto;
import com.eric.ai.dto.ItemDto;
import com.eric.ai.repository.JsonFileRepository;
import com.eric.ai.service.CategoryService;
import com.eric.ai.service.CategoryDtoService;

import java.util.List;


public class ImportNdJsonFile {

    static final String NAME = "";
    static final String ORIG_FILENAME = "src/main/resources/ai-tech-dataset-files/orig/1_" + NAME + ".json";
    static final String INPUT_FILENAME = "src/main/resources/ai-tech-dataset-files/new/1_" + NAME + ".ndjson";
    static final String OUTPUT_FILENAME = "src/main/resources/ai-tech-dataset-files/orig/1_" + NAME + ".json";
    static final String CHILD_FOLDER = "src/main/resources/ai-tech-dataset-files/orig/";

    public static void main(String[] args) {

        List<ItemDto> itemDtoList = JsonFileRepository.getItemDtoListFromFile(INPUT_FILENAME);
        CategoryDto categoryDto =  CategoryDtoService.categoryDtoFromItemDto(itemDtoList, NAME);
        JsonFileRepository.mergeCategoryDtoFile(categoryDto, ORIG_FILENAME, OUTPUT_FILENAME);
        CategoryFileDto categoryFileDto = new CategoryFileDto(NAME, OUTPUT_FILENAME, CHILD_FOLDER);
        Category category = CategoryService.recursiveCategoryBuilder(categoryFileDto);
        category.recursiveDisplay();

    }
}
