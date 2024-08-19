package com.eric.ai.dto;

import java.util.List;

public record CsvDto(
        String fileName,
        String name,
        List<String> azure) {
}
