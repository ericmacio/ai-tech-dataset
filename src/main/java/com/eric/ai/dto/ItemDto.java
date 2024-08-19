package com.eric.ai.dto;

import java.util.List;

public record ItemDto(
        String description,
        String acronym,
        List<String> aws,
        List<String> redhat,
        List<String> apache,
        List<String> opensource,
        List<String> azure,
        List<String> microsoft) {}
