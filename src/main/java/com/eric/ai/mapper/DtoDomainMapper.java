package com.eric.ai.mapper;

import com.eric.ai.domain.Category;
import com.eric.ai.domain.Item;
import com.eric.ai.dto.CategoryDto;
import com.eric.ai.dto.CsvDto;
import com.eric.ai.dto.CsvItemDto;
import com.eric.ai.dto.ItemDto;

public class DtoDomainMapper {

    public static Category dtoToCategoryBuilder (CategoryDto dto) {

        return new Category(dto.name(), dto.parents(), dto.acronym(), dto.level(), dto.childNames(), dto.items());
    }

    public static ItemDto csvToItemDtoBuilder(CsvDto csvDto) {
        return new ItemDto(csvDto.name(), null, null, null, null, null, csvDto.azure(), null);
    }

    public static Item csvItemDtoToItemBuilder(CsvItemDto csvItemDto) {
        return new Item()
    }
}
