package com.eric.ai.service;

import com.eric.ai.domain.Instance;
import com.eric.ai.domain.Item;
import com.eric.ai.dto.CategoryDto;
import com.eric.ai.dto.ItemDto;

import java.util.ArrayList;
import java.util.List;

public class CategoryDtoService {

    static final Integer DEFAULT_LEVEL = 1;
    static final List<String> DEFAULT_PARENTS = new ArrayList<>(List.of("master-categories"));
    static final String DEFAULT_ACRONYM = null;
    static final List<String> DEFAULT_CHILD_NAMES = new ArrayList<>();

    public static CategoryDto categoryDtoFromItemDto (List<ItemDto> itemDtoList, String categoryName) {
        return categoryDtoFromItemDto(itemDtoList, categoryName, DEFAULT_PARENTS,
                DEFAULT_ACRONYM, DEFAULT_LEVEL, DEFAULT_CHILD_NAMES);
    }

    public static CategoryDto categoryDtoFromItemDto (List<ItemDto> itemDtoList, String categoryName, List<String> parents,
                                                 String acronym, Integer level, List<String> childNames) {

        List<Item> itemList = new ArrayList<>();
        itemDtoList.forEach(itemDto -> {
            List<Instance> instanceList = new ArrayList<>();
            if(itemDto.aws() != null) {
                instanceList.add(new Instance("cloud", "aws", itemDto.aws()));
            }
            if(itemDto.azure() != null) {
                instanceList.add(new Instance("cloud", "azure", itemDto.azure()));
            }
            if(itemDto.apache() != null) {
                instanceList.add(new Instance("editor", "apache", itemDto.apache()));
            }
            if(itemDto.redhat() != null) {
                instanceList.add(new Instance("editor", "redhat", itemDto.redhat()));
            }
            if(itemDto.opensource() != null) {
                instanceList.add(new Instance("editor", "opensource", itemDto.opensource()));
            }
            if(itemDto.microsoft() != null) {
                instanceList.add(new Instance("editor", "microsoft", itemDto.microsoft()));
            }
            itemList.add(new Item(itemDto.description(), itemDto.acronym(), instanceList));
        });

        return new CategoryDto(categoryName, parents, acronym, level, childNames, itemList);

    }
}
