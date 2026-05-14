package com.rascal.my_lib.dto;

public record MetaTemplate(
    long page,
    long size,
    long totalElements,
    long totalPages,
    boolean hasNext,
    boolean hasPrevious
) { }
