package id.rascal.response_kit.template;

public record MetaTemplate(
    long page,
    long size,
    long totalElements,
    long totalPages,
    boolean hasNext,
    boolean hasPrevious
) { }
