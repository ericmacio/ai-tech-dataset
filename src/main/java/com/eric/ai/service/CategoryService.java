package com.eric.ai.service;

import com.eric.ai.domain.Category;
import com.eric.ai.dto.CategoryDto;
import com.eric.ai.dto.CategoryFileDto;
import com.eric.ai.dto.MastersRecordDto;
import com.eric.ai.repository.JsonFileRepository;
import com.eric.ai.mapper.DtoDomainMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CategoryService {

    public static Stream<Category> getCategoryStream(MastersRecordDto dto, String fileFolder, String childFolder) {
        return dto.masters().stream()
                .map(m -> new CategoryFileDto(m, fileFolder + m + ".json", childFolder))
                .map(CategoryService::recursiveCategoryBuilder);
    }

    public static Category recursiveCategoryBuilder(CategoryFileDto categoryFileDto)  {
        CategoryDto categoryDto = JsonFileRepository.getCategoryDtoFromFile(categoryFileDto.fileName());
        Category category = DtoDomainMapper.dtoToCategoryBuilder(categoryDto);
        List<String> itemParents = new ArrayList<>(category.getParents());
        itemParents.add(category.getName());
        category.getItems().forEach(item -> {
            item.addParents(itemParents);
            item.setCategory(category.getName());
        });
        category.getChildNames().stream()
                .map(name -> buildChildCategory(categoryFileDto, name))
                .forEach(category::addChild);
        return category;
    }

    private static Category buildChildCategory(CategoryFileDto categoryFileDto, String childCategoryName) {
        String childFileName = categoryFileDto.childFolder() + childCategoryName + ".json";
        CategoryFileDto childCategoryFileDto = new CategoryFileDto(childCategoryName, childFileName, categoryFileDto.childFolder());
        return CategoryService.recursiveCategoryBuilder(childCategoryFileDto);
    }

}
