package com.eric.ai.dto;

import com.eric.ai.domain.Item;

import java.util.List;

public record ItemContainerDto(
        String category,
        List<Item> items) {
}
