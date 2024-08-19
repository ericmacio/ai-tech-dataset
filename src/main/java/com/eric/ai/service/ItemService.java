package com.eric.ai.service;

import com.eric.ai.domain.Category;
import com.eric.ai.domain.Item;
import com.eric.ai.dto.CategoryFileDto;
import com.eric.ai.dto.MastersRecordDto;

import java.util.Collection;
import java.util.stream.Stream;

public class ItemService {

    public static Stream<Item> getItemStream(MastersRecordDto dto, String fileFolder, String childFolder) {
        return dto.masters().stream()
                .map(m -> new CategoryFileDto(m, fileFolder + m + ".json", childFolder))
                .map(CategoryService::recursiveCategoryBuilder)
                .map(Category::recursiveItems)
                .flatMap(Collection::stream);
    }

}
